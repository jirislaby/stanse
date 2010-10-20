#include "cfg_builder.hpp"

#include <boost/assert.hpp>
#include <boost/lexical_cast.hpp>
#include <boost/utility.hpp>

#include <clang/AST/Stmt.h>
#include <clang/AST/StmtCXX.h>
#include <clang/AST/Expr.h>
#include <clang/AST/ExprCXX.h>
#include <clang/AST/Decl.h>
#include <clang/AST/DeclCXX.h>
#include <clang/AST/DeclTemplate.h>
#include <clang/Basic/SourceManager.h>
#include <clang/Basic/FileManager.h>

#include <list>

namespace {

// FIXME: Assumes that sizeof(wchar_t) == 2
std::vector<boost::int64_t> string_literal_to_value_array(clang::StringLiteral const * sl)
{
	std::vector<boost::int64_t> res;

	llvm::StringRef str = sl->getString();
	if (sl->isWide())
	{
		for (std::size_t i = 0; i < str.size(); i += 2)
			res.push_back(str[i+1] * 256 + (unsigned char)str[i]);
	}
	else
	{
		for (std::size_t i = 0; i < str.size(); ++i)
			res.push_back(str[i]);
	}

	res.push_back(0);
	return res;
}

struct context
{
	context(cfg & c, clang::FunctionDecl const * fn, clang::SourceManager const & sm,
		filename_store & fnames, detail::build_cfg_visitor_base & visitor, std::string const & static_prefix)
		: m_static_prefix(static_prefix), m_sm(sm), m_fnames(fnames), m_visitor(visitor), g(c), m_fn(fn),
		m_head(add_vertex(g)), m_exc_exit_node(add_vertex(g)), m_term_exit_node(add_vertex(g))
	{
		g.entry(m_head);

		g[m_exc_exit_node].type = cfg::nt_exit;
		g[m_exc_exit_node].ops.push_back(cfg::operand(cfg::ot_const, "1"));

		auto_var_registration reg = { 0, eop(), m_exc_exit_node };
		m_auto_objects.push_back(reg);

		g[m_term_exit_node].type = cfg::nt_exit;
		g[m_term_exit_node].ops.push_back(cfg::operand(cfg::ot_const, "2"));

		this->build();
	}

	std::string m_static_prefix;
	clang::SourceManager const & m_sm;
	filename_store & m_fnames;
	detail::build_cfg_visitor_base & m_visitor;

	void register_decl_ref(clang::FunctionDecl const * fn)
	{
		// TODO: Will we ever need this function?
	}

	cfg & g;
	clang::FunctionDecl const * m_fn;


	enum eop_type { eot_none, eot_func, eot_oper, eot_const, eot_member, eot_node, eot_var, eot_varptr, eot_nodetgt, eot_vartgt };

	struct eop
	{
		eop_type type;
		cfg::op_id id;

		eop(cfg::operand op)
			: type(static_cast<eop_type>(op.type)), id(op.id)
		{
		}

		eop(eop_type type = eot_none, cfg::op_id id = boost::none)
			: type(type), id(id)
		{
		}
	};

	struct enode
	{
		cfg::node_type type;
		std::vector<eop> ops;
		clang::Stmt const * data;

		enode(cfg::node_type type, clang::Stmt const * data = 0)
			: type(type), data(data)
		{
		}

		enode & operator()(eop const & op)
		{
			ops.push_back(op);
			return *this;
		}

		enode & operator()(eop_type type, cfg::op_id id)
		{
			ops.push_back(eop(type, id));
			return *this;
		}
	};

	struct auto_var_registration
	{
		clang::CXXDestructorDecl const * destr;
		eop varptr;
		cfg::vertex_descriptor excnode;
	};

	std::list<auto_var_registration> m_auto_objects;
	typedef std::list<auto_var_registration>::iterator auto_object_iterator;

	typedef std::vector<auto_object_iterator> lifetime_context_t;
	std::vector<lifetime_context_t> m_fullexpr_lifetimes;
	std::vector<lifetime_context_t> m_block_lifetimes;
	std::vector<clang::Type const *> m_temporaries;

	cfg::vertex_descriptor m_head;
	std::set<cfg::vertex_descriptor> m_exit_nodes;
	cfg::vertex_descriptor m_exc_exit_node;
	cfg::vertex_descriptor m_term_exit_node;

	typedef std::pair<cfg::vertex_descriptor, auto_object_iterator> jump_sentinel_t;
	
	std::vector<jump_sentinel_t> m_break_sentinels;
	std::vector<jump_sentinel_t> m_continue_sentinels;

	std::map<clang::GotoStmt const *, cfg::vertex_descriptor> m_gotos;
	std::map<clang::LabelStmt const *, cfg::vertex_descriptor> m_labels;

	typedef std::pair<cfg::vertex_descriptor, std::map<std::string, cfg::vertex_descriptor> > case_context_t;
	std::vector<case_context_t> m_case_contexts;

	std::map<clang::NamedDecl const *, std::string> m_registered_names;

	jump_sentinel_t create_jump_sentinel()
	{
		return jump_sentinel_t(add_vertex(g), boost::prior(m_auto_objects.end()));
	}

	void join_jump_sentinel(jump_sentinel_t const & s, cfg::vertex_descriptor head)
	{
		BOOST_ASSERT(s.second == boost::prior(m_auto_objects.end()));
		this->join_nodes(s.first, head);
	}

	void connect_to_term(cfg::vertex_descriptor v)
	{
		cfg::edge_descriptor e = add_edge(v, m_term_exit_node, g).first;
		g[e].id = 2;
	}

	void connect_to_exc(cfg::vertex_descriptor v, cfg::vertex_descriptor target)
	{
		cfg::edge_descriptor e = add_edge(v, target, g).first;
		g[e].id = 1;
	}

	void connect_to_exc(cfg::vertex_descriptor v)
	{
		this->connect_to_exc(v, m_auto_objects.back().excnode);
	}

	cfg::vertex_descriptor duplicate_vertex(cfg::vertex_descriptor src)
	{
		BOOST_ASSERT(out_degree(src, g) == 0);

		cfg::vertex_descriptor res = add_vertex(g);
		g[res] = g[src];

		std::pair<cfg::in_edge_iterator, cfg::in_edge_iterator> in_edge_range = in_edges(src, g);
		for (; in_edge_range.first != in_edge_range.second; ++in_edge_range.first)
		{
			cfg::edge_descriptor e = add_edge(source(*in_edge_range.first, g), res, g).first;
			g[e] = g[*in_edge_range.first];
		}
		return res;
	}

	void join_nodes(cfg::vertex_descriptor src, cfg::vertex_descriptor dest)
	{
		g.redirect_vertex(src, dest);
		remove_vertex(src, g);
	}

	std::string get_name(clang::NamedDecl const * decl) const
	{
		std::map<clang::NamedDecl const *, std::string>::const_iterator it = m_registered_names.find(decl);
		if (it == m_registered_names.end())
			return make_decl_name(decl, m_static_prefix);
		else
			return it->second;
	}

	void register_locals(clang::FunctionDecl const * fn)
	{
		if (fn->getResultType()->isStructureOrClassType())
			g.add_param("p:return");

		if (clang::CXXMethodDecl const * d = llvm::dyn_cast<clang::CXXMethodDecl>(fn))
		{
			if (!d->isStatic())
				g.add_param("p:this");
		}

		for (clang::FunctionDecl::param_const_iterator it = fn->param_begin(); it != fn->param_end(); ++it)
		{
			clang::ParmVarDecl const * d = *it;

			std::string name = "p:" + d->getQualifiedNameAsString();
			m_registered_names[d] = name;
			g.add_param(name);
		}

		std::set<std::string> used_names;
		for (clang::FunctionDecl::decl_iterator it = fn->decls_begin(); it != fn->decls_end(); ++it)
		{
			clang::Decl const * decl = *it;
			if (clang::VarDecl const * d = llvm::dyn_cast<clang::VarDecl>(decl))
			{
				std::string name_base;
				if (d->isStaticLocal() || decl->getKind() == clang::Decl::ParmVar)
					continue;

				name_base = "l:" + d->getQualifiedNameAsString();
				name_base += ':';

				std::string name;
				for (std::size_t i = 0;; ++i)
				{
					std::ostringstream ss;
					ss << name_base << i;
					if (used_names.find(ss.str()) == used_names.end())
					{
						name = ss.str();
						used_names.insert(name);
						m_registered_names[d] = name;
						break;
					}
				}

				g.add_local(name);
			}
		}
	}

	eop make_temporary(clang::Type const * type)
	{
		std::ostringstream ss;
		ss << "t:" << m_temporaries.size();

		m_temporaries.push_back(type);
		g.add_local(ss.str());
		return eop(eot_var, ss.str());
	}

	cfg::operand make_rvalue(eop const & op)
	{
		BOOST_ASSERT(op.type < eot_nodetgt);
		return cfg::operand(static_cast<cfg::op_type>(op.type), op.id);
	}

	cfg::operand make_rvalue(cfg::vertex_descriptor & head, eop const & op)
	{
		if (op.type == eot_nodetgt)
		{
			cfg::vertex_descriptor res = head;
			this->add_node(head, enode(cfg::nt_call)
				(eot_oper, "*")
				(eot_node, op.id));
			return cfg::operand(cfg::ot_node, res);
		}
		else if (op.type == eot_vartgt)
		{
			cfg::vertex_descriptor res = head;
			this->add_node(head, enode(cfg::nt_call)
				(eot_oper, "*")
				(eot_var, op.id));
			return cfg::operand(cfg::ot_node, res);
		}

		return this->make_rvalue(op);
	}

	eop make_param(cfg::vertex_descriptor & head, eop const & op, clang::Type const * type)
	{
		if (type->isReferenceType() && !this->is_lvalue(op))
		{
			eop temp = this->make_temporary(type);
			this->add_node(head, enode(cfg::nt_call)
				(eot_oper, "=")
				(this->make_address(temp))
				(op));
			return this->make_address(temp);
		}

		if (type->isReferenceType() || type->isStructureOrClassType())
			return this->make_address(op);
		else
			return op;
	}

	template <typename ParamIter, typename ArgIter>
	void append_args(cfg::vertex_descriptor & head, enode & node, ParamIter param_first, ParamIter param_last, ArgIter arg_first, ArgIter arg_last)
	{
		for (; param_first != param_last; ++param_first, ++arg_first)
			node(this->make_param(head, this->build_expr(head, *arg_first), (*param_first)->getType().getTypePtr()));
		BOOST_ASSERT(arg_first == arg_last);
	}

	bool is_lvalue(eop const & op)
	{
		return op.type == eot_func || op.type == eot_nodetgt || op.type == eot_vartgt || op.type == eot_var;
	}

	eop make_address(eop op)
	{
		switch (op.type)
		{
		case eot_func:
			break;
		case eot_nodetgt:
			op.type = eot_node;
			break;
		case eot_vartgt:
			op.type = eot_var;
			break;
		case eot_var:
			op.type = eot_varptr;
			break;
		default:
			BOOST_ASSERT(0 && "lvalue is required to form an address");
		}
		return op;
	}

	eop make_deref(cfg::vertex_descriptor & head, eop op)
	{
		switch (op.type)
		{
		case eot_func:
			break;
		case eot_node:
			op.type = eot_nodetgt;
			break;
		case eot_nodetgt:
			op = this->make_rvalue(head, op);
			BOOST_ASSERT(op.type == eot_node);
			op.type = eot_nodetgt;
			break;
		case eot_varptr:
			op.type = eot_var;
			break;
		case eot_var:
			op.type = eot_vartgt;
			break;
		case eot_vartgt:
			op = this->make_rvalue(head, op);
			BOOST_ASSERT(op.type == eot_node);
			op.type = eot_nodetgt;
			break;
		default:
			BOOST_ASSERT(0 && "lvalue is required to form an address");
		}
		return op;
	}

	/**
	 * \brief Convert an eop to an op of type ot_node and of the same value.
	 */
	cfg::vertex_descriptor make_node(cfg::vertex_descriptor & head, eop op)
	{
		op = this->make_rvalue(head, op);
		if (op.type != eot_node)
			return this->add_node(head, enode(cfg::nt_value)(op));
		return boost::get<cfg::vertex_descriptor>(op.id);
	}

	cfg::vertex_descriptor make_cond_node(cfg::vertex_descriptor & head, eop op)
	{
		op = this->make_rvalue(head, op);
		if (op.type != eot_node || in_degree(head, g) != 1)
			return this->add_node(head, enode(cfg::nt_value)(op));

		cfg::vertex_descriptor res = boost::get<cfg::vertex_descriptor>(op.id);
		if (source(*in_edges(head, g).first, g) != res)
			return this->add_node(head, enode(cfg::nt_value)(op));

		return res;
	}

	cfg::node convert_node(cfg::vertex_descriptor & head, enode const & node)
	{
		cfg::node n;
		n.type = node.type;
		for (std::size_t i = 0; i < node.ops.size(); ++i)
			n.ops.push_back(this->make_rvalue(head, node.ops[i]));

		if (node.data)
		{
			range_tag r = {};

			clang::SourceRange sr = node.data->getSourceRange();
			clang::PresumedLoc pl = m_sm.getPresumedLoc(sr.getBegin());
			r.fname = m_fnames.add(pl.getFilename());
			r.start_line = pl.getLine();
			r.start_col = pl.getColumn();
			pl = m_sm.getPresumedLoc(sr.getEnd());
			r.end_line = pl.getLine();
			r.end_col = pl.getColumn();

			n.tags.insert(g.add_tag(r));
		}
		return n;
	}

	cfg::vertex_descriptor add_node(cfg::vertex_descriptor & head, enode const & node)
	{
		BOOST_ASSERT(g[head].type == cfg::nt_none);
		BOOST_ASSERT(g[head].ops.empty());

		g[head] = this->convert_node(head, node);

		cfg::vertex_descriptor new_head = add_vertex(g);
		add_edge(head, new_head, g);
		
		using std::swap;
		swap(head, new_head);
		return new_head;
	}

	void set_cond(cfg::vertex_descriptor node, std::size_t index, std::string cond)
	{
		BOOST_ASSERT(in_degree(node, g) == 1);
		cfg::edge_descriptor edge = *in_edges(node, g).first;
		g[edge].id = index;
		g[edge].cond = cond;
	}

	void begin_lifetime_context(std::vector<lifetime_context_t> & ctx)
	{
		ctx.push_back(lifetime_context_t());
	}

	void end_lifetime_context(cfg::vertex_descriptor & head, std::vector<lifetime_context_t> & ctx)
	{
		BOOST_ASSERT(!ctx.empty());
		for (std::size_t i = ctx.back().size(); i != 0; --i)
		{
			auto_object_iterator const & reg_it = ctx.back()[i-1];
			this->unregister_destructible_var(head, reg_it);
		}
		ctx.pop_back();
	}

	void generate_destructor_chain(cfg::vertex_descriptor & head, auto_object_iterator new_end)
	{
		BOOST_ASSERT(new_end != m_auto_objects.begin());

		for (auto_object_iterator it = m_auto_objects.end(); it != new_end; --it)
		{
			auto_var_registration const & reg = *boost::prior(it);

			cfg::vertex_descriptor node = this->add_node(head, enode(cfg::nt_call)
				(eot_func, this->get_name(reg.destr))
				(reg.varptr));
			this->connect_to_exc(node, boost::prior(boost::prior(it))->excnode);
			this->connect_to_term(node);
		}
	}

	void init_auto_reg_node(auto_var_registration const & reg)
	{
		cfg::node & n = g[reg.excnode];
		n.type = cfg::nt_call;
		n.ops.push_back(cfg::operand(cfg::ot_func, this->get_name(reg.destr)));
		n.ops.push_back(this->make_rvalue(reg.varptr));

		this->connect_to_exc(reg.excnode, m_term_exit_node);
		this->connect_to_term(reg.excnode);
	}

	auto_object_iterator register_destructible_var(clang::CXXDestructorDecl const * destructor, eop const & varptr)
	{
		auto_var_registration reg = { destructor, varptr, add_vertex(g) };
		this->init_auto_reg_node(reg);

		add_edge(reg.excnode, m_auto_objects.back().excnode, g);
		m_auto_objects.push_back(reg);
		return boost::prior(m_auto_objects.end());
	}

	void unregister_destructible_var(cfg::vertex_descriptor & head, auto_object_iterator obj)
	{
		for (std::list<auto_var_registration>::iterator it = boost::prior(m_auto_objects.end()); it != obj; --it)
		{
			if (in_degree(it->excnode, g) == 0)
			{
				clear_vertex(it->excnode, g);
				remove_vertex(it->excnode, g);
			}

			it->excnode = add_vertex(g);
			this->init_auto_reg_node(*it);
		}

		auto_var_registration reg = *obj;
		if (in_degree(obj->excnode, g) == 0)
		{
			clear_vertex(obj->excnode, g);
			remove_vertex(obj->excnode, g);
		}
		obj = m_auto_objects.erase(obj);

		for (; obj != m_auto_objects.end(); ++obj)
		{
			if (obj == m_auto_objects.begin())
				add_edge(obj->excnode, m_exc_exit_node, g);
			else
				add_edge(obj->excnode, boost::prior(obj)->excnode, g);
		}

		this->register_decl_ref(reg.destr);

		cfg::vertex_descriptor call_node = this->add_node(head, enode(cfg::nt_call)(eot_func, this->get_name(reg.destr))(reg.varptr));
		this->connect_to_term(call_node);
		this->connect_to_exc(call_node);
	}

	void build_construct_expr(cfg::vertex_descriptor & head, eop const & varptr, clang::CXXConstructExpr const * e)
	{
		BOOST_ASSERT(varptr.type != eot_none);

		enode node(cfg::nt_call, e);
		this->register_decl_ref(e->getConstructor());
		node(eot_func, this->get_name(e->getConstructor()));

		clang::FunctionProtoType const * fntype = llvm::dyn_cast<clang::FunctionProtoType>(e->getConstructor()->getType().getTypePtr());
		node(varptr);

		for (std::size_t i = 0; i < e->getNumArgs(); ++i)
			node(this->make_param(head, this->build_expr(head, e->getArg(i)), fntype->getArgType(i).getTypePtr()));

		cfg::vertex_descriptor call_node = this->add_node(head, node);
		this->connect_to_term(call_node);
		this->connect_to_exc(call_node);
	}

	eop make_phi(cfg::vertex_descriptor & head, cfg::vertex_descriptor branch, eop headop, eop branchop, bool lvalue, clang::Expr const * data = 0)
	{
		if (lvalue)
		{
			headop = this->make_address(headop);
			branchop = this->make_address(branchop);
		}

		cfg::vertex_descriptor head_value_node = this->make_node(head, headop);
		cfg::vertex_descriptor branch_value_node = this->make_node(branch, branchop);
		this->join_nodes(branch, head);

		cfg::vertex_descriptor res_node = this->add_node(head, enode(cfg::nt_phi, data)
			(eot_node, head_value_node)
			(eot_node, branch_value_node));

		if (lvalue)
			return eop(eot_nodetgt, res_node);
		else
			return eop(eot_node, res_node);
	}

	eop build_expr(cfg::vertex_descriptor & head, clang::Expr const * expr)
	{
		BOOST_ASSERT(expr != 0);

		if (clang::BinaryOperator const * e = llvm::dyn_cast<clang::BinaryOperator>(expr))
		{
			eop const & lhs = this->build_expr(head, e->getLHS());

			// Treat assignment specially (takes a pointer to the assignee).
			if (e->isAssignmentOp() || e->isCompoundAssignmentOp())
			{
				eop const & rhs = this->build_expr(head, e->getRHS());
				this->add_node(head, enode(cfg::nt_call, expr)
					(eot_oper, clang::BinaryOperator::getOpcodeStr(e->getOpcode()))
					(this->make_address(lhs))
					(rhs));
				return lhs;
			}
			else if (e->getOpcode() == clang::BO_Comma)
			{
				return this->build_expr(head, e->getRHS());
			}
			else if (e->getOpcode() == clang::BO_LOr || e->getOpcode() == clang::BO_LAnd)
			{
				cfg::vertex_descriptor cond_node = this->make_node(head, lhs);
				cfg::vertex_descriptor cont_head = this->duplicate_vertex(head);

				if (e->getOpcode() == clang::BO_LAnd)
					g[*in_edges(head, g).first].cond = "0";
				else
					g[*in_edges(cont_head, g).first].cond = "0";

				eop const & rhs = this->build_expr(cont_head, e->getRHS());
				cfg::vertex_descriptor rhs_value_node = this->make_node(cont_head, rhs);
				this->join_nodes(cont_head, head);
				return eop(eot_node, this->add_node(head, enode(cfg::nt_phi, e)
					(eot_node, rhs_value_node)
					(eot_node, cond_node)));
			}
			else
			{
				eop const & rhs = this->build_expr(head, e->getRHS());
				return eop(eot_node, this->add_node(head, enode(cfg::nt_call, expr)
					(eot_oper, clang::BinaryOperator::getOpcodeStr(e->getOpcode()))
					(lhs)
					(rhs)));
			}
		}
		else if (clang::UnaryOperator const * e = llvm::dyn_cast<clang::UnaryOperator>(expr))
		{
			if (e->getOpcode() == clang::UO_AddrOf)
			{
				return this->make_address(this->build_expr(head, e->getSubExpr()));
			}
			else if (e->getOpcode() == clang::UO_Deref)
			{
				return this->make_deref(head, this->build_expr(head, e->getSubExpr()));
			}
			else if (e->getOpcode() == clang::UO_PreInc || e->getOpcode() == clang::UO_PreDec)
			{
				eop expr = this->build_expr(head, e->getSubExpr());
				cfg::vertex_descriptor node = this->add_node(head, enode(cfg::nt_call, e)
					(eot_oper, e->getOpcode() == clang::UO_PreInc? "+": "-")
					(expr)
					(eot_const, "1"));
				this->add_node(head, enode(cfg::nt_call, e)
					(eot_oper, "=")
					(this->make_address(expr))
					(eot_node, node));
				return expr;
			}
			else if (e->getOpcode() == clang::UO_PostInc || e->getOpcode() == clang::UO_PostDec)
			{
				eop expr = this->build_expr(head, e->getSubExpr());
				cfg::vertex_descriptor node = this->add_node(head, enode(cfg::nt_call, e)
					(eot_oper, e->getOpcode() == clang::UO_PostInc? "+": "-")
					(expr)
					(eot_const, "1"));
				this->add_node(head, enode(cfg::nt_call, e)
					(eot_oper, "=")
					(this->make_address(expr))
					(eot_node, node));
				return eop(eot_node, node);
			}
			else if (e->getOpcode() == clang::UO_Plus)
			{
				return this->build_expr(head, e->getSubExpr());
			}
			else
			{
				return eop(eot_node, this->add_node(head, enode(cfg::nt_call, e)
					(eot_oper, clang::UnaryOperator::getOpcodeStr(e->getOpcode()))
					(this->build_expr(head, e->getSubExpr()))));
			}
		}
		else if (clang::CXXBoolLiteralExpr const * e = llvm::dyn_cast<clang::CXXBoolLiteralExpr>(expr))
		{
			return eop(eot_const, e->getValue()? "1": "0");
		}
		else if (clang::IntegerLiteral const * e = llvm::dyn_cast<clang::IntegerLiteral>(expr))
		{
			return eop(eot_const, e->getValue().toString(10, true));
		}
		else if (clang::FloatingLiteral const * e = llvm::dyn_cast<clang::FloatingLiteral>(expr))
		{
			return eop(eot_const, boost::lexical_cast<std::string>(e->getValueAsApproximateDouble()));
		}
		else if (clang::CharacterLiteral const * e = llvm::dyn_cast<clang::CharacterLiteral>(expr))
		{
			return eop(eot_const, boost::lexical_cast<std::string>(e->getValue()));
		}
		else if (clang::StringLiteral const * e = llvm::dyn_cast<clang::StringLiteral>(expr))
		{
			std::vector<boost::int64_t> values = string_literal_to_value_array(e);
			std::string res = "[";
			for (std::size_t i = 0; i < values.size(); ++i)
			{
				res.append(boost::lexical_cast<std::string>(values[i]));
				if (i + 1 != values.size())
					res.append(", ");
			}
			res += "]";
			return eop(eot_const, res);
		}
		else if (clang::DeclRefExpr const * e = llvm::dyn_cast<clang::DeclRefExpr>(expr))
		{
			clang::Decl const * decl = e->getDecl();
			if (clang::ValueDecl const * nd = llvm::dyn_cast<clang::ValueDecl>(decl))
			{
				if (clang::EnumConstantDecl const * ecd = llvm::dyn_cast<clang::EnumConstantDecl>(nd))
					return eop(eot_const, ecd->getInitVal().toString(10));

				if (clang::FunctionDecl const * fd = llvm::dyn_cast<clang::FunctionDecl>(nd))
				{
					this->register_decl_ref(fd);
					return eop(eot_func, this->get_name(fd));
				}
				
				if (nd->getType()->isReferenceType())
					return eop(eot_vartgt, this->get_name(nd));

				return eop(eot_var, this->get_name(nd));
			}
			else
			{
				BOOST_ASSERT(0 && "encountered a declref to a non-value decl");
				return eop();
			}
		}
		else if (clang::CXXThisExpr const * e = llvm::dyn_cast<clang::CXXThisExpr>(expr))
		{
			return eop(eot_var, "p:this");
		}
		else if (clang::CallExpr const * e = llvm::dyn_cast<clang::CallExpr>(expr))
		{
			// Deal with pseudo-destructor calls.
			if (clang::CXXPseudoDestructorExpr const * de = llvm::dyn_cast<clang::CXXPseudoDestructorExpr>(e->getCallee()))
			{
				return this->build_expr(head, de->getBase());
			}

			// There are several possibilities.
			//  1. The call is a call to an overloaded operator.
			//  2. The expression type is clang::CXXMemberCallExpr. Then the callee is either
			//    a. clang::MemberExpr and the type of the function can be determined from the
			//       member declaration (remember there is an implicit `this` parameter), or
			//    b. clang::BinaryOperator, with either PtrMemD or PtrMemI; the type of
			//       the parameters can be extracted from the rhs operand.
			//  3. This is a normal invocation, in which case the type of the callee is a pointer to function,
			//     the types of parameters can be extracted from there.
			//
			// Additionally, there can be an implicit parameter representing the return value
			// (if the value is of a class type).

			eop callee_op;
			std::vector<eop> params;
			std::vector<clang::Type const *> param_types;
			clang::FunctionProtoType const * fntype;
			std::size_t arg_index = 0;
			if (llvm::isa<clang::CXXOperatorCallExpr>(e))
			{
				BOOST_ASSERT(e->getDirectCallee() != 0);
				if (clang::CXXMethodDecl const * md = llvm::dyn_cast<clang::CXXMethodDecl>(e->getDirectCallee()))
				{
					// C++03: 13.5/6: overloaded operators can't be static member functions
					eop this_op = this->build_expr(head, e->getArg(arg_index++));
					this_op = this->make_address(this_op);
					params.push_back(this_op);

					param_types.push_back(
						md->getThisType(md->getASTContext()).getTypePtr());

					this->register_decl_ref(md);
					callee_op = eop(eot_func, this->get_name(md));
					fntype = llvm::dyn_cast<clang::FunctionProtoType>(md->getType().getTypePtr());
				}
				else
				{
					callee_op = this->build_expr(head, e->getCallee());
					fntype = llvm::dyn_cast<clang::FunctionProtoType>(e->getCallee()->getType()->getPointeeType());
				}
			}
			else if (llvm::isa<clang::CXXMemberCallExpr>(e))
			{
				if (clang::MemberExpr const * mcallee = llvm::dyn_cast<clang::MemberExpr>(e->getCallee()))
				{
					clang::CXXMethodDecl const * mdecl = llvm::dyn_cast<clang::CXXMethodDecl>(mcallee->getMemberDecl());

					eop this_op = this->build_expr(head, mcallee->getBase());
					if (!mdecl->isStatic())
					{
						if (!mcallee->isArrow())
							this_op = this->make_address(this_op);
						params.push_back(this_op);

						param_types.push_back(
							mdecl->getThisType(mdecl->getASTContext()).getTypePtr());
					}
					else
					{
						this->make_node(head, this_op);
					}

					this->register_decl_ref(mdecl);

					std::string fnname = this->get_name(mdecl);
					if (mdecl->isVirtual() && !mcallee->hasQualifier())
						fnname = "v:" + fnname;

					callee_op = eop(eot_func, fnname);
					fntype = llvm::dyn_cast<clang::FunctionProtoType>(mdecl->getType().getTypePtr());
				}
				else if (clang::BinaryOperator const * callee = llvm::dyn_cast<clang::BinaryOperator>(e->getCallee()))
				{
					BOOST_ASSERT(callee->getOpcode() == clang::BO_PtrMemD || callee->getOpcode() == clang::BO_PtrMemI);

					callee_op = this->build_expr(head, callee->getRHS());
					clang::MemberPointerType const * calleePtrType = llvm::dyn_cast<clang::MemberPointerType>(callee->getRHS()->getType().getTypePtr());
					BOOST_ASSERT(calleePtrType);

					eop this_op = this->build_expr(head, callee->getLHS());
					if (callee->getOpcode() == clang::BO_PtrMemD)
						this_op = this->make_address(this_op);
					params.push_back(this_op);

					// FIXME: this should be a pointer to
					param_types.push_back(calleePtrType->getClass()->getCanonicalTypeInternal().getTypePtr());

					fntype = llvm::dyn_cast<clang::FunctionProtoType>(calleePtrType->getPointeeType().getTypePtr());
				}
				else
					BOOST_ASSERT(0);
			}
			else
			{
				callee_op = this->build_expr(head, e->getCallee());
				fntype = llvm::dyn_cast<clang::FunctionProtoType>(e->getCallee()->getType()->getCanonicalTypeInternal()->getPointeeType().getTypePtr());
			}

			BOOST_ASSERT(fntype);

			eop result_op;
			clang::Type const * restype = fntype->getResultType().getTypePtr();
			if (restype->isStructureOrClassType())
			{
				eop temp = this->make_temporary(restype);
				params.insert(params.begin(), temp);
				param_types.insert(param_types.begin(), restype);
				result_op = temp;
			}

			BOOST_ASSERT(fntype->isVariadic() || fntype->getNumArgs() + arg_index == e->getNumArgs());

			for (std::size_t i = 0; i < fntype->getNumArgs(); ++i)
			{
				params.push_back(this->build_expr(head, e->getArg(i + arg_index)));
				param_types.push_back(fntype->getArgType(i).getTypePtr());
			}

			for (std::size_t i = fntype->getNumArgs() + arg_index; i < e->getNumArgs(); ++i)
			{
				params.push_back(this->build_expr(head, e->getArg(i)));
				param_types.push_back(e->getArg(i)->getType().getTypePtr());
			}

			// TODO: handle classes with conversion to pointer to fn.
			enode node(cfg::nt_call, e);
			node(callee_op);
			for (std::size_t i = 0; i < params.size(); ++i)
				node(this->make_param(head, params[i], param_types[i]));

			cfg::vertex_descriptor call_node = this->add_node(head, node);
			this->connect_to_term(call_node);
			this->connect_to_exc(call_node);

			if (result_op.type != eot_none)
				return result_op;

			if (restype->isReferenceType())
				return eop(eot_nodetgt, call_node);
			else
				return eop(eot_node, call_node);
		}
		else if (clang::ConditionalOperator const * e = llvm::dyn_cast<clang::ConditionalOperator>(expr))
		{
			eop cond_op = this->build_expr(head, e->getCond());
			cfg::vertex_descriptor branch_node = this->make_node(head, cond_op);
			cfg::vertex_descriptor false_head = this->duplicate_vertex(head);
			g[*in_edges(false_head, g).first].cond = "0";

			eop true_res = this->build_expr(head, e->getTrueExpr());
			eop false_res = this->build_expr(false_head, e->getFalseExpr());
			if (true_res.type != eot_none && false_res.type != eot_none)
			{
				return this->make_phi(head, false_head, true_res, false_res, e->isLvalue(m_fn->getASTContext()) == clang::Expr::LV_Valid, e);
			}
			else
			{
				this->join_nodes(false_head, head);
				if (true_res.type == eot_none && false_res.type == eot_none)
					return eop();

				if (true_res.type != eot_none)
					return true_res;
				else
					return false_res;
			}
		}
		else if (clang::SizeOfAlignOfExpr const * e = llvm::dyn_cast<clang::SizeOfAlignOfExpr>(expr))
		{
			// TODO: is there a better way?
			BOOST_ASSERT(e->isSizeOf());
			if (e->isArgumentType())
				return eop(eot_const, "sizeof:" + e->getArgumentType().getAsString());
			else
				return eop(eot_const, "sizeof:" + e->getArgumentExpr()->getType().getAsString());
		}
		else if (clang::MemberExpr const * e = llvm::dyn_cast<clang::MemberExpr>(expr))
		{
			// TODO: lvalue/rvalue
			eop base = this->build_expr(head, e->getBase());
			if (!e->isArrow())
				base = this->make_address(base);
			return eop(eot_nodetgt, this->add_node(head, enode(cfg::nt_call, e)
				(eot_member, this->get_name(e->getMemberDecl()))
				(base)));
		}
		else if (clang::ArraySubscriptExpr const * e = llvm::dyn_cast<clang::ArraySubscriptExpr>(expr))
		{
			return this->get_array_element(head, this->build_expr(head, e->getLHS()), this->build_expr(head, e->getRHS()), e);
		}
		else if (clang::ParenExpr const * e = llvm::dyn_cast<clang::ParenExpr>(expr))
		{
			return this->build_expr(head, e->getSubExpr());
		}
		else if (clang::CXXDefaultArgExpr const * e = llvm::dyn_cast<clang::CXXDefaultArgExpr>(expr))
		{
			return this->build_expr(head, e->getExpr());
		}
		else if (clang::CXXConstructExpr const * e = llvm::dyn_cast<clang::CXXConstructExpr>(expr))
		{
			eop temp = this->make_temporary(e->getType().getTypePtr());
			this->build_construct_expr(head, this->make_address(temp), e);
			return temp;
		}
		else if (clang::CXXExprWithTemporaries const * e = llvm::dyn_cast<clang::CXXExprWithTemporaries>(expr))
		{
			return this->build_expr(head, e->getSubExpr());
		}
		else if (clang::CXXBindTemporaryExpr const * e = llvm::dyn_cast<clang::CXXBindTemporaryExpr>(expr))
		{
			// TODO: deal with extended lifetime of temporaries bound to a reference.
			eop res = this->build_expr(head, e->getSubExpr());
			auto_object_iterator reg = this->register_destructible_var(e->getTemporary()->getDestructor(), this->make_address(res));
			m_fullexpr_lifetimes.back().push_back(reg);
			return res;
		}
		else if (clang::CastExpr const * e = llvm::dyn_cast<clang::CastExpr>(expr))
		{
			// TODO: deal with the casts correctly (notably with dynamic_cast)
			switch (e->getCastKind())
			{
			case clang::CK_ArrayToPointerDecay:
				return this->decay_array_to_pointer(head, this->build_expr(head, e->getSubExpr()));
			default:
				return this->build_expr(head, e->getSubExpr());
			}
		}
		else if (clang::CXXPseudoDestructorExpr const * e = llvm::dyn_cast<clang::CXXPseudoDestructorExpr>(expr))
		{
			return this->build_expr(head, e->getBase());
		}
		else if (clang::CXXNewExpr const * e = llvm::dyn_cast<clang::CXXNewExpr>(expr))
		{
			// TODO: relieve the interpreter of the privilege of having to recognize cxx:new[]
			if (e->isArray())
			{
				enode node(cfg::nt_call, e);
				node(eot_oper, "cxx:new[]");
				node(eot_func, this->get_name(e->getOperatorNew()));
				if (e->getConstructor())
					node(eot_func, this->get_name(e->getConstructor()));
				else
					node(eot_const, "0");
				node(eot_func, this->get_name(e->getOperatorDelete()));
				node(eot_const, e->hasInitializer()? "1": "0");
				this->append_args(
					head,
					node,
					e->getOperatorNew()->param_begin() + 1, e->getOperatorNew()->param_end(),
					e->placement_arg_begin(), e->placement_arg_end());

				return eop(eot_node, this->add_node(head, node));
			}
			else
			{
				// TODO: exception safety

				enode opnew_node(cfg::nt_call, e);
				opnew_node(eot_func, this->get_name(e->getOperatorNew()));
				opnew_node(eot_const, "sizeof:" + e->getAllocatedType().getAsString());
				this->append_args(
					head,
					opnew_node,
					e->getOperatorNew()->param_begin() + 1, e->getOperatorNew()->param_end(),
					e->placement_arg_begin(), e->placement_arg_end());

				eop ptr_op = eop(eot_node, this->add_node(head, opnew_node));
				if (e->getConstructor() != 0)
				{
					enode construct_node(cfg::nt_call, e);
					construct_node(eot_func, this->get_name(e->getConstructor()));
					construct_node(ptr_op);
					this->append_args(head, construct_node, e->getConstructor()->param_begin(), e->getConstructor()->param_end(),
						e->constructor_arg_begin(), e->constructor_arg_end());
					this->add_node(head, construct_node);
				}

				return ptr_op;
			}
		}
		else if (clang::CXXDeleteExpr const * e = llvm::dyn_cast<clang::CXXDeleteExpr>(expr))
		{
			std::string name = e->isArrayForm()? "cxx:delete[]:": "cxx:delete:";

			// TODO: Append a correct type of argument
			//BOOST_ASSERT(llvm::isa<clang::PointerType>(e->getArgument()->getType()));
			//name += e->getArgument()->getType().getAsString();//->getPointeeType()->getCanonicalTypeInternal().getAsString();

			enode node(cfg::nt_call, e);
			node(eot_oper, name);
			node(this->build_expr(head, e->getArgument()));
			node(eot_func, this->get_name(e->getOperatorDelete()));
			return eop(eot_node, this->add_node(head, node));
		}
		else if (clang::CXXThrowExpr const * e = llvm::dyn_cast<clang::CXXThrowExpr>(expr))
		{
			eop exc_mem = eop(eot_node, this->add_node(head, enode(cfg::nt_call)
				(eot_oper, "magic_alloc")
				(eot_const, "sizeof:" + e->getSubExpr()->getType().getAsString())));

			this->init_object(head, exc_mem, e->getType(), e->getSubExpr(), false);
			// TODO: handle exceptions from initialization

			this->join_nodes(head, m_auto_objects.back().excnode);
			head = add_vertex(g);
			return eop();
		}
		else
		{
			BOOST_ASSERT(0 && "unknown AST node encountered");
			return eop();
		}
	}

	eop get_array_element(cfg::vertex_descriptor & head, eop const & decayedptr, eop const & index, clang::Stmt const * data)
	{
		if (index.type == eot_const && boost::get<std::string>(index.id) == "0")
			return this->make_deref(head, decayedptr);

		return eop(eot_nodetgt, this->add_node(head, enode(cfg::nt_call, data)
			(eot_oper, "+")
			(decayedptr)
			(index)));
	}

	eop build_full_expr(cfg::vertex_descriptor & head, clang::Expr const * expr)
	{
		this->begin_lifetime_context(m_fullexpr_lifetimes);
		eop res = this->build_expr(head, expr);
		this->end_lifetime_context(head, m_fullexpr_lifetimes);
		return res;
	}

	eop decay_array_to_pointer(cfg::vertex_descriptor & head, eop const & arr)
	{
		// WISH: When we do cfg typing, fix this to deal with string literals correctly.
		if (arr.type == eot_const)
			return arr;

		// The array must be an lvalue, otherwise we cannot get a pointer to the first element.
		BOOST_ASSERT(this->is_lvalue(arr));
		return eop(eot_node, this->add_node(head, enode(cfg::nt_call)
			(eot_oper, "decay")
			(this->make_address(arr))));
	}

	void zero_initialize(cfg::vertex_descriptor & head, eop const & varptr, clang::QualType vartype, bool blockLifetime, clang::Stmt const * data)
	{
		BOOST_ASSERT(vartype->isScalarType());
		this->add_node(head, enode(cfg::nt_call, data)
			(eot_oper, "=")
			(varptr)
			(eot_const, "0"));
	}

	void value_initialize(cfg::vertex_descriptor & head, eop const & varptr, clang::QualType vartype, bool blockLifetime, clang::Stmt const * data = 0)
	{
		BOOST_ASSERT(!vartype->isStructureOrClassType() && "calls to constructors should be injected to AST by sema");

		if (clang::ArrayType const * at = llvm::dyn_cast<clang::ArrayType>(vartype))
		{
			BOOST_ASSERT(llvm::isa<clang::ConstantArrayType>(at));
			llvm::APInt const & size = llvm::dyn_cast<clang::ConstantArrayType>(at)->getSize();

			eop decayedptr = this->decay_array_to_pointer(head, this->make_deref(head, varptr));
			for (llvm::APInt i(size.getBitWidth(), 0); i != size; ++i)
				this->value_initialize(head, this->get_array_element(head, decayedptr, eop(eot_const, i.toString(10, false)), data), at->getElementType(), blockLifetime, data);
		}
		else
		{
			this->zero_initialize(head, varptr, vartype, blockLifetime, data);
		}
	}

	void init_object(cfg::vertex_descriptor & head, eop const & varptr, clang::QualType vartype, clang::Expr const * e, bool blockLifetime)
	{
		BOOST_ASSERT(e != 0);
		BOOST_ASSERT(!m_fullexpr_lifetimes.empty());

		if (clang::CXXExprWithTemporaries const * te = llvm::dyn_cast<clang::CXXExprWithTemporaries>(e))
		{
			this->init_object(head, varptr, vartype, te->getSubExpr(), blockLifetime);
			return;
		}

		vartype = vartype->getCanonicalTypeUnqualified();

		// TODO: check fullexpr lifetimes
		if (vartype->isStructureOrClassType())
		{
			if (clang::CXXConstructExpr const * ce = llvm::dyn_cast<clang::CXXConstructExpr>(e))
			{
				this->build_construct_expr(head, varptr, llvm::dyn_cast<clang::CXXConstructExpr>(e));
				if (blockLifetime)
				{
					clang::CXXRecordDecl const * recordDecl = vartype->getAsCXXRecordDecl();
					if (recordDecl && recordDecl->hasDeclaredDestructor())
					{
						m_block_lifetimes.back().push_back(this->register_destructible_var(recordDecl->getDestructor(), varptr));
					}
				}
			}
			else if (clang::InitListExpr const * ie = llvm::dyn_cast<clang::InitListExpr>(e))
			{
				clang::CXXRecordDecl const * recordDecl = vartype->getAsCXXRecordDecl();

				BOOST_ASSERT(recordDecl->getNumBases() == 0);
				unsigned init_idx = 0;
				for (clang::CXXRecordDecl::field_iterator it = recordDecl->field_begin(); it != recordDecl->field_end(); ++it, ++init_idx)
				{
					clang::FieldDecl const * field = *it;

					eop fieldptrop = eop(eot_node, this->add_node(head, enode(cfg::nt_call)
						(eot_member, this->get_name(field))
						(varptr)));

					// TODO: exceptions?
					if (init_idx < ie->getNumInits())
						this->init_object(head, fieldptrop, field->getType(), ie->getInit(init_idx), false);
					else
						this->value_initialize(head, fieldptrop, field->getType(), blockLifetime);
				}
			}
			else
			{
				BOOST_ASSERT(0 && "unknown type of initialization encountered");
			}
		}
		else if (vartype->isReferenceType())
		{
			// TODO: extend the lifetime of temporaries
			this->add_node(head, enode(cfg::nt_call, e)
				(eot_oper, "=")
				(varptr)
				(this->make_address(this->build_expr(head, e))));
		}
		else if (vartype->isArrayType())
		{
			BOOST_ASSERT(llvm::isa<clang::ConstantArrayType>(vartype));
			clang::ConstantArrayType const * at = llvm::cast<clang::ConstantArrayType>(vartype);
			eop decayedptr = this->decay_array_to_pointer(head, this->make_deref(head, varptr));

			llvm::APInt i(at->getSize().getBitWidth(), 0);
			if (clang::InitListExpr const * ile = llvm::dyn_cast<clang::InitListExpr>(e))
			{
				// TODO: exception safety
				unsigned init_idx = 0;
				for (; i != at->getSize() && init_idx != ile->getNumInits(); ++i, ++init_idx)
				{
					eop elem = this->get_array_element(head, decayedptr, eop(eot_const, i.toString(10, false)), e);
					this->init_object(head, this->make_address(elem), at->getElementType(), ile->getInit(init_idx), blockLifetime);
				}
			}
			else if (clang::StringLiteral const * sl = llvm::dyn_cast<clang::StringLiteral>(e))
			{
				std::vector<int64_t> values = string_literal_to_value_array(sl);

				std::size_t init_idx = 0;
				for (; i != at->getSize() && init_idx < values.size(); ++i, ++init_idx)
				{
					eop elem = this->get_array_element(head, decayedptr, eop(eot_const, i.toString(10, false)), e);
					this->add_node(head, enode(cfg::nt_call, e)
						(eot_oper, "=")
						(this->make_address(elem))
						(eot_const, boost::lexical_cast<std::string>(values[init_idx])));
				}
			}
			else if (clang::ArraySubscriptExpr const * ie = llvm::dyn_cast<clang::ArraySubscriptExpr>(e))
			{
				// TODO
				i = at->getSize();
			}
			else if (clang::CXXConstructExpr const * ie = llvm::dyn_cast<clang::CXXConstructExpr>(e))
			{
				// TODO
				i = at->getSize();
			}
			else
			{
				BOOST_ASSERT(0 && "unrecognized array initializer");
			}

			if (i != at->getSize())
			{
				eop loop_counter = this->make_temporary(m_fn->getASTContext().getSizeType()->getTypePtr());
				this->add_node(head, enode(cfg::nt_call, e)
					(eot_oper, "=")
					(this->make_address(loop_counter))
					(eot_const, i.toString(10, false)));
				cfg::vertex_descriptor cond_node = this->add_node(head, enode(cfg::nt_call, e)
					(eot_oper, "<")
					(loop_counter)
					(eot_const, at->getSize().toString(10, false)));

				cfg::vertex_descriptor false_head = this->duplicate_vertex(head);
				this->set_cond(false_head, 0, "0");

				eop elem = this->get_array_element(head, decayedptr, loop_counter, e);
				this->value_initialize(head, this->make_address(elem), at->getElementType(), blockLifetime, e);

				cfg::vertex_descriptor incremented = this->add_node(head, enode(cfg::nt_call, e)
					(eot_oper, "+")
					(loop_counter)
					(eot_const, "1"));
				this->add_node(head, enode(cfg::nt_call, e)
					(eot_oper, "=")
					(this->make_address(loop_counter))
					(eot_node, incremented));

				this->join_nodes(head, cond_node);
				head = false_head;
			}
		}
		else
		{
			if (clang::InitListExpr const * ie = llvm::dyn_cast<clang::InitListExpr>(e))
			{
				BOOST_ASSERT(ie->getNumInits() == 1);
				this->init_object(head, varptr, vartype, ie->getInit(0),blockLifetime);
			}
			else if (clang::ImplicitValueInitExpr const * ie = llvm::dyn_cast<clang::ImplicitValueInitExpr>(e))
			{
				this->value_initialize(head, varptr, vartype, blockLifetime);
			}
			else
			{
				this->add_node(head, enode(cfg::nt_call, e)
					(eot_oper, "=")
					(varptr)
					(this->build_expr(head, e)));
			}
		}
	}

	void build_stmt(cfg::vertex_descriptor & head, clang::Stmt const * stmt)
	{
		BOOST_ASSERT(stmt != 0);

		m_visitor.statement_visited(stmt);

		if (clang::CompoundStmt const * s = llvm::dyn_cast<clang::CompoundStmt>(stmt))
		{
			this->begin_lifetime_context(m_block_lifetimes);
			for (clang::CompoundStmt::const_body_iterator it = s->body_begin(); it != s->body_end(); ++it)
				this->build_stmt(head, *it);
			this->end_lifetime_context(head, m_block_lifetimes);
		}
		else if (clang::Expr const * s = llvm::dyn_cast<clang::Expr>(stmt))
		{
			this->build_full_expr(head, s);
		}
		else if (clang::ReturnStmt const * s = llvm::dyn_cast<clang::ReturnStmt>(stmt))
		{
			cfg::operand val;
			if (s->getRetValue() != 0)
			{
				if (m_fn->getResultType()->isStructureOrClassType())
				{
					this->begin_lifetime_context(m_fullexpr_lifetimes);
					this->init_object(head, eop(eot_var, "p:return"), m_fn->getResultType(), s->getRetValue(), false);
					this->end_lifetime_context(head, m_fullexpr_lifetimes);
				}
				else
				{
					eop op = this->build_full_expr(head, s->getRetValue());
					if (m_fn->getResultType()->isReferenceType())
						op = this->make_address(op);
					val = this->make_rvalue(head, op);
				}
			}

			this->generate_destructor_chain(head, boost::next(m_auto_objects.begin()));

			g[head].type = cfg::nt_exit;
			g[head].ops.push_back(cfg::operand(cfg::ot_const, "0"));
			if (val.type != eot_none)
				g[head].ops.push_back(val);
			m_exit_nodes.insert(head);

			head = add_vertex(g);
		}
		else if (clang::BreakStmt const * s = llvm::dyn_cast<clang::BreakStmt>(stmt))
		{
			BOOST_ASSERT(!m_break_sentinels.empty());
			this->generate_destructor_chain(head, boost::next(m_break_sentinels.back().second));
			this->join_nodes(head, m_break_sentinels.back().first);
			head = add_vertex(g);
		}
		else if (clang::ContinueStmt const * s = llvm::dyn_cast<clang::ContinueStmt>(stmt))
		{
			BOOST_ASSERT(!m_continue_sentinels.empty());
			this->generate_destructor_chain(head, boost::next(m_continue_sentinels.back().second));
			this->join_nodes(head, m_continue_sentinels.back().first);
			head = add_vertex(g);
		}
		else if (clang::IfStmt const * s = llvm::dyn_cast<clang::IfStmt>(stmt))
		{
			this->make_node(head, this->build_full_expr(head, s->getCond()));
			cfg::vertex_descriptor else_head = this->duplicate_vertex(head);

			this->set_cond(else_head, 0, "0");

			this->build_stmt(head, s->getThen());
			if (s->getElse() != 0)
				this->build_stmt(else_head, s->getElse());
			this->join_nodes(else_head, head);
		}
		else if (clang::WhileStmt const * s = llvm::dyn_cast<clang::WhileStmt>(stmt))
		{
			cfg::vertex_descriptor cond_start = head;
			this->make_cond_node(head, this->build_full_expr(head, s->getCond()));
			cfg::vertex_descriptor body_head = this->duplicate_vertex(head);
			this->set_cond(head, 0, "0");

			m_break_sentinels.push_back(this->create_jump_sentinel());
			m_continue_sentinels.push_back(this->create_jump_sentinel());

			this->build_stmt(body_head, s->getBody());
			this->join_nodes(body_head, cond_start);

			this->join_jump_sentinel(m_break_sentinels.back(), head);
			this->join_jump_sentinel(m_continue_sentinels.back(), cond_start);
			m_break_sentinels.pop_back();
			m_continue_sentinels.pop_back();
		}
		else if (clang::DoStmt const * s = llvm::dyn_cast<clang::DoStmt>(stmt))
		{
			cfg::vertex_descriptor start_node = head;

			m_break_sentinels.push_back(this->create_jump_sentinel());
			m_continue_sentinels.push_back(this->create_jump_sentinel());

			this->build_stmt(head, s->getBody());

			cfg::vertex_descriptor cond_node = this->make_cond_node(head, this->build_full_expr(head, s->getCond()));
			cfg::vertex_descriptor loop_node = this->duplicate_vertex(head);
			this->set_cond(head, 0, "0");

			this->join_nodes(loop_node, start_node);

			this->join_jump_sentinel(m_break_sentinels.back(), head);
			this->join_jump_sentinel(m_continue_sentinels.back(), cond_node);
			m_break_sentinels.pop_back();
			m_continue_sentinels.pop_back();
		}
		else if (clang::ForStmt const * s = llvm::dyn_cast<clang::ForStmt>(stmt))
		{
			if (s->getInit())
				this->build_stmt(head, s->getInit());

			cfg::vertex_descriptor start_node = head;
			cfg::vertex_descriptor cond_node = head;
			cfg::vertex_descriptor exit_node;
			if (s->getCond())
			{
				cond_node = this->make_cond_node(head, this->build_full_expr(head, s->getCond()));
				exit_node = this->duplicate_vertex(head);
				this->set_cond(exit_node, 0, "0");
			}
			else
				exit_node = add_vertex(g);

			m_break_sentinels.push_back(this->create_jump_sentinel());
			m_continue_sentinels.push_back(this->create_jump_sentinel());

			cfg::vertex_descriptor body_node = head;
			this->build_stmt(head, s->getBody());
			this->join_jump_sentinel(m_continue_sentinels.back(), head);
			m_continue_sentinels.pop_back();

			if (s->getInc())
				this->build_full_expr(head, s->getInc());
			this->join_nodes(head, start_node);
			head = exit_node;

			this->join_jump_sentinel(m_break_sentinels.back(), exit_node);
			m_break_sentinels.pop_back();
		}
		else if (clang::DefaultStmt const * s = llvm::dyn_cast<clang::DefaultStmt>(stmt))
		{
			BOOST_ASSERT(!m_case_contexts.empty());
			BOOST_ASSERT(m_case_contexts.back().first == cfg::null_vertex());
			m_case_contexts.back().first = head;
			this->build_stmt(head, s->getSubStmt());
		}
		else if (clang::CaseStmt const * s = llvm::dyn_cast<clang::CaseStmt>(stmt))
		{
			BOOST_ASSERT(!m_case_contexts.empty());
			BOOST_ASSERT(s->getRHS() == 0 && "case lhs..rhs; gcc extension is not supported");

			eop cond = eop(eot_const, s->getLHS()->EvaluateAsInt(m_fn->getASTContext()).toString(10));

			m_case_contexts.back().second[boost::get<std::string>(cond.id)] = head;
			this->build_stmt(head, s->getSubStmt());
		}
		else if (clang::SwitchStmt const * s = llvm::dyn_cast<clang::SwitchStmt>(stmt))
		{
			cfg::vertex_descriptor cond_node = this->make_node(head, this->build_full_expr(head, s->getCond()));
			cfg::vertex_descriptor cond_cont = head;
			cfg::vertex_descriptor body_start = add_vertex(g);
			head = body_start;

			m_case_contexts.push_back(case_context_t());
			m_break_sentinels.push_back(this->create_jump_sentinel());
			this->build_stmt(head, s->getBody());

			this->join_jump_sentinel(m_break_sentinels.back(), head);
			m_break_sentinels.pop_back();

			case_context_t const & case_ctx = m_case_contexts.back();
			if (case_ctx.first != cfg::null_vertex())
				this->join_nodes(cond_cont, case_ctx.first);
			else
				this->join_nodes(cond_cont, body_start);

			for (case_context_t::second_type::const_iterator it = case_ctx.second.begin(); it != case_ctx.second.end(); ++it)
			{
				cfg::edge_descriptor e = add_edge(cond_node, it->second, g).first;
				g[e].cond = it->first;
			}

			m_case_contexts.pop_back();
		}
		else if (clang::DeclStmt const * s = llvm::dyn_cast<clang::DeclStmt>(stmt))
		{
			for (clang::DeclStmt::const_decl_iterator ci = s->decl_begin(); ci != s->decl_end(); ++ci)
			{
				clang::Decl const * decl = *ci;
				if (clang::VarDecl const * vd = llvm::dyn_cast<clang::VarDecl>(decl))
				{
					// TODO: deal with static variables correctly
					if (vd->isStaticLocal())
						continue;

					if (vd->hasInit())
					{
						this->begin_lifetime_context(m_fullexpr_lifetimes);
						this->init_object(head, eop(eot_varptr, this->get_name(vd)), vd->getType(), vd->getInit(), true);
						this->end_lifetime_context(head, m_fullexpr_lifetimes);
					}
				}
			}
		}
		else if (clang::NullStmt const * s = llvm::dyn_cast<clang::NullStmt>(stmt))
		{
			// Leave a comment about the null statement.
			this->add_node(head, enode(cfg::nt_none)(eot_const, "null"));
		}
		else if (clang::CXXTryStmt const * s = llvm::dyn_cast<clang::CXXTryStmt>(stmt))
		{
			// TODO
			this->build_stmt(head, s->getTryBlock());
		}
		else if (clang::LabelStmt const * s = llvm::dyn_cast<clang::LabelStmt>(stmt))
		{
			m_labels[s] = head;
			this->build_stmt(head, s->getSubStmt());
		}
		else if (clang::GotoStmt const * s = llvm::dyn_cast<clang::GotoStmt>(stmt))
		{
			m_gotos[s] = head;
			head = add_vertex(g);
		}
		else if (clang::AsmStmt const * s = llvm::dyn_cast<clang::AsmStmt>(stmt))
		{
			// Leave a comment about the asm statement but ignore its contents.
			this->add_node(head, enode(cfg::nt_none)(eot_const, "asm")(eot_const, std::string(s->getAsmString()->getString())));
		}
		else
		{
			// IndirectGotoStmt
			// ObjC statements
			BOOST_ASSERT(0 && "unknown AST node encountered");
		}
	}

	void backpatch_gotos()
	{
		for (std::map<clang::GotoStmt const *, cfg::vertex_descriptor>::const_iterator it = m_gotos.begin(); it != m_gotos.end(); ++it)
		{
			BOOST_ASSERT(it->first != 0);
			std::map<clang::LabelStmt const *, cfg::vertex_descriptor>::const_iterator label_it = m_labels.find(it->first->getLabel());
			BOOST_ASSERT(label_it != m_labels.end());
			this->join_nodes(it->second, label_it->second);
		}
	}

	void finish()
	{
		this->backpatch_gotos();

		if (in_degree(m_exc_exit_node, g) == 0)
			remove_vertex(m_exc_exit_node, g);

		if (in_degree(m_term_exit_node, g) == 0)
			remove_vertex(m_term_exit_node, g);

		g[m_head].type = cfg::nt_exit;
		g[m_head].ops.push_back(cfg::operand(cfg::ot_const, "0"));
		m_exit_nodes.insert(m_head);

		for (std::size_t exit_index = 0; exit_index != 1; ++exit_index)
		{
			std::size_t value_node_count = 0;

			std::vector<cfg::vertex_descriptor> exit_nodes;
			for (std::set<cfg::vertex_descriptor>::const_iterator it = m_exit_nodes.begin(); it != m_exit_nodes.end(); ++it)
			{
				BOOST_ASSERT(g[*it].type == cfg::nt_exit);
				BOOST_ASSERT(g[*it].ops.size() == 1 || g[*it].ops.size() == 2);
				BOOST_ASSERT(g[*it].ops[0].type == cfg::ot_const);

				std::size_t idx = boost::lexical_cast<std::size_t>(boost::get<std::string>(g[*it].ops[0].id));
				if (idx == exit_index)
				{
					exit_nodes.push_back(*it);
					if (g[*it].ops.size() == 2)
						++value_node_count;
				}
			}

			if (value_node_count > 0)
			{
				for (std::size_t i = exit_nodes.size(); i != 0; --i)
				{
					if (g[exit_nodes[i-1]].ops.size() == 1)
					{
						using std::swap;
						swap(exit_nodes[i-1], exit_nodes.back());
						m_exit_nodes.erase(exit_nodes.back());
						this->join_nodes(exit_nodes.back(), m_term_exit_node);
						exit_nodes.pop_back();
					}
				}
			}

			if (exit_nodes.size() < 2)
				continue;

			if (value_node_count < 2)
			{
				for (std::size_t i = 1; i != exit_nodes.size(); ++i)
				{
					g.redirect_vertex(exit_nodes[i], exit_nodes[0]);
					m_exit_nodes.erase(exit_nodes[1]);
					remove_vertex(exit_nodes[i], g);
				}
			}
			else
			{
				cfg::vertex_descriptor phi_node = add_vertex(g);
				g[phi_node].type = cfg::nt_phi;

				cfg::vertex_descriptor new_exit_node = add_vertex(g);
				g[new_exit_node].type = cfg::nt_exit;
				g[new_exit_node].ops.push_back(cfg::operand(cfg::ot_const, boost::lexical_cast<std::string>(exit_index)));
				g[new_exit_node].ops.push_back(cfg::operand(cfg::ot_node, phi_node));

				add_edge(phi_node, new_exit_node, g);

				for (std::size_t i = 0; i != exit_nodes.size(); ++i)
				{
					m_exit_nodes.erase(exit_nodes[i]);

					if (g[exit_nodes[i]].ops.size() == 2)
					{
						if (g[exit_nodes[i]].ops[1].type == cfg::ot_node)
						{
							g[phi_node].ops.push_back(g[exit_nodes[i]].ops[1]);
							g.redirect_vertex(exit_nodes[i], phi_node);
							remove_vertex(exit_nodes[i], g);
						}
						else
						{
							g[exit_nodes[i]].type = cfg::nt_value;
							g[exit_nodes[i]].ops.erase(g[exit_nodes[i]].ops.begin());
							add_edge(exit_nodes[i], phi_node, g);
							g[phi_node].ops.push_back(cfg::operand(cfg::ot_node, exit_nodes[i]));
						}
					}
					else
					{
						BOOST_ASSERT(in_degree(exit_nodes[i], g) == 0);
						remove_vertex(exit_nodes[i], g);
					}
				}
			}
		}

		this->simplify();
	}

	void simplify()
	{
		std::vector<cfg::vertex_descriptor> unique_vertices;
		std::pair<cfg::vertex_iterator, cfg::vertex_iterator> vertex_range = vertices(g);
		std::copy(vertex_range.first, vertex_range.second, std::back_inserter(unique_vertices));

		std::map<cfg::vertex_descriptor, cfg::vertex_descriptor> merges;
		
		for (std::size_t i = 0; i != unique_vertices.size(); ++i)
		{
			merges[unique_vertices[i]] = unique_vertices[i];
			for (std::size_t j = i + 1; j != unique_vertices.size(); ++j)
			{
				cfg::vertex_descriptor u = unique_vertices[i];
				cfg::vertex_descriptor v = unique_vertices[j];

				if (g[u].type != g[v].type || g[u].ops.size() != g[v].ops.size() || g[u].tags != g[v].tags || out_degree(u, g) != out_degree(v, g))
					continue;

				if (!std::equal(g[u].ops.begin(), g[u].ops.end(), g[v].ops.begin()))
					continue;

				std::pair<cfg::out_edge_iterator, cfg::out_edge_iterator> out_edges_range = out_edges(u, g);
				std::vector<std::pair<cfg::edge, cfg::vertex_descriptor> > u_out_edges;
				for (; out_edges_range.first != out_edges_range.second; ++out_edges_range.first)
					u_out_edges.push_back(std::make_pair(g[*out_edges_range.first], target(*out_edges_range.first, g)));

				bool ok = true;
				out_edges_range = out_edges(v, g);
				for (; ok && out_edges_range.first != out_edges_range.second; ++out_edges_range.first)
				{
					if (std::find(u_out_edges.begin(), u_out_edges.end(), std::make_pair(g[*out_edges_range.first], target(*out_edges_range.first, g))) == u_out_edges.end())
						ok = false;
				}

				if (!ok)
					continue;

				merges[unique_vertices[j]] = unique_vertices[i];

				using std::swap;
				swap(unique_vertices[j], unique_vertices.back());
				unique_vertices.pop_back();
				--j;
			}
		}

		for (std::size_t i = 0; i != unique_vertices.size(); ++i)
		{
			cfg::node & node = g[unique_vertices[i]];
			for (std::size_t j = 0; j != node.ops.size(); ++j)
			{
				if (node.ops[j].type == cfg::ot_node)
					node.ops[j].id = merges[boost::get<cfg::vertex_descriptor>(node.ops[j].id)];
			}
		}

		for (std::map<cfg::vertex_descriptor, cfg::vertex_descriptor>::const_iterator it = merges.begin(); it != merges.end(); ++it)
		{
			if (it->first == it->second)
				continue;

			std::pair<cfg::in_edge_iterator, cfg::in_edge_iterator> in_edges_range = in_edges(it->first, g);
			for (; in_edges_range.first != in_edges_range.second; ++in_edges_range.first)
			{
				cfg::edge_descriptor e = add_edge(source(*in_edges_range.first, g), it->second, g).first;
				g[e] = g[*in_edges_range.first];
			}

			if (g.entry() == it->first)
				g.entry(it->second);

			clear_vertex(it->first, g);
			remove_vertex(it->first, g);
		}
	}

	void build_constructor(clang::CXXConstructorDecl const * fn)
	{
		// TODO: handle virtual bases properly

		m_block_lifetimes.push_back(lifetime_context_t());
		for (clang::CXXConstructorDecl::init_const_iterator it = fn->init_begin(); it != fn->init_end(); ++it)
		{
			clang::CXXBaseOrMemberInitializer const * init = *it;
			if (init->isMemberInitializer())
			{
				BOOST_ASSERT(!init->isBaseInitializer());
				cfg::vertex_descriptor memberop = this->add_node(m_head, enode(cfg::nt_call)
					(eot_member, this->get_name(init->getMember()))
					(eot_var, "p:this"));

				this->begin_lifetime_context(m_fullexpr_lifetimes);
				this->init_object(m_head, eop(eot_node, memberop), init->getMember()->getType(), init->getInit(), false);
				this->end_lifetime_context(m_head, m_fullexpr_lifetimes);

				// TODO: figure out how to do exception safety here
			}
			else
			{
				// TODO: Convert p:this
				BOOST_ASSERT(init->isBaseInitializer());

				this->begin_lifetime_context(m_fullexpr_lifetimes);
				this->init_object(m_head, eop(eot_var, "p:this"), clang::QualType(init->getBaseClass(), 0), init->getInit(), false);
				this->end_lifetime_context(m_head, m_fullexpr_lifetimes);

				// TODO: figure out how to do exception safety here
			}
		}
		
		// The lifetime gets extended and is guarded by the destructor of this class.
		// TODO: Kill the object in the case of an exception.
		m_block_lifetimes.pop_back();
	}

	void build_destructor(clang::CXXDestructorDecl const * fn)
	{
		clang::CXXRecordDecl const * rec = fn->getParent();
		// TODO: handle virtual bases

		std::vector<clang::FieldDecl const *> fields;
		for (clang::RecordDecl::field_iterator field_it = rec->field_end(); field_it != rec->field_end(); ++field_it)
			fields.push_back(*field_it);

		for (std::size_t i = fields.size(); i != 0; --i)
		{
			clang::FieldDecl const * fd = fields[i-1];
			if (clang::CXXRecordDecl const * rd = fd->getType()->getAsCXXRecordDecl())
			{
				if (rd->hasDeclaredDestructor() && !rd->getDestructor()->isTrivial())
				{
					this->register_decl_ref(rd->getDestructor());

					cfg::vertex_descriptor member = this->add_node(m_head, enode(cfg::nt_call)
						(eot_member, this->get_name(fd))
						(eot_var, "p:this"));

					this->add_node(m_head, enode(cfg::nt_call)
						(eot_func, this->get_name(rd->getDestructor()))
						(eot_node, member));
				}
			}
		}

		for (clang::CXXRecordDecl::reverse_base_class_const_iterator it = rec->bases_rbegin(); it != rec->bases_rend(); ++it)
		{
			clang::CXXBaseSpecifier const & bs = *it;

			if (bs.isVirtual())
				continue;

			if (clang::CXXRecordDecl const * rd = bs.getType()->getAsCXXRecordDecl())
			{
				if (rd->hasDeclaredDestructor() && !rd->getDestructor()->isTrivial())
				{
					this->register_decl_ref(rd->getDestructor());

					// TODO: proper conversion, exceptions
					this->add_node(m_head, enode(cfg::nt_call)
						(eot_func, this->get_name(rd->getDestructor()))
						(eot_var, "p:this"));
				}
			}
		}
	}

	void build()
	{
		BOOST_ASSERT(!m_fn->isDependentContext());

		this->register_locals(m_fn);
		if (clang::CXXConstructorDecl const * cd = llvm::dyn_cast<clang::CXXConstructorDecl>(m_fn))
			this->build_constructor(cd);
		if (m_fn->hasBody())
			this->build_stmt(m_head, m_fn->getBody());
		if (clang::CXXDestructorDecl const * cd = llvm::dyn_cast<clang::CXXDestructorDecl>(m_fn))
			this->build_destructor(cd);
		this->finish();
	}
};

}

void detail::build_cfg(cfg & c, clang::FunctionDecl const * fn, clang::SourceManager const & sm,
	filename_store & fnames, build_cfg_visitor_base & visitor, std::string const & static_prefix)
{
	context(c, fn, sm, fnames, visitor, static_prefix);
}

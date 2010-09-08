#include "cfg_builder.hpp"

#include <boost/assert.hpp>
#include <boost/lexical_cast.hpp>

#include <clang/AST/Stmt.h>
#include <clang/AST/StmtCXX.h>
#include <clang/AST/Expr.h>
#include <clang/AST/ExprCXX.h>
#include <clang/AST/Decl.h>
#include <clang/AST/DeclCXX.h>

namespace {

std::string make_decl_name(clang::NamedDecl const * decl)
{
	if (clang::FunctionDecl const * fndecl = llvm::dyn_cast<clang::FunctionDecl>(decl))
	{
		std::string name = decl->getQualifiedNameAsString();
		name += '(';
		for (clang::FunctionDecl::param_const_iterator param_ci = fndecl->param_begin(); param_ci != fndecl->param_end(); ++param_ci)
		{
			if (param_ci != fndecl->param_begin())
				name += ", ";
			clang::VarDecl const * param_decl = *param_ci;
			name += static_cast<clang::QualType>(param_decl->getType()->getCanonicalTypeUnqualified()).getAsString();
		}
		name += ')';

		if (clang::CXXMethodDecl const * cxxfndecl = llvm::dyn_cast<clang::CXXMethodDecl>(fndecl))
		{
			if (cxxfndecl->getTypeQualifiers() & clang::Qualifiers::Const)
				name += " const";
			if (cxxfndecl->getTypeQualifiers() & clang::Qualifiers::Volatile)
				name += " volatile";
		}
		return name;
	}
	else
		return decl->getQualifiedNameAsString();
}


struct context
{
	context(cfg & c)
		: g(c), m_head(add_vertex(g))
	{
		g.entry(m_head);

		cfg::vertex_descriptor new_head = add_vertex(g);
		add_edge(m_head, new_head, g);
		m_head = new_head;
	}

	cfg & g;
	cfg::vertex_descriptor m_head;
	std::set<cfg::vertex_descriptor> m_exit_nodes;
	std::vector<cfg::vertex_descriptor> m_break_sentinels;
	std::vector<cfg::vertex_descriptor> m_continue_sentinels;

	typedef std::pair<cfg::vertex_descriptor, std::map<std::string, cfg::vertex_descriptor> > case_context_t;
	std::vector<case_context_t> m_case_contexts;

	std::map<clang::NamedDecl const *, std::string> m_registered_names;

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
			return make_decl_name(decl);
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
			if (clang::ValueDecl const * d = llvm::dyn_cast<clang::ValueDecl>(decl))
			{
				std::string name_base;
				if (decl->getKind() == clang::Decl::ParmVar)
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

		BOOST_ASSERT(op.type < eot_nodetgt);
		return cfg::operand(static_cast<cfg::op_type>(op.type), op.id);
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

	/**
	 * \brief Convert an eop to an op of type ot_node and of the same value.
	 */
	cfg::vertex_descriptor make_node(cfg::vertex_descriptor & head, eop op)
	{
		op = this->make_rvalue(head, op);
		if (op.type != eot_node)
			op = this->add_node(head, enode(cfg::nt_value)(op));
		return boost::get<cfg::vertex_descriptor>(op.id);
	}

	eop add_node(cfg::vertex_descriptor & head, enode const & node)
	{
		BOOST_ASSERT(g[head].type == cfg::nt_none);
		BOOST_ASSERT(g[head].ops.empty());

		g[head].type = node.type;
		for (std::size_t i = 0; i < node.ops.size(); ++i)
			g[head].ops.push_back(this->make_rvalue(head, node.ops[i]));
		g[head].data = node.data;

		cfg::vertex_descriptor new_head = add_vertex(g);
		add_edge(head, new_head, g);
		
		using std::swap;
		swap(head, new_head);
		return eop(eot_node, new_head);
	}

	void set_cond(cfg::vertex_descriptor node, std::size_t index, std::string cond)
	{
		BOOST_ASSERT(in_degree(node, g) == 1);
		cfg::edge_descriptor edge = *in_edges(node, g).first;
		g[edge].id = index;
		g[edge].cond = cond;
	}

	eop build_expr(cfg::vertex_descriptor & head, clang::Expr const * expr)
	{
		BOOST_ASSERT(expr != 0);

		if (clang::BinaryOperator const * e = llvm::dyn_cast<clang::BinaryOperator>(expr))
		{
			eop const & lhs = this->build_expr(head, e->getLHS());
			eop const & rhs = this->build_expr(head, e->getRHS());

			// Treat assignment specially (takes a pointer to the assignee).
			if (e->isAssignmentOp() || e->isCompoundAssignmentOp())
			{
				this->add_node(head, enode(cfg::nt_call, expr)
					(eot_oper, clang::BinaryOperator::getOpcodeStr(e->getOpcode()))
					(this->make_address(lhs))
					(rhs));
				return lhs;
			}
			else if (e->getOpcode() == clang::BO_LOr || e->getOpcode() == clang::BO_LAnd)
			{
				/*std::size_t branch_node = this->make_node(lhs);

				rcfg::builder rhs_cfg(m_id_list);
				op_t rhs = rhs_cfg.build_expr(e->getRHS());

				this->append_edge(rhs_cfg, branch_node, m_nodes.size());

				if (e->getOpcode() == clang::BO_LAnd)
					m_nodes[branch_node].succs[0].op = op_t(cfg::ot_const, "0");
				else
					m_nodes[branch_node].succs[1].op = op_t(cfg::ot_const, "0");

				rcfg_node res_node(node_t::nt_phi, e->getExprLoc());
				res_node(this->make_rvalue(lhs));
				res_node(this->make_rvalue(rhs));  // FIXME: rhs is not correct as it refers to a node before relocation
				return this->add_node(res_node);*/
			}
			else
			{
				return this->add_node(head, enode(cfg::nt_call, expr)
					(eot_oper, clang::BinaryOperator::getOpcodeStr(e->getOpcode()))
					(lhs)
					(rhs));
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
		else if (clang::CharacterLiteral const * e = llvm::dyn_cast<clang::CharacterLiteral>(expr))
		{
			return eop(eot_const, boost::lexical_cast<std::string>(e->getValue()));
		}
		else if (clang::StringLiteral const * e = llvm::dyn_cast<clang::StringLiteral>(expr))
		{
			std::string res;
			if (e->isWide())
			{
				res = "L\"";
				llvm::StringRef str = e->getString();
				for (std::size_t i = 0; i < str.size(); i += 2)
				{
					if (str[i+1] == 0 && str[i] >= 32 && str[i] < 128)
					{
						res.push_back(str[i]);
						if (str[i] == '\\')
							res.push_back('\\');
					}
					else
					{
						res.append("\\u");

						char const digits[] = "0123456789abcdef";

						unsigned char ch = str[i+1];
						res.push_back(digits[ch >> 4]);
						res.push_back(digits[ch & 0xf]);

						ch = str[i];
						res.push_back(digits[ch >> 4]);
						res.push_back(digits[ch & 0xf]);
					}
				}
			}
			else
			{
				res = '\"';
				llvm::StringRef str = e->getString();
				for (std::size_t i = 0; i < str.size(); ++i)
				{
					if (str[i] >= 32 && str[i] < 128)
					{
						res.push_back(str[i]);
						if (str[i] == '\\')
							res.push_back('\\');
					}
					else
					{
						res.append("\\x");

						unsigned char ch = str[i];
						char const digits[] = "0123456789abcdef";
						res.push_back(digits[ch >> 4]);
						res.push_back(digits[ch & 0xf]);
					}
				}
			}

			res += '\"';
			return eop(eot_const, res);
		}
		else if (clang::DeclRefExpr const * e = llvm::dyn_cast<clang::DeclRefExpr>(expr))
		{
			clang::Decl const * decl = e->getDecl();
			if (clang::ValueDecl const * nd = llvm::dyn_cast<clang::ValueDecl>(decl))
			{
				if (llvm::isa<clang::FunctionDecl>(nd))
					return eop(eot_func, this->get_name(nd));
				
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
		else if (clang::ImplicitCastExpr const * e = llvm::dyn_cast<clang::ImplicitCastExpr>(expr))
		{
			// TODO: deal with the casts correctly
			return this->build_expr(head, e->getSubExpr());
		}
		else
		{
			BOOST_ASSERT(0 && "unknown AST node encountered");
			return eop();
		}
	}

	void build_stmt(cfg::vertex_descriptor & head, clang::Stmt const * stmt)
	{
		BOOST_ASSERT(stmt != 0);

		if (clang::CompoundStmt const * s = llvm::dyn_cast<clang::CompoundStmt>(stmt))
		{
			for (clang::CompoundStmt::const_body_iterator it = s->body_begin(); it != s->body_end(); ++it)
				this->build_stmt(head, *it);
		}
		else if (clang::Expr const * s = llvm::dyn_cast<clang::Expr>(stmt))
		{
			this->build_expr(head, s);
		}
		else if (clang::ReturnStmt const * s = llvm::dyn_cast<clang::ReturnStmt>(stmt))
		{
			cfg::operand val;
			if (s->getRetValue() != 0)
				val = this->make_rvalue(head, this->build_expr(head, s->getRetValue()));

			g[head].type = cfg::nt_exit;
			g[head].ops.push_back(cfg::operand(cfg::ot_const, "0"));
			if (s->getRetValue() != 0)
				g[head].ops.push_back(val);
			m_exit_nodes.insert(head);

			head = add_vertex(g);
		}
		else if (clang::BreakStmt const * s = llvm::dyn_cast<clang::BreakStmt>(stmt))
		{
			BOOST_ASSERT(!m_break_sentinels.empty());
			this->join_nodes(head, m_break_sentinels.back());
			head = add_vertex(g);
		}
		else if (clang::ContinueStmt const * s = llvm::dyn_cast<clang::ContinueStmt>(stmt))
		{
			BOOST_ASSERT(!m_break_sentinels.empty());
			this->join_nodes(head, m_continue_sentinels.back());
			head = add_vertex(g);
		}
		else if (clang::IfStmt const * s = llvm::dyn_cast<clang::IfStmt>(stmt))
		{
			this->make_node(head, this->build_expr(head, s->getCond()));
			cfg::vertex_descriptor else_head = this->duplicate_vertex(head);

			this->set_cond(else_head, 0, "0");

			this->build_stmt(head, s->getThen());
			if (s->getElse() != 0)
				this->build_stmt(else_head, s->getElse());
			this->join_nodes(else_head, head);
		}
		else if (clang::WhileStmt const * s = llvm::dyn_cast<clang::WhileStmt>(stmt))
		{
			cfg::vertex_descriptor cond_node = this->make_node(head, this->build_expr(head, s->getCond()));
			cfg::vertex_descriptor body_head = this->duplicate_vertex(head);
			this->set_cond(head, 0, "0");

			m_break_sentinels.push_back(add_vertex(g));
			m_continue_sentinels.push_back(add_vertex(g));

			this->build_stmt(body_head, s->getBody());

			this->join_nodes(body_head, cond_node);
			this->join_nodes(m_break_sentinels.back(), head);
			this->join_nodes(m_continue_sentinels.back(), cond_node);
			m_break_sentinels.pop_back();
			m_continue_sentinels.pop_back();
		}
		else if (clang::DoStmt const * s = llvm::dyn_cast<clang::DoStmt>(stmt))
		{
			cfg::vertex_descriptor start_node = head;

			m_break_sentinels.push_back(add_vertex(g));
			m_continue_sentinels.push_back(add_vertex(g));
			this->build_stmt(head, s->getBody());

			cfg::vertex_descriptor cond_node = this->make_node(head, this->build_expr(head, s->getCond()));
			cfg::vertex_descriptor loop_node = this->duplicate_vertex(head);
			this->set_cond(head, 0, "0");

			this->join_nodes(loop_node, start_node);

			this->join_nodes(m_break_sentinels.back(), head);
			this->join_nodes(m_continue_sentinels.back(), cond_node);
			m_break_sentinels.pop_back();
			m_continue_sentinels.pop_back();
		}
		else if (clang::ForStmt const * s = llvm::dyn_cast<clang::ForStmt>(stmt))
		{
			if (s->getInit())
				this->build_stmt(head, s->getInit());

			cfg::vertex_descriptor cond_node = head;
			cfg::vertex_descriptor exit_node;
			if (s->getCond())
			{
				cond_node = this->make_node(head, this->build_expr(head, s->getCond()));
				exit_node = this->duplicate_vertex(head);
				this->set_cond(exit_node, 0, "0");
			}
			else
				exit_node = add_vertex(g);

			m_break_sentinels.push_back(add_vertex(g));
			m_continue_sentinels.push_back(add_vertex(g));

			cfg::vertex_descriptor body_node = head;
			this->build_stmt(head, s->getBody());
			this->join_nodes(m_continue_sentinels.back(), head);
			m_continue_sentinels.pop_back();

			if (s->getInc())
				this->build_expr(head, s->getInc());
			this->join_nodes(head, cond_node);
			head = exit_node;

			this->join_nodes(m_break_sentinels.back(), exit_node);
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

			eop cond = this->build_expr(head, s->getLHS());
			BOOST_ASSERT(cond.type == eot_const);

			m_case_contexts.back().second[boost::get<std::string>(cond.id)] = head;
			this->build_stmt(head, s->getSubStmt());
		}
		else if (clang::SwitchStmt const * s = llvm::dyn_cast<clang::SwitchStmt>(stmt))
		{
			cfg::vertex_descriptor cond_node = this->make_node(head, this->build_expr(head, s->getCond()));
			cfg::vertex_descriptor cond_cont = head;
			cfg::vertex_descriptor body_start = add_vertex(g);
			head = body_start;

			m_case_contexts.push_back(case_context_t());
			m_break_sentinels.push_back(add_vertex(g));
			this->build_stmt(head, s->getBody());
			this->join_nodes(m_break_sentinels.back(), head);
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
					if (vd->hasInit())
					{
						// TODO: deal with in-place construction
						/*if (vd->getType()->isStructureOrClassType())
						{
							this->build_expr(vd->getInit(), opd_t(rcfg_node::ot_varptr, m_id_list(vd)));
						}
						else */if (vd->getType()->isReferenceType())
						{
							this->add_node(head, enode(cfg::nt_call, stmt)
								(eot_oper, "=")
								(eot_varptr, this->get_name(vd))
								(this->make_address(this->build_expr(head, vd->getInit()))));
						}
						else if (vd->getType()->isArrayType())
						{
							// TODO: initialization by string literal
							if (clang::InitListExpr const * e = llvm::dyn_cast<clang::InitListExpr>(vd->getInit()))
							{
								clang::ArrayType const * at = llvm::dyn_cast<clang::ArrayType>(vd->getType());

								// TODO: type safety, make a loop for zero initialization
								for (std::size_t i = 0; i < e->getNumInits(); ++i)
								{
									// TODO: define semantics of [] operator
									eop op = this->add_node(head, enode(cfg::nt_call, stmt)
										(eot_oper, "[]")
										(eot_varptr, this->get_name(vd))
										(eot_const, boost::lexical_cast<std::string>(i)));

									if (!at->getElementType()->isStructureOrClassType())
									{
										this->add_node(head, enode(cfg::nt_call, stmt)
											(eot_oper, "=")
											(op)
											(this->build_expr(head, e->getInit(i))));
									}
									// TODO
									/*else
									{
										this->build_expr(e->getInit(i), op);
									}*/
								}

								if (clang::ConstantArrayType const * cat = llvm::dyn_cast<clang::ConstantArrayType>(at))
								{
									std::size_t bound = cat->getSize().getLimitedValue();
									for (std::size_t i = e->getNumInits(); i < bound; ++i)
									{
										eop op = this->add_node(head, enode(cfg::nt_call, stmt)
											(eot_oper, "[]")
											(eot_varptr, this->get_name(vd))
											(eot_const, boost::lexical_cast<std::string>(i)));

										this->add_node(head, enode(cfg::nt_call, stmt)
											(eot_oper, "=")
											(op)
											(eot_const, "0"));
									}
								}
							}
							// TODO
							/*else
							{
								BOOST_ASSERT(llvm::isa<clang::CXXConstructExpr>(vd->getInit()));

								// TODO: Make this a loop in the program, ensure proper exception safety
								clang::ConstantArrayType const * at = llvm::dyn_cast<clang::ConstantArrayType>(vd->getType());
								BOOST_ASSERT(at->getSize().getBitWidth() <= sizeof(std::size_t) * CHAR_BIT);
								std::size_t bounds = at->getSize().getLimitedValue();
								for (std::size_t i = 0; i < bounds; ++i)
								{
									eop op = this->add_node(head, enode(cfg::nt_call, stmt)
										(eot_oper, "[]")
										(eot_varptr, this->get_name(vd))
										(eot_const, boost::lexical_cast<std::string>(i)));

									this->build_expr(vd->getInit(), op);
								}
							}*/
						}
						else
						{
							this->add_node(head, enode(cfg::nt_call, stmt)
								(eot_oper, "=")
								(eot_varptr, this->get_name(vd))
								(this->build_expr(head, vd->getInit())));
						}
					}
				}
			}
		}
		else if (clang::NullStmt const * s = llvm::dyn_cast<clang::NullStmt>(stmt))
		{
			// TODO: perhaps we should leave a nt_none node?
		}
		else
		{
			BOOST_ASSERT(0 && "unknown AST node encountered");
		}
	}

	void finish()
	{
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
						BOOST_ASSERT(in_degree(exit_nodes.back(), g) == 0);
						m_exit_nodes.erase(exit_nodes.back());
						remove_vertex(exit_nodes.back(), g);
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
				g[phi_node].type = cfg::nt_value;

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

		cfg::vertex_descriptor entry = g.entry();
		BOOST_ASSERT(g[entry].type == cfg::nt_none);
		BOOST_ASSERT(out_degree(entry, g) > 0);
		if (out_degree(entry, g) == 1)
		{
			cfg::vertex_descriptor new_entry = target(*out_edges(entry, g).first, g);
			g.entry(new_entry);
			clear_vertex(entry, g);
			remove_vertex(entry, g);
		}
	}
};

}

std::pair<std::string, cfg> build_cfg(clang::FunctionDecl const * fn)
{
	BOOST_ASSERT(!fn->isDependentContext());
	BOOST_ASSERT(fn->hasBody());

	std::pair<std::string, cfg> res(make_decl_name(fn), cfg());
	cfg & c = res.second;

	context ctx(c);
	ctx.register_locals(fn);
	ctx.build_stmt(ctx.m_head, fn->getBody());
	ctx.finish();
	return res;
}

#include "ast_dumper.hpp"

program build_program(clang::TranslationUnitDecl const * tu)
{
	std::set<clang::FunctionDecl const *> functionDecls;
	get_used_function_defs(tu->getASTContext(), functionDecls);

	program res;
	for (std::set<clang::FunctionDecl const *>::const_iterator it = functionDecls.begin(); it != functionDecls.end(); ++it)
		res.cfgs().insert(build_cfg(*it));
	return res;
}

#include "cfg_builder.hpp"

#include <boost/assert.hpp>
#include <boost/lexical_cast.hpp>

#include <clang/AST/Stmt.h>
#include <clang/AST/StmtCXX.h>
#include <clang/AST/Expr.h>
#include <clang/AST/ExprCXX.h>
#include <clang/AST/Decl.h>
#include <clang/AST/DeclCXX.h>
#include <clang/AST/DeclTemplate.h>

namespace {

void get_functions_from_declcontext(clang::DeclContext const * declctx, std::set<clang::FunctionDecl const *> & fns)
{
	for (clang::DeclContext::decl_iterator it = declctx->decls_begin(); it != declctx->decls_end(); ++it)
	{
		clang::Decl * decl = *it;

		if (clang::NamedDecl const * nd = llvm::dyn_cast<clang::NamedDecl>(decl))
		{
			std::string name = nd->getQualifiedNameAsString();
			name = name;
		}

		if (clang::FunctionDecl * fnDecl = dyn_cast<clang::FunctionDecl>(decl))
		{
			if (fnDecl->isDependentContext())
				continue;
			fns.insert(fnDecl);
		}
		else if (clang::CXXRecordDecl const * classDecl = dyn_cast<clang::CXXRecordDecl>(decl))
		{
			/*if (classDecl->hasDeclaredDefaultConstructor() && !classDecl->getDefaultConstructor()->isTrivial())
				fns.insert(classDecl->getDefaultConstructor());
			if (classDecl->hasDeclaredCopyConstructor() && !classDecl->getCopyConstructor()->isTrivial())
				fns.insert(classDecl->getCopyConstructor());
			if (classDecl->hasDeclaredCopyAssignment() && !classDecl->getCopyAssignmentOperator()->isTrivial())
				fns.insert(classDecl->getCopyAssignmentOperator());*/
			if (classDecl->hasDefinition() && classDecl->hasDeclaredDestructor() && !classDecl->getDestructor()->isTrivial())
				fns.insert(classDecl->getDestructor());
			get_functions_from_declcontext(classDecl, fns);
		}
		else if (clang::RecordDecl const * classDecl = dyn_cast<clang::RecordDecl>(decl))
		{
			get_functions_from_declcontext(classDecl, fns);
		}
	}
}

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
	}

	std::set<clang::FunctionDecl const *> m_referenced_functions2;

	void register_decl_ref(clang::FunctionDecl const * fn)
	{
		m_referenced_functions2.insert(fn);
	}

	std::set<clang::FunctionDecl const *> const & referenced_functions() const
	{
		return m_referenced_functions2;
	}

	cfg & g;
	cfg::vertex_descriptor m_head;
	std::set<cfg::vertex_descriptor> m_exit_nodes;
	std::vector<cfg::vertex_descriptor> m_break_sentinels;
	std::vector<cfg::vertex_descriptor> m_continue_sentinels;

	std::map<clang::GotoStmt const *, cfg::vertex_descriptor> m_gotos;
	std::map<clang::LabelStmt const *, cfg::vertex_descriptor> m_labels;

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

	typedef std::vector<std::pair<clang::CXXDestructorDecl const *, eop> > lifetime_context_t;
	std::vector<lifetime_context_t> m_fullexpr_lifetimes;
	std::vector<lifetime_context_t> m_block_lifetimes;

	std::vector<clang::Type const *> m_temporaries;

	eop make_temporary(clang::Type const * type)
	{
		std::ostringstream ss;
		ss << "t:" << m_temporaries.size();

		m_temporaries.push_back(type);
		g.add_local(ss.str());
		return eop(eot_var, ss.str());
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

		BOOST_ASSERT(op.type < eot_nodetgt);
		return cfg::operand(static_cast<cfg::op_type>(op.type), op.id);
	}

	eop make_param(eop const & op, clang::Type const * type)
	{
		if (type->isReferenceType() || type->isClassType())
			return this->make_address(op);
		else
			return op;
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
			BOOST_ASSERT(op.type == eot_var);
			op.type = eot_vartgt;
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

		cfg::node n;
		n.type = node.type;
		for (std::size_t i = 0; i < node.ops.size(); ++i)
			n.ops.push_back(this->make_rvalue(head, node.ops[i]));
		n.data = node.data;
		g[head] = n;

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

	void build_construct_expr(cfg::vertex_descriptor & head, eop const & tg, clang::CXXConstructExpr const * e)
	{
		BOOST_ASSERT(tg.type != eot_none);

		enode node(cfg::nt_call, e);
		this->register_decl_ref(e->getConstructor());
		node(eot_func, this->get_name(e->getConstructor()));

		clang::FunctionProtoType const * fntype = llvm::dyn_cast<clang::FunctionProtoType>(e->getConstructor()->getType().getTypePtr());
		node(tg);

		for (std::size_t i = 0; i < e->getNumArgs(); ++i)
			node(this->make_param(this->build_expr(head, e->getArg(i)), fntype->getArgType(i).getTypePtr()));

		this->add_node(head, node);
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
				return this->add_node(head, enode(cfg::nt_phi, e)(eot_node, rhs_value_node)(eot_node, cond_node));
			}
			else
			{
				eop const & rhs = this->build_expr(head, e->getRHS());
				return this->add_node(head, enode(cfg::nt_call, expr)
					(eot_oper, clang::BinaryOperator::getOpcodeStr(e->getOpcode()))
					(lhs)
					(rhs));
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
				eop op = this->add_node(head, enode(cfg::nt_call, e)
					(eot_oper, e->getOpcode() == clang::UO_PreInc? "+": "-")
					(expr)
					(eot_const, "1"));
				this->add_node(head, enode(cfg::nt_call, e)
					(eot_oper, "=")
					(this->make_address(expr))
					(op));
				return expr;
			}
			else if (e->getOpcode() == clang::UO_PostInc || e->getOpcode() == clang::UO_PostDec)
			{
				eop expr = this->build_expr(head, e->getSubExpr());
				eop op = this->add_node(head, enode(cfg::nt_call, e)
					(eot_oper, e->getOpcode() == clang::UO_PostInc? "+": "-")
					(expr)
					(eot_const, "1"));
				this->add_node(head, enode(cfg::nt_call, e)
					(eot_oper, "=")
					(this->make_address(expr))
					(op));
				return op;
			}
			else if (e->getOpcode() == clang::UO_Plus)
			{
				return this->build_expr(head, e->getSubExpr());
			}
			else
			{
				return this->add_node(head, enode(cfg::nt_call, e)
					(eot_oper, clang::UnaryOperator::getOpcodeStr(e->getOpcode()))
					(this->build_expr(head, e->getSubExpr())));
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
					callee_op = eop(eot_func, this->get_name(mdecl));

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
				fntype = llvm::dyn_cast<clang::FunctionProtoType>(e->getCallee()->getType()->getPointeeType());
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
				node(this->make_param(params[i], param_types[i]));

			eop node_op = this->add_node(head, node);
			return result_op.type == eot_none? node_op: result_op;
		}
		else if (clang::ConditionalOperator const * e = llvm::dyn_cast<clang::ConditionalOperator>(expr))
		{
			eop cond_op = this->build_expr(head, e->getCond());
			cfg::vertex_descriptor branch_node = this->make_node(head, cond_op);
			cfg::vertex_descriptor false_head = this->duplicate_vertex(head);
			g[*in_edges(false_head, g).first].cond = "0";

			cfg::vertex_descriptor true_node = this->make_node(head, this->build_expr(head, e->getTrueExpr()));
			cfg::vertex_descriptor false_node = this->make_node(false_head, this->build_expr(false_head, e->getFalseExpr()));
			this->join_nodes(false_head, head);

			return this->add_node(head, enode(cfg::nt_phi, e)
				(eot_node, true_node)
				(eot_node, false_node));
		}
		else if (clang::MemberExpr const * e = llvm::dyn_cast<clang::MemberExpr>(expr))
		{
			// TODO: lvalue/rvalue
			eop base = this->build_expr(head, e->getBase());
			if (!e->isArrow())
				base = this->make_address(base);
			return this->make_deref(head, this->add_node(head, enode(cfg::nt_call, e)
				(eot_member, this->get_name(e->getMemberDecl()))
				(base)));
		}
		if (clang::ArraySubscriptExpr const * e = llvm::dyn_cast<clang::ArraySubscriptExpr>(expr))
		{
			return this->make_deref(head, this->add_node(head, enode(cfg::nt_call, e)
				(eot_oper, "+")
				(this->build_expr(head, e->getLHS()))
				(this->build_expr(head, e->getRHS()))));
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
			m_fullexpr_lifetimes.back().push_back(std::make_pair(e->getTemporary()->getDestructor(), this->make_address(res)));
			return res;
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

	eop build_full_expr(cfg::vertex_descriptor & head, clang::Expr const * expr)
	{
		m_fullexpr_lifetimes.push_back(lifetime_context_t());
		eop res = this->build_expr(head, expr);
		lifetime_context_t const & ctx = m_fullexpr_lifetimes.back();
		for (std::size_t i = ctx.size(); i != 0; --i)
		{
			std::pair<clang::CXXDestructorDecl const *, eop> const & op = ctx[i-1];
			BOOST_ASSERT(op.first != 0);
			this->register_decl_ref(op.first);
			this->add_node(head, enode(cfg::nt_call)(eot_func, this->get_name(op.first))(op.second));
		}
		m_fullexpr_lifetimes.pop_back();
		return res;
	}

	void end_block_lifetime(cfg::vertex_descriptor & head)
	{
		BOOST_ASSERT(!m_block_lifetimes.empty());
		for (std::size_t i = m_block_lifetimes.back().size(); i != 0; --i)
		{
			std::pair<clang::CXXDestructorDecl const *, eop> const & op = m_block_lifetimes.back()[i-1];
			BOOST_ASSERT(op.first != 0);
			this->register_decl_ref(op.first);
			this->add_node(head, enode(cfg::nt_call)(eot_func, this->get_name(op.first))(op.second));
		}
		m_block_lifetimes.pop_back();
	}

	void init_object(cfg::vertex_descriptor & head, eop const & var, clang::Expr const * e)
	{
		clang::Type const * type = e->getType().getTypePtr();

		// TODO: check fullexpr lifetimes
		if (e->getType()->isStructureOrClassType())
		{
			BOOST_ASSERT(llvm::isa<clang::CXXConstructExpr>(e));
			this->build_construct_expr(head, var, llvm::dyn_cast<clang::CXXConstructExpr>(e));
			clang::CXXRecordDecl const * recordDecl = e->getType()->getAsCXXRecordDecl();
			if (recordDecl && recordDecl->hasDeclaredDestructor())
				m_block_lifetimes.back().push_back(std::make_pair(recordDecl->getDestructor(), var));
		}
		else if (e->getType()->isReferenceType())
		{
			this->add_node(head, enode(cfg::nt_call, e)
				(eot_oper, "=")
				(var)
				(this->make_address(this->build_expr(head, e))));
		}
		else if (type->isArrayType())
		{
			// TODO: initialization by string literal
			if (clang::InitListExpr const * e = llvm::dyn_cast<clang::InitListExpr>(e))
			{
				clang::ArrayType const * at = llvm::dyn_cast<clang::ArrayType>(type);

				// TODO: type safety, make a loop for zero initialization
				for (std::size_t i = 0; i < e->getNumInits(); ++i)
				{
					// TODO: define semantics of [] operator
					eop op = this->add_node(head, enode(cfg::nt_call, e)
						(eot_oper, "[]")
						(var)
						(eot_const, boost::lexical_cast<std::string>(i)));

					if (!at->getElementType()->isStructureOrClassType())
					{
						this->add_node(head, enode(cfg::nt_call, e)
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
						eop op = this->add_node(head, enode(cfg::nt_call, e)
							(eot_oper, "[]")
							(var)
							(eot_const, boost::lexical_cast<std::string>(i)));

						this->add_node(head, enode(cfg::nt_call, e)
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
			this->add_node(head, enode(cfg::nt_call, e)
				(eot_oper, "=")
				(var)
				(this->build_expr(head, e)));
		}
	}

	void build_stmt(cfg::vertex_descriptor & head, clang::Stmt const * stmt)
	{
		BOOST_ASSERT(stmt != 0);

		if (clang::CompoundStmt const * s = llvm::dyn_cast<clang::CompoundStmt>(stmt))
		{
			m_block_lifetimes.push_back(lifetime_context_t());
			for (clang::CompoundStmt::const_body_iterator it = s->body_begin(); it != s->body_end(); ++it)
				this->build_stmt(head, *it);
			this->end_block_lifetime(head);
		}
		else if (clang::Expr const * s = llvm::dyn_cast<clang::Expr>(stmt))
		{
			this->build_full_expr(head, s);
		}
		else if (clang::ReturnStmt const * s = llvm::dyn_cast<clang::ReturnStmt>(stmt))
		{
			cfg::operand val;
			if (s->getRetValue() != 0)
				val = this->make_rvalue(head, this->build_full_expr(head, s->getRetValue()));

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
			cfg::vertex_descriptor cond_node = this->make_node(head, this->build_full_expr(head, s->getCond()));
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

			cfg::vertex_descriptor cond_node = this->make_node(head, this->build_full_expr(head, s->getCond()));
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
				cond_node = this->make_node(head, this->build_full_expr(head, s->getCond()));
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
				this->build_full_expr(head, s->getInc());
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
			cfg::vertex_descriptor cond_node = this->make_node(head, this->build_full_expr(head, s->getCond()));
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
						this->init_object(head, eop(eot_varptr, this->get_name(vd)), vd->getInit());
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

		BOOST_ASSERT(g.entry() != 0);
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
				eop memberop = this->add_node(m_head, enode(cfg::nt_call)
					(eot_member, this->get_name(init->getMember()))
					(eot_var, "p:this"));
				this->init_object(m_head, memberop, init->getInit());
			}
			else
			{
				// TODO: Convert p:this
				BOOST_ASSERT(init->isBaseInitializer());
				this->init_object(m_head, eop(eot_var, "p:this"), init->getInit());
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

					eop member = this->add_node(m_head, enode(cfg::nt_call)
						(eot_member, this->get_name(fd))
						(eot_var, "p:this"));

					this->add_node(m_head, enode(cfg::nt_call)
						(eot_func, this->get_name(rd->getDestructor()))
						(member));
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

	void build(clang::FunctionDecl const * fn)
	{
		BOOST_ASSERT(!fn->isDependentContext());

		this->register_locals(fn);
		if (clang::CXXConstructorDecl const * cd = llvm::dyn_cast<clang::CXXConstructorDecl>(fn))
			this->build_constructor(cd);
		if (fn->hasBody())
			this->build_stmt(m_head, fn->getBody());
		if (clang::CXXDestructorDecl const * cd = llvm::dyn_cast<clang::CXXDestructorDecl>(fn))
			this->build_destructor(cd);
		this->finish();
	}
};

}

program build_program(clang::TranslationUnitDecl const * tu)
{
	std::set<clang::FunctionDecl const *> unprocessedFunctions;
	get_functions_from_declcontext(tu, unprocessedFunctions);

	std::set<clang::FunctionDecl const *> processedFunctions;
	program res;
	while (!unprocessedFunctions.empty())
	{
		clang::FunctionDecl const * fn = *unprocessedFunctions.begin();
		unprocessedFunctions.erase(unprocessedFunctions.begin());

		BOOST_ASSERT(processedFunctions.find(fn) == processedFunctions.end());

		cfg c;
		context ctx(c);
		ctx.build(fn);

		res.cfgs().insert(std::make_pair(make_decl_name(fn), c));

		std::set_difference(
			ctx.referenced_functions().begin(), ctx.referenced_functions().end(),
			processedFunctions.begin(), processedFunctions.end(),
			std::inserter(unprocessedFunctions, unprocessedFunctions.begin()));
	}
	return res;
}

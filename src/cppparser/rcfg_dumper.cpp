#include "rcfg_dumper.hpp"
#include "ast_dumper.hpp"

#include <clang/AST/Expr.h>
#include <clang/AST/DeclCXX.h>
#include <clang/AST/StmtCXX.h>
#include <clang/AST/ExprCXX.h>

#include <boost/assert.hpp>
#include <boost/lexical_cast.hpp>

#include <sstream>

//==================================================================
rcfg_id_list::rcfg_id_list(clang::FunctionDecl const & fn, clang::ASTContext & ctx)
	: m_fn(fn), m_ctx(ctx)
{
	if (fn.getResultType()->isStructureOrClassType())
		m_parameters.push_back((*this)("r:"));
	m_locals.push_back((*this)("r:"));

	std::set<std::string> used_names;
	for (clang::FunctionDecl::decl_iterator it = fn.decls_begin(); it != fn.decls_end(); ++it)
	{
		clang::Decl const * decl = *it;
		if (clang::ValueDecl const * d = llvm::dyn_cast<clang::ValueDecl>(decl))
		{
			std::string name_base;
			if (decl->getKind() == clang::Decl::ParmVar)
				name_base = "p:" + d->getQualifiedNameAsString();
			else
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
					m_decl_names[d] = name;
					break;
				}
			}

			m_locals.push_back((*this)(d));
			if (decl->getKind() == clang::Decl::ParmVar)
				m_parameters.push_back((*this)(d));
		}
	}
}

std::size_t rcfg_id_list::operator()(clang::NamedDecl const * decl)
{
	std::map<clang::NamedDecl const *, std::string>::const_iterator ci = m_decl_names.find(decl);
	if (ci != m_decl_names.end())
		return this->operator()(ci->second);
	else
		return this->operator()(make_decl_name(decl));
}

std::size_t rcfg_id_list::operator()(std::string const & str)
{
	std::map<std::string, std::size_t>::const_iterator ci = m_name_ids.find(str);
	if (ci == m_name_ids.end())
	{
		m_names.push_back(str);
		ci = m_name_ids.insert(std::make_pair(str, m_names.size() - 1)).first;
	}
	return ci->second;
}

std::size_t rcfg_id_list::make_temporary(clang::Type const *)
{
	std::ostringstream ss;
	ss << "t:" << m_temporaries.size();

	std::size_t id = this->operator()(ss.str());

	m_locals.push_back(id);
	m_temporaries.push_back(id);
	return id; 
}

//==================================================================
rcfg::builder::builder(rcfg_id_list & id_list, clang::Stmt const * stmt)
	: m_id_list(id_list)
{
	if (stmt)
		this->build(stmt);
}

rcfg_node::operand rcfg::builder::add_node(rcfg_node const & node)
{
	m_nodes.push_back(node);
	m_nodes.back().add_succ(m_nodes.size());
	return rcfg_node::operand(rcfg_node::ot_nodeval, m_nodes.size() - 1);
}

std::vector<clang::Type const *> get_function_param_types(clang::FunctionDecl const & fn)
{
	std::vector<clang::Type const *> param_types;

	clang::Type const * restype = fn.getResultType().getTypePtr();
	if (restype->isStructureOrClassType() || restype->isReferenceType())
		param_types.push_back(restype);

	if (clang::CXXMethodDecl const * md = llvm::dyn_cast<clang::CXXMethodDecl>(&fn))
		param_types.push_back(md->getThisType(fn.getASTContext()).getTypePtr());

	for (clang::FunctionDecl::param_const_iterator ci = fn.param_begin(); ci != fn.param_end(); ++ci)
		param_types.push_back((*ci)->getType().getTypePtr());

	return param_types;
}

rcfg_node::operand rcfg::builder::build_expr(clang::Expr const * expr, rcfg_node::operand const & target)
{
	typedef rcfg_node::operand opd_t;

	if (clang::ArraySubscriptExpr const * e = llvm::dyn_cast<clang::ArraySubscriptExpr>(expr))
	{
		return this->add_node(rcfg_node()
			(rcfg_node::ot_function, m_id_list("[]"))
			(this->build_expr(e->getLHS()))
			(this->build_expr(e->getRHS())));
	}
	else if (clang::BinaryOperator const * e = llvm::dyn_cast<clang::BinaryOperator>(expr))
	{
		// Treat assignment specially (takes a pointer to the assignee).
		if (e->isAssignmentOp() || e->isCompoundAssignmentOp())
		{
			op_t lhs = this->build_expr(e->getLHS());
			this->add_node(rcfg_node()
				(rcfg_node::ot_function, m_id_list(clang::BinaryOperator::getOpcodeStr(e->getOpcode())))
				(this->make_address(lhs))
				(this->make_rvalue(this->build_expr(e->getRHS()))));
			return lhs;
		}
		else if (e->getOpcode() == clang::BinaryOperator::LOr || e->getOpcode() == clang::BinaryOperator::LAnd)
		{
			op_t lhs = this->build_expr(e->getLHS());
			std::size_t branch_node = this->make_node(lhs);

			rcfg::builder rhs_cfg(m_id_list);
			op_t rhs = rhs_cfg.build_expr(e->getRHS());

			this->append_edge(rhs_cfg, branch_node, m_nodes.size());

			if (e->getOpcode() == clang::BinaryOperator::LAnd)
				m_nodes[branch_node].succs[0].op = op_t(node_t::ot_const, m_id_list("0"));
			else
				m_nodes[branch_node].succs[1].op = op_t(node_t::ot_const, m_id_list("0"));
			
			rcfg_node res_node(node_t::nt_phi);
			res_node(lhs);
			res_node(rhs);  // FIXME: rhs is not correct as it refers to a node before relocation
			return this->add_node(res_node);
		}
		else
		{
			return this->add_node(rcfg_node()
				(rcfg_node::ot_function, m_id_list(clang::BinaryOperator::getOpcodeStr(e->getOpcode())))
				(this->make_rvalue(this->build_expr(e->getLHS())))
				(this->make_rvalue(this->build_expr(e->getRHS()))));
		}
	}
	else if (clang::UnaryOperator const * e = llvm::dyn_cast<clang::UnaryOperator>(expr))
	{
		if (e->getOpcode() == clang::UnaryOperator::AddrOf)
		{
			return this->make_address(this->build_expr(e->getSubExpr()));
		}
		else if (e->getOpcode() == clang::UnaryOperator::Deref)
		{
			return this->make_deref(this->build_expr(e->getSubExpr()));
		}
		else
		{
			return this->add_node(rcfg_node()
				(rcfg_node::ot_function, m_id_list(clang::UnaryOperator::getOpcodeStr(e->getOpcode())))
				(this->build_expr(e->getSubExpr())));
		}
	}
	else if (clang::DeclRefExpr const * e = llvm::dyn_cast<clang::DeclRefExpr>(expr))
	{
		clang::Decl const * decl = e->getDecl();
		if (clang::ValueDecl const * nd = llvm::dyn_cast<clang::ValueDecl>(decl))
		{
			return opd_t(llvm::isa<clang::FunctionDecl>(nd)? rcfg_node::ot_function: nd->getType()->isReferenceType()? rcfg_node::ot_vartgt: rcfg_node::ot_varval, m_id_list(nd));
		}
		else
		{
			BOOST_ASSERT(0);
		}
	}
	else if (clang::CallExpr const * e = llvm::dyn_cast<clang::CallExpr>(expr))
	{
		// There are three possibilities.
		//  1. The expression type is clang::CXXMemberCallExpr. Then the callee is either
		//    a. clang::MemberExpr and the type of the function can be determined from the
		//       member declaration (remember there is an implicit `this` parameter), or
		//    b. clang::BinaryOperator, with either PtrMemD or PtrMemI; the type of
		//       the parameters can be extracted from the rhs operand.
		//  2. This is a normal invocation, in which case the type of the callee is a pointer to function,
		//     the types of parameters can be extracted from there.
		//
		// Additionally, there can be an implicit parameter representing the return value
		// (if the value is of a class type).

		opd_t callee_op;
		std::vector<opd_t> params;
		std::vector<clang::Type const *> param_types;

		clang::FunctionProtoType const * fntype;

		if (llvm::isa<clang::CXXMemberCallExpr>(e))
		{
			if (clang::MemberExpr const * mcallee = llvm::dyn_cast<clang::MemberExpr>(e->getCallee()))
			{
				clang::CXXMethodDecl const * mdecl = llvm::dyn_cast<clang::CXXMethodDecl>(mcallee->getMemberDecl());
				
				opd_t this_op = this->build_expr(mcallee->getBase());
				if (!mcallee->isArrow())
					this_op = this->make_address(this_op);
				params.push_back(this_op);

				param_types.push_back(
					mdecl->getThisType(mdecl->getASTContext()).getTypePtr());

				callee_op = opd_t(rcfg_node::ot_function, m_id_list(mdecl));

				fntype = llvm::dyn_cast<clang::FunctionProtoType>(mdecl->getType().getTypePtr());
			}
			else if (clang::BinaryOperator const * callee = llvm::dyn_cast<clang::BinaryOperator>(e->getCallee()))
			{
				BOOST_ASSERT(callee->getOpcode() == clang::BinaryOperator::PtrMemD || callee->getOpcode() == clang::BinaryOperator::PtrMemI);

				callee_op = this->build_expr(callee->getRHS());
				clang::MemberPointerType const * calleePtrType = llvm::dyn_cast<clang::MemberPointerType>(callee->getRHS()->getType().getTypePtr());
				BOOST_ASSERT(calleePtrType);

				opd_t this_op = this->build_expr(callee->getLHS());
				if (callee->getOpcode() == clang::BinaryOperator::PtrMemD)
					this_op = this->make_address(this_op);
				params.push_back(this_op);

				param_types.push_back(m_id_list.ctx().getPointerType(calleePtrType->getClass()->getCanonicalTypeInternal()).getTypePtr());

				fntype = llvm::dyn_cast<clang::FunctionProtoType>(calleePtrType->getPointeeType().getTypePtr());
			}
			else
				BOOST_ASSERT(0);
		}
		else
		{
			callee_op = this->build_expr(e->getCallee());
			fntype = llvm::dyn_cast<clang::FunctionProtoType>(e->getCallee()->getType()->getPointeeType());
		}

		BOOST_ASSERT(fntype);

		opd_t result_op;
		clang::Type const * restype = fntype->getResultType().getTypePtr();
		if (restype->isStructureOrClassType())
		{
			std::size_t classres = m_id_list.make_temporary(restype);
			params.insert(params.begin(), opd_t(rcfg_node::ot_varval, classres));
			param_types.insert(param_types.begin(), restype);
			result_op = opd_t(rcfg_node::ot_varval, classres);
		}

		BOOST_ASSERT(fntype->getNumArgs() == e->getNumArgs());

		for (std::size_t i = 0; i < fntype->getNumArgs(); ++i)
		{
			params.push_back(this->build_expr(e->getArg(i)));
			param_types.push_back(fntype->getArgType(i).getTypePtr());
		}

		// TODO: handle classes with conversion to pointer to fn.
		rcfg_node node;
		node(callee_op);
		for (std::size_t i = 0; i < params.size(); ++i)
			node(this->make_param(params[i], param_types[i]));

		rcfg_node::operand node_op = this->add_node(node);
		return result_op.type == rcfg_node::ot_none? node_op: result_op;
	}
	else if (clang::CastExpr const * e = llvm::dyn_cast<clang::CastExpr>(expr))
	{
		opd_t op = this->build_expr(e->getSubExpr());
		return op;
	}
	else if (clang::IntegerLiteral const * e = llvm::dyn_cast<clang::IntegerLiteral>(expr))
	{
		return op_t(rcfg_node::ot_const, m_id_list(e->getValue().toString(10, true)));
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
		return op_t(rcfg_node::ot_const, m_id_list(res));
	}
	else if (clang::CXXConstructExpr const * e = llvm::dyn_cast<clang::CXXConstructExpr>(expr))
	{
		// TODO: proper conversion
		rcfg_node node;
		node(rcfg_node::ot_function, m_id_list(e->getConstructor()));

		clang::FunctionProtoType const * fntype = llvm::dyn_cast<clang::FunctionProtoType>(e->getConstructor()->getType().getTypePtr());

		rcfg_node::operand tg = target;
		if (tg.type == rcfg_node::ot_none)
		//if (clang::CXXTemporaryObjectExpr const * e = llvm::dyn_cast<clang::CXXTemporaryObjectExpr>(expr))
			tg = rcfg_node::operand(rcfg_node::ot_varptr, m_id_list.make_temporary(e->getType().getTypePtr()));

		BOOST_ASSERT(tg.type != rcfg_node::ot_none);
		node(tg);

		for (std::size_t i = 0; i < e->getNumArgs(); ++i)
			node(this->make_param(this->build_expr(e->getArg(i)), fntype->getArgType(i).getTypePtr()));

		this->add_node(node);
		return this->make_deref(tg);
	}
	else if (clang::CXXBindTemporaryExpr const * e = llvm::dyn_cast<clang::CXXBindTemporaryExpr>(expr))
	{
		return this->build_expr(e->getSubExpr(), target);
	}
	else if (clang::CXXExprWithTemporaries const * e = llvm::dyn_cast<clang::CXXExprWithTemporaries>(expr))
	{
		return this->build_expr(e->getSubExpr(), target);
	}
	else if (clang::ParenExpr const * e = llvm::dyn_cast<clang::ParenExpr>(expr))
	{
		return this->build_expr(e->getSubExpr(), target);
	}
	else if (clang::MemberExpr const * e = llvm::dyn_cast<clang::MemberExpr>(expr))
	{
		return this->make_deref(this->add_node(node_t()
			(node_t::ot_member, m_id_list(e->getMemberDecl()->getQualifiedNameAsString()))
			(this->build_expr(e->getBase()))));
	}
	else
	{
		BOOST_ASSERT(0);
	}
}

void rcfg::builder::build(clang::Stmt const * stmt)
{
	BOOST_ASSERT(m_nodes.empty());
	BOOST_ASSERT(stmt != 0);

	typedef rcfg_node::operand opd_t;

	if (llvm::isa<clang::AsmStmt>(stmt) || llvm::isa<clang::NullStmt>(stmt))
	{
		m_nodes.push_back(stmt);
		m_nodes[0].succs.push_back(1);
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
					if (vd->getType()->isStructureOrClassType())
					{
						this->build_expr(vd->getInit(), opd_t(rcfg_node::ot_varptr, m_id_list(vd)));
					}
					else if (vd->getType()->isReferenceType())
					{
						this->add_node(rcfg_node()
							(rcfg_node::ot_function, m_id_list("="))
							(rcfg_node::ot_varptr, m_id_list(vd))
							(this->make_address(this->build_expr(vd->getInit()))));
					}
					else
					{
						this->add_node(rcfg_node()
							(rcfg_node::ot_function, m_id_list("="))
							(rcfg_node::ot_varptr, m_id_list(vd))
							(this->build_expr(vd->getInit())));
					}
				}
			}
		}
		if (!m_nodes.empty())
			m_nodes.back().stmt = s;
	}
	else if (clang::Expr const * s = llvm::dyn_cast<clang::Expr>(stmt))
	{
		rcfg_node::operand op = this->build_expr(s);
		if (op.type != rcfg_node::ot_nodeval && op.type != rcfg_node::ot_nodetgt)
			this->add_node(rcfg_node(node_t::nt_value, s)(op));
		else
			m_nodes[op.id].stmt = s;
	}
	else if (clang::LabelStmt const * s = llvm::dyn_cast<clang::LabelStmt>(stmt))
	{
		this->build(s->getSubStmt());
		m_labels[s] = 0;
	}
	else if (clang::CaseStmt const * s = llvm::dyn_cast<clang::CaseStmt>(stmt))
	{
		this->build(s->getSubStmt());
		m_switch_cases.push_back(std::make_pair(0, s));
	}
	else if (clang::DefaultStmt const * s = llvm::dyn_cast<clang::DefaultStmt>(stmt))
	{
		this->build(s->getSubStmt());
		m_default_case = std::make_pair(0, s);
	}
	else if (clang::ReturnStmt const * s = llvm::dyn_cast<clang::ReturnStmt>(stmt))
	{
		if (s->getRetValue())
		{
			if (m_id_list.fn().getResultType().getTypePtr()->isStructureOrClassType())
			{
				this->build_expr(s->getRetValue(), rcfg_node::operand(rcfg_node::ot_varptr, m_id_list("r:")));
			}
			else
			{
				rcfg_node::operand retval = this->build_expr(s->getRetValue());
				if (m_id_list.fn().getResultType().getTypePtr()->isReferenceType())
					retval = this->make_address(retval);

				this->add_node(rcfg_node()
					(rcfg_node::ot_function, m_id_list("="))
					(rcfg_node::ot_varptr, m_id_list("r:"))
					(this->make_rvalue(retval)));
			}

			m_nodes.back().succs.pop_back();
			m_nodes.back().stmt = stmt;		}
		else
		{
			m_nodes.push_back(stmt);
		}

		m_nodes.back().break_type = rcfg_node::bt_return;
	}
	else if (isa<clang::BreakStmt>(stmt))
	{
		m_nodes.push_back(stmt);
		m_nodes[0].break_type = rcfg_node::bt_break;
	}
	else if (isa<clang::ContinueStmt>(stmt))
	{
		m_nodes.push_back(stmt);
		m_nodes[0].break_type = rcfg_node::bt_continue;
	}
	else if (isa<clang::GotoStmt>(stmt))
	{
		m_nodes.push_back(stmt);
		m_nodes[0].break_type = rcfg_node::bt_goto;
	}
	else if (clang::CompoundStmt const * s = llvm::dyn_cast<clang::CompoundStmt>(stmt))
	{
		for (clang::CompoundStmt::const_body_iterator it = s->body_begin(); it != s->body_end(); ++it)
			this->append(*it);
	}
	else if (clang::IfStmt const * s = llvm::dyn_cast<clang::IfStmt>(stmt))
	{
		std::size_t cond_node = this->make_node(this->build_expr(s->getCond()));
		m_nodes[cond_node].succs.pop_back();
		m_nodes[cond_node].stmt = s->getCond();

		this->append_edge(s->getThen(), cond_node, m_nodes.size());

		if (s->getElse())
			this->append_edge(s->getElse(), cond_node, m_nodes.size());
		else
			m_nodes[cond_node].succs.push_back(m_nodes.size());
	}
	else if (clang::SwitchStmt const * s = llvm::dyn_cast<clang::SwitchStmt>(stmt))
	{
		m_nodes.push_back(rcfg_node(s->getCond()));
		this->append(s->getBody());
		
		// Resolve case labels
		if (m_default_case.second)
		{
			m_nodes[0].succs.push_back(m_default_case.first);
			m_default_case.second = 0;
		}

		for (std::size_t i = 0; i < m_switch_cases.size(); ++i)
			m_nodes[0].succs.push_back(rcfg_node::succ(m_switch_cases[i].first, m_switch_cases[i].second->getLHS()));
		m_switch_cases.clear();

		this->fix(rcfg_node::bt_break, m_nodes.size());
	}
	else if (clang::WhileStmt const * s = llvm::dyn_cast<clang::WhileStmt>(stmt))
	{
		m_nodes.push_back(rcfg_node(s->getCond()));
		this->append_edge(s->getBody(), 0, 0);
		m_nodes[0].succs.push_back(m_nodes.size());

		this->fix(rcfg_node::bt_break, m_nodes.size());
		this->fix(rcfg_node::bt_continue, 0);
	}
	else if (clang::DoStmt const * s = llvm::dyn_cast<clang::DoStmt>(stmt))
	{
		this->build(s->getBody());
		m_nodes.push_back(rcfg_node(s->getCond()));
		m_nodes[0].succs.push_back(0);
		m_nodes[0].succs.push_back(m_nodes.size());

		this->fix(rcfg_node::bt_break, m_nodes.size());
		this->fix(rcfg_node::bt_continue, m_nodes.size() - 1);
	}
	else if (clang::ForStmt const * s = llvm::dyn_cast<clang::ForStmt>(stmt))
	{
		builder body_cfg(m_id_list, s->getBody());
		body_cfg.fix(rcfg_node::bt_continue, body_cfg.m_nodes.size());
		if (s->getInc())
			body_cfg.append(s->getInc());

		builder loop_cfg(m_id_list);
		if (s->getCond())
		{
			loop_cfg.build(s->getCond());
			loop_cfg.append_edge(body_cfg, 0, 0);
		}
		else
		{
			loop_cfg = body_cfg;
			for (std::size_t i = 0; i < loop_cfg.m_nodes.size(); ++i)
			{
				for (std::size_t j = 0; j < loop_cfg.m_nodes[i].succs.size(); ++j)
				{
					if (loop_cfg.m_nodes[i].succs[j].id == loop_cfg.m_nodes.size())
						loop_cfg.m_nodes[i].succs[j].id = 0;
				}
			}
		}
		loop_cfg.fix(rcfg_node::bt_break, loop_cfg.m_nodes.size());

		if (s->getInit())
			m_nodes.push_back(s->getInit());
		this->append(loop_cfg);
	}
	else if (clang::CXXTryStmt const * s = llvm::dyn_cast<clang::CXXTryStmt>(stmt))
	{
		// TODO
	}
	else if (clang::CXXCatchStmt const * s = llvm::dyn_cast<clang::CXXCatchStmt>(stmt))
	{
		// TODO
	}
	else if (clang::CXXCatchStmt const * s = llvm::dyn_cast<clang::CXXCatchStmt>(stmt))
	{
		// TODO
	}
	else
	{
		BOOST_ASSERT(0 && "the statement type is not recognized");
	}
}

void rcfg::builder::fix_function()
{
	this->fix(rcfg_node::bt_return, m_nodes.size());

	// Resolve gotos
	for (std::size_t i = 0; i < m_nodes.size(); ++i)
	{
		if (m_nodes[i].break_type == rcfg_node::bt_goto)
		{
			m_nodes[i].break_type = rcfg_node::bt_none;
			std::map<clang::LabelStmt const *, std::size_t>::const_iterator
				ci = m_labels.find(llvm::dyn_cast<clang::GotoStmt>(m_nodes[i].stmt)->getLabel());
			BOOST_ASSERT(ci != m_labels.end());
			m_nodes[i].succs.push_back(ci->second);
		}
	}

	m_labels.clear();
}

void rcfg::builder::fix(rcfg_node::break_type_t bt, std::size_t target)
{
	for (std::size_t i = 0; i < m_nodes.size(); ++i)
	{
		if (m_nodes[i].break_type == bt)
		{
			m_nodes[i].break_type = rcfg_node::bt_none;
			m_nodes[i].succs.push_back(target);
		}
	}
}

void rcfg::builder::merge_labels(builder const & nested, std::size_t shift)
{
	for (std::map<clang::LabelStmt const *, std::size_t>::const_iterator ci = nested.m_labels.begin(); ci != nested.m_labels.end(); ++ci)
		m_labels[ci->first] = ci->second + shift;

	for (std::size_t i = 0; i < nested.m_switch_cases.size(); ++i)
	{
		m_switch_cases.push_back(nested.m_switch_cases[i]);
		m_switch_cases.back().first += shift;
	}

	if (nested.m_default_case.second)
	{
		BOOST_ASSERT(!m_default_case.second);
		m_default_case.second = nested.m_default_case.second;
		m_default_case.first = nested.m_default_case.first + shift;
	}
}

void rcfg::builder::append(clang::Stmt const * stmt)
{
	builder b(m_id_list);
	b.build(stmt);
	this->append(b);
}

void rcfg::builder::append(builder const & nested)
{
	std::size_t const old_size = m_nodes.size();
	m_nodes.insert(m_nodes.end(), nested.m_nodes.begin(), nested.m_nodes.end());

	for (std::size_t i = old_size; i < m_nodes.size(); ++i)
	{
		for (std::size_t j = 0; j < m_nodes[i].succs.size(); ++j)
			m_nodes[i].succs[j].id += old_size;
		for (std::size_t j = 0; j < m_nodes[i].operands.size(); ++j)
		{
			if (m_nodes[i].operands[j].type == rcfg_node::ot_nodeval || m_nodes[i].operands[j].type == rcfg_node::ot_nodetgt)
				m_nodes[i].operands[j].id += old_size;
		}
	}

	this->merge_labels(nested, old_size);
}

void rcfg::builder::append_edge(clang::Stmt const * stmt, std::size_t source_node, std::size_t end_node, clang::Stmt const * label)
{
	builder b(m_id_list);
	b.build(stmt);
	this->append_edge(b, source_node, end_node, label);
}

void rcfg::builder::append_edge(builder const & nested, std::size_t source_node, std::size_t end_node, clang::Stmt const * label)
{
	if (nested.m_nodes.empty())
	{
		m_nodes[source_node].succs.push_back(rcfg_node::succ(end_node, label));
		return;
	}

	std::size_t const old_size = m_nodes.size();
	m_nodes.insert(m_nodes.end(), nested.m_nodes.begin(), nested.m_nodes.end());
	std::size_t const new_size = m_nodes.size();

	for (std::size_t i = 0; i < old_size; ++i)
	{
		for (std::size_t j = 0; j < m_nodes[i].succs.size(); ++j)
		{
			if (m_nodes[i].succs[j].id == old_size)
				m_nodes[i].succs[j].id = new_size;
		}
	}
	if (end_node == old_size)
		end_node = new_size;

	for (std::size_t i = old_size; i < m_nodes.size(); ++i)
	{
		for (std::size_t j = 0; j < m_nodes[i].succs.size(); ++j)
		{
			if (m_nodes[i].succs[j].id == nested.m_nodes.size())
				m_nodes[i].succs[j].id = end_node;
			else
				m_nodes[i].succs[j].id += old_size;
			for (std::size_t j = 0; j < m_nodes[i].operands.size(); ++j)
			{
				if (m_nodes[i].operands[j].type == rcfg_node::ot_nodeval || m_nodes[i].operands[j].type == rcfg_node::ot_nodetgt)
					m_nodes[i].operands[j].id += old_size;
			}
		}
	}

	m_nodes[source_node].succs.push_back(rcfg_node::succ(old_size, label));

	this->merge_labels(nested, old_size);
}

rcfg_node::operand rcfg::builder::access_var(clang::ValueDecl const * decl)
{
	// TODO: canonical?
	if (decl->getType()->isReferenceType())
	{
		return this->add_node(rcfg_node()
			(rcfg_node::ot_function, m_id_list("*"))
			(rcfg_node::ot_varval, m_id_list(decl)));
	}

	return rcfg_node::operand(rcfg_node::ot_varval, m_id_list(decl));
}

rcfg_node::operand rcfg::builder::make_deref(rcfg_node::operand var)
{
	var = this->make_rvalue(var);
	switch (var.type)
	{
	case rcfg_node::ot_varptr:
		var.type = rcfg_node::ot_varval;
		return var;
	case rcfg_node::ot_varval:
		var.type = rcfg_node::ot_vartgt;
		return var;
	case rcfg_node::ot_nodeval:
		var.type = rcfg_node::ot_nodetgt;
		return var;
	default:
		BOOST_ASSERT(0);
		return var;
	}
}

rcfg_node::operand rcfg::builder::make_address(rcfg_node::operand var)
{
	switch (var.type)
	{
	case rcfg_node::ot_vartgt:
		var.type = rcfg_node::ot_varval;
		return var;
	case rcfg_node::ot_varval:
		var.type = rcfg_node::ot_varptr;
		return var;
	case rcfg_node::ot_nodetgt:
		var.type = rcfg_node::ot_nodeval;
		return var;
	case rcfg_node::ot_function:
		return var;
	case rcfg_node::ot_nodeval:
	case rcfg_node::ot_varptr:
	case rcfg_node::ot_const:
		return this->make_address(this->make_temporary(var));
	default:
		BOOST_ASSERT(0);
		return var;
	}
}

rcfg_node::operand rcfg::builder::make_rvalue(rcfg_node::operand var)
{
	if (var.type == rcfg_node::ot_vartgt)
	{
		var.type = rcfg_node::ot_varval;
		return this->add_node(rcfg_node()
			(rcfg_node::ot_function, m_id_list("*"))
			(var));
	}

	if (var.type == rcfg_node::ot_nodetgt)
	{
		var.type = rcfg_node::ot_nodeval;
		return this->add_node(rcfg_node()
			(rcfg_node::ot_function, m_id_list("*"))
			(var));
	}

	return var;
}

std::size_t rcfg::builder::make_node(rcfg_node::operand const & var)
{
	rcfg_node::operand rv = this->make_rvalue(var);
	switch (rv.type)
	{
	case rcfg_node::ot_nodeval:
		return rv.id;

	case rcfg_node::ot_varval:
	case rcfg_node::ot_varptr:
		return this->add_node(rcfg_node(node_t::nt_value)(rv)).id;

	default:
		BOOST_ASSERT(0);
	}
}

rcfg_node::operand rcfg::builder::make_param(rcfg_node::operand const & op, clang::Type const * type)
{
	if (type->isReferenceType() || type->isClassType())
		return this->make_rvalue(this->make_address(op));
	else
		return this->make_rvalue(op);
}

rcfg_node::operand rcfg::builder::make_temporary(rcfg_node::operand op)
{
	std::size_t temp_id = m_id_list.make_temporary(0);
	this->construct_object(op_t(node_t::ot_varptr, temp_id), op);
	return op_t(node_t::ot_varval, temp_id);
}

void rcfg::builder::construct_object(op_t dest, op_t val)
{
	node_t node;
	node(node_t::ot_function, m_id_list("="));
	node(dest);
	node(val);
	this->add_node(node);
}

//==================================================================
rcfg::rcfg(clang::FunctionDecl const & fn)
	: m_id_list(fn, fn.getASTContext()), m_fn(fn)
{
	builder b(m_id_list, fn.getBody());
	b.fix_function();
	m_nodes = b.m_nodes;
}

void rcfg::xml_print(std::ostream & out, clang::SourceManager const * sm) const
{
	xml_printer p(out, m_id_list.decl_names(), sm);

	out << "<rcfg name=\"";
	p.xml_print_decl_name(&m_fn);
	out << "\" startnode=\"0\" endnode=\"" << m_nodes.size() << "\">";

	out << "<locals>";
	for (std::size_t i = 0; i < m_id_list.locals().size(); ++i)
		out << "<sym>" << xml_escape(m_id_list.name(m_id_list.locals()[i])) << "</sym>";
	out << "</locals>";

	for (std::size_t i = 0; i < m_nodes.size(); ++i)
	{
		rcfg_node const & node = m_nodes[i];
		BOOST_ASSERT(node.break_type == rcfg_node::bt_none);

		static char const * operand_type_names[] = { "none", "function", "member", "const", "varptr", "varval", "vartgt", "nodeval", "nodetgt" };
		static char const * node_type_names[] = { "none", "call", "value", "phi" };

		out << "<node id=\"" << i << "\" type=\"" << node_type_names[node.type] << "\">";
		for (std::size_t j = 0; j < node.operands.size(); ++j)
		{
			switch (node.operands[j].type)
			{
			case node_t::ot_function:
			case node_t::ot_member:
			case node_t::ot_const:
			case node_t::ot_varptr:
			case node_t::ot_varval:
				out << "<op type=\"" << operand_type_names[node.operands[j].type] << "\" val=\"" << xml_escape(m_id_list.name(node.operands[j].id)) << "\"/>";
				break;
			case node_t::ot_nodeval:
				out << "<op type=\"" << operand_type_names[node.operands[j].type] << "\" val=\"" << node.operands[j].id << "\"/>";
				break;
			default:
				BOOST_ASSERT(0);
			}
		}

		if (node.stmt)
		{
			out << "<ast>";
			p.xml_print_statement(node.stmt);
			out << "</ast>";
		}

		for (std::size_t j = 0; j < node.succs.size(); ++j)
		{
			out << "<next nodeid=\"" << node.succs[j].id << "\">";
			if (node.succs[j].label)
			{
				p.xml_print_statement(node.succs[j].label);
			}
			else
			{
				op_t label_op = node.succs[j].op;
				if (label_op.type == node_t::ot_none)
				{
					out << "<default/>";
				}
				else
				{
					BOOST_ASSERT(label_op.type == node_t::ot_const);
					out << "<intConst>";
					out << boost::lexical_cast<long long>(m_id_list.name(label_op.id));
					out << "</intConst>";
				}
			}
			out << "</next>";
		}

		out << "</node>";
	}
	out << "<node id=\"" << m_nodes.size() << "\"><ast>";
	p.xml_print_tag("exit", m_fn.getSourceRange().getEnd(), "/");
	out << "</ast></node></rcfg>";
}

void rcfg::print_op(std::ostream & out, op_t op) const
{
	switch (op.type)
	{
	case rcfg_node::ot_function:
		out << "func " << m_id_list.name(op.id);
		break;
	case rcfg_node::ot_member:
		out << "memb " << m_id_list.name(op.id);
		break;
	case rcfg_node::ot_vartgt:
		out << "vart " << m_id_list.name(op.id);
		break;
	case rcfg_node::ot_varval:
		out << "varv " << m_id_list.name(op.id);
		break;
	case rcfg_node::ot_varptr:
		out << "varp " << m_id_list.name(op.id);
		break;
	case rcfg_node::ot_nodeval:
		out << "node " << op.id;
		break;
	case rcfg_node::ot_nodetgt:
		out << "nodt " << op.id;
		break;
	case rcfg_node::ot_const:
		out << "cons " << m_id_list.name(op.id);
		break;
	}
}

void rcfg::pretty_print(std::ostream & out, clang::SourceManager const * sm) const
{
	xml_printer p(out, m_id_list.decl_names(), sm);

	out << "def " << make_decl_name(&m_fn) << ":\n";

	for (std::size_t i = 0; i < m_id_list.locals().size(); ++i)
		out << "    var: " << m_id_list.name(m_id_list.locals()[i]) << "\n";

	for (std::size_t i = 0; i < m_nodes.size(); ++i)
	{
		rcfg_node const & node = m_nodes[i];
		BOOST_ASSERT(node.break_type == rcfg_node::bt_none);

		out << "    node " << i << " ";
		switch (node.type)
		{
		case node_t::nt_none:
			out << "nt_none ";
			break;
		case node_t::nt_call:
			out << "nt_call ";
			break;
		case node_t::nt_value:
			out << "nt_value ";
			break;
		case node_t::nt_phi:
			out << "nt_phi ";
			break;
		}

		p.xml_print_statement(node.stmt);
		out << "\n";

		for (std::size_t j = 0; j < node.operands.size(); ++j)
		{
			out << "        ";
			this->print_op(out, node.operands[j]);
			out << "\n";
		}

		for (std::size_t j = 0; j < node.succs.size(); ++j)
		{
			out << "        succ " << node.succs[j].id;
			if (node.succs[j].op.type != node_t::ot_none)
			{
				out << " ";
				this->print_op(out, node.succs[j].op);
			}
			out << "\n";
		}
	}
	out << std::endl;
}

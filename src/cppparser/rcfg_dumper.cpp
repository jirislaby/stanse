#include "rcfg_dumper.hpp"
#include "ast_dumper.hpp"

#include <clang/AST/Expr.h>
#include <clang/AST/StmtCXX.h>
#include <clang/AST/ExprCXX.h>

#include <boost/assert.hpp>

#include <sstream>

rcfg_unique_namegen::rcfg_unique_namegen(clang::FunctionDecl const & fn)
{
	std::set<std::string> used_names;
	for (clang::FunctionDecl::decl_iterator it = fn.decls_begin(); it != fn.decls_end(); ++it)\
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

			for (std::size_t i = 0;; ++i)
			{
				std::ostringstream ss;
				ss << name_base << i;
				if (used_names.find(ss.str()) == used_names.end())
				{
					used_names.insert(ss.str());
					m_decl_names[d] = ss.str();
					break;
				}
			}
		}
	}
}

std::string rcfg_unique_namegen::get_name(clang::NamedDecl const * decl) const
{
	std::map<clang::NamedDecl const *, std::string>::const_iterator ci = m_decl_names.find(decl);
	if (ci != m_decl_names.end())
		return ci->second;
	else
		return make_decl_name(decl);
}

rcfg_node::operand rcfg::build_expr(clang::Expr const * expr)
{
	typedef rcfg_node::operand opd_t;

	if (clang::ArraySubscriptExpr const * e = llvm::dyn_cast<clang::ArraySubscriptExpr>(expr))
	{
		opd_t lhs = this->build_expr(e->getLHS());
		opd_t rhs = this->build_expr(e->getRHS());
		
		m_nodes.push_back(0);
		m_nodes.back().succs.push_back(m_nodes.size());
		m_nodes.back().operands.push_back(opd_t("[]"));
		m_nodes.back().operands.push_back(lhs);
		m_nodes.back().operands.push_back(rhs);
		return m_nodes.size() - 1;
	}
	else if (clang::BinaryOperator const * e = llvm::dyn_cast<clang::BinaryOperator>(expr))
	{
		opd_t lhs = this->build_expr(e->getLHS());
		opd_t rhs = this->build_expr(e->getRHS());
		
		m_nodes.push_back(0);
		m_nodes.back().succs.push_back(m_nodes.size());
		m_nodes.back().operands.push_back(opd_t(clang::BinaryOperator::getOpcodeStr(e->getOpcode())));
		m_nodes.back().operands.push_back(lhs);
		m_nodes.back().operands.push_back(rhs);
		return m_nodes.size() - 1;
	}
	else if (clang::DeclRefExpr const * e = llvm::dyn_cast<clang::DeclRefExpr>(expr))
	{
		clang::Decl const * decl = e->getDecl();
		if (clang::ValueDecl const * nd = llvm::dyn_cast<clang::ValueDecl>(decl))
		{
			return opd_t(llvm::isa<clang::FunctionDecl>(nd)? opd_t::ot_function: nd->getType()->isReferenceType()? opd_t::ot_varval: opd_t::ot_varptr, nd);
		}
		else
		{
			BOOST_ASSERT(0);
		}
	}
	else if (clang::CallExpr const * e = llvm::dyn_cast<clang::CallExpr>(expr))
	{
		rcfg_node node;
		node.operands.push_back(this->build_expr(e->getCallee()));
		for (std::size_t i = 0; i < e->getNumArgs(); ++i)
			node.operands.push_back(this->build_expr(e->getArg(i)));
		node.succs.push_back(m_nodes.size() + 1);
		m_nodes.push_back(node);
		return m_nodes.size() - 1;
	}
	else if (clang::ImplicitCastExpr const * e = llvm::dyn_cast<clang::ImplicitCastExpr >(expr))
	{
		opd_t op = this->build_expr(e->getSubExpr());
		return op;
	}
	else if (clang::CastExpr const * e = llvm::dyn_cast<clang::CastExpr>(expr))
	{
		opd_t op = this->build_expr(e->getSubExpr());
		return op;
	}
	else if (clang::IntegerLiteral const * e = llvm::dyn_cast<clang::IntegerLiteral>(expr))
	{
		m_nodes.push_back(0);
		m_nodes.back().operands.push_back("c:int:" + e->getValue().toString(10, true));
		m_nodes.back().succs.push_back(m_nodes.size());
		return m_nodes.size() - 1;
	}
	else
	{
		BOOST_ASSERT(0);
	}
}

void rcfg::create_var(rcfg_node::operand op, clang::Expr const * expr)
{
	rcfg_node node;
	node.operands.push_back("=");
	node.operands.push_back(op);
	node.operands.push_back(this->build_expr(expr));
	m_nodes.push_back(node);
}

void rcfg::build(clang::Stmt const * stmt)
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
					rcfg_node node;
					node.operands.push_back(rcfg_node::operand("="));
					node.operands.push_back(rcfg_node::operand(rcfg_node::operand::ot_varptr, vd));
					node.operands.push_back(this->build_expr(vd->getInit()));
					m_nodes.push_back(node);
					m_nodes.back().succs.push_back(m_nodes.size());
				}
			}
		}
		m_nodes.back().stmt = s;
	}
	else if (clang::Expr const * s = llvm::dyn_cast<clang::Expr>(stmt))
	{
		rcfg_node::operand op = this->build_expr(s);
		if (op.type != rcfg_node::operand::ot_node)
		{
			m_nodes.push_back(s);
			m_nodes.back().operands.push_back(op);
		}
		else
		{
			m_nodes[op.node].stmt = s;
		}
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
			this->create_var(opd_t(opd_t::ot_varptr, "ret"), s->getRetValue());
			m_nodes.back().stmt = stmt;
		}
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
		m_nodes.push_back(rcfg_node(s->getCond()));
		this->append_edge(s->getThen(), 0, m_nodes.size());

		if (s->getElse())
			this->append_edge(s->getElse(), 0, m_nodes.size());
		else
			m_nodes[0].succs.push_back(m_nodes.size());
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
		rcfg body_cfg(s->getBody());
		body_cfg.fix(rcfg_node::bt_continue, body_cfg.m_nodes.size());
		if (s->getInc())
			body_cfg.append(s->getInc());

		rcfg loop_cfg;
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
		m_nodes.push_back(0);
		//BOOST_ASSERT(0 && "the statement type is not recognized");
	}
}

void rcfg::fix_function()
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

void rcfg::fix(rcfg_node::break_type_t bt, std::size_t target)
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

void rcfg::merge_labels(rcfg const & nested, std::size_t shift)
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

void rcfg::append(rcfg const & nested)
{
	std::size_t const old_size = m_nodes.size();
	m_nodes.insert(m_nodes.end(), nested.m_nodes.begin(), nested.m_nodes.end());

	for (std::size_t i = old_size; i < m_nodes.size(); ++i)
	{
		for (std::size_t j = 0; j < m_nodes[i].succs.size(); ++j)
			m_nodes[i].succs[j].id += old_size;
		for (std::size_t j = 0; j < m_nodes[i].operands.size(); ++j)
			m_nodes[i].operands[j].node += old_size;
	}

	this->merge_labels(nested, old_size);
}

void rcfg::append_edge(rcfg const & nested, std::size_t source_node, std::size_t end_node, clang::Stmt const * label)
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
				m_nodes[i].operands[j].node += old_size;
		}
	}

	m_nodes[source_node].succs.push_back(rcfg_node::succ(old_size, label));

	this->merge_labels(nested, old_size);
}

void rcfg::xml_print(std::ostream & out, clang::SourceManager const * sm, clang::FunctionDecl const & fn) const
{
	std::map<clang::Decl const *, std::string> decl_names;
	this->make_decl_names(fn, decl_names);
	xml_printer p(out, decl_names, sm);

	out << "<rcfg name=\"";
	p.xml_print_decl_name(&fn);
	out << "\" startnode=\"0\" endnode=\"" << m_nodes.size() << "\">";
	for (std::size_t i = 0; i < m_nodes.size(); ++i)
	{
		rcfg_node const & node = m_nodes[i];
		BOOST_ASSERT(node.break_type == rcfg_node::bt_none);

		out << "<node id=\"" << i << "\">";
		p.xml_print_statement(node.stmt);
		for (std::size_t j = 0; j < node.succs.size(); ++j)
		{
			out << "<next nodeid=\"" << node.succs[j].id << "\">";
			if (node.succs[j].label)
			{
				p.xml_print_statement(node.succs[j].label);
			}
			else
			{
				switch (j)
				{
				case 0:
					out << "<default />";
					break;
				case 1:
					out << "<intConst>0</intConst>";
					break;
				}
			}
			out << "</next>";
		}

		out << "</node>";
	}
	out << "<node id=\"" << m_nodes.size() << "\">";
	p.xml_print_tag("exit", fn.getSourceRange().getEnd(), "/");
	out << "</node></rcfg>";
}

void rcfg::pretty_print(std::ostream & out, clang::SourceManager const * sm, clang::FunctionDecl const & fn) const
{
	rcfg_unique_namegen namegen(fn);
	std::map<clang::Decl const *, std::string> decl_names;
	this->make_decl_names(fn, decl_names);
	xml_printer p(out, decl_names, sm);

	out << "def " << make_decl_name(&fn) << ":\n";
	for (std::size_t i = 0; i < m_nodes.size(); ++i)
	{
		rcfg_node const & node = m_nodes[i];
		BOOST_ASSERT(node.break_type == rcfg_node::bt_none);

		out << "    node " << i << " ";
		p.xml_print_statement(node.stmt);
		out << "\n";

		for (std::size_t j = 0; j < node.operands.size(); ++j)
		{
			typedef rcfg_node::operand opd_t;

			switch (node.operands[j].type)
			{
			case opd_t::ot_name:
				out << "        name " << namegen.get_name(node.operands[j]);
				break;
			case opd_t::ot_function:
				out << "        func " << namegen.get_name(node.operands[j]);
				break;
			case opd_t::ot_member:
				out << "        memb " << namegen.get_name(node.operands[j]);
				break;
			case opd_t::ot_varval:
				out << "        varv " << namegen.get_name(node.operands[j]);
				break;
			case opd_t::ot_varptr:
				out << "        varp " << namegen.get_name(node.operands[j]);
				break;
			case opd_t::ot_node:
				out << "        node " << node.operands[j].node;
				break;
			}

			out << "\n";
		}

		for (std::size_t j = 0; j < node.succs.size(); ++j)
			out << "        succ " << node.succs[j].id << "\n";
	}
	out << std::endl;
}

void rcfg::make_decl_names(clang::FunctionDecl const & fn, std::map<clang::Decl const *, std::string> & decl_names) const
{
	std::set<std::string> used_names;
	for (clang::FunctionDecl::decl_iterator it = fn.decls_begin(); it != fn.decls_end(); ++it)\
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

			for (std::size_t i = 0;; ++i)
			{
				std::ostringstream ss;
				ss << name_base << i;
				if (used_names.find(ss.str()) == used_names.end())
				{
					used_names.insert(ss.str());
					decl_names[decl] = ss.str();
					break;
				}
			}
		}
	}
}

void rcfg::fix_names(rcfg_unique_namegen const & namegen)
{
	for (std::size_t i = 0; i < m_nodes.size(); ++i)
	{
		for (std::size_t j = 0; j < m_nodes[i].operands.size(); ++j)
		{
			if (m_nodes[i].operands[j].decl != 0)
				m_nodes[i].operands[j].name == namegen.get_name(m_nodes[i].operands[j].decl);
		}
	}
}

std::string rcfg_unique_namegen::get_name(rcfg_node::operand const & op) const
{
	if (op.decl != 0)
		return this->get_name(op.decl);
	return op.name;
}

rcfg_node::operand rcfg::access_var(clang::ValueDecl const * decl)
{
	// TODO: canonical?
	if (decl->getType()->isReferenceType())
	{
		rcfg_node node;
		node.operands.push_back("*");
		node.operands.push_back(rcfg_node::operand(rcfg_node::operand::ot_varval, decl));
		m_nodes.push_back(node);
		return rcfg_node::operand(m_nodes.size() - 1);
	}

	return rcfg_node::operand(rcfg_node::operand::ot_varval, decl);
}

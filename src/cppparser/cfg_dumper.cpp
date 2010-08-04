#include "cfg_dumper.hpp"
#include "ast_dumper.hpp"

#include <clang/ast/Expr.h>
#include <clang/ast/StmtCXX.h>
#include <clang/ast/ExprCXX.h>

#include <boost/assert.hpp>

void cfg::build(clang::Stmt const * stmt)
{
	BOOST_ASSERT(m_nodes.empty());
	BOOST_ASSERT(stmt != 0);

	if (llvm::isa<clang::Expr>(stmt) || llvm::isa<clang::AsmStmt>(stmt)
		|| llvm::isa<clang::DeclStmt>(stmt) || llvm::isa<clang::NullStmt>(stmt))
	{
		m_nodes.push_back(stmt);
		m_nodes[0].succs.push_back(1);
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
	else if (isa<clang::ReturnStmt>(stmt))
	{
		m_nodes.push_back(stmt);
		m_nodes[0].break_type = cfg_node::bt_return;
	}
	else if (isa<clang::BreakStmt>(stmt))
	{
		m_nodes.push_back(stmt);
		m_nodes[0].break_type = cfg_node::bt_break;
	}
	else if (isa<clang::ContinueStmt>(stmt))
	{
		m_nodes.push_back(stmt);
		m_nodes[0].break_type = cfg_node::bt_continue;
	}
	else if (isa<clang::GotoStmt>(stmt))
	{
		m_nodes.push_back(stmt);
		m_nodes[0].break_type = cfg_node::bt_goto;
	}
	else if (clang::CompoundStmt const * s = llvm::dyn_cast<clang::CompoundStmt>(stmt))
	{
		for (clang::CompoundStmt::const_body_iterator it = s->body_begin(); it != s->body_end(); ++it)
			this->append(*it);
	}
	else if (clang::IfStmt const * s = llvm::dyn_cast<clang::IfStmt>(stmt))
	{
		m_nodes.push_back(cfg_node(s->getCond()));
		this->append_edge(s->getThen(), 0, m_nodes.size());

		if (s->getElse())
			this->append_edge(s->getElse(), 0, m_nodes.size());
		else
			m_nodes[0].succs.push_back(m_nodes.size());
	}
	else if (clang::SwitchStmt const * s = llvm::dyn_cast<clang::SwitchStmt>(stmt))
	{
		m_nodes.push_back(cfg_node(s->getCond()));
		this->append(s->getBody());
		
		// Resolve case labels
		if (m_default_case.second)
		{
			m_nodes[0].succs.push_back(m_default_case.first);
			m_default_case.second = 0;
		}

		for (std::size_t i = 0; i < m_switch_cases.size(); ++i)
			m_nodes[0].succs.push_back(cfg_node::succ(m_switch_cases[i].first, m_switch_cases[i].second->getLHS()));
		m_switch_cases.clear();

		this->fix(cfg_node::bt_break, m_nodes.size());
	}
	else if (clang::WhileStmt const * s = llvm::dyn_cast<clang::WhileStmt>(stmt))
	{
		m_nodes.push_back(cfg_node(s->getCond()));
		this->append_edge(s->getBody(), 0, 0);
		m_nodes[0].succs.push_back(m_nodes.size());

		this->fix(cfg_node::bt_break, m_nodes.size());
		this->fix(cfg_node::bt_continue, 0);
	}
	else if (clang::DoStmt const * s = llvm::dyn_cast<clang::DoStmt>(stmt))
	{
		this->build(s->getBody());
		m_nodes.push_back(cfg_node(s->getCond()));
		m_nodes[0].succs.push_back(0);
		m_nodes[0].succs.push_back(m_nodes.size());

		this->fix(cfg_node::bt_break, m_nodes.size());
		this->fix(cfg_node::bt_continue, m_nodes.size() - 1);
	}
	else if (clang::ForStmt const * s = llvm::dyn_cast<clang::ForStmt>(stmt))
	{
		cfg body_cfg(s->getBody());
		body_cfg.fix(cfg_node::bt_continue, body_cfg.m_nodes.size());
		if (s->getInc())
			body_cfg.append(s->getInc());

		cfg loop_cfg;
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
		loop_cfg.fix(cfg_node::bt_break, loop_cfg.m_nodes.size());

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
	else
	{
		m_nodes.push_back(0);
		//BOOST_ASSERT(0 && "the statement type is not recognized");
	}
}

void cfg::fix_function()
{
	this->fix(cfg_node::bt_return, m_nodes.size());

	// Resolve gotos
	for (std::size_t i = 0; i < m_nodes.size(); ++i)
	{
		if (m_nodes[i].break_type == cfg_node::bt_goto)
		{
			m_nodes[i].break_type = cfg_node::bt_none;
			std::map<clang::LabelStmt const *, std::size_t>::const_iterator
				ci = m_labels.find(llvm::dyn_cast<clang::GotoStmt>(m_nodes[i].stmt)->getLabel());
			BOOST_ASSERT(ci != m_labels.end());
			m_nodes[i].succs.push_back(ci->second);
		}
	}

	m_labels.clear();
}

void cfg::fix(cfg_node::break_type_t bt, std::size_t target)
{
	for (std::size_t i = 0; i < m_nodes.size(); ++i)
	{
		if (m_nodes[i].break_type == bt)
		{
			m_nodes[i].break_type = cfg_node::bt_none;
			m_nodes[i].succs.push_back(target);
		}
	}
}

void cfg::merge_labels(cfg const & nested, std::size_t shift)
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

void cfg::append(cfg const & nested)
{
	std::size_t const old_size = m_nodes.size();
	m_nodes.insert(m_nodes.end(), nested.m_nodes.begin(), nested.m_nodes.end());

	for (std::size_t i = old_size; i < m_nodes.size(); ++i)
	{
		for (std::size_t j = 0; j < m_nodes[i].succs.size(); ++j)
			m_nodes[i].succs[j].id += old_size;
	}

	this->merge_labels(nested, old_size);
}

void cfg::append_edge(cfg const & nested, std::size_t source_node, std::size_t end_node, clang::Stmt const * label)
{
	if (nested.m_nodes.empty())
	{
		m_nodes[source_node].succs.push_back(cfg_node::succ(end_node, label));
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
		}
	}

	m_nodes[source_node].succs.push_back(cfg_node::succ(old_size, label));

	this->merge_labels(nested, old_size);
}

void cfg::xml_print(std::ostream & out, clang::SourceManager const * sm, clang::FunctionDecl const & fn) const
{
	xml_printer p(out, sm);

	out << "<cfg name=\"";
	p.xml_print_decl_name(&fn);
	out << "\" startnode=\"0\" endnode=\"" << m_nodes.size() << "\">";
	for (std::size_t i = 0; i < m_nodes.size(); ++i)
	{
		cfg_node const & node = m_nodes[i];
		BOOST_ASSERT(node.break_type == cfg_node::bt_none);

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
	out << "</node></cfg>";
}

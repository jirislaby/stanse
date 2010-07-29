#ifndef CFG_DUMPER_HPP
#define CFG_DUMPER_HPP

#include <clang/ast/Stmt.h>

#include <cstdlib>
#include <iostream>
#include <vector>
#include <map>

struct cfg_node
{
	struct succ
	{
		std::size_t id;
		clang::Stmt const * label;

		succ(std::size_t id, clang::Stmt const * label = 0)
			: id(id), label(label)
		{
		}
	};

	clang::Stmt const * stmt;
	std::vector<succ> succs;
	enum break_type_t { bt_none, bt_break, bt_continue, bt_return, bt_goto } break_type;

	cfg_node(clang::Stmt const * stmt = 0)
		: stmt(stmt), break_type(bt_none)
	{
	}
};

class cfg
{
public:
	cfg(clang::Stmt const * stmt)
	{
		this->build(stmt);
	}

	void fix_function();
	void xml_print(std::ostream & out, std::string const & name) const;

private:
	void fix(cfg_node::break_type_t bt, std::size_t target);

	void build(clang::Stmt const * stmt);
	void append(cfg const & nested);
	void append_edge(cfg const & nested, std::size_t source_node, std::size_t end_node, clang::Stmt const * label = 0);

	void merge_labels(cfg const & nested, std::size_t shift);

	// [1] is the exit node, [0] is the entry node.
	std::vector<cfg_node> m_nodes;
	std::map<clang::LabelStmt const *, std::size_t> m_labels;
	std::vector<std::pair<std::size_t, clang::CaseStmt const *> > m_switch_cases;
	std::pair<std::size_t, clang::DefaultStmt const *> m_default_case;
};

template <typename InputIterator>
void print_cfg(std::ostream & fout, InputIterator firstFun, InputIterator lastFun)
{
	fout <<
		"<?xml version=\"1.0\" encoding=\"utf-8\" ?>"
		"<cfgs>";

	for (; firstFun != lastFun; ++firstFun)
	{
		cfg c((*firstFun)->getBody());
		c.fix_function();
		c.xml_print(fout, (*firstFun)->getQualifiedNameAsString());
	}

	fout << "</cfgs>\n";
}

#endif

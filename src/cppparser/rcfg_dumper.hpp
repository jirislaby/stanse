#ifndef RCFG_DUMPER_HPP
#define RFG_DUMPER_HPP

#include <clang/AST/Stmt.h>
#include <clang/Analysis/CFG.h>

#include <cstdlib>
#include <iostream>
#include <vector>
#include <map>

struct rcfg_node
{
	struct operand
	{
		enum operand_type { ot_name, ot_function, ot_member, ot_varval, ot_varptr, ot_node } type;

		std::string name;
		clang::NamedDecl const * decl;
		std::size_t node;

		operand(std::size_t node)
			: type(ot_node), decl(0), node(node)
		{
		}

		operand(char const * name)
			: type(ot_name), name(name), decl(0), node(0)
		{
		}

		operand(std::string const & name)
			: type(ot_name), name(name), decl(0), node(0)
		{
		}

		operand(operand_type type, std::string const & name)
			: type(type), name(name), decl(0), node(0)
		{
		}

		operand(operand_type type, clang::NamedDecl const * decl)
			: type(type), decl(decl), node(0)
		{
		}
	};

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
	std::vector<operand> operands;
	std::vector<succ> succs;
	enum break_type_t { bt_none, bt_break, bt_continue, bt_return, bt_goto } break_type;

	rcfg_node(clang::Stmt const * stmt = 0)
		: stmt(stmt), break_type(bt_none)
	{
	}
};

struct rcfg_unique_namegen
{
public:
	explicit rcfg_unique_namegen(clang::FunctionDecl const & fn);
	std::string get_name(clang::NamedDecl const * decl) const;
	std::string get_name(rcfg_node::operand const & op) const;

private:
	std::map<clang::NamedDecl const *, std::string> m_decl_names;
};

class rcfg
{
public:
	rcfg()
	{
	}

	rcfg(clang::Stmt const * stmt)
	{
		this->build(stmt);
	}

	void fix_function();
	void xml_print(std::ostream & out, clang::SourceManager const * sm, clang::FunctionDecl const & fn) const;
	void pretty_print(std::ostream & out, clang::SourceManager const * sm, clang::FunctionDecl const & fn) const;

private:
	void make_decl_names(clang::FunctionDecl const & fn, std::map<clang::Decl const *, std::string> & decl_names) const;

	void fix_names(rcfg_unique_namegen const & namegen);
	void fix(rcfg_node::break_type_t bt, std::size_t target);

	void build(clang::Stmt const * stmt);
	rcfg_node::operand build_expr(clang::Expr const * expr);
	void create_var(rcfg_node::operand op, clang::Expr const * expr);
	rcfg_node::operand access_var(clang::ValueDecl const * decl);

	void append(rcfg const & nested);
	void append_edge(rcfg const & nested, std::size_t source_node, std::size_t end_node, clang::Stmt const * label = 0);

	void merge_labels(rcfg const & nested, std::size_t shift);

	// [1] is the exit node, [0] is the entry node.
	std::vector<rcfg_node> m_nodes;
	std::map<clang::LabelStmt const *, std::size_t> m_labels;
	std::vector<std::pair<std::size_t, clang::CaseStmt const *> > m_switch_cases;
	std::pair<std::size_t, clang::DefaultStmt const *> m_default_case;
};

template <typename InputIterator>
void print_rcfg(clang::ASTContext & ctx, std::ostream & fout, clang::SourceManager const * sm, InputIterator firstFun, InputIterator lastFun)
{
	fout <<
		"<?xml version=\"1.0\" encoding=\"utf-8\" ?>"
		"<cfgs>";

	for (; firstFun != lastFun; ++firstFun)
	{
		{
			clang::CFG * cfg = clang::CFG::buildCFG(*firstFun, (*firstFun)->getBody(), &ctx);
			//cfg->dump(clang::LangOptions());
			delete cfg;
		}

		rcfg c((*firstFun)->getBody());
		c.fix_function();
		c.xml_print(fout, sm, **firstFun);
	}

	fout << "</cfgs>\n";
}

template <typename InputIterator>
void print_debug_rcfg(clang::ASTContext & ctx, std::ostream & fout, clang::SourceManager const * sm, InputIterator firstFun, InputIterator lastFun)
{
	for (; firstFun != lastFun; ++firstFun)
	{
		{
			clang::CFG * cfg = clang::CFG::buildCFG(*firstFun, (*firstFun)->getBody(), &ctx);
			//cfg->dump(clang::LangOptions());
			delete cfg;
		}

		rcfg c((*firstFun)->getBody());
		c.fix_function();
		c.pretty_print(fout, sm, **firstFun);
	}
}

#endif

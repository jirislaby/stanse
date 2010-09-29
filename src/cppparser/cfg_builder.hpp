#ifndef CPPPARSER_CFG_BUILDER_HPP
#define CPPPARSER_CFG_BUILDER_HPP

#include "cfg.hpp"
#include "util.hpp"
#include <clang/AST/Decl.h>
#include <string>
#include <utility>

struct default_build_visitor
{
	bool function_started(std::string const &) {}
	void statement_visited(clang::Stmt const *) {}
	void function_completed(std::string const &, cfg const &) {}
};

namespace detail {

struct build_cfg_visitor_base
{
	virtual void statement_visited(clang::Stmt const * stmt) = 0;
};

template <typename Visitor>
struct build_cfg_visitor : build_cfg_visitor_base
{
	build_cfg_visitor(Visitor const & visitor)
		: m_visitor(visitor)
	{
	}

	void statement_visited(clang::Stmt const * stmt)
	{
		m_visitor.statement_visited(stmt);
	}

	Visitor m_visitor;
};

void build_cfg(cfg & c, clang::FunctionDecl const * fn, build_cfg_visitor_base & visitor, std::string const & static_prefix);

}

template <typename Visitor>
program build_program(clang::TranslationUnitDecl const * tu, Visitor visitor, std::string const & static_prefix)
{
	std::set<clang::FunctionDecl const *> unprocessedFunctions;
	get_functions_from_declcontext(tu, unprocessedFunctions);

	program res;
	while (!unprocessedFunctions.empty())
	{
		clang::FunctionDecl const * fn = *unprocessedFunctions.begin();
		unprocessedFunctions.erase(unprocessedFunctions.begin());

		if (!fn->hasBody())
			continue;

		std::string const & fnname = make_decl_name(fn, static_prefix);
		if (!visitor.function_started(fnname))
			continue;

		cfg c;
		detail::build_cfg_visitor<Visitor> cfg_visitor(visitor);
		build_cfg(c, fn, cfg_visitor, static_prefix);

		visitor.function_completed(fnname, c);
		res.cfgs().insert(std::make_pair(fnname, c));
	}
	return res;
}

inline program build_program(clang::TranslationUnitDecl const * tu, std::string const & static_prefix)
{
	return build_program(tu, default_build_visitor(), static_prefix);
}

#endif

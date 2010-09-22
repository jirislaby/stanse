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

void build_cfg(cfg & c, clang::FunctionDecl const * fn, std::set<clang::FunctionDecl const *> & referenced_functions, build_cfg_visitor_base & visitor);

}

template <typename Visitor>
program build_program(clang::TranslationUnitDecl const * tu, Visitor visitor)
{
	std::set<clang::FunctionDecl const *> unprocessedFunctions;
	get_functions_from_declcontext(tu, unprocessedFunctions);

	std::set<clang::FunctionDecl const *> processedFunctions;
	program res;
	while (!unprocessedFunctions.empty())
	{
		clang::FunctionDecl const * fn = *unprocessedFunctions.begin();
		unprocessedFunctions.erase(unprocessedFunctions.begin());

		if (!fn->hasBody())
			continue;

		BOOST_ASSERT(processedFunctions.find(fn) == processedFunctions.end());
		processedFunctions.insert(fn);

		std::string const & fnname = make_decl_name(fn);
		if (!visitor.function_started(fnname))
			continue;

		std::set<clang::FunctionDecl const *> referenced_functions;

		cfg c;
		detail::build_cfg_visitor<Visitor> cfg_visitor(visitor);
		build_cfg(c, fn, referenced_functions, cfg_visitor);

		visitor.function_completed(fnname, c);
		res.cfgs().insert(std::make_pair(fnname, c));

		std::set_difference(
			referenced_functions.begin(), referenced_functions.end(),
			processedFunctions.begin(), processedFunctions.end(),
			std::inserter(unprocessedFunctions, unprocessedFunctions.begin()));
	}
	return res;
}

inline program build_program(clang::TranslationUnitDecl const * tu)
{
	return build_program(tu, default_build_visitor());
}

#endif

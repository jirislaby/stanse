#ifndef CPPPARSER_CFG_BUILDER_HPP
#define CPPPARSER_CFG_BUILDER_HPP

#include "cfg.hpp"
#include "util.hpp"
#include <clang/AST/Decl.h>
#include <string>
#include <utility>

struct default_build_visitor
{
	void function_started(std::string const &) {}
	void function_completed(std::string const &, cfg const &) {}
};

void build_cfg(cfg & c, clang::FunctionDecl const * fn, std::set<clang::FunctionDecl const *> & referenced_functions);

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

		BOOST_ASSERT(processedFunctions.find(fn) == processedFunctions.end());

		std::string const & fnname = make_decl_name(fn);
		visitor.function_started(fnname);

		std::set<clang::FunctionDecl const *> referenced_functions;

		cfg c;
		build_cfg(c, fn, referenced_functions);

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

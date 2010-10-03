#ifndef CPPPARSER_CFG_BUILDER_HPP
#define CPPPARSER_CFG_BUILDER_HPP

#include "cfg.hpp"
#include "unit_navigator.hpp"
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

void build_cfg(cfg & c, clang::FunctionDecl const * fn, clang::SourceManager const & sm,
	filename_store & fnames, build_cfg_visitor_base & visitor, std::string const & static_prefix);

}

template <typename Visitor>
program build_program(unit_navigator const & un, clang::SourceManager const & sm,
	Visitor visitor, std::string const & static_prefix)
{
	program res;
	res.vfn_map(un.vfn_map());
	res.vfn_param_counts(un.vfn_param_counts());
	for (unit_navigator::fns_const_iterator it = un.fns_begin(); it != un.fns_end(); ++it)
	{
		clang::FunctionDecl const * fn = *it;

		if (!fn->hasBody())
			continue;

		std::string const & fnname = make_decl_name(fn, static_prefix);
		if (!visitor.function_started(fnname))
			continue;

		cfg c;
		detail::build_cfg_visitor<Visitor> cfg_visitor(visitor);
		build_cfg(c, fn, sm, res.fnames(), cfg_visitor, static_prefix);

		visitor.function_completed(fnname, c);
		res.add_cfg(fnname, c);
	}
	return res;
}

inline program build_program(unit_navigator const & un,
	clang::SourceManager const & sm, std::string const & static_prefix)
{
	return build_program(un, sm, default_build_visitor(), static_prefix);
}

#endif

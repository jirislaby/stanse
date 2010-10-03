#ifndef CPPPARSER_UNIT_NAVIGATOR_HPP
#define CPPPARSER_UNIT_NAVIGATOR_HPP

#include <clang/AST/Decl.h>

#include <string>
#include <set>
#include <map>

class unit_navigator
{
public:
	explicit unit_navigator(clang::TranslationUnitDecl const * tu)
	{
		this->build(tu);
	}

	void filter(std::string const & prefix);

	typedef std::set<clang::FunctionDecl const *>::const_iterator fns_const_iterator;
	fns_const_iterator fns_begin() const { return m_fns.begin(); }
	fns_const_iterator fns_end() const { return m_fns.end(); }

	std::map<std::string, std::size_t> const & vfn_param_counts() const { return m_vfn_param_count; }
	std::multimap<std::string, std::string> const & vfn_map() const { return m_vfns; }

private:
	void build(clang::DeclContext const * declctx);

	std::set<clang::FunctionDecl const *> m_fns;
	std::multimap<std::string, std::string> m_vfns;
	std::map<std::string, std::size_t> m_vfn_param_count;
};


#endif

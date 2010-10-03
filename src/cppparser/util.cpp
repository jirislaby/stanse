#include "util.hpp"
#include <clang/AST/DeclCXX.h>
#include <clang/AST/DeclTemplate.h>
#include <boost/assert.hpp>

namespace {

std::string make_declctx_name(clang::DeclContext const * declctx, std::string const & static_prefix)
{
	BOOST_ASSERT(declctx);

	if (clang::LinkageSpecDecl const * lsdecl = llvm::dyn_cast<clang::LinkageSpecDecl>(declctx))
	{
		if (lsdecl->getLanguage() == clang::LinkageSpecDecl::lang_c)
			return "__externc::";

		if (lsdecl->getLanguage() == clang::LinkageSpecDecl::lang_cxx)
			return make_declctx_name(lsdecl->getDeclContext(), static_prefix);

		return "__externlang::";
	}

	clang::NamedDecl const * decl = llvm::dyn_cast<clang::NamedDecl>(declctx);
	return decl? make_decl_name(decl, static_prefix) + "::": "";
}

}

std::string make_decl_name(clang::NamedDecl const * decl, std::string const & static_prefix)
{
	BOOST_ASSERT(decl);
	if (clang::VarDecl const * vardecl = llvm::dyn_cast<clang::VarDecl>(decl))
	{
		if (vardecl->getLinkage() == clang::InternalLinkage)
			return make_declctx_name(vardecl->getDeclContext(), static_prefix) + static_prefix + "::static::" + vardecl->getNameAsString();

		if (vardecl->isStaticLocal())
			return make_declctx_name(vardecl->getDeclContext(), static_prefix) + vardecl->getNameAsString();
	}
	else if (clang::FunctionDecl const * fndecl = llvm::dyn_cast<clang::FunctionDecl>(decl))
	{
		std::string name;
		if (fndecl->getLinkage() == clang::InternalLinkage)
			name = make_declctx_name(fndecl->getDeclContext(), static_prefix) + static_prefix + "::static::" + fndecl->getNameAsString();
		else
			name = make_declctx_name(fndecl->getDeclContext(), static_prefix) + fndecl->getNameAsString();

		name += '(';
		for (clang::FunctionDecl::param_const_iterator param_ci = fndecl->param_begin(); param_ci != fndecl->param_end(); ++param_ci)
		{
			if (param_ci != fndecl->param_begin())
				name += ", ";
			clang::VarDecl const * param_decl = *param_ci;
			name += static_cast<clang::QualType>(param_decl->getType()->getCanonicalTypeUnqualified()).getAsString();
		}
		name += ')';

		if (clang::CXXMethodDecl const * cxxfndecl = llvm::dyn_cast<clang::CXXMethodDecl>(fndecl))
		{
			if (cxxfndecl->getTypeQualifiers() & clang::Qualifiers::Const)
				name += " const";
			if (cxxfndecl->getTypeQualifiers() & clang::Qualifiers::Volatile)
				name += " volatile";
		}
		return name;
	}
	else if (clang::NamespaceDecl const * nsdecl = llvm::dyn_cast<clang::NamespaceDecl>(decl))
	{
		if (nsdecl->isAnonymousNamespace())
			return make_declctx_name(nsdecl->getDeclContext(), static_prefix) + static_prefix;
	}

	return make_declctx_name(decl->getDeclContext(), static_prefix) + decl->getNameAsString();
}

#include "util.hpp"
#include <clang/AST/DeclCXX.h>
#include <clang/AST/DeclTemplate.h>
#include <boost/assert.hpp>

void get_functions_from_declcontext(clang::DeclContext const * declctx, std::set<clang::FunctionDecl const *> & fns)
{
	for (clang::DeclContext::decl_iterator it = declctx->decls_begin(); it != declctx->decls_end(); ++it)
	{
		clang::Decl * decl = *it;

#ifndef NDEBUG
		std::string name;
		if (clang::NamedDecl const * nd = llvm::dyn_cast<clang::NamedDecl>(decl))
			name = nd->getQualifiedNameAsString();
#endif

		if (clang::NamespaceDecl * nsdecl = dyn_cast<clang::NamespaceDecl>(decl))
		{
			get_functions_from_declcontext(nsdecl, fns);
		}
		else if (clang::FunctionDecl * fnDecl = dyn_cast<clang::FunctionDecl>(decl))
		{
			if (fnDecl->isDependentContext())
				continue;
			fns.insert(fnDecl);
		}
		else if (clang::RecordDecl const * classDecl = dyn_cast<clang::RecordDecl>(decl))
		{
			get_functions_from_declcontext(classDecl, fns);
		}
		else if (clang::FunctionTemplateDecl const * d = llvm::dyn_cast<clang::FunctionTemplateDecl>(decl))
		{
			for (clang::FunctionTemplateDecl::spec_iterator it = ((clang::FunctionTemplateDecl *)d)->spec_begin(); it != ((clang::FunctionTemplateDecl *)d)->spec_end(); ++it)
			{
				if ((*it)->isDependentContext())
					continue;
				fns.insert(*it);
			}
		}
		else if (clang::ClassTemplateDecl const * d = llvm::dyn_cast<clang::ClassTemplateDecl>(decl))
		{
			for (clang::ClassTemplateDecl::spec_iterator it = ((clang::ClassTemplateDecl *)d)->spec_begin(); it != ((clang::ClassTemplateDecl *)d)->spec_end(); ++it)
				get_functions_from_declcontext(*it, fns);
		}
		else if (clang::LinkageSpecDecl const * lsdecl = llvm::dyn_cast<clang::LinkageSpecDecl>(decl))
		{
			get_functions_from_declcontext(lsdecl, fns);
		}
	}
}

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

#include "util.hpp"
#include <clang/AST/DeclCXX.h>
#include <clang/AST/DeclTemplate.h>
#include <boost/assert.hpp>

void get_functions_from_declcontext(clang::DeclContext const * declctx, std::set<clang::FunctionDecl const *> & fns)
{
	for (clang::DeclContext::decl_iterator it = declctx->decls_begin(); it != declctx->decls_end(); ++it)
	{
		clang::Decl * decl = *it;

		if (clang::NamedDecl const * nd = llvm::dyn_cast<clang::NamedDecl>(decl))
		{
			std::string name = nd->getQualifiedNameAsString();
			name = name;
		}

		if (clang::FunctionDecl * fnDecl = dyn_cast<clang::FunctionDecl>(decl))
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
	}
}

std::string make_decl_name(clang::NamedDecl const * decl)
{
	if (clang::VarDecl const * vardecl = llvm::dyn_cast<clang::VarDecl>(decl))
	{
		if (vardecl->isStaticLocal())
		{
			return make_decl_name(llvm::dyn_cast<clang::NamedDecl>(vardecl->getDeclContext())) + "::" + vardecl->getQualifiedNameAsString();
		}
		else
		{
			return vardecl->getQualifiedNameAsString();
		}
	}
	else if (clang::FunctionDecl const * fndecl = llvm::dyn_cast<clang::FunctionDecl>(decl))
	{
		std::string name = decl->getQualifiedNameAsString();
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
	else
		return decl->getQualifiedNameAsString();
}

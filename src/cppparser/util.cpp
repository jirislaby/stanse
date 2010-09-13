#include "util.hpp"
#include <clang/AST/DeclCXX.h>

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
		else if (clang::CXXRecordDecl const * classDecl = dyn_cast<clang::CXXRecordDecl>(decl))
		{
			/*if (classDecl->hasDeclaredDefaultConstructor() && !classDecl->getDefaultConstructor()->isTrivial())
				fns.insert(classDecl->getDefaultConstructor());
			if (classDecl->hasDeclaredCopyConstructor() && !classDecl->getCopyConstructor()->isTrivial())
				fns.insert(classDecl->getCopyConstructor());
			if (classDecl->hasDeclaredCopyAssignment() && !classDecl->getCopyAssignmentOperator()->isTrivial())
				fns.insert(classDecl->getCopyAssignmentOperator());*/
			if (classDecl->hasDefinition() && classDecl->hasDeclaredDestructor() && !classDecl->getDestructor()->isTrivial())
				fns.insert(classDecl->getDestructor());
			get_functions_from_declcontext(classDecl, fns);
		}
		else if (clang::RecordDecl const * classDecl = dyn_cast<clang::RecordDecl>(decl))
		{
			get_functions_from_declcontext(classDecl, fns);
		}
	}
}

std::string make_decl_name(clang::NamedDecl const * decl)
{
	if (clang::FunctionDecl const * fndecl = llvm::dyn_cast<clang::FunctionDecl>(decl))
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

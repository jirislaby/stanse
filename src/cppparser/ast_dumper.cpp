#include "ast_dumper.hpp"

#include <clang/AST/DeclCXX.h>
#include <clang/AST/DeclTemplate.h>

static void process_decl_context(clang::DeclContext const * declctx, std::vector<clang::FunctionDecl const *> & fns)
{
	for (clang::DeclContext::decl_iterator it = declctx->decls_begin(); it != declctx->decls_end(); ++it)
	{
		clang::Decl * decl = *it;
		if (clang::CXXMethodDecl * fnDecl = dyn_cast<clang::CXXMethodDecl>(decl))
		{
			if (fnDecl->isDependentContext())
				continue;

			clang::CXXRecordDecl const * par = fnDecl->getParent();
			if (par->getDescribedClassTemplate() != 0)
				continue;

			if (fnDecl->isThisDeclarationADefinition())
				fns.push_back(fnDecl);
		}
		else if (clang::TemplateDecl const * classDecl = dyn_cast<clang::TemplateDecl>(decl))
		{
			continue;
		}
		else if (clang::FunctionDecl * fnDecl = dyn_cast<clang::FunctionDecl>(decl))
		{
			if (fnDecl->isThisDeclarationADefinition())
				fns.push_back(fnDecl);
		}
		else if (clang::RecordDecl const * classDecl = dyn_cast<clang::RecordDecl>(decl))
		{
			process_decl_context(classDecl, fns);
		}
	}
}

void get_used_function_defs(clang::ASTContext const & ctx, std::set<clang::FunctionDecl const *> & functionDecls)
{
	std::vector<clang::FunctionDecl const *> unprocessedDecls;

	clang::DeclContext * declctx = ctx.getTranslationUnitDecl();
	process_decl_context(declctx, unprocessedDecls);

	while (!unprocessedDecls.empty())
	{
		clang::FunctionDecl const * decl = unprocessedDecls.back();
		unprocessedDecls.pop_back();
		if (functionDecls.insert(decl).second)
		{
			get_referenced_functions(decl->getBody(), std::back_inserter(unprocessedDecls));
		}
	}
}

#include "ast_dumper.hpp"

void get_used_function_defs(clang::ASTContext const & ctx, std::set<clang::FunctionDecl const *> & functionDecls)
{
	std::vector<clang::FunctionDecl const *> unprocessedDecls;

	clang::DeclContext * declctx = ctx.getTranslationUnitDecl();
	for (clang::DeclContext::decl_iterator it = declctx->decls_begin(); it != declctx->decls_end(); ++it)
	{
		clang::Decl * decl = *it;
		if (clang::FunctionDecl * fnDecl = dyn_cast<clang::FunctionDecl>(decl))
		{
			if (fnDecl->hasBody())
				unprocessedDecls.push_back(fnDecl);
		}
	}

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

#include "ast_pretty_printer.hpp"
#include "util.hpp"

#include <clang/AST/ASTContext.h>
#include <clang/AST/Decl.h>
#include <clang/AST/DeclTemplate.h>

namespace {

void indent(std::ostream & out, int level)
{
	for (; level > 0; --level)
		out << "    ";
}

}

void pretty_print_decl(clang::Decl const * decl, std::ostream & out, int level)
{
	indent(out, level);
	out << decl->getDeclKindName();
	if (clang::NamedDecl const * namedDecl = llvm::dyn_cast<clang::NamedDecl>(decl))
	{
		out << ": " << namedDecl->getQualifiedNameAsString().data();
	}
	if (clang::ValueDecl const * valueDecl = llvm::dyn_cast<clang::ValueDecl>(decl))
	{
		out << ": " << valueDecl->getType().getAsString();
		out << ": " << valueDecl->getType().getTypePtr()->getTypeClassName();
	}
	out << '\n';

	if (clang::DeclContext const * d = llvm::dyn_cast<clang::DeclContext>(decl))
	{
		for (clang::DeclContext::decl_iterator it = d->decls_begin(); it != d->decls_end(); ++it)
		{
			clang::Decl * decl = *it;
			pretty_print_decl(decl, out, level + 1);
		}
	}

	if (clang::FunctionTemplateDecl const * d = llvm::dyn_cast<clang::FunctionTemplateDecl>(decl))
	{
		pretty_print_decl(d->getTemplatedDecl(), out, level + 1);
		for (clang::FunctionTemplateDecl::spec_iterator it = ((clang::FunctionTemplateDecl *)d)->spec_begin(); it != ((clang::FunctionTemplateDecl *)d)->spec_end(); ++it)
			pretty_print_decl(*it, out, level + 1);
	}

	if (clang::ClassTemplateDecl const * d = llvm::dyn_cast<clang::ClassTemplateDecl>(decl))
	{
		pretty_print_decl(d->getTemplatedDecl(), out, level + 1);
		for (clang::ClassTemplateDecl::spec_iterator it = ((clang::ClassTemplateDecl *)d)->spec_begin(); it != ((clang::ClassTemplateDecl *)d)->spec_end(); ++it)
			pretty_print_decl(*it, out, level + 1);
	}

	if (clang::FunctionDecl const * funcDecl = llvm::dyn_cast<clang::FunctionDecl>(decl))
	{
		if (clang::CXXConstructorDecl const * conDecl = llvm::dyn_cast<clang::CXXConstructorDecl>(funcDecl))
		{
			for (clang::CXXConstructorDecl::init_const_iterator it = conDecl->init_begin(); it != conDecl->init_end(); ++it)
			{
				clang::CXXBaseOrMemberInitializer const * init = *it;
				if (!init->isBaseInitializer())
				{
					indent(out, level + 1);
					out << "init member:\n";
					pretty_print_decl(init->getMember(), out, level + 2);
					if (init->getMember()->isAnonymousStructOrUnion())
						pretty_print_decl(init->getAnonUnionMember(), out, level + 2);
				}
				else
				{
					indent(out, level + 1);
					out << "init base:\n";
					pretty_print_decl(init->getBaseClass()->getAsCXXRecordDecl(), out, level + 2);
				}

				pretty_print_stmt(init->getInit(), out, level + 2);
			}
		}

		if (funcDecl->hasBody())
		{
			clang::Stmt const * stmt = funcDecl->getBody();
			pretty_print_stmt(stmt, out, level + 1);
		}
	}
}

void pretty_print_stmt(clang::Stmt const * stmt, std::ostream & out, int level)
{
	indent(out, level);
	out << stmt->getStmtClassName();

	if (clang::Expr const * e = llvm::dyn_cast<clang::Expr>(stmt))
	{
		out << " type:" << e->getType().getAsString();
	}

	if (clang::CastExpr const * e = llvm::dyn_cast<clang::CastExpr>(stmt))
	{
		out << " " << e->getCastKindName();
	}

	out << '\n';
	for (clang::Stmt::const_child_iterator ci = stmt->child_begin(); ci != stmt->child_end(); ++ci)
	{
		if (*ci == 0)
			continue;

		pretty_print_stmt(*ci, out, level + 1);
	}
}

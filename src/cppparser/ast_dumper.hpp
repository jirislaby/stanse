#ifndef AST_DUMPER_HPP
#define AST_DUMPER_HPP

#include "ast_dumper_detail.hpp"

#include <clang/AST/ASTContext.h>
#include <clang/AST/Expr.h>

#include <boost/assert.hpp>

#include <ostream>
#include <set>

template <typename OutputIterator>
void get_referenced_functions(clang::Decl const * decl, OutputIterator out)
{
	if (clang::FunctionDecl const * fnDecl = dyn_cast<clang::FunctionDecl>(decl))
	{
		if (fnDecl->hasBody())
			*out++ = fnDecl;
	}
}

template <typename OutputIterator>
void get_referenced_functions(clang::Stmt const * stmt, OutputIterator out)
{
	if (clang::DeclRefExpr const * expr = dyn_cast<clang::DeclRefExpr>(stmt))
	{
		clang::ValueDecl const * decl = expr->getDecl();
		get_referenced_functions(decl, out);
	}

	for (clang::Stmt::const_child_iterator ci = stmt->child_begin(); ci != stmt->child_end(); ++ci)
	{
		if (*ci != 0)
			get_referenced_functions(*ci, out);
	}
	if (clang::MemberExpr const * memberExpr = dyn_cast<clang::MemberExpr>(stmt))
	{
		get_referenced_functions(memberExpr->getMemberDecl(), out);
	}
}

void get_used_function_defs(clang::ASTContext const & ctx, std::set<clang::FunctionDecl const *> & functionDecls);

template <typename InputIterator>
void print_readable_ast(std::ostream &fout, clang::ASTContext const & ctx, InputIterator firstFun, InputIterator lastFun)
{
	for (; firstFun != lastFun; ++firstFun)
	{
		print_decl(*firstFun, fout, 0);
	}
}

template <typename InputIterator>
void print_ast(std::ostream & fout, clang::ASTContext const & ctx, InputIterator firstFun, InputIterator lastFun)
{
	fout <<
		"<?xml version=\"1.0\" encoding=\"utf-8\" ?>"
		"<translationUnit>";

	xml_printer p(fout);

	for (; firstFun != lastFun; ++firstFun)
	{
		clang::QualType fnType = (*firstFun)->getType();
		clang::FunctionType * ft = dyn_cast<clang::FunctionType>(fnType.getTypePtr());


		fout << "<externalDeclaration><functionDefinition name=\"";
		p.xml_print_decl_name(*firstFun);
		fout << "\"><declarator><id>";
		p.xml_print_decl_name(*firstFun);
		fout << "</id><functionDecl>";
		for (clang::FunctionDecl::param_const_iterator param_firstFun = (*firstFun)->param_begin(); param_firstFun != (*firstFun)->param_end(); ++param_firstFun)
		{
			fout << "<parameter><declarator><id>";
			fout << (*param_firstFun)->getNameAsString();
			fout << "</id></declarator></parameter>";
		}
		fout << "</functionDecl></declarator>";
		p.xml_print_statement((*firstFun)->getBody());
		fout << "</functionDefinition></externalDeclaration>";
	}

	fout <<
		"</translationUnit>\n";
}

#endif

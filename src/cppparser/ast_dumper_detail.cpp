#include "ast_dumper_detail.hpp"

#include <clang/ast/ASTContext.h>
#include <clang/ast/Decl.h>
#include <clang/ast/DeclTemplate.h>

namespace {

void indent(std::ostream & out, int level)
{
	for (; level > 0; --level)
		out << "    ";
}

}

void print_decl(clang::Decl const * decl, std::ostream & out, int level)
{
	indent(out, level);
	out << decl->getDeclKindName();
	if (clang::NamedDecl const * namedDecl = llvm::dyn_cast<clang::NamedDecl>(decl))
	{
		out << ": " << namedDecl->getName().data();
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
			print_decl(decl, out, level + 1);
		}
	}

	if (clang::ClassTemplateDecl const * d = llvm::dyn_cast<clang::ClassTemplateDecl>(decl))
	{
		print_decl(d->getTemplatedDecl(), out, level + 1);
	}

	if (clang::FunctionDecl const * funcDecl = llvm::dyn_cast<clang::FunctionDecl>(decl))
	{
		indent(out, level + 1);
		out << "primary template: \n";
		if (clang::FunctionTemplateDecl * tempDecl = funcDecl->getPrimaryTemplate())
			print_decl(tempDecl, out, level + 2);

		if (funcDecl->hasBody())
		{
			clang::Stmt const * stmt = funcDecl->getBody();
			print_stmt(stmt, out, level + 1);
		}
	}
}

void print_stmt(clang::Stmt const * stmt, std::ostream & out, int level)
{
	indent(out, level);
	out << stmt->getStmtClassName() << '\n';
	for (clang::Stmt::const_child_iterator ci = stmt->child_begin(); ci != stmt->child_end(); ++ci)
	{
		if (*ci == 0)
			continue;

		print_stmt(*ci, out, level + 1);
	}

	return;
	if (clang::CompoundStmt const * compStmt = llvm::dyn_cast<clang::CompoundStmt>(stmt))
	{
		for (clang::CompoundStmt::const_body_iterator it = compStmt->body_begin(); it != compStmt->body_end(); ++it)
		{
			print_stmt(*it, out, level + 1);
		}
	}
	if (clang::DeclStmt const * declStmt = llvm::dyn_cast<clang::DeclStmt>(stmt))
	{
		clang::Decl const * decl = declStmt->getSingleDecl();
		print_decl(decl, out, level + 1);
	}
	if (clang::CallExpr const * callExpr = llvm::dyn_cast<clang::CallExpr>(stmt))
	{
		indent(out, level + 1);
		out << "callee: \n";
		print_stmt(callExpr->getCallee(), out, level + 2);
	}
	if (clang::CastExpr const * castExpr = llvm::dyn_cast<clang::CastExpr>(stmt))
	{
		print_stmt(castExpr->getSubExpr(), out, level + 1);
	}
	if (clang::DeclRefExpr const * expr = llvm::dyn_cast<clang::DeclRefExpr>(stmt))
	{
		indent(out, level + 1);
		out << "decl: " << expr->getDecl()->getDeclKindName() << "\n";
	}
	if (clang::MemberExpr const * expr = llvm::dyn_cast<clang::MemberExpr>(stmt))
	{
		indent(out, level + 1);
		out << "member: " << expr->getMemberDecl()->getName().str() << "\n";
		print_stmt(expr->getBase(), out, level + 1);
	}
}

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

void xml_print_type(clang::QualType type, std::ostream & fout)
{
	clang::Type * ptype = type.getTypePtr();
	if (clang::BuiltinType * t = llvm::dyn_cast<clang::BuiltinType>(ptype))
		fout << "<baseType>" << t->getName(clang::LangOptions()) << "</baseType>\n";
	if (clang::RecordType * t =  llvm::dyn_cast<clang::RecordType>(ptype))
		fout << "<struct id=\"" << t->getDecl()->getNameAsString() << "\" />\n";
}

void xml_print_decl_name(clang::NamedDecl const * decl, std::ostream & fout)
{
	fout << decl->getName().str();
}

void xml_print_expr(clang::Expr const * expr, std::ostream & fout)
{
	if (clang::CallExpr const * e = llvm::dyn_cast<clang::CallExpr>(expr))
	{
		fout << "<functionCall>";
		xml_print_expr(e->getCallee(), fout);
		unsigned arg_count = e->getNumArgs();
		for (unsigned arg = 0; arg < arg_count; ++arg)
			xml_print_expr(e->getArg(arg), fout);
		fout << "</functionCall>";
	}
	else if (clang::CastExpr const * e = llvm::dyn_cast<clang::CastExpr>(expr))
	{
		xml_print_expr(e->getSubExpr(), fout);
	}
	else if (clang::DeclRefExpr const * e = llvm::dyn_cast<clang::DeclRefExpr>(expr))
	{
		fout << "<id>";
		xml_print_decl_name(e->getDecl(), fout);
		fout << "</id>";
	}
	else if (clang::BinaryOperator const * e = llvm::dyn_cast<clang::BinaryOperator>(expr))
	{
		if (e->getOpcode() == clang::BinaryOperator::Assign)
		{
			fout << "<assignExpression>";
			xml_print_expr(e->getLHS(), fout);
			xml_print_expr(e->getRHS(), fout);
			fout << "</assignExpression>";
		}
		else
		{
			fout << "<binaryExpression op=\"" << clang::BinaryOperator::getOpcodeStr(e->getOpcode()) << "\">";
			xml_print_expr(e->getLHS(), fout);
			xml_print_expr(e->getRHS(), fout);
			fout << "</binaryExpression>";
		}
	}
	else if (clang::ParenExpr const * e = llvm::dyn_cast<clang::ParenExpr>(expr))
	{
		xml_print_expr(e->getSubExpr(), fout);
	}
	else if (clang::MemberExpr const * e = llvm::dyn_cast<clang::MemberExpr>(expr))
	{
		if (e->isArrow())
			fout << "<arrowExpression>";

		xml_print_expr(e->getBase(), fout);
		fout << "<member>";
		xml_print_decl_name(e->getMemberDecl(), fout);
		fout << "</member>";

		if (e->isArrow())		
			fout << "</arrowExpression>";
	}
	else if (clang::IntegerLiteral const * e = llvm::dyn_cast<clang::IntegerLiteral>(expr))
	{
		fout << "<intConst>" << e->getValue().toString(10, true) << "</intConst>";
	}
}

void xml_print_statement(clang::Stmt const * stmt, std::ostream & fout)
{
	if (clang::CompoundStmt const * s = llvm::dyn_cast<clang::CompoundStmt>(stmt))
	{
		fout << "<compoundStatement>";
		for (clang::CompoundStmt::const_body_iterator ci = s->body_begin(); ci != s->body_end(); ++ci)
		{
			xml_print_statement(*ci, fout);
		}
		fout << "</compoundStatement>";
	}
	else if (clang::Expr const * expr = llvm::dyn_cast<clang::Expr>(stmt))
	{
		//fout << "<expressionStatement>";
		xml_print_expr(expr, fout);
		//fout << "</expressionStatement>";
	}
	else if (clang::IfStmt const * s = llvm::dyn_cast<clang::IfStmt>(stmt))
	{
		fout << "<ifStatement>";
		xml_print_expr(s->getCond(), fout);
		xml_print_statement(s->getThen(), fout);
		if (s->getElse())
			xml_print_statement(s->getElse(), fout);
		fout << "</ifStatement>";
	}
	else if (clang::ReturnStmt const * s = llvm::dyn_cast<clang::ReturnStmt>(stmt))
	{
		fout << "<returnStatement>";
		xml_print_expr(s->getRetValue(), fout);
		fout << "</returnStatement>";
	}
	else
	{
		fout << "<unknownStatement />";
	}
}

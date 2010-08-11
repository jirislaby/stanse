#include "ast_dumper_detail.hpp"

#include <clang/AST/ASTContext.h>
#include <clang/AST/Decl.h>
#include <clang/AST/DeclTemplate.h>

#include <boost/algorithm/string/replace.hpp>

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
		out << "member: " << expr->getMemberDecl()->getQualifiedNameAsString() << "\n";
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

std::string xml_escape(std::string const & str)
{
	std::string res;

	static struct
	{
		char ch;
		char const * entity;
	}
	const entities[] =
	{
		{ '<', "&lt;" },
		{ '>', "&gt;" },
		{ '"', "&quot;" },
		{ '\'', "&apos;" },
		{ '&', "&amp;" },
		{ 0, 0 }
	};

	char const * start = str.data();
	char const * end = start + str.size();
	char const * cur = start;

	while (cur != end)
	{
		std::size_t eid;
		for (; cur != end; ++cur)
		{
			eid = 0;
			while (entities[eid].entity != 0 && entities[eid].ch != *cur)
				++eid;
			if (entities[eid].entity != 0)
				break;
		}

		res.append(start, cur);
		if (entities[eid].entity != 0)
		{
			res.append(entities[eid].entity);
			start = cur + 1;
			++cur;
		}
	}

	return res;
}

xml_printer::xml_printer(std::ostream & fout, std::map<clang::Decl const *, std::string> const & decl_names, clang::SourceManager const * sm)
	: m_decl_names(decl_names), fout(fout), m_sm(sm)
{
}

void xml_printer::xml_print_type(clang::QualType type)
{
	clang::Type * ptype = type.getTypePtr();
	if (clang::BuiltinType * t = llvm::dyn_cast<clang::BuiltinType>(ptype))
		fout << "<baseType>" << t->getName(clang::LangOptions()) << "</baseType>\n";
	if (clang::RecordType * t =  llvm::dyn_cast<clang::RecordType>(ptype))
		fout << "<struct id=\"" << t->getDecl()->getQualifiedNameAsString() << "\" />\n";
}

void xml_printer::xml_print_decl_name(clang::NamedDecl const * decl)
{
	std::map<clang::Decl const *, std::string>::const_iterator ci = m_decl_names.find(decl);
	if (ci != m_decl_names.end())
		fout << xml_escape(ci->second);
	else
		fout << xml_escape(make_decl_name(decl));
}

void xml_printer::xml_print_expr(clang::Expr const * expr)
{
	if (clang::CallExpr const * e = llvm::dyn_cast<clang::CallExpr>(expr))
	{
		xml_print_tag("functionCall", expr->getExprLoc());
		xml_print_expr(e->getCallee());
		unsigned arg_count = e->getNumArgs();
		for (unsigned arg = 0; arg < arg_count; ++arg)
			xml_print_expr(e->getArg(arg));
		fout << "</functionCall>";
	}
	else if (clang::CastExpr const * e = llvm::dyn_cast<clang::CastExpr>(expr))
	{
		xml_print_expr(e->getSubExpr());
	}
	else if (clang::DeclRefExpr const * e = llvm::dyn_cast<clang::DeclRefExpr>(expr))
	{
		xml_print_tag("id", e->getLocation());
		xml_print_decl_name(e->getDecl());
		fout << "</id>";
	}
	else if (clang::BinaryOperator const * e = llvm::dyn_cast<clang::BinaryOperator>(expr))
	{
		if (e->getOpcode() == clang::BinaryOperator::Assign)
		{
			xml_print_tag("assignExpression", e->getOperatorLoc());
			xml_print_expr(e->getLHS());
			xml_print_expr(e->getRHS());
			fout << "</assignExpression>";
		}
		else
		{
			xml_print_tag("binaryExpression", e->getOperatorLoc(), "op=\"" + xml_escape(clang::BinaryOperator::getOpcodeStr(e->getOpcode())) + "\"");
			xml_print_expr(e->getLHS());
			xml_print_expr(e->getRHS());
			fout << "</binaryExpression>";
		}
	}
	else if (clang::UnaryOperator const * e = llvm::dyn_cast<clang::UnaryOperator>(expr))
	{
		xml_print_tag("unaryExpression", e->getOperatorLoc(), "op=\"" + xml_escape(clang::UnaryOperator::getOpcodeStr(e->getOpcode())) + "\"");
		xml_print_expr(e->getSubExpr());
		fout << "</unaryExpression>";
	}
	else if (clang::ParenExpr const * e = llvm::dyn_cast<clang::ParenExpr>(expr))
	{
		xml_print_expr(e->getSubExpr());
	}
	else if (clang::MemberExpr const * e = llvm::dyn_cast<clang::MemberExpr>(expr))
	{
		if (e->isArrow())
			xml_print_tag("arrowExpression", e->getExprLoc());

		xml_print_expr(e->getBase());
		xml_print_tag("member", e->getMemberLoc());
		xml_print_decl_name(e->getMemberDecl());
		fout << "</member>";

		if (e->isArrow())		
			fout << "</arrowExpression>";
	}
	else if (clang::IntegerLiteral const * e = llvm::dyn_cast<clang::IntegerLiteral>(expr))
	{
		xml_print_tag("intConst", e->getLocation());
		fout << e->getValue().toString(10, true) << "</intConst>";
	}
	else if (clang::StringLiteral const * e = llvm::dyn_cast<clang::StringLiteral>(expr))
	{
		xml_print_tag("stringConst", e->getStrTokenLoc(0));
		fout << "TODO</stringConst>";
		//fout << xml_escape(e->getString()) << "</stringConst>";  // TODO: Escape special values?
	}
	else if (clang::ConditionalOperator const * e = llvm::dyn_cast<clang::ConditionalOperator>(expr))
	{
		xml_print_tag("conditionalExpression", e->getLocStart());
		xml_print_expr(e->getCond());
		xml_print_expr(e->getLHS());
		xml_print_expr(e->getRHS());
		fout << "</conditionalExpression>";
	}
	else
	{
		fout << "<unknownExpression />";
	}
}

void xml_printer::xml_print_tag(std::string const & tag_name, clang::SourceLocation sl, std::string const & extra)
{
	if (m_sm)
		fout << "<" << tag_name << " bl=\"" << m_sm->getInstantiationLineNumber(sl) << "\" " << extra << ">";
	else
		fout << "<" << tag_name << " " << extra << ">";
}

void xml_printer::xml_print_statement(clang::Stmt const * stmt)
{
	if (!stmt)
		return;

	if (clang::CompoundStmt const * s = llvm::dyn_cast<clang::CompoundStmt>(stmt))
	{
		xml_print_tag("compoundStatement", s->getLBracLoc());
		for (clang::CompoundStmt::const_body_iterator ci = s->body_begin(); ci != s->body_end(); ++ci)
		{
			xml_print_statement(*ci);
		}
		fout << "</compoundStatement>";
	}
	else if (clang::Expr const * expr = llvm::dyn_cast<clang::Expr>(stmt))
	{
		//fout << "<expressionStatement>";
		xml_print_expr(expr);
		//fout << "</expressionStatement>";
	}
	else if (clang::IfStmt const * s = llvm::dyn_cast<clang::IfStmt>(stmt))
	{
		xml_print_tag("ifStatement", s->getIfLoc());
		xml_print_expr(s->getCond());
		xml_print_statement(s->getThen());
		if (s->getElse())
			xml_print_statement(s->getElse());
		fout << "</ifStatement>";
	}
	else if (clang::ReturnStmt const * s = llvm::dyn_cast<clang::ReturnStmt>(stmt))
	{
		clang::Expr const * ret_value = s->getRetValue();
		xml_print_tag("returnStatement", s->getReturnLoc());
		if (ret_value)
			xml_print_expr(s->getRetValue());
		fout << "</returnStatement>";
	}
	else
	{
		xml_print_tag("unknownStatement", stmt->getLocStart(), "/");
	}
}

std::string make_decl_name(clang::NamedDecl const * decl)
{
	if (clang::FunctionDecl const * fndecl = llvm::dyn_cast<clang::FunctionDecl>(decl))
	{
		std::string name = decl->getQualifiedNameAsString();
		if (fndecl->getNumParams() != 0)
		{
			name += '(';
			for (clang::FunctionDecl::param_const_iterator param_ci = fndecl->param_begin(); param_ci != fndecl->param_end(); ++param_ci)
			{
				if (param_ci != fndecl->param_begin())
					name += ", ";
				clang::VarDecl const * param_decl = *param_ci;
				name += static_cast<clang::QualType>(param_decl->getType()->getCanonicalTypeUnqualified()).getAsString();
			}
			name += ')';
		}

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

#include <clang/Sema/ParseAST.h>
#include <clang/Lex/Preprocessor.h>
#include <clang/Basic/SourceManager.h>
#include <clang/Basic/FileManager.h>
#include <clang/Basic/TargetInfo.h>
#include <clang/Lex/HeaderSearch.h>
#include <clang/Ast/ASTContext.h>
#include <clang/Frontend/CompilerInvocation.h>
#include <clang/Ast/ASTConsumer.h>
#include <clang/Ast/Decl.h>
#include <clang/Ast/Stmt.h>
#include <clang/Ast/DeclTemplate.h>
#include <clang/Ast/DeclCXX.h>
#include <clang/Analysis/CFG.h>

#include <iostream>
#include <set>

#include <boost/utility.hpp>
#include <boost/assert.hpp>

class MyConsumer
	: public clang::ASTConsumer
{
public:
	void HandleTranslationUnit(clang::ASTContext &Ctx)
	{
	}
};

class MyDiagClient
	: public clang::DiagnosticClient
{
	void HandleDiagnostic(clang::Diagnostic::Level DiagLevel,
		const clang::DiagnosticInfo &Info)
	{
		llvm::SmallVector<char, 1024> str;
		Info.FormatDiagnostic(str);
		str.append(1, (char)0);
		std::cerr << str.data() << std::endl;
	}
};

clang::Decl const * mainDecl = 0;
clang::Stmt const * mainStmt = 0;

void indent(std::ostream & out, int level)
{
	for (; level > 0; --level)
		out << "    ";
}

void print_stmt(clang::Stmt const * stmt, std::ostream & out, int level);

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
			if (funcDecl->getName() == "main")
			{
				mainDecl = funcDecl;
				mainStmt = stmt;
			}
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

int main(int argc, char * argv[])
{
	MyDiagClient diag_client;
	clang::Diagnostic diag(&diag_client);

	char const * args[] = { "-x", "c++" };
	clang::CompilerInvocation ci;
	clang::CompilerInvocation::CreateFromArgs(ci, args, args + sizeof args / sizeof args[0], diag);

	clang::SourceManager sm(diag);
	clang::FileManager fm;

	std::string targetFile = "c:\\Users\\Martin.vejnar\\Documents\\temp\\pokus.cpp";
	bool printAST = false;
	bool printCFG = false;
	bool printReadableAST = false;
	
	for (int i = 1; i < argc; ++i)
	{
		std::string arg = argv[i];
		if (arg == "-a")
			printReadableAST = true;
		else if (arg == "-A")
			printAST = true;
		else if (arg == "-c")
			printCFG = true;
		else
			targetFile = arg;
	}

	if (!printReadableAST && !printAST && !printCFG)
		printReadableAST = true;

	sm.createMainFileID(
		fm.getFile(targetFile),
		clang::SourceLocation());
	
	clang::HeaderSearch hs(fm);

	clang::TargetInfo * pTi = clang::TargetInfo::CreateTargetInfo(diag, ci.getTargetOpts());

	clang::Preprocessor pp(
		diag,
		ci.getLangOpts(),
		*pTi,
		sm,
		hs);

	clang::ASTContext ctx(ci.getLangOpts(), pp.getSourceManager(), pp.getTargetInfo(), pp.getIdentifierTable(), pp.getSelectorTable(), pp.getBuiltinInfo());
	
	MyConsumer consumer;

	clang::ParseAST(pp, &consumer, ctx);

	// Scan the AST and retrieve the set of top-level functions and function template instantiations that are (transitively)
	// used by these top-level functions.
	// TODO: member function should be found too.

	std::set<clang::FunctionDecl const *> functionDecls;
	std::vector<clang::FunctionDecl const *> unprocessedDecls;

	{
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

	if (printAST)
	{
		std::ostream & fout = std::cout;

		fout <<
			"<?xml version=\"1.0\" encoding=\"utf-8\" ?>"
			"<translationUnit>";

		for (std::set<clang::FunctionDecl const *>::const_iterator ci = functionDecls.begin(); ci != functionDecls.end(); ++ci)
		{
			clang::QualType fnType = (*ci)->getType();
			clang::FunctionType * ft = dyn_cast<clang::FunctionType>(fnType.getTypePtr());


			fout << "<externalDeclaration><functionDefinition name=\"";
			xml_print_decl_name(*ci, fout);
			fout << "\"><declarator><id>";
			xml_print_decl_name(*ci, fout);
			fout << "</id><functionDecl>";
			for (clang::FunctionDecl::param_const_iterator param_ci = (*ci)->param_begin(); param_ci != (*ci)->param_end(); ++param_ci)
			{
				fout << "<parameter><declarator><id>";
				fout << (*param_ci)->getNameAsString();
				fout << "</id></declarator></parameter>";
			}
			fout << "</functionDecl></declarator>";
			xml_print_statement((*ci)->getBody(), fout);
			fout << "</functionDefinition></externalDeclaration>";
		}

		fout <<
			"</translationUnit>\n";
	}

	if (printCFG)
	{
		std::ostream & fout = std::cout;

		fout <<
			"<?xml version=\"1.0\" encoding=\"utf-8\" ?>"
			"<cfgs>";

		for (std::set<clang::FunctionDecl const *>::const_iterator ci = functionDecls.begin(); ci != functionDecls.end(); ++ci)
		{
			clang::CFG * cfg = clang::CFG::buildCFG(*ci, (*ci)->getBody(), &ctx);

			int free_nodeid = cfg->getNumBlockIDs();

			fout << "<cfg name=\"";
			xml_print_decl_name(*ci, fout);
			fout << "\" startnode=\"" << cfg->getEntry().getBlockID() << "\" endnode=\"" << cfg->getExit().getBlockID() << "\">";
			for (clang::CFG::const_iterator block_ci = cfg->begin(); block_ci != cfg->end(); ++block_ci)
			{
				clang::CFGBlock * block = *block_ci;

				clang::CFGBlock::const_iterator stmt_ci = block->begin();
				std::size_t stmts_processed = 0;

				int previd = block->getBlockID();
				for (; stmt_ci != block->end(); ++stmt_ci)
				{
					if (boost::next(stmt_ci) != block->end())
					{
						fout << "<node id=\"" << previd;
						previd = free_nodeid;
						fout << "\" next=\"" << free_nodeid++ << "\">";
						xml_print_statement(*stmt_ci, fout);
						fout << "</node>";
						++stmts_processed;
					}
					else
					{
						if (block->getTerminatorCondition() != 0)
						{
							BOOST_ASSERT(block->getTerminatorCondition() == *stmt_ci);

							fout << "<branchnode id=\"" << previd;
							previd = free_nodeid;
							fout << "\"><cond>";
							xml_print_statement(*stmt_ci, fout);
							fout << "</cond>";

							if (clang::IfStmt const * s = llvm::dyn_cast<clang::IfStmt>(block->getTerminator()))
							{
								clang::CFGBlock::const_succ_iterator succ_ci = block->succ_begin();
								fout << "<next nodeid=\"" << (*succ_ci++)->getBlockID() << "\"><default/></next>";
								fout << "<next nodeid=\"" << (*succ_ci++)->getBlockID() << "\"><intConst>0</intConst></next>";
								BOOST_ASSERT(succ_ci == block->succ_end());
							}
							else
							{
								BOOST_ASSERT(0 && "Not implemented yet.");
							}

							fout << "</branchnode>";
							++stmts_processed;
						}
						else
						{
							BOOST_ASSERT(block->succ_size() == 1);

							fout << "<node id=\"" << previd;
							fout << "\" next=\"" << (*block->succ_begin())->getBlockID() << "\">";
							xml_print_statement(*stmt_ci, fout);
							fout << "</node>";
							++stmts_processed;
						}
					}
				}

				if (stmts_processed == 0)
				{
					if (block->succ_size() == 0)
					{
						fout << "<exitnode id=\"" << previd << "\"><exit /></exitnode>";
					}
					else
					{
						fout << "<node id=\"" << previd << "\" next=\"" << (*block->succ_begin())->getBlockID() << "\"><emptyStatement/></node>";
					}
				}
			}

			fout << "</cfg>";
		}

		fout <<
			"</cfgs>\n";
	}


	if (printReadableAST)
	{
		for (std::set<clang::FunctionDecl const *>::const_iterator ci = functionDecls.begin(); ci != functionDecls.end(); ++ci)
		{
			print_decl(*ci, std::cout, 0);
		}
	}
}
#ifndef AST_DUMPER_HPP
#define AST_DUMPER_HPP

#include "ast_dumper_detail.hpp"

#include <clang/ast/ASTContext.h>
#include <clang/ast/Expr.h>

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

	for (; firstFun != lastFun; ++firstFun)
	{
		clang::QualType fnType = (*firstFun)->getType();
		clang::FunctionType * ft = dyn_cast<clang::FunctionType>(fnType.getTypePtr());


		fout << "<externalDeclaration><functionDefinition name=\"";
		xml_print_decl_name(*firstFun, fout);
		fout << "\"><declarator><id>";
		xml_print_decl_name(*firstFun, fout);
		fout << "</id><functionDecl>";
		for (clang::FunctionDecl::param_const_iterator param_firstFun = (*firstFun)->param_begin(); param_firstFun != (*firstFun)->param_end(); ++param_firstFun)
		{
			fout << "<parameter><declarator><id>";
			fout << (*param_firstFun)->getNameAsString();
			fout << "</id></declarator></parameter>";
		}
		fout << "</functionDecl></declarator>";
		xml_print_statement((*firstFun)->getBody(), fout);
		fout << "</functionDefinition></externalDeclaration>";
	}

	fout <<
		"</translationUnit>\n";
}

template <typename InputIterator>
void print_cfg(std::ostream & fout, clang::ASTContext & ctx, InputIterator firstFun, InputIterator lastFun)
{
	fout <<
		"<?xml version=\"1.0\" encoding=\"utf-8\" ?>"
		"<cfgs>";

	for (; firstFun != lastFun; ++firstFun)
	{
		clang::CFG * cfg = clang::CFG::buildCFG(*firstFun, (*firstFun)->getBody(), &ctx);

		int free_nodeid = cfg->getNumBlockIDs();

		fout << "<cfg name=\"";
		xml_print_decl_name(*firstFun, fout);
		fout << "\" startnode=\"" << cfg->getEntry().getBlockID() << "\" endnode=\"" << cfg->getExit().getBlockID() << "\">";
		for (clang::CFG::const_iterator block_firstFun = cfg->begin(); block_firstFun != cfg->end(); ++block_firstFun)
		{
			clang::CFGBlock * block = *block_firstFun;

			clang::CFGBlock::const_iterator stmt_firstFun = block->begin();
			std::size_t stmts_processed = 0;

			int previd = block->getBlockID();
			for (; stmt_firstFun != block->end(); ++stmt_firstFun)
			{
				if (boost::next(stmt_firstFun) != block->end())
				{
					fout << "<node id=\"" << previd;
					previd = free_nodeid;
					fout << "\" next=\"" << free_nodeid++ << "\">";
					xml_print_statement(*stmt_firstFun, fout);
					fout << "</node>";
					++stmts_processed;
				}
				else
				{
					if (block->getTerminatorCondition() != 0)
					{
						BOOST_ASSERT(block->getTerminatorCondition() == *stmt_firstFun);

						fout << "<branchnode id=\"" << previd;
						previd = free_nodeid;
						fout << "\"><cond>";
						xml_print_statement(*stmt_firstFun, fout);
						fout << "</cond>";

						if (clang::IfStmt const * s = llvm::dyn_cast<clang::IfStmt>(block->getTerminator()))
						{
							clang::CFGBlock::const_succ_iterator succ_firstFun = block->succ_begin();
							fout << "<next nodeid=\"" << (*succ_firstFun++)->getBlockID() << "\"><default/></next>";
							fout << "<next nodeid=\"" << (*succ_firstFun++)->getBlockID() << "\"><intConst>0</intConst></next>";
							BOOST_ASSERT(succ_firstFun == block->succ_end());
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
						xml_print_statement(*stmt_firstFun, fout);
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

#endif

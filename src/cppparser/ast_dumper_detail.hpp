#ifndef AST_DUMPER_DETAIL_HPP
#define AST_DUMPER_DETAIL_HPP

#include <clang/ast/ASTContext.h>

void print_decl(clang::Decl const * decl, std::ostream & out, int level);
void print_stmt(clang::Stmt const * stmt, std::ostream & out, int level);
void xml_print_type(clang::QualType type, std::ostream & fout);
void xml_print_decl_name(clang::NamedDecl const * decl, std::ostream & fout);
void xml_print_expr(clang::Expr const * expr, std::ostream & fout);
void xml_print_statement(clang::Stmt const * stmt, std::ostream & fout);

#endif

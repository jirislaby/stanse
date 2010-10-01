#ifndef AST_PRETTY_PRINTER_HPP
#define AST_PRETTY_PRINTER_HPP

#include <clang/AST/Decl.h>
#include <clang/AST/Stmt.h>

#include <iostream>

void pretty_print_decl(clang::Decl const * decl, std::ostream & out, int level = 0);
void pretty_print_stmt(clang::Stmt const * stmt, std::ostream & out, int level = 0);

#endif

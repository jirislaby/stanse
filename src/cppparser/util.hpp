#ifndef CPPPARSER_UTIL_HPP
#define CPPPARSER_UTIL_HPP

#include <clang/AST/Decl.h>

#include <set>
#include <string>

void get_functions_from_declcontext(clang::DeclContext const * declctx, std::set<clang::FunctionDecl const *> & fns);
std::string make_decl_name(clang::NamedDecl const * decl);

#endif

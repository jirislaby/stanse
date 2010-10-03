#ifndef CPPPARSER_UTIL_HPP
#define CPPPARSER_UTIL_HPP

#include <clang/AST/Decl.h>
#include <string>

std::string make_decl_name(clang::NamedDecl const * decl, std::string const & static_prefix = "__unique");

#endif

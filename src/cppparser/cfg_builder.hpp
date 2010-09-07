#ifndef CPPPARSER_CFG_BUILDER_HPP
#define CPPPARSER_CFG_BUILDER_HPP

#include "cfg.hpp"
#include <clang/AST/Decl.h>
#include <utility>

program build_program(clang::TranslationUnitDecl const * tu);

#endif

#ifndef CPPPARSER_CFG_JSON_WRITER_HPP
#define CPPPARSER_CFG_JSON_WRITER_HPP

#include "cfg.hpp"
#include <iostream>

void cfg_json_write(std::ostream & out, program const & prog, bool readable);

#endif

cmake_minimum_required(VERSION 2.6)
project(CPPPARSER)

set(LLVM_PATH "~/checkouts/llvm" CACHE PATH "a path to LLVM sources")
set(LLVM_BUILD_PATH ${LLVM_PATH}/_build CACHE PATH "a path where LLVM was built")

set(LLVM_INCLUDE_DIR
    ${LLVM_PATH}/include
    ${LLVM_BUILD_PATH}/include
    ${LLVM_PATH}/tools/clang/include
    ${LLVM_BUILD_PATH}/tools/clang/include
    )

set(LLVM_LIB_DIR "${LLVM_BUILD_PATH}/lib")

include_directories(${LLVM_INCLUDE_DIR})
link_directories(${LLVM_LIB_DIR})

add_executable(cppparser ast_pretty_printer.cpp main.cpp cfg.cpp
    cfg_builder.cpp cfg_json_writer.cpp unit_navigator.cpp util.cpp json/json_reader.cpp
    json/json_value.cpp json/json_writer.cpp)
target_link_libraries(cppparser libclang)
set_property(
    TARGET cppparser
    PROPERTY COMPILE_DEFINITIONS __STDC_LIMIT_MACROS __STDC_CONSTANT_MACROS
    )

#!/bin/bash

# CONFIG
OUTPUT_DIR="./out"
LINUX_SRC="./src/linux-2.6.18"
SC_SRC="./src"
JAVA="/opt/jdk1.6.0_02/bin/java -Xms700m -Xmx700m"
PARSER_OBDR="NO"
DEBUG="NO" 

INCLUDE_LINUX=" -I $LINUX_SRC/include/ \
		-I $LINUX_SRC/include/linux \
                -I $LINUX_SRC/include/asm-i386/mach-default \
                -I $LINUX_SRC/arch/um/include/sysdep-i386 "

INCLUDE_SIEMENS=" -I "`echo $1 | sed 's/^\(.*\)\/[^\/]*$/\1/'`" \
		  -I $SC_SRC/inc \
		  -I $SC_SRC/modules/include \
                  -I $SC_SRC/Proxy \
                  -I $SC_SRC/Server \
                  -I $SC_SRC/Server/inc \
                  -I $SC_SRC/Server/linux/inc \
                  -I $SC_SRC/Server/test \
                  -I $SC_SRC/Server/pl_inc \
                  -I $SC_SRC/Server/unix/performance/kernel_module/ "
INCLUDE_OCHCAVKY=" -include ./ochcavky.h"

# DO NOT EDIT THE FOLLOWING SHIT
mkdir -p $OUTPUT_DIR
SOURCE=`echo $1 | sed 's/^.*\/\([^\/]*\)$/\1/'`
SOURCE_PATH=`echo $1 | sed 's/^\(.*\)\/[^\/]*$/\1/' | sed 's/\//\#/g'`
OUT="$OUTPUT_DIR/$SOURCE_PATH#$SOURCE#"
LOG=$OUT"error.log"
touch $LOG

echo 
echo "================ Soubor: $1"
                
# FIND INCLUDE
echo -n "========== Find include: "
cat $1 | grep "^#include" > $OUT"inc"
echo "OK"

# GET MACROS
echo -n "============ Get macros: "
cpp -w -E -dM $INCLUDE_OCHCAVKY $INCLUDE_LINUX $INCLUDE_SIEMENS $OUT"inc" > $OUT"inc-all-macros.h" 2> $LOG
if [ $? -eq 0 ] ; then
    echo "OK"    
else
    echo "ERROR => exit"
    if [ "$DEBUG" == "NO" ] ; then
	echo bla
	rm -rf $OUT"inc" $OUT"inc-all-macros.h"
    fi
    exit
fi 
grep -v -f ./del-macros $OUT"inc-all-macros.h" > $OUT"inc-macro.h"

# PREPROCESS INCLUDE
echo -n "==== Preprocess include: "
cpp -w -E -P $INCLUDE_OCHCAVKY $INCLUDE_LINUX $INCLUDE_SIEMENS $OUT"inc" > $OUT"inc-prep.c" 2> $LOG
if [ $? -eq 0 ] ; then
    echo "OK"    
else
    echo "ERROR => exit"
    if [ "$DEBUG" == "NO" ] ; then
	rm -rf $OUT"inc" $OUT"inc-all-macros.h" $OUT"inc-macro.h" $OUT"inc-prep.c"
    fi
    exit
fi 

#FIND TYPEDEFS
echo -n "========= Find typedefs: "
if [ $PARSER_OBDR == "YES" ] ; then 
    vi -f -n -c "%s/;[[:space:]]*\n[[:space:]]*;;*/;/" -c "wq" $OUT"inc-prep.c"
    $JAVA -cp ./printTypes:./printTypes/antlr-runtime-3.0.jar PrintTypes $OUT"inc-prep.c" > $OUT"inc-typedefs" 2> $LOG
else
    $JAVA -jar ./dist/scv.jar $OUT"inc-prep.c" > $OUT"inc-typedefs" 2> $LOG
fi

if [ -s $LOG ] ; then
    echo "ERROR => exit"
    if [ "$DEBUG" == "NO" ] ; then
	rm -rf $OUT"inc" $OUT"inc-all-macros.h" $OUT"inc-macro.h" $OUT"inc-prep.c" $OUT"inc-typedefs"
    fi
    exit    
else
    echo "OK"
fi 

#FINAL PREPROCESS
echo -n "====== Final preprocess: "
cat $OUT"inc-macro.h" $OUT"inc-typedefs" $1 | grep -v "^#include" > $OUT"final.c"
cpp -w -E -P -D__KERNEL__ $INCLUDE_SIEMENS $OUT"final.c" > $OUT"final-prep.c" 2> $LOG
if [ $? -eq 0 ] ; then
    echo "OK"    
else
    echo "ERROR => exit"
    if [ "$DEBUG" == "NO" ] ; then
	rm -rf $OUT"inc" $OUT"inc-all-macros.h" $OUT"inc-macro.h" $OUT"inc-prep.c" $OUT"inc-typedefs"
    fi
    exit
fi 

#CLEAN
if [ $DEBUG == "NO" ] ; then
    rm -rf $LOG $OUT"inc" $OUT"inc-all-macros.h" $OUT"inc-macro.h" $OUT"inc-prep.c" $OUT"inc-typedefs"
fi
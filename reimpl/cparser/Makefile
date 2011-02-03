CC=gcc
OS=linux
CFLAGS=-g -Wall -O2 -Wno-unused -march=core2 -fPIC -I$(JAVA_HOME)/include \
	-I$(JAVA_HOME)/include/$(OS)
LDFLAGS=-g -O2 -fPIC
LIBS=-lantlr3c
ANTLR=../../tools/antlr-3.2.jar
JFLAGS=-cp ../../dist/lib/log4j-1.2.16.jar
JSOURCES=$(wildcard cparser/AST/*.java) cparser/CParser.java
JCLASSES=$(patsubst %.java,%.class,$(JSOURCES))

all: libcparser.so $(JCLASSES)

libcparser.so: GNUCaLexer.o GNUCaParser.o ASTEmitter.o jni_glue.o parse.o
	$(CC) -shared $(LDFLAGS) -o $@ $^ $(LIBS)

main.c: GNUCaParser.h GNUCaLexer.h ASTEmitter.h

cparser_CParser.h: cparser/CParser.class
	javah cparser.CParser
	touch cparser_CParser.h

jni_glue.c: cparser_CParser.h

$(JCLASSES): $(JSOURCES)
	javac $(JFLAGS) $^

GNUCaParser.c GNUCaLexer.c GNUCaParser.h GNUCaLexer.h: .GNUCa_g

.%_g: %.g
	java -jar $(ANTLR) -depend $^ >.$^.u
	java -jar $(ANTLR) $^
	touch $@

ASTEmitter.c ASTEmitter.h: ASTEmitter.g
	java -jar $(ANTLR) $^

clean:
	rm -f *.o *Parser.[ch] *Lexer.[ch] *Emitter.[ch] *.tokens .*_g .*.u

.PHONY: all clean

-include .*.u

CC=gcc
CFLAGS=-Wall -O2 -Wno-unused -march=core2
LDFLAGS=-O2
LIBS=-lantlr3c
ANTLR=../../tools/antlr-3.2.jar

all: test

test: main.o GNUCaLexer.o GNUCaParser.o ASTEmitter.o
	$(CC) $(LDFLAGS) -o $@ $^ $(LIBS)

main.c: GNUCaParser.h GNUCaLexer.h ASTEmitter.h

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

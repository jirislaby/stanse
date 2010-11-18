CC=gcc
CFLAGS=-Wall -O2 -Wno-unused -march=core2
LDFLAGS=-O2
LIBS=-lantlr3c

all: test

test: main.o GNUCaLexer.o GNUCaParser.o CFGEmitter.o
	$(CC) $(LDFLAGS) -o $@ $^ $(LIBS)

main.c: GNUCaParser.h GNUCaLexer.h CFGEmitter.h

GNUCaParser.c GNUCaLexer.c GNUCaParser.h GNUCaLexer.h: .GNUCa_g

.%_g: %.g
	java -jar ../tools/antlr-3.2.jar -depend $^ >.$^.u
	java -jar ../tools/antlr-3.2.jar $^
	touch $@

CFGEmitter.c CFGEmitter.h: CFGEmitter.g
	java -jar ../tools/antlr-3.2.jar $^

clean:
	rm -f *.o *Parser.[ch] *Lexer.[ch] *Emitter.[ch] *.tokens .*_g .*.u

.PHONY: all clean

-include .*.u

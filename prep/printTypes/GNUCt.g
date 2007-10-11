/*%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

GNUC grammar for ANTLR v3
Based on ANSIC99 grammar by Jan Obdrzalek
Some bits taken from C.g by Terence Parr

AUTHOR:		Jan Obdrzalek, Jul 07, 2007
	
TODO:
	- fix typedefs
	- improve alternate keywords handling
	- check 'I','J' imaginary suffixes
	- finish string literal concatenation
	- attributes in typeQualifierList
	- fix the externalDefinition prediction
	- fix 'extern __typeof__' bug

IMPLEMENTED EXTENSIONS:	
	(as of 4.1.0 GCC manual)
	5.1 Statements and Declarations in Expressions
	5.4 Nested Functions
	5.5 Constructing Function Calls (IGNORED)
	5.6 Referring to a Type with typeof
	5.7 Conditionals with Omitted Operands
	5.8 Double-Word Integers (C99)	
	5.9 Complex Numbers (partly C99)	
	5.10 Hex Floats (C99)
	5.11 Arrays of Length Zero (IGNORED)	
	5.12 Structures With No Members
	5.18 Non-Constant Initializers (C99)
	5.19 Compound Literals (C99/IGNORED)
	5.20 Designated Initializers
	5.21 Case Ranges
	5.22 Cast to a Union Type (IGNORED)
	5.23 Mixed Declarations and Code (C99)
	5.24 Declaring Attributes of Functions
	5.25 Attribute Syntax
	5.26 Prototypes and Old-Style Function Definitions (IGNORED)
	5.27 C++ Style Comments	(C99)
	5.28 Dollar Signs in Identifier Names
	5.29 The Character <ESC> in Constants
	5.30 Inquiring on Alignment of Types or Variables
	5.31 Specifying Attributes of Variables
	5.32 Specifying Attributes of Types
	5.33 An Inline Function is As Fast As a Macro (C99/IGNORED)	
	5.34 Assembler Instructions with C Expression Operands
	5.35 Constraints for asm Operands
	5.36 Controlling Names Used in Assembler Code
	5.37 Variables in Specified Registers
	5.38 Alternate Keywords
	5.43 Offsetof
	5.50 Unnamed struct/union fields within structs/unions (IGNORED)
	5.51 Thread-Local Storage
	
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%*/


grammar GNUCt;
options {
	backtrack=true;
	memoize=true;
	k=2;
}

scope Symbols {
	Set types; // only track types in order to get parser working
}

@header{
//	package cz.muni.fi.iti.scv.c2xml;
	import java.util.Set;
	import java.util.HashSet;
	import java.util.Iterator;
	import java.util.NoSuchElementException;	
}

@members{
	boolean isTypeName(String name) {
		for (int i = Symbols_stack.size()-1; i>=0; i--) {
			Symbols_scope scope = (Symbols_scope)Symbols_stack.get(i);
			if ( scope.types.contains(name) ) {
//				System.out.println("S "+name);
				return true;
			}
		}
//		System.out.println("U "+name);
		return false;
	}
	
	public void printTypes() {
		Symbols_scope scope = (Symbols_scope)Symbols_stack.get(0);
		Iterator i= scope.types.iterator();
		try {
			while (true) {System.out.println((String) i.next()); }
		} catch (NoSuchElementException e) { }
	}
		
	
	public String getErrorMessage(RecognitionException e,
String[] tokenNames)
{
List stack = getRuleInvocationStack(e, this.getClass().getName());
String msg = null;
if ( e instanceof NoViableAltException ) {
NoViableAltException nvae = (NoViableAltException)e;
msg = " no viable alt; token="+e.token+
" (decision="+nvae.decisionNumber+
" state "+nvae.stateNumber+")"+
" decision=<<"+nvae.grammarDecisionDescription+">>";
}
else {
msg = super.getErrorMessage(e, tokenNames);
}
return stack+" "+msg;
}
public String getTokenErrorDisplay(Token t) {
return t.toString();
}
}

/** The grammar starts here */

// A.2.4 External definitions

translationUnit							// (6.9)
scope Symbols; // entire file is a scope
@init { $Symbols::types = new HashSet(); }
//@after { // prints only top-level types
//	Iterator i= $Symbols::types.iterator();
//	try {
//		while (true) {System.out.println((String) i.next()); }
//	} catch (NoSuchElementException e) { }
//	}
        :	externalDeclaration*				// Empty source files = GNU
        ;


//
externalDeclaration
options {k=1;}
	:	( declarationSpecifiers declarator ('{' | storageClassSpecifier | typeSpecifier | typeQualifier | functionSpecifier| '__attribute' | '__attribute__') )=> functionDefinition	// (6.9.1)
	|	declaration
	|	';'
	|	asmDefinition	// GNU
        ;

asmDefinition	// GNU
	:	simpleAsmExpr
	;

simpleAsmExpr	// GNU
	:	('asm'|'__asm'|'__asm__') '(' asmStringLiteral ')'
	;
	
asmStringLiteral // GNU
	:	sTRING_LITERAL
	;

functionDefinition						// (6.9.1)
scope Symbols; // // put parameters and locals into same scope for now
@init { $Symbols::types = new HashSet(); }
	:	declarationSpecifiers declarator 
		(	compoundStatement		// ANSI style
		|	declaration+ compoundStatement	// K&R style
		)
	;

nestedFunctionDefinition	// GNU C
scope Symbols; // // put parameters and locals into same scope for now
@init { $Symbols::types = new HashSet(); }
	:	declarationSpecifiers declarator declaration* compoundStatement
	;


// A.2.2 Declarations

declaration		// (6.7)
scope { boolean isTypedef; }
@init { $declaration::isTypedef = false; }
	:	'typedef' declarationSpecifiers? {$declaration::isTypedef=true;}  initDeclaratorList ';'  // special case, looking for typedef	
	|	declarationSpecifiers initDeclaratorList? ';'
	;
	
declarationSpecifiers	// (6.7)
	:	( storageClassSpecifier
		| typeSpecifier
		| typeQualifier
		| functionSpecifier 
		| attributes)+
	;

initDeclaratorList
	:	initDeclarator ( ',' initDeclarator)*
	;
	
initDeclarator		// (6.7)
	:	declarator simpleAsmExpr? attributes? ( '=' initializer )?  // simpleAsmExpr is GNU
	;
	
storageClassSpecifier	// (6.7.1)
// typedef covered by a special case in "declaration"
	:	'extern'
	|	'static'
	|	'auto'
	|	'register'
	|	'__thread'		// GNU
	;
	
typeSpecifier		// (6.7.2)
        :
        (       'void'
        |       'char'
        |       'short'
        |       'int'
        |       'long'
        |       'float'
        |       'double'
        |       'signed'
        |       '__signed'		// GNU
        |       '__signed__'		// GNU
        |       'unsigned'
        |	'_Bool'
        |	'_Complex'
        |	'__complex'		// GNU
        |	'__complex__'		// GNU
        |	'_Imaginary'		// TODO removed in C99 TC2
        |       structOrUnionSpecifier
        |       enumSpecifier
        |       typedefName
        |	typeofSpecifier		// GNU
        )
        ;

structOrUnionSpecifier // (6.7.2.1)
options{k=3;}
scope Symbols; // structs are scopes
@init { $Symbols::types = new HashSet(); }
	:	structOrUnion attributes? IDENTIFIER? '{' structDeclaration* '}' attributes?
	|	structOrUnion attributes? IDENTIFIER
	;
	
structOrUnion		// (6.7.2.1)
	:	'struct'
	|	'union'
	;

// for now we ignore missing semicolon at the end or extra semicolons TODO
structDeclaration	// (6.7.2.1)
	:	specifierQualifier+ structDeclaratorList? ';'
	;

	
specifierQualifier
	:	typeSpecifier
	|	typeQualifier
	|	attributes
	;
	
structDeclaratorList
	:	structDeclarator ( ',' attributes? structDeclarator )*
	;
	
structDeclarator
	:	declarator ( ':' constantExpression )? attributes?
	|	':' constantExpression attributes?
	;
	
enumSpecifier
options {k=3;}		// TODO
	:	'enum' attributes? '{' enumeratorList ( ',' )? '}'
	|	'enum' attributes? IDENTIFIER '{' enumeratorList ( ',' )? '}'
	|	'enum' attributes? IDENTIFIER
	;
	
enumeratorList
	:	enumerator ( ',' enumerator )*
	;

enumerator
	:	IDENTIFIER  ( '=' constantExpression )?
	;
	
typeQualifier
        :       'const'
        |	'__const'	// GNU
        |	'__const__'	// GNU
	|	'restrict'
	|	'__restrict'	// GNU
	|	'__restrict__'	// GNU
        |       'volatile'
        |       '__volatile'	// GNU
        |       '__volatile__'	// GNU
        ;

functionSpecifier
	:	'inline'
	|	'__inline'	// GNU
	|	'__inline__'	// GNU
	;
	
declarator
	:	pointer? directDeclarator
	;

directDeclarator
	:	 ( IDENTIFIER
			{
			if ($declaration.size()>0&&$declaration::isTypedef) {
				$Symbols::types.add($IDENTIFIER.text);
//				System.out.println("define type "+$IDENTIFIER.text);
//				System.out.println($IDENTIFIER.text);
				System.out.println("typedef int "+$IDENTIFIER.text+";");
			}
			}
	|	'(' attributes? declarator ')'
		)
		{if ($declaration.size()>0) { $declaration::isTypedef=false;}} // fix ugly hack from getting parameters into types TODO
	( 	'[' typeQualifier* assignmentExpression? ']'
	|	'[' 'static' typeQualifier* assignmentExpression ']'
	|	'[' typeQualifier+ 'static' assignmentExpression ']'
	|	'[' typeQualifier* '*' ']'
	|	'(' parameterTypeList ')'
	|	'(' identifierList? ')'
	)*
	;
	
pointer
	:	'*' typeQualifier* pointer?
	;
	
parameterTypeList
	:	parameterList ( ',' '...' )?
	;

parameterList
	:	parameterDeclaration ( ',' parameterDeclaration )*
	;
	
parameterDeclaration
	:	declarationSpecifiers (declarator|abstractDeclarator?) attributes?
	;

identifierList
	:	IDENTIFIER (',' IDENTIFIER)*
	;

typeName
	:	specifierQualifier+ abstractDeclarator?
	;

abstractDeclarator
	:	pointer directAbstractDeclarator?
	|	directAbstractDeclarator
	;

directAbstractDeclarator
	:	( '(' attributes? abstractDeclarator ')' )?
		( '[' assignmentExpression ']'
		| '[' '*' ']'
		| '(' parameterTypeList? ')'
		)
	;

typedefName
	:	{isTypeName(input.LT(1).getText())}? IDENTIFIER
	;

typeofSpecifier
	:	('typeof'|'__typeof'|'__typeof__') '(' (expression | typeName) ')'
	;
	
initializer
	:	assignmentExpression
	|	'{' '}'				// GNU
	|	'{' initializerList ','? '}'
	;

initializerList
	:	(designation? initializer)+
	;

designation
	:	arrayDesignator			// GNU
	|	IDENTIFIER ':'			// GNU
	|	designator+ '='
	;
	
designator
	:	'[' constantExpression ']'
	|	'.' IDENTIFIER
	;

arrayDesignator					// GNU
	:	'[' constantExpression '...' constantExpression ']'
	;

attributes
	:	attribute+
	;

attribute
	:	('__attribute'|'__attribute__') '(' '(' attributeList ')' ')'
	;

attributeList
	:	attrib (',' attrib)*
	;

attrib		// taken from GnuCParser.g (Monty Zukowski)
	:     ( ~('('| ')'| ',')
	|	'(' attributeList ')' )*	
	;
	
// A.2.1 Expressions

primaryExpression
	:	IDENTIFIER
	|	CONSTANT
	|	sTRING_LITERAL
	|	'(' compoundStatement ')'
	|	'(' expression ')'
	|	'__builtin_offsetof' '(' typeName ',' offsetofMemberDesignator ')'
	;

offsetofMemberDesignator
	:     IDENTIFIER ( ('.' IDENTIFIER) | ('[' expression ']') )*
	;
	
	
postfixExpression
	:	
	(	primaryExpression
	|	'(' typeName ')' '{' initializerList ','? '}'
	)
	(	'[' expression ']'
        |	'(' argumentExpressionList? ')'
        |	'.' IDENTIFIER
        |	'*' IDENTIFIER
        |	'->' IDENTIFIER
        |	'++'
        |	'--'
        )*
	;
	
argumentExpressionList
	:	assignmentExpression (',' assignmentExpression)*
	;
	
unaryExpression
	:	postfixExpression
	|	'++' unaryExpression
	|	'--' unaryExpression
	|	unaryOperator castExpression
	|	'sizeof' unaryExpression
	|	'sizeof' '(' typeName ')'
	|	('__alignof'|'__alignof__') unaryExpression
	|	('__alignof'|'__alignof__') '(' typeName ')'
	;

unaryOperator
	:	'&'
	|	'*'
	|	'+'
	|	'-'
	|	'~'
	|	'!'
	|	'__real'
	|	'__real__'
	|	'__imag'
	|	'__imag__'
	;
	
castExpression
	:	('(' typeName ')') => '('typeName ')' ( castExpression | '{' initializerList ','? '}')
	|	unaryExpression
	;


multiplicativeExpression
	:	castExpression (('*'|'/'|'%') castExpression)*
	;

additiveExpression
	:	multiplicativeExpression (('+'|'-') multiplicativeExpression)*
	;

shiftExpression
	:	additiveExpression (('<<'|'>>') additiveExpression)*
	;

relationalExpression
	:	shiftExpression (('<'|'>'|'<='|'>=') shiftExpression)*
	;

equalityExpression
	:	relationalExpression (('=='|'!=') relationalExpression)*
	;

andExpression
	:	equalityExpression ('&' equalityExpression)*
	;

xorExpression
	:	andExpression ('^' andExpression)*
	;
	
orExpression
	:	xorExpression ('|' xorExpression)*
	;
	
landExpression
	:	orExpression ('&&' orExpression)*
	;
	
lorExpression
	:	landExpression ('||' landExpression)*
	;
	
conditionalExpression
	:	lorExpression ('?' expression? ':' conditionalExpression)?  // expression optional in GNU C
	;
	
assignmentExpression
// ORIG
//	:	unaryExpression assignmentOperator assignmentExpression -> ^(ASSIGNMENT_EXPRESSION assignmentOperator unaryExpression assignmentExpression)
//	|	conditionalExpression 
// THIS IS A GCC-STYLE HACK TO GET IT REASONABLY WORKING DOES NOT CATCH EVERYTHING, BUT SHOULD BE OK
	:	conditionalExpression (assignmentOperator assignmentExpression|) 
	;

assignmentOperator
	:	'='
	|	'*='
	|	'/='
	|	'%='
	|	'+='
	|	'-='
	|	'<<='
	|	'>>='
	|	'&='
	|	'^='
	|	'|='
	;

expression
	:	assignmentExpression (',' assignmentExpression)*
	;
	
constantExpression
	:	conditionalExpression
	;
	
// A.2.3 Statements
statement
	:	labeledStatement
	|	compoundStatement
	|	expressionStatement
	| 	selectionStatement
	|	iterationStatement
	|	jumpStatement
	|	asmStatement		// GNU
	;
	
labeledStatement
	:	IDENTIFIER ':' attributes? statement
	|	'case' constantExpression ('...' constantExpression)? ':' statement // '...' is GNU
	|	'default' ':' statement
	;

compoundStatement
scope Symbols; // blocks are scopes
@init { $Symbols::types = new HashSet(); }
	:	'{' (declaration | nestedFunctionDefinition |statement)* '}' // nestedFD = GNU C
	;
	
expressionStatement
	:	expression? ';'
	;
	
selectionStatement
	:	'if' '(' expression ')' statement (options {k=1; backtrack=false;}:'else' statement)?
	|	'switch' '(' expression ')' statement
	;
	
iterationStatement
	:	'while' '(' expression ')' statement
	|	'do' statement 'while' '(' expression ')' ';'
	|	'for' '(' expression? ';' expression? ';' expression? ')' statement
	|	'for' '(' declaration expression? ';' expression? ')' statement
	;

jumpStatement
	:	'goto' IDENTIFIER ';'
	|	'continue' ';'
	|	'break' ';'
	|	'return' expression? ';'
	;
	
asmStatement	// GNU
	:	('asm'|'__asm'|'__asm__') typeQualifier? '(' asmArgument ')' ';'
	;

asmArgument	// GNU
	:	asmStringLiteral (':' asmOperands? (':' asmOperands? (':' asmClobbers)?)?)?
	;
	
asmOperands	// GNU
	:	asmOperand (',' asmOperand)*
	;
     
asmOperand	// GNU
	:	asmStringLiteral '(' expression ')'
	|	'[' IDENTIFIER ']' asmStringLiteral '(' expression ')'	
	;
	
asmClobbers	// GNU
	:	asmStringLiteral (',' asmStringLiteral)*
	;


// ugly hack to get string concatenation TODO to ANSI C99

sTRING_LITERAL	
	:	STRING_LITERAL+
	;

	
// Lexical grammar

// A.1.3 Identifiers

EXTENSION	// GNU
	:	'__extension__' {$channel=HIDDEN;}	// just ignore - see 5.28
	;

IDENTIFIER
	:	NonDigit (NonDigit|Digit)*
	;
	
fragment
NonDigit
	:	'_'
	|	'a'..'z'
	|	'A'..'Z'
	|	'$'			// GNU
	;
	
fragment
Digit
	:	'0'..'9'
	;
	
// A.1.4 Universal character names

// A.1.5 Constants

// A.1.6 String literals

STRING_LITERAL
	:	'L'? '"' (EscapeSequence | ~('"'|'\\'))* '"'
	;

CHARACTER_LITERAL
	:	'L'? '\'' (EscapeSequence | ~('"'|'\\'))* '\''
	;


fragment
HexDigit
	:	'0'..'9'|'a'..'f'|'A'..'F'
	;

fragment 
Sign
	:	'+'
	|	'-'
	;

fragment
EscapeSequence
	:	'\\' ('\''|'"'|'?'|'\\'|'a'|'b'|'e'|'f'|'n'|'r'|'t'|'v')	// '\e' is GNU
	|	OctalEscape
	|	HexadecimalEscape
	|	UniversalCharacterName
	;

fragment
OctalEscape
	:	'\\' ('0'..'3') ('0'..'7') ('0'..'7')
	|	'\\' ('0'..'7') ('0'..'7')
	|	'\\' ('0'..'7')
	;

fragment
HexadecimalEscape 
	:	'\\' 'x' HexDigit+
	;

fragment
UniversalCharacterName
	:	'\\' 'u' HexDigit HexDigit HexDigit HexDigit
	|	'\\' 'U' HexDigit HexDigit HexDigit HexDigit HexDigit HexDigit HexDigit HexDigit
	;

CONSTANT
	:	DecimalConstant IntegerSuffix?
	|	OctalConstant IntegerSuffix?
	|	HexadecimalConstant IntegerSuffix?
	|	DecimalFloatingConstant
	|	HexadecimalFloatingConstant
	|	IDENTIFIER
	|	CHARACTER_LITERAL
	;

fragment
IntegerSuffix
	:	('u'|'U') ('l'|'L')?
	|	('u'|'U') ('ll'|'LL')
	|	('l'|'L') ('u'|'U')? 
	|	('ll'|'LL') ('u'|'U')?
	|	'i'|'j'
	|	'I'|'J'
	;

fragment
DecimalConstant
	:	'1'..'9' ('0'..'9')*
	;

fragment
OctalConstant
	:	'0' ('0'..'7')*
	;
	
fragment
HexadecimalConstant
	:	'0' ('x'|'X') HexDigit+
	;
	
fragment
DecimalFloatingConstant
	:	('0'..'9')* '.' ('0'..'9')+ ExponentPart? FloatingSuffix?
	|	('0'..'9')+ '.'             ExponentPart? FloatingSuffix?
	|	('0'..'9')+                 ExponentPart  FloatingSuffix?
	;

fragment
ExponentPart
	:	('e'|'E') Sign? ('0'..'9')+
	;
	
fragment
HexadecimalFloatingConstant
	:	'0' ('x'|'X') HexDigit* '.' HexDigit+ BinaryExponentPart FloatingSuffix?
	|	'0' ('x'|'X') HexDigit+ '.'           BinaryExponentPart FloatingSuffix?
	|	'0' ('x'|'X') HexDigit+               BinaryExponentPart FloatingSuffix?
	;

fragment
BinaryExponentPart
	:	('p'|'P') Sign? ('0'..'9')+
	;

fragment
FloatingSuffix
	:	'f'|'l'|'F'|'L'
	|	'i'|'j'
	|	'I'|'J'
	;	

// Comments - not in the grammar?

WS  :  (' '|'\r'|'\t'|'\u000C'|'\n') {$channel=HIDDEN;}
    ;

COMMENT
    :   '/*' ( options {greedy=false;} : . )* '*/' {$channel=HIDDEN;}
    ;

LINE_COMMENT
    : '//' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;}
    ;

// ignore #line info for now
LINE_COMMAND 
    : '#' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;}
    ;


/*%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

GNUC grammar for ANTLR v3
Based on ANSIC99 grammar by Jan Obdrzalek
Some bits taken from C.g by Terence Parr

AUTHOR:		Jan Obdrzalek, Jul 07, 2007

TODO:
	- improve alternate keywords handling
	- check 'I','J' imaginary suffixes
	- finish string literal concatenation
	- attributes in typeQualifierList
	- fix the externalDefinition prediction
	- fix 'extern __typeof__' bug

	- remove ambiguities (curently handled by backtracking)
	  o attributes at more than one place
	  o declarator|abstractDeclarator in parameterDeclaration (MANY)
		
	5.4 Nested Functions
	  o are (almost) the same as declarations

IMPLEMENTED EXTENSIONS:
	(as of 4.1.0 GCC manual)
	5.1 Statements and Declarations in Expressions
	5.2 Locally Declared Labels
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

COMMENTS:
	- expression/typeName conflicts are sorted by using semantic predicates
	  we always try '(' typename ')' first, as it should have a shorter derivation

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%*/


grammar GNUCa;
options {
	backtrack=true;
	memoize=true;
	k=2;
	output = AST;
}

tokens{
	BRACKET_DESIGNATOR;
	SIGNED;
	E1;
	E2;
	E3;
	EXPRESSION_STATEMENT;
	ASM;
	PP;
	MM;
	PU;
	MU;
	XU;
	AU;
	LABREF;
	BUILTIN_OFFSETOF;
	TYPEDEF_NAME;
	TYPEOF;
	ASSIGNMENT_EXPRESSION;
	TYPE_NAME;
	FUNCTION_DECLARATOR;
	ARRAY_DECLARATOR;
	INITIALIZER;
	DESIGNATOR;
	ENUMERATOR;
	TRANSLATION_UNIT;
	COMPOUND_LITERAL;
	ARRAY_ACCESS;
	PARAMETER;
	VARARGS;
	DECLARATOR;
	POINTER;
	STRUCT_DECLARATOR;
	STRUCT_DECLARATION;
	FUNCTION_DEFINITION;
	XID;
	XTYPE_SPECIFIER;
	XTYPE_QUALIFIER;
	XSTORAGE_CLASS;
	DECLARATION;
	INIT_DECLARATOR;
	DECLARATION_SPECIFIERS;
	BASETYPE;
	CAST_EXPRESSION;
	CONDITIONAL_EXPRESSION;
	LABEL;
	COMPOUND_STATEMENT;
	ALIGNOF='alignof';
	FUNCTION_CALL;
	STR_LITERAL;
}

scope Symbols {
	Set types; // only track types in order to get parser working
}

@header{
	package cz.muni.stanse.c2xml;
	import java.util.Set;
	import java.util.HashSet;
}

@lexer::header{
	package cz.muni.stanse.c2xml;
}

@members{
	boolean isTypeName(String name) {
		for (int i = Symbols_stack.size()-1; i>=0; i--) {
			Symbols_scope scope = (Symbols_scope)Symbols_stack.get(i);
			if ( scope.types.contains(name) ) {
				return true;
			}
		}
		return false;
	}
	public String getErrorMessage(RecognitionException e, String[] tokenNames) {
		List stack = getRuleInvocationStack(e, this.getClass().getName());
		String msg = null;
		if ( e instanceof NoViableAltException ) {
			NoViableAltException nvae = (NoViableAltException)e;
			msg = " no viable alt; token="+e.token+
				" (decision="+nvae.decisionNumber+
				" state "+nvae.stateNumber+")"+
				" decision=<<"+nvae.grammarDecisionDescription+">>";
		} else {
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
        :	externalDeclaration* -> ^(TRANSLATION_UNIT externalDeclaration*)				// Empty source files = GNU
        ;


//
externalDeclaration
options {k=1;}
	:	( declarationSpecifiers declarator ('{' | storageClassSpecifier | typeSpecifier | typeQualifier | functionSpecifier| '__attribute' | '__attribute__') )=> functionDefinition	// (6.9.1)
	|	declaration
	|	';'!
	|	asmDefinition	// GNU
        ;

asmDefinition	// GNU
	:	simpleAsmExpr
	;

simpleAsmExpr	// GNU
	:	('asm'|'__asm'|'__asm__') '(' asmStringLiteral ')' -> // null // ^('asm' asmStringLiteral)
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
		-> ^(FUNCTION_DEFINITION declarationSpecifiers declarator declaration* compoundStatement)
	;

nestedFunctionDefinition	// GNU C
scope Symbols; // // put parameters and locals into same scope for now
@init { $Symbols::types = new HashSet(); }
	:	declarationSpecifiers declarator declaration* compoundStatement
		-> ^(FUNCTION_DEFINITION declarationSpecifiers declarator declaration* compoundStatement)
	;


// A.2.2 Declarations

declaration		// (6.7)
scope { boolean isTypedef; }
@init { $declaration::isTypedef = false; }
	:	'typedef' declarationSpecifiers? {$declaration::isTypedef=true;}  initDeclaratorList? ';' -> ^('typedef' declarationSpecifiers? initDeclaratorList?)
		  // special case, looking for typedef
	|	declarationSpecifiers initDeclaratorList? ';' -> ^(DECLARATION declarationSpecifiers initDeclaratorList?)
	;

declarationSpecifiers	// (6.7)
	:	( sc+=storageClassSpecifier
		| typeSpecifier
		| typeQualifier
		| sc+=functionSpecifier
			| attributes)+ -> ^(DECLARATION_SPECIFIERS ^(XTYPE_SPECIFIER typeSpecifier*) ^(XTYPE_QUALIFIER typeQualifier*) ^(XSTORAGE_CLASS $sc*))
	;

initDeclaratorList
	:	initDeclarator ( ',' initDeclarator)* -> initDeclarator+
	;

initDeclarator		// (6.7)
	:	declarator simpleAsmExpr? attributes? ( '=' initializer )?  -> ^(INIT_DECLARATOR declarator initializer?)
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
        :       'void'			-> ^(BASETYPE 'void')
        |       'char'			-> ^(BASETYPE 'char')
        |       'short'			-> ^(BASETYPE 'short')
        |       'int'			-> ^(BASETYPE 'int')
        |       'long'			-> ^(BASETYPE 'long')
        |       'float'			-> ^(BASETYPE 'float')
        |       'double'		-> ^(BASETYPE 'double')
        |       'signed'		-> ^(BASETYPE SIGNED)			// TODO
        |       '__signed'		-> ^(BASETYPE SIGNED)			// TODO
        |       '__signed__'		-> ^(BASETYPE SIGNED)			// TODO
        |       'unsigned'		-> ^(BASETYPE 'unsigned')
        |	'_Bool'			-> ^(BASETYPE '_Bool')
        |	'_Complex'		-> ^(BASETYPE '_Complex')
        |	'__complex'		-> ^(BASETYPE XID)		// TODO
        |	'__complex__'		-> ^(BASETYPE XID)		// TODO
        |	'_Imaginary'		-> ^(BASETYPE '_Imaginary')		// TODO removed in C99 TC2
        |       structOrUnionSpecifier
        |       enumSpecifier
        |       typedefName
        |	typeofSpecifier
        ;

structOrUnionSpecifier // (6.7.2.1)
options{k=3;}
scope Symbols; // structs are scopes
@init { $Symbols::types = new HashSet(); }
	:	structOrUnion attributes? IDENTIFIER? '{' structDeclaration* '}' attributes? -> ^(structOrUnion ^(XID IDENTIFIER?) structDeclaration* )
	|	structOrUnion attributes? IDENTIFIER -> ^(structOrUnion ^(XID IDENTIFIER))
	;

structOrUnion		// (6.7.2.1)
	:	'struct'
	|	'union'
	;

// for now we ignore missing semicolon at the end TODO
structDeclaration	// (6.7.2.1)
	:	specifierQualifier+ structDeclaratorList? ';' -> ^(STRUCT_DECLARATION specifierQualifier+ structDeclaratorList?)
	|	';'!
	;


specifierQualifier // TODO AST
	:	typeSpecifier -> ^(XTYPE_SPECIFIER typeSpecifier)
	|	typeQualifier -> ^(XTYPE_QUALIFIER typeQualifier)
	|	attributes -> // NULL
	;

structDeclaratorList
	:	structDeclarator ( ',' attributes? structDeclarator )* -> structDeclarator+
	;

structDeclarator
	:	declarator ( ':' constantExpression )? attributes? -> ^(STRUCT_DECLARATOR declarator constantExpression?)
	|	':' constantExpression attributes? -> ^(STRUCT_DECLARATOR constantExpression)
	;

enumSpecifier // TODO improve the grammar
	:	'enum' attributes?
	(
		'{' enumeratorList ( ',' )? '}' -> ^('enum' enumeratorList)
	|	IDENTIFIER '{' enumeratorList ( ',' )? '}' -> ^('enum' ^(XID IDENTIFIER) enumeratorList)
	|	IDENTIFIER -> ^('enum' ^(XID IDENTIFIER))
	)
// orig:
//	:	'enum' attributes? '{' enumeratorList ( ',' )? '}' -> ^('enum' enumeratorList)
//	|	'enum' attributes? IDENTIFIER '{' enumeratorList ( ',' )? '}' -> ^('enum' ^(XID IDENTIFIER) enumeratorList)
//	|	'enum' attributes? IDENTIFIER -> ^('enum' ^(XID IDENTIFIER))
	;

enumeratorList
	:	enumerator ( ',' enumerator )* -> enumerator+
	;

enumerator
	:	IDENTIFIER  ( '=' constantExpression )? -> ^(ENUMERATOR IDENTIFIER constantExpression?)
	;

typeQualifier
        :       'const'
        |	'__const'	-> 'const'
        |	'__const__'	-> 'const'
	|	'restrict'
	|	'__restrict'	-> 'restrict'
	|	'__restrict__'	-> 'restrict'
        |       'volatile'
        |       '__volatile'	-> 'volatile'
        |       '__volatile__'	-> 'volatile'
        ;

functionSpecifier
	:	'inline'
	|	'__inline'	-> 'inline'
	|	'__inline__'	-> 'inline'
	;

declarator
	:	pointer? directDeclarator -> ^(DECLARATOR pointer? directDeclarator)
	;

directDeclarator
	:	 ( IDENTIFIER
			{
//			System.err.println("T: ID="+$IDENTIFIER.text+", size="+$declaration.size());
			if ($declaration.size()>0&&$declaration::isTypedef) {
				$Symbols::types.add($IDENTIFIER.text);
//				System.err.println("define type "+$IDENTIFIER.text);
			}
			} -> IDENTIFIER
	|	'(' attributes? declarator ')' -> declarator
		)
	//  ugly hack, prevents getting function parameters into types
	{if ($declaration.size()>0) { $declaration::isTypedef=false;}}
	( 	'[' { list_tq = null; } tq+=typeQualifier* ae=assignmentExpression? ']' -> ^(ARRAY_DECLARATOR $directDeclarator $tq* $ae?)
	|	'[' { list_tq = null; } 'static' tq+=typeQualifier* ae=assignmentExpression ']' -> ^(ARRAY_DECLARATOR $directDeclarator 'static' $tq* $ae)
	|	('[' typeQualifier+ 'static') => '[' { list_tq = null; } tq+=typeQualifier+ 'static' ae=assignmentExpression ']' -> ^(ARRAY_DECLARATOR $directDeclarator 'static' $tq+ $ae)
	|	('[' typeQualifier* '*' ']') => '[' { list_tq = null; }  tq+=typeQualifier* '*' ']' -> ^(ARRAY_DECLARATOR $directDeclarator '*' $tq*)
		// LPAREN ID (COMMA | RPAREN) is identifierList. Lookahead k=3 should work, but does not :(
		// adding a syntactic predicate
	|	'(' parameterTypeList ')' -> ^(FUNCTION_DECLARATOR $directDeclarator parameterTypeList)
	|	('(' IDENTIFIER (',' | ')')) => '(' identifierList? ')' -> ^(FUNCTION_DECLARATOR $directDeclarator identifierList?)
	)*
	;

pointer
	:	'*' typeQualifier* pointer? -> ^(POINTER typeQualifier* pointer?) // TODO AST
	;

parameterTypeList
	:	(parameterList -> parameterList) ((',' '...') -> parameterList VARARGS)?
	;

parameterList
	:	parameterDeclaration ( ',' parameterDeclaration )* -> parameterDeclaration+
	;

parameterDeclaration
	:	declarationSpecifiers (declarator|abstractDeclarator?) attributes? -> ^(PARAMETER declarationSpecifiers declarator? abstractDeclarator? )
	;

identifierList
	:	IDENTIFIER (',' IDENTIFIER)* -> ^(PARAMETER IDENTIFIER)+
	;

typeName
	:	specifierQualifier+ abstractDeclarator? -> ^(TYPE_NAME specifierQualifier+ abstractDeclarator?)
	;

abstractDeclarator // TODO AST
	:	pointer directAbstractDeclarator?
	|	directAbstractDeclarator
	;

directAbstractDeclarator
	:	'(' attributes? abstractDeclarator ')'
		( '[' assignmentExpression? ']' -> ^(ARRAY_DECLARATOR abstractDeclarator assignmentExpression?)
		| ('[' '*' ']') => '[' '*' ']' -> ^(ARRAY_DECLARATOR abstractDeclarator '*')
		| '(' parameterTypeList? ')'  ->  ^(FUNCTION_DECLARATOR abstractDeclarator parameterTypeList?)
		)?
	|	'[' assignmentExpression? ']' -> ^(ARRAY_DECLARATOR assignmentExpression?)
	|	('[' '*' ']') => '[' '*' ']' -> ^(ARRAY_DECLARATOR '*')
	|	'(' parameterTypeList? ')'  ->  ^(FUNCTION_DECLARATOR parameterTypeList?)
	;

typedefName
	:	{isTypeName(input.LT(1).getText())}? IDENTIFIER
	;

typeofSpecifier
	:	('typeof'|'__typeof'|'__typeof__') '(' (expression -> ^(TYPEOF expression)| typeName -> ^(TYPEOF typeName)) ')'
	;

initializer
	:	assignmentExpression	-> ^(INITIALIZER assignmentExpression)
	|	'{' '}'			-> ^(INITIALIZER)				// GNU
	|	'{' initializerList ','? '}' -> ^(INITIALIZER initializerList)
	;

initializerList
	:	designation? initializer (',' (designation? initializer))* -> (designation? initializer)+
	;

designation
	// this semantic predicate may be expensive, backtrack+memmoize are probably better
	:	('[' constantExpression '...')=> arrayDesignator -> //null		// GNU
	|	IDENTIFIER ':'!			// GNU
	|	designator+ '=' -> ^(DESIGNATOR designator)+
	;

designator	// TODO AST
	:	'[' constantExpression ']' -> ^(BRACKET_DESIGNATOR constantExpression)
	|	'.'! IDENTIFIER
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
	|	'('! compoundStatement ')'!
	|	'('! expression ')'!
	|	'__builtin_offsetof' '(' typeName ',' offsetofMemberDesignator ')' -> ^(BUILTIN_OFFSETOF typeName offsetofMemberDesignator)
	;

offsetofMemberDesignator
	:     IDENTIFIER ( ('.' IDENTIFIER) | ('[' expression ']') )*
	;


postfixExpression
	:
	(	('(' typeName ')') => '(' typeName ')' '{' initializerList ','? '}' -> ^(COMPOUND_LITERAL typeName initializerList)
	|	primaryExpression -> primaryExpression
	)
	(	'[' expression ']' -> ^(ARRAY_ACCESS $postfixExpression expression)
        |	'(' argumentExpressionList? ')' -> ^(FUNCTION_CALL $postfixExpression argumentExpressionList?)
        |	'.' IDENTIFIER -> ^('.' $postfixExpression IDENTIFIER)
        |	'->' IDENTIFIER -> ^('->' $postfixExpression IDENTIFIER)
        |	'++' -> ^(PP $postfixExpression)
        |	'--' -> ^(MM $postfixExpression)
        )*
	;

argumentExpressionList
	:	assignmentExpression (',' assignmentExpression)* -> assignmentExpression+
	;

unaryExpression
	:	postfixExpression
	|	'++'^ unaryExpression
	|	'--'^ unaryExpression
	|	unaryOperator^ castExpression
	|	('sizeof' '(' typeName ')') => 'sizeof'^ '('! typeName ')'!
	|	'sizeof'^ unaryExpression
	|	(('__alignof'|'__alignof__') '(' typeName ')')=> ('__alignof'|'__alignof__') '(' typeName ')' -> ^('__alignof__' typeName)
	|	('__alignof'|'__alignof__') unaryExpression -> ^('__alignof__' unaryExpression)
	;

unaryOperator
	:	'&' -> AU
	|	'*' -> XU
	|	'+' -> PU
	|	'-' -> MU
	|	'~'
	|	'!'
	|	'&&' -> LABREF
	|	'__real' -> '__real__'
	|	'__real__'
	|	'__imag' -> '__imag__'
	|	'__imag__'
	;

castExpression
// ORIG: 	'(' typeName ')' castExpression
// gets rid of a lot of backtracking
// GNUC-like
	:	('(' typeName ')') => '('typeName ')' ( castExpression  -> ^(CAST_EXPRESSION typeName castExpression)| '{' initializerList ','? '}' -> ^(COMPOUND_LITERAL typeName initializerList))
	|	unaryExpression
	;


multiplicativeExpression
	:	castExpression (('*'|'/'|'%')^ castExpression)*
	;

additiveExpression
	:	multiplicativeExpression (('+'|'-')^ multiplicativeExpression)*
	;

shiftExpression
	:	additiveExpression (('<<'|'>>')^ additiveExpression)*
	;

relationalExpression
	:	shiftExpression (('<'|'>'|'<='|'>=')^ shiftExpression)*
	;

equalityExpression
	:	relationalExpression (('=='|'!=')^ relationalExpression)*
	;

andExpression
	:	equalityExpression ('&'^ equalityExpression)*
	;

xorExpression
	:	andExpression ('^'^ andExpression)*
	;

orExpression
	:	xorExpression ('|'^ xorExpression)*
	;

landExpression
	:	orExpression ('&&'^ orExpression)*
	;

lorExpression
	:	landExpression ('||'^ landExpression)*
	;

conditionalExpression
	:	lorExpression ('?' expression? ':' conditionalExpression -> ^(CONDITIONAL_EXPRESSION ^(E1 lorExpression) ^(E2 expression?) ^(E3 conditionalExpression))| -> lorExpression) // TODO
	;

assignmentExpression
// ORIG
//	:	unaryExpression assignmentOperator assignmentExpression -> ^(ASSIGNMENT_EXPRESSION assignmentOperator unaryExpression assignmentExpression)
//	|	conditionalExpression
// THIS IS A GCC-STYLE HACK TO GET IT REASONABLY WORKING DOES NOT CATCH EVERYTHING, BUT SHOULD BE OK
	:	conditionalExpression (assignmentOperator assignmentExpression -> ^(ASSIGNMENT_EXPRESSION assignmentOperator conditionalExpression assignmentExpression)| -> conditionalExpression)
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
	:	assignmentExpression (','^ assignmentExpression)*
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
	:	IDENTIFIER ':' attributes? statement -> ^(LABEL IDENTIFIER attributes? statement)
	|	'case' constantExpression ('...' constantExpression)? ':' statement -> ^('case' constantExpression statement) // TODO '...' AST
	|	'default'^ ':'! statement
	;

compoundStatement
scope Symbols; // blocks are scopes
@init { $Symbols::types = new HashSet(); }
//	:	'{' (x+=declaration | x+=nestedFunctionDefinition | x+=statement)* '}' -> ^(COMPOUND_STATEMENT $x*)
	:	'{' blockItem* '}' -> ^(COMPOUND_STATEMENT blockItem*)
	|	'{' labelDeclaration+ blockItem* '}' -> ^(COMPOUND_STATEMENT blockItem*)	// GNU // TODO labels AST
	;

blockItem
	:	declaration
// TODO GNUC : dela velky bordel, je v zasade stejna jak declaration
//
//	|	nestedFunctionDefinition 
	|	statement
	;

labelDeclaration
	:	'__label__' identifierList ';'
	;

expressionStatement
	:	expression? ';' -> ^(EXPRESSION_STATEMENT expression?)
	;

selectionStatement
	:	'if' '(' expression ')' the=statement (options {k=1; backtrack=false;}:'else' els=statement)? -> ^('if' expression $the $els?)
	|	'switch' '(' expression ')' statement -> ^('switch' expression statement)
	;

iterationStatement
	:	'while' '(' expression ')' statement -> ^('while' expression statement)
	|	'do' statement 'while' '(' expression ')' ';' -> ^('do' statement expression)
	|	'for' '(' (declaration | e1=expression? ';') e2=expression? ';' e3=expression? ')' statement -> ^('for' declaration? ^(E1 $e1)? ^(E2 $e2?) ^(E3 $e3?) statement)

	;

jumpStatement
	:	'goto'^ IDENTIFIER ';'!
	|	'goto' '*' expression ';' -> ^('goto' XU expression)
	|	'continue' ';'!
	|	'break' ';'!
	|	'return'^ expression? ';'!
	;

asmStatement	// GNU
	:	('asm'|'__asm'|'__asm__') typeQualifier? '(' asmArgument ')' ';' -> ^(ASM) // TODO
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
	:	STRING_LITERAL+ -> ^(STR_LITERAL STRING_LITERAL+)
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

fragment
CharacterLiteral
	:	'L'? '\'' (EscapeSequence | ~('\\')) '\''
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
	|	CharacterLiteral
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


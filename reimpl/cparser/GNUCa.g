/*%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

GNUC grammar for ANTLR v3

AUTHOR:	Jan Obdrzalek, 2007-2009
	Jiri Slaby (modifications), 2008-2009
	Typedef handling taken from C.g distributed with ANTLR v3,  (c) Terence Parr

TODO:
	- 5.4 Nested Functions - (almost) the same as declarations
	- attributes

DISABLED ATTRIBUTE-RELATED EXTENSIONS:
	5.24 Declaring Attributes of Functions
	5.31 Specifying Attributes of Variables
	5.32 Specifying Attributes of Types
	- removed to get rid of backtracking
	- too ambiguous grammar, not very well documented
	- easily removed by the following macro: #define __attrobute__(x)
	- attributes in typeQualifierList not implemented

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
	5.25 Attribute Syntax
	5.26 Prototypes and Old-Style Function Definitions (IGNORED)
	5.27 C++ Style Comments	(C99)
	5.28 Dollar Signs in Identifier Names
	5.29 The Character <ESC> in Constants
	5.30 Inquiring on Alignment of Types or Variables
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
	- expression/typeName conflicts are sorted by using syntactic predicates
	  we always try '(' typename ')' first, as it should have a shorter derivation

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%*/


grammar GNUCa;
options {
	backtrack=false;
	memoize=true;
	k=2;
	output = AST;
	language=C;
	ASTLabelType = pANTLR3_BASE_TREE;
}

// list of imaginary nodes
tokens{
	BRACKET_DESIGNATOR;
	SIGNED;
	COMPLEX;
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
	ABSTRACT_DECLARATOR;
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
	CS_END;
	ALIGNOF='alignof';
	FUNCTION_CALL;
	STR_LITERAL;
}

scope Symbols {
	pANTLR3_HASH_TABLE types; // only track types in order to get parser working
}

scope Typedef {
	_Bool isTypedef;
}


@header{
#include "common.h"

#define HASH_SIZE	100
}

@lexer::header{
#include "common.h"
}

@members{
	int isTypeName(pGNUCaParser ctx, pANTLR3_UINT8 name)
	{
		int a;
		for (a = SCOPE_SIZE(Symbols) - 1; a >= 0; a--) {
			SCOPE_TYPE(Symbols) scope = SCOPE_INSTANCE(Symbols, a);
			pANTLR3_HASH_TABLE types = scope->types;
			pANTLR3_STRING symbol = types->get(types, name);
			if (symbol)
				return 1;
		}
		return 0;
	}
	void ANTLR3_CDECL freetypes(SCOPE_TYPE(Symbols) symtab)
	{
		symtab->types->free(symtab->types);
	}

	/* overtaken from antlr3collections.c */
	void dump_types(SCOPE_TYPE(Symbols) symtab)
	{
		pANTLR3_HASH_TABLE types = symtab->types;
		pANTLR3_HASH_BUCKET thisBucket;
		pANTLR3_HASH_ENTRY entry;
		ANTLR3_UINT32 bucket;

		for (bucket = 0; bucket < types->modulo; bucket++) {
			thisBucket = &types->buckets[bucket];

			if (!thisBucket)
				continue;

			entry = thisBucket->entries;

			while (entry) {
				pANTLR3_STRING symbol = entry->data;
				puts((char *)symbol->chars);
				entry = entry->nextEntry;
			}
		}
	}
}

/** The grammar starts here */

// A.2.4 External definitions

translationUnit							// (6.9)
scope Symbols; // entire file is a scope
@init { $Symbols::types = antlr3HashTableNew(HASH_SIZE); SCOPE_TOP(Symbols)->free = freetypes; }
        :	externalDeclaration* -> ^(TRANSLATION_UNIT externalDeclaration*)				// Empty source files = GNU
        ;

// left factoring - inlined parts of declaration and functionDefinition  to gain speed
externalDeclaration
scope Typedef;
@init { $Typedef::isTypedef = 0; }
	:	'typedef' declarationSpecifiers? {$Typedef::isTypedef = 1;} initDeclaratorList? ';' -> ^('typedef' declarationSpecifiers? initDeclaratorList?) // special case
	|	declarationSpecifiers
		(	';' -> ^(DECLARATION declarationSpecifiers)
		|	declarator declarationOrFnDef[$declarationSpecifiers.tree, $declarator.tree] -> declarationOrFnDef
		)
	|	';'!
	|	asmDefinition	// GNU
	;

declarationOrFnDef[pANTLR3_BASE_TREE ds, pANTLR3_BASE_TREE d]
	:	declarationSuffix[ds, d]
	|	functionDefinitionSuffix[ds, d]
	;

declarationSuffix[pANTLR3_BASE_TREE ds, pANTLR3_BASE_TREE d]
	:	simpleAsmExpr? ( '=' initializer )? ( ',' initDeclarator)* ';'
			-> ^(DECLARATION {$ds} ^(INIT_DECLARATOR {$d} initializer?) initDeclarator* )
	;

functionDefinitionSuffix[pANTLR3_BASE_TREE ds, pANTLR3_BASE_TREE d]
	:
	(	compoundStatement		// ANSI style
	|	declaration+ compoundStatement	// K&R style
	)	-> ^(FUNCTION_DEFINITION  {$ds} {$d} declaration* compoundStatement)
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

// A.2.2 Declarations

declaration		// (6.7)
scope Typedef;
@init { $Typedef::isTypedef = 0; }
	:	'typedef' declarationSpecifiers? {$Typedef::isTypedef=1;}  initDeclaratorList? ';' -> ^('typedef' declarationSpecifiers? initDeclaratorList?)
		  // special case, looking for typedef
	|	declarationSpecifiers initDeclaratorList? ';' -> ^(DECLARATION declarationSpecifiers initDeclaratorList?)
	;

declarationSpecifiers	// (6.7)
	:	( sc+=storageClassSpecifier
		| typeSpecifier
		| typeQualifier
		| fs+=functionSpecifier
		)+ -> ^(DECLARATION_SPECIFIERS ^(XTYPE_SPECIFIER typeSpecifier*) ^(XTYPE_QUALIFIER typeQualifier*) ^(XSTORAGE_CLASS $sc* $fs*))
	;

initDeclaratorList
	:	initDeclarator ( ',' initDeclarator)* -> initDeclarator+
	;

initDeclarator		// (6.7)
	:	d=declarator simpleAsmExpr? ( '=' initializer )? -> ^(INIT_DECLARATOR[$d.start] $d initializer?)
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
        |       s='signed'		-> ^(BASETYPE SIGNED[$s])
        |       s='__signed'		-> ^(BASETYPE SIGNED[$s])
        |       s='__signed__'		-> ^(BASETYPE SIGNED[$s])
        |       'unsigned'		-> ^(BASETYPE 'unsigned')
        |	'_Bool'			-> ^(BASETYPE '_Bool')
        |	c='_Complex'		-> ^(BASETYPE COMPLEX[$c])
        |	c='__complex'		-> ^(BASETYPE COMPLEX[$c])
        |	c='__complex__'		-> ^(BASETYPE COMPLEX[$c])
        |	'_Imaginary'		-> ^(BASETYPE '_Imaginary')		// TODO removed in C99 TC2
        |       structOrUnionSpecifier
        |       enumSpecifier
        |       typedefName
        |	typeofSpecifier
        ;

structOrUnionSpecifier // (6.7.2.1)
options{k=3;}
scope Symbols; // structs are scopes
@init { $Symbols::types = antlr3HashTableNew(HASH_SIZE); SCOPE_TOP(Symbols)->free = freetypes; }
	:	structOrUnion IDENTIFIER? '{' structDeclaration* '}' -> ^(structOrUnion ^(XID IDENTIFIER?) structDeclaration* )
	|	structOrUnion IDENTIFIER -> ^(structOrUnion ^(XID IDENTIFIER))
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
	;

structDeclaratorList
	:	structDeclarator ( ',' structDeclarator )* -> structDeclarator+
	;

structDeclarator
	:	declarator ( ':' constantExpression )? -> ^(STRUCT_DECLARATOR declarator constantExpression?)
	|	':' constantExpression -> ^(STRUCT_DECLARATOR constantExpression)
	;

enumSpecifier // TODO improve the grammar
	:	'enum'
	(
		'{' enumeratorList ( ',' )? '}' -> ^('enum' enumeratorList)
	|	IDENTIFIER '{' enumeratorList ( ',' )? '}' -> ^('enum' ^(XID IDENTIFIER) enumeratorList)
	|	IDENTIFIER -> ^('enum' ^(XID IDENTIFIER))
	)
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
	:	pointer? dd=directDeclarator -> ^(DECLARATOR[$dd.start] pointer? $dd)
	;

directDeclarator
@init {_Bool wasTypedef=0;}
@after {if (wasTypedef) $Typedef::isTypedef=1;}
	:	 ( IDENTIFIER
			{
			// $declaration.size() is 0 if declaration is currently not being evaluated
			if ($Typedef->size($Typedef)>0 && $Typedef::isTypedef) {
				pANTLR3_STRING idText = $IDENTIFIER.text;
				$Symbols::types->put($Symbols::types, idText->chars, idText, NULL);
			}
			} -> IDENTIFIER
	|	'(' declarator ')' -> declarator
		)
	// prevents getting function parameters into types
	{	if ($Typedef->size($Typedef)>0 && $Typedef::isTypedef) {
			$Typedef::isTypedef=0;
			wasTypedef=1;
		}
	}
	// left factoring array declarator
	// left factoring function declarator
	(	'['
		(	'static' tq=typeQualifier*  ae=assignmentExpression ']' -> ^(ARRAY_DECLARATOR $directDeclarator 'static' $tq* $ae)
		|	'*' ']' -> ^(ARRAY_DECLARATOR $directDeclarator '*')
		|	tq=typeQualifier+
			(	'*' ']' -> ^(ARRAY_DECLARATOR $directDeclarator '*' $tq+)
			|	'static' ae=assignmentExpression ']' -> ^(ARRAY_DECLARATOR $directDeclarator 'static' $tq+ $ae)
			|	ae=assignmentExpression? ']' -> ^(ARRAY_DECLARATOR $directDeclarator $tq+ $ae?)
			)
		|	ae=assignmentExpression? ']' -> ^(ARRAY_DECLARATOR $directDeclarator $ae?)
		)
	|	'(' // cannot do (input.LA(2)==',')||(input.LA(2)==')'), since values for ',' and ')' do not get expanded
		(	')' -> ^(FUNCTION_DECLARATOR $directDeclarator)	// K&R style, empty
		|	{(LA(1)==IDENTIFIER) && ((!strcmp((char *)LT(2)->getText(LT(2))->chars, ","))||(!strcmp((char *)LT(2)->getText(LT(2))->chars, ")"))) && !isTypeName(ctx, LT(1)->getText(LT(1))->chars)}?  identifierList ')' -> ^(FUNCTION_DECLARATOR $directDeclarator identifierList)
		|	parameterTypeList ')' -> ^(FUNCTION_DECLARATOR $directDeclarator parameterTypeList)
		)
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
	// syntactic predicate: declarator must end-up with an IDENTIFIER, abstract with pointer or '['
	//   TODO check for correctness
// the complicated rewrite of attributes? is necessary to remove ambiguities. If abstractDeclarator is empty, it should not have any attributes.
	:	declarationSpecifiers ( ( pointer? ('(' pointer?)* IDENTIFIER ) => declarator | abstractDeclarator ? ) -> ^(PARAMETER declarationSpecifiers declarator? abstractDeclarator? )
	;

identifierList
	:	IDENTIFIER (',' IDENTIFIER)* -> ^(PARAMETER IDENTIFIER)+
	;

typeName
	:	specifierQualifier+ abstractDeclarator? -> ^(TYPE_NAME specifierQualifier+ abstractDeclarator?)
	;

abstractDeclarator
	:	pointer directAbstractDeclarator? -> ^(ABSTRACT_DECLARATOR pointer directAbstractDeclarator?)
	|	directAbstractDeclarator -> ^(ABSTRACT_DECLARATOR directAbstractDeclarator)
	;

directAbstractDeclarator
	:	'('! abstractDeclarator ')'! arrayOrFunctionDeclarator*
	|	arrayOrFunctionDeclarator+
	;

arrayOrFunctionDeclarator
	:	'[' assignmentExpression? ']' -> ^(ARRAY_DECLARATOR assignmentExpression?)
	|	('[' '*' ']') => '[' '*' ']' -> ^(ARRAY_DECLARATOR '*')
	|	'(' parameterTypeList? ')'  ->  ^(FUNCTION_DECLARATOR parameterTypeList?)
	;

typedefName
	:	{isTypeName(ctx, LT(1)->getText(LT(1))->chars)}? IDENTIFIER
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
	:	designationInitializer (',' designationInitializer)* -> designationInitializer+
	;

// this was folded into initializerList, but it sucks, parsing lasts longer and makes crippled AST
designationInitializer:
	designation? initializer
	;

designation
	:	IDENTIFIER ':'!			// GNU
	|	designator+ '=' -> ^(DESIGNATOR designator)+
	;

designator
	// this semantic predicate may be expensive, backtrack+memmoize are probably better
	:	('[' constantExpression '...') => arrayDesignator
	|	'[' constantExpression ']' -> ^(BRACKET_DESIGNATOR constantExpression)
	|	'.'! IDENTIFIER
	;

arrayDesignator					// GNU
	:	'['! constantExpression '...'^ constantExpression ']'!
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
	|	constant
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
	:	('(' typeName ')') => '('typeName ')' ( castExpression  -> ^(CAST_EXPRESSION typeName castExpression)| '{' '}' -> ^(COMPOUND_LITERAL typeName) | '{' initializerList ','? '}' -> ^(COMPOUND_LITERAL typeName initializerList))
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
	|	selectionStatement
	|	iterationStatement
	|	jumpStatement
	|	asmStatement		// GNU
	;

labeledStatement
	:	IDENTIFIER ':' statement -> ^(LABEL IDENTIFIER statement)
	|	'case' constantExpression ('...' constantExpression)? ':' statement -> ^('case' constantExpression statement) // TODO '...' AST
	|	'default'^ ':'! statement
	;

compoundStatement
scope Symbols; // blocks are scopes
@init { $Symbols::types = antlr3HashTableNew(HASH_SIZE); SCOPE_TOP(Symbols)->free = freetypes; }
	:	'{' blockItem* end='}' -> ^(COMPOUND_STATEMENT CS_END[$end] blockItem*)
	|	'{' labelDeclaration+ blockItem* end='}' -> ^(COMPOUND_STATEMENT CS_END[$end] blockItem*)	// GNU // TODO labels AST
	;

blockItem
	:	declaration
// TODO GNUC
//	|	nestedFunctionDefinition
	|	statement
	;

labelDeclaration
	:	'__label__' identifierList ';'
	;

expressionStatement
	:	expression? sc=';' -> ^(EXPRESSION_STATEMENT[$sc] expression?)
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
	:	(a='asm'|a='__asm'|a='__asm__') typeQualifier? 'goto'? '(' asmArgument ')' ';' -> ^(ASM[$a]) // TODO
	;

asmArgument	// GNU
	:	asmStringLiteral (':' asmOperands? (':' asmOperands? (':' asmClobbers? (':' asmLabels)?)?)?)?
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

asmLabels	// GNU
	:	IDENTIFIER (',' IDENTIFIER)*
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

constant
	:	ICONSTANT
	|	RCONSTANT
	;

ICONSTANT
	:	DecimalConstant IntegerSuffix?
	|	OctalConstant IntegerSuffix?
	|	HexadecimalConstant IntegerSuffix?
	|	CharacterLiteral
	;

RCONSTANT
	:	DecimalFloatingConstant
	|	HexadecimalFloatingConstant
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


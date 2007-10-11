lexer grammar GNUCt;

T30 : ';' ;
T31 : 'asm' ;
T32 : '__asm' ;
T33 : '__asm__' ;
T34 : '(' ;
T35 : ')' ;
T36 : 'typedef' ;
T37 : ',' ;
T38 : '=' ;
T39 : 'extern' ;
T40 : 'static' ;
T41 : 'auto' ;
T42 : 'register' ;
T43 : '__thread' ;
T44 : 'void' ;
T45 : 'char' ;
T46 : 'short' ;
T47 : 'int' ;
T48 : 'long' ;
T49 : 'float' ;
T50 : 'double' ;
T51 : 'signed' ;
T52 : '__signed' ;
T53 : '__signed__' ;
T54 : 'unsigned' ;
T55 : '_Bool' ;
T56 : '_Complex' ;
T57 : '__complex' ;
T58 : '__complex__' ;
T59 : '_Imaginary' ;
T60 : '{' ;
T61 : '}' ;
T62 : 'struct' ;
T63 : 'union' ;
T64 : ':' ;
T65 : 'enum' ;
T66 : 'const' ;
T67 : '__const' ;
T68 : '__const__' ;
T69 : 'restrict' ;
T70 : '__restrict' ;
T71 : '__restrict__' ;
T72 : 'volatile' ;
T73 : '__volatile' ;
T74 : '__volatile__' ;
T75 : 'inline' ;
T76 : '__inline' ;
T77 : '__inline__' ;
T78 : '[' ;
T79 : ']' ;
T80 : '*' ;
T81 : '...' ;
T82 : 'typeof' ;
T83 : '__typeof' ;
T84 : '__typeof__' ;
T85 : '.' ;
T86 : '__attribute' ;
T87 : '__attribute__' ;
T88 : '__builtin_offsetof' ;
T89 : '->' ;
T90 : '++' ;
T91 : '--' ;
T92 : 'sizeof' ;
T93 : '__alignof' ;
T94 : '__alignof__' ;
T95 : '&' ;
T96 : '+' ;
T97 : '-' ;
T98 : '~' ;
T99 : '!' ;
T100 : '__real' ;
T101 : '__real__' ;
T102 : '__imag' ;
T103 : '__imag__' ;
T104 : '/' ;
T105 : '%' ;
T106 : '<<' ;
T107 : '>>' ;
T108 : '<' ;
T109 : '>' ;
T110 : '<=' ;
T111 : '>=' ;
T112 : '==' ;
T113 : '!=' ;
T114 : '^' ;
T115 : '|' ;
T116 : '&&' ;
T117 : '||' ;
T118 : '?' ;
T119 : '*=' ;
T120 : '/=' ;
T121 : '%=' ;
T122 : '+=' ;
T123 : '-=' ;
T124 : '<<=' ;
T125 : '>>=' ;
T126 : '&=' ;
T127 : '^=' ;
T128 : '|=' ;
T129 : 'case' ;
T130 : 'default' ;
T131 : 'if' ;
T132 : 'else' ;
T133 : 'switch' ;
T134 : 'while' ;
T135 : 'do' ;
T136 : 'for' ;
T137 : 'goto' ;
T138 : 'continue' ;
T139 : 'break' ;
T140 : 'return' ;

// $ANTLR src "GNUCt.g" 632
EXTENSION	// GNU
	:	'__extension__' {$channel=HIDDEN;}	// just ignore - see 5.28
	;

// $ANTLR src "GNUCt.g" 636
IDENTIFIER
	:	NonDigit (NonDigit|Digit)*
	;
	
// $ANTLR src "GNUCt.g" 640
fragment
NonDigit
	:	'_'
	|	'a'..'z'
	|	'A'..'Z'
	|	'$'			// GNU
	;
	
// $ANTLR src "GNUCt.g" 648
fragment
Digit
	:	'0'..'9'
	;
	
// A.1.4 Universal character names

// A.1.5 Constants

// A.1.6 String literals

// $ANTLR src "GNUCt.g" 659
STRING_LITERAL
	:	'L'? '"' (EscapeSequence | ~('"'|'\\'))* '"'
	;

// $ANTLR src "GNUCt.g" 663
CHARACTER_LITERAL
	:	'L'? '\'' (EscapeSequence | ~('"'|'\\'))* '\''
	;


// $ANTLR src "GNUCt.g" 668
fragment
HexDigit
	:	'0'..'9'|'a'..'f'|'A'..'F'
	;

// $ANTLR src "GNUCt.g" 673
fragment 
Sign
	:	'+'
	|	'-'
	;

// $ANTLR src "GNUCt.g" 679
fragment
EscapeSequence
	:	'\\' ('\''|'"'|'?'|'\\'|'a'|'b'|'e'|'f'|'n'|'r'|'t'|'v')	// '\e' is GNU
	|	OctalEscape
	|	HexadecimalEscape
	|	UniversalCharacterName
	;

// $ANTLR src "GNUCt.g" 687
fragment
OctalEscape
	:	'\\' ('0'..'3') ('0'..'7') ('0'..'7')
	|	'\\' ('0'..'7') ('0'..'7')
	|	'\\' ('0'..'7')
	;

// $ANTLR src "GNUCt.g" 694
fragment
HexadecimalEscape 
	:	'\\' 'x' HexDigit+
	;

// $ANTLR src "GNUCt.g" 699
fragment
UniversalCharacterName
	:	'\\' 'u' HexDigit HexDigit HexDigit HexDigit
	|	'\\' 'U' HexDigit HexDigit HexDigit HexDigit HexDigit HexDigit HexDigit HexDigit
	;

// $ANTLR src "GNUCt.g" 705
CONSTANT
	:	DecimalConstant IntegerSuffix?
	|	OctalConstant IntegerSuffix?
	|	HexadecimalConstant IntegerSuffix?
	|	DecimalFloatingConstant
	|	HexadecimalFloatingConstant
	|	IDENTIFIER
	|	CHARACTER_LITERAL
	;

// $ANTLR src "GNUCt.g" 715
fragment
IntegerSuffix
	:	('u'|'U') ('l'|'L')?
	|	('u'|'U') ('ll'|'LL')
	|	('l'|'L') ('u'|'U')? 
	|	('ll'|'LL') ('u'|'U')?
	|	'i'|'j'
	|	'I'|'J'
	;

// $ANTLR src "GNUCt.g" 725
fragment
DecimalConstant
	:	'1'..'9' ('0'..'9')*
	;

// $ANTLR src "GNUCt.g" 730
fragment
OctalConstant
	:	'0' ('0'..'7')*
	;
	
// $ANTLR src "GNUCt.g" 735
fragment
HexadecimalConstant
	:	'0' ('x'|'X') HexDigit+
	;
	
// $ANTLR src "GNUCt.g" 740
fragment
DecimalFloatingConstant
	:	('0'..'9')* '.' ('0'..'9')+ ExponentPart? FloatingSuffix?
	|	('0'..'9')+ '.'             ExponentPart? FloatingSuffix?
	|	('0'..'9')+                 ExponentPart  FloatingSuffix?
	;

// $ANTLR src "GNUCt.g" 747
fragment
ExponentPart
	:	('e'|'E') Sign? ('0'..'9')+
	;
	
// $ANTLR src "GNUCt.g" 752
fragment
HexadecimalFloatingConstant
	:	'0' ('x'|'X') HexDigit* '.' HexDigit+ BinaryExponentPart FloatingSuffix?
	|	'0' ('x'|'X') HexDigit+ '.'           BinaryExponentPart FloatingSuffix?
	|	'0' ('x'|'X') HexDigit+               BinaryExponentPart FloatingSuffix?
	;

// $ANTLR src "GNUCt.g" 759
fragment
BinaryExponentPart
	:	('p'|'P') Sign? ('0'..'9')+
	;

// $ANTLR src "GNUCt.g" 764
fragment
FloatingSuffix
	:	'f'|'l'|'F'|'L'
	|	'i'|'j'
	|	'I'|'J'
	;	

// Comments - not in the grammar?

// $ANTLR src "GNUCt.g" 773
WS  :  (' '|'\r'|'\t'|'\u000C'|'\n') {$channel=HIDDEN;}
    ;

// $ANTLR src "GNUCt.g" 776
COMMENT
    :   '/*' ( options {greedy=false;} : . )* '*/' {$channel=HIDDEN;}
    ;

// $ANTLR src "GNUCt.g" 780
LINE_COMMENT
    : '//' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;}
    ;

// ignore #line info for now
// $ANTLR src "GNUCt.g" 785
LINE_COMMAND 
    : '#' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;}
    ;


// $ANTLR 3.0.1 GNUCa.g 2008-11-07 14:25:28

	package cz.muni.stanse.c2xml;
import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class GNUCaLexer extends Lexer {
    public static final int T114=114;
    public static final int OctalConstant=68;
    public static final int T115=115;
    public static final int T116=116;
    public static final int T117=117;
    public static final int T118=118;
    public static final int T119=119;
    public static final int BinaryExponentPart=74;
    public static final int POINTER=34;
    public static final int NonDigit=57;
    public static final int BRACKET_DESIGNATOR=4;
    public static final int E2=7;
    public static final int E1=6;
    public static final int E3=8;
    public static final int EOF=-1;
    public static final int T120=120;
    public static final int T122=122;
    public static final int T121=121;
    public static final int T124=124;
    public static final int T123=123;
    public static final int T127=127;
    public static final int T128=128;
    public static final int T125=125;
    public static final int T126=126;
    public static final int STRING_LITERAL=55;
    public static final int T129=129;
    public static final int Sign=62;
    public static final int T131=131;
    public static final int T130=130;
    public static final int FUNCTION_DEFINITION=37;
    public static final int T135=135;
    public static final int T134=134;
    public static final int T133=133;
    public static final int T132=132;
    public static final int XU=15;
    public static final int ASM=10;
    public static final int T100=100;
    public static final int T102=102;
    public static final int T101=101;
    public static final int COMPOUND_LITERAL=29;
    public static final int T109=109;
    public static final int T107=107;
    public static final int DESIGNATOR=26;
    public static final int T108=108;
    public static final int WS=75;
    public static final int T105=105;
    public static final int T106=106;
    public static final int T103=103;
    public static final int T104=104;
    public static final int TYPEOF=20;
    public static final int COMPOUND_STATEMENT=49;
    public static final int CONSTANT=54;
    public static final int IntegerSuffix=67;
    public static final int EXPRESSION_STATEMENT=9;
    public static final int T113=113;
    public static final int FUNCTION_CALL=51;
    public static final int T112=112;
    public static final int T111=111;
    public static final int T110=110;
    public static final int CAST_EXPRESSION=46;
    public static final int T79=79;
    public static final int T159=159;
    public static final int T158=158;
    public static final int T161=161;
    public static final int T162=162;
    public static final int T163=163;
    public static final int T164=164;
    public static final int T165=165;
    public static final int T166=166;
    public static final int T167=167;
    public static final int T168=168;
    public static final int HexDigit=61;
    public static final int DECLARATION=42;
    public static final int ASSIGNMENT_EXPRESSION=21;
    public static final int T160=160;
    public static final int XTYPE_SPECIFIER=39;
    public static final int AU=16;
    public static final int T169=169;
    public static final int T174=174;
    public static final int PU=13;
    public static final int ENUMERATOR=27;
    public static final int T175=175;
    public static final int T172=172;
    public static final int T173=173;
    public static final int PP=11;
    public static final int T178=178;
    public static final int T179=179;
    public static final int T176=176;
    public static final int T177=177;
    public static final int T170=170;
    public static final int T171=171;
    public static final int XTYPE_QUALIFIER=40;
    public static final int T99=99;
    public static final int T97=97;
    public static final int T98=98;
    public static final int T95=95;
    public static final int T96=96;
    public static final int T137=137;
    public static final int T136=136;
    public static final int T139=139;
    public static final int T138=138;
    public static final int T143=143;
    public static final int XSTORAGE_CLASS=41;
    public static final int T144=144;
    public static final int T145=145;
    public static final int T146=146;
    public static final int T140=140;
    public static final int T141=141;
    public static final int T142=142;
    public static final int T94=94;
    public static final int Tokens=190;
    public static final int T93=93;
    public static final int T92=92;
    public static final int T91=91;
    public static final int T90=90;
    public static final int T88=88;
    public static final int T89=89;
    public static final int T84=84;
    public static final int T85=85;
    public static final int T86=86;
    public static final int T87=87;
    public static final int TRANSLATION_UNIT=28;
    public static final int T149=149;
    public static final int T148=148;
    public static final int T147=147;
    public static final int T156=156;
    public static final int T157=157;
    public static final int T154=154;
    public static final int T155=155;
    public static final int T152=152;
    public static final int FloatingSuffix=73;
    public static final int T153=153;
    public static final int XID=38;
    public static final int T150=150;
    public static final int T151=151;
    public static final int T81=81;
    public static final int T80=80;
    public static final int T83=83;
    public static final int T82=82;
    public static final int EXTENSION=56;
    public static final int OctalEscape=63;
    public static final int SIGNED=5;
    public static final int UniversalCharacterName=65;
    public static final int DecimalConstant=66;
    public static final int DecimalFloatingConstant=70;
    public static final int TYPEDEF_NAME=19;
    public static final int MU=14;
    public static final int HexadecimalFloatingConstant=71;
    public static final int MM=12;
    public static final int PARAMETER=31;
    public static final int ExponentPart=72;
    public static final int COMMENT=76;
    public static final int FUNCTION_DECLARATOR=23;
    public static final int LINE_COMMENT=77;
    public static final int HexadecimalEscape=64;
    public static final int DECLARATOR=33;
    public static final int STR_LITERAL=52;
    public static final int T182=182;
    public static final int T181=181;
    public static final int T180=180;
    public static final int T186=186;
    public static final int T185=185;
    public static final int T184=184;
    public static final int T183=183;
    public static final int T189=189;
    public static final int T188=188;
    public static final int LINE_COMMAND=78;
    public static final int T187=187;
    public static final int INIT_DECLARATOR=43;
    public static final int BASETYPE=45;
    public static final int EscapeSequence=59;
    public static final int VARARGS=32;
    public static final int CharacterLiteral=60;
    public static final int ALIGNOF=50;
    public static final int INITIALIZER=25;
    public static final int STRUCT_DECLARATOR=35;
    public static final int CONDITIONAL_EXPRESSION=47;
    public static final int IDENTIFIER=53;
    public static final int TYPE_NAME=22;
    public static final int HexadecimalConstant=69;
    public static final int ARRAY_ACCESS=30;
    public static final int Digit=58;
    public static final int ARRAY_DECLARATOR=24;
    public static final int BUILTIN_OFFSETOF=18;
    public static final int LABEL=48;
    public static final int STRUCT_DECLARATION=36;
    public static final int LABREF=17;
    public static final int DECLARATION_SPECIFIERS=44;
    public GNUCaLexer() {;} 
    public GNUCaLexer(CharStream input) {
        super(input);
    }
    public String getGrammarFileName() { return "GNUCa.g"; }

    // $ANTLR start ALIGNOF
    public final void mALIGNOF() throws RecognitionException {
        try {
            int _type = ALIGNOF;
            // GNUCa.g:3:9: ( 'alignof' )
            // GNUCa.g:3:11: 'alignof'
            {
            match("alignof"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end ALIGNOF

    // $ANTLR start T79
    public final void mT79() throws RecognitionException {
        try {
            int _type = T79;
            // GNUCa.g:4:5: ( ';' )
            // GNUCa.g:4:7: ';'
            {
            match(';'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T79

    // $ANTLR start T80
    public final void mT80() throws RecognitionException {
        try {
            int _type = T80;
            // GNUCa.g:5:5: ( 'asm' )
            // GNUCa.g:5:7: 'asm'
            {
            match("asm"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T80

    // $ANTLR start T81
    public final void mT81() throws RecognitionException {
        try {
            int _type = T81;
            // GNUCa.g:6:5: ( '__asm' )
            // GNUCa.g:6:7: '__asm'
            {
            match("__asm"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T81

    // $ANTLR start T82
    public final void mT82() throws RecognitionException {
        try {
            int _type = T82;
            // GNUCa.g:7:5: ( '__asm__' )
            // GNUCa.g:7:7: '__asm__'
            {
            match("__asm__"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T82

    // $ANTLR start T83
    public final void mT83() throws RecognitionException {
        try {
            int _type = T83;
            // GNUCa.g:8:5: ( '(' )
            // GNUCa.g:8:7: '('
            {
            match('('); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T83

    // $ANTLR start T84
    public final void mT84() throws RecognitionException {
        try {
            int _type = T84;
            // GNUCa.g:9:5: ( ')' )
            // GNUCa.g:9:7: ')'
            {
            match(')'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T84

    // $ANTLR start T85
    public final void mT85() throws RecognitionException {
        try {
            int _type = T85;
            // GNUCa.g:10:5: ( 'typedef' )
            // GNUCa.g:10:7: 'typedef'
            {
            match("typedef"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T85

    // $ANTLR start T86
    public final void mT86() throws RecognitionException {
        try {
            int _type = T86;
            // GNUCa.g:11:5: ( ',' )
            // GNUCa.g:11:7: ','
            {
            match(','); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T86

    // $ANTLR start T87
    public final void mT87() throws RecognitionException {
        try {
            int _type = T87;
            // GNUCa.g:12:5: ( '=' )
            // GNUCa.g:12:7: '='
            {
            match('='); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T87

    // $ANTLR start T88
    public final void mT88() throws RecognitionException {
        try {
            int _type = T88;
            // GNUCa.g:13:5: ( 'extern' )
            // GNUCa.g:13:7: 'extern'
            {
            match("extern"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T88

    // $ANTLR start T89
    public final void mT89() throws RecognitionException {
        try {
            int _type = T89;
            // GNUCa.g:14:5: ( 'static' )
            // GNUCa.g:14:7: 'static'
            {
            match("static"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T89

    // $ANTLR start T90
    public final void mT90() throws RecognitionException {
        try {
            int _type = T90;
            // GNUCa.g:15:5: ( 'auto' )
            // GNUCa.g:15:7: 'auto'
            {
            match("auto"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T90

    // $ANTLR start T91
    public final void mT91() throws RecognitionException {
        try {
            int _type = T91;
            // GNUCa.g:16:5: ( 'register' )
            // GNUCa.g:16:7: 'register'
            {
            match("register"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T91

    // $ANTLR start T92
    public final void mT92() throws RecognitionException {
        try {
            int _type = T92;
            // GNUCa.g:17:5: ( '__thread' )
            // GNUCa.g:17:7: '__thread'
            {
            match("__thread"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T92

    // $ANTLR start T93
    public final void mT93() throws RecognitionException {
        try {
            int _type = T93;
            // GNUCa.g:18:5: ( 'void' )
            // GNUCa.g:18:7: 'void'
            {
            match("void"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T93

    // $ANTLR start T94
    public final void mT94() throws RecognitionException {
        try {
            int _type = T94;
            // GNUCa.g:19:5: ( 'char' )
            // GNUCa.g:19:7: 'char'
            {
            match("char"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T94

    // $ANTLR start T95
    public final void mT95() throws RecognitionException {
        try {
            int _type = T95;
            // GNUCa.g:20:5: ( 'short' )
            // GNUCa.g:20:7: 'short'
            {
            match("short"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T95

    // $ANTLR start T96
    public final void mT96() throws RecognitionException {
        try {
            int _type = T96;
            // GNUCa.g:21:5: ( 'int' )
            // GNUCa.g:21:7: 'int'
            {
            match("int"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T96

    // $ANTLR start T97
    public final void mT97() throws RecognitionException {
        try {
            int _type = T97;
            // GNUCa.g:22:5: ( 'long' )
            // GNUCa.g:22:7: 'long'
            {
            match("long"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T97

    // $ANTLR start T98
    public final void mT98() throws RecognitionException {
        try {
            int _type = T98;
            // GNUCa.g:23:5: ( 'float' )
            // GNUCa.g:23:7: 'float'
            {
            match("float"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T98

    // $ANTLR start T99
    public final void mT99() throws RecognitionException {
        try {
            int _type = T99;
            // GNUCa.g:24:5: ( 'double' )
            // GNUCa.g:24:7: 'double'
            {
            match("double"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T99

    // $ANTLR start T100
    public final void mT100() throws RecognitionException {
        try {
            int _type = T100;
            // GNUCa.g:25:6: ( 'signed' )
            // GNUCa.g:25:8: 'signed'
            {
            match("signed"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T100

    // $ANTLR start T101
    public final void mT101() throws RecognitionException {
        try {
            int _type = T101;
            // GNUCa.g:26:6: ( '__signed' )
            // GNUCa.g:26:8: '__signed'
            {
            match("__signed"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T101

    // $ANTLR start T102
    public final void mT102() throws RecognitionException {
        try {
            int _type = T102;
            // GNUCa.g:27:6: ( '__signed__' )
            // GNUCa.g:27:8: '__signed__'
            {
            match("__signed__"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T102

    // $ANTLR start T103
    public final void mT103() throws RecognitionException {
        try {
            int _type = T103;
            // GNUCa.g:28:6: ( 'unsigned' )
            // GNUCa.g:28:8: 'unsigned'
            {
            match("unsigned"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T103

    // $ANTLR start T104
    public final void mT104() throws RecognitionException {
        try {
            int _type = T104;
            // GNUCa.g:29:6: ( '_Bool' )
            // GNUCa.g:29:8: '_Bool'
            {
            match("_Bool"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T104

    // $ANTLR start T105
    public final void mT105() throws RecognitionException {
        try {
            int _type = T105;
            // GNUCa.g:30:6: ( '_Complex' )
            // GNUCa.g:30:8: '_Complex'
            {
            match("_Complex"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T105

    // $ANTLR start T106
    public final void mT106() throws RecognitionException {
        try {
            int _type = T106;
            // GNUCa.g:31:6: ( '__complex' )
            // GNUCa.g:31:8: '__complex'
            {
            match("__complex"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T106

    // $ANTLR start T107
    public final void mT107() throws RecognitionException {
        try {
            int _type = T107;
            // GNUCa.g:32:6: ( '__complex__' )
            // GNUCa.g:32:8: '__complex__'
            {
            match("__complex__"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T107

    // $ANTLR start T108
    public final void mT108() throws RecognitionException {
        try {
            int _type = T108;
            // GNUCa.g:33:6: ( '_Imaginary' )
            // GNUCa.g:33:8: '_Imaginary'
            {
            match("_Imaginary"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T108

    // $ANTLR start T109
    public final void mT109() throws RecognitionException {
        try {
            int _type = T109;
            // GNUCa.g:34:6: ( '{' )
            // GNUCa.g:34:8: '{'
            {
            match('{'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T109

    // $ANTLR start T110
    public final void mT110() throws RecognitionException {
        try {
            int _type = T110;
            // GNUCa.g:35:6: ( '}' )
            // GNUCa.g:35:8: '}'
            {
            match('}'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T110

    // $ANTLR start T111
    public final void mT111() throws RecognitionException {
        try {
            int _type = T111;
            // GNUCa.g:36:6: ( 'struct' )
            // GNUCa.g:36:8: 'struct'
            {
            match("struct"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T111

    // $ANTLR start T112
    public final void mT112() throws RecognitionException {
        try {
            int _type = T112;
            // GNUCa.g:37:6: ( 'union' )
            // GNUCa.g:37:8: 'union'
            {
            match("union"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T112

    // $ANTLR start T113
    public final void mT113() throws RecognitionException {
        try {
            int _type = T113;
            // GNUCa.g:38:6: ( ':' )
            // GNUCa.g:38:8: ':'
            {
            match(':'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T113

    // $ANTLR start T114
    public final void mT114() throws RecognitionException {
        try {
            int _type = T114;
            // GNUCa.g:39:6: ( 'enum' )
            // GNUCa.g:39:8: 'enum'
            {
            match("enum"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T114

    // $ANTLR start T115
    public final void mT115() throws RecognitionException {
        try {
            int _type = T115;
            // GNUCa.g:40:6: ( 'const' )
            // GNUCa.g:40:8: 'const'
            {
            match("const"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T115

    // $ANTLR start T116
    public final void mT116() throws RecognitionException {
        try {
            int _type = T116;
            // GNUCa.g:41:6: ( '__const' )
            // GNUCa.g:41:8: '__const'
            {
            match("__const"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T116

    // $ANTLR start T117
    public final void mT117() throws RecognitionException {
        try {
            int _type = T117;
            // GNUCa.g:42:6: ( '__const__' )
            // GNUCa.g:42:8: '__const__'
            {
            match("__const__"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T117

    // $ANTLR start T118
    public final void mT118() throws RecognitionException {
        try {
            int _type = T118;
            // GNUCa.g:43:6: ( 'restrict' )
            // GNUCa.g:43:8: 'restrict'
            {
            match("restrict"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T118

    // $ANTLR start T119
    public final void mT119() throws RecognitionException {
        try {
            int _type = T119;
            // GNUCa.g:44:6: ( '__restrict' )
            // GNUCa.g:44:8: '__restrict'
            {
            match("__restrict"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T119

    // $ANTLR start T120
    public final void mT120() throws RecognitionException {
        try {
            int _type = T120;
            // GNUCa.g:45:6: ( '__restrict__' )
            // GNUCa.g:45:8: '__restrict__'
            {
            match("__restrict__"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T120

    // $ANTLR start T121
    public final void mT121() throws RecognitionException {
        try {
            int _type = T121;
            // GNUCa.g:46:6: ( 'volatile' )
            // GNUCa.g:46:8: 'volatile'
            {
            match("volatile"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T121

    // $ANTLR start T122
    public final void mT122() throws RecognitionException {
        try {
            int _type = T122;
            // GNUCa.g:47:6: ( '__volatile' )
            // GNUCa.g:47:8: '__volatile'
            {
            match("__volatile"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T122

    // $ANTLR start T123
    public final void mT123() throws RecognitionException {
        try {
            int _type = T123;
            // GNUCa.g:48:6: ( '__volatile__' )
            // GNUCa.g:48:8: '__volatile__'
            {
            match("__volatile__"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T123

    // $ANTLR start T124
    public final void mT124() throws RecognitionException {
        try {
            int _type = T124;
            // GNUCa.g:49:6: ( 'inline' )
            // GNUCa.g:49:8: 'inline'
            {
            match("inline"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T124

    // $ANTLR start T125
    public final void mT125() throws RecognitionException {
        try {
            int _type = T125;
            // GNUCa.g:50:6: ( '__inline' )
            // GNUCa.g:50:8: '__inline'
            {
            match("__inline"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T125

    // $ANTLR start T126
    public final void mT126() throws RecognitionException {
        try {
            int _type = T126;
            // GNUCa.g:51:6: ( '__inline__' )
            // GNUCa.g:51:8: '__inline__'
            {
            match("__inline__"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T126

    // $ANTLR start T127
    public final void mT127() throws RecognitionException {
        try {
            int _type = T127;
            // GNUCa.g:52:6: ( '[' )
            // GNUCa.g:52:8: '['
            {
            match('['); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T127

    // $ANTLR start T128
    public final void mT128() throws RecognitionException {
        try {
            int _type = T128;
            // GNUCa.g:53:6: ( ']' )
            // GNUCa.g:53:8: ']'
            {
            match(']'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T128

    // $ANTLR start T129
    public final void mT129() throws RecognitionException {
        try {
            int _type = T129;
            // GNUCa.g:54:6: ( '*' )
            // GNUCa.g:54:8: '*'
            {
            match('*'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T129

    // $ANTLR start T130
    public final void mT130() throws RecognitionException {
        try {
            int _type = T130;
            // GNUCa.g:55:6: ( '...' )
            // GNUCa.g:55:8: '...'
            {
            match("..."); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T130

    // $ANTLR start T131
    public final void mT131() throws RecognitionException {
        try {
            int _type = T131;
            // GNUCa.g:56:6: ( 'typeof' )
            // GNUCa.g:56:8: 'typeof'
            {
            match("typeof"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T131

    // $ANTLR start T132
    public final void mT132() throws RecognitionException {
        try {
            int _type = T132;
            // GNUCa.g:57:6: ( '__typeof' )
            // GNUCa.g:57:8: '__typeof'
            {
            match("__typeof"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T132

    // $ANTLR start T133
    public final void mT133() throws RecognitionException {
        try {
            int _type = T133;
            // GNUCa.g:58:6: ( '__typeof__' )
            // GNUCa.g:58:8: '__typeof__'
            {
            match("__typeof__"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T133

    // $ANTLR start T134
    public final void mT134() throws RecognitionException {
        try {
            int _type = T134;
            // GNUCa.g:59:6: ( '.' )
            // GNUCa.g:59:8: '.'
            {
            match('.'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T134

    // $ANTLR start T135
    public final void mT135() throws RecognitionException {
        try {
            int _type = T135;
            // GNUCa.g:60:6: ( '__attribute' )
            // GNUCa.g:60:8: '__attribute'
            {
            match("__attribute"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T135

    // $ANTLR start T136
    public final void mT136() throws RecognitionException {
        try {
            int _type = T136;
            // GNUCa.g:61:6: ( '__attribute__' )
            // GNUCa.g:61:8: '__attribute__'
            {
            match("__attribute__"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T136

    // $ANTLR start T137
    public final void mT137() throws RecognitionException {
        try {
            int _type = T137;
            // GNUCa.g:62:6: ( '__builtin_offsetof' )
            // GNUCa.g:62:8: '__builtin_offsetof'
            {
            match("__builtin_offsetof"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T137

    // $ANTLR start T138
    public final void mT138() throws RecognitionException {
        try {
            int _type = T138;
            // GNUCa.g:63:6: ( '->' )
            // GNUCa.g:63:8: '->'
            {
            match("->"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T138

    // $ANTLR start T139
    public final void mT139() throws RecognitionException {
        try {
            int _type = T139;
            // GNUCa.g:64:6: ( '++' )
            // GNUCa.g:64:8: '++'
            {
            match("++"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T139

    // $ANTLR start T140
    public final void mT140() throws RecognitionException {
        try {
            int _type = T140;
            // GNUCa.g:65:6: ( '--' )
            // GNUCa.g:65:8: '--'
            {
            match("--"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T140

    // $ANTLR start T141
    public final void mT141() throws RecognitionException {
        try {
            int _type = T141;
            // GNUCa.g:66:6: ( 'sizeof' )
            // GNUCa.g:66:8: 'sizeof'
            {
            match("sizeof"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T141

    // $ANTLR start T142
    public final void mT142() throws RecognitionException {
        try {
            int _type = T142;
            // GNUCa.g:67:6: ( '__alignof' )
            // GNUCa.g:67:8: '__alignof'
            {
            match("__alignof"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T142

    // $ANTLR start T143
    public final void mT143() throws RecognitionException {
        try {
            int _type = T143;
            // GNUCa.g:68:6: ( '__alignof__' )
            // GNUCa.g:68:8: '__alignof__'
            {
            match("__alignof__"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T143

    // $ANTLR start T144
    public final void mT144() throws RecognitionException {
        try {
            int _type = T144;
            // GNUCa.g:69:6: ( '&' )
            // GNUCa.g:69:8: '&'
            {
            match('&'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T144

    // $ANTLR start T145
    public final void mT145() throws RecognitionException {
        try {
            int _type = T145;
            // GNUCa.g:70:6: ( '+' )
            // GNUCa.g:70:8: '+'
            {
            match('+'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T145

    // $ANTLR start T146
    public final void mT146() throws RecognitionException {
        try {
            int _type = T146;
            // GNUCa.g:71:6: ( '-' )
            // GNUCa.g:71:8: '-'
            {
            match('-'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T146

    // $ANTLR start T147
    public final void mT147() throws RecognitionException {
        try {
            int _type = T147;
            // GNUCa.g:72:6: ( '~' )
            // GNUCa.g:72:8: '~'
            {
            match('~'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T147

    // $ANTLR start T148
    public final void mT148() throws RecognitionException {
        try {
            int _type = T148;
            // GNUCa.g:73:6: ( '!' )
            // GNUCa.g:73:8: '!'
            {
            match('!'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T148

    // $ANTLR start T149
    public final void mT149() throws RecognitionException {
        try {
            int _type = T149;
            // GNUCa.g:74:6: ( '&&' )
            // GNUCa.g:74:8: '&&'
            {
            match("&&"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T149

    // $ANTLR start T150
    public final void mT150() throws RecognitionException {
        try {
            int _type = T150;
            // GNUCa.g:75:6: ( '__real' )
            // GNUCa.g:75:8: '__real'
            {
            match("__real"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T150

    // $ANTLR start T151
    public final void mT151() throws RecognitionException {
        try {
            int _type = T151;
            // GNUCa.g:76:6: ( '__real__' )
            // GNUCa.g:76:8: '__real__'
            {
            match("__real__"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T151

    // $ANTLR start T152
    public final void mT152() throws RecognitionException {
        try {
            int _type = T152;
            // GNUCa.g:77:6: ( '__imag' )
            // GNUCa.g:77:8: '__imag'
            {
            match("__imag"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T152

    // $ANTLR start T153
    public final void mT153() throws RecognitionException {
        try {
            int _type = T153;
            // GNUCa.g:78:6: ( '__imag__' )
            // GNUCa.g:78:8: '__imag__'
            {
            match("__imag__"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T153

    // $ANTLR start T154
    public final void mT154() throws RecognitionException {
        try {
            int _type = T154;
            // GNUCa.g:79:6: ( '/' )
            // GNUCa.g:79:8: '/'
            {
            match('/'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T154

    // $ANTLR start T155
    public final void mT155() throws RecognitionException {
        try {
            int _type = T155;
            // GNUCa.g:80:6: ( '%' )
            // GNUCa.g:80:8: '%'
            {
            match('%'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T155

    // $ANTLR start T156
    public final void mT156() throws RecognitionException {
        try {
            int _type = T156;
            // GNUCa.g:81:6: ( '<<' )
            // GNUCa.g:81:8: '<<'
            {
            match("<<"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T156

    // $ANTLR start T157
    public final void mT157() throws RecognitionException {
        try {
            int _type = T157;
            // GNUCa.g:82:6: ( '>>' )
            // GNUCa.g:82:8: '>>'
            {
            match(">>"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T157

    // $ANTLR start T158
    public final void mT158() throws RecognitionException {
        try {
            int _type = T158;
            // GNUCa.g:83:6: ( '<' )
            // GNUCa.g:83:8: '<'
            {
            match('<'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T158

    // $ANTLR start T159
    public final void mT159() throws RecognitionException {
        try {
            int _type = T159;
            // GNUCa.g:84:6: ( '>' )
            // GNUCa.g:84:8: '>'
            {
            match('>'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T159

    // $ANTLR start T160
    public final void mT160() throws RecognitionException {
        try {
            int _type = T160;
            // GNUCa.g:85:6: ( '<=' )
            // GNUCa.g:85:8: '<='
            {
            match("<="); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T160

    // $ANTLR start T161
    public final void mT161() throws RecognitionException {
        try {
            int _type = T161;
            // GNUCa.g:86:6: ( '>=' )
            // GNUCa.g:86:8: '>='
            {
            match(">="); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T161

    // $ANTLR start T162
    public final void mT162() throws RecognitionException {
        try {
            int _type = T162;
            // GNUCa.g:87:6: ( '==' )
            // GNUCa.g:87:8: '=='
            {
            match("=="); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T162

    // $ANTLR start T163
    public final void mT163() throws RecognitionException {
        try {
            int _type = T163;
            // GNUCa.g:88:6: ( '!=' )
            // GNUCa.g:88:8: '!='
            {
            match("!="); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T163

    // $ANTLR start T164
    public final void mT164() throws RecognitionException {
        try {
            int _type = T164;
            // GNUCa.g:89:6: ( '^' )
            // GNUCa.g:89:8: '^'
            {
            match('^'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T164

    // $ANTLR start T165
    public final void mT165() throws RecognitionException {
        try {
            int _type = T165;
            // GNUCa.g:90:6: ( '|' )
            // GNUCa.g:90:8: '|'
            {
            match('|'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T165

    // $ANTLR start T166
    public final void mT166() throws RecognitionException {
        try {
            int _type = T166;
            // GNUCa.g:91:6: ( '||' )
            // GNUCa.g:91:8: '||'
            {
            match("||"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T166

    // $ANTLR start T167
    public final void mT167() throws RecognitionException {
        try {
            int _type = T167;
            // GNUCa.g:92:6: ( '?' )
            // GNUCa.g:92:8: '?'
            {
            match('?'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T167

    // $ANTLR start T168
    public final void mT168() throws RecognitionException {
        try {
            int _type = T168;
            // GNUCa.g:93:6: ( '*=' )
            // GNUCa.g:93:8: '*='
            {
            match("*="); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T168

    // $ANTLR start T169
    public final void mT169() throws RecognitionException {
        try {
            int _type = T169;
            // GNUCa.g:94:6: ( '/=' )
            // GNUCa.g:94:8: '/='
            {
            match("/="); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T169

    // $ANTLR start T170
    public final void mT170() throws RecognitionException {
        try {
            int _type = T170;
            // GNUCa.g:95:6: ( '%=' )
            // GNUCa.g:95:8: '%='
            {
            match("%="); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T170

    // $ANTLR start T171
    public final void mT171() throws RecognitionException {
        try {
            int _type = T171;
            // GNUCa.g:96:6: ( '+=' )
            // GNUCa.g:96:8: '+='
            {
            match("+="); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T171

    // $ANTLR start T172
    public final void mT172() throws RecognitionException {
        try {
            int _type = T172;
            // GNUCa.g:97:6: ( '-=' )
            // GNUCa.g:97:8: '-='
            {
            match("-="); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T172

    // $ANTLR start T173
    public final void mT173() throws RecognitionException {
        try {
            int _type = T173;
            // GNUCa.g:98:6: ( '<<=' )
            // GNUCa.g:98:8: '<<='
            {
            match("<<="); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T173

    // $ANTLR start T174
    public final void mT174() throws RecognitionException {
        try {
            int _type = T174;
            // GNUCa.g:99:6: ( '>>=' )
            // GNUCa.g:99:8: '>>='
            {
            match(">>="); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T174

    // $ANTLR start T175
    public final void mT175() throws RecognitionException {
        try {
            int _type = T175;
            // GNUCa.g:100:6: ( '&=' )
            // GNUCa.g:100:8: '&='
            {
            match("&="); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T175

    // $ANTLR start T176
    public final void mT176() throws RecognitionException {
        try {
            int _type = T176;
            // GNUCa.g:101:6: ( '^=' )
            // GNUCa.g:101:8: '^='
            {
            match("^="); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T176

    // $ANTLR start T177
    public final void mT177() throws RecognitionException {
        try {
            int _type = T177;
            // GNUCa.g:102:6: ( '|=' )
            // GNUCa.g:102:8: '|='
            {
            match("|="); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T177

    // $ANTLR start T178
    public final void mT178() throws RecognitionException {
        try {
            int _type = T178;
            // GNUCa.g:103:6: ( 'case' )
            // GNUCa.g:103:8: 'case'
            {
            match("case"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T178

    // $ANTLR start T179
    public final void mT179() throws RecognitionException {
        try {
            int _type = T179;
            // GNUCa.g:104:6: ( 'default' )
            // GNUCa.g:104:8: 'default'
            {
            match("default"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T179

    // $ANTLR start T180
    public final void mT180() throws RecognitionException {
        try {
            int _type = T180;
            // GNUCa.g:105:6: ( 'if' )
            // GNUCa.g:105:8: 'if'
            {
            match("if"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T180

    // $ANTLR start T181
    public final void mT181() throws RecognitionException {
        try {
            int _type = T181;
            // GNUCa.g:106:6: ( 'else' )
            // GNUCa.g:106:8: 'else'
            {
            match("else"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T181

    // $ANTLR start T182
    public final void mT182() throws RecognitionException {
        try {
            int _type = T182;
            // GNUCa.g:107:6: ( 'switch' )
            // GNUCa.g:107:8: 'switch'
            {
            match("switch"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T182

    // $ANTLR start T183
    public final void mT183() throws RecognitionException {
        try {
            int _type = T183;
            // GNUCa.g:108:6: ( 'while' )
            // GNUCa.g:108:8: 'while'
            {
            match("while"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T183

    // $ANTLR start T184
    public final void mT184() throws RecognitionException {
        try {
            int _type = T184;
            // GNUCa.g:109:6: ( 'do' )
            // GNUCa.g:109:8: 'do'
            {
            match("do"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T184

    // $ANTLR start T185
    public final void mT185() throws RecognitionException {
        try {
            int _type = T185;
            // GNUCa.g:110:6: ( 'for' )
            // GNUCa.g:110:8: 'for'
            {
            match("for"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T185

    // $ANTLR start T186
    public final void mT186() throws RecognitionException {
        try {
            int _type = T186;
            // GNUCa.g:111:6: ( 'goto' )
            // GNUCa.g:111:8: 'goto'
            {
            match("goto"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T186

    // $ANTLR start T187
    public final void mT187() throws RecognitionException {
        try {
            int _type = T187;
            // GNUCa.g:112:6: ( 'continue' )
            // GNUCa.g:112:8: 'continue'
            {
            match("continue"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T187

    // $ANTLR start T188
    public final void mT188() throws RecognitionException {
        try {
            int _type = T188;
            // GNUCa.g:113:6: ( 'break' )
            // GNUCa.g:113:8: 'break'
            {
            match("break"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T188

    // $ANTLR start T189
    public final void mT189() throws RecognitionException {
        try {
            int _type = T189;
            // GNUCa.g:114:6: ( 'return' )
            // GNUCa.g:114:8: 'return'
            {
            match("return"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T189

    // $ANTLR start EXTENSION
    public final void mEXTENSION() throws RecognitionException {
        try {
            int _type = EXTENSION;
            // GNUCa.g:686:2: ( '__extension__' )
            // GNUCa.g:686:4: '__extension__'
            {
            match("__extension__"); 

            channel=HIDDEN;

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end EXTENSION

    // $ANTLR start IDENTIFIER
    public final void mIDENTIFIER() throws RecognitionException {
        try {
            int _type = IDENTIFIER;
            // GNUCa.g:690:2: ( NonDigit ( NonDigit | Digit )* )
            // GNUCa.g:690:4: NonDigit ( NonDigit | Digit )*
            {
            mNonDigit(); 
            // GNUCa.g:690:13: ( NonDigit | Digit )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0=='$'||(LA1_0>='0' && LA1_0<='9')||(LA1_0>='A' && LA1_0<='Z')||LA1_0=='_'||(LA1_0>='a' && LA1_0<='z')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // GNUCa.g:
            	    {
            	    if ( input.LA(1)=='$'||(input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse =
            	            new MismatchedSetException(null,input);
            	        recover(mse);    throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end IDENTIFIER

    // $ANTLR start NonDigit
    public final void mNonDigit() throws RecognitionException {
        try {
            // GNUCa.g:695:2: ( '_' | 'a' .. 'z' | 'A' .. 'Z' | '$' )
            // GNUCa.g:
            {
            if ( input.LA(1)=='$'||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recover(mse);    throw mse;
            }


            }

        }
        finally {
        }
    }
    // $ANTLR end NonDigit

    // $ANTLR start Digit
    public final void mDigit() throws RecognitionException {
        try {
            // GNUCa.g:703:2: ( '0' .. '9' )
            // GNUCa.g:703:4: '0' .. '9'
            {
            matchRange('0','9'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end Digit

    // $ANTLR start STRING_LITERAL
    public final void mSTRING_LITERAL() throws RecognitionException {
        try {
            int _type = STRING_LITERAL;
            // GNUCa.g:713:2: ( ( 'L' )? '\"' ( EscapeSequence | ~ ( '\"' | '\\\\' ) )* '\"' )
            // GNUCa.g:713:4: ( 'L' )? '\"' ( EscapeSequence | ~ ( '\"' | '\\\\' ) )* '\"'
            {
            // GNUCa.g:713:4: ( 'L' )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0=='L') ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // GNUCa.g:713:4: 'L'
                    {
                    match('L'); 

                    }
                    break;

            }

            match('\"'); 
            // GNUCa.g:713:13: ( EscapeSequence | ~ ( '\"' | '\\\\' ) )*
            loop3:
            do {
                int alt3=3;
                int LA3_0 = input.LA(1);

                if ( (LA3_0=='\\') ) {
                    alt3=1;
                }
                else if ( ((LA3_0>='\u0000' && LA3_0<='!')||(LA3_0>='#' && LA3_0<='[')||(LA3_0>=']' && LA3_0<='\uFFFE')) ) {
                    alt3=2;
                }


                switch (alt3) {
            	case 1 :
            	    // GNUCa.g:713:14: EscapeSequence
            	    {
            	    mEscapeSequence(); 

            	    }
            	    break;
            	case 2 :
            	    // GNUCa.g:713:31: ~ ( '\"' | '\\\\' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFE') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse =
            	            new MismatchedSetException(null,input);
            	        recover(mse);    throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

            match('\"'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end STRING_LITERAL

    // $ANTLR start CharacterLiteral
    public final void mCharacterLiteral() throws RecognitionException {
        try {
            // GNUCa.g:718:2: ( ( 'L' )? '\\'' ( EscapeSequence | ~ ( '\\\\' ) ) '\\'' )
            // GNUCa.g:718:4: ( 'L' )? '\\'' ( EscapeSequence | ~ ( '\\\\' ) ) '\\''
            {
            // GNUCa.g:718:4: ( 'L' )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='L') ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // GNUCa.g:718:4: 'L'
                    {
                    match('L'); 

                    }
                    break;

            }

            match('\''); 
            // GNUCa.g:718:14: ( EscapeSequence | ~ ( '\\\\' ) )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0=='\\') ) {
                alt5=1;
            }
            else if ( ((LA5_0>='\u0000' && LA5_0<='[')||(LA5_0>=']' && LA5_0<='\uFFFE')) ) {
                alt5=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("718:14: ( EscapeSequence | ~ ( '\\\\' ) )", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // GNUCa.g:718:15: EscapeSequence
                    {
                    mEscapeSequence(); 

                    }
                    break;
                case 2 :
                    // GNUCa.g:718:32: ~ ( '\\\\' )
                    {
                    if ( (input.LA(1)>='\u0000' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFE') ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse =
                            new MismatchedSetException(null,input);
                        recover(mse);    throw mse;
                    }


                    }
                    break;

            }

            match('\''); 

            }

        }
        finally {
        }
    }
    // $ANTLR end CharacterLiteral

    // $ANTLR start HexDigit
    public final void mHexDigit() throws RecognitionException {
        try {
            // GNUCa.g:724:2: ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' )
            // GNUCa.g:
            {
            if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='F')||(input.LA(1)>='a' && input.LA(1)<='f') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recover(mse);    throw mse;
            }


            }

        }
        finally {
        }
    }
    // $ANTLR end HexDigit

    // $ANTLR start Sign
    public final void mSign() throws RecognitionException {
        try {
            // GNUCa.g:729:2: ( '+' | '-' )
            // GNUCa.g:
            {
            if ( input.LA(1)=='+'||input.LA(1)=='-' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recover(mse);    throw mse;
            }


            }

        }
        finally {
        }
    }
    // $ANTLR end Sign

    // $ANTLR start EscapeSequence
    public final void mEscapeSequence() throws RecognitionException {
        try {
            // GNUCa.g:735:2: ( '\\\\' ( '\\'' | '\"' | '?' | '\\\\' | 'a' | 'b' | 'e' | 'f' | 'n' | 'r' | 't' | 'v' ) | OctalEscape | HexadecimalEscape | UniversalCharacterName )
            int alt6=4;
            int LA6_0 = input.LA(1);

            if ( (LA6_0=='\\') ) {
                switch ( input.LA(2) ) {
                case 'U':
                case 'u':
                    {
                    alt6=4;
                    }
                    break;
                case '\"':
                case '\'':
                case '?':
                case '\\':
                case 'a':
                case 'b':
                case 'e':
                case 'f':
                case 'n':
                case 'r':
                case 't':
                case 'v':
                    {
                    alt6=1;
                    }
                    break;
                case 'x':
                    {
                    alt6=3;
                    }
                    break;
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                    {
                    alt6=2;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("733:1: fragment EscapeSequence : ( '\\\\' ( '\\'' | '\"' | '?' | '\\\\' | 'a' | 'b' | 'e' | 'f' | 'n' | 'r' | 't' | 'v' ) | OctalEscape | HexadecimalEscape | UniversalCharacterName );", 6, 1, input);

                    throw nvae;
                }

            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("733:1: fragment EscapeSequence : ( '\\\\' ( '\\'' | '\"' | '?' | '\\\\' | 'a' | 'b' | 'e' | 'f' | 'n' | 'r' | 't' | 'v' ) | OctalEscape | HexadecimalEscape | UniversalCharacterName );", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // GNUCa.g:735:4: '\\\\' ( '\\'' | '\"' | '?' | '\\\\' | 'a' | 'b' | 'e' | 'f' | 'n' | 'r' | 't' | 'v' )
                    {
                    match('\\'); 
                    if ( input.LA(1)=='\"'||input.LA(1)=='\''||input.LA(1)=='?'||input.LA(1)=='\\'||(input.LA(1)>='a' && input.LA(1)<='b')||(input.LA(1)>='e' && input.LA(1)<='f')||input.LA(1)=='n'||input.LA(1)=='r'||input.LA(1)=='t'||input.LA(1)=='v' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse =
                            new MismatchedSetException(null,input);
                        recover(mse);    throw mse;
                    }


                    }
                    break;
                case 2 :
                    // GNUCa.g:736:4: OctalEscape
                    {
                    mOctalEscape(); 

                    }
                    break;
                case 3 :
                    // GNUCa.g:737:4: HexadecimalEscape
                    {
                    mHexadecimalEscape(); 

                    }
                    break;
                case 4 :
                    // GNUCa.g:738:4: UniversalCharacterName
                    {
                    mUniversalCharacterName(); 

                    }
                    break;

            }
        }
        finally {
        }
    }
    // $ANTLR end EscapeSequence

    // $ANTLR start OctalEscape
    public final void mOctalEscape() throws RecognitionException {
        try {
            // GNUCa.g:743:2: ( '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) )
            int alt7=3;
            int LA7_0 = input.LA(1);

            if ( (LA7_0=='\\') ) {
                int LA7_1 = input.LA(2);

                if ( ((LA7_1>='0' && LA7_1<='3')) ) {
                    int LA7_2 = input.LA(3);

                    if ( ((LA7_2>='0' && LA7_2<='7')) ) {
                        int LA7_5 = input.LA(4);

                        if ( ((LA7_5>='0' && LA7_5<='7')) ) {
                            alt7=1;
                        }
                        else {
                            alt7=2;}
                    }
                    else {
                        alt7=3;}
                }
                else if ( ((LA7_1>='4' && LA7_1<='7')) ) {
                    int LA7_3 = input.LA(3);

                    if ( ((LA7_3>='0' && LA7_3<='7')) ) {
                        alt7=2;
                    }
                    else {
                        alt7=3;}
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("741:1: fragment OctalEscape : ( '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) );", 7, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("741:1: fragment OctalEscape : ( '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) );", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // GNUCa.g:743:4: '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' )
                    {
                    match('\\'); 
                    // GNUCa.g:743:9: ( '0' .. '3' )
                    // GNUCa.g:743:10: '0' .. '3'
                    {
                    matchRange('0','3'); 

                    }

                    // GNUCa.g:743:20: ( '0' .. '7' )
                    // GNUCa.g:743:21: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }

                    // GNUCa.g:743:31: ( '0' .. '7' )
                    // GNUCa.g:743:32: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }


                    }
                    break;
                case 2 :
                    // GNUCa.g:744:4: '\\\\' ( '0' .. '7' ) ( '0' .. '7' )
                    {
                    match('\\'); 
                    // GNUCa.g:744:9: ( '0' .. '7' )
                    // GNUCa.g:744:10: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }

                    // GNUCa.g:744:20: ( '0' .. '7' )
                    // GNUCa.g:744:21: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }


                    }
                    break;
                case 3 :
                    // GNUCa.g:745:4: '\\\\' ( '0' .. '7' )
                    {
                    match('\\'); 
                    // GNUCa.g:745:9: ( '0' .. '7' )
                    // GNUCa.g:745:10: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }


                    }
                    break;

            }
        }
        finally {
        }
    }
    // $ANTLR end OctalEscape

    // $ANTLR start HexadecimalEscape
    public final void mHexadecimalEscape() throws RecognitionException {
        try {
            // GNUCa.g:750:2: ( '\\\\' 'x' ( HexDigit )+ )
            // GNUCa.g:750:4: '\\\\' 'x' ( HexDigit )+
            {
            match('\\'); 
            match('x'); 
            // GNUCa.g:750:13: ( HexDigit )+
            int cnt8=0;
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( ((LA8_0>='0' && LA8_0<='9')||(LA8_0>='A' && LA8_0<='F')||(LA8_0>='a' && LA8_0<='f')) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // GNUCa.g:750:13: HexDigit
            	    {
            	    mHexDigit(); 

            	    }
            	    break;

            	default :
            	    if ( cnt8 >= 1 ) break loop8;
                        EarlyExitException eee =
                            new EarlyExitException(8, input);
                        throw eee;
                }
                cnt8++;
            } while (true);


            }

        }
        finally {
        }
    }
    // $ANTLR end HexadecimalEscape

    // $ANTLR start UniversalCharacterName
    public final void mUniversalCharacterName() throws RecognitionException {
        try {
            // GNUCa.g:755:2: ( '\\\\' 'u' HexDigit HexDigit HexDigit HexDigit | '\\\\' 'U' HexDigit HexDigit HexDigit HexDigit HexDigit HexDigit HexDigit HexDigit )
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0=='\\') ) {
                int LA9_1 = input.LA(2);

                if ( (LA9_1=='U') ) {
                    alt9=2;
                }
                else if ( (LA9_1=='u') ) {
                    alt9=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("753:1: fragment UniversalCharacterName : ( '\\\\' 'u' HexDigit HexDigit HexDigit HexDigit | '\\\\' 'U' HexDigit HexDigit HexDigit HexDigit HexDigit HexDigit HexDigit HexDigit );", 9, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("753:1: fragment UniversalCharacterName : ( '\\\\' 'u' HexDigit HexDigit HexDigit HexDigit | '\\\\' 'U' HexDigit HexDigit HexDigit HexDigit HexDigit HexDigit HexDigit HexDigit );", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1 :
                    // GNUCa.g:755:4: '\\\\' 'u' HexDigit HexDigit HexDigit HexDigit
                    {
                    match('\\'); 
                    match('u'); 
                    mHexDigit(); 
                    mHexDigit(); 
                    mHexDigit(); 
                    mHexDigit(); 

                    }
                    break;
                case 2 :
                    // GNUCa.g:756:4: '\\\\' 'U' HexDigit HexDigit HexDigit HexDigit HexDigit HexDigit HexDigit HexDigit
                    {
                    match('\\'); 
                    match('U'); 
                    mHexDigit(); 
                    mHexDigit(); 
                    mHexDigit(); 
                    mHexDigit(); 
                    mHexDigit(); 
                    mHexDigit(); 
                    mHexDigit(); 
                    mHexDigit(); 

                    }
                    break;

            }
        }
        finally {
        }
    }
    // $ANTLR end UniversalCharacterName

    // $ANTLR start CONSTANT
    public final void mCONSTANT() throws RecognitionException {
        try {
            int _type = CONSTANT;
            // GNUCa.g:760:2: ( DecimalConstant ( IntegerSuffix )? | OctalConstant ( IntegerSuffix )? | HexadecimalConstant ( IntegerSuffix )? | DecimalFloatingConstant | HexadecimalFloatingConstant | CharacterLiteral )
            int alt13=6;
            alt13 = dfa13.predict(input);
            switch (alt13) {
                case 1 :
                    // GNUCa.g:760:4: DecimalConstant ( IntegerSuffix )?
                    {
                    mDecimalConstant(); 
                    // GNUCa.g:760:20: ( IntegerSuffix )?
                    int alt10=2;
                    int LA10_0 = input.LA(1);

                    if ( ((LA10_0>='I' && LA10_0<='J')||LA10_0=='L'||LA10_0=='U'||(LA10_0>='i' && LA10_0<='j')||LA10_0=='l'||LA10_0=='u') ) {
                        alt10=1;
                    }
                    switch (alt10) {
                        case 1 :
                            // GNUCa.g:760:20: IntegerSuffix
                            {
                            mIntegerSuffix(); 

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // GNUCa.g:761:4: OctalConstant ( IntegerSuffix )?
                    {
                    mOctalConstant(); 
                    // GNUCa.g:761:18: ( IntegerSuffix )?
                    int alt11=2;
                    int LA11_0 = input.LA(1);

                    if ( ((LA11_0>='I' && LA11_0<='J')||LA11_0=='L'||LA11_0=='U'||(LA11_0>='i' && LA11_0<='j')||LA11_0=='l'||LA11_0=='u') ) {
                        alt11=1;
                    }
                    switch (alt11) {
                        case 1 :
                            // GNUCa.g:761:18: IntegerSuffix
                            {
                            mIntegerSuffix(); 

                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // GNUCa.g:762:4: HexadecimalConstant ( IntegerSuffix )?
                    {
                    mHexadecimalConstant(); 
                    // GNUCa.g:762:24: ( IntegerSuffix )?
                    int alt12=2;
                    int LA12_0 = input.LA(1);

                    if ( ((LA12_0>='I' && LA12_0<='J')||LA12_0=='L'||LA12_0=='U'||(LA12_0>='i' && LA12_0<='j')||LA12_0=='l'||LA12_0=='u') ) {
                        alt12=1;
                    }
                    switch (alt12) {
                        case 1 :
                            // GNUCa.g:762:24: IntegerSuffix
                            {
                            mIntegerSuffix(); 

                            }
                            break;

                    }


                    }
                    break;
                case 4 :
                    // GNUCa.g:763:4: DecimalFloatingConstant
                    {
                    mDecimalFloatingConstant(); 

                    }
                    break;
                case 5 :
                    // GNUCa.g:764:4: HexadecimalFloatingConstant
                    {
                    mHexadecimalFloatingConstant(); 

                    }
                    break;
                case 6 :
                    // GNUCa.g:765:4: CharacterLiteral
                    {
                    mCharacterLiteral(); 

                    }
                    break;

            }
            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end CONSTANT

    // $ANTLR start IntegerSuffix
    public final void mIntegerSuffix() throws RecognitionException {
        try {
            // GNUCa.g:770:2: ( ( 'u' | 'U' ) ( 'l' | 'L' )? | ( 'u' | 'U' ) ( 'll' | 'LL' ) | ( 'l' | 'L' ) ( 'u' | 'U' )? | ( 'll' | 'LL' ) ( 'u' | 'U' )? | 'i' | 'j' | 'I' | 'J' )
            int alt19=8;
            switch ( input.LA(1) ) {
            case 'U':
            case 'u':
                {
                switch ( input.LA(2) ) {
                case 'l':
                    {
                    int LA19_8 = input.LA(3);

                    if ( (LA19_8=='l') ) {
                        alt19=2;
                    }
                    else {
                        alt19=1;}
                    }
                    break;
                case 'L':
                    {
                    int LA19_10 = input.LA(3);

                    if ( (LA19_10=='L') ) {
                        alt19=2;
                    }
                    else {
                        alt19=1;}
                    }
                    break;
                default:
                    alt19=1;}

                }
                break;
            case 'l':
                {
                int LA19_2 = input.LA(2);

                if ( (LA19_2=='l') ) {
                    alt19=4;
                }
                else {
                    alt19=3;}
                }
                break;
            case 'L':
                {
                int LA19_3 = input.LA(2);

                if ( (LA19_3=='L') ) {
                    alt19=4;
                }
                else {
                    alt19=3;}
                }
                break;
            case 'i':
                {
                alt19=5;
                }
                break;
            case 'j':
                {
                alt19=6;
                }
                break;
            case 'I':
                {
                alt19=7;
                }
                break;
            case 'J':
                {
                alt19=8;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("768:1: fragment IntegerSuffix : ( ( 'u' | 'U' ) ( 'l' | 'L' )? | ( 'u' | 'U' ) ( 'll' | 'LL' ) | ( 'l' | 'L' ) ( 'u' | 'U' )? | ( 'll' | 'LL' ) ( 'u' | 'U' )? | 'i' | 'j' | 'I' | 'J' );", 19, 0, input);

                throw nvae;
            }

            switch (alt19) {
                case 1 :
                    // GNUCa.g:770:4: ( 'u' | 'U' ) ( 'l' | 'L' )?
                    {
                    if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse =
                            new MismatchedSetException(null,input);
                        recover(mse);    throw mse;
                    }

                    // GNUCa.g:770:14: ( 'l' | 'L' )?
                    int alt14=2;
                    int LA14_0 = input.LA(1);

                    if ( (LA14_0=='L'||LA14_0=='l') ) {
                        alt14=1;
                    }
                    switch (alt14) {
                        case 1 :
                            // GNUCa.g:
                            {
                            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                                input.consume();

                            }
                            else {
                                MismatchedSetException mse =
                                    new MismatchedSetException(null,input);
                                recover(mse);    throw mse;
                            }


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // GNUCa.g:771:4: ( 'u' | 'U' ) ( 'll' | 'LL' )
                    {
                    if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse =
                            new MismatchedSetException(null,input);
                        recover(mse);    throw mse;
                    }

                    // GNUCa.g:771:14: ( 'll' | 'LL' )
                    int alt15=2;
                    int LA15_0 = input.LA(1);

                    if ( (LA15_0=='l') ) {
                        alt15=1;
                    }
                    else if ( (LA15_0=='L') ) {
                        alt15=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("771:14: ( 'll' | 'LL' )", 15, 0, input);

                        throw nvae;
                    }
                    switch (alt15) {
                        case 1 :
                            // GNUCa.g:771:15: 'll'
                            {
                            match("ll"); 


                            }
                            break;
                        case 2 :
                            // GNUCa.g:771:20: 'LL'
                            {
                            match("LL"); 


                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // GNUCa.g:772:4: ( 'l' | 'L' ) ( 'u' | 'U' )?
                    {
                    if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse =
                            new MismatchedSetException(null,input);
                        recover(mse);    throw mse;
                    }

                    // GNUCa.g:772:14: ( 'u' | 'U' )?
                    int alt16=2;
                    int LA16_0 = input.LA(1);

                    if ( (LA16_0=='U'||LA16_0=='u') ) {
                        alt16=1;
                    }
                    switch (alt16) {
                        case 1 :
                            // GNUCa.g:
                            {
                            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                                input.consume();

                            }
                            else {
                                MismatchedSetException mse =
                                    new MismatchedSetException(null,input);
                                recover(mse);    throw mse;
                            }


                            }
                            break;

                    }


                    }
                    break;
                case 4 :
                    // GNUCa.g:773:4: ( 'll' | 'LL' ) ( 'u' | 'U' )?
                    {
                    // GNUCa.g:773:4: ( 'll' | 'LL' )
                    int alt17=2;
                    int LA17_0 = input.LA(1);

                    if ( (LA17_0=='l') ) {
                        alt17=1;
                    }
                    else if ( (LA17_0=='L') ) {
                        alt17=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("773:4: ( 'll' | 'LL' )", 17, 0, input);

                        throw nvae;
                    }
                    switch (alt17) {
                        case 1 :
                            // GNUCa.g:773:5: 'll'
                            {
                            match("ll"); 


                            }
                            break;
                        case 2 :
                            // GNUCa.g:773:10: 'LL'
                            {
                            match("LL"); 


                            }
                            break;

                    }

                    // GNUCa.g:773:16: ( 'u' | 'U' )?
                    int alt18=2;
                    int LA18_0 = input.LA(1);

                    if ( (LA18_0=='U'||LA18_0=='u') ) {
                        alt18=1;
                    }
                    switch (alt18) {
                        case 1 :
                            // GNUCa.g:
                            {
                            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                                input.consume();

                            }
                            else {
                                MismatchedSetException mse =
                                    new MismatchedSetException(null,input);
                                recover(mse);    throw mse;
                            }


                            }
                            break;

                    }


                    }
                    break;
                case 5 :
                    // GNUCa.g:774:4: 'i'
                    {
                    match('i'); 

                    }
                    break;
                case 6 :
                    // GNUCa.g:774:8: 'j'
                    {
                    match('j'); 

                    }
                    break;
                case 7 :
                    // GNUCa.g:775:4: 'I'
                    {
                    match('I'); 

                    }
                    break;
                case 8 :
                    // GNUCa.g:775:8: 'J'
                    {
                    match('J'); 

                    }
                    break;

            }
        }
        finally {
        }
    }
    // $ANTLR end IntegerSuffix

    // $ANTLR start DecimalConstant
    public final void mDecimalConstant() throws RecognitionException {
        try {
            // GNUCa.g:780:2: ( '1' .. '9' ( '0' .. '9' )* )
            // GNUCa.g:780:4: '1' .. '9' ( '0' .. '9' )*
            {
            matchRange('1','9'); 
            // GNUCa.g:780:13: ( '0' .. '9' )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( ((LA20_0>='0' && LA20_0<='9')) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // GNUCa.g:780:14: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    break loop20;
                }
            } while (true);


            }

        }
        finally {
        }
    }
    // $ANTLR end DecimalConstant

    // $ANTLR start OctalConstant
    public final void mOctalConstant() throws RecognitionException {
        try {
            // GNUCa.g:785:2: ( '0' ( '0' .. '7' )* )
            // GNUCa.g:785:4: '0' ( '0' .. '7' )*
            {
            match('0'); 
            // GNUCa.g:785:8: ( '0' .. '7' )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( ((LA21_0>='0' && LA21_0<='7')) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // GNUCa.g:785:9: '0' .. '7'
            	    {
            	    matchRange('0','7'); 

            	    }
            	    break;

            	default :
            	    break loop21;
                }
            } while (true);


            }

        }
        finally {
        }
    }
    // $ANTLR end OctalConstant

    // $ANTLR start HexadecimalConstant
    public final void mHexadecimalConstant() throws RecognitionException {
        try {
            // GNUCa.g:790:2: ( '0' ( 'x' | 'X' ) ( HexDigit )+ )
            // GNUCa.g:790:4: '0' ( 'x' | 'X' ) ( HexDigit )+
            {
            match('0'); 
            if ( input.LA(1)=='X'||input.LA(1)=='x' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recover(mse);    throw mse;
            }

            // GNUCa.g:790:18: ( HexDigit )+
            int cnt22=0;
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( ((LA22_0>='0' && LA22_0<='9')||(LA22_0>='A' && LA22_0<='F')||(LA22_0>='a' && LA22_0<='f')) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // GNUCa.g:790:18: HexDigit
            	    {
            	    mHexDigit(); 

            	    }
            	    break;

            	default :
            	    if ( cnt22 >= 1 ) break loop22;
                        EarlyExitException eee =
                            new EarlyExitException(22, input);
                        throw eee;
                }
                cnt22++;
            } while (true);


            }

        }
        finally {
        }
    }
    // $ANTLR end HexadecimalConstant

    // $ANTLR start DecimalFloatingConstant
    public final void mDecimalFloatingConstant() throws RecognitionException {
        try {
            // GNUCa.g:795:2: ( ( '0' .. '9' )* '.' ( '0' .. '9' )+ ( ExponentPart )? ( FloatingSuffix )? | ( '0' .. '9' )+ '.' ( ExponentPart )? ( FloatingSuffix )? | ( '0' .. '9' )+ ExponentPart ( FloatingSuffix )? )
            int alt32=3;
            alt32 = dfa32.predict(input);
            switch (alt32) {
                case 1 :
                    // GNUCa.g:795:4: ( '0' .. '9' )* '.' ( '0' .. '9' )+ ( ExponentPart )? ( FloatingSuffix )?
                    {
                    // GNUCa.g:795:4: ( '0' .. '9' )*
                    loop23:
                    do {
                        int alt23=2;
                        int LA23_0 = input.LA(1);

                        if ( ((LA23_0>='0' && LA23_0<='9')) ) {
                            alt23=1;
                        }


                        switch (alt23) {
                    	case 1 :
                    	    // GNUCa.g:795:5: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    break loop23;
                        }
                    } while (true);

                    match('.'); 
                    // GNUCa.g:795:20: ( '0' .. '9' )+
                    int cnt24=0;
                    loop24:
                    do {
                        int alt24=2;
                        int LA24_0 = input.LA(1);

                        if ( ((LA24_0>='0' && LA24_0<='9')) ) {
                            alt24=1;
                        }


                        switch (alt24) {
                    	case 1 :
                    	    // GNUCa.g:795:21: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt24 >= 1 ) break loop24;
                                EarlyExitException eee =
                                    new EarlyExitException(24, input);
                                throw eee;
                        }
                        cnt24++;
                    } while (true);

                    // GNUCa.g:795:32: ( ExponentPart )?
                    int alt25=2;
                    int LA25_0 = input.LA(1);

                    if ( (LA25_0=='E'||LA25_0=='e') ) {
                        alt25=1;
                    }
                    switch (alt25) {
                        case 1 :
                            // GNUCa.g:795:32: ExponentPart
                            {
                            mExponentPart(); 

                            }
                            break;

                    }

                    // GNUCa.g:795:46: ( FloatingSuffix )?
                    int alt26=2;
                    int LA26_0 = input.LA(1);

                    if ( (LA26_0=='F'||(LA26_0>='I' && LA26_0<='J')||LA26_0=='L'||LA26_0=='f'||(LA26_0>='i' && LA26_0<='j')||LA26_0=='l') ) {
                        alt26=1;
                    }
                    switch (alt26) {
                        case 1 :
                            // GNUCa.g:795:46: FloatingSuffix
                            {
                            mFloatingSuffix(); 

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // GNUCa.g:796:4: ( '0' .. '9' )+ '.' ( ExponentPart )? ( FloatingSuffix )?
                    {
                    // GNUCa.g:796:4: ( '0' .. '9' )+
                    int cnt27=0;
                    loop27:
                    do {
                        int alt27=2;
                        int LA27_0 = input.LA(1);

                        if ( ((LA27_0>='0' && LA27_0<='9')) ) {
                            alt27=1;
                        }


                        switch (alt27) {
                    	case 1 :
                    	    // GNUCa.g:796:5: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt27 >= 1 ) break loop27;
                                EarlyExitException eee =
                                    new EarlyExitException(27, input);
                                throw eee;
                        }
                        cnt27++;
                    } while (true);

                    match('.'); 
                    // GNUCa.g:796:32: ( ExponentPart )?
                    int alt28=2;
                    int LA28_0 = input.LA(1);

                    if ( (LA28_0=='E'||LA28_0=='e') ) {
                        alt28=1;
                    }
                    switch (alt28) {
                        case 1 :
                            // GNUCa.g:796:32: ExponentPart
                            {
                            mExponentPart(); 

                            }
                            break;

                    }

                    // GNUCa.g:796:46: ( FloatingSuffix )?
                    int alt29=2;
                    int LA29_0 = input.LA(1);

                    if ( (LA29_0=='F'||(LA29_0>='I' && LA29_0<='J')||LA29_0=='L'||LA29_0=='f'||(LA29_0>='i' && LA29_0<='j')||LA29_0=='l') ) {
                        alt29=1;
                    }
                    switch (alt29) {
                        case 1 :
                            // GNUCa.g:796:46: FloatingSuffix
                            {
                            mFloatingSuffix(); 

                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // GNUCa.g:797:4: ( '0' .. '9' )+ ExponentPart ( FloatingSuffix )?
                    {
                    // GNUCa.g:797:4: ( '0' .. '9' )+
                    int cnt30=0;
                    loop30:
                    do {
                        int alt30=2;
                        int LA30_0 = input.LA(1);

                        if ( ((LA30_0>='0' && LA30_0<='9')) ) {
                            alt30=1;
                        }


                        switch (alt30) {
                    	case 1 :
                    	    // GNUCa.g:797:5: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt30 >= 1 ) break loop30;
                                EarlyExitException eee =
                                    new EarlyExitException(30, input);
                                throw eee;
                        }
                        cnt30++;
                    } while (true);

                    mExponentPart(); 
                    // GNUCa.g:797:46: ( FloatingSuffix )?
                    int alt31=2;
                    int LA31_0 = input.LA(1);

                    if ( (LA31_0=='F'||(LA31_0>='I' && LA31_0<='J')||LA31_0=='L'||LA31_0=='f'||(LA31_0>='i' && LA31_0<='j')||LA31_0=='l') ) {
                        alt31=1;
                    }
                    switch (alt31) {
                        case 1 :
                            // GNUCa.g:797:46: FloatingSuffix
                            {
                            mFloatingSuffix(); 

                            }
                            break;

                    }


                    }
                    break;

            }
        }
        finally {
        }
    }
    // $ANTLR end DecimalFloatingConstant

    // $ANTLR start ExponentPart
    public final void mExponentPart() throws RecognitionException {
        try {
            // GNUCa.g:802:2: ( ( 'e' | 'E' ) ( Sign )? ( '0' .. '9' )+ )
            // GNUCa.g:802:4: ( 'e' | 'E' ) ( Sign )? ( '0' .. '9' )+
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recover(mse);    throw mse;
            }

            // GNUCa.g:802:14: ( Sign )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0=='+'||LA33_0=='-') ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // GNUCa.g:802:14: Sign
                    {
                    mSign(); 

                    }
                    break;

            }

            // GNUCa.g:802:20: ( '0' .. '9' )+
            int cnt34=0;
            loop34:
            do {
                int alt34=2;
                int LA34_0 = input.LA(1);

                if ( ((LA34_0>='0' && LA34_0<='9')) ) {
                    alt34=1;
                }


                switch (alt34) {
            	case 1 :
            	    // GNUCa.g:802:21: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt34 >= 1 ) break loop34;
                        EarlyExitException eee =
                            new EarlyExitException(34, input);
                        throw eee;
                }
                cnt34++;
            } while (true);


            }

        }
        finally {
        }
    }
    // $ANTLR end ExponentPart

    // $ANTLR start HexadecimalFloatingConstant
    public final void mHexadecimalFloatingConstant() throws RecognitionException {
        try {
            // GNUCa.g:807:2: ( '0' ( 'x' | 'X' ) ( HexDigit )* '.' ( HexDigit )+ BinaryExponentPart ( FloatingSuffix )? | '0' ( 'x' | 'X' ) ( HexDigit )+ '.' BinaryExponentPart ( FloatingSuffix )? | '0' ( 'x' | 'X' ) ( HexDigit )+ BinaryExponentPart ( FloatingSuffix )? )
            int alt42=3;
            alt42 = dfa42.predict(input);
            switch (alt42) {
                case 1 :
                    // GNUCa.g:807:4: '0' ( 'x' | 'X' ) ( HexDigit )* '.' ( HexDigit )+ BinaryExponentPart ( FloatingSuffix )?
                    {
                    match('0'); 
                    if ( input.LA(1)=='X'||input.LA(1)=='x' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse =
                            new MismatchedSetException(null,input);
                        recover(mse);    throw mse;
                    }

                    // GNUCa.g:807:18: ( HexDigit )*
                    loop35:
                    do {
                        int alt35=2;
                        int LA35_0 = input.LA(1);

                        if ( ((LA35_0>='0' && LA35_0<='9')||(LA35_0>='A' && LA35_0<='F')||(LA35_0>='a' && LA35_0<='f')) ) {
                            alt35=1;
                        }


                        switch (alt35) {
                    	case 1 :
                    	    // GNUCa.g:807:18: HexDigit
                    	    {
                    	    mHexDigit(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop35;
                        }
                    } while (true);

                    match('.'); 
                    // GNUCa.g:807:32: ( HexDigit )+
                    int cnt36=0;
                    loop36:
                    do {
                        int alt36=2;
                        int LA36_0 = input.LA(1);

                        if ( ((LA36_0>='0' && LA36_0<='9')||(LA36_0>='A' && LA36_0<='F')||(LA36_0>='a' && LA36_0<='f')) ) {
                            alt36=1;
                        }


                        switch (alt36) {
                    	case 1 :
                    	    // GNUCa.g:807:32: HexDigit
                    	    {
                    	    mHexDigit(); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt36 >= 1 ) break loop36;
                                EarlyExitException eee =
                                    new EarlyExitException(36, input);
                                throw eee;
                        }
                        cnt36++;
                    } while (true);

                    mBinaryExponentPart(); 
                    // GNUCa.g:807:61: ( FloatingSuffix )?
                    int alt37=2;
                    int LA37_0 = input.LA(1);

                    if ( (LA37_0=='F'||(LA37_0>='I' && LA37_0<='J')||LA37_0=='L'||LA37_0=='f'||(LA37_0>='i' && LA37_0<='j')||LA37_0=='l') ) {
                        alt37=1;
                    }
                    switch (alt37) {
                        case 1 :
                            // GNUCa.g:807:61: FloatingSuffix
                            {
                            mFloatingSuffix(); 

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // GNUCa.g:808:4: '0' ( 'x' | 'X' ) ( HexDigit )+ '.' BinaryExponentPart ( FloatingSuffix )?
                    {
                    match('0'); 
                    if ( input.LA(1)=='X'||input.LA(1)=='x' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse =
                            new MismatchedSetException(null,input);
                        recover(mse);    throw mse;
                    }

                    // GNUCa.g:808:18: ( HexDigit )+
                    int cnt38=0;
                    loop38:
                    do {
                        int alt38=2;
                        int LA38_0 = input.LA(1);

                        if ( ((LA38_0>='0' && LA38_0<='9')||(LA38_0>='A' && LA38_0<='F')||(LA38_0>='a' && LA38_0<='f')) ) {
                            alt38=1;
                        }


                        switch (alt38) {
                    	case 1 :
                    	    // GNUCa.g:808:18: HexDigit
                    	    {
                    	    mHexDigit(); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt38 >= 1 ) break loop38;
                                EarlyExitException eee =
                                    new EarlyExitException(38, input);
                                throw eee;
                        }
                        cnt38++;
                    } while (true);

                    match('.'); 
                    mBinaryExponentPart(); 
                    // GNUCa.g:808:61: ( FloatingSuffix )?
                    int alt39=2;
                    int LA39_0 = input.LA(1);

                    if ( (LA39_0=='F'||(LA39_0>='I' && LA39_0<='J')||LA39_0=='L'||LA39_0=='f'||(LA39_0>='i' && LA39_0<='j')||LA39_0=='l') ) {
                        alt39=1;
                    }
                    switch (alt39) {
                        case 1 :
                            // GNUCa.g:808:61: FloatingSuffix
                            {
                            mFloatingSuffix(); 

                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // GNUCa.g:809:4: '0' ( 'x' | 'X' ) ( HexDigit )+ BinaryExponentPart ( FloatingSuffix )?
                    {
                    match('0'); 
                    if ( input.LA(1)=='X'||input.LA(1)=='x' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse =
                            new MismatchedSetException(null,input);
                        recover(mse);    throw mse;
                    }

                    // GNUCa.g:809:18: ( HexDigit )+
                    int cnt40=0;
                    loop40:
                    do {
                        int alt40=2;
                        int LA40_0 = input.LA(1);

                        if ( ((LA40_0>='0' && LA40_0<='9')||(LA40_0>='A' && LA40_0<='F')||(LA40_0>='a' && LA40_0<='f')) ) {
                            alt40=1;
                        }


                        switch (alt40) {
                    	case 1 :
                    	    // GNUCa.g:809:18: HexDigit
                    	    {
                    	    mHexDigit(); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt40 >= 1 ) break loop40;
                                EarlyExitException eee =
                                    new EarlyExitException(40, input);
                                throw eee;
                        }
                        cnt40++;
                    } while (true);

                    mBinaryExponentPart(); 
                    // GNUCa.g:809:61: ( FloatingSuffix )?
                    int alt41=2;
                    int LA41_0 = input.LA(1);

                    if ( (LA41_0=='F'||(LA41_0>='I' && LA41_0<='J')||LA41_0=='L'||LA41_0=='f'||(LA41_0>='i' && LA41_0<='j')||LA41_0=='l') ) {
                        alt41=1;
                    }
                    switch (alt41) {
                        case 1 :
                            // GNUCa.g:809:61: FloatingSuffix
                            {
                            mFloatingSuffix(); 

                            }
                            break;

                    }


                    }
                    break;

            }
        }
        finally {
        }
    }
    // $ANTLR end HexadecimalFloatingConstant

    // $ANTLR start BinaryExponentPart
    public final void mBinaryExponentPart() throws RecognitionException {
        try {
            // GNUCa.g:814:2: ( ( 'p' | 'P' ) ( Sign )? ( '0' .. '9' )+ )
            // GNUCa.g:814:4: ( 'p' | 'P' ) ( Sign )? ( '0' .. '9' )+
            {
            if ( input.LA(1)=='P'||input.LA(1)=='p' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recover(mse);    throw mse;
            }

            // GNUCa.g:814:14: ( Sign )?
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( (LA43_0=='+'||LA43_0=='-') ) {
                alt43=1;
            }
            switch (alt43) {
                case 1 :
                    // GNUCa.g:814:14: Sign
                    {
                    mSign(); 

                    }
                    break;

            }

            // GNUCa.g:814:20: ( '0' .. '9' )+
            int cnt44=0;
            loop44:
            do {
                int alt44=2;
                int LA44_0 = input.LA(1);

                if ( ((LA44_0>='0' && LA44_0<='9')) ) {
                    alt44=1;
                }


                switch (alt44) {
            	case 1 :
            	    // GNUCa.g:814:21: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt44 >= 1 ) break loop44;
                        EarlyExitException eee =
                            new EarlyExitException(44, input);
                        throw eee;
                }
                cnt44++;
            } while (true);


            }

        }
        finally {
        }
    }
    // $ANTLR end BinaryExponentPart

    // $ANTLR start FloatingSuffix
    public final void mFloatingSuffix() throws RecognitionException {
        try {
            // GNUCa.g:819:2: ( 'f' | 'l' | 'F' | 'L' | 'i' | 'j' | 'I' | 'J' )
            // GNUCa.g:
            {
            if ( input.LA(1)=='F'||(input.LA(1)>='I' && input.LA(1)<='J')||input.LA(1)=='L'||input.LA(1)=='f'||(input.LA(1)>='i' && input.LA(1)<='j')||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recover(mse);    throw mse;
            }


            }

        }
        finally {
        }
    }
    // $ANTLR end FloatingSuffix

    // $ANTLR start WS
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            // GNUCa.g:826:5: ( ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' ) )
            // GNUCa.g:826:8: ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' )
            {
            if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||(input.LA(1)>='\f' && input.LA(1)<='\r')||input.LA(1)==' ' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recover(mse);    throw mse;
            }

            channel=HIDDEN;

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end WS

    // $ANTLR start COMMENT
    public final void mCOMMENT() throws RecognitionException {
        try {
            int _type = COMMENT;
            // GNUCa.g:830:5: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // GNUCa.g:830:9: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // GNUCa.g:830:14: ( options {greedy=false; } : . )*
            loop45:
            do {
                int alt45=2;
                int LA45_0 = input.LA(1);

                if ( (LA45_0=='*') ) {
                    int LA45_1 = input.LA(2);

                    if ( (LA45_1=='/') ) {
                        alt45=2;
                    }
                    else if ( ((LA45_1>='\u0000' && LA45_1<='.')||(LA45_1>='0' && LA45_1<='\uFFFE')) ) {
                        alt45=1;
                    }


                }
                else if ( ((LA45_0>='\u0000' && LA45_0<=')')||(LA45_0>='+' && LA45_0<='\uFFFE')) ) {
                    alt45=1;
                }


                switch (alt45) {
            	case 1 :
            	    // GNUCa.g:830:42: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop45;
                }
            } while (true);

            match("*/"); 

            channel=HIDDEN;

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end COMMENT

    // $ANTLR start LINE_COMMENT
    public final void mLINE_COMMENT() throws RecognitionException {
        try {
            int _type = LINE_COMMENT;
            // GNUCa.g:834:5: ( '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n' )
            // GNUCa.g:834:7: '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n'
            {
            match("//"); 

            // GNUCa.g:834:12: (~ ( '\\n' | '\\r' ) )*
            loop46:
            do {
                int alt46=2;
                int LA46_0 = input.LA(1);

                if ( ((LA46_0>='\u0000' && LA46_0<='\t')||(LA46_0>='\u000B' && LA46_0<='\f')||(LA46_0>='\u000E' && LA46_0<='\uFFFE')) ) {
                    alt46=1;
                }


                switch (alt46) {
            	case 1 :
            	    // GNUCa.g:834:12: ~ ( '\\n' | '\\r' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFE') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse =
            	            new MismatchedSetException(null,input);
            	        recover(mse);    throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop46;
                }
            } while (true);

            // GNUCa.g:834:26: ( '\\r' )?
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( (LA47_0=='\r') ) {
                alt47=1;
            }
            switch (alt47) {
                case 1 :
                    // GNUCa.g:834:26: '\\r'
                    {
                    match('\r'); 

                    }
                    break;

            }

            match('\n'); 
            channel=HIDDEN;

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end LINE_COMMENT

    // $ANTLR start LINE_COMMAND
    public final void mLINE_COMMAND() throws RecognitionException {
        try {
            int _type = LINE_COMMAND;
            // GNUCa.g:839:5: ( '#' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n' )
            // GNUCa.g:839:7: '#' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n'
            {
            match('#'); 
            // GNUCa.g:839:11: (~ ( '\\n' | '\\r' ) )*
            loop48:
            do {
                int alt48=2;
                int LA48_0 = input.LA(1);

                if ( ((LA48_0>='\u0000' && LA48_0<='\t')||(LA48_0>='\u000B' && LA48_0<='\f')||(LA48_0>='\u000E' && LA48_0<='\uFFFE')) ) {
                    alt48=1;
                }


                switch (alt48) {
            	case 1 :
            	    // GNUCa.g:839:11: ~ ( '\\n' | '\\r' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFE') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse =
            	            new MismatchedSetException(null,input);
            	        recover(mse);    throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop48;
                }
            } while (true);

            // GNUCa.g:839:25: ( '\\r' )?
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0=='\r') ) {
                alt49=1;
            }
            switch (alt49) {
                case 1 :
                    // GNUCa.g:839:25: '\\r'
                    {
                    match('\r'); 

                    }
                    break;

            }

            match('\n'); 
            channel=HIDDEN;

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end LINE_COMMAND

    public void mTokens() throws RecognitionException {
        // GNUCa.g:1:8: ( ALIGNOF | T79 | T80 | T81 | T82 | T83 | T84 | T85 | T86 | T87 | T88 | T89 | T90 | T91 | T92 | T93 | T94 | T95 | T96 | T97 | T98 | T99 | T100 | T101 | T102 | T103 | T104 | T105 | T106 | T107 | T108 | T109 | T110 | T111 | T112 | T113 | T114 | T115 | T116 | T117 | T118 | T119 | T120 | T121 | T122 | T123 | T124 | T125 | T126 | T127 | T128 | T129 | T130 | T131 | T132 | T133 | T134 | T135 | T136 | T137 | T138 | T139 | T140 | T141 | T142 | T143 | T144 | T145 | T146 | T147 | T148 | T149 | T150 | T151 | T152 | T153 | T154 | T155 | T156 | T157 | T158 | T159 | T160 | T161 | T162 | T163 | T164 | T165 | T166 | T167 | T168 | T169 | T170 | T171 | T172 | T173 | T174 | T175 | T176 | T177 | T178 | T179 | T180 | T181 | T182 | T183 | T184 | T185 | T186 | T187 | T188 | T189 | EXTENSION | IDENTIFIER | STRING_LITERAL | CONSTANT | WS | COMMENT | LINE_COMMENT | LINE_COMMAND )
        int alt50=120;
        switch ( input.LA(1) ) {
        case 'a':
            {
            switch ( input.LA(2) ) {
            case 'u':
                {
                int LA50_47 = input.LA(3);

                if ( (LA50_47=='t') ) {
                    int LA50_113 = input.LA(4);

                    if ( (LA50_113=='o') ) {
                        int LA50_164 = input.LA(5);

                        if ( (LA50_164=='$'||(LA50_164>='0' && LA50_164<='9')||(LA50_164>='A' && LA50_164<='Z')||LA50_164=='_'||(LA50_164>='a' && LA50_164<='z')) ) {
                            alt50=114;
                        }
                        else {
                            alt50=13;}
                    }
                    else {
                        alt50=114;}
                }
                else {
                    alt50=114;}
                }
                break;
            case 's':
                {
                int LA50_48 = input.LA(3);

                if ( (LA50_48=='m') ) {
                    int LA50_114 = input.LA(4);

                    if ( (LA50_114=='$'||(LA50_114>='0' && LA50_114<='9')||(LA50_114>='A' && LA50_114<='Z')||LA50_114=='_'||(LA50_114>='a' && LA50_114<='z')) ) {
                        alt50=114;
                    }
                    else {
                        alt50=3;}
                }
                else {
                    alt50=114;}
                }
                break;
            case 'l':
                {
                int LA50_49 = input.LA(3);

                if ( (LA50_49=='i') ) {
                    int LA50_115 = input.LA(4);

                    if ( (LA50_115=='g') ) {
                        int LA50_166 = input.LA(5);

                        if ( (LA50_166=='n') ) {
                            int LA50_215 = input.LA(6);

                            if ( (LA50_215=='o') ) {
                                int LA50_264 = input.LA(7);

                                if ( (LA50_264=='f') ) {
                                    int LA50_307 = input.LA(8);

                                    if ( (LA50_307=='$'||(LA50_307>='0' && LA50_307<='9')||(LA50_307>='A' && LA50_307<='Z')||LA50_307=='_'||(LA50_307>='a' && LA50_307<='z')) ) {
                                        alt50=114;
                                    }
                                    else {
                                        alt50=1;}
                                }
                                else {
                                    alt50=114;}
                            }
                            else {
                                alt50=114;}
                        }
                        else {
                            alt50=114;}
                    }
                    else {
                        alt50=114;}
                }
                else {
                    alt50=114;}
                }
                break;
            default:
                alt50=114;}

            }
            break;
        case ';':
            {
            alt50=2;
            }
            break;
        case '_':
            {
            switch ( input.LA(2) ) {
            case '_':
                {
                switch ( input.LA(3) ) {
                case 'v':
                    {
                    int LA50_116 = input.LA(4);

                    if ( (LA50_116=='o') ) {
                        int LA50_167 = input.LA(5);

                        if ( (LA50_167=='l') ) {
                            int LA50_216 = input.LA(6);

                            if ( (LA50_216=='a') ) {
                                int LA50_265 = input.LA(7);

                                if ( (LA50_265=='t') ) {
                                    int LA50_308 = input.LA(8);

                                    if ( (LA50_308=='i') ) {
                                        int LA50_345 = input.LA(9);

                                        if ( (LA50_345=='l') ) {
                                            int LA50_370 = input.LA(10);

                                            if ( (LA50_370=='e') ) {
                                                switch ( input.LA(11) ) {
                                                case '_':
                                                    {
                                                    int LA50_408 = input.LA(12);

                                                    if ( (LA50_408=='_') ) {
                                                        int LA50_421 = input.LA(13);

                                                        if ( (LA50_421=='$'||(LA50_421>='0' && LA50_421<='9')||(LA50_421>='A' && LA50_421<='Z')||LA50_421=='_'||(LA50_421>='a' && LA50_421<='z')) ) {
                                                            alt50=114;
                                                        }
                                                        else {
                                                            alt50=46;}
                                                    }
                                                    else {
                                                        alt50=114;}
                                                    }
                                                    break;
                                                case '$':
                                                case '0':
                                                case '1':
                                                case '2':
                                                case '3':
                                                case '4':
                                                case '5':
                                                case '6':
                                                case '7':
                                                case '8':
                                                case '9':
                                                case 'A':
                                                case 'B':
                                                case 'C':
                                                case 'D':
                                                case 'E':
                                                case 'F':
                                                case 'G':
                                                case 'H':
                                                case 'I':
                                                case 'J':
                                                case 'K':
                                                case 'L':
                                                case 'M':
                                                case 'N':
                                                case 'O':
                                                case 'P':
                                                case 'Q':
                                                case 'R':
                                                case 'S':
                                                case 'T':
                                                case 'U':
                                                case 'V':
                                                case 'W':
                                                case 'X':
                                                case 'Y':
                                                case 'Z':
                                                case 'a':
                                                case 'b':
                                                case 'c':
                                                case 'd':
                                                case 'e':
                                                case 'f':
                                                case 'g':
                                                case 'h':
                                                case 'i':
                                                case 'j':
                                                case 'k':
                                                case 'l':
                                                case 'm':
                                                case 'n':
                                                case 'o':
                                                case 'p':
                                                case 'q':
                                                case 'r':
                                                case 's':
                                                case 't':
                                                case 'u':
                                                case 'v':
                                                case 'w':
                                                case 'x':
                                                case 'y':
                                                case 'z':
                                                    {
                                                    alt50=114;
                                                    }
                                                    break;
                                                default:
                                                    alt50=45;}

                                            }
                                            else {
                                                alt50=114;}
                                        }
                                        else {
                                            alt50=114;}
                                    }
                                    else {
                                        alt50=114;}
                                }
                                else {
                                    alt50=114;}
                            }
                            else {
                                alt50=114;}
                        }
                        else {
                            alt50=114;}
                    }
                    else {
                        alt50=114;}
                    }
                    break;
                case 'i':
                    {
                    switch ( input.LA(4) ) {
                    case 'n':
                        {
                        int LA50_168 = input.LA(5);

                        if ( (LA50_168=='l') ) {
                            int LA50_217 = input.LA(6);

                            if ( (LA50_217=='i') ) {
                                int LA50_266 = input.LA(7);

                                if ( (LA50_266=='n') ) {
                                    int LA50_309 = input.LA(8);

                                    if ( (LA50_309=='e') ) {
                                        switch ( input.LA(9) ) {
                                        case '_':
                                            {
                                            int LA50_371 = input.LA(10);

                                            if ( (LA50_371=='_') ) {
                                                int LA50_395 = input.LA(11);

                                                if ( (LA50_395=='$'||(LA50_395>='0' && LA50_395<='9')||(LA50_395>='A' && LA50_395<='Z')||LA50_395=='_'||(LA50_395>='a' && LA50_395<='z')) ) {
                                                    alt50=114;
                                                }
                                                else {
                                                    alt50=49;}
                                            }
                                            else {
                                                alt50=114;}
                                            }
                                            break;
                                        case '$':
                                        case '0':
                                        case '1':
                                        case '2':
                                        case '3':
                                        case '4':
                                        case '5':
                                        case '6':
                                        case '7':
                                        case '8':
                                        case '9':
                                        case 'A':
                                        case 'B':
                                        case 'C':
                                        case 'D':
                                        case 'E':
                                        case 'F':
                                        case 'G':
                                        case 'H':
                                        case 'I':
                                        case 'J':
                                        case 'K':
                                        case 'L':
                                        case 'M':
                                        case 'N':
                                        case 'O':
                                        case 'P':
                                        case 'Q':
                                        case 'R':
                                        case 'S':
                                        case 'T':
                                        case 'U':
                                        case 'V':
                                        case 'W':
                                        case 'X':
                                        case 'Y':
                                        case 'Z':
                                        case 'a':
                                        case 'b':
                                        case 'c':
                                        case 'd':
                                        case 'e':
                                        case 'f':
                                        case 'g':
                                        case 'h':
                                        case 'i':
                                        case 'j':
                                        case 'k':
                                        case 'l':
                                        case 'm':
                                        case 'n':
                                        case 'o':
                                        case 'p':
                                        case 'q':
                                        case 'r':
                                        case 's':
                                        case 't':
                                        case 'u':
                                        case 'v':
                                        case 'w':
                                        case 'x':
                                        case 'y':
                                        case 'z':
                                            {
                                            alt50=114;
                                            }
                                            break;
                                        default:
                                            alt50=48;}

                                    }
                                    else {
                                        alt50=114;}
                                }
                                else {
                                    alt50=114;}
                            }
                            else {
                                alt50=114;}
                        }
                        else {
                            alt50=114;}
                        }
                        break;
                    case 'm':
                        {
                        int LA50_169 = input.LA(5);

                        if ( (LA50_169=='a') ) {
                            int LA50_218 = input.LA(6);

                            if ( (LA50_218=='g') ) {
                                switch ( input.LA(7) ) {
                                case '_':
                                    {
                                    int LA50_310 = input.LA(8);

                                    if ( (LA50_310=='_') ) {
                                        int LA50_347 = input.LA(9);

                                        if ( (LA50_347=='$'||(LA50_347>='0' && LA50_347<='9')||(LA50_347>='A' && LA50_347<='Z')||LA50_347=='_'||(LA50_347>='a' && LA50_347<='z')) ) {
                                            alt50=114;
                                        }
                                        else {
                                            alt50=76;}
                                    }
                                    else {
                                        alt50=114;}
                                    }
                                    break;
                                case '$':
                                case '0':
                                case '1':
                                case '2':
                                case '3':
                                case '4':
                                case '5':
                                case '6':
                                case '7':
                                case '8':
                                case '9':
                                case 'A':
                                case 'B':
                                case 'C':
                                case 'D':
                                case 'E':
                                case 'F':
                                case 'G':
                                case 'H':
                                case 'I':
                                case 'J':
                                case 'K':
                                case 'L':
                                case 'M':
                                case 'N':
                                case 'O':
                                case 'P':
                                case 'Q':
                                case 'R':
                                case 'S':
                                case 'T':
                                case 'U':
                                case 'V':
                                case 'W':
                                case 'X':
                                case 'Y':
                                case 'Z':
                                case 'a':
                                case 'b':
                                case 'c':
                                case 'd':
                                case 'e':
                                case 'f':
                                case 'g':
                                case 'h':
                                case 'i':
                                case 'j':
                                case 'k':
                                case 'l':
                                case 'm':
                                case 'n':
                                case 'o':
                                case 'p':
                                case 'q':
                                case 'r':
                                case 's':
                                case 't':
                                case 'u':
                                case 'v':
                                case 'w':
                                case 'x':
                                case 'y':
                                case 'z':
                                    {
                                    alt50=114;
                                    }
                                    break;
                                default:
                                    alt50=75;}

                            }
                            else {
                                alt50=114;}
                        }
                        else {
                            alt50=114;}
                        }
                        break;
                    default:
                        alt50=114;}

                    }
                    break;
                case 'b':
                    {
                    int LA50_118 = input.LA(4);

                    if ( (LA50_118=='u') ) {
                        int LA50_170 = input.LA(5);

                        if ( (LA50_170=='i') ) {
                            int LA50_219 = input.LA(6);

                            if ( (LA50_219=='l') ) {
                                int LA50_268 = input.LA(7);

                                if ( (LA50_268=='t') ) {
                                    int LA50_312 = input.LA(8);

                                    if ( (LA50_312=='i') ) {
                                        int LA50_348 = input.LA(9);

                                        if ( (LA50_348=='n') ) {
                                            int LA50_374 = input.LA(10);

                                            if ( (LA50_374=='_') ) {
                                                int LA50_396 = input.LA(11);

                                                if ( (LA50_396=='o') ) {
                                                    int LA50_411 = input.LA(12);

                                                    if ( (LA50_411=='f') ) {
                                                        int LA50_422 = input.LA(13);

                                                        if ( (LA50_422=='f') ) {
                                                            int LA50_430 = input.LA(14);

                                                            if ( (LA50_430=='s') ) {
                                                                int LA50_434 = input.LA(15);

                                                                if ( (LA50_434=='e') ) {
                                                                    int LA50_437 = input.LA(16);

                                                                    if ( (LA50_437=='t') ) {
                                                                        int LA50_438 = input.LA(17);

                                                                        if ( (LA50_438=='o') ) {
                                                                            int LA50_439 = input.LA(18);

                                                                            if ( (LA50_439=='f') ) {
                                                                                int LA50_440 = input.LA(19);

                                                                                if ( (LA50_440=='$'||(LA50_440>='0' && LA50_440<='9')||(LA50_440>='A' && LA50_440<='Z')||LA50_440=='_'||(LA50_440>='a' && LA50_440<='z')) ) {
                                                                                    alt50=114;
                                                                                }
                                                                                else {
                                                                                    alt50=60;}
                                                                            }
                                                                            else {
                                                                                alt50=114;}
                                                                        }
                                                                        else {
                                                                            alt50=114;}
                                                                    }
                                                                    else {
                                                                        alt50=114;}
                                                                }
                                                                else {
                                                                    alt50=114;}
                                                            }
                                                            else {
                                                                alt50=114;}
                                                        }
                                                        else {
                                                            alt50=114;}
                                                    }
                                                    else {
                                                        alt50=114;}
                                                }
                                                else {
                                                    alt50=114;}
                                            }
                                            else {
                                                alt50=114;}
                                        }
                                        else {
                                            alt50=114;}
                                    }
                                    else {
                                        alt50=114;}
                                }
                                else {
                                    alt50=114;}
                            }
                            else {
                                alt50=114;}
                        }
                        else {
                            alt50=114;}
                    }
                    else {
                        alt50=114;}
                    }
                    break;
                case 'a':
                    {
                    switch ( input.LA(4) ) {
                    case 'l':
                        {
                        int LA50_171 = input.LA(5);

                        if ( (LA50_171=='i') ) {
                            int LA50_220 = input.LA(6);

                            if ( (LA50_220=='g') ) {
                                int LA50_269 = input.LA(7);

                                if ( (LA50_269=='n') ) {
                                    int LA50_313 = input.LA(8);

                                    if ( (LA50_313=='o') ) {
                                        int LA50_349 = input.LA(9);

                                        if ( (LA50_349=='f') ) {
                                            switch ( input.LA(10) ) {
                                            case '_':
                                                {
                                                int LA50_397 = input.LA(11);

                                                if ( (LA50_397=='_') ) {
                                                    int LA50_412 = input.LA(12);

                                                    if ( (LA50_412=='$'||(LA50_412>='0' && LA50_412<='9')||(LA50_412>='A' && LA50_412<='Z')||LA50_412=='_'||(LA50_412>='a' && LA50_412<='z')) ) {
                                                        alt50=114;
                                                    }
                                                    else {
                                                        alt50=66;}
                                                }
                                                else {
                                                    alt50=114;}
                                                }
                                                break;
                                            case '$':
                                            case '0':
                                            case '1':
                                            case '2':
                                            case '3':
                                            case '4':
                                            case '5':
                                            case '6':
                                            case '7':
                                            case '8':
                                            case '9':
                                            case 'A':
                                            case 'B':
                                            case 'C':
                                            case 'D':
                                            case 'E':
                                            case 'F':
                                            case 'G':
                                            case 'H':
                                            case 'I':
                                            case 'J':
                                            case 'K':
                                            case 'L':
                                            case 'M':
                                            case 'N':
                                            case 'O':
                                            case 'P':
                                            case 'Q':
                                            case 'R':
                                            case 'S':
                                            case 'T':
                                            case 'U':
                                            case 'V':
                                            case 'W':
                                            case 'X':
                                            case 'Y':
                                            case 'Z':
                                            case 'a':
                                            case 'b':
                                            case 'c':
                                            case 'd':
                                            case 'e':
                                            case 'f':
                                            case 'g':
                                            case 'h':
                                            case 'i':
                                            case 'j':
                                            case 'k':
                                            case 'l':
                                            case 'm':
                                            case 'n':
                                            case 'o':
                                            case 'p':
                                            case 'q':
                                            case 'r':
                                            case 's':
                                            case 't':
                                            case 'u':
                                            case 'v':
                                            case 'w':
                                            case 'x':
                                            case 'y':
                                            case 'z':
                                                {
                                                alt50=114;
                                                }
                                                break;
                                            default:
                                                alt50=65;}

                                        }
                                        else {
                                            alt50=114;}
                                    }
                                    else {
                                        alt50=114;}
                                }
                                else {
                                    alt50=114;}
                            }
                            else {
                                alt50=114;}
                        }
                        else {
                            alt50=114;}
                        }
                        break;
                    case 's':
                        {
                        int LA50_172 = input.LA(5);

                        if ( (LA50_172=='m') ) {
                            switch ( input.LA(6) ) {
                            case '_':
                                {
                                int LA50_270 = input.LA(7);

                                if ( (LA50_270=='_') ) {
                                    int LA50_314 = input.LA(8);

                                    if ( (LA50_314=='$'||(LA50_314>='0' && LA50_314<='9')||(LA50_314>='A' && LA50_314<='Z')||LA50_314=='_'||(LA50_314>='a' && LA50_314<='z')) ) {
                                        alt50=114;
                                    }
                                    else {
                                        alt50=5;}
                                }
                                else {
                                    alt50=114;}
                                }
                                break;
                            case '$':
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                            case 'G':
                            case 'H':
                            case 'I':
                            case 'J':
                            case 'K':
                            case 'L':
                            case 'M':
                            case 'N':
                            case 'O':
                            case 'P':
                            case 'Q':
                            case 'R':
                            case 'S':
                            case 'T':
                            case 'U':
                            case 'V':
                            case 'W':
                            case 'X':
                            case 'Y':
                            case 'Z':
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                            case 'g':
                            case 'h':
                            case 'i':
                            case 'j':
                            case 'k':
                            case 'l':
                            case 'm':
                            case 'n':
                            case 'o':
                            case 'p':
                            case 'q':
                            case 'r':
                            case 's':
                            case 't':
                            case 'u':
                            case 'v':
                            case 'w':
                            case 'x':
                            case 'y':
                            case 'z':
                                {
                                alt50=114;
                                }
                                break;
                            default:
                                alt50=4;}

                        }
                        else {
                            alt50=114;}
                        }
                        break;
                    case 't':
                        {
                        int LA50_173 = input.LA(5);

                        if ( (LA50_173=='t') ) {
                            int LA50_222 = input.LA(6);

                            if ( (LA50_222=='r') ) {
                                int LA50_272 = input.LA(7);

                                if ( (LA50_272=='i') ) {
                                    int LA50_315 = input.LA(8);

                                    if ( (LA50_315=='b') ) {
                                        int LA50_351 = input.LA(9);

                                        if ( (LA50_351=='u') ) {
                                            int LA50_376 = input.LA(10);

                                            if ( (LA50_376=='t') ) {
                                                int LA50_399 = input.LA(11);

                                                if ( (LA50_399=='e') ) {
                                                    switch ( input.LA(12) ) {
                                                    case '_':
                                                        {
                                                        int LA50_424 = input.LA(13);

                                                        if ( (LA50_424=='_') ) {
                                                            int LA50_431 = input.LA(14);

                                                            if ( (LA50_431=='$'||(LA50_431>='0' && LA50_431<='9')||(LA50_431>='A' && LA50_431<='Z')||LA50_431=='_'||(LA50_431>='a' && LA50_431<='z')) ) {
                                                                alt50=114;
                                                            }
                                                            else {
                                                                alt50=59;}
                                                        }
                                                        else {
                                                            alt50=114;}
                                                        }
                                                        break;
                                                    case '$':
                                                    case '0':
                                                    case '1':
                                                    case '2':
                                                    case '3':
                                                    case '4':
                                                    case '5':
                                                    case '6':
                                                    case '7':
                                                    case '8':
                                                    case '9':
                                                    case 'A':
                                                    case 'B':
                                                    case 'C':
                                                    case 'D':
                                                    case 'E':
                                                    case 'F':
                                                    case 'G':
                                                    case 'H':
                                                    case 'I':
                                                    case 'J':
                                                    case 'K':
                                                    case 'L':
                                                    case 'M':
                                                    case 'N':
                                                    case 'O':
                                                    case 'P':
                                                    case 'Q':
                                                    case 'R':
                                                    case 'S':
                                                    case 'T':
                                                    case 'U':
                                                    case 'V':
                                                    case 'W':
                                                    case 'X':
                                                    case 'Y':
                                                    case 'Z':
                                                    case 'a':
                                                    case 'b':
                                                    case 'c':
                                                    case 'd':
                                                    case 'e':
                                                    case 'f':
                                                    case 'g':
                                                    case 'h':
                                                    case 'i':
                                                    case 'j':
                                                    case 'k':
                                                    case 'l':
                                                    case 'm':
                                                    case 'n':
                                                    case 'o':
                                                    case 'p':
                                                    case 'q':
                                                    case 'r':
                                                    case 's':
                                                    case 't':
                                                    case 'u':
                                                    case 'v':
                                                    case 'w':
                                                    case 'x':
                                                    case 'y':
                                                    case 'z':
                                                        {
                                                        alt50=114;
                                                        }
                                                        break;
                                                    default:
                                                        alt50=58;}

                                                }
                                                else {
                                                    alt50=114;}
                                            }
                                            else {
                                                alt50=114;}
                                        }
                                        else {
                                            alt50=114;}
                                    }
                                    else {
                                        alt50=114;}
                                }
                                else {
                                    alt50=114;}
                            }
                            else {
                                alt50=114;}
                        }
                        else {
                            alt50=114;}
                        }
                        break;
                    default:
                        alt50=114;}

                    }
                    break;
                case 'c':
                    {
                    int LA50_120 = input.LA(4);

                    if ( (LA50_120=='o') ) {
                        switch ( input.LA(5) ) {
                        case 'm':
                            {
                            int LA50_223 = input.LA(6);

                            if ( (LA50_223=='p') ) {
                                int LA50_273 = input.LA(7);

                                if ( (LA50_273=='l') ) {
                                    int LA50_316 = input.LA(8);

                                    if ( (LA50_316=='e') ) {
                                        int LA50_352 = input.LA(9);

                                        if ( (LA50_352=='x') ) {
                                            switch ( input.LA(10) ) {
                                            case '_':
                                                {
                                                int LA50_400 = input.LA(11);

                                                if ( (LA50_400=='_') ) {
                                                    int LA50_414 = input.LA(12);

                                                    if ( (LA50_414=='$'||(LA50_414>='0' && LA50_414<='9')||(LA50_414>='A' && LA50_414<='Z')||LA50_414=='_'||(LA50_414>='a' && LA50_414<='z')) ) {
                                                        alt50=114;
                                                    }
                                                    else {
                                                        alt50=30;}
                                                }
                                                else {
                                                    alt50=114;}
                                                }
                                                break;
                                            case '$':
                                            case '0':
                                            case '1':
                                            case '2':
                                            case '3':
                                            case '4':
                                            case '5':
                                            case '6':
                                            case '7':
                                            case '8':
                                            case '9':
                                            case 'A':
                                            case 'B':
                                            case 'C':
                                            case 'D':
                                            case 'E':
                                            case 'F':
                                            case 'G':
                                            case 'H':
                                            case 'I':
                                            case 'J':
                                            case 'K':
                                            case 'L':
                                            case 'M':
                                            case 'N':
                                            case 'O':
                                            case 'P':
                                            case 'Q':
                                            case 'R':
                                            case 'S':
                                            case 'T':
                                            case 'U':
                                            case 'V':
                                            case 'W':
                                            case 'X':
                                            case 'Y':
                                            case 'Z':
                                            case 'a':
                                            case 'b':
                                            case 'c':
                                            case 'd':
                                            case 'e':
                                            case 'f':
                                            case 'g':
                                            case 'h':
                                            case 'i':
                                            case 'j':
                                            case 'k':
                                            case 'l':
                                            case 'm':
                                            case 'n':
                                            case 'o':
                                            case 'p':
                                            case 'q':
                                            case 'r':
                                            case 's':
                                            case 't':
                                            case 'u':
                                            case 'v':
                                            case 'w':
                                            case 'x':
                                            case 'y':
                                            case 'z':
                                                {
                                                alt50=114;
                                                }
                                                break;
                                            default:
                                                alt50=29;}

                                        }
                                        else {
                                            alt50=114;}
                                    }
                                    else {
                                        alt50=114;}
                                }
                                else {
                                    alt50=114;}
                            }
                            else {
                                alt50=114;}
                            }
                            break;
                        case 'n':
                            {
                            int LA50_224 = input.LA(6);

                            if ( (LA50_224=='s') ) {
                                int LA50_274 = input.LA(7);

                                if ( (LA50_274=='t') ) {
                                    switch ( input.LA(8) ) {
                                    case '_':
                                        {
                                        int LA50_353 = input.LA(9);

                                        if ( (LA50_353=='_') ) {
                                            int LA50_378 = input.LA(10);

                                            if ( (LA50_378=='$'||(LA50_378>='0' && LA50_378<='9')||(LA50_378>='A' && LA50_378<='Z')||LA50_378=='_'||(LA50_378>='a' && LA50_378<='z')) ) {
                                                alt50=114;
                                            }
                                            else {
                                                alt50=40;}
                                        }
                                        else {
                                            alt50=114;}
                                        }
                                        break;
                                    case '$':
                                    case '0':
                                    case '1':
                                    case '2':
                                    case '3':
                                    case '4':
                                    case '5':
                                    case '6':
                                    case '7':
                                    case '8':
                                    case '9':
                                    case 'A':
                                    case 'B':
                                    case 'C':
                                    case 'D':
                                    case 'E':
                                    case 'F':
                                    case 'G':
                                    case 'H':
                                    case 'I':
                                    case 'J':
                                    case 'K':
                                    case 'L':
                                    case 'M':
                                    case 'N':
                                    case 'O':
                                    case 'P':
                                    case 'Q':
                                    case 'R':
                                    case 'S':
                                    case 'T':
                                    case 'U':
                                    case 'V':
                                    case 'W':
                                    case 'X':
                                    case 'Y':
                                    case 'Z':
                                    case 'a':
                                    case 'b':
                                    case 'c':
                                    case 'd':
                                    case 'e':
                                    case 'f':
                                    case 'g':
                                    case 'h':
                                    case 'i':
                                    case 'j':
                                    case 'k':
                                    case 'l':
                                    case 'm':
                                    case 'n':
                                    case 'o':
                                    case 'p':
                                    case 'q':
                                    case 'r':
                                    case 's':
                                    case 't':
                                    case 'u':
                                    case 'v':
                                    case 'w':
                                    case 'x':
                                    case 'y':
                                    case 'z':
                                        {
                                        alt50=114;
                                        }
                                        break;
                                    default:
                                        alt50=39;}

                                }
                                else {
                                    alt50=114;}
                            }
                            else {
                                alt50=114;}
                            }
                            break;
                        default:
                            alt50=114;}

                    }
                    else {
                        alt50=114;}
                    }
                    break;
                case 't':
                    {
                    switch ( input.LA(4) ) {
                    case 'y':
                        {
                        int LA50_175 = input.LA(5);

                        if ( (LA50_175=='p') ) {
                            int LA50_225 = input.LA(6);

                            if ( (LA50_225=='e') ) {
                                int LA50_275 = input.LA(7);

                                if ( (LA50_275=='o') ) {
                                    int LA50_318 = input.LA(8);

                                    if ( (LA50_318=='f') ) {
                                        switch ( input.LA(9) ) {
                                        case '_':
                                            {
                                            int LA50_379 = input.LA(10);

                                            if ( (LA50_379=='_') ) {
                                                int LA50_403 = input.LA(11);

                                                if ( (LA50_403=='$'||(LA50_403>='0' && LA50_403<='9')||(LA50_403>='A' && LA50_403<='Z')||LA50_403=='_'||(LA50_403>='a' && LA50_403<='z')) ) {
                                                    alt50=114;
                                                }
                                                else {
                                                    alt50=56;}
                                            }
                                            else {
                                                alt50=114;}
                                            }
                                            break;
                                        case '$':
                                        case '0':
                                        case '1':
                                        case '2':
                                        case '3':
                                        case '4':
                                        case '5':
                                        case '6':
                                        case '7':
                                        case '8':
                                        case '9':
                                        case 'A':
                                        case 'B':
                                        case 'C':
                                        case 'D':
                                        case 'E':
                                        case 'F':
                                        case 'G':
                                        case 'H':
                                        case 'I':
                                        case 'J':
                                        case 'K':
                                        case 'L':
                                        case 'M':
                                        case 'N':
                                        case 'O':
                                        case 'P':
                                        case 'Q':
                                        case 'R':
                                        case 'S':
                                        case 'T':
                                        case 'U':
                                        case 'V':
                                        case 'W':
                                        case 'X':
                                        case 'Y':
                                        case 'Z':
                                        case 'a':
                                        case 'b':
                                        case 'c':
                                        case 'd':
                                        case 'e':
                                        case 'f':
                                        case 'g':
                                        case 'h':
                                        case 'i':
                                        case 'j':
                                        case 'k':
                                        case 'l':
                                        case 'm':
                                        case 'n':
                                        case 'o':
                                        case 'p':
                                        case 'q':
                                        case 'r':
                                        case 's':
                                        case 't':
                                        case 'u':
                                        case 'v':
                                        case 'w':
                                        case 'x':
                                        case 'y':
                                        case 'z':
                                            {
                                            alt50=114;
                                            }
                                            break;
                                        default:
                                            alt50=55;}

                                    }
                                    else {
                                        alt50=114;}
                                }
                                else {
                                    alt50=114;}
                            }
                            else {
                                alt50=114;}
                        }
                        else {
                            alt50=114;}
                        }
                        break;
                    case 'h':
                        {
                        int LA50_176 = input.LA(5);

                        if ( (LA50_176=='r') ) {
                            int LA50_226 = input.LA(6);

                            if ( (LA50_226=='e') ) {
                                int LA50_276 = input.LA(7);

                                if ( (LA50_276=='a') ) {
                                    int LA50_319 = input.LA(8);

                                    if ( (LA50_319=='d') ) {
                                        int LA50_356 = input.LA(9);

                                        if ( (LA50_356=='$'||(LA50_356>='0' && LA50_356<='9')||(LA50_356>='A' && LA50_356<='Z')||LA50_356=='_'||(LA50_356>='a' && LA50_356<='z')) ) {
                                            alt50=114;
                                        }
                                        else {
                                            alt50=15;}
                                    }
                                    else {
                                        alt50=114;}
                                }
                                else {
                                    alt50=114;}
                            }
                            else {
                                alt50=114;}
                        }
                        else {
                            alt50=114;}
                        }
                        break;
                    default:
                        alt50=114;}

                    }
                    break;
                case 's':
                    {
                    int LA50_122 = input.LA(4);

                    if ( (LA50_122=='i') ) {
                        int LA50_177 = input.LA(5);

                        if ( (LA50_177=='g') ) {
                            int LA50_227 = input.LA(6);

                            if ( (LA50_227=='n') ) {
                                int LA50_277 = input.LA(7);

                                if ( (LA50_277=='e') ) {
                                    int LA50_320 = input.LA(8);

                                    if ( (LA50_320=='d') ) {
                                        switch ( input.LA(9) ) {
                                        case '_':
                                            {
                                            int LA50_382 = input.LA(10);

                                            if ( (LA50_382=='_') ) {
                                                int LA50_404 = input.LA(11);

                                                if ( (LA50_404=='$'||(LA50_404>='0' && LA50_404<='9')||(LA50_404>='A' && LA50_404<='Z')||LA50_404=='_'||(LA50_404>='a' && LA50_404<='z')) ) {
                                                    alt50=114;
                                                }
                                                else {
                                                    alt50=25;}
                                            }
                                            else {
                                                alt50=114;}
                                            }
                                            break;
                                        case '$':
                                        case '0':
                                        case '1':
                                        case '2':
                                        case '3':
                                        case '4':
                                        case '5':
                                        case '6':
                                        case '7':
                                        case '8':
                                        case '9':
                                        case 'A':
                                        case 'B':
                                        case 'C':
                                        case 'D':
                                        case 'E':
                                        case 'F':
                                        case 'G':
                                        case 'H':
                                        case 'I':
                                        case 'J':
                                        case 'K':
                                        case 'L':
                                        case 'M':
                                        case 'N':
                                        case 'O':
                                        case 'P':
                                        case 'Q':
                                        case 'R':
                                        case 'S':
                                        case 'T':
                                        case 'U':
                                        case 'V':
                                        case 'W':
                                        case 'X':
                                        case 'Y':
                                        case 'Z':
                                        case 'a':
                                        case 'b':
                                        case 'c':
                                        case 'd':
                                        case 'e':
                                        case 'f':
                                        case 'g':
                                        case 'h':
                                        case 'i':
                                        case 'j':
                                        case 'k':
                                        case 'l':
                                        case 'm':
                                        case 'n':
                                        case 'o':
                                        case 'p':
                                        case 'q':
                                        case 'r':
                                        case 's':
                                        case 't':
                                        case 'u':
                                        case 'v':
                                        case 'w':
                                        case 'x':
                                        case 'y':
                                        case 'z':
                                            {
                                            alt50=114;
                                            }
                                            break;
                                        default:
                                            alt50=24;}

                                    }
                                    else {
                                        alt50=114;}
                                }
                                else {
                                    alt50=114;}
                            }
                            else {
                                alt50=114;}
                        }
                        else {
                            alt50=114;}
                    }
                    else {
                        alt50=114;}
                    }
                    break;
                case 'r':
                    {
                    int LA50_123 = input.LA(4);

                    if ( (LA50_123=='e') ) {
                        switch ( input.LA(5) ) {
                        case 'a':
                            {
                            int LA50_228 = input.LA(6);

                            if ( (LA50_228=='l') ) {
                                switch ( input.LA(7) ) {
                                case '_':
                                    {
                                    int LA50_321 = input.LA(8);

                                    if ( (LA50_321=='_') ) {
                                        int LA50_358 = input.LA(9);

                                        if ( (LA50_358=='$'||(LA50_358>='0' && LA50_358<='9')||(LA50_358>='A' && LA50_358<='Z')||LA50_358=='_'||(LA50_358>='a' && LA50_358<='z')) ) {
                                            alt50=114;
                                        }
                                        else {
                                            alt50=74;}
                                    }
                                    else {
                                        alt50=114;}
                                    }
                                    break;
                                case '$':
                                case '0':
                                case '1':
                                case '2':
                                case '3':
                                case '4':
                                case '5':
                                case '6':
                                case '7':
                                case '8':
                                case '9':
                                case 'A':
                                case 'B':
                                case 'C':
                                case 'D':
                                case 'E':
                                case 'F':
                                case 'G':
                                case 'H':
                                case 'I':
                                case 'J':
                                case 'K':
                                case 'L':
                                case 'M':
                                case 'N':
                                case 'O':
                                case 'P':
                                case 'Q':
                                case 'R':
                                case 'S':
                                case 'T':
                                case 'U':
                                case 'V':
                                case 'W':
                                case 'X':
                                case 'Y':
                                case 'Z':
                                case 'a':
                                case 'b':
                                case 'c':
                                case 'd':
                                case 'e':
                                case 'f':
                                case 'g':
                                case 'h':
                                case 'i':
                                case 'j':
                                case 'k':
                                case 'l':
                                case 'm':
                                case 'n':
                                case 'o':
                                case 'p':
                                case 'q':
                                case 'r':
                                case 's':
                                case 't':
                                case 'u':
                                case 'v':
                                case 'w':
                                case 'x':
                                case 'y':
                                case 'z':
                                    {
                                    alt50=114;
                                    }
                                    break;
                                default:
                                    alt50=73;}

                            }
                            else {
                                alt50=114;}
                            }
                            break;
                        case 's':
                            {
                            int LA50_229 = input.LA(6);

                            if ( (LA50_229=='t') ) {
                                int LA50_279 = input.LA(7);

                                if ( (LA50_279=='r') ) {
                                    int LA50_323 = input.LA(8);

                                    if ( (LA50_323=='i') ) {
                                        int LA50_359 = input.LA(9);

                                        if ( (LA50_359=='c') ) {
                                            int LA50_385 = input.LA(10);

                                            if ( (LA50_385=='t') ) {
                                                switch ( input.LA(11) ) {
                                                case '_':
                                                    {
                                                    int LA50_417 = input.LA(12);

                                                    if ( (LA50_417=='_') ) {
                                                        int LA50_427 = input.LA(13);

                                                        if ( (LA50_427=='$'||(LA50_427>='0' && LA50_427<='9')||(LA50_427>='A' && LA50_427<='Z')||LA50_427=='_'||(LA50_427>='a' && LA50_427<='z')) ) {
                                                            alt50=114;
                                                        }
                                                        else {
                                                            alt50=43;}
                                                    }
                                                    else {
                                                        alt50=114;}
                                                    }
                                                    break;
                                                case '$':
                                                case '0':
                                                case '1':
                                                case '2':
                                                case '3':
                                                case '4':
                                                case '5':
                                                case '6':
                                                case '7':
                                                case '8':
                                                case '9':
                                                case 'A':
                                                case 'B':
                                                case 'C':
                                                case 'D':
                                                case 'E':
                                                case 'F':
                                                case 'G':
                                                case 'H':
                                                case 'I':
                                                case 'J':
                                                case 'K':
                                                case 'L':
                                                case 'M':
                                                case 'N':
                                                case 'O':
                                                case 'P':
                                                case 'Q':
                                                case 'R':
                                                case 'S':
                                                case 'T':
                                                case 'U':
                                                case 'V':
                                                case 'W':
                                                case 'X':
                                                case 'Y':
                                                case 'Z':
                                                case 'a':
                                                case 'b':
                                                case 'c':
                                                case 'd':
                                                case 'e':
                                                case 'f':
                                                case 'g':
                                                case 'h':
                                                case 'i':
                                                case 'j':
                                                case 'k':
                                                case 'l':
                                                case 'm':
                                                case 'n':
                                                case 'o':
                                                case 'p':
                                                case 'q':
                                                case 'r':
                                                case 's':
                                                case 't':
                                                case 'u':
                                                case 'v':
                                                case 'w':
                                                case 'x':
                                                case 'y':
                                                case 'z':
                                                    {
                                                    alt50=114;
                                                    }
                                                    break;
                                                default:
                                                    alt50=42;}

                                            }
                                            else {
                                                alt50=114;}
                                        }
                                        else {
                                            alt50=114;}
                                    }
                                    else {
                                        alt50=114;}
                                }
                                else {
                                    alt50=114;}
                            }
                            else {
                                alt50=114;}
                            }
                            break;
                        default:
                            alt50=114;}

                    }
                    else {
                        alt50=114;}
                    }
                    break;
                case 'e':
                    {
                    int LA50_124 = input.LA(4);

                    if ( (LA50_124=='x') ) {
                        int LA50_179 = input.LA(5);

                        if ( (LA50_179=='t') ) {
                            int LA50_230 = input.LA(6);

                            if ( (LA50_230=='e') ) {
                                int LA50_280 = input.LA(7);

                                if ( (LA50_280=='n') ) {
                                    int LA50_324 = input.LA(8);

                                    if ( (LA50_324=='s') ) {
                                        int LA50_360 = input.LA(9);

                                        if ( (LA50_360=='i') ) {
                                            int LA50_386 = input.LA(10);

                                            if ( (LA50_386=='o') ) {
                                                int LA50_406 = input.LA(11);

                                                if ( (LA50_406=='n') ) {
                                                    int LA50_419 = input.LA(12);

                                                    if ( (LA50_419=='_') ) {
                                                        int LA50_428 = input.LA(13);

                                                        if ( (LA50_428=='_') ) {
                                                            int LA50_433 = input.LA(14);

                                                            if ( (LA50_433=='$'||(LA50_433>='0' && LA50_433<='9')||(LA50_433>='A' && LA50_433<='Z')||LA50_433=='_'||(LA50_433>='a' && LA50_433<='z')) ) {
                                                                alt50=114;
                                                            }
                                                            else {
                                                                alt50=113;}
                                                        }
                                                        else {
                                                            alt50=114;}
                                                    }
                                                    else {
                                                        alt50=114;}
                                                }
                                                else {
                                                    alt50=114;}
                                            }
                                            else {
                                                alt50=114;}
                                        }
                                        else {
                                            alt50=114;}
                                    }
                                    else {
                                        alt50=114;}
                                }
                                else {
                                    alt50=114;}
                            }
                            else {
                                alt50=114;}
                        }
                        else {
                            alt50=114;}
                    }
                    else {
                        alt50=114;}
                    }
                    break;
                default:
                    alt50=114;}

                }
                break;
            case 'C':
                {
                int LA50_51 = input.LA(3);

                if ( (LA50_51=='o') ) {
                    int LA50_125 = input.LA(4);

                    if ( (LA50_125=='m') ) {
                        int LA50_180 = input.LA(5);

                        if ( (LA50_180=='p') ) {
                            int LA50_231 = input.LA(6);

                            if ( (LA50_231=='l') ) {
                                int LA50_281 = input.LA(7);

                                if ( (LA50_281=='e') ) {
                                    int LA50_325 = input.LA(8);

                                    if ( (LA50_325=='x') ) {
                                        int LA50_361 = input.LA(9);

                                        if ( (LA50_361=='$'||(LA50_361>='0' && LA50_361<='9')||(LA50_361>='A' && LA50_361<='Z')||LA50_361=='_'||(LA50_361>='a' && LA50_361<='z')) ) {
                                            alt50=114;
                                        }
                                        else {
                                            alt50=28;}
                                    }
                                    else {
                                        alt50=114;}
                                }
                                else {
                                    alt50=114;}
                            }
                            else {
                                alt50=114;}
                        }
                        else {
                            alt50=114;}
                    }
                    else {
                        alt50=114;}
                }
                else {
                    alt50=114;}
                }
                break;
            case 'B':
                {
                int LA50_52 = input.LA(3);

                if ( (LA50_52=='o') ) {
                    int LA50_126 = input.LA(4);

                    if ( (LA50_126=='o') ) {
                        int LA50_181 = input.LA(5);

                        if ( (LA50_181=='l') ) {
                            int LA50_232 = input.LA(6);

                            if ( (LA50_232=='$'||(LA50_232>='0' && LA50_232<='9')||(LA50_232>='A' && LA50_232<='Z')||LA50_232=='_'||(LA50_232>='a' && LA50_232<='z')) ) {
                                alt50=114;
                            }
                            else {
                                alt50=27;}
                        }
                        else {
                            alt50=114;}
                    }
                    else {
                        alt50=114;}
                }
                else {
                    alt50=114;}
                }
                break;
            case 'I':
                {
                int LA50_53 = input.LA(3);

                if ( (LA50_53=='m') ) {
                    int LA50_127 = input.LA(4);

                    if ( (LA50_127=='a') ) {
                        int LA50_182 = input.LA(5);

                        if ( (LA50_182=='g') ) {
                            int LA50_233 = input.LA(6);

                            if ( (LA50_233=='i') ) {
                                int LA50_283 = input.LA(7);

                                if ( (LA50_283=='n') ) {
                                    int LA50_326 = input.LA(8);

                                    if ( (LA50_326=='a') ) {
                                        int LA50_362 = input.LA(9);

                                        if ( (LA50_362=='r') ) {
                                            int LA50_388 = input.LA(10);

                                            if ( (LA50_388=='y') ) {
                                                int LA50_407 = input.LA(11);

                                                if ( (LA50_407=='$'||(LA50_407>='0' && LA50_407<='9')||(LA50_407>='A' && LA50_407<='Z')||LA50_407=='_'||(LA50_407>='a' && LA50_407<='z')) ) {
                                                    alt50=114;
                                                }
                                                else {
                                                    alt50=31;}
                                            }
                                            else {
                                                alt50=114;}
                                        }
                                        else {
                                            alt50=114;}
                                    }
                                    else {
                                        alt50=114;}
                                }
                                else {
                                    alt50=114;}
                            }
                            else {
                                alt50=114;}
                        }
                        else {
                            alt50=114;}
                    }
                    else {
                        alt50=114;}
                }
                else {
                    alt50=114;}
                }
                break;
            default:
                alt50=114;}

            }
            break;
        case '(':
            {
            alt50=6;
            }
            break;
        case ')':
            {
            alt50=7;
            }
            break;
        case 't':
            {
            int LA50_6 = input.LA(2);

            if ( (LA50_6=='y') ) {
                int LA50_54 = input.LA(3);

                if ( (LA50_54=='p') ) {
                    int LA50_128 = input.LA(4);

                    if ( (LA50_128=='e') ) {
                        switch ( input.LA(5) ) {
                        case 'o':
                            {
                            int LA50_234 = input.LA(6);

                            if ( (LA50_234=='f') ) {
                                int LA50_284 = input.LA(7);

                                if ( (LA50_284=='$'||(LA50_284>='0' && LA50_284<='9')||(LA50_284>='A' && LA50_284<='Z')||LA50_284=='_'||(LA50_284>='a' && LA50_284<='z')) ) {
                                    alt50=114;
                                }
                                else {
                                    alt50=54;}
                            }
                            else {
                                alt50=114;}
                            }
                            break;
                        case 'd':
                            {
                            int LA50_235 = input.LA(6);

                            if ( (LA50_235=='e') ) {
                                int LA50_285 = input.LA(7);

                                if ( (LA50_285=='f') ) {
                                    int LA50_328 = input.LA(8);

                                    if ( (LA50_328=='$'||(LA50_328>='0' && LA50_328<='9')||(LA50_328>='A' && LA50_328<='Z')||LA50_328=='_'||(LA50_328>='a' && LA50_328<='z')) ) {
                                        alt50=114;
                                    }
                                    else {
                                        alt50=8;}
                                }
                                else {
                                    alt50=114;}
                            }
                            else {
                                alt50=114;}
                            }
                            break;
                        default:
                            alt50=114;}

                    }
                    else {
                        alt50=114;}
                }
                else {
                    alt50=114;}
            }
            else {
                alt50=114;}
            }
            break;
        case ',':
            {
            alt50=9;
            }
            break;
        case '=':
            {
            int LA50_8 = input.LA(2);

            if ( (LA50_8=='=') ) {
                alt50=85;
            }
            else {
                alt50=10;}
            }
            break;
        case 'e':
            {
            switch ( input.LA(2) ) {
            case 'n':
                {
                int LA50_57 = input.LA(3);

                if ( (LA50_57=='u') ) {
                    int LA50_129 = input.LA(4);

                    if ( (LA50_129=='m') ) {
                        int LA50_184 = input.LA(5);

                        if ( (LA50_184=='$'||(LA50_184>='0' && LA50_184<='9')||(LA50_184>='A' && LA50_184<='Z')||LA50_184=='_'||(LA50_184>='a' && LA50_184<='z')) ) {
                            alt50=114;
                        }
                        else {
                            alt50=37;}
                    }
                    else {
                        alt50=114;}
                }
                else {
                    alt50=114;}
                }
                break;
            case 'l':
                {
                int LA50_58 = input.LA(3);

                if ( (LA50_58=='s') ) {
                    int LA50_130 = input.LA(4);

                    if ( (LA50_130=='e') ) {
                        int LA50_185 = input.LA(5);

                        if ( (LA50_185=='$'||(LA50_185>='0' && LA50_185<='9')||(LA50_185>='A' && LA50_185<='Z')||LA50_185=='_'||(LA50_185>='a' && LA50_185<='z')) ) {
                            alt50=114;
                        }
                        else {
                            alt50=104;}
                    }
                    else {
                        alt50=114;}
                }
                else {
                    alt50=114;}
                }
                break;
            case 'x':
                {
                int LA50_59 = input.LA(3);

                if ( (LA50_59=='t') ) {
                    int LA50_131 = input.LA(4);

                    if ( (LA50_131=='e') ) {
                        int LA50_186 = input.LA(5);

                        if ( (LA50_186=='r') ) {
                            int LA50_238 = input.LA(6);

                            if ( (LA50_238=='n') ) {
                                int LA50_286 = input.LA(7);

                                if ( (LA50_286=='$'||(LA50_286>='0' && LA50_286<='9')||(LA50_286>='A' && LA50_286<='Z')||LA50_286=='_'||(LA50_286>='a' && LA50_286<='z')) ) {
                                    alt50=114;
                                }
                                else {
                                    alt50=11;}
                            }
                            else {
                                alt50=114;}
                        }
                        else {
                            alt50=114;}
                    }
                    else {
                        alt50=114;}
                }
                else {
                    alt50=114;}
                }
                break;
            default:
                alt50=114;}

            }
            break;
        case 's':
            {
            switch ( input.LA(2) ) {
            case 't':
                {
                switch ( input.LA(3) ) {
                case 'r':
                    {
                    int LA50_132 = input.LA(4);

                    if ( (LA50_132=='u') ) {
                        int LA50_187 = input.LA(5);

                        if ( (LA50_187=='c') ) {
                            int LA50_239 = input.LA(6);

                            if ( (LA50_239=='t') ) {
                                int LA50_287 = input.LA(7);

                                if ( (LA50_287=='$'||(LA50_287>='0' && LA50_287<='9')||(LA50_287>='A' && LA50_287<='Z')||LA50_287=='_'||(LA50_287>='a' && LA50_287<='z')) ) {
                                    alt50=114;
                                }
                                else {
                                    alt50=34;}
                            }
                            else {
                                alt50=114;}
                        }
                        else {
                            alt50=114;}
                    }
                    else {
                        alt50=114;}
                    }
                    break;
                case 'a':
                    {
                    int LA50_133 = input.LA(4);

                    if ( (LA50_133=='t') ) {
                        int LA50_188 = input.LA(5);

                        if ( (LA50_188=='i') ) {
                            int LA50_240 = input.LA(6);

                            if ( (LA50_240=='c') ) {
                                int LA50_288 = input.LA(7);

                                if ( (LA50_288=='$'||(LA50_288>='0' && LA50_288<='9')||(LA50_288>='A' && LA50_288<='Z')||LA50_288=='_'||(LA50_288>='a' && LA50_288<='z')) ) {
                                    alt50=114;
                                }
                                else {
                                    alt50=12;}
                            }
                            else {
                                alt50=114;}
                        }
                        else {
                            alt50=114;}
                    }
                    else {
                        alt50=114;}
                    }
                    break;
                default:
                    alt50=114;}

                }
                break;
            case 'i':
                {
                switch ( input.LA(3) ) {
                case 'g':
                    {
                    int LA50_134 = input.LA(4);

                    if ( (LA50_134=='n') ) {
                        int LA50_189 = input.LA(5);

                        if ( (LA50_189=='e') ) {
                            int LA50_241 = input.LA(6);

                            if ( (LA50_241=='d') ) {
                                int LA50_289 = input.LA(7);

                                if ( (LA50_289=='$'||(LA50_289>='0' && LA50_289<='9')||(LA50_289>='A' && LA50_289<='Z')||LA50_289=='_'||(LA50_289>='a' && LA50_289<='z')) ) {
                                    alt50=114;
                                }
                                else {
                                    alt50=23;}
                            }
                            else {
                                alt50=114;}
                        }
                        else {
                            alt50=114;}
                    }
                    else {
                        alt50=114;}
                    }
                    break;
                case 'z':
                    {
                    int LA50_135 = input.LA(4);

                    if ( (LA50_135=='e') ) {
                        int LA50_190 = input.LA(5);

                        if ( (LA50_190=='o') ) {
                            int LA50_242 = input.LA(6);

                            if ( (LA50_242=='f') ) {
                                int LA50_290 = input.LA(7);

                                if ( (LA50_290=='$'||(LA50_290>='0' && LA50_290<='9')||(LA50_290>='A' && LA50_290<='Z')||LA50_290=='_'||(LA50_290>='a' && LA50_290<='z')) ) {
                                    alt50=114;
                                }
                                else {
                                    alt50=64;}
                            }
                            else {
                                alt50=114;}
                        }
                        else {
                            alt50=114;}
                    }
                    else {
                        alt50=114;}
                    }
                    break;
                default:
                    alt50=114;}

                }
                break;
            case 'w':
                {
                int LA50_62 = input.LA(3);

                if ( (LA50_62=='i') ) {
                    int LA50_136 = input.LA(4);

                    if ( (LA50_136=='t') ) {
                        int LA50_191 = input.LA(5);

                        if ( (LA50_191=='c') ) {
                            int LA50_243 = input.LA(6);

                            if ( (LA50_243=='h') ) {
                                int LA50_291 = input.LA(7);

                                if ( (LA50_291=='$'||(LA50_291>='0' && LA50_291<='9')||(LA50_291>='A' && LA50_291<='Z')||LA50_291=='_'||(LA50_291>='a' && LA50_291<='z')) ) {
                                    alt50=114;
                                }
                                else {
                                    alt50=105;}
                            }
                            else {
                                alt50=114;}
                        }
                        else {
                            alt50=114;}
                    }
                    else {
                        alt50=114;}
                }
                else {
                    alt50=114;}
                }
                break;
            case 'h':
                {
                int LA50_63 = input.LA(3);

                if ( (LA50_63=='o') ) {
                    int LA50_137 = input.LA(4);

                    if ( (LA50_137=='r') ) {
                        int LA50_192 = input.LA(5);

                        if ( (LA50_192=='t') ) {
                            int LA50_244 = input.LA(6);

                            if ( (LA50_244=='$'||(LA50_244>='0' && LA50_244<='9')||(LA50_244>='A' && LA50_244<='Z')||LA50_244=='_'||(LA50_244>='a' && LA50_244<='z')) ) {
                                alt50=114;
                            }
                            else {
                                alt50=18;}
                        }
                        else {
                            alt50=114;}
                    }
                    else {
                        alt50=114;}
                }
                else {
                    alt50=114;}
                }
                break;
            default:
                alt50=114;}

            }
            break;
        case 'r':
            {
            int LA50_11 = input.LA(2);

            if ( (LA50_11=='e') ) {
                switch ( input.LA(3) ) {
                case 't':
                    {
                    int LA50_138 = input.LA(4);

                    if ( (LA50_138=='u') ) {
                        int LA50_193 = input.LA(5);

                        if ( (LA50_193=='r') ) {
                            int LA50_245 = input.LA(6);

                            if ( (LA50_245=='n') ) {
                                int LA50_293 = input.LA(7);

                                if ( (LA50_293=='$'||(LA50_293>='0' && LA50_293<='9')||(LA50_293>='A' && LA50_293<='Z')||LA50_293=='_'||(LA50_293>='a' && LA50_293<='z')) ) {
                                    alt50=114;
                                }
                                else {
                                    alt50=112;}
                            }
                            else {
                                alt50=114;}
                        }
                        else {
                            alt50=114;}
                    }
                    else {
                        alt50=114;}
                    }
                    break;
                case 'g':
                    {
                    int LA50_139 = input.LA(4);

                    if ( (LA50_139=='i') ) {
                        int LA50_194 = input.LA(5);

                        if ( (LA50_194=='s') ) {
                            int LA50_246 = input.LA(6);

                            if ( (LA50_246=='t') ) {
                                int LA50_294 = input.LA(7);

                                if ( (LA50_294=='e') ) {
                                    int LA50_336 = input.LA(8);

                                    if ( (LA50_336=='r') ) {
                                        int LA50_364 = input.LA(9);

                                        if ( (LA50_364=='$'||(LA50_364>='0' && LA50_364<='9')||(LA50_364>='A' && LA50_364<='Z')||LA50_364=='_'||(LA50_364>='a' && LA50_364<='z')) ) {
                                            alt50=114;
                                        }
                                        else {
                                            alt50=14;}
                                    }
                                    else {
                                        alt50=114;}
                                }
                                else {
                                    alt50=114;}
                            }
                            else {
                                alt50=114;}
                        }
                        else {
                            alt50=114;}
                    }
                    else {
                        alt50=114;}
                    }
                    break;
                case 's':
                    {
                    int LA50_140 = input.LA(4);

                    if ( (LA50_140=='t') ) {
                        int LA50_195 = input.LA(5);

                        if ( (LA50_195=='r') ) {
                            int LA50_247 = input.LA(6);

                            if ( (LA50_247=='i') ) {
                                int LA50_295 = input.LA(7);

                                if ( (LA50_295=='c') ) {
                                    int LA50_337 = input.LA(8);

                                    if ( (LA50_337=='t') ) {
                                        int LA50_365 = input.LA(9);

                                        if ( (LA50_365=='$'||(LA50_365>='0' && LA50_365<='9')||(LA50_365>='A' && LA50_365<='Z')||LA50_365=='_'||(LA50_365>='a' && LA50_365<='z')) ) {
                                            alt50=114;
                                        }
                                        else {
                                            alt50=41;}
                                    }
                                    else {
                                        alt50=114;}
                                }
                                else {
                                    alt50=114;}
                            }
                            else {
                                alt50=114;}
                        }
                        else {
                            alt50=114;}
                    }
                    else {
                        alt50=114;}
                    }
                    break;
                default:
                    alt50=114;}

            }
            else {
                alt50=114;}
            }
            break;
        case 'v':
            {
            int LA50_12 = input.LA(2);

            if ( (LA50_12=='o') ) {
                switch ( input.LA(3) ) {
                case 'i':
                    {
                    int LA50_141 = input.LA(4);

                    if ( (LA50_141=='d') ) {
                        int LA50_196 = input.LA(5);

                        if ( (LA50_196=='$'||(LA50_196>='0' && LA50_196<='9')||(LA50_196>='A' && LA50_196<='Z')||LA50_196=='_'||(LA50_196>='a' && LA50_196<='z')) ) {
                            alt50=114;
                        }
                        else {
                            alt50=16;}
                    }
                    else {
                        alt50=114;}
                    }
                    break;
                case 'l':
                    {
                    int LA50_142 = input.LA(4);

                    if ( (LA50_142=='a') ) {
                        int LA50_197 = input.LA(5);

                        if ( (LA50_197=='t') ) {
                            int LA50_249 = input.LA(6);

                            if ( (LA50_249=='i') ) {
                                int LA50_296 = input.LA(7);

                                if ( (LA50_296=='l') ) {
                                    int LA50_338 = input.LA(8);

                                    if ( (LA50_338=='e') ) {
                                        int LA50_366 = input.LA(9);

                                        if ( (LA50_366=='$'||(LA50_366>='0' && LA50_366<='9')||(LA50_366>='A' && LA50_366<='Z')||LA50_366=='_'||(LA50_366>='a' && LA50_366<='z')) ) {
                                            alt50=114;
                                        }
                                        else {
                                            alt50=44;}
                                    }
                                    else {
                                        alt50=114;}
                                }
                                else {
                                    alt50=114;}
                            }
                            else {
                                alt50=114;}
                        }
                        else {
                            alt50=114;}
                    }
                    else {
                        alt50=114;}
                    }
                    break;
                default:
                    alt50=114;}

            }
            else {
                alt50=114;}
            }
            break;
        case 'c':
            {
            switch ( input.LA(2) ) {
            case 'o':
                {
                int LA50_66 = input.LA(3);

                if ( (LA50_66=='n') ) {
                    switch ( input.LA(4) ) {
                    case 't':
                        {
                        int LA50_198 = input.LA(5);

                        if ( (LA50_198=='i') ) {
                            int LA50_250 = input.LA(6);

                            if ( (LA50_250=='n') ) {
                                int LA50_297 = input.LA(7);

                                if ( (LA50_297=='u') ) {
                                    int LA50_339 = input.LA(8);

                                    if ( (LA50_339=='e') ) {
                                        int LA50_367 = input.LA(9);

                                        if ( (LA50_367=='$'||(LA50_367>='0' && LA50_367<='9')||(LA50_367>='A' && LA50_367<='Z')||LA50_367=='_'||(LA50_367>='a' && LA50_367<='z')) ) {
                                            alt50=114;
                                        }
                                        else {
                                            alt50=110;}
                                    }
                                    else {
                                        alt50=114;}
                                }
                                else {
                                    alt50=114;}
                            }
                            else {
                                alt50=114;}
                        }
                        else {
                            alt50=114;}
                        }
                        break;
                    case 's':
                        {
                        int LA50_199 = input.LA(5);

                        if ( (LA50_199=='t') ) {
                            int LA50_251 = input.LA(6);

                            if ( (LA50_251=='$'||(LA50_251>='0' && LA50_251<='9')||(LA50_251>='A' && LA50_251<='Z')||LA50_251=='_'||(LA50_251>='a' && LA50_251<='z')) ) {
                                alt50=114;
                            }
                            else {
                                alt50=38;}
                        }
                        else {
                            alt50=114;}
                        }
                        break;
                    default:
                        alt50=114;}

                }
                else {
                    alt50=114;}
                }
                break;
            case 'a':
                {
                int LA50_67 = input.LA(3);

                if ( (LA50_67=='s') ) {
                    int LA50_144 = input.LA(4);

                    if ( (LA50_144=='e') ) {
                        int LA50_200 = input.LA(5);

                        if ( (LA50_200=='$'||(LA50_200>='0' && LA50_200<='9')||(LA50_200>='A' && LA50_200<='Z')||LA50_200=='_'||(LA50_200>='a' && LA50_200<='z')) ) {
                            alt50=114;
                        }
                        else {
                            alt50=101;}
                    }
                    else {
                        alt50=114;}
                }
                else {
                    alt50=114;}
                }
                break;
            case 'h':
                {
                int LA50_68 = input.LA(3);

                if ( (LA50_68=='a') ) {
                    int LA50_145 = input.LA(4);

                    if ( (LA50_145=='r') ) {
                        int LA50_201 = input.LA(5);

                        if ( (LA50_201=='$'||(LA50_201>='0' && LA50_201<='9')||(LA50_201>='A' && LA50_201<='Z')||LA50_201=='_'||(LA50_201>='a' && LA50_201<='z')) ) {
                            alt50=114;
                        }
                        else {
                            alt50=17;}
                    }
                    else {
                        alt50=114;}
                }
                else {
                    alt50=114;}
                }
                break;
            default:
                alt50=114;}

            }
            break;
        case 'i':
            {
            switch ( input.LA(2) ) {
            case 'n':
                {
                switch ( input.LA(3) ) {
                case 'l':
                    {
                    int LA50_146 = input.LA(4);

                    if ( (LA50_146=='i') ) {
                        int LA50_202 = input.LA(5);

                        if ( (LA50_202=='n') ) {
                            int LA50_254 = input.LA(6);

                            if ( (LA50_254=='e') ) {
                                int LA50_299 = input.LA(7);

                                if ( (LA50_299=='$'||(LA50_299>='0' && LA50_299<='9')||(LA50_299>='A' && LA50_299<='Z')||LA50_299=='_'||(LA50_299>='a' && LA50_299<='z')) ) {
                                    alt50=114;
                                }
                                else {
                                    alt50=47;}
                            }
                            else {
                                alt50=114;}
                        }
                        else {
                            alt50=114;}
                    }
                    else {
                        alt50=114;}
                    }
                    break;
                case 't':
                    {
                    int LA50_147 = input.LA(4);

                    if ( (LA50_147=='$'||(LA50_147>='0' && LA50_147<='9')||(LA50_147>='A' && LA50_147<='Z')||LA50_147=='_'||(LA50_147>='a' && LA50_147<='z')) ) {
                        alt50=114;
                    }
                    else {
                        alt50=19;}
                    }
                    break;
                default:
                    alt50=114;}

                }
                break;
            case 'f':
                {
                int LA50_70 = input.LA(3);

                if ( (LA50_70=='$'||(LA50_70>='0' && LA50_70<='9')||(LA50_70>='A' && LA50_70<='Z')||LA50_70=='_'||(LA50_70>='a' && LA50_70<='z')) ) {
                    alt50=114;
                }
                else {
                    alt50=103;}
                }
                break;
            default:
                alt50=114;}

            }
            break;
        case 'l':
            {
            int LA50_15 = input.LA(2);

            if ( (LA50_15=='o') ) {
                int LA50_71 = input.LA(3);

                if ( (LA50_71=='n') ) {
                    int LA50_149 = input.LA(4);

                    if ( (LA50_149=='g') ) {
                        int LA50_204 = input.LA(5);

                        if ( (LA50_204=='$'||(LA50_204>='0' && LA50_204<='9')||(LA50_204>='A' && LA50_204<='Z')||LA50_204=='_'||(LA50_204>='a' && LA50_204<='z')) ) {
                            alt50=114;
                        }
                        else {
                            alt50=20;}
                    }
                    else {
                        alt50=114;}
                }
                else {
                    alt50=114;}
            }
            else {
                alt50=114;}
            }
            break;
        case 'f':
            {
            switch ( input.LA(2) ) {
            case 'l':
                {
                int LA50_72 = input.LA(3);

                if ( (LA50_72=='o') ) {
                    int LA50_150 = input.LA(4);

                    if ( (LA50_150=='a') ) {
                        int LA50_205 = input.LA(5);

                        if ( (LA50_205=='t') ) {
                            int LA50_256 = input.LA(6);

                            if ( (LA50_256=='$'||(LA50_256>='0' && LA50_256<='9')||(LA50_256>='A' && LA50_256<='Z')||LA50_256=='_'||(LA50_256>='a' && LA50_256<='z')) ) {
                                alt50=114;
                            }
                            else {
                                alt50=21;}
                        }
                        else {
                            alt50=114;}
                    }
                    else {
                        alt50=114;}
                }
                else {
                    alt50=114;}
                }
                break;
            case 'o':
                {
                int LA50_73 = input.LA(3);

                if ( (LA50_73=='r') ) {
                    int LA50_151 = input.LA(4);

                    if ( (LA50_151=='$'||(LA50_151>='0' && LA50_151<='9')||(LA50_151>='A' && LA50_151<='Z')||LA50_151=='_'||(LA50_151>='a' && LA50_151<='z')) ) {
                        alt50=114;
                    }
                    else {
                        alt50=108;}
                }
                else {
                    alt50=114;}
                }
                break;
            default:
                alt50=114;}

            }
            break;
        case 'd':
            {
            switch ( input.LA(2) ) {
            case 'e':
                {
                int LA50_74 = input.LA(3);

                if ( (LA50_74=='f') ) {
                    int LA50_152 = input.LA(4);

                    if ( (LA50_152=='a') ) {
                        int LA50_207 = input.LA(5);

                        if ( (LA50_207=='u') ) {
                            int LA50_257 = input.LA(6);

                            if ( (LA50_257=='l') ) {
                                int LA50_301 = input.LA(7);

                                if ( (LA50_301=='t') ) {
                                    int LA50_341 = input.LA(8);

                                    if ( (LA50_341=='$'||(LA50_341>='0' && LA50_341<='9')||(LA50_341>='A' && LA50_341<='Z')||LA50_341=='_'||(LA50_341>='a' && LA50_341<='z')) ) {
                                        alt50=114;
                                    }
                                    else {
                                        alt50=102;}
                                }
                                else {
                                    alt50=114;}
                            }
                            else {
                                alt50=114;}
                        }
                        else {
                            alt50=114;}
                    }
                    else {
                        alt50=114;}
                }
                else {
                    alt50=114;}
                }
                break;
            case 'o':
                {
                switch ( input.LA(3) ) {
                case 'u':
                    {
                    int LA50_153 = input.LA(4);

                    if ( (LA50_153=='b') ) {
                        int LA50_208 = input.LA(5);

                        if ( (LA50_208=='l') ) {
                            int LA50_258 = input.LA(6);

                            if ( (LA50_258=='e') ) {
                                int LA50_302 = input.LA(7);

                                if ( (LA50_302=='$'||(LA50_302>='0' && LA50_302<='9')||(LA50_302>='A' && LA50_302<='Z')||LA50_302=='_'||(LA50_302>='a' && LA50_302<='z')) ) {
                                    alt50=114;
                                }
                                else {
                                    alt50=22;}
                            }
                            else {
                                alt50=114;}
                        }
                        else {
                            alt50=114;}
                    }
                    else {
                        alt50=114;}
                    }
                    break;
                case '$':
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                case 'A':
                case 'B':
                case 'C':
                case 'D':
                case 'E':
                case 'F':
                case 'G':
                case 'H':
                case 'I':
                case 'J':
                case 'K':
                case 'L':
                case 'M':
                case 'N':
                case 'O':
                case 'P':
                case 'Q':
                case 'R':
                case 'S':
                case 'T':
                case 'U':
                case 'V':
                case 'W':
                case 'X':
                case 'Y':
                case 'Z':
                case '_':
                case 'a':
                case 'b':
                case 'c':
                case 'd':
                case 'e':
                case 'f':
                case 'g':
                case 'h':
                case 'i':
                case 'j':
                case 'k':
                case 'l':
                case 'm':
                case 'n':
                case 'o':
                case 'p':
                case 'q':
                case 'r':
                case 's':
                case 't':
                case 'v':
                case 'w':
                case 'x':
                case 'y':
                case 'z':
                    {
                    alt50=114;
                    }
                    break;
                default:
                    alt50=107;}

                }
                break;
            default:
                alt50=114;}

            }
            break;
        case 'u':
            {
            int LA50_18 = input.LA(2);

            if ( (LA50_18=='n') ) {
                switch ( input.LA(3) ) {
                case 's':
                    {
                    int LA50_155 = input.LA(4);

                    if ( (LA50_155=='i') ) {
                        int LA50_209 = input.LA(5);

                        if ( (LA50_209=='g') ) {
                            int LA50_259 = input.LA(6);

                            if ( (LA50_259=='n') ) {
                                int LA50_303 = input.LA(7);

                                if ( (LA50_303=='e') ) {
                                    int LA50_343 = input.LA(8);

                                    if ( (LA50_343=='d') ) {
                                        int LA50_369 = input.LA(9);

                                        if ( (LA50_369=='$'||(LA50_369>='0' && LA50_369<='9')||(LA50_369>='A' && LA50_369<='Z')||LA50_369=='_'||(LA50_369>='a' && LA50_369<='z')) ) {
                                            alt50=114;
                                        }
                                        else {
                                            alt50=26;}
                                    }
                                    else {
                                        alt50=114;}
                                }
                                else {
                                    alt50=114;}
                            }
                            else {
                                alt50=114;}
                        }
                        else {
                            alt50=114;}
                    }
                    else {
                        alt50=114;}
                    }
                    break;
                case 'i':
                    {
                    int LA50_156 = input.LA(4);

                    if ( (LA50_156=='o') ) {
                        int LA50_210 = input.LA(5);

                        if ( (LA50_210=='n') ) {
                            int LA50_260 = input.LA(6);

                            if ( (LA50_260=='$'||(LA50_260>='0' && LA50_260<='9')||(LA50_260>='A' && LA50_260<='Z')||LA50_260=='_'||(LA50_260>='a' && LA50_260<='z')) ) {
                                alt50=114;
                            }
                            else {
                                alt50=35;}
                        }
                        else {
                            alt50=114;}
                    }
                    else {
                        alt50=114;}
                    }
                    break;
                default:
                    alt50=114;}

            }
            else {
                alt50=114;}
            }
            break;
        case '{':
            {
            alt50=32;
            }
            break;
        case '}':
            {
            alt50=33;
            }
            break;
        case ':':
            {
            alt50=36;
            }
            break;
        case '[':
            {
            alt50=50;
            }
            break;
        case ']':
            {
            alt50=51;
            }
            break;
        case '*':
            {
            int LA50_24 = input.LA(2);

            if ( (LA50_24=='=') ) {
                alt50=91;
            }
            else {
                alt50=52;}
            }
            break;
        case '.':
            {
            switch ( input.LA(2) ) {
            case '.':
                {
                alt50=53;
                }
                break;
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                {
                alt50=116;
                }
                break;
            default:
                alt50=57;}

            }
            break;
        case '-':
            {
            switch ( input.LA(2) ) {
            case '>':
                {
                alt50=61;
                }
                break;
            case '-':
                {
                alt50=63;
                }
                break;
            case '=':
                {
                alt50=95;
                }
                break;
            default:
                alt50=69;}

            }
            break;
        case '+':
            {
            switch ( input.LA(2) ) {
            case '+':
                {
                alt50=62;
                }
                break;
            case '=':
                {
                alt50=94;
                }
                break;
            default:
                alt50=68;}

            }
            break;
        case '&':
            {
            switch ( input.LA(2) ) {
            case '&':
                {
                alt50=72;
                }
                break;
            case '=':
                {
                alt50=98;
                }
                break;
            default:
                alt50=67;}

            }
            break;
        case '~':
            {
            alt50=70;
            }
            break;
        case '!':
            {
            int LA50_30 = input.LA(2);

            if ( (LA50_30=='=') ) {
                alt50=86;
            }
            else {
                alt50=71;}
            }
            break;
        case '/':
            {
            switch ( input.LA(2) ) {
            case '*':
                {
                alt50=118;
                }
                break;
            case '/':
                {
                alt50=119;
                }
                break;
            case '=':
                {
                alt50=92;
                }
                break;
            default:
                alt50=77;}

            }
            break;
        case '%':
            {
            int LA50_32 = input.LA(2);

            if ( (LA50_32=='=') ) {
                alt50=93;
            }
            else {
                alt50=78;}
            }
            break;
        case '<':
            {
            switch ( input.LA(2) ) {
            case '<':
                {
                int LA50_99 = input.LA(3);

                if ( (LA50_99=='=') ) {
                    alt50=96;
                }
                else {
                    alt50=79;}
                }
                break;
            case '=':
                {
                alt50=83;
                }
                break;
            default:
                alt50=81;}

            }
            break;
        case '>':
            {
            switch ( input.LA(2) ) {
            case '>':
                {
                int LA50_102 = input.LA(3);

                if ( (LA50_102=='=') ) {
                    alt50=97;
                }
                else {
                    alt50=80;}
                }
                break;
            case '=':
                {
                alt50=84;
                }
                break;
            default:
                alt50=82;}

            }
            break;
        case '^':
            {
            int LA50_35 = input.LA(2);

            if ( (LA50_35=='=') ) {
                alt50=99;
            }
            else {
                alt50=87;}
            }
            break;
        case '|':
            {
            switch ( input.LA(2) ) {
            case '|':
                {
                alt50=89;
                }
                break;
            case '=':
                {
                alt50=100;
                }
                break;
            default:
                alt50=88;}

            }
            break;
        case '?':
            {
            alt50=90;
            }
            break;
        case 'w':
            {
            int LA50_38 = input.LA(2);

            if ( (LA50_38=='h') ) {
                int LA50_110 = input.LA(3);

                if ( (LA50_110=='i') ) {
                    int LA50_161 = input.LA(4);

                    if ( (LA50_161=='l') ) {
                        int LA50_211 = input.LA(5);

                        if ( (LA50_211=='e') ) {
                            int LA50_261 = input.LA(6);

                            if ( (LA50_261=='$'||(LA50_261>='0' && LA50_261<='9')||(LA50_261>='A' && LA50_261<='Z')||LA50_261=='_'||(LA50_261>='a' && LA50_261<='z')) ) {
                                alt50=114;
                            }
                            else {
                                alt50=106;}
                        }
                        else {
                            alt50=114;}
                    }
                    else {
                        alt50=114;}
                }
                else {
                    alt50=114;}
            }
            else {
                alt50=114;}
            }
            break;
        case 'g':
            {
            int LA50_39 = input.LA(2);

            if ( (LA50_39=='o') ) {
                int LA50_111 = input.LA(3);

                if ( (LA50_111=='t') ) {
                    int LA50_162 = input.LA(4);

                    if ( (LA50_162=='o') ) {
                        int LA50_212 = input.LA(5);

                        if ( (LA50_212=='$'||(LA50_212>='0' && LA50_212<='9')||(LA50_212>='A' && LA50_212<='Z')||LA50_212=='_'||(LA50_212>='a' && LA50_212<='z')) ) {
                            alt50=114;
                        }
                        else {
                            alt50=109;}
                    }
                    else {
                        alt50=114;}
                }
                else {
                    alt50=114;}
            }
            else {
                alt50=114;}
            }
            break;
        case 'b':
            {
            int LA50_40 = input.LA(2);

            if ( (LA50_40=='r') ) {
                int LA50_112 = input.LA(3);

                if ( (LA50_112=='e') ) {
                    int LA50_163 = input.LA(4);

                    if ( (LA50_163=='a') ) {
                        int LA50_213 = input.LA(5);

                        if ( (LA50_213=='k') ) {
                            int LA50_263 = input.LA(6);

                            if ( (LA50_263=='$'||(LA50_263>='0' && LA50_263<='9')||(LA50_263>='A' && LA50_263<='Z')||LA50_263=='_'||(LA50_263>='a' && LA50_263<='z')) ) {
                                alt50=114;
                            }
                            else {
                                alt50=111;}
                        }
                        else {
                            alt50=114;}
                    }
                    else {
                        alt50=114;}
                }
                else {
                    alt50=114;}
            }
            else {
                alt50=114;}
            }
            break;
        case 'L':
            {
            switch ( input.LA(2) ) {
            case '\'':
                {
                alt50=116;
                }
                break;
            case '\"':
                {
                alt50=115;
                }
                break;
            default:
                alt50=114;}

            }
            break;
        case '$':
        case 'A':
        case 'B':
        case 'C':
        case 'D':
        case 'E':
        case 'F':
        case 'G':
        case 'H':
        case 'I':
        case 'J':
        case 'K':
        case 'M':
        case 'N':
        case 'O':
        case 'P':
        case 'Q':
        case 'R':
        case 'S':
        case 'T':
        case 'U':
        case 'V':
        case 'W':
        case 'X':
        case 'Y':
        case 'Z':
        case 'h':
        case 'j':
        case 'k':
        case 'm':
        case 'n':
        case 'o':
        case 'p':
        case 'q':
        case 'x':
        case 'y':
        case 'z':
            {
            alt50=114;
            }
            break;
        case '\"':
            {
            alt50=115;
            }
            break;
        case '\'':
        case '0':
        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
        case '8':
        case '9':
            {
            alt50=116;
            }
            break;
        case '\t':
        case '\n':
        case '\f':
        case '\r':
        case ' ':
            {
            alt50=117;
            }
            break;
        case '#':
            {
            alt50=120;
            }
            break;
        default:
            NoViableAltException nvae =
                new NoViableAltException("1:1: Tokens : ( ALIGNOF | T79 | T80 | T81 | T82 | T83 | T84 | T85 | T86 | T87 | T88 | T89 | T90 | T91 | T92 | T93 | T94 | T95 | T96 | T97 | T98 | T99 | T100 | T101 | T102 | T103 | T104 | T105 | T106 | T107 | T108 | T109 | T110 | T111 | T112 | T113 | T114 | T115 | T116 | T117 | T118 | T119 | T120 | T121 | T122 | T123 | T124 | T125 | T126 | T127 | T128 | T129 | T130 | T131 | T132 | T133 | T134 | T135 | T136 | T137 | T138 | T139 | T140 | T141 | T142 | T143 | T144 | T145 | T146 | T147 | T148 | T149 | T150 | T151 | T152 | T153 | T154 | T155 | T156 | T157 | T158 | T159 | T160 | T161 | T162 | T163 | T164 | T165 | T166 | T167 | T168 | T169 | T170 | T171 | T172 | T173 | T174 | T175 | T176 | T177 | T178 | T179 | T180 | T181 | T182 | T183 | T184 | T185 | T186 | T187 | T188 | T189 | EXTENSION | IDENTIFIER | STRING_LITERAL | CONSTANT | WS | COMMENT | LINE_COMMENT | LINE_COMMAND );", 50, 0, input);

            throw nvae;
        }

        switch (alt50) {
            case 1 :
                // GNUCa.g:1:10: ALIGNOF
                {
                mALIGNOF(); 

                }
                break;
            case 2 :
                // GNUCa.g:1:18: T79
                {
                mT79(); 

                }
                break;
            case 3 :
                // GNUCa.g:1:22: T80
                {
                mT80(); 

                }
                break;
            case 4 :
                // GNUCa.g:1:26: T81
                {
                mT81(); 

                }
                break;
            case 5 :
                // GNUCa.g:1:30: T82
                {
                mT82(); 

                }
                break;
            case 6 :
                // GNUCa.g:1:34: T83
                {
                mT83(); 

                }
                break;
            case 7 :
                // GNUCa.g:1:38: T84
                {
                mT84(); 

                }
                break;
            case 8 :
                // GNUCa.g:1:42: T85
                {
                mT85(); 

                }
                break;
            case 9 :
                // GNUCa.g:1:46: T86
                {
                mT86(); 

                }
                break;
            case 10 :
                // GNUCa.g:1:50: T87
                {
                mT87(); 

                }
                break;
            case 11 :
                // GNUCa.g:1:54: T88
                {
                mT88(); 

                }
                break;
            case 12 :
                // GNUCa.g:1:58: T89
                {
                mT89(); 

                }
                break;
            case 13 :
                // GNUCa.g:1:62: T90
                {
                mT90(); 

                }
                break;
            case 14 :
                // GNUCa.g:1:66: T91
                {
                mT91(); 

                }
                break;
            case 15 :
                // GNUCa.g:1:70: T92
                {
                mT92(); 

                }
                break;
            case 16 :
                // GNUCa.g:1:74: T93
                {
                mT93(); 

                }
                break;
            case 17 :
                // GNUCa.g:1:78: T94
                {
                mT94(); 

                }
                break;
            case 18 :
                // GNUCa.g:1:82: T95
                {
                mT95(); 

                }
                break;
            case 19 :
                // GNUCa.g:1:86: T96
                {
                mT96(); 

                }
                break;
            case 20 :
                // GNUCa.g:1:90: T97
                {
                mT97(); 

                }
                break;
            case 21 :
                // GNUCa.g:1:94: T98
                {
                mT98(); 

                }
                break;
            case 22 :
                // GNUCa.g:1:98: T99
                {
                mT99(); 

                }
                break;
            case 23 :
                // GNUCa.g:1:102: T100
                {
                mT100(); 

                }
                break;
            case 24 :
                // GNUCa.g:1:107: T101
                {
                mT101(); 

                }
                break;
            case 25 :
                // GNUCa.g:1:112: T102
                {
                mT102(); 

                }
                break;
            case 26 :
                // GNUCa.g:1:117: T103
                {
                mT103(); 

                }
                break;
            case 27 :
                // GNUCa.g:1:122: T104
                {
                mT104(); 

                }
                break;
            case 28 :
                // GNUCa.g:1:127: T105
                {
                mT105(); 

                }
                break;
            case 29 :
                // GNUCa.g:1:132: T106
                {
                mT106(); 

                }
                break;
            case 30 :
                // GNUCa.g:1:137: T107
                {
                mT107(); 

                }
                break;
            case 31 :
                // GNUCa.g:1:142: T108
                {
                mT108(); 

                }
                break;
            case 32 :
                // GNUCa.g:1:147: T109
                {
                mT109(); 

                }
                break;
            case 33 :
                // GNUCa.g:1:152: T110
                {
                mT110(); 

                }
                break;
            case 34 :
                // GNUCa.g:1:157: T111
                {
                mT111(); 

                }
                break;
            case 35 :
                // GNUCa.g:1:162: T112
                {
                mT112(); 

                }
                break;
            case 36 :
                // GNUCa.g:1:167: T113
                {
                mT113(); 

                }
                break;
            case 37 :
                // GNUCa.g:1:172: T114
                {
                mT114(); 

                }
                break;
            case 38 :
                // GNUCa.g:1:177: T115
                {
                mT115(); 

                }
                break;
            case 39 :
                // GNUCa.g:1:182: T116
                {
                mT116(); 

                }
                break;
            case 40 :
                // GNUCa.g:1:187: T117
                {
                mT117(); 

                }
                break;
            case 41 :
                // GNUCa.g:1:192: T118
                {
                mT118(); 

                }
                break;
            case 42 :
                // GNUCa.g:1:197: T119
                {
                mT119(); 

                }
                break;
            case 43 :
                // GNUCa.g:1:202: T120
                {
                mT120(); 

                }
                break;
            case 44 :
                // GNUCa.g:1:207: T121
                {
                mT121(); 

                }
                break;
            case 45 :
                // GNUCa.g:1:212: T122
                {
                mT122(); 

                }
                break;
            case 46 :
                // GNUCa.g:1:217: T123
                {
                mT123(); 

                }
                break;
            case 47 :
                // GNUCa.g:1:222: T124
                {
                mT124(); 

                }
                break;
            case 48 :
                // GNUCa.g:1:227: T125
                {
                mT125(); 

                }
                break;
            case 49 :
                // GNUCa.g:1:232: T126
                {
                mT126(); 

                }
                break;
            case 50 :
                // GNUCa.g:1:237: T127
                {
                mT127(); 

                }
                break;
            case 51 :
                // GNUCa.g:1:242: T128
                {
                mT128(); 

                }
                break;
            case 52 :
                // GNUCa.g:1:247: T129
                {
                mT129(); 

                }
                break;
            case 53 :
                // GNUCa.g:1:252: T130
                {
                mT130(); 

                }
                break;
            case 54 :
                // GNUCa.g:1:257: T131
                {
                mT131(); 

                }
                break;
            case 55 :
                // GNUCa.g:1:262: T132
                {
                mT132(); 

                }
                break;
            case 56 :
                // GNUCa.g:1:267: T133
                {
                mT133(); 

                }
                break;
            case 57 :
                // GNUCa.g:1:272: T134
                {
                mT134(); 

                }
                break;
            case 58 :
                // GNUCa.g:1:277: T135
                {
                mT135(); 

                }
                break;
            case 59 :
                // GNUCa.g:1:282: T136
                {
                mT136(); 

                }
                break;
            case 60 :
                // GNUCa.g:1:287: T137
                {
                mT137(); 

                }
                break;
            case 61 :
                // GNUCa.g:1:292: T138
                {
                mT138(); 

                }
                break;
            case 62 :
                // GNUCa.g:1:297: T139
                {
                mT139(); 

                }
                break;
            case 63 :
                // GNUCa.g:1:302: T140
                {
                mT140(); 

                }
                break;
            case 64 :
                // GNUCa.g:1:307: T141
                {
                mT141(); 

                }
                break;
            case 65 :
                // GNUCa.g:1:312: T142
                {
                mT142(); 

                }
                break;
            case 66 :
                // GNUCa.g:1:317: T143
                {
                mT143(); 

                }
                break;
            case 67 :
                // GNUCa.g:1:322: T144
                {
                mT144(); 

                }
                break;
            case 68 :
                // GNUCa.g:1:327: T145
                {
                mT145(); 

                }
                break;
            case 69 :
                // GNUCa.g:1:332: T146
                {
                mT146(); 

                }
                break;
            case 70 :
                // GNUCa.g:1:337: T147
                {
                mT147(); 

                }
                break;
            case 71 :
                // GNUCa.g:1:342: T148
                {
                mT148(); 

                }
                break;
            case 72 :
                // GNUCa.g:1:347: T149
                {
                mT149(); 

                }
                break;
            case 73 :
                // GNUCa.g:1:352: T150
                {
                mT150(); 

                }
                break;
            case 74 :
                // GNUCa.g:1:357: T151
                {
                mT151(); 

                }
                break;
            case 75 :
                // GNUCa.g:1:362: T152
                {
                mT152(); 

                }
                break;
            case 76 :
                // GNUCa.g:1:367: T153
                {
                mT153(); 

                }
                break;
            case 77 :
                // GNUCa.g:1:372: T154
                {
                mT154(); 

                }
                break;
            case 78 :
                // GNUCa.g:1:377: T155
                {
                mT155(); 

                }
                break;
            case 79 :
                // GNUCa.g:1:382: T156
                {
                mT156(); 

                }
                break;
            case 80 :
                // GNUCa.g:1:387: T157
                {
                mT157(); 

                }
                break;
            case 81 :
                // GNUCa.g:1:392: T158
                {
                mT158(); 

                }
                break;
            case 82 :
                // GNUCa.g:1:397: T159
                {
                mT159(); 

                }
                break;
            case 83 :
                // GNUCa.g:1:402: T160
                {
                mT160(); 

                }
                break;
            case 84 :
                // GNUCa.g:1:407: T161
                {
                mT161(); 

                }
                break;
            case 85 :
                // GNUCa.g:1:412: T162
                {
                mT162(); 

                }
                break;
            case 86 :
                // GNUCa.g:1:417: T163
                {
                mT163(); 

                }
                break;
            case 87 :
                // GNUCa.g:1:422: T164
                {
                mT164(); 

                }
                break;
            case 88 :
                // GNUCa.g:1:427: T165
                {
                mT165(); 

                }
                break;
            case 89 :
                // GNUCa.g:1:432: T166
                {
                mT166(); 

                }
                break;
            case 90 :
                // GNUCa.g:1:437: T167
                {
                mT167(); 

                }
                break;
            case 91 :
                // GNUCa.g:1:442: T168
                {
                mT168(); 

                }
                break;
            case 92 :
                // GNUCa.g:1:447: T169
                {
                mT169(); 

                }
                break;
            case 93 :
                // GNUCa.g:1:452: T170
                {
                mT170(); 

                }
                break;
            case 94 :
                // GNUCa.g:1:457: T171
                {
                mT171(); 

                }
                break;
            case 95 :
                // GNUCa.g:1:462: T172
                {
                mT172(); 

                }
                break;
            case 96 :
                // GNUCa.g:1:467: T173
                {
                mT173(); 

                }
                break;
            case 97 :
                // GNUCa.g:1:472: T174
                {
                mT174(); 

                }
                break;
            case 98 :
                // GNUCa.g:1:477: T175
                {
                mT175(); 

                }
                break;
            case 99 :
                // GNUCa.g:1:482: T176
                {
                mT176(); 

                }
                break;
            case 100 :
                // GNUCa.g:1:487: T177
                {
                mT177(); 

                }
                break;
            case 101 :
                // GNUCa.g:1:492: T178
                {
                mT178(); 

                }
                break;
            case 102 :
                // GNUCa.g:1:497: T179
                {
                mT179(); 

                }
                break;
            case 103 :
                // GNUCa.g:1:502: T180
                {
                mT180(); 

                }
                break;
            case 104 :
                // GNUCa.g:1:507: T181
                {
                mT181(); 

                }
                break;
            case 105 :
                // GNUCa.g:1:512: T182
                {
                mT182(); 

                }
                break;
            case 106 :
                // GNUCa.g:1:517: T183
                {
                mT183(); 

                }
                break;
            case 107 :
                // GNUCa.g:1:522: T184
                {
                mT184(); 

                }
                break;
            case 108 :
                // GNUCa.g:1:527: T185
                {
                mT185(); 

                }
                break;
            case 109 :
                // GNUCa.g:1:532: T186
                {
                mT186(); 

                }
                break;
            case 110 :
                // GNUCa.g:1:537: T187
                {
                mT187(); 

                }
                break;
            case 111 :
                // GNUCa.g:1:542: T188
                {
                mT188(); 

                }
                break;
            case 112 :
                // GNUCa.g:1:547: T189
                {
                mT189(); 

                }
                break;
            case 113 :
                // GNUCa.g:1:552: EXTENSION
                {
                mEXTENSION(); 

                }
                break;
            case 114 :
                // GNUCa.g:1:562: IDENTIFIER
                {
                mIDENTIFIER(); 

                }
                break;
            case 115 :
                // GNUCa.g:1:573: STRING_LITERAL
                {
                mSTRING_LITERAL(); 

                }
                break;
            case 116 :
                // GNUCa.g:1:588: CONSTANT
                {
                mCONSTANT(); 

                }
                break;
            case 117 :
                // GNUCa.g:1:597: WS
                {
                mWS(); 

                }
                break;
            case 118 :
                // GNUCa.g:1:600: COMMENT
                {
                mCOMMENT(); 

                }
                break;
            case 119 :
                // GNUCa.g:1:608: LINE_COMMENT
                {
                mLINE_COMMENT(); 

                }
                break;
            case 120 :
                // GNUCa.g:1:621: LINE_COMMAND
                {
                mLINE_COMMAND(); 

                }
                break;

        }

    }


    protected DFA13 dfa13 = new DFA13(this);
    protected DFA32 dfa32 = new DFA32(this);
    protected DFA42 dfa42 = new DFA42(this);
    static final String DFA13_eotS =
        "\1\uffff\1\6\1\11\2\uffff\1\6\2\uffff\1\11\1\uffff\1\14\2\uffff";
    static final String DFA13_eofS =
        "\15\uffff";
    static final String DFA13_minS =
        "\1\47\2\56\2\uffff\1\56\1\uffff\2\56\1\uffff\1\56\2\uffff";
    static final String DFA13_maxS =
        "\1\114\1\145\1\170\2\uffff\1\145\1\uffff\1\146\1\145\1\uffff\1\160"+
        "\2\uffff";
    static final String DFA13_acceptS =
        "\3\uffff\1\4\1\6\1\uffff\1\1\2\uffff\1\2\1\uffff\1\5\1\3";
    static final String DFA13_specialS =
        "\15\uffff}>";
    static final String[] DFA13_transitionS = {
            "\1\4\6\uffff\1\3\1\uffff\1\2\11\1\22\uffff\1\4",
            "\1\3\1\uffff\12\5\13\uffff\1\3\37\uffff\1\3",
            "\1\3\1\uffff\10\10\2\3\13\uffff\1\3\22\uffff\1\7\14\uffff\1"+
            "\3\22\uffff\1\7",
            "",
            "",
            "\1\3\1\uffff\12\5\13\uffff\1\3\37\uffff\1\3",
            "",
            "\1\13\1\uffff\12\12\7\uffff\6\12\32\uffff\6\12",
            "\1\3\1\uffff\10\10\2\3\13\uffff\1\3\37\uffff\1\3",
            "",
            "\1\13\1\uffff\12\12\7\uffff\6\12\11\uffff\1\13\20\uffff\6\12"+
            "\11\uffff\1\13",
            "",
            ""
    };

    static final short[] DFA13_eot = DFA.unpackEncodedString(DFA13_eotS);
    static final short[] DFA13_eof = DFA.unpackEncodedString(DFA13_eofS);
    static final char[] DFA13_min = DFA.unpackEncodedStringToUnsignedChars(DFA13_minS);
    static final char[] DFA13_max = DFA.unpackEncodedStringToUnsignedChars(DFA13_maxS);
    static final short[] DFA13_accept = DFA.unpackEncodedString(DFA13_acceptS);
    static final short[] DFA13_special = DFA.unpackEncodedString(DFA13_specialS);
    static final short[][] DFA13_transition;

    static {
        int numStates = DFA13_transitionS.length;
        DFA13_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA13_transition[i] = DFA.unpackEncodedString(DFA13_transitionS[i]);
        }
    }

    class DFA13 extends DFA {

        public DFA13(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 13;
            this.eot = DFA13_eot;
            this.eof = DFA13_eof;
            this.min = DFA13_min;
            this.max = DFA13_max;
            this.accept = DFA13_accept;
            this.special = DFA13_special;
            this.transition = DFA13_transition;
        }
        public String getDescription() {
            return "759:1: CONSTANT : ( DecimalConstant ( IntegerSuffix )? | OctalConstant ( IntegerSuffix )? | HexadecimalConstant ( IntegerSuffix )? | DecimalFloatingConstant | HexadecimalFloatingConstant | CharacterLiteral );";
        }
    }
    static final String DFA32_eotS =
        "\3\uffff\1\5\2\uffff";
    static final String DFA32_eofS =
        "\6\uffff";
    static final String DFA32_minS =
        "\2\56\1\uffff\1\60\2\uffff";
    static final String DFA32_maxS =
        "\1\71\1\145\1\uffff\1\71\2\uffff";
    static final String DFA32_acceptS =
        "\2\uffff\1\1\1\uffff\1\3\1\2";
    static final String DFA32_specialS =
        "\6\uffff}>";
    static final String[] DFA32_transitionS = {
            "\1\2\1\uffff\12\1",
            "\1\3\1\uffff\12\1\13\uffff\1\4\37\uffff\1\4",
            "",
            "\12\2",
            "",
            ""
    };

    static final short[] DFA32_eot = DFA.unpackEncodedString(DFA32_eotS);
    static final short[] DFA32_eof = DFA.unpackEncodedString(DFA32_eofS);
    static final char[] DFA32_min = DFA.unpackEncodedStringToUnsignedChars(DFA32_minS);
    static final char[] DFA32_max = DFA.unpackEncodedStringToUnsignedChars(DFA32_maxS);
    static final short[] DFA32_accept = DFA.unpackEncodedString(DFA32_acceptS);
    static final short[] DFA32_special = DFA.unpackEncodedString(DFA32_specialS);
    static final short[][] DFA32_transition;

    static {
        int numStates = DFA32_transitionS.length;
        DFA32_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA32_transition[i] = DFA.unpackEncodedString(DFA32_transitionS[i]);
        }
    }

    class DFA32 extends DFA {

        public DFA32(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 32;
            this.eot = DFA32_eot;
            this.eof = DFA32_eof;
            this.min = DFA32_min;
            this.max = DFA32_max;
            this.accept = DFA32_accept;
            this.special = DFA32_special;
            this.transition = DFA32_transition;
        }
        public String getDescription() {
            return "793:1: fragment DecimalFloatingConstant : ( ( '0' .. '9' )* '.' ( '0' .. '9' )+ ( ExponentPart )? ( FloatingSuffix )? | ( '0' .. '9' )+ '.' ( ExponentPart )? ( FloatingSuffix )? | ( '0' .. '9' )+ ExponentPart ( FloatingSuffix )? );";
        }
    }
    static final String DFA42_eotS =
        "\10\uffff";
    static final String DFA42_eofS =
        "\10\uffff";
    static final String DFA42_minS =
        "\1\60\1\130\2\56\1\uffff\1\60\2\uffff";
    static final String DFA42_maxS =
        "\1\60\1\170\1\146\1\160\1\uffff\1\160\2\uffff";
    static final String DFA42_acceptS =
        "\4\uffff\1\1\1\uffff\1\3\1\2";
    static final String DFA42_specialS =
        "\10\uffff}>";
    static final String[] DFA42_transitionS = {
            "\1\1",
            "\1\2\37\uffff\1\2",
            "\1\4\1\uffff\12\3\7\uffff\6\3\32\uffff\6\3",
            "\1\5\1\uffff\12\3\7\uffff\6\3\11\uffff\1\6\20\uffff\6\3\11\uffff"+
            "\1\6",
            "",
            "\12\4\7\uffff\6\4\11\uffff\1\7\20\uffff\6\4\11\uffff\1\7",
            "",
            ""
    };

    static final short[] DFA42_eot = DFA.unpackEncodedString(DFA42_eotS);
    static final short[] DFA42_eof = DFA.unpackEncodedString(DFA42_eofS);
    static final char[] DFA42_min = DFA.unpackEncodedStringToUnsignedChars(DFA42_minS);
    static final char[] DFA42_max = DFA.unpackEncodedStringToUnsignedChars(DFA42_maxS);
    static final short[] DFA42_accept = DFA.unpackEncodedString(DFA42_acceptS);
    static final short[] DFA42_special = DFA.unpackEncodedString(DFA42_specialS);
    static final short[][] DFA42_transition;

    static {
        int numStates = DFA42_transitionS.length;
        DFA42_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA42_transition[i] = DFA.unpackEncodedString(DFA42_transitionS[i]);
        }
    }

    class DFA42 extends DFA {

        public DFA42(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 42;
            this.eot = DFA42_eot;
            this.eof = DFA42_eof;
            this.min = DFA42_min;
            this.max = DFA42_max;
            this.accept = DFA42_accept;
            this.special = DFA42_special;
            this.transition = DFA42_transition;
        }
        public String getDescription() {
            return "805:1: fragment HexadecimalFloatingConstant : ( '0' ( 'x' | 'X' ) ( HexDigit )* '.' ( HexDigit )+ BinaryExponentPart ( FloatingSuffix )? | '0' ( 'x' | 'X' ) ( HexDigit )+ '.' BinaryExponentPart ( FloatingSuffix )? | '0' ( 'x' | 'X' ) ( HexDigit )+ BinaryExponentPart ( FloatingSuffix )? );";
        }
    }
 

}

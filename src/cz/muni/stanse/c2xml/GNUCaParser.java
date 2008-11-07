// $ANTLR 3.0.1 GNUCa.g 2008-11-07 14:25:25

	package cz.muni.stanse.c2xml;

	import java.util.Set;
	import java.util.HashSet;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.tree.*;

public class GNUCaParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "BRACKET_DESIGNATOR", "SIGNED", "E1", "E2", "E3", "EXPRESSION_STATEMENT", "ASM", "PP", "MM", "PU", "MU", "XU", "AU", "LABREF", "BUILTIN_OFFSETOF", "TYPEDEF_NAME", "TYPEOF", "ASSIGNMENT_EXPRESSION", "TYPE_NAME", "FUNCTION_DECLARATOR", "ARRAY_DECLARATOR", "INITIALIZER", "DESIGNATOR", "ENUMERATOR", "TRANSLATION_UNIT", "COMPOUND_LITERAL", "ARRAY_ACCESS", "PARAMETER", "VARARGS", "DECLARATOR", "POINTER", "STRUCT_DECLARATOR", "STRUCT_DECLARATION", "FUNCTION_DEFINITION", "XID", "XTYPE_SPECIFIER", "XTYPE_QUALIFIER", "XSTORAGE_CLASS", "DECLARATION", "INIT_DECLARATOR", "DECLARATION_SPECIFIERS", "BASETYPE", "CAST_EXPRESSION", "CONDITIONAL_EXPRESSION", "LABEL", "COMPOUND_STATEMENT", "ALIGNOF", "FUNCTION_CALL", "STR_LITERAL", "IDENTIFIER", "CONSTANT", "STRING_LITERAL", "EXTENSION", "NonDigit", "Digit", "EscapeSequence", "CharacterLiteral", "HexDigit", "Sign", "OctalEscape", "HexadecimalEscape", "UniversalCharacterName", "DecimalConstant", "IntegerSuffix", "OctalConstant", "HexadecimalConstant", "DecimalFloatingConstant", "HexadecimalFloatingConstant", "ExponentPart", "FloatingSuffix", "BinaryExponentPart", "WS", "COMMENT", "LINE_COMMENT", "LINE_COMMAND", "';'", "'asm'", "'__asm'", "'__asm__'", "'('", "')'", "'typedef'", "','", "'='", "'extern'", "'static'", "'auto'", "'register'", "'__thread'", "'void'", "'char'", "'short'", "'int'", "'long'", "'float'", "'double'", "'signed'", "'__signed'", "'__signed__'", "'unsigned'", "'_Bool'", "'_Complex'", "'__complex'", "'__complex__'", "'_Imaginary'", "'{'", "'}'", "'struct'", "'union'", "':'", "'enum'", "'const'", "'__const'", "'__const__'", "'restrict'", "'__restrict'", "'__restrict__'", "'volatile'", "'__volatile'", "'__volatile__'", "'inline'", "'__inline'", "'__inline__'", "'['", "']'", "'*'", "'...'", "'typeof'", "'__typeof'", "'__typeof__'", "'.'", "'__attribute'", "'__attribute__'", "'__builtin_offsetof'", "'->'", "'++'", "'--'", "'sizeof'", "'__alignof'", "'__alignof__'", "'&'", "'+'", "'-'", "'~'", "'!'", "'&&'", "'__real'", "'__real__'", "'__imag'", "'__imag__'", "'/'", "'%'", "'<<'", "'>>'", "'<'", "'>'", "'<='", "'>='", "'=='", "'!='", "'^'", "'|'", "'||'", "'?'", "'*='", "'/='", "'%='", "'+='", "'-='", "'<<='", "'>>='", "'&='", "'^='", "'|='", "'case'", "'default'", "'if'", "'else'", "'switch'", "'while'", "'do'", "'for'", "'goto'", "'continue'", "'break'", "'return'"
    };
    public static final int OctalConstant=68;
    public static final int CAST_EXPRESSION=46;
    public static final int SIGNED=5;
    public static final int BinaryExponentPart=74;
    public static final int VARARGS=32;
    public static final int UniversalCharacterName=65;
    public static final int CharacterLiteral=60;
    public static final int DecimalConstant=66;
    public static final int POINTER=34;
    public static final int NonDigit=57;
    public static final int DecimalFloatingConstant=70;
    public static final int BRACKET_DESIGNATOR=4;
    public static final int E2=7;
    public static final int E1=6;
    public static final int E3=8;
    public static final int ALIGNOF=50;
    public static final int EOF=-1;
    public static final int HexDigit=61;
    public static final int DECLARATION=42;
    public static final int INITIALIZER=25;
    public static final int ASSIGNMENT_EXPRESSION=21;
    public static final int TYPEDEF_NAME=19;
    public static final int MU=14;
    public static final int STRUCT_DECLARATOR=35;
    public static final int STRING_LITERAL=55;
    public static final int XTYPE_SPECIFIER=39;
    public static final int AU=16;
    public static final int HexadecimalFloatingConstant=71;
    public static final int MM=12;
    public static final int CONDITIONAL_EXPRESSION=47;
    public static final int IDENTIFIER=53;
    public static final int PARAMETER=31;
    public static final int TYPE_NAME=22;
    public static final int ExponentPart=72;
    public static final int PU=13;
    public static final int ENUMERATOR=27;
    public static final int PP=11;
    public static final int Sign=62;
    public static final int COMMENT=76;
    public static final int FUNCTION_DEFINITION=37;
    public static final int XTYPE_QUALIFIER=40;
    public static final int XU=15;
    public static final int LINE_COMMENT=77;
    public static final int FUNCTION_DECLARATOR=23;
    public static final int HexadecimalConstant=69;
    public static final int ASM=10;
    public static final int XSTORAGE_CLASS=41;
    public static final int HexadecimalEscape=64;
    public static final int ARRAY_ACCESS=30;
    public static final int Digit=58;
    public static final int COMPOUND_LITERAL=29;
    public static final int DECLARATOR=33;
    public static final int ARRAY_DECLARATOR=24;
    public static final int DESIGNATOR=26;
    public static final int WS=75;
    public static final int STR_LITERAL=52;
    public static final int BUILTIN_OFFSETOF=18;
    public static final int LABEL=48;
    public static final int TYPEOF=20;
    public static final int LABREF=17;
    public static final int STRUCT_DECLARATION=36;
    public static final int TRANSLATION_UNIT=28;
    public static final int COMPOUND_STATEMENT=49;
    public static final int LINE_COMMAND=78;
    public static final int CONSTANT=54;
    public static final int INIT_DECLARATOR=43;
    public static final int IntegerSuffix=67;
    public static final int DECLARATION_SPECIFIERS=44;
    public static final int FloatingSuffix=73;
    public static final int EXPRESSION_STATEMENT=9;
    public static final int XID=38;
    public static final int BASETYPE=45;
    public static final int FUNCTION_CALL=51;
    public static final int OctalEscape=63;
    public static final int EscapeSequence=59;
    public static final int EXTENSION=56;
    protected static class Symbols_scope {
        Set types;
        // only track types in order to get parser working;
    }
    protected Stack Symbols_stack = new Stack();


        public GNUCaParser(TokenStream input) {
            super(input);
            ruleMemo = new HashMap[333+1];
         }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return tokenNames; }
    public String getGrammarFileName() { return "GNUCa.g"; }


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


    public static class translationUnit_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start translationUnit
    // GNUCa.g:159:1: translationUnit : ( externalDeclaration )* -> ^( TRANSLATION_UNIT ( externalDeclaration )* ) ;
    public final translationUnit_return translationUnit() throws RecognitionException {
        Symbols_stack.push(new Symbols_scope());

        translationUnit_return retval = new translationUnit_return();
        retval.start = input.LT(1);
        int translationUnit_StartIndex = input.index();
        Object root_0 = null;

        externalDeclaration_return externalDeclaration1 = null;


        RewriteRuleSubtreeStream stream_externalDeclaration=new RewriteRuleSubtreeStream(adaptor,"rule externalDeclaration");
         ((Symbols_scope)Symbols_stack.peek()).types = new HashSet(); 
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 1) ) { return retval; }
            // GNUCa.g:166:9: ( ( externalDeclaration )* -> ^( TRANSLATION_UNIT ( externalDeclaration )* ) )
            // GNUCa.g:166:11: ( externalDeclaration )*
            {
            // GNUCa.g:166:11: ( externalDeclaration )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==IDENTIFIER||(LA1_0>=79 && LA1_0<=82)||LA1_0==85||(LA1_0>=88 && LA1_0<=108)||(LA1_0>=111 && LA1_0<=112)||(LA1_0>=114 && LA1_0<=126)||(LA1_0>=131 && LA1_0<=133)||(LA1_0>=135 && LA1_0<=136)) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // GNUCa.g:0:0: externalDeclaration
            	    {
            	    pushFollow(FOLLOW_externalDeclaration_in_translationUnit295);
            	    externalDeclaration1=externalDeclaration();
            	    _fsp--;
            	    if (failed) return retval;
            	    if ( backtracking==0 ) stream_externalDeclaration.add(externalDeclaration1.getTree());

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            // AST REWRITE
            // elements: externalDeclaration
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 166:32: -> ^( TRANSLATION_UNIT ( externalDeclaration )* )
            {
                // GNUCa.g:166:35: ^( TRANSLATION_UNIT ( externalDeclaration )* )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(adaptor.create(TRANSLATION_UNIT, "TRANSLATION_UNIT"), root_1);

                // GNUCa.g:166:54: ( externalDeclaration )*
                while ( stream_externalDeclaration.hasNext() ) {
                    adaptor.addChild(root_1, stream_externalDeclaration.next());

                }
                stream_externalDeclaration.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            }

            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 1, translationUnit_StartIndex); }
            Symbols_stack.pop();

        }
        return retval;
    }
    // $ANTLR end translationUnit

    public static class externalDeclaration_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start externalDeclaration
    // GNUCa.g:171:1: externalDeclaration options {k=1; } : ( ( declarationSpecifiers declarator ( '{' | storageClassSpecifier | typeSpecifier | typeQualifier | functionSpecifier | '__attribute' | '__attribute__' ) )=> functionDefinition | declaration | ';' | asmDefinition );
    public final externalDeclaration_return externalDeclaration() throws RecognitionException {
        externalDeclaration_return retval = new externalDeclaration_return();
        retval.start = input.LT(1);
        int externalDeclaration_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal4=null;
        functionDefinition_return functionDefinition2 = null;

        declaration_return declaration3 = null;

        asmDefinition_return asmDefinition5 = null;


        Object char_literal4_tree=null;

        try {
            if ( backtracking>0 && alreadyParsedRule(input, 2) ) { return retval; }
            // GNUCa.g:173:2: ( ( declarationSpecifiers declarator ( '{' | storageClassSpecifier | typeSpecifier | typeQualifier | functionSpecifier | '__attribute' | '__attribute__' ) )=> functionDefinition | declaration | ';' | asmDefinition )
            int alt2=4;
            switch ( input.LA(1) ) {
            case 88:
            case 89:
            case 90:
            case 91:
            case 92:
                {
                int LA2_1 = input.LA(2);

                if ( (synpred8()) ) {
                    alt2=1;
                }
                else if ( (synpred9()) ) {
                    alt2=2;
                }
                else {
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("171:1: externalDeclaration options {k=1; } : ( ( declarationSpecifiers declarator ( '{' | storageClassSpecifier | typeSpecifier | typeQualifier | functionSpecifier | '__attribute' | '__attribute__' ) )=> functionDefinition | declaration | ';' | asmDefinition );", 2, 1, input);

                    throw nvae;
                }
                }
                break;
            case 93:
                {
                int LA2_2 = input.LA(2);

                if ( (synpred8()) ) {
                    alt2=1;
                }
                else if ( (synpred9()) ) {
                    alt2=2;
                }
                else {
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("171:1: externalDeclaration options {k=1; } : ( ( declarationSpecifiers declarator ( '{' | storageClassSpecifier | typeSpecifier | typeQualifier | functionSpecifier | '__attribute' | '__attribute__' ) )=> functionDefinition | declaration | ';' | asmDefinition );", 2, 2, input);

                    throw nvae;
                }
                }
                break;
            case 94:
                {
                int LA2_3 = input.LA(2);

                if ( (synpred8()) ) {
                    alt2=1;
                }
                else if ( (synpred9()) ) {
                    alt2=2;
                }
                else {
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("171:1: externalDeclaration options {k=1; } : ( ( declarationSpecifiers declarator ( '{' | storageClassSpecifier | typeSpecifier | typeQualifier | functionSpecifier | '__attribute' | '__attribute__' ) )=> functionDefinition | declaration | ';' | asmDefinition );", 2, 3, input);

                    throw nvae;
                }
                }
                break;
            case 95:
                {
                int LA2_4 = input.LA(2);

                if ( (synpred8()) ) {
                    alt2=1;
                }
                else if ( (synpred9()) ) {
                    alt2=2;
                }
                else {
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("171:1: externalDeclaration options {k=1; } : ( ( declarationSpecifiers declarator ( '{' | storageClassSpecifier | typeSpecifier | typeQualifier | functionSpecifier | '__attribute' | '__attribute__' ) )=> functionDefinition | declaration | ';' | asmDefinition );", 2, 4, input);

                    throw nvae;
                }
                }
                break;
            case 96:
                {
                int LA2_5 = input.LA(2);

                if ( (synpred8()) ) {
                    alt2=1;
                }
                else if ( (synpred9()) ) {
                    alt2=2;
                }
                else {
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("171:1: externalDeclaration options {k=1; } : ( ( declarationSpecifiers declarator ( '{' | storageClassSpecifier | typeSpecifier | typeQualifier | functionSpecifier | '__attribute' | '__attribute__' ) )=> functionDefinition | declaration | ';' | asmDefinition );", 2, 5, input);

                    throw nvae;
                }
                }
                break;
            case 97:
                {
                int LA2_6 = input.LA(2);

                if ( (synpred8()) ) {
                    alt2=1;
                }
                else if ( (synpred9()) ) {
                    alt2=2;
                }
                else {
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("171:1: externalDeclaration options {k=1; } : ( ( declarationSpecifiers declarator ( '{' | storageClassSpecifier | typeSpecifier | typeQualifier | functionSpecifier | '__attribute' | '__attribute__' ) )=> functionDefinition | declaration | ';' | asmDefinition );", 2, 6, input);

                    throw nvae;
                }
                }
                break;
            case 98:
                {
                int LA2_7 = input.LA(2);

                if ( (synpred8()) ) {
                    alt2=1;
                }
                else if ( (synpred9()) ) {
                    alt2=2;
                }
                else {
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("171:1: externalDeclaration options {k=1; } : ( ( declarationSpecifiers declarator ( '{' | storageClassSpecifier | typeSpecifier | typeQualifier | functionSpecifier | '__attribute' | '__attribute__' ) )=> functionDefinition | declaration | ';' | asmDefinition );", 2, 7, input);

                    throw nvae;
                }
                }
                break;
            case 99:
                {
                int LA2_8 = input.LA(2);

                if ( (synpred8()) ) {
                    alt2=1;
                }
                else if ( (synpred9()) ) {
                    alt2=2;
                }
                else {
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("171:1: externalDeclaration options {k=1; } : ( ( declarationSpecifiers declarator ( '{' | storageClassSpecifier | typeSpecifier | typeQualifier | functionSpecifier | '__attribute' | '__attribute__' ) )=> functionDefinition | declaration | ';' | asmDefinition );", 2, 8, input);

                    throw nvae;
                }
                }
                break;
            case 100:
                {
                int LA2_9 = input.LA(2);

                if ( (synpred8()) ) {
                    alt2=1;
                }
                else if ( (synpred9()) ) {
                    alt2=2;
                }
                else {
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("171:1: externalDeclaration options {k=1; } : ( ( declarationSpecifiers declarator ( '{' | storageClassSpecifier | typeSpecifier | typeQualifier | functionSpecifier | '__attribute' | '__attribute__' ) )=> functionDefinition | declaration | ';' | asmDefinition );", 2, 9, input);

                    throw nvae;
                }
                }
                break;
            case 101:
                {
                int LA2_10 = input.LA(2);

                if ( (synpred8()) ) {
                    alt2=1;
                }
                else if ( (synpred9()) ) {
                    alt2=2;
                }
                else {
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("171:1: externalDeclaration options {k=1; } : ( ( declarationSpecifiers declarator ( '{' | storageClassSpecifier | typeSpecifier | typeQualifier | functionSpecifier | '__attribute' | '__attribute__' ) )=> functionDefinition | declaration | ';' | asmDefinition );", 2, 10, input);

                    throw nvae;
                }
                }
                break;
            case 102:
                {
                int LA2_11 = input.LA(2);

                if ( (synpred8()) ) {
                    alt2=1;
                }
                else if ( (synpred9()) ) {
                    alt2=2;
                }
                else {
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("171:1: externalDeclaration options {k=1; } : ( ( declarationSpecifiers declarator ( '{' | storageClassSpecifier | typeSpecifier | typeQualifier | functionSpecifier | '__attribute' | '__attribute__' ) )=> functionDefinition | declaration | ';' | asmDefinition );", 2, 11, input);

                    throw nvae;
                }
                }
                break;
            case 103:
                {
                int LA2_12 = input.LA(2);

                if ( (synpred8()) ) {
                    alt2=1;
                }
                else if ( (synpred9()) ) {
                    alt2=2;
                }
                else {
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("171:1: externalDeclaration options {k=1; } : ( ( declarationSpecifiers declarator ( '{' | storageClassSpecifier | typeSpecifier | typeQualifier | functionSpecifier | '__attribute' | '__attribute__' ) )=> functionDefinition | declaration | ';' | asmDefinition );", 2, 12, input);

                    throw nvae;
                }
                }
                break;
            case 104:
                {
                int LA2_13 = input.LA(2);

                if ( (synpred8()) ) {
                    alt2=1;
                }
                else if ( (synpred9()) ) {
                    alt2=2;
                }
                else {
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("171:1: externalDeclaration options {k=1; } : ( ( declarationSpecifiers declarator ( '{' | storageClassSpecifier | typeSpecifier | typeQualifier | functionSpecifier | '__attribute' | '__attribute__' ) )=> functionDefinition | declaration | ';' | asmDefinition );", 2, 13, input);

                    throw nvae;
                }
                }
                break;
            case 105:
                {
                int LA2_14 = input.LA(2);

                if ( (synpred8()) ) {
                    alt2=1;
                }
                else if ( (synpred9()) ) {
                    alt2=2;
                }
                else {
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("171:1: externalDeclaration options {k=1; } : ( ( declarationSpecifiers declarator ( '{' | storageClassSpecifier | typeSpecifier | typeQualifier | functionSpecifier | '__attribute' | '__attribute__' ) )=> functionDefinition | declaration | ';' | asmDefinition );", 2, 14, input);

                    throw nvae;
                }
                }
                break;
            case 106:
                {
                int LA2_15 = input.LA(2);

                if ( (synpred8()) ) {
                    alt2=1;
                }
                else if ( (synpred9()) ) {
                    alt2=2;
                }
                else {
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("171:1: externalDeclaration options {k=1; } : ( ( declarationSpecifiers declarator ( '{' | storageClassSpecifier | typeSpecifier | typeQualifier | functionSpecifier | '__attribute' | '__attribute__' ) )=> functionDefinition | declaration | ';' | asmDefinition );", 2, 15, input);

                    throw nvae;
                }
                }
                break;
            case 107:
                {
                int LA2_16 = input.LA(2);

                if ( (synpred8()) ) {
                    alt2=1;
                }
                else if ( (synpred9()) ) {
                    alt2=2;
                }
                else {
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("171:1: externalDeclaration options {k=1; } : ( ( declarationSpecifiers declarator ( '{' | storageClassSpecifier | typeSpecifier | typeQualifier | functionSpecifier | '__attribute' | '__attribute__' ) )=> functionDefinition | declaration | ';' | asmDefinition );", 2, 16, input);

                    throw nvae;
                }
                }
                break;
            case 108:
                {
                int LA2_17 = input.LA(2);

                if ( (synpred8()) ) {
                    alt2=1;
                }
                else if ( (synpred9()) ) {
                    alt2=2;
                }
                else {
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("171:1: externalDeclaration options {k=1; } : ( ( declarationSpecifiers declarator ( '{' | storageClassSpecifier | typeSpecifier | typeQualifier | functionSpecifier | '__attribute' | '__attribute__' ) )=> functionDefinition | declaration | ';' | asmDefinition );", 2, 17, input);

                    throw nvae;
                }
                }
                break;
            case 111:
            case 112:
                {
                int LA2_18 = input.LA(2);

                if ( (synpred8()) ) {
                    alt2=1;
                }
                else if ( (synpred9()) ) {
                    alt2=2;
                }
                else {
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("171:1: externalDeclaration options {k=1; } : ( ( declarationSpecifiers declarator ( '{' | storageClassSpecifier | typeSpecifier | typeQualifier | functionSpecifier | '__attribute' | '__attribute__' ) )=> functionDefinition | declaration | ';' | asmDefinition );", 2, 18, input);

                    throw nvae;
                }
                }
                break;
            case 114:
                {
                int LA2_19 = input.LA(2);

                if ( (synpred8()) ) {
                    alt2=1;
                }
                else if ( (synpred9()) ) {
                    alt2=2;
                }
                else {
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("171:1: externalDeclaration options {k=1; } : ( ( declarationSpecifiers declarator ( '{' | storageClassSpecifier | typeSpecifier | typeQualifier | functionSpecifier | '__attribute' | '__attribute__' ) )=> functionDefinition | declaration | ';' | asmDefinition );", 2, 19, input);

                    throw nvae;
                }
                }
                break;
            case IDENTIFIER:
                {
                int LA2_20 = input.LA(2);

                if ( ((synpred8()&&isTypeName(input.LT(1).getText()))) ) {
                    alt2=1;
                }
                else if ( ((synpred9()&&isTypeName(input.LT(1).getText()))) ) {
                    alt2=2;
                }
                else {
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("171:1: externalDeclaration options {k=1; } : ( ( declarationSpecifiers declarator ( '{' | storageClassSpecifier | typeSpecifier | typeQualifier | functionSpecifier | '__attribute' | '__attribute__' ) )=> functionDefinition | declaration | ';' | asmDefinition );", 2, 20, input);

                    throw nvae;
                }
                }
                break;
            case 131:
                {
                int LA2_21 = input.LA(2);

                if ( (synpred8()) ) {
                    alt2=1;
                }
                else if ( (synpred9()) ) {
                    alt2=2;
                }
                else {
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("171:1: externalDeclaration options {k=1; } : ( ( declarationSpecifiers declarator ( '{' | storageClassSpecifier | typeSpecifier | typeQualifier | functionSpecifier | '__attribute' | '__attribute__' ) )=> functionDefinition | declaration | ';' | asmDefinition );", 2, 21, input);

                    throw nvae;
                }
                }
                break;
            case 132:
                {
                int LA2_22 = input.LA(2);

                if ( (synpred8()) ) {
                    alt2=1;
                }
                else if ( (synpred9()) ) {
                    alt2=2;
                }
                else {
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("171:1: externalDeclaration options {k=1; } : ( ( declarationSpecifiers declarator ( '{' | storageClassSpecifier | typeSpecifier | typeQualifier | functionSpecifier | '__attribute' | '__attribute__' ) )=> functionDefinition | declaration | ';' | asmDefinition );", 2, 22, input);

                    throw nvae;
                }
                }
                break;
            case 133:
                {
                int LA2_23 = input.LA(2);

                if ( (synpred8()) ) {
                    alt2=1;
                }
                else if ( (synpred9()) ) {
                    alt2=2;
                }
                else {
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("171:1: externalDeclaration options {k=1; } : ( ( declarationSpecifiers declarator ( '{' | storageClassSpecifier | typeSpecifier | typeQualifier | functionSpecifier | '__attribute' | '__attribute__' ) )=> functionDefinition | declaration | ';' | asmDefinition );", 2, 23, input);

                    throw nvae;
                }
                }
                break;
            case 115:
                {
                int LA2_24 = input.LA(2);

                if ( (synpred8()) ) {
                    alt2=1;
                }
                else if ( (synpred9()) ) {
                    alt2=2;
                }
                else {
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("171:1: externalDeclaration options {k=1; } : ( ( declarationSpecifiers declarator ( '{' | storageClassSpecifier | typeSpecifier | typeQualifier | functionSpecifier | '__attribute' | '__attribute__' ) )=> functionDefinition | declaration | ';' | asmDefinition );", 2, 24, input);

                    throw nvae;
                }
                }
                break;
            case 116:
                {
                int LA2_25 = input.LA(2);

                if ( (synpred8()) ) {
                    alt2=1;
                }
                else if ( (synpred9()) ) {
                    alt2=2;
                }
                else {
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("171:1: externalDeclaration options {k=1; } : ( ( declarationSpecifiers declarator ( '{' | storageClassSpecifier | typeSpecifier | typeQualifier | functionSpecifier | '__attribute' | '__attribute__' ) )=> functionDefinition | declaration | ';' | asmDefinition );", 2, 25, input);

                    throw nvae;
                }
                }
                break;
            case 117:
                {
                int LA2_26 = input.LA(2);

                if ( (synpred8()) ) {
                    alt2=1;
                }
                else if ( (synpred9()) ) {
                    alt2=2;
                }
                else {
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("171:1: externalDeclaration options {k=1; } : ( ( declarationSpecifiers declarator ( '{' | storageClassSpecifier | typeSpecifier | typeQualifier | functionSpecifier | '__attribute' | '__attribute__' ) )=> functionDefinition | declaration | ';' | asmDefinition );", 2, 26, input);

                    throw nvae;
                }
                }
                break;
            case 118:
                {
                int LA2_27 = input.LA(2);

                if ( (synpred8()) ) {
                    alt2=1;
                }
                else if ( (synpred9()) ) {
                    alt2=2;
                }
                else {
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("171:1: externalDeclaration options {k=1; } : ( ( declarationSpecifiers declarator ( '{' | storageClassSpecifier | typeSpecifier | typeQualifier | functionSpecifier | '__attribute' | '__attribute__' ) )=> functionDefinition | declaration | ';' | asmDefinition );", 2, 27, input);

                    throw nvae;
                }
                }
                break;
            case 119:
                {
                int LA2_28 = input.LA(2);

                if ( (synpred8()) ) {
                    alt2=1;
                }
                else if ( (synpred9()) ) {
                    alt2=2;
                }
                else {
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("171:1: externalDeclaration options {k=1; } : ( ( declarationSpecifiers declarator ( '{' | storageClassSpecifier | typeSpecifier | typeQualifier | functionSpecifier | '__attribute' | '__attribute__' ) )=> functionDefinition | declaration | ';' | asmDefinition );", 2, 28, input);

                    throw nvae;
                }
                }
                break;
            case 120:
                {
                int LA2_29 = input.LA(2);

                if ( (synpred8()) ) {
                    alt2=1;
                }
                else if ( (synpred9()) ) {
                    alt2=2;
                }
                else {
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("171:1: externalDeclaration options {k=1; } : ( ( declarationSpecifiers declarator ( '{' | storageClassSpecifier | typeSpecifier | typeQualifier | functionSpecifier | '__attribute' | '__attribute__' ) )=> functionDefinition | declaration | ';' | asmDefinition );", 2, 29, input);

                    throw nvae;
                }
                }
                break;
            case 121:
                {
                int LA2_30 = input.LA(2);

                if ( (synpred8()) ) {
                    alt2=1;
                }
                else if ( (synpred9()) ) {
                    alt2=2;
                }
                else {
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("171:1: externalDeclaration options {k=1; } : ( ( declarationSpecifiers declarator ( '{' | storageClassSpecifier | typeSpecifier | typeQualifier | functionSpecifier | '__attribute' | '__attribute__' ) )=> functionDefinition | declaration | ';' | asmDefinition );", 2, 30, input);

                    throw nvae;
                }
                }
                break;
            case 122:
                {
                int LA2_31 = input.LA(2);

                if ( (synpred8()) ) {
                    alt2=1;
                }
                else if ( (synpred9()) ) {
                    alt2=2;
                }
                else {
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("171:1: externalDeclaration options {k=1; } : ( ( declarationSpecifiers declarator ( '{' | storageClassSpecifier | typeSpecifier | typeQualifier | functionSpecifier | '__attribute' | '__attribute__' ) )=> functionDefinition | declaration | ';' | asmDefinition );", 2, 31, input);

                    throw nvae;
                }
                }
                break;
            case 123:
                {
                int LA2_32 = input.LA(2);

                if ( (synpred8()) ) {
                    alt2=1;
                }
                else if ( (synpred9()) ) {
                    alt2=2;
                }
                else {
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("171:1: externalDeclaration options {k=1; } : ( ( declarationSpecifiers declarator ( '{' | storageClassSpecifier | typeSpecifier | typeQualifier | functionSpecifier | '__attribute' | '__attribute__' ) )=> functionDefinition | declaration | ';' | asmDefinition );", 2, 32, input);

                    throw nvae;
                }
                }
                break;
            case 124:
                {
                int LA2_33 = input.LA(2);

                if ( (synpred8()) ) {
                    alt2=1;
                }
                else if ( (synpred9()) ) {
                    alt2=2;
                }
                else {
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("171:1: externalDeclaration options {k=1; } : ( ( declarationSpecifiers declarator ( '{' | storageClassSpecifier | typeSpecifier | typeQualifier | functionSpecifier | '__attribute' | '__attribute__' ) )=> functionDefinition | declaration | ';' | asmDefinition );", 2, 33, input);

                    throw nvae;
                }
                }
                break;
            case 125:
                {
                int LA2_34 = input.LA(2);

                if ( (synpred8()) ) {
                    alt2=1;
                }
                else if ( (synpred9()) ) {
                    alt2=2;
                }
                else {
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("171:1: externalDeclaration options {k=1; } : ( ( declarationSpecifiers declarator ( '{' | storageClassSpecifier | typeSpecifier | typeQualifier | functionSpecifier | '__attribute' | '__attribute__' ) )=> functionDefinition | declaration | ';' | asmDefinition );", 2, 34, input);

                    throw nvae;
                }
                }
                break;
            case 126:
                {
                int LA2_35 = input.LA(2);

                if ( (synpred8()) ) {
                    alt2=1;
                }
                else if ( (synpred9()) ) {
                    alt2=2;
                }
                else {
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("171:1: externalDeclaration options {k=1; } : ( ( declarationSpecifiers declarator ( '{' | storageClassSpecifier | typeSpecifier | typeQualifier | functionSpecifier | '__attribute' | '__attribute__' ) )=> functionDefinition | declaration | ';' | asmDefinition );", 2, 35, input);

                    throw nvae;
                }
                }
                break;
            case 135:
            case 136:
                {
                int LA2_36 = input.LA(2);

                if ( (synpred8()) ) {
                    alt2=1;
                }
                else if ( (synpred9()) ) {
                    alt2=2;
                }
                else {
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("171:1: externalDeclaration options {k=1; } : ( ( declarationSpecifiers declarator ( '{' | storageClassSpecifier | typeSpecifier | typeQualifier | functionSpecifier | '__attribute' | '__attribute__' ) )=> functionDefinition | declaration | ';' | asmDefinition );", 2, 36, input);

                    throw nvae;
                }
                }
                break;
            case 85:
                {
                alt2=2;
                }
                break;
            case 79:
                {
                alt2=3;
                }
                break;
            case 80:
            case 81:
            case 82:
                {
                alt2=4;
                }
                break;
            default:
                if (backtracking>0) {failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("171:1: externalDeclaration options {k=1; } : ( ( declarationSpecifiers declarator ( '{' | storageClassSpecifier | typeSpecifier | typeQualifier | functionSpecifier | '__attribute' | '__attribute__' ) )=> functionDefinition | declaration | ';' | asmDefinition );", 2, 0, input);

                throw nvae;
            }

            switch (alt2) {
                case 1 :
                    // GNUCa.g:173:4: ( declarationSpecifiers declarator ( '{' | storageClassSpecifier | typeSpecifier | typeQualifier | functionSpecifier | '__attribute' | '__attribute__' ) )=> functionDefinition
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_functionDefinition_in_externalDeclaration372);
                    functionDefinition2=functionDefinition();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) adaptor.addChild(root_0, functionDefinition2.getTree());

                    }
                    break;
                case 2 :
                    // GNUCa.g:174:4: declaration
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_declaration_in_externalDeclaration378);
                    declaration3=declaration();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) adaptor.addChild(root_0, declaration3.getTree());

                    }
                    break;
                case 3 :
                    // GNUCa.g:175:4: ';'
                    {
                    root_0 = (Object)adaptor.nil();

                    char_literal4=(Token)input.LT(1);
                    match(input,79,FOLLOW_79_in_externalDeclaration383); if (failed) return retval;

                    }
                    break;
                case 4 :
                    // GNUCa.g:176:4: asmDefinition
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_asmDefinition_in_externalDeclaration389);
                    asmDefinition5=asmDefinition();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) adaptor.addChild(root_0, asmDefinition5.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 2, externalDeclaration_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end externalDeclaration

    public static class asmDefinition_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start asmDefinition
    // GNUCa.g:179:1: asmDefinition : simpleAsmExpr ;
    public final asmDefinition_return asmDefinition() throws RecognitionException {
        asmDefinition_return retval = new asmDefinition_return();
        retval.start = input.LT(1);
        int asmDefinition_StartIndex = input.index();
        Object root_0 = null;

        simpleAsmExpr_return simpleAsmExpr6 = null;



        try {
            if ( backtracking>0 && alreadyParsedRule(input, 3) ) { return retval; }
            // GNUCa.g:180:2: ( simpleAsmExpr )
            // GNUCa.g:180:4: simpleAsmExpr
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_simpleAsmExpr_in_asmDefinition409);
            simpleAsmExpr6=simpleAsmExpr();
            _fsp--;
            if (failed) return retval;
            if ( backtracking==0 ) adaptor.addChild(root_0, simpleAsmExpr6.getTree());

            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 3, asmDefinition_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end asmDefinition

    public static class simpleAsmExpr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start simpleAsmExpr
    // GNUCa.g:183:1: simpleAsmExpr : ( 'asm' | '__asm' | '__asm__' ) '(' asmStringLiteral ')' ->;
    public final simpleAsmExpr_return simpleAsmExpr() throws RecognitionException {
        simpleAsmExpr_return retval = new simpleAsmExpr_return();
        retval.start = input.LT(1);
        int simpleAsmExpr_StartIndex = input.index();
        Object root_0 = null;

        Token string_literal7=null;
        Token string_literal8=null;
        Token string_literal9=null;
        Token char_literal10=null;
        Token char_literal12=null;
        asmStringLiteral_return asmStringLiteral11 = null;


        Object string_literal7_tree=null;
        Object string_literal8_tree=null;
        Object string_literal9_tree=null;
        Object char_literal10_tree=null;
        Object char_literal12_tree=null;
        RewriteRuleTokenStream stream_82=new RewriteRuleTokenStream(adaptor,"token 82");
        RewriteRuleTokenStream stream_83=new RewriteRuleTokenStream(adaptor,"token 83");
        RewriteRuleTokenStream stream_80=new RewriteRuleTokenStream(adaptor,"token 80");
        RewriteRuleTokenStream stream_81=new RewriteRuleTokenStream(adaptor,"token 81");
        RewriteRuleTokenStream stream_84=new RewriteRuleTokenStream(adaptor,"token 84");
        RewriteRuleSubtreeStream stream_asmStringLiteral=new RewriteRuleSubtreeStream(adaptor,"rule asmStringLiteral");
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 4) ) { return retval; }
            // GNUCa.g:184:2: ( ( 'asm' | '__asm' | '__asm__' ) '(' asmStringLiteral ')' ->)
            // GNUCa.g:184:4: ( 'asm' | '__asm' | '__asm__' ) '(' asmStringLiteral ')'
            {
            // GNUCa.g:184:4: ( 'asm' | '__asm' | '__asm__' )
            int alt3=3;
            switch ( input.LA(1) ) {
            case 80:
                {
                alt3=1;
                }
                break;
            case 81:
                {
                alt3=2;
                }
                break;
            case 82:
                {
                alt3=3;
                }
                break;
            default:
                if (backtracking>0) {failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("184:4: ( 'asm' | '__asm' | '__asm__' )", 3, 0, input);

                throw nvae;
            }

            switch (alt3) {
                case 1 :
                    // GNUCa.g:184:5: 'asm'
                    {
                    string_literal7=(Token)input.LT(1);
                    match(input,80,FOLLOW_80_in_simpleAsmExpr422); if (failed) return retval;
                    if ( backtracking==0 ) stream_80.add(string_literal7);


                    }
                    break;
                case 2 :
                    // GNUCa.g:184:11: '__asm'
                    {
                    string_literal8=(Token)input.LT(1);
                    match(input,81,FOLLOW_81_in_simpleAsmExpr424); if (failed) return retval;
                    if ( backtracking==0 ) stream_81.add(string_literal8);


                    }
                    break;
                case 3 :
                    // GNUCa.g:184:19: '__asm__'
                    {
                    string_literal9=(Token)input.LT(1);
                    match(input,82,FOLLOW_82_in_simpleAsmExpr426); if (failed) return retval;
                    if ( backtracking==0 ) stream_82.add(string_literal9);


                    }
                    break;

            }

            char_literal10=(Token)input.LT(1);
            match(input,83,FOLLOW_83_in_simpleAsmExpr429); if (failed) return retval;
            if ( backtracking==0 ) stream_83.add(char_literal10);

            pushFollow(FOLLOW_asmStringLiteral_in_simpleAsmExpr431);
            asmStringLiteral11=asmStringLiteral();
            _fsp--;
            if (failed) return retval;
            if ( backtracking==0 ) stream_asmStringLiteral.add(asmStringLiteral11.getTree());
            char_literal12=(Token)input.LT(1);
            match(input,84,FOLLOW_84_in_simpleAsmExpr433); if (failed) return retval;
            if ( backtracking==0 ) stream_84.add(char_literal12);


            // AST REWRITE
            // elements: 
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 184:55: ->
            {
                root_0 = null;
            }

            }

            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 4, simpleAsmExpr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end simpleAsmExpr

    public static class asmStringLiteral_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start asmStringLiteral
    // GNUCa.g:187:1: asmStringLiteral : sTRING_LITERAL ;
    public final asmStringLiteral_return asmStringLiteral() throws RecognitionException {
        asmStringLiteral_return retval = new asmStringLiteral_return();
        retval.start = input.LT(1);
        int asmStringLiteral_StartIndex = input.index();
        Object root_0 = null;

        sTRING_LITERAL_return sTRING_LITERAL13 = null;



        try {
            if ( backtracking>0 && alreadyParsedRule(input, 5) ) { return retval; }
            // GNUCa.g:188:2: ( sTRING_LITERAL )
            // GNUCa.g:188:4: sTRING_LITERAL
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_sTRING_LITERAL_in_asmStringLiteral449);
            sTRING_LITERAL13=sTRING_LITERAL();
            _fsp--;
            if (failed) return retval;
            if ( backtracking==0 ) adaptor.addChild(root_0, sTRING_LITERAL13.getTree());

            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 5, asmStringLiteral_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end asmStringLiteral

    public static class functionDefinition_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start functionDefinition
    // GNUCa.g:191:1: functionDefinition : declarationSpecifiers declarator ( compoundStatement | ( declaration )+ compoundStatement ) -> ^( FUNCTION_DEFINITION declarationSpecifiers declarator ( declaration )* compoundStatement ) ;
    public final functionDefinition_return functionDefinition() throws RecognitionException {
        Symbols_stack.push(new Symbols_scope());

        functionDefinition_return retval = new functionDefinition_return();
        retval.start = input.LT(1);
        int functionDefinition_StartIndex = input.index();
        Object root_0 = null;

        declarationSpecifiers_return declarationSpecifiers14 = null;

        declarator_return declarator15 = null;

        compoundStatement_return compoundStatement16 = null;

        declaration_return declaration17 = null;

        compoundStatement_return compoundStatement18 = null;


        RewriteRuleSubtreeStream stream_declarator=new RewriteRuleSubtreeStream(adaptor,"rule declarator");
        RewriteRuleSubtreeStream stream_declaration=new RewriteRuleSubtreeStream(adaptor,"rule declaration");
        RewriteRuleSubtreeStream stream_compoundStatement=new RewriteRuleSubtreeStream(adaptor,"rule compoundStatement");
        RewriteRuleSubtreeStream stream_declarationSpecifiers=new RewriteRuleSubtreeStream(adaptor,"rule declarationSpecifiers");
         ((Symbols_scope)Symbols_stack.peek()).types = new HashSet(); 
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 6) ) { return retval; }
            // GNUCa.g:194:2: ( declarationSpecifiers declarator ( compoundStatement | ( declaration )+ compoundStatement ) -> ^( FUNCTION_DEFINITION declarationSpecifiers declarator ( declaration )* compoundStatement ) )
            // GNUCa.g:194:4: declarationSpecifiers declarator ( compoundStatement | ( declaration )+ compoundStatement )
            {
            pushFollow(FOLLOW_declarationSpecifiers_in_functionDefinition477);
            declarationSpecifiers14=declarationSpecifiers();
            _fsp--;
            if (failed) return retval;
            if ( backtracking==0 ) stream_declarationSpecifiers.add(declarationSpecifiers14.getTree());
            pushFollow(FOLLOW_declarator_in_functionDefinition479);
            declarator15=declarator();
            _fsp--;
            if (failed) return retval;
            if ( backtracking==0 ) stream_declarator.add(declarator15.getTree());
            // GNUCa.g:195:3: ( compoundStatement | ( declaration )+ compoundStatement )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==109) ) {
                alt5=1;
            }
            else if ( (LA5_0==IDENTIFIER||LA5_0==85||(LA5_0>=88 && LA5_0<=108)||(LA5_0>=111 && LA5_0<=112)||(LA5_0>=114 && LA5_0<=126)||(LA5_0>=131 && LA5_0<=133)||(LA5_0>=135 && LA5_0<=136)) ) {
                alt5=2;
            }
            else {
                if (backtracking>0) {failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("195:3: ( compoundStatement | ( declaration )+ compoundStatement )", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // GNUCa.g:195:5: compoundStatement
                    {
                    pushFollow(FOLLOW_compoundStatement_in_functionDefinition486);
                    compoundStatement16=compoundStatement();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_compoundStatement.add(compoundStatement16.getTree());

                    }
                    break;
                case 2 :
                    // GNUCa.g:196:5: ( declaration )+ compoundStatement
                    {
                    // GNUCa.g:196:5: ( declaration )+
                    int cnt4=0;
                    loop4:
                    do {
                        int alt4=2;
                        int LA4_0 = input.LA(1);

                        if ( (LA4_0==IDENTIFIER||LA4_0==85||(LA4_0>=88 && LA4_0<=108)||(LA4_0>=111 && LA4_0<=112)||(LA4_0>=114 && LA4_0<=126)||(LA4_0>=131 && LA4_0<=133)||(LA4_0>=135 && LA4_0<=136)) ) {
                            alt4=1;
                        }


                        switch (alt4) {
                    	case 1 :
                    	    // GNUCa.g:0:0: declaration
                    	    {
                    	    pushFollow(FOLLOW_declaration_in_functionDefinition494);
                    	    declaration17=declaration();
                    	    _fsp--;
                    	    if (failed) return retval;
                    	    if ( backtracking==0 ) stream_declaration.add(declaration17.getTree());

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt4 >= 1 ) break loop4;
                    	    if (backtracking>0) {failed=true; return retval;}
                                EarlyExitException eee =
                                    new EarlyExitException(4, input);
                                throw eee;
                        }
                        cnt4++;
                    } while (true);

                    pushFollow(FOLLOW_compoundStatement_in_functionDefinition497);
                    compoundStatement18=compoundStatement();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_compoundStatement.add(compoundStatement18.getTree());

                    }
                    break;

            }


            // AST REWRITE
            // elements: declaration, declarationSpecifiers, declarator, compoundStatement
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 198:3: -> ^( FUNCTION_DEFINITION declarationSpecifiers declarator ( declaration )* compoundStatement )
            {
                // GNUCa.g:198:6: ^( FUNCTION_DEFINITION declarationSpecifiers declarator ( declaration )* compoundStatement )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(adaptor.create(FUNCTION_DEFINITION, "FUNCTION_DEFINITION"), root_1);

                adaptor.addChild(root_1, stream_declarationSpecifiers.next());
                adaptor.addChild(root_1, stream_declarator.next());
                // GNUCa.g:198:61: ( declaration )*
                while ( stream_declaration.hasNext() ) {
                    adaptor.addChild(root_1, stream_declaration.next());

                }
                stream_declaration.reset();
                adaptor.addChild(root_1, stream_compoundStatement.next());

                adaptor.addChild(root_0, root_1);
                }

            }

            }

            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 6, functionDefinition_StartIndex); }
            Symbols_stack.pop();

        }
        return retval;
    }
    // $ANTLR end functionDefinition

    public static class nestedFunctionDefinition_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start nestedFunctionDefinition
    // GNUCa.g:201:1: nestedFunctionDefinition : declarationSpecifiers declarator ( declaration )* compoundStatement -> ^( FUNCTION_DEFINITION declarationSpecifiers declarator ( declaration )* compoundStatement ) ;
    public final nestedFunctionDefinition_return nestedFunctionDefinition() throws RecognitionException {
        Symbols_stack.push(new Symbols_scope());

        nestedFunctionDefinition_return retval = new nestedFunctionDefinition_return();
        retval.start = input.LT(1);
        int nestedFunctionDefinition_StartIndex = input.index();
        Object root_0 = null;

        declarationSpecifiers_return declarationSpecifiers19 = null;

        declarator_return declarator20 = null;

        declaration_return declaration21 = null;

        compoundStatement_return compoundStatement22 = null;


        RewriteRuleSubtreeStream stream_declarator=new RewriteRuleSubtreeStream(adaptor,"rule declarator");
        RewriteRuleSubtreeStream stream_declaration=new RewriteRuleSubtreeStream(adaptor,"rule declaration");
        RewriteRuleSubtreeStream stream_compoundStatement=new RewriteRuleSubtreeStream(adaptor,"rule compoundStatement");
        RewriteRuleSubtreeStream stream_declarationSpecifiers=new RewriteRuleSubtreeStream(adaptor,"rule declarationSpecifiers");
         ((Symbols_scope)Symbols_stack.peek()).types = new HashSet(); 
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 7) ) { return retval; }
            // GNUCa.g:204:2: ( declarationSpecifiers declarator ( declaration )* compoundStatement -> ^( FUNCTION_DEFINITION declarationSpecifiers declarator ( declaration )* compoundStatement ) )
            // GNUCa.g:204:4: declarationSpecifiers declarator ( declaration )* compoundStatement
            {
            pushFollow(FOLLOW_declarationSpecifiers_in_nestedFunctionDefinition542);
            declarationSpecifiers19=declarationSpecifiers();
            _fsp--;
            if (failed) return retval;
            if ( backtracking==0 ) stream_declarationSpecifiers.add(declarationSpecifiers19.getTree());
            pushFollow(FOLLOW_declarator_in_nestedFunctionDefinition544);
            declarator20=declarator();
            _fsp--;
            if (failed) return retval;
            if ( backtracking==0 ) stream_declarator.add(declarator20.getTree());
            // GNUCa.g:204:37: ( declaration )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==IDENTIFIER||LA6_0==85||(LA6_0>=88 && LA6_0<=108)||(LA6_0>=111 && LA6_0<=112)||(LA6_0>=114 && LA6_0<=126)||(LA6_0>=131 && LA6_0<=133)||(LA6_0>=135 && LA6_0<=136)) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // GNUCa.g:0:0: declaration
            	    {
            	    pushFollow(FOLLOW_declaration_in_nestedFunctionDefinition546);
            	    declaration21=declaration();
            	    _fsp--;
            	    if (failed) return retval;
            	    if ( backtracking==0 ) stream_declaration.add(declaration21.getTree());

            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);

            pushFollow(FOLLOW_compoundStatement_in_nestedFunctionDefinition549);
            compoundStatement22=compoundStatement();
            _fsp--;
            if (failed) return retval;
            if ( backtracking==0 ) stream_compoundStatement.add(compoundStatement22.getTree());

            // AST REWRITE
            // elements: declaration, declarationSpecifiers, declarator, compoundStatement
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 205:3: -> ^( FUNCTION_DEFINITION declarationSpecifiers declarator ( declaration )* compoundStatement )
            {
                // GNUCa.g:205:6: ^( FUNCTION_DEFINITION declarationSpecifiers declarator ( declaration )* compoundStatement )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(adaptor.create(FUNCTION_DEFINITION, "FUNCTION_DEFINITION"), root_1);

                adaptor.addChild(root_1, stream_declarationSpecifiers.next());
                adaptor.addChild(root_1, stream_declarator.next());
                // GNUCa.g:205:61: ( declaration )*
                while ( stream_declaration.hasNext() ) {
                    adaptor.addChild(root_1, stream_declaration.next());

                }
                stream_declaration.reset();
                adaptor.addChild(root_1, stream_compoundStatement.next());

                adaptor.addChild(root_0, root_1);
                }

            }

            }

            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 7, nestedFunctionDefinition_StartIndex); }
            Symbols_stack.pop();

        }
        return retval;
    }
    // $ANTLR end nestedFunctionDefinition

    protected static class declaration_scope {
        boolean isTypedef;
    }
    protected Stack declaration_stack = new Stack();

    public static class declaration_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start declaration
    // GNUCa.g:211:1: declaration : ( 'typedef' ( declarationSpecifiers )? ( initDeclaratorList )? ';' -> ^( 'typedef' ( declarationSpecifiers )? ( initDeclaratorList )? ) | declarationSpecifiers ( initDeclaratorList )? ';' -> ^( DECLARATION declarationSpecifiers ( initDeclaratorList )? ) );
    public final declaration_return declaration() throws RecognitionException {
        declaration_stack.push(new declaration_scope());
        declaration_return retval = new declaration_return();
        retval.start = input.LT(1);
        int declaration_StartIndex = input.index();
        Object root_0 = null;

        Token string_literal23=null;
        Token char_literal26=null;
        Token char_literal29=null;
        declarationSpecifiers_return declarationSpecifiers24 = null;

        initDeclaratorList_return initDeclaratorList25 = null;

        declarationSpecifiers_return declarationSpecifiers27 = null;

        initDeclaratorList_return initDeclaratorList28 = null;


        Object string_literal23_tree=null;
        Object char_literal26_tree=null;
        Object char_literal29_tree=null;
        RewriteRuleTokenStream stream_79=new RewriteRuleTokenStream(adaptor,"token 79");
        RewriteRuleTokenStream stream_85=new RewriteRuleTokenStream(adaptor,"token 85");
        RewriteRuleSubtreeStream stream_declarationSpecifiers=new RewriteRuleSubtreeStream(adaptor,"rule declarationSpecifiers");
        RewriteRuleSubtreeStream stream_initDeclaratorList=new RewriteRuleSubtreeStream(adaptor,"rule initDeclaratorList");
         ((declaration_scope)declaration_stack.peek()).isTypedef = false; 
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 8) ) { return retval; }
            // GNUCa.g:214:2: ( 'typedef' ( declarationSpecifiers )? ( initDeclaratorList )? ';' -> ^( 'typedef' ( declarationSpecifiers )? ( initDeclaratorList )? ) | declarationSpecifiers ( initDeclaratorList )? ';' -> ^( DECLARATION declarationSpecifiers ( initDeclaratorList )? ) )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==85) ) {
                alt10=1;
            }
            else if ( (LA10_0==IDENTIFIER||(LA10_0>=88 && LA10_0<=108)||(LA10_0>=111 && LA10_0<=112)||(LA10_0>=114 && LA10_0<=126)||(LA10_0>=131 && LA10_0<=133)||(LA10_0>=135 && LA10_0<=136)) ) {
                alt10=2;
            }
            else {
                if (backtracking>0) {failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("211:1: declaration : ( 'typedef' ( declarationSpecifiers )? ( initDeclaratorList )? ';' -> ^( 'typedef' ( declarationSpecifiers )? ( initDeclaratorList )? ) | declarationSpecifiers ( initDeclaratorList )? ';' -> ^( DECLARATION declarationSpecifiers ( initDeclaratorList )? ) );", 10, 0, input);

                throw nvae;
            }
            switch (alt10) {
                case 1 :
                    // GNUCa.g:214:4: 'typedef' ( declarationSpecifiers )? ( initDeclaratorList )? ';'
                    {
                    string_literal23=(Token)input.LT(1);
                    match(input,85,FOLLOW_85_in_declaration592); if (failed) return retval;
                    if ( backtracking==0 ) stream_85.add(string_literal23);

                    // GNUCa.g:214:14: ( declarationSpecifiers )?
                    int alt7=2;
                    int LA7_0 = input.LA(1);

                    if ( ((LA7_0>=88 && LA7_0<=108)||(LA7_0>=111 && LA7_0<=112)||(LA7_0>=114 && LA7_0<=126)||(LA7_0>=131 && LA7_0<=133)||(LA7_0>=135 && LA7_0<=136)) ) {
                        alt7=1;
                    }
                    else if ( (LA7_0==IDENTIFIER) ) {
                        int LA7_20 = input.LA(2);

                        if ( ((synpred16()&&isTypeName(input.LT(1).getText()))) ) {
                            alt7=1;
                        }
                    }
                    switch (alt7) {
                        case 1 :
                            // GNUCa.g:0:0: declarationSpecifiers
                            {
                            pushFollow(FOLLOW_declarationSpecifiers_in_declaration594);
                            declarationSpecifiers24=declarationSpecifiers();
                            _fsp--;
                            if (failed) return retval;
                            if ( backtracking==0 ) stream_declarationSpecifiers.add(declarationSpecifiers24.getTree());

                            }
                            break;

                    }

                    if ( backtracking==0 ) {
                      ((declaration_scope)declaration_stack.peek()).isTypedef =true;
                    }
                    // GNUCa.g:214:70: ( initDeclaratorList )?
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0==IDENTIFIER||LA8_0==83||LA8_0==129) ) {
                        alt8=1;
                    }
                    switch (alt8) {
                        case 1 :
                            // GNUCa.g:0:0: initDeclaratorList
                            {
                            pushFollow(FOLLOW_initDeclaratorList_in_declaration600);
                            initDeclaratorList25=initDeclaratorList();
                            _fsp--;
                            if (failed) return retval;
                            if ( backtracking==0 ) stream_initDeclaratorList.add(initDeclaratorList25.getTree());

                            }
                            break;

                    }

                    char_literal26=(Token)input.LT(1);
                    match(input,79,FOLLOW_79_in_declaration603); if (failed) return retval;
                    if ( backtracking==0 ) stream_79.add(char_literal26);


                    // AST REWRITE
                    // elements: 85, declarationSpecifiers, initDeclaratorList
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 214:94: -> ^( 'typedef' ( declarationSpecifiers )? ( initDeclaratorList )? )
                    {
                        // GNUCa.g:214:97: ^( 'typedef' ( declarationSpecifiers )? ( initDeclaratorList )? )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_85.next(), root_1);

                        // GNUCa.g:214:109: ( declarationSpecifiers )?
                        if ( stream_declarationSpecifiers.hasNext() ) {
                            adaptor.addChild(root_1, stream_declarationSpecifiers.next());

                        }
                        stream_declarationSpecifiers.reset();
                        // GNUCa.g:214:132: ( initDeclaratorList )?
                        if ( stream_initDeclaratorList.hasNext() ) {
                            adaptor.addChild(root_1, stream_initDeclaratorList.next());

                        }
                        stream_initDeclaratorList.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;
                case 2 :
                    // GNUCa.g:216:4: declarationSpecifiers ( initDeclaratorList )? ';'
                    {
                    pushFollow(FOLLOW_declarationSpecifiers_in_declaration625);
                    declarationSpecifiers27=declarationSpecifiers();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_declarationSpecifiers.add(declarationSpecifiers27.getTree());
                    // GNUCa.g:216:26: ( initDeclaratorList )?
                    int alt9=2;
                    int LA9_0 = input.LA(1);

                    if ( (LA9_0==IDENTIFIER||LA9_0==83||LA9_0==129) ) {
                        alt9=1;
                    }
                    switch (alt9) {
                        case 1 :
                            // GNUCa.g:0:0: initDeclaratorList
                            {
                            pushFollow(FOLLOW_initDeclaratorList_in_declaration627);
                            initDeclaratorList28=initDeclaratorList();
                            _fsp--;
                            if (failed) return retval;
                            if ( backtracking==0 ) stream_initDeclaratorList.add(initDeclaratorList28.getTree());

                            }
                            break;

                    }

                    char_literal29=(Token)input.LT(1);
                    match(input,79,FOLLOW_79_in_declaration630); if (failed) return retval;
                    if ( backtracking==0 ) stream_79.add(char_literal29);


                    // AST REWRITE
                    // elements: initDeclaratorList, declarationSpecifiers
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 216:50: -> ^( DECLARATION declarationSpecifiers ( initDeclaratorList )? )
                    {
                        // GNUCa.g:216:53: ^( DECLARATION declarationSpecifiers ( initDeclaratorList )? )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(adaptor.create(DECLARATION, "DECLARATION"), root_1);

                        adaptor.addChild(root_1, stream_declarationSpecifiers.next());
                        // GNUCa.g:216:89: ( initDeclaratorList )?
                        if ( stream_initDeclaratorList.hasNext() ) {
                            adaptor.addChild(root_1, stream_initDeclaratorList.next());

                        }
                        stream_initDeclaratorList.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 8, declaration_StartIndex); }
            declaration_stack.pop();
        }
        return retval;
    }
    // $ANTLR end declaration

    public static class declarationSpecifiers_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start declarationSpecifiers
    // GNUCa.g:219:1: declarationSpecifiers : (sc+= storageClassSpecifier | typeSpecifier | typeQualifier | sc+= functionSpecifier | attributes )+ -> ^( DECLARATION_SPECIFIERS ^( XTYPE_SPECIFIER ( typeSpecifier )* ) ^( XTYPE_QUALIFIER ( typeQualifier )* ) ^( XSTORAGE_CLASS ( $sc)* ) ) ;
    public final declarationSpecifiers_return declarationSpecifiers() throws RecognitionException {
        declarationSpecifiers_return retval = new declarationSpecifiers_return();
        retval.start = input.LT(1);
        int declarationSpecifiers_StartIndex = input.index();
        Object root_0 = null;

        List list_sc=null;
        typeSpecifier_return typeSpecifier30 = null;

        typeQualifier_return typeQualifier31 = null;

        attributes_return attributes32 = null;

        RuleReturnScope sc = null;
        RewriteRuleSubtreeStream stream_functionSpecifier=new RewriteRuleSubtreeStream(adaptor,"rule functionSpecifier");
        RewriteRuleSubtreeStream stream_typeSpecifier=new RewriteRuleSubtreeStream(adaptor,"rule typeSpecifier");
        RewriteRuleSubtreeStream stream_attributes=new RewriteRuleSubtreeStream(adaptor,"rule attributes");
        RewriteRuleSubtreeStream stream_typeQualifier=new RewriteRuleSubtreeStream(adaptor,"rule typeQualifier");
        RewriteRuleSubtreeStream stream_storageClassSpecifier=new RewriteRuleSubtreeStream(adaptor,"rule storageClassSpecifier");
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 9) ) { return retval; }
            // GNUCa.g:220:2: ( (sc+= storageClassSpecifier | typeSpecifier | typeQualifier | sc+= functionSpecifier | attributes )+ -> ^( DECLARATION_SPECIFIERS ^( XTYPE_SPECIFIER ( typeSpecifier )* ) ^( XTYPE_QUALIFIER ( typeQualifier )* ) ^( XSTORAGE_CLASS ( $sc)* ) ) )
            // GNUCa.g:220:4: (sc+= storageClassSpecifier | typeSpecifier | typeQualifier | sc+= functionSpecifier | attributes )+
            {
            // GNUCa.g:220:4: (sc+= storageClassSpecifier | typeSpecifier | typeQualifier | sc+= functionSpecifier | attributes )+
            int cnt11=0;
            loop11:
            do {
                int alt11=6;
                switch ( input.LA(1) ) {
                case IDENTIFIER:
                    {
                    int LA11_2 = input.LA(2);

                    if ( ((synpred21()&&isTypeName(input.LT(1).getText()))) ) {
                        alt11=2;
                    }


                    }
                    break;
                case 135:
                case 136:
                    {
                    int LA11_6 = input.LA(2);

                    if ( (LA11_6==83) ) {
                        int LA11_94 = input.LA(3);

                        if ( (synpred24()) ) {
                            alt11=5;
                        }


                    }


                    }
                    break;
                case 88:
                case 89:
                case 90:
                case 91:
                case 92:
                    {
                    alt11=1;
                    }
                    break;
                case 93:
                case 94:
                case 95:
                case 96:
                case 97:
                case 98:
                case 99:
                case 100:
                case 101:
                case 102:
                case 103:
                case 104:
                case 105:
                case 106:
                case 107:
                case 108:
                case 111:
                case 112:
                case 114:
                case 131:
                case 132:
                case 133:
                    {
                    alt11=2;
                    }
                    break;
                case 115:
                case 116:
                case 117:
                case 118:
                case 119:
                case 120:
                case 121:
                case 122:
                case 123:
                    {
                    alt11=3;
                    }
                    break;
                case 124:
                case 125:
                case 126:
                    {
                    alt11=4;
                    }
                    break;

                }

                switch (alt11) {
            	case 1 :
            	    // GNUCa.g:220:6: sc+= storageClassSpecifier
            	    {
            	    pushFollow(FOLLOW_storageClassSpecifier_in_declarationSpecifiers658);
            	    sc=storageClassSpecifier();
            	    _fsp--;
            	    if (failed) return retval;
            	    if ( backtracking==0 ) stream_storageClassSpecifier.add(sc.getTree());
            	    if (list_sc==null) list_sc=new ArrayList();
            	    list_sc.add(sc);


            	    }
            	    break;
            	case 2 :
            	    // GNUCa.g:221:5: typeSpecifier
            	    {
            	    pushFollow(FOLLOW_typeSpecifier_in_declarationSpecifiers664);
            	    typeSpecifier30=typeSpecifier();
            	    _fsp--;
            	    if (failed) return retval;
            	    if ( backtracking==0 ) stream_typeSpecifier.add(typeSpecifier30.getTree());

            	    }
            	    break;
            	case 3 :
            	    // GNUCa.g:222:5: typeQualifier
            	    {
            	    pushFollow(FOLLOW_typeQualifier_in_declarationSpecifiers670);
            	    typeQualifier31=typeQualifier();
            	    _fsp--;
            	    if (failed) return retval;
            	    if ( backtracking==0 ) stream_typeQualifier.add(typeQualifier31.getTree());

            	    }
            	    break;
            	case 4 :
            	    // GNUCa.g:223:5: sc+= functionSpecifier
            	    {
            	    pushFollow(FOLLOW_functionSpecifier_in_declarationSpecifiers678);
            	    sc=functionSpecifier();
            	    _fsp--;
            	    if (failed) return retval;
            	    if ( backtracking==0 ) stream_functionSpecifier.add(sc.getTree());
            	    if (list_sc==null) list_sc=new ArrayList();
            	    list_sc.add(sc);


            	    }
            	    break;
            	case 5 :
            	    // GNUCa.g:224:5: attributes
            	    {
            	    pushFollow(FOLLOW_attributes_in_declarationSpecifiers685);
            	    attributes32=attributes();
            	    _fsp--;
            	    if (failed) return retval;
            	    if ( backtracking==0 ) stream_attributes.add(attributes32.getTree());

            	    }
            	    break;

            	default :
            	    if ( cnt11 >= 1 ) break loop11;
            	    if (backtracking>0) {failed=true; return retval;}
                        EarlyExitException eee =
                            new EarlyExitException(11, input);
                        throw eee;
                }
                cnt11++;
            } while (true);


            // AST REWRITE
            // elements: typeQualifier, sc, typeSpecifier
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: sc
            if ( backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_sc=new RewriteRuleSubtreeStream(adaptor,"token sc",list_sc);
            root_0 = (Object)adaptor.nil();
            // 224:18: -> ^( DECLARATION_SPECIFIERS ^( XTYPE_SPECIFIER ( typeSpecifier )* ) ^( XTYPE_QUALIFIER ( typeQualifier )* ) ^( XSTORAGE_CLASS ( $sc)* ) )
            {
                // GNUCa.g:224:21: ^( DECLARATION_SPECIFIERS ^( XTYPE_SPECIFIER ( typeSpecifier )* ) ^( XTYPE_QUALIFIER ( typeQualifier )* ) ^( XSTORAGE_CLASS ( $sc)* ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(adaptor.create(DECLARATION_SPECIFIERS, "DECLARATION_SPECIFIERS"), root_1);

                // GNUCa.g:224:46: ^( XTYPE_SPECIFIER ( typeSpecifier )* )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot(adaptor.create(XTYPE_SPECIFIER, "XTYPE_SPECIFIER"), root_2);

                // GNUCa.g:224:64: ( typeSpecifier )*
                while ( stream_typeSpecifier.hasNext() ) {
                    adaptor.addChild(root_2, stream_typeSpecifier.next());

                }
                stream_typeSpecifier.reset();

                adaptor.addChild(root_1, root_2);
                }
                // GNUCa.g:224:80: ^( XTYPE_QUALIFIER ( typeQualifier )* )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot(adaptor.create(XTYPE_QUALIFIER, "XTYPE_QUALIFIER"), root_2);

                // GNUCa.g:224:98: ( typeQualifier )*
                while ( stream_typeQualifier.hasNext() ) {
                    adaptor.addChild(root_2, stream_typeQualifier.next());

                }
                stream_typeQualifier.reset();

                adaptor.addChild(root_1, root_2);
                }
                // GNUCa.g:224:114: ^( XSTORAGE_CLASS ( $sc)* )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot(adaptor.create(XSTORAGE_CLASS, "XSTORAGE_CLASS"), root_2);

                // GNUCa.g:224:131: ( $sc)*
                while ( stream_sc.hasNext() ) {
                    adaptor.addChild(root_2, ((ParserRuleReturnScope)stream_sc.next()).getTree());

                }
                stream_sc.reset();

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            }

            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 9, declarationSpecifiers_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end declarationSpecifiers

    public static class initDeclaratorList_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start initDeclaratorList
    // GNUCa.g:227:1: initDeclaratorList : initDeclarator ( ',' initDeclarator )* -> ( initDeclarator )+ ;
    public final initDeclaratorList_return initDeclaratorList() throws RecognitionException {
        initDeclaratorList_return retval = new initDeclaratorList_return();
        retval.start = input.LT(1);
        int initDeclaratorList_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal34=null;
        initDeclarator_return initDeclarator33 = null;

        initDeclarator_return initDeclarator35 = null;


        Object char_literal34_tree=null;
        RewriteRuleTokenStream stream_86=new RewriteRuleTokenStream(adaptor,"token 86");
        RewriteRuleSubtreeStream stream_initDeclarator=new RewriteRuleSubtreeStream(adaptor,"rule initDeclarator");
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 10) ) { return retval; }
            // GNUCa.g:228:2: ( initDeclarator ( ',' initDeclarator )* -> ( initDeclarator )+ )
            // GNUCa.g:228:4: initDeclarator ( ',' initDeclarator )*
            {
            pushFollow(FOLLOW_initDeclarator_in_initDeclaratorList726);
            initDeclarator33=initDeclarator();
            _fsp--;
            if (failed) return retval;
            if ( backtracking==0 ) stream_initDeclarator.add(initDeclarator33.getTree());
            // GNUCa.g:228:19: ( ',' initDeclarator )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==86) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // GNUCa.g:228:21: ',' initDeclarator
            	    {
            	    char_literal34=(Token)input.LT(1);
            	    match(input,86,FOLLOW_86_in_initDeclaratorList730); if (failed) return retval;
            	    if ( backtracking==0 ) stream_86.add(char_literal34);

            	    pushFollow(FOLLOW_initDeclarator_in_initDeclaratorList732);
            	    initDeclarator35=initDeclarator();
            	    _fsp--;
            	    if (failed) return retval;
            	    if ( backtracking==0 ) stream_initDeclarator.add(initDeclarator35.getTree());

            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);


            // AST REWRITE
            // elements: initDeclarator
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 228:42: -> ( initDeclarator )+
            {
                if ( !(stream_initDeclarator.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_initDeclarator.hasNext() ) {
                    adaptor.addChild(root_0, stream_initDeclarator.next());

                }
                stream_initDeclarator.reset();

            }

            }

            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 10, initDeclaratorList_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end initDeclaratorList

    public static class initDeclarator_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start initDeclarator
    // GNUCa.g:231:1: initDeclarator : declarator ( simpleAsmExpr )? ( attributes )? ( '=' initializer )? -> ^( INIT_DECLARATOR declarator ( initializer )? ) ;
    public final initDeclarator_return initDeclarator() throws RecognitionException {
        initDeclarator_return retval = new initDeclarator_return();
        retval.start = input.LT(1);
        int initDeclarator_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal39=null;
        declarator_return declarator36 = null;

        simpleAsmExpr_return simpleAsmExpr37 = null;

        attributes_return attributes38 = null;

        initializer_return initializer40 = null;


        Object char_literal39_tree=null;
        RewriteRuleTokenStream stream_87=new RewriteRuleTokenStream(adaptor,"token 87");
        RewriteRuleSubtreeStream stream_simpleAsmExpr=new RewriteRuleSubtreeStream(adaptor,"rule simpleAsmExpr");
        RewriteRuleSubtreeStream stream_declarator=new RewriteRuleSubtreeStream(adaptor,"rule declarator");
        RewriteRuleSubtreeStream stream_attributes=new RewriteRuleSubtreeStream(adaptor,"rule attributes");
        RewriteRuleSubtreeStream stream_initializer=new RewriteRuleSubtreeStream(adaptor,"rule initializer");
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 11) ) { return retval; }
            // GNUCa.g:232:2: ( declarator ( simpleAsmExpr )? ( attributes )? ( '=' initializer )? -> ^( INIT_DECLARATOR declarator ( initializer )? ) )
            // GNUCa.g:232:4: declarator ( simpleAsmExpr )? ( attributes )? ( '=' initializer )?
            {
            pushFollow(FOLLOW_declarator_in_initDeclarator753);
            declarator36=declarator();
            _fsp--;
            if (failed) return retval;
            if ( backtracking==0 ) stream_declarator.add(declarator36.getTree());
            // GNUCa.g:232:15: ( simpleAsmExpr )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( ((LA13_0>=80 && LA13_0<=82)) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // GNUCa.g:0:0: simpleAsmExpr
                    {
                    pushFollow(FOLLOW_simpleAsmExpr_in_initDeclarator755);
                    simpleAsmExpr37=simpleAsmExpr();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_simpleAsmExpr.add(simpleAsmExpr37.getTree());

                    }
                    break;

            }

            // GNUCa.g:232:30: ( attributes )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( ((LA14_0>=135 && LA14_0<=136)) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // GNUCa.g:0:0: attributes
                    {
                    pushFollow(FOLLOW_attributes_in_initDeclarator758);
                    attributes38=attributes();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_attributes.add(attributes38.getTree());

                    }
                    break;

            }

            // GNUCa.g:232:42: ( '=' initializer )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==87) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // GNUCa.g:232:44: '=' initializer
                    {
                    char_literal39=(Token)input.LT(1);
                    match(input,87,FOLLOW_87_in_initDeclarator763); if (failed) return retval;
                    if ( backtracking==0 ) stream_87.add(char_literal39);

                    pushFollow(FOLLOW_initializer_in_initDeclarator765);
                    initializer40=initializer();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_initializer.add(initializer40.getTree());

                    }
                    break;

            }


            // AST REWRITE
            // elements: declarator, initializer
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 232:64: -> ^( INIT_DECLARATOR declarator ( initializer )? )
            {
                // GNUCa.g:232:67: ^( INIT_DECLARATOR declarator ( initializer )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(adaptor.create(INIT_DECLARATOR, "INIT_DECLARATOR"), root_1);

                adaptor.addChild(root_1, stream_declarator.next());
                // GNUCa.g:232:96: ( initializer )?
                if ( stream_initializer.hasNext() ) {
                    adaptor.addChild(root_1, stream_initializer.next());

                }
                stream_initializer.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            }

            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 11, initDeclarator_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end initDeclarator

    public static class storageClassSpecifier_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start storageClassSpecifier
    // GNUCa.g:235:1: storageClassSpecifier : ( 'extern' | 'static' | 'auto' | 'register' | '__thread' );
    public final storageClassSpecifier_return storageClassSpecifier() throws RecognitionException {
        storageClassSpecifier_return retval = new storageClassSpecifier_return();
        retval.start = input.LT(1);
        int storageClassSpecifier_StartIndex = input.index();
        Object root_0 = null;

        Token set41=null;

        Object set41_tree=null;

        try {
            if ( backtracking>0 && alreadyParsedRule(input, 12) ) { return retval; }
            // GNUCa.g:237:2: ( 'extern' | 'static' | 'auto' | 'register' | '__thread' )
            // GNUCa.g:
            {
            root_0 = (Object)adaptor.nil();

            set41=(Token)input.LT(1);
            if ( (input.LA(1)>=88 && input.LA(1)<=92) ) {
                input.consume();
                if ( backtracking==0 ) adaptor.addChild(root_0, adaptor.create(set41));
                errorRecovery=false;failed=false;
            }
            else {
                if (backtracking>0) {failed=true; return retval;}
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recoverFromMismatchedSet(input,mse,FOLLOW_set_in_storageClassSpecifier0);    throw mse;
            }


            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 12, storageClassSpecifier_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end storageClassSpecifier

    public static class typeSpecifier_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start typeSpecifier
    // GNUCa.g:244:1: typeSpecifier : ( 'void' -> ^( BASETYPE 'void' ) | 'char' -> ^( BASETYPE 'char' ) | 'short' -> ^( BASETYPE 'short' ) | 'int' -> ^( BASETYPE 'int' ) | 'long' -> ^( BASETYPE 'long' ) | 'float' -> ^( BASETYPE 'float' ) | 'double' -> ^( BASETYPE 'double' ) | 'signed' -> ^( BASETYPE SIGNED ) | '__signed' -> ^( BASETYPE SIGNED ) | '__signed__' -> ^( BASETYPE SIGNED ) | 'unsigned' -> ^( BASETYPE 'unsigned' ) | '_Bool' -> ^( BASETYPE '_Bool' ) | '_Complex' -> ^( BASETYPE '_Complex' ) | '__complex' -> ^( BASETYPE XID ) | '__complex__' -> ^( BASETYPE XID ) | '_Imaginary' -> ^( BASETYPE '_Imaginary' ) | structOrUnionSpecifier | enumSpecifier | typedefName | typeofSpecifier );
    public final typeSpecifier_return typeSpecifier() throws RecognitionException {
        typeSpecifier_return retval = new typeSpecifier_return();
        retval.start = input.LT(1);
        int typeSpecifier_StartIndex = input.index();
        Object root_0 = null;

        Token string_literal42=null;
        Token string_literal43=null;
        Token string_literal44=null;
        Token string_literal45=null;
        Token string_literal46=null;
        Token string_literal47=null;
        Token string_literal48=null;
        Token string_literal49=null;
        Token string_literal50=null;
        Token string_literal51=null;
        Token string_literal52=null;
        Token string_literal53=null;
        Token string_literal54=null;
        Token string_literal55=null;
        Token string_literal56=null;
        Token string_literal57=null;
        structOrUnionSpecifier_return structOrUnionSpecifier58 = null;

        enumSpecifier_return enumSpecifier59 = null;

        typedefName_return typedefName60 = null;

        typeofSpecifier_return typeofSpecifier61 = null;


        Object string_literal42_tree=null;
        Object string_literal43_tree=null;
        Object string_literal44_tree=null;
        Object string_literal45_tree=null;
        Object string_literal46_tree=null;
        Object string_literal47_tree=null;
        Object string_literal48_tree=null;
        Object string_literal49_tree=null;
        Object string_literal50_tree=null;
        Object string_literal51_tree=null;
        Object string_literal52_tree=null;
        Object string_literal53_tree=null;
        Object string_literal54_tree=null;
        Object string_literal55_tree=null;
        Object string_literal56_tree=null;
        Object string_literal57_tree=null;
        RewriteRuleTokenStream stream_98=new RewriteRuleTokenStream(adaptor,"token 98");
        RewriteRuleTokenStream stream_97=new RewriteRuleTokenStream(adaptor,"token 97");
        RewriteRuleTokenStream stream_96=new RewriteRuleTokenStream(adaptor,"token 96");
        RewriteRuleTokenStream stream_95=new RewriteRuleTokenStream(adaptor,"token 95");
        RewriteRuleTokenStream stream_94=new RewriteRuleTokenStream(adaptor,"token 94");
        RewriteRuleTokenStream stream_93=new RewriteRuleTokenStream(adaptor,"token 93");
        RewriteRuleTokenStream stream_108=new RewriteRuleTokenStream(adaptor,"token 108");
        RewriteRuleTokenStream stream_107=new RewriteRuleTokenStream(adaptor,"token 107");
        RewriteRuleTokenStream stream_106=new RewriteRuleTokenStream(adaptor,"token 106");
        RewriteRuleTokenStream stream_105=new RewriteRuleTokenStream(adaptor,"token 105");
        RewriteRuleTokenStream stream_104=new RewriteRuleTokenStream(adaptor,"token 104");
        RewriteRuleTokenStream stream_103=new RewriteRuleTokenStream(adaptor,"token 103");
        RewriteRuleTokenStream stream_99=new RewriteRuleTokenStream(adaptor,"token 99");
        RewriteRuleTokenStream stream_102=new RewriteRuleTokenStream(adaptor,"token 102");
        RewriteRuleTokenStream stream_101=new RewriteRuleTokenStream(adaptor,"token 101");
        RewriteRuleTokenStream stream_100=new RewriteRuleTokenStream(adaptor,"token 100");

        try {
            if ( backtracking>0 && alreadyParsedRule(input, 13) ) { return retval; }
            // GNUCa.g:245:9: ( 'void' -> ^( BASETYPE 'void' ) | 'char' -> ^( BASETYPE 'char' ) | 'short' -> ^( BASETYPE 'short' ) | 'int' -> ^( BASETYPE 'int' ) | 'long' -> ^( BASETYPE 'long' ) | 'float' -> ^( BASETYPE 'float' ) | 'double' -> ^( BASETYPE 'double' ) | 'signed' -> ^( BASETYPE SIGNED ) | '__signed' -> ^( BASETYPE SIGNED ) | '__signed__' -> ^( BASETYPE SIGNED ) | 'unsigned' -> ^( BASETYPE 'unsigned' ) | '_Bool' -> ^( BASETYPE '_Bool' ) | '_Complex' -> ^( BASETYPE '_Complex' ) | '__complex' -> ^( BASETYPE XID ) | '__complex__' -> ^( BASETYPE XID ) | '_Imaginary' -> ^( BASETYPE '_Imaginary' ) | structOrUnionSpecifier | enumSpecifier | typedefName | typeofSpecifier )
            int alt16=20;
            switch ( input.LA(1) ) {
            case 93:
                {
                alt16=1;
                }
                break;
            case 94:
                {
                alt16=2;
                }
                break;
            case 95:
                {
                alt16=3;
                }
                break;
            case 96:
                {
                alt16=4;
                }
                break;
            case 97:
                {
                alt16=5;
                }
                break;
            case 98:
                {
                alt16=6;
                }
                break;
            case 99:
                {
                alt16=7;
                }
                break;
            case 100:
                {
                alt16=8;
                }
                break;
            case 101:
                {
                alt16=9;
                }
                break;
            case 102:
                {
                alt16=10;
                }
                break;
            case 103:
                {
                alt16=11;
                }
                break;
            case 104:
                {
                alt16=12;
                }
                break;
            case 105:
                {
                alt16=13;
                }
                break;
            case 106:
                {
                alt16=14;
                }
                break;
            case 107:
                {
                alt16=15;
                }
                break;
            case 108:
                {
                alt16=16;
                }
                break;
            case 111:
            case 112:
                {
                alt16=17;
                }
                break;
            case 114:
                {
                alt16=18;
                }
                break;
            case IDENTIFIER:
                {
                alt16=19;
                }
                break;
            case 131:
            case 132:
            case 133:
                {
                alt16=20;
                }
                break;
            default:
                if (backtracking>0) {failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("244:1: typeSpecifier : ( 'void' -> ^( BASETYPE 'void' ) | 'char' -> ^( BASETYPE 'char' ) | 'short' -> ^( BASETYPE 'short' ) | 'int' -> ^( BASETYPE 'int' ) | 'long' -> ^( BASETYPE 'long' ) | 'float' -> ^( BASETYPE 'float' ) | 'double' -> ^( BASETYPE 'double' ) | 'signed' -> ^( BASETYPE SIGNED ) | '__signed' -> ^( BASETYPE SIGNED ) | '__signed__' -> ^( BASETYPE SIGNED ) | 'unsigned' -> ^( BASETYPE 'unsigned' ) | '_Bool' -> ^( BASETYPE '_Bool' ) | '_Complex' -> ^( BASETYPE '_Complex' ) | '__complex' -> ^( BASETYPE XID ) | '__complex__' -> ^( BASETYPE XID ) | '_Imaginary' -> ^( BASETYPE '_Imaginary' ) | structOrUnionSpecifier | enumSpecifier | typedefName | typeofSpecifier );", 16, 0, input);

                throw nvae;
            }

            switch (alt16) {
                case 1 :
                    // GNUCa.g:245:17: 'void'
                    {
                    string_literal42=(Token)input.LT(1);
                    match(input,93,FOLLOW_93_in_typeSpecifier843); if (failed) return retval;
                    if ( backtracking==0 ) stream_93.add(string_literal42);


                    // AST REWRITE
                    // elements: 93
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 245:26: -> ^( BASETYPE 'void' )
                    {
                        // GNUCa.g:245:29: ^( BASETYPE 'void' )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(adaptor.create(BASETYPE, "BASETYPE"), root_1);

                        adaptor.addChild(root_1, stream_93.next());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;
                case 2 :
                    // GNUCa.g:246:17: 'char'
                    {
                    string_literal43=(Token)input.LT(1);
                    match(input,94,FOLLOW_94_in_typeSpecifier871); if (failed) return retval;
                    if ( backtracking==0 ) stream_94.add(string_literal43);


                    // AST REWRITE
                    // elements: 94
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 246:26: -> ^( BASETYPE 'char' )
                    {
                        // GNUCa.g:246:29: ^( BASETYPE 'char' )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(adaptor.create(BASETYPE, "BASETYPE"), root_1);

                        adaptor.addChild(root_1, stream_94.next());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;
                case 3 :
                    // GNUCa.g:247:17: 'short'
                    {
                    string_literal44=(Token)input.LT(1);
                    match(input,95,FOLLOW_95_in_typeSpecifier902); if (failed) return retval;
                    if ( backtracking==0 ) stream_95.add(string_literal44);


                    // AST REWRITE
                    // elements: 95
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 247:27: -> ^( BASETYPE 'short' )
                    {
                        // GNUCa.g:247:30: ^( BASETYPE 'short' )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(adaptor.create(BASETYPE, "BASETYPE"), root_1);

                        adaptor.addChild(root_1, stream_95.next());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;
                case 4 :
                    // GNUCa.g:248:17: 'int'
                    {
                    string_literal45=(Token)input.LT(1);
                    match(input,96,FOLLOW_96_in_typeSpecifier930); if (failed) return retval;
                    if ( backtracking==0 ) stream_96.add(string_literal45);


                    // AST REWRITE
                    // elements: 96
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 248:25: -> ^( BASETYPE 'int' )
                    {
                        // GNUCa.g:248:28: ^( BASETYPE 'int' )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(adaptor.create(BASETYPE, "BASETYPE"), root_1);

                        adaptor.addChild(root_1, stream_96.next());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;
                case 5 :
                    // GNUCa.g:249:17: 'long'
                    {
                    string_literal46=(Token)input.LT(1);
                    match(input,97,FOLLOW_97_in_typeSpecifier958); if (failed) return retval;
                    if ( backtracking==0 ) stream_97.add(string_literal46);


                    // AST REWRITE
                    // elements: 97
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 249:26: -> ^( BASETYPE 'long' )
                    {
                        // GNUCa.g:249:29: ^( BASETYPE 'long' )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(adaptor.create(BASETYPE, "BASETYPE"), root_1);

                        adaptor.addChild(root_1, stream_97.next());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;
                case 6 :
                    // GNUCa.g:250:17: 'float'
                    {
                    string_literal47=(Token)input.LT(1);
                    match(input,98,FOLLOW_98_in_typeSpecifier986); if (failed) return retval;
                    if ( backtracking==0 ) stream_98.add(string_literal47);


                    // AST REWRITE
                    // elements: 98
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 250:27: -> ^( BASETYPE 'float' )
                    {
                        // GNUCa.g:250:30: ^( BASETYPE 'float' )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(adaptor.create(BASETYPE, "BASETYPE"), root_1);

                        adaptor.addChild(root_1, stream_98.next());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;
                case 7 :
                    // GNUCa.g:251:17: 'double'
                    {
                    string_literal48=(Token)input.LT(1);
                    match(input,99,FOLLOW_99_in_typeSpecifier1014); if (failed) return retval;
                    if ( backtracking==0 ) stream_99.add(string_literal48);


                    // AST REWRITE
                    // elements: 99
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 251:27: -> ^( BASETYPE 'double' )
                    {
                        // GNUCa.g:251:30: ^( BASETYPE 'double' )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(adaptor.create(BASETYPE, "BASETYPE"), root_1);

                        adaptor.addChild(root_1, stream_99.next());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;
                case 8 :
                    // GNUCa.g:252:17: 'signed'
                    {
                    string_literal49=(Token)input.LT(1);
                    match(input,100,FOLLOW_100_in_typeSpecifier1041); if (failed) return retval;
                    if ( backtracking==0 ) stream_100.add(string_literal49);


                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 252:27: -> ^( BASETYPE SIGNED )
                    {
                        // GNUCa.g:252:30: ^( BASETYPE SIGNED )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(adaptor.create(BASETYPE, "BASETYPE"), root_1);

                        adaptor.addChild(root_1, adaptor.create(SIGNED, "SIGNED"));

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;
                case 9 :
                    // GNUCa.g:253:17: '__signed'
                    {
                    string_literal50=(Token)input.LT(1);
                    match(input,101,FOLLOW_101_in_typeSpecifier1071); if (failed) return retval;
                    if ( backtracking==0 ) stream_101.add(string_literal50);


                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 253:29: -> ^( BASETYPE SIGNED )
                    {
                        // GNUCa.g:253:32: ^( BASETYPE SIGNED )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(adaptor.create(BASETYPE, "BASETYPE"), root_1);

                        adaptor.addChild(root_1, adaptor.create(SIGNED, "SIGNED"));

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;
                case 10 :
                    // GNUCa.g:254:17: '__signed__'
                    {
                    string_literal51=(Token)input.LT(1);
                    match(input,102,FOLLOW_102_in_typeSpecifier1101); if (failed) return retval;
                    if ( backtracking==0 ) stream_102.add(string_literal51);


                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 254:31: -> ^( BASETYPE SIGNED )
                    {
                        // GNUCa.g:254:34: ^( BASETYPE SIGNED )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(adaptor.create(BASETYPE, "BASETYPE"), root_1);

                        adaptor.addChild(root_1, adaptor.create(SIGNED, "SIGNED"));

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;
                case 11 :
                    // GNUCa.g:255:17: 'unsigned'
                    {
                    string_literal52=(Token)input.LT(1);
                    match(input,103,FOLLOW_103_in_typeSpecifier1131); if (failed) return retval;
                    if ( backtracking==0 ) stream_103.add(string_literal52);


                    // AST REWRITE
                    // elements: 103
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 255:29: -> ^( BASETYPE 'unsigned' )
                    {
                        // GNUCa.g:255:32: ^( BASETYPE 'unsigned' )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(adaptor.create(BASETYPE, "BASETYPE"), root_1);

                        adaptor.addChild(root_1, stream_103.next());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;
                case 12 :
                    // GNUCa.g:256:11: '_Bool'
                    {
                    string_literal53=(Token)input.LT(1);
                    match(input,104,FOLLOW_104_in_typeSpecifier1152); if (failed) return retval;
                    if ( backtracking==0 ) stream_104.add(string_literal53);


                    // AST REWRITE
                    // elements: 104
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 256:21: -> ^( BASETYPE '_Bool' )
                    {
                        // GNUCa.g:256:24: ^( BASETYPE '_Bool' )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(adaptor.create(BASETYPE, "BASETYPE"), root_1);

                        adaptor.addChild(root_1, stream_104.next());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;
                case 13 :
                    // GNUCa.g:257:11: '_Complex'
                    {
                    string_literal54=(Token)input.LT(1);
                    match(input,105,FOLLOW_105_in_typeSpecifier1174); if (failed) return retval;
                    if ( backtracking==0 ) stream_105.add(string_literal54);


                    // AST REWRITE
                    // elements: 105
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 257:23: -> ^( BASETYPE '_Complex' )
                    {
                        // GNUCa.g:257:26: ^( BASETYPE '_Complex' )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(adaptor.create(BASETYPE, "BASETYPE"), root_1);

                        adaptor.addChild(root_1, stream_105.next());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;
                case 14 :
                    // GNUCa.g:258:11: '__complex'
                    {
                    string_literal55=(Token)input.LT(1);
                    match(input,106,FOLLOW_106_in_typeSpecifier1195); if (failed) return retval;
                    if ( backtracking==0 ) stream_106.add(string_literal55);


                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 258:24: -> ^( BASETYPE XID )
                    {
                        // GNUCa.g:258:27: ^( BASETYPE XID )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(adaptor.create(BASETYPE, "BASETYPE"), root_1);

                        adaptor.addChild(root_1, adaptor.create(XID, "XID"));

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;
                case 15 :
                    // GNUCa.g:259:11: '__complex__'
                    {
                    string_literal56=(Token)input.LT(1);
                    match(input,107,FOLLOW_107_in_typeSpecifier1218); if (failed) return retval;
                    if ( backtracking==0 ) stream_107.add(string_literal56);


                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 259:26: -> ^( BASETYPE XID )
                    {
                        // GNUCa.g:259:29: ^( BASETYPE XID )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(adaptor.create(BASETYPE, "BASETYPE"), root_1);

                        adaptor.addChild(root_1, adaptor.create(XID, "XID"));

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;
                case 16 :
                    // GNUCa.g:260:11: '_Imaginary'
                    {
                    string_literal57=(Token)input.LT(1);
                    match(input,108,FOLLOW_108_in_typeSpecifier1241); if (failed) return retval;
                    if ( backtracking==0 ) stream_108.add(string_literal57);


                    // AST REWRITE
                    // elements: 108
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 260:25: -> ^( BASETYPE '_Imaginary' )
                    {
                        // GNUCa.g:260:28: ^( BASETYPE '_Imaginary' )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(adaptor.create(BASETYPE, "BASETYPE"), root_1);

                        adaptor.addChild(root_1, stream_108.next());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;
                case 17 :
                    // GNUCa.g:261:17: structOrUnionSpecifier
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_structOrUnionSpecifier_in_typeSpecifier1270);
                    structOrUnionSpecifier58=structOrUnionSpecifier();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) adaptor.addChild(root_0, structOrUnionSpecifier58.getTree());

                    }
                    break;
                case 18 :
                    // GNUCa.g:262:17: enumSpecifier
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_enumSpecifier_in_typeSpecifier1288);
                    enumSpecifier59=enumSpecifier();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) adaptor.addChild(root_0, enumSpecifier59.getTree());

                    }
                    break;
                case 19 :
                    // GNUCa.g:263:17: typedefName
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_typedefName_in_typeSpecifier1306);
                    typedefName60=typedefName();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) adaptor.addChild(root_0, typedefName60.getTree());

                    }
                    break;
                case 20 :
                    // GNUCa.g:264:11: typeofSpecifier
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_typeofSpecifier_in_typeSpecifier1318);
                    typeofSpecifier61=typeofSpecifier();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) adaptor.addChild(root_0, typeofSpecifier61.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 13, typeSpecifier_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end typeSpecifier

    public static class structOrUnionSpecifier_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start structOrUnionSpecifier
    // GNUCa.g:267:1: structOrUnionSpecifier options {k=3; } : ( structOrUnion ( attributes )? ( IDENTIFIER )? '{' ( structDeclaration )* '}' ( attributes )? -> ^( structOrUnion ^( XID ( IDENTIFIER )? ) ( structDeclaration )* ) | structOrUnion ( attributes )? IDENTIFIER -> ^( structOrUnion ^( XID IDENTIFIER ) ) );
    public final structOrUnionSpecifier_return structOrUnionSpecifier() throws RecognitionException {
        Symbols_stack.push(new Symbols_scope());

        structOrUnionSpecifier_return retval = new structOrUnionSpecifier_return();
        retval.start = input.LT(1);
        int structOrUnionSpecifier_StartIndex = input.index();
        Object root_0 = null;

        Token IDENTIFIER64=null;
        Token char_literal65=null;
        Token char_literal67=null;
        Token IDENTIFIER71=null;
        structOrUnion_return structOrUnion62 = null;

        attributes_return attributes63 = null;

        structDeclaration_return structDeclaration66 = null;

        attributes_return attributes68 = null;

        structOrUnion_return structOrUnion69 = null;

        attributes_return attributes70 = null;


        Object IDENTIFIER64_tree=null;
        Object char_literal65_tree=null;
        Object char_literal67_tree=null;
        Object IDENTIFIER71_tree=null;
        RewriteRuleTokenStream stream_109=new RewriteRuleTokenStream(adaptor,"token 109");
        RewriteRuleTokenStream stream_110=new RewriteRuleTokenStream(adaptor,"token 110");
        RewriteRuleTokenStream stream_IDENTIFIER=new RewriteRuleTokenStream(adaptor,"token IDENTIFIER");
        RewriteRuleSubtreeStream stream_attributes=new RewriteRuleSubtreeStream(adaptor,"rule attributes");
        RewriteRuleSubtreeStream stream_structOrUnion=new RewriteRuleSubtreeStream(adaptor,"rule structOrUnion");
        RewriteRuleSubtreeStream stream_structDeclaration=new RewriteRuleSubtreeStream(adaptor,"rule structDeclaration");
         ((Symbols_scope)Symbols_stack.peek()).types = new HashSet(); 
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 14) ) { return retval; }
            // GNUCa.g:271:2: ( structOrUnion ( attributes )? ( IDENTIFIER )? '{' ( structDeclaration )* '}' ( attributes )? -> ^( structOrUnion ^( XID ( IDENTIFIER )? ) ( structDeclaration )* ) | structOrUnion ( attributes )? IDENTIFIER -> ^( structOrUnion ^( XID IDENTIFIER ) ) )
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( ((LA22_0>=111 && LA22_0<=112)) ) {
                switch ( input.LA(2) ) {
                case 135:
                case 136:
                    {
                    int LA22_2 = input.LA(3);

                    if ( (LA22_2==83) ) {
                        int LA22_5 = input.LA(4);

                        if ( (synpred56()) ) {
                            alt22=1;
                        }
                        else if ( (true) ) {
                            alt22=2;
                        }
                        else {
                            if (backtracking>0) {failed=true; return retval;}
                            NoViableAltException nvae =
                                new NoViableAltException("267:1: structOrUnionSpecifier options {k=3; } : ( structOrUnion ( attributes )? ( IDENTIFIER )? '{' ( structDeclaration )* '}' ( attributes )? -> ^( structOrUnion ^( XID ( IDENTIFIER )? ) ( structDeclaration )* ) | structOrUnion ( attributes )? IDENTIFIER -> ^( structOrUnion ^( XID IDENTIFIER ) ) );", 22, 5, input);

                            throw nvae;
                        }
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("267:1: structOrUnionSpecifier options {k=3; } : ( structOrUnion ( attributes )? ( IDENTIFIER )? '{' ( structDeclaration )* '}' ( attributes )? -> ^( structOrUnion ^( XID ( IDENTIFIER )? ) ( structDeclaration )* ) | structOrUnion ( attributes )? IDENTIFIER -> ^( structOrUnion ^( XID IDENTIFIER ) ) );", 22, 2, input);

                        throw nvae;
                    }
                    }
                    break;
                case IDENTIFIER:
                    {
                    int LA22_3 = input.LA(3);

                    if ( (LA22_3==109) ) {
                        alt22=1;
                    }
                    else if ( (LA22_3==EOF||LA22_3==IDENTIFIER||LA22_3==79||(LA22_3>=83 && LA22_3<=84)||LA22_3==86||(LA22_3>=88 && LA22_3<=108)||(LA22_3>=111 && LA22_3<=127)||LA22_3==129||(LA22_3>=131 && LA22_3<=133)||(LA22_3>=135 && LA22_3<=136)) ) {
                        alt22=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("267:1: structOrUnionSpecifier options {k=3; } : ( structOrUnion ( attributes )? ( IDENTIFIER )? '{' ( structDeclaration )* '}' ( attributes )? -> ^( structOrUnion ^( XID ( IDENTIFIER )? ) ( structDeclaration )* ) | structOrUnion ( attributes )? IDENTIFIER -> ^( structOrUnion ^( XID IDENTIFIER ) ) );", 22, 3, input);

                        throw nvae;
                    }
                    }
                    break;
                case 109:
                    {
                    alt22=1;
                    }
                    break;
                default:
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("267:1: structOrUnionSpecifier options {k=3; } : ( structOrUnion ( attributes )? ( IDENTIFIER )? '{' ( structDeclaration )* '}' ( attributes )? -> ^( structOrUnion ^( XID ( IDENTIFIER )? ) ( structDeclaration )* ) | structOrUnion ( attributes )? IDENTIFIER -> ^( structOrUnion ^( XID IDENTIFIER ) ) );", 22, 1, input);

                    throw nvae;
                }

            }
            else {
                if (backtracking>0) {failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("267:1: structOrUnionSpecifier options {k=3; } : ( structOrUnion ( attributes )? ( IDENTIFIER )? '{' ( structDeclaration )* '}' ( attributes )? -> ^( structOrUnion ^( XID ( IDENTIFIER )? ) ( structDeclaration )* ) | structOrUnion ( attributes )? IDENTIFIER -> ^( structOrUnion ^( XID IDENTIFIER ) ) );", 22, 0, input);

                throw nvae;
            }
            switch (alt22) {
                case 1 :
                    // GNUCa.g:271:4: structOrUnion ( attributes )? ( IDENTIFIER )? '{' ( structDeclaration )* '}' ( attributes )?
                    {
                    pushFollow(FOLLOW_structOrUnion_in_structOrUnionSpecifier1355);
                    structOrUnion62=structOrUnion();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_structOrUnion.add(structOrUnion62.getTree());
                    // GNUCa.g:271:18: ( attributes )?
                    int alt17=2;
                    int LA17_0 = input.LA(1);

                    if ( ((LA17_0>=135 && LA17_0<=136)) ) {
                        alt17=1;
                    }
                    switch (alt17) {
                        case 1 :
                            // GNUCa.g:0:0: attributes
                            {
                            pushFollow(FOLLOW_attributes_in_structOrUnionSpecifier1357);
                            attributes63=attributes();
                            _fsp--;
                            if (failed) return retval;
                            if ( backtracking==0 ) stream_attributes.add(attributes63.getTree());

                            }
                            break;

                    }

                    // GNUCa.g:271:30: ( IDENTIFIER )?
                    int alt18=2;
                    int LA18_0 = input.LA(1);

                    if ( (LA18_0==IDENTIFIER) ) {
                        alt18=1;
                    }
                    switch (alt18) {
                        case 1 :
                            // GNUCa.g:0:0: IDENTIFIER
                            {
                            IDENTIFIER64=(Token)input.LT(1);
                            match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_structOrUnionSpecifier1360); if (failed) return retval;
                            if ( backtracking==0 ) stream_IDENTIFIER.add(IDENTIFIER64);


                            }
                            break;

                    }

                    char_literal65=(Token)input.LT(1);
                    match(input,109,FOLLOW_109_in_structOrUnionSpecifier1363); if (failed) return retval;
                    if ( backtracking==0 ) stream_109.add(char_literal65);

                    // GNUCa.g:271:46: ( structDeclaration )*
                    loop19:
                    do {
                        int alt19=2;
                        int LA19_0 = input.LA(1);

                        if ( (LA19_0==IDENTIFIER||LA19_0==79||(LA19_0>=93 && LA19_0<=108)||(LA19_0>=111 && LA19_0<=112)||(LA19_0>=114 && LA19_0<=123)||(LA19_0>=131 && LA19_0<=133)||(LA19_0>=135 && LA19_0<=136)) ) {
                            alt19=1;
                        }


                        switch (alt19) {
                    	case 1 :
                    	    // GNUCa.g:0:0: structDeclaration
                    	    {
                    	    pushFollow(FOLLOW_structDeclaration_in_structOrUnionSpecifier1365);
                    	    structDeclaration66=structDeclaration();
                    	    _fsp--;
                    	    if (failed) return retval;
                    	    if ( backtracking==0 ) stream_structDeclaration.add(structDeclaration66.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop19;
                        }
                    } while (true);

                    char_literal67=(Token)input.LT(1);
                    match(input,110,FOLLOW_110_in_structOrUnionSpecifier1368); if (failed) return retval;
                    if ( backtracking==0 ) stream_110.add(char_literal67);

                    // GNUCa.g:271:69: ( attributes )?
                    int alt20=2;
                    int LA20_0 = input.LA(1);

                    if ( ((LA20_0>=135 && LA20_0<=136)) ) {
                        int LA20_1 = input.LA(2);

                        if ( (LA20_1==83) ) {
                            int LA20_45 = input.LA(3);

                            if ( (synpred55()) ) {
                                alt20=1;
                            }
                        }
                    }
                    switch (alt20) {
                        case 1 :
                            // GNUCa.g:0:0: attributes
                            {
                            pushFollow(FOLLOW_attributes_in_structOrUnionSpecifier1370);
                            attributes68=attributes();
                            _fsp--;
                            if (failed) return retval;
                            if ( backtracking==0 ) stream_attributes.add(attributes68.getTree());

                            }
                            break;

                    }


                    // AST REWRITE
                    // elements: structDeclaration, structOrUnion, IDENTIFIER
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 271:81: -> ^( structOrUnion ^( XID ( IDENTIFIER )? ) ( structDeclaration )* )
                    {
                        // GNUCa.g:271:84: ^( structOrUnion ^( XID ( IDENTIFIER )? ) ( structDeclaration )* )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_structOrUnion.nextNode(), root_1);

                        // GNUCa.g:271:100: ^( XID ( IDENTIFIER )? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot(adaptor.create(XID, "XID"), root_2);

                        // GNUCa.g:271:106: ( IDENTIFIER )?
                        if ( stream_IDENTIFIER.hasNext() ) {
                            adaptor.addChild(root_2, stream_IDENTIFIER.next());

                        }
                        stream_IDENTIFIER.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // GNUCa.g:271:119: ( structDeclaration )*
                        while ( stream_structDeclaration.hasNext() ) {
                            adaptor.addChild(root_1, stream_structDeclaration.next());

                        }
                        stream_structDeclaration.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;
                case 2 :
                    // GNUCa.g:272:4: structOrUnion ( attributes )? IDENTIFIER
                    {
                    pushFollow(FOLLOW_structOrUnion_in_structOrUnionSpecifier1393);
                    structOrUnion69=structOrUnion();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_structOrUnion.add(structOrUnion69.getTree());
                    // GNUCa.g:272:18: ( attributes )?
                    int alt21=2;
                    int LA21_0 = input.LA(1);

                    if ( ((LA21_0>=135 && LA21_0<=136)) ) {
                        alt21=1;
                    }
                    switch (alt21) {
                        case 1 :
                            // GNUCa.g:0:0: attributes
                            {
                            pushFollow(FOLLOW_attributes_in_structOrUnionSpecifier1395);
                            attributes70=attributes();
                            _fsp--;
                            if (failed) return retval;
                            if ( backtracking==0 ) stream_attributes.add(attributes70.getTree());

                            }
                            break;

                    }

                    IDENTIFIER71=(Token)input.LT(1);
                    match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_structOrUnionSpecifier1398); if (failed) return retval;
                    if ( backtracking==0 ) stream_IDENTIFIER.add(IDENTIFIER71);


                    // AST REWRITE
                    // elements: structOrUnion, IDENTIFIER
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 272:41: -> ^( structOrUnion ^( XID IDENTIFIER ) )
                    {
                        // GNUCa.g:272:44: ^( structOrUnion ^( XID IDENTIFIER ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_structOrUnion.nextNode(), root_1);

                        // GNUCa.g:272:60: ^( XID IDENTIFIER )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot(adaptor.create(XID, "XID"), root_2);

                        adaptor.addChild(root_2, stream_IDENTIFIER.next());

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 14, structOrUnionSpecifier_StartIndex); }
            Symbols_stack.pop();

        }
        return retval;
    }
    // $ANTLR end structOrUnionSpecifier

    public static class structOrUnion_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start structOrUnion
    // GNUCa.g:275:1: structOrUnion : ( 'struct' | 'union' );
    public final structOrUnion_return structOrUnion() throws RecognitionException {
        structOrUnion_return retval = new structOrUnion_return();
        retval.start = input.LT(1);
        int structOrUnion_StartIndex = input.index();
        Object root_0 = null;

        Token set72=null;

        Object set72_tree=null;

        try {
            if ( backtracking>0 && alreadyParsedRule(input, 15) ) { return retval; }
            // GNUCa.g:276:2: ( 'struct' | 'union' )
            // GNUCa.g:
            {
            root_0 = (Object)adaptor.nil();

            set72=(Token)input.LT(1);
            if ( (input.LA(1)>=111 && input.LA(1)<=112) ) {
                input.consume();
                if ( backtracking==0 ) adaptor.addChild(root_0, adaptor.create(set72));
                errorRecovery=false;failed=false;
            }
            else {
                if (backtracking>0) {failed=true; return retval;}
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recoverFromMismatchedSet(input,mse,FOLLOW_set_in_structOrUnion0);    throw mse;
            }


            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 15, structOrUnion_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end structOrUnion

    public static class structDeclaration_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start structDeclaration
    // GNUCa.g:281:1: structDeclaration : ( ( specifierQualifier )+ ( structDeclaratorList )? ';' -> ^( STRUCT_DECLARATION ( specifierQualifier )+ ( structDeclaratorList )? ) | ';' );
    public final structDeclaration_return structDeclaration() throws RecognitionException {
        structDeclaration_return retval = new structDeclaration_return();
        retval.start = input.LT(1);
        int structDeclaration_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal75=null;
        Token char_literal76=null;
        specifierQualifier_return specifierQualifier73 = null;

        structDeclaratorList_return structDeclaratorList74 = null;


        Object char_literal75_tree=null;
        Object char_literal76_tree=null;
        RewriteRuleTokenStream stream_79=new RewriteRuleTokenStream(adaptor,"token 79");
        RewriteRuleSubtreeStream stream_specifierQualifier=new RewriteRuleSubtreeStream(adaptor,"rule specifierQualifier");
        RewriteRuleSubtreeStream stream_structDeclaratorList=new RewriteRuleSubtreeStream(adaptor,"rule structDeclaratorList");
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 16) ) { return retval; }
            // GNUCa.g:282:2: ( ( specifierQualifier )+ ( structDeclaratorList )? ';' -> ^( STRUCT_DECLARATION ( specifierQualifier )+ ( structDeclaratorList )? ) | ';' )
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==IDENTIFIER||(LA25_0>=93 && LA25_0<=108)||(LA25_0>=111 && LA25_0<=112)||(LA25_0>=114 && LA25_0<=123)||(LA25_0>=131 && LA25_0<=133)||(LA25_0>=135 && LA25_0<=136)) ) {
                alt25=1;
            }
            else if ( (LA25_0==79) ) {
                alt25=2;
            }
            else {
                if (backtracking>0) {failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("281:1: structDeclaration : ( ( specifierQualifier )+ ( structDeclaratorList )? ';' -> ^( STRUCT_DECLARATION ( specifierQualifier )+ ( structDeclaratorList )? ) | ';' );", 25, 0, input);

                throw nvae;
            }
            switch (alt25) {
                case 1 :
                    // GNUCa.g:282:4: ( specifierQualifier )+ ( structDeclaratorList )? ';'
                    {
                    // GNUCa.g:282:4: ( specifierQualifier )+
                    int cnt23=0;
                    loop23:
                    do {
                        int alt23=2;
                        int LA23_0 = input.LA(1);

                        if ( (LA23_0==IDENTIFIER) ) {
                            int LA23_2 = input.LA(2);

                            if ( ((synpred59()&&isTypeName(input.LT(1).getText()))) ) {
                                alt23=1;
                            }


                        }
                        else if ( ((LA23_0>=93 && LA23_0<=108)||(LA23_0>=111 && LA23_0<=112)||(LA23_0>=114 && LA23_0<=123)||(LA23_0>=131 && LA23_0<=133)||(LA23_0>=135 && LA23_0<=136)) ) {
                            alt23=1;
                        }


                        switch (alt23) {
                    	case 1 :
                    	    // GNUCa.g:0:0: specifierQualifier
                    	    {
                    	    pushFollow(FOLLOW_specifierQualifier_in_structDeclaration1443);
                    	    specifierQualifier73=specifierQualifier();
                    	    _fsp--;
                    	    if (failed) return retval;
                    	    if ( backtracking==0 ) stream_specifierQualifier.add(specifierQualifier73.getTree());

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt23 >= 1 ) break loop23;
                    	    if (backtracking>0) {failed=true; return retval;}
                                EarlyExitException eee =
                                    new EarlyExitException(23, input);
                                throw eee;
                        }
                        cnt23++;
                    } while (true);

                    // GNUCa.g:282:24: ( structDeclaratorList )?
                    int alt24=2;
                    int LA24_0 = input.LA(1);

                    if ( (LA24_0==IDENTIFIER||LA24_0==83||LA24_0==113||LA24_0==129) ) {
                        alt24=1;
                    }
                    switch (alt24) {
                        case 1 :
                            // GNUCa.g:0:0: structDeclaratorList
                            {
                            pushFollow(FOLLOW_structDeclaratorList_in_structDeclaration1446);
                            structDeclaratorList74=structDeclaratorList();
                            _fsp--;
                            if (failed) return retval;
                            if ( backtracking==0 ) stream_structDeclaratorList.add(structDeclaratorList74.getTree());

                            }
                            break;

                    }

                    char_literal75=(Token)input.LT(1);
                    match(input,79,FOLLOW_79_in_structDeclaration1449); if (failed) return retval;
                    if ( backtracking==0 ) stream_79.add(char_literal75);


                    // AST REWRITE
                    // elements: structDeclaratorList, specifierQualifier
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 282:50: -> ^( STRUCT_DECLARATION ( specifierQualifier )+ ( structDeclaratorList )? )
                    {
                        // GNUCa.g:282:53: ^( STRUCT_DECLARATION ( specifierQualifier )+ ( structDeclaratorList )? )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(adaptor.create(STRUCT_DECLARATION, "STRUCT_DECLARATION"), root_1);

                        if ( !(stream_specifierQualifier.hasNext()) ) {
                            throw new RewriteEarlyExitException();
                        }
                        while ( stream_specifierQualifier.hasNext() ) {
                            adaptor.addChild(root_1, stream_specifierQualifier.next());

                        }
                        stream_specifierQualifier.reset();
                        // GNUCa.g:282:94: ( structDeclaratorList )?
                        if ( stream_structDeclaratorList.hasNext() ) {
                            adaptor.addChild(root_1, stream_structDeclaratorList.next());

                        }
                        stream_structDeclaratorList.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;
                case 2 :
                    // GNUCa.g:283:4: ';'
                    {
                    root_0 = (Object)adaptor.nil();

                    char_literal76=(Token)input.LT(1);
                    match(input,79,FOLLOW_79_in_structDeclaration1466); if (failed) return retval;

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 16, structDeclaration_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end structDeclaration

    public static class specifierQualifier_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start specifierQualifier
    // GNUCa.g:287:1: specifierQualifier : ( typeSpecifier -> ^( XTYPE_SPECIFIER typeSpecifier ) | typeQualifier -> ^( XTYPE_QUALIFIER typeQualifier ) | attributes ->);
    public final specifierQualifier_return specifierQualifier() throws RecognitionException {
        specifierQualifier_return retval = new specifierQualifier_return();
        retval.start = input.LT(1);
        int specifierQualifier_StartIndex = input.index();
        Object root_0 = null;

        typeSpecifier_return typeSpecifier77 = null;

        typeQualifier_return typeQualifier78 = null;

        attributes_return attributes79 = null;


        RewriteRuleSubtreeStream stream_typeSpecifier=new RewriteRuleSubtreeStream(adaptor,"rule typeSpecifier");
        RewriteRuleSubtreeStream stream_attributes=new RewriteRuleSubtreeStream(adaptor,"rule attributes");
        RewriteRuleSubtreeStream stream_typeQualifier=new RewriteRuleSubtreeStream(adaptor,"rule typeQualifier");
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 17) ) { return retval; }
            // GNUCa.g:288:2: ( typeSpecifier -> ^( XTYPE_SPECIFIER typeSpecifier ) | typeQualifier -> ^( XTYPE_QUALIFIER typeQualifier ) | attributes ->)
            int alt26=3;
            switch ( input.LA(1) ) {
            case IDENTIFIER:
            case 93:
            case 94:
            case 95:
            case 96:
            case 97:
            case 98:
            case 99:
            case 100:
            case 101:
            case 102:
            case 103:
            case 104:
            case 105:
            case 106:
            case 107:
            case 108:
            case 111:
            case 112:
            case 114:
            case 131:
            case 132:
            case 133:
                {
                alt26=1;
                }
                break;
            case 115:
            case 116:
            case 117:
            case 118:
            case 119:
            case 120:
            case 121:
            case 122:
            case 123:
                {
                alt26=2;
                }
                break;
            case 135:
            case 136:
                {
                alt26=3;
                }
                break;
            default:
                if (backtracking>0) {failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("287:1: specifierQualifier : ( typeSpecifier -> ^( XTYPE_SPECIFIER typeSpecifier ) | typeQualifier -> ^( XTYPE_QUALIFIER typeQualifier ) | attributes ->);", 26, 0, input);

                throw nvae;
            }

            switch (alt26) {
                case 1 :
                    // GNUCa.g:288:4: typeSpecifier
                    {
                    pushFollow(FOLLOW_typeSpecifier_in_specifierQualifier1481);
                    typeSpecifier77=typeSpecifier();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_typeSpecifier.add(typeSpecifier77.getTree());

                    // AST REWRITE
                    // elements: typeSpecifier
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 288:18: -> ^( XTYPE_SPECIFIER typeSpecifier )
                    {
                        // GNUCa.g:288:21: ^( XTYPE_SPECIFIER typeSpecifier )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(adaptor.create(XTYPE_SPECIFIER, "XTYPE_SPECIFIER"), root_1);

                        adaptor.addChild(root_1, stream_typeSpecifier.next());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;
                case 2 :
                    // GNUCa.g:289:4: typeQualifier
                    {
                    pushFollow(FOLLOW_typeQualifier_in_specifierQualifier1494);
                    typeQualifier78=typeQualifier();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_typeQualifier.add(typeQualifier78.getTree());

                    // AST REWRITE
                    // elements: typeQualifier
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 289:18: -> ^( XTYPE_QUALIFIER typeQualifier )
                    {
                        // GNUCa.g:289:21: ^( XTYPE_QUALIFIER typeQualifier )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(adaptor.create(XTYPE_QUALIFIER, "XTYPE_QUALIFIER"), root_1);

                        adaptor.addChild(root_1, stream_typeQualifier.next());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;
                case 3 :
                    // GNUCa.g:290:4: attributes
                    {
                    pushFollow(FOLLOW_attributes_in_specifierQualifier1507);
                    attributes79=attributes();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_attributes.add(attributes79.getTree());

                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 290:15: ->
                    {
                        root_0 = null;
                    }

                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 17, specifierQualifier_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end specifierQualifier

    public static class structDeclaratorList_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start structDeclaratorList
    // GNUCa.g:293:1: structDeclaratorList : structDeclarator ( ',' ( attributes )? structDeclarator )* -> ( structDeclarator )+ ;
    public final structDeclaratorList_return structDeclaratorList() throws RecognitionException {
        structDeclaratorList_return retval = new structDeclaratorList_return();
        retval.start = input.LT(1);
        int structDeclaratorList_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal81=null;
        structDeclarator_return structDeclarator80 = null;

        attributes_return attributes82 = null;

        structDeclarator_return structDeclarator83 = null;


        Object char_literal81_tree=null;
        RewriteRuleTokenStream stream_86=new RewriteRuleTokenStream(adaptor,"token 86");
        RewriteRuleSubtreeStream stream_structDeclarator=new RewriteRuleSubtreeStream(adaptor,"rule structDeclarator");
        RewriteRuleSubtreeStream stream_attributes=new RewriteRuleSubtreeStream(adaptor,"rule attributes");
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 18) ) { return retval; }
            // GNUCa.g:294:2: ( structDeclarator ( ',' ( attributes )? structDeclarator )* -> ( structDeclarator )+ )
            // GNUCa.g:294:4: structDeclarator ( ',' ( attributes )? structDeclarator )*
            {
            pushFollow(FOLLOW_structDeclarator_in_structDeclaratorList1522);
            structDeclarator80=structDeclarator();
            _fsp--;
            if (failed) return retval;
            if ( backtracking==0 ) stream_structDeclarator.add(structDeclarator80.getTree());
            // GNUCa.g:294:21: ( ',' ( attributes )? structDeclarator )*
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( (LA28_0==86) ) {
                    alt28=1;
                }


                switch (alt28) {
            	case 1 :
            	    // GNUCa.g:294:23: ',' ( attributes )? structDeclarator
            	    {
            	    char_literal81=(Token)input.LT(1);
            	    match(input,86,FOLLOW_86_in_structDeclaratorList1526); if (failed) return retval;
            	    if ( backtracking==0 ) stream_86.add(char_literal81);

            	    // GNUCa.g:294:27: ( attributes )?
            	    int alt27=2;
            	    int LA27_0 = input.LA(1);

            	    if ( ((LA27_0>=135 && LA27_0<=136)) ) {
            	        alt27=1;
            	    }
            	    switch (alt27) {
            	        case 1 :
            	            // GNUCa.g:0:0: attributes
            	            {
            	            pushFollow(FOLLOW_attributes_in_structDeclaratorList1528);
            	            attributes82=attributes();
            	            _fsp--;
            	            if (failed) return retval;
            	            if ( backtracking==0 ) stream_attributes.add(attributes82.getTree());

            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_structDeclarator_in_structDeclaratorList1531);
            	    structDeclarator83=structDeclarator();
            	    _fsp--;
            	    if (failed) return retval;
            	    if ( backtracking==0 ) stream_structDeclarator.add(structDeclarator83.getTree());

            	    }
            	    break;

            	default :
            	    break loop28;
                }
            } while (true);


            // AST REWRITE
            // elements: structDeclarator
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 294:59: -> ( structDeclarator )+
            {
                if ( !(stream_structDeclarator.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_structDeclarator.hasNext() ) {
                    adaptor.addChild(root_0, stream_structDeclarator.next());

                }
                stream_structDeclarator.reset();

            }

            }

            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 18, structDeclaratorList_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end structDeclaratorList

    public static class structDeclarator_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start structDeclarator
    // GNUCa.g:297:1: structDeclarator : ( declarator ( ':' constantExpression )? ( attributes )? -> ^( STRUCT_DECLARATOR declarator ( constantExpression )? ) | ':' constantExpression ( attributes )? -> ^( STRUCT_DECLARATOR constantExpression ) );
    public final structDeclarator_return structDeclarator() throws RecognitionException {
        structDeclarator_return retval = new structDeclarator_return();
        retval.start = input.LT(1);
        int structDeclarator_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal85=null;
        Token char_literal88=null;
        declarator_return declarator84 = null;

        constantExpression_return constantExpression86 = null;

        attributes_return attributes87 = null;

        constantExpression_return constantExpression89 = null;

        attributes_return attributes90 = null;


        Object char_literal85_tree=null;
        Object char_literal88_tree=null;
        RewriteRuleTokenStream stream_113=new RewriteRuleTokenStream(adaptor,"token 113");
        RewriteRuleSubtreeStream stream_declarator=new RewriteRuleSubtreeStream(adaptor,"rule declarator");
        RewriteRuleSubtreeStream stream_constantExpression=new RewriteRuleSubtreeStream(adaptor,"rule constantExpression");
        RewriteRuleSubtreeStream stream_attributes=new RewriteRuleSubtreeStream(adaptor,"rule attributes");
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 19) ) { return retval; }
            // GNUCa.g:298:2: ( declarator ( ':' constantExpression )? ( attributes )? -> ^( STRUCT_DECLARATOR declarator ( constantExpression )? ) | ':' constantExpression ( attributes )? -> ^( STRUCT_DECLARATOR constantExpression ) )
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==IDENTIFIER||LA32_0==83||LA32_0==129) ) {
                alt32=1;
            }
            else if ( (LA32_0==113) ) {
                alt32=2;
            }
            else {
                if (backtracking>0) {failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("297:1: structDeclarator : ( declarator ( ':' constantExpression )? ( attributes )? -> ^( STRUCT_DECLARATOR declarator ( constantExpression )? ) | ':' constantExpression ( attributes )? -> ^( STRUCT_DECLARATOR constantExpression ) );", 32, 0, input);

                throw nvae;
            }
            switch (alt32) {
                case 1 :
                    // GNUCa.g:298:4: declarator ( ':' constantExpression )? ( attributes )?
                    {
                    pushFollow(FOLLOW_declarator_in_structDeclarator1551);
                    declarator84=declarator();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_declarator.add(declarator84.getTree());
                    // GNUCa.g:298:15: ( ':' constantExpression )?
                    int alt29=2;
                    int LA29_0 = input.LA(1);

                    if ( (LA29_0==113) ) {
                        alt29=1;
                    }
                    switch (alt29) {
                        case 1 :
                            // GNUCa.g:298:17: ':' constantExpression
                            {
                            char_literal85=(Token)input.LT(1);
                            match(input,113,FOLLOW_113_in_structDeclarator1555); if (failed) return retval;
                            if ( backtracking==0 ) stream_113.add(char_literal85);

                            pushFollow(FOLLOW_constantExpression_in_structDeclarator1557);
                            constantExpression86=constantExpression();
                            _fsp--;
                            if (failed) return retval;
                            if ( backtracking==0 ) stream_constantExpression.add(constantExpression86.getTree());

                            }
                            break;

                    }

                    // GNUCa.g:298:43: ( attributes )?
                    int alt30=2;
                    int LA30_0 = input.LA(1);

                    if ( ((LA30_0>=135 && LA30_0<=136)) ) {
                        alt30=1;
                    }
                    switch (alt30) {
                        case 1 :
                            // GNUCa.g:0:0: attributes
                            {
                            pushFollow(FOLLOW_attributes_in_structDeclarator1562);
                            attributes87=attributes();
                            _fsp--;
                            if (failed) return retval;
                            if ( backtracking==0 ) stream_attributes.add(attributes87.getTree());

                            }
                            break;

                    }


                    // AST REWRITE
                    // elements: constantExpression, declarator
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 298:55: -> ^( STRUCT_DECLARATOR declarator ( constantExpression )? )
                    {
                        // GNUCa.g:298:58: ^( STRUCT_DECLARATOR declarator ( constantExpression )? )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(adaptor.create(STRUCT_DECLARATOR, "STRUCT_DECLARATOR"), root_1);

                        adaptor.addChild(root_1, stream_declarator.next());
                        // GNUCa.g:298:89: ( constantExpression )?
                        if ( stream_constantExpression.hasNext() ) {
                            adaptor.addChild(root_1, stream_constantExpression.next());

                        }
                        stream_constantExpression.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;
                case 2 :
                    // GNUCa.g:299:4: ':' constantExpression ( attributes )?
                    {
                    char_literal88=(Token)input.LT(1);
                    match(input,113,FOLLOW_113_in_structDeclarator1579); if (failed) return retval;
                    if ( backtracking==0 ) stream_113.add(char_literal88);

                    pushFollow(FOLLOW_constantExpression_in_structDeclarator1581);
                    constantExpression89=constantExpression();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_constantExpression.add(constantExpression89.getTree());
                    // GNUCa.g:299:27: ( attributes )?
                    int alt31=2;
                    int LA31_0 = input.LA(1);

                    if ( ((LA31_0>=135 && LA31_0<=136)) ) {
                        alt31=1;
                    }
                    switch (alt31) {
                        case 1 :
                            // GNUCa.g:0:0: attributes
                            {
                            pushFollow(FOLLOW_attributes_in_structDeclarator1583);
                            attributes90=attributes();
                            _fsp--;
                            if (failed) return retval;
                            if ( backtracking==0 ) stream_attributes.add(attributes90.getTree());

                            }
                            break;

                    }


                    // AST REWRITE
                    // elements: constantExpression
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 299:39: -> ^( STRUCT_DECLARATOR constantExpression )
                    {
                        // GNUCa.g:299:42: ^( STRUCT_DECLARATOR constantExpression )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(adaptor.create(STRUCT_DECLARATOR, "STRUCT_DECLARATOR"), root_1);

                        adaptor.addChild(root_1, stream_constantExpression.next());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 19, structDeclarator_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end structDeclarator

    public static class enumSpecifier_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start enumSpecifier
    // GNUCa.g:302:1: enumSpecifier : 'enum' ( attributes )? ( '{' enumeratorList ( ',' )? '}' -> ^( 'enum' enumeratorList ) | IDENTIFIER '{' enumeratorList ( ',' )? '}' -> ^( 'enum' ^( XID IDENTIFIER ) enumeratorList ) | IDENTIFIER -> ^( 'enum' ^( XID IDENTIFIER ) ) ) ;
    public final enumSpecifier_return enumSpecifier() throws RecognitionException {
        enumSpecifier_return retval = new enumSpecifier_return();
        retval.start = input.LT(1);
        int enumSpecifier_StartIndex = input.index();
        Object root_0 = null;

        Token string_literal91=null;
        Token char_literal93=null;
        Token char_literal95=null;
        Token char_literal96=null;
        Token IDENTIFIER97=null;
        Token char_literal98=null;
        Token char_literal100=null;
        Token char_literal101=null;
        Token IDENTIFIER102=null;
        attributes_return attributes92 = null;

        enumeratorList_return enumeratorList94 = null;

        enumeratorList_return enumeratorList99 = null;


        Object string_literal91_tree=null;
        Object char_literal93_tree=null;
        Object char_literal95_tree=null;
        Object char_literal96_tree=null;
        Object IDENTIFIER97_tree=null;
        Object char_literal98_tree=null;
        Object char_literal100_tree=null;
        Object char_literal101_tree=null;
        Object IDENTIFIER102_tree=null;
        RewriteRuleTokenStream stream_114=new RewriteRuleTokenStream(adaptor,"token 114");
        RewriteRuleTokenStream stream_109=new RewriteRuleTokenStream(adaptor,"token 109");
        RewriteRuleTokenStream stream_110=new RewriteRuleTokenStream(adaptor,"token 110");
        RewriteRuleTokenStream stream_IDENTIFIER=new RewriteRuleTokenStream(adaptor,"token IDENTIFIER");
        RewriteRuleTokenStream stream_86=new RewriteRuleTokenStream(adaptor,"token 86");
        RewriteRuleSubtreeStream stream_attributes=new RewriteRuleSubtreeStream(adaptor,"rule attributes");
        RewriteRuleSubtreeStream stream_enumeratorList=new RewriteRuleSubtreeStream(adaptor,"rule enumeratorList");
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 20) ) { return retval; }
            // GNUCa.g:303:2: ( 'enum' ( attributes )? ( '{' enumeratorList ( ',' )? '}' -> ^( 'enum' enumeratorList ) | IDENTIFIER '{' enumeratorList ( ',' )? '}' -> ^( 'enum' ^( XID IDENTIFIER ) enumeratorList ) | IDENTIFIER -> ^( 'enum' ^( XID IDENTIFIER ) ) ) )
            // GNUCa.g:303:4: 'enum' ( attributes )? ( '{' enumeratorList ( ',' )? '}' -> ^( 'enum' enumeratorList ) | IDENTIFIER '{' enumeratorList ( ',' )? '}' -> ^( 'enum' ^( XID IDENTIFIER ) enumeratorList ) | IDENTIFIER -> ^( 'enum' ^( XID IDENTIFIER ) ) )
            {
            string_literal91=(Token)input.LT(1);
            match(input,114,FOLLOW_114_in_enumSpecifier1605); if (failed) return retval;
            if ( backtracking==0 ) stream_114.add(string_literal91);

            // GNUCa.g:303:11: ( attributes )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( ((LA33_0>=135 && LA33_0<=136)) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // GNUCa.g:0:0: attributes
                    {
                    pushFollow(FOLLOW_attributes_in_enumSpecifier1607);
                    attributes92=attributes();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_attributes.add(attributes92.getTree());

                    }
                    break;

            }

            // GNUCa.g:304:2: ( '{' enumeratorList ( ',' )? '}' -> ^( 'enum' enumeratorList ) | IDENTIFIER '{' enumeratorList ( ',' )? '}' -> ^( 'enum' ^( XID IDENTIFIER ) enumeratorList ) | IDENTIFIER -> ^( 'enum' ^( XID IDENTIFIER ) ) )
            int alt36=3;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==109) ) {
                alt36=1;
            }
            else if ( (LA36_0==IDENTIFIER) ) {
                int LA36_2 = input.LA(2);

                if ( (LA36_2==109) ) {
                    alt36=2;
                }
                else if ( (LA36_2==EOF||LA36_2==IDENTIFIER||LA36_2==79||(LA36_2>=83 && LA36_2<=84)||LA36_2==86||(LA36_2>=88 && LA36_2<=108)||(LA36_2>=111 && LA36_2<=127)||LA36_2==129||(LA36_2>=131 && LA36_2<=133)||(LA36_2>=135 && LA36_2<=136)) ) {
                    alt36=3;
                }
                else {
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("304:2: ( '{' enumeratorList ( ',' )? '}' -> ^( 'enum' enumeratorList ) | IDENTIFIER '{' enumeratorList ( ',' )? '}' -> ^( 'enum' ^( XID IDENTIFIER ) enumeratorList ) | IDENTIFIER -> ^( 'enum' ^( XID IDENTIFIER ) ) )", 36, 2, input);

                    throw nvae;
                }
            }
            else {
                if (backtracking>0) {failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("304:2: ( '{' enumeratorList ( ',' )? '}' -> ^( 'enum' enumeratorList ) | IDENTIFIER '{' enumeratorList ( ',' )? '}' -> ^( 'enum' ^( XID IDENTIFIER ) enumeratorList ) | IDENTIFIER -> ^( 'enum' ^( XID IDENTIFIER ) ) )", 36, 0, input);

                throw nvae;
            }
            switch (alt36) {
                case 1 :
                    // GNUCa.g:305:3: '{' enumeratorList ( ',' )? '}'
                    {
                    char_literal93=(Token)input.LT(1);
                    match(input,109,FOLLOW_109_in_enumSpecifier1615); if (failed) return retval;
                    if ( backtracking==0 ) stream_109.add(char_literal93);

                    pushFollow(FOLLOW_enumeratorList_in_enumSpecifier1617);
                    enumeratorList94=enumeratorList();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_enumeratorList.add(enumeratorList94.getTree());
                    // GNUCa.g:305:22: ( ',' )?
                    int alt34=2;
                    int LA34_0 = input.LA(1);

                    if ( (LA34_0==86) ) {
                        alt34=1;
                    }
                    switch (alt34) {
                        case 1 :
                            // GNUCa.g:305:24: ','
                            {
                            char_literal95=(Token)input.LT(1);
                            match(input,86,FOLLOW_86_in_enumSpecifier1621); if (failed) return retval;
                            if ( backtracking==0 ) stream_86.add(char_literal95);


                            }
                            break;

                    }

                    char_literal96=(Token)input.LT(1);
                    match(input,110,FOLLOW_110_in_enumSpecifier1626); if (failed) return retval;
                    if ( backtracking==0 ) stream_110.add(char_literal96);


                    // AST REWRITE
                    // elements: enumeratorList, 114
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 305:35: -> ^( 'enum' enumeratorList )
                    {
                        // GNUCa.g:305:38: ^( 'enum' enumeratorList )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_114.next(), root_1);

                        adaptor.addChild(root_1, stream_enumeratorList.next());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;
                case 2 :
                    // GNUCa.g:306:4: IDENTIFIER '{' enumeratorList ( ',' )? '}'
                    {
                    IDENTIFIER97=(Token)input.LT(1);
                    match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_enumSpecifier1639); if (failed) return retval;
                    if ( backtracking==0 ) stream_IDENTIFIER.add(IDENTIFIER97);

                    char_literal98=(Token)input.LT(1);
                    match(input,109,FOLLOW_109_in_enumSpecifier1641); if (failed) return retval;
                    if ( backtracking==0 ) stream_109.add(char_literal98);

                    pushFollow(FOLLOW_enumeratorList_in_enumSpecifier1643);
                    enumeratorList99=enumeratorList();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_enumeratorList.add(enumeratorList99.getTree());
                    // GNUCa.g:306:34: ( ',' )?
                    int alt35=2;
                    int LA35_0 = input.LA(1);

                    if ( (LA35_0==86) ) {
                        alt35=1;
                    }
                    switch (alt35) {
                        case 1 :
                            // GNUCa.g:306:36: ','
                            {
                            char_literal100=(Token)input.LT(1);
                            match(input,86,FOLLOW_86_in_enumSpecifier1647); if (failed) return retval;
                            if ( backtracking==0 ) stream_86.add(char_literal100);


                            }
                            break;

                    }

                    char_literal101=(Token)input.LT(1);
                    match(input,110,FOLLOW_110_in_enumSpecifier1652); if (failed) return retval;
                    if ( backtracking==0 ) stream_110.add(char_literal101);


                    // AST REWRITE
                    // elements: enumeratorList, IDENTIFIER, 114
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 306:47: -> ^( 'enum' ^( XID IDENTIFIER ) enumeratorList )
                    {
                        // GNUCa.g:306:50: ^( 'enum' ^( XID IDENTIFIER ) enumeratorList )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_114.next(), root_1);

                        // GNUCa.g:306:59: ^( XID IDENTIFIER )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot(adaptor.create(XID, "XID"), root_2);

                        adaptor.addChild(root_2, stream_IDENTIFIER.next());

                        adaptor.addChild(root_1, root_2);
                        }
                        adaptor.addChild(root_1, stream_enumeratorList.next());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;
                case 3 :
                    // GNUCa.g:307:4: IDENTIFIER
                    {
                    IDENTIFIER102=(Token)input.LT(1);
                    match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_enumSpecifier1671); if (failed) return retval;
                    if ( backtracking==0 ) stream_IDENTIFIER.add(IDENTIFIER102);


                    // AST REWRITE
                    // elements: IDENTIFIER, 114
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 307:15: -> ^( 'enum' ^( XID IDENTIFIER ) )
                    {
                        // GNUCa.g:307:18: ^( 'enum' ^( XID IDENTIFIER ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_114.next(), root_1);

                        // GNUCa.g:307:27: ^( XID IDENTIFIER )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot(adaptor.create(XID, "XID"), root_2);

                        adaptor.addChild(root_2, stream_IDENTIFIER.next());

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 20, enumSpecifier_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end enumSpecifier

    public static class enumeratorList_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start enumeratorList
    // GNUCa.g:315:1: enumeratorList : enumerator ( ',' enumerator )* -> ( enumerator )+ ;
    public final enumeratorList_return enumeratorList() throws RecognitionException {
        enumeratorList_return retval = new enumeratorList_return();
        retval.start = input.LT(1);
        int enumeratorList_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal104=null;
        enumerator_return enumerator103 = null;

        enumerator_return enumerator105 = null;


        Object char_literal104_tree=null;
        RewriteRuleTokenStream stream_86=new RewriteRuleTokenStream(adaptor,"token 86");
        RewriteRuleSubtreeStream stream_enumerator=new RewriteRuleSubtreeStream(adaptor,"rule enumerator");
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 21) ) { return retval; }
            // GNUCa.g:316:2: ( enumerator ( ',' enumerator )* -> ( enumerator )+ )
            // GNUCa.g:316:4: enumerator ( ',' enumerator )*
            {
            pushFollow(FOLLOW_enumerator_in_enumeratorList1702);
            enumerator103=enumerator();
            _fsp--;
            if (failed) return retval;
            if ( backtracking==0 ) stream_enumerator.add(enumerator103.getTree());
            // GNUCa.g:316:15: ( ',' enumerator )*
            loop37:
            do {
                int alt37=2;
                int LA37_0 = input.LA(1);

                if ( (LA37_0==86) ) {
                    int LA37_1 = input.LA(2);

                    if ( (LA37_1==IDENTIFIER) ) {
                        alt37=1;
                    }


                }


                switch (alt37) {
            	case 1 :
            	    // GNUCa.g:316:17: ',' enumerator
            	    {
            	    char_literal104=(Token)input.LT(1);
            	    match(input,86,FOLLOW_86_in_enumeratorList1706); if (failed) return retval;
            	    if ( backtracking==0 ) stream_86.add(char_literal104);

            	    pushFollow(FOLLOW_enumerator_in_enumeratorList1708);
            	    enumerator105=enumerator();
            	    _fsp--;
            	    if (failed) return retval;
            	    if ( backtracking==0 ) stream_enumerator.add(enumerator105.getTree());

            	    }
            	    break;

            	default :
            	    break loop37;
                }
            } while (true);


            // AST REWRITE
            // elements: enumerator
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 316:35: -> ( enumerator )+
            {
                if ( !(stream_enumerator.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_enumerator.hasNext() ) {
                    adaptor.addChild(root_0, stream_enumerator.next());

                }
                stream_enumerator.reset();

            }

            }

            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 21, enumeratorList_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end enumeratorList

    public static class enumerator_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start enumerator
    // GNUCa.g:319:1: enumerator : IDENTIFIER ( '=' constantExpression )? -> ^( ENUMERATOR IDENTIFIER ( constantExpression )? ) ;
    public final enumerator_return enumerator() throws RecognitionException {
        enumerator_return retval = new enumerator_return();
        retval.start = input.LT(1);
        int enumerator_StartIndex = input.index();
        Object root_0 = null;

        Token IDENTIFIER106=null;
        Token char_literal107=null;
        constantExpression_return constantExpression108 = null;


        Object IDENTIFIER106_tree=null;
        Object char_literal107_tree=null;
        RewriteRuleTokenStream stream_IDENTIFIER=new RewriteRuleTokenStream(adaptor,"token IDENTIFIER");
        RewriteRuleTokenStream stream_87=new RewriteRuleTokenStream(adaptor,"token 87");
        RewriteRuleSubtreeStream stream_constantExpression=new RewriteRuleSubtreeStream(adaptor,"rule constantExpression");
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 22) ) { return retval; }
            // GNUCa.g:320:2: ( IDENTIFIER ( '=' constantExpression )? -> ^( ENUMERATOR IDENTIFIER ( constantExpression )? ) )
            // GNUCa.g:320:4: IDENTIFIER ( '=' constantExpression )?
            {
            IDENTIFIER106=(Token)input.LT(1);
            match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_enumerator1727); if (failed) return retval;
            if ( backtracking==0 ) stream_IDENTIFIER.add(IDENTIFIER106);

            // GNUCa.g:320:16: ( '=' constantExpression )?
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==87) ) {
                alt38=1;
            }
            switch (alt38) {
                case 1 :
                    // GNUCa.g:320:18: '=' constantExpression
                    {
                    char_literal107=(Token)input.LT(1);
                    match(input,87,FOLLOW_87_in_enumerator1732); if (failed) return retval;
                    if ( backtracking==0 ) stream_87.add(char_literal107);

                    pushFollow(FOLLOW_constantExpression_in_enumerator1734);
                    constantExpression108=constantExpression();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_constantExpression.add(constantExpression108.getTree());

                    }
                    break;

            }


            // AST REWRITE
            // elements: IDENTIFIER, constantExpression
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 320:44: -> ^( ENUMERATOR IDENTIFIER ( constantExpression )? )
            {
                // GNUCa.g:320:47: ^( ENUMERATOR IDENTIFIER ( constantExpression )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(adaptor.create(ENUMERATOR, "ENUMERATOR"), root_1);

                adaptor.addChild(root_1, stream_IDENTIFIER.next());
                // GNUCa.g:320:71: ( constantExpression )?
                if ( stream_constantExpression.hasNext() ) {
                    adaptor.addChild(root_1, stream_constantExpression.next());

                }
                stream_constantExpression.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            }

            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 22, enumerator_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end enumerator

    public static class typeQualifier_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start typeQualifier
    // GNUCa.g:323:1: typeQualifier : ( 'const' | '__const' -> 'const' | '__const__' -> 'const' | 'restrict' | '__restrict' -> 'restrict' | '__restrict__' -> 'restrict' | 'volatile' | '__volatile' -> 'volatile' | '__volatile__' -> 'volatile' );
    public final typeQualifier_return typeQualifier() throws RecognitionException {
        typeQualifier_return retval = new typeQualifier_return();
        retval.start = input.LT(1);
        int typeQualifier_StartIndex = input.index();
        Object root_0 = null;

        Token string_literal109=null;
        Token string_literal110=null;
        Token string_literal111=null;
        Token string_literal112=null;
        Token string_literal113=null;
        Token string_literal114=null;
        Token string_literal115=null;
        Token string_literal116=null;
        Token string_literal117=null;

        Object string_literal109_tree=null;
        Object string_literal110_tree=null;
        Object string_literal111_tree=null;
        Object string_literal112_tree=null;
        Object string_literal113_tree=null;
        Object string_literal114_tree=null;
        Object string_literal115_tree=null;
        Object string_literal116_tree=null;
        Object string_literal117_tree=null;
        RewriteRuleTokenStream stream_116=new RewriteRuleTokenStream(adaptor,"token 116");
        RewriteRuleTokenStream stream_117=new RewriteRuleTokenStream(adaptor,"token 117");
        RewriteRuleTokenStream stream_122=new RewriteRuleTokenStream(adaptor,"token 122");
        RewriteRuleTokenStream stream_123=new RewriteRuleTokenStream(adaptor,"token 123");
        RewriteRuleTokenStream stream_120=new RewriteRuleTokenStream(adaptor,"token 120");
        RewriteRuleTokenStream stream_119=new RewriteRuleTokenStream(adaptor,"token 119");

        try {
            if ( backtracking>0 && alreadyParsedRule(input, 23) ) { return retval; }
            // GNUCa.g:324:9: ( 'const' | '__const' -> 'const' | '__const__' -> 'const' | 'restrict' | '__restrict' -> 'restrict' | '__restrict__' -> 'restrict' | 'volatile' | '__volatile' -> 'volatile' | '__volatile__' -> 'volatile' )
            int alt39=9;
            switch ( input.LA(1) ) {
            case 115:
                {
                alt39=1;
                }
                break;
            case 116:
                {
                alt39=2;
                }
                break;
            case 117:
                {
                alt39=3;
                }
                break;
            case 118:
                {
                alt39=4;
                }
                break;
            case 119:
                {
                alt39=5;
                }
                break;
            case 120:
                {
                alt39=6;
                }
                break;
            case 121:
                {
                alt39=7;
                }
                break;
            case 122:
                {
                alt39=8;
                }
                break;
            case 123:
                {
                alt39=9;
                }
                break;
            default:
                if (backtracking>0) {failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("323:1: typeQualifier : ( 'const' | '__const' -> 'const' | '__const__' -> 'const' | 'restrict' | '__restrict' -> 'restrict' | '__restrict__' -> 'restrict' | 'volatile' | '__volatile' -> 'volatile' | '__volatile__' -> 'volatile' );", 39, 0, input);

                throw nvae;
            }

            switch (alt39) {
                case 1 :
                    // GNUCa.g:324:17: 'const'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal109=(Token)input.LT(1);
                    match(input,115,FOLLOW_115_in_typeQualifier1773); if (failed) return retval;
                    if ( backtracking==0 ) {
                    string_literal109_tree = (Object)adaptor.create(string_literal109);
                    adaptor.addChild(root_0, string_literal109_tree);
                    }

                    }
                    break;
                case 2 :
                    // GNUCa.g:325:11: '__const'
                    {
                    string_literal110=(Token)input.LT(1);
                    match(input,116,FOLLOW_116_in_typeQualifier1785); if (failed) return retval;
                    if ( backtracking==0 ) stream_116.add(string_literal110);


                    // AST REWRITE
                    // elements: 115
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 325:21: -> 'const'
                    {
                        adaptor.addChild(root_0, adaptor.create(115, "115"));

                    }

                    }

                    }
                    break;
                case 3 :
                    // GNUCa.g:326:11: '__const__'
                    {
                    string_literal111=(Token)input.LT(1);
                    match(input,117,FOLLOW_117_in_typeQualifier1801); if (failed) return retval;
                    if ( backtracking==0 ) stream_117.add(string_literal111);


                    // AST REWRITE
                    // elements: 115
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 326:23: -> 'const'
                    {
                        adaptor.addChild(root_0, adaptor.create(115, "115"));

                    }

                    }

                    }
                    break;
                case 4 :
                    // GNUCa.g:327:4: 'restrict'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal112=(Token)input.LT(1);
                    match(input,118,FOLLOW_118_in_typeQualifier1810); if (failed) return retval;
                    if ( backtracking==0 ) {
                    string_literal112_tree = (Object)adaptor.create(string_literal112);
                    adaptor.addChild(root_0, string_literal112_tree);
                    }

                    }
                    break;
                case 5 :
                    // GNUCa.g:328:4: '__restrict'
                    {
                    string_literal113=(Token)input.LT(1);
                    match(input,119,FOLLOW_119_in_typeQualifier1815); if (failed) return retval;
                    if ( backtracking==0 ) stream_119.add(string_literal113);


                    // AST REWRITE
                    // elements: 118
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 328:17: -> 'restrict'
                    {
                        adaptor.addChild(root_0, adaptor.create(118, "118"));

                    }

                    }

                    }
                    break;
                case 6 :
                    // GNUCa.g:329:4: '__restrict__'
                    {
                    string_literal114=(Token)input.LT(1);
                    match(input,120,FOLLOW_120_in_typeQualifier1824); if (failed) return retval;
                    if ( backtracking==0 ) stream_120.add(string_literal114);


                    // AST REWRITE
                    // elements: 118
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 329:19: -> 'restrict'
                    {
                        adaptor.addChild(root_0, adaptor.create(118, "118"));

                    }

                    }

                    }
                    break;
                case 7 :
                    // GNUCa.g:330:17: 'volatile'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal115=(Token)input.LT(1);
                    match(input,121,FOLLOW_121_in_typeQualifier1846); if (failed) return retval;
                    if ( backtracking==0 ) {
                    string_literal115_tree = (Object)adaptor.create(string_literal115);
                    adaptor.addChild(root_0, string_literal115_tree);
                    }

                    }
                    break;
                case 8 :
                    // GNUCa.g:331:17: '__volatile'
                    {
                    string_literal116=(Token)input.LT(1);
                    match(input,122,FOLLOW_122_in_typeQualifier1864); if (failed) return retval;
                    if ( backtracking==0 ) stream_122.add(string_literal116);


                    // AST REWRITE
                    // elements: 121
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 331:30: -> 'volatile'
                    {
                        adaptor.addChild(root_0, adaptor.create(121, "121"));

                    }

                    }

                    }
                    break;
                case 9 :
                    // GNUCa.g:332:17: '__volatile__'
                    {
                    string_literal117=(Token)input.LT(1);
                    match(input,123,FOLLOW_123_in_typeQualifier1886); if (failed) return retval;
                    if ( backtracking==0 ) stream_123.add(string_literal117);


                    // AST REWRITE
                    // elements: 121
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 332:32: -> 'volatile'
                    {
                        adaptor.addChild(root_0, adaptor.create(121, "121"));

                    }

                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 23, typeQualifier_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end typeQualifier

    public static class functionSpecifier_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start functionSpecifier
    // GNUCa.g:335:1: functionSpecifier : ( 'inline' | '__inline' -> 'inline' | '__inline__' -> 'inline' );
    public final functionSpecifier_return functionSpecifier() throws RecognitionException {
        functionSpecifier_return retval = new functionSpecifier_return();
        retval.start = input.LT(1);
        int functionSpecifier_StartIndex = input.index();
        Object root_0 = null;

        Token string_literal118=null;
        Token string_literal119=null;
        Token string_literal120=null;

        Object string_literal118_tree=null;
        Object string_literal119_tree=null;
        Object string_literal120_tree=null;
        RewriteRuleTokenStream stream_125=new RewriteRuleTokenStream(adaptor,"token 125");
        RewriteRuleTokenStream stream_126=new RewriteRuleTokenStream(adaptor,"token 126");

        try {
            if ( backtracking>0 && alreadyParsedRule(input, 24) ) { return retval; }
            // GNUCa.g:336:2: ( 'inline' | '__inline' -> 'inline' | '__inline__' -> 'inline' )
            int alt40=3;
            switch ( input.LA(1) ) {
            case 124:
                {
                alt40=1;
                }
                break;
            case 125:
                {
                alt40=2;
                }
                break;
            case 126:
                {
                alt40=3;
                }
                break;
            default:
                if (backtracking>0) {failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("335:1: functionSpecifier : ( 'inline' | '__inline' -> 'inline' | '__inline__' -> 'inline' );", 40, 0, input);

                throw nvae;
            }

            switch (alt40) {
                case 1 :
                    // GNUCa.g:336:4: 'inline'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal118=(Token)input.LT(1);
                    match(input,124,FOLLOW_124_in_functionSpecifier1908); if (failed) return retval;
                    if ( backtracking==0 ) {
                    string_literal118_tree = (Object)adaptor.create(string_literal118);
                    adaptor.addChild(root_0, string_literal118_tree);
                    }

                    }
                    break;
                case 2 :
                    // GNUCa.g:337:4: '__inline'
                    {
                    string_literal119=(Token)input.LT(1);
                    match(input,125,FOLLOW_125_in_functionSpecifier1913); if (failed) return retval;
                    if ( backtracking==0 ) stream_125.add(string_literal119);


                    // AST REWRITE
                    // elements: 124
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 337:15: -> 'inline'
                    {
                        adaptor.addChild(root_0, adaptor.create(124, "124"));

                    }

                    }

                    }
                    break;
                case 3 :
                    // GNUCa.g:338:4: '__inline__'
                    {
                    string_literal120=(Token)input.LT(1);
                    match(input,126,FOLLOW_126_in_functionSpecifier1922); if (failed) return retval;
                    if ( backtracking==0 ) stream_126.add(string_literal120);


                    // AST REWRITE
                    // elements: 124
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 338:17: -> 'inline'
                    {
                        adaptor.addChild(root_0, adaptor.create(124, "124"));

                    }

                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 24, functionSpecifier_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end functionSpecifier

    public static class declarator_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start declarator
    // GNUCa.g:341:1: declarator : ( pointer )? directDeclarator -> ^( DECLARATOR ( pointer )? directDeclarator ) ;
    public final declarator_return declarator() throws RecognitionException {
        declarator_return retval = new declarator_return();
        retval.start = input.LT(1);
        int declarator_StartIndex = input.index();
        Object root_0 = null;

        pointer_return pointer121 = null;

        directDeclarator_return directDeclarator122 = null;


        RewriteRuleSubtreeStream stream_directDeclarator=new RewriteRuleSubtreeStream(adaptor,"rule directDeclarator");
        RewriteRuleSubtreeStream stream_pointer=new RewriteRuleSubtreeStream(adaptor,"rule pointer");
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 25) ) { return retval; }
            // GNUCa.g:342:2: ( ( pointer )? directDeclarator -> ^( DECLARATOR ( pointer )? directDeclarator ) )
            // GNUCa.g:342:4: ( pointer )? directDeclarator
            {
            // GNUCa.g:342:4: ( pointer )?
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==129) ) {
                alt41=1;
            }
            switch (alt41) {
                case 1 :
                    // GNUCa.g:0:0: pointer
                    {
                    pushFollow(FOLLOW_pointer_in_declarator1938);
                    pointer121=pointer();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_pointer.add(pointer121.getTree());

                    }
                    break;

            }

            pushFollow(FOLLOW_directDeclarator_in_declarator1941);
            directDeclarator122=directDeclarator();
            _fsp--;
            if (failed) return retval;
            if ( backtracking==0 ) stream_directDeclarator.add(directDeclarator122.getTree());

            // AST REWRITE
            // elements: pointer, directDeclarator
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 342:30: -> ^( DECLARATOR ( pointer )? directDeclarator )
            {
                // GNUCa.g:342:33: ^( DECLARATOR ( pointer )? directDeclarator )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(adaptor.create(DECLARATOR, "DECLARATOR"), root_1);

                // GNUCa.g:342:46: ( pointer )?
                if ( stream_pointer.hasNext() ) {
                    adaptor.addChild(root_1, stream_pointer.next());

                }
                stream_pointer.reset();
                adaptor.addChild(root_1, stream_directDeclarator.next());

                adaptor.addChild(root_0, root_1);
                }

            }

            }

            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 25, declarator_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end declarator

    public static class directDeclarator_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start directDeclarator
    // GNUCa.g:345:1: directDeclarator : ( IDENTIFIER -> IDENTIFIER | '(' ( attributes )? declarator ')' -> declarator ) ( '[' (tq+= typeQualifier )* (ae= assignmentExpression )? ']' -> ^( ARRAY_DECLARATOR $directDeclarator ( $tq)* ( $ae)? ) | '[' 'static' (tq+= typeQualifier )* ae= assignmentExpression ']' -> ^( ARRAY_DECLARATOR $directDeclarator 'static' ( $tq)* $ae) | '[' (tq+= typeQualifier )+ 'static' ae= assignmentExpression ']' -> ^( ARRAY_DECLARATOR $directDeclarator 'static' ( $tq)+ $ae) | '[' (tq+= typeQualifier )* '*' ']' -> ^( ARRAY_DECLARATOR $directDeclarator '*' ( $tq)* ) | '(' parameterTypeList ')' -> ^( FUNCTION_DECLARATOR $directDeclarator parameterTypeList ) | '(' ( identifierList )? ')' -> ^( FUNCTION_DECLARATOR $directDeclarator ( identifierList )? ) )* ;
    public final directDeclarator_return directDeclarator() throws RecognitionException {
        directDeclarator_return retval = new directDeclarator_return();
        retval.start = input.LT(1);
        int directDeclarator_StartIndex = input.index();
        Object root_0 = null;

        Token IDENTIFIER123=null;
        Token char_literal124=null;
        Token char_literal127=null;
        Token char_literal128=null;
        Token char_literal129=null;
        Token char_literal130=null;
        Token string_literal131=null;
        Token char_literal132=null;
        Token char_literal133=null;
        Token string_literal134=null;
        Token char_literal135=null;
        Token char_literal136=null;
        Token char_literal137=null;
        Token char_literal138=null;
        Token char_literal139=null;
        Token char_literal141=null;
        Token char_literal142=null;
        Token char_literal144=null;
        List list_tq=null;
        assignmentExpression_return ae = null;

        attributes_return attributes125 = null;

        declarator_return declarator126 = null;

        parameterTypeList_return parameterTypeList140 = null;

        identifierList_return identifierList143 = null;

        RuleReturnScope tq = null;
        Object IDENTIFIER123_tree=null;
        Object char_literal124_tree=null;
        Object char_literal127_tree=null;
        Object char_literal128_tree=null;
        Object char_literal129_tree=null;
        Object char_literal130_tree=null;
        Object string_literal131_tree=null;
        Object char_literal132_tree=null;
        Object char_literal133_tree=null;
        Object string_literal134_tree=null;
        Object char_literal135_tree=null;
        Object char_literal136_tree=null;
        Object char_literal137_tree=null;
        Object char_literal138_tree=null;
        Object char_literal139_tree=null;
        Object char_literal141_tree=null;
        Object char_literal142_tree=null;
        Object char_literal144_tree=null;
        RewriteRuleTokenStream stream_127=new RewriteRuleTokenStream(adaptor,"token 127");
        RewriteRuleTokenStream stream_128=new RewriteRuleTokenStream(adaptor,"token 128");
        RewriteRuleTokenStream stream_83=new RewriteRuleTokenStream(adaptor,"token 83");
        RewriteRuleTokenStream stream_IDENTIFIER=new RewriteRuleTokenStream(adaptor,"token IDENTIFIER");
        RewriteRuleTokenStream stream_129=new RewriteRuleTokenStream(adaptor,"token 129");
        RewriteRuleTokenStream stream_84=new RewriteRuleTokenStream(adaptor,"token 84");
        RewriteRuleTokenStream stream_89=new RewriteRuleTokenStream(adaptor,"token 89");
        RewriteRuleSubtreeStream stream_declarator=new RewriteRuleSubtreeStream(adaptor,"rule declarator");
        RewriteRuleSubtreeStream stream_assignmentExpression=new RewriteRuleSubtreeStream(adaptor,"rule assignmentExpression");
        RewriteRuleSubtreeStream stream_identifierList=new RewriteRuleSubtreeStream(adaptor,"rule identifierList");
        RewriteRuleSubtreeStream stream_attributes=new RewriteRuleSubtreeStream(adaptor,"rule attributes");
        RewriteRuleSubtreeStream stream_parameterTypeList=new RewriteRuleSubtreeStream(adaptor,"rule parameterTypeList");
        RewriteRuleSubtreeStream stream_typeQualifier=new RewriteRuleSubtreeStream(adaptor,"rule typeQualifier");
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 26) ) { return retval; }
            // GNUCa.g:346:2: ( ( IDENTIFIER -> IDENTIFIER | '(' ( attributes )? declarator ')' -> declarator ) ( '[' (tq+= typeQualifier )* (ae= assignmentExpression )? ']' -> ^( ARRAY_DECLARATOR $directDeclarator ( $tq)* ( $ae)? ) | '[' 'static' (tq+= typeQualifier )* ae= assignmentExpression ']' -> ^( ARRAY_DECLARATOR $directDeclarator 'static' ( $tq)* $ae) | '[' (tq+= typeQualifier )+ 'static' ae= assignmentExpression ']' -> ^( ARRAY_DECLARATOR $directDeclarator 'static' ( $tq)+ $ae) | '[' (tq+= typeQualifier )* '*' ']' -> ^( ARRAY_DECLARATOR $directDeclarator '*' ( $tq)* ) | '(' parameterTypeList ')' -> ^( FUNCTION_DECLARATOR $directDeclarator parameterTypeList ) | '(' ( identifierList )? ')' -> ^( FUNCTION_DECLARATOR $directDeclarator ( identifierList )? ) )* )
            // GNUCa.g:346:5: ( IDENTIFIER -> IDENTIFIER | '(' ( attributes )? declarator ')' -> declarator ) ( '[' (tq+= typeQualifier )* (ae= assignmentExpression )? ']' -> ^( ARRAY_DECLARATOR $directDeclarator ( $tq)* ( $ae)? ) | '[' 'static' (tq+= typeQualifier )* ae= assignmentExpression ']' -> ^( ARRAY_DECLARATOR $directDeclarator 'static' ( $tq)* $ae) | '[' (tq+= typeQualifier )+ 'static' ae= assignmentExpression ']' -> ^( ARRAY_DECLARATOR $directDeclarator 'static' ( $tq)+ $ae) | '[' (tq+= typeQualifier )* '*' ']' -> ^( ARRAY_DECLARATOR $directDeclarator '*' ( $tq)* ) | '(' parameterTypeList ')' -> ^( FUNCTION_DECLARATOR $directDeclarator parameterTypeList ) | '(' ( identifierList )? ')' -> ^( FUNCTION_DECLARATOR $directDeclarator ( identifierList )? ) )*
            {
            // GNUCa.g:346:5: ( IDENTIFIER -> IDENTIFIER | '(' ( attributes )? declarator ')' -> declarator )
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( (LA43_0==IDENTIFIER) ) {
                alt43=1;
            }
            else if ( (LA43_0==83) ) {
                alt43=2;
            }
            else {
                if (backtracking>0) {failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("346:5: ( IDENTIFIER -> IDENTIFIER | '(' ( attributes )? declarator ')' -> declarator )", 43, 0, input);

                throw nvae;
            }
            switch (alt43) {
                case 1 :
                    // GNUCa.g:346:7: IDENTIFIER
                    {
                    IDENTIFIER123=(Token)input.LT(1);
                    match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_directDeclarator1966); if (failed) return retval;
                    if ( backtracking==0 ) stream_IDENTIFIER.add(IDENTIFIER123);

                    if ( backtracking==0 ) {

                      			if (declaration_stack.size()>0&&((declaration_scope)declaration_stack.peek()).isTypedef) {
                      				((Symbols_scope)Symbols_stack.peek()).types.add(IDENTIFIER123.getText());
                      //				System.out.println("define type "+IDENTIFIER123.getText());
                      			}
                      			
                    }

                    // AST REWRITE
                    // elements: IDENTIFIER
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 352:6: -> IDENTIFIER
                    {
                        adaptor.addChild(root_0, stream_IDENTIFIER.next());

                    }

                    }

                    }
                    break;
                case 2 :
                    // GNUCa.g:353:4: '(' ( attributes )? declarator ')'
                    {
                    char_literal124=(Token)input.LT(1);
                    match(input,83,FOLLOW_83_in_directDeclarator1980); if (failed) return retval;
                    if ( backtracking==0 ) stream_83.add(char_literal124);

                    // GNUCa.g:353:8: ( attributes )?
                    int alt42=2;
                    int LA42_0 = input.LA(1);

                    if ( ((LA42_0>=135 && LA42_0<=136)) ) {
                        alt42=1;
                    }
                    switch (alt42) {
                        case 1 :
                            // GNUCa.g:0:0: attributes
                            {
                            pushFollow(FOLLOW_attributes_in_directDeclarator1982);
                            attributes125=attributes();
                            _fsp--;
                            if (failed) return retval;
                            if ( backtracking==0 ) stream_attributes.add(attributes125.getTree());

                            }
                            break;

                    }

                    pushFollow(FOLLOW_declarator_in_directDeclarator1985);
                    declarator126=declarator();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_declarator.add(declarator126.getTree());
                    char_literal127=(Token)input.LT(1);
                    match(input,84,FOLLOW_84_in_directDeclarator1987); if (failed) return retval;
                    if ( backtracking==0 ) stream_84.add(char_literal127);


                    // AST REWRITE
                    // elements: declarator
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 353:35: -> declarator
                    {
                        adaptor.addChild(root_0, stream_declarator.next());

                    }

                    }

                    }
                    break;

            }

            if ( backtracking==0 ) {
              if (declaration_stack.size()>0) { ((declaration_scope)declaration_stack.peek()).isTypedef =false;}
            }
            // GNUCa.g:357:2: ( '[' (tq+= typeQualifier )* (ae= assignmentExpression )? ']' -> ^( ARRAY_DECLARATOR $directDeclarator ( $tq)* ( $ae)? ) | '[' 'static' (tq+= typeQualifier )* ae= assignmentExpression ']' -> ^( ARRAY_DECLARATOR $directDeclarator 'static' ( $tq)* $ae) | '[' (tq+= typeQualifier )+ 'static' ae= assignmentExpression ']' -> ^( ARRAY_DECLARATOR $directDeclarator 'static' ( $tq)+ $ae) | '[' (tq+= typeQualifier )* '*' ']' -> ^( ARRAY_DECLARATOR $directDeclarator '*' ( $tq)* ) | '(' parameterTypeList ')' -> ^( FUNCTION_DECLARATOR $directDeclarator parameterTypeList ) | '(' ( identifierList )? ')' -> ^( FUNCTION_DECLARATOR $directDeclarator ( identifierList )? ) )*
            loop50:
            do {
                int alt50=7;
                int LA50_0 = input.LA(1);

                if ( (LA50_0==127) ) {
                    switch ( input.LA(2) ) {
                    case 89:
                        {
                        alt50=2;
                        }
                        break;
                    case 115:
                        {
                        int LA50_52 = input.LA(3);

                        if ( (synpred92()) ) {
                            alt50=1;
                        }
                        else if ( (synpred96()) ) {
                            alt50=3;
                        }
                        else if ( (synpred98()) ) {
                            alt50=4;
                        }


                        }
                        break;
                    case 116:
                        {
                        int LA50_53 = input.LA(3);

                        if ( (synpred92()) ) {
                            alt50=1;
                        }
                        else if ( (synpred96()) ) {
                            alt50=3;
                        }
                        else if ( (synpred98()) ) {
                            alt50=4;
                        }


                        }
                        break;
                    case 117:
                        {
                        int LA50_54 = input.LA(3);

                        if ( (synpred92()) ) {
                            alt50=1;
                        }
                        else if ( (synpred96()) ) {
                            alt50=3;
                        }
                        else if ( (synpred98()) ) {
                            alt50=4;
                        }


                        }
                        break;
                    case 118:
                        {
                        int LA50_55 = input.LA(3);

                        if ( (synpred92()) ) {
                            alt50=1;
                        }
                        else if ( (synpred96()) ) {
                            alt50=3;
                        }
                        else if ( (synpred98()) ) {
                            alt50=4;
                        }


                        }
                        break;
                    case 119:
                        {
                        int LA50_56 = input.LA(3);

                        if ( (synpred92()) ) {
                            alt50=1;
                        }
                        else if ( (synpred96()) ) {
                            alt50=3;
                        }
                        else if ( (synpred98()) ) {
                            alt50=4;
                        }


                        }
                        break;
                    case 120:
                        {
                        int LA50_57 = input.LA(3);

                        if ( (synpred92()) ) {
                            alt50=1;
                        }
                        else if ( (synpred96()) ) {
                            alt50=3;
                        }
                        else if ( (synpred98()) ) {
                            alt50=4;
                        }


                        }
                        break;
                    case 121:
                        {
                        int LA50_58 = input.LA(3);

                        if ( (synpred92()) ) {
                            alt50=1;
                        }
                        else if ( (synpred96()) ) {
                            alt50=3;
                        }
                        else if ( (synpred98()) ) {
                            alt50=4;
                        }


                        }
                        break;
                    case 122:
                        {
                        int LA50_59 = input.LA(3);

                        if ( (synpred92()) ) {
                            alt50=1;
                        }
                        else if ( (synpred96()) ) {
                            alt50=3;
                        }
                        else if ( (synpred98()) ) {
                            alt50=4;
                        }


                        }
                        break;
                    case 123:
                        {
                        int LA50_60 = input.LA(3);

                        if ( (synpred92()) ) {
                            alt50=1;
                        }
                        else if ( (synpred96()) ) {
                            alt50=3;
                        }
                        else if ( (synpred98()) ) {
                            alt50=4;
                        }


                        }
                        break;
                    case 129:
                        {
                        int LA50_61 = input.LA(3);

                        if ( (synpred92()) ) {
                            alt50=1;
                        }
                        else if ( (synpred98()) ) {
                            alt50=4;
                        }


                        }
                        break;
                    case IDENTIFIER:
                    case CONSTANT:
                    case STRING_LITERAL:
                    case 83:
                    case 128:
                    case 137:
                    case 139:
                    case 140:
                    case 141:
                    case 142:
                    case 143:
                    case 144:
                    case 145:
                    case 146:
                    case 147:
                    case 148:
                    case 149:
                    case 150:
                    case 151:
                    case 152:
                    case 153:
                        {
                        alt50=1;
                        }
                        break;

                    }

                }
                else if ( (LA50_0==83) ) {
                    switch ( input.LA(2) ) {
                    case 88:
                    case 89:
                    case 90:
                    case 91:
                    case 92:
                    case 93:
                    case 94:
                    case 95:
                    case 96:
                    case 97:
                    case 98:
                    case 99:
                    case 100:
                    case 101:
                    case 102:
                    case 103:
                    case 104:
                    case 105:
                    case 106:
                    case 107:
                    case 108:
                    case 111:
                    case 112:
                    case 114:
                    case 115:
                    case 116:
                    case 117:
                    case 118:
                    case 119:
                    case 120:
                    case 121:
                    case 122:
                    case 123:
                    case 124:
                    case 125:
                    case 126:
                    case 131:
                    case 132:
                    case 133:
                    case 135:
                    case 136:
                        {
                        alt50=5;
                        }
                        break;
                    case IDENTIFIER:
                        {
                        int LA50_102 = input.LA(3);

                        if ( (synpred99()) ) {
                            alt50=5;
                        }
                        else if ( (synpred101()) ) {
                            alt50=6;
                        }


                        }
                        break;
                    case 84:
                        {
                        alt50=6;
                        }
                        break;

                    }

                }


                switch (alt50) {
            	case 1 :
            	    // GNUCa.g:357:5: '[' (tq+= typeQualifier )* (ae= assignmentExpression )? ']'
            	    {
            	    char_literal128=(Token)input.LT(1);
            	    match(input,127,FOLLOW_127_in_directDeclarator2010); if (failed) return retval;
            	    if ( backtracking==0 ) stream_127.add(char_literal128);

            	    if ( backtracking==0 ) {
            	       list_tq = null; 
            	    }
            	    // GNUCa.g:357:31: (tq+= typeQualifier )*
            	    loop44:
            	    do {
            	        int alt44=2;
            	        int LA44_0 = input.LA(1);

            	        if ( ((LA44_0>=115 && LA44_0<=123)) ) {
            	            alt44=1;
            	        }


            	        switch (alt44) {
            	    	case 1 :
            	    	    // GNUCa.g:0:0: tq+= typeQualifier
            	    	    {
            	    	    pushFollow(FOLLOW_typeQualifier_in_directDeclarator2016);
            	    	    tq=typeQualifier();
            	    	    _fsp--;
            	    	    if (failed) return retval;
            	    	    if ( backtracking==0 ) stream_typeQualifier.add(tq.getTree());
            	    	    if (list_tq==null) list_tq=new ArrayList();
            	    	    list_tq.add(tq);


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop44;
            	        }
            	    } while (true);

            	    // GNUCa.g:357:50: (ae= assignmentExpression )?
            	    int alt45=2;
            	    int LA45_0 = input.LA(1);

            	    if ( ((LA45_0>=IDENTIFIER && LA45_0<=STRING_LITERAL)||LA45_0==83||LA45_0==129||LA45_0==137||(LA45_0>=139 && LA45_0<=153)) ) {
            	        alt45=1;
            	    }
            	    switch (alt45) {
            	        case 1 :
            	            // GNUCa.g:0:0: ae= assignmentExpression
            	            {
            	            pushFollow(FOLLOW_assignmentExpression_in_directDeclarator2021);
            	            ae=assignmentExpression();
            	            _fsp--;
            	            if (failed) return retval;
            	            if ( backtracking==0 ) stream_assignmentExpression.add(ae.getTree());

            	            }
            	            break;

            	    }

            	    char_literal129=(Token)input.LT(1);
            	    match(input,128,FOLLOW_128_in_directDeclarator2024); if (failed) return retval;
            	    if ( backtracking==0 ) stream_128.add(char_literal129);


            	    // AST REWRITE
            	    // elements: ae, directDeclarator, tq
            	    // token labels: 
            	    // rule labels: retval, ae
            	    // token list labels: 
            	    // rule list labels: tq
            	    if ( backtracking==0 ) {
            	    retval.tree = root_0;
            	    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);
            	    RewriteRuleSubtreeStream stream_ae=new RewriteRuleSubtreeStream(adaptor,"token ae",ae!=null?ae.tree:null);
            	    RewriteRuleSubtreeStream stream_tq=new RewriteRuleSubtreeStream(adaptor,"token tq",list_tq);
            	    root_0 = (Object)adaptor.nil();
            	    // 357:77: -> ^( ARRAY_DECLARATOR $directDeclarator ( $tq)* ( $ae)? )
            	    {
            	        // GNUCa.g:357:80: ^( ARRAY_DECLARATOR $directDeclarator ( $tq)* ( $ae)? )
            	        {
            	        Object root_1 = (Object)adaptor.nil();
            	        root_1 = (Object)adaptor.becomeRoot(adaptor.create(ARRAY_DECLARATOR, "ARRAY_DECLARATOR"), root_1);

            	        adaptor.addChild(root_1, stream_retval.next());
            	        // GNUCa.g:357:117: ( $tq)*
            	        while ( stream_tq.hasNext() ) {
            	            adaptor.addChild(root_1, ((ParserRuleReturnScope)stream_tq.next()).getTree());

            	        }
            	        stream_tq.reset();
            	        // GNUCa.g:357:122: ( $ae)?
            	        if ( stream_ae.hasNext() ) {
            	            adaptor.addChild(root_1, stream_ae.next());

            	        }
            	        stream_ae.reset();

            	        adaptor.addChild(root_0, root_1);
            	        }

            	    }

            	    }

            	    }
            	    break;
            	case 2 :
            	    // GNUCa.g:358:4: '[' 'static' (tq+= typeQualifier )* ae= assignmentExpression ']'
            	    {
            	    char_literal130=(Token)input.LT(1);
            	    match(input,127,FOLLOW_127_in_directDeclarator2046); if (failed) return retval;
            	    if ( backtracking==0 ) stream_127.add(char_literal130);

            	    if ( backtracking==0 ) {
            	       list_tq = null; 
            	    }
            	    string_literal131=(Token)input.LT(1);
            	    match(input,89,FOLLOW_89_in_directDeclarator2050); if (failed) return retval;
            	    if ( backtracking==0 ) stream_89.add(string_literal131);

            	    // GNUCa.g:358:39: (tq+= typeQualifier )*
            	    loop46:
            	    do {
            	        int alt46=2;
            	        int LA46_0 = input.LA(1);

            	        if ( ((LA46_0>=115 && LA46_0<=123)) ) {
            	            alt46=1;
            	        }


            	        switch (alt46) {
            	    	case 1 :
            	    	    // GNUCa.g:0:0: tq+= typeQualifier
            	    	    {
            	    	    pushFollow(FOLLOW_typeQualifier_in_directDeclarator2054);
            	    	    tq=typeQualifier();
            	    	    _fsp--;
            	    	    if (failed) return retval;
            	    	    if ( backtracking==0 ) stream_typeQualifier.add(tq.getTree());
            	    	    if (list_tq==null) list_tq=new ArrayList();
            	    	    list_tq.add(tq);


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop46;
            	        }
            	    } while (true);

            	    pushFollow(FOLLOW_assignmentExpression_in_directDeclarator2059);
            	    ae=assignmentExpression();
            	    _fsp--;
            	    if (failed) return retval;
            	    if ( backtracking==0 ) stream_assignmentExpression.add(ae.getTree());
            	    char_literal132=(Token)input.LT(1);
            	    match(input,128,FOLLOW_128_in_directDeclarator2061); if (failed) return retval;
            	    if ( backtracking==0 ) stream_128.add(char_literal132);


            	    // AST REWRITE
            	    // elements: directDeclarator, 89, tq, ae
            	    // token labels: 
            	    // rule labels: retval, ae
            	    // token list labels: 
            	    // rule list labels: tq
            	    if ( backtracking==0 ) {
            	    retval.tree = root_0;
            	    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);
            	    RewriteRuleSubtreeStream stream_ae=new RewriteRuleSubtreeStream(adaptor,"token ae",ae!=null?ae.tree:null);
            	    RewriteRuleSubtreeStream stream_tq=new RewriteRuleSubtreeStream(adaptor,"token tq",list_tq);
            	    root_0 = (Object)adaptor.nil();
            	    // 358:84: -> ^( ARRAY_DECLARATOR $directDeclarator 'static' ( $tq)* $ae)
            	    {
            	        // GNUCa.g:358:87: ^( ARRAY_DECLARATOR $directDeclarator 'static' ( $tq)* $ae)
            	        {
            	        Object root_1 = (Object)adaptor.nil();
            	        root_1 = (Object)adaptor.becomeRoot(adaptor.create(ARRAY_DECLARATOR, "ARRAY_DECLARATOR"), root_1);

            	        adaptor.addChild(root_1, stream_retval.next());
            	        adaptor.addChild(root_1, stream_89.next());
            	        // GNUCa.g:358:133: ( $tq)*
            	        while ( stream_tq.hasNext() ) {
            	            adaptor.addChild(root_1, ((ParserRuleReturnScope)stream_tq.next()).getTree());

            	        }
            	        stream_tq.reset();
            	        adaptor.addChild(root_1, stream_ae.next());

            	        adaptor.addChild(root_0, root_1);
            	        }

            	    }

            	    }

            	    }
            	    break;
            	case 3 :
            	    // GNUCa.g:359:4: '[' (tq+= typeQualifier )+ 'static' ae= assignmentExpression ']'
            	    {
            	    char_literal133=(Token)input.LT(1);
            	    match(input,127,FOLLOW_127_in_directDeclarator2084); if (failed) return retval;
            	    if ( backtracking==0 ) stream_127.add(char_literal133);

            	    if ( backtracking==0 ) {
            	       list_tq = null; 
            	    }
            	    // GNUCa.g:359:30: (tq+= typeQualifier )+
            	    int cnt47=0;
            	    loop47:
            	    do {
            	        int alt47=2;
            	        int LA47_0 = input.LA(1);

            	        if ( ((LA47_0>=115 && LA47_0<=123)) ) {
            	            alt47=1;
            	        }


            	        switch (alt47) {
            	    	case 1 :
            	    	    // GNUCa.g:0:0: tq+= typeQualifier
            	    	    {
            	    	    pushFollow(FOLLOW_typeQualifier_in_directDeclarator2090);
            	    	    tq=typeQualifier();
            	    	    _fsp--;
            	    	    if (failed) return retval;
            	    	    if ( backtracking==0 ) stream_typeQualifier.add(tq.getTree());
            	    	    if (list_tq==null) list_tq=new ArrayList();
            	    	    list_tq.add(tq);


            	    	    }
            	    	    break;

            	    	default :
            	    	    if ( cnt47 >= 1 ) break loop47;
            	    	    if (backtracking>0) {failed=true; return retval;}
            	                EarlyExitException eee =
            	                    new EarlyExitException(47, input);
            	                throw eee;
            	        }
            	        cnt47++;
            	    } while (true);

            	    string_literal134=(Token)input.LT(1);
            	    match(input,89,FOLLOW_89_in_directDeclarator2093); if (failed) return retval;
            	    if ( backtracking==0 ) stream_89.add(string_literal134);

            	    pushFollow(FOLLOW_assignmentExpression_in_directDeclarator2097);
            	    ae=assignmentExpression();
            	    _fsp--;
            	    if (failed) return retval;
            	    if ( backtracking==0 ) stream_assignmentExpression.add(ae.getTree());
            	    char_literal135=(Token)input.LT(1);
            	    match(input,128,FOLLOW_128_in_directDeclarator2099); if (failed) return retval;
            	    if ( backtracking==0 ) stream_128.add(char_literal135);


            	    // AST REWRITE
            	    // elements: directDeclarator, tq, 89, ae
            	    // token labels: 
            	    // rule labels: retval, ae
            	    // token list labels: 
            	    // rule list labels: tq
            	    if ( backtracking==0 ) {
            	    retval.tree = root_0;
            	    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);
            	    RewriteRuleSubtreeStream stream_ae=new RewriteRuleSubtreeStream(adaptor,"token ae",ae!=null?ae.tree:null);
            	    RewriteRuleSubtreeStream stream_tq=new RewriteRuleSubtreeStream(adaptor,"token tq",list_tq);
            	    root_0 = (Object)adaptor.nil();
            	    // 359:84: -> ^( ARRAY_DECLARATOR $directDeclarator 'static' ( $tq)+ $ae)
            	    {
            	        // GNUCa.g:359:87: ^( ARRAY_DECLARATOR $directDeclarator 'static' ( $tq)+ $ae)
            	        {
            	        Object root_1 = (Object)adaptor.nil();
            	        root_1 = (Object)adaptor.becomeRoot(adaptor.create(ARRAY_DECLARATOR, "ARRAY_DECLARATOR"), root_1);

            	        adaptor.addChild(root_1, stream_retval.next());
            	        adaptor.addChild(root_1, stream_89.next());
            	        if ( !(stream_tq.hasNext()) ) {
            	            throw new RewriteEarlyExitException();
            	        }
            	        while ( stream_tq.hasNext() ) {
            	            adaptor.addChild(root_1, ((ParserRuleReturnScope)stream_tq.next()).getTree());

            	        }
            	        stream_tq.reset();
            	        adaptor.addChild(root_1, stream_ae.next());

            	        adaptor.addChild(root_0, root_1);
            	        }

            	    }

            	    }

            	    }
            	    break;
            	case 4 :
            	    // GNUCa.g:360:4: '[' (tq+= typeQualifier )* '*' ']'
            	    {
            	    char_literal136=(Token)input.LT(1);
            	    match(input,127,FOLLOW_127_in_directDeclarator2122); if (failed) return retval;
            	    if ( backtracking==0 ) stream_127.add(char_literal136);

            	    if ( backtracking==0 ) {
            	       list_tq = null; 
            	    }
            	    // GNUCa.g:360:30: (tq+= typeQualifier )*
            	    loop48:
            	    do {
            	        int alt48=2;
            	        int LA48_0 = input.LA(1);

            	        if ( ((LA48_0>=115 && LA48_0<=123)) ) {
            	            alt48=1;
            	        }


            	        switch (alt48) {
            	    	case 1 :
            	    	    // GNUCa.g:0:0: tq+= typeQualifier
            	    	    {
            	    	    pushFollow(FOLLOW_typeQualifier_in_directDeclarator2128);
            	    	    tq=typeQualifier();
            	    	    _fsp--;
            	    	    if (failed) return retval;
            	    	    if ( backtracking==0 ) stream_typeQualifier.add(tq.getTree());
            	    	    if (list_tq==null) list_tq=new ArrayList();
            	    	    list_tq.add(tq);


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop48;
            	        }
            	    } while (true);

            	    char_literal137=(Token)input.LT(1);
            	    match(input,129,FOLLOW_129_in_directDeclarator2131); if (failed) return retval;
            	    if ( backtracking==0 ) stream_129.add(char_literal137);

            	    char_literal138=(Token)input.LT(1);
            	    match(input,128,FOLLOW_128_in_directDeclarator2133); if (failed) return retval;
            	    if ( backtracking==0 ) stream_128.add(char_literal138);


            	    // AST REWRITE
            	    // elements: tq, 129, directDeclarator
            	    // token labels: 
            	    // rule labels: retval
            	    // token list labels: 
            	    // rule list labels: tq
            	    if ( backtracking==0 ) {
            	    retval.tree = root_0;
            	    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);
            	    RewriteRuleSubtreeStream stream_tq=new RewriteRuleSubtreeStream(adaptor,"token tq",list_tq);
            	    root_0 = (Object)adaptor.nil();
            	    // 360:55: -> ^( ARRAY_DECLARATOR $directDeclarator '*' ( $tq)* )
            	    {
            	        // GNUCa.g:360:58: ^( ARRAY_DECLARATOR $directDeclarator '*' ( $tq)* )
            	        {
            	        Object root_1 = (Object)adaptor.nil();
            	        root_1 = (Object)adaptor.becomeRoot(adaptor.create(ARRAY_DECLARATOR, "ARRAY_DECLARATOR"), root_1);

            	        adaptor.addChild(root_1, stream_retval.next());
            	        adaptor.addChild(root_1, stream_129.next());
            	        // GNUCa.g:360:99: ( $tq)*
            	        while ( stream_tq.hasNext() ) {
            	            adaptor.addChild(root_1, ((ParserRuleReturnScope)stream_tq.next()).getTree());

            	        }
            	        stream_tq.reset();

            	        adaptor.addChild(root_0, root_1);
            	        }

            	    }

            	    }

            	    }
            	    break;
            	case 5 :
            	    // GNUCa.g:361:4: '(' parameterTypeList ')'
            	    {
            	    char_literal139=(Token)input.LT(1);
            	    match(input,83,FOLLOW_83_in_directDeclarator2153); if (failed) return retval;
            	    if ( backtracking==0 ) stream_83.add(char_literal139);

            	    pushFollow(FOLLOW_parameterTypeList_in_directDeclarator2155);
            	    parameterTypeList140=parameterTypeList();
            	    _fsp--;
            	    if (failed) return retval;
            	    if ( backtracking==0 ) stream_parameterTypeList.add(parameterTypeList140.getTree());
            	    char_literal141=(Token)input.LT(1);
            	    match(input,84,FOLLOW_84_in_directDeclarator2157); if (failed) return retval;
            	    if ( backtracking==0 ) stream_84.add(char_literal141);


            	    // AST REWRITE
            	    // elements: parameterTypeList, directDeclarator
            	    // token labels: 
            	    // rule labels: retval
            	    // token list labels: 
            	    // rule list labels: 
            	    if ( backtracking==0 ) {
            	    retval.tree = root_0;
            	    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            	    root_0 = (Object)adaptor.nil();
            	    // 361:30: -> ^( FUNCTION_DECLARATOR $directDeclarator parameterTypeList )
            	    {
            	        // GNUCa.g:361:33: ^( FUNCTION_DECLARATOR $directDeclarator parameterTypeList )
            	        {
            	        Object root_1 = (Object)adaptor.nil();
            	        root_1 = (Object)adaptor.becomeRoot(adaptor.create(FUNCTION_DECLARATOR, "FUNCTION_DECLARATOR"), root_1);

            	        adaptor.addChild(root_1, stream_retval.next());
            	        adaptor.addChild(root_1, stream_parameterTypeList.next());

            	        adaptor.addChild(root_0, root_1);
            	        }

            	    }

            	    }

            	    }
            	    break;
            	case 6 :
            	    // GNUCa.g:362:4: '(' ( identifierList )? ')'
            	    {
            	    char_literal142=(Token)input.LT(1);
            	    match(input,83,FOLLOW_83_in_directDeclarator2173); if (failed) return retval;
            	    if ( backtracking==0 ) stream_83.add(char_literal142);

            	    // GNUCa.g:362:8: ( identifierList )?
            	    int alt49=2;
            	    int LA49_0 = input.LA(1);

            	    if ( (LA49_0==IDENTIFIER) ) {
            	        alt49=1;
            	    }
            	    switch (alt49) {
            	        case 1 :
            	            // GNUCa.g:0:0: identifierList
            	            {
            	            pushFollow(FOLLOW_identifierList_in_directDeclarator2175);
            	            identifierList143=identifierList();
            	            _fsp--;
            	            if (failed) return retval;
            	            if ( backtracking==0 ) stream_identifierList.add(identifierList143.getTree());

            	            }
            	            break;

            	    }

            	    char_literal144=(Token)input.LT(1);
            	    match(input,84,FOLLOW_84_in_directDeclarator2178); if (failed) return retval;
            	    if ( backtracking==0 ) stream_84.add(char_literal144);


            	    // AST REWRITE
            	    // elements: directDeclarator, identifierList
            	    // token labels: 
            	    // rule labels: retval
            	    // token list labels: 
            	    // rule list labels: 
            	    if ( backtracking==0 ) {
            	    retval.tree = root_0;
            	    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            	    root_0 = (Object)adaptor.nil();
            	    // 362:28: -> ^( FUNCTION_DECLARATOR $directDeclarator ( identifierList )? )
            	    {
            	        // GNUCa.g:362:31: ^( FUNCTION_DECLARATOR $directDeclarator ( identifierList )? )
            	        {
            	        Object root_1 = (Object)adaptor.nil();
            	        root_1 = (Object)adaptor.becomeRoot(adaptor.create(FUNCTION_DECLARATOR, "FUNCTION_DECLARATOR"), root_1);

            	        adaptor.addChild(root_1, stream_retval.next());
            	        // GNUCa.g:362:71: ( identifierList )?
            	        if ( stream_identifierList.hasNext() ) {
            	            adaptor.addChild(root_1, stream_identifierList.next());

            	        }
            	        stream_identifierList.reset();

            	        adaptor.addChild(root_0, root_1);
            	        }

            	    }

            	    }

            	    }
            	    break;

            	default :
            	    break loop50;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 26, directDeclarator_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end directDeclarator

    public static class pointer_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start pointer
    // GNUCa.g:366:1: pointer : '*' ( typeQualifier )* ( pointer )? -> ^( POINTER ( typeQualifier )* ( pointer )? ) ;
    public final pointer_return pointer() throws RecognitionException {
        pointer_return retval = new pointer_return();
        retval.start = input.LT(1);
        int pointer_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal145=null;
        typeQualifier_return typeQualifier146 = null;

        pointer_return pointer147 = null;


        Object char_literal145_tree=null;
        RewriteRuleTokenStream stream_129=new RewriteRuleTokenStream(adaptor,"token 129");
        RewriteRuleSubtreeStream stream_pointer=new RewriteRuleSubtreeStream(adaptor,"rule pointer");
        RewriteRuleSubtreeStream stream_typeQualifier=new RewriteRuleSubtreeStream(adaptor,"rule typeQualifier");
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 27) ) { return retval; }
            // GNUCa.g:367:2: ( '*' ( typeQualifier )* ( pointer )? -> ^( POINTER ( typeQualifier )* ( pointer )? ) )
            // GNUCa.g:367:4: '*' ( typeQualifier )* ( pointer )?
            {
            char_literal145=(Token)input.LT(1);
            match(input,129,FOLLOW_129_in_pointer2206); if (failed) return retval;
            if ( backtracking==0 ) stream_129.add(char_literal145);

            // GNUCa.g:367:8: ( typeQualifier )*
            loop51:
            do {
                int alt51=2;
                int LA51_0 = input.LA(1);

                if ( ((LA51_0>=115 && LA51_0<=123)) ) {
                    alt51=1;
                }


                switch (alt51) {
            	case 1 :
            	    // GNUCa.g:0:0: typeQualifier
            	    {
            	    pushFollow(FOLLOW_typeQualifier_in_pointer2208);
            	    typeQualifier146=typeQualifier();
            	    _fsp--;
            	    if (failed) return retval;
            	    if ( backtracking==0 ) stream_typeQualifier.add(typeQualifier146.getTree());

            	    }
            	    break;

            	default :
            	    break loop51;
                }
            } while (true);

            // GNUCa.g:367:23: ( pointer )?
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( (LA52_0==129) ) {
                alt52=1;
            }
            switch (alt52) {
                case 1 :
                    // GNUCa.g:0:0: pointer
                    {
                    pushFollow(FOLLOW_pointer_in_pointer2211);
                    pointer147=pointer();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_pointer.add(pointer147.getTree());

                    }
                    break;

            }


            // AST REWRITE
            // elements: pointer, typeQualifier
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 367:32: -> ^( POINTER ( typeQualifier )* ( pointer )? )
            {
                // GNUCa.g:367:35: ^( POINTER ( typeQualifier )* ( pointer )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(adaptor.create(POINTER, "POINTER"), root_1);

                // GNUCa.g:367:45: ( typeQualifier )*
                while ( stream_typeQualifier.hasNext() ) {
                    adaptor.addChild(root_1, stream_typeQualifier.next());

                }
                stream_typeQualifier.reset();
                // GNUCa.g:367:60: ( pointer )?
                if ( stream_pointer.hasNext() ) {
                    adaptor.addChild(root_1, stream_pointer.next());

                }
                stream_pointer.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            }

            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 27, pointer_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end pointer

    public static class parameterTypeList_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start parameterTypeList
    // GNUCa.g:370:1: parameterTypeList : ( parameterList -> parameterList ) ( ( ',' '...' ) -> parameterList VARARGS )? ;
    public final parameterTypeList_return parameterTypeList() throws RecognitionException {
        parameterTypeList_return retval = new parameterTypeList_return();
        retval.start = input.LT(1);
        int parameterTypeList_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal149=null;
        Token string_literal150=null;
        parameterList_return parameterList148 = null;


        Object char_literal149_tree=null;
        Object string_literal150_tree=null;
        RewriteRuleTokenStream stream_86=new RewriteRuleTokenStream(adaptor,"token 86");
        RewriteRuleTokenStream stream_130=new RewriteRuleTokenStream(adaptor,"token 130");
        RewriteRuleSubtreeStream stream_parameterList=new RewriteRuleSubtreeStream(adaptor,"rule parameterList");
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 28) ) { return retval; }
            // GNUCa.g:371:2: ( ( parameterList -> parameterList ) ( ( ',' '...' ) -> parameterList VARARGS )? )
            // GNUCa.g:371:4: ( parameterList -> parameterList ) ( ( ',' '...' ) -> parameterList VARARGS )?
            {
            // GNUCa.g:371:4: ( parameterList -> parameterList )
            // GNUCa.g:371:5: parameterList
            {
            pushFollow(FOLLOW_parameterList_in_parameterTypeList2238);
            parameterList148=parameterList();
            _fsp--;
            if (failed) return retval;
            if ( backtracking==0 ) stream_parameterList.add(parameterList148.getTree());

            // AST REWRITE
            // elements: parameterList
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 371:19: -> parameterList
            {
                adaptor.addChild(root_0, stream_parameterList.next());

            }

            }

            }

            // GNUCa.g:371:37: ( ( ',' '...' ) -> parameterList VARARGS )?
            int alt53=2;
            int LA53_0 = input.LA(1);

            if ( (LA53_0==86) ) {
                alt53=1;
            }
            switch (alt53) {
                case 1 :
                    // GNUCa.g:371:38: ( ',' '...' )
                    {
                    // GNUCa.g:371:38: ( ',' '...' )
                    // GNUCa.g:371:39: ',' '...'
                    {
                    char_literal149=(Token)input.LT(1);
                    match(input,86,FOLLOW_86_in_parameterTypeList2247); if (failed) return retval;
                    if ( backtracking==0 ) stream_86.add(char_literal149);

                    string_literal150=(Token)input.LT(1);
                    match(input,130,FOLLOW_130_in_parameterTypeList2249); if (failed) return retval;
                    if ( backtracking==0 ) stream_130.add(string_literal150);


                    }


                    // AST REWRITE
                    // elements: parameterList
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 371:50: -> parameterList VARARGS
                    {
                        adaptor.addChild(root_0, stream_parameterList.next());
                        adaptor.addChild(root_0, adaptor.create(VARARGS, "VARARGS"));

                    }

                    }

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 28, parameterTypeList_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end parameterTypeList

    public static class parameterList_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start parameterList
    // GNUCa.g:374:1: parameterList : parameterDeclaration ( ',' parameterDeclaration )* -> ( parameterDeclaration )+ ;
    public final parameterList_return parameterList() throws RecognitionException {
        parameterList_return retval = new parameterList_return();
        retval.start = input.LT(1);
        int parameterList_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal152=null;
        parameterDeclaration_return parameterDeclaration151 = null;

        parameterDeclaration_return parameterDeclaration153 = null;


        Object char_literal152_tree=null;
        RewriteRuleTokenStream stream_86=new RewriteRuleTokenStream(adaptor,"token 86");
        RewriteRuleSubtreeStream stream_parameterDeclaration=new RewriteRuleSubtreeStream(adaptor,"rule parameterDeclaration");
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 29) ) { return retval; }
            // GNUCa.g:375:2: ( parameterDeclaration ( ',' parameterDeclaration )* -> ( parameterDeclaration )+ )
            // GNUCa.g:375:4: parameterDeclaration ( ',' parameterDeclaration )*
            {
            pushFollow(FOLLOW_parameterDeclaration_in_parameterList2269);
            parameterDeclaration151=parameterDeclaration();
            _fsp--;
            if (failed) return retval;
            if ( backtracking==0 ) stream_parameterDeclaration.add(parameterDeclaration151.getTree());
            // GNUCa.g:375:25: ( ',' parameterDeclaration )*
            loop54:
            do {
                int alt54=2;
                int LA54_0 = input.LA(1);

                if ( (LA54_0==86) ) {
                    int LA54_1 = input.LA(2);

                    if ( (LA54_1==IDENTIFIER||(LA54_1>=88 && LA54_1<=108)||(LA54_1>=111 && LA54_1<=112)||(LA54_1>=114 && LA54_1<=126)||(LA54_1>=131 && LA54_1<=133)||(LA54_1>=135 && LA54_1<=136)) ) {
                        alt54=1;
                    }


                }


                switch (alt54) {
            	case 1 :
            	    // GNUCa.g:375:27: ',' parameterDeclaration
            	    {
            	    char_literal152=(Token)input.LT(1);
            	    match(input,86,FOLLOW_86_in_parameterList2273); if (failed) return retval;
            	    if ( backtracking==0 ) stream_86.add(char_literal152);

            	    pushFollow(FOLLOW_parameterDeclaration_in_parameterList2275);
            	    parameterDeclaration153=parameterDeclaration();
            	    _fsp--;
            	    if (failed) return retval;
            	    if ( backtracking==0 ) stream_parameterDeclaration.add(parameterDeclaration153.getTree());

            	    }
            	    break;

            	default :
            	    break loop54;
                }
            } while (true);


            // AST REWRITE
            // elements: parameterDeclaration
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 375:55: -> ( parameterDeclaration )+
            {
                if ( !(stream_parameterDeclaration.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_parameterDeclaration.hasNext() ) {
                    adaptor.addChild(root_0, stream_parameterDeclaration.next());

                }
                stream_parameterDeclaration.reset();

            }

            }

            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 29, parameterList_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end parameterList

    public static class parameterDeclaration_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start parameterDeclaration
    // GNUCa.g:378:1: parameterDeclaration : declarationSpecifiers ( declarator | ( abstractDeclarator )? ) ( attributes )? -> ^( PARAMETER declarationSpecifiers ( declarator )? ( abstractDeclarator )? ) ;
    public final parameterDeclaration_return parameterDeclaration() throws RecognitionException {
        parameterDeclaration_return retval = new parameterDeclaration_return();
        retval.start = input.LT(1);
        int parameterDeclaration_StartIndex = input.index();
        Object root_0 = null;

        declarationSpecifiers_return declarationSpecifiers154 = null;

        declarator_return declarator155 = null;

        abstractDeclarator_return abstractDeclarator156 = null;

        attributes_return attributes157 = null;


        RewriteRuleSubtreeStream stream_declarator=new RewriteRuleSubtreeStream(adaptor,"rule declarator");
        RewriteRuleSubtreeStream stream_declarationSpecifiers=new RewriteRuleSubtreeStream(adaptor,"rule declarationSpecifiers");
        RewriteRuleSubtreeStream stream_attributes=new RewriteRuleSubtreeStream(adaptor,"rule attributes");
        RewriteRuleSubtreeStream stream_abstractDeclarator=new RewriteRuleSubtreeStream(adaptor,"rule abstractDeclarator");
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 30) ) { return retval; }
            // GNUCa.g:379:2: ( declarationSpecifiers ( declarator | ( abstractDeclarator )? ) ( attributes )? -> ^( PARAMETER declarationSpecifiers ( declarator )? ( abstractDeclarator )? ) )
            // GNUCa.g:379:4: declarationSpecifiers ( declarator | ( abstractDeclarator )? ) ( attributes )?
            {
            pushFollow(FOLLOW_declarationSpecifiers_in_parameterDeclaration2295);
            declarationSpecifiers154=declarationSpecifiers();
            _fsp--;
            if (failed) return retval;
            if ( backtracking==0 ) stream_declarationSpecifiers.add(declarationSpecifiers154.getTree());
            // GNUCa.g:379:26: ( declarator | ( abstractDeclarator )? )
            int alt56=2;
            switch ( input.LA(1) ) {
            case 129:
                {
                switch ( input.LA(2) ) {
                case 115:
                    {
                    int LA56_9 = input.LA(3);

                    if ( (synpred106()) ) {
                        alt56=1;
                    }
                    else if ( (true) ) {
                        alt56=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("379:26: ( declarator | ( abstractDeclarator )? )", 56, 9, input);

                        throw nvae;
                    }
                    }
                    break;
                case 116:
                    {
                    int LA56_10 = input.LA(3);

                    if ( (synpred106()) ) {
                        alt56=1;
                    }
                    else if ( (true) ) {
                        alt56=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("379:26: ( declarator | ( abstractDeclarator )? )", 56, 10, input);

                        throw nvae;
                    }
                    }
                    break;
                case 117:
                    {
                    int LA56_11 = input.LA(3);

                    if ( (synpred106()) ) {
                        alt56=1;
                    }
                    else if ( (true) ) {
                        alt56=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("379:26: ( declarator | ( abstractDeclarator )? )", 56, 11, input);

                        throw nvae;
                    }
                    }
                    break;
                case 118:
                    {
                    int LA56_12 = input.LA(3);

                    if ( (synpred106()) ) {
                        alt56=1;
                    }
                    else if ( (true) ) {
                        alt56=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("379:26: ( declarator | ( abstractDeclarator )? )", 56, 12, input);

                        throw nvae;
                    }
                    }
                    break;
                case 119:
                    {
                    int LA56_13 = input.LA(3);

                    if ( (synpred106()) ) {
                        alt56=1;
                    }
                    else if ( (true) ) {
                        alt56=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("379:26: ( declarator | ( abstractDeclarator )? )", 56, 13, input);

                        throw nvae;
                    }
                    }
                    break;
                case 120:
                    {
                    int LA56_14 = input.LA(3);

                    if ( (synpred106()) ) {
                        alt56=1;
                    }
                    else if ( (true) ) {
                        alt56=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("379:26: ( declarator | ( abstractDeclarator )? )", 56, 14, input);

                        throw nvae;
                    }
                    }
                    break;
                case 121:
                    {
                    int LA56_15 = input.LA(3);

                    if ( (synpred106()) ) {
                        alt56=1;
                    }
                    else if ( (true) ) {
                        alt56=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("379:26: ( declarator | ( abstractDeclarator )? )", 56, 15, input);

                        throw nvae;
                    }
                    }
                    break;
                case 122:
                    {
                    int LA56_16 = input.LA(3);

                    if ( (synpred106()) ) {
                        alt56=1;
                    }
                    else if ( (true) ) {
                        alt56=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("379:26: ( declarator | ( abstractDeclarator )? )", 56, 16, input);

                        throw nvae;
                    }
                    }
                    break;
                case 123:
                    {
                    int LA56_17 = input.LA(3);

                    if ( (synpred106()) ) {
                        alt56=1;
                    }
                    else if ( (true) ) {
                        alt56=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("379:26: ( declarator | ( abstractDeclarator )? )", 56, 17, input);

                        throw nvae;
                    }
                    }
                    break;
                case 129:
                    {
                    int LA56_18 = input.LA(3);

                    if ( (synpred106()) ) {
                        alt56=1;
                    }
                    else if ( (true) ) {
                        alt56=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("379:26: ( declarator | ( abstractDeclarator )? )", 56, 18, input);

                        throw nvae;
                    }
                    }
                    break;
                case 83:
                    {
                    int LA56_19 = input.LA(3);

                    if ( (synpred106()) ) {
                        alt56=1;
                    }
                    else if ( (true) ) {
                        alt56=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("379:26: ( declarator | ( abstractDeclarator )? )", 56, 19, input);

                        throw nvae;
                    }
                    }
                    break;
                case EOF:
                case 84:
                case 86:
                case 127:
                case 135:
                case 136:
                    {
                    alt56=2;
                    }
                    break;
                case IDENTIFIER:
                    {
                    alt56=1;
                    }
                    break;
                default:
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("379:26: ( declarator | ( abstractDeclarator )? )", 56, 1, input);

                    throw nvae;
                }

                }
                break;
            case IDENTIFIER:
                {
                alt56=1;
                }
                break;
            case 83:
                {
                switch ( input.LA(2) ) {
                case 84:
                case 88:
                case 89:
                case 90:
                case 91:
                case 92:
                case 93:
                case 94:
                case 95:
                case 96:
                case 97:
                case 98:
                case 99:
                case 100:
                case 101:
                case 102:
                case 103:
                case 104:
                case 105:
                case 106:
                case 107:
                case 108:
                case 111:
                case 112:
                case 114:
                case 115:
                case 116:
                case 117:
                case 118:
                case 119:
                case 120:
                case 121:
                case 122:
                case 123:
                case 124:
                case 125:
                case 126:
                case 127:
                case 131:
                case 132:
                case 133:
                    {
                    alt56=2;
                    }
                    break;
                case IDENTIFIER:
                    {
                    int LA56_45 = input.LA(3);

                    if ( (synpred106()) ) {
                        alt56=1;
                    }
                    else if ( (true) ) {
                        alt56=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("379:26: ( declarator | ( abstractDeclarator )? )", 56, 45, input);

                        throw nvae;
                    }
                    }
                    break;
                case 135:
                case 136:
                    {
                    int LA56_61 = input.LA(3);

                    if ( (synpred106()) ) {
                        alt56=1;
                    }
                    else if ( (true) ) {
                        alt56=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("379:26: ( declarator | ( abstractDeclarator )? )", 56, 61, input);

                        throw nvae;
                    }
                    }
                    break;
                case 129:
                    {
                    int LA56_63 = input.LA(3);

                    if ( (synpred106()) ) {
                        alt56=1;
                    }
                    else if ( (true) ) {
                        alt56=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("379:26: ( declarator | ( abstractDeclarator )? )", 56, 63, input);

                        throw nvae;
                    }
                    }
                    break;
                case 83:
                    {
                    int LA56_64 = input.LA(3);

                    if ( (synpred106()) ) {
                        alt56=1;
                    }
                    else if ( (true) ) {
                        alt56=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("379:26: ( declarator | ( abstractDeclarator )? )", 56, 64, input);

                        throw nvae;
                    }
                    }
                    break;
                default:
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("379:26: ( declarator | ( abstractDeclarator )? )", 56, 3, input);

                    throw nvae;
                }

                }
                break;
            case EOF:
            case 84:
            case 86:
            case 127:
            case 135:
            case 136:
                {
                alt56=2;
                }
                break;
            default:
                if (backtracking>0) {failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("379:26: ( declarator | ( abstractDeclarator )? )", 56, 0, input);

                throw nvae;
            }

            switch (alt56) {
                case 1 :
                    // GNUCa.g:379:27: declarator
                    {
                    pushFollow(FOLLOW_declarator_in_parameterDeclaration2298);
                    declarator155=declarator();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_declarator.add(declarator155.getTree());

                    }
                    break;
                case 2 :
                    // GNUCa.g:379:38: ( abstractDeclarator )?
                    {
                    // GNUCa.g:379:38: ( abstractDeclarator )?
                    int alt55=2;
                    int LA55_0 = input.LA(1);

                    if ( (LA55_0==83||LA55_0==127||LA55_0==129) ) {
                        alt55=1;
                    }
                    switch (alt55) {
                        case 1 :
                            // GNUCa.g:0:0: abstractDeclarator
                            {
                            pushFollow(FOLLOW_abstractDeclarator_in_parameterDeclaration2300);
                            abstractDeclarator156=abstractDeclarator();
                            _fsp--;
                            if (failed) return retval;
                            if ( backtracking==0 ) stream_abstractDeclarator.add(abstractDeclarator156.getTree());

                            }
                            break;

                    }


                    }
                    break;

            }

            // GNUCa.g:379:59: ( attributes )?
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( ((LA57_0>=135 && LA57_0<=136)) ) {
                alt57=1;
            }
            switch (alt57) {
                case 1 :
                    // GNUCa.g:0:0: attributes
                    {
                    pushFollow(FOLLOW_attributes_in_parameterDeclaration2304);
                    attributes157=attributes();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_attributes.add(attributes157.getTree());

                    }
                    break;

            }


            // AST REWRITE
            // elements: declarator, declarationSpecifiers, abstractDeclarator
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 379:71: -> ^( PARAMETER declarationSpecifiers ( declarator )? ( abstractDeclarator )? )
            {
                // GNUCa.g:379:74: ^( PARAMETER declarationSpecifiers ( declarator )? ( abstractDeclarator )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(adaptor.create(PARAMETER, "PARAMETER"), root_1);

                adaptor.addChild(root_1, stream_declarationSpecifiers.next());
                // GNUCa.g:379:108: ( declarator )?
                if ( stream_declarator.hasNext() ) {
                    adaptor.addChild(root_1, stream_declarator.next());

                }
                stream_declarator.reset();
                // GNUCa.g:379:120: ( abstractDeclarator )?
                if ( stream_abstractDeclarator.hasNext() ) {
                    adaptor.addChild(root_1, stream_abstractDeclarator.next());

                }
                stream_abstractDeclarator.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            }

            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 30, parameterDeclaration_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end parameterDeclaration

    public static class identifierList_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start identifierList
    // GNUCa.g:382:1: identifierList : IDENTIFIER ( ',' IDENTIFIER )* -> ( ^( PARAMETER IDENTIFIER ) )+ ;
    public final identifierList_return identifierList() throws RecognitionException {
        identifierList_return retval = new identifierList_return();
        retval.start = input.LT(1);
        int identifierList_StartIndex = input.index();
        Object root_0 = null;

        Token IDENTIFIER158=null;
        Token char_literal159=null;
        Token IDENTIFIER160=null;

        Object IDENTIFIER158_tree=null;
        Object char_literal159_tree=null;
        Object IDENTIFIER160_tree=null;
        RewriteRuleTokenStream stream_IDENTIFIER=new RewriteRuleTokenStream(adaptor,"token IDENTIFIER");
        RewriteRuleTokenStream stream_86=new RewriteRuleTokenStream(adaptor,"token 86");

        try {
            if ( backtracking>0 && alreadyParsedRule(input, 31) ) { return retval; }
            // GNUCa.g:383:2: ( IDENTIFIER ( ',' IDENTIFIER )* -> ( ^( PARAMETER IDENTIFIER ) )+ )
            // GNUCa.g:383:4: IDENTIFIER ( ',' IDENTIFIER )*
            {
            IDENTIFIER158=(Token)input.LT(1);
            match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_identifierList2331); if (failed) return retval;
            if ( backtracking==0 ) stream_IDENTIFIER.add(IDENTIFIER158);

            // GNUCa.g:383:15: ( ',' IDENTIFIER )*
            loop58:
            do {
                int alt58=2;
                int LA58_0 = input.LA(1);

                if ( (LA58_0==86) ) {
                    alt58=1;
                }


                switch (alt58) {
            	case 1 :
            	    // GNUCa.g:383:16: ',' IDENTIFIER
            	    {
            	    char_literal159=(Token)input.LT(1);
            	    match(input,86,FOLLOW_86_in_identifierList2334); if (failed) return retval;
            	    if ( backtracking==0 ) stream_86.add(char_literal159);

            	    IDENTIFIER160=(Token)input.LT(1);
            	    match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_identifierList2336); if (failed) return retval;
            	    if ( backtracking==0 ) stream_IDENTIFIER.add(IDENTIFIER160);


            	    }
            	    break;

            	default :
            	    break loop58;
                }
            } while (true);


            // AST REWRITE
            // elements: IDENTIFIER
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 383:33: -> ( ^( PARAMETER IDENTIFIER ) )+
            {
                if ( !(stream_IDENTIFIER.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_IDENTIFIER.hasNext() ) {
                    // GNUCa.g:383:36: ^( PARAMETER IDENTIFIER )
                    {
                    Object root_1 = (Object)adaptor.nil();
                    root_1 = (Object)adaptor.becomeRoot(adaptor.create(PARAMETER, "PARAMETER"), root_1);

                    adaptor.addChild(root_1, stream_IDENTIFIER.next());

                    adaptor.addChild(root_0, root_1);
                    }

                }
                stream_IDENTIFIER.reset();

            }

            }

            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 31, identifierList_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end identifierList

    public static class typeName_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start typeName
    // GNUCa.g:386:1: typeName : ( specifierQualifier )+ ( abstractDeclarator )? -> ^( TYPE_NAME ( specifierQualifier )+ ( abstractDeclarator )? ) ;
    public final typeName_return typeName() throws RecognitionException {
        typeName_return retval = new typeName_return();
        retval.start = input.LT(1);
        int typeName_StartIndex = input.index();
        Object root_0 = null;

        specifierQualifier_return specifierQualifier161 = null;

        abstractDeclarator_return abstractDeclarator162 = null;


        RewriteRuleSubtreeStream stream_specifierQualifier=new RewriteRuleSubtreeStream(adaptor,"rule specifierQualifier");
        RewriteRuleSubtreeStream stream_abstractDeclarator=new RewriteRuleSubtreeStream(adaptor,"rule abstractDeclarator");
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 32) ) { return retval; }
            // GNUCa.g:387:2: ( ( specifierQualifier )+ ( abstractDeclarator )? -> ^( TYPE_NAME ( specifierQualifier )+ ( abstractDeclarator )? ) )
            // GNUCa.g:387:4: ( specifierQualifier )+ ( abstractDeclarator )?
            {
            // GNUCa.g:387:4: ( specifierQualifier )+
            int cnt59=0;
            loop59:
            do {
                int alt59=2;
                int LA59_0 = input.LA(1);

                if ( (LA59_0==IDENTIFIER||(LA59_0>=93 && LA59_0<=108)||(LA59_0>=111 && LA59_0<=112)||(LA59_0>=114 && LA59_0<=123)||(LA59_0>=131 && LA59_0<=133)||(LA59_0>=135 && LA59_0<=136)) ) {
                    alt59=1;
                }


                switch (alt59) {
            	case 1 :
            	    // GNUCa.g:0:0: specifierQualifier
            	    {
            	    pushFollow(FOLLOW_specifierQualifier_in_typeName2358);
            	    specifierQualifier161=specifierQualifier();
            	    _fsp--;
            	    if (failed) return retval;
            	    if ( backtracking==0 ) stream_specifierQualifier.add(specifierQualifier161.getTree());

            	    }
            	    break;

            	default :
            	    if ( cnt59 >= 1 ) break loop59;
            	    if (backtracking>0) {failed=true; return retval;}
                        EarlyExitException eee =
                            new EarlyExitException(59, input);
                        throw eee;
                }
                cnt59++;
            } while (true);

            // GNUCa.g:387:24: ( abstractDeclarator )?
            int alt60=2;
            int LA60_0 = input.LA(1);

            if ( (LA60_0==83||LA60_0==127||LA60_0==129) ) {
                alt60=1;
            }
            switch (alt60) {
                case 1 :
                    // GNUCa.g:0:0: abstractDeclarator
                    {
                    pushFollow(FOLLOW_abstractDeclarator_in_typeName2361);
                    abstractDeclarator162=abstractDeclarator();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_abstractDeclarator.add(abstractDeclarator162.getTree());

                    }
                    break;

            }


            // AST REWRITE
            // elements: abstractDeclarator, specifierQualifier
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 387:44: -> ^( TYPE_NAME ( specifierQualifier )+ ( abstractDeclarator )? )
            {
                // GNUCa.g:387:47: ^( TYPE_NAME ( specifierQualifier )+ ( abstractDeclarator )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(adaptor.create(TYPE_NAME, "TYPE_NAME"), root_1);

                if ( !(stream_specifierQualifier.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_specifierQualifier.hasNext() ) {
                    adaptor.addChild(root_1, stream_specifierQualifier.next());

                }
                stream_specifierQualifier.reset();
                // GNUCa.g:387:79: ( abstractDeclarator )?
                if ( stream_abstractDeclarator.hasNext() ) {
                    adaptor.addChild(root_1, stream_abstractDeclarator.next());

                }
                stream_abstractDeclarator.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            }

            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 32, typeName_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end typeName

    public static class abstractDeclarator_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start abstractDeclarator
    // GNUCa.g:390:1: abstractDeclarator : ( pointer ( directAbstractDeclarator )? | directAbstractDeclarator );
    public final abstractDeclarator_return abstractDeclarator() throws RecognitionException {
        abstractDeclarator_return retval = new abstractDeclarator_return();
        retval.start = input.LT(1);
        int abstractDeclarator_StartIndex = input.index();
        Object root_0 = null;

        pointer_return pointer163 = null;

        directAbstractDeclarator_return directAbstractDeclarator164 = null;

        directAbstractDeclarator_return directAbstractDeclarator165 = null;



        try {
            if ( backtracking>0 && alreadyParsedRule(input, 33) ) { return retval; }
            // GNUCa.g:391:2: ( pointer ( directAbstractDeclarator )? | directAbstractDeclarator )
            int alt62=2;
            int LA62_0 = input.LA(1);

            if ( (LA62_0==129) ) {
                alt62=1;
            }
            else if ( (LA62_0==83||LA62_0==127) ) {
                alt62=2;
            }
            else {
                if (backtracking>0) {failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("390:1: abstractDeclarator : ( pointer ( directAbstractDeclarator )? | directAbstractDeclarator );", 62, 0, input);

                throw nvae;
            }
            switch (alt62) {
                case 1 :
                    // GNUCa.g:391:4: pointer ( directAbstractDeclarator )?
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_pointer_in_abstractDeclarator2386);
                    pointer163=pointer();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) adaptor.addChild(root_0, pointer163.getTree());
                    // GNUCa.g:391:12: ( directAbstractDeclarator )?
                    int alt61=2;
                    int LA61_0 = input.LA(1);

                    if ( (LA61_0==83||LA61_0==127) ) {
                        alt61=1;
                    }
                    switch (alt61) {
                        case 1 :
                            // GNUCa.g:0:0: directAbstractDeclarator
                            {
                            pushFollow(FOLLOW_directAbstractDeclarator_in_abstractDeclarator2388);
                            directAbstractDeclarator164=directAbstractDeclarator();
                            _fsp--;
                            if (failed) return retval;
                            if ( backtracking==0 ) adaptor.addChild(root_0, directAbstractDeclarator164.getTree());

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // GNUCa.g:392:4: directAbstractDeclarator
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_directAbstractDeclarator_in_abstractDeclarator2394);
                    directAbstractDeclarator165=directAbstractDeclarator();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) adaptor.addChild(root_0, directAbstractDeclarator165.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 33, abstractDeclarator_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end abstractDeclarator

    public static class directAbstractDeclarator_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start directAbstractDeclarator
    // GNUCa.g:395:1: directAbstractDeclarator : ( '(' ( attributes )? abstractDeclarator ')' ( '[' ( assignmentExpression )? ']' -> ^( ARRAY_DECLARATOR ( abstractDeclarator )? assignmentExpression ) | '[' '*' ']' -> ^( ARRAY_DECLARATOR abstractDeclarator '*' ) | '(' ( parameterTypeList )? ')' -> ^( FUNCTION_DECLARATOR abstractDeclarator ( parameterTypeList )? ) )? | '[' ( assignmentExpression )? ']' -> ^( ARRAY_DECLARATOR ( assignmentExpression )? ) | '[' '*' ']' -> ^( ARRAY_DECLARATOR '*' ) | '(' ( parameterTypeList )? ')' -> ^( FUNCTION_DECLARATOR ( parameterTypeList )? ) );
    public final directAbstractDeclarator_return directAbstractDeclarator() throws RecognitionException {
        directAbstractDeclarator_return retval = new directAbstractDeclarator_return();
        retval.start = input.LT(1);
        int directAbstractDeclarator_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal166=null;
        Token char_literal169=null;
        Token char_literal170=null;
        Token char_literal172=null;
        Token char_literal173=null;
        Token char_literal174=null;
        Token char_literal175=null;
        Token char_literal176=null;
        Token char_literal178=null;
        Token char_literal179=null;
        Token char_literal181=null;
        Token char_literal182=null;
        Token char_literal183=null;
        Token char_literal184=null;
        Token char_literal185=null;
        Token char_literal187=null;
        attributes_return attributes167 = null;

        abstractDeclarator_return abstractDeclarator168 = null;

        assignmentExpression_return assignmentExpression171 = null;

        parameterTypeList_return parameterTypeList177 = null;

        assignmentExpression_return assignmentExpression180 = null;

        parameterTypeList_return parameterTypeList186 = null;


        Object char_literal166_tree=null;
        Object char_literal169_tree=null;
        Object char_literal170_tree=null;
        Object char_literal172_tree=null;
        Object char_literal173_tree=null;
        Object char_literal174_tree=null;
        Object char_literal175_tree=null;
        Object char_literal176_tree=null;
        Object char_literal178_tree=null;
        Object char_literal179_tree=null;
        Object char_literal181_tree=null;
        Object char_literal182_tree=null;
        Object char_literal183_tree=null;
        Object char_literal184_tree=null;
        Object char_literal185_tree=null;
        Object char_literal187_tree=null;
        RewriteRuleTokenStream stream_127=new RewriteRuleTokenStream(adaptor,"token 127");
        RewriteRuleTokenStream stream_128=new RewriteRuleTokenStream(adaptor,"token 128");
        RewriteRuleTokenStream stream_83=new RewriteRuleTokenStream(adaptor,"token 83");
        RewriteRuleTokenStream stream_129=new RewriteRuleTokenStream(adaptor,"token 129");
        RewriteRuleTokenStream stream_84=new RewriteRuleTokenStream(adaptor,"token 84");
        RewriteRuleSubtreeStream stream_assignmentExpression=new RewriteRuleSubtreeStream(adaptor,"rule assignmentExpression");
        RewriteRuleSubtreeStream stream_attributes=new RewriteRuleSubtreeStream(adaptor,"rule attributes");
        RewriteRuleSubtreeStream stream_parameterTypeList=new RewriteRuleSubtreeStream(adaptor,"rule parameterTypeList");
        RewriteRuleSubtreeStream stream_abstractDeclarator=new RewriteRuleSubtreeStream(adaptor,"rule abstractDeclarator");
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 34) ) { return retval; }
            // GNUCa.g:396:2: ( '(' ( attributes )? abstractDeclarator ')' ( '[' ( assignmentExpression )? ']' -> ^( ARRAY_DECLARATOR ( abstractDeclarator )? assignmentExpression ) | '[' '*' ']' -> ^( ARRAY_DECLARATOR abstractDeclarator '*' ) | '(' ( parameterTypeList )? ')' -> ^( FUNCTION_DECLARATOR abstractDeclarator ( parameterTypeList )? ) )? | '[' ( assignmentExpression )? ']' -> ^( ARRAY_DECLARATOR ( assignmentExpression )? ) | '[' '*' ']' -> ^( ARRAY_DECLARATOR '*' ) | '(' ( parameterTypeList )? ')' -> ^( FUNCTION_DECLARATOR ( parameterTypeList )? ) )
            int alt69=4;
            int LA69_0 = input.LA(1);

            if ( (LA69_0==83) ) {
                switch ( input.LA(2) ) {
                case IDENTIFIER:
                case 84:
                case 88:
                case 89:
                case 90:
                case 91:
                case 92:
                case 93:
                case 94:
                case 95:
                case 96:
                case 97:
                case 98:
                case 99:
                case 100:
                case 101:
                case 102:
                case 103:
                case 104:
                case 105:
                case 106:
                case 107:
                case 108:
                case 111:
                case 112:
                case 114:
                case 115:
                case 116:
                case 117:
                case 118:
                case 119:
                case 120:
                case 121:
                case 122:
                case 123:
                case 124:
                case 125:
                case 126:
                case 131:
                case 132:
                case 133:
                    {
                    alt69=4;
                    }
                    break;
                case 135:
                case 136:
                    {
                    int LA69_38 = input.LA(3);

                    if ( (synpred120()) ) {
                        alt69=1;
                    }
                    else if ( (true) ) {
                        alt69=4;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("395:1: directAbstractDeclarator : ( '(' ( attributes )? abstractDeclarator ')' ( '[' ( assignmentExpression )? ']' -> ^( ARRAY_DECLARATOR ( abstractDeclarator )? assignmentExpression ) | '[' '*' ']' -> ^( ARRAY_DECLARATOR abstractDeclarator '*' ) | '(' ( parameterTypeList )? ')' -> ^( FUNCTION_DECLARATOR abstractDeclarator ( parameterTypeList )? ) )? | '[' ( assignmentExpression )? ']' -> ^( ARRAY_DECLARATOR ( assignmentExpression )? ) | '[' '*' ']' -> ^( ARRAY_DECLARATOR '*' ) | '(' ( parameterTypeList )? ')' -> ^( FUNCTION_DECLARATOR ( parameterTypeList )? ) );", 69, 38, input);

                        throw nvae;
                    }
                    }
                    break;
                case 83:
                case 127:
                case 129:
                    {
                    alt69=1;
                    }
                    break;
                default:
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("395:1: directAbstractDeclarator : ( '(' ( attributes )? abstractDeclarator ')' ( '[' ( assignmentExpression )? ']' -> ^( ARRAY_DECLARATOR ( abstractDeclarator )? assignmentExpression ) | '[' '*' ']' -> ^( ARRAY_DECLARATOR abstractDeclarator '*' ) | '(' ( parameterTypeList )? ')' -> ^( FUNCTION_DECLARATOR abstractDeclarator ( parameterTypeList )? ) )? | '[' ( assignmentExpression )? ']' -> ^( ARRAY_DECLARATOR ( assignmentExpression )? ) | '[' '*' ']' -> ^( ARRAY_DECLARATOR '*' ) | '(' ( parameterTypeList )? ')' -> ^( FUNCTION_DECLARATOR ( parameterTypeList )? ) );", 69, 1, input);

                    throw nvae;
                }

            }
            else if ( (LA69_0==127) ) {
                int LA69_2 = input.LA(2);

                if ( (LA69_2==129) ) {
                    int LA69_43 = input.LA(3);

                    if ( (synpred122()) ) {
                        alt69=2;
                    }
                    else if ( (synpred123()) ) {
                        alt69=3;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("395:1: directAbstractDeclarator : ( '(' ( attributes )? abstractDeclarator ')' ( '[' ( assignmentExpression )? ']' -> ^( ARRAY_DECLARATOR ( abstractDeclarator )? assignmentExpression ) | '[' '*' ']' -> ^( ARRAY_DECLARATOR abstractDeclarator '*' ) | '(' ( parameterTypeList )? ')' -> ^( FUNCTION_DECLARATOR abstractDeclarator ( parameterTypeList )? ) )? | '[' ( assignmentExpression )? ']' -> ^( ARRAY_DECLARATOR ( assignmentExpression )? ) | '[' '*' ']' -> ^( ARRAY_DECLARATOR '*' ) | '(' ( parameterTypeList )? ')' -> ^( FUNCTION_DECLARATOR ( parameterTypeList )? ) );", 69, 43, input);

                        throw nvae;
                    }
                }
                else if ( ((LA69_2>=IDENTIFIER && LA69_2<=STRING_LITERAL)||LA69_2==83||LA69_2==128||LA69_2==137||(LA69_2>=139 && LA69_2<=153)) ) {
                    alt69=2;
                }
                else {
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("395:1: directAbstractDeclarator : ( '(' ( attributes )? abstractDeclarator ')' ( '[' ( assignmentExpression )? ']' -> ^( ARRAY_DECLARATOR ( abstractDeclarator )? assignmentExpression ) | '[' '*' ']' -> ^( ARRAY_DECLARATOR abstractDeclarator '*' ) | '(' ( parameterTypeList )? ')' -> ^( FUNCTION_DECLARATOR abstractDeclarator ( parameterTypeList )? ) )? | '[' ( assignmentExpression )? ']' -> ^( ARRAY_DECLARATOR ( assignmentExpression )? ) | '[' '*' ']' -> ^( ARRAY_DECLARATOR '*' ) | '(' ( parameterTypeList )? ')' -> ^( FUNCTION_DECLARATOR ( parameterTypeList )? ) );", 69, 2, input);

                    throw nvae;
                }
            }
            else {
                if (backtracking>0) {failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("395:1: directAbstractDeclarator : ( '(' ( attributes )? abstractDeclarator ')' ( '[' ( assignmentExpression )? ']' -> ^( ARRAY_DECLARATOR ( abstractDeclarator )? assignmentExpression ) | '[' '*' ']' -> ^( ARRAY_DECLARATOR abstractDeclarator '*' ) | '(' ( parameterTypeList )? ')' -> ^( FUNCTION_DECLARATOR abstractDeclarator ( parameterTypeList )? ) )? | '[' ( assignmentExpression )? ']' -> ^( ARRAY_DECLARATOR ( assignmentExpression )? ) | '[' '*' ']' -> ^( ARRAY_DECLARATOR '*' ) | '(' ( parameterTypeList )? ')' -> ^( FUNCTION_DECLARATOR ( parameterTypeList )? ) );", 69, 0, input);

                throw nvae;
            }
            switch (alt69) {
                case 1 :
                    // GNUCa.g:396:4: '(' ( attributes )? abstractDeclarator ')' ( '[' ( assignmentExpression )? ']' -> ^( ARRAY_DECLARATOR ( abstractDeclarator )? assignmentExpression ) | '[' '*' ']' -> ^( ARRAY_DECLARATOR abstractDeclarator '*' ) | '(' ( parameterTypeList )? ')' -> ^( FUNCTION_DECLARATOR abstractDeclarator ( parameterTypeList )? ) )?
                    {
                    char_literal166=(Token)input.LT(1);
                    match(input,83,FOLLOW_83_in_directAbstractDeclarator2405); if (failed) return retval;
                    if ( backtracking==0 ) stream_83.add(char_literal166);

                    // GNUCa.g:396:8: ( attributes )?
                    int alt63=2;
                    int LA63_0 = input.LA(1);

                    if ( ((LA63_0>=135 && LA63_0<=136)) ) {
                        alt63=1;
                    }
                    switch (alt63) {
                        case 1 :
                            // GNUCa.g:0:0: attributes
                            {
                            pushFollow(FOLLOW_attributes_in_directAbstractDeclarator2407);
                            attributes167=attributes();
                            _fsp--;
                            if (failed) return retval;
                            if ( backtracking==0 ) stream_attributes.add(attributes167.getTree());

                            }
                            break;

                    }

                    pushFollow(FOLLOW_abstractDeclarator_in_directAbstractDeclarator2410);
                    abstractDeclarator168=abstractDeclarator();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_abstractDeclarator.add(abstractDeclarator168.getTree());
                    char_literal169=(Token)input.LT(1);
                    match(input,84,FOLLOW_84_in_directAbstractDeclarator2412); if (failed) return retval;
                    if ( backtracking==0 ) stream_84.add(char_literal169);

                    // GNUCa.g:397:3: ( '[' ( assignmentExpression )? ']' -> ^( ARRAY_DECLARATOR ( abstractDeclarator )? assignmentExpression ) | '[' '*' ']' -> ^( ARRAY_DECLARATOR abstractDeclarator '*' ) | '(' ( parameterTypeList )? ')' -> ^( FUNCTION_DECLARATOR abstractDeclarator ( parameterTypeList )? ) )?
                    int alt66=4;
                    int LA66_0 = input.LA(1);

                    if ( (LA66_0==127) ) {
                        int LA66_1 = input.LA(2);

                        if ( (LA66_1==129) ) {
                            int LA66_7 = input.LA(3);

                            if ( (synpred116()) ) {
                                alt66=1;
                            }
                            else if ( (synpred117()) ) {
                                alt66=2;
                            }
                        }
                        else if ( ((LA66_1>=IDENTIFIER && LA66_1<=STRING_LITERAL)||LA66_1==83||LA66_1==128||LA66_1==137||(LA66_1>=139 && LA66_1<=153)) ) {
                            alt66=1;
                        }
                    }
                    else if ( (LA66_0==83) ) {
                        alt66=3;
                    }
                    switch (alt66) {
                        case 1 :
                            // GNUCa.g:397:5: '[' ( assignmentExpression )? ']'
                            {
                            char_literal170=(Token)input.LT(1);
                            match(input,127,FOLLOW_127_in_directAbstractDeclarator2419); if (failed) return retval;
                            if ( backtracking==0 ) stream_127.add(char_literal170);

                            // GNUCa.g:397:9: ( assignmentExpression )?
                            int alt64=2;
                            int LA64_0 = input.LA(1);

                            if ( ((LA64_0>=IDENTIFIER && LA64_0<=STRING_LITERAL)||LA64_0==83||LA64_0==129||LA64_0==137||(LA64_0>=139 && LA64_0<=153)) ) {
                                alt64=1;
                            }
                            switch (alt64) {
                                case 1 :
                                    // GNUCa.g:0:0: assignmentExpression
                                    {
                                    pushFollow(FOLLOW_assignmentExpression_in_directAbstractDeclarator2421);
                                    assignmentExpression171=assignmentExpression();
                                    _fsp--;
                                    if (failed) return retval;
                                    if ( backtracking==0 ) stream_assignmentExpression.add(assignmentExpression171.getTree());

                                    }
                                    break;

                            }

                            char_literal172=(Token)input.LT(1);
                            match(input,128,FOLLOW_128_in_directAbstractDeclarator2424); if (failed) return retval;
                            if ( backtracking==0 ) stream_128.add(char_literal172);


                            // AST REWRITE
                            // elements: assignmentExpression, abstractDeclarator
                            // token labels: 
                            // rule labels: retval
                            // token list labels: 
                            // rule list labels: 
                            if ( backtracking==0 ) {
                            retval.tree = root_0;
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                            root_0 = (Object)adaptor.nil();
                            // 397:35: -> ^( ARRAY_DECLARATOR ( abstractDeclarator )? assignmentExpression )
                            {
                                // GNUCa.g:397:38: ^( ARRAY_DECLARATOR ( abstractDeclarator )? assignmentExpression )
                                {
                                Object root_1 = (Object)adaptor.nil();
                                root_1 = (Object)adaptor.becomeRoot(adaptor.create(ARRAY_DECLARATOR, "ARRAY_DECLARATOR"), root_1);

                                // GNUCa.g:397:57: ( abstractDeclarator )?
                                if ( stream_abstractDeclarator.hasNext() ) {
                                    adaptor.addChild(root_1, stream_abstractDeclarator.next());

                                }
                                stream_abstractDeclarator.reset();
                                adaptor.addChild(root_1, stream_assignmentExpression.next());

                                adaptor.addChild(root_0, root_1);
                                }

                            }

                            }

                            }
                            break;
                        case 2 :
                            // GNUCa.g:398:5: '[' '*' ']'
                            {
                            char_literal173=(Token)input.LT(1);
                            match(input,127,FOLLOW_127_in_directAbstractDeclarator2441); if (failed) return retval;
                            if ( backtracking==0 ) stream_127.add(char_literal173);

                            char_literal174=(Token)input.LT(1);
                            match(input,129,FOLLOW_129_in_directAbstractDeclarator2443); if (failed) return retval;
                            if ( backtracking==0 ) stream_129.add(char_literal174);

                            char_literal175=(Token)input.LT(1);
                            match(input,128,FOLLOW_128_in_directAbstractDeclarator2445); if (failed) return retval;
                            if ( backtracking==0 ) stream_128.add(char_literal175);


                            // AST REWRITE
                            // elements: 129, abstractDeclarator
                            // token labels: 
                            // rule labels: retval
                            // token list labels: 
                            // rule list labels: 
                            if ( backtracking==0 ) {
                            retval.tree = root_0;
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                            root_0 = (Object)adaptor.nil();
                            // 398:17: -> ^( ARRAY_DECLARATOR abstractDeclarator '*' )
                            {
                                // GNUCa.g:398:20: ^( ARRAY_DECLARATOR abstractDeclarator '*' )
                                {
                                Object root_1 = (Object)adaptor.nil();
                                root_1 = (Object)adaptor.becomeRoot(adaptor.create(ARRAY_DECLARATOR, "ARRAY_DECLARATOR"), root_1);

                                adaptor.addChild(root_1, stream_abstractDeclarator.next());
                                adaptor.addChild(root_1, stream_129.next());

                                adaptor.addChild(root_0, root_1);
                                }

                            }

                            }

                            }
                            break;
                        case 3 :
                            // GNUCa.g:399:5: '(' ( parameterTypeList )? ')'
                            {
                            char_literal176=(Token)input.LT(1);
                            match(input,83,FOLLOW_83_in_directAbstractDeclarator2461); if (failed) return retval;
                            if ( backtracking==0 ) stream_83.add(char_literal176);

                            // GNUCa.g:399:9: ( parameterTypeList )?
                            int alt65=2;
                            int LA65_0 = input.LA(1);

                            if ( (LA65_0==IDENTIFIER||(LA65_0>=88 && LA65_0<=108)||(LA65_0>=111 && LA65_0<=112)||(LA65_0>=114 && LA65_0<=126)||(LA65_0>=131 && LA65_0<=133)||(LA65_0>=135 && LA65_0<=136)) ) {
                                alt65=1;
                            }
                            switch (alt65) {
                                case 1 :
                                    // GNUCa.g:0:0: parameterTypeList
                                    {
                                    pushFollow(FOLLOW_parameterTypeList_in_directAbstractDeclarator2463);
                                    parameterTypeList177=parameterTypeList();
                                    _fsp--;
                                    if (failed) return retval;
                                    if ( backtracking==0 ) stream_parameterTypeList.add(parameterTypeList177.getTree());

                                    }
                                    break;

                            }

                            char_literal178=(Token)input.LT(1);
                            match(input,84,FOLLOW_84_in_directAbstractDeclarator2466); if (failed) return retval;
                            if ( backtracking==0 ) stream_84.add(char_literal178);


                            // AST REWRITE
                            // elements: parameterTypeList, abstractDeclarator
                            // token labels: 
                            // rule labels: retval
                            // token list labels: 
                            // rule list labels: 
                            if ( backtracking==0 ) {
                            retval.tree = root_0;
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                            root_0 = (Object)adaptor.nil();
                            // 399:33: -> ^( FUNCTION_DECLARATOR abstractDeclarator ( parameterTypeList )? )
                            {
                                // GNUCa.g:399:37: ^( FUNCTION_DECLARATOR abstractDeclarator ( parameterTypeList )? )
                                {
                                Object root_1 = (Object)adaptor.nil();
                                root_1 = (Object)adaptor.becomeRoot(adaptor.create(FUNCTION_DECLARATOR, "FUNCTION_DECLARATOR"), root_1);

                                adaptor.addChild(root_1, stream_abstractDeclarator.next());
                                // GNUCa.g:399:78: ( parameterTypeList )?
                                if ( stream_parameterTypeList.hasNext() ) {
                                    adaptor.addChild(root_1, stream_parameterTypeList.next());

                                }
                                stream_parameterTypeList.reset();

                                adaptor.addChild(root_0, root_1);
                                }

                            }

                            }

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // GNUCa.g:401:4: '[' ( assignmentExpression )? ']'
                    {
                    char_literal179=(Token)input.LT(1);
                    match(input,127,FOLLOW_127_in_directAbstractDeclarator2489); if (failed) return retval;
                    if ( backtracking==0 ) stream_127.add(char_literal179);

                    // GNUCa.g:401:8: ( assignmentExpression )?
                    int alt67=2;
                    int LA67_0 = input.LA(1);

                    if ( ((LA67_0>=IDENTIFIER && LA67_0<=STRING_LITERAL)||LA67_0==83||LA67_0==129||LA67_0==137||(LA67_0>=139 && LA67_0<=153)) ) {
                        alt67=1;
                    }
                    switch (alt67) {
                        case 1 :
                            // GNUCa.g:0:0: assignmentExpression
                            {
                            pushFollow(FOLLOW_assignmentExpression_in_directAbstractDeclarator2491);
                            assignmentExpression180=assignmentExpression();
                            _fsp--;
                            if (failed) return retval;
                            if ( backtracking==0 ) stream_assignmentExpression.add(assignmentExpression180.getTree());

                            }
                            break;

                    }

                    char_literal181=(Token)input.LT(1);
                    match(input,128,FOLLOW_128_in_directAbstractDeclarator2494); if (failed) return retval;
                    if ( backtracking==0 ) stream_128.add(char_literal181);


                    // AST REWRITE
                    // elements: assignmentExpression
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 401:34: -> ^( ARRAY_DECLARATOR ( assignmentExpression )? )
                    {
                        // GNUCa.g:401:37: ^( ARRAY_DECLARATOR ( assignmentExpression )? )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(adaptor.create(ARRAY_DECLARATOR, "ARRAY_DECLARATOR"), root_1);

                        // GNUCa.g:401:56: ( assignmentExpression )?
                        if ( stream_assignmentExpression.hasNext() ) {
                            adaptor.addChild(root_1, stream_assignmentExpression.next());

                        }
                        stream_assignmentExpression.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;
                case 3 :
                    // GNUCa.g:402:4: '[' '*' ']'
                    {
                    char_literal182=(Token)input.LT(1);
                    match(input,127,FOLLOW_127_in_directAbstractDeclarator2508); if (failed) return retval;
                    if ( backtracking==0 ) stream_127.add(char_literal182);

                    char_literal183=(Token)input.LT(1);
                    match(input,129,FOLLOW_129_in_directAbstractDeclarator2510); if (failed) return retval;
                    if ( backtracking==0 ) stream_129.add(char_literal183);

                    char_literal184=(Token)input.LT(1);
                    match(input,128,FOLLOW_128_in_directAbstractDeclarator2512); if (failed) return retval;
                    if ( backtracking==0 ) stream_128.add(char_literal184);


                    // AST REWRITE
                    // elements: 129
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 402:16: -> ^( ARRAY_DECLARATOR '*' )
                    {
                        // GNUCa.g:402:19: ^( ARRAY_DECLARATOR '*' )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(adaptor.create(ARRAY_DECLARATOR, "ARRAY_DECLARATOR"), root_1);

                        adaptor.addChild(root_1, stream_129.next());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;
                case 4 :
                    // GNUCa.g:403:4: '(' ( parameterTypeList )? ')'
                    {
                    char_literal185=(Token)input.LT(1);
                    match(input,83,FOLLOW_83_in_directAbstractDeclarator2525); if (failed) return retval;
                    if ( backtracking==0 ) stream_83.add(char_literal185);

                    // GNUCa.g:403:8: ( parameterTypeList )?
                    int alt68=2;
                    int LA68_0 = input.LA(1);

                    if ( (LA68_0==IDENTIFIER||(LA68_0>=88 && LA68_0<=108)||(LA68_0>=111 && LA68_0<=112)||(LA68_0>=114 && LA68_0<=126)||(LA68_0>=131 && LA68_0<=133)||(LA68_0>=135 && LA68_0<=136)) ) {
                        alt68=1;
                    }
                    switch (alt68) {
                        case 1 :
                            // GNUCa.g:0:0: parameterTypeList
                            {
                            pushFollow(FOLLOW_parameterTypeList_in_directAbstractDeclarator2527);
                            parameterTypeList186=parameterTypeList();
                            _fsp--;
                            if (failed) return retval;
                            if ( backtracking==0 ) stream_parameterTypeList.add(parameterTypeList186.getTree());

                            }
                            break;

                    }

                    char_literal187=(Token)input.LT(1);
                    match(input,84,FOLLOW_84_in_directAbstractDeclarator2530); if (failed) return retval;
                    if ( backtracking==0 ) stream_84.add(char_literal187);


                    // AST REWRITE
                    // elements: parameterTypeList
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 403:32: -> ^( FUNCTION_DECLARATOR ( parameterTypeList )? )
                    {
                        // GNUCa.g:403:36: ^( FUNCTION_DECLARATOR ( parameterTypeList )? )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(adaptor.create(FUNCTION_DECLARATOR, "FUNCTION_DECLARATOR"), root_1);

                        // GNUCa.g:403:58: ( parameterTypeList )?
                        if ( stream_parameterTypeList.hasNext() ) {
                            adaptor.addChild(root_1, stream_parameterTypeList.next());

                        }
                        stream_parameterTypeList.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 34, directAbstractDeclarator_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end directAbstractDeclarator

    public static class typedefName_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start typedefName
    // GNUCa.g:406:1: typedefName : {...}? IDENTIFIER ;
    public final typedefName_return typedefName() throws RecognitionException {
        typedefName_return retval = new typedefName_return();
        retval.start = input.LT(1);
        int typedefName_StartIndex = input.index();
        Object root_0 = null;

        Token IDENTIFIER188=null;

        Object IDENTIFIER188_tree=null;

        try {
            if ( backtracking>0 && alreadyParsedRule(input, 35) ) { return retval; }
            // GNUCa.g:407:2: ({...}? IDENTIFIER )
            // GNUCa.g:407:4: {...}? IDENTIFIER
            {
            root_0 = (Object)adaptor.nil();

            if ( !(isTypeName(input.LT(1).getText())) ) {
                if (backtracking>0) {failed=true; return retval;}
                throw new FailedPredicateException(input, "typedefName", "isTypeName(input.LT(1).getText())");
            }
            IDENTIFIER188=(Token)input.LT(1);
            match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_typedefName2554); if (failed) return retval;
            if ( backtracking==0 ) {
            IDENTIFIER188_tree = (Object)adaptor.create(IDENTIFIER188);
            adaptor.addChild(root_0, IDENTIFIER188_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 35, typedefName_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end typedefName

    public static class typeofSpecifier_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start typeofSpecifier
    // GNUCa.g:410:1: typeofSpecifier : ( 'typeof' | '__typeof' | '__typeof__' ) '(' ( expression -> ^( TYPEOF expression ) | typeName -> ^( TYPEOF typeName ) ) ')' ;
    public final typeofSpecifier_return typeofSpecifier() throws RecognitionException {
        typeofSpecifier_return retval = new typeofSpecifier_return();
        retval.start = input.LT(1);
        int typeofSpecifier_StartIndex = input.index();
        Object root_0 = null;

        Token string_literal189=null;
        Token string_literal190=null;
        Token string_literal191=null;
        Token char_literal192=null;
        Token char_literal195=null;
        expression_return expression193 = null;

        typeName_return typeName194 = null;


        Object string_literal189_tree=null;
        Object string_literal190_tree=null;
        Object string_literal191_tree=null;
        Object char_literal192_tree=null;
        Object char_literal195_tree=null;
        RewriteRuleTokenStream stream_132=new RewriteRuleTokenStream(adaptor,"token 132");
        RewriteRuleTokenStream stream_133=new RewriteRuleTokenStream(adaptor,"token 133");
        RewriteRuleTokenStream stream_83=new RewriteRuleTokenStream(adaptor,"token 83");
        RewriteRuleTokenStream stream_131=new RewriteRuleTokenStream(adaptor,"token 131");
        RewriteRuleTokenStream stream_84=new RewriteRuleTokenStream(adaptor,"token 84");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_typeName=new RewriteRuleSubtreeStream(adaptor,"rule typeName");
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 36) ) { return retval; }
            // GNUCa.g:411:2: ( ( 'typeof' | '__typeof' | '__typeof__' ) '(' ( expression -> ^( TYPEOF expression ) | typeName -> ^( TYPEOF typeName ) ) ')' )
            // GNUCa.g:411:4: ( 'typeof' | '__typeof' | '__typeof__' ) '(' ( expression -> ^( TYPEOF expression ) | typeName -> ^( TYPEOF typeName ) ) ')'
            {
            // GNUCa.g:411:4: ( 'typeof' | '__typeof' | '__typeof__' )
            int alt70=3;
            switch ( input.LA(1) ) {
            case 131:
                {
                alt70=1;
                }
                break;
            case 132:
                {
                alt70=2;
                }
                break;
            case 133:
                {
                alt70=3;
                }
                break;
            default:
                if (backtracking>0) {failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("411:4: ( 'typeof' | '__typeof' | '__typeof__' )", 70, 0, input);

                throw nvae;
            }

            switch (alt70) {
                case 1 :
                    // GNUCa.g:411:5: 'typeof'
                    {
                    string_literal189=(Token)input.LT(1);
                    match(input,131,FOLLOW_131_in_typeofSpecifier2566); if (failed) return retval;
                    if ( backtracking==0 ) stream_131.add(string_literal189);


                    }
                    break;
                case 2 :
                    // GNUCa.g:411:14: '__typeof'
                    {
                    string_literal190=(Token)input.LT(1);
                    match(input,132,FOLLOW_132_in_typeofSpecifier2568); if (failed) return retval;
                    if ( backtracking==0 ) stream_132.add(string_literal190);


                    }
                    break;
                case 3 :
                    // GNUCa.g:411:25: '__typeof__'
                    {
                    string_literal191=(Token)input.LT(1);
                    match(input,133,FOLLOW_133_in_typeofSpecifier2570); if (failed) return retval;
                    if ( backtracking==0 ) stream_133.add(string_literal191);


                    }
                    break;

            }

            char_literal192=(Token)input.LT(1);
            match(input,83,FOLLOW_83_in_typeofSpecifier2573); if (failed) return retval;
            if ( backtracking==0 ) stream_83.add(char_literal192);

            // GNUCa.g:411:43: ( expression -> ^( TYPEOF expression ) | typeName -> ^( TYPEOF typeName ) )
            int alt71=2;
            switch ( input.LA(1) ) {
            case CONSTANT:
            case STRING_LITERAL:
            case 83:
            case 129:
            case 137:
            case 139:
            case 140:
            case 141:
            case 142:
            case 143:
            case 144:
            case 145:
            case 146:
            case 147:
            case 148:
            case 149:
            case 150:
            case 151:
            case 152:
            case 153:
                {
                alt71=1;
                }
                break;
            case IDENTIFIER:
                {
                int LA71_2 = input.LA(2);

                if ( (synpred127()) ) {
                    alt71=1;
                }
                else if ( (isTypeName(input.LT(1).getText())) ) {
                    alt71=2;
                }
                else {
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("411:43: ( expression -> ^( TYPEOF expression ) | typeName -> ^( TYPEOF typeName ) )", 71, 2, input);

                    throw nvae;
                }
                }
                break;
            case 93:
            case 94:
            case 95:
            case 96:
            case 97:
            case 98:
            case 99:
            case 100:
            case 101:
            case 102:
            case 103:
            case 104:
            case 105:
            case 106:
            case 107:
            case 108:
            case 111:
            case 112:
            case 114:
            case 115:
            case 116:
            case 117:
            case 118:
            case 119:
            case 120:
            case 121:
            case 122:
            case 123:
            case 131:
            case 132:
            case 133:
            case 135:
            case 136:
                {
                alt71=2;
                }
                break;
            default:
                if (backtracking>0) {failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("411:43: ( expression -> ^( TYPEOF expression ) | typeName -> ^( TYPEOF typeName ) )", 71, 0, input);

                throw nvae;
            }

            switch (alt71) {
                case 1 :
                    // GNUCa.g:411:44: expression
                    {
                    pushFollow(FOLLOW_expression_in_typeofSpecifier2576);
                    expression193=expression();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_expression.add(expression193.getTree());

                    // AST REWRITE
                    // elements: expression
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 411:55: -> ^( TYPEOF expression )
                    {
                        // GNUCa.g:411:58: ^( TYPEOF expression )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(adaptor.create(TYPEOF, "TYPEOF"), root_1);

                        adaptor.addChild(root_1, stream_expression.next());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;
                case 2 :
                    // GNUCa.g:411:80: typeName
                    {
                    pushFollow(FOLLOW_typeName_in_typeofSpecifier2587);
                    typeName194=typeName();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_typeName.add(typeName194.getTree());

                    // AST REWRITE
                    // elements: typeName
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 411:89: -> ^( TYPEOF typeName )
                    {
                        // GNUCa.g:411:92: ^( TYPEOF typeName )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(adaptor.create(TYPEOF, "TYPEOF"), root_1);

                        adaptor.addChild(root_1, stream_typeName.next());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;

            }

            char_literal195=(Token)input.LT(1);
            match(input,84,FOLLOW_84_in_typeofSpecifier2598); if (failed) return retval;
            if ( backtracking==0 ) stream_84.add(char_literal195);


            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 36, typeofSpecifier_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end typeofSpecifier

    public static class initializer_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start initializer
    // GNUCa.g:414:1: initializer : ( assignmentExpression -> ^( INITIALIZER assignmentExpression ) | '{' '}' -> ^( INITIALIZER ) | '{' initializerList ( ',' )? '}' -> ^( INITIALIZER initializerList ) );
    public final initializer_return initializer() throws RecognitionException {
        initializer_return retval = new initializer_return();
        retval.start = input.LT(1);
        int initializer_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal197=null;
        Token char_literal198=null;
        Token char_literal199=null;
        Token char_literal201=null;
        Token char_literal202=null;
        assignmentExpression_return assignmentExpression196 = null;

        initializerList_return initializerList200 = null;


        Object char_literal197_tree=null;
        Object char_literal198_tree=null;
        Object char_literal199_tree=null;
        Object char_literal201_tree=null;
        Object char_literal202_tree=null;
        RewriteRuleTokenStream stream_109=new RewriteRuleTokenStream(adaptor,"token 109");
        RewriteRuleTokenStream stream_110=new RewriteRuleTokenStream(adaptor,"token 110");
        RewriteRuleTokenStream stream_86=new RewriteRuleTokenStream(adaptor,"token 86");
        RewriteRuleSubtreeStream stream_assignmentExpression=new RewriteRuleSubtreeStream(adaptor,"rule assignmentExpression");
        RewriteRuleSubtreeStream stream_initializerList=new RewriteRuleSubtreeStream(adaptor,"rule initializerList");
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 37) ) { return retval; }
            // GNUCa.g:415:2: ( assignmentExpression -> ^( INITIALIZER assignmentExpression ) | '{' '}' -> ^( INITIALIZER ) | '{' initializerList ( ',' )? '}' -> ^( INITIALIZER initializerList ) )
            int alt73=3;
            int LA73_0 = input.LA(1);

            if ( ((LA73_0>=IDENTIFIER && LA73_0<=STRING_LITERAL)||LA73_0==83||LA73_0==129||LA73_0==137||(LA73_0>=139 && LA73_0<=153)) ) {
                alt73=1;
            }
            else if ( (LA73_0==109) ) {
                int LA73_22 = input.LA(2);

                if ( (LA73_22==110) ) {
                    alt73=2;
                }
                else if ( ((LA73_22>=IDENTIFIER && LA73_22<=STRING_LITERAL)||LA73_22==83||LA73_22==109||LA73_22==127||LA73_22==129||LA73_22==134||LA73_22==137||(LA73_22>=139 && LA73_22<=153)) ) {
                    alt73=3;
                }
                else {
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("414:1: initializer : ( assignmentExpression -> ^( INITIALIZER assignmentExpression ) | '{' '}' -> ^( INITIALIZER ) | '{' initializerList ( ',' )? '}' -> ^( INITIALIZER initializerList ) );", 73, 22, input);

                    throw nvae;
                }
            }
            else {
                if (backtracking>0) {failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("414:1: initializer : ( assignmentExpression -> ^( INITIALIZER assignmentExpression ) | '{' '}' -> ^( INITIALIZER ) | '{' initializerList ( ',' )? '}' -> ^( INITIALIZER initializerList ) );", 73, 0, input);

                throw nvae;
            }
            switch (alt73) {
                case 1 :
                    // GNUCa.g:415:4: assignmentExpression
                    {
                    pushFollow(FOLLOW_assignmentExpression_in_initializer2610);
                    assignmentExpression196=assignmentExpression();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_assignmentExpression.add(assignmentExpression196.getTree());

                    // AST REWRITE
                    // elements: assignmentExpression
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 415:25: -> ^( INITIALIZER assignmentExpression )
                    {
                        // GNUCa.g:415:28: ^( INITIALIZER assignmentExpression )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(adaptor.create(INITIALIZER, "INITIALIZER"), root_1);

                        adaptor.addChild(root_1, stream_assignmentExpression.next());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;
                case 2 :
                    // GNUCa.g:416:4: '{' '}'
                    {
                    char_literal197=(Token)input.LT(1);
                    match(input,109,FOLLOW_109_in_initializer2623); if (failed) return retval;
                    if ( backtracking==0 ) stream_109.add(char_literal197);

                    char_literal198=(Token)input.LT(1);
                    match(input,110,FOLLOW_110_in_initializer2625); if (failed) return retval;
                    if ( backtracking==0 ) stream_110.add(char_literal198);


                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 416:14: -> ^( INITIALIZER )
                    {
                        // GNUCa.g:416:17: ^( INITIALIZER )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(adaptor.create(INITIALIZER, "INITIALIZER"), root_1);

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;
                case 3 :
                    // GNUCa.g:417:4: '{' initializerList ( ',' )? '}'
                    {
                    char_literal199=(Token)input.LT(1);
                    match(input,109,FOLLOW_109_in_initializer2642); if (failed) return retval;
                    if ( backtracking==0 ) stream_109.add(char_literal199);

                    pushFollow(FOLLOW_initializerList_in_initializer2644);
                    initializerList200=initializerList();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_initializerList.add(initializerList200.getTree());
                    // GNUCa.g:417:24: ( ',' )?
                    int alt72=2;
                    int LA72_0 = input.LA(1);

                    if ( (LA72_0==86) ) {
                        alt72=1;
                    }
                    switch (alt72) {
                        case 1 :
                            // GNUCa.g:0:0: ','
                            {
                            char_literal201=(Token)input.LT(1);
                            match(input,86,FOLLOW_86_in_initializer2646); if (failed) return retval;
                            if ( backtracking==0 ) stream_86.add(char_literal201);


                            }
                            break;

                    }

                    char_literal202=(Token)input.LT(1);
                    match(input,110,FOLLOW_110_in_initializer2649); if (failed) return retval;
                    if ( backtracking==0 ) stream_110.add(char_literal202);


                    // AST REWRITE
                    // elements: initializerList
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 417:33: -> ^( INITIALIZER initializerList )
                    {
                        // GNUCa.g:417:36: ^( INITIALIZER initializerList )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(adaptor.create(INITIALIZER, "INITIALIZER"), root_1);

                        adaptor.addChild(root_1, stream_initializerList.next());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 37, initializer_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end initializer

    public static class initializerList_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start initializerList
    // GNUCa.g:420:1: initializerList : ( designation )? initializer ( ',' ( ( designation )? initializer ) )* -> ( ( designation )? initializer )+ ;
    public final initializerList_return initializerList() throws RecognitionException {
        initializerList_return retval = new initializerList_return();
        retval.start = input.LT(1);
        int initializerList_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal205=null;
        designation_return designation203 = null;

        initializer_return initializer204 = null;

        designation_return designation206 = null;

        initializer_return initializer207 = null;


        Object char_literal205_tree=null;
        RewriteRuleTokenStream stream_86=new RewriteRuleTokenStream(adaptor,"token 86");
        RewriteRuleSubtreeStream stream_designation=new RewriteRuleSubtreeStream(adaptor,"rule designation");
        RewriteRuleSubtreeStream stream_initializer=new RewriteRuleSubtreeStream(adaptor,"rule initializer");
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 38) ) { return retval; }
            // GNUCa.g:421:2: ( ( designation )? initializer ( ',' ( ( designation )? initializer ) )* -> ( ( designation )? initializer )+ )
            // GNUCa.g:421:4: ( designation )? initializer ( ',' ( ( designation )? initializer ) )*
            {
            // GNUCa.g:421:4: ( designation )?
            int alt74=2;
            int LA74_0 = input.LA(1);

            if ( (LA74_0==127||LA74_0==134) ) {
                alt74=1;
            }
            else if ( (LA74_0==IDENTIFIER) ) {
                int LA74_2 = input.LA(2);

                if ( (LA74_2==113) ) {
                    alt74=1;
                }
            }
            switch (alt74) {
                case 1 :
                    // GNUCa.g:0:0: designation
                    {
                    pushFollow(FOLLOW_designation_in_initializerList2668);
                    designation203=designation();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_designation.add(designation203.getTree());

                    }
                    break;

            }

            pushFollow(FOLLOW_initializer_in_initializerList2671);
            initializer204=initializer();
            _fsp--;
            if (failed) return retval;
            if ( backtracking==0 ) stream_initializer.add(initializer204.getTree());
            // GNUCa.g:421:29: ( ',' ( ( designation )? initializer ) )*
            loop76:
            do {
                int alt76=2;
                int LA76_0 = input.LA(1);

                if ( (LA76_0==86) ) {
                    int LA76_1 = input.LA(2);

                    if ( ((LA76_1>=IDENTIFIER && LA76_1<=STRING_LITERAL)||LA76_1==83||LA76_1==109||LA76_1==127||LA76_1==129||LA76_1==134||LA76_1==137||(LA76_1>=139 && LA76_1<=153)) ) {
                        alt76=1;
                    }


                }


                switch (alt76) {
            	case 1 :
            	    // GNUCa.g:421:30: ',' ( ( designation )? initializer )
            	    {
            	    char_literal205=(Token)input.LT(1);
            	    match(input,86,FOLLOW_86_in_initializerList2674); if (failed) return retval;
            	    if ( backtracking==0 ) stream_86.add(char_literal205);

            	    // GNUCa.g:421:34: ( ( designation )? initializer )
            	    // GNUCa.g:421:35: ( designation )? initializer
            	    {
            	    // GNUCa.g:421:35: ( designation )?
            	    int alt75=2;
            	    int LA75_0 = input.LA(1);

            	    if ( (LA75_0==127||LA75_0==134) ) {
            	        alt75=1;
            	    }
            	    else if ( (LA75_0==IDENTIFIER) ) {
            	        int LA75_2 = input.LA(2);

            	        if ( (LA75_2==113) ) {
            	            alt75=1;
            	        }
            	    }
            	    switch (alt75) {
            	        case 1 :
            	            // GNUCa.g:0:0: designation
            	            {
            	            pushFollow(FOLLOW_designation_in_initializerList2677);
            	            designation206=designation();
            	            _fsp--;
            	            if (failed) return retval;
            	            if ( backtracking==0 ) stream_designation.add(designation206.getTree());

            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_initializer_in_initializerList2680);
            	    initializer207=initializer();
            	    _fsp--;
            	    if (failed) return retval;
            	    if ( backtracking==0 ) stream_initializer.add(initializer207.getTree());

            	    }


            	    }
            	    break;

            	default :
            	    break loop76;
                }
            } while (true);


            // AST REWRITE
            // elements: designation, initializer
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 421:63: -> ( ( designation )? initializer )+
            {
                if ( !(stream_initializer.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_initializer.hasNext() ) {
                    // GNUCa.g:421:67: ( designation )?
                    if ( stream_designation.hasNext() ) {
                        adaptor.addChild(root_0, stream_designation.next());

                    }
                    stream_designation.reset();
                    adaptor.addChild(root_0, stream_initializer.next());

                }
                stream_initializer.reset();

            }

            }

            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 38, initializerList_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end initializerList

    public static class designation_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start designation
    // GNUCa.g:424:1: designation : ( arrayDesignator -> | IDENTIFIER ':' | ( designator )+ '=' -> ( ^( DESIGNATOR designator ) )+ );
    public final designation_return designation() throws RecognitionException {
        designation_return retval = new designation_return();
        retval.start = input.LT(1);
        int designation_StartIndex = input.index();
        Object root_0 = null;

        Token IDENTIFIER209=null;
        Token char_literal210=null;
        Token char_literal212=null;
        arrayDesignator_return arrayDesignator208 = null;

        designator_return designator211 = null;


        Object IDENTIFIER209_tree=null;
        Object char_literal210_tree=null;
        Object char_literal212_tree=null;
        RewriteRuleTokenStream stream_87=new RewriteRuleTokenStream(adaptor,"token 87");
        RewriteRuleSubtreeStream stream_arrayDesignator=new RewriteRuleSubtreeStream(adaptor,"rule arrayDesignator");
        RewriteRuleSubtreeStream stream_designator=new RewriteRuleSubtreeStream(adaptor,"rule designator");
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 39) ) { return retval; }
            // GNUCa.g:425:2: ( arrayDesignator -> | IDENTIFIER ':' | ( designator )+ '=' -> ( ^( DESIGNATOR designator ) )+ )
            int alt78=3;
            switch ( input.LA(1) ) {
            case 127:
                {
                switch ( input.LA(2) ) {
                case 83:
                    {
                    int LA78_4 = input.LA(3);

                    if ( (synpred134()) ) {
                        alt78=1;
                    }
                    else if ( (true) ) {
                        alt78=3;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("424:1: designation : ( arrayDesignator -> | IDENTIFIER ':' | ( designator )+ '=' -> ( ^( DESIGNATOR designator ) )+ );", 78, 4, input);

                        throw nvae;
                    }
                    }
                    break;
                case IDENTIFIER:
                    {
                    int LA78_5 = input.LA(3);

                    if ( (synpred134()) ) {
                        alt78=1;
                    }
                    else if ( (true) ) {
                        alt78=3;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("424:1: designation : ( arrayDesignator -> | IDENTIFIER ':' | ( designator )+ '=' -> ( ^( DESIGNATOR designator ) )+ );", 78, 5, input);

                        throw nvae;
                    }
                    }
                    break;
                case CONSTANT:
                    {
                    int LA78_6 = input.LA(3);

                    if ( (synpred134()) ) {
                        alt78=1;
                    }
                    else if ( (true) ) {
                        alt78=3;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("424:1: designation : ( arrayDesignator -> | IDENTIFIER ':' | ( designator )+ '=' -> ( ^( DESIGNATOR designator ) )+ );", 78, 6, input);

                        throw nvae;
                    }
                    }
                    break;
                case STRING_LITERAL:
                    {
                    int LA78_7 = input.LA(3);

                    if ( (synpred134()) ) {
                        alt78=1;
                    }
                    else if ( (true) ) {
                        alt78=3;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("424:1: designation : ( arrayDesignator -> | IDENTIFIER ':' | ( designator )+ '=' -> ( ^( DESIGNATOR designator ) )+ );", 78, 7, input);

                        throw nvae;
                    }
                    }
                    break;
                case 137:
                    {
                    int LA78_8 = input.LA(3);

                    if ( (synpred134()) ) {
                        alt78=1;
                    }
                    else if ( (true) ) {
                        alt78=3;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("424:1: designation : ( arrayDesignator -> | IDENTIFIER ':' | ( designator )+ '=' -> ( ^( DESIGNATOR designator ) )+ );", 78, 8, input);

                        throw nvae;
                    }
                    }
                    break;
                case 139:
                    {
                    int LA78_9 = input.LA(3);

                    if ( (synpred134()) ) {
                        alt78=1;
                    }
                    else if ( (true) ) {
                        alt78=3;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("424:1: designation : ( arrayDesignator -> | IDENTIFIER ':' | ( designator )+ '=' -> ( ^( DESIGNATOR designator ) )+ );", 78, 9, input);

                        throw nvae;
                    }
                    }
                    break;
                case 140:
                    {
                    int LA78_10 = input.LA(3);

                    if ( (synpred134()) ) {
                        alt78=1;
                    }
                    else if ( (true) ) {
                        alt78=3;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("424:1: designation : ( arrayDesignator -> | IDENTIFIER ':' | ( designator )+ '=' -> ( ^( DESIGNATOR designator ) )+ );", 78, 10, input);

                        throw nvae;
                    }
                    }
                    break;
                case 144:
                    {
                    int LA78_11 = input.LA(3);

                    if ( (synpred134()) ) {
                        alt78=1;
                    }
                    else if ( (true) ) {
                        alt78=3;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("424:1: designation : ( arrayDesignator -> | IDENTIFIER ':' | ( designator )+ '=' -> ( ^( DESIGNATOR designator ) )+ );", 78, 11, input);

                        throw nvae;
                    }
                    }
                    break;
                case 129:
                    {
                    int LA78_12 = input.LA(3);

                    if ( (synpred134()) ) {
                        alt78=1;
                    }
                    else if ( (true) ) {
                        alt78=3;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("424:1: designation : ( arrayDesignator -> | IDENTIFIER ':' | ( designator )+ '=' -> ( ^( DESIGNATOR designator ) )+ );", 78, 12, input);

                        throw nvae;
                    }
                    }
                    break;
                case 145:
                    {
                    int LA78_13 = input.LA(3);

                    if ( (synpred134()) ) {
                        alt78=1;
                    }
                    else if ( (true) ) {
                        alt78=3;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("424:1: designation : ( arrayDesignator -> | IDENTIFIER ':' | ( designator )+ '=' -> ( ^( DESIGNATOR designator ) )+ );", 78, 13, input);

                        throw nvae;
                    }
                    }
                    break;
                case 146:
                    {
                    int LA78_14 = input.LA(3);

                    if ( (synpred134()) ) {
                        alt78=1;
                    }
                    else if ( (true) ) {
                        alt78=3;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("424:1: designation : ( arrayDesignator -> | IDENTIFIER ':' | ( designator )+ '=' -> ( ^( DESIGNATOR designator ) )+ );", 78, 14, input);

                        throw nvae;
                    }
                    }
                    break;
                case 147:
                    {
                    int LA78_15 = input.LA(3);

                    if ( (synpred134()) ) {
                        alt78=1;
                    }
                    else if ( (true) ) {
                        alt78=3;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("424:1: designation : ( arrayDesignator -> | IDENTIFIER ':' | ( designator )+ '=' -> ( ^( DESIGNATOR designator ) )+ );", 78, 15, input);

                        throw nvae;
                    }
                    }
                    break;
                case 148:
                    {
                    int LA78_16 = input.LA(3);

                    if ( (synpred134()) ) {
                        alt78=1;
                    }
                    else if ( (true) ) {
                        alt78=3;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("424:1: designation : ( arrayDesignator -> | IDENTIFIER ':' | ( designator )+ '=' -> ( ^( DESIGNATOR designator ) )+ );", 78, 16, input);

                        throw nvae;
                    }
                    }
                    break;
                case 149:
                    {
                    int LA78_17 = input.LA(3);

                    if ( (synpred134()) ) {
                        alt78=1;
                    }
                    else if ( (true) ) {
                        alt78=3;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("424:1: designation : ( arrayDesignator -> | IDENTIFIER ':' | ( designator )+ '=' -> ( ^( DESIGNATOR designator ) )+ );", 78, 17, input);

                        throw nvae;
                    }
                    }
                    break;
                case 150:
                    {
                    int LA78_18 = input.LA(3);

                    if ( (synpred134()) ) {
                        alt78=1;
                    }
                    else if ( (true) ) {
                        alt78=3;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("424:1: designation : ( arrayDesignator -> | IDENTIFIER ':' | ( designator )+ '=' -> ( ^( DESIGNATOR designator ) )+ );", 78, 18, input);

                        throw nvae;
                    }
                    }
                    break;
                case 151:
                    {
                    int LA78_19 = input.LA(3);

                    if ( (synpred134()) ) {
                        alt78=1;
                    }
                    else if ( (true) ) {
                        alt78=3;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("424:1: designation : ( arrayDesignator -> | IDENTIFIER ':' | ( designator )+ '=' -> ( ^( DESIGNATOR designator ) )+ );", 78, 19, input);

                        throw nvae;
                    }
                    }
                    break;
                case 152:
                    {
                    int LA78_20 = input.LA(3);

                    if ( (synpred134()) ) {
                        alt78=1;
                    }
                    else if ( (true) ) {
                        alt78=3;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("424:1: designation : ( arrayDesignator -> | IDENTIFIER ':' | ( designator )+ '=' -> ( ^( DESIGNATOR designator ) )+ );", 78, 20, input);

                        throw nvae;
                    }
                    }
                    break;
                case 153:
                    {
                    int LA78_21 = input.LA(3);

                    if ( (synpred134()) ) {
                        alt78=1;
                    }
                    else if ( (true) ) {
                        alt78=3;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("424:1: designation : ( arrayDesignator -> | IDENTIFIER ':' | ( designator )+ '=' -> ( ^( DESIGNATOR designator ) )+ );", 78, 21, input);

                        throw nvae;
                    }
                    }
                    break;
                case 141:
                    {
                    int LA78_22 = input.LA(3);

                    if ( (synpred134()) ) {
                        alt78=1;
                    }
                    else if ( (true) ) {
                        alt78=3;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("424:1: designation : ( arrayDesignator -> | IDENTIFIER ':' | ( designator )+ '=' -> ( ^( DESIGNATOR designator ) )+ );", 78, 22, input);

                        throw nvae;
                    }
                    }
                    break;
                case 142:
                    {
                    int LA78_23 = input.LA(3);

                    if ( (synpred134()) ) {
                        alt78=1;
                    }
                    else if ( (true) ) {
                        alt78=3;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("424:1: designation : ( arrayDesignator -> | IDENTIFIER ':' | ( designator )+ '=' -> ( ^( DESIGNATOR designator ) )+ );", 78, 23, input);

                        throw nvae;
                    }
                    }
                    break;
                case 143:
                    {
                    int LA78_24 = input.LA(3);

                    if ( (synpred134()) ) {
                        alt78=1;
                    }
                    else if ( (true) ) {
                        alt78=3;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("424:1: designation : ( arrayDesignator -> | IDENTIFIER ':' | ( designator )+ '=' -> ( ^( DESIGNATOR designator ) )+ );", 78, 24, input);

                        throw nvae;
                    }
                    }
                    break;
                default:
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("424:1: designation : ( arrayDesignator -> | IDENTIFIER ':' | ( designator )+ '=' -> ( ^( DESIGNATOR designator ) )+ );", 78, 1, input);

                    throw nvae;
                }

                }
                break;
            case IDENTIFIER:
                {
                alt78=2;
                }
                break;
            case 134:
                {
                alt78=3;
                }
                break;
            default:
                if (backtracking>0) {failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("424:1: designation : ( arrayDesignator -> | IDENTIFIER ':' | ( designator )+ '=' -> ( ^( DESIGNATOR designator ) )+ );", 78, 0, input);

                throw nvae;
            }

            switch (alt78) {
                case 1 :
                    // GNUCa.g:425:4: arrayDesignator
                    {
                    pushFollow(FOLLOW_arrayDesignator_in_designation2704);
                    arrayDesignator208=arrayDesignator();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_arrayDesignator.add(arrayDesignator208.getTree());

                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 425:21: ->
                    {
                        root_0 = null;
                    }

                    }

                    }
                    break;
                case 2 :
                    // GNUCa.g:426:4: IDENTIFIER ':'
                    {
                    root_0 = (Object)adaptor.nil();

                    IDENTIFIER209=(Token)input.LT(1);
                    match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_designation2713); if (failed) return retval;
                    if ( backtracking==0 ) {
                    IDENTIFIER209_tree = (Object)adaptor.create(IDENTIFIER209);
                    adaptor.addChild(root_0, IDENTIFIER209_tree);
                    }
                    char_literal210=(Token)input.LT(1);
                    match(input,113,FOLLOW_113_in_designation2715); if (failed) return retval;

                    }
                    break;
                case 3 :
                    // GNUCa.g:427:4: ( designator )+ '='
                    {
                    // GNUCa.g:427:4: ( designator )+
                    int cnt77=0;
                    loop77:
                    do {
                        int alt77=2;
                        int LA77_0 = input.LA(1);

                        if ( (LA77_0==127||LA77_0==134) ) {
                            alt77=1;
                        }


                        switch (alt77) {
                    	case 1 :
                    	    // GNUCa.g:0:0: designator
                    	    {
                    	    pushFollow(FOLLOW_designator_in_designation2724);
                    	    designator211=designator();
                    	    _fsp--;
                    	    if (failed) return retval;
                    	    if ( backtracking==0 ) stream_designator.add(designator211.getTree());

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt77 >= 1 ) break loop77;
                    	    if (backtracking>0) {failed=true; return retval;}
                                EarlyExitException eee =
                                    new EarlyExitException(77, input);
                                throw eee;
                        }
                        cnt77++;
                    } while (true);

                    char_literal212=(Token)input.LT(1);
                    match(input,87,FOLLOW_87_in_designation2727); if (failed) return retval;
                    if ( backtracking==0 ) stream_87.add(char_literal212);


                    // AST REWRITE
                    // elements: designator
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 427:20: -> ( ^( DESIGNATOR designator ) )+
                    {
                        if ( !(stream_designator.hasNext()) ) {
                            throw new RewriteEarlyExitException();
                        }
                        while ( stream_designator.hasNext() ) {
                            // GNUCa.g:427:23: ^( DESIGNATOR designator )
                            {
                            Object root_1 = (Object)adaptor.nil();
                            root_1 = (Object)adaptor.becomeRoot(adaptor.create(DESIGNATOR, "DESIGNATOR"), root_1);

                            adaptor.addChild(root_1, stream_designator.next());

                            adaptor.addChild(root_0, root_1);
                            }

                        }
                        stream_designator.reset();

                    }

                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 39, designation_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end designation

    public static class designator_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start designator
    // GNUCa.g:430:1: designator : ( '[' constantExpression ']' -> ^( BRACKET_DESIGNATOR constantExpression ) | '.' IDENTIFIER );
    public final designator_return designator() throws RecognitionException {
        designator_return retval = new designator_return();
        retval.start = input.LT(1);
        int designator_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal213=null;
        Token char_literal215=null;
        Token char_literal216=null;
        Token IDENTIFIER217=null;
        constantExpression_return constantExpression214 = null;


        Object char_literal213_tree=null;
        Object char_literal215_tree=null;
        Object char_literal216_tree=null;
        Object IDENTIFIER217_tree=null;
        RewriteRuleTokenStream stream_127=new RewriteRuleTokenStream(adaptor,"token 127");
        RewriteRuleTokenStream stream_128=new RewriteRuleTokenStream(adaptor,"token 128");
        RewriteRuleSubtreeStream stream_constantExpression=new RewriteRuleSubtreeStream(adaptor,"rule constantExpression");
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 40) ) { return retval; }
            // GNUCa.g:431:2: ( '[' constantExpression ']' -> ^( BRACKET_DESIGNATOR constantExpression ) | '.' IDENTIFIER )
            int alt79=2;
            int LA79_0 = input.LA(1);

            if ( (LA79_0==127) ) {
                alt79=1;
            }
            else if ( (LA79_0==134) ) {
                alt79=2;
            }
            else {
                if (backtracking>0) {failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("430:1: designator : ( '[' constantExpression ']' -> ^( BRACKET_DESIGNATOR constantExpression ) | '.' IDENTIFIER );", 79, 0, input);

                throw nvae;
            }
            switch (alt79) {
                case 1 :
                    // GNUCa.g:431:4: '[' constantExpression ']'
                    {
                    char_literal213=(Token)input.LT(1);
                    match(input,127,FOLLOW_127_in_designator2749); if (failed) return retval;
                    if ( backtracking==0 ) stream_127.add(char_literal213);

                    pushFollow(FOLLOW_constantExpression_in_designator2751);
                    constantExpression214=constantExpression();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_constantExpression.add(constantExpression214.getTree());
                    char_literal215=(Token)input.LT(1);
                    match(input,128,FOLLOW_128_in_designator2753); if (failed) return retval;
                    if ( backtracking==0 ) stream_128.add(char_literal215);


                    // AST REWRITE
                    // elements: constantExpression
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 431:31: -> ^( BRACKET_DESIGNATOR constantExpression )
                    {
                        // GNUCa.g:431:34: ^( BRACKET_DESIGNATOR constantExpression )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(adaptor.create(BRACKET_DESIGNATOR, "BRACKET_DESIGNATOR"), root_1);

                        adaptor.addChild(root_1, stream_constantExpression.next());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;
                case 2 :
                    // GNUCa.g:432:4: '.' IDENTIFIER
                    {
                    root_0 = (Object)adaptor.nil();

                    char_literal216=(Token)input.LT(1);
                    match(input,134,FOLLOW_134_in_designator2766); if (failed) return retval;
                    IDENTIFIER217=(Token)input.LT(1);
                    match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_designator2769); if (failed) return retval;
                    if ( backtracking==0 ) {
                    IDENTIFIER217_tree = (Object)adaptor.create(IDENTIFIER217);
                    adaptor.addChild(root_0, IDENTIFIER217_tree);
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 40, designator_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end designator

    public static class arrayDesignator_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start arrayDesignator
    // GNUCa.g:435:1: arrayDesignator : '[' constantExpression '...' constantExpression ']' ;
    public final arrayDesignator_return arrayDesignator() throws RecognitionException {
        arrayDesignator_return retval = new arrayDesignator_return();
        retval.start = input.LT(1);
        int arrayDesignator_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal218=null;
        Token string_literal220=null;
        Token char_literal222=null;
        constantExpression_return constantExpression219 = null;

        constantExpression_return constantExpression221 = null;


        Object char_literal218_tree=null;
        Object string_literal220_tree=null;
        Object char_literal222_tree=null;

        try {
            if ( backtracking>0 && alreadyParsedRule(input, 41) ) { return retval; }
            // GNUCa.g:436:2: ( '[' constantExpression '...' constantExpression ']' )
            // GNUCa.g:436:4: '[' constantExpression '...' constantExpression ']'
            {
            root_0 = (Object)adaptor.nil();

            char_literal218=(Token)input.LT(1);
            match(input,127,FOLLOW_127_in_arrayDesignator2785); if (failed) return retval;
            if ( backtracking==0 ) {
            char_literal218_tree = (Object)adaptor.create(char_literal218);
            adaptor.addChild(root_0, char_literal218_tree);
            }
            pushFollow(FOLLOW_constantExpression_in_arrayDesignator2787);
            constantExpression219=constantExpression();
            _fsp--;
            if (failed) return retval;
            if ( backtracking==0 ) adaptor.addChild(root_0, constantExpression219.getTree());
            string_literal220=(Token)input.LT(1);
            match(input,130,FOLLOW_130_in_arrayDesignator2789); if (failed) return retval;
            if ( backtracking==0 ) {
            string_literal220_tree = (Object)adaptor.create(string_literal220);
            adaptor.addChild(root_0, string_literal220_tree);
            }
            pushFollow(FOLLOW_constantExpression_in_arrayDesignator2791);
            constantExpression221=constantExpression();
            _fsp--;
            if (failed) return retval;
            if ( backtracking==0 ) adaptor.addChild(root_0, constantExpression221.getTree());
            char_literal222=(Token)input.LT(1);
            match(input,128,FOLLOW_128_in_arrayDesignator2793); if (failed) return retval;
            if ( backtracking==0 ) {
            char_literal222_tree = (Object)adaptor.create(char_literal222);
            adaptor.addChild(root_0, char_literal222_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 41, arrayDesignator_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end arrayDesignator

    public static class attributes_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start attributes
    // GNUCa.g:439:1: attributes : ( attribute )+ ;
    public final attributes_return attributes() throws RecognitionException {
        attributes_return retval = new attributes_return();
        retval.start = input.LT(1);
        int attributes_StartIndex = input.index();
        Object root_0 = null;

        attribute_return attribute223 = null;



        try {
            if ( backtracking>0 && alreadyParsedRule(input, 42) ) { return retval; }
            // GNUCa.g:440:2: ( ( attribute )+ )
            // GNUCa.g:440:4: ( attribute )+
            {
            root_0 = (Object)adaptor.nil();

            // GNUCa.g:440:4: ( attribute )+
            int cnt80=0;
            loop80:
            do {
                int alt80=2;
                int LA80_0 = input.LA(1);

                if ( ((LA80_0>=135 && LA80_0<=136)) ) {
                    int LA80_6 = input.LA(2);

                    if ( (synpred138()) ) {
                        alt80=1;
                    }


                }


                switch (alt80) {
            	case 1 :
            	    // GNUCa.g:0:0: attribute
            	    {
            	    pushFollow(FOLLOW_attribute_in_attributes2804);
            	    attribute223=attribute();
            	    _fsp--;
            	    if (failed) return retval;
            	    if ( backtracking==0 ) adaptor.addChild(root_0, attribute223.getTree());

            	    }
            	    break;

            	default :
            	    if ( cnt80 >= 1 ) break loop80;
            	    if (backtracking>0) {failed=true; return retval;}
                        EarlyExitException eee =
                            new EarlyExitException(80, input);
                        throw eee;
                }
                cnt80++;
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 42, attributes_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end attributes

    public static class attribute_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start attribute
    // GNUCa.g:443:1: attribute : ( '__attribute' | '__attribute__' ) '(' '(' attributeList ')' ')' ;
    public final attribute_return attribute() throws RecognitionException {
        attribute_return retval = new attribute_return();
        retval.start = input.LT(1);
        int attribute_StartIndex = input.index();
        Object root_0 = null;

        Token set224=null;
        Token char_literal225=null;
        Token char_literal226=null;
        Token char_literal228=null;
        Token char_literal229=null;
        attributeList_return attributeList227 = null;


        Object set224_tree=null;
        Object char_literal225_tree=null;
        Object char_literal226_tree=null;
        Object char_literal228_tree=null;
        Object char_literal229_tree=null;

        try {
            if ( backtracking>0 && alreadyParsedRule(input, 43) ) { return retval; }
            // GNUCa.g:444:2: ( ( '__attribute' | '__attribute__' ) '(' '(' attributeList ')' ')' )
            // GNUCa.g:444:4: ( '__attribute' | '__attribute__' ) '(' '(' attributeList ')' ')'
            {
            root_0 = (Object)adaptor.nil();

            set224=(Token)input.LT(1);
            if ( (input.LA(1)>=135 && input.LA(1)<=136) ) {
                input.consume();
                if ( backtracking==0 ) adaptor.addChild(root_0, adaptor.create(set224));
                errorRecovery=false;failed=false;
            }
            else {
                if (backtracking>0) {failed=true; return retval;}
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recoverFromMismatchedSet(input,mse,FOLLOW_set_in_attribute2816);    throw mse;
            }

            char_literal225=(Token)input.LT(1);
            match(input,83,FOLLOW_83_in_attribute2822); if (failed) return retval;
            if ( backtracking==0 ) {
            char_literal225_tree = (Object)adaptor.create(char_literal225);
            adaptor.addChild(root_0, char_literal225_tree);
            }
            char_literal226=(Token)input.LT(1);
            match(input,83,FOLLOW_83_in_attribute2824); if (failed) return retval;
            if ( backtracking==0 ) {
            char_literal226_tree = (Object)adaptor.create(char_literal226);
            adaptor.addChild(root_0, char_literal226_tree);
            }
            pushFollow(FOLLOW_attributeList_in_attribute2826);
            attributeList227=attributeList();
            _fsp--;
            if (failed) return retval;
            if ( backtracking==0 ) adaptor.addChild(root_0, attributeList227.getTree());
            char_literal228=(Token)input.LT(1);
            match(input,84,FOLLOW_84_in_attribute2828); if (failed) return retval;
            if ( backtracking==0 ) {
            char_literal228_tree = (Object)adaptor.create(char_literal228);
            adaptor.addChild(root_0, char_literal228_tree);
            }
            char_literal229=(Token)input.LT(1);
            match(input,84,FOLLOW_84_in_attribute2830); if (failed) return retval;
            if ( backtracking==0 ) {
            char_literal229_tree = (Object)adaptor.create(char_literal229);
            adaptor.addChild(root_0, char_literal229_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 43, attribute_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end attribute

    public static class attributeList_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start attributeList
    // GNUCa.g:447:1: attributeList : attrib ( ',' attrib )* ;
    public final attributeList_return attributeList() throws RecognitionException {
        attributeList_return retval = new attributeList_return();
        retval.start = input.LT(1);
        int attributeList_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal231=null;
        attrib_return attrib230 = null;

        attrib_return attrib232 = null;


        Object char_literal231_tree=null;

        try {
            if ( backtracking>0 && alreadyParsedRule(input, 44) ) { return retval; }
            // GNUCa.g:448:2: ( attrib ( ',' attrib )* )
            // GNUCa.g:448:4: attrib ( ',' attrib )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_attrib_in_attributeList2841);
            attrib230=attrib();
            _fsp--;
            if (failed) return retval;
            if ( backtracking==0 ) adaptor.addChild(root_0, attrib230.getTree());
            // GNUCa.g:448:11: ( ',' attrib )*
            loop81:
            do {
                int alt81=2;
                int LA81_0 = input.LA(1);

                if ( (LA81_0==86) ) {
                    alt81=1;
                }


                switch (alt81) {
            	case 1 :
            	    // GNUCa.g:448:12: ',' attrib
            	    {
            	    char_literal231=(Token)input.LT(1);
            	    match(input,86,FOLLOW_86_in_attributeList2844); if (failed) return retval;
            	    if ( backtracking==0 ) {
            	    char_literal231_tree = (Object)adaptor.create(char_literal231);
            	    adaptor.addChild(root_0, char_literal231_tree);
            	    }
            	    pushFollow(FOLLOW_attrib_in_attributeList2846);
            	    attrib232=attrib();
            	    _fsp--;
            	    if (failed) return retval;
            	    if ( backtracking==0 ) adaptor.addChild(root_0, attrib232.getTree());

            	    }
            	    break;

            	default :
            	    break loop81;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 44, attributeList_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end attributeList

    public static class attrib_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start attrib
    // GNUCa.g:451:1: attrib : (~ ( '(' | ')' | ',' ) | '(' attributeList ')' )* ;
    public final attrib_return attrib() throws RecognitionException {
        attrib_return retval = new attrib_return();
        retval.start = input.LT(1);
        int attrib_StartIndex = input.index();
        Object root_0 = null;

        Token set233=null;
        Token char_literal234=null;
        Token char_literal236=null;
        attributeList_return attributeList235 = null;


        Object set233_tree=null;
        Object char_literal234_tree=null;
        Object char_literal236_tree=null;

        try {
            if ( backtracking>0 && alreadyParsedRule(input, 45) ) { return retval; }
            // GNUCa.g:452:2: ( (~ ( '(' | ')' | ',' ) | '(' attributeList ')' )* )
            // GNUCa.g:452:8: (~ ( '(' | ')' | ',' ) | '(' attributeList ')' )*
            {
            root_0 = (Object)adaptor.nil();

            // GNUCa.g:452:8: (~ ( '(' | ')' | ',' ) | '(' attributeList ')' )*
            loop82:
            do {
                int alt82=3;
                int LA82_0 = input.LA(1);

                if ( ((LA82_0>=BRACKET_DESIGNATOR && LA82_0<=82)||LA82_0==85||(LA82_0>=87 && LA82_0<=189)) ) {
                    alt82=1;
                }
                else if ( (LA82_0==83) ) {
                    alt82=2;
                }


                switch (alt82) {
            	case 1 :
            	    // GNUCa.g:452:10: ~ ( '(' | ')' | ',' )
            	    {
            	    set233=(Token)input.LT(1);
            	    if ( (input.LA(1)>=BRACKET_DESIGNATOR && input.LA(1)<=82)||input.LA(1)==85||(input.LA(1)>=87 && input.LA(1)<=189) ) {
            	        input.consume();
            	        if ( backtracking==0 ) adaptor.addChild(root_0, adaptor.create(set233));
            	        errorRecovery=false;failed=false;
            	    }
            	    else {
            	        if (backtracking>0) {failed=true; return retval;}
            	        MismatchedSetException mse =
            	            new MismatchedSetException(null,input);
            	        recoverFromMismatchedSet(input,mse,FOLLOW_set_in_attrib2867);    throw mse;
            	    }


            	    }
            	    break;
            	case 2 :
            	    // GNUCa.g:453:4: '(' attributeList ')'
            	    {
            	    char_literal234=(Token)input.LT(1);
            	    match(input,83,FOLLOW_83_in_attrib2881); if (failed) return retval;
            	    if ( backtracking==0 ) {
            	    char_literal234_tree = (Object)adaptor.create(char_literal234);
            	    adaptor.addChild(root_0, char_literal234_tree);
            	    }
            	    pushFollow(FOLLOW_attributeList_in_attrib2883);
            	    attributeList235=attributeList();
            	    _fsp--;
            	    if (failed) return retval;
            	    if ( backtracking==0 ) adaptor.addChild(root_0, attributeList235.getTree());
            	    char_literal236=(Token)input.LT(1);
            	    match(input,84,FOLLOW_84_in_attrib2885); if (failed) return retval;
            	    if ( backtracking==0 ) {
            	    char_literal236_tree = (Object)adaptor.create(char_literal236);
            	    adaptor.addChild(root_0, char_literal236_tree);
            	    }

            	    }
            	    break;

            	default :
            	    break loop82;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 45, attrib_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end attrib

    public static class primaryExpression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start primaryExpression
    // GNUCa.g:458:1: primaryExpression : ( IDENTIFIER | CONSTANT | sTRING_LITERAL | '(' compoundStatement ')' | '(' expression ')' | '__builtin_offsetof' '(' typeName ',' offsetofMemberDesignator ')' -> ^( BUILTIN_OFFSETOF typeName offsetofMemberDesignator ) );
    public final primaryExpression_return primaryExpression() throws RecognitionException {
        primaryExpression_return retval = new primaryExpression_return();
        retval.start = input.LT(1);
        int primaryExpression_StartIndex = input.index();
        Object root_0 = null;

        Token IDENTIFIER237=null;
        Token CONSTANT238=null;
        Token char_literal240=null;
        Token char_literal242=null;
        Token char_literal243=null;
        Token char_literal245=null;
        Token string_literal246=null;
        Token char_literal247=null;
        Token char_literal249=null;
        Token char_literal251=null;
        sTRING_LITERAL_return sTRING_LITERAL239 = null;

        compoundStatement_return compoundStatement241 = null;

        expression_return expression244 = null;

        typeName_return typeName248 = null;

        offsetofMemberDesignator_return offsetofMemberDesignator250 = null;


        Object IDENTIFIER237_tree=null;
        Object CONSTANT238_tree=null;
        Object char_literal240_tree=null;
        Object char_literal242_tree=null;
        Object char_literal243_tree=null;
        Object char_literal245_tree=null;
        Object string_literal246_tree=null;
        Object char_literal247_tree=null;
        Object char_literal249_tree=null;
        Object char_literal251_tree=null;
        RewriteRuleTokenStream stream_137=new RewriteRuleTokenStream(adaptor,"token 137");
        RewriteRuleTokenStream stream_83=new RewriteRuleTokenStream(adaptor,"token 83");
        RewriteRuleTokenStream stream_86=new RewriteRuleTokenStream(adaptor,"token 86");
        RewriteRuleTokenStream stream_84=new RewriteRuleTokenStream(adaptor,"token 84");
        RewriteRuleSubtreeStream stream_typeName=new RewriteRuleSubtreeStream(adaptor,"rule typeName");
        RewriteRuleSubtreeStream stream_offsetofMemberDesignator=new RewriteRuleSubtreeStream(adaptor,"rule offsetofMemberDesignator");
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 46) ) { return retval; }
            // GNUCa.g:459:2: ( IDENTIFIER | CONSTANT | sTRING_LITERAL | '(' compoundStatement ')' | '(' expression ')' | '__builtin_offsetof' '(' typeName ',' offsetofMemberDesignator ')' -> ^( BUILTIN_OFFSETOF typeName offsetofMemberDesignator ) )
            int alt83=6;
            switch ( input.LA(1) ) {
            case IDENTIFIER:
                {
                alt83=1;
                }
                break;
            case CONSTANT:
                {
                alt83=2;
                }
                break;
            case STRING_LITERAL:
                {
                alt83=3;
                }
                break;
            case 83:
                {
                int LA83_4 = input.LA(2);

                if ( (LA83_4==109) ) {
                    alt83=4;
                }
                else if ( ((LA83_4>=IDENTIFIER && LA83_4<=STRING_LITERAL)||LA83_4==83||LA83_4==129||LA83_4==137||(LA83_4>=139 && LA83_4<=153)) ) {
                    alt83=5;
                }
                else {
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("458:1: primaryExpression : ( IDENTIFIER | CONSTANT | sTRING_LITERAL | '(' compoundStatement ')' | '(' expression ')' | '__builtin_offsetof' '(' typeName ',' offsetofMemberDesignator ')' -> ^( BUILTIN_OFFSETOF typeName offsetofMemberDesignator ) );", 83, 4, input);

                    throw nvae;
                }
                }
                break;
            case 137:
                {
                alt83=6;
                }
                break;
            default:
                if (backtracking>0) {failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("458:1: primaryExpression : ( IDENTIFIER | CONSTANT | sTRING_LITERAL | '(' compoundStatement ')' | '(' expression ')' | '__builtin_offsetof' '(' typeName ',' offsetofMemberDesignator ')' -> ^( BUILTIN_OFFSETOF typeName offsetofMemberDesignator ) );", 83, 0, input);

                throw nvae;
            }

            switch (alt83) {
                case 1 :
                    // GNUCa.g:459:4: IDENTIFIER
                    {
                    root_0 = (Object)adaptor.nil();

                    IDENTIFIER237=(Token)input.LT(1);
                    match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_primaryExpression2903); if (failed) return retval;
                    if ( backtracking==0 ) {
                    IDENTIFIER237_tree = (Object)adaptor.create(IDENTIFIER237);
                    adaptor.addChild(root_0, IDENTIFIER237_tree);
                    }

                    }
                    break;
                case 2 :
                    // GNUCa.g:460:4: CONSTANT
                    {
                    root_0 = (Object)adaptor.nil();

                    CONSTANT238=(Token)input.LT(1);
                    match(input,CONSTANT,FOLLOW_CONSTANT_in_primaryExpression2908); if (failed) return retval;
                    if ( backtracking==0 ) {
                    CONSTANT238_tree = (Object)adaptor.create(CONSTANT238);
                    adaptor.addChild(root_0, CONSTANT238_tree);
                    }

                    }
                    break;
                case 3 :
                    // GNUCa.g:461:4: sTRING_LITERAL
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_sTRING_LITERAL_in_primaryExpression2913);
                    sTRING_LITERAL239=sTRING_LITERAL();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) adaptor.addChild(root_0, sTRING_LITERAL239.getTree());

                    }
                    break;
                case 4 :
                    // GNUCa.g:462:4: '(' compoundStatement ')'
                    {
                    root_0 = (Object)adaptor.nil();

                    char_literal240=(Token)input.LT(1);
                    match(input,83,FOLLOW_83_in_primaryExpression2918); if (failed) return retval;
                    pushFollow(FOLLOW_compoundStatement_in_primaryExpression2921);
                    compoundStatement241=compoundStatement();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) adaptor.addChild(root_0, compoundStatement241.getTree());
                    char_literal242=(Token)input.LT(1);
                    match(input,84,FOLLOW_84_in_primaryExpression2923); if (failed) return retval;

                    }
                    break;
                case 5 :
                    // GNUCa.g:463:4: '(' expression ')'
                    {
                    root_0 = (Object)adaptor.nil();

                    char_literal243=(Token)input.LT(1);
                    match(input,83,FOLLOW_83_in_primaryExpression2930); if (failed) return retval;
                    pushFollow(FOLLOW_expression_in_primaryExpression2933);
                    expression244=expression();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) adaptor.addChild(root_0, expression244.getTree());
                    char_literal245=(Token)input.LT(1);
                    match(input,84,FOLLOW_84_in_primaryExpression2935); if (failed) return retval;

                    }
                    break;
                case 6 :
                    // GNUCa.g:464:4: '__builtin_offsetof' '(' typeName ',' offsetofMemberDesignator ')'
                    {
                    string_literal246=(Token)input.LT(1);
                    match(input,137,FOLLOW_137_in_primaryExpression2941); if (failed) return retval;
                    if ( backtracking==0 ) stream_137.add(string_literal246);

                    char_literal247=(Token)input.LT(1);
                    match(input,83,FOLLOW_83_in_primaryExpression2943); if (failed) return retval;
                    if ( backtracking==0 ) stream_83.add(char_literal247);

                    pushFollow(FOLLOW_typeName_in_primaryExpression2945);
                    typeName248=typeName();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_typeName.add(typeName248.getTree());
                    char_literal249=(Token)input.LT(1);
                    match(input,86,FOLLOW_86_in_primaryExpression2947); if (failed) return retval;
                    if ( backtracking==0 ) stream_86.add(char_literal249);

                    pushFollow(FOLLOW_offsetofMemberDesignator_in_primaryExpression2949);
                    offsetofMemberDesignator250=offsetofMemberDesignator();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_offsetofMemberDesignator.add(offsetofMemberDesignator250.getTree());
                    char_literal251=(Token)input.LT(1);
                    match(input,84,FOLLOW_84_in_primaryExpression2951); if (failed) return retval;
                    if ( backtracking==0 ) stream_84.add(char_literal251);


                    // AST REWRITE
                    // elements: offsetofMemberDesignator, typeName
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 464:71: -> ^( BUILTIN_OFFSETOF typeName offsetofMemberDesignator )
                    {
                        // GNUCa.g:464:74: ^( BUILTIN_OFFSETOF typeName offsetofMemberDesignator )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(adaptor.create(BUILTIN_OFFSETOF, "BUILTIN_OFFSETOF"), root_1);

                        adaptor.addChild(root_1, stream_typeName.next());
                        adaptor.addChild(root_1, stream_offsetofMemberDesignator.next());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 46, primaryExpression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end primaryExpression

    public static class offsetofMemberDesignator_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start offsetofMemberDesignator
    // GNUCa.g:467:1: offsetofMemberDesignator : IDENTIFIER ( ( '.' IDENTIFIER ) | ( '[' expression ']' ) )* ;
    public final offsetofMemberDesignator_return offsetofMemberDesignator() throws RecognitionException {
        offsetofMemberDesignator_return retval = new offsetofMemberDesignator_return();
        retval.start = input.LT(1);
        int offsetofMemberDesignator_StartIndex = input.index();
        Object root_0 = null;

        Token IDENTIFIER252=null;
        Token char_literal253=null;
        Token IDENTIFIER254=null;
        Token char_literal255=null;
        Token char_literal257=null;
        expression_return expression256 = null;


        Object IDENTIFIER252_tree=null;
        Object char_literal253_tree=null;
        Object IDENTIFIER254_tree=null;
        Object char_literal255_tree=null;
        Object char_literal257_tree=null;

        try {
            if ( backtracking>0 && alreadyParsedRule(input, 47) ) { return retval; }
            // GNUCa.g:468:2: ( IDENTIFIER ( ( '.' IDENTIFIER ) | ( '[' expression ']' ) )* )
            // GNUCa.g:468:8: IDENTIFIER ( ( '.' IDENTIFIER ) | ( '[' expression ']' ) )*
            {
            root_0 = (Object)adaptor.nil();

            IDENTIFIER252=(Token)input.LT(1);
            match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_offsetofMemberDesignator2976); if (failed) return retval;
            if ( backtracking==0 ) {
            IDENTIFIER252_tree = (Object)adaptor.create(IDENTIFIER252);
            adaptor.addChild(root_0, IDENTIFIER252_tree);
            }
            // GNUCa.g:468:19: ( ( '.' IDENTIFIER ) | ( '[' expression ']' ) )*
            loop84:
            do {
                int alt84=3;
                int LA84_0 = input.LA(1);

                if ( (LA84_0==134) ) {
                    alt84=1;
                }
                else if ( (LA84_0==127) ) {
                    alt84=2;
                }


                switch (alt84) {
            	case 1 :
            	    // GNUCa.g:468:21: ( '.' IDENTIFIER )
            	    {
            	    // GNUCa.g:468:21: ( '.' IDENTIFIER )
            	    // GNUCa.g:468:22: '.' IDENTIFIER
            	    {
            	    char_literal253=(Token)input.LT(1);
            	    match(input,134,FOLLOW_134_in_offsetofMemberDesignator2981); if (failed) return retval;
            	    if ( backtracking==0 ) {
            	    char_literal253_tree = (Object)adaptor.create(char_literal253);
            	    adaptor.addChild(root_0, char_literal253_tree);
            	    }
            	    IDENTIFIER254=(Token)input.LT(1);
            	    match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_offsetofMemberDesignator2983); if (failed) return retval;
            	    if ( backtracking==0 ) {
            	    IDENTIFIER254_tree = (Object)adaptor.create(IDENTIFIER254);
            	    adaptor.addChild(root_0, IDENTIFIER254_tree);
            	    }

            	    }


            	    }
            	    break;
            	case 2 :
            	    // GNUCa.g:468:40: ( '[' expression ']' )
            	    {
            	    // GNUCa.g:468:40: ( '[' expression ']' )
            	    // GNUCa.g:468:41: '[' expression ']'
            	    {
            	    char_literal255=(Token)input.LT(1);
            	    match(input,127,FOLLOW_127_in_offsetofMemberDesignator2989); if (failed) return retval;
            	    if ( backtracking==0 ) {
            	    char_literal255_tree = (Object)adaptor.create(char_literal255);
            	    adaptor.addChild(root_0, char_literal255_tree);
            	    }
            	    pushFollow(FOLLOW_expression_in_offsetofMemberDesignator2991);
            	    expression256=expression();
            	    _fsp--;
            	    if (failed) return retval;
            	    if ( backtracking==0 ) adaptor.addChild(root_0, expression256.getTree());
            	    char_literal257=(Token)input.LT(1);
            	    match(input,128,FOLLOW_128_in_offsetofMemberDesignator2993); if (failed) return retval;
            	    if ( backtracking==0 ) {
            	    char_literal257_tree = (Object)adaptor.create(char_literal257);
            	    adaptor.addChild(root_0, char_literal257_tree);
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop84;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 47, offsetofMemberDesignator_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end offsetofMemberDesignator

    public static class postfixExpression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start postfixExpression
    // GNUCa.g:472:1: postfixExpression : ( primaryExpression -> primaryExpression | '(' typeName ')' '{' initializerList ( ',' )? '}' -> ^( COMPOUND_LITERAL typeName initializerList ) ) ( '[' expression ']' -> ^( ARRAY_ACCESS $postfixExpression expression ) | '(' ( argumentExpressionList )? ')' -> ^( FUNCTION_CALL $postfixExpression ( argumentExpressionList )? ) | '.' IDENTIFIER -> ^( '.' $postfixExpression IDENTIFIER ) | '->' IDENTIFIER -> ^( '->' $postfixExpression IDENTIFIER ) | '++' -> ^( PP $postfixExpression) | '--' -> ^( MM $postfixExpression) )* ;
    public final postfixExpression_return postfixExpression() throws RecognitionException {
        postfixExpression_return retval = new postfixExpression_return();
        retval.start = input.LT(1);
        int postfixExpression_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal259=null;
        Token char_literal261=null;
        Token char_literal262=null;
        Token char_literal264=null;
        Token char_literal265=null;
        Token char_literal266=null;
        Token char_literal268=null;
        Token char_literal269=null;
        Token char_literal271=null;
        Token char_literal272=null;
        Token IDENTIFIER273=null;
        Token string_literal274=null;
        Token IDENTIFIER275=null;
        Token string_literal276=null;
        Token string_literal277=null;
        primaryExpression_return primaryExpression258 = null;

        typeName_return typeName260 = null;

        initializerList_return initializerList263 = null;

        expression_return expression267 = null;

        argumentExpressionList_return argumentExpressionList270 = null;


        Object char_literal259_tree=null;
        Object char_literal261_tree=null;
        Object char_literal262_tree=null;
        Object char_literal264_tree=null;
        Object char_literal265_tree=null;
        Object char_literal266_tree=null;
        Object char_literal268_tree=null;
        Object char_literal269_tree=null;
        Object char_literal271_tree=null;
        Object char_literal272_tree=null;
        Object IDENTIFIER273_tree=null;
        Object string_literal274_tree=null;
        Object IDENTIFIER275_tree=null;
        Object string_literal276_tree=null;
        Object string_literal277_tree=null;
        RewriteRuleTokenStream stream_134=new RewriteRuleTokenStream(adaptor,"token 134");
        RewriteRuleTokenStream stream_127=new RewriteRuleTokenStream(adaptor,"token 127");
        RewriteRuleTokenStream stream_128=new RewriteRuleTokenStream(adaptor,"token 128");
        RewriteRuleTokenStream stream_138=new RewriteRuleTokenStream(adaptor,"token 138");
        RewriteRuleTokenStream stream_109=new RewriteRuleTokenStream(adaptor,"token 109");
        RewriteRuleTokenStream stream_139=new RewriteRuleTokenStream(adaptor,"token 139");
        RewriteRuleTokenStream stream_110=new RewriteRuleTokenStream(adaptor,"token 110");
        RewriteRuleTokenStream stream_83=new RewriteRuleTokenStream(adaptor,"token 83");
        RewriteRuleTokenStream stream_IDENTIFIER=new RewriteRuleTokenStream(adaptor,"token IDENTIFIER");
        RewriteRuleTokenStream stream_86=new RewriteRuleTokenStream(adaptor,"token 86");
        RewriteRuleTokenStream stream_140=new RewriteRuleTokenStream(adaptor,"token 140");
        RewriteRuleTokenStream stream_84=new RewriteRuleTokenStream(adaptor,"token 84");
        RewriteRuleSubtreeStream stream_typeName=new RewriteRuleSubtreeStream(adaptor,"rule typeName");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_initializerList=new RewriteRuleSubtreeStream(adaptor,"rule initializerList");
        RewriteRuleSubtreeStream stream_argumentExpressionList=new RewriteRuleSubtreeStream(adaptor,"rule argumentExpressionList");
        RewriteRuleSubtreeStream stream_primaryExpression=new RewriteRuleSubtreeStream(adaptor,"rule primaryExpression");
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 48) ) { return retval; }
            // GNUCa.g:473:2: ( ( primaryExpression -> primaryExpression | '(' typeName ')' '{' initializerList ( ',' )? '}' -> ^( COMPOUND_LITERAL typeName initializerList ) ) ( '[' expression ']' -> ^( ARRAY_ACCESS $postfixExpression expression ) | '(' ( argumentExpressionList )? ')' -> ^( FUNCTION_CALL $postfixExpression ( argumentExpressionList )? ) | '.' IDENTIFIER -> ^( '.' $postfixExpression IDENTIFIER ) | '->' IDENTIFIER -> ^( '->' $postfixExpression IDENTIFIER ) | '++' -> ^( PP $postfixExpression) | '--' -> ^( MM $postfixExpression) )* )
            // GNUCa.g:474:2: ( primaryExpression -> primaryExpression | '(' typeName ')' '{' initializerList ( ',' )? '}' -> ^( COMPOUND_LITERAL typeName initializerList ) ) ( '[' expression ']' -> ^( ARRAY_ACCESS $postfixExpression expression ) | '(' ( argumentExpressionList )? ')' -> ^( FUNCTION_CALL $postfixExpression ( argumentExpressionList )? ) | '.' IDENTIFIER -> ^( '.' $postfixExpression IDENTIFIER ) | '->' IDENTIFIER -> ^( '->' $postfixExpression IDENTIFIER ) | '++' -> ^( PP $postfixExpression) | '--' -> ^( MM $postfixExpression) )*
            {
            // GNUCa.g:474:2: ( primaryExpression -> primaryExpression | '(' typeName ')' '{' initializerList ( ',' )? '}' -> ^( COMPOUND_LITERAL typeName initializerList ) )
            int alt86=2;
            int LA86_0 = input.LA(1);

            if ( ((LA86_0>=IDENTIFIER && LA86_0<=STRING_LITERAL)||LA86_0==137) ) {
                alt86=1;
            }
            else if ( (LA86_0==83) ) {
                switch ( input.LA(2) ) {
                case CONSTANT:
                case STRING_LITERAL:
                case 83:
                case 109:
                case 129:
                case 137:
                case 139:
                case 140:
                case 141:
                case 142:
                case 143:
                case 144:
                case 145:
                case 146:
                case 147:
                case 148:
                case 149:
                case 150:
                case 151:
                case 152:
                case 153:
                    {
                    alt86=1;
                    }
                    break;
                case IDENTIFIER:
                    {
                    int LA86_7 = input.LA(3);

                    if ( (synpred152()) ) {
                        alt86=1;
                    }
                    else if ( (true) ) {
                        alt86=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("474:2: ( primaryExpression -> primaryExpression | '(' typeName ')' '{' initializerList ( ',' )? '}' -> ^( COMPOUND_LITERAL typeName initializerList ) )", 86, 7, input);

                        throw nvae;
                    }
                    }
                    break;
                case 93:
                case 94:
                case 95:
                case 96:
                case 97:
                case 98:
                case 99:
                case 100:
                case 101:
                case 102:
                case 103:
                case 104:
                case 105:
                case 106:
                case 107:
                case 108:
                case 111:
                case 112:
                case 114:
                case 115:
                case 116:
                case 117:
                case 118:
                case 119:
                case 120:
                case 121:
                case 122:
                case 123:
                case 131:
                case 132:
                case 133:
                case 135:
                case 136:
                    {
                    alt86=2;
                    }
                    break;
                default:
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("474:2: ( primaryExpression -> primaryExpression | '(' typeName ')' '{' initializerList ( ',' )? '}' -> ^( COMPOUND_LITERAL typeName initializerList ) )", 86, 4, input);

                    throw nvae;
                }

            }
            else {
                if (backtracking>0) {failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("474:2: ( primaryExpression -> primaryExpression | '(' typeName ')' '{' initializerList ( ',' )? '}' -> ^( COMPOUND_LITERAL typeName initializerList ) )", 86, 0, input);

                throw nvae;
            }
            switch (alt86) {
                case 1 :
                    // GNUCa.g:474:4: primaryExpression
                    {
                    pushFollow(FOLLOW_primaryExpression_in_postfixExpression3015);
                    primaryExpression258=primaryExpression();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_primaryExpression.add(primaryExpression258.getTree());

                    // AST REWRITE
                    // elements: primaryExpression
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 474:22: -> primaryExpression
                    {
                        adaptor.addChild(root_0, stream_primaryExpression.next());

                    }

                    }

                    }
                    break;
                case 2 :
                    // GNUCa.g:475:4: '(' typeName ')' '{' initializerList ( ',' )? '}'
                    {
                    char_literal259=(Token)input.LT(1);
                    match(input,83,FOLLOW_83_in_postfixExpression3024); if (failed) return retval;
                    if ( backtracking==0 ) stream_83.add(char_literal259);

                    pushFollow(FOLLOW_typeName_in_postfixExpression3026);
                    typeName260=typeName();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_typeName.add(typeName260.getTree());
                    char_literal261=(Token)input.LT(1);
                    match(input,84,FOLLOW_84_in_postfixExpression3028); if (failed) return retval;
                    if ( backtracking==0 ) stream_84.add(char_literal261);

                    char_literal262=(Token)input.LT(1);
                    match(input,109,FOLLOW_109_in_postfixExpression3030); if (failed) return retval;
                    if ( backtracking==0 ) stream_109.add(char_literal262);

                    pushFollow(FOLLOW_initializerList_in_postfixExpression3032);
                    initializerList263=initializerList();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_initializerList.add(initializerList263.getTree());
                    // GNUCa.g:475:41: ( ',' )?
                    int alt85=2;
                    int LA85_0 = input.LA(1);

                    if ( (LA85_0==86) ) {
                        alt85=1;
                    }
                    switch (alt85) {
                        case 1 :
                            // GNUCa.g:0:0: ','
                            {
                            char_literal264=(Token)input.LT(1);
                            match(input,86,FOLLOW_86_in_postfixExpression3034); if (failed) return retval;
                            if ( backtracking==0 ) stream_86.add(char_literal264);


                            }
                            break;

                    }

                    char_literal265=(Token)input.LT(1);
                    match(input,110,FOLLOW_110_in_postfixExpression3037); if (failed) return retval;
                    if ( backtracking==0 ) stream_110.add(char_literal265);


                    // AST REWRITE
                    // elements: typeName, initializerList
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 475:50: -> ^( COMPOUND_LITERAL typeName initializerList )
                    {
                        // GNUCa.g:475:53: ^( COMPOUND_LITERAL typeName initializerList )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(adaptor.create(COMPOUND_LITERAL, "COMPOUND_LITERAL"), root_1);

                        adaptor.addChild(root_1, stream_typeName.next());
                        adaptor.addChild(root_1, stream_initializerList.next());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;

            }

            // GNUCa.g:477:2: ( '[' expression ']' -> ^( ARRAY_ACCESS $postfixExpression expression ) | '(' ( argumentExpressionList )? ')' -> ^( FUNCTION_CALL $postfixExpression ( argumentExpressionList )? ) | '.' IDENTIFIER -> ^( '.' $postfixExpression IDENTIFIER ) | '->' IDENTIFIER -> ^( '->' $postfixExpression IDENTIFIER ) | '++' -> ^( PP $postfixExpression) | '--' -> ^( MM $postfixExpression) )*
            loop88:
            do {
                int alt88=7;
                switch ( input.LA(1) ) {
                case 127:
                    {
                    alt88=1;
                    }
                    break;
                case 83:
                    {
                    alt88=2;
                    }
                    break;
                case 134:
                    {
                    alt88=3;
                    }
                    break;
                case 138:
                    {
                    alt88=4;
                    }
                    break;
                case 139:
                    {
                    alt88=5;
                    }
                    break;
                case 140:
                    {
                    alt88=6;
                    }
                    break;

                }

                switch (alt88) {
            	case 1 :
            	    // GNUCa.g:477:4: '[' expression ']'
            	    {
            	    char_literal266=(Token)input.LT(1);
            	    match(input,127,FOLLOW_127_in_postfixExpression3055); if (failed) return retval;
            	    if ( backtracking==0 ) stream_127.add(char_literal266);

            	    pushFollow(FOLLOW_expression_in_postfixExpression3057);
            	    expression267=expression();
            	    _fsp--;
            	    if (failed) return retval;
            	    if ( backtracking==0 ) stream_expression.add(expression267.getTree());
            	    char_literal268=(Token)input.LT(1);
            	    match(input,128,FOLLOW_128_in_postfixExpression3059); if (failed) return retval;
            	    if ( backtracking==0 ) stream_128.add(char_literal268);


            	    // AST REWRITE
            	    // elements: expression, postfixExpression
            	    // token labels: 
            	    // rule labels: retval
            	    // token list labels: 
            	    // rule list labels: 
            	    if ( backtracking==0 ) {
            	    retval.tree = root_0;
            	    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            	    root_0 = (Object)adaptor.nil();
            	    // 477:23: -> ^( ARRAY_ACCESS $postfixExpression expression )
            	    {
            	        // GNUCa.g:477:26: ^( ARRAY_ACCESS $postfixExpression expression )
            	        {
            	        Object root_1 = (Object)adaptor.nil();
            	        root_1 = (Object)adaptor.becomeRoot(adaptor.create(ARRAY_ACCESS, "ARRAY_ACCESS"), root_1);

            	        adaptor.addChild(root_1, stream_retval.next());
            	        adaptor.addChild(root_1, stream_expression.next());

            	        adaptor.addChild(root_0, root_1);
            	        }

            	    }

            	    }

            	    }
            	    break;
            	case 2 :
            	    // GNUCa.g:478:11: '(' ( argumentExpressionList )? ')'
            	    {
            	    char_literal269=(Token)input.LT(1);
            	    match(input,83,FOLLOW_83_in_postfixExpression3082); if (failed) return retval;
            	    if ( backtracking==0 ) stream_83.add(char_literal269);

            	    // GNUCa.g:478:15: ( argumentExpressionList )?
            	    int alt87=2;
            	    int LA87_0 = input.LA(1);

            	    if ( ((LA87_0>=IDENTIFIER && LA87_0<=STRING_LITERAL)||LA87_0==83||LA87_0==129||LA87_0==137||(LA87_0>=139 && LA87_0<=153)) ) {
            	        alt87=1;
            	    }
            	    switch (alt87) {
            	        case 1 :
            	            // GNUCa.g:0:0: argumentExpressionList
            	            {
            	            pushFollow(FOLLOW_argumentExpressionList_in_postfixExpression3084);
            	            argumentExpressionList270=argumentExpressionList();
            	            _fsp--;
            	            if (failed) return retval;
            	            if ( backtracking==0 ) stream_argumentExpressionList.add(argumentExpressionList270.getTree());

            	            }
            	            break;

            	    }

            	    char_literal271=(Token)input.LT(1);
            	    match(input,84,FOLLOW_84_in_postfixExpression3087); if (failed) return retval;
            	    if ( backtracking==0 ) stream_84.add(char_literal271);


            	    // AST REWRITE
            	    // elements: argumentExpressionList, postfixExpression
            	    // token labels: 
            	    // rule labels: retval
            	    // token list labels: 
            	    // rule list labels: 
            	    if ( backtracking==0 ) {
            	    retval.tree = root_0;
            	    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            	    root_0 = (Object)adaptor.nil();
            	    // 478:43: -> ^( FUNCTION_CALL $postfixExpression ( argumentExpressionList )? )
            	    {
            	        // GNUCa.g:478:46: ^( FUNCTION_CALL $postfixExpression ( argumentExpressionList )? )
            	        {
            	        Object root_1 = (Object)adaptor.nil();
            	        root_1 = (Object)adaptor.becomeRoot(adaptor.create(FUNCTION_CALL, "FUNCTION_CALL"), root_1);

            	        adaptor.addChild(root_1, stream_retval.next());
            	        // GNUCa.g:478:81: ( argumentExpressionList )?
            	        if ( stream_argumentExpressionList.hasNext() ) {
            	            adaptor.addChild(root_1, stream_argumentExpressionList.next());

            	        }
            	        stream_argumentExpressionList.reset();

            	        adaptor.addChild(root_0, root_1);
            	        }

            	    }

            	    }

            	    }
            	    break;
            	case 3 :
            	    // GNUCa.g:479:11: '.' IDENTIFIER
            	    {
            	    char_literal272=(Token)input.LT(1);
            	    match(input,134,FOLLOW_134_in_postfixExpression3111); if (failed) return retval;
            	    if ( backtracking==0 ) stream_134.add(char_literal272);

            	    IDENTIFIER273=(Token)input.LT(1);
            	    match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_postfixExpression3113); if (failed) return retval;
            	    if ( backtracking==0 ) stream_IDENTIFIER.add(IDENTIFIER273);


            	    // AST REWRITE
            	    // elements: postfixExpression, 134, IDENTIFIER
            	    // token labels: 
            	    // rule labels: retval
            	    // token list labels: 
            	    // rule list labels: 
            	    if ( backtracking==0 ) {
            	    retval.tree = root_0;
            	    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            	    root_0 = (Object)adaptor.nil();
            	    // 479:26: -> ^( '.' $postfixExpression IDENTIFIER )
            	    {
            	        // GNUCa.g:479:29: ^( '.' $postfixExpression IDENTIFIER )
            	        {
            	        Object root_1 = (Object)adaptor.nil();
            	        root_1 = (Object)adaptor.becomeRoot(stream_134.next(), root_1);

            	        adaptor.addChild(root_1, stream_retval.next());
            	        adaptor.addChild(root_1, stream_IDENTIFIER.next());

            	        adaptor.addChild(root_0, root_1);
            	        }

            	    }

            	    }

            	    }
            	    break;
            	case 4 :
            	    // GNUCa.g:480:11: '->' IDENTIFIER
            	    {
            	    string_literal274=(Token)input.LT(1);
            	    match(input,138,FOLLOW_138_in_postfixExpression3136); if (failed) return retval;
            	    if ( backtracking==0 ) stream_138.add(string_literal274);

            	    IDENTIFIER275=(Token)input.LT(1);
            	    match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_postfixExpression3138); if (failed) return retval;
            	    if ( backtracking==0 ) stream_IDENTIFIER.add(IDENTIFIER275);


            	    // AST REWRITE
            	    // elements: IDENTIFIER, postfixExpression, 138
            	    // token labels: 
            	    // rule labels: retval
            	    // token list labels: 
            	    // rule list labels: 
            	    if ( backtracking==0 ) {
            	    retval.tree = root_0;
            	    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            	    root_0 = (Object)adaptor.nil();
            	    // 480:27: -> ^( '->' $postfixExpression IDENTIFIER )
            	    {
            	        // GNUCa.g:480:30: ^( '->' $postfixExpression IDENTIFIER )
            	        {
            	        Object root_1 = (Object)adaptor.nil();
            	        root_1 = (Object)adaptor.becomeRoot(stream_138.next(), root_1);

            	        adaptor.addChild(root_1, stream_retval.next());
            	        adaptor.addChild(root_1, stream_IDENTIFIER.next());

            	        adaptor.addChild(root_0, root_1);
            	        }

            	    }

            	    }

            	    }
            	    break;
            	case 5 :
            	    // GNUCa.g:481:11: '++'
            	    {
            	    string_literal276=(Token)input.LT(1);
            	    match(input,139,FOLLOW_139_in_postfixExpression3161); if (failed) return retval;
            	    if ( backtracking==0 ) stream_139.add(string_literal276);


            	    // AST REWRITE
            	    // elements: postfixExpression
            	    // token labels: 
            	    // rule labels: retval
            	    // token list labels: 
            	    // rule list labels: 
            	    if ( backtracking==0 ) {
            	    retval.tree = root_0;
            	    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            	    root_0 = (Object)adaptor.nil();
            	    // 481:16: -> ^( PP $postfixExpression)
            	    {
            	        // GNUCa.g:481:19: ^( PP $postfixExpression)
            	        {
            	        Object root_1 = (Object)adaptor.nil();
            	        root_1 = (Object)adaptor.becomeRoot(adaptor.create(PP, "PP"), root_1);

            	        adaptor.addChild(root_1, stream_retval.next());

            	        adaptor.addChild(root_0, root_1);
            	        }

            	    }

            	    }

            	    }
            	    break;
            	case 6 :
            	    // GNUCa.g:482:11: '--'
            	    {
            	    string_literal277=(Token)input.LT(1);
            	    match(input,140,FOLLOW_140_in_postfixExpression3182); if (failed) return retval;
            	    if ( backtracking==0 ) stream_140.add(string_literal277);


            	    // AST REWRITE
            	    // elements: postfixExpression
            	    // token labels: 
            	    // rule labels: retval
            	    // token list labels: 
            	    // rule list labels: 
            	    if ( backtracking==0 ) {
            	    retval.tree = root_0;
            	    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            	    root_0 = (Object)adaptor.nil();
            	    // 482:16: -> ^( MM $postfixExpression)
            	    {
            	        // GNUCa.g:482:19: ^( MM $postfixExpression)
            	        {
            	        Object root_1 = (Object)adaptor.nil();
            	        root_1 = (Object)adaptor.becomeRoot(adaptor.create(MM, "MM"), root_1);

            	        adaptor.addChild(root_1, stream_retval.next());

            	        adaptor.addChild(root_0, root_1);
            	        }

            	    }

            	    }

            	    }
            	    break;

            	default :
            	    break loop88;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 48, postfixExpression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end postfixExpression

    public static class argumentExpressionList_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start argumentExpressionList
    // GNUCa.g:486:1: argumentExpressionList : assignmentExpression ( ',' assignmentExpression )* -> ( assignmentExpression )+ ;
    public final argumentExpressionList_return argumentExpressionList() throws RecognitionException {
        argumentExpressionList_return retval = new argumentExpressionList_return();
        retval.start = input.LT(1);
        int argumentExpressionList_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal279=null;
        assignmentExpression_return assignmentExpression278 = null;

        assignmentExpression_return assignmentExpression280 = null;


        Object char_literal279_tree=null;
        RewriteRuleTokenStream stream_86=new RewriteRuleTokenStream(adaptor,"token 86");
        RewriteRuleSubtreeStream stream_assignmentExpression=new RewriteRuleSubtreeStream(adaptor,"rule assignmentExpression");
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 49) ) { return retval; }
            // GNUCa.g:487:2: ( assignmentExpression ( ',' assignmentExpression )* -> ( assignmentExpression )+ )
            // GNUCa.g:487:4: assignmentExpression ( ',' assignmentExpression )*
            {
            pushFollow(FOLLOW_assignmentExpression_in_argumentExpressionList3214);
            assignmentExpression278=assignmentExpression();
            _fsp--;
            if (failed) return retval;
            if ( backtracking==0 ) stream_assignmentExpression.add(assignmentExpression278.getTree());
            // GNUCa.g:487:25: ( ',' assignmentExpression )*
            loop89:
            do {
                int alt89=2;
                int LA89_0 = input.LA(1);

                if ( (LA89_0==86) ) {
                    alt89=1;
                }


                switch (alt89) {
            	case 1 :
            	    // GNUCa.g:487:26: ',' assignmentExpression
            	    {
            	    char_literal279=(Token)input.LT(1);
            	    match(input,86,FOLLOW_86_in_argumentExpressionList3217); if (failed) return retval;
            	    if ( backtracking==0 ) stream_86.add(char_literal279);

            	    pushFollow(FOLLOW_assignmentExpression_in_argumentExpressionList3219);
            	    assignmentExpression280=assignmentExpression();
            	    _fsp--;
            	    if (failed) return retval;
            	    if ( backtracking==0 ) stream_assignmentExpression.add(assignmentExpression280.getTree());

            	    }
            	    break;

            	default :
            	    break loop89;
                }
            } while (true);


            // AST REWRITE
            // elements: assignmentExpression
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 487:53: -> ( assignmentExpression )+
            {
                if ( !(stream_assignmentExpression.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_assignmentExpression.hasNext() ) {
                    adaptor.addChild(root_0, stream_assignmentExpression.next());

                }
                stream_assignmentExpression.reset();

            }

            }

            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 49, argumentExpressionList_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end argumentExpressionList

    public static class unaryExpression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start unaryExpression
    // GNUCa.g:490:1: unaryExpression : ( postfixExpression | '++' unaryExpression | '--' unaryExpression | unaryOperator castExpression | 'sizeof' unaryExpression | 'sizeof' '(' typeName ')' | ( '__alignof' | '__alignof__' ) unaryExpression -> ^( '__alignof__' unaryExpression ) | ( '__alignof' | '__alignof__' ) '(' typeName ')' -> ^( '__alignof__' typeName ) );
    public final unaryExpression_return unaryExpression() throws RecognitionException {
        unaryExpression_return retval = new unaryExpression_return();
        retval.start = input.LT(1);
        int unaryExpression_StartIndex = input.index();
        Object root_0 = null;

        Token string_literal282=null;
        Token string_literal284=null;
        Token string_literal288=null;
        Token string_literal290=null;
        Token char_literal291=null;
        Token char_literal293=null;
        Token string_literal294=null;
        Token string_literal295=null;
        Token string_literal297=null;
        Token string_literal298=null;
        Token char_literal299=null;
        Token char_literal301=null;
        postfixExpression_return postfixExpression281 = null;

        unaryExpression_return unaryExpression283 = null;

        unaryExpression_return unaryExpression285 = null;

        unaryOperator_return unaryOperator286 = null;

        castExpression_return castExpression287 = null;

        unaryExpression_return unaryExpression289 = null;

        typeName_return typeName292 = null;

        unaryExpression_return unaryExpression296 = null;

        typeName_return typeName300 = null;


        Object string_literal282_tree=null;
        Object string_literal284_tree=null;
        Object string_literal288_tree=null;
        Object string_literal290_tree=null;
        Object char_literal291_tree=null;
        Object char_literal293_tree=null;
        Object string_literal294_tree=null;
        Object string_literal295_tree=null;
        Object string_literal297_tree=null;
        Object string_literal298_tree=null;
        Object char_literal299_tree=null;
        Object char_literal301_tree=null;
        RewriteRuleTokenStream stream_143=new RewriteRuleTokenStream(adaptor,"token 143");
        RewriteRuleTokenStream stream_83=new RewriteRuleTokenStream(adaptor,"token 83");
        RewriteRuleTokenStream stream_84=new RewriteRuleTokenStream(adaptor,"token 84");
        RewriteRuleTokenStream stream_142=new RewriteRuleTokenStream(adaptor,"token 142");
        RewriteRuleSubtreeStream stream_typeName=new RewriteRuleSubtreeStream(adaptor,"rule typeName");
        RewriteRuleSubtreeStream stream_unaryExpression=new RewriteRuleSubtreeStream(adaptor,"rule unaryExpression");
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 50) ) { return retval; }
            // GNUCa.g:491:2: ( postfixExpression | '++' unaryExpression | '--' unaryExpression | unaryOperator castExpression | 'sizeof' unaryExpression | 'sizeof' '(' typeName ')' | ( '__alignof' | '__alignof__' ) unaryExpression -> ^( '__alignof__' unaryExpression ) | ( '__alignof' | '__alignof__' ) '(' typeName ')' -> ^( '__alignof__' typeName ) )
            int alt92=8;
            switch ( input.LA(1) ) {
            case IDENTIFIER:
            case CONSTANT:
            case STRING_LITERAL:
            case 83:
            case 137:
                {
                alt92=1;
                }
                break;
            case 139:
                {
                alt92=2;
                }
                break;
            case 140:
                {
                alt92=3;
                }
                break;
            case 129:
            case 144:
            case 145:
            case 146:
            case 147:
            case 148:
            case 149:
            case 150:
            case 151:
            case 152:
            case 153:
                {
                alt92=4;
                }
                break;
            case 141:
                {
                int LA92_19 = input.LA(2);

                if ( (LA92_19==83) ) {
                    int LA92_22 = input.LA(3);

                    if ( (synpred166()) ) {
                        alt92=5;
                    }
                    else if ( (synpred167()) ) {
                        alt92=6;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("490:1: unaryExpression : ( postfixExpression | '++' unaryExpression | '--' unaryExpression | unaryOperator castExpression | 'sizeof' unaryExpression | 'sizeof' '(' typeName ')' | ( '__alignof' | '__alignof__' ) unaryExpression -> ^( '__alignof__' unaryExpression ) | ( '__alignof' | '__alignof__' ) '(' typeName ')' -> ^( '__alignof__' typeName ) );", 92, 22, input);

                        throw nvae;
                    }
                }
                else if ( ((LA92_19>=IDENTIFIER && LA92_19<=STRING_LITERAL)||LA92_19==129||LA92_19==137||(LA92_19>=139 && LA92_19<=153)) ) {
                    alt92=5;
                }
                else {
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("490:1: unaryExpression : ( postfixExpression | '++' unaryExpression | '--' unaryExpression | unaryOperator castExpression | 'sizeof' unaryExpression | 'sizeof' '(' typeName ')' | ( '__alignof' | '__alignof__' ) unaryExpression -> ^( '__alignof__' unaryExpression ) | ( '__alignof' | '__alignof__' ) '(' typeName ')' -> ^( '__alignof__' typeName ) );", 92, 19, input);

                    throw nvae;
                }
                }
                break;
            case 142:
                {
                int LA92_20 = input.LA(2);

                if ( (LA92_20==83) ) {
                    int LA92_43 = input.LA(3);

                    if ( (synpred169()) ) {
                        alt92=7;
                    }
                    else if ( (true) ) {
                        alt92=8;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("490:1: unaryExpression : ( postfixExpression | '++' unaryExpression | '--' unaryExpression | unaryOperator castExpression | 'sizeof' unaryExpression | 'sizeof' '(' typeName ')' | ( '__alignof' | '__alignof__' ) unaryExpression -> ^( '__alignof__' unaryExpression ) | ( '__alignof' | '__alignof__' ) '(' typeName ')' -> ^( '__alignof__' typeName ) );", 92, 43, input);

                        throw nvae;
                    }
                }
                else if ( ((LA92_20>=IDENTIFIER && LA92_20<=STRING_LITERAL)||LA92_20==129||LA92_20==137||(LA92_20>=139 && LA92_20<=153)) ) {
                    alt92=7;
                }
                else {
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("490:1: unaryExpression : ( postfixExpression | '++' unaryExpression | '--' unaryExpression | unaryOperator castExpression | 'sizeof' unaryExpression | 'sizeof' '(' typeName ')' | ( '__alignof' | '__alignof__' ) unaryExpression -> ^( '__alignof__' unaryExpression ) | ( '__alignof' | '__alignof__' ) '(' typeName ')' -> ^( '__alignof__' typeName ) );", 92, 20, input);

                    throw nvae;
                }
                }
                break;
            case 143:
                {
                int LA92_21 = input.LA(2);

                if ( (LA92_21==83) ) {
                    int LA92_64 = input.LA(3);

                    if ( (synpred169()) ) {
                        alt92=7;
                    }
                    else if ( (true) ) {
                        alt92=8;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("490:1: unaryExpression : ( postfixExpression | '++' unaryExpression | '--' unaryExpression | unaryOperator castExpression | 'sizeof' unaryExpression | 'sizeof' '(' typeName ')' | ( '__alignof' | '__alignof__' ) unaryExpression -> ^( '__alignof__' unaryExpression ) | ( '__alignof' | '__alignof__' ) '(' typeName ')' -> ^( '__alignof__' typeName ) );", 92, 64, input);

                        throw nvae;
                    }
                }
                else if ( ((LA92_21>=IDENTIFIER && LA92_21<=STRING_LITERAL)||LA92_21==129||LA92_21==137||(LA92_21>=139 && LA92_21<=153)) ) {
                    alt92=7;
                }
                else {
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("490:1: unaryExpression : ( postfixExpression | '++' unaryExpression | '--' unaryExpression | unaryOperator castExpression | 'sizeof' unaryExpression | 'sizeof' '(' typeName ')' | ( '__alignof' | '__alignof__' ) unaryExpression -> ^( '__alignof__' unaryExpression ) | ( '__alignof' | '__alignof__' ) '(' typeName ')' -> ^( '__alignof__' typeName ) );", 92, 21, input);

                    throw nvae;
                }
                }
                break;
            default:
                if (backtracking>0) {failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("490:1: unaryExpression : ( postfixExpression | '++' unaryExpression | '--' unaryExpression | unaryOperator castExpression | 'sizeof' unaryExpression | 'sizeof' '(' typeName ')' | ( '__alignof' | '__alignof__' ) unaryExpression -> ^( '__alignof__' unaryExpression ) | ( '__alignof' | '__alignof__' ) '(' typeName ')' -> ^( '__alignof__' typeName ) );", 92, 0, input);

                throw nvae;
            }

            switch (alt92) {
                case 1 :
                    // GNUCa.g:491:4: postfixExpression
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_postfixExpression_in_unaryExpression3238);
                    postfixExpression281=postfixExpression();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) adaptor.addChild(root_0, postfixExpression281.getTree());

                    }
                    break;
                case 2 :
                    // GNUCa.g:492:4: '++' unaryExpression
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal282=(Token)input.LT(1);
                    match(input,139,FOLLOW_139_in_unaryExpression3243); if (failed) return retval;
                    if ( backtracking==0 ) {
                    string_literal282_tree = (Object)adaptor.create(string_literal282);
                    root_0 = (Object)adaptor.becomeRoot(string_literal282_tree, root_0);
                    }
                    pushFollow(FOLLOW_unaryExpression_in_unaryExpression3246);
                    unaryExpression283=unaryExpression();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) adaptor.addChild(root_0, unaryExpression283.getTree());

                    }
                    break;
                case 3 :
                    // GNUCa.g:493:4: '--' unaryExpression
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal284=(Token)input.LT(1);
                    match(input,140,FOLLOW_140_in_unaryExpression3251); if (failed) return retval;
                    if ( backtracking==0 ) {
                    string_literal284_tree = (Object)adaptor.create(string_literal284);
                    root_0 = (Object)adaptor.becomeRoot(string_literal284_tree, root_0);
                    }
                    pushFollow(FOLLOW_unaryExpression_in_unaryExpression3254);
                    unaryExpression285=unaryExpression();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) adaptor.addChild(root_0, unaryExpression285.getTree());

                    }
                    break;
                case 4 :
                    // GNUCa.g:494:4: unaryOperator castExpression
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_unaryOperator_in_unaryExpression3259);
                    unaryOperator286=unaryOperator();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(unaryOperator286.getTree(), root_0);
                    pushFollow(FOLLOW_castExpression_in_unaryExpression3262);
                    castExpression287=castExpression();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) adaptor.addChild(root_0, castExpression287.getTree());

                    }
                    break;
                case 5 :
                    // GNUCa.g:495:4: 'sizeof' unaryExpression
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal288=(Token)input.LT(1);
                    match(input,141,FOLLOW_141_in_unaryExpression3267); if (failed) return retval;
                    if ( backtracking==0 ) {
                    string_literal288_tree = (Object)adaptor.create(string_literal288);
                    root_0 = (Object)adaptor.becomeRoot(string_literal288_tree, root_0);
                    }
                    pushFollow(FOLLOW_unaryExpression_in_unaryExpression3270);
                    unaryExpression289=unaryExpression();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) adaptor.addChild(root_0, unaryExpression289.getTree());

                    }
                    break;
                case 6 :
                    // GNUCa.g:496:4: 'sizeof' '(' typeName ')'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal290=(Token)input.LT(1);
                    match(input,141,FOLLOW_141_in_unaryExpression3275); if (failed) return retval;
                    if ( backtracking==0 ) {
                    string_literal290_tree = (Object)adaptor.create(string_literal290);
                    root_0 = (Object)adaptor.becomeRoot(string_literal290_tree, root_0);
                    }
                    char_literal291=(Token)input.LT(1);
                    match(input,83,FOLLOW_83_in_unaryExpression3278); if (failed) return retval;
                    pushFollow(FOLLOW_typeName_in_unaryExpression3281);
                    typeName292=typeName();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) adaptor.addChild(root_0, typeName292.getTree());
                    char_literal293=(Token)input.LT(1);
                    match(input,84,FOLLOW_84_in_unaryExpression3283); if (failed) return retval;

                    }
                    break;
                case 7 :
                    // GNUCa.g:497:4: ( '__alignof' | '__alignof__' ) unaryExpression
                    {
                    // GNUCa.g:497:4: ( '__alignof' | '__alignof__' )
                    int alt90=2;
                    int LA90_0 = input.LA(1);

                    if ( (LA90_0==142) ) {
                        alt90=1;
                    }
                    else if ( (LA90_0==143) ) {
                        alt90=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("497:4: ( '__alignof' | '__alignof__' )", 90, 0, input);

                        throw nvae;
                    }
                    switch (alt90) {
                        case 1 :
                            // GNUCa.g:497:5: '__alignof'
                            {
                            string_literal294=(Token)input.LT(1);
                            match(input,142,FOLLOW_142_in_unaryExpression3290); if (failed) return retval;
                            if ( backtracking==0 ) stream_142.add(string_literal294);


                            }
                            break;
                        case 2 :
                            // GNUCa.g:497:17: '__alignof__'
                            {
                            string_literal295=(Token)input.LT(1);
                            match(input,143,FOLLOW_143_in_unaryExpression3292); if (failed) return retval;
                            if ( backtracking==0 ) stream_143.add(string_literal295);


                            }
                            break;

                    }

                    pushFollow(FOLLOW_unaryExpression_in_unaryExpression3295);
                    unaryExpression296=unaryExpression();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_unaryExpression.add(unaryExpression296.getTree());

                    // AST REWRITE
                    // elements: 143, unaryExpression
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 497:48: -> ^( '__alignof__' unaryExpression )
                    {
                        // GNUCa.g:497:51: ^( '__alignof__' unaryExpression )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_143.next(), root_1);

                        adaptor.addChild(root_1, stream_unaryExpression.next());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;
                case 8 :
                    // GNUCa.g:498:4: ( '__alignof' | '__alignof__' ) '(' typeName ')'
                    {
                    // GNUCa.g:498:4: ( '__alignof' | '__alignof__' )
                    int alt91=2;
                    int LA91_0 = input.LA(1);

                    if ( (LA91_0==142) ) {
                        alt91=1;
                    }
                    else if ( (LA91_0==143) ) {
                        alt91=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("498:4: ( '__alignof' | '__alignof__' )", 91, 0, input);

                        throw nvae;
                    }
                    switch (alt91) {
                        case 1 :
                            // GNUCa.g:498:5: '__alignof'
                            {
                            string_literal297=(Token)input.LT(1);
                            match(input,142,FOLLOW_142_in_unaryExpression3309); if (failed) return retval;
                            if ( backtracking==0 ) stream_142.add(string_literal297);


                            }
                            break;
                        case 2 :
                            // GNUCa.g:498:17: '__alignof__'
                            {
                            string_literal298=(Token)input.LT(1);
                            match(input,143,FOLLOW_143_in_unaryExpression3311); if (failed) return retval;
                            if ( backtracking==0 ) stream_143.add(string_literal298);


                            }
                            break;

                    }

                    char_literal299=(Token)input.LT(1);
                    match(input,83,FOLLOW_83_in_unaryExpression3314); if (failed) return retval;
                    if ( backtracking==0 ) stream_83.add(char_literal299);

                    pushFollow(FOLLOW_typeName_in_unaryExpression3316);
                    typeName300=typeName();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_typeName.add(typeName300.getTree());
                    char_literal301=(Token)input.LT(1);
                    match(input,84,FOLLOW_84_in_unaryExpression3318); if (failed) return retval;
                    if ( backtracking==0 ) stream_84.add(char_literal301);


                    // AST REWRITE
                    // elements: typeName, 143
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 498:49: -> ^( '__alignof__' typeName )
                    {
                        // GNUCa.g:498:52: ^( '__alignof__' typeName )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_143.next(), root_1);

                        adaptor.addChild(root_1, stream_typeName.next());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 50, unaryExpression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end unaryExpression

    public static class unaryOperator_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start unaryOperator
    // GNUCa.g:501:1: unaryOperator : ( '&' -> AU | '*' -> XU | '+' -> PU | '-' -> MU | '~' | '!' | '&&' -> LABREF | '__real' -> '__real__' | '__real__' | '__imag' -> '__imag__' | '__imag__' );
    public final unaryOperator_return unaryOperator() throws RecognitionException {
        unaryOperator_return retval = new unaryOperator_return();
        retval.start = input.LT(1);
        int unaryOperator_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal302=null;
        Token char_literal303=null;
        Token char_literal304=null;
        Token char_literal305=null;
        Token char_literal306=null;
        Token char_literal307=null;
        Token string_literal308=null;
        Token string_literal309=null;
        Token string_literal310=null;
        Token string_literal311=null;
        Token string_literal312=null;

        Object char_literal302_tree=null;
        Object char_literal303_tree=null;
        Object char_literal304_tree=null;
        Object char_literal305_tree=null;
        Object char_literal306_tree=null;
        Object char_literal307_tree=null;
        Object string_literal308_tree=null;
        Object string_literal309_tree=null;
        Object string_literal310_tree=null;
        Object string_literal311_tree=null;
        Object string_literal312_tree=null;
        RewriteRuleTokenStream stream_152=new RewriteRuleTokenStream(adaptor,"token 152");
        RewriteRuleTokenStream stream_144=new RewriteRuleTokenStream(adaptor,"token 144");
        RewriteRuleTokenStream stream_150=new RewriteRuleTokenStream(adaptor,"token 150");
        RewriteRuleTokenStream stream_145=new RewriteRuleTokenStream(adaptor,"token 145");
        RewriteRuleTokenStream stream_146=new RewriteRuleTokenStream(adaptor,"token 146");
        RewriteRuleTokenStream stream_149=new RewriteRuleTokenStream(adaptor,"token 149");
        RewriteRuleTokenStream stream_129=new RewriteRuleTokenStream(adaptor,"token 129");

        try {
            if ( backtracking>0 && alreadyParsedRule(input, 51) ) { return retval; }
            // GNUCa.g:502:2: ( '&' -> AU | '*' -> XU | '+' -> PU | '-' -> MU | '~' | '!' | '&&' -> LABREF | '__real' -> '__real__' | '__real__' | '__imag' -> '__imag__' | '__imag__' )
            int alt93=11;
            switch ( input.LA(1) ) {
            case 144:
                {
                alt93=1;
                }
                break;
            case 129:
                {
                alt93=2;
                }
                break;
            case 145:
                {
                alt93=3;
                }
                break;
            case 146:
                {
                alt93=4;
                }
                break;
            case 147:
                {
                alt93=5;
                }
                break;
            case 148:
                {
                alt93=6;
                }
                break;
            case 149:
                {
                alt93=7;
                }
                break;
            case 150:
                {
                alt93=8;
                }
                break;
            case 151:
                {
                alt93=9;
                }
                break;
            case 152:
                {
                alt93=10;
                }
                break;
            case 153:
                {
                alt93=11;
                }
                break;
            default:
                if (backtracking>0) {failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("501:1: unaryOperator : ( '&' -> AU | '*' -> XU | '+' -> PU | '-' -> MU | '~' | '!' | '&&' -> LABREF | '__real' -> '__real__' | '__real__' | '__imag' -> '__imag__' | '__imag__' );", 93, 0, input);

                throw nvae;
            }

            switch (alt93) {
                case 1 :
                    // GNUCa.g:502:4: '&'
                    {
                    char_literal302=(Token)input.LT(1);
                    match(input,144,FOLLOW_144_in_unaryOperator3337); if (failed) return retval;
                    if ( backtracking==0 ) stream_144.add(char_literal302);


                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 502:8: -> AU
                    {
                        adaptor.addChild(root_0, adaptor.create(AU, "AU"));

                    }

                    }

                    }
                    break;
                case 2 :
                    // GNUCa.g:503:4: '*'
                    {
                    char_literal303=(Token)input.LT(1);
                    match(input,129,FOLLOW_129_in_unaryOperator3346); if (failed) return retval;
                    if ( backtracking==0 ) stream_129.add(char_literal303);


                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 503:8: -> XU
                    {
                        adaptor.addChild(root_0, adaptor.create(XU, "XU"));

                    }

                    }

                    }
                    break;
                case 3 :
                    // GNUCa.g:504:4: '+'
                    {
                    char_literal304=(Token)input.LT(1);
                    match(input,145,FOLLOW_145_in_unaryOperator3355); if (failed) return retval;
                    if ( backtracking==0 ) stream_145.add(char_literal304);


                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 504:8: -> PU
                    {
                        adaptor.addChild(root_0, adaptor.create(PU, "PU"));

                    }

                    }

                    }
                    break;
                case 4 :
                    // GNUCa.g:505:4: '-'
                    {
                    char_literal305=(Token)input.LT(1);
                    match(input,146,FOLLOW_146_in_unaryOperator3364); if (failed) return retval;
                    if ( backtracking==0 ) stream_146.add(char_literal305);


                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 505:8: -> MU
                    {
                        adaptor.addChild(root_0, adaptor.create(MU, "MU"));

                    }

                    }

                    }
                    break;
                case 5 :
                    // GNUCa.g:506:4: '~'
                    {
                    root_0 = (Object)adaptor.nil();

                    char_literal306=(Token)input.LT(1);
                    match(input,147,FOLLOW_147_in_unaryOperator3373); if (failed) return retval;
                    if ( backtracking==0 ) {
                    char_literal306_tree = (Object)adaptor.create(char_literal306);
                    adaptor.addChild(root_0, char_literal306_tree);
                    }

                    }
                    break;
                case 6 :
                    // GNUCa.g:507:4: '!'
                    {
                    root_0 = (Object)adaptor.nil();

                    char_literal307=(Token)input.LT(1);
                    match(input,148,FOLLOW_148_in_unaryOperator3378); if (failed) return retval;
                    if ( backtracking==0 ) {
                    char_literal307_tree = (Object)adaptor.create(char_literal307);
                    adaptor.addChild(root_0, char_literal307_tree);
                    }

                    }
                    break;
                case 7 :
                    // GNUCa.g:508:4: '&&'
                    {
                    string_literal308=(Token)input.LT(1);
                    match(input,149,FOLLOW_149_in_unaryOperator3383); if (failed) return retval;
                    if ( backtracking==0 ) stream_149.add(string_literal308);


                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 508:9: -> LABREF
                    {
                        adaptor.addChild(root_0, adaptor.create(LABREF, "LABREF"));

                    }

                    }

                    }
                    break;
                case 8 :
                    // GNUCa.g:509:4: '__real'
                    {
                    string_literal309=(Token)input.LT(1);
                    match(input,150,FOLLOW_150_in_unaryOperator3392); if (failed) return retval;
                    if ( backtracking==0 ) stream_150.add(string_literal309);


                    // AST REWRITE
                    // elements: 151
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 509:13: -> '__real__'
                    {
                        adaptor.addChild(root_0, adaptor.create(151, "151"));

                    }

                    }

                    }
                    break;
                case 9 :
                    // GNUCa.g:510:4: '__real__'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal310=(Token)input.LT(1);
                    match(input,151,FOLLOW_151_in_unaryOperator3401); if (failed) return retval;
                    if ( backtracking==0 ) {
                    string_literal310_tree = (Object)adaptor.create(string_literal310);
                    adaptor.addChild(root_0, string_literal310_tree);
                    }

                    }
                    break;
                case 10 :
                    // GNUCa.g:511:4: '__imag'
                    {
                    string_literal311=(Token)input.LT(1);
                    match(input,152,FOLLOW_152_in_unaryOperator3406); if (failed) return retval;
                    if ( backtracking==0 ) stream_152.add(string_literal311);


                    // AST REWRITE
                    // elements: 153
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 511:13: -> '__imag__'
                    {
                        adaptor.addChild(root_0, adaptor.create(153, "153"));

                    }

                    }

                    }
                    break;
                case 11 :
                    // GNUCa.g:512:4: '__imag__'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal312=(Token)input.LT(1);
                    match(input,153,FOLLOW_153_in_unaryOperator3415); if (failed) return retval;
                    if ( backtracking==0 ) {
                    string_literal312_tree = (Object)adaptor.create(string_literal312);
                    adaptor.addChild(root_0, string_literal312_tree);
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 51, unaryOperator_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end unaryOperator

    public static class castExpression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start castExpression
    // GNUCa.g:515:1: castExpression : ( ( '(' typeName ')' )=> '(' typeName ')' ( castExpression -> ^( CAST_EXPRESSION typeName castExpression ) | '{' initializerList ( ',' )? '}' -> ^( COMPOUND_LITERAL typeName initializerList ) ) | unaryExpression );
    public final castExpression_return castExpression() throws RecognitionException {
        castExpression_return retval = new castExpression_return();
        retval.start = input.LT(1);
        int castExpression_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal313=null;
        Token char_literal315=null;
        Token char_literal317=null;
        Token char_literal319=null;
        Token char_literal320=null;
        typeName_return typeName314 = null;

        castExpression_return castExpression316 = null;

        initializerList_return initializerList318 = null;

        unaryExpression_return unaryExpression321 = null;


        Object char_literal313_tree=null;
        Object char_literal315_tree=null;
        Object char_literal317_tree=null;
        Object char_literal319_tree=null;
        Object char_literal320_tree=null;
        RewriteRuleTokenStream stream_109=new RewriteRuleTokenStream(adaptor,"token 109");
        RewriteRuleTokenStream stream_110=new RewriteRuleTokenStream(adaptor,"token 110");
        RewriteRuleTokenStream stream_83=new RewriteRuleTokenStream(adaptor,"token 83");
        RewriteRuleTokenStream stream_86=new RewriteRuleTokenStream(adaptor,"token 86");
        RewriteRuleTokenStream stream_84=new RewriteRuleTokenStream(adaptor,"token 84");
        RewriteRuleSubtreeStream stream_typeName=new RewriteRuleSubtreeStream(adaptor,"rule typeName");
        RewriteRuleSubtreeStream stream_castExpression=new RewriteRuleSubtreeStream(adaptor,"rule castExpression");
        RewriteRuleSubtreeStream stream_initializerList=new RewriteRuleSubtreeStream(adaptor,"rule initializerList");
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 52) ) { return retval; }
            // GNUCa.g:519:2: ( ( '(' typeName ')' )=> '(' typeName ')' ( castExpression -> ^( CAST_EXPRESSION typeName castExpression ) | '{' initializerList ( ',' )? '}' -> ^( COMPOUND_LITERAL typeName initializerList ) ) | unaryExpression )
            int alt96=2;
            int LA96_0 = input.LA(1);

            if ( (LA96_0==83) ) {
                switch ( input.LA(2) ) {
                case 93:
                    {
                    int LA96_22 = input.LA(3);

                    if ( (synpred181()) ) {
                        alt96=1;
                    }
                    else if ( (true) ) {
                        alt96=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("515:1: castExpression : ( ( '(' typeName ')' )=> '(' typeName ')' ( castExpression -> ^( CAST_EXPRESSION typeName castExpression ) | '{' initializerList ( ',' )? '}' -> ^( COMPOUND_LITERAL typeName initializerList ) ) | unaryExpression );", 96, 22, input);

                        throw nvae;
                    }
                    }
                    break;
                case 94:
                    {
                    int LA96_23 = input.LA(3);

                    if ( (synpred181()) ) {
                        alt96=1;
                    }
                    else if ( (true) ) {
                        alt96=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("515:1: castExpression : ( ( '(' typeName ')' )=> '(' typeName ')' ( castExpression -> ^( CAST_EXPRESSION typeName castExpression ) | '{' initializerList ( ',' )? '}' -> ^( COMPOUND_LITERAL typeName initializerList ) ) | unaryExpression );", 96, 23, input);

                        throw nvae;
                    }
                    }
                    break;
                case 95:
                    {
                    int LA96_24 = input.LA(3);

                    if ( (synpred181()) ) {
                        alt96=1;
                    }
                    else if ( (true) ) {
                        alt96=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("515:1: castExpression : ( ( '(' typeName ')' )=> '(' typeName ')' ( castExpression -> ^( CAST_EXPRESSION typeName castExpression ) | '{' initializerList ( ',' )? '}' -> ^( COMPOUND_LITERAL typeName initializerList ) ) | unaryExpression );", 96, 24, input);

                        throw nvae;
                    }
                    }
                    break;
                case 96:
                    {
                    int LA96_25 = input.LA(3);

                    if ( (synpred181()) ) {
                        alt96=1;
                    }
                    else if ( (true) ) {
                        alt96=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("515:1: castExpression : ( ( '(' typeName ')' )=> '(' typeName ')' ( castExpression -> ^( CAST_EXPRESSION typeName castExpression ) | '{' initializerList ( ',' )? '}' -> ^( COMPOUND_LITERAL typeName initializerList ) ) | unaryExpression );", 96, 25, input);

                        throw nvae;
                    }
                    }
                    break;
                case 97:
                    {
                    int LA96_26 = input.LA(3);

                    if ( (synpred181()) ) {
                        alt96=1;
                    }
                    else if ( (true) ) {
                        alt96=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("515:1: castExpression : ( ( '(' typeName ')' )=> '(' typeName ')' ( castExpression -> ^( CAST_EXPRESSION typeName castExpression ) | '{' initializerList ( ',' )? '}' -> ^( COMPOUND_LITERAL typeName initializerList ) ) | unaryExpression );", 96, 26, input);

                        throw nvae;
                    }
                    }
                    break;
                case 98:
                    {
                    int LA96_27 = input.LA(3);

                    if ( (synpred181()) ) {
                        alt96=1;
                    }
                    else if ( (true) ) {
                        alt96=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("515:1: castExpression : ( ( '(' typeName ')' )=> '(' typeName ')' ( castExpression -> ^( CAST_EXPRESSION typeName castExpression ) | '{' initializerList ( ',' )? '}' -> ^( COMPOUND_LITERAL typeName initializerList ) ) | unaryExpression );", 96, 27, input);

                        throw nvae;
                    }
                    }
                    break;
                case 99:
                    {
                    int LA96_28 = input.LA(3);

                    if ( (synpred181()) ) {
                        alt96=1;
                    }
                    else if ( (true) ) {
                        alt96=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("515:1: castExpression : ( ( '(' typeName ')' )=> '(' typeName ')' ( castExpression -> ^( CAST_EXPRESSION typeName castExpression ) | '{' initializerList ( ',' )? '}' -> ^( COMPOUND_LITERAL typeName initializerList ) ) | unaryExpression );", 96, 28, input);

                        throw nvae;
                    }
                    }
                    break;
                case 100:
                    {
                    int LA96_29 = input.LA(3);

                    if ( (synpred181()) ) {
                        alt96=1;
                    }
                    else if ( (true) ) {
                        alt96=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("515:1: castExpression : ( ( '(' typeName ')' )=> '(' typeName ')' ( castExpression -> ^( CAST_EXPRESSION typeName castExpression ) | '{' initializerList ( ',' )? '}' -> ^( COMPOUND_LITERAL typeName initializerList ) ) | unaryExpression );", 96, 29, input);

                        throw nvae;
                    }
                    }
                    break;
                case 101:
                    {
                    int LA96_30 = input.LA(3);

                    if ( (synpred181()) ) {
                        alt96=1;
                    }
                    else if ( (true) ) {
                        alt96=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("515:1: castExpression : ( ( '(' typeName ')' )=> '(' typeName ')' ( castExpression -> ^( CAST_EXPRESSION typeName castExpression ) | '{' initializerList ( ',' )? '}' -> ^( COMPOUND_LITERAL typeName initializerList ) ) | unaryExpression );", 96, 30, input);

                        throw nvae;
                    }
                    }
                    break;
                case 102:
                    {
                    int LA96_31 = input.LA(3);

                    if ( (synpred181()) ) {
                        alt96=1;
                    }
                    else if ( (true) ) {
                        alt96=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("515:1: castExpression : ( ( '(' typeName ')' )=> '(' typeName ')' ( castExpression -> ^( CAST_EXPRESSION typeName castExpression ) | '{' initializerList ( ',' )? '}' -> ^( COMPOUND_LITERAL typeName initializerList ) ) | unaryExpression );", 96, 31, input);

                        throw nvae;
                    }
                    }
                    break;
                case 103:
                    {
                    int LA96_32 = input.LA(3);

                    if ( (synpred181()) ) {
                        alt96=1;
                    }
                    else if ( (true) ) {
                        alt96=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("515:1: castExpression : ( ( '(' typeName ')' )=> '(' typeName ')' ( castExpression -> ^( CAST_EXPRESSION typeName castExpression ) | '{' initializerList ( ',' )? '}' -> ^( COMPOUND_LITERAL typeName initializerList ) ) | unaryExpression );", 96, 32, input);

                        throw nvae;
                    }
                    }
                    break;
                case 104:
                    {
                    int LA96_33 = input.LA(3);

                    if ( (synpred181()) ) {
                        alt96=1;
                    }
                    else if ( (true) ) {
                        alt96=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("515:1: castExpression : ( ( '(' typeName ')' )=> '(' typeName ')' ( castExpression -> ^( CAST_EXPRESSION typeName castExpression ) | '{' initializerList ( ',' )? '}' -> ^( COMPOUND_LITERAL typeName initializerList ) ) | unaryExpression );", 96, 33, input);

                        throw nvae;
                    }
                    }
                    break;
                case 105:
                    {
                    int LA96_34 = input.LA(3);

                    if ( (synpred181()) ) {
                        alt96=1;
                    }
                    else if ( (true) ) {
                        alt96=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("515:1: castExpression : ( ( '(' typeName ')' )=> '(' typeName ')' ( castExpression -> ^( CAST_EXPRESSION typeName castExpression ) | '{' initializerList ( ',' )? '}' -> ^( COMPOUND_LITERAL typeName initializerList ) ) | unaryExpression );", 96, 34, input);

                        throw nvae;
                    }
                    }
                    break;
                case 106:
                    {
                    int LA96_35 = input.LA(3);

                    if ( (synpred181()) ) {
                        alt96=1;
                    }
                    else if ( (true) ) {
                        alt96=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("515:1: castExpression : ( ( '(' typeName ')' )=> '(' typeName ')' ( castExpression -> ^( CAST_EXPRESSION typeName castExpression ) | '{' initializerList ( ',' )? '}' -> ^( COMPOUND_LITERAL typeName initializerList ) ) | unaryExpression );", 96, 35, input);

                        throw nvae;
                    }
                    }
                    break;
                case 107:
                    {
                    int LA96_36 = input.LA(3);

                    if ( (synpred181()) ) {
                        alt96=1;
                    }
                    else if ( (true) ) {
                        alt96=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("515:1: castExpression : ( ( '(' typeName ')' )=> '(' typeName ')' ( castExpression -> ^( CAST_EXPRESSION typeName castExpression ) | '{' initializerList ( ',' )? '}' -> ^( COMPOUND_LITERAL typeName initializerList ) ) | unaryExpression );", 96, 36, input);

                        throw nvae;
                    }
                    }
                    break;
                case 108:
                    {
                    int LA96_37 = input.LA(3);

                    if ( (synpred181()) ) {
                        alt96=1;
                    }
                    else if ( (true) ) {
                        alt96=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("515:1: castExpression : ( ( '(' typeName ')' )=> '(' typeName ')' ( castExpression -> ^( CAST_EXPRESSION typeName castExpression ) | '{' initializerList ( ',' )? '}' -> ^( COMPOUND_LITERAL typeName initializerList ) ) | unaryExpression );", 96, 37, input);

                        throw nvae;
                    }
                    }
                    break;
                case 111:
                case 112:
                    {
                    int LA96_38 = input.LA(3);

                    if ( (synpred181()) ) {
                        alt96=1;
                    }
                    else if ( (true) ) {
                        alt96=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("515:1: castExpression : ( ( '(' typeName ')' )=> '(' typeName ')' ( castExpression -> ^( CAST_EXPRESSION typeName castExpression ) | '{' initializerList ( ',' )? '}' -> ^( COMPOUND_LITERAL typeName initializerList ) ) | unaryExpression );", 96, 38, input);

                        throw nvae;
                    }
                    }
                    break;
                case 114:
                    {
                    int LA96_39 = input.LA(3);

                    if ( (synpred181()) ) {
                        alt96=1;
                    }
                    else if ( (true) ) {
                        alt96=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("515:1: castExpression : ( ( '(' typeName ')' )=> '(' typeName ')' ( castExpression -> ^( CAST_EXPRESSION typeName castExpression ) | '{' initializerList ( ',' )? '}' -> ^( COMPOUND_LITERAL typeName initializerList ) ) | unaryExpression );", 96, 39, input);

                        throw nvae;
                    }
                    }
                    break;
                case IDENTIFIER:
                    {
                    int LA96_40 = input.LA(3);

                    if ( (synpred181()) ) {
                        alt96=1;
                    }
                    else if ( (true) ) {
                        alt96=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("515:1: castExpression : ( ( '(' typeName ')' )=> '(' typeName ')' ( castExpression -> ^( CAST_EXPRESSION typeName castExpression ) | '{' initializerList ( ',' )? '}' -> ^( COMPOUND_LITERAL typeName initializerList ) ) | unaryExpression );", 96, 40, input);

                        throw nvae;
                    }
                    }
                    break;
                case 131:
                    {
                    int LA96_41 = input.LA(3);

                    if ( (synpred181()) ) {
                        alt96=1;
                    }
                    else if ( (true) ) {
                        alt96=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("515:1: castExpression : ( ( '(' typeName ')' )=> '(' typeName ')' ( castExpression -> ^( CAST_EXPRESSION typeName castExpression ) | '{' initializerList ( ',' )? '}' -> ^( COMPOUND_LITERAL typeName initializerList ) ) | unaryExpression );", 96, 41, input);

                        throw nvae;
                    }
                    }
                    break;
                case 132:
                    {
                    int LA96_42 = input.LA(3);

                    if ( (synpred181()) ) {
                        alt96=1;
                    }
                    else if ( (true) ) {
                        alt96=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("515:1: castExpression : ( ( '(' typeName ')' )=> '(' typeName ')' ( castExpression -> ^( CAST_EXPRESSION typeName castExpression ) | '{' initializerList ( ',' )? '}' -> ^( COMPOUND_LITERAL typeName initializerList ) ) | unaryExpression );", 96, 42, input);

                        throw nvae;
                    }
                    }
                    break;
                case 133:
                    {
                    int LA96_43 = input.LA(3);

                    if ( (synpred181()) ) {
                        alt96=1;
                    }
                    else if ( (true) ) {
                        alt96=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("515:1: castExpression : ( ( '(' typeName ')' )=> '(' typeName ')' ( castExpression -> ^( CAST_EXPRESSION typeName castExpression ) | '{' initializerList ( ',' )? '}' -> ^( COMPOUND_LITERAL typeName initializerList ) ) | unaryExpression );", 96, 43, input);

                        throw nvae;
                    }
                    }
                    break;
                case 115:
                    {
                    int LA96_44 = input.LA(3);

                    if ( (synpred181()) ) {
                        alt96=1;
                    }
                    else if ( (true) ) {
                        alt96=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("515:1: castExpression : ( ( '(' typeName ')' )=> '(' typeName ')' ( castExpression -> ^( CAST_EXPRESSION typeName castExpression ) | '{' initializerList ( ',' )? '}' -> ^( COMPOUND_LITERAL typeName initializerList ) ) | unaryExpression );", 96, 44, input);

                        throw nvae;
                    }
                    }
                    break;
                case 116:
                    {
                    int LA96_45 = input.LA(3);

                    if ( (synpred181()) ) {
                        alt96=1;
                    }
                    else if ( (true) ) {
                        alt96=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("515:1: castExpression : ( ( '(' typeName ')' )=> '(' typeName ')' ( castExpression -> ^( CAST_EXPRESSION typeName castExpression ) | '{' initializerList ( ',' )? '}' -> ^( COMPOUND_LITERAL typeName initializerList ) ) | unaryExpression );", 96, 45, input);

                        throw nvae;
                    }
                    }
                    break;
                case 117:
                    {
                    int LA96_46 = input.LA(3);

                    if ( (synpred181()) ) {
                        alt96=1;
                    }
                    else if ( (true) ) {
                        alt96=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("515:1: castExpression : ( ( '(' typeName ')' )=> '(' typeName ')' ( castExpression -> ^( CAST_EXPRESSION typeName castExpression ) | '{' initializerList ( ',' )? '}' -> ^( COMPOUND_LITERAL typeName initializerList ) ) | unaryExpression );", 96, 46, input);

                        throw nvae;
                    }
                    }
                    break;
                case 118:
                    {
                    int LA96_47 = input.LA(3);

                    if ( (synpred181()) ) {
                        alt96=1;
                    }
                    else if ( (true) ) {
                        alt96=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("515:1: castExpression : ( ( '(' typeName ')' )=> '(' typeName ')' ( castExpression -> ^( CAST_EXPRESSION typeName castExpression ) | '{' initializerList ( ',' )? '}' -> ^( COMPOUND_LITERAL typeName initializerList ) ) | unaryExpression );", 96, 47, input);

                        throw nvae;
                    }
                    }
                    break;
                case 119:
                    {
                    int LA96_48 = input.LA(3);

                    if ( (synpred181()) ) {
                        alt96=1;
                    }
                    else if ( (true) ) {
                        alt96=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("515:1: castExpression : ( ( '(' typeName ')' )=> '(' typeName ')' ( castExpression -> ^( CAST_EXPRESSION typeName castExpression ) | '{' initializerList ( ',' )? '}' -> ^( COMPOUND_LITERAL typeName initializerList ) ) | unaryExpression );", 96, 48, input);

                        throw nvae;
                    }
                    }
                    break;
                case 120:
                    {
                    int LA96_49 = input.LA(3);

                    if ( (synpred181()) ) {
                        alt96=1;
                    }
                    else if ( (true) ) {
                        alt96=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("515:1: castExpression : ( ( '(' typeName ')' )=> '(' typeName ')' ( castExpression -> ^( CAST_EXPRESSION typeName castExpression ) | '{' initializerList ( ',' )? '}' -> ^( COMPOUND_LITERAL typeName initializerList ) ) | unaryExpression );", 96, 49, input);

                        throw nvae;
                    }
                    }
                    break;
                case 121:
                    {
                    int LA96_50 = input.LA(3);

                    if ( (synpred181()) ) {
                        alt96=1;
                    }
                    else if ( (true) ) {
                        alt96=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("515:1: castExpression : ( ( '(' typeName ')' )=> '(' typeName ')' ( castExpression -> ^( CAST_EXPRESSION typeName castExpression ) | '{' initializerList ( ',' )? '}' -> ^( COMPOUND_LITERAL typeName initializerList ) ) | unaryExpression );", 96, 50, input);

                        throw nvae;
                    }
                    }
                    break;
                case 122:
                    {
                    int LA96_51 = input.LA(3);

                    if ( (synpred181()) ) {
                        alt96=1;
                    }
                    else if ( (true) ) {
                        alt96=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("515:1: castExpression : ( ( '(' typeName ')' )=> '(' typeName ')' ( castExpression -> ^( CAST_EXPRESSION typeName castExpression ) | '{' initializerList ( ',' )? '}' -> ^( COMPOUND_LITERAL typeName initializerList ) ) | unaryExpression );", 96, 51, input);

                        throw nvae;
                    }
                    }
                    break;
                case 123:
                    {
                    int LA96_52 = input.LA(3);

                    if ( (synpred181()) ) {
                        alt96=1;
                    }
                    else if ( (true) ) {
                        alt96=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("515:1: castExpression : ( ( '(' typeName ')' )=> '(' typeName ')' ( castExpression -> ^( CAST_EXPRESSION typeName castExpression ) | '{' initializerList ( ',' )? '}' -> ^( COMPOUND_LITERAL typeName initializerList ) ) | unaryExpression );", 96, 52, input);

                        throw nvae;
                    }
                    }
                    break;
                case 135:
                case 136:
                    {
                    int LA96_53 = input.LA(3);

                    if ( (synpred181()) ) {
                        alt96=1;
                    }
                    else if ( (true) ) {
                        alt96=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("515:1: castExpression : ( ( '(' typeName ')' )=> '(' typeName ')' ( castExpression -> ^( CAST_EXPRESSION typeName castExpression ) | '{' initializerList ( ',' )? '}' -> ^( COMPOUND_LITERAL typeName initializerList ) ) | unaryExpression );", 96, 53, input);

                        throw nvae;
                    }
                    }
                    break;
                case CONSTANT:
                case STRING_LITERAL:
                case 83:
                case 109:
                case 129:
                case 137:
                case 139:
                case 140:
                case 141:
                case 142:
                case 143:
                case 144:
                case 145:
                case 146:
                case 147:
                case 148:
                case 149:
                case 150:
                case 151:
                case 152:
                case 153:
                    {
                    alt96=2;
                    }
                    break;
                default:
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("515:1: castExpression : ( ( '(' typeName ')' )=> '(' typeName ')' ( castExpression -> ^( CAST_EXPRESSION typeName castExpression ) | '{' initializerList ( ',' )? '}' -> ^( COMPOUND_LITERAL typeName initializerList ) ) | unaryExpression );", 96, 1, input);

                    throw nvae;
                }

            }
            else if ( ((LA96_0>=IDENTIFIER && LA96_0<=STRING_LITERAL)||LA96_0==129||LA96_0==137||(LA96_0>=139 && LA96_0<=153)) ) {
                alt96=2;
            }
            else {
                if (backtracking>0) {failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("515:1: castExpression : ( ( '(' typeName ')' )=> '(' typeName ')' ( castExpression -> ^( CAST_EXPRESSION typeName castExpression ) | '{' initializerList ( ',' )? '}' -> ^( COMPOUND_LITERAL typeName initializerList ) ) | unaryExpression );", 96, 0, input);

                throw nvae;
            }
            switch (alt96) {
                case 1 :
                    // GNUCa.g:519:4: ( '(' typeName ')' )=> '(' typeName ')' ( castExpression -> ^( CAST_EXPRESSION typeName castExpression ) | '{' initializerList ( ',' )? '}' -> ^( COMPOUND_LITERAL typeName initializerList ) )
                    {
                    char_literal313=(Token)input.LT(1);
                    match(input,83,FOLLOW_83_in_castExpression3440); if (failed) return retval;
                    if ( backtracking==0 ) stream_83.add(char_literal313);

                    pushFollow(FOLLOW_typeName_in_castExpression3441);
                    typeName314=typeName();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_typeName.add(typeName314.getTree());
                    char_literal315=(Token)input.LT(1);
                    match(input,84,FOLLOW_84_in_castExpression3443); if (failed) return retval;
                    if ( backtracking==0 ) stream_84.add(char_literal315);

                    // GNUCa.g:519:42: ( castExpression -> ^( CAST_EXPRESSION typeName castExpression ) | '{' initializerList ( ',' )? '}' -> ^( COMPOUND_LITERAL typeName initializerList ) )
                    int alt95=2;
                    int LA95_0 = input.LA(1);

                    if ( ((LA95_0>=IDENTIFIER && LA95_0<=STRING_LITERAL)||LA95_0==83||LA95_0==129||LA95_0==137||(LA95_0>=139 && LA95_0<=153)) ) {
                        alt95=1;
                    }
                    else if ( (LA95_0==109) ) {
                        alt95=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("519:42: ( castExpression -> ^( CAST_EXPRESSION typeName castExpression ) | '{' initializerList ( ',' )? '}' -> ^( COMPOUND_LITERAL typeName initializerList ) )", 95, 0, input);

                        throw nvae;
                    }
                    switch (alt95) {
                        case 1 :
                            // GNUCa.g:519:44: castExpression
                            {
                            pushFollow(FOLLOW_castExpression_in_castExpression3447);
                            castExpression316=castExpression();
                            _fsp--;
                            if (failed) return retval;
                            if ( backtracking==0 ) stream_castExpression.add(castExpression316.getTree());

                            // AST REWRITE
                            // elements: typeName, castExpression
                            // token labels: 
                            // rule labels: retval
                            // token list labels: 
                            // rule list labels: 
                            if ( backtracking==0 ) {
                            retval.tree = root_0;
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                            root_0 = (Object)adaptor.nil();
                            // 519:60: -> ^( CAST_EXPRESSION typeName castExpression )
                            {
                                // GNUCa.g:519:63: ^( CAST_EXPRESSION typeName castExpression )
                                {
                                Object root_1 = (Object)adaptor.nil();
                                root_1 = (Object)adaptor.becomeRoot(adaptor.create(CAST_EXPRESSION, "CAST_EXPRESSION"), root_1);

                                adaptor.addChild(root_1, stream_typeName.next());
                                adaptor.addChild(root_1, stream_castExpression.next());

                                adaptor.addChild(root_0, root_1);
                                }

                            }

                            }

                            }
                            break;
                        case 2 :
                            // GNUCa.g:519:107: '{' initializerList ( ',' )? '}'
                            {
                            char_literal317=(Token)input.LT(1);
                            match(input,109,FOLLOW_109_in_castExpression3461); if (failed) return retval;
                            if ( backtracking==0 ) stream_109.add(char_literal317);

                            pushFollow(FOLLOW_initializerList_in_castExpression3463);
                            initializerList318=initializerList();
                            _fsp--;
                            if (failed) return retval;
                            if ( backtracking==0 ) stream_initializerList.add(initializerList318.getTree());
                            // GNUCa.g:519:127: ( ',' )?
                            int alt94=2;
                            int LA94_0 = input.LA(1);

                            if ( (LA94_0==86) ) {
                                alt94=1;
                            }
                            switch (alt94) {
                                case 1 :
                                    // GNUCa.g:0:0: ','
                                    {
                                    char_literal319=(Token)input.LT(1);
                                    match(input,86,FOLLOW_86_in_castExpression3465); if (failed) return retval;
                                    if ( backtracking==0 ) stream_86.add(char_literal319);


                                    }
                                    break;

                            }

                            char_literal320=(Token)input.LT(1);
                            match(input,110,FOLLOW_110_in_castExpression3468); if (failed) return retval;
                            if ( backtracking==0 ) stream_110.add(char_literal320);


                            // AST REWRITE
                            // elements: typeName, initializerList
                            // token labels: 
                            // rule labels: retval
                            // token list labels: 
                            // rule list labels: 
                            if ( backtracking==0 ) {
                            retval.tree = root_0;
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                            root_0 = (Object)adaptor.nil();
                            // 519:136: -> ^( COMPOUND_LITERAL typeName initializerList )
                            {
                                // GNUCa.g:519:139: ^( COMPOUND_LITERAL typeName initializerList )
                                {
                                Object root_1 = (Object)adaptor.nil();
                                root_1 = (Object)adaptor.becomeRoot(adaptor.create(COMPOUND_LITERAL, "COMPOUND_LITERAL"), root_1);

                                adaptor.addChild(root_1, stream_typeName.next());
                                adaptor.addChild(root_1, stream_initializerList.next());

                                adaptor.addChild(root_0, root_1);
                                }

                            }

                            }

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // GNUCa.g:520:4: unaryExpression
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_unaryExpression_in_castExpression3485);
                    unaryExpression321=unaryExpression();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) adaptor.addChild(root_0, unaryExpression321.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 52, castExpression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end castExpression

    public static class multiplicativeExpression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start multiplicativeExpression
    // GNUCa.g:524:1: multiplicativeExpression : castExpression ( ( '*' | '/' | '%' ) castExpression )* ;
    public final multiplicativeExpression_return multiplicativeExpression() throws RecognitionException {
        multiplicativeExpression_return retval = new multiplicativeExpression_return();
        retval.start = input.LT(1);
        int multiplicativeExpression_StartIndex = input.index();
        Object root_0 = null;

        Token set323=null;
        castExpression_return castExpression322 = null;

        castExpression_return castExpression324 = null;


        Object set323_tree=null;

        try {
            if ( backtracking>0 && alreadyParsedRule(input, 53) ) { return retval; }
            // GNUCa.g:525:2: ( castExpression ( ( '*' | '/' | '%' ) castExpression )* )
            // GNUCa.g:525:4: castExpression ( ( '*' | '/' | '%' ) castExpression )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_castExpression_in_multiplicativeExpression3498);
            castExpression322=castExpression();
            _fsp--;
            if (failed) return retval;
            if ( backtracking==0 ) adaptor.addChild(root_0, castExpression322.getTree());
            // GNUCa.g:525:19: ( ( '*' | '/' | '%' ) castExpression )*
            loop97:
            do {
                int alt97=2;
                int LA97_0 = input.LA(1);

                if ( (LA97_0==129||(LA97_0>=154 && LA97_0<=155)) ) {
                    alt97=1;
                }


                switch (alt97) {
            	case 1 :
            	    // GNUCa.g:525:20: ( '*' | '/' | '%' ) castExpression
            	    {
            	    set323=(Token)input.LT(1);
            	    if ( input.LA(1)==129||(input.LA(1)>=154 && input.LA(1)<=155) ) {
            	        input.consume();
            	        if ( backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(adaptor.create(set323), root_0);
            	        errorRecovery=false;failed=false;
            	    }
            	    else {
            	        if (backtracking>0) {failed=true; return retval;}
            	        MismatchedSetException mse =
            	            new MismatchedSetException(null,input);
            	        recoverFromMismatchedSet(input,mse,FOLLOW_set_in_multiplicativeExpression3501);    throw mse;
            	    }

            	    pushFollow(FOLLOW_castExpression_in_multiplicativeExpression3510);
            	    castExpression324=castExpression();
            	    _fsp--;
            	    if (failed) return retval;
            	    if ( backtracking==0 ) adaptor.addChild(root_0, castExpression324.getTree());

            	    }
            	    break;

            	default :
            	    break loop97;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 53, multiplicativeExpression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end multiplicativeExpression

    public static class additiveExpression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start additiveExpression
    // GNUCa.g:528:1: additiveExpression : multiplicativeExpression ( ( '+' | '-' ) multiplicativeExpression )* ;
    public final additiveExpression_return additiveExpression() throws RecognitionException {
        additiveExpression_return retval = new additiveExpression_return();
        retval.start = input.LT(1);
        int additiveExpression_StartIndex = input.index();
        Object root_0 = null;

        Token set326=null;
        multiplicativeExpression_return multiplicativeExpression325 = null;

        multiplicativeExpression_return multiplicativeExpression327 = null;


        Object set326_tree=null;

        try {
            if ( backtracking>0 && alreadyParsedRule(input, 54) ) { return retval; }
            // GNUCa.g:529:2: ( multiplicativeExpression ( ( '+' | '-' ) multiplicativeExpression )* )
            // GNUCa.g:529:4: multiplicativeExpression ( ( '+' | '-' ) multiplicativeExpression )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression3523);
            multiplicativeExpression325=multiplicativeExpression();
            _fsp--;
            if (failed) return retval;
            if ( backtracking==0 ) adaptor.addChild(root_0, multiplicativeExpression325.getTree());
            // GNUCa.g:529:29: ( ( '+' | '-' ) multiplicativeExpression )*
            loop98:
            do {
                int alt98=2;
                int LA98_0 = input.LA(1);

                if ( ((LA98_0>=145 && LA98_0<=146)) ) {
                    alt98=1;
                }


                switch (alt98) {
            	case 1 :
            	    // GNUCa.g:529:30: ( '+' | '-' ) multiplicativeExpression
            	    {
            	    set326=(Token)input.LT(1);
            	    if ( (input.LA(1)>=145 && input.LA(1)<=146) ) {
            	        input.consume();
            	        if ( backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(adaptor.create(set326), root_0);
            	        errorRecovery=false;failed=false;
            	    }
            	    else {
            	        if (backtracking>0) {failed=true; return retval;}
            	        MismatchedSetException mse =
            	            new MismatchedSetException(null,input);
            	        recoverFromMismatchedSet(input,mse,FOLLOW_set_in_additiveExpression3526);    throw mse;
            	    }

            	    pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression3533);
            	    multiplicativeExpression327=multiplicativeExpression();
            	    _fsp--;
            	    if (failed) return retval;
            	    if ( backtracking==0 ) adaptor.addChild(root_0, multiplicativeExpression327.getTree());

            	    }
            	    break;

            	default :
            	    break loop98;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 54, additiveExpression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end additiveExpression

    public static class shiftExpression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start shiftExpression
    // GNUCa.g:532:1: shiftExpression : additiveExpression ( ( '<<' | '>>' ) additiveExpression )* ;
    public final shiftExpression_return shiftExpression() throws RecognitionException {
        shiftExpression_return retval = new shiftExpression_return();
        retval.start = input.LT(1);
        int shiftExpression_StartIndex = input.index();
        Object root_0 = null;

        Token set329=null;
        additiveExpression_return additiveExpression328 = null;

        additiveExpression_return additiveExpression330 = null;


        Object set329_tree=null;

        try {
            if ( backtracking>0 && alreadyParsedRule(input, 55) ) { return retval; }
            // GNUCa.g:533:2: ( additiveExpression ( ( '<<' | '>>' ) additiveExpression )* )
            // GNUCa.g:533:4: additiveExpression ( ( '<<' | '>>' ) additiveExpression )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_additiveExpression_in_shiftExpression3546);
            additiveExpression328=additiveExpression();
            _fsp--;
            if (failed) return retval;
            if ( backtracking==0 ) adaptor.addChild(root_0, additiveExpression328.getTree());
            // GNUCa.g:533:23: ( ( '<<' | '>>' ) additiveExpression )*
            loop99:
            do {
                int alt99=2;
                int LA99_0 = input.LA(1);

                if ( ((LA99_0>=156 && LA99_0<=157)) ) {
                    alt99=1;
                }


                switch (alt99) {
            	case 1 :
            	    // GNUCa.g:533:24: ( '<<' | '>>' ) additiveExpression
            	    {
            	    set329=(Token)input.LT(1);
            	    if ( (input.LA(1)>=156 && input.LA(1)<=157) ) {
            	        input.consume();
            	        if ( backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(adaptor.create(set329), root_0);
            	        errorRecovery=false;failed=false;
            	    }
            	    else {
            	        if (backtracking>0) {failed=true; return retval;}
            	        MismatchedSetException mse =
            	            new MismatchedSetException(null,input);
            	        recoverFromMismatchedSet(input,mse,FOLLOW_set_in_shiftExpression3549);    throw mse;
            	    }

            	    pushFollow(FOLLOW_additiveExpression_in_shiftExpression3556);
            	    additiveExpression330=additiveExpression();
            	    _fsp--;
            	    if (failed) return retval;
            	    if ( backtracking==0 ) adaptor.addChild(root_0, additiveExpression330.getTree());

            	    }
            	    break;

            	default :
            	    break loop99;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 55, shiftExpression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end shiftExpression

    public static class relationalExpression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start relationalExpression
    // GNUCa.g:536:1: relationalExpression : shiftExpression ( ( '<' | '>' | '<=' | '>=' ) shiftExpression )* ;
    public final relationalExpression_return relationalExpression() throws RecognitionException {
        relationalExpression_return retval = new relationalExpression_return();
        retval.start = input.LT(1);
        int relationalExpression_StartIndex = input.index();
        Object root_0 = null;

        Token set332=null;
        shiftExpression_return shiftExpression331 = null;

        shiftExpression_return shiftExpression333 = null;


        Object set332_tree=null;

        try {
            if ( backtracking>0 && alreadyParsedRule(input, 56) ) { return retval; }
            // GNUCa.g:537:2: ( shiftExpression ( ( '<' | '>' | '<=' | '>=' ) shiftExpression )* )
            // GNUCa.g:537:4: shiftExpression ( ( '<' | '>' | '<=' | '>=' ) shiftExpression )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_shiftExpression_in_relationalExpression3569);
            shiftExpression331=shiftExpression();
            _fsp--;
            if (failed) return retval;
            if ( backtracking==0 ) adaptor.addChild(root_0, shiftExpression331.getTree());
            // GNUCa.g:537:20: ( ( '<' | '>' | '<=' | '>=' ) shiftExpression )*
            loop100:
            do {
                int alt100=2;
                int LA100_0 = input.LA(1);

                if ( ((LA100_0>=158 && LA100_0<=161)) ) {
                    alt100=1;
                }


                switch (alt100) {
            	case 1 :
            	    // GNUCa.g:537:21: ( '<' | '>' | '<=' | '>=' ) shiftExpression
            	    {
            	    set332=(Token)input.LT(1);
            	    if ( (input.LA(1)>=158 && input.LA(1)<=161) ) {
            	        input.consume();
            	        if ( backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(adaptor.create(set332), root_0);
            	        errorRecovery=false;failed=false;
            	    }
            	    else {
            	        if (backtracking>0) {failed=true; return retval;}
            	        MismatchedSetException mse =
            	            new MismatchedSetException(null,input);
            	        recoverFromMismatchedSet(input,mse,FOLLOW_set_in_relationalExpression3572);    throw mse;
            	    }

            	    pushFollow(FOLLOW_shiftExpression_in_relationalExpression3583);
            	    shiftExpression333=shiftExpression();
            	    _fsp--;
            	    if (failed) return retval;
            	    if ( backtracking==0 ) adaptor.addChild(root_0, shiftExpression333.getTree());

            	    }
            	    break;

            	default :
            	    break loop100;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 56, relationalExpression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end relationalExpression

    public static class equalityExpression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start equalityExpression
    // GNUCa.g:540:1: equalityExpression : relationalExpression ( ( '==' | '!=' ) relationalExpression )* ;
    public final equalityExpression_return equalityExpression() throws RecognitionException {
        equalityExpression_return retval = new equalityExpression_return();
        retval.start = input.LT(1);
        int equalityExpression_StartIndex = input.index();
        Object root_0 = null;

        Token set335=null;
        relationalExpression_return relationalExpression334 = null;

        relationalExpression_return relationalExpression336 = null;


        Object set335_tree=null;

        try {
            if ( backtracking>0 && alreadyParsedRule(input, 57) ) { return retval; }
            // GNUCa.g:541:2: ( relationalExpression ( ( '==' | '!=' ) relationalExpression )* )
            // GNUCa.g:541:4: relationalExpression ( ( '==' | '!=' ) relationalExpression )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_relationalExpression_in_equalityExpression3596);
            relationalExpression334=relationalExpression();
            _fsp--;
            if (failed) return retval;
            if ( backtracking==0 ) adaptor.addChild(root_0, relationalExpression334.getTree());
            // GNUCa.g:541:25: ( ( '==' | '!=' ) relationalExpression )*
            loop101:
            do {
                int alt101=2;
                int LA101_0 = input.LA(1);

                if ( ((LA101_0>=162 && LA101_0<=163)) ) {
                    alt101=1;
                }


                switch (alt101) {
            	case 1 :
            	    // GNUCa.g:541:26: ( '==' | '!=' ) relationalExpression
            	    {
            	    set335=(Token)input.LT(1);
            	    if ( (input.LA(1)>=162 && input.LA(1)<=163) ) {
            	        input.consume();
            	        if ( backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(adaptor.create(set335), root_0);
            	        errorRecovery=false;failed=false;
            	    }
            	    else {
            	        if (backtracking>0) {failed=true; return retval;}
            	        MismatchedSetException mse =
            	            new MismatchedSetException(null,input);
            	        recoverFromMismatchedSet(input,mse,FOLLOW_set_in_equalityExpression3599);    throw mse;
            	    }

            	    pushFollow(FOLLOW_relationalExpression_in_equalityExpression3606);
            	    relationalExpression336=relationalExpression();
            	    _fsp--;
            	    if (failed) return retval;
            	    if ( backtracking==0 ) adaptor.addChild(root_0, relationalExpression336.getTree());

            	    }
            	    break;

            	default :
            	    break loop101;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 57, equalityExpression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end equalityExpression

    public static class andExpression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start andExpression
    // GNUCa.g:544:1: andExpression : equalityExpression ( '&' equalityExpression )* ;
    public final andExpression_return andExpression() throws RecognitionException {
        andExpression_return retval = new andExpression_return();
        retval.start = input.LT(1);
        int andExpression_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal338=null;
        equalityExpression_return equalityExpression337 = null;

        equalityExpression_return equalityExpression339 = null;


        Object char_literal338_tree=null;

        try {
            if ( backtracking>0 && alreadyParsedRule(input, 58) ) { return retval; }
            // GNUCa.g:545:2: ( equalityExpression ( '&' equalityExpression )* )
            // GNUCa.g:545:4: equalityExpression ( '&' equalityExpression )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_equalityExpression_in_andExpression3619);
            equalityExpression337=equalityExpression();
            _fsp--;
            if (failed) return retval;
            if ( backtracking==0 ) adaptor.addChild(root_0, equalityExpression337.getTree());
            // GNUCa.g:545:23: ( '&' equalityExpression )*
            loop102:
            do {
                int alt102=2;
                int LA102_0 = input.LA(1);

                if ( (LA102_0==144) ) {
                    alt102=1;
                }


                switch (alt102) {
            	case 1 :
            	    // GNUCa.g:545:24: '&' equalityExpression
            	    {
            	    char_literal338=(Token)input.LT(1);
            	    match(input,144,FOLLOW_144_in_andExpression3622); if (failed) return retval;
            	    if ( backtracking==0 ) {
            	    char_literal338_tree = (Object)adaptor.create(char_literal338);
            	    root_0 = (Object)adaptor.becomeRoot(char_literal338_tree, root_0);
            	    }
            	    pushFollow(FOLLOW_equalityExpression_in_andExpression3625);
            	    equalityExpression339=equalityExpression();
            	    _fsp--;
            	    if (failed) return retval;
            	    if ( backtracking==0 ) adaptor.addChild(root_0, equalityExpression339.getTree());

            	    }
            	    break;

            	default :
            	    break loop102;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 58, andExpression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end andExpression

    public static class xorExpression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start xorExpression
    // GNUCa.g:548:1: xorExpression : andExpression ( '^' andExpression )* ;
    public final xorExpression_return xorExpression() throws RecognitionException {
        xorExpression_return retval = new xorExpression_return();
        retval.start = input.LT(1);
        int xorExpression_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal341=null;
        andExpression_return andExpression340 = null;

        andExpression_return andExpression342 = null;


        Object char_literal341_tree=null;

        try {
            if ( backtracking>0 && alreadyParsedRule(input, 59) ) { return retval; }
            // GNUCa.g:549:2: ( andExpression ( '^' andExpression )* )
            // GNUCa.g:549:4: andExpression ( '^' andExpression )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_andExpression_in_xorExpression3638);
            andExpression340=andExpression();
            _fsp--;
            if (failed) return retval;
            if ( backtracking==0 ) adaptor.addChild(root_0, andExpression340.getTree());
            // GNUCa.g:549:18: ( '^' andExpression )*
            loop103:
            do {
                int alt103=2;
                int LA103_0 = input.LA(1);

                if ( (LA103_0==164) ) {
                    alt103=1;
                }


                switch (alt103) {
            	case 1 :
            	    // GNUCa.g:549:19: '^' andExpression
            	    {
            	    char_literal341=(Token)input.LT(1);
            	    match(input,164,FOLLOW_164_in_xorExpression3641); if (failed) return retval;
            	    if ( backtracking==0 ) {
            	    char_literal341_tree = (Object)adaptor.create(char_literal341);
            	    root_0 = (Object)adaptor.becomeRoot(char_literal341_tree, root_0);
            	    }
            	    pushFollow(FOLLOW_andExpression_in_xorExpression3644);
            	    andExpression342=andExpression();
            	    _fsp--;
            	    if (failed) return retval;
            	    if ( backtracking==0 ) adaptor.addChild(root_0, andExpression342.getTree());

            	    }
            	    break;

            	default :
            	    break loop103;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 59, xorExpression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end xorExpression

    public static class orExpression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start orExpression
    // GNUCa.g:552:1: orExpression : xorExpression ( '|' xorExpression )* ;
    public final orExpression_return orExpression() throws RecognitionException {
        orExpression_return retval = new orExpression_return();
        retval.start = input.LT(1);
        int orExpression_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal344=null;
        xorExpression_return xorExpression343 = null;

        xorExpression_return xorExpression345 = null;


        Object char_literal344_tree=null;

        try {
            if ( backtracking>0 && alreadyParsedRule(input, 60) ) { return retval; }
            // GNUCa.g:553:2: ( xorExpression ( '|' xorExpression )* )
            // GNUCa.g:553:4: xorExpression ( '|' xorExpression )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_xorExpression_in_orExpression3658);
            xorExpression343=xorExpression();
            _fsp--;
            if (failed) return retval;
            if ( backtracking==0 ) adaptor.addChild(root_0, xorExpression343.getTree());
            // GNUCa.g:553:18: ( '|' xorExpression )*
            loop104:
            do {
                int alt104=2;
                int LA104_0 = input.LA(1);

                if ( (LA104_0==165) ) {
                    alt104=1;
                }


                switch (alt104) {
            	case 1 :
            	    // GNUCa.g:553:19: '|' xorExpression
            	    {
            	    char_literal344=(Token)input.LT(1);
            	    match(input,165,FOLLOW_165_in_orExpression3661); if (failed) return retval;
            	    if ( backtracking==0 ) {
            	    char_literal344_tree = (Object)adaptor.create(char_literal344);
            	    root_0 = (Object)adaptor.becomeRoot(char_literal344_tree, root_0);
            	    }
            	    pushFollow(FOLLOW_xorExpression_in_orExpression3664);
            	    xorExpression345=xorExpression();
            	    _fsp--;
            	    if (failed) return retval;
            	    if ( backtracking==0 ) adaptor.addChild(root_0, xorExpression345.getTree());

            	    }
            	    break;

            	default :
            	    break loop104;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 60, orExpression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end orExpression

    public static class landExpression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start landExpression
    // GNUCa.g:556:1: landExpression : orExpression ( '&&' orExpression )* ;
    public final landExpression_return landExpression() throws RecognitionException {
        landExpression_return retval = new landExpression_return();
        retval.start = input.LT(1);
        int landExpression_StartIndex = input.index();
        Object root_0 = null;

        Token string_literal347=null;
        orExpression_return orExpression346 = null;

        orExpression_return orExpression348 = null;


        Object string_literal347_tree=null;

        try {
            if ( backtracking>0 && alreadyParsedRule(input, 61) ) { return retval; }
            // GNUCa.g:557:2: ( orExpression ( '&&' orExpression )* )
            // GNUCa.g:557:4: orExpression ( '&&' orExpression )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_orExpression_in_landExpression3678);
            orExpression346=orExpression();
            _fsp--;
            if (failed) return retval;
            if ( backtracking==0 ) adaptor.addChild(root_0, orExpression346.getTree());
            // GNUCa.g:557:17: ( '&&' orExpression )*
            loop105:
            do {
                int alt105=2;
                int LA105_0 = input.LA(1);

                if ( (LA105_0==149) ) {
                    alt105=1;
                }


                switch (alt105) {
            	case 1 :
            	    // GNUCa.g:557:18: '&&' orExpression
            	    {
            	    string_literal347=(Token)input.LT(1);
            	    match(input,149,FOLLOW_149_in_landExpression3681); if (failed) return retval;
            	    if ( backtracking==0 ) {
            	    string_literal347_tree = (Object)adaptor.create(string_literal347);
            	    root_0 = (Object)adaptor.becomeRoot(string_literal347_tree, root_0);
            	    }
            	    pushFollow(FOLLOW_orExpression_in_landExpression3684);
            	    orExpression348=orExpression();
            	    _fsp--;
            	    if (failed) return retval;
            	    if ( backtracking==0 ) adaptor.addChild(root_0, orExpression348.getTree());

            	    }
            	    break;

            	default :
            	    break loop105;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 61, landExpression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end landExpression

    public static class lorExpression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start lorExpression
    // GNUCa.g:560:1: lorExpression : landExpression ( '||' landExpression )* ;
    public final lorExpression_return lorExpression() throws RecognitionException {
        lorExpression_return retval = new lorExpression_return();
        retval.start = input.LT(1);
        int lorExpression_StartIndex = input.index();
        Object root_0 = null;

        Token string_literal350=null;
        landExpression_return landExpression349 = null;

        landExpression_return landExpression351 = null;


        Object string_literal350_tree=null;

        try {
            if ( backtracking>0 && alreadyParsedRule(input, 62) ) { return retval; }
            // GNUCa.g:561:2: ( landExpression ( '||' landExpression )* )
            // GNUCa.g:561:4: landExpression ( '||' landExpression )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_landExpression_in_lorExpression3698);
            landExpression349=landExpression();
            _fsp--;
            if (failed) return retval;
            if ( backtracking==0 ) adaptor.addChild(root_0, landExpression349.getTree());
            // GNUCa.g:561:19: ( '||' landExpression )*
            loop106:
            do {
                int alt106=2;
                int LA106_0 = input.LA(1);

                if ( (LA106_0==166) ) {
                    alt106=1;
                }


                switch (alt106) {
            	case 1 :
            	    // GNUCa.g:561:20: '||' landExpression
            	    {
            	    string_literal350=(Token)input.LT(1);
            	    match(input,166,FOLLOW_166_in_lorExpression3701); if (failed) return retval;
            	    if ( backtracking==0 ) {
            	    string_literal350_tree = (Object)adaptor.create(string_literal350);
            	    root_0 = (Object)adaptor.becomeRoot(string_literal350_tree, root_0);
            	    }
            	    pushFollow(FOLLOW_landExpression_in_lorExpression3704);
            	    landExpression351=landExpression();
            	    _fsp--;
            	    if (failed) return retval;
            	    if ( backtracking==0 ) adaptor.addChild(root_0, landExpression351.getTree());

            	    }
            	    break;

            	default :
            	    break loop106;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 62, lorExpression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end lorExpression

    public static class conditionalExpression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start conditionalExpression
    // GNUCa.g:564:1: conditionalExpression : lorExpression ( '?' ( expression )? ':' conditionalExpression -> ^( CONDITIONAL_EXPRESSION ^( E1 lorExpression ) ^( E2 ( expression )? ) ^( E3 conditionalExpression ) ) | -> lorExpression ) ;
    public final conditionalExpression_return conditionalExpression() throws RecognitionException {
        conditionalExpression_return retval = new conditionalExpression_return();
        retval.start = input.LT(1);
        int conditionalExpression_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal353=null;
        Token char_literal355=null;
        lorExpression_return lorExpression352 = null;

        expression_return expression354 = null;

        conditionalExpression_return conditionalExpression356 = null;


        Object char_literal353_tree=null;
        Object char_literal355_tree=null;
        RewriteRuleTokenStream stream_113=new RewriteRuleTokenStream(adaptor,"token 113");
        RewriteRuleTokenStream stream_167=new RewriteRuleTokenStream(adaptor,"token 167");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_lorExpression=new RewriteRuleSubtreeStream(adaptor,"rule lorExpression");
        RewriteRuleSubtreeStream stream_conditionalExpression=new RewriteRuleSubtreeStream(adaptor,"rule conditionalExpression");
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 63) ) { return retval; }
            // GNUCa.g:565:2: ( lorExpression ( '?' ( expression )? ':' conditionalExpression -> ^( CONDITIONAL_EXPRESSION ^( E1 lorExpression ) ^( E2 ( expression )? ) ^( E3 conditionalExpression ) ) | -> lorExpression ) )
            // GNUCa.g:565:4: lorExpression ( '?' ( expression )? ':' conditionalExpression -> ^( CONDITIONAL_EXPRESSION ^( E1 lorExpression ) ^( E2 ( expression )? ) ^( E3 conditionalExpression ) ) | -> lorExpression )
            {
            pushFollow(FOLLOW_lorExpression_in_conditionalExpression3718);
            lorExpression352=lorExpression();
            _fsp--;
            if (failed) return retval;
            if ( backtracking==0 ) stream_lorExpression.add(lorExpression352.getTree());
            // GNUCa.g:565:18: ( '?' ( expression )? ':' conditionalExpression -> ^( CONDITIONAL_EXPRESSION ^( E1 lorExpression ) ^( E2 ( expression )? ) ^( E3 conditionalExpression ) ) | -> lorExpression )
            int alt108=2;
            int LA108_0 = input.LA(1);

            if ( (LA108_0==167) ) {
                alt108=1;
            }
            else if ( (LA108_0==EOF||LA108_0==79||LA108_0==84||(LA108_0>=86 && LA108_0<=87)||LA108_0==110||LA108_0==113||LA108_0==128||LA108_0==130||(LA108_0>=135 && LA108_0<=136)||(LA108_0>=168 && LA108_0<=177)) ) {
                alt108=2;
            }
            else {
                if (backtracking>0) {failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("565:18: ( '?' ( expression )? ':' conditionalExpression -> ^( CONDITIONAL_EXPRESSION ^( E1 lorExpression ) ^( E2 ( expression )? ) ^( E3 conditionalExpression ) ) | -> lorExpression )", 108, 0, input);

                throw nvae;
            }
            switch (alt108) {
                case 1 :
                    // GNUCa.g:565:19: '?' ( expression )? ':' conditionalExpression
                    {
                    char_literal353=(Token)input.LT(1);
                    match(input,167,FOLLOW_167_in_conditionalExpression3721); if (failed) return retval;
                    if ( backtracking==0 ) stream_167.add(char_literal353);

                    // GNUCa.g:565:23: ( expression )?
                    int alt107=2;
                    int LA107_0 = input.LA(1);

                    if ( ((LA107_0>=IDENTIFIER && LA107_0<=STRING_LITERAL)||LA107_0==83||LA107_0==129||LA107_0==137||(LA107_0>=139 && LA107_0<=153)) ) {
                        alt107=1;
                    }
                    switch (alt107) {
                        case 1 :
                            // GNUCa.g:0:0: expression
                            {
                            pushFollow(FOLLOW_expression_in_conditionalExpression3723);
                            expression354=expression();
                            _fsp--;
                            if (failed) return retval;
                            if ( backtracking==0 ) stream_expression.add(expression354.getTree());

                            }
                            break;

                    }

                    char_literal355=(Token)input.LT(1);
                    match(input,113,FOLLOW_113_in_conditionalExpression3726); if (failed) return retval;
                    if ( backtracking==0 ) stream_113.add(char_literal355);

                    pushFollow(FOLLOW_conditionalExpression_in_conditionalExpression3728);
                    conditionalExpression356=conditionalExpression();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_conditionalExpression.add(conditionalExpression356.getTree());

                    // AST REWRITE
                    // elements: lorExpression, conditionalExpression, expression
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 565:61: -> ^( CONDITIONAL_EXPRESSION ^( E1 lorExpression ) ^( E2 ( expression )? ) ^( E3 conditionalExpression ) )
                    {
                        // GNUCa.g:565:64: ^( CONDITIONAL_EXPRESSION ^( E1 lorExpression ) ^( E2 ( expression )? ) ^( E3 conditionalExpression ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(adaptor.create(CONDITIONAL_EXPRESSION, "CONDITIONAL_EXPRESSION"), root_1);

                        // GNUCa.g:565:89: ^( E1 lorExpression )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot(adaptor.create(E1, "E1"), root_2);

                        adaptor.addChild(root_2, stream_lorExpression.next());

                        adaptor.addChild(root_1, root_2);
                        }
                        // GNUCa.g:565:109: ^( E2 ( expression )? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot(adaptor.create(E2, "E2"), root_2);

                        // GNUCa.g:565:114: ( expression )?
                        if ( stream_expression.hasNext() ) {
                            adaptor.addChild(root_2, stream_expression.next());

                        }
                        stream_expression.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // GNUCa.g:565:127: ^( E3 conditionalExpression )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot(adaptor.create(E3, "E3"), root_2);

                        adaptor.addChild(root_2, stream_conditionalExpression.next());

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;
                case 2 :
                    // GNUCa.g:565:157: 
                    {

                    // AST REWRITE
                    // elements: lorExpression
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 565:157: -> lorExpression
                    {
                        adaptor.addChild(root_0, stream_lorExpression.next());

                    }

                    }

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 63, conditionalExpression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end conditionalExpression

    public static class assignmentExpression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start assignmentExpression
    // GNUCa.g:568:1: assignmentExpression : conditionalExpression ( assignmentOperator assignmentExpression -> ^( ASSIGNMENT_EXPRESSION assignmentOperator conditionalExpression assignmentExpression ) | -> conditionalExpression ) ;
    public final assignmentExpression_return assignmentExpression() throws RecognitionException {
        assignmentExpression_return retval = new assignmentExpression_return();
        retval.start = input.LT(1);
        int assignmentExpression_StartIndex = input.index();
        Object root_0 = null;

        conditionalExpression_return conditionalExpression357 = null;

        assignmentOperator_return assignmentOperator358 = null;

        assignmentExpression_return assignmentExpression359 = null;


        RewriteRuleSubtreeStream stream_assignmentOperator=new RewriteRuleSubtreeStream(adaptor,"rule assignmentOperator");
        RewriteRuleSubtreeStream stream_assignmentExpression=new RewriteRuleSubtreeStream(adaptor,"rule assignmentExpression");
        RewriteRuleSubtreeStream stream_conditionalExpression=new RewriteRuleSubtreeStream(adaptor,"rule conditionalExpression");
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 64) ) { return retval; }
            // GNUCa.g:573:2: ( conditionalExpression ( assignmentOperator assignmentExpression -> ^( ASSIGNMENT_EXPRESSION assignmentOperator conditionalExpression assignmentExpression ) | -> conditionalExpression ) )
            // GNUCa.g:573:4: conditionalExpression ( assignmentOperator assignmentExpression -> ^( ASSIGNMENT_EXPRESSION assignmentOperator conditionalExpression assignmentExpression ) | -> conditionalExpression )
            {
            pushFollow(FOLLOW_conditionalExpression_in_assignmentExpression3776);
            conditionalExpression357=conditionalExpression();
            _fsp--;
            if (failed) return retval;
            if ( backtracking==0 ) stream_conditionalExpression.add(conditionalExpression357.getTree());
            // GNUCa.g:573:26: ( assignmentOperator assignmentExpression -> ^( ASSIGNMENT_EXPRESSION assignmentOperator conditionalExpression assignmentExpression ) | -> conditionalExpression )
            int alt109=2;
            int LA109_0 = input.LA(1);

            if ( (LA109_0==87||(LA109_0>=168 && LA109_0<=177)) ) {
                alt109=1;
            }
            else if ( (LA109_0==EOF||LA109_0==79||LA109_0==84||LA109_0==86||LA109_0==110||LA109_0==113||LA109_0==128) ) {
                alt109=2;
            }
            else {
                if (backtracking>0) {failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("573:26: ( assignmentOperator assignmentExpression -> ^( ASSIGNMENT_EXPRESSION assignmentOperator conditionalExpression assignmentExpression ) | -> conditionalExpression )", 109, 0, input);

                throw nvae;
            }
            switch (alt109) {
                case 1 :
                    // GNUCa.g:573:27: assignmentOperator assignmentExpression
                    {
                    pushFollow(FOLLOW_assignmentOperator_in_assignmentExpression3779);
                    assignmentOperator358=assignmentOperator();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_assignmentOperator.add(assignmentOperator358.getTree());
                    pushFollow(FOLLOW_assignmentExpression_in_assignmentExpression3781);
                    assignmentExpression359=assignmentExpression();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_assignmentExpression.add(assignmentExpression359.getTree());

                    // AST REWRITE
                    // elements: assignmentOperator, conditionalExpression, assignmentExpression
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 573:67: -> ^( ASSIGNMENT_EXPRESSION assignmentOperator conditionalExpression assignmentExpression )
                    {
                        // GNUCa.g:573:70: ^( ASSIGNMENT_EXPRESSION assignmentOperator conditionalExpression assignmentExpression )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(adaptor.create(ASSIGNMENT_EXPRESSION, "ASSIGNMENT_EXPRESSION"), root_1);

                        adaptor.addChild(root_1, stream_assignmentOperator.next());
                        adaptor.addChild(root_1, stream_conditionalExpression.next());
                        adaptor.addChild(root_1, stream_assignmentExpression.next());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;
                case 2 :
                    // GNUCa.g:573:158: 
                    {

                    // AST REWRITE
                    // elements: conditionalExpression
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 573:158: -> conditionalExpression
                    {
                        adaptor.addChild(root_0, stream_conditionalExpression.next());

                    }

                    }

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 64, assignmentExpression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end assignmentExpression

    public static class assignmentOperator_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start assignmentOperator
    // GNUCa.g:576:1: assignmentOperator : ( '=' | '*=' | '/=' | '%=' | '+=' | '-=' | '<<=' | '>>=' | '&=' | '^=' | '|=' );
    public final assignmentOperator_return assignmentOperator() throws RecognitionException {
        assignmentOperator_return retval = new assignmentOperator_return();
        retval.start = input.LT(1);
        int assignmentOperator_StartIndex = input.index();
        Object root_0 = null;

        Token set360=null;

        Object set360_tree=null;

        try {
            if ( backtracking>0 && alreadyParsedRule(input, 65) ) { return retval; }
            // GNUCa.g:577:2: ( '=' | '*=' | '/=' | '%=' | '+=' | '-=' | '<<=' | '>>=' | '&=' | '^=' | '|=' )
            // GNUCa.g:
            {
            root_0 = (Object)adaptor.nil();

            set360=(Token)input.LT(1);
            if ( input.LA(1)==87||(input.LA(1)>=168 && input.LA(1)<=177) ) {
                input.consume();
                if ( backtracking==0 ) adaptor.addChild(root_0, adaptor.create(set360));
                errorRecovery=false;failed=false;
            }
            else {
                if (backtracking>0) {failed=true; return retval;}
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recoverFromMismatchedSet(input,mse,FOLLOW_set_in_assignmentOperator0);    throw mse;
            }


            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 65, assignmentOperator_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end assignmentOperator

    public static class expression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start expression
    // GNUCa.g:590:1: expression : assignmentExpression ( ',' assignmentExpression )* ;
    public final expression_return expression() throws RecognitionException {
        expression_return retval = new expression_return();
        retval.start = input.LT(1);
        int expression_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal362=null;
        assignmentExpression_return assignmentExpression361 = null;

        assignmentExpression_return assignmentExpression363 = null;


        Object char_literal362_tree=null;

        try {
            if ( backtracking>0 && alreadyParsedRule(input, 66) ) { return retval; }
            // GNUCa.g:591:2: ( assignmentExpression ( ',' assignmentExpression )* )
            // GNUCa.g:591:4: assignmentExpression ( ',' assignmentExpression )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_assignmentExpression_in_expression3872);
            assignmentExpression361=assignmentExpression();
            _fsp--;
            if (failed) return retval;
            if ( backtracking==0 ) adaptor.addChild(root_0, assignmentExpression361.getTree());
            // GNUCa.g:591:25: ( ',' assignmentExpression )*
            loop110:
            do {
                int alt110=2;
                int LA110_0 = input.LA(1);

                if ( (LA110_0==86) ) {
                    alt110=1;
                }


                switch (alt110) {
            	case 1 :
            	    // GNUCa.g:591:26: ',' assignmentExpression
            	    {
            	    char_literal362=(Token)input.LT(1);
            	    match(input,86,FOLLOW_86_in_expression3875); if (failed) return retval;
            	    if ( backtracking==0 ) {
            	    char_literal362_tree = (Object)adaptor.create(char_literal362);
            	    root_0 = (Object)adaptor.becomeRoot(char_literal362_tree, root_0);
            	    }
            	    pushFollow(FOLLOW_assignmentExpression_in_expression3878);
            	    assignmentExpression363=assignmentExpression();
            	    _fsp--;
            	    if (failed) return retval;
            	    if ( backtracking==0 ) adaptor.addChild(root_0, assignmentExpression363.getTree());

            	    }
            	    break;

            	default :
            	    break loop110;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 66, expression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end expression

    public static class constantExpression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start constantExpression
    // GNUCa.g:594:1: constantExpression : conditionalExpression ;
    public final constantExpression_return constantExpression() throws RecognitionException {
        constantExpression_return retval = new constantExpression_return();
        retval.start = input.LT(1);
        int constantExpression_StartIndex = input.index();
        Object root_0 = null;

        conditionalExpression_return conditionalExpression364 = null;



        try {
            if ( backtracking>0 && alreadyParsedRule(input, 67) ) { return retval; }
            // GNUCa.g:595:2: ( conditionalExpression )
            // GNUCa.g:595:4: conditionalExpression
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_conditionalExpression_in_constantExpression3893);
            conditionalExpression364=conditionalExpression();
            _fsp--;
            if (failed) return retval;
            if ( backtracking==0 ) adaptor.addChild(root_0, conditionalExpression364.getTree());

            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 67, constantExpression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end constantExpression

    public static class statement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start statement
    // GNUCa.g:599:1: statement : ( labeledStatement | compoundStatement | expressionStatement | selectionStatement | iterationStatement | jumpStatement | asmStatement );
    public final statement_return statement() throws RecognitionException {
        statement_return retval = new statement_return();
        retval.start = input.LT(1);
        int statement_StartIndex = input.index();
        Object root_0 = null;

        labeledStatement_return labeledStatement365 = null;

        compoundStatement_return compoundStatement366 = null;

        expressionStatement_return expressionStatement367 = null;

        selectionStatement_return selectionStatement368 = null;

        iterationStatement_return iterationStatement369 = null;

        jumpStatement_return jumpStatement370 = null;

        asmStatement_return asmStatement371 = null;



        try {
            if ( backtracking>0 && alreadyParsedRule(input, 68) ) { return retval; }
            // GNUCa.g:600:2: ( labeledStatement | compoundStatement | expressionStatement | selectionStatement | iterationStatement | jumpStatement | asmStatement )
            int alt111=7;
            switch ( input.LA(1) ) {
            case IDENTIFIER:
                {
                int LA111_1 = input.LA(2);

                if ( (LA111_1==113) ) {
                    alt111=1;
                }
                else if ( (LA111_1==79||LA111_1==83||(LA111_1>=86 && LA111_1<=87)||LA111_1==127||LA111_1==129||LA111_1==134||(LA111_1>=138 && LA111_1<=140)||(LA111_1>=144 && LA111_1<=146)||LA111_1==149||(LA111_1>=154 && LA111_1<=177)) ) {
                    alt111=3;
                }
                else {
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("599:1: statement : ( labeledStatement | compoundStatement | expressionStatement | selectionStatement | iterationStatement | jumpStatement | asmStatement );", 111, 1, input);

                    throw nvae;
                }
                }
                break;
            case 178:
            case 179:
                {
                alt111=1;
                }
                break;
            case 109:
                {
                alt111=2;
                }
                break;
            case CONSTANT:
            case STRING_LITERAL:
            case 79:
            case 83:
            case 129:
            case 137:
            case 139:
            case 140:
            case 141:
            case 142:
            case 143:
            case 144:
            case 145:
            case 146:
            case 147:
            case 148:
            case 149:
            case 150:
            case 151:
            case 152:
            case 153:
                {
                alt111=3;
                }
                break;
            case 180:
            case 182:
                {
                alt111=4;
                }
                break;
            case 183:
            case 184:
            case 185:
                {
                alt111=5;
                }
                break;
            case 186:
            case 187:
            case 188:
            case 189:
                {
                alt111=6;
                }
                break;
            case 80:
            case 81:
            case 82:
                {
                alt111=7;
                }
                break;
            default:
                if (backtracking>0) {failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("599:1: statement : ( labeledStatement | compoundStatement | expressionStatement | selectionStatement | iterationStatement | jumpStatement | asmStatement );", 111, 0, input);

                throw nvae;
            }

            switch (alt111) {
                case 1 :
                    // GNUCa.g:600:4: labeledStatement
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_labeledStatement_in_statement3906);
                    labeledStatement365=labeledStatement();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) adaptor.addChild(root_0, labeledStatement365.getTree());

                    }
                    break;
                case 2 :
                    // GNUCa.g:601:4: compoundStatement
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_compoundStatement_in_statement3911);
                    compoundStatement366=compoundStatement();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) adaptor.addChild(root_0, compoundStatement366.getTree());

                    }
                    break;
                case 3 :
                    // GNUCa.g:602:4: expressionStatement
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_expressionStatement_in_statement3916);
                    expressionStatement367=expressionStatement();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) adaptor.addChild(root_0, expressionStatement367.getTree());

                    }
                    break;
                case 4 :
                    // GNUCa.g:603:5: selectionStatement
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_selectionStatement_in_statement3922);
                    selectionStatement368=selectionStatement();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) adaptor.addChild(root_0, selectionStatement368.getTree());

                    }
                    break;
                case 5 :
                    // GNUCa.g:604:4: iterationStatement
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_iterationStatement_in_statement3927);
                    iterationStatement369=iterationStatement();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) adaptor.addChild(root_0, iterationStatement369.getTree());

                    }
                    break;
                case 6 :
                    // GNUCa.g:605:4: jumpStatement
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_jumpStatement_in_statement3932);
                    jumpStatement370=jumpStatement();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) adaptor.addChild(root_0, jumpStatement370.getTree());

                    }
                    break;
                case 7 :
                    // GNUCa.g:606:4: asmStatement
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_asmStatement_in_statement3937);
                    asmStatement371=asmStatement();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) adaptor.addChild(root_0, asmStatement371.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 68, statement_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end statement

    public static class labeledStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start labeledStatement
    // GNUCa.g:609:1: labeledStatement : ( IDENTIFIER ':' ( attributes )? statement -> ^( LABEL IDENTIFIER ( attributes )? statement ) | 'case' constantExpression ( '...' constantExpression )? ':' statement -> ^( 'case' constantExpression statement ) | 'default' ':' statement );
    public final labeledStatement_return labeledStatement() throws RecognitionException {
        labeledStatement_return retval = new labeledStatement_return();
        retval.start = input.LT(1);
        int labeledStatement_StartIndex = input.index();
        Object root_0 = null;

        Token IDENTIFIER372=null;
        Token char_literal373=null;
        Token string_literal376=null;
        Token string_literal378=null;
        Token char_literal380=null;
        Token string_literal382=null;
        Token char_literal383=null;
        attributes_return attributes374 = null;

        statement_return statement375 = null;

        constantExpression_return constantExpression377 = null;

        constantExpression_return constantExpression379 = null;

        statement_return statement381 = null;

        statement_return statement384 = null;


        Object IDENTIFIER372_tree=null;
        Object char_literal373_tree=null;
        Object string_literal376_tree=null;
        Object string_literal378_tree=null;
        Object char_literal380_tree=null;
        Object string_literal382_tree=null;
        Object char_literal383_tree=null;
        RewriteRuleTokenStream stream_113=new RewriteRuleTokenStream(adaptor,"token 113");
        RewriteRuleTokenStream stream_178=new RewriteRuleTokenStream(adaptor,"token 178");
        RewriteRuleTokenStream stream_IDENTIFIER=new RewriteRuleTokenStream(adaptor,"token IDENTIFIER");
        RewriteRuleTokenStream stream_130=new RewriteRuleTokenStream(adaptor,"token 130");
        RewriteRuleSubtreeStream stream_statement=new RewriteRuleSubtreeStream(adaptor,"rule statement");
        RewriteRuleSubtreeStream stream_constantExpression=new RewriteRuleSubtreeStream(adaptor,"rule constantExpression");
        RewriteRuleSubtreeStream stream_attributes=new RewriteRuleSubtreeStream(adaptor,"rule attributes");
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 69) ) { return retval; }
            // GNUCa.g:610:2: ( IDENTIFIER ':' ( attributes )? statement -> ^( LABEL IDENTIFIER ( attributes )? statement ) | 'case' constantExpression ( '...' constantExpression )? ':' statement -> ^( 'case' constantExpression statement ) | 'default' ':' statement )
            int alt114=3;
            switch ( input.LA(1) ) {
            case IDENTIFIER:
                {
                alt114=1;
                }
                break;
            case 178:
                {
                alt114=2;
                }
                break;
            case 179:
                {
                alt114=3;
                }
                break;
            default:
                if (backtracking>0) {failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("609:1: labeledStatement : ( IDENTIFIER ':' ( attributes )? statement -> ^( LABEL IDENTIFIER ( attributes )? statement ) | 'case' constantExpression ( '...' constantExpression )? ':' statement -> ^( 'case' constantExpression statement ) | 'default' ':' statement );", 114, 0, input);

                throw nvae;
            }

            switch (alt114) {
                case 1 :
                    // GNUCa.g:610:4: IDENTIFIER ':' ( attributes )? statement
                    {
                    IDENTIFIER372=(Token)input.LT(1);
                    match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_labeledStatement3951); if (failed) return retval;
                    if ( backtracking==0 ) stream_IDENTIFIER.add(IDENTIFIER372);

                    char_literal373=(Token)input.LT(1);
                    match(input,113,FOLLOW_113_in_labeledStatement3953); if (failed) return retval;
                    if ( backtracking==0 ) stream_113.add(char_literal373);

                    // GNUCa.g:610:19: ( attributes )?
                    int alt112=2;
                    int LA112_0 = input.LA(1);

                    if ( ((LA112_0>=135 && LA112_0<=136)) ) {
                        alt112=1;
                    }
                    switch (alt112) {
                        case 1 :
                            // GNUCa.g:0:0: attributes
                            {
                            pushFollow(FOLLOW_attributes_in_labeledStatement3955);
                            attributes374=attributes();
                            _fsp--;
                            if (failed) return retval;
                            if ( backtracking==0 ) stream_attributes.add(attributes374.getTree());

                            }
                            break;

                    }

                    pushFollow(FOLLOW_statement_in_labeledStatement3958);
                    statement375=statement();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_statement.add(statement375.getTree());

                    // AST REWRITE
                    // elements: attributes, statement, IDENTIFIER
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 610:41: -> ^( LABEL IDENTIFIER ( attributes )? statement )
                    {
                        // GNUCa.g:610:44: ^( LABEL IDENTIFIER ( attributes )? statement )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(adaptor.create(LABEL, "LABEL"), root_1);

                        adaptor.addChild(root_1, stream_IDENTIFIER.next());
                        // GNUCa.g:610:63: ( attributes )?
                        if ( stream_attributes.hasNext() ) {
                            adaptor.addChild(root_1, stream_attributes.next());

                        }
                        stream_attributes.reset();
                        adaptor.addChild(root_1, stream_statement.next());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;
                case 2 :
                    // GNUCa.g:611:4: 'case' constantExpression ( '...' constantExpression )? ':' statement
                    {
                    string_literal376=(Token)input.LT(1);
                    match(input,178,FOLLOW_178_in_labeledStatement3976); if (failed) return retval;
                    if ( backtracking==0 ) stream_178.add(string_literal376);

                    pushFollow(FOLLOW_constantExpression_in_labeledStatement3978);
                    constantExpression377=constantExpression();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_constantExpression.add(constantExpression377.getTree());
                    // GNUCa.g:611:30: ( '...' constantExpression )?
                    int alt113=2;
                    int LA113_0 = input.LA(1);

                    if ( (LA113_0==130) ) {
                        alt113=1;
                    }
                    switch (alt113) {
                        case 1 :
                            // GNUCa.g:611:31: '...' constantExpression
                            {
                            string_literal378=(Token)input.LT(1);
                            match(input,130,FOLLOW_130_in_labeledStatement3981); if (failed) return retval;
                            if ( backtracking==0 ) stream_130.add(string_literal378);

                            pushFollow(FOLLOW_constantExpression_in_labeledStatement3983);
                            constantExpression379=constantExpression();
                            _fsp--;
                            if (failed) return retval;
                            if ( backtracking==0 ) stream_constantExpression.add(constantExpression379.getTree());

                            }
                            break;

                    }

                    char_literal380=(Token)input.LT(1);
                    match(input,113,FOLLOW_113_in_labeledStatement3987); if (failed) return retval;
                    if ( backtracking==0 ) stream_113.add(char_literal380);

                    pushFollow(FOLLOW_statement_in_labeledStatement3989);
                    statement381=statement();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_statement.add(statement381.getTree());

                    // AST REWRITE
                    // elements: constantExpression, 178, statement
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 611:72: -> ^( 'case' constantExpression statement )
                    {
                        // GNUCa.g:611:75: ^( 'case' constantExpression statement )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_178.next(), root_1);

                        adaptor.addChild(root_1, stream_constantExpression.next());
                        adaptor.addChild(root_1, stream_statement.next());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;
                case 3 :
                    // GNUCa.g:612:4: 'default' ':' statement
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal382=(Token)input.LT(1);
                    match(input,179,FOLLOW_179_in_labeledStatement4005); if (failed) return retval;
                    if ( backtracking==0 ) {
                    string_literal382_tree = (Object)adaptor.create(string_literal382);
                    root_0 = (Object)adaptor.becomeRoot(string_literal382_tree, root_0);
                    }
                    char_literal383=(Token)input.LT(1);
                    match(input,113,FOLLOW_113_in_labeledStatement4008); if (failed) return retval;
                    pushFollow(FOLLOW_statement_in_labeledStatement4011);
                    statement384=statement();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) adaptor.addChild(root_0, statement384.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 69, labeledStatement_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end labeledStatement

    public static class compoundStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start compoundStatement
    // GNUCa.g:615:1: compoundStatement : '{' (x+= declaration | x+= nestedFunctionDefinition | x+= statement )* '}' -> ^( COMPOUND_STATEMENT ( $x)* ) ;
    public final compoundStatement_return compoundStatement() throws RecognitionException {
        Symbols_stack.push(new Symbols_scope());

        compoundStatement_return retval = new compoundStatement_return();
        retval.start = input.LT(1);
        int compoundStatement_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal385=null;
        Token char_literal386=null;
        List list_x=null;
        RuleReturnScope x = null;
        Object char_literal385_tree=null;
        Object char_literal386_tree=null;
        RewriteRuleTokenStream stream_109=new RewriteRuleTokenStream(adaptor,"token 109");
        RewriteRuleTokenStream stream_110=new RewriteRuleTokenStream(adaptor,"token 110");
        RewriteRuleSubtreeStream stream_statement=new RewriteRuleSubtreeStream(adaptor,"rule statement");
        RewriteRuleSubtreeStream stream_declaration=new RewriteRuleSubtreeStream(adaptor,"rule declaration");
        RewriteRuleSubtreeStream stream_nestedFunctionDefinition=new RewriteRuleSubtreeStream(adaptor,"rule nestedFunctionDefinition");
         ((Symbols_scope)Symbols_stack.peek()).types = new HashSet(); 
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 70) ) { return retval; }
            // GNUCa.g:618:2: ( '{' (x+= declaration | x+= nestedFunctionDefinition | x+= statement )* '}' -> ^( COMPOUND_STATEMENT ( $x)* ) )
            // GNUCa.g:618:4: '{' (x+= declaration | x+= nestedFunctionDefinition | x+= statement )* '}'
            {
            char_literal385=(Token)input.LT(1);
            match(input,109,FOLLOW_109_in_compoundStatement4033); if (failed) return retval;
            if ( backtracking==0 ) stream_109.add(char_literal385);

            // GNUCa.g:618:8: (x+= declaration | x+= nestedFunctionDefinition | x+= statement )*
            loop115:
            do {
                int alt115=4;
                switch ( input.LA(1) ) {
                case 85:
                    {
                    alt115=1;
                    }
                    break;
                case 88:
                case 89:
                case 90:
                case 91:
                case 92:
                    {
                    int LA115_3 = input.LA(2);

                    if ( (synpred226()) ) {
                        alt115=1;
                    }
                    else if ( (synpred227()) ) {
                        alt115=2;
                    }


                    }
                    break;
                case 93:
                    {
                    int LA115_4 = input.LA(2);

                    if ( (synpred226()) ) {
                        alt115=1;
                    }
                    else if ( (synpred227()) ) {
                        alt115=2;
                    }


                    }
                    break;
                case 94:
                    {
                    int LA115_5 = input.LA(2);

                    if ( (synpred226()) ) {
                        alt115=1;
                    }
                    else if ( (synpred227()) ) {
                        alt115=2;
                    }


                    }
                    break;
                case 95:
                    {
                    int LA115_6 = input.LA(2);

                    if ( (synpred226()) ) {
                        alt115=1;
                    }
                    else if ( (synpred227()) ) {
                        alt115=2;
                    }


                    }
                    break;
                case 96:
                    {
                    int LA115_7 = input.LA(2);

                    if ( (synpred226()) ) {
                        alt115=1;
                    }
                    else if ( (synpred227()) ) {
                        alt115=2;
                    }


                    }
                    break;
                case 97:
                    {
                    int LA115_8 = input.LA(2);

                    if ( (synpred226()) ) {
                        alt115=1;
                    }
                    else if ( (synpred227()) ) {
                        alt115=2;
                    }


                    }
                    break;
                case 98:
                    {
                    int LA115_9 = input.LA(2);

                    if ( (synpred226()) ) {
                        alt115=1;
                    }
                    else if ( (synpred227()) ) {
                        alt115=2;
                    }


                    }
                    break;
                case 99:
                    {
                    int LA115_10 = input.LA(2);

                    if ( (synpred226()) ) {
                        alt115=1;
                    }
                    else if ( (synpred227()) ) {
                        alt115=2;
                    }


                    }
                    break;
                case 100:
                    {
                    int LA115_11 = input.LA(2);

                    if ( (synpred226()) ) {
                        alt115=1;
                    }
                    else if ( (synpred227()) ) {
                        alt115=2;
                    }


                    }
                    break;
                case 101:
                    {
                    int LA115_12 = input.LA(2);

                    if ( (synpred226()) ) {
                        alt115=1;
                    }
                    else if ( (synpred227()) ) {
                        alt115=2;
                    }


                    }
                    break;
                case 102:
                    {
                    int LA115_13 = input.LA(2);

                    if ( (synpred226()) ) {
                        alt115=1;
                    }
                    else if ( (synpred227()) ) {
                        alt115=2;
                    }


                    }
                    break;
                case 103:
                    {
                    int LA115_14 = input.LA(2);

                    if ( (synpred226()) ) {
                        alt115=1;
                    }
                    else if ( (synpred227()) ) {
                        alt115=2;
                    }


                    }
                    break;
                case 104:
                    {
                    int LA115_15 = input.LA(2);

                    if ( (synpred226()) ) {
                        alt115=1;
                    }
                    else if ( (synpred227()) ) {
                        alt115=2;
                    }


                    }
                    break;
                case 105:
                    {
                    int LA115_16 = input.LA(2);

                    if ( (synpred226()) ) {
                        alt115=1;
                    }
                    else if ( (synpred227()) ) {
                        alt115=2;
                    }


                    }
                    break;
                case 106:
                    {
                    int LA115_17 = input.LA(2);

                    if ( (synpred226()) ) {
                        alt115=1;
                    }
                    else if ( (synpred227()) ) {
                        alt115=2;
                    }


                    }
                    break;
                case 107:
                    {
                    int LA115_18 = input.LA(2);

                    if ( (synpred226()) ) {
                        alt115=1;
                    }
                    else if ( (synpred227()) ) {
                        alt115=2;
                    }


                    }
                    break;
                case 108:
                    {
                    int LA115_19 = input.LA(2);

                    if ( (synpred226()) ) {
                        alt115=1;
                    }
                    else if ( (synpred227()) ) {
                        alt115=2;
                    }


                    }
                    break;
                case 111:
                case 112:
                    {
                    int LA115_20 = input.LA(2);

                    if ( (synpred226()) ) {
                        alt115=1;
                    }
                    else if ( (synpred227()) ) {
                        alt115=2;
                    }


                    }
                    break;
                case 114:
                    {
                    int LA115_21 = input.LA(2);

                    if ( (synpred226()) ) {
                        alt115=1;
                    }
                    else if ( (synpred227()) ) {
                        alt115=2;
                    }


                    }
                    break;
                case IDENTIFIER:
                    {
                    int LA115_22 = input.LA(2);

                    if ( ((synpred226()&&isTypeName(input.LT(1).getText()))) ) {
                        alt115=1;
                    }
                    else if ( ((synpred227()&&isTypeName(input.LT(1).getText()))) ) {
                        alt115=2;
                    }
                    else if ( (synpred228()) ) {
                        alt115=3;
                    }


                    }
                    break;
                case 131:
                    {
                    int LA115_23 = input.LA(2);

                    if ( (synpred226()) ) {
                        alt115=1;
                    }
                    else if ( (synpred227()) ) {
                        alt115=2;
                    }


                    }
                    break;
                case 132:
                    {
                    int LA115_24 = input.LA(2);

                    if ( (synpred226()) ) {
                        alt115=1;
                    }
                    else if ( (synpred227()) ) {
                        alt115=2;
                    }


                    }
                    break;
                case 133:
                    {
                    int LA115_25 = input.LA(2);

                    if ( (synpred226()) ) {
                        alt115=1;
                    }
                    else if ( (synpred227()) ) {
                        alt115=2;
                    }


                    }
                    break;
                case 115:
                    {
                    int LA115_26 = input.LA(2);

                    if ( (synpred226()) ) {
                        alt115=1;
                    }
                    else if ( (synpred227()) ) {
                        alt115=2;
                    }


                    }
                    break;
                case 116:
                    {
                    int LA115_27 = input.LA(2);

                    if ( (synpred226()) ) {
                        alt115=1;
                    }
                    else if ( (synpred227()) ) {
                        alt115=2;
                    }


                    }
                    break;
                case 117:
                    {
                    int LA115_28 = input.LA(2);

                    if ( (synpred226()) ) {
                        alt115=1;
                    }
                    else if ( (synpred227()) ) {
                        alt115=2;
                    }


                    }
                    break;
                case 118:
                    {
                    int LA115_29 = input.LA(2);

                    if ( (synpred226()) ) {
                        alt115=1;
                    }
                    else if ( (synpred227()) ) {
                        alt115=2;
                    }


                    }
                    break;
                case 119:
                    {
                    int LA115_30 = input.LA(2);

                    if ( (synpred226()) ) {
                        alt115=1;
                    }
                    else if ( (synpred227()) ) {
                        alt115=2;
                    }


                    }
                    break;
                case 120:
                    {
                    int LA115_31 = input.LA(2);

                    if ( (synpred226()) ) {
                        alt115=1;
                    }
                    else if ( (synpred227()) ) {
                        alt115=2;
                    }


                    }
                    break;
                case 121:
                    {
                    int LA115_32 = input.LA(2);

                    if ( (synpred226()) ) {
                        alt115=1;
                    }
                    else if ( (synpred227()) ) {
                        alt115=2;
                    }


                    }
                    break;
                case 122:
                    {
                    int LA115_33 = input.LA(2);

                    if ( (synpred226()) ) {
                        alt115=1;
                    }
                    else if ( (synpred227()) ) {
                        alt115=2;
                    }


                    }
                    break;
                case 123:
                    {
                    int LA115_34 = input.LA(2);

                    if ( (synpred226()) ) {
                        alt115=1;
                    }
                    else if ( (synpred227()) ) {
                        alt115=2;
                    }


                    }
                    break;
                case 124:
                    {
                    int LA115_35 = input.LA(2);

                    if ( (synpred226()) ) {
                        alt115=1;
                    }
                    else if ( (synpred227()) ) {
                        alt115=2;
                    }


                    }
                    break;
                case 125:
                    {
                    int LA115_36 = input.LA(2);

                    if ( (synpred226()) ) {
                        alt115=1;
                    }
                    else if ( (synpred227()) ) {
                        alt115=2;
                    }


                    }
                    break;
                case 126:
                    {
                    int LA115_37 = input.LA(2);

                    if ( (synpred226()) ) {
                        alt115=1;
                    }
                    else if ( (synpred227()) ) {
                        alt115=2;
                    }


                    }
                    break;
                case 135:
                case 136:
                    {
                    int LA115_38 = input.LA(2);

                    if ( (synpred226()) ) {
                        alt115=1;
                    }
                    else if ( (synpred227()) ) {
                        alt115=2;
                    }


                    }
                    break;
                case CONSTANT:
                case STRING_LITERAL:
                case 79:
                case 80:
                case 81:
                case 82:
                case 83:
                case 109:
                case 129:
                case 137:
                case 139:
                case 140:
                case 141:
                case 142:
                case 143:
                case 144:
                case 145:
                case 146:
                case 147:
                case 148:
                case 149:
                case 150:
                case 151:
                case 152:
                case 153:
                case 178:
                case 179:
                case 180:
                case 182:
                case 183:
                case 184:
                case 185:
                case 186:
                case 187:
                case 188:
                case 189:
                    {
                    alt115=3;
                    }
                    break;

                }

                switch (alt115) {
            	case 1 :
            	    // GNUCa.g:618:9: x+= declaration
            	    {
            	    pushFollow(FOLLOW_declaration_in_compoundStatement4038);
            	    x=declaration();
            	    _fsp--;
            	    if (failed) return retval;
            	    if ( backtracking==0 ) stream_declaration.add(x.getTree());
            	    if (list_x==null) list_x=new ArrayList();
            	    list_x.add(x);


            	    }
            	    break;
            	case 2 :
            	    // GNUCa.g:618:26: x+= nestedFunctionDefinition
            	    {
            	    pushFollow(FOLLOW_nestedFunctionDefinition_in_compoundStatement4044);
            	    x=nestedFunctionDefinition();
            	    _fsp--;
            	    if (failed) return retval;
            	    if ( backtracking==0 ) stream_nestedFunctionDefinition.add(x.getTree());
            	    if (list_x==null) list_x=new ArrayList();
            	    list_x.add(x);


            	    }
            	    break;
            	case 3 :
            	    // GNUCa.g:618:56: x+= statement
            	    {
            	    pushFollow(FOLLOW_statement_in_compoundStatement4050);
            	    x=statement();
            	    _fsp--;
            	    if (failed) return retval;
            	    if ( backtracking==0 ) stream_statement.add(x.getTree());
            	    if (list_x==null) list_x=new ArrayList();
            	    list_x.add(x);


            	    }
            	    break;

            	default :
            	    break loop115;
                }
            } while (true);

            char_literal386=(Token)input.LT(1);
            match(input,110,FOLLOW_110_in_compoundStatement4054); if (failed) return retval;
            if ( backtracking==0 ) stream_110.add(char_literal386);


            // AST REWRITE
            // elements: x
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: x
            if ( backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_x=new RewriteRuleSubtreeStream(adaptor,"token x",list_x);
            root_0 = (Object)adaptor.nil();
            // 618:75: -> ^( COMPOUND_STATEMENT ( $x)* )
            {
                // GNUCa.g:618:78: ^( COMPOUND_STATEMENT ( $x)* )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(adaptor.create(COMPOUND_STATEMENT, "COMPOUND_STATEMENT"), root_1);

                // GNUCa.g:618:99: ( $x)*
                while ( stream_x.hasNext() ) {
                    adaptor.addChild(root_1, ((ParserRuleReturnScope)stream_x.next()).getTree());

                }
                stream_x.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            }

            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 70, compoundStatement_StartIndex); }
            Symbols_stack.pop();

        }
        return retval;
    }
    // $ANTLR end compoundStatement

    public static class expressionStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start expressionStatement
    // GNUCa.g:628:1: expressionStatement : ( expression )? ';' -> ^( EXPRESSION_STATEMENT ( expression )? ) ;
    public final expressionStatement_return expressionStatement() throws RecognitionException {
        expressionStatement_return retval = new expressionStatement_return();
        retval.start = input.LT(1);
        int expressionStatement_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal388=null;
        expression_return expression387 = null;


        Object char_literal388_tree=null;
        RewriteRuleTokenStream stream_79=new RewriteRuleTokenStream(adaptor,"token 79");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 71) ) { return retval; }
            // GNUCa.g:629:2: ( ( expression )? ';' -> ^( EXPRESSION_STATEMENT ( expression )? ) )
            // GNUCa.g:629:4: ( expression )? ';'
            {
            // GNUCa.g:629:4: ( expression )?
            int alt116=2;
            int LA116_0 = input.LA(1);

            if ( ((LA116_0>=IDENTIFIER && LA116_0<=STRING_LITERAL)||LA116_0==83||LA116_0==129||LA116_0==137||(LA116_0>=139 && LA116_0<=153)) ) {
                alt116=1;
            }
            switch (alt116) {
                case 1 :
                    // GNUCa.g:0:0: expression
                    {
                    pushFollow(FOLLOW_expression_in_expressionStatement4083);
                    expression387=expression();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_expression.add(expression387.getTree());

                    }
                    break;

            }

            char_literal388=(Token)input.LT(1);
            match(input,79,FOLLOW_79_in_expressionStatement4086); if (failed) return retval;
            if ( backtracking==0 ) stream_79.add(char_literal388);


            // AST REWRITE
            // elements: expression
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 629:20: -> ^( EXPRESSION_STATEMENT ( expression )? )
            {
                // GNUCa.g:629:23: ^( EXPRESSION_STATEMENT ( expression )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(adaptor.create(EXPRESSION_STATEMENT, "EXPRESSION_STATEMENT"), root_1);

                // GNUCa.g:629:46: ( expression )?
                if ( stream_expression.hasNext() ) {
                    adaptor.addChild(root_1, stream_expression.next());

                }
                stream_expression.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            }

            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 71, expressionStatement_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end expressionStatement

    public static class selectionStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start selectionStatement
    // GNUCa.g:632:1: selectionStatement : ( 'if' '(' expression ')' the= statement ( options {k=1; backtrack=false; } : 'else' els= statement )? -> ^( 'if' expression $the ( $els)? ) | 'switch' '(' expression ')' statement -> ^( 'switch' expression statement ) );
    public final selectionStatement_return selectionStatement() throws RecognitionException {
        selectionStatement_return retval = new selectionStatement_return();
        retval.start = input.LT(1);
        int selectionStatement_StartIndex = input.index();
        Object root_0 = null;

        Token string_literal389=null;
        Token char_literal390=null;
        Token char_literal392=null;
        Token string_literal393=null;
        Token string_literal394=null;
        Token char_literal395=null;
        Token char_literal397=null;
        statement_return the = null;

        statement_return els = null;

        expression_return expression391 = null;

        expression_return expression396 = null;

        statement_return statement398 = null;


        Object string_literal389_tree=null;
        Object char_literal390_tree=null;
        Object char_literal392_tree=null;
        Object string_literal393_tree=null;
        Object string_literal394_tree=null;
        Object char_literal395_tree=null;
        Object char_literal397_tree=null;
        RewriteRuleTokenStream stream_180=new RewriteRuleTokenStream(adaptor,"token 180");
        RewriteRuleTokenStream stream_181=new RewriteRuleTokenStream(adaptor,"token 181");
        RewriteRuleTokenStream stream_182=new RewriteRuleTokenStream(adaptor,"token 182");
        RewriteRuleTokenStream stream_83=new RewriteRuleTokenStream(adaptor,"token 83");
        RewriteRuleTokenStream stream_84=new RewriteRuleTokenStream(adaptor,"token 84");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_statement=new RewriteRuleSubtreeStream(adaptor,"rule statement");
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 72) ) { return retval; }
            // GNUCa.g:633:2: ( 'if' '(' expression ')' the= statement ( options {k=1; backtrack=false; } : 'else' els= statement )? -> ^( 'if' expression $the ( $els)? ) | 'switch' '(' expression ')' statement -> ^( 'switch' expression statement ) )
            int alt118=2;
            int LA118_0 = input.LA(1);

            if ( (LA118_0==180) ) {
                alt118=1;
            }
            else if ( (LA118_0==182) ) {
                alt118=2;
            }
            else {
                if (backtracking>0) {failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("632:1: selectionStatement : ( 'if' '(' expression ')' the= statement ( options {k=1; backtrack=false; } : 'else' els= statement )? -> ^( 'if' expression $the ( $els)? ) | 'switch' '(' expression ')' statement -> ^( 'switch' expression statement ) );", 118, 0, input);

                throw nvae;
            }
            switch (alt118) {
                case 1 :
                    // GNUCa.g:633:4: 'if' '(' expression ')' the= statement ( options {k=1; backtrack=false; } : 'else' els= statement )?
                    {
                    string_literal389=(Token)input.LT(1);
                    match(input,180,FOLLOW_180_in_selectionStatement4107); if (failed) return retval;
                    if ( backtracking==0 ) stream_180.add(string_literal389);

                    char_literal390=(Token)input.LT(1);
                    match(input,83,FOLLOW_83_in_selectionStatement4109); if (failed) return retval;
                    if ( backtracking==0 ) stream_83.add(char_literal390);

                    pushFollow(FOLLOW_expression_in_selectionStatement4111);
                    expression391=expression();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_expression.add(expression391.getTree());
                    char_literal392=(Token)input.LT(1);
                    match(input,84,FOLLOW_84_in_selectionStatement4113); if (failed) return retval;
                    if ( backtracking==0 ) stream_84.add(char_literal392);

                    pushFollow(FOLLOW_statement_in_selectionStatement4117);
                    the=statement();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_statement.add(the.getTree());
                    // GNUCa.g:633:42: ( options {k=1; backtrack=false; } : 'else' els= statement )?
                    int alt117=2;
                    int LA117_0 = input.LA(1);

                    if ( (LA117_0==181) ) {
                        alt117=1;
                    }
                    switch (alt117) {
                        case 1 :
                            // GNUCa.g:633:75: 'else' els= statement
                            {
                            string_literal393=(Token)input.LT(1);
                            match(input,181,FOLLOW_181_in_selectionStatement4132); if (failed) return retval;
                            if ( backtracking==0 ) stream_181.add(string_literal393);

                            pushFollow(FOLLOW_statement_in_selectionStatement4136);
                            els=statement();
                            _fsp--;
                            if (failed) return retval;
                            if ( backtracking==0 ) stream_statement.add(els.getTree());

                            }
                            break;

                    }


                    // AST REWRITE
                    // elements: expression, els, 180, the
                    // token labels: 
                    // rule labels: retval, the, els
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_the=new RewriteRuleSubtreeStream(adaptor,"token the",the!=null?the.tree:null);
                    RewriteRuleSubtreeStream stream_els=new RewriteRuleSubtreeStream(adaptor,"token els",els!=null?els.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 633:98: -> ^( 'if' expression $the ( $els)? )
                    {
                        // GNUCa.g:633:101: ^( 'if' expression $the ( $els)? )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_180.next(), root_1);

                        adaptor.addChild(root_1, stream_expression.next());
                        adaptor.addChild(root_1, stream_the.next());
                        // GNUCa.g:633:124: ( $els)?
                        if ( stream_els.hasNext() ) {
                            adaptor.addChild(root_1, stream_els.next());

                        }
                        stream_els.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;
                case 2 :
                    // GNUCa.g:634:4: 'switch' '(' expression ')' statement
                    {
                    string_literal394=(Token)input.LT(1);
                    match(input,182,FOLLOW_182_in_selectionStatement4158); if (failed) return retval;
                    if ( backtracking==0 ) stream_182.add(string_literal394);

                    char_literal395=(Token)input.LT(1);
                    match(input,83,FOLLOW_83_in_selectionStatement4160); if (failed) return retval;
                    if ( backtracking==0 ) stream_83.add(char_literal395);

                    pushFollow(FOLLOW_expression_in_selectionStatement4162);
                    expression396=expression();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_expression.add(expression396.getTree());
                    char_literal397=(Token)input.LT(1);
                    match(input,84,FOLLOW_84_in_selectionStatement4164); if (failed) return retval;
                    if ( backtracking==0 ) stream_84.add(char_literal397);

                    pushFollow(FOLLOW_statement_in_selectionStatement4166);
                    statement398=statement();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_statement.add(statement398.getTree());

                    // AST REWRITE
                    // elements: expression, statement, 182
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 634:42: -> ^( 'switch' expression statement )
                    {
                        // GNUCa.g:634:45: ^( 'switch' expression statement )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_182.next(), root_1);

                        adaptor.addChild(root_1, stream_expression.next());
                        adaptor.addChild(root_1, stream_statement.next());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 72, selectionStatement_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end selectionStatement

    public static class iterationStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start iterationStatement
    // GNUCa.g:637:1: iterationStatement : ( 'while' '(' expression ')' statement -> ^( 'while' expression statement ) | 'do' statement 'while' '(' expression ')' ';' -> ^( 'do' statement expression ) | 'for' '(' ( declaration | (e1= expression )? ';' ) (e2= expression )? ';' (e3= expression )? ')' statement -> ^( 'for' ( declaration )? ( ^( E1 $e1) )? ^( E2 ( $e2)? ) ^( E3 ( $e3)? ) statement ) );
    public final iterationStatement_return iterationStatement() throws RecognitionException {
        iterationStatement_return retval = new iterationStatement_return();
        retval.start = input.LT(1);
        int iterationStatement_StartIndex = input.index();
        Object root_0 = null;

        Token string_literal399=null;
        Token char_literal400=null;
        Token char_literal402=null;
        Token string_literal404=null;
        Token string_literal406=null;
        Token char_literal407=null;
        Token char_literal409=null;
        Token char_literal410=null;
        Token string_literal411=null;
        Token char_literal412=null;
        Token char_literal414=null;
        Token char_literal415=null;
        Token char_literal416=null;
        expression_return e1 = null;

        expression_return e2 = null;

        expression_return e3 = null;

        expression_return expression401 = null;

        statement_return statement403 = null;

        statement_return statement405 = null;

        expression_return expression408 = null;

        declaration_return declaration413 = null;

        statement_return statement417 = null;


        Object string_literal399_tree=null;
        Object char_literal400_tree=null;
        Object char_literal402_tree=null;
        Object string_literal404_tree=null;
        Object string_literal406_tree=null;
        Object char_literal407_tree=null;
        Object char_literal409_tree=null;
        Object char_literal410_tree=null;
        Object string_literal411_tree=null;
        Object char_literal412_tree=null;
        Object char_literal414_tree=null;
        Object char_literal415_tree=null;
        Object char_literal416_tree=null;
        RewriteRuleTokenStream stream_79=new RewriteRuleTokenStream(adaptor,"token 79");
        RewriteRuleTokenStream stream_183=new RewriteRuleTokenStream(adaptor,"token 183");
        RewriteRuleTokenStream stream_184=new RewriteRuleTokenStream(adaptor,"token 184");
        RewriteRuleTokenStream stream_185=new RewriteRuleTokenStream(adaptor,"token 185");
        RewriteRuleTokenStream stream_83=new RewriteRuleTokenStream(adaptor,"token 83");
        RewriteRuleTokenStream stream_84=new RewriteRuleTokenStream(adaptor,"token 84");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_statement=new RewriteRuleSubtreeStream(adaptor,"rule statement");
        RewriteRuleSubtreeStream stream_declaration=new RewriteRuleSubtreeStream(adaptor,"rule declaration");
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 73) ) { return retval; }
            // GNUCa.g:638:2: ( 'while' '(' expression ')' statement -> ^( 'while' expression statement ) | 'do' statement 'while' '(' expression ')' ';' -> ^( 'do' statement expression ) | 'for' '(' ( declaration | (e1= expression )? ';' ) (e2= expression )? ';' (e3= expression )? ')' statement -> ^( 'for' ( declaration )? ( ^( E1 $e1) )? ^( E2 ( $e2)? ) ^( E3 ( $e3)? ) statement ) )
            int alt123=3;
            switch ( input.LA(1) ) {
            case 183:
                {
                alt123=1;
                }
                break;
            case 184:
                {
                alt123=2;
                }
                break;
            case 185:
                {
                alt123=3;
                }
                break;
            default:
                if (backtracking>0) {failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("637:1: iterationStatement : ( 'while' '(' expression ')' statement -> ^( 'while' expression statement ) | 'do' statement 'while' '(' expression ')' ';' -> ^( 'do' statement expression ) | 'for' '(' ( declaration | (e1= expression )? ';' ) (e2= expression )? ';' (e3= expression )? ')' statement -> ^( 'for' ( declaration )? ( ^( E1 $e1) )? ^( E2 ( $e2)? ) ^( E3 ( $e3)? ) statement ) );", 123, 0, input);

                throw nvae;
            }

            switch (alt123) {
                case 1 :
                    // GNUCa.g:638:4: 'while' '(' expression ')' statement
                    {
                    string_literal399=(Token)input.LT(1);
                    match(input,183,FOLLOW_183_in_iterationStatement4188); if (failed) return retval;
                    if ( backtracking==0 ) stream_183.add(string_literal399);

                    char_literal400=(Token)input.LT(1);
                    match(input,83,FOLLOW_83_in_iterationStatement4190); if (failed) return retval;
                    if ( backtracking==0 ) stream_83.add(char_literal400);

                    pushFollow(FOLLOW_expression_in_iterationStatement4192);
                    expression401=expression();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_expression.add(expression401.getTree());
                    char_literal402=(Token)input.LT(1);
                    match(input,84,FOLLOW_84_in_iterationStatement4194); if (failed) return retval;
                    if ( backtracking==0 ) stream_84.add(char_literal402);

                    pushFollow(FOLLOW_statement_in_iterationStatement4196);
                    statement403=statement();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_statement.add(statement403.getTree());

                    // AST REWRITE
                    // elements: 183, statement, expression
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 638:41: -> ^( 'while' expression statement )
                    {
                        // GNUCa.g:638:44: ^( 'while' expression statement )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_183.next(), root_1);

                        adaptor.addChild(root_1, stream_expression.next());
                        adaptor.addChild(root_1, stream_statement.next());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;
                case 2 :
                    // GNUCa.g:639:4: 'do' statement 'while' '(' expression ')' ';'
                    {
                    string_literal404=(Token)input.LT(1);
                    match(input,184,FOLLOW_184_in_iterationStatement4211); if (failed) return retval;
                    if ( backtracking==0 ) stream_184.add(string_literal404);

                    pushFollow(FOLLOW_statement_in_iterationStatement4213);
                    statement405=statement();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_statement.add(statement405.getTree());
                    string_literal406=(Token)input.LT(1);
                    match(input,183,FOLLOW_183_in_iterationStatement4215); if (failed) return retval;
                    if ( backtracking==0 ) stream_183.add(string_literal406);

                    char_literal407=(Token)input.LT(1);
                    match(input,83,FOLLOW_83_in_iterationStatement4217); if (failed) return retval;
                    if ( backtracking==0 ) stream_83.add(char_literal407);

                    pushFollow(FOLLOW_expression_in_iterationStatement4219);
                    expression408=expression();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_expression.add(expression408.getTree());
                    char_literal409=(Token)input.LT(1);
                    match(input,84,FOLLOW_84_in_iterationStatement4221); if (failed) return retval;
                    if ( backtracking==0 ) stream_84.add(char_literal409);

                    char_literal410=(Token)input.LT(1);
                    match(input,79,FOLLOW_79_in_iterationStatement4223); if (failed) return retval;
                    if ( backtracking==0 ) stream_79.add(char_literal410);


                    // AST REWRITE
                    // elements: expression, statement, 184
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 639:50: -> ^( 'do' statement expression )
                    {
                        // GNUCa.g:639:53: ^( 'do' statement expression )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_184.next(), root_1);

                        adaptor.addChild(root_1, stream_statement.next());
                        adaptor.addChild(root_1, stream_expression.next());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;
                case 3 :
                    // GNUCa.g:640:4: 'for' '(' ( declaration | (e1= expression )? ';' ) (e2= expression )? ';' (e3= expression )? ')' statement
                    {
                    string_literal411=(Token)input.LT(1);
                    match(input,185,FOLLOW_185_in_iterationStatement4238); if (failed) return retval;
                    if ( backtracking==0 ) stream_185.add(string_literal411);

                    char_literal412=(Token)input.LT(1);
                    match(input,83,FOLLOW_83_in_iterationStatement4240); if (failed) return retval;
                    if ( backtracking==0 ) stream_83.add(char_literal412);

                    // GNUCa.g:640:14: ( declaration | (e1= expression )? ';' )
                    int alt120=2;
                    switch ( input.LA(1) ) {
                    case 85:
                    case 88:
                    case 89:
                    case 90:
                    case 91:
                    case 92:
                    case 93:
                    case 94:
                    case 95:
                    case 96:
                    case 97:
                    case 98:
                    case 99:
                    case 100:
                    case 101:
                    case 102:
                    case 103:
                    case 104:
                    case 105:
                    case 106:
                    case 107:
                    case 108:
                    case 111:
                    case 112:
                    case 114:
                    case 115:
                    case 116:
                    case 117:
                    case 118:
                    case 119:
                    case 120:
                    case 121:
                    case 122:
                    case 123:
                    case 124:
                    case 125:
                    case 126:
                    case 131:
                    case 132:
                    case 133:
                    case 135:
                    case 136:
                        {
                        alt120=1;
                        }
                        break;
                    case IDENTIFIER:
                        {
                        switch ( input.LA(2) ) {
                        case 129:
                            {
                            int LA120_59 = input.LA(3);

                            if ( ((synpred233()&&isTypeName(input.LT(1).getText()))) ) {
                                alt120=1;
                            }
                            else if ( (true) ) {
                                alt120=2;
                            }
                            else {
                                if (backtracking>0) {failed=true; return retval;}
                                NoViableAltException nvae =
                                    new NoViableAltException("640:14: ( declaration | (e1= expression )? ';' )", 120, 59, input);

                                throw nvae;
                            }
                            }
                            break;
                        case IDENTIFIER:
                        case 88:
                        case 89:
                        case 90:
                        case 91:
                        case 92:
                        case 93:
                        case 94:
                        case 95:
                        case 96:
                        case 97:
                        case 98:
                        case 99:
                        case 100:
                        case 101:
                        case 102:
                        case 103:
                        case 104:
                        case 105:
                        case 106:
                        case 107:
                        case 108:
                        case 111:
                        case 112:
                        case 114:
                        case 115:
                        case 116:
                        case 117:
                        case 118:
                        case 119:
                        case 120:
                        case 121:
                        case 122:
                        case 123:
                        case 124:
                        case 125:
                        case 126:
                        case 131:
                        case 132:
                        case 133:
                        case 135:
                        case 136:
                            {
                            alt120=1;
                            }
                            break;
                        case 83:
                            {
                            int LA120_61 = input.LA(3);

                            if ( ((synpred233()&&isTypeName(input.LT(1).getText()))) ) {
                                alt120=1;
                            }
                            else if ( (true) ) {
                                alt120=2;
                            }
                            else {
                                if (backtracking>0) {failed=true; return retval;}
                                NoViableAltException nvae =
                                    new NoViableAltException("640:14: ( declaration | (e1= expression )? ';' )", 120, 61, input);

                                throw nvae;
                            }
                            }
                            break;
                        case 79:
                            {
                            int LA120_62 = input.LA(3);

                            if ( ((synpred233()&&isTypeName(input.LT(1).getText()))) ) {
                                alt120=1;
                            }
                            else if ( (true) ) {
                                alt120=2;
                            }
                            else {
                                if (backtracking>0) {failed=true; return retval;}
                                NoViableAltException nvae =
                                    new NoViableAltException("640:14: ( declaration | (e1= expression )? ';' )", 120, 62, input);

                                throw nvae;
                            }
                            }
                            break;
                        case 86:
                        case 87:
                        case 127:
                        case 134:
                        case 138:
                        case 139:
                        case 140:
                        case 144:
                        case 145:
                        case 146:
                        case 149:
                        case 154:
                        case 155:
                        case 156:
                        case 157:
                        case 158:
                        case 159:
                        case 160:
                        case 161:
                        case 162:
                        case 163:
                        case 164:
                        case 165:
                        case 166:
                        case 167:
                        case 168:
                        case 169:
                        case 170:
                        case 171:
                        case 172:
                        case 173:
                        case 174:
                        case 175:
                        case 176:
                        case 177:
                            {
                            alt120=2;
                            }
                            break;
                        default:
                            if (backtracking>0) {failed=true; return retval;}
                            NoViableAltException nvae =
                                new NoViableAltException("640:14: ( declaration | (e1= expression )? ';' )", 120, 21, input);

                            throw nvae;
                        }

                        }
                        break;
                    case CONSTANT:
                    case STRING_LITERAL:
                    case 79:
                    case 83:
                    case 129:
                    case 137:
                    case 139:
                    case 140:
                    case 141:
                    case 142:
                    case 143:
                    case 144:
                    case 145:
                    case 146:
                    case 147:
                    case 148:
                    case 149:
                    case 150:
                    case 151:
                    case 152:
                    case 153:
                        {
                        alt120=2;
                        }
                        break;
                    default:
                        if (backtracking>0) {failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("640:14: ( declaration | (e1= expression )? ';' )", 120, 0, input);

                        throw nvae;
                    }

                    switch (alt120) {
                        case 1 :
                            // GNUCa.g:640:15: declaration
                            {
                            pushFollow(FOLLOW_declaration_in_iterationStatement4243);
                            declaration413=declaration();
                            _fsp--;
                            if (failed) return retval;
                            if ( backtracking==0 ) stream_declaration.add(declaration413.getTree());

                            }
                            break;
                        case 2 :
                            // GNUCa.g:640:29: (e1= expression )? ';'
                            {
                            // GNUCa.g:640:31: (e1= expression )?
                            int alt119=2;
                            int LA119_0 = input.LA(1);

                            if ( ((LA119_0>=IDENTIFIER && LA119_0<=STRING_LITERAL)||LA119_0==83||LA119_0==129||LA119_0==137||(LA119_0>=139 && LA119_0<=153)) ) {
                                alt119=1;
                            }
                            switch (alt119) {
                                case 1 :
                                    // GNUCa.g:0:0: e1= expression
                                    {
                                    pushFollow(FOLLOW_expression_in_iterationStatement4249);
                                    e1=expression();
                                    _fsp--;
                                    if (failed) return retval;
                                    if ( backtracking==0 ) stream_expression.add(e1.getTree());

                                    }
                                    break;

                            }

                            char_literal414=(Token)input.LT(1);
                            match(input,79,FOLLOW_79_in_iterationStatement4252); if (failed) return retval;
                            if ( backtracking==0 ) stream_79.add(char_literal414);


                            }
                            break;

                    }

                    // GNUCa.g:640:51: (e2= expression )?
                    int alt121=2;
                    int LA121_0 = input.LA(1);

                    if ( ((LA121_0>=IDENTIFIER && LA121_0<=STRING_LITERAL)||LA121_0==83||LA121_0==129||LA121_0==137||(LA121_0>=139 && LA121_0<=153)) ) {
                        alt121=1;
                    }
                    switch (alt121) {
                        case 1 :
                            // GNUCa.g:0:0: e2= expression
                            {
                            pushFollow(FOLLOW_expression_in_iterationStatement4257);
                            e2=expression();
                            _fsp--;
                            if (failed) return retval;
                            if ( backtracking==0 ) stream_expression.add(e2.getTree());

                            }
                            break;

                    }

                    char_literal415=(Token)input.LT(1);
                    match(input,79,FOLLOW_79_in_iterationStatement4260); if (failed) return retval;
                    if ( backtracking==0 ) stream_79.add(char_literal415);

                    // GNUCa.g:640:70: (e3= expression )?
                    int alt122=2;
                    int LA122_0 = input.LA(1);

                    if ( ((LA122_0>=IDENTIFIER && LA122_0<=STRING_LITERAL)||LA122_0==83||LA122_0==129||LA122_0==137||(LA122_0>=139 && LA122_0<=153)) ) {
                        alt122=1;
                    }
                    switch (alt122) {
                        case 1 :
                            // GNUCa.g:0:0: e3= expression
                            {
                            pushFollow(FOLLOW_expression_in_iterationStatement4264);
                            e3=expression();
                            _fsp--;
                            if (failed) return retval;
                            if ( backtracking==0 ) stream_expression.add(e3.getTree());

                            }
                            break;

                    }

                    char_literal416=(Token)input.LT(1);
                    match(input,84,FOLLOW_84_in_iterationStatement4267); if (failed) return retval;
                    if ( backtracking==0 ) stream_84.add(char_literal416);

                    pushFollow(FOLLOW_statement_in_iterationStatement4269);
                    statement417=statement();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_statement.add(statement417.getTree());

                    // AST REWRITE
                    // elements: statement, 185, e1, e2, e3, declaration
                    // token labels: 
                    // rule labels: e3, retval, e1, e2
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_e3=new RewriteRuleSubtreeStream(adaptor,"token e3",e3!=null?e3.tree:null);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_e1=new RewriteRuleSubtreeStream(adaptor,"token e1",e1!=null?e1.tree:null);
                    RewriteRuleSubtreeStream stream_e2=new RewriteRuleSubtreeStream(adaptor,"token e2",e2!=null?e2.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 640:97: -> ^( 'for' ( declaration )? ( ^( E1 $e1) )? ^( E2 ( $e2)? ) ^( E3 ( $e3)? ) statement )
                    {
                        // GNUCa.g:640:100: ^( 'for' ( declaration )? ( ^( E1 $e1) )? ^( E2 ( $e2)? ) ^( E3 ( $e3)? ) statement )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_185.next(), root_1);

                        // GNUCa.g:640:108: ( declaration )?
                        if ( stream_declaration.hasNext() ) {
                            adaptor.addChild(root_1, stream_declaration.next());

                        }
                        stream_declaration.reset();
                        // GNUCa.g:640:121: ( ^( E1 $e1) )?
                        if ( stream_e1.hasNext() ) {
                            // GNUCa.g:640:121: ^( E1 $e1)
                            {
                            Object root_2 = (Object)adaptor.nil();
                            root_2 = (Object)adaptor.becomeRoot(adaptor.create(E1, "E1"), root_2);

                            adaptor.addChild(root_2, stream_e1.next());

                            adaptor.addChild(root_1, root_2);
                            }

                        }
                        stream_e1.reset();
                        // GNUCa.g:640:132: ^( E2 ( $e2)? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot(adaptor.create(E2, "E2"), root_2);

                        // GNUCa.g:640:137: ( $e2)?
                        if ( stream_e2.hasNext() ) {
                            adaptor.addChild(root_2, stream_e2.next());

                        }
                        stream_e2.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // GNUCa.g:640:143: ^( E3 ( $e3)? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot(adaptor.create(E3, "E3"), root_2);

                        // GNUCa.g:640:148: ( $e3)?
                        if ( stream_e3.hasNext() ) {
                            adaptor.addChild(root_2, stream_e3.next());

                        }
                        stream_e3.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        adaptor.addChild(root_1, stream_statement.next());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 73, iterationStatement_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end iterationStatement

    public static class jumpStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start jumpStatement
    // GNUCa.g:644:1: jumpStatement : ( 'goto' IDENTIFIER ';' | 'goto' '*' expression ';' -> ^( 'goto' XU expression ) | 'continue' ';' | 'break' ';' | 'return' ( expression )? ';' );
    public final jumpStatement_return jumpStatement() throws RecognitionException {
        jumpStatement_return retval = new jumpStatement_return();
        retval.start = input.LT(1);
        int jumpStatement_StartIndex = input.index();
        Object root_0 = null;

        Token string_literal418=null;
        Token IDENTIFIER419=null;
        Token char_literal420=null;
        Token string_literal421=null;
        Token char_literal422=null;
        Token char_literal424=null;
        Token string_literal425=null;
        Token char_literal426=null;
        Token string_literal427=null;
        Token char_literal428=null;
        Token string_literal429=null;
        Token char_literal431=null;
        expression_return expression423 = null;

        expression_return expression430 = null;


        Object string_literal418_tree=null;
        Object IDENTIFIER419_tree=null;
        Object char_literal420_tree=null;
        Object string_literal421_tree=null;
        Object char_literal422_tree=null;
        Object char_literal424_tree=null;
        Object string_literal425_tree=null;
        Object char_literal426_tree=null;
        Object string_literal427_tree=null;
        Object char_literal428_tree=null;
        Object string_literal429_tree=null;
        Object char_literal431_tree=null;
        RewriteRuleTokenStream stream_79=new RewriteRuleTokenStream(adaptor,"token 79");
        RewriteRuleTokenStream stream_186=new RewriteRuleTokenStream(adaptor,"token 186");
        RewriteRuleTokenStream stream_129=new RewriteRuleTokenStream(adaptor,"token 129");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 74) ) { return retval; }
            // GNUCa.g:645:2: ( 'goto' IDENTIFIER ';' | 'goto' '*' expression ';' -> ^( 'goto' XU expression ) | 'continue' ';' | 'break' ';' | 'return' ( expression )? ';' )
            int alt125=5;
            switch ( input.LA(1) ) {
            case 186:
                {
                int LA125_1 = input.LA(2);

                if ( (LA125_1==129) ) {
                    alt125=2;
                }
                else if ( (LA125_1==IDENTIFIER) ) {
                    alt125=1;
                }
                else {
                    if (backtracking>0) {failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("644:1: jumpStatement : ( 'goto' IDENTIFIER ';' | 'goto' '*' expression ';' -> ^( 'goto' XU expression ) | 'continue' ';' | 'break' ';' | 'return' ( expression )? ';' );", 125, 1, input);

                    throw nvae;
                }
                }
                break;
            case 187:
                {
                alt125=3;
                }
                break;
            case 188:
                {
                alt125=4;
                }
                break;
            case 189:
                {
                alt125=5;
                }
                break;
            default:
                if (backtracking>0) {failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("644:1: jumpStatement : ( 'goto' IDENTIFIER ';' | 'goto' '*' expression ';' -> ^( 'goto' XU expression ) | 'continue' ';' | 'break' ';' | 'return' ( expression )? ';' );", 125, 0, input);

                throw nvae;
            }

            switch (alt125) {
                case 1 :
                    // GNUCa.g:645:4: 'goto' IDENTIFIER ';'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal418=(Token)input.LT(1);
                    match(input,186,FOLLOW_186_in_jumpStatement4317); if (failed) return retval;
                    if ( backtracking==0 ) {
                    string_literal418_tree = (Object)adaptor.create(string_literal418);
                    root_0 = (Object)adaptor.becomeRoot(string_literal418_tree, root_0);
                    }
                    IDENTIFIER419=(Token)input.LT(1);
                    match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_jumpStatement4320); if (failed) return retval;
                    if ( backtracking==0 ) {
                    IDENTIFIER419_tree = (Object)adaptor.create(IDENTIFIER419);
                    adaptor.addChild(root_0, IDENTIFIER419_tree);
                    }
                    char_literal420=(Token)input.LT(1);
                    match(input,79,FOLLOW_79_in_jumpStatement4322); if (failed) return retval;

                    }
                    break;
                case 2 :
                    // GNUCa.g:646:4: 'goto' '*' expression ';'
                    {
                    string_literal421=(Token)input.LT(1);
                    match(input,186,FOLLOW_186_in_jumpStatement4328); if (failed) return retval;
                    if ( backtracking==0 ) stream_186.add(string_literal421);

                    char_literal422=(Token)input.LT(1);
                    match(input,129,FOLLOW_129_in_jumpStatement4330); if (failed) return retval;
                    if ( backtracking==0 ) stream_129.add(char_literal422);

                    pushFollow(FOLLOW_expression_in_jumpStatement4332);
                    expression423=expression();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_expression.add(expression423.getTree());
                    char_literal424=(Token)input.LT(1);
                    match(input,79,FOLLOW_79_in_jumpStatement4334); if (failed) return retval;
                    if ( backtracking==0 ) stream_79.add(char_literal424);


                    // AST REWRITE
                    // elements: 186, expression
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    if ( backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 646:30: -> ^( 'goto' XU expression )
                    {
                        // GNUCa.g:646:33: ^( 'goto' XU expression )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_186.next(), root_1);

                        adaptor.addChild(root_1, adaptor.create(XU, "XU"));
                        adaptor.addChild(root_1, stream_expression.next());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    }

                    }
                    break;
                case 3 :
                    // GNUCa.g:647:4: 'continue' ';'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal425=(Token)input.LT(1);
                    match(input,187,FOLLOW_187_in_jumpStatement4349); if (failed) return retval;
                    if ( backtracking==0 ) {
                    string_literal425_tree = (Object)adaptor.create(string_literal425);
                    adaptor.addChild(root_0, string_literal425_tree);
                    }
                    char_literal426=(Token)input.LT(1);
                    match(input,79,FOLLOW_79_in_jumpStatement4351); if (failed) return retval;

                    }
                    break;
                case 4 :
                    // GNUCa.g:648:4: 'break' ';'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal427=(Token)input.LT(1);
                    match(input,188,FOLLOW_188_in_jumpStatement4357); if (failed) return retval;
                    if ( backtracking==0 ) {
                    string_literal427_tree = (Object)adaptor.create(string_literal427);
                    adaptor.addChild(root_0, string_literal427_tree);
                    }
                    char_literal428=(Token)input.LT(1);
                    match(input,79,FOLLOW_79_in_jumpStatement4359); if (failed) return retval;

                    }
                    break;
                case 5 :
                    // GNUCa.g:649:4: 'return' ( expression )? ';'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal429=(Token)input.LT(1);
                    match(input,189,FOLLOW_189_in_jumpStatement4365); if (failed) return retval;
                    if ( backtracking==0 ) {
                    string_literal429_tree = (Object)adaptor.create(string_literal429);
                    root_0 = (Object)adaptor.becomeRoot(string_literal429_tree, root_0);
                    }
                    // GNUCa.g:649:14: ( expression )?
                    int alt124=2;
                    int LA124_0 = input.LA(1);

                    if ( ((LA124_0>=IDENTIFIER && LA124_0<=STRING_LITERAL)||LA124_0==83||LA124_0==129||LA124_0==137||(LA124_0>=139 && LA124_0<=153)) ) {
                        alt124=1;
                    }
                    switch (alt124) {
                        case 1 :
                            // GNUCa.g:0:0: expression
                            {
                            pushFollow(FOLLOW_expression_in_jumpStatement4368);
                            expression430=expression();
                            _fsp--;
                            if (failed) return retval;
                            if ( backtracking==0 ) adaptor.addChild(root_0, expression430.getTree());

                            }
                            break;

                    }

                    char_literal431=(Token)input.LT(1);
                    match(input,79,FOLLOW_79_in_jumpStatement4371); if (failed) return retval;

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 74, jumpStatement_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end jumpStatement

    public static class asmStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start asmStatement
    // GNUCa.g:652:1: asmStatement : ( 'asm' | '__asm' | '__asm__' ) ( typeQualifier )? '(' asmArgument ')' ';' -> ^( ASM ) ;
    public final asmStatement_return asmStatement() throws RecognitionException {
        asmStatement_return retval = new asmStatement_return();
        retval.start = input.LT(1);
        int asmStatement_StartIndex = input.index();
        Object root_0 = null;

        Token string_literal432=null;
        Token string_literal433=null;
        Token string_literal434=null;
        Token char_literal436=null;
        Token char_literal438=null;
        Token char_literal439=null;
        typeQualifier_return typeQualifier435 = null;

        asmArgument_return asmArgument437 = null;


        Object string_literal432_tree=null;
        Object string_literal433_tree=null;
        Object string_literal434_tree=null;
        Object char_literal436_tree=null;
        Object char_literal438_tree=null;
        Object char_literal439_tree=null;
        RewriteRuleTokenStream stream_79=new RewriteRuleTokenStream(adaptor,"token 79");
        RewriteRuleTokenStream stream_82=new RewriteRuleTokenStream(adaptor,"token 82");
        RewriteRuleTokenStream stream_83=new RewriteRuleTokenStream(adaptor,"token 83");
        RewriteRuleTokenStream stream_80=new RewriteRuleTokenStream(adaptor,"token 80");
        RewriteRuleTokenStream stream_81=new RewriteRuleTokenStream(adaptor,"token 81");
        RewriteRuleTokenStream stream_84=new RewriteRuleTokenStream(adaptor,"token 84");
        RewriteRuleSubtreeStream stream_asmArgument=new RewriteRuleSubtreeStream(adaptor,"rule asmArgument");
        RewriteRuleSubtreeStream stream_typeQualifier=new RewriteRuleSubtreeStream(adaptor,"rule typeQualifier");
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 75) ) { return retval; }
            // GNUCa.g:653:2: ( ( 'asm' | '__asm' | '__asm__' ) ( typeQualifier )? '(' asmArgument ')' ';' -> ^( ASM ) )
            // GNUCa.g:653:4: ( 'asm' | '__asm' | '__asm__' ) ( typeQualifier )? '(' asmArgument ')' ';'
            {
            // GNUCa.g:653:4: ( 'asm' | '__asm' | '__asm__' )
            int alt126=3;
            switch ( input.LA(1) ) {
            case 80:
                {
                alt126=1;
                }
                break;
            case 81:
                {
                alt126=2;
                }
                break;
            case 82:
                {
                alt126=3;
                }
                break;
            default:
                if (backtracking>0) {failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("653:4: ( 'asm' | '__asm' | '__asm__' )", 126, 0, input);

                throw nvae;
            }

            switch (alt126) {
                case 1 :
                    // GNUCa.g:653:5: 'asm'
                    {
                    string_literal432=(Token)input.LT(1);
                    match(input,80,FOLLOW_80_in_asmStatement4386); if (failed) return retval;
                    if ( backtracking==0 ) stream_80.add(string_literal432);


                    }
                    break;
                case 2 :
                    // GNUCa.g:653:11: '__asm'
                    {
                    string_literal433=(Token)input.LT(1);
                    match(input,81,FOLLOW_81_in_asmStatement4388); if (failed) return retval;
                    if ( backtracking==0 ) stream_81.add(string_literal433);


                    }
                    break;
                case 3 :
                    // GNUCa.g:653:19: '__asm__'
                    {
                    string_literal434=(Token)input.LT(1);
                    match(input,82,FOLLOW_82_in_asmStatement4390); if (failed) return retval;
                    if ( backtracking==0 ) stream_82.add(string_literal434);


                    }
                    break;

            }

            // GNUCa.g:653:30: ( typeQualifier )?
            int alt127=2;
            int LA127_0 = input.LA(1);

            if ( ((LA127_0>=115 && LA127_0<=123)) ) {
                alt127=1;
            }
            switch (alt127) {
                case 1 :
                    // GNUCa.g:0:0: typeQualifier
                    {
                    pushFollow(FOLLOW_typeQualifier_in_asmStatement4393);
                    typeQualifier435=typeQualifier();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) stream_typeQualifier.add(typeQualifier435.getTree());

                    }
                    break;

            }

            char_literal436=(Token)input.LT(1);
            match(input,83,FOLLOW_83_in_asmStatement4396); if (failed) return retval;
            if ( backtracking==0 ) stream_83.add(char_literal436);

            pushFollow(FOLLOW_asmArgument_in_asmStatement4398);
            asmArgument437=asmArgument();
            _fsp--;
            if (failed) return retval;
            if ( backtracking==0 ) stream_asmArgument.add(asmArgument437.getTree());
            char_literal438=(Token)input.LT(1);
            match(input,84,FOLLOW_84_in_asmStatement4400); if (failed) return retval;
            if ( backtracking==0 ) stream_84.add(char_literal438);

            char_literal439=(Token)input.LT(1);
            match(input,79,FOLLOW_79_in_asmStatement4402); if (failed) return retval;
            if ( backtracking==0 ) stream_79.add(char_literal439);


            // AST REWRITE
            // elements: 
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 653:69: -> ^( ASM )
            {
                // GNUCa.g:653:72: ^( ASM )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(adaptor.create(ASM, "ASM"), root_1);

                adaptor.addChild(root_0, root_1);
                }

            }

            }

            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 75, asmStatement_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end asmStatement

    public static class asmArgument_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start asmArgument
    // GNUCa.g:656:1: asmArgument : asmStringLiteral ( ':' ( asmOperands )? ( ':' ( asmOperands )? ( ':' asmClobbers )? )? )? ;
    public final asmArgument_return asmArgument() throws RecognitionException {
        asmArgument_return retval = new asmArgument_return();
        retval.start = input.LT(1);
        int asmArgument_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal441=null;
        Token char_literal443=null;
        Token char_literal445=null;
        asmStringLiteral_return asmStringLiteral440 = null;

        asmOperands_return asmOperands442 = null;

        asmOperands_return asmOperands444 = null;

        asmClobbers_return asmClobbers446 = null;


        Object char_literal441_tree=null;
        Object char_literal443_tree=null;
        Object char_literal445_tree=null;

        try {
            if ( backtracking>0 && alreadyParsedRule(input, 76) ) { return retval; }
            // GNUCa.g:657:2: ( asmStringLiteral ( ':' ( asmOperands )? ( ':' ( asmOperands )? ( ':' asmClobbers )? )? )? )
            // GNUCa.g:657:4: asmStringLiteral ( ':' ( asmOperands )? ( ':' ( asmOperands )? ( ':' asmClobbers )? )? )?
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_asmStringLiteral_in_asmArgument4421);
            asmStringLiteral440=asmStringLiteral();
            _fsp--;
            if (failed) return retval;
            if ( backtracking==0 ) adaptor.addChild(root_0, asmStringLiteral440.getTree());
            // GNUCa.g:657:21: ( ':' ( asmOperands )? ( ':' ( asmOperands )? ( ':' asmClobbers )? )? )?
            int alt132=2;
            int LA132_0 = input.LA(1);

            if ( (LA132_0==113) ) {
                alt132=1;
            }
            switch (alt132) {
                case 1 :
                    // GNUCa.g:657:22: ':' ( asmOperands )? ( ':' ( asmOperands )? ( ':' asmClobbers )? )?
                    {
                    char_literal441=(Token)input.LT(1);
                    match(input,113,FOLLOW_113_in_asmArgument4424); if (failed) return retval;
                    if ( backtracking==0 ) {
                    char_literal441_tree = (Object)adaptor.create(char_literal441);
                    adaptor.addChild(root_0, char_literal441_tree);
                    }
                    // GNUCa.g:657:26: ( asmOperands )?
                    int alt128=2;
                    int LA128_0 = input.LA(1);

                    if ( (LA128_0==STRING_LITERAL||LA128_0==127) ) {
                        alt128=1;
                    }
                    switch (alt128) {
                        case 1 :
                            // GNUCa.g:0:0: asmOperands
                            {
                            pushFollow(FOLLOW_asmOperands_in_asmArgument4426);
                            asmOperands442=asmOperands();
                            _fsp--;
                            if (failed) return retval;
                            if ( backtracking==0 ) adaptor.addChild(root_0, asmOperands442.getTree());

                            }
                            break;

                    }

                    // GNUCa.g:657:39: ( ':' ( asmOperands )? ( ':' asmClobbers )? )?
                    int alt131=2;
                    int LA131_0 = input.LA(1);

                    if ( (LA131_0==113) ) {
                        alt131=1;
                    }
                    switch (alt131) {
                        case 1 :
                            // GNUCa.g:657:40: ':' ( asmOperands )? ( ':' asmClobbers )?
                            {
                            char_literal443=(Token)input.LT(1);
                            match(input,113,FOLLOW_113_in_asmArgument4430); if (failed) return retval;
                            if ( backtracking==0 ) {
                            char_literal443_tree = (Object)adaptor.create(char_literal443);
                            adaptor.addChild(root_0, char_literal443_tree);
                            }
                            // GNUCa.g:657:44: ( asmOperands )?
                            int alt129=2;
                            int LA129_0 = input.LA(1);

                            if ( (LA129_0==STRING_LITERAL||LA129_0==127) ) {
                                alt129=1;
                            }
                            switch (alt129) {
                                case 1 :
                                    // GNUCa.g:0:0: asmOperands
                                    {
                                    pushFollow(FOLLOW_asmOperands_in_asmArgument4432);
                                    asmOperands444=asmOperands();
                                    _fsp--;
                                    if (failed) return retval;
                                    if ( backtracking==0 ) adaptor.addChild(root_0, asmOperands444.getTree());

                                    }
                                    break;

                            }

                            // GNUCa.g:657:57: ( ':' asmClobbers )?
                            int alt130=2;
                            int LA130_0 = input.LA(1);

                            if ( (LA130_0==113) ) {
                                alt130=1;
                            }
                            switch (alt130) {
                                case 1 :
                                    // GNUCa.g:657:58: ':' asmClobbers
                                    {
                                    char_literal445=(Token)input.LT(1);
                                    match(input,113,FOLLOW_113_in_asmArgument4436); if (failed) return retval;
                                    if ( backtracking==0 ) {
                                    char_literal445_tree = (Object)adaptor.create(char_literal445);
                                    adaptor.addChild(root_0, char_literal445_tree);
                                    }
                                    pushFollow(FOLLOW_asmClobbers_in_asmArgument4438);
                                    asmClobbers446=asmClobbers();
                                    _fsp--;
                                    if (failed) return retval;
                                    if ( backtracking==0 ) adaptor.addChild(root_0, asmClobbers446.getTree());

                                    }
                                    break;

                            }


                            }
                            break;

                    }


                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 76, asmArgument_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end asmArgument

    public static class asmOperands_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start asmOperands
    // GNUCa.g:660:1: asmOperands : asmOperand ( ',' asmOperand )* ;
    public final asmOperands_return asmOperands() throws RecognitionException {
        asmOperands_return retval = new asmOperands_return();
        retval.start = input.LT(1);
        int asmOperands_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal448=null;
        asmOperand_return asmOperand447 = null;

        asmOperand_return asmOperand449 = null;


        Object char_literal448_tree=null;

        try {
            if ( backtracking>0 && alreadyParsedRule(input, 77) ) { return retval; }
            // GNUCa.g:661:2: ( asmOperand ( ',' asmOperand )* )
            // GNUCa.g:661:4: asmOperand ( ',' asmOperand )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_asmOperand_in_asmOperands4457);
            asmOperand447=asmOperand();
            _fsp--;
            if (failed) return retval;
            if ( backtracking==0 ) adaptor.addChild(root_0, asmOperand447.getTree());
            // GNUCa.g:661:15: ( ',' asmOperand )*
            loop133:
            do {
                int alt133=2;
                int LA133_0 = input.LA(1);

                if ( (LA133_0==86) ) {
                    alt133=1;
                }


                switch (alt133) {
            	case 1 :
            	    // GNUCa.g:661:16: ',' asmOperand
            	    {
            	    char_literal448=(Token)input.LT(1);
            	    match(input,86,FOLLOW_86_in_asmOperands4460); if (failed) return retval;
            	    if ( backtracking==0 ) {
            	    char_literal448_tree = (Object)adaptor.create(char_literal448);
            	    adaptor.addChild(root_0, char_literal448_tree);
            	    }
            	    pushFollow(FOLLOW_asmOperand_in_asmOperands4462);
            	    asmOperand449=asmOperand();
            	    _fsp--;
            	    if (failed) return retval;
            	    if ( backtracking==0 ) adaptor.addChild(root_0, asmOperand449.getTree());

            	    }
            	    break;

            	default :
            	    break loop133;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 77, asmOperands_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end asmOperands

    public static class asmOperand_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start asmOperand
    // GNUCa.g:664:1: asmOperand : ( asmStringLiteral '(' expression ')' | '[' IDENTIFIER ']' asmStringLiteral '(' expression ')' );
    public final asmOperand_return asmOperand() throws RecognitionException {
        asmOperand_return retval = new asmOperand_return();
        retval.start = input.LT(1);
        int asmOperand_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal451=null;
        Token char_literal453=null;
        Token char_literal454=null;
        Token IDENTIFIER455=null;
        Token char_literal456=null;
        Token char_literal458=null;
        Token char_literal460=null;
        asmStringLiteral_return asmStringLiteral450 = null;

        expression_return expression452 = null;

        asmStringLiteral_return asmStringLiteral457 = null;

        expression_return expression459 = null;


        Object char_literal451_tree=null;
        Object char_literal453_tree=null;
        Object char_literal454_tree=null;
        Object IDENTIFIER455_tree=null;
        Object char_literal456_tree=null;
        Object char_literal458_tree=null;
        Object char_literal460_tree=null;

        try {
            if ( backtracking>0 && alreadyParsedRule(input, 78) ) { return retval; }
            // GNUCa.g:665:2: ( asmStringLiteral '(' expression ')' | '[' IDENTIFIER ']' asmStringLiteral '(' expression ')' )
            int alt134=2;
            int LA134_0 = input.LA(1);

            if ( (LA134_0==STRING_LITERAL) ) {
                alt134=1;
            }
            else if ( (LA134_0==127) ) {
                alt134=2;
            }
            else {
                if (backtracking>0) {failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("664:1: asmOperand : ( asmStringLiteral '(' expression ')' | '[' IDENTIFIER ']' asmStringLiteral '(' expression ')' );", 134, 0, input);

                throw nvae;
            }
            switch (alt134) {
                case 1 :
                    // GNUCa.g:665:4: asmStringLiteral '(' expression ')'
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_asmStringLiteral_in_asmOperand4481);
                    asmStringLiteral450=asmStringLiteral();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) adaptor.addChild(root_0, asmStringLiteral450.getTree());
                    char_literal451=(Token)input.LT(1);
                    match(input,83,FOLLOW_83_in_asmOperand4483); if (failed) return retval;
                    if ( backtracking==0 ) {
                    char_literal451_tree = (Object)adaptor.create(char_literal451);
                    adaptor.addChild(root_0, char_literal451_tree);
                    }
                    pushFollow(FOLLOW_expression_in_asmOperand4485);
                    expression452=expression();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) adaptor.addChild(root_0, expression452.getTree());
                    char_literal453=(Token)input.LT(1);
                    match(input,84,FOLLOW_84_in_asmOperand4487); if (failed) return retval;
                    if ( backtracking==0 ) {
                    char_literal453_tree = (Object)adaptor.create(char_literal453);
                    adaptor.addChild(root_0, char_literal453_tree);
                    }

                    }
                    break;
                case 2 :
                    // GNUCa.g:666:4: '[' IDENTIFIER ']' asmStringLiteral '(' expression ')'
                    {
                    root_0 = (Object)adaptor.nil();

                    char_literal454=(Token)input.LT(1);
                    match(input,127,FOLLOW_127_in_asmOperand4492); if (failed) return retval;
                    if ( backtracking==0 ) {
                    char_literal454_tree = (Object)adaptor.create(char_literal454);
                    adaptor.addChild(root_0, char_literal454_tree);
                    }
                    IDENTIFIER455=(Token)input.LT(1);
                    match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_asmOperand4494); if (failed) return retval;
                    if ( backtracking==0 ) {
                    IDENTIFIER455_tree = (Object)adaptor.create(IDENTIFIER455);
                    adaptor.addChild(root_0, IDENTIFIER455_tree);
                    }
                    char_literal456=(Token)input.LT(1);
                    match(input,128,FOLLOW_128_in_asmOperand4496); if (failed) return retval;
                    if ( backtracking==0 ) {
                    char_literal456_tree = (Object)adaptor.create(char_literal456);
                    adaptor.addChild(root_0, char_literal456_tree);
                    }
                    pushFollow(FOLLOW_asmStringLiteral_in_asmOperand4498);
                    asmStringLiteral457=asmStringLiteral();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) adaptor.addChild(root_0, asmStringLiteral457.getTree());
                    char_literal458=(Token)input.LT(1);
                    match(input,83,FOLLOW_83_in_asmOperand4500); if (failed) return retval;
                    if ( backtracking==0 ) {
                    char_literal458_tree = (Object)adaptor.create(char_literal458);
                    adaptor.addChild(root_0, char_literal458_tree);
                    }
                    pushFollow(FOLLOW_expression_in_asmOperand4502);
                    expression459=expression();
                    _fsp--;
                    if (failed) return retval;
                    if ( backtracking==0 ) adaptor.addChild(root_0, expression459.getTree());
                    char_literal460=(Token)input.LT(1);
                    match(input,84,FOLLOW_84_in_asmOperand4504); if (failed) return retval;
                    if ( backtracking==0 ) {
                    char_literal460_tree = (Object)adaptor.create(char_literal460);
                    adaptor.addChild(root_0, char_literal460_tree);
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 78, asmOperand_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end asmOperand

    public static class asmClobbers_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start asmClobbers
    // GNUCa.g:669:1: asmClobbers : asmStringLiteral ( ',' asmStringLiteral )* ;
    public final asmClobbers_return asmClobbers() throws RecognitionException {
        asmClobbers_return retval = new asmClobbers_return();
        retval.start = input.LT(1);
        int asmClobbers_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal462=null;
        asmStringLiteral_return asmStringLiteral461 = null;

        asmStringLiteral_return asmStringLiteral463 = null;


        Object char_literal462_tree=null;

        try {
            if ( backtracking>0 && alreadyParsedRule(input, 79) ) { return retval; }
            // GNUCa.g:670:2: ( asmStringLiteral ( ',' asmStringLiteral )* )
            // GNUCa.g:670:4: asmStringLiteral ( ',' asmStringLiteral )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_asmStringLiteral_in_asmClobbers4518);
            asmStringLiteral461=asmStringLiteral();
            _fsp--;
            if (failed) return retval;
            if ( backtracking==0 ) adaptor.addChild(root_0, asmStringLiteral461.getTree());
            // GNUCa.g:670:21: ( ',' asmStringLiteral )*
            loop135:
            do {
                int alt135=2;
                int LA135_0 = input.LA(1);

                if ( (LA135_0==86) ) {
                    alt135=1;
                }


                switch (alt135) {
            	case 1 :
            	    // GNUCa.g:670:22: ',' asmStringLiteral
            	    {
            	    char_literal462=(Token)input.LT(1);
            	    match(input,86,FOLLOW_86_in_asmClobbers4521); if (failed) return retval;
            	    if ( backtracking==0 ) {
            	    char_literal462_tree = (Object)adaptor.create(char_literal462);
            	    adaptor.addChild(root_0, char_literal462_tree);
            	    }
            	    pushFollow(FOLLOW_asmStringLiteral_in_asmClobbers4523);
            	    asmStringLiteral463=asmStringLiteral();
            	    _fsp--;
            	    if (failed) return retval;
            	    if ( backtracking==0 ) adaptor.addChild(root_0, asmStringLiteral463.getTree());

            	    }
            	    break;

            	default :
            	    break loop135;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 79, asmClobbers_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end asmClobbers

    public static class sTRING_LITERAL_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start sTRING_LITERAL
    // GNUCa.g:676:1: sTRING_LITERAL : ( STRING_LITERAL )+ -> ^( STR_LITERAL ( STRING_LITERAL )+ ) ;
    public final sTRING_LITERAL_return sTRING_LITERAL() throws RecognitionException {
        sTRING_LITERAL_return retval = new sTRING_LITERAL_return();
        retval.start = input.LT(1);
        int sTRING_LITERAL_StartIndex = input.index();
        Object root_0 = null;

        Token STRING_LITERAL464=null;

        Object STRING_LITERAL464_tree=null;
        RewriteRuleTokenStream stream_STRING_LITERAL=new RewriteRuleTokenStream(adaptor,"token STRING_LITERAL");

        try {
            if ( backtracking>0 && alreadyParsedRule(input, 80) ) { return retval; }
            // GNUCa.g:677:2: ( ( STRING_LITERAL )+ -> ^( STR_LITERAL ( STRING_LITERAL )+ ) )
            // GNUCa.g:677:4: ( STRING_LITERAL )+
            {
            // GNUCa.g:677:4: ( STRING_LITERAL )+
            int cnt136=0;
            loop136:
            do {
                int alt136=2;
                int LA136_0 = input.LA(1);

                if ( (LA136_0==STRING_LITERAL) ) {
                    alt136=1;
                }


                switch (alt136) {
            	case 1 :
            	    // GNUCa.g:0:0: STRING_LITERAL
            	    {
            	    STRING_LITERAL464=(Token)input.LT(1);
            	    match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_sTRING_LITERAL4540); if (failed) return retval;
            	    if ( backtracking==0 ) stream_STRING_LITERAL.add(STRING_LITERAL464);


            	    }
            	    break;

            	default :
            	    if ( cnt136 >= 1 ) break loop136;
            	    if (backtracking>0) {failed=true; return retval;}
                        EarlyExitException eee =
                            new EarlyExitException(136, input);
                        throw eee;
                }
                cnt136++;
            } while (true);


            // AST REWRITE
            // elements: STRING_LITERAL
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 677:20: -> ^( STR_LITERAL ( STRING_LITERAL )+ )
            {
                // GNUCa.g:677:23: ^( STR_LITERAL ( STRING_LITERAL )+ )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(adaptor.create(STR_LITERAL, "STR_LITERAL"), root_1);

                if ( !(stream_STRING_LITERAL.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_STRING_LITERAL.hasNext() ) {
                    adaptor.addChild(root_1, stream_STRING_LITERAL.next());

                }
                stream_STRING_LITERAL.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            }

            }

            retval.stop = input.LT(-1);

            if ( backtracking==0 ) {
                retval.tree = (Object)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 80, sTRING_LITERAL_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end sTRING_LITERAL

    // $ANTLR start synpred8
    public final void synpred8_fragment() throws RecognitionException {   
        // GNUCa.g:173:4: ( declarationSpecifiers declarator ( '{' | storageClassSpecifier | typeSpecifier | typeQualifier | functionSpecifier | '__attribute' | '__attribute__' ) )
        // GNUCa.g:173:6: declarationSpecifiers declarator ( '{' | storageClassSpecifier | typeSpecifier | typeQualifier | functionSpecifier | '__attribute' | '__attribute__' )
        {
        pushFollow(FOLLOW_declarationSpecifiers_in_synpred8338);
        declarationSpecifiers();
        _fsp--;
        if (failed) return ;
        pushFollow(FOLLOW_declarator_in_synpred8340);
        declarator();
        _fsp--;
        if (failed) return ;
        // GNUCa.g:173:39: ( '{' | storageClassSpecifier | typeSpecifier | typeQualifier | functionSpecifier | '__attribute' | '__attribute__' )
        int alt137=7;
        switch ( input.LA(1) ) {
        case 109:
            {
            alt137=1;
            }
            break;
        case 88:
        case 89:
        case 90:
        case 91:
        case 92:
            {
            alt137=2;
            }
            break;
        case IDENTIFIER:
        case 93:
        case 94:
        case 95:
        case 96:
        case 97:
        case 98:
        case 99:
        case 100:
        case 101:
        case 102:
        case 103:
        case 104:
        case 105:
        case 106:
        case 107:
        case 108:
        case 111:
        case 112:
        case 114:
        case 131:
        case 132:
        case 133:
            {
            alt137=3;
            }
            break;
        case 115:
        case 116:
        case 117:
        case 118:
        case 119:
        case 120:
        case 121:
        case 122:
        case 123:
            {
            alt137=4;
            }
            break;
        case 124:
        case 125:
        case 126:
            {
            alt137=5;
            }
            break;
        case 135:
            {
            alt137=6;
            }
            break;
        case 136:
            {
            alt137=7;
            }
            break;
        default:
            if (backtracking>0) {failed=true; return ;}
            NoViableAltException nvae =
                new NoViableAltException("173:39: ( '{' | storageClassSpecifier | typeSpecifier | typeQualifier | functionSpecifier | '__attribute' | '__attribute__' )", 137, 0, input);

            throw nvae;
        }

        switch (alt137) {
            case 1 :
                // GNUCa.g:173:40: '{'
                {
                match(input,109,FOLLOW_109_in_synpred8343); if (failed) return ;

                }
                break;
            case 2 :
                // GNUCa.g:173:46: storageClassSpecifier
                {
                pushFollow(FOLLOW_storageClassSpecifier_in_synpred8347);
                storageClassSpecifier();
                _fsp--;
                if (failed) return ;

                }
                break;
            case 3 :
                // GNUCa.g:173:70: typeSpecifier
                {
                pushFollow(FOLLOW_typeSpecifier_in_synpred8351);
                typeSpecifier();
                _fsp--;
                if (failed) return ;

                }
                break;
            case 4 :
                // GNUCa.g:173:86: typeQualifier
                {
                pushFollow(FOLLOW_typeQualifier_in_synpred8355);
                typeQualifier();
                _fsp--;
                if (failed) return ;

                }
                break;
            case 5 :
                // GNUCa.g:173:102: functionSpecifier
                {
                pushFollow(FOLLOW_functionSpecifier_in_synpred8359);
                functionSpecifier();
                _fsp--;
                if (failed) return ;

                }
                break;
            case 6 :
                // GNUCa.g:173:121: '__attribute'
                {
                match(input,135,FOLLOW_135_in_synpred8362); if (failed) return ;

                }
                break;
            case 7 :
                // GNUCa.g:173:137: '__attribute__'
                {
                match(input,136,FOLLOW_136_in_synpred8366); if (failed) return ;

                }
                break;

        }


        }
    }
    // $ANTLR end synpred8

    // $ANTLR start synpred9
    public final void synpred9_fragment() throws RecognitionException {   
        // GNUCa.g:174:4: ( declaration )
        // GNUCa.g:174:4: declaration
        {
        pushFollow(FOLLOW_declaration_in_synpred9378);
        declaration();
        _fsp--;
        if (failed) return ;

        }
    }
    // $ANTLR end synpred9

    // $ANTLR start synpred16
    public final void synpred16_fragment() throws RecognitionException {   
        // GNUCa.g:214:14: ( declarationSpecifiers )
        // GNUCa.g:214:14: declarationSpecifiers
        {
        pushFollow(FOLLOW_declarationSpecifiers_in_synpred16594);
        declarationSpecifiers();
        _fsp--;
        if (failed) return ;

        }
    }
    // $ANTLR end synpred16

    // $ANTLR start synpred21
    public final void synpred21_fragment() throws RecognitionException {   
        // GNUCa.g:221:5: ( typeSpecifier )
        // GNUCa.g:221:5: typeSpecifier
        {
        pushFollow(FOLLOW_typeSpecifier_in_synpred21664);
        typeSpecifier();
        _fsp--;
        if (failed) return ;

        }
    }
    // $ANTLR end synpred21

    // $ANTLR start synpred24
    public final void synpred24_fragment() throws RecognitionException {   
        // GNUCa.g:224:5: ( attributes )
        // GNUCa.g:224:5: attributes
        {
        pushFollow(FOLLOW_attributes_in_synpred24685);
        attributes();
        _fsp--;
        if (failed) return ;

        }
    }
    // $ANTLR end synpred24

    // $ANTLR start synpred55
    public final void synpred55_fragment() throws RecognitionException {   
        // GNUCa.g:271:69: ( attributes )
        // GNUCa.g:271:69: attributes
        {
        pushFollow(FOLLOW_attributes_in_synpred551370);
        attributes();
        _fsp--;
        if (failed) return ;

        }
    }
    // $ANTLR end synpred55

    // $ANTLR start synpred56
    public final void synpred56_fragment() throws RecognitionException {   
        // GNUCa.g:271:4: ( structOrUnion ( attributes )? ( IDENTIFIER )? '{' ( structDeclaration )* '}' ( attributes )? )
        // GNUCa.g:271:4: structOrUnion ( attributes )? ( IDENTIFIER )? '{' ( structDeclaration )* '}' ( attributes )?
        {
        pushFollow(FOLLOW_structOrUnion_in_synpred561355);
        structOrUnion();
        _fsp--;
        if (failed) return ;
        // GNUCa.g:271:18: ( attributes )?
        int alt140=2;
        int LA140_0 = input.LA(1);

        if ( ((LA140_0>=135 && LA140_0<=136)) ) {
            alt140=1;
        }
        switch (alt140) {
            case 1 :
                // GNUCa.g:0:0: attributes
                {
                pushFollow(FOLLOW_attributes_in_synpred561357);
                attributes();
                _fsp--;
                if (failed) return ;

                }
                break;

        }

        // GNUCa.g:271:30: ( IDENTIFIER )?
        int alt141=2;
        int LA141_0 = input.LA(1);

        if ( (LA141_0==IDENTIFIER) ) {
            alt141=1;
        }
        switch (alt141) {
            case 1 :
                // GNUCa.g:0:0: IDENTIFIER
                {
                match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_synpred561360); if (failed) return ;

                }
                break;

        }

        match(input,109,FOLLOW_109_in_synpred561363); if (failed) return ;
        // GNUCa.g:271:46: ( structDeclaration )*
        loop142:
        do {
            int alt142=2;
            int LA142_0 = input.LA(1);

            if ( (LA142_0==IDENTIFIER||LA142_0==79||(LA142_0>=93 && LA142_0<=108)||(LA142_0>=111 && LA142_0<=112)||(LA142_0>=114 && LA142_0<=123)||(LA142_0>=131 && LA142_0<=133)||(LA142_0>=135 && LA142_0<=136)) ) {
                alt142=1;
            }


            switch (alt142) {
        	case 1 :
        	    // GNUCa.g:0:0: structDeclaration
        	    {
        	    pushFollow(FOLLOW_structDeclaration_in_synpred561365);
        	    structDeclaration();
        	    _fsp--;
        	    if (failed) return ;

        	    }
        	    break;

        	default :
        	    break loop142;
            }
        } while (true);

        match(input,110,FOLLOW_110_in_synpred561368); if (failed) return ;
        // GNUCa.g:271:69: ( attributes )?
        int alt143=2;
        int LA143_0 = input.LA(1);

        if ( ((LA143_0>=135 && LA143_0<=136)) ) {
            alt143=1;
        }
        switch (alt143) {
            case 1 :
                // GNUCa.g:0:0: attributes
                {
                pushFollow(FOLLOW_attributes_in_synpred561370);
                attributes();
                _fsp--;
                if (failed) return ;

                }
                break;

        }


        }
    }
    // $ANTLR end synpred56

    // $ANTLR start synpred59
    public final void synpred59_fragment() throws RecognitionException {   
        // GNUCa.g:282:4: ( specifierQualifier )
        // GNUCa.g:282:4: specifierQualifier
        {
        pushFollow(FOLLOW_specifierQualifier_in_synpred591443);
        specifierQualifier();
        _fsp--;
        if (failed) return ;

        }
    }
    // $ANTLR end synpred59

    // $ANTLR start synpred92
    public final void synpred92_fragment() throws RecognitionException {   
        // GNUCa.g:357:5: ( '[' ( typeQualifier )* ( assignmentExpression )? ']' )
        // GNUCa.g:357:5: '[' ( typeQualifier )* ( assignmentExpression )? ']'
        {
        match(input,127,FOLLOW_127_in_synpred922010); if (failed) return ;
        // GNUCa.g:357:31: ( typeQualifier )*
        loop151:
        do {
            int alt151=2;
            int LA151_0 = input.LA(1);

            if ( ((LA151_0>=115 && LA151_0<=123)) ) {
                alt151=1;
            }


            switch (alt151) {
        	case 1 :
        	    // GNUCa.g:0:0: typeQualifier
        	    {
        	    pushFollow(FOLLOW_typeQualifier_in_synpred922016);
        	    typeQualifier();
        	    _fsp--;
        	    if (failed) return ;

        	    }
        	    break;

        	default :
        	    break loop151;
            }
        } while (true);

        // GNUCa.g:357:50: ( assignmentExpression )?
        int alt152=2;
        int LA152_0 = input.LA(1);

        if ( ((LA152_0>=IDENTIFIER && LA152_0<=STRING_LITERAL)||LA152_0==83||LA152_0==129||LA152_0==137||(LA152_0>=139 && LA152_0<=153)) ) {
            alt152=1;
        }
        switch (alt152) {
            case 1 :
                // GNUCa.g:0:0: assignmentExpression
                {
                pushFollow(FOLLOW_assignmentExpression_in_synpred922021);
                assignmentExpression();
                _fsp--;
                if (failed) return ;

                }
                break;

        }

        match(input,128,FOLLOW_128_in_synpred922024); if (failed) return ;

        }
    }
    // $ANTLR end synpred92

    // $ANTLR start synpred96
    public final void synpred96_fragment() throws RecognitionException {   
        // GNUCa.g:359:4: ( '[' ( typeQualifier )+ 'static' assignmentExpression ']' )
        // GNUCa.g:359:4: '[' ( typeQualifier )+ 'static' assignmentExpression ']'
        {
        match(input,127,FOLLOW_127_in_synpred962084); if (failed) return ;
        // GNUCa.g:359:30: ( typeQualifier )+
        int cnt154=0;
        loop154:
        do {
            int alt154=2;
            int LA154_0 = input.LA(1);

            if ( ((LA154_0>=115 && LA154_0<=123)) ) {
                alt154=1;
            }


            switch (alt154) {
        	case 1 :
        	    // GNUCa.g:0:0: typeQualifier
        	    {
        	    pushFollow(FOLLOW_typeQualifier_in_synpred962090);
        	    typeQualifier();
        	    _fsp--;
        	    if (failed) return ;

        	    }
        	    break;

        	default :
        	    if ( cnt154 >= 1 ) break loop154;
        	    if (backtracking>0) {failed=true; return ;}
                    EarlyExitException eee =
                        new EarlyExitException(154, input);
                    throw eee;
            }
            cnt154++;
        } while (true);

        match(input,89,FOLLOW_89_in_synpred962093); if (failed) return ;
        pushFollow(FOLLOW_assignmentExpression_in_synpred962097);
        assignmentExpression();
        _fsp--;
        if (failed) return ;
        match(input,128,FOLLOW_128_in_synpred962099); if (failed) return ;

        }
    }
    // $ANTLR end synpred96

    // $ANTLR start synpred98
    public final void synpred98_fragment() throws RecognitionException {   
        // GNUCa.g:360:4: ( '[' ( typeQualifier )* '*' ']' )
        // GNUCa.g:360:4: '[' ( typeQualifier )* '*' ']'
        {
        match(input,127,FOLLOW_127_in_synpred982122); if (failed) return ;
        // GNUCa.g:360:30: ( typeQualifier )*
        loop155:
        do {
            int alt155=2;
            int LA155_0 = input.LA(1);

            if ( ((LA155_0>=115 && LA155_0<=123)) ) {
                alt155=1;
            }


            switch (alt155) {
        	case 1 :
        	    // GNUCa.g:0:0: typeQualifier
        	    {
        	    pushFollow(FOLLOW_typeQualifier_in_synpred982128);
        	    typeQualifier();
        	    _fsp--;
        	    if (failed) return ;

        	    }
        	    break;

        	default :
        	    break loop155;
            }
        } while (true);

        match(input,129,FOLLOW_129_in_synpred982131); if (failed) return ;
        match(input,128,FOLLOW_128_in_synpred982133); if (failed) return ;

        }
    }
    // $ANTLR end synpred98

    // $ANTLR start synpred99
    public final void synpred99_fragment() throws RecognitionException {   
        // GNUCa.g:361:4: ( '(' parameterTypeList ')' )
        // GNUCa.g:361:4: '(' parameterTypeList ')'
        {
        match(input,83,FOLLOW_83_in_synpred992153); if (failed) return ;
        pushFollow(FOLLOW_parameterTypeList_in_synpred992155);
        parameterTypeList();
        _fsp--;
        if (failed) return ;
        match(input,84,FOLLOW_84_in_synpred992157); if (failed) return ;

        }
    }
    // $ANTLR end synpred99

    // $ANTLR start synpred101
    public final void synpred101_fragment() throws RecognitionException {   
        // GNUCa.g:362:4: ( '(' ( identifierList )? ')' )
        // GNUCa.g:362:4: '(' ( identifierList )? ')'
        {
        match(input,83,FOLLOW_83_in_synpred1012173); if (failed) return ;
        // GNUCa.g:362:8: ( identifierList )?
        int alt156=2;
        int LA156_0 = input.LA(1);

        if ( (LA156_0==IDENTIFIER) ) {
            alt156=1;
        }
        switch (alt156) {
            case 1 :
                // GNUCa.g:0:0: identifierList
                {
                pushFollow(FOLLOW_identifierList_in_synpred1012175);
                identifierList();
                _fsp--;
                if (failed) return ;

                }
                break;

        }

        match(input,84,FOLLOW_84_in_synpred1012178); if (failed) return ;

        }
    }
    // $ANTLR end synpred101

    // $ANTLR start synpred106
    public final void synpred106_fragment() throws RecognitionException {   
        // GNUCa.g:379:27: ( declarator )
        // GNUCa.g:379:27: declarator
        {
        pushFollow(FOLLOW_declarator_in_synpred1062298);
        declarator();
        _fsp--;
        if (failed) return ;

        }
    }
    // $ANTLR end synpred106

    // $ANTLR start synpred116
    public final void synpred116_fragment() throws RecognitionException {   
        // GNUCa.g:397:5: ( '[' ( assignmentExpression )? ']' )
        // GNUCa.g:397:5: '[' ( assignmentExpression )? ']'
        {
        match(input,127,FOLLOW_127_in_synpred1162419); if (failed) return ;
        // GNUCa.g:397:9: ( assignmentExpression )?
        int alt158=2;
        int LA158_0 = input.LA(1);

        if ( ((LA158_0>=IDENTIFIER && LA158_0<=STRING_LITERAL)||LA158_0==83||LA158_0==129||LA158_0==137||(LA158_0>=139 && LA158_0<=153)) ) {
            alt158=1;
        }
        switch (alt158) {
            case 1 :
                // GNUCa.g:0:0: assignmentExpression
                {
                pushFollow(FOLLOW_assignmentExpression_in_synpred1162421);
                assignmentExpression();
                _fsp--;
                if (failed) return ;

                }
                break;

        }

        match(input,128,FOLLOW_128_in_synpred1162424); if (failed) return ;

        }
    }
    // $ANTLR end synpred116

    // $ANTLR start synpred117
    public final void synpred117_fragment() throws RecognitionException {   
        // GNUCa.g:398:5: ( '[' '*' ']' )
        // GNUCa.g:398:5: '[' '*' ']'
        {
        match(input,127,FOLLOW_127_in_synpred1172441); if (failed) return ;
        match(input,129,FOLLOW_129_in_synpred1172443); if (failed) return ;
        match(input,128,FOLLOW_128_in_synpred1172445); if (failed) return ;

        }
    }
    // $ANTLR end synpred117

    // $ANTLR start synpred120
    public final void synpred120_fragment() throws RecognitionException {   
        // GNUCa.g:396:4: ( '(' ( attributes )? abstractDeclarator ')' ( '[' ( assignmentExpression )? ']' | '[' '*' ']' | '(' ( parameterTypeList )? ')' )? )
        // GNUCa.g:396:4: '(' ( attributes )? abstractDeclarator ')' ( '[' ( assignmentExpression )? ']' | '[' '*' ']' | '(' ( parameterTypeList )? ')' )?
        {
        match(input,83,FOLLOW_83_in_synpred1202405); if (failed) return ;
        // GNUCa.g:396:8: ( attributes )?
        int alt160=2;
        int LA160_0 = input.LA(1);

        if ( ((LA160_0>=135 && LA160_0<=136)) ) {
            alt160=1;
        }
        switch (alt160) {
            case 1 :
                // GNUCa.g:0:0: attributes
                {
                pushFollow(FOLLOW_attributes_in_synpred1202407);
                attributes();
                _fsp--;
                if (failed) return ;

                }
                break;

        }

        pushFollow(FOLLOW_abstractDeclarator_in_synpred1202410);
        abstractDeclarator();
        _fsp--;
        if (failed) return ;
        match(input,84,FOLLOW_84_in_synpred1202412); if (failed) return ;
        // GNUCa.g:397:3: ( '[' ( assignmentExpression )? ']' | '[' '*' ']' | '(' ( parameterTypeList )? ')' )?
        int alt163=4;
        int LA163_0 = input.LA(1);

        if ( (LA163_0==127) ) {
            int LA163_1 = input.LA(2);

            if ( (LA163_1==129) ) {
                int LA163_4 = input.LA(3);

                if ( (synpred116()) ) {
                    alt163=1;
                }
                else if ( (synpred117()) ) {
                    alt163=2;
                }
            }
            else if ( ((LA163_1>=IDENTIFIER && LA163_1<=STRING_LITERAL)||LA163_1==83||LA163_1==128||LA163_1==137||(LA163_1>=139 && LA163_1<=153)) ) {
                alt163=1;
            }
        }
        else if ( (LA163_0==83) ) {
            alt163=3;
        }
        switch (alt163) {
            case 1 :
                // GNUCa.g:397:5: '[' ( assignmentExpression )? ']'
                {
                match(input,127,FOLLOW_127_in_synpred1202419); if (failed) return ;
                // GNUCa.g:397:9: ( assignmentExpression )?
                int alt161=2;
                int LA161_0 = input.LA(1);

                if ( ((LA161_0>=IDENTIFIER && LA161_0<=STRING_LITERAL)||LA161_0==83||LA161_0==129||LA161_0==137||(LA161_0>=139 && LA161_0<=153)) ) {
                    alt161=1;
                }
                switch (alt161) {
                    case 1 :
                        // GNUCa.g:0:0: assignmentExpression
                        {
                        pushFollow(FOLLOW_assignmentExpression_in_synpred1202421);
                        assignmentExpression();
                        _fsp--;
                        if (failed) return ;

                        }
                        break;

                }

                match(input,128,FOLLOW_128_in_synpred1202424); if (failed) return ;

                }
                break;
            case 2 :
                // GNUCa.g:398:5: '[' '*' ']'
                {
                match(input,127,FOLLOW_127_in_synpred1202441); if (failed) return ;
                match(input,129,FOLLOW_129_in_synpred1202443); if (failed) return ;
                match(input,128,FOLLOW_128_in_synpred1202445); if (failed) return ;

                }
                break;
            case 3 :
                // GNUCa.g:399:5: '(' ( parameterTypeList )? ')'
                {
                match(input,83,FOLLOW_83_in_synpred1202461); if (failed) return ;
                // GNUCa.g:399:9: ( parameterTypeList )?
                int alt162=2;
                int LA162_0 = input.LA(1);

                if ( (LA162_0==IDENTIFIER||(LA162_0>=88 && LA162_0<=108)||(LA162_0>=111 && LA162_0<=112)||(LA162_0>=114 && LA162_0<=126)||(LA162_0>=131 && LA162_0<=133)||(LA162_0>=135 && LA162_0<=136)) ) {
                    alt162=1;
                }
                switch (alt162) {
                    case 1 :
                        // GNUCa.g:0:0: parameterTypeList
                        {
                        pushFollow(FOLLOW_parameterTypeList_in_synpred1202463);
                        parameterTypeList();
                        _fsp--;
                        if (failed) return ;

                        }
                        break;

                }

                match(input,84,FOLLOW_84_in_synpred1202466); if (failed) return ;

                }
                break;

        }


        }
    }
    // $ANTLR end synpred120

    // $ANTLR start synpred122
    public final void synpred122_fragment() throws RecognitionException {   
        // GNUCa.g:401:4: ( '[' ( assignmentExpression )? ']' )
        // GNUCa.g:401:4: '[' ( assignmentExpression )? ']'
        {
        match(input,127,FOLLOW_127_in_synpred1222489); if (failed) return ;
        // GNUCa.g:401:8: ( assignmentExpression )?
        int alt164=2;
        int LA164_0 = input.LA(1);

        if ( ((LA164_0>=IDENTIFIER && LA164_0<=STRING_LITERAL)||LA164_0==83||LA164_0==129||LA164_0==137||(LA164_0>=139 && LA164_0<=153)) ) {
            alt164=1;
        }
        switch (alt164) {
            case 1 :
                // GNUCa.g:0:0: assignmentExpression
                {
                pushFollow(FOLLOW_assignmentExpression_in_synpred1222491);
                assignmentExpression();
                _fsp--;
                if (failed) return ;

                }
                break;

        }

        match(input,128,FOLLOW_128_in_synpred1222494); if (failed) return ;

        }
    }
    // $ANTLR end synpred122

    // $ANTLR start synpred123
    public final void synpred123_fragment() throws RecognitionException {   
        // GNUCa.g:402:4: ( '[' '*' ']' )
        // GNUCa.g:402:4: '[' '*' ']'
        {
        match(input,127,FOLLOW_127_in_synpred1232508); if (failed) return ;
        match(input,129,FOLLOW_129_in_synpred1232510); if (failed) return ;
        match(input,128,FOLLOW_128_in_synpred1232512); if (failed) return ;

        }
    }
    // $ANTLR end synpred123

    // $ANTLR start synpred127
    public final void synpred127_fragment() throws RecognitionException {   
        // GNUCa.g:411:44: ( expression )
        // GNUCa.g:411:44: expression
        {
        pushFollow(FOLLOW_expression_in_synpred1272576);
        expression();
        _fsp--;
        if (failed) return ;

        }
    }
    // $ANTLR end synpred127

    // $ANTLR start synpred134
    public final void synpred134_fragment() throws RecognitionException {   
        // GNUCa.g:425:4: ( arrayDesignator )
        // GNUCa.g:425:4: arrayDesignator
        {
        pushFollow(FOLLOW_arrayDesignator_in_synpred1342704);
        arrayDesignator();
        _fsp--;
        if (failed) return ;

        }
    }
    // $ANTLR end synpred134

    // $ANTLR start synpred138
    public final void synpred138_fragment() throws RecognitionException {   
        // GNUCa.g:440:4: ( attribute )
        // GNUCa.g:440:4: attribute
        {
        pushFollow(FOLLOW_attribute_in_synpred1382804);
        attribute();
        _fsp--;
        if (failed) return ;

        }
    }
    // $ANTLR end synpred138

    // $ANTLR start synpred152
    public final void synpred152_fragment() throws RecognitionException {   
        // GNUCa.g:474:4: ( primaryExpression )
        // GNUCa.g:474:4: primaryExpression
        {
        pushFollow(FOLLOW_primaryExpression_in_synpred1523015);
        primaryExpression();
        _fsp--;
        if (failed) return ;

        }
    }
    // $ANTLR end synpred152

    // $ANTLR start synpred166
    public final void synpred166_fragment() throws RecognitionException {   
        // GNUCa.g:495:4: ( 'sizeof' unaryExpression )
        // GNUCa.g:495:4: 'sizeof' unaryExpression
        {
        match(input,141,FOLLOW_141_in_synpred1663267); if (failed) return ;
        pushFollow(FOLLOW_unaryExpression_in_synpred1663270);
        unaryExpression();
        _fsp--;
        if (failed) return ;

        }
    }
    // $ANTLR end synpred166

    // $ANTLR start synpred167
    public final void synpred167_fragment() throws RecognitionException {   
        // GNUCa.g:496:4: ( 'sizeof' '(' typeName ')' )
        // GNUCa.g:496:4: 'sizeof' '(' typeName ')'
        {
        match(input,141,FOLLOW_141_in_synpred1673275); if (failed) return ;
        match(input,83,FOLLOW_83_in_synpred1673278); if (failed) return ;
        pushFollow(FOLLOW_typeName_in_synpred1673281);
        typeName();
        _fsp--;
        if (failed) return ;
        match(input,84,FOLLOW_84_in_synpred1673283); if (failed) return ;

        }
    }
    // $ANTLR end synpred167

    // $ANTLR start synpred169
    public final void synpred169_fragment() throws RecognitionException {   
        // GNUCa.g:497:4: ( ( '__alignof' | '__alignof__' ) unaryExpression )
        // GNUCa.g:497:4: ( '__alignof' | '__alignof__' ) unaryExpression
        {
        if ( (input.LA(1)>=142 && input.LA(1)<=143) ) {
            input.consume();
            errorRecovery=false;failed=false;
        }
        else {
            if (backtracking>0) {failed=true; return ;}
            MismatchedSetException mse =
                new MismatchedSetException(null,input);
            recoverFromMismatchedSet(input,mse,FOLLOW_set_in_synpred1693289);    throw mse;
        }

        pushFollow(FOLLOW_unaryExpression_in_synpred1693295);
        unaryExpression();
        _fsp--;
        if (failed) return ;

        }
    }
    // $ANTLR end synpred169

    // $ANTLR start synpred181
    public final void synpred181_fragment() throws RecognitionException {   
        // GNUCa.g:519:4: ( '(' typeName ')' )
        // GNUCa.g:519:5: '(' typeName ')'
        {
        match(input,83,FOLLOW_83_in_synpred1813431); if (failed) return ;
        pushFollow(FOLLOW_typeName_in_synpred1813433);
        typeName();
        _fsp--;
        if (failed) return ;
        match(input,84,FOLLOW_84_in_synpred1813435); if (failed) return ;

        }
    }
    // $ANTLR end synpred181

    // $ANTLR start synpred226
    public final void synpred226_fragment() throws RecognitionException {   
        // GNUCa.g:618:9: ( declaration )
        // GNUCa.g:618:9: declaration
        {
        pushFollow(FOLLOW_declaration_in_synpred2264038);
        declaration();
        _fsp--;
        if (failed) return ;

        }
    }
    // $ANTLR end synpred226

    // $ANTLR start synpred227
    public final void synpred227_fragment() throws RecognitionException {   
        // GNUCa.g:618:26: ( nestedFunctionDefinition )
        // GNUCa.g:618:26: nestedFunctionDefinition
        {
        pushFollow(FOLLOW_nestedFunctionDefinition_in_synpred2274044);
        nestedFunctionDefinition();
        _fsp--;
        if (failed) return ;

        }
    }
    // $ANTLR end synpred227

    // $ANTLR start synpred228
    public final void synpred228_fragment() throws RecognitionException {   
        // GNUCa.g:618:56: ( statement )
        // GNUCa.g:618:56: statement
        {
        pushFollow(FOLLOW_statement_in_synpred2284050);
        statement();
        _fsp--;
        if (failed) return ;

        }
    }
    // $ANTLR end synpred228

    // $ANTLR start synpred233
    public final void synpred233_fragment() throws RecognitionException {   
        // GNUCa.g:640:15: ( declaration )
        // GNUCa.g:640:15: declaration
        {
        pushFollow(FOLLOW_declaration_in_synpred2334243);
        declaration();
        _fsp--;
        if (failed) return ;

        }
    }
    // $ANTLR end synpred233

    public final boolean synpred127() {
        backtracking++;
        int start = input.mark();
        try {
            synpred127_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred122() {
        backtracking++;
        int start = input.mark();
        try {
            synpred122_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred123() {
        backtracking++;
        int start = input.mark();
        try {
            synpred123_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred101() {
        backtracking++;
        int start = input.mark();
        try {
            synpred101_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred120() {
        backtracking++;
        int start = input.mark();
        try {
            synpred120_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred106() {
        backtracking++;
        int start = input.mark();
        try {
            synpred106_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred181() {
        backtracking++;
        int start = input.mark();
        try {
            synpred181_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred169() {
        backtracking++;
        int start = input.mark();
        try {
            synpred169_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred24() {
        backtracking++;
        int start = input.mark();
        try {
            synpred24_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred233() {
        backtracking++;
        int start = input.mark();
        try {
            synpred233_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred21() {
        backtracking++;
        int start = input.mark();
        try {
            synpred21_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred167() {
        backtracking++;
        int start = input.mark();
        try {
            synpred167_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred166() {
        backtracking++;
        int start = input.mark();
        try {
            synpred166_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred96() {
        backtracking++;
        int start = input.mark();
        try {
            synpred96_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred117() {
        backtracking++;
        int start = input.mark();
        try {
            synpred117_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred59() {
        backtracking++;
        int start = input.mark();
        try {
            synpred59_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred116() {
        backtracking++;
        int start = input.mark();
        try {
            synpred116_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred134() {
        backtracking++;
        int start = input.mark();
        try {
            synpred134_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred92() {
        backtracking++;
        int start = input.mark();
        try {
            synpred92_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred56() {
        backtracking++;
        int start = input.mark();
        try {
            synpred56_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred55() {
        backtracking++;
        int start = input.mark();
        try {
            synpred55_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred138() {
        backtracking++;
        int start = input.mark();
        try {
            synpred138_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred8() {
        backtracking++;
        int start = input.mark();
        try {
            synpred8_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred98() {
        backtracking++;
        int start = input.mark();
        try {
            synpred98_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred99() {
        backtracking++;
        int start = input.mark();
        try {
            synpred99_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred152() {
        backtracking++;
        int start = input.mark();
        try {
            synpred152_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred226() {
        backtracking++;
        int start = input.mark();
        try {
            synpred226_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred9() {
        backtracking++;
        int start = input.mark();
        try {
            synpred9_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred227() {
        backtracking++;
        int start = input.mark();
        try {
            synpred227_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred228() {
        backtracking++;
        int start = input.mark();
        try {
            synpred228_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred16() {
        backtracking++;
        int start = input.mark();
        try {
            synpred16_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }


 

    public static final BitSet FOLLOW_externalDeclaration_in_translationUnit295 = new BitSet(new long[]{0x0020000000000002L,0x7FFD9FFFFF278000L,0x00000000000001B8L});
    public static final BitSet FOLLOW_functionDefinition_in_externalDeclaration372 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_declaration_in_externalDeclaration378 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_79_in_externalDeclaration383 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_asmDefinition_in_externalDeclaration389 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_simpleAsmExpr_in_asmDefinition409 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_80_in_simpleAsmExpr422 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_81_in_simpleAsmExpr424 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_82_in_simpleAsmExpr426 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_simpleAsmExpr429 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_asmStringLiteral_in_simpleAsmExpr431 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_simpleAsmExpr433 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_sTRING_LITERAL_in_asmStringLiteral449 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_declarationSpecifiers_in_functionDefinition477 = new BitSet(new long[]{0x0020000000000000L,0x0000000000080000L,0x0000000000000002L});
    public static final BitSet FOLLOW_declarator_in_functionDefinition479 = new BitSet(new long[]{0x0020000000000000L,0x7FFDBFFFFF200000L,0x00000000000001B8L});
    public static final BitSet FOLLOW_compoundStatement_in_functionDefinition486 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_declaration_in_functionDefinition494 = new BitSet(new long[]{0x0020000000000000L,0x7FFDBFFFFF200000L,0x00000000000001B8L});
    public static final BitSet FOLLOW_compoundStatement_in_functionDefinition497 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_declarationSpecifiers_in_nestedFunctionDefinition542 = new BitSet(new long[]{0x0020000000000000L,0x0000000000080000L,0x0000000000000002L});
    public static final BitSet FOLLOW_declarator_in_nestedFunctionDefinition544 = new BitSet(new long[]{0x0020000000000000L,0x7FFDBFFFFF200000L,0x00000000000001B8L});
    public static final BitSet FOLLOW_declaration_in_nestedFunctionDefinition546 = new BitSet(new long[]{0x0020000000000000L,0x7FFDBFFFFF200000L,0x00000000000001B8L});
    public static final BitSet FOLLOW_compoundStatement_in_nestedFunctionDefinition549 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_85_in_declaration592 = new BitSet(new long[]{0x0020000000000000L,0x7FFD9FFFFF088000L,0x00000000000001BAL});
    public static final BitSet FOLLOW_declarationSpecifiers_in_declaration594 = new BitSet(new long[]{0x0020000000000000L,0x0000000000088000L,0x0000000000000002L});
    public static final BitSet FOLLOW_initDeclaratorList_in_declaration600 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_declaration603 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_declarationSpecifiers_in_declaration625 = new BitSet(new long[]{0x0020000000000000L,0x0000000000088000L,0x0000000000000002L});
    public static final BitSet FOLLOW_initDeclaratorList_in_declaration627 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_declaration630 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_storageClassSpecifier_in_declarationSpecifiers658 = new BitSet(new long[]{0x0020000000000002L,0x7FFD9FFFFF000000L,0x00000000000001B8L});
    public static final BitSet FOLLOW_typeSpecifier_in_declarationSpecifiers664 = new BitSet(new long[]{0x0020000000000002L,0x7FFD9FFFFF000000L,0x00000000000001B8L});
    public static final BitSet FOLLOW_typeQualifier_in_declarationSpecifiers670 = new BitSet(new long[]{0x0020000000000002L,0x7FFD9FFFFF000000L,0x00000000000001B8L});
    public static final BitSet FOLLOW_functionSpecifier_in_declarationSpecifiers678 = new BitSet(new long[]{0x0020000000000002L,0x7FFD9FFFFF000000L,0x00000000000001B8L});
    public static final BitSet FOLLOW_attributes_in_declarationSpecifiers685 = new BitSet(new long[]{0x0020000000000002L,0x7FFD9FFFFF000000L,0x00000000000001B8L});
    public static final BitSet FOLLOW_initDeclarator_in_initDeclaratorList726 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_initDeclaratorList730 = new BitSet(new long[]{0x0020000000000000L,0x0000000000080000L,0x0000000000000002L});
    public static final BitSet FOLLOW_initDeclarator_in_initDeclaratorList732 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_declarator_in_initDeclarator753 = new BitSet(new long[]{0x0000000000000002L,0x0000000000870000L,0x0000000000000180L});
    public static final BitSet FOLLOW_simpleAsmExpr_in_initDeclarator755 = new BitSet(new long[]{0x0000000000000002L,0x0000000000800000L,0x0000000000000180L});
    public static final BitSet FOLLOW_attributes_in_initDeclarator758 = new BitSet(new long[]{0x0000000000000002L,0x0000000000800000L});
    public static final BitSet FOLLOW_87_in_initDeclarator763 = new BitSet(new long[]{0x00E0000000000000L,0x0000200000080000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_initializer_in_initDeclarator765 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_storageClassSpecifier0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_93_in_typeSpecifier843 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_94_in_typeSpecifier871 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_95_in_typeSpecifier902 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_96_in_typeSpecifier930 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_97_in_typeSpecifier958 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_98_in_typeSpecifier986 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_99_in_typeSpecifier1014 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_100_in_typeSpecifier1041 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_101_in_typeSpecifier1071 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_102_in_typeSpecifier1101 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_103_in_typeSpecifier1131 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_104_in_typeSpecifier1152 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_105_in_typeSpecifier1174 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_106_in_typeSpecifier1195 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_107_in_typeSpecifier1218 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_108_in_typeSpecifier1241 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_structOrUnionSpecifier_in_typeSpecifier1270 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_enumSpecifier_in_typeSpecifier1288 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typedefName_in_typeSpecifier1306 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeofSpecifier_in_typeSpecifier1318 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_structOrUnion_in_structOrUnionSpecifier1355 = new BitSet(new long[]{0x0020000000000000L,0x0000200000000000L,0x0000000000000180L});
    public static final BitSet FOLLOW_attributes_in_structOrUnionSpecifier1357 = new BitSet(new long[]{0x0020000000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_structOrUnionSpecifier1360 = new BitSet(new long[]{0x0000000000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_109_in_structOrUnionSpecifier1363 = new BitSet(new long[]{0x0020000000000000L,0x0FFDDFFFE0008000L,0x00000000000001B8L});
    public static final BitSet FOLLOW_structDeclaration_in_structOrUnionSpecifier1365 = new BitSet(new long[]{0x0020000000000000L,0x0FFDDFFFE0008000L,0x00000000000001B8L});
    public static final BitSet FOLLOW_110_in_structOrUnionSpecifier1368 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000180L});
    public static final BitSet FOLLOW_attributes_in_structOrUnionSpecifier1370 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_structOrUnion_in_structOrUnionSpecifier1393 = new BitSet(new long[]{0x0020000000000000L,0x0000000000000000L,0x0000000000000180L});
    public static final BitSet FOLLOW_attributes_in_structOrUnionSpecifier1395 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_structOrUnionSpecifier1398 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_structOrUnion0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_specifierQualifier_in_structDeclaration1443 = new BitSet(new long[]{0x0020000000000000L,0x0FFF9FFFE0088000L,0x00000000000001BAL});
    public static final BitSet FOLLOW_structDeclaratorList_in_structDeclaration1446 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_structDeclaration1449 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_79_in_structDeclaration1466 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeSpecifier_in_specifierQualifier1481 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeQualifier_in_specifierQualifier1494 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_attributes_in_specifierQualifier1507 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_structDeclarator_in_structDeclaratorList1522 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_structDeclaratorList1526 = new BitSet(new long[]{0x0020000000000000L,0x0002000000080000L,0x0000000000000182L});
    public static final BitSet FOLLOW_attributes_in_structDeclaratorList1528 = new BitSet(new long[]{0x0020000000000000L,0x0002000000080000L,0x0000000000000002L});
    public static final BitSet FOLLOW_structDeclarator_in_structDeclaratorList1531 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_declarator_in_structDeclarator1551 = new BitSet(new long[]{0x0000000000000002L,0x0002000000000000L,0x0000000000000180L});
    public static final BitSet FOLLOW_113_in_structDeclarator1555 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000080000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_constantExpression_in_structDeclarator1557 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000180L});
    public static final BitSet FOLLOW_attributes_in_structDeclarator1562 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_113_in_structDeclarator1579 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000080000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_constantExpression_in_structDeclarator1581 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000180L});
    public static final BitSet FOLLOW_attributes_in_structDeclarator1583 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_114_in_enumSpecifier1605 = new BitSet(new long[]{0x0020000000000000L,0x0000200000000000L,0x0000000000000180L});
    public static final BitSet FOLLOW_attributes_in_enumSpecifier1607 = new BitSet(new long[]{0x0020000000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_109_in_enumSpecifier1615 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_enumeratorList_in_enumSpecifier1617 = new BitSet(new long[]{0x0000000000000000L,0x0000400000400000L});
    public static final BitSet FOLLOW_86_in_enumSpecifier1621 = new BitSet(new long[]{0x0000000000000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_110_in_enumSpecifier1626 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTIFIER_in_enumSpecifier1639 = new BitSet(new long[]{0x0000000000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_109_in_enumSpecifier1641 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_enumeratorList_in_enumSpecifier1643 = new BitSet(new long[]{0x0000000000000000L,0x0000400000400000L});
    public static final BitSet FOLLOW_86_in_enumSpecifier1647 = new BitSet(new long[]{0x0000000000000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_110_in_enumSpecifier1652 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTIFIER_in_enumSpecifier1671 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_enumerator_in_enumeratorList1702 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_enumeratorList1706 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_enumerator_in_enumeratorList1708 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_enumerator1727 = new BitSet(new long[]{0x0000000000000002L,0x0000000000800000L});
    public static final BitSet FOLLOW_87_in_enumerator1732 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000080000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_constantExpression_in_enumerator1734 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_115_in_typeQualifier1773 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_116_in_typeQualifier1785 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_117_in_typeQualifier1801 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_118_in_typeQualifier1810 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_119_in_typeQualifier1815 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_120_in_typeQualifier1824 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_121_in_typeQualifier1846 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_122_in_typeQualifier1864 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_123_in_typeQualifier1886 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_124_in_functionSpecifier1908 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_125_in_functionSpecifier1913 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_126_in_functionSpecifier1922 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pointer_in_declarator1938 = new BitSet(new long[]{0x0020000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_directDeclarator_in_declarator1941 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTIFIER_in_directDeclarator1966 = new BitSet(new long[]{0x0000000000000002L,0x8000000000080000L});
    public static final BitSet FOLLOW_83_in_directDeclarator1980 = new BitSet(new long[]{0x0020000000000000L,0x0000000000080000L,0x0000000000000182L});
    public static final BitSet FOLLOW_attributes_in_directDeclarator1982 = new BitSet(new long[]{0x0020000000000000L,0x0000000000080000L,0x0000000000000002L});
    public static final BitSet FOLLOW_declarator_in_directDeclarator1985 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_directDeclarator1987 = new BitSet(new long[]{0x0000000000000002L,0x8000000000080000L});
    public static final BitSet FOLLOW_127_in_directDeclarator2010 = new BitSet(new long[]{0x00E0000000000000L,0x0FF8000000080000L,0x0000000003FFFA03L});
    public static final BitSet FOLLOW_typeQualifier_in_directDeclarator2016 = new BitSet(new long[]{0x00E0000000000000L,0x0FF8000000080000L,0x0000000003FFFA03L});
    public static final BitSet FOLLOW_assignmentExpression_in_directDeclarator2021 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_128_in_directDeclarator2024 = new BitSet(new long[]{0x0000000000000002L,0x8000000000080000L});
    public static final BitSet FOLLOW_127_in_directDeclarator2046 = new BitSet(new long[]{0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_89_in_directDeclarator2050 = new BitSet(new long[]{0x00E0000000000000L,0x0FF8000000080000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_typeQualifier_in_directDeclarator2054 = new BitSet(new long[]{0x00E0000000000000L,0x0FF8000000080000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_assignmentExpression_in_directDeclarator2059 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_128_in_directDeclarator2061 = new BitSet(new long[]{0x0000000000000002L,0x8000000000080000L});
    public static final BitSet FOLLOW_127_in_directDeclarator2084 = new BitSet(new long[]{0x0000000000000000L,0x0FF8000000000000L});
    public static final BitSet FOLLOW_typeQualifier_in_directDeclarator2090 = new BitSet(new long[]{0x0000000000000000L,0x0FF8000002000000L});
    public static final BitSet FOLLOW_89_in_directDeclarator2093 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000080000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_assignmentExpression_in_directDeclarator2097 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_128_in_directDeclarator2099 = new BitSet(new long[]{0x0000000000000002L,0x8000000000080000L});
    public static final BitSet FOLLOW_127_in_directDeclarator2122 = new BitSet(new long[]{0x0000000000000000L,0x0FF8000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_typeQualifier_in_directDeclarator2128 = new BitSet(new long[]{0x0000000000000000L,0x0FF8000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_129_in_directDeclarator2131 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_128_in_directDeclarator2133 = new BitSet(new long[]{0x0000000000000002L,0x8000000000080000L});
    public static final BitSet FOLLOW_83_in_directDeclarator2153 = new BitSet(new long[]{0x0020000000000000L,0x7FFD9FFFFF000000L,0x00000000000001B8L});
    public static final BitSet FOLLOW_parameterTypeList_in_directDeclarator2155 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_directDeclarator2157 = new BitSet(new long[]{0x0000000000000002L,0x8000000000080000L});
    public static final BitSet FOLLOW_83_in_directDeclarator2173 = new BitSet(new long[]{0x0020000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_identifierList_in_directDeclarator2175 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_directDeclarator2178 = new BitSet(new long[]{0x0000000000000002L,0x8000000000080000L});
    public static final BitSet FOLLOW_129_in_pointer2206 = new BitSet(new long[]{0x0000000000000002L,0x0FF8000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_typeQualifier_in_pointer2208 = new BitSet(new long[]{0x0000000000000002L,0x0FF8000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_pointer_in_pointer2211 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_parameterList_in_parameterTypeList2238 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_parameterTypeList2247 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_130_in_parameterTypeList2249 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_parameterDeclaration_in_parameterList2269 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_parameterList2273 = new BitSet(new long[]{0x0020000000000000L,0x7FFD9FFFFF000000L,0x00000000000001B8L});
    public static final BitSet FOLLOW_parameterDeclaration_in_parameterList2275 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_declarationSpecifiers_in_parameterDeclaration2295 = new BitSet(new long[]{0x0020000000000002L,0x8000000000080000L,0x0000000000000182L});
    public static final BitSet FOLLOW_declarator_in_parameterDeclaration2298 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000180L});
    public static final BitSet FOLLOW_abstractDeclarator_in_parameterDeclaration2300 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000180L});
    public static final BitSet FOLLOW_attributes_in_parameterDeclaration2304 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTIFIER_in_identifierList2331 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_identifierList2334 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_identifierList2336 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_specifierQualifier_in_typeName2358 = new BitSet(new long[]{0x0020000000000002L,0x8FFD9FFFE0080000L,0x00000000000001BAL});
    public static final BitSet FOLLOW_abstractDeclarator_in_typeName2361 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pointer_in_abstractDeclarator2386 = new BitSet(new long[]{0x0000000000000002L,0x8000000000080000L});
    public static final BitSet FOLLOW_directAbstractDeclarator_in_abstractDeclarator2388 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_directAbstractDeclarator_in_abstractDeclarator2394 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_directAbstractDeclarator2405 = new BitSet(new long[]{0x0000000000000000L,0x8000000000080000L,0x0000000000000182L});
    public static final BitSet FOLLOW_attributes_in_directAbstractDeclarator2407 = new BitSet(new long[]{0x0000000000000000L,0x8000000000080000L,0x0000000000000002L});
    public static final BitSet FOLLOW_abstractDeclarator_in_directAbstractDeclarator2410 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_directAbstractDeclarator2412 = new BitSet(new long[]{0x0000000000000002L,0x8000000000080000L});
    public static final BitSet FOLLOW_127_in_directAbstractDeclarator2419 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000080000L,0x0000000003FFFA03L});
    public static final BitSet FOLLOW_assignmentExpression_in_directAbstractDeclarator2421 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_128_in_directAbstractDeclarator2424 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_127_in_directAbstractDeclarator2441 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_129_in_directAbstractDeclarator2443 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_128_in_directAbstractDeclarator2445 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_directAbstractDeclarator2461 = new BitSet(new long[]{0x0020000000000000L,0x7FFD9FFFFF100000L,0x00000000000001B8L});
    public static final BitSet FOLLOW_parameterTypeList_in_directAbstractDeclarator2463 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_directAbstractDeclarator2466 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_127_in_directAbstractDeclarator2489 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000080000L,0x0000000003FFFA03L});
    public static final BitSet FOLLOW_assignmentExpression_in_directAbstractDeclarator2491 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_128_in_directAbstractDeclarator2494 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_127_in_directAbstractDeclarator2508 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_129_in_directAbstractDeclarator2510 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_128_in_directAbstractDeclarator2512 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_directAbstractDeclarator2525 = new BitSet(new long[]{0x0020000000000000L,0x7FFD9FFFFF100000L,0x00000000000001B8L});
    public static final BitSet FOLLOW_parameterTypeList_in_directAbstractDeclarator2527 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_directAbstractDeclarator2530 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTIFIER_in_typedefName2554 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_131_in_typeofSpecifier2566 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_132_in_typeofSpecifier2568 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_133_in_typeofSpecifier2570 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_typeofSpecifier2573 = new BitSet(new long[]{0x00E0000000000000L,0x0FFD9FFFE0080000L,0x0000000003FFFBBAL});
    public static final BitSet FOLLOW_expression_in_typeofSpecifier2576 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_typeName_in_typeofSpecifier2587 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_typeofSpecifier2598 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignmentExpression_in_initializer2610 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_109_in_initializer2623 = new BitSet(new long[]{0x0000000000000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_110_in_initializer2625 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_109_in_initializer2642 = new BitSet(new long[]{0x00E0000000000000L,0x8000200000080000L,0x0000000003FFFA42L});
    public static final BitSet FOLLOW_initializerList_in_initializer2644 = new BitSet(new long[]{0x0000000000000000L,0x0000400000400000L});
    public static final BitSet FOLLOW_86_in_initializer2646 = new BitSet(new long[]{0x0000000000000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_110_in_initializer2649 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_designation_in_initializerList2668 = new BitSet(new long[]{0x00E0000000000000L,0x0000200000080000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_initializer_in_initializerList2671 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_initializerList2674 = new BitSet(new long[]{0x00E0000000000000L,0x8000200000080000L,0x0000000003FFFA42L});
    public static final BitSet FOLLOW_designation_in_initializerList2677 = new BitSet(new long[]{0x00E0000000000000L,0x0000200000080000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_initializer_in_initializerList2680 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_arrayDesignator_in_designation2704 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTIFIER_in_designation2713 = new BitSet(new long[]{0x0000000000000000L,0x0002000000000000L});
    public static final BitSet FOLLOW_113_in_designation2715 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_designator_in_designation2724 = new BitSet(new long[]{0x0000000000000000L,0x8000000000800000L,0x0000000000000040L});
    public static final BitSet FOLLOW_87_in_designation2727 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_127_in_designator2749 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000080000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_constantExpression_in_designator2751 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_128_in_designator2753 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_134_in_designator2766 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_designator2769 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_127_in_arrayDesignator2785 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000080000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_constantExpression_in_arrayDesignator2787 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_130_in_arrayDesignator2789 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000080000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_constantExpression_in_arrayDesignator2791 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_128_in_arrayDesignator2793 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_attribute_in_attributes2804 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000180L});
    public static final BitSet FOLLOW_set_in_attribute2816 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_attribute2822 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_attribute2824 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF0L,0xFFFFFFFFFFFFFFFFL,0x3FFFFFFFFFFFFFFFL});
    public static final BitSet FOLLOW_attributeList_in_attribute2826 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_attribute2828 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_attribute2830 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_attrib_in_attributeList2841 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_attributeList2844 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF2L,0xFFFFFFFFFFEFFFFFL,0x3FFFFFFFFFFFFFFFL});
    public static final BitSet FOLLOW_attrib_in_attributeList2846 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_set_in_attrib2867 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF2L,0xFFFFFFFFFFAFFFFFL,0x3FFFFFFFFFFFFFFFL});
    public static final BitSet FOLLOW_83_in_attrib2881 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF0L,0xFFFFFFFFFFFFFFFFL,0x3FFFFFFFFFFFFFFFL});
    public static final BitSet FOLLOW_attributeList_in_attrib2883 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_attrib2885 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF2L,0xFFFFFFFFFFAFFFFFL,0x3FFFFFFFFFFFFFFFL});
    public static final BitSet FOLLOW_IDENTIFIER_in_primaryExpression2903 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CONSTANT_in_primaryExpression2908 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_sTRING_LITERAL_in_primaryExpression2913 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_primaryExpression2918 = new BitSet(new long[]{0x0000000000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_compoundStatement_in_primaryExpression2921 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_primaryExpression2923 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_primaryExpression2930 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000080000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_expression_in_primaryExpression2933 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_primaryExpression2935 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_137_in_primaryExpression2941 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_primaryExpression2943 = new BitSet(new long[]{0x0020000000000000L,0x0FFD9FFFE0000000L,0x00000000000001B8L});
    public static final BitSet FOLLOW_typeName_in_primaryExpression2945 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_primaryExpression2947 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_offsetofMemberDesignator_in_primaryExpression2949 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_primaryExpression2951 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTIFIER_in_offsetofMemberDesignator2976 = new BitSet(new long[]{0x0000000000000002L,0x8000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_134_in_offsetofMemberDesignator2981 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_offsetofMemberDesignator2983 = new BitSet(new long[]{0x0000000000000002L,0x8000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_127_in_offsetofMemberDesignator2989 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000080000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_expression_in_offsetofMemberDesignator2991 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_128_in_offsetofMemberDesignator2993 = new BitSet(new long[]{0x0000000000000002L,0x8000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_primaryExpression_in_postfixExpression3015 = new BitSet(new long[]{0x0000000000000002L,0x8000000000080000L,0x0000000000001C40L});
    public static final BitSet FOLLOW_83_in_postfixExpression3024 = new BitSet(new long[]{0x0020000000000000L,0x0FFD9FFFE0000000L,0x00000000000001B8L});
    public static final BitSet FOLLOW_typeName_in_postfixExpression3026 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_postfixExpression3028 = new BitSet(new long[]{0x0000000000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_109_in_postfixExpression3030 = new BitSet(new long[]{0x00E0000000000000L,0x8000200000080000L,0x0000000003FFFA42L});
    public static final BitSet FOLLOW_initializerList_in_postfixExpression3032 = new BitSet(new long[]{0x0000000000000000L,0x0000400000400000L});
    public static final BitSet FOLLOW_86_in_postfixExpression3034 = new BitSet(new long[]{0x0000000000000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_110_in_postfixExpression3037 = new BitSet(new long[]{0x0000000000000002L,0x8000000000080000L,0x0000000000001C40L});
    public static final BitSet FOLLOW_127_in_postfixExpression3055 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000080000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_expression_in_postfixExpression3057 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_128_in_postfixExpression3059 = new BitSet(new long[]{0x0000000000000002L,0x8000000000080000L,0x0000000000001C40L});
    public static final BitSet FOLLOW_83_in_postfixExpression3082 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000180000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_argumentExpressionList_in_postfixExpression3084 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_postfixExpression3087 = new BitSet(new long[]{0x0000000000000002L,0x8000000000080000L,0x0000000000001C40L});
    public static final BitSet FOLLOW_134_in_postfixExpression3111 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_postfixExpression3113 = new BitSet(new long[]{0x0000000000000002L,0x8000000000080000L,0x0000000000001C40L});
    public static final BitSet FOLLOW_138_in_postfixExpression3136 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_postfixExpression3138 = new BitSet(new long[]{0x0000000000000002L,0x8000000000080000L,0x0000000000001C40L});
    public static final BitSet FOLLOW_139_in_postfixExpression3161 = new BitSet(new long[]{0x0000000000000002L,0x8000000000080000L,0x0000000000001C40L});
    public static final BitSet FOLLOW_140_in_postfixExpression3182 = new BitSet(new long[]{0x0000000000000002L,0x8000000000080000L,0x0000000000001C40L});
    public static final BitSet FOLLOW_assignmentExpression_in_argumentExpressionList3214 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_argumentExpressionList3217 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000080000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_assignmentExpression_in_argumentExpressionList3219 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_postfixExpression_in_unaryExpression3238 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_139_in_unaryExpression3243 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000080000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpression3246 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_140_in_unaryExpression3251 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000080000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpression3254 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_unaryOperator_in_unaryExpression3259 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000080000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_castExpression_in_unaryExpression3262 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_141_in_unaryExpression3267 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000080000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpression3270 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_141_in_unaryExpression3275 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_unaryExpression3278 = new BitSet(new long[]{0x0020000000000000L,0x0FFD9FFFE0000000L,0x00000000000001B8L});
    public static final BitSet FOLLOW_typeName_in_unaryExpression3281 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_unaryExpression3283 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_142_in_unaryExpression3290 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000080000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_143_in_unaryExpression3292 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000080000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpression3295 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_142_in_unaryExpression3309 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_143_in_unaryExpression3311 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_unaryExpression3314 = new BitSet(new long[]{0x0020000000000000L,0x0FFD9FFFE0000000L,0x00000000000001B8L});
    public static final BitSet FOLLOW_typeName_in_unaryExpression3316 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_unaryExpression3318 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_144_in_unaryOperator3337 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_129_in_unaryOperator3346 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_145_in_unaryOperator3355 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_146_in_unaryOperator3364 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_147_in_unaryOperator3373 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_148_in_unaryOperator3378 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_149_in_unaryOperator3383 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_150_in_unaryOperator3392 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_151_in_unaryOperator3401 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_152_in_unaryOperator3406 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_153_in_unaryOperator3415 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_castExpression3440 = new BitSet(new long[]{0x0020000000000000L,0x0FFD9FFFE0000000L,0x00000000000001B8L});
    public static final BitSet FOLLOW_typeName_in_castExpression3441 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_castExpression3443 = new BitSet(new long[]{0x00E0000000000000L,0x0000200000080000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_castExpression_in_castExpression3447 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_109_in_castExpression3461 = new BitSet(new long[]{0x00E0000000000000L,0x8000200000080000L,0x0000000003FFFA42L});
    public static final BitSet FOLLOW_initializerList_in_castExpression3463 = new BitSet(new long[]{0x0000000000000000L,0x0000400000400000L});
    public static final BitSet FOLLOW_86_in_castExpression3465 = new BitSet(new long[]{0x0000000000000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_110_in_castExpression3468 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_unaryExpression_in_castExpression3485 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_castExpression_in_multiplicativeExpression3498 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x000000000C000002L});
    public static final BitSet FOLLOW_set_in_multiplicativeExpression3501 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000080000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_castExpression_in_multiplicativeExpression3510 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x000000000C000002L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression3523 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000060000L});
    public static final BitSet FOLLOW_set_in_additiveExpression3526 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000080000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression3533 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000060000L});
    public static final BitSet FOLLOW_additiveExpression_in_shiftExpression3546 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000030000000L});
    public static final BitSet FOLLOW_set_in_shiftExpression3549 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000080000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_additiveExpression_in_shiftExpression3556 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000030000000L});
    public static final BitSet FOLLOW_shiftExpression_in_relationalExpression3569 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x00000003C0000000L});
    public static final BitSet FOLLOW_set_in_relationalExpression3572 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000080000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_shiftExpression_in_relationalExpression3583 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x00000003C0000000L});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression3596 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000C00000000L});
    public static final BitSet FOLLOW_set_in_equalityExpression3599 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000080000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression3606 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000C00000000L});
    public static final BitSet FOLLOW_equalityExpression_in_andExpression3619 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_144_in_andExpression3622 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000080000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_equalityExpression_in_andExpression3625 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_andExpression_in_xorExpression3638 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_164_in_xorExpression3641 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000080000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_andExpression_in_xorExpression3644 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_xorExpression_in_orExpression3658 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_165_in_orExpression3661 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000080000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_xorExpression_in_orExpression3664 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_orExpression_in_landExpression3678 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_149_in_landExpression3681 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000080000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_orExpression_in_landExpression3684 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_landExpression_in_lorExpression3698 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_166_in_lorExpression3701 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000080000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_landExpression_in_lorExpression3704 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_lorExpression_in_conditionalExpression3718 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_167_in_conditionalExpression3721 = new BitSet(new long[]{0x00E0000000000000L,0x0002000000080000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_expression_in_conditionalExpression3723 = new BitSet(new long[]{0x0000000000000000L,0x0002000000000000L});
    public static final BitSet FOLLOW_113_in_conditionalExpression3726 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000080000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_conditionalExpression_in_conditionalExpression3728 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_conditionalExpression_in_assignmentExpression3776 = new BitSet(new long[]{0x0000000000000002L,0x0000000000800000L,0x0003FF0000000000L});
    public static final BitSet FOLLOW_assignmentOperator_in_assignmentExpression3779 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000080000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_assignmentExpression_in_assignmentExpression3781 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_assignmentOperator0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignmentExpression_in_expression3872 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_expression3875 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000080000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_assignmentExpression_in_expression3878 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_conditionalExpression_in_constantExpression3893 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_labeledStatement_in_statement3906 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_compoundStatement_in_statement3911 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expressionStatement_in_statement3916 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_selectionStatement_in_statement3922 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_iterationStatement_in_statement3927 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_jumpStatement_in_statement3932 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_asmStatement_in_statement3937 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTIFIER_in_labeledStatement3951 = new BitSet(new long[]{0x0000000000000000L,0x0002000000000000L});
    public static final BitSet FOLLOW_113_in_labeledStatement3953 = new BitSet(new long[]{0x00E0000000000000L,0x00002000000F8000L,0x3FDC000003FFFB82L});
    public static final BitSet FOLLOW_attributes_in_labeledStatement3955 = new BitSet(new long[]{0x00E0000000000000L,0x00002000000F8000L,0x3FDC000003FFFA02L});
    public static final BitSet FOLLOW_statement_in_labeledStatement3958 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_178_in_labeledStatement3976 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000080000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_constantExpression_in_labeledStatement3978 = new BitSet(new long[]{0x0000000000000000L,0x0002000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_130_in_labeledStatement3981 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000080000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_constantExpression_in_labeledStatement3983 = new BitSet(new long[]{0x0000000000000000L,0x0002000000000000L});
    public static final BitSet FOLLOW_113_in_labeledStatement3987 = new BitSet(new long[]{0x00E0000000000000L,0x00002000000F8000L,0x3FDC000003FFFA02L});
    public static final BitSet FOLLOW_statement_in_labeledStatement3989 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_179_in_labeledStatement4005 = new BitSet(new long[]{0x0000000000000000L,0x0002000000000000L});
    public static final BitSet FOLLOW_113_in_labeledStatement4008 = new BitSet(new long[]{0x00E0000000000000L,0x00002000000F8000L,0x3FDC000003FFFA02L});
    public static final BitSet FOLLOW_statement_in_labeledStatement4011 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_109_in_compoundStatement4033 = new BitSet(new long[]{0x00E0000000000000L,0x7FFDFFFFFF2F8000L,0x3FDC000003FFFBBAL});
    public static final BitSet FOLLOW_declaration_in_compoundStatement4038 = new BitSet(new long[]{0x00E0000000000000L,0x7FFDFFFFFF2F8000L,0x3FDC000003FFFBBAL});
    public static final BitSet FOLLOW_nestedFunctionDefinition_in_compoundStatement4044 = new BitSet(new long[]{0x00E0000000000000L,0x7FFDFFFFFF2F8000L,0x3FDC000003FFFBBAL});
    public static final BitSet FOLLOW_statement_in_compoundStatement4050 = new BitSet(new long[]{0x00E0000000000000L,0x7FFDFFFFFF2F8000L,0x3FDC000003FFFBBAL});
    public static final BitSet FOLLOW_110_in_compoundStatement4054 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_expressionStatement4083 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_expressionStatement4086 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_180_in_selectionStatement4107 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_selectionStatement4109 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000080000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_expression_in_selectionStatement4111 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_selectionStatement4113 = new BitSet(new long[]{0x00E0000000000000L,0x00002000000F8000L,0x3FDC000003FFFA02L});
    public static final BitSet FOLLOW_statement_in_selectionStatement4117 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_181_in_selectionStatement4132 = new BitSet(new long[]{0x00E0000000000000L,0x00002000000F8000L,0x3FDC000003FFFA02L});
    public static final BitSet FOLLOW_statement_in_selectionStatement4136 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_182_in_selectionStatement4158 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_selectionStatement4160 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000080000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_expression_in_selectionStatement4162 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_selectionStatement4164 = new BitSet(new long[]{0x00E0000000000000L,0x00002000000F8000L,0x3FDC000003FFFA02L});
    public static final BitSet FOLLOW_statement_in_selectionStatement4166 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_183_in_iterationStatement4188 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_iterationStatement4190 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000080000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_expression_in_iterationStatement4192 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_iterationStatement4194 = new BitSet(new long[]{0x00E0000000000000L,0x00002000000F8000L,0x3FDC000003FFFA02L});
    public static final BitSet FOLLOW_statement_in_iterationStatement4196 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_184_in_iterationStatement4211 = new BitSet(new long[]{0x00E0000000000000L,0x00002000000F8000L,0x3FDC000003FFFA02L});
    public static final BitSet FOLLOW_statement_in_iterationStatement4213 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_183_in_iterationStatement4215 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_iterationStatement4217 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000080000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_expression_in_iterationStatement4219 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_iterationStatement4221 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_iterationStatement4223 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_185_in_iterationStatement4238 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_iterationStatement4240 = new BitSet(new long[]{0x00E0000000000000L,0x7FFD9FFFFF288000L,0x0000000003FFFBBAL});
    public static final BitSet FOLLOW_declaration_in_iterationStatement4243 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000088000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_expression_in_iterationStatement4249 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_iterationStatement4252 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000088000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_expression_in_iterationStatement4257 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_iterationStatement4260 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000180000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_expression_in_iterationStatement4264 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_iterationStatement4267 = new BitSet(new long[]{0x00E0000000000000L,0x00002000000F8000L,0x3FDC000003FFFA02L});
    public static final BitSet FOLLOW_statement_in_iterationStatement4269 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_186_in_jumpStatement4317 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_jumpStatement4320 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_jumpStatement4322 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_186_in_jumpStatement4328 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_129_in_jumpStatement4330 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000080000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_expression_in_jumpStatement4332 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_jumpStatement4334 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_187_in_jumpStatement4349 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_jumpStatement4351 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_188_in_jumpStatement4357 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_jumpStatement4359 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_189_in_jumpStatement4365 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000088000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_expression_in_jumpStatement4368 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_jumpStatement4371 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_80_in_asmStatement4386 = new BitSet(new long[]{0x0000000000000000L,0x0FF8000000080000L});
    public static final BitSet FOLLOW_81_in_asmStatement4388 = new BitSet(new long[]{0x0000000000000000L,0x0FF8000000080000L});
    public static final BitSet FOLLOW_82_in_asmStatement4390 = new BitSet(new long[]{0x0000000000000000L,0x0FF8000000080000L});
    public static final BitSet FOLLOW_typeQualifier_in_asmStatement4393 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_asmStatement4396 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_asmArgument_in_asmStatement4398 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_asmStatement4400 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_asmStatement4402 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_asmStringLiteral_in_asmArgument4421 = new BitSet(new long[]{0x0000000000000002L,0x0002000000000000L});
    public static final BitSet FOLLOW_113_in_asmArgument4424 = new BitSet(new long[]{0x0080000000000002L,0x8002000000000000L});
    public static final BitSet FOLLOW_asmOperands_in_asmArgument4426 = new BitSet(new long[]{0x0000000000000002L,0x0002000000000000L});
    public static final BitSet FOLLOW_113_in_asmArgument4430 = new BitSet(new long[]{0x0080000000000002L,0x8002000000000000L});
    public static final BitSet FOLLOW_asmOperands_in_asmArgument4432 = new BitSet(new long[]{0x0000000000000002L,0x0002000000000000L});
    public static final BitSet FOLLOW_113_in_asmArgument4436 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_asmClobbers_in_asmArgument4438 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_asmOperand_in_asmOperands4457 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_asmOperands4460 = new BitSet(new long[]{0x0080000000000000L,0x8000000000000000L});
    public static final BitSet FOLLOW_asmOperand_in_asmOperands4462 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_asmStringLiteral_in_asmOperand4481 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_asmOperand4483 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000080000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_expression_in_asmOperand4485 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_asmOperand4487 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_127_in_asmOperand4492 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_asmOperand4494 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_128_in_asmOperand4496 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_asmStringLiteral_in_asmOperand4498 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_asmOperand4500 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000080000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_expression_in_asmOperand4502 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_asmOperand4504 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_asmStringLiteral_in_asmClobbers4518 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_asmClobbers4521 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_asmStringLiteral_in_asmClobbers4523 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_STRING_LITERAL_in_sTRING_LITERAL4540 = new BitSet(new long[]{0x0080000000000002L});
    public static final BitSet FOLLOW_declarationSpecifiers_in_synpred8338 = new BitSet(new long[]{0x0020000000000000L,0x0000000000080000L,0x0000000000000002L});
    public static final BitSet FOLLOW_declarator_in_synpred8340 = new BitSet(new long[]{0x0020000000000000L,0x7FFDBFFFFF000000L,0x00000000000001B8L});
    public static final BitSet FOLLOW_109_in_synpred8343 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_storageClassSpecifier_in_synpred8347 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeSpecifier_in_synpred8351 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeQualifier_in_synpred8355 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_functionSpecifier_in_synpred8359 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_135_in_synpred8362 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_136_in_synpred8366 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_declaration_in_synpred9378 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_declarationSpecifiers_in_synpred16594 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeSpecifier_in_synpred21664 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_attributes_in_synpred24685 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_attributes_in_synpred551370 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_structOrUnion_in_synpred561355 = new BitSet(new long[]{0x0020000000000000L,0x0000200000000000L,0x0000000000000180L});
    public static final BitSet FOLLOW_attributes_in_synpred561357 = new BitSet(new long[]{0x0020000000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_synpred561360 = new BitSet(new long[]{0x0000000000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_109_in_synpred561363 = new BitSet(new long[]{0x0020000000000000L,0x0FFDDFFFE0008000L,0x00000000000001B8L});
    public static final BitSet FOLLOW_structDeclaration_in_synpred561365 = new BitSet(new long[]{0x0020000000000000L,0x0FFDDFFFE0008000L,0x00000000000001B8L});
    public static final BitSet FOLLOW_110_in_synpred561368 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000180L});
    public static final BitSet FOLLOW_attributes_in_synpred561370 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_specifierQualifier_in_synpred591443 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_127_in_synpred922010 = new BitSet(new long[]{0x00E0000000000000L,0x0FF8000000080000L,0x0000000003FFFA03L});
    public static final BitSet FOLLOW_typeQualifier_in_synpred922016 = new BitSet(new long[]{0x00E0000000000000L,0x0FF8000000080000L,0x0000000003FFFA03L});
    public static final BitSet FOLLOW_assignmentExpression_in_synpred922021 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_128_in_synpred922024 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_127_in_synpred962084 = new BitSet(new long[]{0x0000000000000000L,0x0FF8000000000000L});
    public static final BitSet FOLLOW_typeQualifier_in_synpred962090 = new BitSet(new long[]{0x0000000000000000L,0x0FF8000002000000L});
    public static final BitSet FOLLOW_89_in_synpred962093 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000080000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_assignmentExpression_in_synpred962097 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_128_in_synpred962099 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_127_in_synpred982122 = new BitSet(new long[]{0x0000000000000000L,0x0FF8000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_typeQualifier_in_synpred982128 = new BitSet(new long[]{0x0000000000000000L,0x0FF8000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_129_in_synpred982131 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_128_in_synpred982133 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_synpred992153 = new BitSet(new long[]{0x0020000000000000L,0x7FFD9FFFFF000000L,0x00000000000001B8L});
    public static final BitSet FOLLOW_parameterTypeList_in_synpred992155 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_synpred992157 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_synpred1012173 = new BitSet(new long[]{0x0020000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_identifierList_in_synpred1012175 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_synpred1012178 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_declarator_in_synpred1062298 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_127_in_synpred1162419 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000080000L,0x0000000003FFFA03L});
    public static final BitSet FOLLOW_assignmentExpression_in_synpred1162421 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_128_in_synpred1162424 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_127_in_synpred1172441 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_129_in_synpred1172443 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_128_in_synpred1172445 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_synpred1202405 = new BitSet(new long[]{0x0000000000000000L,0x8000000000080000L,0x0000000000000182L});
    public static final BitSet FOLLOW_attributes_in_synpred1202407 = new BitSet(new long[]{0x0000000000000000L,0x8000000000080000L,0x0000000000000002L});
    public static final BitSet FOLLOW_abstractDeclarator_in_synpred1202410 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_synpred1202412 = new BitSet(new long[]{0x0000000000000002L,0x8000000000080000L});
    public static final BitSet FOLLOW_127_in_synpred1202419 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000080000L,0x0000000003FFFA03L});
    public static final BitSet FOLLOW_assignmentExpression_in_synpred1202421 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_128_in_synpred1202424 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_127_in_synpred1202441 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_129_in_synpred1202443 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_128_in_synpred1202445 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_synpred1202461 = new BitSet(new long[]{0x0020000000000000L,0x7FFD9FFFFF100000L,0x00000000000001B8L});
    public static final BitSet FOLLOW_parameterTypeList_in_synpred1202463 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_synpred1202466 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_127_in_synpred1222489 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000080000L,0x0000000003FFFA03L});
    public static final BitSet FOLLOW_assignmentExpression_in_synpred1222491 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_128_in_synpred1222494 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_127_in_synpred1232508 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_129_in_synpred1232510 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_128_in_synpred1232512 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_synpred1272576 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrayDesignator_in_synpred1342704 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_attribute_in_synpred1382804 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primaryExpression_in_synpred1523015 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_141_in_synpred1663267 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000080000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_unaryExpression_in_synpred1663270 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_141_in_synpred1673275 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_synpred1673278 = new BitSet(new long[]{0x0020000000000000L,0x0FFD9FFFE0000000L,0x00000000000001B8L});
    public static final BitSet FOLLOW_typeName_in_synpred1673281 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_synpred1673283 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_synpred1693289 = new BitSet(new long[]{0x00E0000000000000L,0x0000000000080000L,0x0000000003FFFA02L});
    public static final BitSet FOLLOW_unaryExpression_in_synpred1693295 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_synpred1813431 = new BitSet(new long[]{0x0020000000000000L,0x0FFD9FFFE0000000L,0x00000000000001B8L});
    public static final BitSet FOLLOW_typeName_in_synpred1813433 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_synpred1813435 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_declaration_in_synpred2264038 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nestedFunctionDefinition_in_synpred2274044 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statement_in_synpred2284050 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_declaration_in_synpred2334243 = new BitSet(new long[]{0x0000000000000002L});

}
// $ANTLR 3.0.1 XMLEmitter.g 2008-11-07 16:49:18

package cz.muni.stanse.c2xml;

import java.io.IOException;

import java.util.HashSet;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;


import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class XMLEmitter extends TreeParser {
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
    public static final int POINTER=34;
    public static final int DecimalConstant=66;
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
    public static final int FUNCTION_DECLARATOR=23;
    public static final int LINE_COMMENT=77;
    public static final int ASM=10;
    public static final int HexadecimalConstant=69;
    public static final int XSTORAGE_CLASS=41;
    public static final int ARRAY_ACCESS=30;
    public static final int HexadecimalEscape=64;
    public static final int Digit=58;
    public static final int COMPOUND_LITERAL=29;
    public static final int DECLARATOR=33;
    public static final int ARRAY_DECLARATOR=24;
    public static final int DESIGNATOR=26;
    public static final int STR_LITERAL=52;
    public static final int WS=75;
    public static final int BUILTIN_OFFSETOF=18;
    public static final int LABEL=48;
    public static final int TYPEOF=20;
    public static final int STRUCT_DECLARATION=36;
    public static final int LABREF=17;
    public static final int TRANSLATION_UNIT=28;
    public static final int COMPOUND_STATEMENT=49;
    public static final int LINE_COMMAND=78;
    public static final int CONSTANT=54;
    public static final int INIT_DECLARATOR=43;
    public static final int IntegerSuffix=67;
    public static final int DECLARATION_SPECIFIERS=44;
    public static final int EXPRESSION_STATEMENT=9;
    public static final int FloatingSuffix=73;
    public static final int XID=38;
    public static final int BASETYPE=45;
    public static final int FUNCTION_CALL=51;
    public static final int EXTENSION=56;
    public static final int EscapeSequence=59;
    public static final int OctalEscape=63;
    protected static class Symbols_scope {
        Stack<String> variables;
        Stack<String> variablesOld;
    }
    protected Stack Symbols_stack = new Stack();


        public XMLEmitter(TreeNodeStream input) {
            super(input);
        }
        

    public String[] getTokenNames() { return tokenNames; }
    public String getGrammarFileName() { return "XMLEmitter.g"; }


    	protected Document xmlDocument = DocumentHelper.createDocument();
    	protected DocumentFactory xmlFactory = DocumentFactory.getInstance();
    	private int uniqCnt;
    	private boolean symbolsEnabled = true;

    	/* configuration */
    	final private Boolean normalizeTypes = false;
    	final private Boolean uniqueVariables = true;
    	final private Boolean uniqueVariablesDebug = false;
    	final private Boolean printXML = false;

    	private void outputXML() {
    		if (!printXML)
    			return;
    		OutputFormat format = OutputFormat.createPrettyPrint();
    		try {
    			XMLWriter writer = new XMLWriter(System.out, format);
    			writer.write(xmlDocument);
    		} catch (IOException e) {
    			System.err.println("write failed: " + e);
    		}
    	}
    	private Element newElement(String text) {
    		return xmlFactory.createElement(text);
    	}
    	private Element newListElement(List<Element> els, String text) {
    		Element e = newElement(text);
    		els.add(e);
    		return e;
    	}
    	private Element addElementBin(Element dest, String name, Element e1, Element e2) {
    		Element e = dest.addElement(name);
    		e.add(e1);
    		e.add(e2);
    		return e;
    	}
    	private void addElementCond(Element dest, Element src) {
    		if (src != null)
    			dest.add(src);
    	}
    	private void addAllElements(Element dest, List<Element> src) {
    		if (src == null)
    			return;
    		for (Element el: src)
    			dest.add(el);
    	}

    	/* TODO check bitfields */
    	private List<Element> typeNormalize(List<Element> tss) {
    		if (!normalizeTypes || tss.isEmpty())
    			return tss;

    		final String[][] rewrite = {
    			{ "signed short", "short" },
    			{ "short int", "short" },
    			{ "signed short int", "short" },
    			{ "unsigned short int", "unsigned short" },
    			{ "signed", "int" },
    			{ "signed int", "int" },
    			{ "unsigned int", "unsigned" },
    			{ "signed long", "long" },
    			{ "long int", "long" },
    			{ "signed long int", "long" },
    			{ "unsigned long int", "unsigned long" },
    			{ "signed long long", "long long" },
    			{ "long long int", "long long" },
    			{ "signed long long int", "long long" },
    			{ "unsigned long long int", "unsigned long long" },
    		};
    		StringBuilder sb = new StringBuilder();
    		Boolean hadBase = false;

    		for (Element ts: tss) {
    			if (ts.element("baseType") == null) {
    				if (hadBase)
    					throw new RuntimeException("non-baseType among baseType?");
    				continue;
    			}
    			hadBase = true;
    			sb.append(ts.element("baseType").getText()).append(" ");
    		}
    		if (sb.length() == 0) /* no baseType */
    			return tss;
    		sb.setLength(sb.length() - 1);

    		String type = sb.toString();
    		for (String[] rule: rewrite)
    			if (rule[0].equals(type)) {
    				tss = new ArrayList<Element>();
    				for (String baseType: rule[1].split(" "))
    					newListElement(tss, "typeSpecifier").addElement("baseType").addText(baseType);
    				break;
    			}
    		return tss;
    	}

    	private String renameVariable(String old) {
    		if (!uniqueVariables)
    			return old;
    		String new_ = old;

    		while (((Symbols_scope)Symbols_stack.peek()).variables.search(new_) > 0)
    			new_ = old + "_" + uniqCnt++;
    		return new_;
    	}

    	private void pushSymbol(String old, String new_, int type) {
    		if (!uniqueVariables || !symbolsEnabled)
    			return;
    		((Symbols_scope)Symbols_stack.peek()).variablesOld.push(old);
    		((Symbols_scope)Symbols_stack.peek()).variables.push(new_);
    		if (uniqueVariablesDebug && type != 0) {
    			System.out.println(((Symbols_scope)Symbols_stack.peek()).variablesOld);
    			System.out.println(((Symbols_scope)Symbols_stack.peek()).variables + " added" + type + ": " + new_);
    		}
    	}

    	private void popUntil(String s) {
    		if (!uniqueVariables)
    			return;
    		((Symbols_scope)Symbols_stack.peek()).variablesOld.pop();
    		while (!((Symbols_scope)Symbols_stack.peek()).variables.pop().equals(s))
    			((Symbols_scope)Symbols_stack.peek()).variablesOld.pop();
    	}

    	private String findVariable(String old) {
    		if (!uniqueVariables)
    			return old;
    		/* find topmost variable (index is bottom based) */
    		int idx = ((Symbols_scope)Symbols_stack.peek()).variablesOld.lastIndexOf(old);
    		if (idx < 0)
    			return old; /* throw an exception? */
    		return ((Symbols_scope)Symbols_stack.peek()).variables.elementAt(idx);
    	}



    // $ANTLR start translationUnit
    // XMLEmitter.g:174:1: translationUnit returns [Document d] : ^( TRANSLATION_UNIT (eds= externalDeclaration )* ) ;
    public final Document translationUnit() throws RecognitionException {
        Symbols_stack.push(new Symbols_scope());

        Document d = null;

        Element eds = null;



        	Element root = xmlDocument.addElement("translationUnit");
        	((Symbols_scope)Symbols_stack.peek()).variables = new Stack<String>();
        	((Symbols_scope)Symbols_stack.peek()).variablesOld = new Stack<String>();

        try {
            // XMLEmitter.g:181:2: ( ^( TRANSLATION_UNIT (eds= externalDeclaration )* ) )
            // XMLEmitter.g:181:4: ^( TRANSLATION_UNIT (eds= externalDeclaration )* )
            {
            match(input,TRANSLATION_UNIT,FOLLOW_TRANSLATION_UNIT_in_translationUnit65); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // XMLEmitter.g:181:23: (eds= externalDeclaration )*
                loop1:
                do {
                    int alt1=2;
                    int LA1_0 = input.LA(1);

                    if ( (LA1_0==FUNCTION_DEFINITION||LA1_0==DECLARATION||LA1_0==85) ) {
                        alt1=1;
                    }


                    switch (alt1) {
                	case 1 :
                	    // XMLEmitter.g:181:24: eds= externalDeclaration
                	    {
                	    pushFollow(FOLLOW_externalDeclaration_in_translationUnit70);
                	    eds=externalDeclaration();
                	    _fsp--;

                	    root.add(eds);

                	    }
                	    break;

                	default :
                	    break loop1;
                    }
                } while (true);


                match(input, Token.UP, null); 
            }

            		xmlDocument.setRootElement(root);
            		outputXML();
            		d = xmlDocument;
            	

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            Symbols_stack.pop();

        }
        return d;
    }
    // $ANTLR end translationUnit


    // $ANTLR start externalDeclaration
    // XMLEmitter.g:188:1: externalDeclaration returns [Element e] : ( functionDefinition | declaration );
    public final Element externalDeclaration() throws RecognitionException {
        Element e = null;

        Element functionDefinition1 = null;

        Element declaration2 = null;



        	e = newElement("externalDeclaration");

        try {
            // XMLEmitter.g:192:2: ( functionDefinition | declaration )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==FUNCTION_DEFINITION) ) {
                alt2=1;
            }
            else if ( (LA2_0==DECLARATION||LA2_0==85) ) {
                alt2=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("188:1: externalDeclaration returns [Element e] : ( functionDefinition | declaration );", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // XMLEmitter.g:192:4: functionDefinition
                    {
                    pushFollow(FOLLOW_functionDefinition_in_externalDeclaration98);
                    functionDefinition1=functionDefinition();
                    _fsp--;

                     e.add(functionDefinition1); 

                    }
                    break;
                case 2 :
                    // XMLEmitter.g:193:4: declaration
                    {
                    pushFollow(FOLLOW_declaration_in_externalDeclaration105);
                    declaration2=declaration();
                    _fsp--;

                     e.add(declaration2); 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return e;
    }
    // $ANTLR end externalDeclaration


    // $ANTLR start functionDefinition
    // XMLEmitter.g:196:1: functionDefinition returns [Element e] : ^( FUNCTION_DEFINITION declarationSpecifiers declarator (d= declaration )* compoundStatement ) ;
    public final Element functionDefinition() throws RecognitionException {
        Element e = null;

        Element d = null;

        Element declarationSpecifiers3 = null;

        Element declarator4 = null;

        Element compoundStatement5 = null;



        	List<Element> ds = new ArrayList<Element>();
        	e = newElement("functionDefinition");

        try {
            // XMLEmitter.g:201:2: ( ^( FUNCTION_DEFINITION declarationSpecifiers declarator (d= declaration )* compoundStatement ) )
            // XMLEmitter.g:201:4: ^( FUNCTION_DEFINITION declarationSpecifiers declarator (d= declaration )* compoundStatement )
            {
            match(input,FUNCTION_DEFINITION,FOLLOW_FUNCTION_DEFINITION_in_functionDefinition129); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_declarationSpecifiers_in_functionDefinition131);
            declarationSpecifiers3=declarationSpecifiers();
            _fsp--;

            pushFollow(FOLLOW_declarator_in_functionDefinition133);
            declarator4=declarator();
            _fsp--;

            // XMLEmitter.g:201:59: (d= declaration )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==DECLARATION||LA3_0==85) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // XMLEmitter.g:201:60: d= declaration
            	    {
            	    pushFollow(FOLLOW_declaration_in_functionDefinition138);
            	    d=declaration();
            	    _fsp--;

            	    ds.add(d);

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

            pushFollow(FOLLOW_compoundStatement_in_functionDefinition144);
            compoundStatement5=compoundStatement();
            _fsp--;


            match(input, Token.UP, null); 

            		e.add(declarationSpecifiers3);
            		e.add(declarator4);
            		addAllElements(e, ds);
            		e.add(compoundStatement5);
            		popUntil("-");
            	

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return e;
    }
    // $ANTLR end functionDefinition


    // $ANTLR start declaration
    // XMLEmitter.g:210:1: declaration returns [Element e] : ( ^( 'typedef' ( declarationSpecifiers )? (id= initDeclarator )* ) | ^( DECLARATION declarationSpecifiers (id= initDeclarator )* ) );
    public final Element declaration() throws RecognitionException {
        Element e = null;

        Element id = null;

        Element declarationSpecifiers6 = null;

        Element declarationSpecifiers7 = null;



        	List<Element> ids = new ArrayList<Element>();
        	e = newElement("declaration");

        try {
            // XMLEmitter.g:215:2: ( ^( 'typedef' ( declarationSpecifiers )? (id= initDeclarator )* ) | ^( DECLARATION declarationSpecifiers (id= initDeclarator )* ) )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==85) ) {
                alt7=1;
            }
            else if ( (LA7_0==DECLARATION) ) {
                alt7=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("210:1: declaration returns [Element e] : ( ^( 'typedef' ( declarationSpecifiers )? (id= initDeclarator )* ) | ^( DECLARATION declarationSpecifiers (id= initDeclarator )* ) );", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // XMLEmitter.g:215:4: ^( 'typedef' ( declarationSpecifiers )? (id= initDeclarator )* )
                    {
                    match(input,85,FOLLOW_85_in_declaration168); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // XMLEmitter.g:215:16: ( declarationSpecifiers )?
                        int alt4=2;
                        int LA4_0 = input.LA(1);

                        if ( (LA4_0==DECLARATION_SPECIFIERS) ) {
                            alt4=1;
                        }
                        switch (alt4) {
                            case 1 :
                                // XMLEmitter.g:215:16: declarationSpecifiers
                                {
                                pushFollow(FOLLOW_declarationSpecifiers_in_declaration170);
                                declarationSpecifiers6=declarationSpecifiers();
                                _fsp--;


                                }
                                break;

                        }

                        // XMLEmitter.g:215:39: (id= initDeclarator )*
                        loop5:
                        do {
                            int alt5=2;
                            int LA5_0 = input.LA(1);

                            if ( (LA5_0==INIT_DECLARATOR) ) {
                                alt5=1;
                            }


                            switch (alt5) {
                        	case 1 :
                        	    // XMLEmitter.g:215:40: id= initDeclarator
                        	    {
                        	    pushFollow(FOLLOW_initDeclarator_in_declaration176);
                        	    id=initDeclarator();
                        	    _fsp--;

                        	    ids.add(id);

                        	    }
                        	    break;

                        	default :
                        	    break loop5;
                            }
                        } while (true);


                        match(input, Token.UP, null); 
                    }

                    		Element ds;
                    		if (declarationSpecifiers6 != null)
                    			e.add(ds = declarationSpecifiers6);
                    		else
                    			ds = e.addElement("declarationSpecifiers");
                    		ds.addAttribute("storageClass", "typedef");
                    		addAllElements(e, ids);
                    	

                    }
                    break;
                case 2 :
                    // XMLEmitter.g:224:4: ^( DECLARATION declarationSpecifiers (id= initDeclarator )* )
                    {
                    match(input,DECLARATION,FOLLOW_DECLARATION_in_declaration189); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_declarationSpecifiers_in_declaration191);
                    declarationSpecifiers7=declarationSpecifiers();
                    _fsp--;

                    // XMLEmitter.g:224:40: (id= initDeclarator )*
                    loop6:
                    do {
                        int alt6=2;
                        int LA6_0 = input.LA(1);

                        if ( (LA6_0==INIT_DECLARATOR) ) {
                            alt6=1;
                        }


                        switch (alt6) {
                    	case 1 :
                    	    // XMLEmitter.g:224:41: id= initDeclarator
                    	    {
                    	    pushFollow(FOLLOW_initDeclarator_in_declaration196);
                    	    id=initDeclarator();
                    	    _fsp--;

                    	    ids.add(id);

                    	    }
                    	    break;

                    	default :
                    	    break loop6;
                        }
                    } while (true);


                    match(input, Token.UP, null); 

                    		e.add(declarationSpecifiers7);
                    		addAllElements(e, ids);
                    	

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return e;
    }
    // $ANTLR end declaration


    // $ANTLR start declarationSpecifiers
    // XMLEmitter.g:230:1: declarationSpecifiers returns [Element e] : ^( DECLARATION_SPECIFIERS ^( XTYPE_SPECIFIER (ts= typeSpecifier )* ) ^( XTYPE_QUALIFIER (tq= typeQualifier )* ) ^( XSTORAGE_CLASS (sc= storageClassSpecifier | fs= functionSpecifier )* ) ) ;
    public final Element declarationSpecifiers() throws RecognitionException {
        Element e = null;

        Element ts = null;

        String tq = null;

        String sc = null;

        String fs = null;



        	List<Element> tss = new ArrayList<Element>();
        	Set<String> tqs = new HashSet<String>();
        	Set<String> scs = new HashSet<String>();
        	Set<String> fss = new HashSet<String>();

        try {
            // XMLEmitter.g:237:2: ( ^( DECLARATION_SPECIFIERS ^( XTYPE_SPECIFIER (ts= typeSpecifier )* ) ^( XTYPE_QUALIFIER (tq= typeQualifier )* ) ^( XSTORAGE_CLASS (sc= storageClassSpecifier | fs= functionSpecifier )* ) ) )
            // XMLEmitter.g:237:4: ^( DECLARATION_SPECIFIERS ^( XTYPE_SPECIFIER (ts= typeSpecifier )* ) ^( XTYPE_QUALIFIER (tq= typeQualifier )* ) ^( XSTORAGE_CLASS (sc= storageClassSpecifier | fs= functionSpecifier )* ) )
            {
            match(input,DECLARATION_SPECIFIERS,FOLLOW_DECLARATION_SPECIFIERS_in_declarationSpecifiers224); 

            match(input, Token.DOWN, null); 
            match(input,XTYPE_SPECIFIER,FOLLOW_XTYPE_SPECIFIER_in_declarationSpecifiers227); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // XMLEmitter.g:237:47: (ts= typeSpecifier )*
                loop8:
                do {
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0==TYPEOF||LA8_0==BASETYPE||LA8_0==IDENTIFIER||(LA8_0>=111 && LA8_0<=112)||LA8_0==114) ) {
                        alt8=1;
                    }


                    switch (alt8) {
                	case 1 :
                	    // XMLEmitter.g:237:48: ts= typeSpecifier
                	    {
                	    pushFollow(FOLLOW_typeSpecifier_in_declarationSpecifiers232);
                	    ts=typeSpecifier();
                	    _fsp--;

                	    tss.add(ts);

                	    }
                	    break;

                	default :
                	    break loop8;
                    }
                } while (true);


                match(input, Token.UP, null); 
            }
            match(input,XTYPE_QUALIFIER,FOLLOW_XTYPE_QUALIFIER_in_declarationSpecifiers240); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // XMLEmitter.g:237:101: (tq= typeQualifier )*
                loop9:
                do {
                    int alt9=2;
                    int LA9_0 = input.LA(1);

                    if ( (LA9_0==115||LA9_0==118||LA9_0==121) ) {
                        alt9=1;
                    }


                    switch (alt9) {
                	case 1 :
                	    // XMLEmitter.g:237:102: tq= typeQualifier
                	    {
                	    pushFollow(FOLLOW_typeQualifier_in_declarationSpecifiers245);
                	    tq=typeQualifier();
                	    _fsp--;

                	    tqs.add(tq);

                	    }
                	    break;

                	default :
                	    break loop9;
                    }
                } while (true);


                match(input, Token.UP, null); 
            }
            match(input,XSTORAGE_CLASS,FOLLOW_XSTORAGE_CLASS_in_declarationSpecifiers253); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // XMLEmitter.g:237:154: (sc= storageClassSpecifier | fs= functionSpecifier )*
                loop10:
                do {
                    int alt10=3;
                    int LA10_0 = input.LA(1);

                    if ( ((LA10_0>=88 && LA10_0<=92)) ) {
                        alt10=1;
                    }
                    else if ( (LA10_0==124) ) {
                        alt10=2;
                    }


                    switch (alt10) {
                	case 1 :
                	    // XMLEmitter.g:237:155: sc= storageClassSpecifier
                	    {
                	    pushFollow(FOLLOW_storageClassSpecifier_in_declarationSpecifiers258);
                	    sc=storageClassSpecifier();
                	    _fsp--;

                	    scs.add(sc);

                	    }
                	    break;
                	case 2 :
                	    // XMLEmitter.g:237:195: fs= functionSpecifier
                	    {
                	    pushFollow(FOLLOW_functionSpecifier_in_declarationSpecifiers264);
                	    fs=functionSpecifier();
                	    _fsp--;

                	    fss.add(fs);

                	    }
                	    break;

                	default :
                	    break loop10;
                    }
                } while (true);


                match(input, Token.UP, null); 
            }

            match(input, Token.UP, null); 

            		e = newElement("declarationSpecifiers");
            		addAllElements(e, typeNormalize(tss));
            		for (String str: tqs)
            			e.addAttribute(str, "1");
            		for (String str: scs)
            			e.addAttribute("storageClass", str);
            		for (String str: fss)
            			e.addAttribute("function", str);
            	

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return e;
    }
    // $ANTLR end declarationSpecifiers


    // $ANTLR start declarator
    // XMLEmitter.g:249:1: declarator returns [Element e] : ^( DECLARATOR ( pointer )? directDeclarator ) ;
    public final Element declarator() throws RecognitionException {
        Element e = null;

        List<Element> pointer8 = null;

        List<Element> directDeclarator9 = null;


        try {
            // XMLEmitter.g:250:2: ( ^( DECLARATOR ( pointer )? directDeclarator ) )
            // XMLEmitter.g:250:4: ^( DECLARATOR ( pointer )? directDeclarator )
            {
            match(input,DECLARATOR,FOLLOW_DECLARATOR_in_declarator288); 

            match(input, Token.DOWN, null); 
            // XMLEmitter.g:250:17: ( pointer )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==POINTER) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // XMLEmitter.g:250:17: pointer
                    {
                    pushFollow(FOLLOW_pointer_in_declarator290);
                    pointer8=pointer();
                    _fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_directDeclarator_in_declarator293);
            directDeclarator9=directDeclarator();
            _fsp--;


            match(input, Token.UP, null); 

            		e = newElement("declarator");
            		addAllElements(e, pointer8);
            		addAllElements(e, directDeclarator9);
            	

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return e;
    }
    // $ANTLR end declarator


    // $ANTLR start directDeclarator
    // XMLEmitter.g:257:1: directDeclarator returns [List<Element> els] : ( IDENTIFIER | declarator | directDeclarator1 );
    public final List<Element> directDeclarator() throws RecognitionException {
        List<Element> els = null;

        CommonTree IDENTIFIER10=null;
        Element declarator11 = null;

        List<Element> directDeclarator112 = null;



        	els = new ArrayList<Element>();

        try {
            // XMLEmitter.g:261:2: ( IDENTIFIER | declarator | directDeclarator1 )
            int alt12=3;
            switch ( input.LA(1) ) {
            case IDENTIFIER:
                {
                alt12=1;
                }
                break;
            case DECLARATOR:
                {
                alt12=2;
                }
                break;
            case FUNCTION_DECLARATOR:
            case ARRAY_DECLARATOR:
                {
                alt12=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("257:1: directDeclarator returns [List<Element> els] : ( IDENTIFIER | declarator | directDeclarator1 );", 12, 0, input);

                throw nvae;
            }

            switch (alt12) {
                case 1 :
                    // XMLEmitter.g:261:4: IDENTIFIER
                    {
                    IDENTIFIER10=(CommonTree)input.LT(1);
                    match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_directDeclarator316); 

                    		String newName = renameVariable(IDENTIFIER10.getText());
                    		if (!newName.equals(IDENTIFIER10.getText()))
                    			newListElement(els, "oldId").addText(IDENTIFIER10.getText());
                    		newListElement(els, "id").addText(newName);
                    		pushSymbol(IDENTIFIER10.getText(), newName, 1);
                    	

                    }
                    break;
                case 2 :
                    // XMLEmitter.g:268:4: declarator
                    {
                    pushFollow(FOLLOW_declarator_in_directDeclarator323);
                    declarator11=declarator();
                    _fsp--;

                     newListElement(els, "declarator").add(declarator11); 

                    }
                    break;
                case 3 :
                    // XMLEmitter.g:269:4: directDeclarator1
                    {
                    pushFollow(FOLLOW_directDeclarator1_in_directDeclarator330);
                    directDeclarator112=directDeclarator1();
                    _fsp--;

                     els = directDeclarator112; 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return els;
    }
    // $ANTLR end directDeclarator


    // $ANTLR start directDeclarator1
    // XMLEmitter.g:272:1: directDeclarator1 returns [List<Element> els] : ( ^( ARRAY_DECLARATOR ( IDENTIFIER | dd= directDeclarator1 ) ( 'static' | asterisk= '*' )? (tq= typeQualifier )* ( expression )? ) | ^( FUNCTION_DECLARATOR ( IDENTIFIER | declarator ) (pl= parameterTypeList | (i= identifier )* ) ) );
    public final List<Element> directDeclarator1() throws RecognitionException {
        List<Element> els = null;

        CommonTree asterisk=null;
        CommonTree IDENTIFIER13=null;
        CommonTree IDENTIFIER15=null;
        List<Element> dd = null;

        String tq = null;

        List<Element> pl = null;

        Element i = null;

        Element expression14 = null;

        Element declarator16 = null;



        	Set<String> tqs = new HashSet<String>();
        	List<Element> l = new ArrayList<Element>();
        	els = new ArrayList<Element>();

        try {
            // XMLEmitter.g:278:2: ( ^( ARRAY_DECLARATOR ( IDENTIFIER | dd= directDeclarator1 ) ( 'static' | asterisk= '*' )? (tq= typeQualifier )* ( expression )? ) | ^( FUNCTION_DECLARATOR ( IDENTIFIER | declarator ) (pl= parameterTypeList | (i= identifier )* ) ) )
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==ARRAY_DECLARATOR) ) {
                alt20=1;
            }
            else if ( (LA20_0==FUNCTION_DECLARATOR) ) {
                alt20=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("272:1: directDeclarator1 returns [List<Element> els] : ( ^( ARRAY_DECLARATOR ( IDENTIFIER | dd= directDeclarator1 ) ( 'static' | asterisk= '*' )? (tq= typeQualifier )* ( expression )? ) | ^( FUNCTION_DECLARATOR ( IDENTIFIER | declarator ) (pl= parameterTypeList | (i= identifier )* ) ) );", 20, 0, input);

                throw nvae;
            }
            switch (alt20) {
                case 1 :
                    // XMLEmitter.g:278:4: ^( ARRAY_DECLARATOR ( IDENTIFIER | dd= directDeclarator1 ) ( 'static' | asterisk= '*' )? (tq= typeQualifier )* ( expression )? )
                    {
                    match(input,ARRAY_DECLARATOR,FOLLOW_ARRAY_DECLARATOR_in_directDeclarator1355); 

                    match(input, Token.DOWN, null); 
                    // XMLEmitter.g:278:23: ( IDENTIFIER | dd= directDeclarator1 )
                    int alt13=2;
                    int LA13_0 = input.LA(1);

                    if ( (LA13_0==IDENTIFIER) ) {
                        alt13=1;
                    }
                    else if ( ((LA13_0>=FUNCTION_DECLARATOR && LA13_0<=ARRAY_DECLARATOR)) ) {
                        alt13=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("278:23: ( IDENTIFIER | dd= directDeclarator1 )", 13, 0, input);

                        throw nvae;
                    }
                    switch (alt13) {
                        case 1 :
                            // XMLEmitter.g:278:24: IDENTIFIER
                            {
                            IDENTIFIER13=(CommonTree)input.LT(1);
                            match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_directDeclarator1358); 

                            }
                            break;
                        case 2 :
                            // XMLEmitter.g:278:35: dd= directDeclarator1
                            {
                            pushFollow(FOLLOW_directDeclarator1_in_directDeclarator1362);
                            dd=directDeclarator1();
                            _fsp--;


                            }
                            break;

                    }

                    // XMLEmitter.g:278:57: ( 'static' | asterisk= '*' )?
                    int alt14=3;
                    int LA14_0 = input.LA(1);

                    if ( (LA14_0==89) ) {
                        alt14=1;
                    }
                    else if ( (LA14_0==129) ) {
                        int LA14_2 = input.LA(2);

                        if ( (LA14_2==UP||(LA14_2>=PP && LA14_2<=BUILTIN_OFFSETOF)||LA14_2==ASSIGNMENT_EXPRESSION||(LA14_2>=COMPOUND_LITERAL && LA14_2<=ARRAY_ACCESS)||(LA14_2>=CAST_EXPRESSION && LA14_2<=CONDITIONAL_EXPRESSION)||LA14_2==COMPOUND_STATEMENT||(LA14_2>=FUNCTION_CALL && LA14_2<=CONSTANT)||LA14_2==86||LA14_2==115||LA14_2==118||LA14_2==121||LA14_2==129||LA14_2==134||(LA14_2>=138 && LA14_2<=141)||(LA14_2>=143 && LA14_2<=149)||LA14_2==151||(LA14_2>=153 && LA14_2<=166)) ) {
                            alt14=2;
                        }
                    }
                    switch (alt14) {
                        case 1 :
                            // XMLEmitter.g:278:58: 'static'
                            {
                            match(input,89,FOLLOW_89_in_directDeclarator1366); 
                            tqs.add("static");

                            }
                            break;
                        case 2 :
                            // XMLEmitter.g:278:88: asterisk= '*'
                            {
                            asterisk=(CommonTree)input.LT(1);
                            match(input,129,FOLLOW_129_in_directDeclarator1372); 

                            }
                            break;

                    }

                    // XMLEmitter.g:278:103: (tq= typeQualifier )*
                    loop15:
                    do {
                        int alt15=2;
                        int LA15_0 = input.LA(1);

                        if ( (LA15_0==115||LA15_0==118||LA15_0==121) ) {
                            alt15=1;
                        }


                        switch (alt15) {
                    	case 1 :
                    	    // XMLEmitter.g:278:104: tq= typeQualifier
                    	    {
                    	    pushFollow(FOLLOW_typeQualifier_in_directDeclarator1379);
                    	    tq=typeQualifier();
                    	    _fsp--;

                    	    tqs.add(tq);

                    	    }
                    	    break;

                    	default :
                    	    break loop15;
                        }
                    } while (true);

                    // XMLEmitter.g:278:138: ( expression )?
                    int alt16=2;
                    int LA16_0 = input.LA(1);

                    if ( ((LA16_0>=PP && LA16_0<=BUILTIN_OFFSETOF)||LA16_0==ASSIGNMENT_EXPRESSION||(LA16_0>=COMPOUND_LITERAL && LA16_0<=ARRAY_ACCESS)||(LA16_0>=CAST_EXPRESSION && LA16_0<=CONDITIONAL_EXPRESSION)||LA16_0==COMPOUND_STATEMENT||(LA16_0>=FUNCTION_CALL && LA16_0<=CONSTANT)||LA16_0==86||LA16_0==129||LA16_0==134||(LA16_0>=138 && LA16_0<=141)||(LA16_0>=143 && LA16_0<=149)||LA16_0==151||(LA16_0>=153 && LA16_0<=166)) ) {
                        alt16=1;
                    }
                    switch (alt16) {
                        case 1 :
                            // XMLEmitter.g:278:138: expression
                            {
                            pushFollow(FOLLOW_expression_in_directDeclarator1385);
                            expression14=expression();
                            _fsp--;


                            }
                            break;

                    }


                    match(input, Token.UP, null); 

                    		if (IDENTIFIER13 != null) {
                    			String newName = renameVariable(IDENTIFIER13.getText());
                    			if (!newName.equals(IDENTIFIER13.getText()))
                    				newListElement(els, "oldId").addText(IDENTIFIER13.getText());
                    			newListElement(els, "id").addText(newName);
                    			pushSymbol(IDENTIFIER13.getText(), newName, 2);
                    		} else
                    			addAllElements(newListElement(els, "declarator"), dd);
                    		Element e = newListElement(els, "arrayDecl");
                    		for (String t: tqs)
                    			e.addAttribute(t, "1");
                    		if (asterisk != null)
                    			e.addElement("asterisk");
                    		addElementCond(e, expression14);
                    	

                    }
                    break;
                case 2 :
                    // XMLEmitter.g:294:4: ^( FUNCTION_DECLARATOR ( IDENTIFIER | declarator ) (pl= parameterTypeList | (i= identifier )* ) )
                    {
                    match(input,FUNCTION_DECLARATOR,FOLLOW_FUNCTION_DECLARATOR_in_directDeclarator1395); 

                    match(input, Token.DOWN, null); 
                    // XMLEmitter.g:294:26: ( IDENTIFIER | declarator )
                    int alt17=2;
                    int LA17_0 = input.LA(1);

                    if ( (LA17_0==IDENTIFIER) ) {
                        alt17=1;
                    }
                    else if ( (LA17_0==DECLARATOR) ) {
                        alt17=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("294:26: ( IDENTIFIER | declarator )", 17, 0, input);

                        throw nvae;
                    }
                    switch (alt17) {
                        case 1 :
                            // XMLEmitter.g:294:27: IDENTIFIER
                            {
                            IDENTIFIER15=(CommonTree)input.LT(1);
                            match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_directDeclarator1398); 
                             /* we need to process the id before params */
                            				String newName = renameVariable(IDENTIFIER15.getText());
                            				if (!newName.equals(IDENTIFIER15.getText()))
                            					newListElement(els, "oldId").addText(IDENTIFIER15.getText());
                            				newListElement(els, "id").addText(newName);
                            				pushSymbol(IDENTIFIER15.getText(), newName, 3);
                            				pushSymbol("-", "-", 0); /* see functionDefinition */
                            			

                            }
                            break;
                        case 2 :
                            // XMLEmitter.g:301:6: declarator
                            {
                            pushFollow(FOLLOW_declarator_in_directDeclarator1402);
                            declarator16=declarator();
                            _fsp--;


                            }
                            break;

                    }

                    // XMLEmitter.g:301:18: (pl= parameterTypeList | (i= identifier )* )
                    int alt19=2;
                    int LA19_0 = input.LA(1);

                    if ( (LA19_0==PARAMETER) ) {
                        int LA19_1 = input.LA(2);

                        if ( (LA19_1==DOWN) ) {
                            int LA19_3 = input.LA(3);

                            if ( (LA19_3==IDENTIFIER) ) {
                                alt19=2;
                            }
                            else if ( (LA19_3==DECLARATION_SPECIFIERS) ) {
                                alt19=1;
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("301:18: (pl= parameterTypeList | (i= identifier )* )", 19, 3, input);

                                throw nvae;
                            }
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("301:18: (pl= parameterTypeList | (i= identifier )* )", 19, 1, input);

                            throw nvae;
                        }
                    }
                    else if ( (LA19_0==UP) ) {
                        alt19=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("301:18: (pl= parameterTypeList | (i= identifier )* )", 19, 0, input);

                        throw nvae;
                    }
                    switch (alt19) {
                        case 1 :
                            // XMLEmitter.g:301:19: pl= parameterTypeList
                            {
                            pushFollow(FOLLOW_parameterTypeList_in_directDeclarator1408);
                            pl=parameterTypeList();
                            _fsp--;


                            }
                            break;
                        case 2 :
                            // XMLEmitter.g:301:40: (i= identifier )*
                            {
                            // XMLEmitter.g:301:40: (i= identifier )*
                            loop18:
                            do {
                                int alt18=2;
                                int LA18_0 = input.LA(1);

                                if ( (LA18_0==PARAMETER) ) {
                                    alt18=1;
                                }


                                switch (alt18) {
                            	case 1 :
                            	    // XMLEmitter.g:301:41: i= identifier
                            	    {
                            	    pushFollow(FOLLOW_identifier_in_directDeclarator1413);
                            	    i=identifier();
                            	    _fsp--;

                            	    l.add(i);

                            	    }
                            	    break;

                            	default :
                            	    break loop18;
                                }
                            } while (true);


                            }
                            break;

                    }


                    match(input, Token.UP, null); 

                    		if (IDENTIFIER15 == null)
                    			els.add(declarator16);
                    		Element e = newListElement(els, "functionDecl");
                    		addAllElements(e, pl != null ? pl : l);
                    	

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return els;
    }
    // $ANTLR end directDeclarator1


    // $ANTLR start initDeclarator
    // XMLEmitter.g:309:1: initDeclarator returns [Element e] : ^( INIT_DECLARATOR declarator ( initializer )? ) ;
    public final Element initDeclarator() throws RecognitionException {
        Element e = null;

        Element declarator17 = null;

        Element initializer18 = null;


        try {
            // XMLEmitter.g:310:2: ( ^( INIT_DECLARATOR declarator ( initializer )? ) )
            // XMLEmitter.g:310:4: ^( INIT_DECLARATOR declarator ( initializer )? )
            {
            match(input,INIT_DECLARATOR,FOLLOW_INIT_DECLARATOR_in_initDeclarator437); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_declarator_in_initDeclarator439);
            declarator17=declarator();
            _fsp--;

            // XMLEmitter.g:310:33: ( initializer )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==INITIALIZER) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // XMLEmitter.g:310:33: initializer
                    {
                    pushFollow(FOLLOW_initializer_in_initDeclarator441);
                    initializer18=initializer();
                    _fsp--;


                    }
                    break;

            }


            match(input, Token.UP, null); 

            		e = newElement("initDeclarator");
            		e.add(declarator17);
            		addElementCond(e, initializer18);
            	

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return e;
    }
    // $ANTLR end initDeclarator


    // $ANTLR start initializer
    // XMLEmitter.g:317:1: initializer returns [Element e] : ( ^( INITIALIZER expression ) | INITIALIZER | ^( INITIALIZER initializerList ) );
    public final Element initializer() throws RecognitionException {
        Element e = null;

        Element expression19 = null;

        List<Element> initializerList20 = null;



        	e = newElement("initializer");

        try {
            // XMLEmitter.g:321:2: ( ^( INITIALIZER expression ) | INITIALIZER | ^( INITIALIZER initializerList ) )
            int alt22=3;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==INITIALIZER) ) {
                int LA22_1 = input.LA(2);

                if ( (LA22_1==DOWN) ) {
                    switch ( input.LA(3) ) {
                    case PP:
                    case MM:
                    case PU:
                    case MU:
                    case XU:
                    case AU:
                    case LABREF:
                    case BUILTIN_OFFSETOF:
                    case ASSIGNMENT_EXPRESSION:
                    case COMPOUND_LITERAL:
                    case ARRAY_ACCESS:
                    case CAST_EXPRESSION:
                    case CONDITIONAL_EXPRESSION:
                    case COMPOUND_STATEMENT:
                    case FUNCTION_CALL:
                    case STR_LITERAL:
                    case CONSTANT:
                    case 86:
                    case 129:
                    case 134:
                    case 138:
                    case 139:
                    case 140:
                    case 141:
                    case 143:
                    case 144:
                    case 145:
                    case 146:
                    case 147:
                    case 148:
                    case 149:
                    case 151:
                    case 153:
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
                        {
                        alt22=1;
                        }
                        break;
                    case IDENTIFIER:
                        {
                        int LA22_5 = input.LA(4);

                        if ( (LA22_5==UP) ) {
                            alt22=1;
                        }
                        else if ( ((LA22_5>=INITIALIZER && LA22_5<=DESIGNATOR)||LA22_5==IDENTIFIER) ) {
                            alt22=3;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("317:1: initializer returns [Element e] : ( ^( INITIALIZER expression ) | INITIALIZER | ^( INITIALIZER initializerList ) );", 22, 5, input);

                            throw nvae;
                        }
                        }
                        break;
                    case INITIALIZER:
                    case DESIGNATOR:
                        {
                        alt22=3;
                        }
                        break;
                    default:
                        NoViableAltException nvae =
                            new NoViableAltException("317:1: initializer returns [Element e] : ( ^( INITIALIZER expression ) | INITIALIZER | ^( INITIALIZER initializerList ) );", 22, 2, input);

                        throw nvae;
                    }

                }
                else if ( (LA22_1==UP||(LA22_1>=INITIALIZER && LA22_1<=DESIGNATOR)||LA22_1==IDENTIFIER) ) {
                    alt22=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("317:1: initializer returns [Element e] : ( ^( INITIALIZER expression ) | INITIALIZER | ^( INITIALIZER initializerList ) );", 22, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("317:1: initializer returns [Element e] : ( ^( INITIALIZER expression ) | INITIALIZER | ^( INITIALIZER initializerList ) );", 22, 0, input);

                throw nvae;
            }
            switch (alt22) {
                case 1 :
                    // XMLEmitter.g:321:4: ^( INITIALIZER expression )
                    {
                    match(input,INITIALIZER,FOLLOW_INITIALIZER_in_initializer466); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_initializer468);
                    expression19=expression();
                    _fsp--;


                    match(input, Token.UP, null); 
                     e.add(expression19); 

                    }
                    break;
                case 2 :
                    // XMLEmitter.g:322:4: INITIALIZER
                    {
                    match(input,INITIALIZER,FOLLOW_INITIALIZER_in_initializer476); 

                    }
                    break;
                case 3 :
                    // XMLEmitter.g:323:4: ^( INITIALIZER initializerList )
                    {
                    match(input,INITIALIZER,FOLLOW_INITIALIZER_in_initializer484); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_initializerList_in_initializer486);
                    initializerList20=initializerList();
                    _fsp--;


                    match(input, Token.UP, null); 
                     addAllElements(e, initializerList20); 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return e;
    }
    // $ANTLR end initializer


    // $ANTLR start initializerList
    // XMLEmitter.g:326:1: initializerList returns [List<Element> els] : ( (d= designator )* initializer )+ ;
    public final List<Element> initializerList() throws RecognitionException {
        List<Element> els = null;

        Element d = null;

        Element initializer21 = null;



        	els = new ArrayList<Element>();

        try {
            // XMLEmitter.g:330:2: ( ( (d= designator )* initializer )+ )
            // XMLEmitter.g:330:4: ( (d= designator )* initializer )+
            {
            // XMLEmitter.g:330:4: ( (d= designator )* initializer )+
            int cnt24=0;
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( ((LA24_0>=INITIALIZER && LA24_0<=DESIGNATOR)||LA24_0==IDENTIFIER) ) {
                    alt24=1;
                }


                switch (alt24) {
            	case 1 :
            	    // XMLEmitter.g:330:5: (d= designator )* initializer
            	    {
            	    // XMLEmitter.g:330:5: (d= designator )*
            	    loop23:
            	    do {
            	        int alt23=2;
            	        int LA23_0 = input.LA(1);

            	        if ( (LA23_0==DESIGNATOR||LA23_0==IDENTIFIER) ) {
            	            alt23=1;
            	        }


            	        switch (alt23) {
            	    	case 1 :
            	    	    // XMLEmitter.g:330:6: d= designator
            	    	    {
            	    	    pushFollow(FOLLOW_designator_in_initializerList512);
            	    	    d=designator();
            	    	    _fsp--;

            	    	    els.add(d);

            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop23;
            	        }
            	    } while (true);

            	    pushFollow(FOLLOW_initializer_in_initializerList518);
            	    initializer21=initializer();
            	    _fsp--;


            	    		els.add(initializer21);
            	    	

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


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return els;
    }
    // $ANTLR end initializerList


    // $ANTLR start designator
    // XMLEmitter.g:335:1: designator returns [Element e] : ( ^( DESIGNATOR ^( BRACKET_DESIGNATOR expression ) ) | ^( DESIGNATOR IDENTIFIER ) | IDENTIFIER );
    public final Element designator() throws RecognitionException {
        Element e = null;

        CommonTree IDENTIFIER23=null;
        CommonTree IDENTIFIER24=null;
        Element expression22 = null;



        	e = newElement("designator");

        try {
            // XMLEmitter.g:339:2: ( ^( DESIGNATOR ^( BRACKET_DESIGNATOR expression ) ) | ^( DESIGNATOR IDENTIFIER ) | IDENTIFIER )
            int alt25=3;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==DESIGNATOR) ) {
                int LA25_1 = input.LA(2);

                if ( (LA25_1==DOWN) ) {
                    int LA25_3 = input.LA(3);

                    if ( (LA25_3==BRACKET_DESIGNATOR) ) {
                        alt25=1;
                    }
                    else if ( (LA25_3==IDENTIFIER) ) {
                        alt25=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("335:1: designator returns [Element e] : ( ^( DESIGNATOR ^( BRACKET_DESIGNATOR expression ) ) | ^( DESIGNATOR IDENTIFIER ) | IDENTIFIER );", 25, 3, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("335:1: designator returns [Element e] : ( ^( DESIGNATOR ^( BRACKET_DESIGNATOR expression ) ) | ^( DESIGNATOR IDENTIFIER ) | IDENTIFIER );", 25, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA25_0==IDENTIFIER) ) {
                alt25=3;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("335:1: designator returns [Element e] : ( ^( DESIGNATOR ^( BRACKET_DESIGNATOR expression ) ) | ^( DESIGNATOR IDENTIFIER ) | IDENTIFIER );", 25, 0, input);

                throw nvae;
            }
            switch (alt25) {
                case 1 :
                    // XMLEmitter.g:339:4: ^( DESIGNATOR ^( BRACKET_DESIGNATOR expression ) )
                    {
                    match(input,DESIGNATOR,FOLLOW_DESIGNATOR_in_designator543); 

                    match(input, Token.DOWN, null); 
                    match(input,BRACKET_DESIGNATOR,FOLLOW_BRACKET_DESIGNATOR_in_designator546); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_designator548);
                    expression22=expression();
                    _fsp--;


                    match(input, Token.UP, null); 

                    match(input, Token.UP, null); 
                     e.add(expression22); 

                    }
                    break;
                case 2 :
                    // XMLEmitter.g:340:4: ^( DESIGNATOR IDENTIFIER )
                    {
                    match(input,DESIGNATOR,FOLLOW_DESIGNATOR_in_designator558); 

                    match(input, Token.DOWN, null); 
                    IDENTIFIER23=(CommonTree)input.LT(1);
                    match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_designator560); 

                    match(input, Token.UP, null); 
                     e.addElement("id").addText(IDENTIFIER23.getText()); 

                    }
                    break;
                case 3 :
                    // XMLEmitter.g:341:4: IDENTIFIER
                    {
                    IDENTIFIER24=(CommonTree)input.LT(1);
                    match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_designator568); 
                     e.addElement("id").addText(IDENTIFIER24.getText()); 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return e;
    }
    // $ANTLR end designator


    // $ANTLR start compoundStatement
    // XMLEmitter.g:344:1: compoundStatement returns [Element e] : ^( COMPOUND_STATEMENT (d= declaration | fd= functionDefinition | st= statement )* ) ;
    public final Element compoundStatement() throws RecognitionException {
        Element e = null;

        Element d = null;

        Element fd = null;

        Element st = null;



        	List<Element> els = new ArrayList<Element>();
        	pushSymbol(".", ".", 0);

        try {
            // XMLEmitter.g:349:2: ( ^( COMPOUND_STATEMENT (d= declaration | fd= functionDefinition | st= statement )* ) )
            // XMLEmitter.g:349:4: ^( COMPOUND_STATEMENT (d= declaration | fd= functionDefinition | st= statement )* )
            {
            match(input,COMPOUND_STATEMENT,FOLLOW_COMPOUND_STATEMENT_in_compoundStatement593); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // XMLEmitter.g:349:25: (d= declaration | fd= functionDefinition | st= statement )*
                loop26:
                do {
                    int alt26=4;
                    switch ( input.LA(1) ) {
                    case DECLARATION:
                    case 85:
                        {
                        alt26=1;
                        }
                        break;
                    case FUNCTION_DEFINITION:
                        {
                        alt26=2;
                        }
                        break;
                    case EXPRESSION_STATEMENT:
                    case ASM:
                    case LABEL:
                    case COMPOUND_STATEMENT:
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
                        alt26=3;
                        }
                        break;

                    }

                    switch (alt26) {
                	case 1 :
                	    // XMLEmitter.g:349:26: d= declaration
                	    {
                	    pushFollow(FOLLOW_declaration_in_compoundStatement598);
                	    d=declaration();
                	    _fsp--;

                	    els.add(d);

                	    }
                	    break;
                	case 2 :
                	    // XMLEmitter.g:349:54: fd= functionDefinition
                	    {
                	    pushFollow(FOLLOW_functionDefinition_in_compoundStatement604);
                	    fd=functionDefinition();
                	    _fsp--;

                	    els.add(fd);

                	    }
                	    break;
                	case 3 :
                	    // XMLEmitter.g:349:90: st= statement
                	    {
                	    pushFollow(FOLLOW_statement_in_compoundStatement609);
                	    st=statement();
                	    _fsp--;

                	    els.add(st);

                	    }
                	    break;

                	default :
                	    break loop26;
                    }
                } while (true);


                match(input, Token.UP, null); 
            }

            		e = newElement("compoundStatement");
            		addAllElements(e, els);
            		popUntil(".");
            	

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return e;
    }
    // $ANTLR end compoundStatement


    // $ANTLR start parameterTypeList
    // XMLEmitter.g:356:1: parameterTypeList returns [List<Element> els] : (p= parameterDeclaration )+ ( VARARGS )? ;
    public final List<Element> parameterTypeList() throws RecognitionException {
        List<Element> els = null;

        CommonTree VARARGS25=null;
        Element p = null;



        	els = new ArrayList<Element>();

        try {
            // XMLEmitter.g:360:2: ( (p= parameterDeclaration )+ ( VARARGS )? )
            // XMLEmitter.g:360:4: (p= parameterDeclaration )+ ( VARARGS )?
            {
            // XMLEmitter.g:360:4: (p= parameterDeclaration )+
            int cnt27=0;
            loop27:
            do {
                int alt27=2;
                int LA27_0 = input.LA(1);

                if ( (LA27_0==PARAMETER) ) {
                    alt27=1;
                }


                switch (alt27) {
            	case 1 :
            	    // XMLEmitter.g:360:5: p= parameterDeclaration
            	    {
            	    pushFollow(FOLLOW_parameterDeclaration_in_parameterTypeList638);
            	    p=parameterDeclaration();
            	    _fsp--;

            	    els.add(p);

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

            // XMLEmitter.g:360:45: ( VARARGS )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==VARARGS) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // XMLEmitter.g:360:45: VARARGS
                    {
                    VARARGS25=(CommonTree)input.LT(1);
                    match(input,VARARGS,FOLLOW_VARARGS_in_parameterTypeList644); 

                    }
                    break;

            }


            		if (VARARGS25 != null) {
            			Element va = newElement("parameter");
            			va.addElement("varArgs");
            			els.add(va);
            		}
            	

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return els;
    }
    // $ANTLR end parameterTypeList


    // $ANTLR start parameterDeclaration
    // XMLEmitter.g:369:1: parameterDeclaration returns [Element e] : ^( PARAMETER declarationSpecifiers ( declarator )? ( abstractDeclarator )? ) ;
    public final Element parameterDeclaration() throws RecognitionException {
        Element e = null;

        Element declarationSpecifiers26 = null;

        Element declarator27 = null;

        Element abstractDeclarator28 = null;


        try {
            // XMLEmitter.g:370:2: ( ^( PARAMETER declarationSpecifiers ( declarator )? ( abstractDeclarator )? ) )
            // XMLEmitter.g:370:4: ^( PARAMETER declarationSpecifiers ( declarator )? ( abstractDeclarator )? )
            {
            match(input,PARAMETER,FOLLOW_PARAMETER_in_parameterDeclaration663); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_declarationSpecifiers_in_parameterDeclaration665);
            declarationSpecifiers26=declarationSpecifiers();
            _fsp--;

            // XMLEmitter.g:370:38: ( declarator )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==DECLARATOR) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // XMLEmitter.g:370:38: declarator
                    {
                    pushFollow(FOLLOW_declarator_in_parameterDeclaration667);
                    declarator27=declarator();
                    _fsp--;


                    }
                    break;

            }

            // XMLEmitter.g:370:50: ( abstractDeclarator )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( ((LA30_0>=FUNCTION_DECLARATOR && LA30_0<=ARRAY_DECLARATOR)||LA30_0==POINTER) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // XMLEmitter.g:370:50: abstractDeclarator
                    {
                    pushFollow(FOLLOW_abstractDeclarator_in_parameterDeclaration670);
                    abstractDeclarator28=abstractDeclarator();
                    _fsp--;


                    }
                    break;

            }


            match(input, Token.UP, null); 

            		e = newElement("parameter");
            		e.add(declarationSpecifiers26);
            		addElementCond(e, declarator27);
            		addElementCond(e, abstractDeclarator28);
            	

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return e;
    }
    // $ANTLR end parameterDeclaration


    // $ANTLR start abstractDeclarator
    // XMLEmitter.g:378:1: abstractDeclarator returns [Element e] : ( pointer ( directAbstractDeclarator )? | directAbstractDeclarator );
    public final Element abstractDeclarator() throws RecognitionException {
        Element e = null;

        List<Element> pointer29 = null;

        List<Element> directAbstractDeclarator30 = null;

        List<Element> directAbstractDeclarator31 = null;



        	e = newElement("abstractDeclarator");

        try {
            // XMLEmitter.g:382:2: ( pointer ( directAbstractDeclarator )? | directAbstractDeclarator )
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==POINTER) ) {
                alt32=1;
            }
            else if ( ((LA32_0>=FUNCTION_DECLARATOR && LA32_0<=ARRAY_DECLARATOR)) ) {
                alt32=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("378:1: abstractDeclarator returns [Element e] : ( pointer ( directAbstractDeclarator )? | directAbstractDeclarator );", 32, 0, input);

                throw nvae;
            }
            switch (alt32) {
                case 1 :
                    // XMLEmitter.g:382:4: pointer ( directAbstractDeclarator )?
                    {
                    pushFollow(FOLLOW_pointer_in_abstractDeclarator694);
                    pointer29=pointer();
                    _fsp--;

                    // XMLEmitter.g:382:12: ( directAbstractDeclarator )?
                    int alt31=2;
                    int LA31_0 = input.LA(1);

                    if ( ((LA31_0>=FUNCTION_DECLARATOR && LA31_0<=ARRAY_DECLARATOR)) ) {
                        alt31=1;
                    }
                    switch (alt31) {
                        case 1 :
                            // XMLEmitter.g:382:12: directAbstractDeclarator
                            {
                            pushFollow(FOLLOW_directAbstractDeclarator_in_abstractDeclarator696);
                            directAbstractDeclarator30=directAbstractDeclarator();
                            _fsp--;


                            }
                            break;

                    }


                    		addAllElements(e, pointer29);
                    		addAllElements(e, directAbstractDeclarator30);
                    	

                    }
                    break;
                case 2 :
                    // XMLEmitter.g:386:4: directAbstractDeclarator
                    {
                    pushFollow(FOLLOW_directAbstractDeclarator_in_abstractDeclarator704);
                    directAbstractDeclarator31=directAbstractDeclarator();
                    _fsp--;

                     addAllElements(e, directAbstractDeclarator31); 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return e;
    }
    // $ANTLR end abstractDeclarator


    // $ANTLR start directAbstractDeclarator
    // XMLEmitter.g:389:1: directAbstractDeclarator returns [List<Element> els] : ( ^( ARRAY_DECLARATOR ( abstractDeclarator )? ( ( expression )? | ast= '*' ) ) | ^( FUNCTION_DECLARATOR ( abstractDeclarator )? ( parameterTypeList )? ) );
    public final List<Element> directAbstractDeclarator() throws RecognitionException {
        List<Element> els = null;

        CommonTree ast=null;
        Element abstractDeclarator32 = null;

        Element expression33 = null;

        Element abstractDeclarator34 = null;

        List<Element> parameterTypeList35 = null;



        	els = new ArrayList<Element>();

        try {
            // XMLEmitter.g:393:2: ( ^( ARRAY_DECLARATOR ( abstractDeclarator )? ( ( expression )? | ast= '*' ) ) | ^( FUNCTION_DECLARATOR ( abstractDeclarator )? ( parameterTypeList )? ) )
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==ARRAY_DECLARATOR) ) {
                alt38=1;
            }
            else if ( (LA38_0==FUNCTION_DECLARATOR) ) {
                alt38=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("389:1: directAbstractDeclarator returns [List<Element> els] : ( ^( ARRAY_DECLARATOR ( abstractDeclarator )? ( ( expression )? | ast= '*' ) ) | ^( FUNCTION_DECLARATOR ( abstractDeclarator )? ( parameterTypeList )? ) );", 38, 0, input);

                throw nvae;
            }
            switch (alt38) {
                case 1 :
                    // XMLEmitter.g:393:4: ^( ARRAY_DECLARATOR ( abstractDeclarator )? ( ( expression )? | ast= '*' ) )
                    {
                    match(input,ARRAY_DECLARATOR,FOLLOW_ARRAY_DECLARATOR_in_directAbstractDeclarator727); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // XMLEmitter.g:393:23: ( abstractDeclarator )?
                        int alt33=2;
                        int LA33_0 = input.LA(1);

                        if ( ((LA33_0>=FUNCTION_DECLARATOR && LA33_0<=ARRAY_DECLARATOR)||LA33_0==POINTER) ) {
                            alt33=1;
                        }
                        switch (alt33) {
                            case 1 :
                                // XMLEmitter.g:393:23: abstractDeclarator
                                {
                                pushFollow(FOLLOW_abstractDeclarator_in_directAbstractDeclarator729);
                                abstractDeclarator32=abstractDeclarator();
                                _fsp--;


                                }
                                break;

                        }

                        // XMLEmitter.g:393:43: ( ( expression )? | ast= '*' )
                        int alt35=2;
                        int LA35_0 = input.LA(1);

                        if ( (LA35_0==UP||(LA35_0>=PP && LA35_0<=BUILTIN_OFFSETOF)||LA35_0==ASSIGNMENT_EXPRESSION||(LA35_0>=COMPOUND_LITERAL && LA35_0<=ARRAY_ACCESS)||(LA35_0>=CAST_EXPRESSION && LA35_0<=CONDITIONAL_EXPRESSION)||LA35_0==COMPOUND_STATEMENT||(LA35_0>=FUNCTION_CALL && LA35_0<=CONSTANT)||LA35_0==86||LA35_0==134||(LA35_0>=138 && LA35_0<=141)||(LA35_0>=143 && LA35_0<=149)||LA35_0==151||(LA35_0>=153 && LA35_0<=166)) ) {
                            alt35=1;
                        }
                        else if ( (LA35_0==129) ) {
                            int LA35_2 = input.LA(2);

                            if ( (LA35_2==DOWN) ) {
                                alt35=1;
                            }
                            else if ( (LA35_2==UP) ) {
                                alt35=2;
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("393:43: ( ( expression )? | ast= '*' )", 35, 2, input);

                                throw nvae;
                            }
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("393:43: ( ( expression )? | ast= '*' )", 35, 0, input);

                            throw nvae;
                        }
                        switch (alt35) {
                            case 1 :
                                // XMLEmitter.g:393:44: ( expression )?
                                {
                                // XMLEmitter.g:393:44: ( expression )?
                                int alt34=2;
                                int LA34_0 = input.LA(1);

                                if ( ((LA34_0>=PP && LA34_0<=BUILTIN_OFFSETOF)||LA34_0==ASSIGNMENT_EXPRESSION||(LA34_0>=COMPOUND_LITERAL && LA34_0<=ARRAY_ACCESS)||(LA34_0>=CAST_EXPRESSION && LA34_0<=CONDITIONAL_EXPRESSION)||LA34_0==COMPOUND_STATEMENT||(LA34_0>=FUNCTION_CALL && LA34_0<=CONSTANT)||LA34_0==86||LA34_0==129||LA34_0==134||(LA34_0>=138 && LA34_0<=141)||(LA34_0>=143 && LA34_0<=149)||LA34_0==151||(LA34_0>=153 && LA34_0<=166)) ) {
                                    alt34=1;
                                }
                                switch (alt34) {
                                    case 1 :
                                        // XMLEmitter.g:393:44: expression
                                        {
                                        pushFollow(FOLLOW_expression_in_directAbstractDeclarator733);
                                        expression33=expression();
                                        _fsp--;


                                        }
                                        break;

                                }


                                }
                                break;
                            case 2 :
                                // XMLEmitter.g:393:56: ast= '*'
                                {
                                ast=(CommonTree)input.LT(1);
                                match(input,129,FOLLOW_129_in_directAbstractDeclarator738); 

                                }
                                break;

                        }


                        match(input, Token.UP, null); 
                    }

                    		if (abstractDeclarator32 != null)
                    			els.add(abstractDeclarator32);
                    		Element e = newListElement(els, "arrayDecl");
                    		if (expression33 != null)
                    			e.add(expression33);
                    		else if (ast != null)
                    			e.addAttribute("asterisk", "1");
                    	

                    }
                    break;
                case 2 :
                    // XMLEmitter.g:402:4: ^( FUNCTION_DECLARATOR ( abstractDeclarator )? ( parameterTypeList )? )
                    {
                    match(input,FUNCTION_DECLARATOR,FOLLOW_FUNCTION_DECLARATOR_in_directAbstractDeclarator748); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // XMLEmitter.g:402:26: ( abstractDeclarator )?
                        int alt36=2;
                        int LA36_0 = input.LA(1);

                        if ( ((LA36_0>=FUNCTION_DECLARATOR && LA36_0<=ARRAY_DECLARATOR)||LA36_0==POINTER) ) {
                            alt36=1;
                        }
                        switch (alt36) {
                            case 1 :
                                // XMLEmitter.g:402:26: abstractDeclarator
                                {
                                pushFollow(FOLLOW_abstractDeclarator_in_directAbstractDeclarator750);
                                abstractDeclarator34=abstractDeclarator();
                                _fsp--;


                                }
                                break;

                        }

                        // XMLEmitter.g:402:46: ( parameterTypeList )?
                        int alt37=2;
                        int LA37_0 = input.LA(1);

                        if ( (LA37_0==PARAMETER) ) {
                            alt37=1;
                        }
                        switch (alt37) {
                            case 1 :
                                // XMLEmitter.g:402:46: parameterTypeList
                                {
                                pushFollow(FOLLOW_parameterTypeList_in_directAbstractDeclarator753);
                                parameterTypeList35=parameterTypeList();
                                _fsp--;


                                }
                                break;

                        }


                        match(input, Token.UP, null); 
                    }

                    		if (abstractDeclarator34 != null)
                    			els.add(abstractDeclarator34);
                    		Element e = newElement("functionDecl");
                    		addAllElements(e, parameterTypeList35);
                    	

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return els;
    }
    // $ANTLR end directAbstractDeclarator


    // $ANTLR start identifier
    // XMLEmitter.g:410:1: identifier returns [Element e] : ^( PARAMETER IDENTIFIER ) ;
    public final Element identifier() throws RecognitionException {
        Element e = null;

        CommonTree IDENTIFIER36=null;

        try {
            // XMLEmitter.g:411:2: ( ^( PARAMETER IDENTIFIER ) )
            // XMLEmitter.g:411:4: ^( PARAMETER IDENTIFIER )
            {
            match(input,PARAMETER,FOLLOW_PARAMETER_in_identifier773); 

            match(input, Token.DOWN, null); 
            IDENTIFIER36=(CommonTree)input.LT(1);
            match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_identifier775); 

            match(input, Token.UP, null); 

            		String newName = renameVariable(IDENTIFIER36.getText());
            		e = newElement("id");
            		if (!newName.equals(IDENTIFIER36.getText()))
            			e.addAttribute("oldId", IDENTIFIER36.getText());
            		e.addText(newName);
            		pushSymbol(IDENTIFIER36.getText(), newName, 4);
            	

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return e;
    }
    // $ANTLR end identifier


    // $ANTLR start typeName
    // XMLEmitter.g:423:1: typeName returns [Element e] : ^( TYPE_NAME (sq= specifierQualifier )+ ( abstractDeclarator )? ) ;
    public final Element typeName() throws RecognitionException {
        Element e = null;

        specifierQualifier_return sq = null;

        Element abstractDeclarator37 = null;



        	List<specifierQualifier_return> sqs = new ArrayList<specifierQualifier_return>();

        try {
            // XMLEmitter.g:427:2: ( ^( TYPE_NAME (sq= specifierQualifier )+ ( abstractDeclarator )? ) )
            // XMLEmitter.g:427:4: ^( TYPE_NAME (sq= specifierQualifier )+ ( abstractDeclarator )? )
            {
            match(input,TYPE_NAME,FOLLOW_TYPE_NAME_in_typeName802); 

            match(input, Token.DOWN, null); 
            // XMLEmitter.g:427:16: (sq= specifierQualifier )+
            int cnt39=0;
            loop39:
            do {
                int alt39=2;
                int LA39_0 = input.LA(1);

                if ( ((LA39_0>=XTYPE_SPECIFIER && LA39_0<=XTYPE_QUALIFIER)) ) {
                    alt39=1;
                }


                switch (alt39) {
            	case 1 :
            	    // XMLEmitter.g:427:17: sq= specifierQualifier
            	    {
            	    pushFollow(FOLLOW_specifierQualifier_in_typeName807);
            	    sq=specifierQualifier();
            	    _fsp--;

            	    sqs.add(sq);

            	    }
            	    break;

            	default :
            	    if ( cnt39 >= 1 ) break loop39;
                        EarlyExitException eee =
                            new EarlyExitException(39, input);
                        throw eee;
                }
                cnt39++;
            } while (true);

            // XMLEmitter.g:427:56: ( abstractDeclarator )?
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( ((LA40_0>=FUNCTION_DECLARATOR && LA40_0<=ARRAY_DECLARATOR)||LA40_0==POINTER) ) {
                alt40=1;
            }
            switch (alt40) {
                case 1 :
                    // XMLEmitter.g:427:56: abstractDeclarator
                    {
                    pushFollow(FOLLOW_abstractDeclarator_in_typeName813);
                    abstractDeclarator37=abstractDeclarator();
                    _fsp--;


                    }
                    break;

            }


            match(input, Token.UP, null); 

            		List <Element> tss = new ArrayList<Element>();
            		e = newElement("typeName");
            		for (specifierQualifier_return sqr: sqs)
            			if (sqr.qual != null)
            				e.addAttribute(sqr.qual, "1");
            			else
            				tss.add(sqr.spec);
            		for (Element el: typeNormalize(tss))
            			e.add(el);
            		addElementCond(e, abstractDeclarator37);
            	

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return e;
    }
    // $ANTLR end typeName

    public static class specifierQualifier_return extends TreeRuleReturnScope {
        public Element spec;
        public String qual;
    };

    // $ANTLR start specifierQualifier
    // XMLEmitter.g:441:1: specifierQualifier returns [Element spec, String qual] : ( ^( XTYPE_SPECIFIER typeSpecifier ) | ^( XTYPE_QUALIFIER typeQualifier ) );
    public final specifierQualifier_return specifierQualifier() throws RecognitionException {
        specifierQualifier_return retval = new specifierQualifier_return();
        retval.start = input.LT(1);

        Element typeSpecifier38 = null;

        String typeQualifier39 = null;


        try {
            // XMLEmitter.g:442:2: ( ^( XTYPE_SPECIFIER typeSpecifier ) | ^( XTYPE_QUALIFIER typeQualifier ) )
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==XTYPE_SPECIFIER) ) {
                alt41=1;
            }
            else if ( (LA41_0==XTYPE_QUALIFIER) ) {
                alt41=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("441:1: specifierQualifier returns [Element spec, String qual] : ( ^( XTYPE_SPECIFIER typeSpecifier ) | ^( XTYPE_QUALIFIER typeQualifier ) );", 41, 0, input);

                throw nvae;
            }
            switch (alt41) {
                case 1 :
                    // XMLEmitter.g:442:4: ^( XTYPE_SPECIFIER typeSpecifier )
                    {
                    match(input,XTYPE_SPECIFIER,FOLLOW_XTYPE_SPECIFIER_in_specifierQualifier833); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_typeSpecifier_in_specifierQualifier835);
                    typeSpecifier38=typeSpecifier();
                    _fsp--;


                    match(input, Token.UP, null); 
                     retval.spec = typeSpecifier38; 

                    }
                    break;
                case 2 :
                    // XMLEmitter.g:443:4: ^( XTYPE_QUALIFIER typeQualifier )
                    {
                    match(input,XTYPE_QUALIFIER,FOLLOW_XTYPE_QUALIFIER_in_specifierQualifier844); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_typeQualifier_in_specifierQualifier846);
                    typeQualifier39=typeQualifier();
                    _fsp--;


                    match(input, Token.UP, null); 
                     retval.qual = typeQualifier39; 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end specifierQualifier


    // $ANTLR start typeQualifier
    // XMLEmitter.g:446:1: typeQualifier returns [String s] : ( 'const' | 'restrict' | 'volatile' );
    public final String typeQualifier() throws RecognitionException {
        String s = null;

        try {
            // XMLEmitter.g:447:2: ( 'const' | 'restrict' | 'volatile' )
            int alt42=3;
            switch ( input.LA(1) ) {
            case 115:
                {
                alt42=1;
                }
                break;
            case 118:
                {
                alt42=2;
                }
                break;
            case 121:
                {
                alt42=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("446:1: typeQualifier returns [String s] : ( 'const' | 'restrict' | 'volatile' );", 42, 0, input);

                throw nvae;
            }

            switch (alt42) {
                case 1 :
                    // XMLEmitter.g:447:4: 'const'
                    {
                    match(input,115,FOLLOW_115_in_typeQualifier864); 
                     s = "const"; 

                    }
                    break;
                case 2 :
                    // XMLEmitter.g:448:4: 'restrict'
                    {
                    match(input,118,FOLLOW_118_in_typeQualifier871); 
                     s = "restrict"; 

                    }
                    break;
                case 3 :
                    // XMLEmitter.g:449:4: 'volatile'
                    {
                    match(input,121,FOLLOW_121_in_typeQualifier878); 
                     s = "volatile"; 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return s;
    }
    // $ANTLR end typeQualifier


    // $ANTLR start typeSpecifier
    // XMLEmitter.g:452:1: typeSpecifier returns [Element e] : ( ^( BASETYPE 'void' ) | ^( BASETYPE 'char' ) | ^( BASETYPE 'short' ) | ^( BASETYPE 'int' ) | ^( BASETYPE 'long' ) | ^( BASETYPE 'float' ) | ^( BASETYPE 'double' ) | ^( BASETYPE SIGNED ) | ^( BASETYPE 'unsigned' ) | ^( BASETYPE '_Bool' ) | ^( BASETYPE '_Complex' ) | ^( BASETYPE XID ) | ^( BASETYPE '_Imaginary' ) | structOrUnionSpecifier | enumSpecifier | typedefName | typeofSpecifier );
    public final Element typeSpecifier() throws RecognitionException {
        Element e = null;

        Element structOrUnionSpecifier40 = null;

        Element enumSpecifier41 = null;

        String typedefName42 = null;



        	e = newElement("typeSpecifier");

        try {
            // XMLEmitter.g:456:2: ( ^( BASETYPE 'void' ) | ^( BASETYPE 'char' ) | ^( BASETYPE 'short' ) | ^( BASETYPE 'int' ) | ^( BASETYPE 'long' ) | ^( BASETYPE 'float' ) | ^( BASETYPE 'double' ) | ^( BASETYPE SIGNED ) | ^( BASETYPE 'unsigned' ) | ^( BASETYPE '_Bool' ) | ^( BASETYPE '_Complex' ) | ^( BASETYPE XID ) | ^( BASETYPE '_Imaginary' ) | structOrUnionSpecifier | enumSpecifier | typedefName | typeofSpecifier )
            int alt43=17;
            switch ( input.LA(1) ) {
            case BASETYPE:
                {
                int LA43_1 = input.LA(2);

                if ( (LA43_1==DOWN) ) {
                    switch ( input.LA(3) ) {
                    case 99:
                        {
                        alt43=7;
                        }
                        break;
                    case 96:
                        {
                        alt43=4;
                        }
                        break;
                    case 105:
                        {
                        alt43=11;
                        }
                        break;
                    case 93:
                        {
                        alt43=1;
                        }
                        break;
                    case SIGNED:
                        {
                        alt43=8;
                        }
                        break;
                    case 97:
                        {
                        alt43=5;
                        }
                        break;
                    case 94:
                        {
                        alt43=2;
                        }
                        break;
                    case 108:
                        {
                        alt43=13;
                        }
                        break;
                    case 103:
                        {
                        alt43=9;
                        }
                        break;
                    case 95:
                        {
                        alt43=3;
                        }
                        break;
                    case 98:
                        {
                        alt43=6;
                        }
                        break;
                    case XID:
                        {
                        alt43=12;
                        }
                        break;
                    case 104:
                        {
                        alt43=10;
                        }
                        break;
                    default:
                        NoViableAltException nvae =
                            new NoViableAltException("452:1: typeSpecifier returns [Element e] : ( ^( BASETYPE 'void' ) | ^( BASETYPE 'char' ) | ^( BASETYPE 'short' ) | ^( BASETYPE 'int' ) | ^( BASETYPE 'long' ) | ^( BASETYPE 'float' ) | ^( BASETYPE 'double' ) | ^( BASETYPE SIGNED ) | ^( BASETYPE 'unsigned' ) | ^( BASETYPE '_Bool' ) | ^( BASETYPE '_Complex' ) | ^( BASETYPE XID ) | ^( BASETYPE '_Imaginary' ) | structOrUnionSpecifier | enumSpecifier | typedefName | typeofSpecifier );", 43, 6, input);

                        throw nvae;
                    }

                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("452:1: typeSpecifier returns [Element e] : ( ^( BASETYPE 'void' ) | ^( BASETYPE 'char' ) | ^( BASETYPE 'short' ) | ^( BASETYPE 'int' ) | ^( BASETYPE 'long' ) | ^( BASETYPE 'float' ) | ^( BASETYPE 'double' ) | ^( BASETYPE SIGNED ) | ^( BASETYPE 'unsigned' ) | ^( BASETYPE '_Bool' ) | ^( BASETYPE '_Complex' ) | ^( BASETYPE XID ) | ^( BASETYPE '_Imaginary' ) | structOrUnionSpecifier | enumSpecifier | typedefName | typeofSpecifier );", 43, 1, input);

                    throw nvae;
                }
                }
                break;
            case 111:
            case 112:
                {
                alt43=14;
                }
                break;
            case 114:
                {
                alt43=15;
                }
                break;
            case IDENTIFIER:
                {
                alt43=16;
                }
                break;
            case TYPEOF:
                {
                alt43=17;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("452:1: typeSpecifier returns [Element e] : ( ^( BASETYPE 'void' ) | ^( BASETYPE 'char' ) | ^( BASETYPE 'short' ) | ^( BASETYPE 'int' ) | ^( BASETYPE 'long' ) | ^( BASETYPE 'float' ) | ^( BASETYPE 'double' ) | ^( BASETYPE SIGNED ) | ^( BASETYPE 'unsigned' ) | ^( BASETYPE '_Bool' ) | ^( BASETYPE '_Complex' ) | ^( BASETYPE XID ) | ^( BASETYPE '_Imaginary' ) | structOrUnionSpecifier | enumSpecifier | typedefName | typeofSpecifier );", 43, 0, input);

                throw nvae;
            }

            switch (alt43) {
                case 1 :
                    // XMLEmitter.g:456:4: ^( BASETYPE 'void' )
                    {
                    match(input,BASETYPE,FOLLOW_BASETYPE_in_typeSpecifier901); 

                    match(input, Token.DOWN, null); 
                    match(input,93,FOLLOW_93_in_typeSpecifier903); 

                    match(input, Token.UP, null); 
                     e.addElement("baseType").addText("void"); 

                    }
                    break;
                case 2 :
                    // XMLEmitter.g:457:4: ^( BASETYPE 'char' )
                    {
                    match(input,BASETYPE,FOLLOW_BASETYPE_in_typeSpecifier912); 

                    match(input, Token.DOWN, null); 
                    match(input,94,FOLLOW_94_in_typeSpecifier914); 

                    match(input, Token.UP, null); 
                     e.addElement("baseType").addText("char"); 

                    }
                    break;
                case 3 :
                    // XMLEmitter.g:458:4: ^( BASETYPE 'short' )
                    {
                    match(input,BASETYPE,FOLLOW_BASETYPE_in_typeSpecifier923); 

                    match(input, Token.DOWN, null); 
                    match(input,95,FOLLOW_95_in_typeSpecifier925); 

                    match(input, Token.UP, null); 
                     e.addElement("baseType").addText("short"); 

                    }
                    break;
                case 4 :
                    // XMLEmitter.g:459:4: ^( BASETYPE 'int' )
                    {
                    match(input,BASETYPE,FOLLOW_BASETYPE_in_typeSpecifier934); 

                    match(input, Token.DOWN, null); 
                    match(input,96,FOLLOW_96_in_typeSpecifier936); 

                    match(input, Token.UP, null); 
                     e.addElement("baseType").addText("int"); 

                    }
                    break;
                case 5 :
                    // XMLEmitter.g:460:4: ^( BASETYPE 'long' )
                    {
                    match(input,BASETYPE,FOLLOW_BASETYPE_in_typeSpecifier945); 

                    match(input, Token.DOWN, null); 
                    match(input,97,FOLLOW_97_in_typeSpecifier947); 

                    match(input, Token.UP, null); 
                     e.addElement("baseType").addText("long"); 

                    }
                    break;
                case 6 :
                    // XMLEmitter.g:461:4: ^( BASETYPE 'float' )
                    {
                    match(input,BASETYPE,FOLLOW_BASETYPE_in_typeSpecifier956); 

                    match(input, Token.DOWN, null); 
                    match(input,98,FOLLOW_98_in_typeSpecifier958); 

                    match(input, Token.UP, null); 
                     e.addElement("baseType").addText("float"); 

                    }
                    break;
                case 7 :
                    // XMLEmitter.g:462:4: ^( BASETYPE 'double' )
                    {
                    match(input,BASETYPE,FOLLOW_BASETYPE_in_typeSpecifier967); 

                    match(input, Token.DOWN, null); 
                    match(input,99,FOLLOW_99_in_typeSpecifier969); 

                    match(input, Token.UP, null); 
                     e.addElement("baseType").addText("double"); 

                    }
                    break;
                case 8 :
                    // XMLEmitter.g:463:4: ^( BASETYPE SIGNED )
                    {
                    match(input,BASETYPE,FOLLOW_BASETYPE_in_typeSpecifier978); 

                    match(input, Token.DOWN, null); 
                    match(input,SIGNED,FOLLOW_SIGNED_in_typeSpecifier980); 

                    match(input, Token.UP, null); 
                     e.addElement("baseType").addText("signed"); 

                    }
                    break;
                case 9 :
                    // XMLEmitter.g:464:4: ^( BASETYPE 'unsigned' )
                    {
                    match(input,BASETYPE,FOLLOW_BASETYPE_in_typeSpecifier989); 

                    match(input, Token.DOWN, null); 
                    match(input,103,FOLLOW_103_in_typeSpecifier991); 

                    match(input, Token.UP, null); 
                     e.addElement("baseType").addText("unsigned"); 

                    }
                    break;
                case 10 :
                    // XMLEmitter.g:465:4: ^( BASETYPE '_Bool' )
                    {
                    match(input,BASETYPE,FOLLOW_BASETYPE_in_typeSpecifier999); 

                    match(input, Token.DOWN, null); 
                    match(input,104,FOLLOW_104_in_typeSpecifier1001); 

                    match(input, Token.UP, null); 
                     e.addElement("baseType").addText("_Bool"); 

                    }
                    break;
                case 11 :
                    // XMLEmitter.g:466:4: ^( BASETYPE '_Complex' )
                    {
                    match(input,BASETYPE,FOLLOW_BASETYPE_in_typeSpecifier1010); 

                    match(input, Token.DOWN, null); 
                    match(input,105,FOLLOW_105_in_typeSpecifier1012); 

                    match(input, Token.UP, null); 
                     e.addElement("baseType").addText("_Complex"); 

                    }
                    break;
                case 12 :
                    // XMLEmitter.g:467:4: ^( BASETYPE XID )
                    {
                    match(input,BASETYPE,FOLLOW_BASETYPE_in_typeSpecifier1020); 

                    match(input, Token.DOWN, null); 
                    match(input,XID,FOLLOW_XID_in_typeSpecifier1022); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 13 :
                    // XMLEmitter.g:468:4: ^( BASETYPE '_Imaginary' )
                    {
                    match(input,BASETYPE,FOLLOW_BASETYPE_in_typeSpecifier1029); 

                    match(input, Token.DOWN, null); 
                    match(input,108,FOLLOW_108_in_typeSpecifier1031); 

                    match(input, Token.UP, null); 
                     e.addElement("baseType").addText("_Imaginary"); 

                    }
                    break;
                case 14 :
                    // XMLEmitter.g:469:4: structOrUnionSpecifier
                    {
                    pushFollow(FOLLOW_structOrUnionSpecifier_in_typeSpecifier1039);
                    structOrUnionSpecifier40=structOrUnionSpecifier();
                    _fsp--;

                     e.add(structOrUnionSpecifier40); 

                    }
                    break;
                case 15 :
                    // XMLEmitter.g:470:4: enumSpecifier
                    {
                    pushFollow(FOLLOW_enumSpecifier_in_typeSpecifier1045);
                    enumSpecifier41=enumSpecifier();
                    _fsp--;

                     e.add(enumSpecifier41); 

                    }
                    break;
                case 16 :
                    // XMLEmitter.g:471:4: typedefName
                    {
                    pushFollow(FOLLOW_typedefName_in_typeSpecifier1053);
                    typedefName42=typedefName();
                    _fsp--;

                     e.addElement("typedef").addElement("id").addText(typedefName42); 

                    }
                    break;
                case 17 :
                    // XMLEmitter.g:472:4: typeofSpecifier
                    {
                    pushFollow(FOLLOW_typeofSpecifier_in_typeSpecifier1061);
                    typeofSpecifier();
                    _fsp--;


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return e;
    }
    // $ANTLR end typeSpecifier


    // $ANTLR start structOrUnionSpecifier
    // XMLEmitter.g:475:1: structOrUnionSpecifier returns [Element e] : ^( structOrUnion ^( XID ( IDENTIFIER )? ) (sd= structDeclaration )* ) ;
    public final Element structOrUnionSpecifier() throws RecognitionException {
        Element e = null;

        CommonTree IDENTIFIER44=null;
        Element sd = null;

        String structOrUnion43 = null;



        	List<Element> sds = new ArrayList<Element>();
        	symbolsEnabled = false;

        try {
            // XMLEmitter.g:480:2: ( ^( structOrUnion ^( XID ( IDENTIFIER )? ) (sd= structDeclaration )* ) )
            // XMLEmitter.g:480:4: ^( structOrUnion ^( XID ( IDENTIFIER )? ) (sd= structDeclaration )* )
            {
            pushFollow(FOLLOW_structOrUnion_in_structOrUnionSpecifier1082);
            structOrUnion43=structOrUnion();
            _fsp--;


            match(input, Token.DOWN, null); 
            match(input,XID,FOLLOW_XID_in_structOrUnionSpecifier1085); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // XMLEmitter.g:480:26: ( IDENTIFIER )?
                int alt44=2;
                int LA44_0 = input.LA(1);

                if ( (LA44_0==IDENTIFIER) ) {
                    alt44=1;
                }
                switch (alt44) {
                    case 1 :
                        // XMLEmitter.g:480:26: IDENTIFIER
                        {
                        IDENTIFIER44=(CommonTree)input.LT(1);
                        match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_structOrUnionSpecifier1087); 

                        }
                        break;

                }


                match(input, Token.UP, null); 
            }
            // XMLEmitter.g:480:39: (sd= structDeclaration )*
            loop45:
            do {
                int alt45=2;
                int LA45_0 = input.LA(1);

                if ( (LA45_0==STRUCT_DECLARATION) ) {
                    alt45=1;
                }


                switch (alt45) {
            	case 1 :
            	    // XMLEmitter.g:480:40: sd= structDeclaration
            	    {
            	    pushFollow(FOLLOW_structDeclaration_in_structOrUnionSpecifier1094);
            	    sd=structDeclaration();
            	    _fsp--;

            	    sds.add(sd);

            	    }
            	    break;

            	default :
            	    break loop45;
                }
            } while (true);


            match(input, Token.UP, null); 

            		e = newElement(structOrUnion43);
            		if (IDENTIFIER44 != null)
            			e.addAttribute("id", IDENTIFIER44.getText());
            		addAllElements(e, sds);
            		symbolsEnabled = true;
            	

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return e;
    }
    // $ANTLR end structOrUnionSpecifier


    // $ANTLR start structOrUnion
    // XMLEmitter.g:489:1: structOrUnion returns [String s] : ( 'struct' | 'union' );
    public final String structOrUnion() throws RecognitionException {
        String s = null;

        try {
            // XMLEmitter.g:490:2: ( 'struct' | 'union' )
            int alt46=2;
            int LA46_0 = input.LA(1);

            if ( (LA46_0==111) ) {
                alt46=1;
            }
            else if ( (LA46_0==112) ) {
                alt46=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("489:1: structOrUnion returns [String s] : ( 'struct' | 'union' );", 46, 0, input);

                throw nvae;
            }
            switch (alt46) {
                case 1 :
                    // XMLEmitter.g:490:4: 'struct'
                    {
                    match(input,111,FOLLOW_111_in_structOrUnion1116); 
                     s = "struct"; 

                    }
                    break;
                case 2 :
                    // XMLEmitter.g:491:4: 'union'
                    {
                    match(input,112,FOLLOW_112_in_structOrUnion1123); 
                     s = "union"; 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return s;
    }
    // $ANTLR end structOrUnion


    // $ANTLR start structDeclaration
    // XMLEmitter.g:494:1: structDeclaration returns [Element e] : ^( STRUCT_DECLARATION (sq= specifierQualifier )+ (sd= structDeclarator )* ) ;
    public final Element structDeclaration() throws RecognitionException {
        Element e = null;

        specifierQualifier_return sq = null;

        Element sd = null;



        	List<specifierQualifier_return> sqs = new ArrayList<specifierQualifier_return>();
        	List<Element> sds = new ArrayList<Element>();

        try {
            // XMLEmitter.g:499:2: ( ^( STRUCT_DECLARATION (sq= specifierQualifier )+ (sd= structDeclarator )* ) )
            // XMLEmitter.g:499:4: ^( STRUCT_DECLARATION (sq= specifierQualifier )+ (sd= structDeclarator )* )
            {
            match(input,STRUCT_DECLARATION,FOLLOW_STRUCT_DECLARATION_in_structDeclaration1146); 

            match(input, Token.DOWN, null); 
            // XMLEmitter.g:499:25: (sq= specifierQualifier )+
            int cnt47=0;
            loop47:
            do {
                int alt47=2;
                int LA47_0 = input.LA(1);

                if ( ((LA47_0>=XTYPE_SPECIFIER && LA47_0<=XTYPE_QUALIFIER)) ) {
                    alt47=1;
                }


                switch (alt47) {
            	case 1 :
            	    // XMLEmitter.g:499:26: sq= specifierQualifier
            	    {
            	    pushFollow(FOLLOW_specifierQualifier_in_structDeclaration1151);
            	    sq=specifierQualifier();
            	    _fsp--;

            	    sqs.add(sq);

            	    }
            	    break;

            	default :
            	    if ( cnt47 >= 1 ) break loop47;
                        EarlyExitException eee =
                            new EarlyExitException(47, input);
                        throw eee;
                }
                cnt47++;
            } while (true);

            // XMLEmitter.g:499:65: (sd= structDeclarator )*
            loop48:
            do {
                int alt48=2;
                int LA48_0 = input.LA(1);

                if ( (LA48_0==STRUCT_DECLARATOR) ) {
                    alt48=1;
                }


                switch (alt48) {
            	case 1 :
            	    // XMLEmitter.g:499:66: sd= structDeclarator
            	    {
            	    pushFollow(FOLLOW_structDeclarator_in_structDeclaration1160);
            	    sd=structDeclarator();
            	    _fsp--;

            	    sds.add(sd);

            	    }
            	    break;

            	default :
            	    break loop48;
                }
            } while (true);


            match(input, Token.UP, null); 

            		List <Element> tss = new ArrayList<Element>();
            		e = newElement("structDeclaration");
            		for (specifierQualifier_return sqr: sqs)
            			if (sqr.qual != null)
            				e.addAttribute(sqr.qual, "1");
            			else
            				tss.add(sqr.spec);
            		for (Element el: typeNormalize(tss))
            			e.add(el);
            		addAllElements(e, sds);
            	

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return e;
    }
    // $ANTLR end structDeclaration


    // $ANTLR start structDeclarator
    // XMLEmitter.g:513:1: structDeclarator returns [Element e] : ^( STRUCT_DECLARATOR ( declarator )? ( expression )? ) ;
    public final Element structDeclarator() throws RecognitionException {
        Element e = null;

        Element declarator45 = null;

        Element expression46 = null;


        try {
            // XMLEmitter.g:514:2: ( ^( STRUCT_DECLARATOR ( declarator )? ( expression )? ) )
            // XMLEmitter.g:514:4: ^( STRUCT_DECLARATOR ( declarator )? ( expression )? )
            {
            match(input,STRUCT_DECLARATOR,FOLLOW_STRUCT_DECLARATOR_in_structDeclarator1183); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // XMLEmitter.g:514:24: ( declarator )?
                int alt49=2;
                int LA49_0 = input.LA(1);

                if ( (LA49_0==DECLARATOR) ) {
                    alt49=1;
                }
                switch (alt49) {
                    case 1 :
                        // XMLEmitter.g:514:24: declarator
                        {
                        pushFollow(FOLLOW_declarator_in_structDeclarator1185);
                        declarator45=declarator();
                        _fsp--;


                        }
                        break;

                }

                // XMLEmitter.g:514:36: ( expression )?
                int alt50=2;
                int LA50_0 = input.LA(1);

                if ( ((LA50_0>=PP && LA50_0<=BUILTIN_OFFSETOF)||LA50_0==ASSIGNMENT_EXPRESSION||(LA50_0>=COMPOUND_LITERAL && LA50_0<=ARRAY_ACCESS)||(LA50_0>=CAST_EXPRESSION && LA50_0<=CONDITIONAL_EXPRESSION)||LA50_0==COMPOUND_STATEMENT||(LA50_0>=FUNCTION_CALL && LA50_0<=CONSTANT)||LA50_0==86||LA50_0==129||LA50_0==134||(LA50_0>=138 && LA50_0<=141)||(LA50_0>=143 && LA50_0<=149)||LA50_0==151||(LA50_0>=153 && LA50_0<=166)) ) {
                    alt50=1;
                }
                switch (alt50) {
                    case 1 :
                        // XMLEmitter.g:514:36: expression
                        {
                        pushFollow(FOLLOW_expression_in_structDeclarator1188);
                        expression46=expression();
                        _fsp--;


                        }
                        break;

                }


                match(input, Token.UP, null); 
            }

            		e = newElement("structDeclarator");
            		addElementCond(e, declarator45);
            		addElementCond(e, expression46);
            	

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return e;
    }
    // $ANTLR end structDeclarator


    // $ANTLR start enumSpecifier
    // XMLEmitter.g:521:1: enumSpecifier returns [Element e] : ^( 'enum' ( ^( XID IDENTIFIER ) )? (en= enumerator )* ) ;
    public final Element enumSpecifier() throws RecognitionException {
        Element e = null;

        CommonTree IDENTIFIER47=null;
        Element en = null;



        	List<Element> ens = new ArrayList<Element>();

        try {
            // XMLEmitter.g:525:2: ( ^( 'enum' ( ^( XID IDENTIFIER ) )? (en= enumerator )* ) )
            // XMLEmitter.g:525:4: ^( 'enum' ( ^( XID IDENTIFIER ) )? (en= enumerator )* )
            {
            match(input,114,FOLLOW_114_in_enumSpecifier1213); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // XMLEmitter.g:525:13: ( ^( XID IDENTIFIER ) )?
                int alt51=2;
                int LA51_0 = input.LA(1);

                if ( (LA51_0==XID) ) {
                    alt51=1;
                }
                switch (alt51) {
                    case 1 :
                        // XMLEmitter.g:525:14: ^( XID IDENTIFIER )
                        {
                        match(input,XID,FOLLOW_XID_in_enumSpecifier1217); 

                        match(input, Token.DOWN, null); 
                        IDENTIFIER47=(CommonTree)input.LT(1);
                        match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_enumSpecifier1219); 

                        match(input, Token.UP, null); 

                        }
                        break;

                }

                // XMLEmitter.g:525:34: (en= enumerator )*
                loop52:
                do {
                    int alt52=2;
                    int LA52_0 = input.LA(1);

                    if ( (LA52_0==ENUMERATOR) ) {
                        alt52=1;
                    }


                    switch (alt52) {
                	case 1 :
                	    // XMLEmitter.g:525:35: en= enumerator
                	    {
                	    pushFollow(FOLLOW_enumerator_in_enumSpecifier1227);
                	    en=enumerator();
                	    _fsp--;

                	    ens.add(en);

                	    }
                	    break;

                	default :
                	    break loop52;
                    }
                } while (true);


                match(input, Token.UP, null); 
            }

            		e = newElement("enum");
            		if (IDENTIFIER47 != null)
            			e.addAttribute("id", IDENTIFIER47.getText());
            		addAllElements(e, ens);
            	

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return e;
    }
    // $ANTLR end enumSpecifier


    // $ANTLR start enumerator
    // XMLEmitter.g:533:1: enumerator returns [Element e] : ^( ENUMERATOR IDENTIFIER ( expression )? ) ;
    public final Element enumerator() throws RecognitionException {
        Element e = null;

        CommonTree IDENTIFIER48=null;
        Element expression49 = null;


        try {
            // XMLEmitter.g:534:2: ( ^( ENUMERATOR IDENTIFIER ( expression )? ) )
            // XMLEmitter.g:534:4: ^( ENUMERATOR IDENTIFIER ( expression )? )
            {
            match(input,ENUMERATOR,FOLLOW_ENUMERATOR_in_enumerator1250); 

            match(input, Token.DOWN, null); 
            IDENTIFIER48=(CommonTree)input.LT(1);
            match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_enumerator1252); 
            // XMLEmitter.g:534:28: ( expression )?
            int alt53=2;
            int LA53_0 = input.LA(1);

            if ( ((LA53_0>=PP && LA53_0<=BUILTIN_OFFSETOF)||LA53_0==ASSIGNMENT_EXPRESSION||(LA53_0>=COMPOUND_LITERAL && LA53_0<=ARRAY_ACCESS)||(LA53_0>=CAST_EXPRESSION && LA53_0<=CONDITIONAL_EXPRESSION)||LA53_0==COMPOUND_STATEMENT||(LA53_0>=FUNCTION_CALL && LA53_0<=CONSTANT)||LA53_0==86||LA53_0==129||LA53_0==134||(LA53_0>=138 && LA53_0<=141)||(LA53_0>=143 && LA53_0<=149)||LA53_0==151||(LA53_0>=153 && LA53_0<=166)) ) {
                alt53=1;
            }
            switch (alt53) {
                case 1 :
                    // XMLEmitter.g:534:28: expression
                    {
                    pushFollow(FOLLOW_expression_in_enumerator1254);
                    expression49=expression();
                    _fsp--;


                    }
                    break;

            }


            match(input, Token.UP, null); 

            		e = newElement("enumerator");
            		e.addAttribute("id", IDENTIFIER48.getText());
            		addElementCond(e, expression49);
            	

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return e;
    }
    // $ANTLR end enumerator


    // $ANTLR start typedefName
    // XMLEmitter.g:541:1: typedefName returns [String s] : IDENTIFIER ;
    public final String typedefName() throws RecognitionException {
        String s = null;

        CommonTree IDENTIFIER50=null;

        try {
            // XMLEmitter.g:542:2: ( IDENTIFIER )
            // XMLEmitter.g:542:4: IDENTIFIER
            {
            IDENTIFIER50=(CommonTree)input.LT(1);
            match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_typedefName1273); 
             s = IDENTIFIER50.getText(); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return s;
    }
    // $ANTLR end typedefName


    // $ANTLR start typeofSpecifier
    // XMLEmitter.g:545:1: typeofSpecifier : ( ^( TYPEOF expression ) | ^( TYPEOF typeName ) );
    public final void typeofSpecifier() throws RecognitionException {
        try {
            // XMLEmitter.g:546:2: ( ^( TYPEOF expression ) | ^( TYPEOF typeName ) )
            int alt54=2;
            int LA54_0 = input.LA(1);

            if ( (LA54_0==TYPEOF) ) {
                int LA54_1 = input.LA(2);

                if ( (LA54_1==DOWN) ) {
                    int LA54_2 = input.LA(3);

                    if ( ((LA54_2>=PP && LA54_2<=BUILTIN_OFFSETOF)||LA54_2==ASSIGNMENT_EXPRESSION||(LA54_2>=COMPOUND_LITERAL && LA54_2<=ARRAY_ACCESS)||(LA54_2>=CAST_EXPRESSION && LA54_2<=CONDITIONAL_EXPRESSION)||LA54_2==COMPOUND_STATEMENT||(LA54_2>=FUNCTION_CALL && LA54_2<=CONSTANT)||LA54_2==86||LA54_2==129||LA54_2==134||(LA54_2>=138 && LA54_2<=141)||(LA54_2>=143 && LA54_2<=149)||LA54_2==151||(LA54_2>=153 && LA54_2<=166)) ) {
                        alt54=1;
                    }
                    else if ( (LA54_2==TYPE_NAME) ) {
                        alt54=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("545:1: typeofSpecifier : ( ^( TYPEOF expression ) | ^( TYPEOF typeName ) );", 54, 2, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("545:1: typeofSpecifier : ( ^( TYPEOF expression ) | ^( TYPEOF typeName ) );", 54, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("545:1: typeofSpecifier : ( ^( TYPEOF expression ) | ^( TYPEOF typeName ) );", 54, 0, input);

                throw nvae;
            }
            switch (alt54) {
                case 1 :
                    // XMLEmitter.g:546:4: ^( TYPEOF expression )
                    {
                    match(input,TYPEOF,FOLLOW_TYPEOF_in_typeofSpecifier1287); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_typeofSpecifier1289);
                    expression();
                    _fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // XMLEmitter.g:547:4: ^( TYPEOF typeName )
                    {
                    match(input,TYPEOF,FOLLOW_TYPEOF_in_typeofSpecifier1296); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_typeName_in_typeofSpecifier1298);
                    typeName();
                    _fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end typeofSpecifier


    // $ANTLR start storageClassSpecifier
    // XMLEmitter.g:550:1: storageClassSpecifier returns [String s] : ( 'extern' | 'static' | 'auto' | 'register' | '__thread' );
    public final String storageClassSpecifier() throws RecognitionException {
        String s = null;

        try {
            // XMLEmitter.g:551:2: ( 'extern' | 'static' | 'auto' | 'register' | '__thread' )
            int alt55=5;
            switch ( input.LA(1) ) {
            case 88:
                {
                alt55=1;
                }
                break;
            case 89:
                {
                alt55=2;
                }
                break;
            case 90:
                {
                alt55=3;
                }
                break;
            case 91:
                {
                alt55=4;
                }
                break;
            case 92:
                {
                alt55=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("550:1: storageClassSpecifier returns [String s] : ( 'extern' | 'static' | 'auto' | 'register' | '__thread' );", 55, 0, input);

                throw nvae;
            }

            switch (alt55) {
                case 1 :
                    // XMLEmitter.g:551:4: 'extern'
                    {
                    match(input,88,FOLLOW_88_in_storageClassSpecifier1314); 
                     s = "extern"; 

                    }
                    break;
                case 2 :
                    // XMLEmitter.g:552:4: 'static'
                    {
                    match(input,89,FOLLOW_89_in_storageClassSpecifier1321); 
                     s = "static"; 

                    }
                    break;
                case 3 :
                    // XMLEmitter.g:553:4: 'auto'
                    {
                    match(input,90,FOLLOW_90_in_storageClassSpecifier1328); 
                     s = "auto"; 

                    }
                    break;
                case 4 :
                    // XMLEmitter.g:554:4: 'register'
                    {
                    match(input,91,FOLLOW_91_in_storageClassSpecifier1335); 
                     s = "register"; 

                    }
                    break;
                case 5 :
                    // XMLEmitter.g:555:4: '__thread'
                    {
                    match(input,92,FOLLOW_92_in_storageClassSpecifier1342); 
                     s = "__thread"; 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return s;
    }
    // $ANTLR end storageClassSpecifier


    // $ANTLR start functionSpecifier
    // XMLEmitter.g:558:1: functionSpecifier returns [String s] : 'inline' ;
    public final String functionSpecifier() throws RecognitionException {
        String s = null;

        try {
            // XMLEmitter.g:559:2: ( 'inline' )
            // XMLEmitter.g:559:4: 'inline'
            {
            match(input,124,FOLLOW_124_in_functionSpecifier1359); 
             s = "inline"; 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return s;
    }
    // $ANTLR end functionSpecifier


    // $ANTLR start pointer
    // XMLEmitter.g:562:1: pointer returns [List<Element> els] : ^( POINTER (tq= typeQualifier )* (ptr= pointer )? ) ;
    public final List<Element> pointer() throws RecognitionException {
        List<Element> els = null;

        String tq = null;

        List<Element> ptr = null;



        	Set<String> tqs = new HashSet<String>();
        	els = new ArrayList<Element>();

        try {
            // XMLEmitter.g:567:2: ( ^( POINTER (tq= typeQualifier )* (ptr= pointer )? ) )
            // XMLEmitter.g:567:4: ^( POINTER (tq= typeQualifier )* (ptr= pointer )? )
            {
            match(input,POINTER,FOLLOW_POINTER_in_pointer1382); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // XMLEmitter.g:567:14: (tq= typeQualifier )*
                loop56:
                do {
                    int alt56=2;
                    int LA56_0 = input.LA(1);

                    if ( (LA56_0==115||LA56_0==118||LA56_0==121) ) {
                        alt56=1;
                    }


                    switch (alt56) {
                	case 1 :
                	    // XMLEmitter.g:567:15: tq= typeQualifier
                	    {
                	    pushFollow(FOLLOW_typeQualifier_in_pointer1387);
                	    tq=typeQualifier();
                	    _fsp--;

                	    tqs.add(tq);

                	    }
                	    break;

                	default :
                	    break loop56;
                    }
                } while (true);

                // XMLEmitter.g:567:52: (ptr= pointer )?
                int alt57=2;
                int LA57_0 = input.LA(1);

                if ( (LA57_0==POINTER) ) {
                    alt57=1;
                }
                switch (alt57) {
                    case 1 :
                        // XMLEmitter.g:567:52: ptr= pointer
                        {
                        pushFollow(FOLLOW_pointer_in_pointer1395);
                        ptr=pointer();
                        _fsp--;


                        }
                        break;

                }


                match(input, Token.UP, null); 
            }

            		Element e = newElement("pointer");
            		for (String t: tqs)
            			e.addAttribute(t, "1");
            		els.add(e);
            		if (ptr != null)
            			els.addAll(ptr);
            	

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return els;
    }
    // $ANTLR end pointer


    // $ANTLR start statement
    // XMLEmitter.g:581:1: statement returns [Element e] : ( labeledStatement | compoundStatement | expressionStatement | selectionStatement | iterationStatement | jumpStatement | asmStatement );
    public final Element statement() throws RecognitionException {
        Element e = null;

        Element labeledStatement51 = null;

        Element compoundStatement52 = null;

        Element expressionStatement53 = null;

        Element selectionStatement54 = null;

        Element iterationStatement55 = null;

        Element jumpStatement56 = null;

        Element asmStatement57 = null;



        	e = newElement("statement");

        try {
            // XMLEmitter.g:585:2: ( labeledStatement | compoundStatement | expressionStatement | selectionStatement | iterationStatement | jumpStatement | asmStatement )
            int alt58=7;
            switch ( input.LA(1) ) {
            case LABEL:
            case 178:
            case 179:
                {
                alt58=1;
                }
                break;
            case COMPOUND_STATEMENT:
                {
                alt58=2;
                }
                break;
            case EXPRESSION_STATEMENT:
                {
                alt58=3;
                }
                break;
            case 180:
            case 182:
                {
                alt58=4;
                }
                break;
            case 183:
            case 184:
            case 185:
                {
                alt58=5;
                }
                break;
            case 186:
            case 187:
            case 188:
            case 189:
                {
                alt58=6;
                }
                break;
            case ASM:
                {
                alt58=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("581:1: statement returns [Element e] : ( labeledStatement | compoundStatement | expressionStatement | selectionStatement | iterationStatement | jumpStatement | asmStatement );", 58, 0, input);

                throw nvae;
            }

            switch (alt58) {
                case 1 :
                    // XMLEmitter.g:585:4: labeledStatement
                    {
                    pushFollow(FOLLOW_labeledStatement_in_statement1425);
                    labeledStatement51=labeledStatement();
                    _fsp--;

                     e.add(labeledStatement51); 

                    }
                    break;
                case 2 :
                    // XMLEmitter.g:586:4: compoundStatement
                    {
                    pushFollow(FOLLOW_compoundStatement_in_statement1432);
                    compoundStatement52=compoundStatement();
                    _fsp--;

                     e.add(compoundStatement52); 

                    }
                    break;
                case 3 :
                    // XMLEmitter.g:587:4: expressionStatement
                    {
                    pushFollow(FOLLOW_expressionStatement_in_statement1439);
                    expressionStatement53=expressionStatement();
                    _fsp--;

                     addElementCond(e, expressionStatement53); 

                    }
                    break;
                case 4 :
                    // XMLEmitter.g:588:4: selectionStatement
                    {
                    pushFollow(FOLLOW_selectionStatement_in_statement1446);
                    selectionStatement54=selectionStatement();
                    _fsp--;

                     e.add(selectionStatement54); 

                    }
                    break;
                case 5 :
                    // XMLEmitter.g:589:4: iterationStatement
                    {
                    pushFollow(FOLLOW_iterationStatement_in_statement1453);
                    iterationStatement55=iterationStatement();
                    _fsp--;

                     e.add(iterationStatement55); 

                    }
                    break;
                case 6 :
                    // XMLEmitter.g:590:4: jumpStatement
                    {
                    pushFollow(FOLLOW_jumpStatement_in_statement1460);
                    jumpStatement56=jumpStatement();
                    _fsp--;

                     e.add(jumpStatement56); 

                    }
                    break;
                case 7 :
                    // XMLEmitter.g:591:4: asmStatement
                    {
                    pushFollow(FOLLOW_asmStatement_in_statement1468);
                    asmStatement57=asmStatement();
                    _fsp--;

                     e.add(asmStatement57); 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return e;
    }
    // $ANTLR end statement


    // $ANTLR start labeledStatement
    // XMLEmitter.g:594:1: labeledStatement returns [Element e] : ( ^( LABEL IDENTIFIER statement ) | ^( 'case' expression statement ) | ^( 'default' statement ) );
    public final Element labeledStatement() throws RecognitionException {
        Element e = null;

        CommonTree IDENTIFIER59=null;
        Element statement58 = null;

        Element expression60 = null;

        Element statement61 = null;

        Element statement62 = null;


        try {
            // XMLEmitter.g:595:2: ( ^( LABEL IDENTIFIER statement ) | ^( 'case' expression statement ) | ^( 'default' statement ) )
            int alt59=3;
            switch ( input.LA(1) ) {
            case LABEL:
                {
                alt59=1;
                }
                break;
            case 178:
                {
                alt59=2;
                }
                break;
            case 179:
                {
                alt59=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("594:1: labeledStatement returns [Element e] : ( ^( LABEL IDENTIFIER statement ) | ^( 'case' expression statement ) | ^( 'default' statement ) );", 59, 0, input);

                throw nvae;
            }

            switch (alt59) {
                case 1 :
                    // XMLEmitter.g:595:4: ^( LABEL IDENTIFIER statement )
                    {
                    match(input,LABEL,FOLLOW_LABEL_in_labeledStatement1487); 

                    match(input, Token.DOWN, null); 
                    IDENTIFIER59=(CommonTree)input.LT(1);
                    match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_labeledStatement1489); 
                    pushFollow(FOLLOW_statement_in_labeledStatement1491);
                    statement58=statement();
                    _fsp--;


                    match(input, Token.UP, null); 

                    		e = newElement("labelStatement");
                    		e.add(statement58);
                    		e.addAttribute("id", IDENTIFIER59.getText());
                    	

                    }
                    break;
                case 2 :
                    // XMLEmitter.g:600:4: ^( 'case' expression statement )
                    {
                    match(input,178,FOLLOW_178_in_labeledStatement1500); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_labeledStatement1502);
                    expression60=expression();
                    _fsp--;

                    pushFollow(FOLLOW_statement_in_labeledStatement1504);
                    statement61=statement();
                    _fsp--;


                    match(input, Token.UP, null); 

                    		e = newElement("labeledStatement");
                    		e.add(expression60);
                    		e.add(statement61);
                    	

                    }
                    break;
                case 3 :
                    // XMLEmitter.g:605:4: ^( 'default' statement )
                    {
                    match(input,179,FOLLOW_179_in_labeledStatement1513); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_statement_in_labeledStatement1515);
                    statement62=statement();
                    _fsp--;


                    match(input, Token.UP, null); 

                    		e = newElement("defaultLabelStatement");
                    		e.add(statement62);
                    	

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return e;
    }
    // $ANTLR end labeledStatement


    // $ANTLR start expressionStatement
    // XMLEmitter.g:611:1: expressionStatement returns [Element e] : ^( EXPRESSION_STATEMENT ( expression )? ) ;
    public final Element expressionStatement() throws RecognitionException {
        Element e = null;

        Element expression63 = null;


        try {
            // XMLEmitter.g:612:2: ( ^( EXPRESSION_STATEMENT ( expression )? ) )
            // XMLEmitter.g:612:4: ^( EXPRESSION_STATEMENT ( expression )? )
            {
            match(input,EXPRESSION_STATEMENT,FOLLOW_EXPRESSION_STATEMENT_in_expressionStatement1534); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // XMLEmitter.g:612:27: ( expression )?
                int alt60=2;
                int LA60_0 = input.LA(1);

                if ( ((LA60_0>=PP && LA60_0<=BUILTIN_OFFSETOF)||LA60_0==ASSIGNMENT_EXPRESSION||(LA60_0>=COMPOUND_LITERAL && LA60_0<=ARRAY_ACCESS)||(LA60_0>=CAST_EXPRESSION && LA60_0<=CONDITIONAL_EXPRESSION)||LA60_0==COMPOUND_STATEMENT||(LA60_0>=FUNCTION_CALL && LA60_0<=CONSTANT)||LA60_0==86||LA60_0==129||LA60_0==134||(LA60_0>=138 && LA60_0<=141)||(LA60_0>=143 && LA60_0<=149)||LA60_0==151||(LA60_0>=153 && LA60_0<=166)) ) {
                    alt60=1;
                }
                switch (alt60) {
                    case 1 :
                        // XMLEmitter.g:612:27: expression
                        {
                        pushFollow(FOLLOW_expression_in_expressionStatement1536);
                        expression63=expression();
                        _fsp--;


                        }
                        break;

                }


                match(input, Token.UP, null); 
            }
             e = expression63; 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return e;
    }
    // $ANTLR end expressionStatement


    // $ANTLR start selectionStatement
    // XMLEmitter.g:615:1: selectionStatement returns [Element e] : ( ^( 'if' expression s1= statement (s2= statement )? ) | ^( 'switch' expression statement ) );
    public final Element selectionStatement() throws RecognitionException {
        Element e = null;

        Element s1 = null;

        Element s2 = null;

        Element expression64 = null;

        Element expression65 = null;

        Element statement66 = null;


        try {
            // XMLEmitter.g:616:2: ( ^( 'if' expression s1= statement (s2= statement )? ) | ^( 'switch' expression statement ) )
            int alt62=2;
            int LA62_0 = input.LA(1);

            if ( (LA62_0==180) ) {
                alt62=1;
            }
            else if ( (LA62_0==182) ) {
                alt62=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("615:1: selectionStatement returns [Element e] : ( ^( 'if' expression s1= statement (s2= statement )? ) | ^( 'switch' expression statement ) );", 62, 0, input);

                throw nvae;
            }
            switch (alt62) {
                case 1 :
                    // XMLEmitter.g:616:4: ^( 'if' expression s1= statement (s2= statement )? )
                    {
                    match(input,180,FOLLOW_180_in_selectionStatement1556); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_selectionStatement1558);
                    expression64=expression();
                    _fsp--;

                    pushFollow(FOLLOW_statement_in_selectionStatement1562);
                    s1=statement();
                    _fsp--;

                    // XMLEmitter.g:616:37: (s2= statement )?
                    int alt61=2;
                    int LA61_0 = input.LA(1);

                    if ( ((LA61_0>=EXPRESSION_STATEMENT && LA61_0<=ASM)||(LA61_0>=LABEL && LA61_0<=COMPOUND_STATEMENT)||(LA61_0>=178 && LA61_0<=180)||(LA61_0>=182 && LA61_0<=189)) ) {
                        alt61=1;
                    }
                    switch (alt61) {
                        case 1 :
                            // XMLEmitter.g:616:37: s2= statement
                            {
                            pushFollow(FOLLOW_statement_in_selectionStatement1566);
                            s2=statement();
                            _fsp--;


                            }
                            break;

                    }


                    match(input, Token.UP, null); 

                    		e = newElement(s2 == null ? "ifStatement" : "ifElseStatement");
                    		e.add(expression64);
                    		e.add(s1);
                    		addElementCond(e, s2);
                    	

                    }
                    break;
                case 2 :
                    // XMLEmitter.g:622:4: ^( 'switch' expression statement )
                    {
                    match(input,182,FOLLOW_182_in_selectionStatement1576); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_selectionStatement1578);
                    expression65=expression();
                    _fsp--;

                    pushFollow(FOLLOW_statement_in_selectionStatement1580);
                    statement66=statement();
                    _fsp--;


                    match(input, Token.UP, null); 

                    		e = newElement("switchStatement");
                    		e.add(expression65);
                    		e.add(statement66);
                    	

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return e;
    }
    // $ANTLR end selectionStatement


    // $ANTLR start iterationStatement
    // XMLEmitter.g:629:1: iterationStatement returns [Element e] : ( ^( 'while' expression statement ) | ^( 'do' statement expression ) | ^( 'for' ( declaration )? ( ^( E1 e1= expression ) )? ^( E2 (e2= expression )? ) ^( E3 (e3= expression )? ) statement ) );
    public final Element iterationStatement() throws RecognitionException {
        Element e = null;

        Element e1 = null;

        Element e2 = null;

        Element e3 = null;

        Element expression67 = null;

        Element statement68 = null;

        Element statement69 = null;

        Element expression70 = null;

        Element declaration71 = null;

        Element statement72 = null;


        try {
            // XMLEmitter.g:630:2: ( ^( 'while' expression statement ) | ^( 'do' statement expression ) | ^( 'for' ( declaration )? ( ^( E1 e1= expression ) )? ^( E2 (e2= expression )? ) ^( E3 (e3= expression )? ) statement ) )
            int alt67=3;
            switch ( input.LA(1) ) {
            case 183:
                {
                alt67=1;
                }
                break;
            case 184:
                {
                alt67=2;
                }
                break;
            case 185:
                {
                alt67=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("629:1: iterationStatement returns [Element e] : ( ^( 'while' expression statement ) | ^( 'do' statement expression ) | ^( 'for' ( declaration )? ( ^( E1 e1= expression ) )? ^( E2 (e2= expression )? ) ^( E3 (e3= expression )? ) statement ) );", 67, 0, input);

                throw nvae;
            }

            switch (alt67) {
                case 1 :
                    // XMLEmitter.g:630:4: ^( 'while' expression statement )
                    {
                    match(input,183,FOLLOW_183_in_iterationStatement1599); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_iterationStatement1601);
                    expression67=expression();
                    _fsp--;

                    pushFollow(FOLLOW_statement_in_iterationStatement1603);
                    statement68=statement();
                    _fsp--;


                    match(input, Token.UP, null); 

                    		e = newElement("whileStatement");
                    		e.add(expression67);
                    		e.add(statement68);
                    	

                    }
                    break;
                case 2 :
                    // XMLEmitter.g:635:4: ^( 'do' statement expression )
                    {
                    match(input,184,FOLLOW_184_in_iterationStatement1612); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_statement_in_iterationStatement1614);
                    statement69=statement();
                    _fsp--;

                    pushFollow(FOLLOW_expression_in_iterationStatement1616);
                    expression70=expression();
                    _fsp--;


                    match(input, Token.UP, null); 

                    		e = newElement("doStatement");
                    		e.add(statement69);
                    		e.add(expression70);
                    	

                    }
                    break;
                case 3 :
                    // XMLEmitter.g:640:4: ^( 'for' ( declaration )? ( ^( E1 e1= expression ) )? ^( E2 (e2= expression )? ) ^( E3 (e3= expression )? ) statement )
                    {
                    match(input,185,FOLLOW_185_in_iterationStatement1625); 

                    match(input, Token.DOWN, null); 
                    // XMLEmitter.g:640:12: ( declaration )?
                    int alt63=2;
                    int LA63_0 = input.LA(1);

                    if ( (LA63_0==DECLARATION||LA63_0==85) ) {
                        alt63=1;
                    }
                    switch (alt63) {
                        case 1 :
                            // XMLEmitter.g:640:12: declaration
                            {
                            pushFollow(FOLLOW_declaration_in_iterationStatement1627);
                            declaration71=declaration();
                            _fsp--;


                            }
                            break;

                    }

                    // XMLEmitter.g:640:25: ( ^( E1 e1= expression ) )?
                    int alt64=2;
                    int LA64_0 = input.LA(1);

                    if ( (LA64_0==E1) ) {
                        alt64=1;
                    }
                    switch (alt64) {
                        case 1 :
                            // XMLEmitter.g:640:26: ^( E1 e1= expression )
                            {
                            match(input,E1,FOLLOW_E1_in_iterationStatement1632); 

                            match(input, Token.DOWN, null); 
                            pushFollow(FOLLOW_expression_in_iterationStatement1636);
                            e1=expression();
                            _fsp--;


                            match(input, Token.UP, null); 

                            }
                            break;

                    }

                    match(input,E2,FOLLOW_E2_in_iterationStatement1642); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // XMLEmitter.g:640:55: (e2= expression )?
                        int alt65=2;
                        int LA65_0 = input.LA(1);

                        if ( ((LA65_0>=PP && LA65_0<=BUILTIN_OFFSETOF)||LA65_0==ASSIGNMENT_EXPRESSION||(LA65_0>=COMPOUND_LITERAL && LA65_0<=ARRAY_ACCESS)||(LA65_0>=CAST_EXPRESSION && LA65_0<=CONDITIONAL_EXPRESSION)||LA65_0==COMPOUND_STATEMENT||(LA65_0>=FUNCTION_CALL && LA65_0<=CONSTANT)||LA65_0==86||LA65_0==129||LA65_0==134||(LA65_0>=138 && LA65_0<=141)||(LA65_0>=143 && LA65_0<=149)||LA65_0==151||(LA65_0>=153 && LA65_0<=166)) ) {
                            alt65=1;
                        }
                        switch (alt65) {
                            case 1 :
                                // XMLEmitter.g:640:55: e2= expression
                                {
                                pushFollow(FOLLOW_expression_in_iterationStatement1646);
                                e2=expression();
                                _fsp--;


                                }
                                break;

                        }


                        match(input, Token.UP, null); 
                    }
                    match(input,E3,FOLLOW_E3_in_iterationStatement1651); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // XMLEmitter.g:640:76: (e3= expression )?
                        int alt66=2;
                        int LA66_0 = input.LA(1);

                        if ( ((LA66_0>=PP && LA66_0<=BUILTIN_OFFSETOF)||LA66_0==ASSIGNMENT_EXPRESSION||(LA66_0>=COMPOUND_LITERAL && LA66_0<=ARRAY_ACCESS)||(LA66_0>=CAST_EXPRESSION && LA66_0<=CONDITIONAL_EXPRESSION)||LA66_0==COMPOUND_STATEMENT||(LA66_0>=FUNCTION_CALL && LA66_0<=CONSTANT)||LA66_0==86||LA66_0==129||LA66_0==134||(LA66_0>=138 && LA66_0<=141)||(LA66_0>=143 && LA66_0<=149)||LA66_0==151||(LA66_0>=153 && LA66_0<=166)) ) {
                            alt66=1;
                        }
                        switch (alt66) {
                            case 1 :
                                // XMLEmitter.g:640:76: e3= expression
                                {
                                pushFollow(FOLLOW_expression_in_iterationStatement1655);
                                e3=expression();
                                _fsp--;


                                }
                                break;

                        }


                        match(input, Token.UP, null); 
                    }
                    pushFollow(FOLLOW_statement_in_iterationStatement1659);
                    statement72=statement();
                    _fsp--;


                    match(input, Token.UP, null); 

                    		e = newElement("forStatement");
                    		if (declaration71 != null)
                    			e.add(declaration71);
                    		else if (e1 != null)
                    			e.add(e1);
                    		else
                    			e.addElement("expression");
                    		if (e2 != null)
                    			e.add(e2);
                    		else
                    			e.addElement("expression");
                    		if (e3 != null)
                    			e.add(e3);
                    		else
                    			e.addElement("expression");
                    		e.add(statement72);
                    	

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return e;
    }
    // $ANTLR end iterationStatement


    // $ANTLR start jumpStatement
    // XMLEmitter.g:660:1: jumpStatement returns [Element e] : ( ^( 'goto' IDENTIFIER ) | ^( 'goto' XU expression ) | 'continue' | 'break' | ^( 'return' ( expression )? ) );
    public final Element jumpStatement() throws RecognitionException {
        Element e = null;

        CommonTree IDENTIFIER73=null;
        Element expression74 = null;

        Element expression75 = null;


        try {
            // XMLEmitter.g:661:2: ( ^( 'goto' IDENTIFIER ) | ^( 'goto' XU expression ) | 'continue' | 'break' | ^( 'return' ( expression )? ) )
            int alt69=5;
            switch ( input.LA(1) ) {
            case 186:
                {
                int LA69_1 = input.LA(2);

                if ( (LA69_1==DOWN) ) {
                    int LA69_5 = input.LA(3);

                    if ( (LA69_5==XU) ) {
                        alt69=2;
                    }
                    else if ( (LA69_5==IDENTIFIER) ) {
                        alt69=1;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("660:1: jumpStatement returns [Element e] : ( ^( 'goto' IDENTIFIER ) | ^( 'goto' XU expression ) | 'continue' | 'break' | ^( 'return' ( expression )? ) );", 69, 5, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("660:1: jumpStatement returns [Element e] : ( ^( 'goto' IDENTIFIER ) | ^( 'goto' XU expression ) | 'continue' | 'break' | ^( 'return' ( expression )? ) );", 69, 1, input);

                    throw nvae;
                }
                }
                break;
            case 187:
                {
                alt69=3;
                }
                break;
            case 188:
                {
                alt69=4;
                }
                break;
            case 189:
                {
                alt69=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("660:1: jumpStatement returns [Element e] : ( ^( 'goto' IDENTIFIER ) | ^( 'goto' XU expression ) | 'continue' | 'break' | ^( 'return' ( expression )? ) );", 69, 0, input);

                throw nvae;
            }

            switch (alt69) {
                case 1 :
                    // XMLEmitter.g:661:4: ^( 'goto' IDENTIFIER )
                    {
                    match(input,186,FOLLOW_186_in_jumpStatement1678); 

                    match(input, Token.DOWN, null); 
                    IDENTIFIER73=(CommonTree)input.LT(1);
                    match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_jumpStatement1680); 

                    match(input, Token.UP, null); 

                    		e = newElement("gotoStatement");
                    		e.addElement("expression").addElement("id").addText(IDENTIFIER73.getText());
                    	

                    }
                    break;
                case 2 :
                    // XMLEmitter.g:665:4: ^( 'goto' XU expression )
                    {
                    match(input,186,FOLLOW_186_in_jumpStatement1689); 

                    match(input, Token.DOWN, null); 
                    match(input,XU,FOLLOW_XU_in_jumpStatement1691); 
                    pushFollow(FOLLOW_expression_in_jumpStatement1693);
                    expression74=expression();
                    _fsp--;


                    match(input, Token.UP, null); 

                    		e = newElement("gotoStatement");
                    		e.addElement("expression").addElement("derefExpression").add(expression74);
                    	

                    }
                    break;
                case 3 :
                    // XMLEmitter.g:669:4: 'continue'
                    {
                    match(input,187,FOLLOW_187_in_jumpStatement1701); 
                     e = newElement("continueStatement"); 

                    }
                    break;
                case 4 :
                    // XMLEmitter.g:670:4: 'break'
                    {
                    match(input,188,FOLLOW_188_in_jumpStatement1708); 
                     e = newElement("breakStatement"); 

                    }
                    break;
                case 5 :
                    // XMLEmitter.g:671:4: ^( 'return' ( expression )? )
                    {
                    match(input,189,FOLLOW_189_in_jumpStatement1716); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // XMLEmitter.g:671:15: ( expression )?
                        int alt68=2;
                        int LA68_0 = input.LA(1);

                        if ( ((LA68_0>=PP && LA68_0<=BUILTIN_OFFSETOF)||LA68_0==ASSIGNMENT_EXPRESSION||(LA68_0>=COMPOUND_LITERAL && LA68_0<=ARRAY_ACCESS)||(LA68_0>=CAST_EXPRESSION && LA68_0<=CONDITIONAL_EXPRESSION)||LA68_0==COMPOUND_STATEMENT||(LA68_0>=FUNCTION_CALL && LA68_0<=CONSTANT)||LA68_0==86||LA68_0==129||LA68_0==134||(LA68_0>=138 && LA68_0<=141)||(LA68_0>=143 && LA68_0<=149)||LA68_0==151||(LA68_0>=153 && LA68_0<=166)) ) {
                            alt68=1;
                        }
                        switch (alt68) {
                            case 1 :
                                // XMLEmitter.g:671:15: expression
                                {
                                pushFollow(FOLLOW_expression_in_jumpStatement1718);
                                expression75=expression();
                                _fsp--;


                                }
                                break;

                        }


                        match(input, Token.UP, null); 
                    }

                    		e = newElement("returnStatement");
                    		addElementCond(e, expression75);
                    	

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return e;
    }
    // $ANTLR end jumpStatement


    // $ANTLR start asmStatement
    // XMLEmitter.g:677:1: asmStatement returns [Element e] : ASM ;
    public final Element asmStatement() throws RecognitionException {
        Element e = null;

        try {
            // XMLEmitter.g:678:2: ( ASM )
            // XMLEmitter.g:678:4: ASM
            {
            match(input,ASM,FOLLOW_ASM_in_asmStatement1737); 
             e = newElement("gnuAssembler"); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return e;
    }
    // $ANTLR end asmStatement


    // $ANTLR start expression
    // XMLEmitter.g:685:1: expression returns [Element e] : ( ^( ASSIGNMENT_EXPRESSION assignmentOperator e1= expression e2= expression ) | ^( CONDITIONAL_EXPRESSION ^( E1 e1= expression ) ^( E2 (e2= expression )? ) ^( E3 e3= expression ) ) | ^( CAST_EXPRESSION e1= typeName e2= expression ) | ^( ARRAY_ACCESS e1= expression e2= expression ) | ^( FUNCTION_CALL e1= expression (e2= expression )* ) | ^( COMPOUND_LITERAL e1= typeName initializerList ) | ^( ',' e1= expression e2= expression ) | ^( '++' e1= expression ) | ^( '--' e1= expression ) | ^( unaryOp e1= expression ) | ^( 'sizeof' (e1= expression | e1= typeName ) ) | ^( '__alignof__' (e1= expression | e1= typeName ) ) | ^( '.' e1= expression IDENTIFIER ) | ^( '->' e1= expression IDENTIFIER ) | ^( AU e1= expression ) | ^( XU e1= expression ) | ^( PP e1= expression ) | ^( MM e1= expression ) | binaryExpression | primaryExpression );
    public final Element expression() throws RecognitionException {
        Element e = null;

        CommonTree IDENTIFIER79=null;
        CommonTree IDENTIFIER80=null;
        Element e1 = null;

        Element e2 = null;

        Element e3 = null;

        assignmentOperator_return assignmentOperator76 = null;

        List<Element> initializerList77 = null;

        String unaryOp78 = null;

        Element binaryExpression81 = null;

        Element primaryExpression82 = null;



        	List<Element> exs = new ArrayList<Element>();
        	Element exp;
        	e = newElement("expression");

        try {
            // XMLEmitter.g:691:2: ( ^( ASSIGNMENT_EXPRESSION assignmentOperator e1= expression e2= expression ) | ^( CONDITIONAL_EXPRESSION ^( E1 e1= expression ) ^( E2 (e2= expression )? ) ^( E3 e3= expression ) ) | ^( CAST_EXPRESSION e1= typeName e2= expression ) | ^( ARRAY_ACCESS e1= expression e2= expression ) | ^( FUNCTION_CALL e1= expression (e2= expression )* ) | ^( COMPOUND_LITERAL e1= typeName initializerList ) | ^( ',' e1= expression e2= expression ) | ^( '++' e1= expression ) | ^( '--' e1= expression ) | ^( unaryOp e1= expression ) | ^( 'sizeof' (e1= expression | e1= typeName ) ) | ^( '__alignof__' (e1= expression | e1= typeName ) ) | ^( '.' e1= expression IDENTIFIER ) | ^( '->' e1= expression IDENTIFIER ) | ^( AU e1= expression ) | ^( XU e1= expression ) | ^( PP e1= expression ) | ^( MM e1= expression ) | binaryExpression | primaryExpression )
            int alt74=20;
            switch ( input.LA(1) ) {
            case ASSIGNMENT_EXPRESSION:
                {
                alt74=1;
                }
                break;
            case CONDITIONAL_EXPRESSION:
                {
                alt74=2;
                }
                break;
            case CAST_EXPRESSION:
                {
                alt74=3;
                }
                break;
            case ARRAY_ACCESS:
                {
                alt74=4;
                }
                break;
            case FUNCTION_CALL:
                {
                alt74=5;
                }
                break;
            case COMPOUND_LITERAL:
                {
                alt74=6;
                }
                break;
            case 86:
                {
                alt74=7;
                }
                break;
            case 139:
                {
                alt74=8;
                }
                break;
            case 140:
                {
                alt74=9;
                }
                break;
            case PU:
            case MU:
            case LABREF:
            case 147:
            case 148:
            case 151:
            case 153:
                {
                alt74=10;
                }
                break;
            case 141:
                {
                alt74=11;
                }
                break;
            case 143:
                {
                alt74=12;
                }
                break;
            case 134:
                {
                alt74=13;
                }
                break;
            case 138:
                {
                alt74=14;
                }
                break;
            case AU:
                {
                alt74=15;
                }
                break;
            case XU:
                {
                alt74=16;
                }
                break;
            case PP:
                {
                alt74=17;
                }
                break;
            case MM:
                {
                alt74=18;
                }
                break;
            case 129:
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
                {
                alt74=19;
                }
                break;
            case BUILTIN_OFFSETOF:
            case COMPOUND_STATEMENT:
            case STR_LITERAL:
            case IDENTIFIER:
            case CONSTANT:
                {
                alt74=20;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("685:1: expression returns [Element e] : ( ^( ASSIGNMENT_EXPRESSION assignmentOperator e1= expression e2= expression ) | ^( CONDITIONAL_EXPRESSION ^( E1 e1= expression ) ^( E2 (e2= expression )? ) ^( E3 e3= expression ) ) | ^( CAST_EXPRESSION e1= typeName e2= expression ) | ^( ARRAY_ACCESS e1= expression e2= expression ) | ^( FUNCTION_CALL e1= expression (e2= expression )* ) | ^( COMPOUND_LITERAL e1= typeName initializerList ) | ^( ',' e1= expression e2= expression ) | ^( '++' e1= expression ) | ^( '--' e1= expression ) | ^( unaryOp e1= expression ) | ^( 'sizeof' (e1= expression | e1= typeName ) ) | ^( '__alignof__' (e1= expression | e1= typeName ) ) | ^( '.' e1= expression IDENTIFIER ) | ^( '->' e1= expression IDENTIFIER ) | ^( AU e1= expression ) | ^( XU e1= expression ) | ^( PP e1= expression ) | ^( MM e1= expression ) | binaryExpression | primaryExpression );", 74, 0, input);

                throw nvae;
            }

            switch (alt74) {
                case 1 :
                    // XMLEmitter.g:691:4: ^( ASSIGNMENT_EXPRESSION assignmentOperator e1= expression e2= expression )
                    {
                    match(input,ASSIGNMENT_EXPRESSION,FOLLOW_ASSIGNMENT_EXPRESSION_in_expression1767); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_assignmentOperator_in_expression1769);
                    assignmentOperator76=assignmentOperator();
                    _fsp--;

                    pushFollow(FOLLOW_expression_in_expression1773);
                    e1=expression();
                    _fsp--;

                    pushFollow(FOLLOW_expression_in_expression1777);
                    e2=expression();
                    _fsp--;


                    match(input, Token.UP, null); 

                    		String op = input.getTokenStream().toString(
                      input.getTreeAdaptor().getTokenStartIndex(assignmentOperator76.start),
                      input.getTreeAdaptor().getTokenStopIndex(assignmentOperator76.start));
                    		exp = addElementBin(e, "assignExpression", e1, e2);
                    		if (!op.equals("="))
                    			exp.addAttribute("op", op.substring(0, op.length() - 1));
                    	

                    }
                    break;
                case 2 :
                    // XMLEmitter.g:697:4: ^( CONDITIONAL_EXPRESSION ^( E1 e1= expression ) ^( E2 (e2= expression )? ) ^( E3 e3= expression ) )
                    {
                    match(input,CONDITIONAL_EXPRESSION,FOLLOW_CONDITIONAL_EXPRESSION_in_expression1786); 

                    match(input, Token.DOWN, null); 
                    match(input,E1,FOLLOW_E1_in_expression1789); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_expression1793);
                    e1=expression();
                    _fsp--;


                    match(input, Token.UP, null); 
                    match(input,E2,FOLLOW_E2_in_expression1797); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // XMLEmitter.g:697:56: (e2= expression )?
                        int alt70=2;
                        int LA70_0 = input.LA(1);

                        if ( ((LA70_0>=PP && LA70_0<=BUILTIN_OFFSETOF)||LA70_0==ASSIGNMENT_EXPRESSION||(LA70_0>=COMPOUND_LITERAL && LA70_0<=ARRAY_ACCESS)||(LA70_0>=CAST_EXPRESSION && LA70_0<=CONDITIONAL_EXPRESSION)||LA70_0==COMPOUND_STATEMENT||(LA70_0>=FUNCTION_CALL && LA70_0<=CONSTANT)||LA70_0==86||LA70_0==129||LA70_0==134||(LA70_0>=138 && LA70_0<=141)||(LA70_0>=143 && LA70_0<=149)||LA70_0==151||(LA70_0>=153 && LA70_0<=166)) ) {
                            alt70=1;
                        }
                        switch (alt70) {
                            case 1 :
                                // XMLEmitter.g:697:56: e2= expression
                                {
                                pushFollow(FOLLOW_expression_in_expression1801);
                                e2=expression();
                                _fsp--;


                                }
                                break;

                        }


                        match(input, Token.UP, null); 
                    }
                    match(input,E3,FOLLOW_E3_in_expression1806); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_expression1810);
                    e3=expression();
                    _fsp--;


                    match(input, Token.UP, null); 

                    match(input, Token.UP, null); 

                    		exp = e.addElement("conditionalExpression");
                    		exp.add(e1);
                    		addElementCond(exp, e2);
                    		exp.add(e3);
                    	

                    }
                    break;
                case 3 :
                    // XMLEmitter.g:703:4: ^( CAST_EXPRESSION e1= typeName e2= expression )
                    {
                    match(input,CAST_EXPRESSION,FOLLOW_CAST_EXPRESSION_in_expression1820); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_typeName_in_expression1824);
                    e1=typeName();
                    _fsp--;

                    pushFollow(FOLLOW_expression_in_expression1828);
                    e2=expression();
                    _fsp--;


                    match(input, Token.UP, null); 
                     addElementBin(e, "castExpression", e1, e2); 

                    }
                    break;
                case 4 :
                    // XMLEmitter.g:704:4: ^( ARRAY_ACCESS e1= expression e2= expression )
                    {
                    match(input,ARRAY_ACCESS,FOLLOW_ARRAY_ACCESS_in_expression1837); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_expression1841);
                    e1=expression();
                    _fsp--;

                    pushFollow(FOLLOW_expression_in_expression1845);
                    e2=expression();
                    _fsp--;


                    match(input, Token.UP, null); 
                     addElementBin(e, "arrayAccess", e1, e2); 

                    }
                    break;
                case 5 :
                    // XMLEmitter.g:705:4: ^( FUNCTION_CALL e1= expression (e2= expression )* )
                    {
                    match(input,FUNCTION_CALL,FOLLOW_FUNCTION_CALL_in_expression1854); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_expression1858);
                    e1=expression();
                    _fsp--;

                    // XMLEmitter.g:705:34: (e2= expression )*
                    loop71:
                    do {
                        int alt71=2;
                        int LA71_0 = input.LA(1);

                        if ( ((LA71_0>=PP && LA71_0<=BUILTIN_OFFSETOF)||LA71_0==ASSIGNMENT_EXPRESSION||(LA71_0>=COMPOUND_LITERAL && LA71_0<=ARRAY_ACCESS)||(LA71_0>=CAST_EXPRESSION && LA71_0<=CONDITIONAL_EXPRESSION)||LA71_0==COMPOUND_STATEMENT||(LA71_0>=FUNCTION_CALL && LA71_0<=CONSTANT)||LA71_0==86||LA71_0==129||LA71_0==134||(LA71_0>=138 && LA71_0<=141)||(LA71_0>=143 && LA71_0<=149)||LA71_0==151||(LA71_0>=153 && LA71_0<=166)) ) {
                            alt71=1;
                        }


                        switch (alt71) {
                    	case 1 :
                    	    // XMLEmitter.g:705:35: e2= expression
                    	    {
                    	    pushFollow(FOLLOW_expression_in_expression1863);
                    	    e2=expression();
                    	    _fsp--;

                    	    exs.add(e2);

                    	    }
                    	    break;

                    	default :
                    	    break loop71;
                        }
                    } while (true);


                    match(input, Token.UP, null); 

                    		exp = e.addElement("functionCall");
                    		exp.add(e1);
                    		for (Element el: exs)
                    			exp.add(el);
                    	

                    }
                    break;
                case 6 :
                    // XMLEmitter.g:711:4: ^( COMPOUND_LITERAL e1= typeName initializerList )
                    {
                    match(input,COMPOUND_LITERAL,FOLLOW_COMPOUND_LITERAL_in_expression1876); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_typeName_in_expression1880);
                    e1=typeName();
                    _fsp--;

                    pushFollow(FOLLOW_initializerList_in_expression1882);
                    initializerList77=initializerList();
                    _fsp--;


                    match(input, Token.UP, null); 

                    		addElementBin(e, "compoundLiteral", e1, e2 = newElement("initializer"));
                    		addAllElements(e2, initializerList77);
                    	

                    }
                    break;
                case 7 :
                    // XMLEmitter.g:715:4: ^( ',' e1= expression e2= expression )
                    {
                    match(input,86,FOLLOW_86_in_expression1891); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_expression1895);
                    e1=expression();
                    _fsp--;

                    pushFollow(FOLLOW_expression_in_expression1899);
                    e2=expression();
                    _fsp--;


                    match(input, Token.UP, null); 
                     addElementBin(e, "commaExpression", e1, e2); 

                    }
                    break;
                case 8 :
                    // XMLEmitter.g:716:4: ^( '++' e1= expression )
                    {
                    match(input,139,FOLLOW_139_in_expression1908); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_expression1912);
                    e1=expression();
                    _fsp--;


                    match(input, Token.UP, null); 
                     e.addElement("prefixExpression").addAttribute("op", "++").add(e1); 

                    }
                    break;
                case 9 :
                    // XMLEmitter.g:717:4: ^( '--' e1= expression )
                    {
                    match(input,140,FOLLOW_140_in_expression1922); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_expression1926);
                    e1=expression();
                    _fsp--;


                    match(input, Token.UP, null); 
                     e.addElement("prefixExpression").addAttribute("op", "--").add(e1); 

                    }
                    break;
                case 10 :
                    // XMLEmitter.g:718:4: ^( unaryOp e1= expression )
                    {
                    pushFollow(FOLLOW_unaryOp_in_expression1936);
                    unaryOp78=unaryOp();
                    _fsp--;


                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_expression1940);
                    e1=expression();
                    _fsp--;


                    match(input, Token.UP, null); 
                     e.addElement("prefixExpression").addAttribute("op", unaryOp78).add(e1); 

                    }
                    break;
                case 11 :
                    // XMLEmitter.g:719:4: ^( 'sizeof' (e1= expression | e1= typeName ) )
                    {
                    match(input,141,FOLLOW_141_in_expression1949); 

                    match(input, Token.DOWN, null); 
                    // XMLEmitter.g:719:15: (e1= expression | e1= typeName )
                    int alt72=2;
                    int LA72_0 = input.LA(1);

                    if ( ((LA72_0>=PP && LA72_0<=BUILTIN_OFFSETOF)||LA72_0==ASSIGNMENT_EXPRESSION||(LA72_0>=COMPOUND_LITERAL && LA72_0<=ARRAY_ACCESS)||(LA72_0>=CAST_EXPRESSION && LA72_0<=CONDITIONAL_EXPRESSION)||LA72_0==COMPOUND_STATEMENT||(LA72_0>=FUNCTION_CALL && LA72_0<=CONSTANT)||LA72_0==86||LA72_0==129||LA72_0==134||(LA72_0>=138 && LA72_0<=141)||(LA72_0>=143 && LA72_0<=149)||LA72_0==151||(LA72_0>=153 && LA72_0<=166)) ) {
                        alt72=1;
                    }
                    else if ( (LA72_0==TYPE_NAME) ) {
                        alt72=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("719:15: (e1= expression | e1= typeName )", 72, 0, input);

                        throw nvae;
                    }
                    switch (alt72) {
                        case 1 :
                            // XMLEmitter.g:719:16: e1= expression
                            {
                            pushFollow(FOLLOW_expression_in_expression1954);
                            e1=expression();
                            _fsp--;


                            }
                            break;
                        case 2 :
                            // XMLEmitter.g:719:30: e1= typeName
                            {
                            pushFollow(FOLLOW_typeName_in_expression1958);
                            e1=typeName();
                            _fsp--;


                            }
                            break;

                    }


                    match(input, Token.UP, null); 
                     e.addElement("sizeofExpression").add(e1); 

                    }
                    break;
                case 12 :
                    // XMLEmitter.g:720:4: ^( '__alignof__' (e1= expression | e1= typeName ) )
                    {
                    match(input,143,FOLLOW_143_in_expression1968); 

                    match(input, Token.DOWN, null); 
                    // XMLEmitter.g:720:20: (e1= expression | e1= typeName )
                    int alt73=2;
                    int LA73_0 = input.LA(1);

                    if ( ((LA73_0>=PP && LA73_0<=BUILTIN_OFFSETOF)||LA73_0==ASSIGNMENT_EXPRESSION||(LA73_0>=COMPOUND_LITERAL && LA73_0<=ARRAY_ACCESS)||(LA73_0>=CAST_EXPRESSION && LA73_0<=CONDITIONAL_EXPRESSION)||LA73_0==COMPOUND_STATEMENT||(LA73_0>=FUNCTION_CALL && LA73_0<=CONSTANT)||LA73_0==86||LA73_0==129||LA73_0==134||(LA73_0>=138 && LA73_0<=141)||(LA73_0>=143 && LA73_0<=149)||LA73_0==151||(LA73_0>=153 && LA73_0<=166)) ) {
                        alt73=1;
                    }
                    else if ( (LA73_0==TYPE_NAME) ) {
                        alt73=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("720:20: (e1= expression | e1= typeName )", 73, 0, input);

                        throw nvae;
                    }
                    switch (alt73) {
                        case 1 :
                            // XMLEmitter.g:720:21: e1= expression
                            {
                            pushFollow(FOLLOW_expression_in_expression1973);
                            e1=expression();
                            _fsp--;


                            }
                            break;
                        case 2 :
                            // XMLEmitter.g:720:35: e1= typeName
                            {
                            pushFollow(FOLLOW_typeName_in_expression1977);
                            e1=typeName();
                            _fsp--;


                            }
                            break;

                    }


                    match(input, Token.UP, null); 
                     e.addElement("allignofExpression").add(e1); 

                    }
                    break;
                case 13 :
                    // XMLEmitter.g:721:4: ^( '.' e1= expression IDENTIFIER )
                    {
                    match(input,134,FOLLOW_134_in_expression1987); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_expression1991);
                    e1=expression();
                    _fsp--;

                    IDENTIFIER79=(CommonTree)input.LT(1);
                    match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_expression1993); 

                    match(input, Token.UP, null); 

                    		exp = e.addElement("dotExpression");
                    		exp.add(e1);
                    		exp.addElement("member").addText(IDENTIFIER79.getText());
                    	

                    }
                    break;
                case 14 :
                    // XMLEmitter.g:726:4: ^( '->' e1= expression IDENTIFIER )
                    {
                    match(input,138,FOLLOW_138_in_expression2002); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_expression2006);
                    e1=expression();
                    _fsp--;

                    IDENTIFIER80=(CommonTree)input.LT(1);
                    match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_expression2008); 

                    match(input, Token.UP, null); 

                    		exp = e.addElement("arrowExpression");
                    		exp.add(e1);
                    		exp.addElement("member").addText(IDENTIFIER80.getText());
                    	

                    }
                    break;
                case 15 :
                    // XMLEmitter.g:731:4: ^( AU e1= expression )
                    {
                    match(input,AU,FOLLOW_AU_in_expression2017); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_expression2021);
                    e1=expression();
                    _fsp--;


                    match(input, Token.UP, null); 
                     e.addElement("addrExpression").add(e1); 

                    }
                    break;
                case 16 :
                    // XMLEmitter.g:732:4: ^( XU e1= expression )
                    {
                    match(input,XU,FOLLOW_XU_in_expression2030); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_expression2034);
                    e1=expression();
                    _fsp--;


                    match(input, Token.UP, null); 
                     e.addElement("derefExpression").add(e1); 

                    }
                    break;
                case 17 :
                    // XMLEmitter.g:733:4: ^( PP e1= expression )
                    {
                    match(input,PP,FOLLOW_PP_in_expression2043); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_expression2047);
                    e1=expression();
                    _fsp--;


                    match(input, Token.UP, null); 
                     e.addElement("postfixExpression").addAttribute("op", "++").add(e1); 

                    }
                    break;
                case 18 :
                    // XMLEmitter.g:734:4: ^( MM e1= expression )
                    {
                    match(input,MM,FOLLOW_MM_in_expression2056); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_expression2060);
                    e1=expression();
                    _fsp--;


                    match(input, Token.UP, null); 
                     e.addElement("postfixExpression").addAttribute("op", "--").add(e1); 

                    }
                    break;
                case 19 :
                    // XMLEmitter.g:735:4: binaryExpression
                    {
                    pushFollow(FOLLOW_binaryExpression_in_expression2068);
                    binaryExpression81=binaryExpression();
                    _fsp--;

                     e.add(binaryExpression81); 

                    }
                    break;
                case 20 :
                    // XMLEmitter.g:736:4: primaryExpression
                    {
                    pushFollow(FOLLOW_primaryExpression_in_expression2075);
                    primaryExpression82=primaryExpression();
                    _fsp--;

                     e.add(primaryExpression82); 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return e;
    }
    // $ANTLR end expression


    // $ANTLR start binaryExpression
    // XMLEmitter.g:739:1: binaryExpression returns [Element e] : ( ^(op= '||' e1= expression e2= expression ) | ^(op= '&&' e1= expression e2= expression ) | ^(op= '|' e1= expression e2= expression ) | ^(op= '&' e1= expression e2= expression ) | ^(op= '^' e1= expression e2= expression ) | ^(op= '==' e1= expression e2= expression ) | ^(op= '!=' e1= expression e2= expression ) | ^(op= '<=' e1= expression e2= expression ) | ^(op= '<' e1= expression e2= expression ) | ^(op= '>=' e1= expression e2= expression ) | ^(op= '>' e1= expression e2= expression ) | ^(op= '>>' e1= expression e2= expression ) | ^(op= '<<' e1= expression e2= expression ) | ^(op= '+' e1= expression e2= expression ) | ^(op= '-' e1= expression e2= expression ) | ^(op= '*' e1= expression e2= expression ) | ^(op= '/' e1= expression e2= expression ) | ^(op= '%' e1= expression e2= expression ) );
    public final Element binaryExpression() throws RecognitionException {
        Element e = null;

        CommonTree op=null;
        Element e1 = null;

        Element e2 = null;


        try {
            // XMLEmitter.g:746:2: ( ^(op= '||' e1= expression e2= expression ) | ^(op= '&&' e1= expression e2= expression ) | ^(op= '|' e1= expression e2= expression ) | ^(op= '&' e1= expression e2= expression ) | ^(op= '^' e1= expression e2= expression ) | ^(op= '==' e1= expression e2= expression ) | ^(op= '!=' e1= expression e2= expression ) | ^(op= '<=' e1= expression e2= expression ) | ^(op= '<' e1= expression e2= expression ) | ^(op= '>=' e1= expression e2= expression ) | ^(op= '>' e1= expression e2= expression ) | ^(op= '>>' e1= expression e2= expression ) | ^(op= '<<' e1= expression e2= expression ) | ^(op= '+' e1= expression e2= expression ) | ^(op= '-' e1= expression e2= expression ) | ^(op= '*' e1= expression e2= expression ) | ^(op= '/' e1= expression e2= expression ) | ^(op= '%' e1= expression e2= expression ) )
            int alt75=18;
            switch ( input.LA(1) ) {
            case 166:
                {
                alt75=1;
                }
                break;
            case 149:
                {
                alt75=2;
                }
                break;
            case 165:
                {
                alt75=3;
                }
                break;
            case 144:
                {
                alt75=4;
                }
                break;
            case 164:
                {
                alt75=5;
                }
                break;
            case 162:
                {
                alt75=6;
                }
                break;
            case 163:
                {
                alt75=7;
                }
                break;
            case 160:
                {
                alt75=8;
                }
                break;
            case 158:
                {
                alt75=9;
                }
                break;
            case 161:
                {
                alt75=10;
                }
                break;
            case 159:
                {
                alt75=11;
                }
                break;
            case 157:
                {
                alt75=12;
                }
                break;
            case 156:
                {
                alt75=13;
                }
                break;
            case 145:
                {
                alt75=14;
                }
                break;
            case 146:
                {
                alt75=15;
                }
                break;
            case 129:
                {
                alt75=16;
                }
                break;
            case 154:
                {
                alt75=17;
                }
                break;
            case 155:
                {
                alt75=18;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("739:1: binaryExpression returns [Element e] : ( ^(op= '||' e1= expression e2= expression ) | ^(op= '&&' e1= expression e2= expression ) | ^(op= '|' e1= expression e2= expression ) | ^(op= '&' e1= expression e2= expression ) | ^(op= '^' e1= expression e2= expression ) | ^(op= '==' e1= expression e2= expression ) | ^(op= '!=' e1= expression e2= expression ) | ^(op= '<=' e1= expression e2= expression ) | ^(op= '<' e1= expression e2= expression ) | ^(op= '>=' e1= expression e2= expression ) | ^(op= '>' e1= expression e2= expression ) | ^(op= '>>' e1= expression e2= expression ) | ^(op= '<<' e1= expression e2= expression ) | ^(op= '+' e1= expression e2= expression ) | ^(op= '-' e1= expression e2= expression ) | ^(op= '*' e1= expression e2= expression ) | ^(op= '/' e1= expression e2= expression ) | ^(op= '%' e1= expression e2= expression ) );", 75, 0, input);

                throw nvae;
            }

            switch (alt75) {
                case 1 :
                    // XMLEmitter.g:746:4: ^(op= '||' e1= expression e2= expression )
                    {
                    op=(CommonTree)input.LT(1);
                    match(input,166,FOLLOW_166_in_binaryExpression2100); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_binaryExpression2104);
                    e1=expression();
                    _fsp--;

                    pushFollow(FOLLOW_expression_in_binaryExpression2108);
                    e2=expression();
                    _fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // XMLEmitter.g:747:4: ^(op= '&&' e1= expression e2= expression )
                    {
                    op=(CommonTree)input.LT(1);
                    match(input,149,FOLLOW_149_in_binaryExpression2117); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_binaryExpression2121);
                    e1=expression();
                    _fsp--;

                    pushFollow(FOLLOW_expression_in_binaryExpression2125);
                    e2=expression();
                    _fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // XMLEmitter.g:748:4: ^(op= '|' e1= expression e2= expression )
                    {
                    op=(CommonTree)input.LT(1);
                    match(input,165,FOLLOW_165_in_binaryExpression2134); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_binaryExpression2138);
                    e1=expression();
                    _fsp--;

                    pushFollow(FOLLOW_expression_in_binaryExpression2142);
                    e2=expression();
                    _fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // XMLEmitter.g:749:4: ^(op= '&' e1= expression e2= expression )
                    {
                    op=(CommonTree)input.LT(1);
                    match(input,144,FOLLOW_144_in_binaryExpression2151); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_binaryExpression2155);
                    e1=expression();
                    _fsp--;

                    pushFollow(FOLLOW_expression_in_binaryExpression2159);
                    e2=expression();
                    _fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // XMLEmitter.g:750:4: ^(op= '^' e1= expression e2= expression )
                    {
                    op=(CommonTree)input.LT(1);
                    match(input,164,FOLLOW_164_in_binaryExpression2168); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_binaryExpression2172);
                    e1=expression();
                    _fsp--;

                    pushFollow(FOLLOW_expression_in_binaryExpression2176);
                    e2=expression();
                    _fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // XMLEmitter.g:751:4: ^(op= '==' e1= expression e2= expression )
                    {
                    op=(CommonTree)input.LT(1);
                    match(input,162,FOLLOW_162_in_binaryExpression2185); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_binaryExpression2189);
                    e1=expression();
                    _fsp--;

                    pushFollow(FOLLOW_expression_in_binaryExpression2193);
                    e2=expression();
                    _fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 7 :
                    // XMLEmitter.g:752:4: ^(op= '!=' e1= expression e2= expression )
                    {
                    op=(CommonTree)input.LT(1);
                    match(input,163,FOLLOW_163_in_binaryExpression2202); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_binaryExpression2206);
                    e1=expression();
                    _fsp--;

                    pushFollow(FOLLOW_expression_in_binaryExpression2210);
                    e2=expression();
                    _fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 8 :
                    // XMLEmitter.g:753:4: ^(op= '<=' e1= expression e2= expression )
                    {
                    op=(CommonTree)input.LT(1);
                    match(input,160,FOLLOW_160_in_binaryExpression2219); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_binaryExpression2223);
                    e1=expression();
                    _fsp--;

                    pushFollow(FOLLOW_expression_in_binaryExpression2227);
                    e2=expression();
                    _fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 9 :
                    // XMLEmitter.g:754:4: ^(op= '<' e1= expression e2= expression )
                    {
                    op=(CommonTree)input.LT(1);
                    match(input,158,FOLLOW_158_in_binaryExpression2236); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_binaryExpression2240);
                    e1=expression();
                    _fsp--;

                    pushFollow(FOLLOW_expression_in_binaryExpression2244);
                    e2=expression();
                    _fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 10 :
                    // XMLEmitter.g:755:4: ^(op= '>=' e1= expression e2= expression )
                    {
                    op=(CommonTree)input.LT(1);
                    match(input,161,FOLLOW_161_in_binaryExpression2253); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_binaryExpression2257);
                    e1=expression();
                    _fsp--;

                    pushFollow(FOLLOW_expression_in_binaryExpression2261);
                    e2=expression();
                    _fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 11 :
                    // XMLEmitter.g:756:4: ^(op= '>' e1= expression e2= expression )
                    {
                    op=(CommonTree)input.LT(1);
                    match(input,159,FOLLOW_159_in_binaryExpression2270); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_binaryExpression2274);
                    e1=expression();
                    _fsp--;

                    pushFollow(FOLLOW_expression_in_binaryExpression2278);
                    e2=expression();
                    _fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 12 :
                    // XMLEmitter.g:757:4: ^(op= '>>' e1= expression e2= expression )
                    {
                    op=(CommonTree)input.LT(1);
                    match(input,157,FOLLOW_157_in_binaryExpression2287); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_binaryExpression2291);
                    e1=expression();
                    _fsp--;

                    pushFollow(FOLLOW_expression_in_binaryExpression2295);
                    e2=expression();
                    _fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 13 :
                    // XMLEmitter.g:758:4: ^(op= '<<' e1= expression e2= expression )
                    {
                    op=(CommonTree)input.LT(1);
                    match(input,156,FOLLOW_156_in_binaryExpression2304); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_binaryExpression2308);
                    e1=expression();
                    _fsp--;

                    pushFollow(FOLLOW_expression_in_binaryExpression2312);
                    e2=expression();
                    _fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 14 :
                    // XMLEmitter.g:759:4: ^(op= '+' e1= expression e2= expression )
                    {
                    op=(CommonTree)input.LT(1);
                    match(input,145,FOLLOW_145_in_binaryExpression2321); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_binaryExpression2325);
                    e1=expression();
                    _fsp--;

                    pushFollow(FOLLOW_expression_in_binaryExpression2329);
                    e2=expression();
                    _fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 15 :
                    // XMLEmitter.g:760:4: ^(op= '-' e1= expression e2= expression )
                    {
                    op=(CommonTree)input.LT(1);
                    match(input,146,FOLLOW_146_in_binaryExpression2338); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_binaryExpression2342);
                    e1=expression();
                    _fsp--;

                    pushFollow(FOLLOW_expression_in_binaryExpression2346);
                    e2=expression();
                    _fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 16 :
                    // XMLEmitter.g:761:4: ^(op= '*' e1= expression e2= expression )
                    {
                    op=(CommonTree)input.LT(1);
                    match(input,129,FOLLOW_129_in_binaryExpression2355); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_binaryExpression2359);
                    e1=expression();
                    _fsp--;

                    pushFollow(FOLLOW_expression_in_binaryExpression2363);
                    e2=expression();
                    _fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 17 :
                    // XMLEmitter.g:762:4: ^(op= '/' e1= expression e2= expression )
                    {
                    op=(CommonTree)input.LT(1);
                    match(input,154,FOLLOW_154_in_binaryExpression2372); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_binaryExpression2376);
                    e1=expression();
                    _fsp--;

                    pushFollow(FOLLOW_expression_in_binaryExpression2380);
                    e2=expression();
                    _fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 18 :
                    // XMLEmitter.g:763:4: ^(op= '%' e1= expression e2= expression )
                    {
                    op=(CommonTree)input.LT(1);
                    match(input,155,FOLLOW_155_in_binaryExpression2389); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_binaryExpression2393);
                    e1=expression();
                    _fsp--;

                    pushFollow(FOLLOW_expression_in_binaryExpression2397);
                    e2=expression();
                    _fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;

            }

            	e = newElement("binaryExpression");
            	e.add(e1);
            	e.add(e2);
            	e.addAttribute("op", op.getText());

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return e;
    }
    // $ANTLR end binaryExpression


    // $ANTLR start primaryExpression
    // XMLEmitter.g:766:1: primaryExpression returns [Element e] : ( IDENTIFIER | CONSTANT | sTRING_LITERAL | compoundStatement | ^( BUILTIN_OFFSETOF typeName offsetofMemberDesignator ) );
    public final Element primaryExpression() throws RecognitionException {
        Element e = null;

        CommonTree IDENTIFIER83=null;
        CommonTree CONSTANT84=null;
        String sTRING_LITERAL85 = null;

        Element compoundStatement86 = null;

        Element typeName87 = null;

        Element offsetofMemberDesignator88 = null;


        try {
            // XMLEmitter.g:767:2: ( IDENTIFIER | CONSTANT | sTRING_LITERAL | compoundStatement | ^( BUILTIN_OFFSETOF typeName offsetofMemberDesignator ) )
            int alt76=5;
            switch ( input.LA(1) ) {
            case IDENTIFIER:
                {
                alt76=1;
                }
                break;
            case CONSTANT:
                {
                alt76=2;
                }
                break;
            case STR_LITERAL:
                {
                alt76=3;
                }
                break;
            case COMPOUND_STATEMENT:
                {
                alt76=4;
                }
                break;
            case BUILTIN_OFFSETOF:
                {
                alt76=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("766:1: primaryExpression returns [Element e] : ( IDENTIFIER | CONSTANT | sTRING_LITERAL | compoundStatement | ^( BUILTIN_OFFSETOF typeName offsetofMemberDesignator ) );", 76, 0, input);

                throw nvae;
            }

            switch (alt76) {
                case 1 :
                    // XMLEmitter.g:767:4: IDENTIFIER
                    {
                    IDENTIFIER83=(CommonTree)input.LT(1);
                    match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_primaryExpression2413); 
                     e = newElement("id"); e.addText(findVariable(IDENTIFIER83.getText())); 

                    }
                    break;
                case 2 :
                    // XMLEmitter.g:768:4: CONSTANT
                    {
                    CONSTANT84=(CommonTree)input.LT(1);
                    match(input,CONSTANT,FOLLOW_CONSTANT_in_primaryExpression2421); 
                     e = newElement("intConst"); e.addText(CONSTANT84.getText()); 

                    }
                    break;
                case 3 :
                    // XMLEmitter.g:769:4: sTRING_LITERAL
                    {
                    pushFollow(FOLLOW_sTRING_LITERAL_in_primaryExpression2429);
                    sTRING_LITERAL85=sTRING_LITERAL();
                    _fsp--;

                     e = newElement("stringConst"); e.addText(sTRING_LITERAL85); 

                    }
                    break;
                case 4 :
                    // XMLEmitter.g:770:4: compoundStatement
                    {
                    pushFollow(FOLLOW_compoundStatement_in_primaryExpression2436);
                    compoundStatement86=compoundStatement();
                    _fsp--;

                     e = compoundStatement86; 

                    }
                    break;
                case 5 :
                    // XMLEmitter.g:771:4: ^( BUILTIN_OFFSETOF typeName offsetofMemberDesignator )
                    {
                    match(input,BUILTIN_OFFSETOF,FOLLOW_BUILTIN_OFFSETOF_in_primaryExpression2444); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_typeName_in_primaryExpression2446);
                    typeName87=typeName();
                    _fsp--;

                    pushFollow(FOLLOW_offsetofMemberDesignator_in_primaryExpression2448);
                    offsetofMemberDesignator88=offsetofMemberDesignator();
                    _fsp--;


                    match(input, Token.UP, null); 

                    		e = newElement("offsetofExpression");
                    		e.add(typeName87);
                    		e.add(offsetofMemberDesignator88);
                    	

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return e;
    }
    // $ANTLR end primaryExpression


    // $ANTLR start sTRING_LITERAL
    // XMLEmitter.g:778:1: sTRING_LITERAL returns [String text] : ^( STR_LITERAL ( STRING_LITERAL )+ ) ;
    public final String sTRING_LITERAL() throws RecognitionException {
        String text = null;

        CommonTree STRING_LITERAL89=null;


        	List<String> sls = new ArrayList<String>();

        try {
            // XMLEmitter.g:782:2: ( ^( STR_LITERAL ( STRING_LITERAL )+ ) )
            // XMLEmitter.g:782:4: ^( STR_LITERAL ( STRING_LITERAL )+ )
            {
            match(input,STR_LITERAL,FOLLOW_STR_LITERAL_in_sTRING_LITERAL2472); 

            match(input, Token.DOWN, null); 
            // XMLEmitter.g:782:18: ( STRING_LITERAL )+
            int cnt77=0;
            loop77:
            do {
                int alt77=2;
                int LA77_0 = input.LA(1);

                if ( (LA77_0==STRING_LITERAL) ) {
                    alt77=1;
                }


                switch (alt77) {
            	case 1 :
            	    // XMLEmitter.g:782:19: STRING_LITERAL
            	    {
            	    STRING_LITERAL89=(CommonTree)input.LT(1);
            	    match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_sTRING_LITERAL2475); 
            	    sls.add(STRING_LITERAL89.getText());

            	    }
            	    break;

            	default :
            	    if ( cnt77 >= 1 ) break loop77;
                        EarlyExitException eee =
                            new EarlyExitException(77, input);
                        throw eee;
                }
                cnt77++;
            } while (true);


            match(input, Token.UP, null); 

            		StringBuilder sb = new StringBuilder();
            		for (String str: sls) /* crop the quotation */
            			sb.append(str.substring(1, str.length() - 1));
            		text = sb.toString();
            	

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return text;
    }
    // $ANTLR end sTRING_LITERAL


    // $ANTLR start offsetofMemberDesignator
    // XMLEmitter.g:790:1: offsetofMemberDesignator returns [Element e] : id1= IDENTIFIER ( ( '.' id2= IDENTIFIER ) | ( '[' expression ']' ) )* ;
    public final Element offsetofMemberDesignator() throws RecognitionException {
        Element e = null;

        CommonTree id1=null;
        CommonTree id2=null;
        Element expression90 = null;



        	Element e1;

        try {
            // XMLEmitter.g:794:2: (id1= IDENTIFIER ( ( '.' id2= IDENTIFIER ) | ( '[' expression ']' ) )* )
            // XMLEmitter.g:794:4: id1= IDENTIFIER ( ( '.' id2= IDENTIFIER ) | ( '[' expression ']' ) )*
            {
            id1=(CommonTree)input.LT(1);
            match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_offsetofMemberDesignator2504); 

            		e = newElement("expression");
            		e.addElement("id").addText(id1.getText());
            	
            // XMLEmitter.g:797:4: ( ( '.' id2= IDENTIFIER ) | ( '[' expression ']' ) )*
            loop78:
            do {
                int alt78=3;
                int LA78_0 = input.LA(1);

                if ( (LA78_0==134) ) {
                    alt78=1;
                }
                else if ( (LA78_0==127) ) {
                    alt78=2;
                }


                switch (alt78) {
            	case 1 :
            	    // XMLEmitter.g:797:6: ( '.' id2= IDENTIFIER )
            	    {
            	    // XMLEmitter.g:797:6: ( '.' id2= IDENTIFIER )
            	    // XMLEmitter.g:797:7: '.' id2= IDENTIFIER
            	    {
            	    match(input,134,FOLLOW_134_in_offsetofMemberDesignator2511); 
            	    id2=(CommonTree)input.LT(1);
            	    match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_offsetofMemberDesignator2515); 

            	    			e1 = newElement("dotExpression");
            	    			e1.add(e);
            	    			e1.addElement("member").addText(id2.getText());
            	    			e = newElement("expression");
            	    			e.add(e1);
            	    		

            	    }


            	    }
            	    break;
            	case 2 :
            	    // XMLEmitter.g:804:5: ( '[' expression ']' )
            	    {
            	    // XMLEmitter.g:804:5: ( '[' expression ']' )
            	    // XMLEmitter.g:804:6: '[' expression ']'
            	    {
            	    match(input,127,FOLLOW_127_in_offsetofMemberDesignator2525); 
            	    pushFollow(FOLLOW_expression_in_offsetofMemberDesignator2527);
            	    expression90=expression();
            	    _fsp--;

            	    match(input,128,FOLLOW_128_in_offsetofMemberDesignator2529); 

            	    			e1 = newElement("arrayAccess");
            	    			e1.add(e);
            	    			e1.add(expression90);
            	    			e = newElement("expression");
            	    			e.add(e1);
            	    		

            	    }


            	    }
            	    break;

            	default :
            	    break loop78;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return e;
    }
    // $ANTLR end offsetofMemberDesignator

    public static class assignmentOperator_return extends TreeRuleReturnScope {
    };

    // $ANTLR start assignmentOperator
    // XMLEmitter.g:814:1: assignmentOperator : ( '=' | '*=' | '/=' | '%=' | '+=' | '-=' | '<<=' | '>>=' | '&=' | '^=' | '|=' );
    public final assignmentOperator_return assignmentOperator() throws RecognitionException {
        assignmentOperator_return retval = new assignmentOperator_return();
        retval.start = input.LT(1);

        try {
            // XMLEmitter.g:815:2: ( '=' | '*=' | '/=' | '%=' | '+=' | '-=' | '<<=' | '>>=' | '&=' | '^=' | '|=' )
            // XMLEmitter.g:
            {
            if ( input.LA(1)==87||(input.LA(1)>=168 && input.LA(1)<=177) ) {
                input.consume();
                errorRecovery=false;
            }
            else {
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recoverFromMismatchedSet(input,mse,FOLLOW_set_in_assignmentOperator0);    throw mse;
            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end assignmentOperator


    // $ANTLR start unaryOp
    // XMLEmitter.g:828:1: unaryOp returns [String op] : ( PU | MU | '~' | '!' | LABREF | '__real__' | '__imag__' );
    public final String unaryOp() throws RecognitionException {
        String op = null;

        try {
            // XMLEmitter.g:829:2: ( PU | MU | '~' | '!' | LABREF | '__real__' | '__imag__' )
            int alt79=7;
            switch ( input.LA(1) ) {
            case PU:
                {
                alt79=1;
                }
                break;
            case MU:
                {
                alt79=2;
                }
                break;
            case 147:
                {
                alt79=3;
                }
                break;
            case 148:
                {
                alt79=4;
                }
                break;
            case LABREF:
                {
                alt79=5;
                }
                break;
            case 151:
                {
                alt79=6;
                }
                break;
            case 153:
                {
                alt79=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("828:1: unaryOp returns [String op] : ( PU | MU | '~' | '!' | LABREF | '__real__' | '__imag__' );", 79, 0, input);

                throw nvae;
            }

            switch (alt79) {
                case 1 :
                    // XMLEmitter.g:829:4: PU
                    {
                    match(input,PU,FOLLOW_PU_in_unaryOp2613); 
                     op = "+"; 

                    }
                    break;
                case 2 :
                    // XMLEmitter.g:830:4: MU
                    {
                    match(input,MU,FOLLOW_MU_in_unaryOp2621); 
                     op = "-"; 

                    }
                    break;
                case 3 :
                    // XMLEmitter.g:831:4: '~'
                    {
                    match(input,147,FOLLOW_147_in_unaryOp2629); 
                     op = "~"; 

                    }
                    break;
                case 4 :
                    // XMLEmitter.g:832:4: '!'
                    {
                    match(input,148,FOLLOW_148_in_unaryOp2637); 
                     op = "!"; 

                    }
                    break;
                case 5 :
                    // XMLEmitter.g:833:4: LABREF
                    {
                    match(input,LABREF,FOLLOW_LABREF_in_unaryOp2645); 
                     op = "&&"; 

                    }
                    break;
                case 6 :
                    // XMLEmitter.g:834:4: '__real__'
                    {
                    match(input,151,FOLLOW_151_in_unaryOp2652); 
                     op = "__real"; 

                    }
                    break;
                case 7 :
                    // XMLEmitter.g:835:4: '__imag__'
                    {
                    match(input,153,FOLLOW_153_in_unaryOp2659); 
                     op = "__imag"; 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return op;
    }
    // $ANTLR end unaryOp


 

    public static final BitSet FOLLOW_TRANSLATION_UNIT_in_translationUnit65 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_externalDeclaration_in_translationUnit70 = new BitSet(new long[]{0x0000042000000008L,0x0000000000200000L});
    public static final BitSet FOLLOW_functionDefinition_in_externalDeclaration98 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_declaration_in_externalDeclaration105 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FUNCTION_DEFINITION_in_functionDefinition129 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_declarationSpecifiers_in_functionDefinition131 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_declarator_in_functionDefinition133 = new BitSet(new long[]{0x0002040000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_declaration_in_functionDefinition138 = new BitSet(new long[]{0x0002040000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_compoundStatement_in_functionDefinition144 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_85_in_declaration168 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_declarationSpecifiers_in_declaration170 = new BitSet(new long[]{0x0000080000000008L});
    public static final BitSet FOLLOW_initDeclarator_in_declaration176 = new BitSet(new long[]{0x0000080000000008L});
    public static final BitSet FOLLOW_DECLARATION_in_declaration189 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_declarationSpecifiers_in_declaration191 = new BitSet(new long[]{0x0000080000000008L});
    public static final BitSet FOLLOW_initDeclarator_in_declaration196 = new BitSet(new long[]{0x0000080000000008L});
    public static final BitSet FOLLOW_DECLARATION_SPECIFIERS_in_declarationSpecifiers224 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_XTYPE_SPECIFIER_in_declarationSpecifiers227 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_typeSpecifier_in_declarationSpecifiers232 = new BitSet(new long[]{0x0020200000100008L,0x0005800000000000L});
    public static final BitSet FOLLOW_XTYPE_QUALIFIER_in_declarationSpecifiers240 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_typeQualifier_in_declarationSpecifiers245 = new BitSet(new long[]{0x0000000000000008L,0x0248000000000000L});
    public static final BitSet FOLLOW_XSTORAGE_CLASS_in_declarationSpecifiers253 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_storageClassSpecifier_in_declarationSpecifiers258 = new BitSet(new long[]{0x0000000000000008L,0x100000001F000000L});
    public static final BitSet FOLLOW_functionSpecifier_in_declarationSpecifiers264 = new BitSet(new long[]{0x0000000000000008L,0x100000001F000000L});
    public static final BitSet FOLLOW_DECLARATOR_in_declarator288 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_pointer_in_declarator290 = new BitSet(new long[]{0x0020000201800000L});
    public static final BitSet FOLLOW_directDeclarator_in_declarator293 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IDENTIFIER_in_directDeclarator316 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_declarator_in_directDeclarator323 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_directDeclarator1_in_directDeclarator330 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ARRAY_DECLARATOR_in_directDeclarator1355 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENTIFIER_in_directDeclarator1358 = new BitSet(new long[]{0x007AC0006027F808L,0x0248000002400000L,0x0000007FFEBFBC42L});
    public static final BitSet FOLLOW_directDeclarator1_in_directDeclarator1362 = new BitSet(new long[]{0x007AC0006027F808L,0x0248000002400000L,0x0000007FFEBFBC42L});
    public static final BitSet FOLLOW_89_in_directDeclarator1366 = new BitSet(new long[]{0x007AC0006027F808L,0x0248000000400000L,0x0000007FFEBFBC42L});
    public static final BitSet FOLLOW_129_in_directDeclarator1372 = new BitSet(new long[]{0x007AC0006027F808L,0x0248000000400000L,0x0000007FFEBFBC42L});
    public static final BitSet FOLLOW_typeQualifier_in_directDeclarator1379 = new BitSet(new long[]{0x007AC0006027F808L,0x0248000000400000L,0x0000007FFEBFBC42L});
    public static final BitSet FOLLOW_expression_in_directDeclarator1385 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_FUNCTION_DECLARATOR_in_directDeclarator1395 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENTIFIER_in_directDeclarator1398 = new BitSet(new long[]{0x0000000080000008L});
    public static final BitSet FOLLOW_declarator_in_directDeclarator1402 = new BitSet(new long[]{0x0000000080000008L});
    public static final BitSet FOLLOW_parameterTypeList_in_directDeclarator1408 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_identifier_in_directDeclarator1413 = new BitSet(new long[]{0x0000000080000008L});
    public static final BitSet FOLLOW_INIT_DECLARATOR_in_initDeclarator437 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_declarator_in_initDeclarator439 = new BitSet(new long[]{0x0000000002000008L});
    public static final BitSet FOLLOW_initializer_in_initDeclarator441 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INITIALIZER_in_initializer466 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_initializer468 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INITIALIZER_in_initializer476 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INITIALIZER_in_initializer484 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_initializerList_in_initializer486 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_designator_in_initializerList512 = new BitSet(new long[]{0x0020000006000000L});
    public static final BitSet FOLLOW_initializer_in_initializerList518 = new BitSet(new long[]{0x0020000006000002L});
    public static final BitSet FOLLOW_DESIGNATOR_in_designator543 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_BRACKET_DESIGNATOR_in_designator546 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_designator548 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DESIGNATOR_in_designator558 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENTIFIER_in_designator560 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IDENTIFIER_in_designator568 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COMPOUND_STATEMENT_in_compoundStatement593 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_declaration_in_compoundStatement598 = new BitSet(new long[]{0x0003042000000608L,0x0000000000200000L,0x3FDC000000000000L});
    public static final BitSet FOLLOW_functionDefinition_in_compoundStatement604 = new BitSet(new long[]{0x0003042000000608L,0x0000000000200000L,0x3FDC000000000000L});
    public static final BitSet FOLLOW_statement_in_compoundStatement609 = new BitSet(new long[]{0x0003042000000608L,0x0000000000200000L,0x3FDC000000000000L});
    public static final BitSet FOLLOW_parameterDeclaration_in_parameterTypeList638 = new BitSet(new long[]{0x0000000180000002L});
    public static final BitSet FOLLOW_VARARGS_in_parameterTypeList644 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PARAMETER_in_parameterDeclaration663 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_declarationSpecifiers_in_parameterDeclaration665 = new BitSet(new long[]{0x0000000601800008L});
    public static final BitSet FOLLOW_declarator_in_parameterDeclaration667 = new BitSet(new long[]{0x0000000401800008L});
    public static final BitSet FOLLOW_abstractDeclarator_in_parameterDeclaration670 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_pointer_in_abstractDeclarator694 = new BitSet(new long[]{0x0000000001800002L});
    public static final BitSet FOLLOW_directAbstractDeclarator_in_abstractDeclarator696 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_directAbstractDeclarator_in_abstractDeclarator704 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ARRAY_DECLARATOR_in_directAbstractDeclarator727 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_abstractDeclarator_in_directAbstractDeclarator729 = new BitSet(new long[]{0x007AC0006027F808L,0x0000000000400000L,0x0000007FFEBFBC42L});
    public static final BitSet FOLLOW_expression_in_directAbstractDeclarator733 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_129_in_directAbstractDeclarator738 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_FUNCTION_DECLARATOR_in_directAbstractDeclarator748 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_abstractDeclarator_in_directAbstractDeclarator750 = new BitSet(new long[]{0x0000000080000008L});
    public static final BitSet FOLLOW_parameterTypeList_in_directAbstractDeclarator753 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PARAMETER_in_identifier773 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENTIFIER_in_identifier775 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_TYPE_NAME_in_typeName802 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_specifierQualifier_in_typeName807 = new BitSet(new long[]{0x0000018401800008L});
    public static final BitSet FOLLOW_abstractDeclarator_in_typeName813 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_XTYPE_SPECIFIER_in_specifierQualifier833 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_typeSpecifier_in_specifierQualifier835 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_XTYPE_QUALIFIER_in_specifierQualifier844 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_typeQualifier_in_specifierQualifier846 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_115_in_typeQualifier864 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_118_in_typeQualifier871 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_121_in_typeQualifier878 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BASETYPE_in_typeSpecifier901 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_93_in_typeSpecifier903 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BASETYPE_in_typeSpecifier912 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_94_in_typeSpecifier914 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BASETYPE_in_typeSpecifier923 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_95_in_typeSpecifier925 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BASETYPE_in_typeSpecifier934 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_96_in_typeSpecifier936 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BASETYPE_in_typeSpecifier945 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_97_in_typeSpecifier947 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BASETYPE_in_typeSpecifier956 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_98_in_typeSpecifier958 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BASETYPE_in_typeSpecifier967 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_99_in_typeSpecifier969 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BASETYPE_in_typeSpecifier978 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_SIGNED_in_typeSpecifier980 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BASETYPE_in_typeSpecifier989 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_103_in_typeSpecifier991 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BASETYPE_in_typeSpecifier999 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_104_in_typeSpecifier1001 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BASETYPE_in_typeSpecifier1010 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_105_in_typeSpecifier1012 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BASETYPE_in_typeSpecifier1020 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_XID_in_typeSpecifier1022 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BASETYPE_in_typeSpecifier1029 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_108_in_typeSpecifier1031 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_structOrUnionSpecifier_in_typeSpecifier1039 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_enumSpecifier_in_typeSpecifier1045 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typedefName_in_typeSpecifier1053 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeofSpecifier_in_typeSpecifier1061 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_structOrUnion_in_structOrUnionSpecifier1082 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_XID_in_structOrUnionSpecifier1085 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENTIFIER_in_structOrUnionSpecifier1087 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_structDeclaration_in_structOrUnionSpecifier1094 = new BitSet(new long[]{0x0000001000000008L});
    public static final BitSet FOLLOW_111_in_structOrUnion1116 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_112_in_structOrUnion1123 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRUCT_DECLARATION_in_structDeclaration1146 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_specifierQualifier_in_structDeclaration1151 = new BitSet(new long[]{0x0000018800000008L});
    public static final BitSet FOLLOW_structDeclarator_in_structDeclaration1160 = new BitSet(new long[]{0x0000000800000008L});
    public static final BitSet FOLLOW_STRUCT_DECLARATOR_in_structDeclarator1183 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_declarator_in_structDeclarator1185 = new BitSet(new long[]{0x007AC0006027F808L,0x0000000000400000L,0x0000007FFEBFBC42L});
    public static final BitSet FOLLOW_expression_in_structDeclarator1188 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_114_in_enumSpecifier1213 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_XID_in_enumSpecifier1217 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENTIFIER_in_enumSpecifier1219 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_enumerator_in_enumSpecifier1227 = new BitSet(new long[]{0x0000000008000008L});
    public static final BitSet FOLLOW_ENUMERATOR_in_enumerator1250 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENTIFIER_in_enumerator1252 = new BitSet(new long[]{0x007AC0006027F808L,0x0000000000400000L,0x0000007FFEBFBC42L});
    public static final BitSet FOLLOW_expression_in_enumerator1254 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IDENTIFIER_in_typedefName1273 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TYPEOF_in_typeofSpecifier1287 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_typeofSpecifier1289 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_TYPEOF_in_typeofSpecifier1296 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_typeName_in_typeofSpecifier1298 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_88_in_storageClassSpecifier1314 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_89_in_storageClassSpecifier1321 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_90_in_storageClassSpecifier1328 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_91_in_storageClassSpecifier1335 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_92_in_storageClassSpecifier1342 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_124_in_functionSpecifier1359 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_POINTER_in_pointer1382 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_typeQualifier_in_pointer1387 = new BitSet(new long[]{0x0000000400000008L,0x0248000000000000L});
    public static final BitSet FOLLOW_pointer_in_pointer1395 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_labeledStatement_in_statement1425 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_compoundStatement_in_statement1432 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expressionStatement_in_statement1439 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_selectionStatement_in_statement1446 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_iterationStatement_in_statement1453 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_jumpStatement_in_statement1460 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_asmStatement_in_statement1468 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LABEL_in_labeledStatement1487 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENTIFIER_in_labeledStatement1489 = new BitSet(new long[]{0x0003000000000600L,0x0000000000000000L,0x3FDC000000000000L});
    public static final BitSet FOLLOW_statement_in_labeledStatement1491 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_178_in_labeledStatement1500 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_labeledStatement1502 = new BitSet(new long[]{0x0003000000000600L,0x0000000000000000L,0x3FDC000000000000L});
    public static final BitSet FOLLOW_statement_in_labeledStatement1504 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_179_in_labeledStatement1513 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_statement_in_labeledStatement1515 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EXPRESSION_STATEMENT_in_expressionStatement1534 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expressionStatement1536 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_180_in_selectionStatement1556 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_selectionStatement1558 = new BitSet(new long[]{0x0003000000000600L,0x0000000000000000L,0x3FDC000000000000L});
    public static final BitSet FOLLOW_statement_in_selectionStatement1562 = new BitSet(new long[]{0x0003000000000608L,0x0000000000000000L,0x3FDC000000000000L});
    public static final BitSet FOLLOW_statement_in_selectionStatement1566 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_182_in_selectionStatement1576 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_selectionStatement1578 = new BitSet(new long[]{0x0003000000000600L,0x0000000000000000L,0x3FDC000000000000L});
    public static final BitSet FOLLOW_statement_in_selectionStatement1580 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_183_in_iterationStatement1599 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_iterationStatement1601 = new BitSet(new long[]{0x0003000000000600L,0x0000000000000000L,0x3FDC000000000000L});
    public static final BitSet FOLLOW_statement_in_iterationStatement1603 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_184_in_iterationStatement1612 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_statement_in_iterationStatement1614 = new BitSet(new long[]{0x007AC0006027F800L,0x0000000000400000L,0x0000007FFEBFBC42L});
    public static final BitSet FOLLOW_expression_in_iterationStatement1616 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_185_in_iterationStatement1625 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_declaration_in_iterationStatement1627 = new BitSet(new long[]{0x00000000000000C0L});
    public static final BitSet FOLLOW_E1_in_iterationStatement1632 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_iterationStatement1636 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_E2_in_iterationStatement1642 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_iterationStatement1646 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_E3_in_iterationStatement1651 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_iterationStatement1655 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_statement_in_iterationStatement1659 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_186_in_jumpStatement1678 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENTIFIER_in_jumpStatement1680 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_186_in_jumpStatement1689 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_XU_in_jumpStatement1691 = new BitSet(new long[]{0x007AC0006027F800L,0x0000000000400000L,0x0000007FFEBFBC42L});
    public static final BitSet FOLLOW_expression_in_jumpStatement1693 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_187_in_jumpStatement1701 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_188_in_jumpStatement1708 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_189_in_jumpStatement1716 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_jumpStatement1718 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ASM_in_asmStatement1737 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ASSIGNMENT_EXPRESSION_in_expression1767 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_assignmentOperator_in_expression1769 = new BitSet(new long[]{0x007AC0006027F800L,0x0000000000400000L,0x0000007FFEBFBC42L});
    public static final BitSet FOLLOW_expression_in_expression1773 = new BitSet(new long[]{0x007AC0006027F800L,0x0000000000400000L,0x0000007FFEBFBC42L});
    public static final BitSet FOLLOW_expression_in_expression1777 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CONDITIONAL_EXPRESSION_in_expression1786 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_E1_in_expression1789 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression1793 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_E2_in_expression1797 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression1801 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_E3_in_expression1806 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression1810 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CAST_EXPRESSION_in_expression1820 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_typeName_in_expression1824 = new BitSet(new long[]{0x007AC0006027F800L,0x0000000000400000L,0x0000007FFEBFBC42L});
    public static final BitSet FOLLOW_expression_in_expression1828 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ARRAY_ACCESS_in_expression1837 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression1841 = new BitSet(new long[]{0x007AC0006027F800L,0x0000000000400000L,0x0000007FFEBFBC42L});
    public static final BitSet FOLLOW_expression_in_expression1845 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_FUNCTION_CALL_in_expression1854 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression1858 = new BitSet(new long[]{0x007AC0006027F808L,0x0000000000400000L,0x0000007FFEBFBC42L});
    public static final BitSet FOLLOW_expression_in_expression1863 = new BitSet(new long[]{0x007AC0006027F808L,0x0000000000400000L,0x0000007FFEBFBC42L});
    public static final BitSet FOLLOW_COMPOUND_LITERAL_in_expression1876 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_typeName_in_expression1880 = new BitSet(new long[]{0x0020000006000000L});
    public static final BitSet FOLLOW_initializerList_in_expression1882 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_86_in_expression1891 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression1895 = new BitSet(new long[]{0x007AC0006027F800L,0x0000000000400000L,0x0000007FFEBFBC42L});
    public static final BitSet FOLLOW_expression_in_expression1899 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_139_in_expression1908 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression1912 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_140_in_expression1922 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression1926 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_unaryOp_in_expression1936 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression1940 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_141_in_expression1949 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression1954 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_typeName_in_expression1958 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_143_in_expression1968 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression1973 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_typeName_in_expression1977 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_134_in_expression1987 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression1991 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_expression1993 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_138_in_expression2002 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression2006 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_expression2008 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_AU_in_expression2017 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression2021 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_XU_in_expression2030 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression2034 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PP_in_expression2043 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression2047 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MM_in_expression2056 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression2060 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_binaryExpression_in_expression2068 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primaryExpression_in_expression2075 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_166_in_binaryExpression2100 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_binaryExpression2104 = new BitSet(new long[]{0x007AC0006027F800L,0x0000000000400000L,0x0000007FFEBFBC42L});
    public static final BitSet FOLLOW_expression_in_binaryExpression2108 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_149_in_binaryExpression2117 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_binaryExpression2121 = new BitSet(new long[]{0x007AC0006027F800L,0x0000000000400000L,0x0000007FFEBFBC42L});
    public static final BitSet FOLLOW_expression_in_binaryExpression2125 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_165_in_binaryExpression2134 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_binaryExpression2138 = new BitSet(new long[]{0x007AC0006027F800L,0x0000000000400000L,0x0000007FFEBFBC42L});
    public static final BitSet FOLLOW_expression_in_binaryExpression2142 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_144_in_binaryExpression2151 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_binaryExpression2155 = new BitSet(new long[]{0x007AC0006027F800L,0x0000000000400000L,0x0000007FFEBFBC42L});
    public static final BitSet FOLLOW_expression_in_binaryExpression2159 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_164_in_binaryExpression2168 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_binaryExpression2172 = new BitSet(new long[]{0x007AC0006027F800L,0x0000000000400000L,0x0000007FFEBFBC42L});
    public static final BitSet FOLLOW_expression_in_binaryExpression2176 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_162_in_binaryExpression2185 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_binaryExpression2189 = new BitSet(new long[]{0x007AC0006027F800L,0x0000000000400000L,0x0000007FFEBFBC42L});
    public static final BitSet FOLLOW_expression_in_binaryExpression2193 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_163_in_binaryExpression2202 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_binaryExpression2206 = new BitSet(new long[]{0x007AC0006027F800L,0x0000000000400000L,0x0000007FFEBFBC42L});
    public static final BitSet FOLLOW_expression_in_binaryExpression2210 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_160_in_binaryExpression2219 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_binaryExpression2223 = new BitSet(new long[]{0x007AC0006027F800L,0x0000000000400000L,0x0000007FFEBFBC42L});
    public static final BitSet FOLLOW_expression_in_binaryExpression2227 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_158_in_binaryExpression2236 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_binaryExpression2240 = new BitSet(new long[]{0x007AC0006027F800L,0x0000000000400000L,0x0000007FFEBFBC42L});
    public static final BitSet FOLLOW_expression_in_binaryExpression2244 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_161_in_binaryExpression2253 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_binaryExpression2257 = new BitSet(new long[]{0x007AC0006027F800L,0x0000000000400000L,0x0000007FFEBFBC42L});
    public static final BitSet FOLLOW_expression_in_binaryExpression2261 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_159_in_binaryExpression2270 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_binaryExpression2274 = new BitSet(new long[]{0x007AC0006027F800L,0x0000000000400000L,0x0000007FFEBFBC42L});
    public static final BitSet FOLLOW_expression_in_binaryExpression2278 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_157_in_binaryExpression2287 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_binaryExpression2291 = new BitSet(new long[]{0x007AC0006027F800L,0x0000000000400000L,0x0000007FFEBFBC42L});
    public static final BitSet FOLLOW_expression_in_binaryExpression2295 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_156_in_binaryExpression2304 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_binaryExpression2308 = new BitSet(new long[]{0x007AC0006027F800L,0x0000000000400000L,0x0000007FFEBFBC42L});
    public static final BitSet FOLLOW_expression_in_binaryExpression2312 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_145_in_binaryExpression2321 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_binaryExpression2325 = new BitSet(new long[]{0x007AC0006027F800L,0x0000000000400000L,0x0000007FFEBFBC42L});
    public static final BitSet FOLLOW_expression_in_binaryExpression2329 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_146_in_binaryExpression2338 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_binaryExpression2342 = new BitSet(new long[]{0x007AC0006027F800L,0x0000000000400000L,0x0000007FFEBFBC42L});
    public static final BitSet FOLLOW_expression_in_binaryExpression2346 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_129_in_binaryExpression2355 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_binaryExpression2359 = new BitSet(new long[]{0x007AC0006027F800L,0x0000000000400000L,0x0000007FFEBFBC42L});
    public static final BitSet FOLLOW_expression_in_binaryExpression2363 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_154_in_binaryExpression2372 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_binaryExpression2376 = new BitSet(new long[]{0x007AC0006027F800L,0x0000000000400000L,0x0000007FFEBFBC42L});
    public static final BitSet FOLLOW_expression_in_binaryExpression2380 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_155_in_binaryExpression2389 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_binaryExpression2393 = new BitSet(new long[]{0x007AC0006027F800L,0x0000000000400000L,0x0000007FFEBFBC42L});
    public static final BitSet FOLLOW_expression_in_binaryExpression2397 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IDENTIFIER_in_primaryExpression2413 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CONSTANT_in_primaryExpression2421 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_sTRING_LITERAL_in_primaryExpression2429 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_compoundStatement_in_primaryExpression2436 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BUILTIN_OFFSETOF_in_primaryExpression2444 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_typeName_in_primaryExpression2446 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_offsetofMemberDesignator_in_primaryExpression2448 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_STR_LITERAL_in_sTRING_LITERAL2472 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_STRING_LITERAL_in_sTRING_LITERAL2475 = new BitSet(new long[]{0x0080000000000008L});
    public static final BitSet FOLLOW_IDENTIFIER_in_offsetofMemberDesignator2504 = new BitSet(new long[]{0x0000000000000002L,0x8000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_134_in_offsetofMemberDesignator2511 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_offsetofMemberDesignator2515 = new BitSet(new long[]{0x0000000000000002L,0x8000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_127_in_offsetofMemberDesignator2525 = new BitSet(new long[]{0x007AC0006027F800L,0x0000000000400000L,0x0000007FFEBFBC42L});
    public static final BitSet FOLLOW_expression_in_offsetofMemberDesignator2527 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_128_in_offsetofMemberDesignator2529 = new BitSet(new long[]{0x0000000000000002L,0x8000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_set_in_assignmentOperator0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PU_in_unaryOp2613 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MU_in_unaryOp2621 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_147_in_unaryOp2629 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_148_in_unaryOp2637 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LABREF_in_unaryOp2645 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_151_in_unaryOp2652 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_153_in_unaryOp2659 = new BitSet(new long[]{0x0000000000000002L});

}
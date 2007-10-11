header {
	package cz.muni.fi.iti.scv.c2xml;
}

{
import java.io.*;

import antlr.CommonAST;
import antlr.DumpASTVisitor;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.DocumentFactory;

import java.util.ArrayList;
import java.util.Iterator;

}

class XmlEmitter extends TreeParser;

options {
	importVocab= GNUC;
	buildAST= false;
	ASTLabelType= "TNode";
	codeGenMakeSwitchThreshold= 2;
	codeGenBitsetTestThreshold= 3;
}
{
        protected Document xmlDocument = DocumentHelper.createDocument();
        protected DocumentFactory xmlFactory = DocumentFactory.getInstance();
		
		public Document getXmlDocument() {
			return xmlDocument;	
		}
		
		public void addElementPositions(Element e, TNode node){
			CToken token = new CToken();
            token.setFromTNode(node);
            e.addAttribute("bl", String.valueOf(token.getLine()));
            e.addAttribute("bc", String.valueOf(token.getColumn()));
            e.addAttribute("el", String.valueOf(token.getEndLine()));
            e.addAttribute("ec", String.valueOf(token.getEndColumn()));            
		}
		
		// removes all underscores
		protected String stripUnderscores(String s) {
			return s.replaceAll("_","");
		}
		
	    int traceDepth = 0;
        public void reportError(RecognitionException ex) {
          if ( ex != null)   {
                System.err.println("ANTLR Tree Parsing RecognitionException Error: " + ex.getClass().getName() + " " + ex );
                ex.printStackTrace(System.err);
          }
        }
        public void reportError(NoViableAltException ex) {
                System.err.println("ANTLR Tree Parsing NoViableAltException Error: " + ex.toString());
                TNode.printTree( ex.node );
                ex.printStackTrace(System.err);
        }
        public void reportError(MismatchedTokenException ex) {
          if ( ex != null)   {
                TNode.printTree( ex.node );
                System.err.println("ANTLR Tree Parsing MismatchedTokenException Error: " + ex );
                ex.printStackTrace(System.err);
          }
        }
        public void reportError(String s) {
                System.err.println("ANTLR Error from String: " + s);
        }
        public void reportWarning(String s) {
                System.err.println("ANTLR Warning from String: " + s);
        }
        protected void match(AST t, int ttype) throws MismatchedTokenException {
                //System.out.println("match("+ttype+"); cursor is "+t);
                super.match(t, ttype);
        }
        public void match(AST t, BitSet b) throws MismatchedTokenException {
                //System.out.println("match("+b+"); cursor is "+t);
                super.match(t, b);
        }
        protected void matchNot(AST t, int ttype) throws MismatchedTokenException {
                //System.out.println("matchNot("+ttype+"); cursor is "+t);
                super.matchNot(t, ttype);
                }
        public void traceIn(String rname, AST t) {
          traceDepth += 1;
          for (int x=0; x<traceDepth; x++) System.out.print(" ");
          super.traceIn(rname, t);   
        }
        public void traceOut(String rname, AST t) {
          for (int x=0; x<traceDepth; x++) System.out.print(" ");
          super.traceOut(rname, t);
          traceDepth -= 1;
        }
}

// ***** grammar starts here ***** //

translationUnit 
options {
	defaultErrorHandler=false;
}
{
	Element translationUnitElement = xmlDocument.addElement("translationUnit");
    addElementPositions(translationUnitElement, #translationUnit);	
}
:
 ( a:externalList 
 		{
 			#a.reverseStack();  // we need to reverse the stack
 								// otherwise we get a wrong order
 								// TODO: change stack into queue
 			try {
				while (true) { translationUnitElement.add(#a.popElement());	} 			
 			} catch (java.util.EmptyStackException e) {}
 		}
 )? 
        ;

externalList :( ed:externalDef { #externalList.pushElement(#ed.popElement()); }	)+
        ;

externalDef :declaration
        |       functionDef
        |       typelessDeclaration
        |       asm_expr
        ;

typelessDeclaration :#(NTypeMissing initDeclList SEMI)
        ;

asm_expr :#( "asm" ( "volatile" )? LCURLY expr RCURLY ( SEMI )+ )
        ;

declaration  
        {
			Element declarationElement = xmlFactory.createElement("declaration");
		    addElementPositions(declarationElement, #declaration);				
           	#declaration.pushElement(declarationElement);
        }
		:
               #( NDeclaration
                    ds:declSpecifiers 
                    	{declarationElement.add(#ds.popElement());}
                    (                   
                        id:initDeclList //doplni se samo
                    )?
                    ( SEMI )+
                )
        ;

declSpecifiers  
				{
					Element declSpecifierElement = xmlFactory.createElement("declarationSpecifiers");
					addElementPositions(declSpecifierElement, #declSpecifiers);
					declSpecifierElement.addAttribute("const", "false");
		            declSpecifierElement.addAttribute("restrict", "false");					
					declSpecifierElement.addAttribute("volatile", "false");
					#declSpecifiers.pushElement(declSpecifierElement);
				}
		:
				( scs:storageClassSpecifier {declSpecifierElement.addAttribute("storageClass",#scs.getText());}
                | tq:typeQualifier {
                		// this gets confused by the likes of __const
                		// declSpecifierElement.attribute(#tq.getText()).setValue("true");
                		declSpecifierElement.attribute(stripUnderscores(#tq.getText())).setValue("true");
                		}
                | ts:typeSpecifier //<- Zde je ta chyba
                		{declSpecifierElement.add(#ts.popElement());}
                )+
        ;

storageClassSpecifier :"auto"
        |       "register"
        |       "typedef"
        |       functionStorageClassSpecifier
        ;

functionStorageClassSpecifier :"extern"
        |       "static"
        |       "inline"
        ;

typeQualifier :"const"
		|		"restrict"
        |       "volatile"
        ;

typeSpecifier 
		{
			Element tsEl = xmlFactory.createElement("typeSpecifier"); 
			addElementPositions(tsEl, #typeSpecifier);
			#typeSpecifier.pushElement(tsEl);
		}
		:
				a:"void" { Element el = xmlFactory.createElement("baseType");el.setText(#a.getText()); addElementPositions(el, #a); tsEl.add(el);}
        |       b:"char" {Element el = xmlFactory.createElement("baseType");el.setText(#b.getText()); addElementPositions(el, #b); tsEl.add(el);}
        |       c:"short" {Element el = xmlFactory.createElement("baseType");el.setText(#c.getText()); addElementPositions(el, #c); tsEl.add(el);}
        |       d:"int" {Element el = xmlFactory.createElement("baseType");el.setText(#d.getText()); addElementPositions(el, #d); tsEl.add(el);}
        |       e:"long" {Element el = xmlFactory.createElement("baseType");el.setText(#e.getText()); addElementPositions(el, #e); tsEl.add(el);}
        |       f:"float" {Element el = xmlFactory.createElement("baseType");el.setText(#f.getText()); addElementPositions(el, #f); tsEl.add(el);}
        |       g:"double" {Element el = xmlFactory.createElement("baseType");el.setText(#g.getText()); addElementPositions(el, #g); tsEl.add(el);}
        |       h:"signed" {Element el = xmlFactory.createElement("baseType");el.setText(#h.getText()); addElementPositions(el, #h); tsEl.add(el);}
        |       i:"unsigned" {Element el = xmlFactory.createElement("baseType");el.setText(#i.getText()); addElementPositions(el, #i); tsEl.add(el);}
        |       j:"_Bool" {Element el = xmlFactory.createElement("baseType");el.setText(#j.getText()); addElementPositions(el, #j); tsEl.add(el);}
        |       k:"_Complex" {Element el = xmlFactory.createElement("baseType");el.setText(#k.getText()); addElementPositions(el, #k); tsEl.add(el);}
        |       l:"_Imaginary" {Element el = xmlFactory.createElement("baseType");el.setText(#l.getText()); addElementPositions(el, #l); tsEl.add(el);}
        |       ss:structSpecifier ( attributeDecl )* {if (ss!=null){tsEl.add(#ss.popElement());}}
        |       us:unionSpecifier  ( attributeDecl )* {if (us!=null){tsEl.add(#us.popElement());}}
        |       es:enumSpecifier {if (es!=null) tsEl.add(#es.popElement());}
        |       td:typedefName {if (td!=null) tsEl.add(#td.popElement());}
        |       #("typeof" LPAREN
                    ( (typeName )=> typeName 
                    | expr
                    )
                    RPAREN
                )
        |       "__complex"        
		;

typedefName :#(NTypedefName id:ID)
			{
				Element typedefNameElement = xmlFactory.createElement("baseType");
				typedefNameElement.setText(#id.getText());
				addElementPositions(typedefNameElement, #typedefName);
				#typedefName.pushElement(typedefNameElement);	}
        ;

structSpecifier :#( "struct" body:structOrUnionBody ) {
					#structSpecifier.pushElement(#body.popElement());
				}
        ;

unionSpecifier :#( "union" body:structOrUnionBody ){
					#unionSpecifier.pushElement(#body.popElement());
				}
        ;

structOrUnionBody 
				{
					Element structOrUnionBodyElement = xmlFactory.createElement("structOrUnionType");
					addElementPositions(structOrUnionBodyElement, #structOrUnionBody);
					#structOrUnionBody.pushElement(structOrUnionBodyElement);
				}
				:( (ID LCURLY) => id1:ID LCURLY
                        ( structDeclarationList /*doplni se k elementu samo*/)?
                        RCURLY  
                        {structOrUnionBodyElement.addAttribute("id", id1.getText());}
                |   LCURLY
                    ( structDeclarationList /*doplni se k elementu samo*/)?
                    RCURLY
                | id2:ID {structOrUnionBodyElement.addAttribute("id", id2.getText());}
                )
        ;

structDeclarationList :( sd:structDeclaration 
						{ Element suElement = #structDeclarationList.findLastElement("structOrUnionType"); 
						  if (suElement != null) suElement.add(#sd.popElement());  } 
					   )+
        ;

structDeclaration  
			{
				Element structDeclarationElement = xmlFactory.createElement("structDeclaration");
				structDeclarationElement.addAttribute("const","false");
				structDeclarationElement.addAttribute("restrict","false");
				structDeclarationElement.addAttribute("volatile","false");
				addElementPositions(structDeclarationElement, #structDeclaration);
				#structDeclaration.pushElement(structDeclarationElement);
			}		
		:	
			specifierQualifierList //doplni se samy
			sdl:structDeclaratorList //doplni se samy
        ;

specifierQualifierList :(
                ts:typeSpecifier {Element sdElement = #specifierQualifierList.findLastElement("structDeclaration");
                				  if (sdElement != null) sdElement.add(#ts.popElement());
                				 }
                	
                | tq:typeQualifier {Element sdElement = #specifierQualifierList.findLastElement("structDeclaration");
                					if (sdElement != null) sdElement.attribute(stripUnderscores(#tq.getText())).setValue("true");
                					}
                )+
        ;

structDeclaratorList :( sd:structDeclarator 
							{Element sdElement = #structDeclaratorList.findLastElement("structDeclaration");
							 if (sdElement != null) sdElement.add(#sd.popElement());
							}
						)+
        ;

structDeclarator 
			{
				Element structDeclaratorElement = xmlFactory.createElement("structDeclarator");
				addElementPositions(structDeclaratorElement, #structDeclarator);
				#structDeclarator.pushElement(structDeclaratorElement);
			}
		:#( NStructDeclarator      
            ( d:declarator {structDeclaratorElement.add(#d.popElement());})?
            ( COLON e:expr {structDeclaratorElement.add(#e.popElement());})?
            ( attributeDecl )*
        )
        ;

enumSpecifier 
		{
				Element enumSpecifierElement = xmlFactory.createElement("enum");
				addElementPositions(enumSpecifierElement, #enumSpecifier);
				#enumSpecifier.pushElement(enumSpecifierElement);
		}
		:#(  "enum"
                ( id:ID {enumSpecifierElement.addAttribute("id", #id.getText());})? 
                ( LCURLY enumList RCURLY )?
            )
        ;

enumList :( e:enumerator 
				{Element enumElement = #enumList.findLastElement("enum");
				 if (enumElement != null) enumElement.add(#e.popElement());
				}
		  )+
        ;

enumerator 
		{
				Element enumeratorElement = xmlFactory.createElement("enumerator");
				addElementPositions(enumeratorElement, #enumerator);				
				#enumerator.pushElement(enumeratorElement);
		}
		:id:ID { enumeratorElement.addAttribute("id", #id.getText()); }
		 ( ASSIGN e:expr {enumeratorElement.add(#e.popElement());} )?
        ;

attributeDecl :#( "__attribute" (.)* )
        | #( NAsmAttribute LPAREN expr RPAREN )
        ;

initDeclList :( ind:initDecl {
								Element declarationElement = #initDeclList.findLastElement("declaration");
								if (declarationElement != null) declarationElement.add(#ind.popElement());								
							} )+
        ;

initDecl 	{   
				Element initDeclElement = xmlFactory.createElement("initDeclarator");
				addElementPositions(initDeclElement, #initDecl);				
				#initDecl.pushElement(initDeclElement);
			}

		:#( NInitDecl
                decl:declarator {initDeclElement.add(#decl.popElement());}
                ( attributeDecl )*
                ( ASSIGN init:initializer {initDeclElement.add(#init.popElement());}
                | COLON expr
                )?
                )
        ;

pointerGroup 
		{
			Element pointerElement = xmlFactory.createElement("pointer");
			pointerElement.addAttribute("const", "false");
			pointerElement.addAttribute("restrict", "false");
			pointerElement.addAttribute("volatile", "false");
 			addElementPositions(pointerElement, #pointerGroup);
		}
		:#( NPointerGroup 
					( STAR 
						( tq:typeQualifier {pointerElement.attribute(stripUnderscores(#tq.getText())).setValue("true");}
						)* 
					)+ 
				{
						Element declaratorElement = #pointerGroup.findLastElement("declarator", "abstractDeclarator");
						if (declaratorElement != null) declaratorElement.add(pointerElement);
				}
		)
        ;

idList	// old style parameters 
		:id1:ID {
				Element parameterElement = xmlFactory.createElement("parameter");
	 			addElementPositions(parameterElement, #id1);				
				Element idElement = xmlFactory.createElement("id");
				idElement.setText(#id1.getText());
	 			addElementPositions(idElement, #id1);
				parameterElement.add(idElement);
				#idList.pushElement(parameterElement);		
			}
			( COMMA id2:ID 
				{
				Element parameterElement = xmlFactory.createElement("parameter");
	 			addElementPositions(parameterElement, #id2);				
				Element idElement = xmlFactory.createElement("id");
				idElement.setText(#id2.getText());
	 			addElementPositions(idElement, #id2);
				parameterElement.add(idElement);
				#idList.pushElement(parameterElement);		
				}
			)*
        ;

initializer :#( NInitializer 
				(ie:initializerElementLabel {#initializer.pushElement(#ie.popElement());} )? 
				  e:expr {#initializer.pushElement(#e.popElement());} 
			  )
                |   lcurlyInitializer //doplni se samo
        ;

initializerElementLabel 
		{
			Element designatorElement = xmlFactory.createElement("designator");
			addElementPositions(designatorElement, #initializerElementLabel);
			#initializerElementLabel.pushElement(designatorElement);
		}
		:#( NInitializerElementLabel
                (
                    ( LBRACKET e:expr {designatorElement.add(#e.popElement());} RBRACKET (ASSIGN)? )
                    | id:ID COLON {Element idElement = xmlFactory.createElement("id"); addElementPositions(idElement, #id); idElement.setText(id.getText()); designatorElement.add(idElement); }
                    | DOT ID ASSIGN
                )
            )
        ;

lcurlyInitializer :#( NLcurlyInitializer
                initializerList
                RCURLY
            )
        ;

initializerList 
		:( ini:initializer 
		//Toto by chtelo predelat a taky otestovat, jestli to vubec funguje. to getParent je zde, abychom nenasli sami sebe pri hledani initializerElementu
		//? Mozna by to chtelo dat alespon do lcurlyInitializer ?
		    {Element initializerElement = #initializerList.getParent().findLastElement("initializer");
		     if (initializerElement != null) initializerElement.add(#ini.popElement());}
		 )*   
        ;

declarator 
		{
			Element declaratorElement = xmlFactory.createElement("declarator");
			#declarator.pushElement(declaratorElement);
			addElementPositions(declaratorElement, #declarator);
			Element arrayDeclElement = null; //deklarace je zde, aby na nej bylo videt, davame ho ven
		}
		:#( NDeclarator
                ( pointerGroup )? //pointery se samy pridaji k elementu declaratorElement

                ( id:ID {Element idElement = xmlFactory.createElement("id"); addElementPositions(idElement, #id); idElement.setText(id.getText()); declaratorElement.add(idElement);}
                | LPAREN decl:declarator RPAREN {declaratorElement.add(#decl.popElement());}
                )

                (   #( NParameterTypeList
                      (
                        pl:parameterTypeList { //new style parameters
				 			#pl.reverseStack();  // we need to reverse the stack, otherwise we get a wrong order
				 			try {
							  while (true) { declaratorElement.add(#pl.popElement()); } 			
				 			} catch (java.util.EmptyStackException ex) {}
				 		}
                        | (il:idList { //old style parameters
				 			#il.reverseStack();  // we need to reverse the stack, otherwise we get a wrong order
				 			try {
							  while (true) { declaratorElement.add(#il.popElement()); } 			
				 			} catch (java.util.EmptyStackException ex) {}
				 		}
                        	)?	
                      )
                      RPAREN
                    )
                 | {
					arrayDeclElement = xmlFactory.createElement("arrayDecl");
					addElementPositions(arrayDeclElement, #declarator);
					declaratorElement.add(arrayDeclElement);
                   }LBRACKET ( e:expr {arrayDeclElement.add(#e.popElement());} )? RBRACKET //array declarator
                )*
             )
        ;

parameterTypeList 
		:( pd:parameterDeclaration {#parameterTypeList.pushElement(#pd.popElement());} 
		   ( COMMA | SEMI )? 			
		 )+ ( VARARGS {
		 		Element varArgsElement = xmlFactory.createElement("varArgs");
		 		addElementPositions(varArgsElement, #parameterTypeList); // TODO needs fixing
		 		#parameterTypeList.pushElement(varArgsElement);
		 	})?
        ;

parameterDeclaration 
		{
			Element parameterElement = xmlFactory.createElement("parameter");
			addElementPositions(parameterElement, #parameterDeclaration);
			#parameterDeclaration.pushElement(parameterElement);
		}
		:#( NParameterDeclaration
                ds:declSpecifiers  {parameterElement.add(#ds.popElement());} 
                (dec:declarator {parameterElement.add(#dec.popElement());}
                | ad:nonemptyAbstractDeclarator {parameterElement.add(#ad.popElement());} )?
                )
        ;

functionDef 
	{ 
		Element functionDefElement = xmlFactory.createElement("functionDefinition"); 
		addElementPositions(functionDefElement, #functionDef);
        #functionDef.pushElement(functionDefElement);
	}
			:#( NFunctionDef 
                ( fds:functionDeclSpecifiers
                	{
                		if (#fds!=null) {functionDefElement.add(#fds.popElement());}
                	}
                 )? 
                dr:declarator {functionDefElement.add(#dr.popElement());}
                (dn:declaration | VARARGS)* // old style TODO add this!
                cs:compoundStatement {functionDefElement.add(#cs.popElement());}
            )
        ;

functionDeclSpecifiers 
		{
			Element functionDeclSpecifiersElement = xmlFactory.createElement("declarationSpecifier");
           	functionDeclSpecifiersElement.addAttribute("const", "false");
           	functionDeclSpecifiersElement.addAttribute("restrict", "false");
           	functionDeclSpecifiersElement.addAttribute("volatile", "false");
           	addElementPositions(functionDeclSpecifiersElement, #functionDeclSpecifiers);
			#functionDeclSpecifiers.pushElement(functionDeclSpecifiersElement);
		}
				:( 
			      scs:functionStorageClassSpecifier
			      	{ if (#scs!=null) functionDeclSpecifiersElement.addAttribute("storageClass", #scs.getText()); }
                | tq:typeQualifier
                	{
                		if (#tq!=null) {
					        functionDeclSpecifiersElement.attribute(stripUnderscores(#tq.getText())).setValue("true");                			
                		}
                	}
                | ts:typeSpecifier
                	{
			      		if (#ts!=null) functionDeclSpecifiersElement.add(#ts.popElement());
                	}
                )+
        ;

declarationList :(   //ANTLR doesn't know that declarationList properly eats all the declarations
                    //so it warns about the ambiguity
                    options {
                        warnWhenFollowAmbig = false;
                    } :
                localLabelDecl
                | d:declaration {Element csElement = #declarationList.findLastElement("compoundStatement");
                				 if (csElement != null) csElement.add(#d.popElement());
                				}
                )+
        ;

localLabelDecl :#("__label__" (ID)+ )
        ;

compoundStatement 
		{
					Element compoundStatementElement = xmlFactory.createElement("compoundStatement");	
		           	addElementPositions(compoundStatementElement, #compoundStatement);
	        	   	#compoundStatement.pushElement(compoundStatementElement);                            		
		}
		:#( NCompoundStatement
//                ( declaration /*vracene elementy se pripoji ke compoundStatement, proto je nemusime predavat*/
                ( de:declaration {compoundStatementElement.add(#de.popElement());}
                | fd:functionDef {compoundStatementElement.add(#fd.popElement());}
				| st:statement {compoundStatementElement.add(#st.popElement());}
                )*
                RCURLY
                )
        ;

statementList 
		:( stat:statement 
     		{Element csElement = #statementList.findLastElement("compoundStatement");
     		 if (csElement != null) csElement.add(#stat.popElement());
     		}
		)+
        ;

statement :
  			sb:statementBody { #statement.pushElement(#sb.popElement());}
        ;

statementBody 

		:SEMI  {// Empty statements
					Element emptyStatementElement = xmlFactory.createElement("emptyStatement");	
		           	addElementPositions(emptyStatementElement, #statementBody);
	        	   	#statementBody.pushElement(emptyStatementElement);                            		
               }
        |       cs:compoundStatement {// Group of statements
        			#statementBody.pushElement(#cs.popElement());
      			}      

        |       #(NStatementExpr expression:expr) {                   // Expressions
	        	   	#statementBody.pushElement(#expression.popElement());
        		}

// Iteration statements:

        |       #( "while" whileExpr:expr whileStat:statement ) 
        			{
						Element whileStatementElement = xmlFactory.createElement("whileStatement");
			           	addElementPositions(whileStatementElement, #statementBody);
		        	   	#statementBody.pushElement(whileStatementElement);		        	   	
		        	   	whileStatementElement.add(#whileExpr.popElement());
		        	   	whileStatementElement.add(#whileStat.popElement());
        			}
        |       #( "do" doStat:statement doExpr:expr 
        			{
						Element doStatementElement = xmlFactory.createElement("doStatement");
			           	addElementPositions(doStatementElement, #statementBody);
		        	   	#statementBody.pushElement(doStatementElement);		        	   	
		        	   	doStatementElement.add(#doExpr.popElement());
		        	   	doStatementElement.add(#doStat.popElement());
        			}
        		)
        |       #( "for"
                (forExpr1:expr | forDecl:declaration) forExpr2:expr forExpr3:expr
                forStat:statement
                	{
						Element forStatementElement = xmlFactory.createElement("forStatement");
			           	addElementPositions(forStatementElement, #statementBody);
		        	   	#statementBody.pushElement(forStatementElement);
						if (forExpr1 != null) {		        	   	
			        	   	forStatementElement.add(#forExpr1.popElement());
			        	} else {
			        		forStatementElement.add(#forDecl.popElement());
			        	}
		        	   	forStatementElement.add(#forExpr2.popElement());
		        	   	forStatementElement.add(#forExpr3.popElement());
		        	   	forStatementElement.add(#forStat.popElement());
                	}                
                )


// Jump statements:

        |       #( "goto" gotoExpr:expr )
        			{ 
        				Element gotoStatementElement = xmlFactory.createElement("gotoStatement");
			           	addElementPositions(gotoStatementElement, #statementBody);
        				gotoStatementElement.add(#gotoExpr.popElement());
		        	   	#statementBody.pushElement(gotoStatementElement);		        	   	
        			}
        |       "continue" 
        			{ 
        				Element continueStatementElement = xmlFactory.createElement("continueStatement");
			           	addElementPositions(continueStatementElement, #statementBody);
		        	   	#statementBody.pushElement(continueStatementElement);		        	   	
        			}
        |       "break" 
        			{ 
        				Element breakStatementElement = xmlFactory.createElement("breakStatement");
			           	addElementPositions(breakStatementElement, #statementBody);
		        	   	#statementBody.pushElement(breakStatementElement);		        	   	
        			}        
        |       #( "return" ( returnExpr:expr )? 
        			{ 
        				Element returnStatementElement = xmlFactory.createElement("returnStatement");
			           	addElementPositions(returnStatementElement, #statementBody);
		        	   	#statementBody.pushElement(returnStatementElement);	
		        	   	if ((#returnExpr!=null)) returnStatementElement.add(#returnExpr.popElement());
        			}                
        		)


// Labeled statements:
        |       #( NLabel labelId:ID (labelStat:statement)? 
                    {
        				Element labelStatementElement = xmlFactory.createElement("labelStatement");
			           	addElementPositions(labelStatementElement, #statementBody);
        				labelStatementElement.addAttribute("id", #labelId.getText());
        				if (#labelStat!=null) {labelStatementElement.add(#labelStat.popElement());}
		        	   	#statementBody.pushElement(labelStatementElement);		        	   	
                    }
                 )
        |       #( "case" caseExpr:expr (caseStat:statement)? 
                    {
        				Element caseLabelStatementElement = xmlFactory.createElement("caseLabelStatement");
			           	addElementPositions(caseLabelStatementElement, #statementBody);        				
        				if (#caseExpr!=null) {caseLabelStatementElement.add(#caseExpr.popElement());}
        				if (#caseStat!=null) {caseLabelStatementElement.add(#caseStat.popElement());}
		        	   	#statementBody.pushElement(caseLabelStatementElement);		        	   	
                    }
                 )
        |       #( "default" (defaultStat:statement)? 
                    {
        				Element defaultLabelStatementElement = xmlFactory.createElement("defaultLabelStatement");
			           	addElementPositions(defaultLabelStatementElement, #statementBody);        				
        				if (#defaultStat!=null) {defaultLabelStatementElement.add(#defaultStat.popElement());}
		        	   	#statementBody.pushElement(defaultLabelStatementElement);		        	   	
                    }
                 )



// Selection statements:

        |       #( "if"
                    ifExpr:expr ifStat1:statement
                    ( "else" ifStat2:statement )?
                        {
                        	if (#ifStat2==null) { //ifStatement
	                            Element ifStatementElement = xmlFactory.createElement("ifStatement");
					           	addElementPositions(ifStatementElement, #statementBody);        				
    	                        if (#ifExpr!=null) {ifStatementElement.add(#ifExpr.popElement());}
	                            if (#ifStat1!=null) {ifStatementElement.add(#ifStat1.popElement());}
                                #statementBody.pushElement(ifStatementElement);
                        	} else { //ifElseStatement
                                Element ifElseStatementElement = xmlFactory.createElement("ifElseStatement");
					           	addElementPositions(ifElseStatementElement, #statementBody);        				
   	                            if (#ifExpr!=null) {ifElseStatementElement.add(#ifExpr.popElement());}
                                if (#ifStat1!=null) {ifElseStatementElement.add(#ifStat1.popElement());}
                                if (#ifStat2!=null) {ifElseStatementElement.add(#ifStat2.popElement());}
                                #statementBody.pushElement(ifElseStatementElement);
                        	}
                        }
                 )
        |       #( "switch" switchExpr:expr switchStat:statement 
                    {
                        Element switchStatementElement = xmlFactory.createElement("switchStatement");
			           	addElementPositions(switchStatementElement, #statementBody);        				
                        if (#switchExpr!=null) {switchStatementElement.add(#switchExpr.popElement());}
                        if (#switchStat!=null) {switchStatementElement.add(#switchStat.popElement());}
                        #statementBody.pushElement(switchStatementElement);
                    }
                 )



        ;

expr {  //jestlize nalezena expression, tak se vrati element "expression"
	    Element exprElement = xmlFactory.createElement("expression"); 
       	addElementPositions(exprElement, #expr);
	    #expr.pushElement(exprElement);
     }
    	:   	assignE:assignExpr {#expr.pushElement(#assignE.popElement());}
        |       conditionalE:conditionalExpr {#expr.pushElement(#conditionalE.popElement());}
        |       logicalOrE:logicalOrExpr {#expr.pushElement(#logicalOrE.popElement());}
        |       logicalAndE:logicalAndExpr {#expr.pushElement(#logicalAndE.popElement());}
        |       inclusiveOrE:inclusiveOrExpr {#expr.pushElement(#inclusiveOrE.popElement());}
        |       exclusiveOrE:exclusiveOrExpr {#expr.pushElement(#exclusiveOrE.popElement());}
        |       bitAndE:bitAndExpr {#expr.pushElement(#bitAndE.popElement());}
        |       equalityE:equalityExpr {#expr.pushElement(#equalityE.popElement());}
        |       relationalE:relationalExpr {#expr.pushElement(#relationalE.popElement());}
        |       shiftE:shiftExpr {#expr.pushElement(#shiftE.popElement());}
        |       additiveE:additiveExpr {#expr.pushElement(#additiveE.popElement());}
        |       multE:multExpr {#expr.pushElement(#multE.popElement());}
        |       castE:castExpr {#expr.pushElement(#castE.popElement());}
        |       unaryE:unaryExpr {#expr.pushElement(#unaryE.popElement());}
        |       postfixE:postfixExpr {#expr.pushElement(#postfixE.popElement());}
        |       primaryE:primaryExpr {#expr.pushElement(#primaryE.popElement());}
        |       commaE:commaExpr {#expr.pushElement(#commaE.popElement());}
        |       emptyE:emptyExpr {#expr.pushElement(#emptyE.popElement());}
        |       compoundStatementE:compoundStatementExpr {#expr.pushElement(#compoundStatementE.popElement());}
        |       initE:initializer {#expr.pushElement(#initE.popElement());}
        |       rangeE:rangeExpr {#expr.pushElement(#rangeE.popElement());}
        |       gnuAsmE:gnuAsmExpr {#expr.pushElement(#gnuAsmE.popElement());}
		;

commaExpr :#(NCommaExpr e1:expr e2:expr)
        {
            Element commaExprElement = xmlFactory.createElement("commaExpression");
	       	addElementPositions(commaExprElement, #commaExpr);            
        	#commaExpr.pushElement(commaExprElement);
        	if (#e1 != null) commaExprElement.add(#e1.popElement());
        	if (#e2 != null) commaExprElement.add(#e2.popElement());
        }
        ;

emptyExpr :NEmptyExpression
        {
            Element emptyExprElement = xmlFactory.createElement("emptyExpression");
	       	addElementPositions(emptyExprElement, #emptyExpr);
        	#emptyExpr.pushElement(emptyExprElement);
        }

        ;

compoundStatementExpr :#(LPAREN cs:compoundStatement RPAREN)
        {
            #compoundStatementExpr.pushElement(#cs.popElement());
        }
        ;

rangeExpr :#(NRangeExpr e1:expr VARARGS e2:expr) 
        {
            Element rangeExprElement = xmlFactory.createElement("rangeExpression");
	       	addElementPositions(rangeExprElement, #rangeExpr);
        	#rangeExpr.pushElement(rangeExprElement);
        	if (#e1 != null) rangeExprElement.add(#e1.popElement());
        	if (#e2 != null) rangeExprElement.add(#e2.popElement());
        }
        ;

gnuAsmExpr :#(NGnuAsmExpr
                ("volatile")? 
                LPAREN stringConst
                ( options { warnWhenFollowAmbig = false; }:
                  COLON (strOptExprPair ( COMMA strOptExprPair)* )?
                  ( options { warnWhenFollowAmbig = false; }:
                    COLON (strOptExprPair ( COMMA strOptExprPair)* )?
                  )?
                )?
                ( COLON stringConst ( COMMA stringConst)* )?
                RPAREN
            )
            {
            	Element gnuAsmExprElement = xmlFactory.createElement("gnuAssembler");
		       	addElementPositions(gnuAsmExprElement, #gnuAsmExpr);
            	#gnuAsmExpr.pushElement(gnuAsmExprElement);
            }
        ;

strOptExprPair :stringConst ( LPAREN expr RPAREN )?
        ;

assignExpr 
        {
            Element assignExprElement = xmlFactory.createElement("assignExpression");
	       	addElementPositions(assignExprElement, #assignExpr);
        	#assignExpr.pushElement(assignExprElement);	        	
        }
        :       #( ASSIGN assignE1:expr assignE2:expr) {assignExprElement.add(assignE1.popElement());assignExprElement.add(assignE2.popElement());}
        |       #( DIV_ASSIGN divE1:expr divE2:expr) {assignExprElement.addAttribute("op", "/");assignExprElement.add(divE1.popElement());assignExprElement.add(divE2.popElement());}
        |       #( PLUS_ASSIGN plusE1:expr plusE2:expr) {assignExprElement.addAttribute("op", "+");assignExprElement.add(plusE1.popElement());assignExprElement.add(plusE2.popElement());}
        |       #( MINUS_ASSIGN minusE1:expr minusE2:expr) {assignExprElement.addAttribute("op", "-");assignExprElement.add(minusE1.popElement());assignExprElement.add(minusE2.popElement());}
        |       #( STAR_ASSIGN starE1:expr starE2:expr) {assignExprElement.addAttribute("op", "*");assignExprElement.add(starE1.popElement());assignExprElement.add(starE2.popElement());}
        |       #( MOD_ASSIGN modE1:expr modE2:expr) {assignExprElement.addAttribute("op", "%");assignExprElement.add(modE1.popElement());assignExprElement.add(modE2.popElement());}
        |       #( RSHIFT_ASSIGN rshiftE1:expr rshiftE2:expr) {assignExprElement.addAttribute("op", ">>");assignExprElement.add(rshiftE1.popElement());assignExprElement.add(rshiftE2.popElement());}
        |       #( LSHIFT_ASSIGN lshiftE1:expr lshiftE2:expr) {assignExprElement.addAttribute("op", "<<");assignExprElement.add(lshiftE1.popElement());assignExprElement.add(lshiftE2.popElement());}
        |       #( BAND_ASSIGN bandE1:expr bandE2:expr) {assignExprElement.addAttribute("op", "&");assignExprElement.add(bandE1.popElement());assignExprElement.add(bandE2.popElement());}
        |       #( BOR_ASSIGN borE1:expr borE2:expr) {assignExprElement.addAttribute("op", "|");assignExprElement.add(borE1.popElement());assignExprElement.add(borE2.popElement());}
        |       #( BXOR_ASSIGN bxorE1:expr bxorE2:expr) {assignExprElement.addAttribute("op", "^");assignExprElement.add(bxorE1.popElement());assignExprElement.add(bxorE2.popElement());}
        ;

conditionalExpr :#( QUESTION qe:expr (e:expr)? COLON ce:expr )
        {
            Element conditionalExprElement = xmlFactory.createElement("conditionalExpression");
	       	addElementPositions(conditionalExprElement, #conditionalExpr);
        	#conditionalExpr.pushElement(conditionalExprElement);
        	if (#qe != null) conditionalExprElement.add(#qe.popElement());
        	if (#e != null) conditionalExprElement.add(#e.popElement()); //pozor, zde musi IF byt, protoze #e se nemusi vyskytovat
        	if (#ce != null) conditionalExprElement.add(#ce.popElement());
        }
        ;

logicalOrExpr :#( LOR e1:expr e2:expr)
		{
            Element logicalOrExprElement = xmlFactory.createElement("binaryExpression");
	       	addElementPositions(logicalOrExprElement, #logicalOrExpr);
            logicalOrExprElement.addAttribute("op", "||");
        	#logicalOrExpr.pushElement(logicalOrExprElement);
        	logicalOrExprElement.add(#e1.popElement());
        	logicalOrExprElement.add(#e2.popElement());
		} 
        ;

logicalAndExpr :#( LAND e1:expr e2:expr )
		{
            Element logicalAndExprElement = xmlFactory.createElement("binaryExpression");
   	       	addElementPositions(logicalAndExprElement, #logicalAndExpr);           
            logicalAndExprElement.addAttribute("op", "&&");
        	#logicalAndExpr.pushElement(logicalAndExprElement);
        	logicalAndExprElement.add(#e1.popElement());
        	logicalAndExprElement.add(#e2.popElement());
		} 
        ;

inclusiveOrExpr :#( BOR e1:expr e2:expr )
		{
            Element inclusiveOrExprElement = xmlFactory.createElement("binaryExpression");
   	       	addElementPositions(inclusiveOrExprElement, #inclusiveOrExpr);                       
            inclusiveOrExprElement.addAttribute("op", "|");
        	#inclusiveOrExpr.pushElement(inclusiveOrExprElement);
        	inclusiveOrExprElement.add(#e1.popElement());
        	inclusiveOrExprElement.add(#e2.popElement());
		} 
        ;

exclusiveOrExpr :#( BXOR e1:expr e2:expr )
		{
            Element exclusiveOrExprElement = xmlFactory.createElement("binaryExpression");
   	       	addElementPositions(exclusiveOrExprElement, #exclusiveOrExpr);                                   
            exclusiveOrExprElement.addAttribute("op", "^");
        	#exclusiveOrExpr.pushElement(exclusiveOrExprElement);
        	exclusiveOrExprElement.add(#e1.popElement());
        	exclusiveOrExprElement.add(#e2.popElement());
		} 
        ;

bitAndExpr :#( BAND e1:expr e2:expr )
		{
            Element bitAndExprElement = xmlFactory.createElement("binaryExpression");
   	       	addElementPositions(bitAndExprElement, #bitAndExpr);
            bitAndExprElement.addAttribute("op", "&;");
        	#bitAndExpr.pushElement(bitAndExprElement);
        	bitAndExprElement.add(#e1.popElement());
        	bitAndExprElement.add(#e2.popElement());
		} 
        ;

equalityExpr :  #( EQUAL e1:expr e2:expr)
		{
            Element equalityExprElement = xmlFactory.createElement("binaryExpression");
   	       	addElementPositions(equalityExprElement, #equalityExpr);            
            equalityExprElement.addAttribute("op", "==");
        	#equalityExpr.pushElement(equalityExprElement);
        	equalityExprElement.add(#e1.popElement());
        	equalityExprElement.add(#e2.popElement());
		} 
        |       #( NOT_EQUAL ne1:expr ne2:expr)
		{
            Element equalityExprElement = xmlFactory.createElement("binaryExpression");
   	       	addElementPositions(equalityExprElement, #equalityExpr);            
            equalityExprElement.addAttribute("op", "!=");
        	#equalityExpr.pushElement(equalityExprElement);
        	equalityExprElement.add(#ne1.popElement());
        	equalityExprElement.add(#ne2.popElement());
		}         
        ;

relationalExpr :#( LT ltE1:expr ltE2:expr)
		{
            Element relationalExprElement = xmlFactory.createElement("binaryExpression");
   	       	addElementPositions(relationalExprElement, #relationalExpr);            
            relationalExprElement.addAttribute("op", "<");
        	#relationalExpr.pushElement(relationalExprElement);
        	relationalExprElement.add(#ltE1.popElement());
        	relationalExprElement.add(#ltE2.popElement());
		} 
        |       #( LTE lteE1:expr lteE2:expr)
		{
            Element relationalExprElement = xmlFactory.createElement("binaryExpression");
   	       	addElementPositions(relationalExprElement, #relationalExpr);            
            relationalExprElement.addAttribute("op", "<=");
        	#relationalExpr.pushElement(relationalExprElement);
        	relationalExprElement.add(#lteE1.popElement());
        	relationalExprElement.add(#lteE2.popElement());
		} 
        |       #( GT gtE1:expr gtE2:expr)
		{
            Element relationalExprElement = xmlFactory.createElement("binaryExpression");
   	       	addElementPositions(relationalExprElement, #relationalExpr);            
            relationalExprElement.addAttribute("op", ">");
        	#relationalExpr.pushElement(relationalExprElement);
        	relationalExprElement.add(#gtE1.popElement());
        	relationalExprElement.add(#gtE2.popElement());
		} 
        |       #( GTE gteE1:expr gteE2:expr)
		{
            Element relationalExprElement = xmlFactory.createElement("binaryExpression");
   	       	addElementPositions(relationalExprElement, #relationalExpr);            
            relationalExprElement.addAttribute("op", ">=");
        	#relationalExpr.pushElement(relationalExprElement);
        	relationalExprElement.add(#gteE1.popElement());
        	relationalExprElement.add(#gteE2.popElement());
		} 
        ;

shiftExpr :#( LSHIFT lsE1:expr lsE2:expr)
		{
            Element shiftExprElement = xmlFactory.createElement("binaryExpression");
   	       	addElementPositions(shiftExprElement, #shiftExpr);            
            shiftExprElement.addAttribute("op", "<<");
        	#shiftExpr.pushElement(shiftExprElement);
        	shiftExprElement.add(#lsE1.popElement());
        	shiftExprElement.add(#lsE2.popElement());
		} 
                | #( RSHIFT rsE1:expr rsE2:expr)
		{
            Element shiftExprElement = xmlFactory.createElement("binaryExpression");
   	       	addElementPositions(shiftExprElement, #shiftExpr);            
            shiftExprElement.addAttribute("op", ">>");
        	#shiftExpr.pushElement(shiftExprElement);
        	shiftExprElement.add(#rsE1.popElement());
        	shiftExprElement.add(#rsE2.popElement());
		} 
        ;

additiveExpr :#( PLUS pE1:expr pE2:expr)
		{
            Element additiveExprElement = xmlFactory.createElement("binaryExpression");
   	       	addElementPositions(additiveExprElement, #additiveExpr);            
            additiveExprElement.addAttribute("op", "+");
        	#additiveExpr.pushElement(additiveExprElement);
        	additiveExprElement.add(#pE1.popElement());
        	additiveExprElement.add(#pE2.popElement());
		} 
        |       #( MINUS mE1:expr mE2:expr)
		{
            Element additiveExprElement = xmlFactory.createElement("binaryExpression");
   	       	addElementPositions(additiveExprElement, #additiveExpr);            
            additiveExprElement.addAttribute("op", "-");
        	#additiveExpr.pushElement(additiveExprElement);
        	additiveExprElement.add(#mE1.popElement());
        	additiveExprElement.add(#mE2.popElement());
		}         
        ;

multExpr :#( STAR multE1:expr multE2:expr)
		{
            Element multExprElement = xmlFactory.createElement("binaryExpression");
   	       	addElementPositions(multExprElement, #multExpr);            
            multExprElement.addAttribute("op", "*");
        	#multExpr.pushElement(multExprElement);
        	multExprElement.add(#multE1.popElement());
        	multExprElement.add(#multE2.popElement());
		} 
        |       #( DIV divE1:expr divE2:expr)
		{
            Element multExprElement = xmlFactory.createElement("binaryExpression");
   	       	addElementPositions(multExprElement, #multExpr);            
            multExprElement.addAttribute("op", "/");
        	#multExpr.pushElement(multExprElement);
        	multExprElement.add(#divE1.popElement());
        	multExprElement.add(#divE2.popElement());
		} 
        |       #( MOD modE1:expr modE2:expr)
		{
            Element multExprElement = xmlFactory.createElement("binaryExpression");
   	       	addElementPositions(multExprElement, #multExpr);            
            multExprElement.addAttribute("op", "%");
        	#multExpr.pushElement(multExprElement);
        	multExprElement.add(#modE1.popElement());
        	multExprElement.add(#modE2.popElement());
		} 
        ;

castExpr :#( NCast tn:typeName RPAREN e:expr)
		{
            Element castExprElement = xmlFactory.createElement("castExpression");
   	       	addElementPositions(castExprElement, #castExpr);            
            castExprElement.add(#tn.popElement());
            castExprElement.add(#e.popElement());
            #castExpr.pushElement(castExprElement);
		}
        ;

typeName 
		{
            Element typeNameElement = xmlFactory.createElement("typeName");
            typeNameElement.addAttribute("const", "false");
            typeNameElement.addAttribute("restrict", "false");
            typeNameElement.addAttribute("volatile", "false");
   	       	addElementPositions(typeNameElement, #typeName);            
			#typeName.pushElement(typeNameElement);	
		}
		:specifierQualifierList  //doplni se samo
		 (ad:nonemptyAbstractDeclarator
			 {
             	typeNameElement.add(#ad.popElement());
			 }
		 )?
        ;

nonemptyAbstractDeclarator 
			{
	            Element nonemptyAbstractDeclaratorElement = xmlFactory.createElement("abstractDeclarator");
	   	       	addElementPositions(nonemptyAbstractDeclaratorElement, #nonemptyAbstractDeclarator);            
	            #nonemptyAbstractDeclarator.pushElement(nonemptyAbstractDeclaratorElement);
				Element arrayDeclElement = null; //deklarace je zde, aby na nej bylo videt, davame ho ven
			}
			:#( NNonemptyAbstractDeclarator
            (   pointerGroup
                (   (LPAREN  
                    (   ad1:nonemptyAbstractDeclarator {nonemptyAbstractDeclaratorElement.add(#ad1.popElement());}
                        | pl:parameterTypeList //doplnilo se puvodne samo 
                        {
                        	#pl.reverseStack();  // we need to reverse the stack, otherwise we get a wrong order
				 			try {
							  while (true) { nonemptyAbstractDeclaratorElement.add(#pl.popElement()); } 			
				 			} catch (java.util.EmptyStackException ex) {}
                        	
                        }
                    )?
                    RPAREN)
                | {
					arrayDeclElement = xmlFactory.createElement("arrayDecl");
		   	       	addElementPositions(arrayDeclElement, #nonemptyAbstractDeclarator);            
					nonemptyAbstractDeclaratorElement.add(arrayDeclElement);
                  } 
                  (LBRACKET (e1:expr {arrayDeclElement.add(#e1.popElement());})? RBRACKET)
                )*

            |  (   (LPAREN  
                    (   ad2:nonemptyAbstractDeclarator {nonemptyAbstractDeclaratorElement.add(#ad2.popElement());}
                        | pl2:parameterTypeList //doplnilo se puvodne samo
                        {
                        	#pl2.reverseStack();  // we need to reverse the stack, otherwise we get a wrong order
				 			try {
							  while (true) { nonemptyAbstractDeclaratorElement.add(#pl2.popElement()); } 			
				 			} catch (java.util.EmptyStackException ex) {}
                        	
                        }
                    )?
                    RPAREN)
                | {
					arrayDeclElement = xmlFactory.createElement("arrayDecl");
		   	       	addElementPositions(arrayDeclElement, #nonemptyAbstractDeclarator);            
					nonemptyAbstractDeclaratorElement.add(arrayDeclElement);
                  }
                  (LBRACKET (e2:expr {arrayDeclElement.add(#e2.popElement());})? RBRACKET)
                )+
            )
            )
        ;

unaryExpr 
		{
			Element	prefixExprElement = xmlFactory.createElement("prefixExpression");
   	       	addElementPositions(prefixExprElement, #unaryExpr);            
			#unaryExpr.pushElement(prefixExprElement);
		}
		:		#( INC incE:expr {prefixExprElement.addAttribute("op","++"); prefixExprElement.add(#incE.popElement());})
        |       #( DEC decE:expr {prefixExprElement.addAttribute("op","--"); prefixExprElement.add(#decE.popElement());})
        |       #( NUnaryExpr unaryOperator uoE:expr {prefixExprElement.add(#uoE.popElement());})
        |       #( "sizeof" {/*zmeni se typ elementu*/ prefixExprElement.setName("sizeofExpression");}
        	
                    ( ( LPAREN typeName )=> LPAREN sizeoftn:typeName RPAREN { prefixExprElement.add(#sizeoftn.popElement()); }
                    | sizeofE:expr { prefixExprElement.add(#sizeofE.popElement()); }
                    )
                )
        |       #( "__alignof" {/*zmeni se typ elementu*/ prefixExprElement.setName("alignofExpression");}
                    ( ( LPAREN typeName )=> LPAREN alignoftn:typeName RPAREN { prefixExprElement.add(#alignoftn.popElement()); }
                    | alignofE:expr { prefixExprElement.add(#alignofE.popElement()); }
                    )
                )
        ;

unaryOperator 
		:		BAND {
						Element addrExpressionElement = xmlFactory.createElement("addrExpression"); 
						addElementPositions(addrExpressionElement, #unaryOperator); 
						
						Element puvodniElement = #unaryOperator.getParent().popElement(); //vyhodnime z predka puvodni element
						if (#unaryOperator.findLastElement("prefixExpression") == puvodniElement) {
							#unaryOperator.getParent().pushElement(addrExpressionElement);  // a nahradime jinym
						} else { //nekde se stala chyba - neni to ten samy element -> vratime ho zpet !!! TOTO BY SE NIKDY NEMELO STAT
							#unaryOperator.getParent().pushElement(puvodniElement);
						}
					 } 
        |       STAR {
						Element derefExpressionElement = xmlFactory.createElement("derefExpression");
						addElementPositions(derefExpressionElement, #unaryOperator); 

						Element puvodniElement = #unaryOperator.getParent().popElement(); //vyhodnime z predka puvodni element
						if (#unaryOperator.findLastElement("prefixExpression") == puvodniElement) {
							#unaryOperator.getParent().pushElement(derefExpressionElement);  // a nahradime jinym
						} else { //nekde se stala chyba - neni to ten samy element -> vratime ho zpet !!! TOTO BY SE NIKDY NEMELO STAT
							#unaryOperator.getParent().pushElement(puvodniElement);
						}
        			  } 
        |       PLUS {Element pe = #unaryOperator.findLastElement("prefixExpression"); if (pe != null) pe.addAttribute("op","+");}
        |       MINUS {Element pe = #unaryOperator.findLastElement("prefixExpression"); if (pe != null) pe.addAttribute("op","-");}
        |       BNOT {Element pe = #unaryOperator.findLastElement("prefixExpression"); if (pe != null) pe.addAttribute("op","~");}
        |       LNOT {Element pe = #unaryOperator.findLastElement("prefixExpression"); if (pe != null) pe.addAttribute("op","!");}
        |       LAND {Element pe = #unaryOperator.findLastElement("prefixExpression"); if (pe != null) pe.addAttribute("op","&&");}
        |       "__real" {Element pe = #unaryOperator.findLastElement("prefixExpression"); if (pe != null) pe.addAttribute("op","__real");}
        |       "__imag" {Element pe = #unaryOperator.findLastElement("prefixExpression"); if (pe != null) pe.addAttribute("op","__imag");}
        ;

// TODO - spatne vyhodnocene pozice (kazda cast ma pozici celeho vyrazu)
postfixExpr :#( NPostfixExpr
                    pe:primaryExpr
                    	{
                    		#postfixExpr.pushElement(#pe.popElement());
                    		// postfixExpr ma jeden element, ktery se bude menit s kazdym dalsim postfixovym operatorem
                    		// (na konci pravidla je "+" !)
                    	}
                    ( PTR ptrId:ID      // pe->ptrID
                        {
				            Element ptrElement = xmlFactory.createElement("arrowExpression");
							addElementPositions(ptrElement, #postfixExpr);				            
				            ptrElement.add(#postfixExpr.popElement());
				            Element memberElement = xmlFactory.createElement("member");
							addElementPositions(memberElement, #postfixExpr);
				            memberElement.setText(#ptrId.getText());
				            ptrElement.add(memberElement);
				            #postfixExpr.pushElement(ptrElement);
                        }                                                            
                    | DOT dotId:ID    // pe->dotID
                        {
				            Element dotElement = xmlFactory.createElement("dotExpression");
							addElementPositions(dotElement, #postfixExpr);				            
				            dotElement.add(#postfixExpr.popElement());
				            Element memberElement = xmlFactory.createElement("member");
							addElementPositions(memberElement, #postfixExpr);				            
				            memberElement.setText(#dotId.getText());
				            dotElement.add(memberElement);
				            #postfixExpr.pushElement(dotElement);
                        }                                        
                    | #( NFunctionCallArgs 
                    		{	// TODO nemit jako nazev funkce jenom ID
								Element functionCallElement = xmlFactory.createElement("functionCall"); 
								addElementPositions(functionCallElement, #postfixExpr);				            
				             	Element idElement = xmlFactory.createElement("id"); 
								addElementPositions(idElement, #postfixExpr);				            
             					idElement.setText(pe.getText());
             					#postfixExpr.popElement(); // vymazeme postfixExpr, protoze mame jenom id!
             					functionCallElement.add(idElement);
             					#postfixExpr.pushElement(functionCallElement);
                    		}
                    		(argExprList)? //argExprList sam navaze svoje elementy na functionCallElement
                    		
                    		RPAREN 
                    		
                    
                       )
                    | LBRACKET expr RBRACKET // TODO?
                    | INC 
                        {
				            Element postfixElement = xmlFactory.createElement("postfixExpression");
				            postfixElement.addAttribute("op", "++");
							addElementPositions(postfixElement, #postfixExpr);				            
				            postfixElement.add(#postfixExpr.popElement());
				            #postfixExpr.pushElement(postfixElement);
                        }                    
                    | DEC
                        {
				            Element postfixElement = xmlFactory.createElement("postfixExpression");
							addElementPositions(postfixElement, #postfixExpr);				            
				            postfixElement.addAttribute("op", "--");
				            postfixElement.add(#postfixExpr.popElement());
				            #postfixExpr.pushElement(postfixElement);
                        }                    
                    )+
                )
        ;

primaryExpr: id:ID 
             {
             	Element idElement = xmlFactory.createElement("id");
				addElementPositions(idElement, #primaryExpr);
             	idElement.setText(id.getText());
             	#primaryExpr.pushElement(idElement);
             }
        |    num:Number 
             {
             	Element numberElement;
				try {
					Integer intNumber = Integer.parseInt(#num.getText()); //prevod na integer
					numberElement = xmlFactory.createElement("intConst"); 
					addElementPositions(numberElement, #primaryExpr);					
					numberElement.setText(intNumber.toString());
				} catch (NumberFormatException intException) {
					try {
						Double doubleNumber = Double.parseDouble(#num.getText());
						numberElement = xmlFactory.createElement("realConst"); 
						addElementPositions(numberElement, #primaryExpr);					
						numberElement.setText(doubleNumber.toString());
					} catch (NumberFormatException doubleException) {
						//neni to cislo. Muze byt ale zadane neobvyklou notaci :-(((
						//alespon se vypise
						numberElement = xmlFactory.createElement("realConst");
						addElementPositions(numberElement, #primaryExpr);					
						numberElement.setText(#num.getText());
					}
				}
             	#primaryExpr.pushElement(numberElement);
             }
        |       ch:charConst //to stejne co pro string
             {
             	Element charElement = xmlFactory.createElement("stringConst"); 
				addElementPositions(charElement, #primaryExpr);					
             	charElement.setText(#ch.getText());
             	#primaryExpr.pushElement(charElement);
             }
        |       str:stringConst
             {
             	Element stringElement = xmlFactory.createElement("stringConst"); 
				addElementPositions(stringElement, #primaryExpr);					
             	stringElement.setText(#str.getText());
             	#primaryExpr.pushElement(stringElement);
             }
// JTC:
// ID should catch the enumerator
// leaving it in gives ambiguous err
//      | enumerator

        |       #( NExpressionGroup e:expr {#primaryExpr.pushElement(#e.popElement());}) // TODO copy whole stack? OR NOT?
        ;

argExprList :
		( 
			e:expr { Element fcElement = #argExprList.findLastElement("functionCall");
				     if (fcElement != null) fcElement.add(#e.popElement()); 
				   }
		)+
        ;

protected charConst :ch:CharLiteral
        ;

protected stringConst 
			:#(NStringSeq (sl:StringLiteral) +)
        ;

protected intConst :IntOctalConst
        |       LongOctalConst
        |       UnsignedOctalConst
        |       IntIntConst
        |       LongIntConst
        |       UnsignedIntConst
        |       IntHexConst
        |       LongHexConst
        |       UnsignedHexConst
        ;

protected floatConst :FloatDoubleConst
        |       DoubleDoubleConst
        |       LongDoubleConst
        ;



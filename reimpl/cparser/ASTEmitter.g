/* vim: cin
 *
 * tree walker
 *
 * Copyright (c) 2008 Jiri Slaby <jirislaby@gmail.com>
 *
 * Licensed under GPLv2.
 */

tree grammar ASTEmitter;
options {
	tokenVocab=GNUCa;
	language=C;
	ASTLabelType = pANTLR3_BASE_TREE;
}

scope Symbols {
	pANTLR3_LIST variables;
	pANTLR3_LIST variablesOld;
}

@header {
#include "common.h"

#define LIST_SIZE	20
}

@members {
#if 0
	protected Document xmlDocument = DocumentHelper.createDocument();
	protected DocumentFactory xmlFactory = DocumentFactory.getInstance();
	private int uniqCnt;
	private boolean symbolsEnabled = true;
	private boolean isFunParam = false;
	private List<String> params = new LinkedList<String>();
	private List<String> paramsOld = new LinkedList<String>();

	/* configuration */
	final private Boolean normalizeTypes = false;
	final private Boolean uniqueVariables = true;
	final private Boolean uniqueVariablesDebug = false;

	private Element newElement(String text) {
		return xmlFactory.createElement(text);
	}
	private Element newElement(String text, StanseTree start) {
		return newElement(text).
			addAttribute("bl", Integer.toString(start.getLine())).
			addAttribute("bc", Integer.toString(start.getCharPositionInLine()));
	}
	private Element newListElement(List<Element> els, String text) {
		Element e = newElement(text);
		els.add(e);
		return e;
	}
	private Element newListElement(List<Element> els, String text,
			StanseTree start) {
		Element e = newElement(text, start);
		els.add(e);
		return e;
	}
	private Element newEmptyStatement(CommonTree ct) {
		Element e = xmlFactory.createElement("emptyStatement");
		e.addAttribute("bl", Integer.toString(ct.getLine()));
		e.addAttribute("bc", Integer.toString(
					ct.getCharPositionInLine()));
		return e;
	}
	private void setAttributes(StanseTree start, Element e) {
		e.addAttribute("bl", Integer.toString(start.getLine())).
		addAttribute("bc", Integer.toString(start.getCharPositionInLine()));
		start.setElement(e);
	}
	private Element addElementBin(Element dest, String name, Element e1, Element e2) {
		Element e = dest.addElement(name);
		e.add(e1);
		e.add(e2);
		return e;
	}
	private Element newElementBin(String name, Element e1, Element e2) {
		Element e = newElement(name);
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
				tss = new LinkedList<Element>();
				for (String baseType: rule[1].split(" "))
					newListElement(tss, "typeSpecifier").addElement("baseType").addText(baseType);
				break;
			}
		return tss;
	}

	private void clearFunParams() {
		params.clear();
		paramsOld.clear();
	}
	private void processFunParams() {
		$Symbols::variables.addAll(params);
		$Symbols::variablesOld.addAll(paramsOld);
	}

	private String renameVariable(String old) {
		if (!uniqueVariables)
			return old;
		if ($Symbols.size() == 1 && !isFunParam) { /* forward decls */
			int idx = $Symbols[0]::variablesOld.lastIndexOf(old);
			if (idx >= 0)
				return $Symbols[0]::variables.get(idx);
		}
		String new_ = old;

		while (true) {
			int a;
			for (a = $Symbols.size() - 1; a >= 0; a--)
				if ($Symbols[a]::variables.contains(new_))
					break;
			if (a < 0)
				break;
			new_ = old + "_" + uniqCnt++;
		}
		return new_;
	}

	private void pushSymbol(String old, String new_) {
		if (!uniqueVariables || !symbolsEnabled)
			return;
		if (isFunParam) {
			paramsOld.add(old);
			params.add(new_);
			return;
		}
		/* forward decl already pushed one */
		if ($Symbols.size() == 1 && $Symbols::variables.contains(new_))
			return;

		$Symbols::variablesOld.add(old);
		$Symbols::variables.add(new_);

		if (uniqueVariablesDebug) {
			for (int a = 0; a < $Symbols.size() - 1; a++) {
				System.out.print($Symbols[a]::variablesOld);
				System.out.print(" ");
			}
			System.out.println($Symbols::variablesOld);
			for (int a = 0; a < $Symbols.size() - 1; a++) {
				System.out.print($Symbols[a]::variables);
				System.out.print(" ");
			}
			System.out.println($Symbols::variables + " added: " +
					new_);
		}
	}

	private String findVariable(String old) {
		if (!uniqueVariables)
			return old;
		/* find topmost variable */
		for (int a = $Symbols.size() - 1; a >= 0; a--) {
			int idx = $Symbols[a]::variablesOld.lastIndexOf(old);
			if (idx >= 0)
				return $Symbols[a]::variables.get(idx);
		}
		return old; /* throw an exception? */
	}
#endif
	static my_jobject fill_attr(void *priv, my_jobject obj,
			pANTLR3_BASE_TREE start)
	{
		setLine(priv, obj, start->getLine(start));
		setColumn(priv, obj, start->getCharPositionInLine(start));
		return obj;
	}
	static void ANTLR3_CDECL free_symbols(SCOPE_TYPE(Symbols) symbols)
	{
		symbols->variables->free(symbols->variables);
		symbols->variablesOld->free(symbols->variablesOld);
	}
	static void addChildCond(void *priv, my_jobject parent, my_jobject child)
	{
		if (child)
			addChild(priv, parent, child);
	}
}

translationUnit[void *priv] returns [my_jobject d]
scope Symbols;
@init {
	$Symbols::variables = antlr3ListNew(LIST_SIZE);
	$Symbols::variablesOld = antlr3ListNew(LIST_SIZE);
	SCOPE_TOP(Symbols)->free = free_symbols;
	$d = newTranslationUnit($priv);
}
	: ^(TRANSLATION_UNIT (eds=externalDeclaration[$priv] { addChild($priv, $d, $eds.d); } )*)
	;

externalDeclaration[void *priv] returns [my_jobject d]
@init	{ $d = newExternalDeclaration($priv); }
@after	{
	fill_attr($priv, $d, $externalDeclaration.start);
//	clearFunParams();
}
	: functionDefinition[$priv]	{ addChild($priv, $d, $functionDefinition.d); }
	| declaration[$priv]		{ addChild($priv, $d, $declaration.d); }
	;

functionDefinition[void *priv] returns [my_jobject d]
@init {
	$d = newFunctionDefinition($priv);
//	functionDefinition.start.setElement(e);*/
}
@after	{ fill_attr($priv, $d, $functionDefinition.start); }
	: ^(FUNCTION_DEFINITION declarationSpecifiers[$priv] declarator[$priv] {
		addChild($priv, $d, $declarationSpecifiers.d);
		addChild($priv, $d, $declarator.d);
	} functionDefinitionBody[$priv, $d])
	;

/* we need a scope even here */
functionDefinitionBody[void *priv, my_jobject fd]
scope Symbols;
@init {
	$Symbols::variables = antlr3ListNew(LIST_SIZE);
	$Symbols::variablesOld = antlr3ListNew(LIST_SIZE);
	SCOPE_TOP(Symbols)->free = free_symbols;
//	processFunParams();
}
	: (d=declaration[$priv] {addChild($priv, $fd, $d.d);})* compoundStatement[$priv] {
		addChild($priv, $fd, $compoundStatement.d);
		pANTLR3_BASE_TREE t = $compoundStatement.start;
		t = t->getChild(t, 0);
		setEndLine($priv, $fd, t->getLine(t));
	}
	;

declaration[void *priv] returns [my_jobject d]
@init	{ $d = newDeclaration($priv); }
@after	{ fill_attr($priv, $d, $declaration.start); }
	: ^('typedef' declarationSpecifiers[$priv]? (id=initDeclarator[$priv] {/*ids.add(id.e);*/})*) {
/*		Element ds;
		if (declarationSpecifiers.e != null)
			e.add(ds = declarationSpecifiers.e);
		else
			ds = e.addElement("declarationSpecifiers");
		ds.addAttribute("storageClass", "typedef");
		addAllElements(e, ids);*/
	}
	| ^(DECLARATION ds=declarationSpecifiers[$priv] {addChild($priv, $d, $ds.d);}
			(id=initDeclarator[$priv] {addChild($priv, $d, $id.d);})*)
	;

declarationSpecifiers[void *priv] returns [my_jobject d]
@init	{
	$d = newDeclarationSpecifiers($priv);
/*	List<Element> tss = new LinkedList<Element>();
	List<String> tqs = new LinkedList<String>();
	List<String> scs = new LinkedList<String>();
	List<String> fss = new LinkedList<String>();*/
}
@after	{ fill_attr($priv, $d, $declarationSpecifiers.start); }
	: ^(DECLARATION_SPECIFIERS
		^(XTYPE_SPECIFIER (ts=typeSpecifier[$priv] {addChild($priv, $d, $ts.d);})*)
		^(XTYPE_QUALIFIER (tq=typeQualifier {/*tqs.add(tq.s);*/})*)
		^(XSTORAGE_CLASS (sc=storageClassSpecifier {/*scs.add(sc.s);*/} |
				fs=functionSpecifier {/*fss.add(fs.s);*/})*)) {
/*		addAllElements(e, typeNormalize(tss));
		for (String str: tqs)
			e.addAttribute(str, "1");
		for (String str: scs)
			e.addAttribute("storageClass", str);
		for (String str: fss)
			e.addAttribute("function", str);*/
	}
	;

declarator[void *priv] returns [my_jobject d]
@init   { $d = newDeclarator($priv); }
@after  { fill_attr($priv, $d, $declarator.start); }
	: ^(DECLARATOR pointer[$priv, $d]? directDeclarator[$priv, $d])
	;

directDeclarator[void *priv, my_jobject d]
	: IDENTIFIER {
		addChild($priv, $d, newIdentifier($priv, (char *)$IDENTIFIER.text->chars));
/*		String newName = renameVariable(IDENTIFIER.text);
		if (!newName.equals(IDENTIFIER.text))
			newListElement(els, "oldId").addText(IDENTIFIER.text);
		newListElement(els, "id").addText(newName);
		pushSymbol(IDENTIFIER.text, newName);*/
	}
	| declarator[$priv] { abort(); } /*{ newListElement(els, "declarator", declarator.start).
			add(declarator.e); }*/
	| directDeclarator1[$priv, $d] /* XXX is here the + needed? */
	;

directDeclarator1[void *priv, my_jobject d]
@init {
/*	List<String> tqs = new LinkedList<String>();
	List<Element> l = new LinkedList<Element>();*/
	my_jobject d1;
}
	: ^(ARRAY_DECLARATOR (dd=directDeclarator[$priv, $d]) {
		d1 = newArrayDecl($priv);
		addChild($priv, $d, d1);
	} ('static' {setAttr($priv, d1, "static", "1");} | asterisk='*' {setAttr($priv, d1, "asterisk", "1");})?
	  (tq=typeQualifier {setAttr($priv, d1, $tq.s, "1");})*
	  (e=expression[$priv] {addChild($priv, $d, $e.d);})?)
	| ^(FUNCTION_DECLARATOR (IDENTIFIER { /* we need to process the id before params */
		addChild($priv, $d, newIdentifier($priv, (char *)$IDENTIFIER.text->chars));
		d1 = newFunctionDecl($priv);
/*		String newName = renameVariable(IDENTIFIER.text);
		if (!newName.equals(IDENTIFIER.text))
			newListElement(els, "oldId").addText(IDENTIFIER.text);
		newListElement(els, "id").addText(newName);
		pushSymbol(IDENTIFIER.text, newName);*/
	}|declarator[$priv]) {
//		isFunParam = true;
		d1 = newFunctionDecl($priv);
		addChild($priv, $d, d1);
	} (pl=parameterTypeList[$priv, d1]|(i=identifier[$priv] {addChild($priv, d1, $i.d);})*)) {
//		isFunParam = false;
	}
	;

initDeclarator[void *priv] returns [my_jobject d]
@init	{ $d = newInitDeclarator($priv); }
@after	{ fill_attr($priv, $d, $initDeclarator.start); }
	: ^(INIT_DECLARATOR declarator[$priv] initializer?) {
		addChild($priv, $d, $declarator.d);
//		addChildCond($priv, $d, nitializer.d);
/*		initDeclarator.start.setElement(e);
		e.add(declarator.e);
		addElementCond(e, initializer.e);*/
	}
	;

initializer//returns [Element e]
@init {
//	e = newElement("initializer", initializer.start);
}
	: ^(INITIALIZER expression[NULL])	//{ e.add(expression.e); }
	| INITIALIZER /* just <initializer/> */
	| ^(INITIALIZER initializerList)//{ addAllElements(e, initializerList.els); }
	;

initializerList//returns [List<Element> els]
@init {
//	els = new LinkedList<Element>();
}
	: ((d=designator {/*els.add(d.e);*/})* initializer {
//		els.add(initializer.e);
	})+
	;

designator//returns [Element e]
@init {
//	e = newElement("designator", designator.start);
}
	: ^(DESIGNATOR ^('...' e1=expression[NULL] e2=expression[NULL])) {
/*		Element range = e.addElement("expression").
			addElement("rangeExpression");
		range.add(e1.e);
		range.add(e2.e);*/
	}
	| ^(DESIGNATOR ^(BRACKET_DESIGNATOR expression[NULL])) //{ e.add(expression.e); }
	| ^(DESIGNATOR IDENTIFIER)	//{ e.addElement("id").addText(IDENTIFIER.text); }
	| IDENTIFIER			//{ e.addElement("id").addText(IDENTIFIER.text); }
	;

compoundStatement[void *priv] returns [my_jobject d]
scope Symbols;
@init	{
	$Symbols::variables = antlr3ListNew(LIST_SIZE);
	$Symbols::variablesOld = antlr3ListNew(LIST_SIZE);
	SCOPE_TOP(Symbols)->free = free_symbols;
	$d = newCompoundStatement($priv);
}
@after  { fill_attr($priv, $d, $compoundStatement.start); }
	: ^(COMPOUND_STATEMENT CS_END (dc=declaration[$priv] {addChild($priv, $d, $dc.d);} |
				       fd=functionDefinition[$priv] {addChild($priv, $d, $fd.d);} |
				       st=statement[$priv]{addChild($priv, $d, $st.d);})*) {
/*
		if (els.size() == 0)
			e.add(newEmptyStatement(compoundStatement.start));
		compoundStatement.start.setElement(e);*/
	}
	;

parameterTypeList[void *priv, my_jobject d]
	: (p=parameterDeclaration[$priv] {addChild($priv, $d, $p.d);})+
	  (VARARGS {
		my_jobject param = newParameter($priv);
		setAttr($priv, param, "varArgs", "1");
		addChild($priv, $d, param);
	})?
	;

parameterDeclaration[void *priv] returns [my_jobject d]
@init   { $d = newParameter($priv); }
@after  { fill_attr($priv, $d, $parameterDeclaration.start); }
	: ^(PARAMETER ds=declarationSpecifiers[$priv] {addChild($priv, $d, $ds.d);}
		      (de=declarator[$priv] {addChild($priv, $d, $de.d);})?
		      (ad=abstractDeclarator[$priv] {addChild($priv, $d, $ad.d);})?)
	;

abstractDeclarator[void *priv] returns [my_jobject d]
@init   { $d = newAbstractDeclarator($priv); }
@after  { fill_attr($priv, $d, $abstractDeclarator.start); }  
	: ^(ABSTRACT_DECLARATOR pointer[$priv, $d] directAbstractDeclarator[$priv, $d]?)
	| ^(ABSTRACT_DECLARATOR directAbstractDeclarator[$priv, $d])
	;

directAbstractDeclarator[void *priv, my_jobject d]
	: ad=abstractDeclarator[$priv] {addChild($priv, $d, $ad.d);}
	  (a=arrayOrFunctionDeclarator[$priv] {addChild($priv, $d, $a.d);})*
	| (a=arrayOrFunctionDeclarator[$priv] {addChild($priv, $d, $a.d);})+
	;

arrayOrFunctionDeclarator[void *priv] returns [my_jobject d]
@after  { fill_attr($priv, $d, $arrayOrFunctionDeclarator.start); }  
	: ^(ARRAY_DECLARATOR { $d = newArrayDecl($priv); }
			((e=expression[$priv] {addChild($priv, $d, $e.d);})? |
			 '*' { setAttr($priv, $d, "asterisk", "1"); }))
	| ^(FUNCTION_DECLARATOR { $d = newFunctionDecl($priv); } parameterTypeList[$priv, $d]?)
	;

identifier[void *priv] returns [my_jobject d]
	: ^(PARAMETER IDENTIFIER)	{
		$d = fill_attr($priv, $d, newIdentifier($priv, (char *)$IDENTIFIER.text->chars));
/*		String newName = renameVariable(IDENTIFIER.text);
		e = newElement("id");
		if (!newName.equals(IDENTIFIER.text))
			e.addAttribute("oldId", IDENTIFIER.text);
		e.addText(newName);
		pushSymbol(IDENTIFIER.text, newName);*/
	}
	;

/* TYPES */

typeName[void *priv] returns [my_jobject d]
@init   { $d = newTypeName($priv); }
@after  { fill_attr($priv, $d, $typeName.start); }
	: ^(TYPE_NAME (s=specifier[$priv] {addChild($priv, $d, $s.d);} |
		       q=qualifier {setAttr($priv, $d, $q.q, "1");})+
		      (ad=abstractDeclarator[$priv] {addChild($priv, $d, $ad.d);})?)
//		for (Element el: typeNormalize(tss))
	;

specifier[void *priv] returns [my_jobject d]
	: ^(XTYPE_SPECIFIER typeSpecifier[$priv])	{ $d = $typeSpecifier.d; }
	;

qualifier returns [char *q]
	: ^(XTYPE_QUALIFIER typeQualifier)	{ $q = $typeQualifier.s; }
	;

typeQualifier returns [char *s]
	: 'const'	{ $s = "const"; }
	| 'restrict'	{ $s = "restrict"; }
	| 'volatile'	{ $s = "volatile"; }
	;

typeSpecifier[void *priv] returns [my_jobject d]
@init	{ $d = newTypeSpecifier($priv); }
@after  { fill_attr($priv, $d, $typeSpecifier.start); }
	: ^(BASETYPE 'void')	{ addChild($priv, $d, newBaseType($priv, "void")); }
	| ^(BASETYPE 'char')	{ addChild($priv, $d, newBaseType($priv, "char")); }
	| ^(BASETYPE 'short')	{ addChild($priv, $d, newBaseType($priv, "short")); }
	| ^(BASETYPE 'int')	{ addChild($priv, $d, newBaseType($priv, "int")); }
	| ^(BASETYPE 'long')	{ addChild($priv, $d, newBaseType($priv, "long")); }
	| ^(BASETYPE 'float')	{ addChild($priv, $d, newBaseType($priv, "float")); }
	| ^(BASETYPE 'double')	{ addChild($priv, $d, newBaseType($priv, "double")); }
	| ^(BASETYPE SIGNED)	{ addChild($priv, $d, newBaseType($priv, "signed")); }
	| ^(BASETYPE 'unsigned'){ addChild($priv, $d, newBaseType($priv, "unsigned")); }
	| ^(BASETYPE '_Bool')	{ addChild($priv, $d, newBaseType($priv, "_Bool")); }
	| ^(BASETYPE COMPLEX)	{ addChild($priv, $d, newBaseType($priv, "_Complex")); }
	| ^(BASETYPE XID)
	| ^(BASETYPE '_Imaginary')	{ addChild($priv, $d, newBaseType($priv, "_Imaginary")); }
	| structOrUnionSpecifier[$priv] { addChild($priv, $d, $structOrUnionSpecifier.d); }
	| enumSpecifier[$priv]		{ addChild($priv, $d, $enumSpecifier.d); }
	| typedefName		//{ e.addElement("typedef").addElement("id").addText(typedefName.s); }
	| typeofSpecifier
	;

structOrUnionSpecifier[void *priv] returns [my_jobject d]
@init {
/*	List<Element> sds = new LinkedList<Element>();
	symbolsEnabled = false;*/
}
	: ^(sou=structOrUnion {
		if ($sou.un)
			$d = newUnion($priv);
		else
			$d = newStruct($priv);
	} ^(XID (IDENTIFIER {setAttr($priv, $d, "id", (char *)$IDENTIFIER.text->chars);})?)
	(sd=structDeclaration[$priv] {addChild($priv, $d, $sd.d);})*) {
//		addAllElements(e, sds);
//		symbolsEnabled = true;
	}
	;

structOrUnion returns [char un]
	: 'struct'	{ $un = 0; }
	| 'union'	{ $un = 1; }
	;

structDeclaration[void *priv] returns [my_jobject d]
@init   { $d = newStructDeclaration($priv); }
@after  { fill_attr($priv, $d, $structDeclaration.start); }
	: ^(STRUCT_DECLARATION (s=specifier[$priv] {addChild($priv, $d, $s.d);} |
				q=qualifier {setAttr($priv, $d, $q.q, "1");})+
			       (sd=structDeclarator[$priv] {addChild($priv, $d, $sd.d);})*) {
//		for (Element el: typeNormalize(tss))
	}
	;

structDeclarator[void *priv] returns [my_jobject d]
@init   { $d = newStructDeclarator($priv); }
@after  { fill_attr($priv, $d, $structDeclarator.start); }
	: ^(STRUCT_DECLARATOR (de=declarator[$priv] {addChild($priv, $d, $de.d);})?
			      (ex=expression[$priv] {addChild($priv, $d, $ex.d);})?)
	;

enumSpecifier[void *priv] returns [my_jobject d]
@init   { $d = newEnum($priv); }
@after  { fill_attr($priv, $d, $enumSpecifier.start); }
	: ^('enum' (^(XID IDENTIFIER {setAttr($priv, $d, "id", (char *)$IDENTIFIER.text->chars);}))?
		   (en=enumerator[$priv] {addChild($priv, $d, $en.d);})*)
	;

enumerator[void *priv] returns [my_jobject d]
@after  { fill_attr($priv, $d, $enumerator.start); }
	: ^(ENUMERATOR IDENTIFIER {
		$d = newEnumerator($priv, (char *)$IDENTIFIER.text->chars);
	} (e=expression[$priv] {addChild($priv, $d, $e.d);})?)
	;

typedefName returns [pANTLR3_STRING s]
	: IDENTIFIER	{ $s = $IDENTIFIER.text; }
	;

typeofSpecifier
	: ^(TYPEOF expression[NULL])
	| ^(TYPEOF typeName[NULL])
	;

storageClassSpecifier returns [const char *s]
	: 'extern'	{ $s = "extern"; }
	| 'static'	{ $s = "static"; }
	| 'auto'	{ $s = "auto"; }
	| 'register'	{ $s = "register"; }
	| '__thread'	{ $s = "__thread"; }
	;

functionSpecifier returns [const char *s]
	: 'inline'	{ $s = "inline"; }
	;

pointer[void *priv, my_jobject d]
@init {
/*	List<String> tqs = new LinkedList<String>();
	els = new LinkedList<Element>();*/
}
	: ^(POINTER (tq=typeQualifier {/*tqs.add(tq);*/})* ptr=pointer[$priv, $d]?) {
/*		Element e = newElement("pointer");
		for (String t: tqs)
			e.addAttribute(t, "1");
		els.add(e);
		if (ptr.els != null)
			els.addAll(ptr.els);*/
	}
	;

/* TYPES END */

/* STATEMENTS */

statement[void *priv] returns [my_jobject d]
@after  { fill_attr($priv, $d, $statement.start); }
	: labeledStatement[$priv]	{ $d=$labeledStatement.d; }
	| compoundStatement[$priv]	{ $d=$compoundStatement.d; }
	| expressionStatement[$priv]	//{ e=newElement("expressionStatement");e.add(expressionStatement.e != null ? expressionStatement.e : newEmptyStatement(expressionStatement.start)); }
	| selectionStatement[$priv]	{ $d=$selectionStatement.d; }
	| iterationStatement[$priv]	{ $d=$iterationStatement.d; }
	| jumpStatement[$priv]		{ $d=$jumpStatement.d; }
	| asmStatement[$priv]		{ $d=$asmStatement.d; }
	;

labeledStatement[void *priv] returns [my_jobject d]
	: ^(LABEL IDENTIFIER statement[$priv]) {
/*		e = newElement("labelStatement", labeledStatement.start);
		e.add(statement.e);
		e.addAttribute("id", IDENTIFIER.text);*/
	}
	| ^('case' expression[$priv] statement[$priv]) {
/*		e = newElement("caseLabelStatement", labeledStatement.start);
		e.add(expression.e);
		e.add(statement.e);*/
	}
	| ^('default' statement[$priv]) {
/*		e = newElement("defaultLabelStatement",
				labeledStatement.start);
		e.add(statement.e);*/
	}
	;

expressionStatement[void *priv] returns [my_jobject d]
	: ^(EXPRESSION_STATEMENT expression[$priv]?) { $d = $expression.d; }
	;

selectionStatement[void *priv] returns [my_jobject d]
	: ^('if' expression[$priv] s1=statement[$priv] s2=statement[$priv]?) {
/*		e = newElement(s2 == null ? "ifStatement" : "ifElseStatement",
				selectionStatement.start);
		e.add(expression.e);
		e.add(s1.e);
		addElementCond(e, s2.e);*/
	}
	| ^('switch' expression[$priv] statement[$priv]) {
/*		e = newElement("switchStatement", selectionStatement.start);
		e.add(expression.e);
		e.add(statement.e);*/
	}
	;

iterationStatement[void *priv] returns [my_jobject d]
	: ^('while' expression[$priv] statement[$priv]) {
/*		e = newElement("whileStatement", iterationStatement.start);
		e.add(expression.e);
		e.add(statement.e);*/
	}
	| ^('do' statement[$priv] expression[$priv]) {
/*		e = newElement("doStatement", iterationStatement.start);
		e.add(statement.e);
		e.add(expression.e);*/
	}
	| ^('for' declaration[$priv]?
			(^(E1 e1=expression[$priv]))?
			 ^(E2 e2=expression[$priv]?)
			 ^(E3 e3=expression[$priv]?)
			 statement[$priv]) {
/*		e = newElement("forStatement", iterationStatement.start);
		if (declaration.e != null)
			e.add(declaration.e);
		else if (e1 != null)
			e.add(e1.e);
		else
			e.addElement("expression");
		if (e2 != null)
			e.add(e2.e);
		else
			e.addElement("expression");
		if (e3 != null)
			e.add(e3.e);
		else
			e.addElement("expression");
		e.add(statement.e);*/
	}
	;

jumpStatement[void *priv] returns [my_jobject d]
	: ^('goto' IDENTIFIER) {
/*		e = newElement("gotoStatement", jumpStatement.start);
		e.addElement("expression").addElement("id").addText(IDENTIFIER.text);*/
	}
	| ^('goto' XU expression[$priv]) {
/*		e = newElement("gotoStatement", jumpStatement.start);
		e.addElement("expression").addElement("derefExpression").add(expression.e);*/
	}
	| 'continue'	/*{ e = newElement("continueStatement",
			jumpStatement.start); }*/
	| 'break'	/*{ e = newElement("breakStatement",
			jumpStatement.start); }*/
	| ^('return' expression[$priv]?) {
/*		e = newElement("returnStatement", jumpStatement.start);
		addElementCond(e, expression.e);*/
	}
	;

asmStatement[void *priv] returns [my_jobject d]
	: ASM		//{ e = newElement("gnuAssembler", asmStatement.start); }
	;

/* STATEMENTS END */

/* EXPRESSIONS */

expression[void *priv] returns [my_jobject d]
	: otherExpression[$priv]	{ $d=$otherExpression.d; }
	| binaryExpression[$priv]	{ $d=$binaryExpression.d; }
	| primaryExpression	//{ e=primaryExpression.e; }
	;

otherExpression[void *priv] returns [my_jobject d]
@after  { fill_attr($priv, $d, $otherExpression.start); }
/*	List<Element> exs = new LinkedList<Element>();
	Element exp;*/
	: ^(ASSIGNMENT_EXPRESSION assignmentOperator e1=expression[$priv] e2=expression[$priv]) {
		char my_op[5], *op = $assignmentOperator.text->chars;
		$d = newAssignExpression($priv);
		addChild($priv, $d, $e1.d);
		addChild($priv, $d, $e2.d);
		if (op[0] == "=" && op[1] == 0) {
/*			strcpy(my_op, op);
			my_op
			setAttr($priv, $d, "op", my_op);*/
		}
/*		String op = assignmentOperator.text;
		e = newElementBin("assignExpression", e1.e, e2.e);
		if (!op.equals("="))
			e.addAttribute("op", op.substring(0, op.length() - 1));*/
	}
	| ^(CONDITIONAL_EXPRESSION ^(E1 e1=expression[$priv]) ^(E2 e2=expression[$priv]?) ^(E3 e3=expression[$priv])) {
/*		e=newElement("conditionalExpression");
		e.add(e1.e);
		addElementCond(e, e2.e);
		e.add(e3.e);*/
	}
	| ^(CAST_EXPRESSION tn=typeName[$priv] e1=expression[$priv]) //{ e=newElementBin("castExpression", tn.e, e1.e); }
	| ^(ARRAY_ACCESS e1=expression[$priv] e2=expression[$priv]) //{ e=newElementBin("arrayAccess", e1.e, e2.e); }
	| ^(FUNCTION_CALL e1=expression[$priv] (e2=expression[$priv] {/*exs.add(e2.e);*/})*) {
/*		e=newElement("functionCall");
		e.add(e1.e);
		for (Element el: exs)
			e.add(el);*/
	}
	| ^(COMPOUND_LITERAL tn=typeName[$priv] initializerList?) {
/*		Element me = newElement("initializer");
		e=newElementBin("compoundLiteral", tn.e, me);
		addAllElements(me, initializerList.els);*/
	}
	| ^(',' e1=expression[$priv] e2=expression[$priv])	//{ e=newElementBin("commaExpression", e1.e, e2.e); }
	| ^('++' e1=expression[$priv])		//{ e=newElement("prefixExpression");e.addAttribute("op", "++").add(e1.e); }
	| ^('--' e1=expression[$priv])		//{ e=newElement("prefixExpression");e.addAttribute("op", "--").add(e1.e); }
	| ^(unaryOp e1=expression[$priv])	//{ e=newElement("prefixExpression");e.addAttribute("op", unaryOp.op).add(e1.e); }
	| ^('sizeof' (e1=expression[$priv]|tn=typeName[$priv]))	//{ e=newElement("sizeofExpression"); e.add(e1 != null ? e1.e : tn.e); }
	| ^('__alignof__' (e1=expression[$priv]|tn=typeName[$priv]))	//{ e=newElement("allignofExpression"); e.add(e1 != null ? e1.e : tn.e); }
	| ^('.' e1=expression[$priv] IDENTIFIER)	{
/*		e=newElement("dotExpression");
		e.add(e1.e);
		e.addElement("member").addText(IDENTIFIER.text);*/
	}
	| ^('->' e1=expression[$priv] IDENTIFIER) {
/*		e=newElement("arrowExpression");
		e.add(e1.e);
		e.addElement("member").addText(IDENTIFIER.text);*/
	}
	| ^(AU e1=expression[$priv])	//{ e=newElement("addrExpression"); e.add(e1.e); }
	| ^(XU e1=expression[$priv])	//{ e=newElement("derefExpression"); e.add(e1.e); }
	| ^(PP e1=expression[$priv])	//{ e=newElement("postfixExpression"); e.addAttribute("op", "++").add(e1.e); }
	| ^(MM e1=expression[$priv])	//{ e=newElement("postfixExpression"); e.addAttribute("op", "--").add(e1.e); }
	;

binaryExpression[void *priv] returns [my_jobject d]
@after {
	$d = newBinaryExpression($priv, (char *)op->getText(op)->chars);
	fill_attr($priv, $d, $binaryExpression.start);
	addChild($priv, $d, $e1.d);
	addChild($priv, $d, $e2.d);
}
	: ^(op='||' e1=expression[$priv] e2=expression[$priv])
	| ^(op='&&' e1=expression[$priv] e2=expression[$priv])
	| ^(op='|' e1=expression[$priv] e2=expression[$priv])
	| ^(op='&' e1=expression[$priv] e2=expression[$priv])
	| ^(op='^' e1=expression[$priv] e2=expression[$priv])
	| ^(op='==' e1=expression[$priv] e2=expression[$priv])
	| ^(op='!=' e1=expression[$priv] e2=expression[$priv])
	| ^(op='<=' e1=expression[$priv] e2=expression[$priv])
	| ^(op='<' e1=expression[$priv] e2=expression[$priv])
	| ^(op='>=' e1=expression[$priv] e2=expression[$priv])
	| ^(op='>' e1=expression[$priv] e2=expression[$priv])
	| ^(op='>>' e1=expression[$priv] e2=expression[$priv])
	| ^(op='<<' e1=expression[$priv] e2=expression[$priv])
	| ^(op='+' e1=expression[$priv] e2=expression[$priv])
	| ^(op='-' e1=expression[$priv] e2=expression[$priv])
	| ^(op='*' e1=expression[$priv] e2=expression[$priv])
	| ^(op='/' e1=expression[$priv] e2=expression[$priv])
	| ^(op='%' e1=expression[$priv] e2=expression[$priv])
	;

primaryExpression//returns [Element e]
	: IDENTIFIER		//{ e = newElement("id"); e.addText(findVariable(IDENTIFIER.text)); }
	| constant		//{ e = constant.e; }
	| sTRING_LITERAL	//{ e = newElement("stringConst", sTRING_LITERAL.start); e.addText(sTRING_LITERAL.text); }
	| compoundStatement[NULL]	//{ e = compoundStatement.e; }
	| ^(BUILTIN_OFFSETOF typeName[NULL] offsetofMemberDesignator) {
/*		e = newElement("offsetofExpression", primaryExpression.start);
		e.add(typeName.e);
		e.add(offsetofMemberDesignator.e);*/
	}
	;

sTRING_LITERAL//returns [String text]
@init {
//	List<String> sls = new LinkedList<String>();
}
	: ^(STR_LITERAL (STRING_LITERAL {/*sls.add(STRING_LITERAL.text);*/})+) {
#if 0
		StringBuilder sb = new StringBuilder();
		for (String str: sls) /* crop the quotation */
			sb.append(str.substring(1, str.length() - 1));
		text = sb.toString();
#endif
	}
	;

constant//returns [Element e]
	:	ICONSTANT //{ e = newElement("intConst"); e.addText(ICONSTANT.text); }
	|	RCONSTANT //{ e = newElement("realConst"); e.addText(RCONSTANT.text); }
	;

offsetofMemberDesignator//returns [Element e]
@init {
//	Element e1;
}
	: id1=IDENTIFIER {
/*		e = newElement("expression");
		e.addElement("id").addText(id1.getText());*/
	}	( ('.' id2=IDENTIFIER {
/*			e1 = newElement("dotExpression");
			e1.add(e);
			e1.addElement("member").addText(id2.getText());
			e = newElement("expression");
			e.add(e1);*/
		})
		| ('[' expression[NULL] ']' {
/*			e1 = newElement("arrayAccess");
			e1.add(e);
			e1.add(expression.e);
			e = newElement("expression");
			e.add(e1);*/
		})
		)*
	;

assignmentOperator
	: '='
	| '*='
	| '/='
	| '%='
	| '+='
	| '-='
	| '<<='
	| '>>='
	| '&='
	| '^='
	| '|='
	;

unaryOp returns [char *op]
	: PU		{ $op = "+"; }
	| MU		{ $op = "-"; }
	| '~'		{ $op = "~"; }
	| '!'		{ $op = "!"; }
	| LABREF	{ $op = "&&"; }
	| '__real__'	{ $op = "__real"; }
	| '__imag__'	{ $op = "__imag"; }
	;

/* EXPRESSIONS END */

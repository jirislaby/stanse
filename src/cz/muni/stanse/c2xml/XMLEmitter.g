/* vim: cin
 *
 * tree walker
 *
 * Copyright (c) 2008 Jiri Slaby <jirislaby@gmail.com>
 *
 * Licensed under GPLv2.
 */

tree grammar XMLEmitter;
options {
	tokenVocab=GNUCa;
	ASTLabelType=CommonTree;
}

scope Symbols {
	Stack<String> variables;
	Stack<String> variablesOld;
}

@header {
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
}
@members {
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

		while ($Symbols::variables.search(new_) > 0)
			new_ = old + "_" + uniqCnt++;
		return new_;
	}

	private void pushSymbol(String old, String new_, int type) {
		if (!uniqueVariables || !symbolsEnabled)
			return;
		$Symbols::variablesOld.push(old);
		$Symbols::variables.push(new_);
		if (uniqueVariablesDebug && type != 0) {
			System.out.println($Symbols::variablesOld);
			System.out.println($Symbols::variables + " added" + type + ": " + new_);
		}
	}

	private void popUntil(String s) {
		if (!uniqueVariables)
			return;
		$Symbols::variablesOld.pop();
		while (!$Symbols::variables.pop().equals(s))
			$Symbols::variablesOld.pop();
	}

	private String findVariable(String old) {
		if (!uniqueVariables)
			return old;
		/* find topmost variable (index is bottom based) */
		int idx = $Symbols::variablesOld.lastIndexOf(old);
		if (idx < 0)
			return old; /* throw an exception? */
		return $Symbols::variables.elementAt(idx);
	}
}

translationUnit returns [Document d]
scope Symbols;
@init {
	Element root = xmlDocument.addElement("translationUnit");
	$Symbols::variables = new Stack<String>();
	$Symbols::variablesOld = new Stack<String>();
}
	: ^(TRANSLATION_UNIT (eds=externalDeclaration {root.add(eds);} )*) {
		xmlDocument.setRootElement(root);
		outputXML();
		$d = xmlDocument;
	}
	;

externalDeclaration returns [Element e]
@init {
	$e = newElement("externalDeclaration");
}
	: functionDefinition	{ $e.add($functionDefinition.e); }
	| declaration		{ $e.add($declaration.e); }
	;

functionDefinition returns [Element e]
@init {
	List<Element> ds = new ArrayList<Element>();
	$e = newElement("functionDefinition");
}
	: ^(FUNCTION_DEFINITION declarationSpecifiers declarator (d=declaration {ds.add(d);})* compoundStatement) {
		$e.add($declarationSpecifiers.e);
		$e.add($declarator.e);
		addAllElements($e, ds);
		$e.add($compoundStatement.e);
		popUntil("-");
	}
	;

declaration returns [Element e]
@init {
	List<Element> ids = new ArrayList<Element>();
	$e = newElement("declaration");
}
	: ^('typedef' declarationSpecifiers? (id=initDeclarator {ids.add(id);})*) {
		Element ds;
		if ($declarationSpecifiers.e != null)
			$e.add(ds = $declarationSpecifiers.e);
		else
			ds = $e.addElement("declarationSpecifiers");
		ds.addAttribute("storageClass", "typedef");
		addAllElements($e, ids);
	}
	| ^(DECLARATION declarationSpecifiers (id=initDeclarator {ids.add(id);})*) {
		$e.add($declarationSpecifiers.e);
		addAllElements($e, ids);
	}
	;

declarationSpecifiers returns [Element e]
@init {
	List<Element> tss = new ArrayList<Element>();
	Set<String> tqs = new HashSet<String>();
	Set<String> scs = new HashSet<String>();
	Set<String> fss = new HashSet<String>();
}
	: ^(DECLARATION_SPECIFIERS ^(XTYPE_SPECIFIER (ts=typeSpecifier {tss.add(ts);})*) ^(XTYPE_QUALIFIER (tq=typeQualifier {tqs.add(tq);})*) ^(XSTORAGE_CLASS (sc=storageClassSpecifier {scs.add(sc);}|fs=functionSpecifier {fss.add(fs);})*)) {
		$e = newElement("declarationSpecifiers");
		addAllElements($e, typeNormalize(tss));
		for (String str: tqs)
			$e.addAttribute(str, "1");
		for (String str: scs)
			$e.addAttribute("storageClass", str);
		for (String str: fss)
			$e.addAttribute("function", str);
	}
	;

declarator returns [Element e]
	: ^(DECLARATOR pointer? directDeclarator) {
		$e = newElement("declarator");
		addAllElements($e, $pointer.els);
		addAllElements($e, $directDeclarator.els);
	}
	;

directDeclarator returns [List<Element> els]
@init {
	$els = new ArrayList<Element>();
}
	: IDENTIFIER {
		String newName = renameVariable($IDENTIFIER.text);
		if (!newName.equals($IDENTIFIER.text))
			newListElement($els, "oldId").addText($IDENTIFIER.text);
		newListElement($els, "id").addText(newName);
		pushSymbol($IDENTIFIER.text, newName, 1);
	}
	| declarator { newListElement($els, "declarator").add($declarator.e); }
	| directDeclarator1 { $els = $directDeclarator1.els; } /* XXX is here the + needed? */
	;

directDeclarator1 returns [List<Element> els]
@init {
	Set<String> tqs = new HashSet<String>();
	List<Element> l = new ArrayList<Element>();
	$els = new ArrayList<Element>();
}
	: ^(ARRAY_DECLARATOR (IDENTIFIER|dd=directDeclarator1) ('static' {tqs.add("static");}|asterisk='*')? (tq=typeQualifier {tqs.add(tq);})* expression?) {
		if ($IDENTIFIER != null) {
			String newName = renameVariable($IDENTIFIER.text);
			if (!newName.equals($IDENTIFIER.text))
				newListElement($els, "oldId").addText($IDENTIFIER.text);
			newListElement($els, "id").addText(newName);
			pushSymbol($IDENTIFIER.text, newName, 2);
		} else
			addAllElements(newListElement($els, "declarator"), $dd.els);
		Element e = newListElement($els, "arrayDecl");
		for (String t: tqs)
			e.addAttribute(t, "1");
		if (asterisk != null)
			e.addElement("asterisk");
		addElementCond(e, $expression.e);
	}
	| ^(FUNCTION_DECLARATOR (IDENTIFIER { /* we need to process the id before params */
				String newName = renameVariable($IDENTIFIER.text);
				if (!newName.equals($IDENTIFIER.text))
					newListElement($els, "oldId").addText($IDENTIFIER.text);
				newListElement($els, "id").addText(newName);
				pushSymbol($IDENTIFIER.text, newName, 3);
				pushSymbol("-", "-", 0); /* see functionDefinition */
			}|declarator) (pl=parameterTypeList|(i=identifier {l.add(i);})*)) {
		if ($IDENTIFIER == null)
			$els.add($declarator.e);
		Element e = newListElement($els, "functionDecl");
		addAllElements(e, pl != null ? pl : l);
	}
	;

initDeclarator returns [Element e]
	: ^(INIT_DECLARATOR declarator initializer?) {
		$e = newElement("initDeclarator");
		$e.add($declarator.e);
		addElementCond($e, $initializer.e);
	}
	;

initializer returns [Element e]
@init {
	$e = newElement("initializer");
}
	: ^(INITIALIZER expression)	{ $e.add($expression.e); }
	| INITIALIZER /* just <initializer/> */
	| ^(INITIALIZER initializerList){ addAllElements($e, $initializerList.els); }
	;

initializerList returns [List<Element> els]
@init {
	$els = new ArrayList<Element>();
}
	: ((d=designator {$els.add(d);})* initializer {
		$els.add($initializer.e);
	})+
	;

designator returns [Element e]
@init {
	$e = newElement("designator");
}
	: ^(DESIGNATOR ^(BRACKET_DESIGNATOR expression)) { $e.add($expression.e); }
	| ^(DESIGNATOR IDENTIFIER)	{ $e.addElement("id").addText($IDENTIFIER.text); }
	| IDENTIFIER			{ $e.addElement("id").addText($IDENTIFIER.text); }
	;

compoundStatement returns [Element e]
@init {
	List<Element> els = new ArrayList<Element>();
	pushSymbol(".", ".", 0);
}
	: ^(COMPOUND_STATEMENT (d=declaration {els.add(d);}|fd=functionDefinition{els.add(fd);}|st=statement{els.add(st);})*) {
		$e = newElement("compoundStatement");
		addAllElements($e, els);
		popUntil(".");
	}
	;

parameterTypeList returns [List<Element> els]
@init {
	$els = new ArrayList<Element>();
}
	: (p=parameterDeclaration {$els.add(p);})+ VARARGS? {
		if ($VARARGS != null) {
			Element va = newElement("parameter");
			va.addElement("varArgs");
			$els.add(va);
		}
	}
	;

parameterDeclaration returns [Element e]
	: ^(PARAMETER declarationSpecifiers declarator? abstractDeclarator?) {
		$e = newElement("parameter");
		$e.add($declarationSpecifiers.e);
		addElementCond($e, $declarator.e);
		addElementCond($e, $abstractDeclarator.e);
	}
	;

abstractDeclarator returns [Element e]
@init {
	$e = newElement("abstractDeclarator");
}
	: pointer directAbstractDeclarator? {
		addAllElements($e, $pointer.els);
		addAllElements($e, $directAbstractDeclarator.els);
	}
	| directAbstractDeclarator { addAllElements($e, $directAbstractDeclarator.els); }
	;

directAbstractDeclarator returns [List<Element> els]
@init {
	$els = new ArrayList<Element>();
}
	: ^(ARRAY_DECLARATOR abstractDeclarator? (expression?|ast='*')) {
		if ($abstractDeclarator.e != null)
			$els.add($abstractDeclarator.e);
		Element e = newListElement($els, "arrayDecl");
		if ($expression.e != null)
			e.add($expression.e);
		else if ($ast != null)
			e.addAttribute("asterisk", "1");
	}
	| ^(FUNCTION_DECLARATOR abstractDeclarator? parameterTypeList?) {
		if ($abstractDeclarator.e != null)
			$els.add($abstractDeclarator.e);
		Element e = newElement("functionDecl");
		addAllElements(e, $parameterTypeList.els);
	}
	;

identifier returns [Element e]
	: ^(PARAMETER IDENTIFIER)	{
		String newName = renameVariable($IDENTIFIER.text);
		$e = newElement("id");
		if (!newName.equals($IDENTIFIER.text))
			$e.addAttribute("oldId", $IDENTIFIER.text);
		$e.addText(newName);
		pushSymbol($IDENTIFIER.text, newName, 4);
	}
	;

/* TYPES */

typeName returns [Element e]
@init {
	List<specifierQualifier_return> sqs = new ArrayList<specifierQualifier_return>();
}
	: ^(TYPE_NAME (sq=specifierQualifier {sqs.add(sq);})+ abstractDeclarator?) {
		List <Element> tss = new ArrayList<Element>();
		$e = newElement("typeName");
		for (specifierQualifier_return sqr: sqs)
			if (sqr.qual != null)
				$e.addAttribute(sqr.qual, "1");
			else
				tss.add(sqr.spec);
		for (Element el: typeNormalize(tss))
			$e.add(el);
		addElementCond($e, $abstractDeclarator.e);
	}
	;

specifierQualifier returns [Element spec, String qual]
	: ^(XTYPE_SPECIFIER typeSpecifier)	{ $spec = $typeSpecifier.e; }
	| ^(XTYPE_QUALIFIER typeQualifier)	{ $qual = $typeQualifier.s; }
	;

typeQualifier returns [String s]
	: 'const'	{ $s = "const"; }
	| 'restrict'	{ $s = "restrict"; }
	| 'volatile'	{ $s = "volatile"; }
	;

typeSpecifier returns [Element e]
@init {
	$e = newElement("typeSpecifier");
}
	: ^(BASETYPE 'void')	{ $e.addElement("baseType").addText("void"); }
	| ^(BASETYPE 'char')	{ $e.addElement("baseType").addText("char"); }
	| ^(BASETYPE 'short')	{ $e.addElement("baseType").addText("short"); }
	| ^(BASETYPE 'int')	{ $e.addElement("baseType").addText("int"); }
	| ^(BASETYPE 'long')	{ $e.addElement("baseType").addText("long"); }
	| ^(BASETYPE 'float')	{ $e.addElement("baseType").addText("float"); }
	| ^(BASETYPE 'double')	{ $e.addElement("baseType").addText("double"); }
	| ^(BASETYPE SIGNED)	{ $e.addElement("baseType").addText("signed"); }
	| ^(BASETYPE 'unsigned'){ $e.addElement("baseType").addText("unsigned"); }
	| ^(BASETYPE '_Bool')	{ $e.addElement("baseType").addText("_Bool"); }
	| ^(BASETYPE COMPLEX)	{ $e.addElement("baseType").addText("_Complex"); }
	| ^(BASETYPE XID)
	| ^(BASETYPE '_Imaginary')	{ $e.addElement("baseType").addText("_Imaginary"); }
	| structOrUnionSpecifier{ $e.add($structOrUnionSpecifier.e); }
	| enumSpecifier		{ $e.add($enumSpecifier.e); }
	| typedefName		{ $e.addElement("typedef").addElement("id").addText($typedefName.s); }
	| typeofSpecifier
	;

structOrUnionSpecifier returns [Element e]
@init {
	List<Element> sds = new ArrayList<Element>();
	symbolsEnabled = false;
}
	: ^(structOrUnion ^(XID IDENTIFIER?) (sd=structDeclaration {sds.add(sd);})*) {
		$e = newElement($structOrUnion.s);
		if ($IDENTIFIER != null)
			$e.addAttribute("id", $IDENTIFIER.text);
		addAllElements($e, sds);
		symbolsEnabled = true;
	}
	;

structOrUnion returns [String s]
	: 'struct'	{ $s = "struct"; }
	| 'union'	{ $s = "union"; }
	;

structDeclaration returns [Element e]
@init {
	List<specifierQualifier_return> sqs = new ArrayList<specifierQualifier_return>();
	List<Element> sds = new ArrayList<Element>();
}
	: ^(STRUCT_DECLARATION (sq=specifierQualifier {sqs.add(sq);})+ (sd=structDeclarator {sds.add(sd);})*) {
		List <Element> tss = new ArrayList<Element>();
		$e = newElement("structDeclaration");
		for (specifierQualifier_return sqr: sqs)
			if (sqr.qual != null)
				$e.addAttribute(sqr.qual, "1");
			else
				tss.add(sqr.spec);
		for (Element el: typeNormalize(tss))
			$e.add(el);
		addAllElements($e, sds);
	}
	;

structDeclarator returns [Element e]
	: ^(STRUCT_DECLARATOR declarator? expression?) {
		$e = newElement("structDeclarator");
		addElementCond($e, $declarator.e);
		addElementCond($e, $expression.e);
	}
	;

enumSpecifier returns [Element e]
@init {
	List<Element> ens = new ArrayList<Element>();
}
	: ^('enum' (^(XID IDENTIFIER))? (en=enumerator {ens.add(en);})*) {
		$e = newElement("enum");
		if ($IDENTIFIER != null)
			$e.addAttribute("id", $IDENTIFIER.text);
		addAllElements($e, ens);
	}
	;

enumerator returns [Element e]
	: ^(ENUMERATOR IDENTIFIER expression?) {
		$e = newElement("enumerator");
		$e.addAttribute("id", $IDENTIFIER.text);
		addElementCond($e, $expression.e);
	}
	;

typedefName returns [String s]
	: IDENTIFIER	{ $s = $IDENTIFIER.text; }
	;

typeofSpecifier
	: ^(TYPEOF expression)
	| ^(TYPEOF typeName)
	;

storageClassSpecifier returns [String s]
	: 'extern'	{ $s = "extern"; }
	| 'static'	{ $s = "static"; }
	| 'auto'	{ $s = "auto"; }
	| 'register'	{ $s = "register"; }
	| '__thread'	{ $s = "__thread"; }
	;

functionSpecifier returns [String s]
	: 'inline'	{ $s = "inline"; }
	;

pointer returns [List<Element> els]
@init {
	Set<String> tqs = new HashSet<String>();
	$els = new ArrayList<Element>();
}
	: ^(POINTER (tq=typeQualifier {tqs.add(tq);})* ptr=pointer?) {
		Element e = newElement("pointer");
		for (String t: tqs)
			e.addAttribute(t, "1");
		$els.add(e);
		if ($ptr.els != null)
			$els.addAll($ptr.els);
	}
	;

/* TYPES END */

/* STATEMENTS */

statement returns [Element e]
@init {
	$e = newElement("statement");
}
	: labeledStatement	{ $e.add($labeledStatement.e); }
	| compoundStatement	{ $e.add($compoundStatement.e); }
	| expressionStatement	{ addElementCond($e, $expressionStatement.e); }
	| selectionStatement	{ $e.add($selectionStatement.e); }
	| iterationStatement	{ $e.add($iterationStatement.e); }
	| jumpStatement		{ $e.add($jumpStatement.e); }
	| asmStatement		{ $e.add($asmStatement.e); }
	;

labeledStatement returns [Element e]
	: ^(LABEL IDENTIFIER statement) {
		$e = newElement("labelStatement");
		$e.add($statement.e);
		$e.addAttribute("id", $IDENTIFIER.text);
	}
	| ^('case' expression statement) {
		$e = newElement("labeledStatement");
		$e.add($expression.e);
		$e.add($statement.e);
	}
	| ^('default' statement) {
		$e = newElement("defaultLabelStatement");
		$e.add($statement.e);
	}
	;

expressionStatement returns [Element e]
	: ^(EXPRESSION_STATEMENT expression?) { $e = $expression.e; }
	;

selectionStatement returns [Element e]
	: ^('if' expression s1=statement s2=statement?) {
		$e = newElement(s2 == null ? "ifStatement" : "ifElseStatement");
		$e.add($expression.e);
		$e.add(s1);
		addElementCond($e, s2);
	}
	| ^('switch' expression statement) {
		$e = newElement("switchStatement");
		$e.add($expression.e);
		$e.add($statement.e);
	}
	;

iterationStatement returns [Element e]
	: ^('while' expression statement) {
		$e = newElement("whileStatement");
		$e.add($expression.e);
		$e.add($statement.e);
	}
	| ^('do' statement expression) {
		$e = newElement("doStatement");
		$e.add($statement.e);
		$e.add($expression.e);
	}
	| ^('for' declaration? (^(E1 e1=expression))? ^(E2 e2=expression?) ^(E3 e3=expression?) statement) {
		$e = newElement("forStatement");
		if ($declaration.e != null)
			$e.add($declaration.e);
		else if (e1 != null)
			$e.add($e1.e);
		else
			$e.addElement("expression");
		if (e2 != null)
			$e.add($e2.e);
		else
			$e.addElement("expression");
		if (e3 != null)
			$e.add($e3.e);
		else
			$e.addElement("expression");
		$e.add($statement.e);
	}
	;

jumpStatement returns [Element e]
	: ^('goto' IDENTIFIER) {
		$e = newElement("gotoStatement");
		$e.addElement("expression").addElement("id").addText($IDENTIFIER.text);
	}
	| ^('goto' XU expression) {
		$e = newElement("gotoStatement");
		$e.addElement("expression").addElement("derefExpression").add($expression.e);
	}
	| 'continue'	{ $e = newElement("continueStatement"); }
	| 'break'	{ $e = newElement("breakStatement"); }
	| ^('return' expression?) {
		$e = newElement("returnStatement");
		addElementCond($e, $expression.e);
	}
	;

asmStatement returns [Element e]
	: ASM		{ $e = newElement("gnuAssembler"); }
	;

/* STATEMENTS END */

/* EXPRESSIONS */

expression returns [Element e]
@init {
	List<Element> exs = new ArrayList<Element>();
	Element exp;
	$e = newElement("expression");
}
	: ^(ASSIGNMENT_EXPRESSION assignmentOperator e1=expression e2=expression) {
		String op = $assignmentOperator.text;
		exp = addElementBin($e, "assignExpression", $e1.e, $e2.e);
		if (!op.equals("="))
			exp.addAttribute("op", op.substring(0, op.length() - 1));
	}
	| ^(CONDITIONAL_EXPRESSION ^(E1 e1=expression) ^(E2 e2=expression?) ^(E3 e3=expression)) {
		exp = $e.addElement("conditionalExpression");
		exp.add($e1.e);
		addElementCond(exp, $e2.e);
		exp.add($e3.e);
	}
	| ^(CAST_EXPRESSION tn=typeName e1=expression) { addElementBin($e, "castExpression", $tn.e, $e1.e); }
	| ^(ARRAY_ACCESS e1=expression e2=expression) { addElementBin($e, "arrayAccess", $e1.e, $e2.e); }
	| ^(FUNCTION_CALL e1=expression (e2=expression {exs.add($e2.e);})*) {
		exp = $e.addElement("functionCall");
		exp.add($e1.e);
		for (Element el: exs)
			exp.add(el);
	}
	| ^(COMPOUND_LITERAL tn=typeName initializerList) {
		Element me = newElement("initializer");
		addElementBin($e, "compoundLiteral", $tn.e, me);
		addAllElements(me, $initializerList.els);
	}
	| ^(',' e1=expression e2=expression)	{ addElementBin($e, "commaExpression", $e1.e, $e2.e); }
	| ^('++' e1=expression)		{ $e.addElement("prefixExpression").addAttribute("op", "++").add($e1.e); }
	| ^('--' e1=expression)		{ $e.addElement("prefixExpression").addAttribute("op", "--").add($e1.e); }
	| ^(unaryOp e1=expression)	{ $e.addElement("prefixExpression").addAttribute("op", $unaryOp.op).add($e1.e); }
	| ^('sizeof' (e1=expression|tn=typeName))	{ $e.addElement("sizeofExpression").add(e1 != null ? $e1.e : $tn.e); }
	| ^('__alignof__' (e1=expression|tn=typeName))	{ $e.addElement("allignofExpression").add(e1 != null ? $e1.e : $tn.e); }
	| ^('.' e1=expression IDENTIFIER)	{
		exp = $e.addElement("dotExpression");
		exp.add($e1.e);
		exp.addElement("member").addText($IDENTIFIER.text);
	}
	| ^('->' e1=expression IDENTIFIER) {
		exp = $e.addElement("arrowExpression");
		exp.add($e1.e);
		exp.addElement("member").addText($IDENTIFIER.text);
	}
	| ^(AU e1=expression)	{ $e.addElement("addrExpression").add($e1.e); }
	| ^(XU e1=expression)	{ $e.addElement("derefExpression").add($e1.e); }
	| ^(PP e1=expression)	{ $e.addElement("postfixExpression").addAttribute("op", "++").add($e1.e); }
	| ^(MM e1=expression)	{ $e.addElement("postfixExpression").addAttribute("op", "--").add($e1.e); }
	| binaryExpression	{ $e.add($binaryExpression.e); }
	| primaryExpression	{ $e.add($primaryExpression.e); }
	;

binaryExpression returns [Element e]
@after {
	$e = newElement("binaryExpression");
	$e.add($e1.e);
	$e.add($e2.e);
	$e.addAttribute("op", op.getText());
}
	: ^(op='||' e1=expression e2=expression)
	| ^(op='&&' e1=expression e2=expression)
	| ^(op='|' e1=expression e2=expression)
	| ^(op='&' e1=expression e2=expression)
	| ^(op='^' e1=expression e2=expression)
	| ^(op='==' e1=expression e2=expression)
	| ^(op='!=' e1=expression e2=expression)
	| ^(op='<=' e1=expression e2=expression)
	| ^(op='<' e1=expression e2=expression)
	| ^(op='>=' e1=expression e2=expression)
	| ^(op='>' e1=expression e2=expression)
	| ^(op='>>' e1=expression e2=expression)
	| ^(op='<<' e1=expression e2=expression)
	| ^(op='+' e1=expression e2=expression)
	| ^(op='-' e1=expression e2=expression)
	| ^(op='*' e1=expression e2=expression)
	| ^(op='/' e1=expression e2=expression)
	| ^(op='%' e1=expression e2=expression)
	;

primaryExpression returns [Element e]
	: IDENTIFIER		{ $e = newElement("id"); $e.addText(findVariable($IDENTIFIER.text)); }
	| CONSTANT		{ $e = newElement("intConst"); $e.addText($CONSTANT.text); }
	| sTRING_LITERAL	{ $e = newElement("stringConst"); $e.addText($sTRING_LITERAL.text); }
	| compoundStatement	{ $e = $compoundStatement.e; }
	| ^(BUILTIN_OFFSETOF typeName offsetofMemberDesignator) {
		$e = newElement("offsetofExpression");
		$e.add($typeName.e);
		$e.add($offsetofMemberDesignator.e);
	}
	;

sTRING_LITERAL returns [String text]
@init {
	List<String> sls = new ArrayList<String>();
}
	: ^(STR_LITERAL (STRING_LITERAL {sls.add($STRING_LITERAL.text);})+) {
		StringBuilder sb = new StringBuilder();
		for (String str: sls) /* crop the quotation */
			sb.append(str.substring(1, str.length() - 1));
		$text = sb.toString();
	}
	;

offsetofMemberDesignator returns [Element e]
@init {
	Element e1;
}
	: id1=IDENTIFIER {
		$e = newElement("expression");
		$e.addElement("id").addText(id1.getText());
	}	( ('.' id2=IDENTIFIER {
			e1 = newElement("dotExpression");
			e1.add($e);
			e1.addElement("member").addText(id2.getText());
			$e = newElement("expression");
			$e.add(e1);
		})
		| ('[' expression ']' {
			e1 = newElement("arrayAccess");
			e1.add($e);
			e1.add($expression.e);
			$e = newElement("expression");
			$e.add(e1);
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

unaryOp returns [String op]
	: PU		{ $op = "+"; }
	| MU		{ $op = "-"; }
	| '~'		{ $op = "~"; }
	| '!'		{ $op = "!"; }
	| LABREF	{ $op = "&&"; }
	| '__real__'	{ $op = "__real"; }
	| '__imag__'	{ $op = "__imag"; }
	;

/* EXPRESSIONS END */

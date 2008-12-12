/* vim: cin
 *
 * tree walker
 *
 * Copyright (c) 2008 Jiri Slaby <jirislaby@gmail.com>
 *
 * Licensed under GPLv2.
 */

tree grammar CFGEmitter;
options {
	tokenVocab=GNUCa;
	ASTLabelType=StanseTree;
}

scope IterSwitch {
	Set<CFGBreakNode> breaks;
	Set<CFGBreakNode> conts;
	Map<Integer, CFGNode> cases;
}
scope Function {
	Set<CFGBreakNode> rets;
	List<Pair<String, CFGBreakNode>> gotos;
	Map<String, CFGNode> labels;
	List<CFGPart> unreachables;
}

@header {
package cz.muni.stanse.parser;

import java.io.IOException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.dom4j.Element;

import cz.muni.stanse.utils.Pair;
import cz.muni.stanse.utils.XMLAlgo;
}
@members {
	private CFGPart createCFG(Element e) {
		CFGPart cfg = new CFGPart();
		CFGNode n = new CFGNode(e);
		cfg.setStartNode(n);
		cfg.setEndNode(n);
		return cfg;
	}
}

translationUnit returns [Set<CFG> g]
@init {
	$g = new LinkedHashSet<CFG>();
}
	: ^(TRANSLATION_UNIT (externalDeclaration {
		if ($externalDeclaration.g != null)
			$g.add($externalDeclaration.g);
	} )*)
	;

externalDeclaration returns [CFG g]
	: functionDefinition	{ $g=$functionDefinition.g; }
	| declaration
	;

functionDefinition returns [CFG g]
scope Function;
@init {
	$Function::rets = new HashSet<CFGBreakNode>();
	$Function::labels = new HashMap<String, CFGNode>();
	$Function::gotos = new LinkedList<Pair<String, CFGBreakNode>>();
	$Function::unreachables = new LinkedList<CFGPart>();
}
	: ^(FUNCTION_DEFINITION declarationSpecifiers declarator declaration* compoundStatement) {
		$g = CFG.createFromCFGPart($compoundStatement.g,
				$functionDefinition.start.getElement());
		for (CFGBreakNode n: $Function::rets)
			n.addBreakEdge($g.getEndNode());
		for (Pair<String, CFGBreakNode> gotoPair: $Function::gotos) {
			CFGNode labelNode =
				$Function::labels.get(gotoPair.getFirst());
			gotoPair.getSecond().addBreakEdge(labelNode);
		}
		for (CFGPart cfg: $Function::unreachables)
			if (cfg.getStartNode().getPredecessors().size() == 0) {
				System.err.println("Unreachable:");
				System.err.println(cfg.toStringGraph());
				System.err.println("\n============");
			}

//		System.err.println($g.toStringGraph());
	}
	;

declaration
	: ^('typedef' declarationSpecifiers? initDeclarator*)
	| ^(DECLARATION declarationSpecifiers initDeclarator*)
	;

declarationSpecifiers
	: ^(DECLARATION_SPECIFIERS ^(XTYPE_SPECIFIER typeSpecifier*) ^(XTYPE_QUALIFIER typeQualifier*) ^(XSTORAGE_CLASS (storageClassSpecifier|functionSpecifier)*))
	;

declarator
	: ^(DECLARATOR pointer? directDeclarator)
	;

directDeclarator
	: IDENTIFIER
	| declarator
	| directDeclarator1
	;

directDeclarator1
	: ^(ARRAY_DECLARATOR (IDENTIFIER|directDeclarator1) ('static'|'*')? typeQualifier* expression?)
	| ^(FUNCTION_DECLARATOR (IDENTIFIER|declarator) (parameterTypeList|identifier*))
	;

initDeclarator
	: ^(INIT_DECLARATOR declarator initializer?)
	;

initializer
	: ^(INITIALIZER expression)
	| INITIALIZER
	| ^(INITIALIZER initializerList)
	;

initializerList
	: (designator* initializer)+
	;

designator
	: ^(DESIGNATOR ^('...' expression expression))
	| ^(DESIGNATOR ^(BRACKET_DESIGNATOR expression))
	| ^(DESIGNATOR IDENTIFIER)
	| IDENTIFIER
	;

compoundStatement returns [CFGPart g]
@init {
	LinkedList<CFGPart> cfgs = new LinkedList<CFGPart>();
	CFGPart cfg = new CFGPart();
	cfgs.add(cfg);
	boolean isBreak = false;
}
@after {
	$g = cfgs.removeFirst();

	for (CFGPart cfg1: cfgs) {
		if (cfg1.getStartNode().getPredecessors().size() == 0)
			$Function::unreachables.add(cfg1);
		else
			System.err.println("Gak.");
	}
}
	: ^(COMPOUND_STATEMENT (declaration | functionDefinition |
		st=statement {
			if (isBreak) {
				cfg = new CFGPart();
				cfgs.add(cfg);
				isBreak = false;
			}
			if ($st.g.getEndNode() instanceof CFGBreakNode)
				isBreak = true;
			cfg.append($st.g);
		})*)
	;

parameterTypeList
	: parameterDeclaration+ VARARGS?
	;

parameterDeclaration
	: ^(PARAMETER declarationSpecifiers declarator? abstractDeclarator?)
	;

abstractDeclarator
	: ^(ABSTRACT_DECLARATOR pointer directAbstractDeclarator?)
	| ^(ABSTRACT_DECLARATOR directAbstractDeclarator)
	;

directAbstractDeclarator
	: abstractDeclarator arrayOrFunctionDeclarator*
	| arrayOrFunctionDeclarator+
	;

arrayOrFunctionDeclarator
	: ^(ARRAY_DECLARATOR (expression?|'*'))
	| ^(FUNCTION_DECLARATOR parameterTypeList?)
	;

identifier
	: ^(PARAMETER IDENTIFIER)
	;

/* TYPES */

typeName
	: ^(TYPE_NAME specifierQualifier+ abstractDeclarator?)
	;

specifierQualifier
	: ^(XTYPE_SPECIFIER typeSpecifier)
	| ^(XTYPE_QUALIFIER typeQualifier)
	;

typeQualifier
	: 'const'
	| 'restrict'
	| 'volatile'
	;

typeSpecifier
	: ^(BASETYPE 'void')
	| ^(BASETYPE 'char')
	| ^(BASETYPE 'short')
	| ^(BASETYPE 'int')
	| ^(BASETYPE 'long')
	| ^(BASETYPE 'float')
	| ^(BASETYPE 'double')
	| ^(BASETYPE SIGNED)
	| ^(BASETYPE 'unsigned')
	| ^(BASETYPE '_Bool')
	| ^(BASETYPE COMPLEX)
	| ^(BASETYPE XID)
	| ^(BASETYPE '_Imaginary')
	| structOrUnionSpecifier
	| enumSpecifier
	| typedefName
	| typeofSpecifier
	;

structOrUnionSpecifier
	: ^(structOrUnion ^(XID IDENTIFIER?) structDeclaration*)
	;

structOrUnion
	: 'struct'
	| 'union'
	;

structDeclaration
	: ^(STRUCT_DECLARATION specifierQualifier+ structDeclarator*)
	;

structDeclarator
	: ^(STRUCT_DECLARATOR declarator? expression?)
	;

enumSpecifier
	: ^('enum' (^(XID IDENTIFIER))? enumerator*)
	;

enumerator
	: ^(ENUMERATOR IDENTIFIER expression?)
	;

typedefName
	: IDENTIFIER
	;

typeofSpecifier
	: ^(TYPEOF expression)
	| ^(TYPEOF typeName)
	;

storageClassSpecifier
	: 'extern'
	| 'static'
	| 'auto'
	| 'register'
	| '__thread'
	;

functionSpecifier
	: 'inline'
	;

pointer
	: ^(POINTER typeQualifier* pointer?)
	;

/* TYPES END */

/* STATEMENTS */

statement returns [CFGPart g]
	: labeledStatement	{ $g=$labeledStatement.g; }
	| compoundStatement	{ $g=$compoundStatement.g; }
	| expressionStatement	{ $g=$expressionStatement.g; }
	| selectionStatement	{ $g=$selectionStatement.g; }
	| iterationStatement	{ $g=$iterationStatement.g; }
	| jumpStatement		{ $g=$jumpStatement.g; }
	| asmStatement		{ $g=$asmStatement.g; }
	;

labeledStatement returns [CFGPart g]
	: ^(LABEL IDENTIFIER statement)		{
		$g = $statement.g;
		$Function::labels.put($IDENTIFIER.text, $g.getStartNode());
	}
	| ^('case' expression statement) {
		$g = $statement.g;
		Element labelElement = $expression.start.getElement();
		int label = Integer.decode(labelElement.node(0).getText());
		$IterSwitch::cases.put(label, $g.getStartNode());
	}
	| ^('default' statement) {
		$g = $statement.g;
		$IterSwitch::cases.put(Integer.MIN_VALUE, $g.getStartNode());
	}
	;

expressionStatement returns [CFGPart g]
	: ^(EXPRESSION_STATEMENT expression?) {
		if ($expression.g == null) {
			/* emptyStatement is a child */
			$g = createCFG((Element)$expressionStatement.start.
				getElement().node(0));
		} else
			$g = $expression.g;
	}
	;

selectionStatement returns [CFGPart g]
	: selectionStatementIf		{ $g = $selectionStatementIf.g; }
	| selectionStatementSwitch	{ $g = $selectionStatementSwitch.g; }
	;

selectionStatementIf returns [CFGPart g]
	: ^('if' expression s1=statement s2=statement?) {
		$g = new CFGPart();
		/* fork */
		CFGNode n1 = new CFGBranchNode($expression.start.getElement());
		/* junction */
		CFGNode n2 = new CFGNode();
		$g.setStartNode(n1);
		$g.setEndNode(n2);
		/* true */
		n1.addEdge($s1.g.getStartNode());
		$s1.g.getEndNode().addEdge(n2);
		/* false */
		if (s2 != null) {
			n1.addEdge($s2.g.getStartNode());
			$s2.g.getEndNode().addEdge(n2);
		} else
			n1.addEdge(n2);
	}
	;

selectionStatementSwitch returns [CFGPart g]
scope IterSwitch;
@init {
	$IterSwitch::breaks = new HashSet<CFGBreakNode>();
	$IterSwitch::cases = new TreeMap<Integer, CFGNode>();
}
	: ^('switch' expression statement) {
		$g = new CFGPart();
		CFGBranchNode n = new CFGBranchNode($expression.start.
				getElement());
		$g.setStartNode(n);
		CFGNode breakNode = new CFGNode();
		$g.setEndNode(breakNode);
		Set <Integer> labels = $IterSwitch::cases.keySet();
		for (Integer label: labels)
			n.addEdge($IterSwitch::cases.get(label), label);
		/* add default if not present */
		if (!labels.contains(Integer.MIN_VALUE))
			n.addEdge(breakNode, Integer.MIN_VALUE);
		/* backpatch break */
		for (CFGBreakNode c: $IterSwitch::breaks)
			c.addBreakEdge(breakNode);
	}
	;

iterationStatement returns [CFGPart g]
scope IterSwitch;
@init {
	$g = new CFGPart();
	$IterSwitch::breaks = new HashSet<CFGBreakNode>();
	$IterSwitch::conts = new HashSet<CFGBreakNode>();
	CFGNode breakNode = null;
	CFGNode contNode = null;
}
@after {
	/* backpatch */
	for (CFGBreakNode n: $IterSwitch::breaks)
		n.addBreakEdge(breakNode);
	for (CFGBreakNode n: $IterSwitch::conts)
		n.addBreakEdge(contNode);
}
	: ^('while' expression statement) {
		/* fork */
		contNode = new CFGBranchNode($expression.start.getElement());
		/* junction */
		breakNode = new CFGNode();
		$g.setStartNode(contNode);
		$g.setEndNode(breakNode);
		/* true */
		contNode.addEdge($statement.g.getStartNode());
		$statement.g.getEndNode().addEdge(contNode);
		/* false */
		contNode.addEdge(breakNode);
	}
	| ^('do' statement expression) {
		/* fork */
		contNode = new CFGBranchNode($expression.start.getElement());
		/* junction */
		breakNode = new CFGNode();

		$g.setStartNode($statement.g.getStartNode());
		$g.setEndNode(breakNode);
		$statement.g.getEndNode().addEdge(contNode);
		/* true */
		contNode.addEdge($statement.g.getStartNode());
		/* false */
		contNode.addEdge(breakNode);
	}
	| ^('for' declaration? (^(E1 e1=expression))? ^(E2 e2=expression?) ^(E3 e3=expression?) statement) {
		CFGNode n1, n2;
		if ($e1.g == null)
			n1 = new CFGNode();
		else
			n1 = new CFGNode($e1.start.getElement());
		$g.setStartNode(n1);
		if ($e2.g == null)
			n2 = new CFGNode();
		else
			n2 = new CFGBranchNode($e2.start.getElement());
		if ($e3.g == null)
			contNode = new CFGNode();
		else
			contNode = new CFGNode($e3.start.getElement());
		n1.addEdge(n2);
		contNode.addEdge(n2);
		breakNode = new CFGNode();
		$g.setEndNode(breakNode);
		/* true */
		n2.addEdge($statement.g.getStartNode());
		/* false */
		if ($e2.g != null)
			n2.addEdge(breakNode);

		$statement.g.getEndNode().addEdge(contNode);
	}
	;

jumpStatement returns [CFGPart g]
@init {
	$g = new CFGPart();
	CFGBreakNode n = new CFGBreakNode($jumpStatement.start.getElement());
	$g.setStartNode(n);
	$g.setEndNode(n);
}
	: ^('goto' IDENTIFIER) {
		$Function::gotos.add(
			new Pair<String, CFGBreakNode>($IDENTIFIER.text, n));
	}
	| ^('goto' XU expression)
	| 'continue' {
		/* conts (opposing to breaks) are not there for switch,
		   find deeper in the stack */
		int a = 0;
		while ($IterSwitch[-a]::conts == null)
			a++;
		$IterSwitch[-a]::conts.add(n);
	}
	| 'break' { $IterSwitch::breaks.add(n); }
	| ^('return' expression?) { $Function::rets.add(n); }
	;

asmStatement returns [CFGPart g]
	: ASM		{ $g = createCFG($asmStatement.start.getElement()); }	;

/* STATEMENTS END */

/* EXPRESSIONS */

expression returns [CFGPart g]
@init {
	$g = createCFG($expression.start.getElement());
}
	: ^(ASSIGNMENT_EXPRESSION assignmentOperator e1=expression e2=expression)
	| ^(CONDITIONAL_EXPRESSION ^(E1 e1=expression) ^(E2 e2=expression?) ^(E3 e3=expression))
	| ^(CAST_EXPRESSION tn=typeName e1=expression)
	| ^(ARRAY_ACCESS e1=expression e2=expression)
	| ^(FUNCTION_CALL e1=expression e2=expression*)
	| ^(COMPOUND_LITERAL tn=typeName initializerList)
	| ^(',' e1=expression e2=expression)
	| ^('++' e1=expression)
	| ^('--' e1=expression)
	| ^(unaryOp e1=expression)
	| ^('sizeof' (e1=expression|tn=typeName))
	| ^('__alignof__' (e1=expression|tn=typeName))
	| ^('.' e1=expression IDENTIFIER)
	| ^('->' e1=expression IDENTIFIER)
	| ^(AU e1=expression)
	| ^(XU e1=expression)
	| ^(PP e1=expression)
	| ^(MM e1=expression)
	| binaryExpression
	| primaryExpression
	;

binaryExpression
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

primaryExpression
	: IDENTIFIER
	| CONSTANT
	| sTRING_LITERAL
	| compoundStatement
	| ^(BUILTIN_OFFSETOF typeName offsetofMemberDesignator)
	;

sTRING_LITERAL
	: ^(STR_LITERAL STRING_LITERAL+)
	;

offsetofMemberDesignator
	: id1=IDENTIFIER
		( ('.' id2=IDENTIFIER)
		| ('[' expression ']')
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

unaryOp
	: PU
	| MU
	| '~'
	| '!'
	| LABREF
	| '__real__'
	| '__imag__'
	;

/* EXPRESSIONS END */

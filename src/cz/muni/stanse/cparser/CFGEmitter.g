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
	List<CFGBreakNode> breaks;
	List<CFGBreakNode> conts;
	List<Pair<Element, CFGNode>> cases;
	boolean haveDefault;
}
scope Function {
	List<CFGBreakNode> rets;
	List<Pair<String, CFGBreakNode>> gotos;
	Map<String, CFGNode> labels;
	List<CFGPart> unreachables;
	CFGNode lastStatement;
}

@header {
package cz.muni.stanse.cparser;

import cz.muni.stanse.codestructures.*;

import java.io.IOException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import org.dom4j.DocumentFactory;
import org.dom4j.Element;

import cz.muni.stanse.utils.Pair;
}
@members {
	private Element defaultLabel, falseLabel, emptyStatement;
	private DocumentFactory xmlFactory = DocumentFactory.getInstance();

	private CFGPart createCFG(Element e) {
		CFGPart cfg = new CFGPart();
		CFGNode n = new CFGNode(e);
		cfg.setStartNode(n);
		cfg.setEndNode(n);
		return cfg;
	}
}

translationUnit returns [List<CFG> g]
@init {
	$g = new LinkedList<CFG>();
	emptyStatement = xmlFactory.createElement("emptyStatement");
	defaultLabel = xmlFactory.createElement("default");
	falseLabel = xmlFactory.createElement("intConst");
	falseLabel.setText("0");
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
	$Function::rets = new LinkedList<CFGBreakNode>();
	$Function::labels = new HashMap<String, CFGNode>();
	$Function::gotos = new LinkedList<Pair<String, CFGBreakNode>>();
	$Function::unreachables = new LinkedList<CFGPart>();
}
	: ^(FUNCTION_DEFINITION declarationSpecifiers declarator declaration* compoundStatement) {
		$g = CFG.createFromCFGPart($compoundStatement.g,
				$functionDefinition.start.getElement());
		CFGNode endNode = new CFGNode();
		$g.setEndNode(endNode);
		for (CFGBreakNode n: $Function::rets)
			n.addBreakEdge(endNode);
		$Function::lastStatement.addEdge(endNode);
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
//		System.err.println($g.toDot());
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
	: ^(ARRAY_DECLARATOR (directDeclarator) ('static'|'*')? typeQualifier* expression?)
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
	/* add emptytStatement node if empty */
	if ($g.getStartNode() == null)
		$g.append(new CFGNode((Element)$compoundStatement.start.
					getElement().node(0)));
	$Function::lastStatement = cfg.getEndNode();

	for (CFGPart cfg1: cfgs) {
		if (cfg1.getStartNode().getPredecessors().size() == 0)
			$Function::unreachables.add(cfg1);
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
		$IterSwitch::cases.add(new Pair($expression.start.getElement(),
			$g.getStartNode()));
	}
	| ^('default' statement) {
		$g = $statement.g;
		$IterSwitch::cases.add(new Pair(defaultLabel, $g.getStartNode()));
		$IterSwitch::haveDefault = true;
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
@init {
	CFGBranchNode n1;
	CFGNode s1Last;
}
	: ^('if' expression {
		/* fork */
		n1 = new CFGBranchNode($expression.start.getElement());
	} s1=statement {
		s1Last = $Function::lastStatement;
	} s2=statement?) {
		$g = new CFGPart();
		/* join */
		CFGNode n2 = new CFGJoinNode();
		$g.setStartNode(n1);
		$g.setEndNode(n2);
		/* true */
		n1.addEdge($s1.g.getStartNode(), defaultLabel);
		if ($s1.start.getType() == COMPOUND_STATEMENT)
			s1Last.addEdge(n2);
		else
			$s1.g.getEndNode().addEdge(n2);
		/* false */
		if (s2 != null) {
			n1.addEdge($s2.g.getStartNode(), falseLabel);
			if ($s2.start.getType() == COMPOUND_STATEMENT)
				$Function::lastStatement.addEdge(n2);
			else
				$s2.g.getEndNode().addEdge(n2);
		} else
			n1.addEdge(n2, falseLabel);
	}
	;

selectionStatementSwitch returns [CFGPart g]
scope IterSwitch;
@init {
	$IterSwitch::breaks = new LinkedList<CFGBreakNode>();
	$IterSwitch::cases = new LinkedList<Pair<Element, CFGNode>>();
	$IterSwitch::haveDefault = false;
	CFGBranchNode n;
}
	: ^('switch' expression {
		n = new CFGBranchNode($expression.start.getElement());
	} statement) {
		$g = new CFGPart();
		$g.setStartNode(n);
		CFGNode breakNode = new CFGJoinNode();
		$g.setEndNode(breakNode);
		for (Pair<Element, CFGNode> pair: $IterSwitch::cases)
			n.addEdge(pair.getSecond(), pair.getFirst());
		/* add default if not present */
		if (!$IterSwitch::haveDefault)
			n.addEdge(breakNode, defaultLabel);
		/* backpatch break */
		for (CFGBreakNode c: $IterSwitch::breaks)
			c.addBreakEdge(breakNode);
		$Function::lastStatement.addEdge(breakNode);
	}
	;

iterationStatement returns [CFGPart g]
scope IterSwitch;
@init {
	$g = new CFGPart();
	$IterSwitch::breaks = new LinkedList<CFGBreakNode>();
	$IterSwitch::conts = new LinkedList<CFGBreakNode>();
	CFGNode breakNode = null;
	CFGNode contNode = null;
	CFGNode lastStatement = null;
	CFGNode n1, n2;
	CFGPart statementCFG = null;
	int statementType = 0;
}
@after {
	/* backpatch */
	for (CFGBreakNode n: $IterSwitch::breaks)
		n.addBreakEdge(breakNode);
	for (CFGBreakNode n: $IterSwitch::conts)
		n.addBreakEdge(contNode);
	if (statementType == COMPOUND_STATEMENT)
		lastStatement.addEdge(contNode);
	else
		statementCFG.getEndNode().addEdge(contNode);
}
	: ^('while' expression { /* to preserve sequential numbers */
		/* fork */
		CFGBranchNode branch =
			new CFGBranchNode($expression.start.getElement());
	} statement) {
		statementCFG = $statement.g;
		/* join */
		breakNode = new CFGJoinNode();
		$g.setStartNode(branch);
		$g.setEndNode(breakNode);
		/* true */
		branch.addEdge(statementCFG.getStartNode(), defaultLabel);
		/* false */
		branch.addEdge(breakNode, falseLabel);
		contNode = branch;
		statementType = $statement.start.getType();
		lastStatement = $Function::lastStatement;
	}
	| ^('do' statement {
		lastStatement = $Function::lastStatement;
	} expression) {
		statementCFG = $statement.g;
		/* fork */
		CFGBranchNode branch =
			new CFGBranchNode($expression.start.getElement());
		/* join */
		breakNode = new CFGJoinNode();

		$g.setStartNode(statementCFG.getStartNode());
		$g.setEndNode(breakNode);
		/* true */
		branch.addEdge($statement.g.getStartNode(), defaultLabel);
		/* false */
		branch.addEdge(breakNode, falseLabel);
		contNode = branch;
		statementType = $statement.start.getType();
	}
	| ^('for' declaration? (^(E1 e1=expression))? ^(E2 e2=expression?) {
		if ($e1.g == null) /* no initial */
			n1 = new CFGNode(emptyStatement);
		else
			n1 = new CFGNode($e1.start.getElement());
		$g.setStartNode(n1);
		breakNode = new CFGJoinNode();
		$g.setEndNode(breakNode);
		if ($e2.g == null) { /* no test */
			n2 = new CFGNode(emptyStatement);
		} else {
			CFGBranchNode branch =
				new CFGBranchNode($e2.start.getElement());
			n2 = branch;
		}
		n1.addEdge(n2);
	} ^(E3 e3=expression?) statement) {
		statementCFG = $statement.g;
		if ($e2.g == null)
			n2.addEdge($statement.g.getStartNode());
		else {
			CFGBranchNode branch = (CFGBranchNode)n2;
			/* true */
			branch.addEdge(statementCFG.getStartNode(),
					defaultLabel);
			/* false */
			branch.addEdge(breakNode, falseLabel);
		}
		if ($e3.g == null) /* no post */
			contNode = new CFGNode(emptyStatement);
		else
			contNode = new CFGNode($e3.start.getElement());
		contNode.addEdge(n2);

		statementType = $statement.start.getType();
		lastStatement = $Function::lastStatement;
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
	CFGBranchNode n1;
	CFGNode trueNode = null;
	$g = null;
}
@after {
	if ($g == null)
		$g = createCFG($expression.start.getElement());
}
	: ^(ASSIGNMENT_EXPRESSION assignmentOperator e1=expression e2=expression) { /*$g = $e2.g; do nothing so far XXX to be fixed */  }
	| ^(CONDITIONAL_EXPRESSION ^(E1 e1=expression) {
		/* fork */
		n1 = new CFGBranchNode($e1.start.getElement());
	} ^(E2 e2=expression?) {
		if (e2 == null)
			trueNode = new CFGNode($e1.start.getElement());
	} ^(E3 e3=expression)) {
		$g = new CFGPart();
		/* join */
		CFGNode n2 = new CFGJoinNode();
		$g.setStartNode(n1);
		$g.setEndNode(n2);
		/* true */
		if (trueNode == null) {
			n1.addEdge($e2.g.getStartNode(), defaultLabel);
			$e2.g.getEndNode().addEdge(n2);
		} else {
			n1.addEdge(trueNode, defaultLabel);
			trueNode.addEdge(n2);
		}
		/* false */
		n1.addEdge($e3.g.getStartNode(), falseLabel);
		$e3.g.getEndNode().addEdge(n2);
	}
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
	| binaryExpression { $g=$binaryExpression.g; }
	| primaryExpression { $g=$primaryExpression.g; }
	;

binaryExpression returns [CFGPart g]
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

primaryExpression returns [CFGPart g]
	: IDENTIFIER
	| CONSTANT
	| sTRING_LITERAL
	| compoundStatement { $g=$compoundStatement.g; }
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

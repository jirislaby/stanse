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
	Set<String> symbols;
	CFGNode lastStatement;
}

@header {
package cz.muni.stanse.cparser;

import cz.muni.stanse.codestructures.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.dom4j.DocumentFactory;
import org.dom4j.Element;

import cz.muni.stanse.utils.Pair;
import cz.muni.stanse.utils.Triple;
}
@members {
	static Element defaultLabel = CFGEvaluator.defaultLabel;
	static Element falseLabel = CFGEvaluator.falseLabel;

	private static DocumentFactory xmlFactory =
		DocumentFactory.getInstance();

	private CFGPart createCFG(final Element e, final String c) {
		final CFGPart cfg = new CFGPart();
		final CFGNode n = new CFGNode(e, c);
		cfg.setStartNode(n);
		cfg.setEndNode(n);
		return cfg;
	}

	private CFGPart createCFG(final Element e) {
		return createCFG(e, null);
	}

	private void addSwitchDefaultAssert(CFGBranchNode branch, String code,
			CFGNode breakNode, Element lineElem) {
		CFGNode parent = branch;
		boolean pin = false;
		// add all non-default as negated
		for (Pair<Element, CFGNode> pair: $IterSwitch::cases) {
			Element caseLabel = pair.getFirst();
			if (caseLabel.getName().equals("default"))
				continue;
			pin = true;
			Element cond = xmlFactory.
				createElement("binaryExpression").
				addAttribute("op", "==");
			cond.add(branch.getElement().createCopy());
			cond.add(caseLabel.createCopy());
			parent = CFGEvaluator.addAssert(parent, defaultLabel,
					null, cond, code + " == " + caseLabel.asXML(), true, lineElem);
		}
		if (pin)
			parent.addEdge(breakNode);
		else
			branch.addEdge(breakNode, defaultLabel);
	}
	private Element newEmptyStatement(CommonTree ct) {
		Element e = xmlFactory.createElement("emptyStatement");
		e.addAttribute("bl", Integer.toString(ct.getLine()));
		e.addAttribute("bc", Integer.toString(
					ct.getCharPositionInLine()));
		return e;
	}
}

translationUnit returns [List<CFG> g]
@init {
	$g = new LinkedList<CFG>();
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
	$Function::symbols = new HashSet<String>();
}
	: ^(FUNCTION_DEFINITION declarationSpecifiers declarator declaration* compoundStatement) {
		$g = CFG.createFromCFGPart($compoundStatement.g,
				$functionDefinition.start.getElement());
		$g.setSymbols($Function::symbols);
		CFGNode endNode = new CFGNode(xmlFactory.createElement("exit").
				addAttribute("bl", Integer.toString(
				$compoundStatement.start.getChild(0).
				getLine())));
		$g.setEndNode(endNode);
		for (CFGBreakNode n: $Function::rets)
			n.addBreakEdge(endNode);
		$Function::lastStatement.addEdge(endNode);
		for (Pair<String, CFGBreakNode> gotoPair: $Function::gotos) {
			CFGNode labelNode =
				$Function::labels.get(gotoPair.getFirst());
			gotoPair.getSecond().addBreakEdge(labelNode);
		}

//		System.err.println($g.toStringGraph());
//		System.err.println($g.toDot());
	}
	;

declaration returns [CFGPart g]
@init {
	$g = new CFGPart();
}
	: ^('typedef' declarationSpecifiers? initDeclarator*)
	| ^(DECLARATION declarationSpecifiers (initDeclarator {
			CFGPart idg = $initDeclarator.g;
			if (idg != null)
				$g.append(idg);
		})*)
	;

declarationSpecifiers
	: ^(DECLARATION_SPECIFIERS ^(XTYPE_SPECIFIER typeSpecifier*) ^(XTYPE_QUALIFIER typeQualifier*) ^(XSTORAGE_CLASS (storageClassSpecifier|functionSpecifier)*))
	;

declarator
	: ^(DECLARATOR pointer? directDeclarator)
	;

directDeclarator
	: IDENTIFIER {
		if ($Function.size() > 0) /* skip globals */
			$Function::symbols.add($IDENTIFIER.text);
	}
	| declarator
	| directDeclarator1
	;

directDeclarator1
	: ^(ARRAY_DECLARATOR (directDeclarator) ('static'|'*')? typeQualifier* expression?)
	| ^(FUNCTION_DECLARATOR (IDENTIFIER|declarator) (parameterTypeList|identifier*))
	;

initDeclarator returns [CFGPart g]
	: ^(INIT_DECLARATOR declarator initializer?) {
		if ($initializer.start != null) {
			$g = new CFGPart();
			$g.append(new CFGNode($initDeclarator.start.getElement(), $initDeclarator.text));
		}
	}
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
}
	: ^(COMPOUND_STATEMENT CS_END (declaration {
			if (!$declaration.g.isEmpty())
				cfg.append($declaration.g);
		} | functionDefinition |
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
	: ^(PARAMETER IDENTIFIER) { $Function::symbols.add($IDENTIFIER.text); }
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
	CFGNode s1Last;
	Triple<CFGNode,CFGNode,CFGNode> evalExpr;
}
	: ^('if' expression {
		evalExpr = CFGEvaluator.evaluateExpr(
			$expression.start.getElement(), $expression.text);
	} s1=statement {
		s1Last = $Function::lastStatement;
	} s2=statement?) {
		$g = new CFGPart();
		CFGNode n1, n2 = new CFGJoinNode();
		n1 = ExprEvaluator.connect(evalExpr, $s1.g.getStartNode(),
				s2 == null ? n2 : $s2.g.getStartNode());
		$g.setStartNode(n1);
		$g.setEndNode(n2);
		if ($s1.start.getType() == COMPOUND_STATEMENT)
			s1Last.addEdge(n2);
		else
			$s1.g.getEndNode().addEdge(n2);
		if (s2 != null) {
			if ($s2.start.getType() == COMPOUND_STATEMENT)
				$Function::lastStatement.addEdge(n2);
			else
				$s2.g.getEndNode().addEdge(n2);
		}
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
		n = new CFGBranchNode($expression.start.getElement(), $expression.text);
	} statement) {
		$g = new CFGPart();
		$g.setStartNode(n);
		CFGNode breakNode = new CFGJoinNode();
		$g.setEndNode(breakNode);
		for (Pair<Element, CFGNode> pair: $IterSwitch::cases) {
			Element caseLabel = pair.getFirst();
			if (caseLabel.getName().equals("default")) {
				addSwitchDefaultAssert(n, $expression.text,
					pair.getSecond(), caseLabel);
			} else {
				Element cond = xmlFactory.
					createElement("binaryExpression").
					addAttribute("op", "==");
				cond.add(n.getElement().createCopy());
				cond.add(caseLabel.createCopy());
				CFGEvaluator.addAssert(n, caseLabel,
					pair.getSecond(), cond,
					$expression.text + " == " + caseLabel.asXML(),
					false, caseLabel);
			}
		}
		/* add default if not present */
		if (!$IterSwitch::haveDefault)
			addSwitchDefaultAssert(n, null, breakNode, n.getElement());

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
	Triple<CFGNode,CFGNode,CFGNode> evalExpr;
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
		evalExpr = CFGEvaluator.evaluateExpr(
			$expression.start.getElement(), $expression.text);
	} statement) {
		statementCFG = $statement.g;
		breakNode = new CFGJoinNode();
		contNode = ExprEvaluator.connect(evalExpr,
				statementCFG.getStartNode(), breakNode);
		$g.setStartNode(contNode);
		$g.setEndNode(breakNode);
		statementType = $statement.start.getType();
		lastStatement = $Function::lastStatement;
	}
	| ^('do' statement {
		lastStatement = $Function::lastStatement;
	} expression) {
		statementCFG = $statement.g;
		breakNode = new CFGJoinNode();
		$g.setStartNode(statementCFG.getStartNode());
		$g.setEndNode(breakNode);
		contNode = CFGEvaluator.evaluateExprConnect(
				$expression.start.getElement(),
				$expression.text,
				statementCFG.getStartNode(), breakNode);
		statementType = $statement.start.getType();
	}
	| ^(f='for' declaration? (^(E1 e1=expression))? ^(E2 e2=expression?) {
		n1 = new CFGNode($e1.g == null ? newEmptyStatement($f) :
				$e1.start.getElement());
		breakNode = new CFGJoinNode();
		$g.setStartNode(n1);
		$g.setEndNode(breakNode);
		evalExpr = CFGEvaluator.evaluateExpr($e2.g == null ?
			newEmptyStatement($f) : $e2.start.getElement(),
			$e2.g == null ? null : $e2.text);
	} ^(E3 e3=expression?) statement) {
		statementCFG = $statement.g;
		n2 = ExprEvaluator.connect(evalExpr,
				statementCFG.getStartNode(), breakNode);
		n1.addEdge(n2);

		contNode = new CFGNode($e3.g == null ?
				newEmptyStatement($f) :
				$e3.start.getElement());
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
	: ASM		{ $g = createCFG($asmStatement.start.getElement(), $asmStatement.text); }	;

/* STATEMENTS END */

/* EXPRESSIONS */

expression returns [CFGPart g]
@init {
	CFGNode trueNode = null;
	Triple<CFGNode,CFGNode,CFGNode> evalExpr;
	$g = null;
}
@after {
	if ($g == null)
		$g = createCFG($expression.start.getElement(), $expression.text);
}
	: ^(ASSIGNMENT_EXPRESSION assignmentOperator e1=expression e2=expression) { /*$g = $e2.g; do nothing so far XXX to be fixed */  }
	| ^(CONDITIONAL_EXPRESSION ^(E1 e1=expression) {
		evalExpr = CFGEvaluator.evaluateExpr(
			$e1.start.getElement(), $e1.text);
	} ^(E2 e2=expression?) {
		if (e2 == null)
			trueNode = new CFGNode($e1.start.getElement());
	} ^(E3 e3=expression)) {
		$g = new CFGPart();
		CFGNode n1 = ExprEvaluator.connect(evalExpr,
				trueNode == null ? $e2.g.getStartNode() :
				trueNode, $e3.g.getStartNode());
		CFGNode n2 = new CFGJoinNode();
		$g.setStartNode(n1);
		$g.setEndNode(n2);
		if (trueNode == null)
			$e2.g.getEndNode().addEdge(n2);
		 else
			trueNode.addEdge(n2);
		$e3.g.getEndNode().addEdge(n2);
	}
	| ^(CAST_EXPRESSION tn=typeName e1=expression)
	| ^(ARRAY_ACCESS e1=expression e2=expression)
	| ^(FUNCTION_CALL e1=expression e2=expression*)
	| ^(COMPOUND_LITERAL tn=typeName initializerList?)
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
	| constant
	| sTRING_LITERAL
	| compoundStatement { $g=$compoundStatement.g; }
	| ^(BUILTIN_OFFSETOF typeName offsetofMemberDesignator)
	;

sTRING_LITERAL
	: ^(STR_LITERAL STRING_LITERAL+)
	;

constant
	:	ICONSTANT
	|	RCONSTANT
	;

offsetofMemberDesignator
	: IDENTIFIER
		( ('.' IDENTIFIER)
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

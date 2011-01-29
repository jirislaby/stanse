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
	language=C;
	ASTLabelType = pANTLR3_BASE_TREE;
}

scope IterSwitch {
	ANTLR3_LIST breaks; //List<CFGBreakNode>
	ANTLR3_LIST conts; //List<CFGBreakNode>
	ANTLR3_LIST cases; //List<Pair<Element, CFGNode>>
	_Bool haveDefault;
}
scope Function {
	ANTLR3_LIST rets; //List<CFGBreakNode>
	ANTLR3_LIST gotos; //List<Pair<String, CFGBreakNode>>
	ANTLR3_HASH_TABLE labels; //Map<String, CFGNode>
//	List<CFGPart> unreachables;
	ANTLR3_HASH_TABLE symbols; //Set<String>
	CFGNode lastStatement;
}

@header {
#include "common.h"
}

@members {
#if 0
	static Element defaultLabel = CFGEvaluator.defaultLabel;
	static Element falseLabel = CFGEvaluator.falseLabel;

	private static DocumentFactory xmlFactory =
		DocumentFactory.getInstance();

	private CFGPart createCFG(Element e) {
		CFGPart cfg = new CFGPart();
		CFGNode n = new CFGNode(e);
		cfg.setStartNode(n);
		cfg.setEndNode(n);
		return cfg;
	}

	private void addSwitchDefaultAssert(CFGBranchNode branch,
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
					NULL, cond, true, lineElem);
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
#endif
}

translationUnit returns [pANTLR3_LIST g] // List<CFG>
@init {
	$g = antlr3ListNew(ANTLR3_LIST_SIZE_HINT); //new LinkedList<CFG>();
}
	: ^(TRANSLATION_UNIT (externalDeclaration {
		if ($externalDeclaration.g != NULL)
			$g->add($g, $externalDeclaration.g, NULL);
	} )*)
	;

externalDeclaration returns [CFG g]
	: functionDefinition	{ $g=$functionDefinition.g; }
	| declaration
	;

functionDefinition returns [CFG g]
scope Function;
@init {
#if 0
	$Function::rets = new LinkedList<CFGBreakNode>();
	$Function::labels = new HashMap<String, CFGNode>();
	$Function::gotos = new LinkedList<Pair<String, CFGBreakNode>>();
//	$ Function::unreachables = new LinkedList<CFGPart>();
	$Function::symbols = new HashSet<String>();
#endif
}
	: ^(FUNCTION_DEFINITION declarationSpecifiers declarator declaration* compoundStatement) {
#if 0
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
/*		for (CFGPart cfg: $ Function::unreachables) {
			CFGNode start = cfg.getStartNode();
			if (start.getPredecessors().size() == 0 &&
					!start.getElement().getName().
						equals("breakStatement")) {
				System.err.println("Unreachable:");
				System.err.println(cfg.toStringGraph());
				System.err.println("\n============");
			}
		}*/

//		System.err.println($g.toStringGraph());
//		System.err.println($g.toDot());
#endif
	}
	;

declaration returns [CFGPart g]
@init {
//	$g = new CFGPart();
}
	: ^('typedef' declarationSpecifiers? initDeclarator*)
	| ^(DECLARATION declarationSpecifiers (initDeclarator {
/*			CFGPart idg = $initDeclarator.g;
			if (idg != NULL)
				$g.append(idg);*/
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
#if 0
		if ($Function->size($Function) > 0) /* skip globals */
			$Function::symbols.add($IDENTIFIER.text);
#endif
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
/*		if ($initializer.start != NULL) {
			$g = new CFGPart();
			$g.append(new CFGNode($initDeclarator.start.getElement()));
		}*/
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
#if 0
	LinkedList<CFGPart> cfgs = new LinkedList<CFGPart>();
	CFGPart cfg = new CFGPart();
	cfgs.add(cfg);
	_Bool isBreak = false;
#endif
}
@after {
#if 0
	$g = cfgs.removeFirst();
	/* add emptytStatement node if empty */
	if ($g.getStartNode() == NULL)
		$g.append(new CFGNode((Element)$compoundStatement.start.
					getElement().node(0)));
	$Function::lastStatement = cfg.getEndNode();

/*	for (CFGPart cfg1: cfgs) {
		if (cfg1.getStartNode().getPredecessors().size() == 0)
			$ Function::unreachables.add(cfg1);
	}*/
#endif
}
	: ^(COMPOUND_STATEMENT CS_END (declaration {
/*			if (!$declaration.g.isEmpty())
				cfg.append($declaration.g);*/
		} | functionDefinition |
		st=statement {
/*			if (isBreak) {
				cfg = new CFGPart();
				cfgs.add(cfg);
				isBreak = false;
			}
			if ($st.g.getEndNode() instanceof CFGBreakNode)
				isBreak = true;
			cfg.append($st.g);*/
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
	: ^(PARAMETER IDENTIFIER) //{ $Function::symbols.add($IDENTIFIER.text); }
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
/*		$g = $statement.g;
		$Function::labels.put($IDENTIFIER.text, $g.getStartNode());*/
	}
	| ^('case' expression statement) {
/*		$g = $statement.g;
		$IterSwitch::cases.add(new Pair($expression.start.getElement(),
			$g.getStartNode()));*/
	}
	| ^('default' statement) {
/*		$g = $statement.g;
		$IterSwitch::cases.add(new Pair(defaultLabel, $g.getStartNode()));
		$IterSwitch::haveDefault = true;*/
	}
	;

expressionStatement returns [CFGPart g]
	: ^(EXPRESSION_STATEMENT expression?) {
#if 0
		if ($expression.g == NULL) {
			/* emptyStatement is a child */
			$g = createCFG((Element)$expressionStatement.start.
				getElement().node(0));
		} else
			$g = $expression.g;
#endif
	}
	;

selectionStatement returns [CFGPart g]
	: selectionStatementIf		//{ $g = $selectionStatementIf.g; }
	| selectionStatementSwitch	//{ $g = $selectionStatementSwitch.g; }
	;

selectionStatementIf returns [CFGPart g]
@init {
/*	CFGNode s1Last;
	Triple<CFGNode,CFGNode,CFGNode> evalExpr;*/
}
	: ^('if' expression {
/*		evalExpr = CFGEvaluator.evaluateExpr(
			$expression.start.getElement());*/
	} s1=statement {
//		s1Last = $Function::lastStatement;
	} s2=statement?) {
#if 0
		$g = new CFGPart();
		CFGNode n1, n2 = new CFGJoinNode();
		n1 = ExprEvaluator.connect(evalExpr, $s1.g.getStartNode(),
				s2 == NULL ? n2 : $s2.g.getStartNode());
		$g.setStartNode(n1);
		$g.setEndNode(n2);
		if ($s1.start.getType() == COMPOUND_STATEMENT)
			s1Last.addEdge(n2);
		else
			$s1.g.getEndNode().addEdge(n2);
		if (s2 != NULL) {
			if ($s2.start.getType() == COMPOUND_STATEMENT)
				$Function::lastStatement.addEdge(n2);
			else
				$s2.g.getEndNode().addEdge(n2);
		}
#endif
	}
	;

selectionStatementSwitch returns [CFGPart g]
scope IterSwitch;
@init {
#if 0
	$IterSwitch::breaks = new LinkedList<CFGBreakNode>();
	$IterSwitch::cases = new LinkedList<Pair<Element, CFGNode>>();
	$IterSwitch::haveDefault = false;
	CFGBranchNode n;
#endif
}
	: ^('switch' expression {
//		n = new CFGBranchNode($expression.start.getElement());
	} statement) {
#if 0
		$g = new CFGPart();
		$g.setStartNode(n);
		CFGNode breakNode = new CFGJoinNode();
		$g.setEndNode(breakNode);
		for (Pair<Element, CFGNode> pair: $IterSwitch::cases) {
			Element caseLabel = pair.getFirst();
			if (caseLabel.getName().equals("default")) {
				addSwitchDefaultAssert(n, pair.getSecond(),
					caseLabel);
			} else {
				Element cond = xmlFactory.
					createElement("binaryExpression").
					addAttribute("op", "==");
				cond.add(n.getElement().createCopy());
				cond.add(caseLabel.createCopy());
				CFGEvaluator.addAssert(n, caseLabel,
						pair.getSecond(), cond, false,
						caseLabel);
			}
		}
		/* add default if not present */
		if (!$IterSwitch::haveDefault)
			addSwitchDefaultAssert(n, breakNode, n.getElement());

		/* backpatch break */
		for (CFGBreakNode c: $IterSwitch::breaks)
			c.addBreakEdge(breakNode);
		$Function::lastStatement.addEdge(breakNode);
#endif
	}
	;

iterationStatement returns [CFGPart g]
scope IterSwitch;
@init {
#if 0
	$g = new CFGPart();
	$IterSwitch::breaks = new LinkedList<CFGBreakNode>();
	$IterSwitch::conts = new LinkedList<CFGBreakNode>();
	CFGNode breakNode = NULL;
	CFGNode contNode = NULL;
	CFGNode lastStatement = NULL;
	CFGNode n1, n2;
	CFGPart statementCFG = NULL;
	int statementType = 0;
	Triple<CFGNode,CFGNode,CFGNode> evalExpr;
#endif
}
@after {
#if 0
	/* backpatch */
	for (CFGBreakNode n: $IterSwitch::breaks)
		n.addBreakEdge(breakNode);
	for (CFGBreakNode n: $IterSwitch::conts)
		n.addBreakEdge(contNode);
	if (statementType == COMPOUND_STATEMENT)
		lastStatement.addEdge(contNode);
	else
		statementCFG.getEndNode().addEdge(contNode);
#endif
}
	: ^('while' expression { /* to preserve sequential numbers */
/*		evalExpr = CFGEvaluator.evaluateExpr(
			$expression.start.getElement());*/
	} statement) {
#if 0
		statementCFG = $statement.g;
		breakNode = new CFGJoinNode();
		contNode = ExprEvaluator.connect(evalExpr,
				statementCFG.getStartNode(), breakNode);
		$g.setStartNode(contNode);
		$g.setEndNode(breakNode);
		statementType = $statement.start.getType();
		lastStatement = $Function::lastStatement;
#endif
	}
	| ^('do' statement {
//		lastStatement = $Function::lastStatement;
	} expression) {
#if 0
		statementCFG = $statement.g;
		breakNode = new CFGJoinNode();
		$g.setStartNode(statementCFG.getStartNode());
		$g.setEndNode(breakNode);
		contNode = CFGEvaluator.evaluateExprConnect(
				$expression.start.getElement(),
				statementCFG.getStartNode(), breakNode);
		statementType = $statement.start.getType();
#endif
	}
	| ^(f='for' declaration? (^(E1 e1=expression))? ^(E2 e2=expression?) {
#if 0
		n1 = new CFGNode($e1.g == NULL ? newEmptyStatement($f) :
				$e1.start.getElement());
		breakNode = new CFGJoinNode();
		$g.setStartNode(n1);
		$g.setEndNode(breakNode);
		evalExpr = CFGEvaluator.evaluateExpr($e2.g == NULL ?
			newEmptyStatement($f) : $e2.start.getElement());
#endif
	} ^(E3 e3=expression?) statement) {
#if 0
		statementCFG = $statement.g;
		n2 = ExprEvaluator.connect(evalExpr,
				statementCFG.getStartNode(), breakNode);
		n1.addEdge(n2);

		contNode = new CFGNode($e3.g == NULL ?
				newEmptyStatement($f) :
				$e3.start.getElement());
		contNode.addEdge(n2);

		statementType = $statement.start.getType();
		lastStatement = $Function::lastStatement;
#endif
	}
	;

jumpStatement returns [CFGPart g]
@init {
#if 0
	$g = new CFGPart();
	CFGBreakNode n = new CFGBreakNode($jumpStatement.start.getElement());
	$g.setStartNode(n);
	$g.setEndNode(n);
#endif
}
	: ^('goto' IDENTIFIER) {
/*		$Function::gotos.add(
			new Pair<String, CFGBreakNode>($IDENTIFIER.text, n));*/
	}
	| ^('goto' XU expression)
	| 'continue' {
#if 0
		/* conts (opposing to breaks) are not there for switch,
		   find deeper in the stack */
		int a = 0;
		while ($IterSwitch[-a]::conts == NULL)
			a++;
		$IterSwitch[-a]::conts.add(n);
#endif
	}
	| 'break' //{ $IterSwitch::breaks.add(n); }
	| ^('return' expression?) //{ $Function::rets.add(n); }
	;

asmStatement returns [CFGPart g]
	: ASM		;//{ $g = createCFG($asmStatement.start.getElement()); }	;

/* STATEMENTS END */

/* EXPRESSIONS */

expression returns [CFGPart g]
@init {
#if 0
	CFGNode trueNode = NULL;
	Triple<CFGNode,CFGNode,CFGNode> evalExpr;
	$g = NULL;
#endif
}
@after {
#if 0
	if ($g == NULL)
		$g = createCFG($expression.start.getElement());
#endif
}
	: ^(ASSIGNMENT_EXPRESSION assignmentOperator e1=expression e2=expression) { /*$g = $e2.g; do nothing so far XXX to be fixed */  }
	| ^(CONDITIONAL_EXPRESSION ^(E1 e1=expression) {
/*		evalExpr = CFGEvaluator.evaluateExpr(
			$e1.start.getElement());*/
	} ^(E2 e2=expression?) {
/*		if (e2 == NULL)
			trueNode = new CFGNode($e1.start.getElement());*/
	} ^(E3 e3=expression)) {
#if 0
		$g = new CFGPart();
		CFGNode n1 = ExprEvaluator.connect(evalExpr,
				trueNode == NULL ? $e2.g.getStartNode() :
				trueNode, $e3.g.getStartNode());
		CFGNode n2 = new CFGJoinNode();
		$g.setStartNode(n1);
		$g.setEndNode(n2);
		if (trueNode == NULL)
			$e2.g.getEndNode().addEdge(n2);
		 else
			trueNode.addEdge(n2);
		$e3.g.getEndNode().addEdge(n2);
#endif
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

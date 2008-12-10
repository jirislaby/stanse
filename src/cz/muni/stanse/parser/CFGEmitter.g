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

scope Iteration {
	Set<CFGBreakNode> breaks;
	Set<CFGNode> conts;
}
scope Function {
	Set<CFGBreakNode> rets;
}

@header {
package cz.muni.stanse.parser;

import java.io.IOException;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

import org.dom4j.Element;

import cz.muni.stanse.utils.XMLAlgo;
}
@members {
	private CFG createCFG() {
		CFG cfg = new CFG();
		CFGNode n = new CFGNode();
		cfg.setStartNode(n);
		cfg.setEndNode(n);
		return cfg;
	}
	private CFG createCFG(Element e) {
		CFG cfg = new CFG();
		CFGNode n = new CFGNode(e);
		cfg.setStartNode(n);
		cfg.setEndNode(n);
		return cfg;
	}
}

translationUnit returns [Set<FunctionCFG> g]
@init {
	$g = new LinkedHashSet<FunctionCFG>();
}
	: ^(TRANSLATION_UNIT (externalDeclaration {
		if ($externalDeclaration.g != null)
			$g.add($externalDeclaration.g);
	} )*)
	;

externalDeclaration returns [FunctionCFG g]
	: functionDefinition	{ $g=$functionDefinition.g; }
	| declaration
	;

functionDefinition returns [FunctionCFG g]
scope Function;
@init {
	$Function::rets = new HashSet<CFGBreakNode>();
}
	: ^(FUNCTION_DEFINITION declarationSpecifiers declarator declaration* compoundStatement) { $g = FunctionCFG.createFromCFG($compoundStatement.g, $functionDefinition.start.getElement());
		for (CFGBreakNode n: $Function::rets)
			n.addBreakEdge($g.getEndNode());
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

compoundStatement returns [CFG g]
@init {
	LinkedList<CFG> cfgs = new LinkedList<CFG>();
	CFG cfg = createCFG();
	cfgs.add(cfg);
	boolean isBreak = false;
}
@after {
	$g = cfgs.getFirst();
	$g.append(new CFGNode());
}
	: ^(COMPOUND_STATEMENT (declaration |
		fd=functionDefinition {cfgs.add($fd.g);} |
		st=statement {
			if (isBreak) {
				System.err.println("Unreachable:");
				XMLAlgo.outputXML($st.start.getElement());
				System.err.println("\n============");
				cfg = createCFG();
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

statement returns [CFG g]
	: labeledStatement	{ $g=$labeledStatement.g; }
	| compoundStatement	{ $g=$compoundStatement.g; }
	| expressionStatement	{ $g=$expressionStatement.g; }
	| selectionStatement	{ $g=$selectionStatement.g; }
	| iterationStatement	{ $g=$iterationStatement.g; }
	| jumpStatement		{ $g=$jumpStatement.g; }
	| asmStatement		{ $g=$asmStatement.g; }
	;

labeledStatement returns [CFG g]
	: ^(LABEL IDENTIFIER statement)		{ $g = $statement.g; }
	| ^('case' expression statement)	{ $g = $statement.g; }
	| ^('default' statement)		{ $g = $statement.g; }
	;

expressionStatement returns [CFG g]
	: ^(EXPRESSION_STATEMENT expression?) {
		if ($expression.g == null) {
			/* emptyStatement is a child */
			$g = createCFG((Element)$expressionStatement.start.
				getElement().node(0));
		} else
			$g = $expression.g;
	}
	;

selectionStatement returns [CFG g]
scope Iteration;
@init {
	$Iteration::breaks = new HashSet<CFGBreakNode>();
}
	: ^('if' expression s1=statement s2=statement?) {
		$g = new CFG();
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
	| ^('switch' expression statement) {
		$g = new CFG();
		CFGNode n = new CFGNode($expression.start.getElement());
		$g.setStartNode(n);
		$g.setEndNode(n);
		$g.append($statement.g);
//		System.err.println("---");
		for (CFGBreakNode c: $Iteration::breaks) {
			c.addBreakEdge($g.getEndNode());
//			System.err.println(c);
		}
	}
	;

iterationStatement returns [CFG g]
scope Iteration;
@init {
	$g = new CFG();
	$Iteration::breaks = new HashSet<CFGBreakNode>();
	$Iteration::conts = new HashSet<CFGNode>();
	CFGNode breakNode = null;
	CFGNode contNode;
}
@after {
	/* backpatch */
	for (CFGBreakNode n: $Iteration::breaks)
		n.addBreakEdge(breakNode);
}
	: ^('while' expression statement) {
		/* fork */
		CFGNode n1 = new CFGBranchNode($expression.start.getElement());
		/* junction */
		breakNode = new CFGNode();
		$g.setStartNode(n1);
		$g.setEndNode(breakNode);
		n1.addEdge($statement.g.getStartNode());
		/* true */
		$statement.g.getEndNode().addEdge(n1);
		/* false */
		n1.addEdge(breakNode);
	}
	| ^('do' statement expression) {
		/* fork */
		CFGNode n1 = new CFGBranchNode($expression.start.getElement());
		/* junction */
		breakNode = new CFGNode();
		$g.setStartNode($statement.g.getStartNode());
		$g.setEndNode(breakNode);
		$statement.g.getEndNode().addEdge(n1);
		/* true */
		n1.addEdge($statement.g.getStartNode());
		/* false */
		n1.addEdge(breakNode);
	}
	| ^('for' declaration? (^(E1 e1=expression))? ^(E2 e2=expression?) ^(E3 e3=expression?) statement) {
		breakNode = new CFGNode();
	}
	;

jumpStatement returns [CFG g]
@init {
	$g = new CFG();
	CFGBreakNode n = new CFGBreakNode($jumpStatement.start.getElement());
	$g.setStartNode(n);
	$g.setEndNode(n);
}
	: ^('goto' IDENTIFIER) {
	}
	| ^('goto' XU expression) {
	}
	| 'continue' {
		/* conts (opposing to breaks) are not there for switch,
		   find deeper in the stack */
		int a = 0;
		while ($Iteration[-a]::conts == null)
			a++;
		$Iteration[-a]::conts.add(n);
	}
	| 'break' {
		$Iteration::breaks.add(n);
	}
	| ^('return' expression?) {
		$Function::rets.add(n);
	}
	;

asmStatement returns [CFG g]
	: ASM		{ $g = createCFG($asmStatement.start.getElement()); }	;

/* STATEMENTS END */

/* EXPRESSIONS */

expression returns [CFG g]
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

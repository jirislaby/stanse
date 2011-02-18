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

scope Private {
	void *priv;

	int symbolsEnabled;
	int isFunParam;
	unsigned int uniqCnt;
	pANTLR3_LIST params;
	pANTLR3_LIST paramsOld;
}

scope Symbols {
	pANTLR3_LIST variables;
	pANTLR3_LIST variablesOld;
}

@header {
#include "common.h"

#define LIST_SIZE	20

#define PRIV	(SCOPE_TOP(Private)->priv)
}

@members {
/* configuration */
static const int uniqueVariables = 1;
static const int uniqueVariablesDebug = 0;

	static int lastIndexOfStr(pANTLR3_LIST list, const char *str)
	{
		ANTLR3_UINT32 a, size = list->size(list);
		for (a = size; a > 0; a--)
			if (!strcmp(str, list->get(list, a)))
				return a;
		return -1;
	}
	static int indexOfStr(pANTLR3_LIST list, const char *str)
	{
		ANTLR3_UINT32 a, size = list->size(list);
		for (a = 1; a <= size; a++)
			if (!strcmp(str, list->get(list, a)))
				return a;
		return -1;
	}

	static void dumpList(const pANTLR3_LIST list)
	{
		ANTLR3_UINT32 a, size = list->size(list);
		putchar('[');
		for (a = 1; a <= size; a++)
			printf("\%s\%s", (char *)list->get(list, a),
					a == size ? "" : ", ");
		putchar(']');
	}

	static void ANTLR3_CDECL free_symbols(SCOPE_TYPE(Symbols) symbols)
	{
		symbols->variables->free(symbols->variables);
		symbols->variablesOld->free(symbols->variablesOld);
	}
	static void alloc_symbols(SCOPE_TYPE(Symbols) symbols)
	{
		symbols->variablesOld = antlr3ListNew(LIST_SIZE);
		symbols->variables = antlr3ListNew(LIST_SIZE);
		symbols->variablesOld->table->doStrdup = ANTLR3_FALSE;
		symbols->variables->table->doStrdup = ANTLR3_FALSE;
		symbols->free = free_symbols;
	}

	static void ANTLR3_CDECL free_private(SCOPE_TYPE(Private) priv)
	{
		priv->params->free(priv->params);
		priv->paramsOld->free(priv->paramsOld);
	}
	static void alloc_params(SCOPE_TYPE(Private) priv)
	{
		priv->paramsOld = antlr3ListNew(LIST_SIZE);
		priv->params = antlr3ListNew(LIST_SIZE);
		priv->paramsOld->table->doStrdup = ANTLR3_FALSE;
		priv->params->table->doStrdup = ANTLR3_FALSE;
		priv->free = free_private;
	}

	static void ANTLR3_CDECL free_string(void *entry)
	{
		free(entry);
	}

	static char *renameVariable(pASTEmitter ctx, const pANTLR3_STRING old, int *renamed) {
		SCOPE_TYPE(Private) priv = SCOPE_TOP(Private);
		const char *cold = (char *)old->chars;
		ANTLR3_UINT32 oldlen = old->len;
		char *new_;

		*renamed = 0;

		if (!uniqueVariables)
			return strdup(cold);

		if (SCOPE_SIZE(Symbols) == 1 && !priv->isFunParam) { /* forward decls */
			SCOPE_TYPE(Symbols) symb = SCOPE_TOP(Symbols);
			int idx = lastIndexOfStr(symb->variablesOld, cold);
			if (idx >= 0)
				return strdup(symb->variables->get(symb->variables, idx));
		}

		new_ = malloc(oldlen + 1 + 1 + 10); /* \0, _ and number */
		if (!new_)
			return NULL;
		memcpy(new_, cold, oldlen);
		new_[oldlen] = 0;

		while (1) {
			int a;
			for (a = SCOPE_SIZE(Symbols) - 1; a >= 0; a--) {
				SCOPE_TYPE(Symbols) curr = SCOPE_INSTANCE(Symbols, a);
				if (indexOfStr(curr->variables, new_) >= 0)
					break;
			}
			if (a < 0)
				break;
			sprintf(new_ + oldlen, "_\%u", priv->uniqCnt++);
			*renamed = 1;
		}

		return new_;
	}

	static void pushSymbol(pASTEmitter ctx, char *old, char *new_) {
		SCOPE_TYPE(Private) priv = SCOPE_TOP(Private);
		SCOPE_TYPE(Symbols) symb = SCOPE_TOP(Symbols);

		if (!uniqueVariables || !priv->symbolsEnabled)
			goto free_new;
		if (priv->isFunParam) {
			priv->paramsOld->add(priv->paramsOld, old, NULL);
			/* we remove them in process, so they are not freed, if we need them */
			priv->params->add(priv->params, new_, free_string);
			if (uniqueVariablesDebug > 2) {
				putchar('P');
				dumpList(priv->paramsOld);
				putchar('\n');
				putchar('P');
				dumpList(priv->params);
				printf(" added: \%s\n", new_);
			}
			return;
		}
		/* forward decl already pushed one */
		if (SCOPE_SIZE(Symbols) == 1 && indexOfStr(symb->variables, new_) >= 0)
			goto free_new;

		symb->variablesOld->add(symb->variablesOld, old, NULL);
		symb->variables->add(symb->variables, new_, free_string);

		if (uniqueVariablesDebug) {
			int a;
			putchar('S');
			for (a = 0; a < SCOPE_SIZE(Symbols) - 1; a++) {
				dumpList($Symbols[a]::variablesOld);
				putchar(' ');
			}
			dumpList($Symbols::variablesOld);
			putchar('\n');
			putchar('S');
			for (a = 0; a < SCOPE_SIZE(Symbols) - 1; a++) {
				dumpList($Symbols[a]::variables);
				putchar(' ');
			}
			dumpList($Symbols::variables);
			printf(" added: \%s\n", new_);
		}
		return;
free_new:
		free(new_);
	}

	static const char *findVariable(pASTEmitter ctx, const pANTLR3_STRING old) {
		const char *cold = (char *)old->chars;
		int a;

		if (!uniqueVariables)
			return cold;
		/* find topmost variable */
		for (a = SCOPE_SIZE(Symbols) - 1; a >= 0; a--) {
			int idx = lastIndexOfStr($Symbols[a]::variablesOld, cold);
			if (idx >= 0)
				return $Symbols[a]::variables->get($Symbols[a]::variables, idx);
		}
		/* typedefs, enums, etc. */
		return cold;
	}

	static my_jobject createId(pASTEmitter ctx, pANTLR3_BASE_TREE tree,
			pANTLR3_STRING str, int isTypedef)
	{
		SCOPE_TYPE(Private) priv = SCOPE_TOP(Private)->priv;
		char *cstr = (char *)str->chars;
		my_jobject id;
		char *newName;
		int renamed = 0;

		if (!isTypedef) {
			newName = renameVariable(ctx, str, &renamed);
			if (!newName)
				parser_die(priv, "not enough memory");
		} else
			newName = cstr;

		id = newId(priv, newName);

		if (tree)
			addChild(priv, tree, id);

		if (!isTypedef) {
			if (renamed)
				setAttr(priv, id, "oldId", cstr);

			pushSymbol(ctx, cstr, newName);
		}

		return id;
	}

	static void processFunParams(pASTEmitter ctx)
	{
		pANTLR3_LIST p1 = $Private::paramsOld;
		pANTLR3_LIST p2 = $Private::params;
		pANTLR3_LIST s1 = $Symbols::variablesOld;
		pANTLR3_LIST s2 = $Symbols::variables;
		ANTLR3_UINT32 i, size = p1->size(p1);
		if (uniqueVariablesDebug > 2) {
			putchar('Y');
			dumpList(p1);
			putchar('\n');
			putchar('Y');
			dumpList(p2);
			putchar('\n');
		}
		for (i = 1; i <= size; i++) {
			char *s;
			s = p1->remove(p1, i);
			s1->add(s1, s, NULL);
			s = p2->remove(p2, i);
			s2->add(s2, s, free_string);
		}
		if (uniqueVariablesDebug > 2) {
			putchar('X');
			dumpList(s1);
			putchar('\n');
			putchar('X');
			dumpList(s2);
			putchar('\n');
		}
	}

	static void clearFunParams(SCOPE_TYPE(Private) priv) {
		/* workaround: keys get mangled otherwise */
		priv->paramsOld->free(priv->paramsOld);
		priv->params->free(priv->params);
		alloc_params(priv);
		if (uniqueVariablesDebug > 2)
			puts("P pruned");
	}

	static my_jobject fill_attr(void *priv, my_jobject obj,
			pANTLR3_BASE_TREE start)
	{
		if (!obj)
			abort();
		setLine(priv, obj, start->getLine(start));
		setColumn(priv, obj, start->getCharPositionInLine(start));
		return obj;
	}

	static my_jobject newNode1(void *priv, my_jobject (*creator)(void *),
			my_jobject fi)
	{
		my_jobject ret = creator(priv);
		addChild(priv, ret, fi);
		return ret;
	}
	static my_jobject newNode2(void *priv, my_jobject (*creator)(void *),
			my_jobject fi, my_jobject se)
	{
		my_jobject ret = creator(priv);
		addChild(priv, ret, fi);
		addChild(priv, ret, se);
		return ret;
	}
}

translationUnit[void *priv] returns [my_jobject d]
scope Private;
@init {
	$Private::priv = $priv;
	$Private::symbolsEnabled = 1;
	$Private::isFunParam = 0;
	$Private::uniqCnt = 0;
	alloc_params(SCOPE_TOP(Private));
}
	: translationUnit1	{ $d = $translationUnit1.d; }
	;


translationUnit1 returns [my_jobject d]
scope Symbols;
@init {
	alloc_symbols(SCOPE_TOP(Symbols));
	$d = newTranslationUnit(PRIV);
}
	: ^(TRANSLATION_UNIT (eds=externalDeclaration { addChild(PRIV, $d, $eds.d); } )*)
	;

externalDeclaration returns [my_jobject d]
@init	{ $d = newExternalDeclaration(PRIV); }
@after	{
	fill_attr(PRIV, $d, $externalDeclaration.start);
	clearFunParams(SCOPE_TOP(Private));
}
	: functionDefinition	{ addChild(PRIV, $d, $functionDefinition.d); }
	| declaration		{ addChild(PRIV, $d, $declaration.d); }
	;

functionDefinition returns [my_jobject d]
@init {
	$d = newFunctionDefinition(PRIV);
//	functionDefinition.start.setElement(e);*/
}
@after	{ fill_attr(PRIV, $d, $functionDefinition.start); }
	: ^(FUNCTION_DEFINITION declarationSpecifiers declarator {
		addChild(PRIV, $d, $declarationSpecifiers.d);
		addChild(PRIV, $d, $declarator.d);
	} functionDefinitionBody[$d])
	;

/* we need a scope even here */
functionDefinitionBody[my_jobject fd]
scope Symbols;
@init {
	alloc_symbols(SCOPE_TOP(Symbols));
	processFunParams(CTX);
}
	: (d=declaration {addChild(PRIV, $fd, $d.d);})* compoundStatement {
		addChild(PRIV, $fd, $compoundStatement.d);
		pANTLR3_BASE_TREE t = $compoundStatement.start;
		t = t->getChild(t, 0);
		setEndLine(PRIV, $fd, t->getLine(t));
	}
	;

declaration returns [my_jobject d]
@init	{ $d = newDeclaration(PRIV); my_jobject dsd = NULL; }
@after	{ fill_attr(PRIV, $d, $declaration.start); }
	: ^('typedef' (ds=declarationSpecifiers {dsd=$ds.d;})? {
		if (!dsd)
			dsd = newDeclarationSpecifiers(PRIV);
		addChild(PRIV, $d, dsd);
		setAttr(PRIV, dsd, "storageClass", "typedef");
	} (id=initDeclarator {addChild(PRIV, $d, $id.d);})*)
	| ^(DECLARATION ds=declarationSpecifiers {addChild(PRIV, $d, $ds.d);}
			(id=initDeclarator {addChild(PRIV, $d, $id.d);})*)
	;

declarationSpecifiers returns [my_jobject d]
@init	{ $d = newDeclarationSpecifiers(PRIV); }
@after	{ fill_attr(PRIV, $d, $declarationSpecifiers.start); }
	: ^(DECLARATION_SPECIFIERS
		^(XTYPE_SPECIFIER (ts=typeSpecifier {addChild(PRIV, $d, $ts.d);})*)
		^(XTYPE_QUALIFIER (tq=typeQualifier {setAttr(PRIV, $d, $tq.s, "1");})*)
		^(XSTORAGE_CLASS (sc=storageClassSpecifier {setAttr(PRIV, $d, "storageClass", $sc.s);} |
				  fs=functionSpecifier {setAttr(PRIV, $d, "function", $fs.s);})*))
	;

declarator returns [my_jobject d]
@init	{ $d = newDeclarator(PRIV); }
@after	{ fill_attr(PRIV, $d, $declarator.start); }
	: ^(DECLARATOR pointer[$d]? directDeclarator[$d])
	;

directDeclarator[my_jobject d]
	: IDENTIFIER { createId(CTX, $d, $IDENTIFIER.text, (unsigned long)$IDENTIFIER->u); }
	| declarator { addChild(PRIV, $d, $declarator.d); }
	| directDeclarator1[$d] /* XXX is here the + needed? */
	;

directDeclarator1[my_jobject d]
@init { my_jobject d1; }
	: ^(ARRAY_DECLARATOR (directDeclarator[$d]) {
		d1 = newArrayDecl(PRIV);
		addChild(PRIV, $d, d1);
	} ('static' {setAttr(PRIV, d1, "static", "1");} | asterisk='*' {setAttr(PRIV, d1, "asterisk", "1");})?
	  (tq=typeQualifier {setAttr(PRIV, d1, $tq.s, "1");})*
	  (e=expression {addChild(PRIV, d1, $e.d);})?)
	| ^(FUNCTION_DECLARATOR (IDENTIFIER { /* we need to process the id before params */
		createId(CTX, $d, $IDENTIFIER.text, 0);
	}|declarator {addChild(PRIV, $d, $declarator.d);}) {
		$Private::isFunParam = 1;
		d1 = newFunctionDecl(PRIV);
		addChild(PRIV, $d, d1);
	} (pl=parameterTypeList[d1]|(i=identifier {addChild(PRIV, d1, $i.d);})*)) {
		$Private::isFunParam = 0;
	}
	;

initDeclarator returns [my_jobject d]
@init	{ $d = newInitDeclarator(PRIV); }
@after	{ fill_attr(PRIV, $d, $initDeclarator.start); }
	: ^(INIT_DECLARATOR de=declarator {addChild(PRIV, $d, $de.d);}
			    (in=initializer {addChild(PRIV, $d, $in.d);})?)
//		initDeclarator.start.setElement(e);
	;

initializer returns [my_jobject d]
@init	{ $d = newInitializer(PRIV); }
@after	{ fill_attr(PRIV, $d, $initializer.start); }
	: ^(INITIALIZER expression)	{ addChild(PRIV, $d, $expression.d); }
	| INITIALIZER /* just <initializer/> */
	| ^(INITIALIZER initializerList[$d])
	;

initializerList[my_jobject d]
	: ((de=designator {addChild(PRIV, $d, $de.d);})* initializer {
		addChild(PRIV, $d, $initializer.d);
	})+
	;

designator returns [my_jobject d]
@init   { $d = newDesignator(PRIV); }
@after  { fill_attr(PRIV, $d, $designator.start); }
	: ^(DESIGNATOR ^('...' e1=expression e2=expression)) {
		my_jobject expr = newExpression(PRIV);
		addChild(PRIV, expr, newNode2(PRIV, newRangeExpression, $e1.d, $e2.d));
		addChild(PRIV, $d, expr);
	}
	| ^(DESIGNATOR ^(BRACKET_DESIGNATOR expression)) { addChild(PRIV, $d, $expression.d); }
	| ^(DESIGNATOR IDENTIFIER)	{ addChild(PRIV, $d, newId(PRIV, (char *)$IDENTIFIER.text->chars)); }
	| IDENTIFIER			{ addChild(PRIV, $d, newId(PRIV, (char *)$IDENTIFIER.text->chars)); }
	;

compoundStatement returns [my_jobject d]
scope Symbols;
@init	{
	alloc_symbols(SCOPE_TOP(Symbols));
	$d = newCompoundStatement(PRIV);
	int cnt = 0;
}
@after	{ fill_attr(PRIV, $d, $compoundStatement.start); }
	: ^(COMPOUND_STATEMENT CS_END (dc=declaration {cnt++; addChild(PRIV, $d, $dc.d);} |
				       fd=functionDefinition {cnt++; addChild(PRIV, $d, $fd.d);} |
				       st=statement {cnt++; addChild(PRIV, $d, $st.d);})*) {
		if (cnt == 0)
			addChild(PRIV, $d, fill_attr(PRIV, newEmptyStatement(PRIV), $compoundStatement.start));
//		compoundStatement.start.setElement(e);*/
	}
	;

parameterTypeList[my_jobject d]
	: (p=parameterDeclaration {addChild(PRIV, $d, $p.d);})+
	  (VARARGS {
		my_jobject param = newParameter(PRIV);
		setAttr(PRIV, param, "varArgs", "1");
		addChild(PRIV, $d, param);
	})?
	;

parameterDeclaration returns [my_jobject d]
@init	{ $d = newParameter(PRIV); }
@after	{ fill_attr(PRIV, $d, $parameterDeclaration.start); }
	: ^(PARAMETER ds=declarationSpecifiers {addChild(PRIV, $d, $ds.d);}
		      (de=declarator {addChild(PRIV, $d, $de.d);})?
		      (ad=abstractDeclarator {addChild(PRIV, $d, $ad.d);})?)
	;

abstractDeclarator returns [my_jobject d]
@init	{ $d = newAbstractDeclarator(PRIV); }
@after	{ fill_attr(PRIV, $d, $abstractDeclarator.start); }
	: ^(ABSTRACT_DECLARATOR pointer[$d] directAbstractDeclarator[$d]?)
	| ^(ABSTRACT_DECLARATOR directAbstractDeclarator[$d])
	;

directAbstractDeclarator[my_jobject d]
	: ad=abstractDeclarator {addChild(PRIV, $d, $ad.d);}
	  (a=arrayOrFunctionDeclarator {addChild(PRIV, $d, $a.d);})*
	| (a=arrayOrFunctionDeclarator {addChild(PRIV, $d, $a.d);})+
	;

arrayOrFunctionDeclarator returns [my_jobject d]
@after	{ fill_attr(PRIV, $d, $arrayOrFunctionDeclarator.start); }
	: ^(ARRAY_DECLARATOR { $d = newArrayDecl(PRIV); }
			((e=expression {addChild(PRIV, $d, $e.d);})? |
			 '*' { setAttr(PRIV, $d, "asterisk", "1"); }))
	| ^(FUNCTION_DECLARATOR { $d = newFunctionDecl(PRIV); } parameterTypeList[$d]?)
	;

identifier returns [my_jobject d]
	: ^(PARAMETER IDENTIFIER)	{
		$d = fill_attr(PRIV, createId(CTX, NULL, $IDENTIFIER.text, 0), $identifier.start);
	}
	;

/* TYPES */

typeName returns [my_jobject d]
@init	{ $d = newTypeName(PRIV); }
@after	{ fill_attr(PRIV, $d, $typeName.start); }
	: ^(TYPE_NAME (s=specifier {addChild(PRIV, $d, $s.d);} |
		       q=qualifier {setAttr(PRIV, $d, $q.q, "1");})+
		      (ad=abstractDeclarator {addChild(PRIV, $d, $ad.d);})?)
//		for (Element el: typeNormalize(tss))
	;

specifier returns [my_jobject d]
	: ^(XTYPE_SPECIFIER typeSpecifier)	{ $d = $typeSpecifier.d; }
	;

qualifier returns [const char *q]
	: ^(XTYPE_QUALIFIER typeQualifier)	{ $q = $typeQualifier.s; }
	;

typeQualifier returns [const char *s]
	: 'const'	{ $s = "const"; }
	| 'restrict'	{ $s = "restrict"; }
	| 'volatile'	{ $s = "volatile"; }
	;

typeSpecifier returns [my_jobject d]
@init	{ $d = newTypeSpecifier(PRIV); }
@after	{ fill_attr(PRIV, $d, $typeSpecifier.start); }
	: ^(BASETYPE 'void')	{ addChild(PRIV, $d, newBaseType(PRIV, "void")); }
	| ^(BASETYPE 'char')	{ addChild(PRIV, $d, newBaseType(PRIV, "char")); }
	| ^(BASETYPE 'short')	{ addChild(PRIV, $d, newBaseType(PRIV, "short")); }
	| ^(BASETYPE 'int')	{ addChild(PRIV, $d, newBaseType(PRIV, "int")); }
	| ^(BASETYPE 'long')	{ addChild(PRIV, $d, newBaseType(PRIV, "long")); }
	| ^(BASETYPE 'float')	{ addChild(PRIV, $d, newBaseType(PRIV, "float")); }
	| ^(BASETYPE 'double')	{ addChild(PRIV, $d, newBaseType(PRIV, "double")); }
	| ^(BASETYPE SIGNED)	{ addChild(PRIV, $d, newBaseType(PRIV, "signed")); }
	| ^(BASETYPE 'unsigned'){ addChild(PRIV, $d, newBaseType(PRIV, "unsigned")); }
	| ^(BASETYPE '_Bool')	{ addChild(PRIV, $d, newBaseType(PRIV, "_Bool")); }
	| ^(BASETYPE COMPLEX)	{ addChild(PRIV, $d, newBaseType(PRIV, "_Complex")); }
	| ^(BASETYPE XID)	{ parser_die(PRIV, "XID"); }
	| ^(BASETYPE '_Imaginary')	{ addChild(PRIV, $d, newBaseType(PRIV, "_Imaginary")); }
	| structOrUnionSpecifier	{ addChild(PRIV, $d, $structOrUnionSpecifier.d); }
	| enumSpecifier		{ addChild(PRIV, $d, $enumSpecifier.d); }
	| typedefName		{
		my_jobject td = newNode1(PRIV, newTypedef, newId(PRIV, (char *)$typedefName.s->chars));
		addChild(PRIV, $d, td);
	}
	| typeofSpecifier	{ addChild(PRIV, $d, $typeofSpecifier.d); }
	;

structOrUnionSpecifier returns [my_jobject d]
@init {
	$Private::symbolsEnabled = 0;
}
	: ^(sou=structOrUnion {
		if ($sou.un)
			$d = newUnion(PRIV);
		else
			$d = newStruct(PRIV);
	} ^(XID (IDENTIFIER {setAttr(PRIV, $d, "id", (char *)$IDENTIFIER.text->chars);})?)
	(sd=structDeclaration {addChild(PRIV, $d, $sd.d);})*) {
		$Private::symbolsEnabled = 1;
	}
	;

structOrUnion returns [char un]
	: 'struct'	{ $un = 0; }
	| 'union'	{ $un = 1; }
	;

structDeclaration returns [my_jobject d]
@init	{ $d = newStructDeclaration(PRIV); }
@after	{ fill_attr(PRIV, $d, $structDeclaration.start); }
	: ^(STRUCT_DECLARATION (s=specifier {addChild(PRIV, $d, $s.d);} |
				q=qualifier {setAttr(PRIV, $d, $q.q, "1");})+
			       (sd=structDeclarator {addChild(PRIV, $d, $sd.d);})*)
//		for (Element el: typeNormalize(tss))
	;

structDeclarator returns [my_jobject d]
@init	{ $d = newStructDeclarator(PRIV); }
@after	{ fill_attr(PRIV, $d, $structDeclarator.start); }
	: ^(STRUCT_DECLARATOR (de=declarator {addChild(PRIV, $d, $de.d);})?
			      (ex=expression {addChild(PRIV, $d, $ex.d);})?)
	;

enumSpecifier returns [my_jobject d]
@init	{ $d = newEnum(PRIV); }
@after	{ fill_attr(PRIV, $d, $enumSpecifier.start); }
	: ^('enum' (^(XID IDENTIFIER {setAttr(PRIV, $d, "id", (char *)$IDENTIFIER.text->chars);}))?
		   (en=enumerator {addChild(PRIV, $d, $en.d);})*)
	;

enumerator returns [my_jobject d]
@after	{ fill_attr(PRIV, $d, $enumerator.start); }
	: ^(ENUMERATOR IDENTIFIER {
		$d = newEnumerator(PRIV, (char *)$IDENTIFIER.text->chars);
	} (e=expression {addChild(PRIV, $d, $e.d);})?)
	;

typedefName returns [pANTLR3_STRING s]
	: IDENTIFIER	{ $s = $IDENTIFIER.text; }
	;

typeofSpecifier returns [my_jobject d]
@init	{ $d = newTypeOfSpecifier(PRIV); }
@after	{ fill_attr(PRIV, $d, $typeofSpecifier.start); }
	: ^(TYPEOF expression)	{ addChild(PRIV, $d, $expression.d); }
	| ^(TYPEOF typeName)	{ addChild(PRIV, $d, $typeName.d); }
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

pointer[my_jobject d]
@init   {
	my_jobject ptr = newPointer(PRIV);
	addChild(PRIV, $d, ptr);
}
@after  { fill_attr(PRIV, ptr, $pointer.start); }
	: ^(POINTER (tq=typeQualifier {setAttr(PRIV, ptr, $tq.s, "1");})* pointer[$d]?)
	;

/* TYPES END */

/* STATEMENTS */

statement returns [my_jobject d]
@init { $d = NULL; }
@after	{
	if ($d == NULL)
		printf("\%s: \%s\n", __func__, $statement.start->getText($statement.start)->chars);
	fill_attr(PRIV, $d, $statement.start);
}
	: labeledStatement	{ $d=$labeledStatement.d; }
	| compoundStatement	{ $d=$compoundStatement.d; }
	| expressionStatement	{ $d=$expressionStatement.d; }
	| selectionStatement	{ $d=$selectionStatement.d; }
	| iterationStatement	{ $d=$iterationStatement.d; }
	| jumpStatement		{ $d=$jumpStatement.d; }
	| asmStatement		{ $d=$asmStatement.d; }
	;

labeledStatement returns [my_jobject d]
@after	{ fill_attr(PRIV, $d, $labeledStatement.start); }
	: ^(LABEL IDENTIFIER statement) {
		$d = newLabelStatement(PRIV, (char *)$IDENTIFIER.text->chars);
		addChild(PRIV, $d, $statement.d);
	}
	| ^('case' expression statement) { $d = newNode2(PRIV, newCaseLabelStatement, $expression.d, $statement.d); }
	| ^('default' statement) { $d = newNode1(PRIV, newDefaultLabelStatement, $statement.d); }
	;

expressionStatement returns [my_jobject d]
@init	{ $d = newExpressionStatement(PRIV); my_jobject expr = NULL; }
@after	{
	addChild(PRIV, $d, expr ? : fill_attr(PRIV, newEmptyStatement(PRIV), $expressionStatement.start));
	fill_attr(PRIV, $d, $expressionStatement.start);
}
	: ^(EXPRESSION_STATEMENT (expression {expr = $expression.d;})?)
	;

selectionStatement returns [my_jobject d]
@after	{ fill_attr(PRIV, $d, $selectionStatement.start); }
	: ^('if' ex=expression s1=statement { $d = newNode2(PRIV, newIfStatement, $ex.d, $s1.d); }
		 (s2=statement {addChild(PRIV, $d, $s2.d);})?) {
//		e = newElement(s2 == null ? "ifStatement" : "ifElseStatement",
	}
	| ^('switch' ex=expression st=statement) { $d = newNode2(PRIV, newSwitchStatement, $ex.d, $st.d); }
	;

iterationStatement returns [my_jobject d]
@init	{ my_jobject ded = NULL, e1d = NULL, e2d = NULL, e3d = NULL; }
@after	{ fill_attr(PRIV, $d, $iterationStatement.start); }
	: ^('while' ex=expression st=statement) { $d = newNode2(PRIV, newWhileStatement, $ex.d, $st.d); }
	| ^('do' st=statement ex=expression) { $d = newNode2(PRIV, newDoStatement, $st.d, $ex.d); }
	| ^('for' {$d = newForStatement(PRIV);} (de=declaration {ded=$de.d;})?
			(^(E1 e1=expression) {e1d=$e1.d;})?
			 ^(E2 (e2=expression {e2d=$e2.d;})?)
			 ^(E3 (e3=expression {e3d=$e3.d;})?)
			 st=statement) {
		if (ded)
			addChild(PRIV, $d, ded);
		else if (e1d)
			addChild(PRIV, $d, e1d);
		else
			addChild(PRIV, $d, newExpression(PRIV));
		addChild(PRIV, $d, e2d ? : newExpression(PRIV));
		addChild(PRIV, $d, e3d ? : newExpression(PRIV));
		addChild(PRIV, $d, $st.d);
	}
	;

jumpStatement returns [my_jobject d]
@after	{ fill_attr(PRIV, $d, $jumpStatement.start); }
	: ^('goto' IDENTIFIER) {
		my_jobject expr;
		$d = newGotoStatement(PRIV);
		expr = newNode1(PRIV, newExpression, newId(PRIV, (char *)$IDENTIFIER.text->chars));
		addChild(PRIV, $d, expr);
	}
	| ^('goto' XU expression) {
		my_jobject expr, deref;
		$d = newGotoStatement(PRIV);
		expr = newExpression(PRIV);
		deref = newNode1(PRIV, newDerefExpression, $expression.d);
		addChild(PRIV, $d, expr);
		addChild(PRIV, expr, deref);
	}
	| 'continue'	{ $d = newContinueStatement(PRIV); }
	| 'break'	{ $d = newBreakStatement(PRIV); }
	| ^('return'	{ $d = newReturnStatement(PRIV); }
			(ex=expression {addChild(PRIV, $d, $ex.d);})?)
	;

asmStatement returns [my_jobject d]
	: ASM		{ $d = newGnuAssembler(PRIV); }
	;

/* STATEMENTS END */

/* EXPRESSIONS */

expression returns [my_jobject d]
	: otherExpression	{ $d=$otherExpression.d; }
	| binaryExpression	{ $d=$binaryExpression.d; }
	| primaryExpression	{ $d=$primaryExpression.d; }
	;

otherExpression returns [my_jobject d]
@init { $d = NULL; my_jobject init; }
@after	{
	if ($d == NULL)
		printf("\%s: \%s\n", __func__, $otherExpression.start->getText($otherExpression.start)->chars);
	fill_attr(PRIV, $d, $otherExpression.start);
}
	: ^(ASSIGNMENT_EXPRESSION assignmentOperator e1=expression e2=expression) {
		char my_op[5], *op = (char *)$assignmentOperator.text->chars;
		$d = newNode2(PRIV, newAssignExpression, $e1.d, $e2.d);
		if (op[0] != '=' || op[1] != 0) { /* not '=' */
			strcpy(my_op, op)[strlen(my_op) - 1] = 0;
			setAttr(PRIV, $d, "op", my_op);
		}
	}
	| ^(CONDITIONAL_EXPRESSION ^(E1 e1=expression)	{ $d = newNode1(PRIV, newConditionalExpression, $e1.d); }
				   ^(E2 (e2=expression	{ addChild(PRIV, $d, $e2.d); })?)
				   ^(E3 e3=expression))	{ addChild(PRIV, $d, $e3.d); }
	| ^(CAST_EXPRESSION tn=typeName e1=expression) { $d = newNode2(PRIV, newCastExpression, $tn.d, $e1.d); }
	| ^(ARRAY_ACCESS e1=expression e2=expression)  { $d = newNode2(PRIV, newArrayAccess, $e1.d, $e2.d); }
	| ^(FUNCTION_CALL e1=expression { $d = newNode1(PRIV, newFunctionCall, $e1.d); }
			  (e2=expression {addChild(PRIV, $d, $e2.d);})*)
	| ^(COMPOUND_LITERAL tn=typeName {
		init = newInitializer(PRIV);
		$d = newNode2(PRIV, newCompoundLiteral, $tn.d, init);
	} initializerList[init]?)
	| ^(',' e1=expression e2=expression)	{ $d=newNode2(PRIV, newCommaExpression, $e1.d, $e2.d); }
	| ^('++' e1=expression)		{ $d=newPrefixExpression(PRIV, "++"); addChild(PRIV, $d, $e1.d); }
	| ^('--' e1=expression)		{ $d=newPrefixExpression(PRIV, "--"); addChild(PRIV, $d, $e1.d); }
	| ^(unaryOp e1=expression)	{ $d=newPrefixExpression(PRIV, $unaryOp.op); addChild(PRIV, $d, $e1.d); }
	| ^('sizeof' { $d = newSizeofExpression(PRIV); }
			(e1=expression {addChild(PRIV, $d, $e1.d);} |
			 tn=typeName {addChild(PRIV, $d, $tn.d);}))
	| ^('__alignof__' { $d=newAllignofExpression(PRIV); }
			(e1=expression	{addChild(PRIV, $d, $e1.d);} |
			 tn=typeName	{addChild(PRIV, $d, $tn.d);}))
	| ^('.' e1=expression IDENTIFIER)	{
		$d = newNode1(PRIV, newDotExpression, $e1.d);
		addChild(PRIV, $d, newMember(PRIV, (char *)$IDENTIFIER.text->chars));
	}
	| ^('->' e1=expression IDENTIFIER) {
		$d = newNode1(PRIV, newArrowExpression, $e1.d);
		addChild(PRIV, $d, newMember(PRIV, (char *)$IDENTIFIER.text->chars));
	}
	| ^(AU e1=expression)	{ $d=newNode1(PRIV, newAddrExpression, $e1.d); }
	| ^(XU e1=expression)	{ $d=newNode1(PRIV, newDerefExpression, $e1.d); }
	| ^(PP e1=expression)	{ $d=newPostfixExpression(PRIV, "++"); addChild(PRIV, $d, $e1.d); }
	| ^(MM e1=expression)	{ $d=newPostfixExpression(PRIV, "--"); addChild(PRIV, $d, $e1.d); }
	;

binaryExpression returns [my_jobject d]
@after {
	$d = newBinaryExpression(PRIV, (char *)op->getText(op)->chars);
	fill_attr(PRIV, $d, $binaryExpression.start);
	addChild(PRIV, $d, $e1.d);
	addChild(PRIV, $d, $e2.d);
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

primaryExpression returns [my_jobject d]
	: IDENTIFIER		{ $d = newId(PRIV, findVariable(CTX, $IDENTIFIER.text)); }
	| constant	{ $d = $constant.d; }
	| sTRING_LITERAL	{
		char *str = $sTRING_LITERAL.str;
		$d = newStringConst(PRIV, str);
		free(str);
		fill_attr(PRIV, $d, $sTRING_LITERAL.start);
	}
	| compoundStatement	{ $d = $compoundStatement.d; }
	| ^(BUILTIN_OFFSETOF typeName offsetofMemberDesignator) {
		$d = newNode2(PRIV, newOffsetofExpression, $typeName.d, $offsetofMemberDesignator.d);
		fill_attr(PRIV, $d, $primaryExpression.start);
	}
	;

sTRING_LITERAL returns [char *str]
@init { $str = NULL; unsigned long data = 0, size = 8; }
	: ^(STR_LITERAL (STRING_LITERAL {
		/* +1 and -2 means strip parentheses */
		const char *sl = (char *)$STRING_LITERAL.text->chars + 1;
		unsigned int len = $STRING_LITERAL.text->len - 2;
		if (!$str || data + len + 1 >= size) {
			while (data + len + 1 >= size)
				size *= 2;
			$str = realloc($str, size);
			if (!$str)
				parser_die(PRIV, "cannot allocate memory");
		}
		memcpy($str + data, sl, len);
		data += len;
		$str[data] = 0;
	})+)
	;

constant returns [my_jobject d]
	:	ICONSTANT { $d = newIntConst(PRIV, (char *)$ICONSTANT.text->chars); }
	|	RCONSTANT { $d = newRealConst(PRIV, (char *)$RCONSTANT.text->chars); }
	;

offsetofMemberDesignator returns [my_jobject d]
@init	{ $d = newExpression(PRIV); }
@after	{ fill_attr(PRIV, $d, $offsetofMemberDesignator.start); }
	: id1=IDENTIFIER { addChild(PRIV, $d, newId(PRIV, findVariable(CTX, $id1.text))); }
	  ( ('.' id2=IDENTIFIER {
			my_jobject dot = newNode1(PRIV, newDotExpression, $d);
			addChild(PRIV, dot, newMember(PRIV, (char *)$id2.text->chars));
			$d = newNode1(PRIV, newExpression, dot);
		})
		| ('[' expression ']' {
			my_jobject aa = newNode1(PRIV, newArrayAccess, $d);
			addChild(PRIV, aa, $expression.d);
			$d = newNode1(PRIV, newExpression, aa);
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

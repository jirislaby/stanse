/*
 * Author: Jiri Slaby
 *
 * Licensed under the GPLv2
 */

#include <stdlib.h>
#include <stdio.h>

#include <jni.h>

#include "cparser_CParser.h"
#include "common.h"

enum class_ID {
	cls_CParser,
	cls_Node,

	cls_AbstractDeclarator,
	cls_AddrExpression,
	cls_AllignofExpression,
	cls_ArrayAccess,
	cls_ArrayDecl,
	cls_ArrowExpression,
	cls_AssignExpression,
	cls_BaseType,
	cls_BinaryExpression,
	cls_BreakStatement,
	cls_CaseLabelStatement,
	cls_CastExpression,
	cls_CommaExpression,
	cls_CompoundLiteral,
	cls_CompoundStatement,
	cls_ConditionalExpression,
	cls_ContinueStatement,
	cls_Declaration,
	cls_DeclarationSpecifiers,
	cls_Declarator,
	cls_DefaultLabelStatement,
	cls_DerefExpression,
	cls_Designator,
	cls_DoStatement,
	cls_DotExpression,
	cls_EmptyStatement,
	cls_Enum,
	cls_Enumerator,
	cls_Expression,
	cls_ExpressionStatement,
	cls_ExternalDeclaration,
	cls_ForStatement,
	cls_FunctionCall,
	cls_FunctionDecl,
	cls_FunctionDefinition,
	cls_GnuAssembler,
	cls_GotoStatement,
	cls_Id,
	cls_IfStatement,
	cls_InitDeclarator,
	cls_Initializer,
	cls_IntConst,
	cls_LabelStatement,
	cls_Member,
	cls_OffsetofExpression,
	cls_Parameter,
	cls_Pointer,
	cls_PostfixExpression,
	cls_PrefixExpression,
	cls_RangeExpression,
	cls_RealConst,
	cls_ReturnStatement,
	cls_SizeofExpression,
	cls_StringConst,
	cls_Struct,
	cls_StructDeclaration,
	cls_StructDeclarator,
	cls_StructOrUnion,
	cls_SwitchStatement,
	cls_TranslationUnit,
	cls_Typedef,
	cls_TypeName,
	cls_TypeOfSpecifier,
	cls_TypeSpecifier,
	cls_Union,
	cls_WhileStatement,

	/* has to be the last one */
	cid_count
};

enum method_ID {
	FunctionDefinition_setEndLine,

	Node_addChild,
	Node_setAttrII,
	Node_setAttrIS,
	Node_setAttrSS,
	Node_setColumn,
	Node_setLine,

	/* has to be the last one */
	mid_count
};

enum field_ID {
	CParser_file,
	CParser_root,
	/* has to be the last one */
	fid_count
};

static jclass classes[cid_count];
static jmethodID methods[mid_count];
static jfieldID fields[fid_count];

struct jni_data {
	JNIEnv *env;
};

static void cleanup_class_ids(JNIEnv *env)
{
	unsigned int a;
	for (a = 0; a < cid_count; a++)
		(*env)->DeleteGlobalRef(env, classes[a]);
}

static int get_class_ids(JNIEnv *env)
{
#define AST_CLASS(class) [cls_ ## class] = "cparser/AST/" #class
	static const char *classes_spec[cid_count] = {
		[cls_CParser] = "cparser/CParser",
		AST_CLASS(Node),

		AST_CLASS(AbstractDeclarator),
		AST_CLASS(AddrExpression),
		AST_CLASS(AllignofExpression),
		AST_CLASS(ArrayAccess),
		AST_CLASS(ArrayDecl),
		AST_CLASS(ArrowExpression),
		AST_CLASS(AssignExpression),
		AST_CLASS(BaseType),
		AST_CLASS(BinaryExpression),
		AST_CLASS(BreakStatement),
		AST_CLASS(CaseLabelStatement),
		AST_CLASS(CastExpression),
		AST_CLASS(CommaExpression),
		AST_CLASS(CompoundLiteral),
		AST_CLASS(CompoundStatement),
		AST_CLASS(ConditionalExpression),
		AST_CLASS(ContinueStatement),
		AST_CLASS(Declaration),
		AST_CLASS(DeclarationSpecifiers),
		AST_CLASS(Declarator),
		AST_CLASS(DefaultLabelStatement),
		AST_CLASS(DerefExpression),
		AST_CLASS(Designator),
		AST_CLASS(DoStatement),
		AST_CLASS(DotExpression),
		AST_CLASS(EmptyStatement),
		AST_CLASS(Enum),
		AST_CLASS(Enumerator),
		AST_CLASS(Expression),
		AST_CLASS(ExpressionStatement),
		AST_CLASS(ExternalDeclaration),
		AST_CLASS(ForStatement),
		AST_CLASS(FunctionCall),
		AST_CLASS(FunctionDecl),
		AST_CLASS(FunctionDefinition),
		AST_CLASS(GnuAssembler),
		AST_CLASS(GotoStatement),
		AST_CLASS(Id),
		AST_CLASS(IfStatement),
		AST_CLASS(InitDeclarator),
		AST_CLASS(Initializer),
		AST_CLASS(IntConst),
		AST_CLASS(LabelStatement),
		AST_CLASS(Member),
		AST_CLASS(OffsetofExpression),
		AST_CLASS(Parameter),
		AST_CLASS(Pointer),
		AST_CLASS(PostfixExpression),
		AST_CLASS(PrefixExpression),
		AST_CLASS(RangeExpression),
		AST_CLASS(RealConst),
		AST_CLASS(ReturnStatement),
		AST_CLASS(SizeofExpression),
		AST_CLASS(StringConst),
		AST_CLASS(Struct),
		AST_CLASS(StructDeclaration),
		AST_CLASS(StructDeclarator),
		AST_CLASS(StructOrUnion),
		AST_CLASS(SwitchStatement),
		AST_CLASS(TranslationUnit),
		AST_CLASS(Typedef),
		AST_CLASS(TypeName),
		AST_CLASS(TypeOfSpecifier),
		AST_CLASS(TypeSpecifier),
		AST_CLASS(Union),
		AST_CLASS(WhileStatement),
	};
	int pos;

	for (pos = 0; pos < cid_count; pos++) {
		const char *src = classes_spec[pos];
		jclass *dst = &classes[pos];
		if (!src) {
			fprintf(stderr, "class %d is NULL\n", pos);
			fflush(stderr);
			abort();
		}
		jclass d = (*env)->FindClass(env, src);
		if (!d) {
			cleanup_class_ids(env);
			return -1;
		}
		*dst = (*env)->NewGlobalRef(env, d);
		if (!*dst) {
			cleanup_class_ids(env);
			return -2;
		}
	}

	return 0;
}

struct fm_spec {
	unsigned int class;
	const char *name, *type;
};

#define FM(class, name, type) \
	[class ## _ ## name] = { cls_ ## class, #name, type }

static void cleanup_method_ids(JNIEnv *env)
{
	/* method IDs need not be de/referenced */
}

static int get_method_ids(JNIEnv *env)
{
	static const struct fm_spec methods_spec[] = {
		FM(FunctionDefinition, setEndLine, "(I)V"),

		FM(Node, addChild, "(Lcparser/AST/Node;)V"),
		FM(Node, setAttrII, "(II)V"),
		FM(Node, setAttrIS, "(ILjava/lang/String;)V"),
		FM(Node, setAttrSS,
				"(Ljava/lang/String;Ljava/lang/String;)V"),
		FM(Node, setColumn, "(I)V"),
		FM(Node, setLine, "(I)V"),
	};
	int pos;

	for (pos = 0; pos < mid_count; pos++) {
		const struct fm_spec *src = &methods_spec[pos];
		jmethodID *dst = &methods[pos];
		if (!src->name) {
			fprintf(stderr, "method %d is NULL\n", pos);
			fflush(stderr);
			abort();
		}
		*dst = (*env)->GetMethodID(env, classes[src->class], src->name,
				src->type);
		if (!*dst) {
			cleanup_method_ids(env);
			return -1;
		}
	}

	return 0;
}

static void cleanup_field_ids(JNIEnv *env)
{
	/* the same as method IDs... */
}

static int get_field_ids(JNIEnv *env)
{
	static const struct fm_spec fields_spec[] = {
		FM(CParser, file, "Ljava/lang/String;"),
		FM(CParser, root, "Lcparser/AST/TranslationUnit;"),
		{ }
	};
	int pos;

	for (pos = 0; pos < fid_count; pos++) {
		const struct fm_spec *src = &fields_spec[pos];
		jfieldID *dst = &fields[pos];
		if (!src->name) {
			fprintf(stderr, "field %d is NULL\n", pos);
			fflush(stderr);
			abort();
		}
		*dst = (*env)->GetFieldID(env, classes[src->class], src->name,
				src->type);
		if (!*dst) {
			cleanup_field_ids(env);
			return -1;
		}
	}

	return 0;
}

/*
 * Class:     cparser_CParser
 * Method:    init
 * Signature: ()V
 */
JNIEXPORT jint JNICALL Java_cparser_CParser_init(JNIEnv *env, jclass cls)
{
	int ret;

	ret = get_class_ids(env);
	if (ret)
		return -1;

	ret = get_method_ids(env);
	if (ret)
		goto err_cls;

	ret = get_field_ids(env);
	if (ret)
		goto err_met;

	return 0;
err_met:
	cleanup_method_ids(env);
err_cls:
	cleanup_class_ids(env);
	return -2;
}

/*
 * Class:     cparser_CParser
 * Method:    fini
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_cparser_CParser_fini(JNIEnv *env, jclass cls)
{
	cleanup_field_ids(env);
	cleanup_method_ids(env);
	cleanup_class_ids(env);
}

/*
 * Class:     cparser_CParser
 * Method:    parse
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_cparser_CParser_parse(JNIEnv *env, jobject obj)
{
	struct jni_data jni;
	const char *cstr;
	jstring file;
	my_jobject AST;
	int ret;

	jni.env = env;

	file = (*env)->GetObjectField(env, obj, fields[CParser_file]);
	if (!file)
		return -1;

	cstr = (*env)->GetStringUTFChars(env, file, NULL);

	ret = parse(cstr, &jni, &AST);

	(*env)->ReleaseStringUTFChars(env, file, cstr);

	if (!ret)
		(*env)->SetObjectField(env, obj, fields[CParser_root], AST);

	return ret;
}

static void do_die(JNIEnv *env, const char *reason, const char *func, int line)
{
	char buf[128];
	snprintf(buf, 128, "%s (%d): %s", func, line, reason);
	(*env)->FatalError(env, buf);
}

#define die(env, reason) do_die(env, reason, __func__, __LINE__)

void parser_do_die(void *priv, const char *reason, const char *func, int line)
{
	struct jni_data *jni = priv;

	do_die(jni->env, reason, func, line);
}

static int check_exception(JNIEnv *env)
{
	int ret = (*env)->ExceptionCheck(env);

	if (ret)
		(*env)->ExceptionDescribe(env);

	return ret;
}

static jobject new_obj(JNIEnv *env, enum class_ID cid,
		const char *con_type, ...)
{
	va_list args;
	jmethodID constructor;
	jobject object;

	constructor = (*env)->GetMethodID(env, classes[cid], "<init>",
			con_type);
	if (!constructor || check_exception(env))
		die(env, "no constructor");

	va_start(args, con_type);
	object = (*env)->NewObjectV(env, classes[cid], constructor, args);
	va_end(args);

	if (check_exception(env))
		die(env, "constructor failed");

	return object;
}

static my_jobject new_obj_str(JNIEnv *env, enum class_ID cid, const char *cstr)
{
	jstring jstr;
	jobject obj;

	jstr = (*env)->NewStringUTF(env, cstr);
	obj = new_obj(env, cid, "(Ljava/lang/String;)V", jstr);
	(*env)->DeleteLocalRef(env, jstr);

	return obj;
}

void setLine(void *priv, my_jobject obj, int line)
{
	struct jni_data *jni = priv;
	(*jni->env)->CallVoidMethod(jni->env, obj, methods[Node_setLine], line);
}

/* only some Nodes */
void setAttrII(void *priv, my_jobject obj, const int key, const int val)
{
	struct jni_data *jni = priv;
	JNIEnv *env = jni->env;
	jint jkey, jval;

	jkey = key;
	jval = val;
	(*env)->CallVoidMethod(env, obj, methods[Node_setAttrII], jkey, jval);

	if (check_exception(env))
		die(env, "setAttrII failed");
}

void setAttrIS(void *priv, my_jobject obj, const int key, const char *val)
{
	struct jni_data *jni = priv;
	JNIEnv *env = jni->env;
	jint jkey;
	jstring jval;

	jkey = key;
	jval = (*env)->NewStringUTF(env, val);
	(*env)->CallVoidMethod(env, obj, methods[Node_setAttrIS], jkey, jval);
	(*env)->DeleteLocalRef(env, jval);

	if (check_exception(env))
		die(env, "setAttrIS failed");
}

void setAttrSS(void *priv, my_jobject obj, const char *name, const char *val)
{
	struct jni_data *jni = priv;
	JNIEnv *env = jni->env;
	jstring jname, jval;

	jname = (*env)->NewStringUTF(env, name);
	jval = (*env)->NewStringUTF(env, val);
	(*env)->CallVoidMethod(env, obj, methods[Node_setAttrSS], jname, jval);
	(*env)->DeleteLocalRef(env, jval);
	(*env)->DeleteLocalRef(env, jname);

	if (check_exception(env))
		die(env, "setAttrSS failed");
}

static void call_void_method(struct jni_data *jni, my_jobject obj,
		enum method_ID mid, ...)
{
	JNIEnv *env = jni->env;
	va_list args;

	va_start(args, mid);
	(*env)->CallVoidMethodV(env, obj, methods[mid], args);
	va_end(args);

	if (check_exception(env))
		die(env, "call_void_method failed");
}

/* FunctionDefinition only */
void setEndLine(void *priv, my_jobject obj, int line)
{
	call_void_method(priv, obj, FunctionDefinition_setEndLine, line);
}

void setColumn(void *priv, my_jobject obj, int column)
{
	call_void_method(priv, obj, Node_setColumn, column);
}

void addChild(void *priv, my_jobject parent, my_jobject child)
{
	struct jni_data *jni = priv;
	if (!child)
		die(jni->env, "child is NULL");
	call_void_method(priv, parent, Node_addChild, child);
}

/*
 * ===================================
 * ***            AST              ***
 * ===================================
 */

#define VOID_NODE(name) \
	my_jobject new ## name(void *priv)				\
	{ 								\
		struct jni_data *jni = priv;				\
		return new_obj(jni->env, cls_ ## name, "()V");		\
	}

#define STRING_NODE(name) \
	my_jobject new ## name(void *priv, const char *cstr)		\
	{								\
		struct jni_data *jni = priv;				\
		return new_obj_str(jni->env, cls_ ## name, cstr);	\
	}

VOID_NODE(AbstractDeclarator);
VOID_NODE(AddrExpression);
VOID_NODE(AllignofExpression);
VOID_NODE(ArrayAccess);
VOID_NODE(ArrayDecl);
VOID_NODE(ArrowExpression);
VOID_NODE(AssignExpression);
STRING_NODE(BaseType);
STRING_NODE(BinaryExpression);
VOID_NODE(BreakStatement);
VOID_NODE(CaseLabelStatement);
VOID_NODE(CastExpression);
VOID_NODE(CommaExpression);
VOID_NODE(CompoundLiteral);
VOID_NODE(CompoundStatement);
VOID_NODE(ConditionalExpression);
VOID_NODE(ContinueStatement);
VOID_NODE(Declaration);
VOID_NODE(DeclarationSpecifiers);
VOID_NODE(Declarator);
VOID_NODE(DefaultLabelStatement);
VOID_NODE(DerefExpression);
VOID_NODE(Designator);
VOID_NODE(DotExpression);
VOID_NODE(DoStatement);
VOID_NODE(EmptyStatement);
VOID_NODE(Enum);
STRING_NODE(Enumerator);
VOID_NODE(ExternalDeclaration);
VOID_NODE(Expression);
VOID_NODE(ExpressionStatement);
VOID_NODE(ForStatement);
VOID_NODE(FunctionCall);
VOID_NODE(FunctionDecl);
VOID_NODE(FunctionDefinition);
VOID_NODE(GnuAssembler);
VOID_NODE(GotoStatement);
STRING_NODE(Id);
VOID_NODE(IfStatement);
VOID_NODE(Initializer);
VOID_NODE(InitDeclarator);
STRING_NODE(IntConst);
STRING_NODE(LabelStatement);
STRING_NODE(Member);
VOID_NODE(OffsetofExpression);
VOID_NODE(Parameter);
VOID_NODE(Pointer);
STRING_NODE(PostfixExpression);
STRING_NODE(PrefixExpression);
VOID_NODE(RangeExpression);
STRING_NODE(RealConst);
VOID_NODE(ReturnStatement);
VOID_NODE(SizeofExpression);
STRING_NODE(StringConst);
VOID_NODE(Struct);
VOID_NODE(StructDeclaration);
VOID_NODE(StructDeclarator);
VOID_NODE(SwitchStatement);
VOID_NODE(TranslationUnit);
VOID_NODE(Typedef);
VOID_NODE(TypeName);
VOID_NODE(TypeOfSpecifier);
VOID_NODE(TypeSpecifier);
VOID_NODE(Union);
VOID_NODE(WhileStatement);

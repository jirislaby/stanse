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

	cls_Union,
	cls_TypeSpecifier,
	cls_TypeName,
	cls_TranslationUnit,
	cls_StructOrUnion,
	cls_StructDeclarator,
	cls_StructDeclaration,
	cls_Struct,
	cls_Parameter,
	cls_InitDeclarator,
	cls_Identifier,
	cls_FunctionDefinition,
	cls_FunctionDecl,
	cls_ExternalDeclaration,
	cls_Enumerator,
	cls_Enum,
	cls_Declarator,
	cls_DeclarationSpecifiers,
	cls_Declaration,
	cls_CompoundStatement,
	cls_BinaryExpression,
	cls_BaseType,
	cls_ArrayDecl,
	cls_AbstractDeclarator,

	/* has to be the last one */
	cid_count
};

enum method_ID {
	FunctionDefinition_setEndLine,

	Node_addChild,
	Node_setAttr,
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
		AST_CLASS(ArrayDecl),
		AST_CLASS(BaseType),
		AST_CLASS(BinaryExpression),
		AST_CLASS(CompoundStatement),
		AST_CLASS(Declaration),
		AST_CLASS(DeclarationSpecifiers),
		AST_CLASS(Declarator),
		AST_CLASS(Enum),
		AST_CLASS(Enumerator),
		AST_CLASS(ExternalDeclaration),
		AST_CLASS(FunctionDecl),
		AST_CLASS(FunctionDefinition),
		AST_CLASS(Identifier),
		AST_CLASS(InitDeclarator),
		AST_CLASS(Parameter),
		AST_CLASS(Struct),
		AST_CLASS(StructDeclaration),
		AST_CLASS(StructDeclarator),
		AST_CLASS(StructOrUnion),
		AST_CLASS(TranslationUnit),
		AST_CLASS(TypeName),
		AST_CLASS(TypeSpecifier),
		AST_CLASS(Union),
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
		FM(Node, setAttr,
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
	puts("ggg");

	(*env)->ReleaseStringUTFChars(env, file, cstr);

	puts("ggg1");
	if (!ret)
		(*env)->SetObjectField(env, obj, fields[CParser_root], AST);
	puts("ggg2");

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
	if (!constructor)
		return NULL;

	va_start(args, con_type);
	object = (*env)->NewObjectV(env, classes[cid], constructor, args);
	va_end(args);

	return object;
}

void setLine(void *priv, my_jobject obj, int line)
{
	struct jni_data *jni = priv;
	(*jni->env)->CallVoidMethod(jni->env, obj, methods[Node_setLine], line);
}

/* only some Nodes */
void setAttr(void *priv, my_jobject obj, const char *name, const char *val)
{
	struct jni_data *jni = priv;
	JNIEnv *env = jni->env;
	jstring jname, jval;

	jname = (*env)->NewStringUTF(env, name);
	jval = (*env)->NewStringUTF(env, val);
	(*env)->CallVoidMethod(env, obj, methods[Node_setAttr], jname, jval);
	(*env)->DeleteLocalRef(env, jval);
	(*env)->DeleteLocalRef(env, jname);
}

/* FunctionDefinition only */
void setEndLine(void *priv, my_jobject obj, int line)
{
	struct jni_data *jni = priv;
	(*jni->env)->CallVoidMethod(jni->env, obj,
			methods[FunctionDefinition_setEndLine], line);
}

void setColumn(void *priv, my_jobject obj, int column)
{
	struct jni_data *jni = priv;
	(*jni->env)->CallVoidMethod(jni->env, obj, methods[Node_setColumn],
			column);
}

void addChild(void *priv, my_jobject parent, my_jobject child)
{
	struct jni_data *jni = priv;
	if (!child)
		abort();
	(*jni->env)->CallVoidMethod(jni->env, parent, methods[Node_addChild],
			child);
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
		JNIEnv *env = jni->env;					\
		jstring jstr;						\
		jobject obj;						\
									\
		jstr = (*env)->NewStringUTF(env, cstr);			\
		obj = new_obj(env, cls_ ## name,			\
				"(Ljava/lang/String;)V", jstr);		\
		(*env)->DeleteLocalRef(env, jstr);			\
									\
		return obj;						\
	}

VOID_NODE(AbstractDeclarator);
VOID_NODE(ArrayDecl);
STRING_NODE(BaseType);
STRING_NODE(BinaryExpression);
VOID_NODE(CompoundStatement);
VOID_NODE(Declaration);
VOID_NODE(DeclarationSpecifiers);
VOID_NODE(Declarator);
VOID_NODE(Enum);
STRING_NODE(Enumerator);
VOID_NODE(ExternalDeclaration);
VOID_NODE(FunctionDecl);
VOID_NODE(FunctionDefinition);
STRING_NODE(Identifier);
VOID_NODE(InitDeclarator);
VOID_NODE(Parameter);
VOID_NODE(Struct);
VOID_NODE(StructDeclaration);
VOID_NODE(StructDeclarator);
VOID_NODE(TranslationUnit);
VOID_NODE(TypeName);
VOID_NODE(TypeSpecifier);
VOID_NODE(Union);

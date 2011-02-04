/*
 * Author: Jiri Slaby
 *
 * Licensed under the GPLv2
 */

#include <jni.h>
#include <stdio.h>

#include "cparser_CParser.h"
#include "common.h"

enum class_ID {
	cls_CParser,
	cls_Node,

	cls_BinaryOperation,
	cls_CompoundStatement,
	cls_Declaration,
	cls_DeclarationSpecifiers,
	cls_Declarator,
	cls_ExternalDeclaration,
	cls_FunctionDefinition,
	cls_TranslationUnit,

	/* has to be the last one */
	cid_count
};

enum method_ID {
	FunctionDefinition_setEndLine,

	Node_addChild,
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
	static const char *classes_spec[] = {
		[cls_CParser] = "cparser/CParser",
		AST_CLASS(Node),

		AST_CLASS(BinaryOperation),
		AST_CLASS(CompoundStatement),
		AST_CLASS(Declaration),
		AST_CLASS(DeclarationSpecifiers),
		AST_CLASS(Declarator),
		AST_CLASS(ExternalDeclaration),
		AST_CLASS(FunctionDefinition),
		AST_CLASS(TranslationUnit),

		NULL
	};
	const char * const *src;
	jclass *dst;

	for (src = classes_spec, dst = classes; *src; src++, dst++) {
		jclass d = (*env)->FindClass(env, *src);
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
		FM(Node, setColumn, "(I)V"),
		FM(Node, setLine, "(I)V"),

		{ }
	};
	const struct fm_spec *src;
	jmethodID *dst;

	for (src = methods_spec, dst = methods; src->name; src++, dst++) {
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
	const struct fm_spec *src;
	jfieldID *dst;

	for (src = fields_spec, dst = fields; src->name; src++, dst++) {
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

my_jobject newBinaryOperation(void *priv, const char *op)
{
	struct jni_data *jni = priv;
	JNIEnv *env = jni->env;
	jstring jstr;
	jobject bop;

	jstr = (*env)->NewStringUTF(env, op);
	bop = new_obj(env, cls_BinaryOperation,
			"(Ljava/lang/String;)V", jstr);
	(*env)->DeleteLocalRef(env, jstr);

	return bop;
}

VOID_NODE(CompoundStatement);
VOID_NODE(Declaration);
VOID_NODE(DeclarationSpecifiers);
VOID_NODE(Declarator);
VOID_NODE(ExternalDeclaration);
VOID_NODE(FunctionDefinition);
VOID_NODE(TranslationUnit);

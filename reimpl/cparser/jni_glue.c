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
	cls_TranslationUnit,
	cls_BinaryOperation,
	/* has to be the last one */
	cid_count
};

enum method_ID {
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
	static const char *classes_spec[] = {
		[cls_CParser] = "cparser/CParser",
		[cls_Node] = "cparser/AST/Node",
		[cls_TranslationUnit] = "cparser/AST/TranslationUnit",
		[cls_BinaryOperation] = "cparser/AST/BinaryOperation",
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

static inline void set_line(JNIEnv *env, jobject obj, jint line)
{
	if (obj)
		(*env)->CallVoidMethod(env, obj, methods[Node_setLine], line);
}

my_jobject newTranslationUnit(void *priv)
{
	struct jni_data *jni = priv;
	JNIEnv *env = jni->env;
	jobject tu;

	tu = new_obj(env, cls_TranslationUnit, "()V");

	set_line(env, tu, 5);

	return tu;
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

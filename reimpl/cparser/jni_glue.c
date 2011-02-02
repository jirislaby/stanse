/*
 * Author: Jiri Slaby
 *
 * Licensed under the GPLv2
 */

#include <jni.h>
#include <stdio.h>

#include "cparser_CParser.h"
#include "common.h"

static JNIEnv *glob_env;

/*
 * Class:     cparser_CParser
 * Method:    init
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_cparser_CParser_init(JNIEnv *env, jclass cls)
{
	glob_env = env;
}

/*
 * Class:     cparser_CParser
 * Method:    parse
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_cparser_CParser_parse(JNIEnv *env, jclass cls,
		jstring file)
{
	const char *cstr;
	int ret;

/*	jmethodID test = (*env)->GetStaticMethodID(env, cls, "test",
		"(Ljava/lang/String;)V");
	jstring jst = (*env)->NewStringUTF(env, "cheche");
	if (test && jstr)
		(*env)->CallStaticVoidMethod(env, cls, test, jstr);
	(*env)->DeleteLocalRef(env, jstr);*/

	cstr = (*env)->GetStringUTFChars(env, file, NULL);

	ret = parse(cstr);

	(*env)->ReleaseStringUTFChars(env, file, cstr);

	return ret;
}

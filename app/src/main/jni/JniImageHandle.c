//
// Created by android on 8/22/16.
//

#include "com_ted_jnihandleimage_JniHandle.h"

/*
 *
 *
 */

JNIEXPORT jstring JNICALL Java_com_ted_jnihandleimage_JniHandle_test
        (JNIEnv *env, jobject obj){

    return (*env)->NewStringUTF(env, "Congratulation! This string from JNI!");
}
//
// Created by android on 8/22/16.
//

#include "com_ted_jnihandleimage_JniHandle.h"
#include<android/log.h>

#ifndef TAG
#define TAG "jniHandleImage"
//#undef LOG //这句可能导致log打不出来
#endif

#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,TAG ,__VA_ARGS__) // 定义LOGD类型
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG ,__VA_ARGS__) // 定义LOGI类型
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN,TAG ,__VA_ARGS__) // 定义LOGW类型
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,TAG ,__VA_ARGS__) // 定义LOGE类型
#define LOGF(...) __android_log_print(ANDROID_LOG_FATAL,TAG ,__VA_ARGS__) // 定义LOGF类型

/*
 *
 *
 */

JNIEXPORT jstring JNICALL Java_com_ted_jnihandleimage_JniHandle_test
        (JNIEnv *env, jobject obj){

    return (*env)->NewStringUTF(env, "Congratulation! This string from JNI!");
}

JNIEXPORT void JNICALL
Java_com_ted_jnihandleimage_JniHandle_convertToGray(
        JNIEnv *env,
        jobject instance,
        jobject bitmapIn,
        jobject bitmapOut){

    LOGD("Congratulations! Here is jni method convertToGray! ");
}
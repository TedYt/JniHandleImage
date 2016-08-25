//
// Created by android on 8/22/16.
//

#include "com_ted_jnihandleimage_JniHandle.h"
#include<android/log.h>
#include <android/bitmap.h>

#ifndef TAG
#define TAG "jniHandleImage"
//#undef LOG //这句可能导致log打不出来
#endif

#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,TAG ,__VA_ARGS__) // 定义LOGD类型
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG ,__VA_ARGS__) // 定义LOGI类型
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN,TAG ,__VA_ARGS__) // 定义LOGW类型
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,TAG ,__VA_ARGS__) // 定义LOGE类型
#define LOGF(...) __android_log_print(ANDROID_LOG_FATAL,TAG ,__VA_ARGS__) // 定义LOGF类型

typedef struct {
    unsigned alpha;
    unsigned red;
    unsigned green;
    unsigned blue;
}argb;

/*
 *
 *
 */

JNIEXPORT jstring JNICALL Java_com_ted_jnihandleimage_JniHandle_test
        (JNIEnv *env, jobject obj){

    return (*env)->NewStringUTF(env, "Congratulation! This string from JNI!");
}

/*
 * convertToGray
 * Pixel operation
 * bitmapcolor: 32位彩色图片：格式：ANDROID_BITMAP_FORMAT_RGBA_8888
 * bitmapgray: 8位，灰色图片，格式：ANDROID_BITMAP_FORMAT_A_8
 * 根据彩色图片填充灰色图片数据
 */
JNIEXPORT void JNICALL
Java_com_ted_jnihandleimage_JniHandle_convertToGray(
        JNIEnv *env,
        jobject instance,
        jobject bitmapIn,
        jobject bitmapOut){

    AndroidBitmapInfo infocolor; //AndroidBitmap图片信息
    AndroidBitmapInfo infogray;
    void* pixelcolor; //图片地址
    void* pixelgray;
    int ret;
    int y;
    int x;

    LOGD("Congratulations! Here is jni method convertToGray! ");
    //AndroidBitmap_getInfo(env,bitmapIn,&infocolor); 获取图片信息
    //AndroidBitmap_lockPixels(env,bitmapIn,&pixelcolor) 获取图片地址

    //没有图片信息
    if ((ret = AndroidBitmap_getInfo(env, bitmapIn,&infocolor)) < 0){
        LOGE("AndroidBitmap_getInfo(bitmapIn) FAILED ! error = %d", ret);
        return;
    }

    if ((ret = AndroidBitmap_getInfo(env, bitmapOut, &infogray)) < 0){
        LOGE("AndroidBitmap_getInfo(bitmapOut) FAILED ! error = %d", ret);
        return;
    }

    //获得图片的宽和高
    LOGI("BitmapIn : width is %d, height is %d, stride is %d, format is %d, flags is %d",
         infocolor.width,
         infocolor.height,
         infocolor.stride,
         infocolor.format,
         infocolor.flags);

    //不是24位图片
    if (infocolor.format != ANDROID_BITMAP_FORMAT_RGBA_8888){
        LOGE("BitmapIn's format is not RGBA_8888");
        return;
    }

    LOGI("ImageOut : width is %d, height is %d, stride is %d, format is %d, flags is %d",
         infogray.width,
         infogray.height,
         infogray.stride,
         infogray.format,
         infogray.flags);

    //不是8位图片
    if (infogray.format != ANDROID_BITMAP_FORMAT_A_8){
        LOGE("BitmapOut's format is not A_8 !");
        return;
    }

    //锁定后,pixelcolor 指向图片的首地址
    if ((ret = AndroidBitmap_lockPixels(env, bitmapIn, &pixelcolor)) < 0){
        LOGE("AndroidBitmap_lockPixels(bitmapIn) FAILED ! error = %d", ret);
    }

    if ((ret = AndroidBitmap_lockPixels(env, bitmapOut, &pixelgray)) < 0){
        LOGE("AndroidBitmap_lockPixels(bitmapOut) FAILED ! error = %d", ret);
    }

    for (y = 0; y < infocolor.height; ++y) {
        //每一行的首地址（刚开始是图片的首地址，即第一行的首地址，下面会换行）
        //彩图是每个像素是4个bytes，就是32位的，所以要用一个32位的地址来接收
        argb* line = (argb*) pixelcolor;
        //灰图每个像素是1个byte，就是8位
        unsigned * grayline = (unsigned *)pixelgray;

        //一个像素一个像素的改，将像素的R，G，B，按照一定的比例缩小
        for (x = 0; x < infocolor.width; ++x) {
            grayline[x] = 0.3*line[x].red + 0.59*line[x].green + 0.11*line[x].blue;
        }

        //换行， 每行的首地址+每行的跨度 = 下一行的首地址
        pixelcolor = (char *) pixelcolor + infocolor.stride;
        pixelgray = (char *) pixelgray + infogray.stride;
    }

    LOGI("unLocking pixels");
    AndroidBitmap_unlockPixels(env,bitmapOut);
    AndroidBitmap_unlockPixels(env,bitmapIn);

}

JNIEXPORT void JNICALL
Java_com_ted_jnihandleimage_JniHandle_changeBright(JNIEnv *env, jobject instance,
                                                   jobject bitmap, jint type) {

    AndroidBitmapInfo infogray;
    void* pixelgray;
    int ret;
    int x,y;
    unsigned save;

    if ((ret = AndroidBitmap_getInfo(env, bitmap, &infogray)) < 0){
        LOGE("AndroidBitmap_getInfo FAILED ! error = %d", ret);
        return;
    }

    LOGI("Change Brightness , type is %d" ,type);

    LOGI("Bitmap : width is %d, height is %d, stride is %d, format is %d, flags is %d",
        infogray.width,
        infogray.height,
        infogray.stride,
        infogray.format,
        infogray.flags);

    if (infogray.format != ANDROID_BITMAP_FORMAT_A_8){
        LOGE("Bitmpa format is not A_8");
        return;
    }

    if ((ret = AndroidBitmap_lockPixels(env, bitmap, &pixelgray)) < 0){
        LOGE("AndroidBitmap_lockPixel() failed ! error = %d", ret);
    }

    LOGI("Begin to modify pixels...");

    for (y = 0; y < infogray.height; ++y) {
        unsigned * grayline = (unsigned *) pixelgray;
        int v;
        for (x = 0; x < infogray.width; ++x) {
            v = (int)grayline[x];
            if (type == 1){
                v -= 5;
            }else{
                v += 1;
            }
            if (v >= 255){
                grayline[x] = 255;
            }else if (v < 0){
                grayline[x] = 0;
            } else{
                grayline[x] = (unsigned)v;
            }
        }
        pixelgray = (char *)pixelgray + infogray.stride;
    }
    AndroidBitmap_unlockPixels(env, bitmap);
}
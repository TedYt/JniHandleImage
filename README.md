# JniHandleImage

This is a my experience. I want to know who to handle image via jin code. I think it's awsome.

#Android studio add JNI code 
Must put ndk block into defaultconfig block, like this

defaultConfig {
        ...
        ndk {
            moduleName "jniimage"
        }
        ...
}

Otherwise, there will be a runtime error like this:
08-22 07:59:23.532 13476 13476 E AndroidRuntime: java.lang.UnsatisfiedLinkError: dalvik.system.PathClassLoader[DexPathList[[zip file "/data/app/com.ted.jnihandleimage-2/base.apk"],nativeLibraryDirectories=[/data/app/com.ted.jnihandleimage-2/lib/arm64, /data/app/com.ted.jnihandleimage-2/base.apk!/lib/arm64-v8a, /vendor/lib64, /system/lib64]]] couldn't find "libjniimage.so"

And You can find libjniimage.so under the folder of app/build/intermediates/ndk/debug/lib/ 
There are all type so files, such as arm64-v8a, armeabi, armeabi-v7a and so on.

#Logcat in JNI
Please put ' ldLibs "log" ' in ndk block, like this

defaultConfig {
        ...
        ndk {
            moduleName "jniimage"
            ldLibs "log"
        }
        ...
}

add some code in c file, like this 
#define TAG "jniHandleImage"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,TAG ,__VA_ARGS__) // 定义LOGD类型

And then you can catch log via ' adb logcat -s jniHandleImage '


#send Bitmap to JNI
This is no Bitmap Object in JNI, but you can use Object to receive the argument of Object
For example, you call the JNI method like this :
    mJniHandle.convertToGray(mBitmapOrig, mBitmapGray);
The declaration of the method will be like this :
    public native void convertToGray(Object bitmapIn, Object bitmapOut);


#Bitmap operation in JNI
If you want to use these methods:
    AndroidBitmap_getInfo(...)
    AndroidBitmap_lockPixels(...)
    AndroidBitmap_unlockPixels(...)

1. add #include <android/bitmap.h> in c file
2. put "jnigraphics" in ndk block in build.gradle, like this 

defaultConfig {
        ...
        ndk {
            moduleName "jniimage"
            ldLibs "log","jnigraphics"
        }
        ...
}


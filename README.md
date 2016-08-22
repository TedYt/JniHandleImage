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

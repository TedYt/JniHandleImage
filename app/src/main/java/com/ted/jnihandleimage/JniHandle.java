package com.ted.jnihandleimage;

//import android.graphics.Bitmap;

/**
 * Created by android on 8/22/16.
 */
public class JniHandle {

    static {
        System.loadLibrary("jniimage");
    }

    public native String test();
    public native void convertToGray(Object bitmapIn, Object bitmapOut);
    public native void changeBright(Object bitmap, int type);
}

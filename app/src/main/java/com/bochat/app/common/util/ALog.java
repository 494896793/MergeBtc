package com.bochat.app.common.util;

import android.util.Log;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/09 16:46
 * Description :
 */

public class ALog {
    
    private static final String TAG = "bochat_debug";

    public static void v(String msg){
        Log.v(TAG, msg);
    }
    
    public static void i(String msg){
        Log.i(TAG, msg);
    }
    
    public static void i(String tag, String msg){
        Log.i(TAG, tag + " : " + msg);
    }
 
    public static void i(String parentTag, String tag, String msg){
        Log.i(parentTag, tag + " : " + msg);
    }
    
    
    public static void d(String msg){
        Log.d(TAG, msg);
    }
    
    public static void d(String tag, String msg){
        Log.d(TAG, tag + " : " + msg);
    }
 
    public static void d(String parentTag, String tag, String msg){
        Log.d(parentTag, tag + " : " + msg);
    }
    
    
    public static void w(String msg){
        Log.w(TAG, msg);
    }
    
    public static void w(String tag, String msg){
        Log.w(TAG, tag + " : " + msg);
    }
 
    public static void w(String parentTag, String tag, String msg){
        Log.w(parentTag, tag + " : " + msg);
    }
    
    
    public static void e(String msg){
        Log.e(TAG, msg);
    }
    
    public static void e(String tag, String msg){
        Log.e(TAG, tag + " : " + msg);
    }
 
    public static void e(String parentTag, String tag, String msg){
        Log.e(parentTag, tag + " : " + msg);
    }
}
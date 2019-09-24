package com.bochat.app.model.util;

import android.util.Log;

/**
 * Created by ldl on 2017/9/27 0027.
 */

public class LogUtil {
    public static final boolean isDebug=true;
    public static final String TAG="ldl tag";
    //规定每段显示的长度
    private static int LOG_MAXLENGTH = 2000;

    public static void LogDebug(String tag,String debug){
        if (isDebug){
            int strLength = debug.length();
            int start = 0;
            int end = LOG_MAXLENGTH;
            for (int i = 0; i < 100; i++) {
                //剩下的文本还是大于规定长度则继续重复截取并输出
                if (strLength > end) {
                    Log.d(tag + i, debug.substring(start, end));
                    start = end;
                    end = end + LOG_MAXLENGTH;
                } else {
                    Log.d(tag, debug.substring(start, strLength));
                    break;
                }
            }
        }
    }
    public static void LogDebug(String debug){
        if (isDebug){
            int strLength = debug.length();
            int start = 0;
            int end = LOG_MAXLENGTH;
            for (int i = 0; i < 100; i++) {
                //剩下的文本还是大于规定长度则继续重复截取并输出
                if (strLength > end) {
                    Log.d(TAG + i, debug.substring(start, end));
                    start = end;
                    end = end + LOG_MAXLENGTH;
                } else {
                    Log.d(TAG, debug.substring(start, strLength));
                    break;
                }
            }
        }
    }
    public static void LogError(String tag,String debug){
        if (isDebug){
            Log.e(tag,debug);
        }
    }
    public static void LogError(String debug){
        if (isDebug){
            Log.e(TAG,debug);
        }
    }
}

package com.bochat.app.common.util;

/**
 * Author      : FJ
 * CreateDate  : 2019/07/10 18:55
 * Description :
 */

public class NumberUtil {
    public static double parseDouble(String num){
        try {
            num = num.replaceAll(",", "");
            return Double.valueOf(num);   
        } catch (Exception ignore){
        }
        return 0.0f;
    }
}

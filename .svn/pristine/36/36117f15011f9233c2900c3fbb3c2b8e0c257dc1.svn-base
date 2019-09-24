package com.bochat.app.common.util;

import android.text.TextUtils;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/16 16:31
 * Description :
 */

public class  StringUtil {
    
    /**
     * Author      : FJ
     * CreateDate  : 2019/4/16 0016 下午 4:33
     * Description : 判断手机号是否合法   
     */
    public static boolean isPhoneNumEffective(String phoneNum){
        return !TextUtils.isEmpty(phoneNum) && phoneNum.length() == 11;
    }

    public static boolean isEmptyString(String str) {
        return str == null || "".equals(str) || "null".equals(str);
    }
    
    public static boolean isNotEmpty(Object o) {
        return !(null == o || o.toString().trim().equals("")) ;
    }

    public static boolean isEmpty(Object o) {
        return (null == o || o.toString().trim().equals("")) ? true : false;
    }
    
}

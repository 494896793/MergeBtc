package com.bochat.app.common.util;

import android.text.TextUtils;

import com.bochat.app.model.util.LogUtil;

import java.security.MessageDigest;

/**
 * 2019/4/18
 * Author LDL
 **/
public class MD5Util {

    public static String lock(String key){
        if(TextUtils.isEmpty(key)){
            return "";
        }
        key="Bochat2019"+key+"001";
        //十六进制转换表，0xf
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] btInput = key.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获取摘要密文
            byte[] md = mdInst.digest();

            //md是字节类型的数组，而MD5是一串十六进制的字符
            // 因此要把密文转换成十六进制的字符串形式，长度为32个字符
            int j = md.length;
            //开辟一个缓冲区，用于存转换结果
            char str[] = new char[j * 2];
            int k = 0;
            //加噪声
            for (int i = 0; i < j; i++) {
                //逐个取摘要密文中的字符
                byte byte0 = md[i];
                //无符号右移4位，与0xf（[1111]）取与，获得高四位
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                //获取低四位，这也是srt[]数组长度是2*j的原因
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str).toLowerCase();
        } catch (Exception e) {
            LogUtil.LogError("生成MD5失败", "====");
            return null;
        }
    }

}

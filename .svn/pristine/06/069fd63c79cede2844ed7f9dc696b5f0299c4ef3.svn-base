package com.bochat.app.model.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 2019/7/10
 * Author LDL
 **/
public class NumUtils {

    public static String CointNum(double num, int scale) {
        BigDecimal bigDecimal = new BigDecimal(num);
        return bigDecimal.setScale(scale, RoundingMode.HALF_EVEN).toPlainString();
    }

}

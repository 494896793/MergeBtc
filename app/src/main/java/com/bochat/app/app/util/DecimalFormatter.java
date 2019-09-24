package com.bochat.app.app.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class DecimalFormatter extends DecimalFormat {

    private static DecimalFormatter instance;

    private DecimalFormatter() {
    }

    public static DecimalFormatter newInstance() {
        if (instance == null) {
            synchronized (DecimalFormatter.class) {
                if (instance == null) {
                    instance = new DecimalFormatter();
                }
            }
        }
        return instance;
    }

    public BigDecimal scaleOfRules(double value, int rules, RoundingMode mode) {
        return BigDecimal.valueOf(value).setScale(rules, mode);
    }

    public String format(String pattern, double value) {
        return format(pattern, new BigDecimal(value));
    }

    public String format(String pattern, BigDecimal value) {
        instance.applyPattern(pattern);
        return instance.format(value.doubleValue());
    }
}

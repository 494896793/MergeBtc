package com.bochat.app.common.util;

import android.support.annotation.StringDef;
import android.util.Log;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings(value = {"unused", "WeakerAccess"})
public final class ULog {

    private static final String TAG = "ULog";

    private static final String LOG_INFO = "INFO";
    private static final String LOG_DEBUG = "DEBUG";
    private static final String LOG_ERROR = "ERROR";
    private static final String LOG_VERBOSE = "VERBOSE";
    private static final String LOG_WARN = "WARN";

    @StringDef({LOG_INFO, LOG_DEBUG, LOG_ERROR, LOG_VERBOSE, LOG_WARN})
    @Retention(RetentionPolicy.SOURCE)
    private @interface LogTypeDef {
    }

    private static final ThreadLocal<StackTraceElement> realStackTraceElement = new ThreadLocal<StackTraceElement>() {
        @Override
        protected StackTraceElement initialValue() {
            return null;
        }
    };

    private static void queryRealStackTraceElement() {

        boolean isTrace = false;

        StackTraceElement[] elements = Thread.currentThread().getStackTrace();

        for (StackTraceElement element : elements) {

            boolean isLog = element.getClassName().equals(ULog.class.getName());
            if (isTrace && !isLog) {
                realStackTraceElement.set(element);
                break;
            }
            isTrace = isLog;
        }
    }

    public static int getLineNumber() {
        StackTraceElement element = realStackTraceElement.get();
        if (isNull(element))
            return 0;
        return element.getLineNumber();
    }

    public static String getClassName() {
        StackTraceElement element = realStackTraceElement.get();
        if (isNull(element))
            return "";
        return element.getClassName();
    }

    public static String getFileName() {
        StackTraceElement element = realStackTraceElement.get();
        if (isNull(element))
            return "";
        return element.getFileName();
    }

    public static String getMethodName() {
        StackTraceElement element = realStackTraceElement.get();
        if (isNull(element))
            return "";
        return element.getMethodName();
    }

    public static void d(String format, Object... msg) {
        sl(LOG_DEBUG, format, msg);
    }

    public static void i(String format, Object... msg) {
        sl(LOG_INFO, format, msg);
    }

    public static void e(String format, Object... msg) {
        sl(LOG_ERROR, format, msg);
    }

    public static void v(String format, Object... msg) {
        sl(LOG_VERBOSE, format, msg);
    }

    public static void w(String format, Object... msg) {
        sl(LOG_WARN, format, msg);
    }

    private static <T> boolean isNull(T obj) {
        return obj == null;
    }

    private static void sl(@LogTypeDef String type, String format, Object... msg) {
        queryRealStackTraceElement();
        String tag = format("[%s-%s](%s:%d)", ULog.class.getSimpleName(), type, getFileName(), getLineNumber());
        switch (type) {
            case LOG_INFO:
                Log.i(tag, format(format, msg));
                break;
            case LOG_DEBUG:
                Log.d(tag, format(format, msg));
                break;
            case LOG_ERROR:
                Log.e(tag, format(format, msg));
                break;
            case LOG_VERBOSE:
                Log.v(tag, format(format, msg));
                break;
            case LOG_WARN:
                Log.w(tag, format(format, msg));
                break;
        }
    }

    private static List<Object> parse(Object... msg) {
        return Collections.synchronizedList(new LinkedList<>(Arrays.asList(msg)));
    }

    private static String format(String format, Object... msg) {

        List formatMsg = parse(msg);
        Iterator iterator= formatMsg.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            if(format.contains("%@")) {
                format = format.replace("%@", o != null ? o.toString() : "[Null Object]");
                iterator.remove();
            }
        }
        return String.format(format, formatMsg.toArray());

    }

}

package com.bochat.app.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * <p>@ProjectName:     BoChat</p>
 * <p>@ClassName:       DateUtil.java</p>
 * <p>@PackageName:     com.bochat.app.utils</p>
 * <b>
 * <p>@Description:     类描述</p>
 * </b>
 * <p>@author:          FreddyChen</p>
 * <p>@date:            2019/02/22 18:36</p>
 * <p>@email:           chenshichao@outlook.com</p>
 */
public class DateUtil {

    /**
     * 格式化消息时间（根据微信规则）
     * 目前可用于会话列表页、聊天页等
     * 规则：
     * 给定一个时间戳，先判断是否是今天，即当天00:00:00的时间戳
     * 即：2018年10月20日00:00:00秒的时间为1539964800，小于此值则为19日
     * 如果不是今天，则判断是否为昨天00:00:00的时间戳，小于1539964800并且大于等于1539878400则为昨天（2018年10月19）
     * 如果即不是今天也不是昨天，则格式化时间为yyyy-MM-dd显示
     *
     * @param timestamp 时间戳（单位：毫秒）
     */
    public static String formatChatTime(long timestamp) {
        String chatTimeText = null;
        try {
            timestamp = timestamp;
            SimpleDateFormat sdf = null;
            Calendar chatTimeCalendar = Calendar.getInstance();
            boolean isToday = isToday(timestamp);
            if (isToday) {
                // 如果是今天，格式化时间为HH:mm显示
                sdf = new SimpleDateFormat("HH:mm");
                chatTimeCalendar.setTimeInMillis(timestamp);
                chatTimeText = sdf.format(chatTimeCalendar.getTime());
            } else {
                boolean isYesterday = isYesterday(timestamp);
                if (isYesterday) {
                    chatTimeText = "昨天";
                } else {
                    sdf = new SimpleDateFormat("yyyy-MM-dd");
                    chatTimeCalendar.setTimeInMillis(timestamp);
                    chatTimeText = sdf.format(chatTimeCalendar.getTime());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return chatTimeText;
        }
    }

    public static boolean isThisYear(long timestamp) {
        // 获取当年1月1日00:00:00的时间戳，小于此时间戳的timestamp则为去年
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), 0, 0, 0, 0, 0);
        System.out.println(cal.getTimeInMillis());
        // 今天开始时间
        long thisYearBeginTimestamp = cal.getTimeInMillis();
        return timestamp >= thisYearBeginTimestamp;
    }

    public static boolean isToday(long timestamp) {
        // 获取当天00:00:00的时间戳，小于此时间戳的timestamp则为昨天
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        // 今天开始时间
        long todayBeginTimestamp = cal.getTimeInMillis();
        return timestamp >= todayBeginTimestamp;
    }

    public static boolean isYesterday(long timestamp) {
        // 获取当天00:00:00的时间戳，小于此时间戳的timestamp则为昨天
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        // 今天开始时间
        long todayBeginTimestamp = cal.getTimeInMillis();
        Calendar cal2 = Calendar.getInstance();
        cal2.set(cal2.get(Calendar.YEAR), cal2.get(Calendar.MONTH), cal2.get(Calendar.DAY_OF_MONTH) - 1, 0, 0, 0);
        // 昨天开始时间
        long yesterdayBeginTimestamp = cal2.getTimeInMillis();
        return timestamp >= yesterdayBeginTimestamp && timestamp < todayBeginTimestamp;
    }

    /**
     * 时间戳转yyyy-MM-dd格式
     *
     * @param timestamp
     * @return
     */
    public static String formatTo_yyyy_MM_dd(long timestamp) {
        if (timestamp == 0) {
            return null;
        }

        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timestamp);
            return dateFormat.format(calendar.getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * 时间戳转yyyy-MM-dd格式
     *
     * @param timestamp
     * @return
     */
    public static String formatTo_MM_dd_HH_mm_ss(long timestamp) {
        if (timestamp == 0) {
            return null;
        }

        try {
            DateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timestamp);
            return dateFormat.format(calendar.getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * 时间戳转yyyy-MM-dd格式
     *
     * @param timestamp
     * @return
     */
    public static String format(long timestamp,String format) {
        if (timestamp == 0) {
            return null;
        }
        try {
            DateFormat dateFormat = new SimpleDateFormat(format);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timestamp);
            return dateFormat.format(calendar.getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public static String parse(long timestamp,String format) {
        if (timestamp == 0) {
            return null;
        }
        try {
            DateFormat dateFormat = new SimpleDateFormat(format);
            return format(dateFormat.parse(timestamp+"").getTime(),"yyyy-MM-dd HH:mm:ss");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public static long parse(String content,String format) {

        try {
            DateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.parse(content).getTime();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0L;
    }


}

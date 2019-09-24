package com.bochat.app.model.util;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 2019/6/14
 * Author LDL
 **/
public class TimeUtils {

    private static long networkTimeOffset;
    
    public static void setNetworkTimeOffset(long offset){
        networkTimeOffset = offset;
    }
    
    public static long getNetworkTimeOffset(){
        return networkTimeOffset;
    }
    
    public static int getDistanceTimemin(String startTime, String endTime) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        Date two;
        long day = 0;//天数差
        long hour = 0;//小时数差
        long min = 0;//分钟数差
        long second=0;//秒数差
        try {
            final Calendar c = Calendar.getInstance();
            c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));

            one = df.parse(startTime);
            c.setTime(one);
            two = df.parse(endTime);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff ;
            diff = time2 - time1;

            day = diff / (24 * 60 * 60 * 1000);
            //            Log.i("lgq","diff--==="+diff+"...one="+time1+"..-----.two==="+time2);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            second = diff/1000;
            Log.i("====","====day:"+day);
            Log.i("====","====hour:"+hour);
            Log.i("====","====min:"+min);
            Log.i("====","====second:"+second);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (int)min;
    }

    public static String getDate2String(long time, String pattern) {
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
        return format.format(date);
    }

    public static long getTimeExpend(String startTime, String endTime){
        //传入字串类型 2016/06/28 08:30
        long longStart = getTimeMillis(startTime); //获取开始时间毫秒数
        long longEnd = getTimeMillis(endTime);  //获取结束时间毫秒数
        long longExpend = longEnd - longStart;  //获取时间差

        long longDay =longExpend / (60 * 60 * 60 * 1000); //根据时间差来计算天数
        long longHours = longExpend / (60 * 60 * 1000); //根据时间差来计算小时数
        long longMinutes = (longExpend - longHours * (60 * 60 * 1000)) / (60 * 1000);   //根据时间差来计算分钟数

        //        return longHours + ":" + longMinutes;
        return longHours*60+longMinutes;
    }

    /*获得传入时间与当前时间的时间差*/
    public static long getTimeExpendSecond(String startTime){
        //传入字串类型 2016/06/28 08:30
        long longStart = getTimeMillis(startTime); //获取开始时间毫秒数
        long longEnd = new Date().getTime();
        long longExpend = longEnd - longStart;  //获取时间差

        long longDay =longExpend / (60 * 60 * 60 * 1000); //根据时间差来计算天数
        long longHours = longExpend / (60 * 60 * 1000); //根据时间差来计算小时数
        long longMinutes = (longExpend - longHours * (60 * 60 * 1000)) / (60 * 1000);   //根据时间差来计算分钟数

        //        return longHours + ":" + longMinutes;
        return longHours*60+longMinutes;
    }

    public static long getTimeMillis(String strTime) {
        long returnMillis = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date d = null;
        try {
            d = sdf.parse(strTime);
            returnMillis = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            //            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
        return returnMillis;
    }

}

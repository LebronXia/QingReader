package com.riane.qingreader.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Riane on 2017/7/16.
 */

public class TimeUtil {

    private static long timeMillseconds;
    private static String pat1 = "yyyy-MM-dd HH:mm:ss";
    private static SimpleDateFormat sdf1 = new SimpleDateFormat(pat1);

    public static String getTranslateTime(String time){
        /*
        * 刚刚
        * 几分钟前
        * 几小时前
        * 几月几号
        * */
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date nowTime = new Date();
        long currentTimeMillseconds = nowTime.getTime();
        String currentTime = sdf1.format(nowTime);

        try {
            Date date = format.parse(time);
            timeMillseconds = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long remainingTime = currentTimeMillseconds - timeMillseconds;

        //一分钟以内
        if ( remainingTime < 60000){
            return "刚刚";
        }

        if (remainingTime < 3600000){
            long longMinute = remainingTime / 60000;
           int minute = (int) (longMinute % 100);
            return longMinute + "分钟之前";
        }

        long l = 24 * 60 * 60 * 1000;
        //小于一天
        if (remainingTime < l){
            //long longHour =  l - remainingTime;
            long longHour = (int) (remainingTime / 3600000);
            int hour = (int) (longHour % 100);
            return hour + "小时之前";
        }
        //当大于1天的时候，显示几月几号
        if (remainingTime >= l){
            String year = time.substring(0, 4);
            String currentYear = currentTime.substring(0, 4);
            if (year.equals(currentYear)){
                return time.substring(5, 10);
            } else {
                return time.substring(0, 10);
            }

        }
        return time;
    }
}

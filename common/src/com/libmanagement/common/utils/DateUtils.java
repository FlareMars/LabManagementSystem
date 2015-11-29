package com.libmanagement.common.utils;

import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateUtils {

    private static Logger logger = Logger.getLogger(DateUtils.class);

    public static String getDateStr(long timestamp) {

        Date date = null;

        try {
            date = new Date(timestamp);
        } catch (Exception e) {
            date = new Date();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return sdf.format(date);

    }

    public static String getNowDateStr() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public static String getNowDateSimpleStr() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(date);
    }

    public static long getNowDateTime() {
        return System.currentTimeMillis();
    }

    public static Long getTimeStamp(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(dateStr).getTime();
        } catch (ParseException e) {
            return null;
        }
    }

}

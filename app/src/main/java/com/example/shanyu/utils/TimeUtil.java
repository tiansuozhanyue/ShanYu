package com.example.shanyu.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    public static String stampToDate(String s) {
        String res = "";
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            long lt = new Long(s);
            Date date = new Date(lt);
            res = simpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param date   字符串日期
     * @param format 如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String date2TimeStamp(String date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String stampToDate2(String s) {
        String res = "";
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            long lt = new Long(s);
            Date date = new Date(lt);
            res = simpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }


    public static String stampToDate3(String s) {
        String res = "";
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
            long lt = new Long(s);
            Date date = new Date(lt*1000);
            res = simpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

}

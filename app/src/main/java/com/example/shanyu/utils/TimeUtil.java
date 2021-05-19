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
}

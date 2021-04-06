package com.example.shanyu.utils;

/**
 * author: heroCat
 * time  : 2018/5/25/025.
 * desc  :
 */
public class StringUtil {

    public static boolean isEmpty(String info) {
        if (info == null || info.isEmpty())
            return true;
        return false;
    }

    public static String mobileSecurityInfo(String info) {
        if (isEmpty(info))
            return " ";
        return info.substring(0, 3) + "****" + info.substring(7, 11);
    }

}

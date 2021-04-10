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

    public static boolean isPhoneNumber(String info) {
        if (isEmpty(info) || info.length() != 11)
            return false;
        return true;
    }

    public static boolean isVerifyCode(String info) {
        if (isEmpty(info) || info.length() != 6)
            return false;
        return true;
    }

    public static String mobileSecurityInfo(String info) {
        if (isEmpty(info))
            return " ";
        return info.substring(0, 3) + "****" + info.substring(7, 11);
    }

}

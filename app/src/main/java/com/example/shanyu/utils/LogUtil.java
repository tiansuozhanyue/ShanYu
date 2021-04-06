package com.example.shanyu.utils;

import android.util.Log;


/**
 * author: heroCat
 * time  : 2018/5/25/025.
 * desc  :
 */
public class LogUtil {

    private static String NETLOG = "netLog";
    private static String CAHTINfo = "chatInfo";
    private static String TESTLOG = "testLog";

    public static void net_i(String info) {
        Log.i(NETLOG, info);
    }

    public static void i(String info) {
        Log.i(TESTLOG, info);
    }

    public static void e(String info) {
        Log.e(TESTLOG, info);
    }

    public static void c(String info) {
        Log.i(CAHTINfo, info);
    }


}

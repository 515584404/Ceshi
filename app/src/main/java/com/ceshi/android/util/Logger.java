package com.ceshi.android.util;

import android.util.Log;

/**
 * 管理Log输出
 * Created by killer on 14/12/14.
 */
public final class Logger {

    /* 日志的级别 显示级别参考android.util.Log*/
    /**
     * 配置0全部显示
     * 配置大于7全部不显示
     */
    public static final int LEVLE=0;

    //<=2
    public static void v(String tag, String msg) {
        if (LEVLE <= Log.VERBOSE)
            Log.v(tag, msg);
    }

    public static void v(String tag, String msg, Throwable tr) {
        if (LEVLE <= Log.VERBOSE)
            Log.v(tag, msg, tr);
    }

    //<=3
    public static void d(String tag, String msg) {
        if (LEVLE <= Log.DEBUG)
            Log.d(tag, msg);
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (LEVLE <= Log.DEBUG)
            Log.d(tag, msg, tr);
    }

    //<=4
    public static void i(String tag, String msg) {
        if (LEVLE <= Log.INFO)
            Logger.d(tag, msg);
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (LEVLE <= Log.INFO)
            Logger.d(tag, msg, tr);
    }

    //<=5
    public static void w(String tag, String msg) {
        if (LEVLE <= Log.WARN)
            Log.w(tag, msg);
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (LEVLE <= Log.WARN)
            Log.w(tag, msg, tr);
    }

    //<=5
    public static void w(String tag, Throwable tr) {
        if (LEVLE <= Log.WARN)
            Log.w(tag, tr.getMessage(), tr);
    }

    public static void e(String tag, String msg) {
        if (LEVLE <= Log.ERROR)
            Log.e(tag, msg);
    }

    //<=6
    public static void e(String tag, String msg, Throwable tr) {
        if (LEVLE <= Log.ERROR)
            Log.e(tag, msg, tr);
    }

    public static void e(String tag, Throwable tr) {
        if (LEVLE <= Log.ERROR)
            Log.e(tag, tr.getMessage(), tr);
    }

    //<=7
    public static void wtf(String tag, String msg) {
        if (LEVLE <= Log.ASSERT)
            Log.wtf(tag, msg);
    }

    public static void wtf(String tag, Throwable tr) {
        if (LEVLE <= Log.ASSERT)
            Log.wtf(tag, tr);
    }

    public static void wtf(String tag, String msg, Throwable tr) {
        if (LEVLE <= Log.ASSERT)
            Logger.wtf(tag, msg, tr);
    }

}

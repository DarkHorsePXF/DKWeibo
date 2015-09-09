package com.dk.dkweibo.support.utils;

import android.util.Log;

/**
 * Created by feng on 2015/9/8.
 */
public class LogUtil {
    private static final boolean logable = true;

    private static final String DEBUG = "debug";
    private static final String ERROR = "error";


    /**
     * Log.v(tag,msg)
     *
     * @param tag
     * @param msg
     * @return
     */
    public static void v(String tag, String msg) {
        if (logable) {
            Log.v(tag, msg);
        }
    }

    public static void v(String msg) {
        if (logable) {
            Log.v(DEBUG, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (logable) {
            Log.e(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (logable) {
            Log.e(tag, msg, tr);
        }
    }

    public static void e(String msg, Throwable tr) {
        if (logable) {
            Log.e(ERROR, msg, tr);
        }
    }

    public static void e(String msg) {
        if (logable) {
            Log.e(ERROR, msg);
        }
    }

}

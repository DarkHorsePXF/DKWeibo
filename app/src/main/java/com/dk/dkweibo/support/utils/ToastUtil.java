package com.dk.dkweibo.support.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by feng on 2015/7/18.
 */
public class ToastUtil {
    private static Context context = GlobalContext.getInstance();

    public static void longToast(String string){
        Toast.makeText(context,string,Toast.LENGTH_LONG).show();
    }

    public static void shortToast(String string){
        Toast.makeText(context,string,Toast.LENGTH_SHORT).show();
    }
}

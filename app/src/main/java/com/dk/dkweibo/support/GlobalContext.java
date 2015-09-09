package com.dk.dkweibo.support;

import android.app.Application;

/**
 * Created by feng on 2015/8/28.
 */
public class GlobalContext extends Application {

    private static GlobalContext globalContext = null;

    @Override
    public void onCreate() {
        super.onCreate();
        globalContext = this;
    }

    public static GlobalContext getInstance() {
        return globalContext;
    }
}

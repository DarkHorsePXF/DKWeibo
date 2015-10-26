package com.dk.dkweibo.support.utils;

import android.content.Context;
import com.dk.dkweibo.support.GlobalContext;

/**
 * Created by feng on 2015/10/5.
 */
public class DisplayUtil {
    public static int dp2px(float dp) {
        final float scale = GlobalContext.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int px2dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }
}

package com.dk.dkweibo.support.utils;

import android.app.Activity;
import android.view.View;

/**
 * Created by feng on 2015/9/24.
 */
public class ViewUtil {
    @SuppressWarnings({"unchecked", "UnusedDeclaration"})
    public static <T extends View> T findViewById(View view, int id) {
        return (T) view.findViewById(id);
    }

    @SuppressWarnings({"unchecked", "UnusedDeclaration"})
    public static <T extends View> T findViewById(Activity activity, int id) {
        return (T) activity.findViewById(id);
    }
}

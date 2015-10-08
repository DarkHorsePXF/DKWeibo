package com.dk.dkweibo.support.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.dk.dkweibo.bean.User;

/**
 * Created by feng on 2015/9/8.
 */
public class UserKeeper {
    private static final String PREFERENCES_NAME = "com_dk_dkweibo_user";

    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_HEAD_URL = "head_url";
    private static final String KEY_DESCRIPTION = "description";

    /**
     * 保存用户基本资料
     * @param context 上下文
     * @param user 用户对象
     */
    public static void writeUserKeeper(Context context, User user) {
        if (context == null || user == null) {
            return;
        }

        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);

        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_USER_NAME, user.getUserName());
        editor.putString(KEY_USER_HEAD_URL, user.getLargeUserHeadUrl());
        editor.putString(KEY_DESCRIPTION, user.getDescription());
        editor.commit();
    }

    public static User readUserKeeper(Context context){
        if (context==null)
            return null;

        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,Context.MODE_APPEND);

        User user =new User();
        user.setUserName(pref.getString(KEY_USER_NAME,""));
        user.setLargeUserHeadUrl(pref.getString(KEY_USER_HEAD_URL, ""));
        user.setDescription(pref.getString(KEY_DESCRIPTION,""));

        return user;
    }


}

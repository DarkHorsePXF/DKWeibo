package com.dk.dkweibo.support.sina;

import android.app.Activity;
import android.content.Context;

import com.dk.dkweibo.support.sharepreference.AccessTokenKeeper;
import com.dk.dkweibo.support.utils.LogUtil;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;

/**
 * Created by feng on 2015/8/29.
 */
public final class AuthHelper {
    private Activity mActivity =null;
    private SsoHandler ssoHandler=null;

    public final static String APP_KEY = "2463214780";

    public final static String APP_SECRET = "fdc250099e6e69a2fb6999e956974543";

    private final static String REDIRECT_URL = "http://www.weibo.com";

    private static final String SCOPE = 							   // 应用申请的高级权限
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write";


    private final static long TIME_LIMIT = 3600*24*1;

    public AuthHelper(Activity activity){
        mActivity = activity;
        AuthInfo authInfo =new AuthInfo(activity.getBaseContext(),APP_KEY,REDIRECT_URL,SCOPE);
        ssoHandler =new SsoHandler(mActivity,authInfo);
    }

    public static boolean isNecessaryUpdate(Context context){

        Oauth2AccessToken mAccessToken = AccessTokenKeeper.readAccessToken(context);
        if (mAccessToken==null){
            return true;
        }
        long tokenTime =  mAccessToken.getExpiresTime();
        long currentTime = System.currentTimeMillis();
        LogUtil.v("token time",Long.toString(tokenTime));
        LogUtil.v("current time",Long.toString(currentTime));
        if (currentTime - tokenTime>=TIME_LIMIT){
            return true;
        }
        return false;
    }

    public void authorizeWeb(WeiboAuthListener listener){
        ssoHandler.authorizeWeb(listener);
    }

    public void authorizeClientSso(WeiboAuthListener listener){
        ssoHandler.authorizeClientSso(listener);
    }

}
package com.dk.dkweibo.support.api;

import android.content.Context;

import com.dk.dkweibo.support.sharepreference.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

/**
 * Created by feng on 2015/9/7.
 */
public abstract class AbsAPI {
    protected Context mContext;
    protected Oauth2AccessToken ACCESS_TOKEN;

    public AbsAPI(Context context){
        this.mContext = context;
        ACCESS_TOKEN = AccessTokenKeeper.readAccessToken(mContext);
    }
}

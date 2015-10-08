package com.dk.dkweibo.support.api;

import android.content.Context;

import com.dk.dkweibo.support.preference.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

/**
 * Created by feng on 2015/9/7.
 */
public abstract class AbsAPI {
    private Oauth2AccessToken accessToken;

    public AbsAPI(Context context){
        this.accessToken = AccessTokenKeeper.readAccessToken(context);
    }

    public Oauth2AccessToken getAccessToken() {
        return accessToken;
    }
}

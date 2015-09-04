package com.dk.dkweibo.bean;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * Created by feng on 2015/8/28.
 */
public class UserBean {
    private String userId;
    private String userName = "username";
    private Drawable userHead;
    private String token;
    private String tokenSecret;
    private String description;



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Drawable getUserHead() {
        return userHead;
    }

    public void setUserHead(Drawable userHead) {
        this.userHead = userHead;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }

    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

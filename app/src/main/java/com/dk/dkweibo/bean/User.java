package com.dk.dkweibo.bean;

import android.graphics.drawable.Drawable;

/**
 * Created by feng on 2015/8/28.
 */
public class User {
    private String userId;
    private String userName = "username";
    private String userHeadUrl;
    private String token;
    private String tokenSecret;
    private String description;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getUserHeadUrl() {
        return userHeadUrl;
    }

    public void setUserHeadUrl(String userHeadUrl) {
        this.userHeadUrl = userHeadUrl;
    }

    @Override
    public String toString() {
        return " user_id:" + userId
                + " username:" + userName
                + " description:" + description
                + " head_url:" + userHeadUrl;
    }
}

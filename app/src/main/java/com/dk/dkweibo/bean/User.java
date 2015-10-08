package com.dk.dkweibo.bean;

import org.json.JSONObject;

/**
 * Created by feng on 2015/8/28.
 */
public class User {
    private String userId;
    private String userName = "username";
    private String largeUserHeadUrl;
    private String profileUserHeadUrl;
    private String backgroundPicUrl;
    private String token;
    private String tokenSecret;
    private String description;

    public static User getUserFromJson(JSONObject json){
        if (json==null||json.length()==0)
            return null;
        User user=new User();
        user.setUserId(json.optString("id"));
        user.setUserName(json.optString("screen_name"));
        user.setDescription(json.optString("description"));
        user.setLargeUserHeadUrl(json.optString("avatar_large"));
        user.setProfileUserHeadUrl(json.optString("profile_image_url"));
        user.setBackgroundPicUrl(json.optString("cover_image_phone"));
        return user;
    }


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

    public String getLargeUserHeadUrl() {
        return largeUserHeadUrl;
    }

    public void setLargeUserHeadUrl(String largeUserHeadUrl) {
        this.largeUserHeadUrl = largeUserHeadUrl;
    }

    @Override
    public String toString() {
        return " user_id:" + userId
                + " username:" + userName
                + " description:" + description
                + " head_url:" + largeUserHeadUrl;
    }

    public String getProfileUserHeadUrl() {
        return profileUserHeadUrl;
    }

    public void setProfileUserHeadUrl(String profileUserHeadUrl) {
        this.profileUserHeadUrl = profileUserHeadUrl;
    }

    public String getBackgroundPicUrl() {
        return backgroundPicUrl;
    }

    public void setBackgroundPicUrl(String backgroundPicUrl) {
        this.backgroundPicUrl = backgroundPicUrl;
    }
}

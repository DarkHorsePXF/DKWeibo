package com.dk.dkweibo.support.sina;

/**
 * Created by feng on 2015/8/29.
 */
public final class OAuth {

    private static OAuth instance = new OAuth();

    private OAuth(){

    }

    public static OAuth getInstance() {
        return instance;
    }

    public final static String APP_KEY = "2463214780";

    public final static String APP_SECRET = "fdc250099e6e69a2fb6999e956974543";

    public final static String REDIRECT_URL = "http://www.weibo.com";

    public static final String SCOPE = 							   // 应用申请的高级权限
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write";

}
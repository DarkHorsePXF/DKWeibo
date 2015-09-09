package com.dk.dkweibo.support;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by feng on 2015/9/4.
 */
public class URLHelper {
    //base url
    private static final String URL_SINA_WEIBO = "https://api.weibo.com/2/";

    private static final String Q = "?";
    private static final String AND = "&";

    //user
    private static final String URL_USERS = URL_SINA_WEIBO + "users/";
    public static final String URL_USER_SHOW = URL_USERS + "show.json";


    /**
     *
     * @param params ?号后面的键值对
     * @return GET请求的URL
     */
    public static String addGETParams(Map<String,String> params){
        if (params.isEmpty()){
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(Q);
        Iterator it = params.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            sb.append(key+"="+value);
            if (it.hasNext()){
                sb.append(AND);
            }
        }
        return sb.toString();
    }

}

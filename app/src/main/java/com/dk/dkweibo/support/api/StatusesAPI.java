package com.dk.dkweibo.support.api;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dk.dkweibo.support.Constant;
import com.dk.dkweibo.support.URLHelper;
import com.dk.dkweibo.support.utils.LogUtil;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by feng on 2015/10/6.
 */
public class StatusesAPI extends AbsAPI {
    public StatusesAPI(Context context) {
        super(context);
    }

    public void doGetHomeTimeline(RequestQueue requestQueue,int page,Response.Listener<JSONObject> listener,Response.ErrorListener errorListener){
        Map<String, Object> params = new HashMap<>();
        params.put(Constant.PARAMS_ACCESS_TOKEN, getAccessToken().getToken());
        params.put(Constant.PARAMS_PAGE,page);

        final JsonObjectRequest request =
                new JsonObjectRequest(
                        Request.Method.GET,
                        URLHelper.URL_STATUSES_HOME_TIMELINE
                                + URLHelper.addGETParams(params),
                        listener,
                        errorListener
                );
        LogUtil.v("request",request.getUrl());
        requestQueue.add(request);
    }

}

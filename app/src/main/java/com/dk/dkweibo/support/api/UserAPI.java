package com.dk.dkweibo.support.api;


import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dk.dkweibo.support.URLHelper;
import com.dk.dkweibo.support.Constant;
import com.dk.dkweibo.support.sina.AuthHelper;
import com.dk.dkweibo.support.utils.LogUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by feng on 2015/9/6.
 */
public class UserAPI extends AbsAPI {
    private final String TAG = "userAPI";

    public UserAPI(Context context) {
        super(context);
    }

    public void show(RequestQueue requestQueue, Response.Listener<JSONObject> listener) {

        Map<String, String> params = new HashMap<>();
        params.put(Constant.PARAMS_UID, ACCESS_TOKEN.getUid());
        params.put(Constant.PARAMS_SOURCE, AuthHelper.APP_KEY);
        params.put(Constant.PARAMS_ACCESS_TOKEN, ACCESS_TOKEN.getToken());
        Log.v("request", params.toString());

        JsonObjectRequest request =
                new JsonObjectRequest(
                        Request.Method.GET,
                        URLHelper.URL_USER_SHOW
                                + URLHelper.addGETParams(params),
                        listener,
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                            }
                        });
        requestQueue.add(request);
    }
}

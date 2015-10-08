package com.dk.dkweibo.support.api;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dk.dkweibo.support.Constant;
import com.dk.dkweibo.support.URLHelper;
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

    public void doGetHomeTimeline(RequestQueue requestQueue,Response.Listener<JSONObject> listener,Response.ErrorListener errorListener){
        Map<String, String> params = new HashMap<>();
        params.put(Constant.PARAMS_ACCESS_TOKEN, getAccessToken().getToken());

        Log.v("params", params.toString());

        final JsonObjectRequest request =
                new JsonObjectRequest(
                        Request.Method.GET,
                        URLHelper.URL_STATUSES_HOME_TIMELINE
                                + URLHelper.addGETParams(params),
                        listener,
                        errorListener
                );
        requestQueue.add(request);
    }
}

package com.dk.dkweibo.support.http;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.dk.dkweibo.support.utils.LogUtil;

/**
 *
 * Created by feng on 2015/9/8.
 *
 * 加载用户头像的Volley请求
 */
public class HeadImageRequest extends ImageRequest {
    private final static int maxWidth = 100;
    private final static int maxHeight = 100;
    private final static ImageView.ScaleType scaleType = ImageView.ScaleType.CENTER_CROP;
    private final static Bitmap.Config config = Bitmap.Config.ARGB_4444;

    private final static Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            LogUtil.e(error.getMessage());
        }
    };


    public HeadImageRequest(String url,Response.Listener<Bitmap> listener) {
        super(url, listener, maxWidth, maxHeight, scaleType, config, errorListener);
    }

}

package com.dk.dkweibo.support;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.dk.dkweibo.support.http.HeadImageRequest;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.DraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;

/**
 * Created by feng on 2015/8/28.
 */
public class GlobalContext extends Application {

    private static GlobalContext globalContext = null;
    private RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();

        globalContext = this;
        mRequestQueue = Volley.newRequestQueue(globalContext);

        Fresco.initialize(globalContext);

        DrawerImageLoader.init(new DrawerImageLoader.IDrawerImageLoader() {
            @Override
            public void set(final ImageView imageView, Uri uri, Drawable drawable) {
                mRequestQueue.add(new HeadImageRequest(uri.toString(), new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        imageView.setImageBitmap(response);
                    }
                }));
            }

            @Override
            public void cancel(ImageView imageView) {

            }

            @Override
            public Drawable placeholder(Context context) {
                return null;
            }
        });

    }

    public static GlobalContext getInstance() {
        return globalContext;
    }
}

package com.dk.dkweibo.ui.activity;

import android.os.Bundle;
import android.webkit.WebView;

import com.dk.dkweibo.R;
import com.dk.dkweibo.ui.common.BaseActivity;

/**
 * 通过OAuth授权进行微博登录
 * Created by feng on 2015/8/28.
 */
public class OAuthActivity extends BaseActivity {

    private WebView wvOAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();


    }

    private void init() {

        setContentView(R.layout.activity_oauth);
        wvOAuth = (WebView) findViewById(R.id.wv_OAuth);

    }
}

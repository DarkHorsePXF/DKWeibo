package com.dk.dkweibo.ui.activity;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.dk.dkweibo.R;
import com.dk.dkweibo.bean.User;
import com.dk.dkweibo.support.api.UserAPI;
import com.dk.dkweibo.support.sharepreference.AccessTokenKeeper;
import com.dk.dkweibo.support.sharepreference.UserKeeper;
import com.dk.dkweibo.support.sina.AuthHelper;
import com.dk.dkweibo.support.utils.LogUtil;
import com.dk.dkweibo.support.utils.ToastUtil;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by feng on 2015/9/8.
 */
public class AppActivity extends BaseActivity {

    private AccountHeader headerResult = null;
    private Drawer drawer = null;
    private Toolbar toolbar = null;
    private User user = null;
    private Oauth2AccessToken mAccessToken;
    private Context mContext;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mContext = this;
        mRequestQueue = Volley.newRequestQueue(mContext);


        user = UserKeeper.readUserKeeper(mContext);
        LogUtil.v("user", user.toString());

        //try to update token
//        if (AuthHelper.isNecessaryUpdate(mContext)) {
            requestOauth();
//        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final IProfile profile = new ProfileDrawerItem()
                .withName(user.getUserName())
                .withEmail(user.getDescription())
                .withIcon(user.getUserHeadUrl());

        headerResult = new AccountHeaderBuilder()
                .withHeaderBackground(R.mipmap.bg_home)
                .withActivity(this)
                .addProfiles(profile)
                .build();

        drawer = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(headerResult)
                .build();

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    private void requestOauth() {
        ArrayList<String> itemValueList = new ArrayList<>();

        itemValueList.add(getString(R.string.oauth_login));
        itemValueList.add(getString(R.string.sso_login));

        new MaterialDialog.Builder(mContext)
                .title("请选择授权方式")
                .items(R.array.oauth_ways)
                .autoDismiss(true)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog materialDialog, View view, int which, CharSequence charSequence) {
                        AuthHelper authHelper = new AuthHelper(AppActivity.this);
                        switch (which) {
                            case 0:
                                authHelper.authorizeWeb(mAuthListener);
                                break;
                            case 1:
                                authHelper.authorizeClientSso(mAuthListener);
                                break;
                            default:
                                break;
                        }

                    }
                })
                .negativeText(R.string.cancel)
                .show();
    }

    private WeiboAuthListener mAuthListener = new WeiboAuthListener() {
        @Override
        public void onComplete(Bundle values) {
            // 从 Bundle 中解析 Token
            mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            if (mAccessToken.isSessionValid()) {
                // 显示 Token
                updateAccount();

                // 保存 Token 到 SharedPreferences
                AccessTokenKeeper.writeAccessToken(AppActivity.this, mAccessToken);
                ToastUtil.shortToast(getString(R.string.auth_success));
            } else {
                // 以下几种情况，您会收到 Code：
                // 1. 当您未在平台上注册的应用程序的包名与签名时；
                // 2. 当您注册的应用程序包名与签名不正确时；
                // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
                String code = values.getString("code");
                String message = getString(R.string.auth_failed);
                if (!TextUtils.isEmpty(code)) {
                    message = message + "\nObtained the code: " + code;
                }
                ToastUtil.shortToast(message);
            }
        }

        @Override
        public void onCancel() {
            ToastUtil.shortToast(getString(R.string.auth_cancel));
        }

        @Override
        public void onWeiboException(WeiboException e) {
            e.printStackTrace();
            ToastUtil.longToast(getString(R.string.auth_exception));
        }
    };

    private void updateAccount() {
        String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(
                new java.util.Date(mAccessToken.getExpiresTime() + 3600 * 24 * 1));
        ToastUtil.shortToast("账户有效期至：" + date);
        UserAPI userAPI = new UserAPI(mContext);
        userAPI.show(mRequestQueue, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LogUtil.v("response", response.toString());
                user.setUserId(response.optString("id"));
                user.setUserName(response.optString("screen_name"));
                user.setDescription(response.optString("description"));
                user.setUserHeadUrl(response.optString("profile_image_url"));

                UserKeeper.writeUserKeeper(AppActivity.this,user);

                final IProfile profile = new ProfileDrawerItem()
                        .withName(user.getUserName())
                        .withEmail(user.getDescription())
                        .withIcon(user.getUserHeadUrl());
                headerResult.updateProfile(profile);
            }
        });
    }


}

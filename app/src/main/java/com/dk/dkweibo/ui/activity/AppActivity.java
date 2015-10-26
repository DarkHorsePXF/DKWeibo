package com.dk.dkweibo.ui.activity;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.dk.dkweibo.R;
import com.dk.dkweibo.bean.User;
import com.dk.dkweibo.support.GlobalContext;
import com.dk.dkweibo.support.api.UserAPI;
import com.dk.dkweibo.support.preference.AccessTokenKeeper;
import com.dk.dkweibo.support.preference.UserKeeper;
import com.dk.dkweibo.support.sina.AuthHelper;
import com.dk.dkweibo.support.utils.LogUtil;
import com.dk.dkweibo.support.utils.ToastUtil;
import com.dk.dkweibo.ui.fragment.HomeTimelineFragment;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
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

    private final String TAG="AppActivity";
    private AccountHeader headerResult = null;
    private Drawer drawer = null;
    private Toolbar toolbar = null;
    private User user = null;
    private Oauth2AccessToken mAccessToken;
    private Context mContext;
    private RequestQueue mRequestQueue;

    private final int ID_HOME = 1;
    private final int ID_DISCOVER = 2;
    private final int ID_MESSAGE = 3;
    private final int ID_THEME = 4;
    private final int ID_SETTING = 5;
    private final int ID_EXIT = 6;

    private IProfile profileHead;

    private boolean isOauthAgain = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    private void init() {
        mContext = this;
        mRequestQueue = Volley.newRequestQueue(mContext);

        user = UserKeeper.readUserKeeper(mContext);
        LogUtil.v("user_head_url", user.getLargeUserHeadUrl());

        //try to update token
        if (AuthHelper.isNecessaryUpdate(mContext)) {
            requestOauth();
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_publish:
                        ToastUtil.shortToast("发表微博");
                        break;
                    case R.id.action_search:
                        ToastUtil.shortToast("搜索");
                        break;
                    default:
                        break;
                }
                return false;
            }
        });


        profileHead = new ProfileDrawerItem()
                .withName(user.getUserName())
                .withIcon(user.getLargeUserHeadUrl())
                .withEmail(user.getDescription());


        headerResult = new AccountHeaderBuilder()
                .withHeaderBackground(R.mipmap.bg_home)
                .withActivity(this)
                .addProfiles(profileHead)
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile iProfile, boolean b) {
                        requestOauth();
                        return false;
                    }
                })
                .build();

        headerResult.setActiveProfile(profileHead);

        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        new PrimaryDrawerItem().withIcon(R.mipmap.ic_home).withName(R.string.home_page).withIdentifier(ID_HOME).withSelectable(false),
                        new PrimaryDrawerItem().withIcon(R.mipmap.ic_discover).withName(R.string.discover).withIdentifier(ID_DISCOVER).withSelectable(false),
                        new PrimaryDrawerItem().withIcon(R.mipmap.ic_message).withName(R.string.message).withIdentifier(ID_MESSAGE).withSelectable(false).
                                withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.md_red_700)),
                        new PrimaryDrawerItem().withIcon(R.mipmap.ic_theme).withName(R.string.switch_theme).withIdentifier(ID_THEME).withSelectable(false),
                        new PrimaryDrawerItem().withIcon(R.mipmap.ic_setting).withName(R.string.setting).withIdentifier(ID_SETTING).withSelectable(false),
                        new PrimaryDrawerItem().withIcon(R.mipmap.ic_exit).withName(R.string.exit).withIdentifier(ID_EXIT).withSelectable(false)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem iDrawerItem) {
                        if (iDrawerItem != null) {
                            int id = iDrawerItem.getIdentifier();
                            switch (id) {
                                case ID_HOME:
                                    ToastUtil.shortToast("主页");
                                    break;
                                case ID_DISCOVER:
                                    ToastUtil.shortToast("发现");
                                    break;
                                case ID_MESSAGE:
                                    ToastUtil.shortToast("消息");
                                    break;
                                case ID_THEME:
                                    ToastUtil.shortToast("主题");
                                    break;
                                case ID_SETTING:
                                    ToastUtil.shortToast("设置");
                                    break;
                                case ID_EXIT:
                                    tryToExit();
                                    break;
                            }
                        }
                        return false;
                    }
                })
                .withShowDrawerOnFirstLaunch(true)
                .build();

        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        HomeTimelineFragment fragment = new HomeTimelineFragment();
        transaction.replace(R.id.fl_timeline_content,fragment);
        transaction.commit();

    }

    private void tryToExit() {
        new AlertDialogWrapper.Builder(mContext)
                .setTitle("是否确认退出？")
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (isOauthAgain) {
            isOauthAgain = false;
            this.recreate();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
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
            isOauthAgain = true;
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
        userAPI.doGetShow(mRequestQueue, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                LogUtil.v("response", response.toString());
                user=User.getUserFromJson(response);
                UserKeeper.writeUserKeeper(AppActivity.this, user);

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GlobalContext.getInstance().getRequestQueue().cancelAll(TAG);
    }

    @Override
    public void onBackPressed() {
        tryToExit();
    }
}

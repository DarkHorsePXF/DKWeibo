package com.dk.dkweibo.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.dk.dkweibo.R;
import com.dk.dkweibo.bean.UserBean;
import com.dk.dkweibo.dao.login.UserDao;
import com.dk.dkweibo.support.sina.AccessTokenKeeper;
import com.dk.dkweibo.support.utils.ToastUtil;
import com.dk.dkweibo.ui.adapter.UserListAdapter;
import com.dk.dkweibo.ui.common.BaseActivity;
import com.dk.dkweibo.support.sina.OAuth;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends BaseActivity {
    public static final int ADD_ACCOUNT_REQUEST_CODE = 0;

    private SsoHandler mSsoHandler;
    private AuthInfo mAuthInfo;
    private ListView lvUsers;
    private Button btnAddAccount;
    private Context mContext;
    private Oauth2AccessToken mAccessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();


    }

    private void init() {
        mContext = getApplicationContext();
        mAuthInfo = new AuthInfo(this, OAuth.APP_KEY, OAuth.REDIRECT_URL, OAuth.SCOPE);
        mSsoHandler = new SsoHandler(this, mAuthInfo);

        lvUsers = (ListView) findViewById(R.id.lv_login_user);
        btnAddAccount = (Button) findViewById(R.id.btn_add_account);

        UserListAdapter adapter = new UserListAdapter(getAccountUserList());
        lvUsers.setAdapter(adapter);

        btnAddAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertLoginChoice();
            }

            private void alertLoginChoice() {
                ArrayList<String> itemValueList = new ArrayList<>();

                itemValueList.add(getString(R.string.oauth_login));
                itemValueList.add(getString(R.string.sso_login));

                new AlertDialog.Builder(LoginActivity.this)
                        .setTitle(getString(R.string.login_way))
                        .setItems(itemValueList.toArray(new String[0]),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which){
                                            case 0:
                                                mSsoHandler.authorizeWeb(new AuthListener());
                                                break;
                                            case 1:
                                                mSsoHandler.authorizeClientSso(new AuthListener());
                                                break;
                                            default:
                                                break;
                                        }
                                    }
                                })
                        .show();
            }
        });
    }

    private List<UserBean> getAccountUserList() {
        UserDao userDao = new UserDao();
        List<UserBean> userList = userDao.getAllUser();
        if (userList==null||userList.size()==0){
            ToastUtil.shortToast(getString(R.string.no_login_user));
        }
        return  userList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class AuthListener implements WeiboAuthListener {

        @Override
        public void onComplete(Bundle values) {
            // 从 Bundle 中解析 Token
            mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            //从这里获取用户输入的 电话号码信息
            String  phoneNum =  mAccessToken.getPhoneNum();
            if (mAccessToken.isSessionValid()) {
                // 显示 Token
                updateTokenView(false);

                // 保存 Token 到 SharedPreferences
                AccessTokenKeeper.writeAccessToken(LoginActivity.this, mAccessToken);
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
    }

    /**
     * 显示当前 Token 信息。
     *
     * @param hasExisted 配置文件中是否已存在 token 信息并且合法
     */
    private void updateTokenView(boolean hasExisted) {
        String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(
                new java.util.Date(mAccessToken.getExpiresTime()));
        String strToken = mAccessToken.getToken().toString();
        Log.v("token",strToken);

        if (hasExisted) {
            Log.v("weibo","账户已存在");
        }
    }
}

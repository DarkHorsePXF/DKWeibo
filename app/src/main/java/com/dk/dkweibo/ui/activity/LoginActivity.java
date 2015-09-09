package com.dk.dkweibo.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.dk.dkweibo.R;
import com.dk.dkweibo.bean.User;
import com.dk.dkweibo.dao.login.UserDao;
import com.dk.dkweibo.support.sharepreference.AccessTokenKeeper;
import com.dk.dkweibo.support.utils.ToastUtil;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.util.List;

public class LoginActivity extends BaseActivity {
    public static final int ADD_ACCOUNT_REQUEST_CODE = 0;

    private ListView lvUsers;
    private Button btnAddAccount;
    private Context mContext;
    private Oauth2AccessToken mAccessToken;
    private RequestQueue mRequestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();


    }

    private void init() {
        mContext = getApplicationContext();



        mRequestQueue = Volley.newRequestQueue(mContext);
        mAccessToken = AccessTokenKeeper.readAccessToken(mContext);

        lvUsers = (ListView) findViewById(R.id.lv_login_user);
        btnAddAccount = (Button) findViewById(R.id.btn_add_account);


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private List<User> getAccountUserList() {
        UserDao userDao = new UserDao();
        List<User> userList = userDao.getAllUser();
        if (userList == null || userList.size() == 0) {
            ToastUtil.shortToast(getString(R.string.no_login_user));
        }

        return userList;
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





}

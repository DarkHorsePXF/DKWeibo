package com.dk.dkweibo.dao.login;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.dk.dkweibo.bean.UserBean;
import com.dk.dkweibo.db.DBHelper;
import com.dk.dkweibo.db.DBInfo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by feng on 2015/8/29.
 */
public class UserDao {
    private DBHelper dbHelper = null;
    private SQLiteDatabase db = null;

    /**
     *User表的查询列参数
     */
    private String[] queryColumns = {
            DBInfo.Table._ID, DBInfo.Table.USER_ID, DBInfo.Table.USER_NAME, DBInfo.Table.TOKEN,
            DBInfo.Table.TOKEN_SECRET, DBInfo.Table.DESCRIPTION, DBInfo.Table.USER_HEAD
    };

    public UserDao(){
        dbHelper = new DBHelper();

    }

    public long insertUser(UserBean user){
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBInfo.Table.USER_ID,user.getUserId());
        values.put(DBInfo.Table.USER_NAME,user.getUserName());
        values.put(DBInfo.Table.TOKEN,user.getToken());
        values.put(DBInfo.Table.TOKEN_SECRET,user.getTokenSecret());
        values.put(DBInfo.Table.DESCRIPTION,user.getDescription());

        ByteArrayOutputStream os = null;
        long rowId = 0;
        try {
            //格式化头像
            os = new ByteArrayOutputStream();
            BitmapDrawable newHead = (BitmapDrawable) user.getUserHead();
            newHead.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, os);

            values.put(DBInfo.Table.USER_HEAD, os.toByteArray());
            rowId = db.insert(DBInfo.Table.USER_TABLE, DBInfo.Table.USER_NAME, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            db.close();
        }




        return rowId;
    }

    public boolean updateUser(UserBean user){
        db = dbHelper.getWritableDatabase();
        return true;
    }

    public boolean deleteUser(UserBean user){
        db = dbHelper.getWritableDatabase();
        return true;
    }

    public UserBean getUserByUserId(int userId){
        db = dbHelper.getReadableDatabase();

        return null;
    }

    public List<UserBean> getAllUser(){
        db = dbHelper.getReadableDatabase();
        List<UserBean> userList = null;
        Cursor cursor = db.query(DBInfo.Table.USER_TABLE, queryColumns, null, null, null, null, null);

        if (cursor!=null && cursor.getCount()>0){
            userList = new ArrayList<>();
            while (cursor.moveToNext()){
                UserBean user = new UserBean();

                user.setUserId(cursor.getString(cursor.getColumnIndex(DBInfo.Table.USER_ID)));
                user.setUserName(cursor.getString(cursor.getColumnIndex(DBInfo.Table.USER_NAME)));
                user.setToken(cursor.getString(cursor.getColumnIndex(DBInfo.Table.TOKEN)));
                user.setTokenSecret(cursor.getString(cursor.getColumnIndex(DBInfo.Table.TOKEN_SECRET)));
                user.setDescription(cursor.getString(cursor.getColumnIndex(DBInfo.Table.DESCRIPTION)));

                //头像格式转换
                byte[] byteHead = cursor.getBlob(cursor.getColumnIndex(DBInfo.Table.USER_HEAD));
                if (byteHead!=null){
                    ByteArrayInputStream is = new ByteArrayInputStream(byteHead);
                    Drawable userHead = Drawable.createFromStream(is, "image");
                    user.setUserHead(userHead);
                }

                userList.add(user);
            }
        }
        return  userList;
    }





}

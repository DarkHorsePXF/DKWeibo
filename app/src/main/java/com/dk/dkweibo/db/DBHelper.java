package com.dk.dkweibo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dk.dkweibo.support.GlobalContext;

/**
 * Created by feng on 2015/8/29.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final Context CONTEXT = GlobalContext.getInstance();
    private static final String  DATABASE_NAME = "weibo.db";
    private static final int VERSION = 1;

    public DBHelper() {
        super(CONTEXT, DATABASE_NAME, null, VERSION);
    }

    public DBHelper(int version) {
        super(CONTEXT, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBInfo.Table.CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DBInfo.Table.DROP_USER_TABLE);
        db.execSQL(DBInfo.Table.CREATE_USER_TABLE);
    }
}

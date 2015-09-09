package com.dk.dkweibo.db;


/**
 * Created by feng on 2015/8/29.
 */
public final class DBInfo {
    public final static class DB{

        public static final String DB_NAME = "dk_weibo.db";

    }

    public final static class Table{

        /*
        用户资料表
         */
        public static final String USER_TABLE = "user_info";

        public static final String _ID = "_id";
        public static final String USER_ID = "user_id";
        public static final String USER_NAME = "user_name";
        public static final String TOKEN = "token";
        public static final String TOKEN_SECRET = "token_secret";
        public static final String DESCRIPTION = "description";
        public static final String USER_HEAD = "user_head";

        /*
        创建user_info表
         */
        public static final String CREATE_USER_TABLE = "create table if not exists " +
                USER_TABLE +
                "(" +
                _ID + " integer primary key autoincrement, " +
                USER_ID + " text, " +
                USER_NAME + " text, " +
                USER_HEAD + " text, " +
                TOKEN + " text, " +
                TOKEN_SECRET + " text, " +
                DESCRIPTION + " text " +
                ");";
        /*
        删除user_info表
         */
        public static final String DROP_USER_TABLE = "drop table " + USER_TABLE;




    }

}


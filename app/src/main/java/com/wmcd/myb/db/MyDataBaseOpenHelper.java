package com.wmcd.myb.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/7/18.
 */

public class MyDataBaseOpenHelper extends SQLiteOpenHelper {
    public MyDataBaseOpenHelper(Context context) {
        super(context,context.getExternalFilesDir(null).getPath()+"/Urls.db",null,1);
    }

    /**
     * 当数据库第一次被创建时调用的方法，这个方法只会执行一次
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //初始化数据库
        db.execSQL("create table uuids (_id integer primary key autoincrement, url varchar(30), uuid varchar(20))");
    }

    /**
     * 当这个数据库要被更新时调用的方法
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

}

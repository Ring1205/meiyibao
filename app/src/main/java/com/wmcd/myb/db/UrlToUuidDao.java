package com.wmcd.myb.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Administrator on 2017/7/18.
 */

public class UrlToUuidDao {
    public static MyDataBaseOpenHelper helper ;
    public UrlToUuidDao(Context context){
        helper = new MyDataBaseOpenHelper(context);
    }

    public void add(String url,String uuid){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("insert into uuids (url,uuid) values (?,?)",new Object[]{url,uuid});
        db.close();
    }
    public void delete(String uuid){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from uuids where uuid=?",new Object[]{uuid});
        db.close();
    }
    public void update(String url,String newuuid){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("update uuids set uuid =? where url =?",new Object[]{newuuid,url});
        db.close();
    }
    public String find(String url){
        String uuid = null;
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select uuid from uuids where url=?",new String[]{url});
        boolean result = cursor.moveToNext();
        if (result){
            uuid = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return uuid;
    }
}

package com.mediwit.app.db;

import com.mediwit.app.constant.TableConstant;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "yihui.db"; //定义数据库名称
    private static final int DATABASE_VERSION = 7;//定义数据库版本
    private DatabaseHelper(Context context, String name, CursorFactory factory,
            int version) {
        super(context, name, factory, version);
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        创建系统使用数据库p

        try {
            //id,title,content,type,reciveTime,fromId,fromName,num
            String msgsql = "CREATE TABLE IF NOT EXISTS " + TableConstant.TABLE_MESSAGE
                    + " (id INTEGER PRIMARY KEY, title VARCHAR, content VARCHAR,type VARCHAR,reciveTime DATETIME DEFAULT CURRENT_TIMESTAMP,fromId VARCHAR ,fromName VARCHAR,num INTEGER DEFAULT 1,topTime LONG DEFAULT 1 );";
            db.execSQL(msgsql);

            String usersql = "CREATE TABLE IF NOT EXISTS " + TableConstant.TABLE_USER + " (ID VARCHAR, ID_CN VARCHAR, NAME VARCHAR,NK_NAME VARCHAR ,ICON VARCHAR,HEIGHT INTEGER, WEIGHT INTEGER ,HOME_PHONE VARCHAR,MOBILE_PHONE VARCHAR,PWD VARCHAR,ADDRESS VARCHAR ,GENDER VARCHAR,BIRTHDAY DATETIME,BIRTHPLACE VARCHAR,CAREER VARCHAR,MARRIAGE VARCHAR,CREATE_TIME DATETIME,UPDATE_TIME DATETIME);";
            db.execSQL(usersql);

            String parSql = "CREATE TABLE IF NOT EXISTS " + TableConstant.TABLE_PARS + " (CODE VARCHAR, VALUE VARCHAR);";
            db.execSQL(parSql);
        } catch (SQLException e) {
            Log.e("DatabaseHelper", e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        try {
            //id,title,content,type,reciveTime,fromId,fromName,num
            String msgsql = "CREATE TABLE IF NOT EXISTS " + TableConstant.TABLE_MESSAGE
                    + " (id INTEGER PRIMARY KEY, title VARCHAR, content VARCHAR,type VARCHAR,reciveTime DATETIME DEFAULT CURRENT_TIMESTAMP,fromId VARCHAR ,fromName VARCHAR,num INTEGER DEFAULT 1,topTime LONG DEFAULT 1);";
            db.execSQL(msgsql);
            String usersql = "CREATE TABLE IF NOT EXISTS " + TableConstant.TABLE_USER + " (ID VARCHAR, ID_CN VARCHAR, NAME VARCHAR,NK_NAME VARCHAR ,ICON VARCHAR,HEIGHT INTEGER, WEIGHT INTEGER ,HOME_PHONE VARCHAR,MOBILE_PHONE VARCHAR,PWD VARCHAR,ADDRESS VARCHAR ,GENDER VARCHAR,BIRTHDAY DATETIME,BIRTHPLACE VARCHAR,CAREER VARCHAR,MARRIAGE VARCHAR,CREATE_TIME DATETIME,UPDATE_TIME DATETIME);";
            db.execSQL(usersql);
            String parSql = "CREATE TABLE IF NOT EXISTS " + TableConstant.TABLE_PARS + " (CODE VARCHAR, VALUE VARCHAR);";
            db.execSQL(parSql);
        } catch (SQLException e) {
            Log.e("DatabaseHelper", e.getMessage());
            e.printStackTrace();
        }
        Log.e("db", "数据库更新了."+db.getVersion());
    }

    private static DatabaseHelper _init=null;

    public static DatabaseHelper getInst(Context context){
        if(null==_init){
            _init=new DatabaseHelper(context);
        }
        return _init;

    }

}

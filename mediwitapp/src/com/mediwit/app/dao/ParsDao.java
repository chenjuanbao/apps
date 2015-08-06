package com.mediwit.app.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mediwit.app.constant.TableConstant;
import com.mediwit.app.db.DatabaseHelper;

public class ParsDao {
    DatabaseHelper dataBaseHelper=null;
    public ParsDao(Context context){
        dataBaseHelper=DatabaseHelper.getInst(context);
    }
    public String getPars(String code) {
        String value=null;
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        String sql="select VALUE from "+TableConstant.TABLE_PARS+" where CODE=?";
        Cursor cursor=db.rawQuery(sql, new String[]{code});
        int rtNum=cursor.getCount();
        if(rtNum>0){
            cursor.moveToNext();
            value=cursor.getString(0);
        }
        return value;
    }

    public String updatePars(String code, String value) {
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        String usql="update "+TableConstant.TABLE_PARS+" set VALUE=?  where CODE=?";
        db.execSQL(usql, new Object[]{value,code});
//        db.update(TableConstant.TABLE_PARS, "VALUE=?", "CODE=?", new String[]{value,code});
        return null;
    }
    public String insertPars(String code, String value) {
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        String isql="insert into "+TableConstant.TABLE_PARS+"(CODE,VALUE) VALUES(?,?)";
        db.execSQL(isql, new Object[]{code,value});
//        db.update(TableConstant.TABLE_PARS, "VALUE=?", "CODE=?", new String[]{value,code});
        return null;
    }
    public String getSelfId() {

        return this.getPars(TableConstant.PAR_SELF_ID);
    }

    public void insertSelfId(String uuId) {
        this.insertPars(TableConstant.PAR_SELF_ID, uuId);

    }

}

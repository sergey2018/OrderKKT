package com.sergey.root.orderkkt.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB_Helper extends SQLiteOpenHelper {
    public DB_Helper(Context context) {
        super(context, "mail.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ dbShema.GOODS.NAME +" ("+
                "_id integer primary key autoincrement, "+
                dbShema.GOODS.Cols.NAME + " text, "+
                dbShema.GOODS.Cols.PRICE + " real, "+
                dbShema.GOODS.Cols.CODE + " text, "+
                dbShema.GOODS.Cols.QUANT + " real, "+
                dbShema.GOODS.Cols.TAX + " integer)");
        db.execSQL("create table " + dbShema.ORDER.NAME + " ("+
            "id integer primary key autoincrement, "+
                dbShema.ORDER.Cols.ACCT + " text, "+
                dbShema.ORDER.Cols.DAY + " integer, "+
                dbShema.ORDER.Cols.DATE + " long, "+
                dbShema.ORDER.Cols.GOODS + " integer, "+
                dbShema.ORDER.Cols.ADRESS + " text, " +
                dbShema.ORDER.Cols.PHONE + " text, "+
                dbShema.ORDER.Cols.CONTACT + " text,"+
                dbShema.ORDER.Cols.NOTE + " text,"+
                dbShema.ORDER.Cols.STATUS + " integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

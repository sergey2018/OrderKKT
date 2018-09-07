package com.sergey.root.orderkkt.Model;

import android.content.ContentValues;

import com.sergey.root.orderkkt.DataBase.dbShema;

public class Goods {
    private int mId;
    private String mName;
    private double mPrice;
    private double mQuantity;
    private int Tax;
    private String mCode;
    private boolean mFlags = true;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double price) {
        mPrice = price;
    }

    public double getQuantity() {
        return mQuantity;
    }

    public void setQuantity(double quantity) {
        mQuantity = quantity;
    }

    public int getTax() {
        return Tax;
    }

    public void setTax(int tax) {
        Tax = tax;
    }

    public boolean isFlags() {
        return mFlags;
    }

    public void setFlags(boolean flags) {
        mFlags = flags;
    }

    public double summ(){
        return mPrice * mQuantity;
    }

    public void setFlags(int f){
        switch (f) {
            case 0:
                mFlags = false;
                break;
            case 1: mFlags = true;
            break;
        }

    }

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        mCode = code;
    }

    public ContentValues getContentValue(){
        ContentValues values = new ContentValues();
        values.put(dbShema.GOODS.Cols.NAME,mName);
        values.put(dbShema.GOODS.Cols.PRICE,mPrice);
        values.put(dbShema.GOODS.Cols.CODE,mCode);
        values.put(dbShema.GOODS.Cols.QUANT,mQuantity);
        values.put(dbShema.GOODS.Cols.TAX,Tax);
        return values;
    }
}

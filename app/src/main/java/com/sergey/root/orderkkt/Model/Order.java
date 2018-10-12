package com.sergey.root.orderkkt.Model;

import android.content.ContentValues;
import android.text.format.DateFormat;

import com.sergey.root.orderkkt.DataBase.dbShema;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class Order {
    private UUID mAcct;
    private String mContact;

    private String Adress;
    private int mGoods;
    private String mName;
    private double mPrice;
    private Date mDate;
    private int mStatus;
    private int count;
    private String mPhone;
    private String mNote;
    private double mQuantity;
    private String mCode;

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    private String mType="";

    public String getAdress() {
        return Adress;
    }

    public void setAdress(String adress) {
        Adress = adress;
    }

    public int getGoods() {
        return mGoods;
    }

    public void setGoods(int goods) {
        mGoods = goods;
    }

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int status) {
        mStatus = status;
    }

    public UUID getAcct() {
        return mAcct;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getContact() {
        return mContact;
    }

    public void setContact(String contact) {
        mContact = contact;
    }

    public void setAcct(UUID acct) {
        mAcct = acct;
    }

    public String getNote() {
        return mNote;
    }

    public void setNote(String note) {
        mNote = note;
    }

    public int getCount() {
        return count;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getDate() {
        return mDate;
    }

    public String getDateText(){
        SimpleDateFormat df = new SimpleDateFormat("dd.MMM yyyy", Locale.getDefault());
        return df.format(mDate);
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
    public double Summ(){
        return mQuantity * mPrice;
    }

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        mCode = code;
    }

    public ContentValues getContenValue(int day){
        ContentValues values = new ContentValues();
        values.put(dbShema.ORDER.Cols.ACCT,mAcct.toString());
        values.put(dbShema.ORDER.Cols.GOODS,mGoods);
        values.put(dbShema.ORDER.Cols.DAY,day);
        values.put(dbShema.ORDER.Cols.DATE,mDate.getTime());
        values.put(dbShema.ORDER.Cols.PHONE,mPhone);
        values.put(dbShema.ORDER.Cols.ADRESS,Adress);
        values.put(dbShema.ORDER.Cols.STATUS,mStatus);
        values.put(dbShema.ORDER.Cols.CONTACT,mContact);
        values.put(dbShema.ORDER.Cols.NOTE,mNote);
        return values;
    }
}

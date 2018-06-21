package com.sergey.root.orderkkt.Model;

import android.text.format.DateFormat;

import java.util.Date;
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
        DateFormat format = new DateFormat();
        return (String) format.format("dd.MM.yyyy",mDate);
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
}

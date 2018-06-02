package com.sergey.root.orderkkt.Model;

import java.util.UUID;

public class Order {
    private UUID mAcct;
    private String mContact;
    private int mNumber;
    private String Adress;
    private int mGoods;

    private int mStatus;
    private String mPhone;
    private String mNote;

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


    public int getNumber() {
        return mNumber;
    }

    public void setNumber(int number) {
        mNumber = number;
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
}

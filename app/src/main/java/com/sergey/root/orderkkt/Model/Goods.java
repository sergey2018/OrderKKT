package com.sergey.root.orderkkt.Model;

public class Goods {
    private int mId;
    private String mName;
    private double mPrice;
    private double mQuantity;
    private int Tax;
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
}

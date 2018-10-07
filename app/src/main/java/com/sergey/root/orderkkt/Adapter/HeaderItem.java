package com.sergey.root.orderkkt.Adapter;

public class HeaderItem extends ListItem {

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    private String mDate;
    @Override
    public int getType() {
        return TYPE_HEADER;
    }
}
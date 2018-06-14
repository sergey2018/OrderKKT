package com.sergey.root.orderkkt.KKT;

import android.content.Context;

import com.sergey.root.orderkkt.Model.Goods;

import java.util.ArrayList;

/**
 * Created by root on 30.11.17.
 */

public interface KKT {
    public void Sale(ArrayList<Goods> sale, String type,double summ);
    public void ReturnSale(ArrayList<Goods> sales, String type);
    public void XReport();
    public void ZReport();
    public void close();
    public String getDevises();
    public void Email(String Email);
    public void init(Context context);
    public void connect();
    public boolean getError();
    public String getErrorDescription();
    public void Cash(long sum);
    public void OpenDay();
    public void CashDrawer();
    public void CashOut(long sum);
}

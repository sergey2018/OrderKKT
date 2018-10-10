package com.sergey.root.orderkkt.KKT;

import android.content.Context;

import com.sergey.root.orderkkt.Model.Goods;
import com.sergey.root.orderkkt.Preferes;

import java.util.ArrayList;

import ru.atol.drivers10.fptr.Fptr;
import ru.atol.drivers10.fptr.IFptr;

public class AtolPrintKKT implements KKT {
    private IFptr printer;
    private String mCassir;
    public AtolPrintKKT(Context context) {
        printer = new Fptr(context);
    }

    @Override
    public void Sale(ArrayList<Goods> sale, String type,double summ) {
        connect();
        printer.setParam(1021, mCassir);
        printer.setParam(1203, "123456789047");
        printer.operatorLogin();

        printer.setParam(IFptr.LIBFPTR_PARAM_RECEIPT_TYPE, IFptr.LIBFPTR_RT_SELL);
        printer.openReceipt();
        double sum = 0;
        for (Goods goods: sale){
            printer.setParam(IFptr.LIBFPTR_PARAM_COMMODITY_NAME, goods.getName());
            printer.setParam(IFptr.LIBFPTR_PARAM_PRICE, goods.getPrice());
            printer.setParam(IFptr.LIBFPTR_PARAM_QUANTITY, goods.getQuantity());
            printer.setParam(IFptr.LIBFPTR_PARAM_TAX_TYPE, IFptr.LIBFPTR_TAX_NO);
            printer.registration();
            sum = sum + (goods.getQuantity() * goods.getPrice());
        }
        printer.setParam(IFptr.LIBFPTR_PARAM_SUM, sum);
        printer.receiptTotal();
        printer.closeReceipt();
        close();
    }

    @Override
    public void ReturnSale(ArrayList<Goods> sales, String type) {

    }

    @Override
    public void XReport() {
        connect();
        printer.setParam(IFptr.LIBFPTR_PARAM_REPORT_TYPE, IFptr.LIBFPTR_RT_X);
        printer.report();
        close();

    }

    @Override
    public void ZReport() {
        connect();
        printer.setParam(1021, mCassir);
        printer.setParam(1203, "123456789047");
        printer.operatorLogin();

        printer.setParam(IFptr.LIBFPTR_PARAM_REPORT_TYPE, IFptr.LIBFPTR_RT_CLOSE_SHIFT);
        printer.report();

        printer.checkDocumentClosed();
        close();
    }

    @Override
    public void close() {
        printer.close();
    }

    @Override
    public void initBluetooth(Context contex) {

    }

    @Override
    public String getDevises() {
        return null;
    }

    @Override
    public void Email(String Email) {

    }

    @Override
    public void init(Context context) {
        String set = Preferes.getAtolSettings(context);
        mCassir = Preferes.getCuryer(context);
        printer.setSettings(set);

    }

    @Override
    public void connect() {
        printer.open();
    }

    @Override
    public boolean getError() {
        return false;
    }

    @Override
    public String getErrorDescription() {
        return null;
    }

    @Override
    public void Cash(long sum) {

    }

    @Override
    public void OpenDay() {

    }

    @Override
    public void CashDrawer() {

    }

    @Override
    public void CashOut(long sum) {

    }
}

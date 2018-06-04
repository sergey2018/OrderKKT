package com.sergey.root.orderkkt.KKT;

import android.content.Context;
import android.util.Log;


import com.sergey.root.orderkkt.Model.Goods;
import com.sergey.root.orderkkt.Preferes;
import com.shtrih.fiscalprinter.ShtrihFiscalPrinter;

import java.math.BigDecimal;
import java.util.ArrayList;

import jpos.FiscalPrinter;
import jpos.FiscalPrinterConst;
import jpos.JposConst;
import jpos.JposException;

;


/**
 * Created by root on 30.11.17.
 */

public class ShtrihPrintKKT implements KKT {
    private ShtrihFiscalPrinter mPrinter = null;
    private String Description;
    private String Device;
    private boolean mError = false;

    public ShtrihPrintKKT() {
        mPrinter = new ShtrihFiscalPrinter(new FiscalPrinter());
    }

    @Override
    public void Sale(final ArrayList<Goods> sale, String type) {
        if (mPrinter == null) {
            return;
        }
        mError = false;
        try {
             connect();
            mPrinter.resetPrinter();
            long summ = 0;

            mPrinter.setFiscalReceiptType(jpos.FiscalPrinterConst.FPTR_RT_SALES);
            mPrinter.beginFiscalReceipt(false);
            for (int i = 0; i < sale.size(); i++) {
                String name = sale.get(i).getName();
                long price = toLong(new BigDecimal(sale.get(i).getPrice()));
                int tax = sale.get(i).getTax();
                int qtty = (int)new BigDecimal(sale.get(i).getQuantity()).multiply(new BigDecimal(1000)).intValueExact();
                mPrinter.printRecItem(name, 0, qtty, tax, price, "2wer");
                summ = summ + (price*qtty);
            }
            mPrinter.printRecTotal(summ, summ, type);
            //Email("rabot1993@gmail.com");
            mPrinter.endFiscalReceipt(false);

            close();
            //KKTLABS.getInstance(mContext).salesClear();

        } catch (JposException e) {
            mError = true;
            Description = e.toString();
            e.printStackTrace();

        }
    }

    @Override
    public void ReturnSale(ArrayList<Goods> sales, String type) {
        if (mPrinter == null) {
            return;
        }
        try {
            mError = false;
            connect();

            mPrinter.resetPrinter();
            long summ = 0;
            mPrinter.setFiscalReceiptType(FiscalPrinterConst.FPTR_RT_REFUND);
            mPrinter.beginFiscalReceipt(false);

            for (int i = 0; i < sales.size(); i++) {
                String name = sales.get(i).getName();
                long price =  toLong(new BigDecimal(sales.get(i).getPrice()));
                int qtty = (int)new BigDecimal(sales.get(i).getQuantity()).multiply(new BigDecimal(1000)).intValueExact();
                mPrinter.printRecItemRefund(name, 0, qtty, 0, price, "");
                summ = summ + (price * qtty);
            }

            mPrinter.printRecTotal(summ, summ, type);
            mPrinter.endFiscalReceipt(false);
            close();
            //KKTLABS.getInstance(mContext).salesClear();

        } catch (JposException e) {
            mError = true;
            e.printStackTrace();
        }
    }

    @Override
    public void XReport() {
        try {
            mError = false;
            connect();
            Log.d("TAG",mPrinter.readDeviceMetrics().getDeviceName());
            mPrinter.printXReport();
            close();
        } catch (Exception e) {
            mError = true;
            e.printStackTrace();
        }

    }

    @Override
    public void ZReport() {
        try {
            mError = false;
             connect();
            mPrinter.printZReport();
            close();
        } catch (Exception e) {
            mError = true;
            e.printStackTrace();
        }

    }

    public void connect() {
try {
    if (mPrinter.getState() != JposConst.JPOS_S_CLOSED) {
        mPrinter.close();
    }
    mPrinter.open("ShtrihFptr");
    mPrinter.claim(3000);
    mPrinter.setDeviceEnabled(true);
    String [] line = new String[1];
    mPrinter.getData(FiscalPrinterConst.FPTR_GD_PRINTER_ID,null,line);
     Device = mPrinter.readDeviceMetrics().getDeviceName() + " "+line[0];
} catch(
    Exception e)

    {
        e.printStackTrace();
    }


    }
    @Override
    public String getDevises(){
       return Device;
    }
    @Override
    public void close() {
        try {
            mPrinter.close();
        } catch (JposException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void Email(String Email) {
        try {
           // mPrinter.fsWriteCustomerEmail(Email);
            mPrinter.directIO(0x39,null,Email);
        } catch (JposException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void connect(Context context) {
        try {
            String portName = Preferes.getIP_adres(context)+":"+Preferes.getPort(context);
            //SysUtils.setFilesPath(context.getFilesDir().getAbsolutePath());
            JposConfig.configure("ShtrihFptr", portName, context,"2","0");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
            connect();
    }

    @Override
    public boolean getError() {
        return mError;
    }

    @Override
    public String getErrorDescription() {
        return Description;
    }

    @Override
    public void Cash(long sum) {
        try {
            connect();
            mError = false;
            mPrinter.resetPrinter();
            mPrinter.setFiscalReceiptType(FiscalPrinterConst.FPTR_RT_CASH_IN);
            mPrinter.beginFiscalReceipt(false);
            mPrinter.printRecCash(sum);
            mPrinter.printRecTotal(sum,sum,"0");
            mPrinter.endFiscalReceipt(false);
            close();

        } catch (JposException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OpenDay() {
        try {
            connect();
            mPrinter.openFiscalDay();
            close();
        } catch (JposException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void CashDrawer() {
        try {
            connect();
            mPrinter.openCashDrawer(0);
            close();
        } catch (JposException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void CashOut(long sum) {
        try {
            connect();
            mPrinter.resetPrinter();
            mPrinter.setFiscalReceiptType(FiscalPrinterConst.FPTR_RT_CASH_OUT);
            mPrinter.beginFiscalReceipt(false);
            mPrinter.printRecCash(sum);
            mPrinter.printRecTotal(sum,sum,"0");
            mPrinter.endFiscalReceipt(false);
            close();
        } catch (JposException e) {
            e.printStackTrace();
        }

    }
    public  long toLong(BigDecimal value) {
        return value.multiply(new BigDecimal(100)).intValueExact();
    }

}

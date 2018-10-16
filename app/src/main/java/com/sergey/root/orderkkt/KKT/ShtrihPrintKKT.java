package com.sergey.root.orderkkt.KKT;

import android.content.Context;
import android.util.Log;


import com.sergey.root.orderkkt.Model.Goods;
import com.sergey.root.orderkkt.Preferes;
import com.shtrih.fiscalprinter.ShtrihFiscalPrinter;
import com.shtrih.jpos.fiscalprinter.SmFptrConst;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    private String Casir;
    private String mEmail="";
    private String mPhone="";
    private boolean mError = false;

    public ShtrihPrintKKT() {
        mPrinter = new ShtrihFiscalPrinter(new FiscalPrinter());
    }

    @Override
    public void Sale(final ArrayList<Goods> sale, String type,double summ) {
        if (mPrinter == null) {
            return;
        }
        mError = false;
        try {
             connect();
            mPrinter.resetPrinter();
            long sum = 0;
            mPrinter.writeCashierName(Casir);
            mPrinter.setFiscalReceiptType(jpos.FiscalPrinterConst.FPTR_RT_SALES);

            mPrinter.setParameter(SmFptrConst.SMFPTR_DIO_PARAM_TAX_SYSTEM,8);
            mPrinter.beginFiscalReceipt(false);
            for (int i = 0; i < sale.size(); i++) {
              if(sale.get(i).isFlags()){
                  String name = sale.get(i).getName();
                  long price = toLong(new BigDecimal(sale.get(i).getPrice()));
                  int tax = sale.get(i).getTax();
                  int qtty = (int)new BigDecimal(sale.get(i).getQuantity()).multiply(new BigDecimal(1000)).intValueExact();
                  mPrinter.printRecItem(name, 0, qtty, tax, price, "2wer");
                  sum = sum + (price*qtty);
              }
            }
            mPrinter.printRecTotal(sum,sum , type);
           if(!mEmail.equals("")){
               setmEmail();
           }
            mPrinter.endFiscalReceipt(false);

            close();
            mEmail = "";
            //KKTLABS.getInstance(mContext).salesClear();

        } catch (JposException e) {
            mError = true;
            Description = e.toString();
            close();
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
            mPrinter.writeCashierName(Casir);
            long summ = 0;
            mPrinter.setFiscalReceiptType(FiscalPrinterConst.FPTR_RT_REFUND);
            mPrinter.beginFiscalReceipt(false);

            for (int i = 0; i < sales.size(); i++) {
                String name = sales.get(i).getName();
                long price =  toLong(new BigDecimal(sales.get(i).getPrice()));
                int qtty = (int)new BigDecimal(sales.get(i).getQuantity()).multiply(new BigDecimal(100)).intValueExact();
                mPrinter.printRecItemRefund(name, 0, qtty, 0, price, "");
                summ = summ + (price * qtty);
            }
            //Email("rabot1993@gmail.com");
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
    public void initBluetooth(Context contex) {

    }

    @Override
    public void Email(String Email) {
        mEmail = Email;
    }
    private void setmEmail(){
        try {
            // mPrinter.fsWriteCustomerEmail(Email);
            mPrinter.directIO(0x39,null,mEmail);
        } catch (JposException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(Context context) {
        try {
            Map<String, String> maps = new HashMap<>();
            if(Preferes.getPortType(context).equals("2")) {
                String portName = Preferes.getIP_adres(context) + ":" + Preferes.getPort(context);
                maps.put("portName", portName);
                maps.put("portType", Preferes.getPortType(context));
                maps.put("protocolType","0");
                maps.put("byteTimeout", "1000");
            }
            else {
                String portName = Preferes.getIP_adres(context);
                maps.put("portName", portName);
                maps.put("portType", "3");
                maps.put("portClass", Preferes.getPortClass(context));
                maps.put("protocolType","0");
                maps.put("byteTimeout", "1000");
            }
            Casir = Preferes.getCuryer(context);
            //SysUtils.setFilesPath(context.getFilesDir().getAbsolutePath());
            JposConfig.configure("ShtrihFptr", context,maps);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
            //connect();
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

package com.sergey.root.orderkkt.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.text.format.DateFormat;

import com.sergey.root.orderkkt.CursorWrappes.ExelOrderWrapper;
import com.sergey.root.orderkkt.CursorWrappes.GoodsWrapper;
import com.sergey.root.orderkkt.CursorWrappes.OrderWrappes;
import com.sergey.root.orderkkt.DataBase.dbShema.GOODS;
import com.sergey.root.orderkkt.DataBase.dbShema.ORDER;
import com.sergey.root.orderkkt.KKT.AtolPrintKKT;
import com.sergey.root.orderkkt.KKT.KKT;
import com.sergey.root.orderkkt.KKT.ShtrihPrintKKT;
import com.sergey.root.orderkkt.Model.Goods;
import com.sergey.root.orderkkt.Model.Order;
import com.sergey.root.orderkkt.Preferes;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class OrderLab {
    private static  OrderLab ourInstance;
    private SQLiteDatabase mHelper;
    private Context mContext;
    private int day;
    private boolean isON = false;
    private KKT mKKT;

    public static OrderLab getInstance(Context context) {
        if(ourInstance == null){
            ourInstance = new OrderLab(context);
        }
        return ourInstance;
    }
    public String getDescription(){
        return mKKT.getErrorDescription();
    }

    private OrderLab(Context context) {
        mHelper = new DB_Helper(context.getApplicationContext()).getWritableDatabase();
        mContext = context;
        int kkt = Preferes.getSelect(context);
        day = Preferes.getDay(context);
        switch (kkt){
            case 1: mKKT = new ShtrihPrintKKT();
            mKKT.init(context);
            break;
            case 2: mKKT = new AtolPrintKKT();
            default:isON = true;
            break;
        }
    }

    public ArrayList<Order> getOrder(int status){
        ArrayList<Order> list = new ArrayList<>();
        String table = ORDER.NAME;
        String [] colums = new String[]{ORDER.Cols.ACCT,ORDER.Cols.PHONE,ORDER.Cols.NOTE,ORDER.Cols.CONTACT,ORDER.Cols.ADRESS,"SUM("+ORDER.Cols.STATUS+") AS "+ORDER.Cols.STATUS,"COUNT("+ORDER.Cols.STATUS+") AS cour",ORDER.Cols.DATE};
        Cursor cursor = getQuery(true,table,colums,ORDER.Cols.STATUS+" = ?",new String[]{String.valueOf(status)},ORDER.Cols.ACCT,ORDER.Cols.DATE+" DESC");
        OrderWrappes value = new OrderWrappes(cursor);
        try{
            if(value.getCount() == 0){
                return list;
            };
            value.moveToFirst();
            while (!value.isAfterLast()){
              Order order = value.getOrder();
              list.add(order);
              value.moveToNext();
            }
        }
        finally {
            value.close();
        }
        /*for(int i = 0; i<10; i++){
            Order order = new Order();
            order.setNumber(i+1);
            order.setAdress("Сиреневый 4");
            order.setPhone("+73424126005");
            order.setContact("Сергей");
            order.setStatus(0);
            list.add(order);
        }*/
        return list;
    }
    public ArrayList<Goods> getGoods(UUID id){
        ArrayList<Goods> list = new ArrayList<>();
        String table = ORDER.NAME + " inner join " + GOODS.NAME + " on " + ORDER.Cols.GOODS + " = "+GOODS.NAME+"._id";
        Cursor cursor = getQuery(false,table,null,ORDER.Cols.ACCT + " = ?",new String[]{id.toString()},null,null);
        GoodsWrapper value = new GoodsWrapper(cursor);
        try{
            if(value.getCount() == 0){
                return list;
            }
            value.moveToFirst();
            while (!value.isAfterLast()){
                list.add(value.getGoods());
                value.moveToNext();
            }
        }finally {
            value.close();
        }
        return list;
    }

    private Cursor getQuery(boolean distinct, String table, String[]colms,
                            String where,
                            String [] arg, String group, String order){
        Cursor cursor = mHelper.query(distinct, table,colms,where,arg,group,null,order,null);
        return cursor;
    }

    public void sales(UUID id, String type){
      ContentValues values = new ContentValues();
      values.put(ORDER.Cols.STATUS,1);
      values.put(ORDER.Cols.NOTE,type);
     mHelper.update(ORDER.NAME,values,ORDER.Cols.ACCT+" = ?",new String[]{id.toString()});
    }
    public void sales(UUID acct,String type,int id, int status){
        ContentValues values = new ContentValues();
        values.put(ORDER.Cols.NOTE,type);
        values.put(ORDER.Cols.STATUS,status);
        mHelper.update(ORDER.NAME,values,ORDER.Cols.ACCT+" = ? and "+ORDER.Cols.GOODS+" = ?",new String[]{acct.toString(),String.valueOf(id)});
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void setXML(File file){
        try {
            day = Preferes.getDay(mContext);
            InputStream  stream = new FileInputStream(file);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document doc = documentBuilder.parse(stream);
            NodeList nList1 = doc.getElementsByTagName("Order");
            for (int i = 0; i< nList1.getLength(); i++){
                Node n = nList1.item(i);
                if(n.getNodeType() == Node.ELEMENT_NODE){

                    Element element1 = (Element)n;
                    NodeList list = element1.getElementsByTagName("Good");
                    Order order = new Order();
                    order.setAcct(UUID.randomUUID());
                    order.setAdress(element1.getAttribute("address"));
                    order.setPhone(element1.getAttribute("phone"));
                    order.setDate(new Date());
                    order.setContact(element1.getAttribute("client"));
                    order.setStatus(0);
                    order.setNote("");
                    for(int j = 0; j<list.getLength(); j++){
                        Node p = list.item(j);
                        Element element = (Element)p;
                        Goods goods = new Goods();
                        goods.setName(element.getAttribute("name"));
                        goods.setPrice(Double.parseDouble(element.getAttribute("price")));
                        goods.setQuantity(Double.parseDouble(element.getAttribute("qtty")));

                        goods.setTax(Integer.parseInt(element.getAttribute("tax")));
                        goods.setCode(element.getAttribute("code"));

                            mHelper.insert(GOODS.NAME, null, goods.getContentValue());

                            int id = getId();
                            order.setGoods(id);
                            mHelper.insert(ORDER.NAME, null, order.getContenValue(day));
                        


                    }
                }
                file.delete();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private int getId(){
        try (Cursor cursor = getQuery(false, GOODS.NAME, new String[]{"Max(_id) as _id"}, null, null, null, null)) {
            if (cursor.getCount() == 0) {
                return 0;
            }
            cursor.moveToFirst();
            return cursor.getInt(cursor.getColumnIndex("_id"));
        }
    }

    public void sale(ArrayList<Goods> goods,String type,double sum){
        if(!isON){
            mKKT.Sale(goods,type,sum);
        }
    }
    public void connect(){
        if(!isON){
            mKKT.init(mContext);
            mKKT.connect();
        }
    }
    public String Devices(){
        return mKKT.getDevises();
    }
    public boolean getError(){
        if(isON )return false;
      else  return mKKT.getError();
    }
    public void zreport(){
        if(!isON){
            mKKT.ZReport();
        }
    }

    private static String getDate(Date date) {
        DateFormat format = new DateFormat();
        return (String) format.format("dd_MM_yyyy",date);
    }
    private void createDir(){
        File file = new File(Environment.getExternalStorageDirectory(), "Order");
        if (!file.exists()) {
            file.mkdirs();
        }
    }
}

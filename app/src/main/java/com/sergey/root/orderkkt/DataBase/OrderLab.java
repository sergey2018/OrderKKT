package com.sergey.root.orderkkt.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.annotation.RequiresApi;

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
        switch (kkt){
            case 1: mKKT = new ShtrihPrintKKT();
            break;
            case 2: mKKT = new AtolPrintKKT();
            default:isON = true;
            break;
        }
    }

    public ArrayList<Order> getOrder(){
        ArrayList<Order> list = null;
        String table = ORDER.NAME;
        String [] colums = new String[]{ORDER.Cols.ACCT,ORDER.Cols.PHONE,ORDER.Cols.NOTE,ORDER.Cols.CONTACT,ORDER.Cols.ADRESS,"SUM("+ORDER.Cols.STATUS+") AS "+ORDER.Cols.STATUS,"COUNT("+ORDER.Cols.STATUS+") AS cour",ORDER.Cols.DATE};
        Cursor cursor = getQuery(true,table,colums,null,null,ORDER.Cols.ACCT,null);
        OrderWrappes value = new OrderWrappes(cursor);
        try{
            if(value.getCount() == 0){
                return list;
            }
            list = new ArrayList<>();
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
    private ContentValues addGoods(Goods goods){
        ContentValues values = new ContentValues();
        values.put(GOODS.Cols.NAME,goods.getName());
        values.put(GOODS.Cols.PRICE,goods.getPrice());
        values.put(GOODS.Cols.CODE,goods.getCode());
        values.put(GOODS.Cols.QUANT,goods.getQuantity());
        values.put(GOODS.Cols.TAX,goods.getTax());
        return values;
    }
    private ContentValues addOrder(Order order){
        ContentValues values = new ContentValues();
        values.put(ORDER.Cols.ACCT,order.getAcct().toString());
        values.put(ORDER.Cols.GOODS,order.getGoods());
        values.put(ORDER.Cols.DATE,order.getDate().getTime());
        values.put(ORDER.Cols.PHONE,order.getPhone());
        values.put(ORDER.Cols.ADRESS,order.getAdress());
        values.put(ORDER.Cols.STATUS,order.getStatus());
        values.put(ORDER.Cols.CONTACT,order.getContact());
        values.put(ORDER.Cols.NOTE,order.getNote());
        return values;
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
                        int d = getDouble(goods);
                        if(d == 0) {
                            mHelper.insert(GOODS.NAME, null, addGoods(goods));

                            int id = getId();
                            order.setGoods(id);
                            mHelper.insert(ORDER.NAME, null, addOrder(order));
                        }
                        else {
                            order.setGoods(d);
                            mHelper.insert(ORDER.NAME, null, addOrder(order));
                        }

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

    private int getDouble(Goods goods){
        Cursor cursor = getQuery(false,GOODS.NAME,new String[]{"_id"},GOODS.Cols.NAME + " = ? ",new String[]{goods.getName()},null,null);
        try {
            if (cursor.getCount() == 0){
                return 0;
            }
            cursor.moveToFirst();
            return cursor.getInt(cursor.getColumnIndex("_id"));
        }
        finally {
            cursor.close();
        }
    }

    public void sale(ArrayList<Goods> goods,String type){
        if(!isON){
            mKKT.Sale(goods,type);
        }
    }
    public void connect(){
        if(!isON){
            mKKT.connect(mContext);
        }
    }
    public String Devices(){
        return mKKT.getDevises();
    }
    public boolean getError(){
        return mKKT.getError();
    }

}

package com.sergey.root.orderkkt.CursorWrappes;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.sergey.root.orderkkt.DataBase.dbShema;
import com.sergey.root.orderkkt.Model.Order;

import java.util.Date;
import java.util.UUID;

public class OrderWrappes extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public OrderWrappes(Cursor cursor) {
        super(cursor);
    }

    public Order getOrder(){
        Order order = new Order();
        order.setAcct(UUID.fromString(getString(getColumnIndex(dbShema.ORDER.Cols.ACCT))));
        order.setAdress(getString(getColumnIndex(dbShema.ORDER.Cols.ADRESS)));
        order.setContact(getString(getColumnIndex(dbShema.ORDER.Cols.CONTACT)));
        order.setPhone(getString(getColumnIndex(dbShema.ORDER.Cols.PHONE)));
        order.setStatus(getInt(getColumnIndex(dbShema.ORDER.Cols.STATUS)));
        order.setDate(new Date(getLong(getColumnIndex(dbShema.ORDER.Cols.DATE))));
        order.setNote(getString(getColumnIndex(dbShema.ORDER.Cols.NOTE)));
        if(getColumnIndex("cour") != -1) {
            order.setCount(getInt(getColumnIndex("cour")));
        }
        return order;
    }
}

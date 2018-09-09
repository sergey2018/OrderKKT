package com.sergey.root.orderkkt.CursorWrappes;

import android.database.Cursor;

import com.sergey.root.orderkkt.DataBase.dbShema;
import com.sergey.root.orderkkt.Model.Order;

public class ExelOrderWrapper extends OrderWrappes {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public ExelOrderWrapper(Cursor cursor) {
        super(cursor);
    }
    public Order getOrder2(){
        Order order = new Order();
        order.setName(getString(getColumnIndex(dbShema.GOODS.Cols.NAME)));
        order.setCode(getString(getColumnIndex(dbShema.GOODS.Cols.CODE)));
        order.setPrice(getDouble(getColumnIndex(dbShema.GOODS.Cols.PRICE)));
        order.setQuantity(getDouble(getColumnIndex(dbShema.GOODS.Cols.QUANT)));
        return order;
    }
}

package com.sergey.root.orderkkt.CursorWrappes;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.sergey.root.orderkkt.DataBase.dbShema;
import com.sergey.root.orderkkt.Model.Goods;

public class GoodsWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public GoodsWrapper(Cursor cursor) {
        super(cursor);
    }

    public Goods getGoods(){
        Goods goods = new Goods();
        goods.setId(getInt(getColumnIndex("_id")));
        goods.setName(getString(getColumnIndex(dbShema.GOODS.Cols.NAME)));
        goods.setPrice(getDouble(getColumnIndex(dbShema.GOODS.Cols.PRICE)));
        goods.setQuantity(getDouble(getColumnIndex(dbShema.GOODS.Cols.QUANT)));
        goods.setCode(getString(getColumnIndex(dbShema.GOODS.Cols.CODE)));
        goods.setFlags(true);
        goods.setTax(getInt(getColumnIndex(dbShema.GOODS.Cols.TAX)));
        return goods;
    }
}

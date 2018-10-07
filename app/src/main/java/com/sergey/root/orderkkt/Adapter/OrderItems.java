package com.sergey.root.orderkkt.Adapter;

import com.sergey.root.orderkkt.Model.Order;

public class OrderItems extends ListItem {
    public Order getOrder() {
        return mOrder;
    }

    public void setOrder(Order order) {
        mOrder = order;
    }

    private Order mOrder;

    @Override
    public int getType() {
        return TYPE_EVENT;
    }
}

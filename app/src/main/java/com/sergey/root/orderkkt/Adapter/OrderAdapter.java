package com.sergey.root.orderkkt.Adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sergey.root.orderkkt.Model.Order;
import com.sergey.root.orderkkt.R;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHolder>{

    private ArrayList<Order> mOrders;

    public OrderAdapter(ArrayList<Order> orders) {
        mOrders = orders;
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item,null);
        return new OrderHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder holder, int position) {
        Order order = mOrders.get(position);
        holder.setOrder(order);
    }

    @Override
    public int getItemCount() {
        return mOrders.size();
    }

    public class OrderHolder extends RecyclerView.ViewHolder{

        private TextView mNumber,mAdres,mPhone,mContact,mStatus;
        public OrderHolder(View itemView) {
            super(itemView);
            mNumber = itemView.findViewById(R.id.number_order);
            mAdres = itemView.findViewById(R.id.text_adress);
            mPhone =itemView.findViewById(R.id.phone);
            mContact = itemView.findViewById(R.id.contact);
            mStatus = itemView.findViewById(R.id.status);
        }
        public void setOrder(Order order){
            mNumber.setText("Заказ №" + order.getNumber());
            mAdres.setText(order.getAdress());
            mPhone.setText(order.getPhone());
            mContact.setText(order.getContact());
            if(order.getStatus() == 0) {
                mStatus.setText("Недоставлен");
                mStatus.setTextColor(Color.parseColor("#ff0000"));
            }
            else {
                mStatus.setText("Доставлен");
                mStatus.setTextColor(Color.parseColor("#00ff00"));
            }
        }
    }
}

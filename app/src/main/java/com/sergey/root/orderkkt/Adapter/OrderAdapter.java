package com.sergey.root.orderkkt.Adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.sergey.root.orderkkt.Model.Order;
import com.sergey.root.orderkkt.R;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHolder> {

    private ArrayList<Order> mOrders;
    private Context mContext;

    public OrderAdapter(ArrayList<Order> orders, Context context) {
        mOrders = orders;
        mContext = context;
    }
    public void setOrders(ArrayList<Order> orders){
        mOrders = orders;
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, null);
        return new OrderHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderHolder holder, int position) {
        final Order order = mOrders.get(position);

        holder.mClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(mContext, holder.mClick);
                popupMenu.inflate(R.menu.menu_phone);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.phone_call:
                                Intent intent = new Intent(Intent.ACTION_CALL);
                                intent.setData(Uri.parse("tel:" + order.getPhone()));
                                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                    // TODO: Consider calling
                                    //    ActivityCompat#requestPermissions
                                    // here to request the missing permissions, and then overriding
                                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                    //                                          int[] grantResults)
                                    // to handle the case where the user grants the permission. See the documentation
                                    // for ActivityCompat#requestPermissions for more details.
                                    return false;
                                }
                                mContext.startActivity(intent);
                                break;
                                default:
                                    break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        holder.setOrder(order);
    }

    @Override
    public int getItemCount() {
        return mOrders.size();
    }

    public class OrderHolder extends RecyclerView.ViewHolder{
        public TextView mClick;
        private TextView mAdres,mPhone,mContact,mStatus,mDate;
        public OrderHolder(View itemView) {
            super(itemView);
            mAdres = itemView.findViewById(R.id.text_adress);
            mPhone =itemView.findViewById(R.id.phone);
            mContact = itemView.findViewById(R.id.contact);
            mStatus = itemView.findViewById(R.id.status);
            mDate = itemView.findViewById(R.id.date_order);
            mClick = itemView.findViewById(R.id.txtOptionDigit);
        }
        public void setOrder(Order order){
            mAdres.setText(order.getAdress());
            mDate.setText(order.getDateText());
            mPhone.setText(order.getPhone());
            mContact.setText(order.getContact());
            if(order.getStatus() == 0) {
                mStatus.setText("Недоставлен");
                mStatus.setTextColor(Color.parseColor("#ff0000"));
            }
            else if(order.getStatus() == order.getCount()){
                mStatus.setText("Доставлен");
                mStatus.setTextColor(Color.parseColor("#00ff00"));
            }
            else {
                mStatus.setText("Частично доставлен");
                mStatus.setTextColor(Color.parseColor("#ffff00"));
            }
        }
    }
}

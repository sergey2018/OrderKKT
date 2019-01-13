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
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.sergey.root.orderkkt.Model.Order;
import com.sergey.root.orderkkt.R;

import java.util.ArrayList;

import static com.sergey.root.orderkkt.Adapter.ListItem.TYPE_EVENT;
import static com.sergey.root.orderkkt.Adapter.ListItem.TYPE_HEADER;

public class OrderListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public OrderListAdapter(ArrayList<ListItem> listItem,Context context) {
        mListItem = listItem;
        mContext = context;
    }

    private Context mContext;
    public ArrayList<ListItem> getListItem() {
        return mListItem;
    }

    private ArrayList<ListItem>mListItem = new ArrayList<>();
    private static class HeaderViewHolder extends RecyclerView.ViewHolder {

        TextView txt_header;

        HeaderViewHolder(View itemView) {
            super(itemView);
            txt_header = (TextView) itemView.findViewById(R.id.txt_header);
        }

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType){
            case TYPE_HEADER:
                View itemView = inflater.inflate(R.layout.view_list_item_header, parent, false);
                return new HeaderViewHolder(itemView);
            case  TYPE_EVENT:
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, null);
                return new OrderHolder(v);
            default:
                throw new IllegalStateException("unsupported item type");
        }

    }
    @Override
    public int getItemViewType(int position) {
        return mListItem.get(position).getType();
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType){
            case TYPE_HEADER:
                HeaderItem item = (HeaderItem)mListItem.get(position);
                ((HeaderViewHolder) holder).txt_header.setText(item.getDate());
                break;
            case TYPE_EVENT:
                OrderItems items = (OrderItems)mListItem.get(position);
                final OrderHolder orderHolder = (OrderHolder)holder;
                orderHolder.setOrder(items.getOrder());
                final Order order = items.getOrder();
                if(order.getPhone() == "") return;
                orderHolder.mClick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupMenu popupMenu = new PopupMenu(mContext, orderHolder.mClick);
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
                break;
        }
    }
    public void setItems(ArrayList<ListItem> items){
        mListItem = items;
    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }
    public class OrderHolder extends RecyclerView.ViewHolder{
        public Button mClick;
        private TextView mAdres,mPhone,mContact,mStatus,mDate;
        public OrderHolder(View itemView) {
            super(itemView);
            mAdres = itemView.findViewById(R.id.text_adress);
            mPhone =itemView.findViewById(R.id.phone);
            mContact = itemView.findViewById(R.id.contact);
            mStatus = itemView.findViewById(R.id.status);
            //mDate = itemView.findViewById(R.id.date_order);
            mClick = itemView.findViewById(R.id.txtOptionDigit);
        }
        public void setOrder(Order order){
            mAdres.setText(order.getAdress());
            //mDate.setText(order.getDateText());
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
            else if(order.getStatus() == 2*order.getCount()){
                mStatus.setText("Отменен");
                mStatus.setTextColor(Color.parseColor("#ff0000"));
            }
            else {
                mStatus.setText("Частично доставлен");
                mStatus.setTextColor(Color.parseColor("#ffff00"));
            }
        }
    }
}

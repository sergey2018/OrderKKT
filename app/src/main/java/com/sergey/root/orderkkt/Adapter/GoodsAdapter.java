package com.sergey.root.orderkkt.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.sergey.root.orderkkt.Model.Goods;
import com.sergey.root.orderkkt.R;

import java.util.ArrayList;

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.GoodsHolder>{

    private ArrayList<Goods> mGoods;

    public GoodsAdapter(ArrayList<Goods> goods) {
        mGoods = goods;
    }

    @NonNull
    @Override
    public GoodsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.goods_item,null);
        return new GoodsHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GoodsHolder holder, final int position) {
        Goods goods = mGoods.get(position);

        holder.mSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mGoods.get(position).setFlags(isChecked);
            }
        });
        holder.setGoods(goods);
    }

    @Override
    public int getItemCount() {
        return mGoods.size();
    }

    public class GoodsHolder extends RecyclerView.ViewHolder{
        private TextView mName,mPrice,mSum;
        public CheckBox mSelect;
        public GoodsHolder(View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.goods_name);
            mPrice = itemView.findViewById(R.id.price_quantyt);
            mSum = itemView.findViewById(R.id.goods_sum);
            mSelect = itemView.findViewById(R.id.goods_select);
        }

        public void setGoods(Goods goods){
            mName.setText(goods.getName());
            mPrice.setText(goods.getQuantity() + " x "+goods.getPrice());
            mSum.setText(goods.summ()+"");
            mSelect.setChecked(goods.isFlags());
        }
    }
}

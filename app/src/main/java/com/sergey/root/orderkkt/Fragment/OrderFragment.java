package com.sergey.root.orderkkt.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.sergey.root.orderkkt.Activity.GoodsActivity;
import com.sergey.root.orderkkt.Activity.SettinsActivity;
import com.sergey.root.orderkkt.Adapter.OrderAdapter;
import com.sergey.root.orderkkt.DataBase.OrderLab;
import com.sergey.root.orderkkt.GoodsClickListener;
import com.sergey.root.orderkkt.Model.Order;
import com.sergey.root.orderkkt.R;

import java.util.ArrayList;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {


    @BindView(R.id.order_view)
    RecyclerView mOrderView;
    Unbinder unbinder;

    public OrderFragment() {
        // Required empty public constructor
    }


    private void update(){
        final ArrayList<Order> orders = OrderLab.getInstance(getActivity()).getOrder();
        if(orders == null)return;
        OrderAdapter adapter = new OrderAdapter(orders);
        mOrderView.setAdapter(adapter);
        DividerItemDecoration decoration = new DividerItemDecoration(mOrderView.getContext(),DividerItemDecoration.VERTICAL);
        mOrderView.addItemDecoration(decoration);
        mOrderView.addOnItemTouchListener(new GoodsClickListener(getActivity(), new GoodsClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                UUID id = orders.get(position).getAcct();
                Intent intent = GoodsActivity.newIntent(getActivity(),id);
                startActivity(intent);
            }
        }));
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        mOrderView.setLayoutManager(new LinearLayoutManager(getActivity()));
        update();
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        update();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.settings,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.settigs:
                Intent intent = new Intent(getActivity(), SettinsActivity.class);
                startActivity(intent);
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }
}

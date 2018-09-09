package com.sergey.root.orderkkt.Fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.sergey.root.orderkkt.Preferes;
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
    private int mStatus = 0;
    private static final String ARG_STATSUS = "status";
    private OrderAdapter adapter;
    public OrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
       if(requestCode == 3 && resultCode == Activity.RESULT_OK){
         new ReportTask().execute();

       }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStatus = getArguments().getInt(ARG_STATSUS);

    }

    public static Fragment newIntents(int status){
        OrderFragment fragment = new OrderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_STATSUS,status);
        fragment.setArguments(bundle);
        return fragment;
    }


    private void update(){
        ArrayList<Order> orders = OrderLab.getInstance(getActivity()).getOrder(mStatus);
        if(orders == null)return;
        if(adapter == null){
            adapter = new OrderAdapter(orders,getActivity());
            mOrderView.setAdapter(adapter);
        }
        else {
            adapter.setOrders(orders);
            adapter.notifyDataSetChanged();
        }
        DividerItemDecoration decoration = new DividerItemDecoration(mOrderView.getContext(),DividerItemDecoration.VERTICAL);
        mOrderView.addItemDecoration(decoration);
        mOrderView.addOnItemTouchListener(new GoodsClickListener(getActivity(), new GoodsClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(adapter.getOrders().get(position).getStatus() == 0){
                    UUID id = adapter.getOrders().get(position).getAcct();
                    Intent intent = GoodsActivity.newIntent(getActivity(),id);
                    startActivity(intent);
                }
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
        getActivity().setTitle("Заказы");
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
            case R.id.z_report:
                FragmentManager manager = getFragmentManager();
                ReportDialog dialog = new ReportDialog();
                dialog.setTargetFragment(OrderFragment.this,3);
                dialog.show(manager,"report");
                default:
                    return super.onOptionsItemSelected(item);
        }
    }
    private class ReportTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            OrderLab.getInstance(getActivity()).zreport();
            OrderLab.getInstance(getActivity()).createFile(Preferes.getDay(getActivity()));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(OrderLab.getInstance(getActivity()).getError()) return;
            Preferes.setDay(getActivity());


        }
    }
}

package com.sergey.root.orderkkt.Fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.sergey.root.orderkkt.Adapter.HeaderItem;
import com.sergey.root.orderkkt.Adapter.ListItem;
import com.sergey.root.orderkkt.Adapter.OrderItems;
import com.sergey.root.orderkkt.Adapter.OrderListAdapter;
import com.sergey.root.orderkkt.DataBase.OrderLab;
import com.sergey.root.orderkkt.GoodsClickListener;
import com.sergey.root.orderkkt.Model.Order;
import com.sergey.root.orderkkt.Preferes;
import com.sergey.root.orderkkt.R;
import com.sergey.root.orderkkt.YandexService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.sergey.root.orderkkt.Adapter.ListItem.TYPE_EVENT;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {


    @BindView(R.id.order_view)
    RecyclerView mOrderView;
    Unbinder unbinder;
    private int mStatus = 0;
    private ProgressDialog mDialog;
    private static final String ARG_STATSUS = "status";
    private OrderListAdapter mAdapter;
    private ArrayList<ListItem>mList = new ArrayList<>();
    private BroadcastReceiver mUpdate = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            update1();
        }
    };
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


    private void update1(){
        mList.clear();
        ArrayList<Order> ord = OrderLab.getInstance(getActivity()).getOrder(mStatus);
        Map<String,ArrayList<Order>> map = getOrder(ord);
        for(String date: map.keySet()) {
            HeaderItem item = new HeaderItem();
            item.setDate(date);
            mList.add(item);
            for (Order order : map.get(date)) {
                OrderItems items = new OrderItems();
                items.setOrder(order);
                mList.add(items);
            }
        }
            if(mAdapter == null){
                mAdapter = new OrderListAdapter(mList,getActivity());
                mOrderView.setAdapter(mAdapter);
            }
            else {
                mAdapter.setItems(mList);
                mAdapter.notifyDataSetChanged();
            }
            mOrderView.addOnItemTouchListener(new GoodsClickListener(getActivity(), new GoodsClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    int type = mAdapter.getListItem().get(position).getType();
                    if(type ==TYPE_EVENT){
                        Order order = ((OrderItems)mAdapter.getListItem().get(position)).getOrder();
                        if(order.getStatus() == 0 ){
                            UUID id = order.getAcct();
                            Intent intent = GoodsActivity.newIntent(getActivity(),id);
                            startActivity(intent);
                        }
                    }
                }
            }));


    }

    public Map<String, ArrayList<Order>> getOrder(ArrayList<Order> orders){
        Map<String, ArrayList<Order>> or = new TreeMap<>(Collections.reverseOrder());
        for(Order order: orders){
            ArrayList<Order> value = or.get(order.getDateText());
            if(value == null){
                value = new ArrayList<>();
                or.put(order.getDateText(),value);
            }
            value.add(order);
        }
        return or;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        mOrderView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDialog = new ProgressDialog(getActivity());
        mDialog.setMessage("Передача в кассовый апарат");
        mDialog.setProgress(ProgressDialog.STYLE_SPINNER);
        getActivity().setTitle("Заказы");
        update1();
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(YandexService.SHOW_ACTION);
        getActivity().registerReceiver(mUpdate,filter);
    }

    @Override
    public void onResume() {
        super.onResume();
        update1();
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(mUpdate);
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
                return true;
            case R.id.x_report:
                new ReportXTask().execute();
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
        protected void onPreExecute() {
            super.onPreExecute();
            mDialog.show();
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mDialog.dismiss();
            if(OrderLab.getInstance(getActivity()).getError()) return;
            Preferes.setDay(getActivity());
            update1();


        }
    }
    private class ReportXTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            OrderLab.getInstance(getActivity()).XReport();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mDialog.dismiss();
        }


    }
}

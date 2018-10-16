package com.sergey.root.orderkkt.Fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sergey.root.orderkkt.Adapter.GoodsAdapter;
import com.sergey.root.orderkkt.DataBase.OrderLab;
import com.sergey.root.orderkkt.GoodsClickListener;
import com.sergey.root.orderkkt.KKT.KKT;
import com.sergey.root.orderkkt.Model.Goods;
import com.sergey.root.orderkkt.R;

import java.util.ArrayList;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReturnGoodsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReturnGoodsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_GOODS = "got";
    private static final String ARG_TYPE = "type";
    @BindView(R.id.recyReturn)
    RecyclerView recyReturn;
    @BindView(R.id.textReturnSum)
    TextView textReturnSum;
    @BindView(R.id.ButtReturn)
    Button ButtReturn;
    Unbinder unbinder;
    private UUID mArgs;

    // TODO: Rename and change types of parameters

    private String mType;
    private ArrayList<Goods> mGoods;


    public ReturnGoodsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ReturnGoodsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReturnGoodsFragment newInstance(UUID param1, String type) {
        ReturnGoodsFragment fragment = new ReturnGoodsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_GOODS, param1);
        args.putString(ARG_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    private void update() {
        final GoodsAdapter adapter = new GoodsAdapter(mGoods);
        recyReturn.setAdapter(adapter);
        recyReturn.addOnItemTouchListener(new GoodsClickListener(getActivity(), new GoodsClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(  adapter.getGoods().get(position).isFlags()){
                    adapter.getGoods().get(position).setFlags(false);
                }
                else {
                    adapter.getGoods().get(position).setFlags(true);
                }
                double sum = summ();
                textReturnSum.setText(sum + " руб.");
                adapter.notifyDataSetChanged();
            }
        }));
        DividerItemDecoration decoration = new DividerItemDecoration(recyReturn.getContext(), DividerItemDecoration.VERTICAL);
        recyReturn.addItemDecoration(decoration);
        double sum = summ();
        textReturnSum.setText(sum + " руб.");
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mArgs = (UUID) getArguments().getSerializable(ARG_GOODS);
            mType = getArguments().getString(ARG_TYPE);
            mGoods = OrderLab.getInstance(getActivity()).getGoods(mArgs);
        }

    }
    public double summ(){
        double summ = 0;
        for (int i = 0; i<mGoods.size(); i++){
            if(mGoods.get(i).isFlags()){
                summ = summ + mGoods.get(i).summ();
            }
        }
        return summ;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_return_goods, container, false);
        unbinder = ButterKnife.bind(this, view);
        recyReturn.setLayoutManager(new LinearLayoutManager(getActivity()));
        update();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    @OnClick(R.id.ButtReturn)
    void onClid(){
        new ReturnTask().execute(mType);
    }
    private  class ReturnTask extends AsyncTask<String,Void,Void>{

        @Override
        protected Void doInBackground(String... strings) {
            if(strings[0].equals("Оплата наличными")){
                OrderLab.getInstance(getActivity()).ReturnSale(mGoods,"0");
            }
            else {
                OrderLab.getInstance(getActivity()).ReturnSale(mGoods,"1");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            OrderLab.getInstance(getActivity()).sales(mArgs, mType,0);
            getActivity().finish();
        }
    }
}

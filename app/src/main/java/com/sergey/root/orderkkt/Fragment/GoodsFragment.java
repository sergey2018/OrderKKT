package com.sergey.root.orderkkt.Fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sergey.root.orderkkt.Adapter.GoodsAdapter;
import com.sergey.root.orderkkt.DataBase.OrderLab;
import com.sergey.root.orderkkt.GoodsClickListener;
import com.sergey.root.orderkkt.Model.Goods;
import com.sergey.root.orderkkt.R;

import java.util.ArrayList;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.sergey.root.orderkkt.Fragment.NoteDialogFragment.EXTRA_NOTE;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GoodsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GoodsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ACT = "act";
    @BindView(R.id.goods_list)
    RecyclerView mGoodsList;
    Unbinder unbinder;
    @BindView(R.id.nal_no_cash)
    Button mNalNoCash;
    @BindView(R.id.saleKatr)
    Button mSaleKatr;
    @BindView(R.id.linearLayout)
    LinearLayout mLinearLayout;
    @BindView(R.id.summ)
    TextView mSumm;
    private UUID id;
    private String type;
    private String value;
    private ProgressDialog mDialog;
    private ArrayList<Goods> mGoods;
    // TODO: Rename and change types of parameters


    public GoodsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                value = data.getStringExtra(EXTRA_NOTE);
                new KKTTask().execute(type);
            }
        }
        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                value = data.getStringExtra(EXTRA_NOTE);
                new KKTTask().execute(type);
            }
        }
        if (requestCode == 3 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                value = data.getStringExtra(EXTRA_NOTE);
                OrderLab.getInstance(getActivity()).sales(id, value,2);
                getActivity().finish();
            }
        }
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment GoodsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GoodsFragment newInstance(UUID acct) {
        GoodsFragment fragment = new GoodsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_ACT, acct);
        fragment.setArguments(args);
        return fragment;
    }

    private void update() {
        final GoodsAdapter adapter = new GoodsAdapter(mGoods);
        mGoodsList.setAdapter(adapter);
        mGoodsList.addOnItemTouchListener(new GoodsClickListener(getActivity(), new GoodsClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
              if(  adapter.getGoods().get(position).isFlags()){
                  adapter.getGoods().get(position).setFlags(false);
              }
              else {
                  adapter.getGoods().get(position).setFlags(true);
              }
                double sum = summ();
                mSumm.setText("Итого: " + sum + " руб.");
                adapter.notifyDataSetChanged();
            }
        }));
        DividerItemDecoration decoration = new DividerItemDecoration(mGoodsList.getContext(), DividerItemDecoration.VERTICAL);
        mGoodsList.addItemDecoration(decoration);
        double sum = summ();
        mSumm.setText("Итого: " + sum + " руб.");
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = (UUID) getArguments().getSerializable(ARG_ACT);
            mGoods = OrderLab.getInstance(getActivity()).getGoods(id);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_goods, container, false);
        unbinder = ButterKnife.bind(this, view);
        getActivity().setTitle("Товары");
        mDialog = new ProgressDialog(getActivity());
        mDialog.setMessage("Передача в кассовый апарат");
        mDialog.setProgress(ProgressDialog.STYLE_SPINNER);
        mGoodsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        update();
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_goods,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.del_order:
                FragmentManager manager = getFragmentManager();
                NoteDialogFragment dialogFragment = new NoteDialogFragment();
                dialogFragment.setTargetFragment(GoodsFragment.this, 3);
                dialogFragment.show(manager, "note1");
                return true;
                default:
                    return  super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.nal_no_cash)
    void OnClick() {
        type = "Оплата наличными";
       if(getFalse()){
            new KKTTask().execute(type);
       }
       else {
           FragmentManager manager = getFragmentManager();
           NoteDialogFragment dialogFragment = new NoteDialogFragment();
           dialogFragment.setTargetFragment(GoodsFragment.this, 1);
           dialogFragment.show(manager, "note");
       }


    }
    @OnClick(R.id.saleKatr)
    void Kart(){
        type = "Оплата картой";
        if(getFalse()) {
            new KKTTask().execute(type);
        }
        else {
            FragmentManager manager = getFragmentManager();
            NoteDialogFragment dialogFragment = new NoteDialogFragment();
            dialogFragment.setTargetFragment(GoodsFragment.this, 1);
            dialogFragment.show(manager, "note");
        }
    }

    private boolean getFalse() {
        for (Goods g : mGoods) {
            if (!g.isFlags()) {
                return false;
            }
        }
        return true;
    }

    private class KKTTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            if (strings[0].equals("Оплата наличными")) {
                OrderLab.getInstance(getActivity()).sale(mGoods, "0",summ());
            } else {
                OrderLab.getInstance(getActivity()).sale(mGoods, "1",summ());
            }
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
            if(OrderLab.getInstance(getActivity()).getError()){
                return;
            }

            if (getFalse()) {
                OrderLab.getInstance(getActivity()).sales(id, type);
                getActivity().finish();
            } else {
                for (int i = 0; i < mGoods.size(); i++) {
                    if (!mGoods.get(i).isFlags()) {
                        OrderLab.getInstance(getActivity()).sales(id, value, mGoods.get(i).getId(), 0);
                    } else {
                        OrderLab.getInstance(getActivity()).sales(id, type, mGoods.get(i).getId(), 1);
                    }
                }
                getActivity().finish();
            }
        }
    }
}

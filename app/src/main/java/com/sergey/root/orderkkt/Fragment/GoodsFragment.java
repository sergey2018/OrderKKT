package com.sergey.root.orderkkt.Fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sergey.root.orderkkt.Adapter.GoodsAdapter;
import com.sergey.root.orderkkt.DataBase.OrderLab;
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
    private UUID id;
    private ArrayList<Goods> mGoods;
    // TODO: Rename and change types of parameters


    public GoodsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
           if(data != null){
               String value = data.getStringExtra(EXTRA_NOTE);
               for(int i = 0; i<mGoods.size(); i++){
                   if(!mGoods.get(i).isFlags()){
                       OrderLab.getInstance(getActivity()).sales(id,value,mGoods.get(i).getId(),0);
                   }
                   else {
                       OrderLab.getInstance(getActivity()).sales(id,"Оплата",mGoods.get(i).getId(),1);
                   }
               }
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
                GoodsAdapter adapter = new GoodsAdapter(mGoods);
        mGoodsList.setAdapter(adapter);
        DividerItemDecoration decoration = new DividerItemDecoration(mGoodsList.getContext(), DividerItemDecoration.VERTICAL);
        mGoodsList.addItemDecoration(decoration);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = (UUID) getArguments().getSerializable(ARG_ACT);
            mGoods =  OrderLab.getInstance(getActivity()).getGoods(id);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_goods, container, false);
        unbinder = ButterKnife.bind(this, view);
        mGoodsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        update();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    @OnClick(R.id.nal_no_cash)
    void OnClick(){
        if(getFalse()) {
            OrderLab.getInstance(getActivity()).sales(id, "Оплата");
            getActivity().finish();
        }else {
            FragmentManager manager = getFragmentManager();
            NoteDialogFragment dialogFragment = new NoteDialogFragment();
            dialogFragment.setTargetFragment(GoodsFragment.this,1);
            dialogFragment.show(manager,"note");
        }
    }

    private boolean getFalse(){
        for (Goods g:mGoods) {
            if(!g.isFlags()){
                return false;
            }
        }
        return true;
    }
}

package com.sergey.root.orderkkt.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.sergey.root.orderkkt.Fragment.ReturnGoodsFragment;

import java.util.UUID;

public class ReturnActivity extends SingleActivity {
    public static final String EXTRA_GOODS_ID="root.kkt.extra_goods_id";
    public static final String EXTRA_GOODS_TYPE="root.kkt.extra_goods_type";
    public static Intent newIntent(Context context, UUID id,String Type){
        Intent intent = new Intent(context,ReturnActivity.class);
        intent.putExtra(EXTRA_GOODS_ID,id);
        intent.putExtra(EXTRA_GOODS_TYPE,Type);
        return intent;
    }
    @Override
    protected Fragment createFragment() {
        UUID id = (UUID) getIntent().getSerializableExtra(EXTRA_GOODS_ID);
        String type = getIntent().getStringExtra(EXTRA_GOODS_TYPE);
        return ReturnGoodsFragment.newInstance(id,type);
    }
}

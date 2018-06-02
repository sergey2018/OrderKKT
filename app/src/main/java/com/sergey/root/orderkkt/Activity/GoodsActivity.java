package com.sergey.root.orderkkt.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.sergey.root.orderkkt.Fragment.GoodsFragment;

import java.util.UUID;

public class GoodsActivity extends SingleActivity {


    public static final String EXTRA_GOODS_ID="root.kkt.extra_goods_id";
    public static Intent newIntent(Context context, UUID id){
        Intent intent = new Intent(context,GoodsActivity.class);
        intent.putExtra(EXTRA_GOODS_ID,id);
        return intent;
    }
    @Override
    protected Fragment createFragment() {
        UUID id = (UUID) getIntent().getSerializableExtra(EXTRA_GOODS_ID);
        return GoodsFragment.newInstance(id);
    }
}

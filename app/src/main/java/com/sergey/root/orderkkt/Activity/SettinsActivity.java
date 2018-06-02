package com.sergey.root.orderkkt.Activity;

import android.support.v4.app.Fragment;

import com.sergey.root.orderkkt.Fragment.SettigsFragment;

public class SettinsActivity extends SingleActivity {
    @Override
    protected Fragment createFragment() {
        return new SettigsFragment();
    }
}

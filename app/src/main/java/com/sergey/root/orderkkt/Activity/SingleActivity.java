package com.sergey.root.orderkkt.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.sergey.root.orderkkt.R;

public abstract class SingleActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragment_contens);
        if(fragment == null){
            fragment = createFragment();
            manager.beginTransaction().add(R.id.fragment_contens,fragment).commit();
        }

    }
}

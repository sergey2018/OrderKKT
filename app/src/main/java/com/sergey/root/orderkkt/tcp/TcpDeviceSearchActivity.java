package com.sergey.root.orderkkt.tcp;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import com.sergey.root.orderkkt.R;
import android.support.v7.app.AppCompatActivity;
import com.sergey.root.orderkkt.databinding.ActivityTcpDeviceSearchBinding;



public class TcpDeviceSearchActivity extends AppCompatActivity {

    public static final int REQUEST_SEARCH_TCP_DEVICE = 666;

    private TcpDeviceSearchViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityTcpDeviceSearchBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_tcp_device_search);

        vm = ViewModelProviders.of(this).get(TcpDeviceSearchViewModel.class);

        binding.setVm(vm);
        binding.setActivity(this);
    }

    @Override
    protected void onResume() {
        if(vm != null)
            vm.onStarted();
        
        super.onResume();
    }

    @Override
    protected void onPause() {
        if(vm != null)
            vm.onStopped();

        super.onPause();
    }
}

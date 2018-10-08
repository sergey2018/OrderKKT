package com.sergey.root.orderkkt.Fragment;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.sergey.root.orderkkt.Activity.DeviceListActivity;
import com.sergey.root.orderkkt.DataBase.OrderLab;
import com.sergey.root.orderkkt.Preferes;
import com.sergey.root.orderkkt.R;
import com.sergey.root.orderkkt.Yandex;
import com.sergey.root.orderkkt.YandexService;
import com.sergey.root.orderkkt.tcp.TcpDeviceSearchActivity;
import com.yandex.authsdk.YandexAuthException;
import com.yandex.authsdk.YandexAuthOptions;
import com.yandex.authsdk.YandexAuthSdk;
import com.yandex.authsdk.YandexAuthToken;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettigsFragment extends Fragment {


    @BindView(R.id.yandex_login)
    Button mYandexLogin;
    Unbinder unbinder;
    @BindView(R.id.contact_name)
    EditText mContactName;
    @BindView(R.id.KKT_type)
    Spinner mKKTType;
    @BindView(R.id.ip_adress)
    EditText mIpAdress;
    @BindView(R.id.port)
    EditText mPort;
    @BindView(R.id.test_kkt)
    Button mTestKkt;
    @BindView(R.id.shtrih)
    LinearLayout mShtrih;
    @BindView(R.id.search_kkt)
    Button mSearchKkt;
    @BindView(R.id.shtrih_blutooth)
    ConstraintLayout shtrihBlutooth;
    @BindView(R.id.search_blutooh)
    Button mSearchBlutooh;
    private YandexAuthSdk mSdk;

    public SettigsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            try {
                final YandexAuthToken token = mSdk.extractToken(resultCode, data);
                if (token != null) {
                    Preferes.setToken(getActivity(), token.getValue());
                    new YandexTask().execute();
                }
            } catch (YandexAuthException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == TcpDeviceSearchActivity.REQUEST_SEARCH_TCP_DEVICE) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle extrax = data.getExtras();
                if (extrax == null) {
                    return;
                }
                String adress = extrax.getString("Address");
                if (adress == null) return;
                int ind = adress.indexOf(":");
                mIpAdress.setText(adress.substring(0, ind));
                mPort.setText(adress.substring(ind + 1, adress.length()));

            }
        }
        if(requestCode == 3 ){
            if(resultCode == Activity.RESULT_OK){
                Bundle extrax = data.getExtras();
                if (extrax == null) {
                    return;
                }
                String address = extrax.getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                Preferes.setIP_adres(getActivity(),address);
                new KKTTask().execute();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0 && grantResults.length == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivityForResult(mSdk.createLoginIntent(getActivity(), null), 1);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @OnTextChanged(R.id.contact_name)
    void onText(CharSequence s) {
        Preferes.setCuryer(getActivity(), s.toString());
    }

    @OnTextChanged(R.id.ip_adress)
    void OnIp(CharSequence s) {
        Preferes.setIP_adres(getActivity(), s.toString());
    }

    @OnTextChanged(R.id.port)
    void OnPort(CharSequence s) {
        Preferes.setPort(getActivity(), s.toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settigs, container, false);
        unbinder = ButterKnife.bind(this, view);
        getActivity().setTitle("Настройки");
        mContactName.setText(Preferes.getCuryer(getActivity()));
        mSdk = new YandexAuthSdk(new YandexAuthOptions(getActivity(), true));
        mKKTType.setSelection(Preferes.getSelect(getActivity()));
        mIpAdress.setText(Preferes.getIP_adres(getActivity()));
        mPort.setText(Preferes.getPort(getActivity()));
        mKKTType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Preferes.setSelect(getActivity(), position);
                switch (position) {
                    case 0:
                        mShtrih.setVisibility(View.GONE);
                        shtrihBlutooth.setVisibility(View.GONE);
                        break;
                    case 1:
                        mShtrih.setVisibility(View.VISIBLE);
                        Preferes.setPortType(getActivity(),"2");
                        Preferes.setPortClass(getActivity(),"com.shtrih.fiscalprinter.port.SocketPort");
                        shtrihBlutooth.setVisibility(View.GONE);
                        break;
                    case 2:
                        mShtrih.setVisibility(View.GONE);
                        Preferes.setPortType(getActivity(),"3");
                        Preferes.setPortClass(getActivity(),"com.shtrih.fiscalprinter.port.BluetoothPort");
                        shtrihBlutooth.setVisibility(View.VISIBLE);
                        break;
                    default:
                        mShtrih.setVisibility(View.GONE);
                        shtrihBlutooth.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.yandex_login)
    void onClick() {
        if (mContactName.getText().length() > 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                } else {
                    startActivityForResult(mSdk.createLoginIntent(getActivity(), null), 1);
                }
            } else {
                startActivityForResult(mSdk.createLoginIntent(getActivity(), null), 1);
            }


        } else {
            Toast.makeText(getActivity(), "Не введено имя курьера", Toast.LENGTH_LONG).show();
        }
    }

    private class YandexTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Yandex yandex = new Yandex(getActivity());
            yandex.createDirectory();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getActivity(), "Настройка завершена", Toast.LENGTH_LONG).show();
            YandexService.setServiceAlarm(getActivity(), true);
        }
    }

    @OnClick(R.id.test_kkt)
    void Test() {
        new KKTTask().execute();
    }

    @OnClick(R.id.search_blutooh)
    void Bultoof(){
        Intent intent = new Intent(getActivity(),DeviceListActivity.class);
        startActivityForResult(intent,3);
    }

    @OnClick(R.id.search_kkt)
    void Searh() {
        Intent intent = new Intent(getActivity(), TcpDeviceSearchActivity.class);
        startActivityForResult(intent, TcpDeviceSearchActivity.REQUEST_SEARCH_TCP_DEVICE);
    }

    private class KKTTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            OrderLab.getInstance(getActivity()).connect();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (!OrderLab.getInstance(getActivity()).getError()) {
                Toast.makeText(getActivity(), OrderLab.getInstance(getActivity()).Devices(), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), OrderLab.getInstance(getActivity()).getDescription(), Toast.LENGTH_LONG).show();
            }
        }
    }


}

package com.sergey.root.orderkkt.Fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.sergey.root.orderkkt.Preferes;
import com.sergey.root.orderkkt.R;
import com.sergey.root.orderkkt.Yandex;
import com.sergey.root.orderkkt.YandexService;
import com.yandex.authsdk.YandexAuthException;
import com.yandex.authsdk.YandexAuthOptions;
import com.yandex.authsdk.YandexAuthSdk;
import com.yandex.authsdk.YandexAuthToken;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
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
    }


    @OnTextChanged(R.id.contact_name)
    void onText(CharSequence s) {
        Preferes.setCuryer(getActivity(), s.toString());
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
        mKKTType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0: mShtrih.setVisibility(View.GONE);
                        break;
                    case 1: mShtrih.setVisibility(View.VISIBLE);
                    break;
                    default:mShtrih.setVisibility(View.GONE);
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
        startActivityForResult(mSdk.createLoginIntent(getActivity(), null), 1);
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


}

package com.sergey.root.orderkkt.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sergey.root.orderkkt.R;

public class DialogCash extends DialogFragment {
    private static final String ARG_SUM = "summ";
    public static final String EXTRA_SUM="summ";
    public TextView textSum, TextTotal;
    public EditText textOpl;
    private double summ;

    public static DialogCash newIntens(double summ){
        DialogCash fragment = new DialogCash();
        Bundle bundle = new Bundle();
        bundle.putDouble(ARG_SUM,summ);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        summ = getArguments().getDouble(ARG_SUM);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.cash_nal,null);
        textSum = v.findViewById(R.id.textSum);
        TextTotal = v.findViewById(R.id.Sd);
        textOpl = v.findViewById(R.id.editTextOpl);
        textSum.setText(summ+" .руб");
        textOpl.setText("0");
        TextTotal.setText(-summ+" .руб");
        TextTotal.setTextColor(Color.parseColor("#ff0000"));
        textOpl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    return;
                }
                double opl = Double.parseDouble(s.toString())-summ;
                TextTotal.setText(opl+" . руб");
                if(opl<0){
                    TextTotal.setTextColor(Color.parseColor("#ff0000"));
                }
                else {
                    TextTotal.setTextColor(Color.parseColor("#00ff00"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return new AlertDialog.Builder(getActivity()).setView(v).setTitle("Оплата").setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                double opl = Double.parseDouble(textOpl.getText().toString());
                if(opl>=summ || opl == 0){
                    Intent intent = new Intent();
                    intent.putExtra(EXTRA_SUM,summ);
                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,intent);
                }

            }
        }).setNegativeButton(android.R.string.cancel,null).create();
    }
}

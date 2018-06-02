package com.sergey.root.orderkkt.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Spinner;

import com.sergey.root.orderkkt.R;

public class NoteDialogFragment extends DialogFragment {
    public static final String EXTRA_NOTE="codes";
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_note,null);
        final Spinner spinner = view.findViewById(R.id.note_value);
        return new AlertDialog.Builder(getActivity()).setView(view).setTitle("Укажите причину").setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String Value = spinner.getSelectedItem().toString();
                Intent intent = new Intent();
                intent.putExtra(EXTRA_NOTE,Value);
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,intent);
            }
        }).setNegativeButton(android.R.string.cancel,null).create();
    }
}

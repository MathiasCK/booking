package com.example.booking;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.fragment.app.DialogFragment;

public class MyDialogFragment extends DialogFragment {
    private String headerText;
    private String bodyText;
    
    public static MyDialogFragment newInstance(String headerText, String bodyText) {
        MyDialogFragment fragment = new MyDialogFragment();
        Bundle args = new Bundle();
        args.putString("headerText", headerText);
        args.putString("bodyText", bodyText);
        fragment.setArguments(args);
        
        return fragment;
    }
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        
        if (getArguments() != null) {
            headerText = getArguments().getString("headerText");
            bodyText = getArguments().getString("bodyText");
        }
        
        builder.setTitle(headerText);
        builder.setMessage(bodyText);
        builder.setPositiveButton("Close", (dialog, which) -> dialog.dismiss());
        
        return builder.create();
    }
}


package com.example.booking;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class Settings extends Fragment {
    View v;
    private static final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch messageSwitch;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_settings, container, false);
        
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
        }
        
        messageSwitch = v.findViewById(R.id.message_switch);
        
        messageSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    requestSendSmsPermission();
                    Toast.makeText(requireContext(), "SEND_SMS permission turned on", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "SEND_SMS permission turned off", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
        return v;
    }
    private boolean isSendSmsPermissionGranted() {
        return ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED;
    }
    private void requestSendSmsPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
        }
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == SEND_SMS_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(), "SEND_SMS permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), "SEND_SMS permission denied", Toast.LENGTH_SHORT).show();
                messageSwitch.setChecked(false);
            }
        }
    }
}

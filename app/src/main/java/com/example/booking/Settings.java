package com.example.booking;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class Settings extends Fragment {
    View v;
    private static final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;
    private EditText settings_phone;
    private EditText settings_message;
    Button button_send;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_settings, container, false);
    
        settings_phone = v.findViewById(R.id.settings_phone);
        settings_message = v.findViewById(R.id.settings_message);
        button_send = v.findViewById(R.id.settings_send);
        
        button_send.setOnClickListener(v -> sendMessage());
    
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
        }
        
        return v;
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == SEND_SMS_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(), "Permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), "Permission not granted", Toast.LENGTH_SHORT).show();
            }
        }
    }
    
    private void sendMessage() {
        String phoneNumber = settings_phone.getText().toString();
        String message = settings_message.getText().toString();
        if (!phoneNumber.isEmpty() && !message.isEmpty()) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            
            String msg = String.format("SMS sent to %s", phoneNumber);
            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
            
            Utils.clearSettingsFields(v);
        } else {
            Toast.makeText(requireContext(), "Du har ikke gitt tilatelse til å sende SMS", Toast.LENGTH_SHORT).show();
            button_send.setEnabled(false);
        }
    }
}

package com.example.booking;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    EditText message_input_time;
    SharedPreferences sharedPreferences;
    String currentTime;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_settings, container, false);
         sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", requireContext().MODE_PRIVATE);
         
         currentTime = this.sharedPreferences.getString("SEND_SMS_TIME_OF_DAY_HOUR", "06") + ":" +this.sharedPreferences.getString("SEND_SMS_TIME_OF_DAY_MINUTE", "06");
        
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
        }
        
        messageSwitch = v.findViewById(R.id.message_switch);
        message_input_time = v.findViewById(R.id.message_input_time);
        message_input_time.setText(currentTime);
        
        v.findViewById(R.id.settings_update).setOnClickListener(v -> updateSettings());
        
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
    
    public void updateSettings() {
        String input = message_input_time.getText().toString();
        
        if (input.equals(currentTime)) return;
        
        if(!Utils.isValidTime(input)) {
            Toast.makeText(requireContext(), "Tid format ikke gyldig", Toast.LENGTH_SHORT).show();
            return;
        }
    
        String[] parts = input.split(":");
    
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("SEND_SMS_TIME_OF_DAY_HOUR", parts[0]);
        editor.putString("SEND_SMS_TIME_OF_DAY_MINUTE", parts[1]);
    
        String msg = String.format("Time updated from %s to %s", currentTime, input);
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
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

package com.example.booking;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
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
    Switch message_switch;
    EditText message_input_time;
    EditText message_input_message;
    SharedPreferences sharedPreferences;
    String currentTime;
    String currentMessage;
    Button settings_update;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_settings, container, false);
         sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", requireContext().MODE_PRIVATE);
         
         currentTime = this.sharedPreferences.getString("SEND_SMS_TIME_OF_DAY_HOUR", "") + ":" +this.sharedPreferences.getString("SEND_SMS_TIME_OF_DAY_MINUTE", "");
         currentMessage = this.sharedPreferences.getString("DEFAULT_SMS", "");
    
        message_switch = v.findViewById(R.id.message_switch);
        message_input_time = v.findViewById(R.id.message_input_time);
        message_input_message = v.findViewById(R.id.message_input_message);
        settings_update = v.findViewById(R.id.settings_update);
        
        message_input_time.setText(currentTime);
        message_input_message.setText(currentMessage);
        settings_update.setOnClickListener(v -> updateSettings());
    
        boolean isSmsPermissionGranted = isSendSmsPermissionGranted();
    
        message_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    requestSmsPermission();
                    Toast.makeText(requireContext(), "SMS service skrudd på", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "SMS service skrudd av", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
        return v;
    }
    private boolean isSendSmsPermissionGranted() {
        int permissionStatus = ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.SEND_SMS);
        return permissionStatus == PackageManager.PERMISSION_GRANTED;
    }
    private void requestSmsPermission() {
        requestPermissions(new String[]{android.Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
    }
    
    public void updateSettings() {
        if (updateTime() || updateMessage()) {
            Toast.makeText(requireContext(), "Preferanser oppdatert", Toast.LENGTH_SHORT).show();
        }
    }
    
    public boolean updateMessage() {
        String input = message_input_message.getText().toString();
    
        if (input.equals(currentMessage)) return false;
    
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("DEFAULT_SMS", input);
        
        return true;
    }
    
    public boolean updateTime() {
        String input = message_input_time.getText().toString();
    
        if (input.equals(currentTime)) return false;
    
        if(!Utils.isValidTime(input)) {
            Toast.makeText(requireContext(), "Tid format ikke gyldig", Toast.LENGTH_SHORT).show();
            return false;
        }
    
        String[] parts = input.split(":");
    
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("SEND_SMS_TIME_OF_DAY_HOUR", parts[0]);
        editor.putString("SEND_SMS_TIME_OF_DAY_MINUTE", parts[1]);
        
        return true;
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        
        if (requestCode == SEND_SMS_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                message_switch.setChecked(true);
                initAlarmService();
            } else {
                Toast.makeText(requireContext(), "Du har ikke tilgang til å skru av SMS service", Toast.LENGTH_SHORT).show();
                message_switch.setChecked(false);
            }
        }
    }
    public void initAlarmService() {
        AlarmManager alarmManager = (AlarmManager) requireContext().getSystemService(Context.ALARM_SERVICE);
        
        Intent intent = new Intent(requireContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(requireContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
        
        long intervalMillis = 24 * 60 * 60 * 1000;
        long triggerAtMillis = System.currentTimeMillis() + intervalMillis;
        
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, triggerAtMillis, intervalMillis, pendingIntent);
    }

    
}

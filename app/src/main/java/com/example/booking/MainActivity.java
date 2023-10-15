package com.example.booking;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.booking.appointment.AddAppointment;
import com.example.booking.appointment.Appointments;
import com.example.booking.contact.AddContact;
import com.example.booking.contact.Contacts;

public class MainActivity extends AppCompatActivity {
    
    Contacts contacts = new Contacts();
    AddContact addContact = new AddContact();
    Appointments appointments = new Appointments();
    AddAppointment addAppointment = new AddAppointment();
    
    Settings settings = new Settings();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
        displayFragment(contacts);
        
        initNavLinks();
        initAlarmService();
    
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("SEND_SMS_TIME_OF_DAY_HOUR", "06");
        editor.putString("SEND_SMS_TIME_OF_DAY_MINUTE", "00");
        editor.putString("DEFAULT_SMS", "Du har en avtale!");
        editor.apply();
    }
    private void initNavLinks() {
        Button button_appointments = findViewById(R.id.button_appointments);
        Button button_add_appointment = findViewById(R.id.button_add_appointment);
        Button button_contacts = findViewById(R.id.button_contacts);
        Button button_add_contact = findViewById(R.id.button_add_contact);
        Button button_settings = findViewById(R.id.button_settings);
    
        button_appointments.setOnClickListener(view -> displayFragment(appointments));
        button_add_appointment.setOnClickListener(view -> displayFragment(addAppointment));
        button_contacts.setOnClickListener(view -> displayFragment(contacts));
        button_add_contact.setOnClickListener(view -> displayFragment(addContact));
        button_settings.setOnClickListener(view -> displayFragment(settings));
    }
    private void displayFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }
    
    private void initAlarmService() {
        // Get the AlarmManager service
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Create an intent to the AlarmReceiver
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        
        // Set the interval for the alarm (e.g., every 24 hours)
        long intervalMillis = 24 * 60 * 60 * 1000; // 24 hours
        long triggerAtMillis = System.currentTimeMillis() + intervalMillis;
        
        // Set the repeating alarm
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, triggerAtMillis, intervalMillis, pendingIntent);
    }
}
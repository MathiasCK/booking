package com.example.booking;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayFragment(R.layout.fragment_home);
        
        initNavLinks();
    }
    private void initNavLinks() {
        Button button_appointments = findViewById(R.id.button_appointments);
        Button button_add_appointment = findViewById(R.id.button_add_appointment);
        Button button_contacts = findViewById(R.id.button_contacts);
        Button button_add_contact = findViewById(R.id.button_add_contact);
    
        button_appointments.setOnClickListener(view -> displayFragment(R.layout.fragment_appointments));
        button_add_appointment.setOnClickListener(view -> displayFragment(R.layout.fragment_add_appointment));
        button_contacts.setOnClickListener(view -> displayFragment(R.layout.fragment_contacts));
        button_add_contact.setOnClickListener(view -> displayFragment(R.layout.fragment_add_contact));
    }
    private void displayFragment(int fragmentId) {
        Fragment newFragment = new Fragment(fragmentId);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
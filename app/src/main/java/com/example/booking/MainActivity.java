package com.example.booking;

import android.os.Bundle;
import android.widget.Button;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    
    BottomNavigationView bottomNavigation;
    Appointments appointments = new Appointments();
    AddAppointment addAppointment = new AddAppointment();
    Contacts contacts = new Contacts();
    AddContact addContact = new AddContact();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
        //bottomNavigation = findViewById(R.id.nav);
        
        
        Button button_appointments = findViewById(R.id.button_appointments);
        Button button_add_appointment = findViewById(R.id.button_add_appointment);
        Button button_contacts = findViewById(R.id.button_contacts);
        Button button_add_contact = findViewById(R.id.button_add_contact);
    
        button_appointments.setOnClickListener(view -> {
            Fragment newFragment = new Fragment(R.layout.fragment_appointments); // Create an instance of the new fragment
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        
            transaction.replace(R.id.fragment_container, newFragment);
        
            transaction.addToBackStack(null);
        
            transaction.commit();
        });
        button_add_appointment.setOnClickListener(view -> {
            Fragment newFragment = new Fragment(R.layout.fragment_add_appointment); // Create an instance of the new fragment
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.fragment_container, newFragment);

            transaction.addToBackStack(null);
            
            transaction.commit();
        });
    
        button_contacts.setOnClickListener(view -> {
            Fragment newFragment = new Fragment(R.layout.fragment_contacts); // Create an instance of the new fragment
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        
            transaction.replace(R.id.fragment_container, newFragment);
        
            transaction.addToBackStack(null);
        
            transaction.commit();
        });
        button_add_contact.setOnClickListener(view -> {
            Fragment newFragment = new Fragment(R.layout.fragment_add_contact); // Create an instance of the new fragment
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        
            transaction.replace(R.id.fragment_container, newFragment);
        
            transaction.addToBackStack(null);
        
            transaction.commit();
        });

    }
}
package com.example.booking;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

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
        
        
        Button button = findViewById(R.id.button);
    
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newFragment = new Fragment(R.layout.fragment_add_appointment); // Create an instance of the new fragment
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    
                transaction.replace(R.id.fragment_container, newFragment);
    
                transaction.addToBackStack(null);
                
                transaction.commit();
            }
        });

    }
}
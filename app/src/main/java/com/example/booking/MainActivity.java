package com.example.booking;

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
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
        displayFragment(contacts);
        
        initNavLinks();
    }
    private void initNavLinks() {
        Button button_appointments = findViewById(R.id.button_appointments);
        Button button_add_appointment = findViewById(R.id.button_add_appointment);
        Button button_contacts = findViewById(R.id.button_contacts);
        Button button_add_contact = findViewById(R.id.button_add_contact);
    
        button_appointments.setOnClickListener(view -> displayFragment(appointments));
        button_add_appointment.setOnClickListener(view -> displayFragment(addAppointment));
        button_contacts.setOnClickListener(view -> displayFragment(contacts));
        button_add_contact.setOnClickListener(view -> displayFragment(addContact));
    }
    private void displayFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }
}
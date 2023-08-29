package com.example.booking.appointment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.booking.DB;
import com.example.booking.R;
import com.example.booking.appointment.AppointmentDao;
import com.example.booking.contact.Contact;

import java.util.List;

public class Appointments extends Fragment {
    
    AppointmentDao appointmentDao;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contacts, container, false);
    
        DB db = Room.databaseBuilder(requireContext(), DB.class, "bookings").build();
        appointmentDao = db.appointmentDao();
    
        ListView showContacts = v.findViewById(R.id.contacts);
    
        List<Contact> contacts = appointmentDao.getAllContacts();
    
        ArrayAdapter<Contact> arrayAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, contacts);
    
        showContacts.setAdapter(arrayAdapter);
    
        return v;
    }
}

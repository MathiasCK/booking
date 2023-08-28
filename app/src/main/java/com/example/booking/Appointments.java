package com.example.booking;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class Appointments extends Fragment {
    
    DBHandler dbHelper;
    SQLiteDatabase db;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_appointments,container,false);
        
        dbHelper = new DBHandler((MainActivity) getActivity());
        
        db = dbHelper.getWritableDatabase();
        
        ListView showAppointments = v.findViewById(R.id.appointments);
        
        ArrayList<Appointment> appointments = (ArrayList<Appointment>) dbHelper.listAppointments(db);
        
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, appointments);
    
        showAppointments.setAdapter(arrayAdapter);
        
        return v;
    }
}

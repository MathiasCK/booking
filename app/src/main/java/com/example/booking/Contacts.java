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

import com.example.booking.contact.Contact;

import java.util.ArrayList;

public class Contacts extends Fragment {
    
    DBHandler dbHelper;
    SQLiteDatabase db;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contacts,container,false);
        
        dbHelper = new DBHandler( getActivity());
        
        db = dbHelper.getWritableDatabase();
        
        ListView showContacts = v.findViewById(R.id.contacts);
        
        ArrayList<Contact> contacts = (ArrayList<Contact>) dbHelper.listContacts(db);
        
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, contacts);
    
        showContacts.setAdapter(arrayAdapter);
        
        return v;
    }
    
    
}

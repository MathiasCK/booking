package com.example.booking.contact;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.booking.DB;
import com.example.booking.R;
import com.example.booking.Utils;


import java.util.List;

public class Contacts extends Fragment {
    
    ContactDao contactDao;
    ListView showContacts;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contacts, container, false);

        DB db = Room.databaseBuilder(requireContext(), DB.class, "bookings").build();
        contactDao = db.contactDao();

        showContacts = v.findViewById(R.id.contacts);
    
        try {
            new LoadContactsAsyncTask().execute();
        } catch (Exception e) {
            Utils.showCustomDialog(getChildFragmentManager(), "Error", e.getMessage());
        }
    
        return v;
    }
    
    private class LoadContactsAsyncTask extends AsyncTask<Void, Void, List<Contact>> {
        @Override
        protected List<Contact> doInBackground(Void... voids) {
            return contactDao.getAllContacts();
        }
        
        @Override
        protected void onPostExecute(List<Contact> contacts) {
            ContactAdapter customAdapter = new ContactAdapter(requireContext(), R.layout.contact_row, contacts, contactDao, getChildFragmentManager());
            showContacts.setAdapter(customAdapter);
        }
    }
}

package com.example.booking.contact;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.booking.DB;
import com.example.booking.MyDialogFragment;
import com.example.booking.R;
import com.example.booking.Utils;

public class AddContact extends Fragment {
    
    EditText name;
    EditText phone;
    EditText id;
    View v;
    ContactDao contactDao;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_add_contact, container, false);
        DB db = Room.databaseBuilder(requireContext(), DB.class, "bookings").build();
        contactDao = db.contactDao();
    
        v.findViewById(R.id.button_add_contact).setOnClickListener(v13 -> addContact());
        
        initTextFields();
        return v;
    }
    
    private void initTextFields() {
        this.name = v.findViewById(R.id.input_name);
        this.phone = v.findViewById(R.id.input_phone);
        this.id = v.findViewById(R.id.input_id);
    }
    
    private void addContact() {
        try {
            String name = this.name.getText().toString();
            String phone = this.phone.getText().toString();
            
            if (name.isEmpty() || phone.isEmpty()) {
                throw new Exception("Alle felt må fylles ut!");
            }
    
            Contact contact = new Contact(name, phone);
            Utils.validateContactFields(contact);
    
            new AddContactAsyncTask().execute(contact);
    
            Utils.clearContactFields(v);
            
            Utils.showCustomDialog(getChildFragmentManager(), "Success", "Kontakt opprettet!");
        } catch (Exception e) {
            Utils.showCustomDialog(getChildFragmentManager(), "Error", e.getMessage());
        }
    }
    
    private class AddContactAsyncTask extends AsyncTask<Contact, Void, Void> {
        @Override
        protected Void doInBackground(Contact... contacts) {
            contactDao.insert(contacts[0]);
            return null;
        }
    }
}

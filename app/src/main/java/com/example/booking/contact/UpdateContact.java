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
import com.example.booking.R;
import com.example.booking.Utils;

public class UpdateContact extends Fragment {
    
    EditText contactName;
    EditText contactPhone;
    View v;
    ContactDao contactDao;
    Contact contact;
    
    public UpdateContact(Contact contact) {
        this.contact = contact;
    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_update_contact, container, false);
        DB db = Room.databaseBuilder(requireContext(), DB.class, "bookings").build();
        contactDao = db.contactDao();
        
        initControls();
        
        return v;
    }
    
    private void initControls() {
        Button button_update_contact = v.findViewById(R.id.button_update_contact);
        button_update_contact.setOnClickListener(v12 -> updateContact());
        
        this.contactName = v.findViewById(R.id.input_name);
        this.contactName.setText(this.contact.name);
        
        this.contactPhone = v.findViewById(R.id.input_phone);
        this.contactPhone.setText(this.contact.phone);
    }
    
    private void updateContact() {
        String name = this.contactName.getText().toString();
        String phone = this.contactPhone.getText().toString();
        long _ID = this.contact.get_ID();
        
        Contact contact = new Contact(_ID, name, phone);
        Utils.validateContactFields(contact);
        
        new UpdateContactAsyncTask().execute(contact);
    
        Utils.clearContactFields(v);
    }
    
    private class UpdateContactAsyncTask extends AsyncTask<Contact, Void, Void> {
        @Override
        protected Void doInBackground(Contact... contacts) {
            contactDao.update(contacts[0]);
            return null;
        }
    }

}

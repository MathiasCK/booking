package com.example.booking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

public class AddContact extends Fragment {
    
    EditText name;
    EditText phone;
    EditText id;
    Button button_add_contact;
    Button button_update_contact;
    Button button_delete_contact;
    View v;
    ContactDao contactDao;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_add_contact, container, false);
        DB db = Room.databaseBuilder(requireContext(), DB.class, "bookings").build();
        contactDao = db.contactDao();
        
        initButtons();
        initTextFields();
        
        return v;
    }
    
    private void initButtons() {
        button_add_contact = v.findViewById(R.id.button_add_contact);
        button_update_contact = v.findViewById(R.id.button_update_contact);
        button_delete_contact = v.findViewById(R.id.button_delete_contact);
    
        button_add_contact.setOnClickListener(v13 -> addContact());
        button_update_contact.setOnClickListener(v12 -> updateContact());
        button_delete_contact.setOnClickListener(v1 -> deleteContact());
    }
    
    private void initTextFields() {
        this.name = v.findViewById(R.id.input_name);
        this.phone = v.findViewById(R.id.input_phone);
        this.id = v.findViewById(R.id.input_id);
    }
    
    private void addContact() {
        String name = this.name.getText().toString();
        String phone = this.phone.getText().toString();
        
        Contact contact = new Contact(name, phone);
        Utils.validateContactFields(contact);
        
        contactDao.insert(contact);
    }
    
    private void updateContact() {
        String name = this.name.getText().toString();
        String phone = this.phone.getText().toString();
        long _ID = Long.parseLong(this.id.getText().toString());
        
        Contact contact = new Contact(_ID, name, phone);
        Utils.validateContactFields(contact);
        
        contactDao.update(contact);
    }
    
    private void deleteContact() {
        long id = Long.parseLong(this.id.getText().toString());
        Contact contact = new Contact();
        contact.set_ID(id);
        contactDao.delete(contact);
    }
}

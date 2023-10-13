package com.example.booking.appointment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.booking.DB;
import com.example.booking.R;
import com.example.booking.Utils;
import com.example.booking.contact.Contact;
import com.example.booking.contact.ContactDao;

import java.util.ArrayList;
import java.util.List;

public class AddAppointment extends Fragment {
    
    EditText date;
    EditText place;
    EditText message;
    EditText time;
    EditText id;
    Spinner member;
    View v;
    AppointmentDao appointmentDao;
    ContactDao contactDao;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_add_appointment, container, false);
        DB db = Room.databaseBuilder(requireContext(), DB.class, "bookings").build();
        appointmentDao = db.appointmentDao();
        contactDao = db.contactDao();
    
        v.findViewById(R.id.button_add_appointment).setOnClickListener(v13 -> addAppointment());
        
        initTextFields();
    
        return v;
    }
    
    private void initTextFields() {
        this.date = v.findViewById(R.id.input_date);
        this.place = v.findViewById(R.id.input_place);
        this.message = v.findViewById(R.id.input_message);
        this.time = v.findViewById(R.id.input_time);
        this.id = v.findViewById(R.id.input_id);
        this.member = v.findViewById(R.id.input_member);
        
        new RetrieveContactsAsyncTask().execute();
    }
    
    private void addAppointment() {
        try {
            String date = this.date.getText().toString();
            String place = this.place.getText().toString();
            String message = this.message.getText().toString();
            String time = this.time.getText().toString();
            String member = this.member.getSelectedItem().toString();
    
            if (date.isEmpty() || place.isEmpty() || message.isEmpty() || time.isEmpty()) {
                throw new Exception("Alle felt må fylles ut!");
            }
    
            Appointment appointment = new Appointment(place, message, date, time, member);
            Utils.validateAppointmentFields(appointment);
    
            new AddAppointmentAsync().execute(appointment);
    
            Utils.clearAppointmentFields(v);
            
            Utils.showCustomDialog(getChildFragmentManager(), "Success", "Avtale opprettet!");
        } catch (Exception e) {
            Utils.showCustomDialog(getChildFragmentManager(), "Error", e.getMessage());
        }
    }
    
    private class AddAppointmentAsync extends AsyncTask<Appointment, Void, Void> {
        @Override
        protected Void doInBackground(Appointment... appointments) {
            appointmentDao.insert(appointments[0]);
            return null;
        }
    }
    
    private class RetrieveContactsAsyncTask extends AsyncTask<Void, Void, List<Contact>> {
        @Override
        protected List<Contact> doInBackground(Void... voids) {
            return contactDao.getAllContacts();
        }
    
        protected void onPostExecute(List<Contact> contactList) {
            super.onPostExecute(contactList);
            if (contactList != null) {
                
                List<String> contactNames = new ArrayList<>();
                for (Contact contact : contactList) {
                    contactNames.add(contact.getName());
                }
                
                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, contactNames);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                member.setAdapter(adapter);
            }
        }
    }
    
    private class UpdateAppointmentAsyncTask extends AsyncTask<Appointment, Void, Void> {
        @Override
        protected Void doInBackground(Appointment... appointments) {
            appointmentDao.update(appointments[0]);
            return null;
        }
    }
}

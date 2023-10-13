package com.example.booking.appointment;

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

public class AddAppointment extends Fragment {
    
    EditText date;
    EditText place;
    EditText message;
    EditText time;
    EditText id;
    EditText member;
    View v;
    AppointmentDao appointmentDao;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_add_appointment, container, false);
        DB db = Room.databaseBuilder(requireContext(), DB.class, "bookings").build();
        appointmentDao = db.appointmentDao();
    
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
    }
    
    private void addAppointment() {
        String date = this.date.getText().toString();
        String place = this.place.getText().toString();
        String message = this.message.getText().toString();
        String time = this.time.getText().toString();
        String member = this.member.getText().toString();
        
        Appointment appointment = new Appointment(place, message, date, time, member);
        Utils.validateAppointmentFields(appointment);
    
        new AddAppointmentAsync().execute(appointment);
    
        Utils.clearAppointmentFields(v);
    }
    
    private class AddAppointmentAsync extends AsyncTask<Appointment, Void, Void> {
        @Override
        protected Void doInBackground(Appointment... appointments) {
            appointmentDao.insert(appointments[0]);
            return null;
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

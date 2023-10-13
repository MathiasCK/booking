package com.example.booking.appointment;

import android.os.AsyncTask;
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

import com.example.booking.DB;
import com.example.booking.R;
import com.example.booking.Utils;

public class UpdateAppointment extends Fragment {
    
    EditText date;
    EditText place;
    EditText message;
    EditText time;
    EditText member;
    AppointmentDao appointmentDao;
    View v;
    Appointment appointment;
    public UpdateAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_update_appointment, container, false);
        DB db = Room.databaseBuilder(requireContext(), DB.class, "bookings").build();
        appointmentDao = db.appointmentDao();
        
        initControls();
        
        return v;
    }
    
    private void initControls() {
        Button button_update_appointment = v.findViewById(R.id.button_update_appointment);
        button_update_appointment.setOnClickListener(v12 -> updateAppointment());
        
        this.date = v.findViewById(R.id.input_date);
        this.date.setText(appointment.getDate());
        
        this.place = v.findViewById(R.id.input_place);
        this.place.setText(appointment.getPlace());
        
        this.message = v.findViewById(R.id.input_message);
        this.message.setText(appointment.getMessage());
        
        this.time = v.findViewById(R.id.input_time);
        this.time.setText(appointment.getTime());
        
        this.member = v.findViewById(R.id.input_member);
        this.member.setText(appointment.getMember());
    }
    
    private void updateAppointment() {
        String date = this.date.getText().toString();
        String place = this.place.getText().toString();
        String message = this.message.getText().toString();
        String time = this.time.getText().toString();
        String member = this.member.getText().toString();
        
        Appointment appointment = new Appointment(this.appointment.get_ID(), place, message, date, time, member);
        Utils.validateAppointmentFields(appointment);
        
        new UpdateAppointmentAsyncTask().execute(appointment);
    
        Utils.clearAppointmentFields(v);
    }
    
    private class UpdateAppointmentAsyncTask extends AsyncTask<Appointment, Void, Void> {
        @Override
        protected Void doInBackground(Appointment... appointments) {
            appointmentDao.update(appointments[0]);
            return null;
        }
    }
}

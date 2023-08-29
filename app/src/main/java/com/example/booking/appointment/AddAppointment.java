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
    Button button_add_appointment;
    Button button_update_appointment;
    Button button_delete_appointment;
    View v;
    AppointmentDao appointmentDao;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_add_appointment, container, false);
        DB db = Room.databaseBuilder(requireContext(), DB.class, "bookings").build();
        appointmentDao = db.appointmentDao();
    
        initButtons();
        initTextFields();
    
        return v;
    }
    
    private void initButtons() {
        button_add_appointment = v.findViewById(R.id.button_add_appointment);
        button_update_appointment = v.findViewById(R.id.button_update_appointment);
        button_delete_appointment = v.findViewById(R.id.button_delete_appointment);
    
        button_add_appointment.setOnClickListener(v13 -> addAppointment());
        button_update_appointment.setOnClickListener(v12 -> updateAppointment());
        button_delete_appointment.setOnClickListener(v1 -> deleteAppointment());
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
    
        clearAppointmentFields();
    }
    
    private void updateAppointment() {
        String date = this.date.getText().toString();
        String place = this.place.getText().toString();
        String message = this.message.getText().toString();
        String time = this.time.getText().toString();
        String member = this.member.getText().toString();
        long _ID = Long.parseLong(this.id.getText().toString());
        
        Appointment appointment = new Appointment(_ID, place, message, date, time, member);
        Utils.validateAppointmentFields(appointment);
    
        new UpdateAppointmentAsyncTask().execute(appointment);
    
        clearAppointmentFields();
    }
    
    private void deleteAppointment() {
        long id = Long.parseLong(this.id.getText().toString());
        Appointment appointment = new Appointment();
        appointment.set_ID(id);
    
        new DeleteAppointmentAsyncTask().execute(id);
    
        clearAppointmentFields();
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
    
    private class DeleteAppointmentAsyncTask extends AsyncTask<Long, Void, Void> {
        @Override
        protected Void doInBackground(Long... ids) {
            Appointment appointment = new Appointment();
            appointment.set_ID(ids[0]);
            appointmentDao.delete(appointment);
            return null;
        }
    }
    
    private void clearAppointmentFields() {
        TextView date = v.findViewById(R.id.input_date);
        TextView place = v.findViewById(R.id.input_place);
        TextView message = v.findViewById(R.id.input_message);
        TextView time = v.findViewById(R.id.input_time);
        TextView id = v.findViewById(R.id.input_id);
        TextView member = v.findViewById(R.id.input_member);
        
        date.setText("");
        place.setText("");
        message.setText("");
        time.setText("");
        id.setText("");
        member.setText("");
    }
}

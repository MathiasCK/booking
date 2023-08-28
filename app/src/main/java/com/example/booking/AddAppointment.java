package com.example.booking;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
    DBHandler dbHandler;
    SQLiteDatabase db;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_appointment, container, false);
        dbHandler = new DBHandler(getActivity());
        db = dbHandler.getWritableDatabase();
        
        initButtons(v);
        initTextFields(v);
        
        return v;
    }
    
    private void initButtons(View v) {
        button_add_appointment = v.findViewById(R.id.button_add_appointment);
        button_update_appointment = v.findViewById(R.id.button_update_appointment);
        button_delete_appointment = v.findViewById(R.id.button_delete_appointment);
    
        button_add_appointment.setOnClickListener(v13 -> addAppointment());
        button_update_appointment.setOnClickListener(v12 -> updateAppointment());
        button_delete_appointment.setOnClickListener(v1 -> deleteAppointment());
    }
    
    private void addAppointment() {
        String date = this.date.getText().toString();
        String place = this.place.getText().toString();
        String message = this.message.getText().toString();
        String time = this.time.getText().toString();
        String member = this.member.getText().toString();
        Appointment appointment = new Appointment(place, message, date, time, member);
        dbHandler.addAppointment(db, appointment);
    }
    
    private void updateAppointment() {
        String date = this.date.getText().toString();
        String place = this.place.getText().toString();
        String message = this.message.getText().toString();
        String time = this.time.getText().toString();
        String member = this.member.getText().toString();
        long _ID = Long.parseLong(this.id.getText().toString());
        Appointment appointment = new Appointment(_ID, place, message, date, time, member);
        dbHandler.updateAppointment(db, appointment);
    }
    
    private void deleteAppointment() {
        long _ID = Long.parseLong(this.id.getText().toString());
        dbHandler.deleteAppointment(db, _ID);
    }
    private void initTextFields(View v) {
        this.date = v.findViewById(R.id.input_date);
        this.place = v.findViewById(R.id.input_place);
        this.message = v.findViewById(R.id.input_message);
        this.time = v.findViewById(R.id.input_time);
        this.id = v.findViewById(R.id.input_id);
        this.member = v.findViewById(R.id.input_member);
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }
}

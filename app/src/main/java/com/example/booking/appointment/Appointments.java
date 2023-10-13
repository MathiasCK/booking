package com.example.booking.appointment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.booking.DB;
import com.example.booking.R;
import com.example.booking.contact.Contact;
import com.example.booking.contact.ContactAdapter;

import java.util.List;

public class Appointments extends Fragment {
    
    AppointmentDao appointmentDao;
    ListView showAppointments;
    
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_appointments, container, false);
    
        DB db = Room.databaseBuilder(requireContext(), DB.class, "bookings").build();
        appointmentDao = db.appointmentDao();
    
        showAppointments = v.findViewById(R.id.appointments);
    
        new LoadAppointmentsAsyncTask().execute();
    
        return v;
    }
    
    private class LoadAppointmentsAsyncTask extends AsyncTask<Void, Void, List<Appointment>> {
        @Override
        protected List<Appointment> doInBackground(Void... voids) {
            return appointmentDao.getAllAppointments();
        }
        
        @Override
        protected void onPostExecute(List<Appointment> appointments) {
            AppointmentAdapter customAdapter = new AppointmentAdapter(requireContext(), R.layout.appointment_row, appointments, appointmentDao, getChildFragmentManager());
            showAppointments.setAdapter(customAdapter);
        }
    }
}

package com.example.booking.appointment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.booking.R;

import java.util.List;

public class AppointmentAdapter extends ArrayAdapter<Appointment> {
    private final Context context;
    private final int resource;
    private final AppointmentDao appointmentDao;
    
    public AppointmentAdapter(Context context, int resource, List<Appointment> contacts, AppointmentDao appointmentDao) {
        super(context, resource, contacts);
        this.context = context;
        this.resource = resource;
        this.appointmentDao = appointmentDao;
    }
    
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }
        
        TextView appointment_date = convertView.findViewById(R.id.appointment_date);
        TextView appointment_time = convertView.findViewById(R.id.appointment_time);
        TextView appointment_place = convertView.findViewById(R.id.appointment_place);
        TextView appointment_message = convertView.findViewById(R.id.appointment_message);
        TextView appointment_members = convertView.findViewById(R.id.appointment_members);
    
        Button button_delete_appointment = convertView.findViewById(R.id.button_delete_appointment);
        Button button_update_appointment = convertView.findViewById(R.id.button_update_appointment);
    
        Appointment appointment = getItem(position);
        if (appointment != null) {
            appointment_date.setText("Dato: " + appointment.getDate());
            appointment_time.setText("Tid: " + appointment.getTime());
            appointment_place.setText("Sted: " + appointment.getPlace());
            appointment_message.setText("Melding: " + appointment.getMessage());
            appointment_members.setText("Personer: " + appointment.getMessage());
    
            button_delete_appointment.setOnClickListener(view -> {
                new DeleteContactAsyncTask().execute(appointment.get_ID());
                remove(appointment);
            });
    
            button_update_appointment.setOnClickListener(v -> {
                ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new UpdateAppointment(appointment))
                        .commit();
            });
        }
        
        return convertView;
    }
    
    @SuppressLint("StaticFieldLeak")
    private class DeleteContactAsyncTask extends AsyncTask<Long, Void, Void> {
        @Override
        protected Void doInBackground(Long... ids) {
            Appointment appointment = new Appointment();
            appointment.set_ID(ids[0]);
            appointmentDao.delete(appointment);
            return null;
        }
    }
    
}
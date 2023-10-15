package com.example.booking;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import com.example.booking.appointment.Appointment;
import com.example.booking.contact.Contact;

public class Utils {
    public static void validateAppointmentFields(Appointment appointment) {
        if (appointment == null) {
            throw new IllegalArgumentException("Invalid arguments: db or appointment is null");
        }
    
        if (appointment.getPlace() == null || appointment.getPlace().isEmpty() ||
                appointment.getMessage() == null || appointment.getMessage().isEmpty() ||
                appointment.getDate() == null || appointment.getTime() == null ||
                appointment.getMember() == null || appointment.getMember().isEmpty()) {
            throw new IllegalArgumentException("Appointment fields must not be empty or null");
        }
    }
    
    public static void validateContactFields(Contact contact) {
        if (contact == null) {
            throw new IllegalArgumentException("Invalid arguments: db or contact is null");
        }
        
        if (contact.getName() == null || contact.getName().isEmpty() ||
                contact.getPhone() == null || contact.getPhone().isEmpty()) {
            throw new IllegalArgumentException("Appointment fields must not be empty or null");
        }
    }
    
    public static void clearAppointmentFields(View v) {
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
    
    public static void clearContactFields(View v) {
        TextView name = v.findViewById(R.id.input_name);
        TextView phone = v.findViewById(R.id.input_phone);
        TextView id = v.findViewById(R.id.input_id);
        
        name.setText("");
        phone.setText("");
        id.setText("");
    }
    
    public static void clearSettingsFields(View v) {
        EditText settings_phone = v.findViewById(R.id.settings_phone);
        EditText settings_message = v.findViewById(R.id.settings_message);
        
        settings_phone.setText("");
        settings_message.setText("");
    }
    
    public static void showCustomDialog(FragmentManager fragmentManager, String headerText, String bodyText) {
        MyDialogFragment dialog = MyDialogFragment.newInstance(headerText, bodyText);
        dialog.show(fragmentManager, "CustomDialogFragment");
    }
}

package com.example.booking;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.example.booking.appointment.Appointment;
import com.example.booking.contact.Contact;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        
        date.setText("");
        place.setText("");
        message.setText("");
        time.setText("");
        id.setText("");
    }
    
    public static void clearContactFields(View v) {
        TextView name = v.findViewById(R.id.input_name);
        TextView phone = v.findViewById(R.id.input_phone);
        TextView id = v.findViewById(R.id.input_id);
        
        name.setText("");
        phone.setText("");
        id.setText("");
    }
    
    public static void showCustomDialog(FragmentManager fragmentManager, String headerText, String bodyText) {
        MyDialogFragment dialog = MyDialogFragment.newInstance(headerText, bodyText);
        dialog.show(fragmentManager, "CustomDialogFragment");
    }
    
    public static boolean isValidTime(String input) {
        Pattern pattern = Pattern.compile("^([01][0-9]|2[0-4]):([0-5][0-9])$");
        Matcher matcher = pattern.matcher(input);
        
        return matcher.matches();
    }
}

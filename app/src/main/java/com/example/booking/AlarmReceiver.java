package com.example.booking;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.telephony.SmsManager;

import androidx.core.app.ActivityCompat;

import com.example.booking.appointment.Appointment;
import com.example.booking.appointment.AppointmentDao;
import com.example.booking.contact.Contact;
import com.example.booking.contact.ContactDao;

import java.util.List;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        AppointmentDao appointmentDao = DB.getInstance(context).appointmentDao();
        ContactDao contactDao = DB.getInstance(context).contactDao();
        
        checkAppointmentsAndSendSMS(context, appointmentDao, contactDao);
    }
    
    private void checkAppointmentsAndSendSMS(Context context, AppointmentDao appointmentDao, ContactDao contactDao) {
        long currentTimeMillis = System.currentTimeMillis();
        
        List<Appointment> appointments = appointmentDao.getAllAppointments();
        List<Contact> contacts = contactDao.getAllContacts();
        
        for (Appointment appointment : appointments) {
            long appointmentTimeMillis = Long.parseLong(appointment.getTime());
            
            long timeDifferenceMillis = appointmentTimeMillis - currentTimeMillis;
            
            if (timeDifferenceMillis <= 24 * 60 * 60 * 1000 && timeDifferenceMillis > 0) {
                if (ActivityCompat.checkSelfPermission(context, ".DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION"
                ) == PackageManager.PERMISSION_GRANTED) {
                    
                        for (Contact contact : contacts) {
                            if (appointment.getMember().equals(contact.getName())) {
                                String phoneNumber = contact.getPhone();
                                sendSMS(context, phoneNumber, "Your appointment is less than 24 hours away. Don't forget!");
                                return;
                            }
                        }
                }
            }
        }
    }
    
    private void sendSMS(Context context, String phoneNumber, String message) {
        SmsManager smsManager = SmsManager.getDefault();
        PendingIntent sentPendingIntent = PendingIntent.getBroadcast(context, 0, new Intent("SMS_SENT"), PendingIntent.FLAG_IMMUTABLE);
        PendingIntent deliveredPendingIntent = PendingIntent.getBroadcast(context, 0, new Intent("SMS_DELIVERED"), PendingIntent.FLAG_IMMUTABLE);
        
        smsManager.sendTextMessage(phoneNumber, null, message, sentPendingIntent, deliveredPendingIntent);
    }
    
}

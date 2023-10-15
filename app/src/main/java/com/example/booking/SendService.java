package com.example.booking;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.telephony.SmsManager;

import androidx.annotation.Nullable;

import com.example.booking.appointment.Appointment;
import com.example.booking.appointment.AppointmentDao;
import com.example.booking.contact.Contact;
import com.example.booking.contact.ContactDao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SendService extends Service {
    private AppointmentDao appointmentDao;
    private ContactDao contactDao;
    private SharedPreferences sharedPreferences;
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        this.appointmentDao = DB.getInstance(this).appointmentDao();
        this.contactDao = DB.getInstance(this).contactDao();
        
        checkAppointmentsAndSendSMS();
        stopSelf();
        
        return START_NOT_STICKY;
    }
    
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    
    private void checkAppointmentsAndSendSMS() {
        long currentTimeMillis = System.currentTimeMillis();
        
        List<Appointment> appointments = appointmentDao.getAllAppointments();
        List<Contact> contacts = contactDao.getAllContacts();
        
        for (Appointment appointment : appointments) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Date date = null;
            
            try {
                date = sdf.parse(appointment.getTime());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
    
            long appointmentTimeMillis = date.getTime();
            long timeDifferenceMillis = appointmentTimeMillis - currentTimeMillis;
            
            if (timeDifferenceMillis <= 24 * 60 * 60 * 1000 && timeDifferenceMillis > 0) {
                for (Contact contact : contacts) {
                    if (appointment.getMember().equals(contact.getName())) {
                        String phoneNumber = contact.getPhone();
                        sendSMS(phoneNumber, this.sharedPreferences.getString("DEFAULT_SMS", ""));
                        return;
                    }
                }
            }
        }
    }
    
    private void sendSMS(String phoneNumber, String message) {
        SmsManager smsManager = SmsManager.getDefault();
        PendingIntent sentPendingIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_SENT"), PendingIntent.FLAG_IMMUTABLE);
        PendingIntent deliveredPendingIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_DELIVERED"), PendingIntent.FLAG_IMMUTABLE);
        
        smsManager.sendTextMessage(phoneNumber, null, message, sentPendingIntent, deliveredPendingIntent);
    }
}


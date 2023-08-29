package com.example.booking;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.booking.appointment.Appointment;
import com.example.booking.appointment.AppointmentDao;
import com.example.booking.contact.Contact;
import com.example.booking.contact.ContactDao;

@Database(entities = { Contact.class, Appointment.class}, version = 1)
public abstract class DB extends RoomDatabase {
    public abstract ContactDao contactDao();
    public abstract AppointmentDao appointmentDao();
    
    private static volatile DB instance;
    
    public static synchronized DB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            DB.class, "bookings")
                    .build();
        }
        return instance;
    }
}

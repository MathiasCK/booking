package com.example.booking;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class Utils {
    public static void validateAppointmentFields(SQLiteDatabase db, Appointment appointment) {
        if (db == null || appointment == null) {
            throw new IllegalArgumentException("Invalid arguments: db or appointment is null");
        }
    
        if (appointment.getPlace() == null || appointment.getPlace().isEmpty() ||
                appointment.getMessage() == null || appointment.getMessage().isEmpty() ||
                appointment.getDate() == null || appointment.getTime() == null ||
                appointment.getMember() == null || appointment.getMember().isEmpty()) {
            throw new IllegalArgumentException("Appointment fields must not be empty or null");
        }
    }
    
    public static void validateContactFields(SQLiteDatabase db, Contact contact) {
        if (db == null || contact == null) {
            throw new IllegalArgumentException("Invalid arguments: db or contact is null");
        }
        
        if (contact.getName() == null || contact.getName().isEmpty() ||
                contact.get_ID() == 0 || contact.getPhone() == null || contact.getPhone().isEmpty()) {
            throw new IllegalArgumentException("Appointment fields must not be empty or null");
        }
    }
    
    public static void dbInsert(SQLiteDatabase db, ContentValues values, String TABLE) {
        long newRowId = db.insert(TABLE, null, values);
    
        if (newRowId == -1) {
            throw new RuntimeException("Failed to insert values into the database");
        }
    }
}

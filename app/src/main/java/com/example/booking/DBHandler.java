package com.example.booking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    static String TABLE_CONTACTS = "Contacts";
    static String TABLE_APPOINTMENTS = "Appointments";
    static String KEY_PLACE = "Place";
    static String KEY_MESSAGE = "Message";
    static String KEY_DATE = "Date";
    static String KEY_TIME = "TIME";
    static String KEY_ID = "_ID";
    static String KEY_MEMBER = "Member";
    static String KEY_NAME = "Name";
    static String KEY_PHONE = "Phone";
    static int DATABASE_VERSION = 3;
    static String DATABASE_NAME = "Booking";
    
    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS = "CREATE TABLE " + TABLE_CONTACTS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_PHONE + " TEXT" + ")";
        String CREATE_APPOINTMENTS = "CREATE TABLE " + TABLE_APPOINTMENTS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_PLACE + " TEXT," + KEY_MESSAGE + " TEXT," + KEY_DATE + " TEXT, " + KEY_TIME + " TEXT," + KEY_MEMBER + " TEXT" + ")";
        Log.d("SQL", CREATE_CONTACTS);
        Log.d("SQL", CREATE_APPOINTMENTS);
        db.execSQL(CREATE_CONTACTS);
        db.execSQL(CREATE_APPOINTMENTS);
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPOINTMENTS);
        onCreate(db);
    }
    
    public void addContact(SQLiteDatabase db, Contact contact) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PHONE, contact.getPhone());
        db.insert(TABLE_CONTACTS, null, values);
    }
    
    public void addAppointments(SQLiteDatabase db, Appointment appointment) {
        ContentValues values = new ContentValues();
        values.put(KEY_PLACE, appointment.getPlace());
        values.put(KEY_MESSAGE, appointment.getMessage());
        values.put(KEY_DATE, appointment.getDate());
        values.put(KEY_TIME, appointment.getTime());
        values.put(KEY_MEMBER, appointment.getMember());
        db.insert(TABLE_APPOINTMENTS, null, values);
    }
    
    public List<Contact> listContacts(SQLiteDatabase db) {
        List<Contact> contacts = new ArrayList<Contact>();
        String selectQuery = "SELECT * FROM " + TABLE_CONTACTS;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) { do {
            Contact contact = new Contact();
            contact.set_ID(cursor.getLong(0));
            contact.setName(cursor.getString(1));
            contact.setPhone(cursor.getString(2));
            contacts.add(contact);
        } while (cursor.moveToNext()); } cursor.close();
        return contacts;
    }
    
    public List<Appointment> listAppointments(SQLiteDatabase db) {
        List<Appointment> appointments = new ArrayList<Appointment>();
        String selectQuery = "SELECT * FROM " + TABLE_APPOINTMENTS;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) { do {
            Appointment appointment = new Appointment();
            appointment.set_ID(cursor.getLong(0));;
            appointment.setPlace(cursor.getString(1));
            appointment.setMessage(cursor.getString(2));
            appointment.setDate(cursor.getString(3));
            appointment.setTime(cursor.getString(4));
            appointment.setMember(cursor.getString(4));
    
            appointments.add(appointment);
        } while (cursor.moveToNext()); } cursor.close();
        return appointments;
    }
    
    public void deleteContact(SQLiteDatabase db, Long id) {
        db.delete(TABLE_CONTACTS, KEY_ID + " =? ",
                new String[]{Long.toString(id)});
    }
    
    public void deleteAppointment(SQLiteDatabase db, Long id) {
        db.delete(TABLE_APPOINTMENTS, KEY_ID + " =? ",
                new String[]{Long.toString(id)});
    }
    
    public int updateContact(SQLiteDatabase db, Contact contact) {
        ContentValues values = new ContentValues();
        
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PHONE, contact.getPhone());
        
        return db.update(TABLE_CONTACTS, values, KEY_ID + "= ?", new String[]{String.valueOf(contact.get_ID())});
    }
    
    
    public int updateAppointment(SQLiteDatabase db, Appointment appointment) {
        ContentValues values = new ContentValues();
        
        values.put(KEY_PLACE, appointment.getPlace());
        values.put(KEY_MESSAGE, appointment.getMessage());
        values.put(KEY_DATE, appointment.getDate());
        values.put(KEY_TIME, appointment.getTime());
        values.put(KEY_MEMBER, appointment.getMember());
        
        return db.update(TABLE_APPOINTMENTS, values, KEY_ID + "= ?", new String[]{String.valueOf(appointment.get_ID())});
    }
}

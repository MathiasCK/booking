package com.example.booking;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AppointmentDao {
    @Insert
    void insert(Appointment appointment);
    
    @Update
    void update(Appointment appointment);
    
    @Delete
    void delete(Appointment appointment);
    
    @Query("SELECT * FROM contacts")
    List<Contact> getAllContacts();
}

package com.example.booking.contact;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contacts")
public class Contact {
    @PrimaryKey(autoGenerate = true)
    long _ID;
    @ColumnInfo(name = "name")
    String name;
    @ColumnInfo(name = "phone")
    String phone;
    
    public Contact(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
    
    public Contact(long _ID, String name, String phone) {
        this._ID = _ID;
        this.name = name;
        this.phone = phone;
    }
    
    public Contact() {
    }
    
    public long get_ID() {
        return this._ID;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getPhone() {
        return this.phone;
    }
    
    public void set_ID(long _ID) {
        this._ID = _ID;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    @NonNull
    @Override
    public String toString() {
        return
                "Id:" + _ID +
                        ", Name:" + name +
                        ", Phone:" + phone;
    }
}

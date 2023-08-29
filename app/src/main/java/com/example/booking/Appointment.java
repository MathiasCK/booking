package com.example.booking;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "appointments")
public class Appointment {
    @PrimaryKey(autoGenerate = true)
    long _ID;
    @ColumnInfo(name = "date")
    String date;
    @ColumnInfo(name = "place")
    String place;
    @ColumnInfo(name = "message")
    String message;
    @ColumnInfo(name = "time")
    String time;
    @ColumnInfo(name = "member")
    String member;
    
    public Appointment() {}
    
    public Appointment(long _ID, String date, String place, String message, String time, String member) {
        this._ID = _ID;
        this.date = date;
        this.place = place;
        this.message = message;
        this.time = time;
        this.member = member;
    }
    
    public Appointment(String date, String place, String message, String time, String member) {
        this.date = date;
        this.place = place;
        this.message = message;
        this.time = time;
        this.member = member;
    }
    
    public long get_ID() {
        return this._ID;
    }
    
    public String getDate() {
        return this.date;
    }
    
    public String getMember() {
        return this.member;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public String getPlace() {
        return this.place;
    }
    
    public String getTime() {
        return this.time;
    }
    
    public void set_ID(long _ID) {
        this._ID = _ID;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    
    public void setMember(String member) {
        this.member = member;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public void setPlace(String place) {
        this.place = place;
    }
    
    public void setTime(String time) {
        this.time = time;
    }
    
    @NonNull
    @Override
    public String toString() {
        return
                "Id:" + _ID +
                        ", Place:" + this.place +
                        ", Date:" + this.date +
                        ", Message:" + this.message +
                        ", Time:" + this.time +
                        ", Member:" + this.member;
    }
}

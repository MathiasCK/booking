package com.example.booking;
public class Contact {
    
    long _ID;
    String name;
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
    
    @Override
    public String toString() {
        return
                "Id:" + _ID +
                        ", Name:" + name +
                        ", Phone:" + phone;
    }
}

package com.example.booking;

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
}

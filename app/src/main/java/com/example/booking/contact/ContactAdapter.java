package com.example.booking.contact;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.booking.R;

import java.util.List;

public class ContactAdapter extends ArrayAdapter<Contact> {
    private Context context;
    private int resource;
    
    public ContactAdapter(Context context, int resource, List<Contact> contacts) {
        super(context, resource, contacts);
        this.context = context;
        this.resource = resource;
    }
    
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }
        
        TextView contactId = convertView.findViewById(R.id.contactId);
        TextView contactName = convertView.findViewById(R.id.contactName);
        TextView contactPhone = convertView.findViewById(R.id.contactPhone);
        Button deleteButton = convertView.findViewById(R.id.deleteButton);
        
        Contact contact = getItem(position);
        if (contact != null) {
            contactId.setText("ID: " + contact.get_ID());
            contactName.setText(contact.getName());
            contactPhone.setText(contact.getPhone());
    
            new AddContact.DeleteContactAsyncTask().execute(id);
        }
        
        return convertView;
    }
    
}
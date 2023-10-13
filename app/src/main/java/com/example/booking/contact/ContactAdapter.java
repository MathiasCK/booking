package com.example.booking.contact;

import android.annotation.SuppressLint;
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
    private final Context context;
    private final int resource;
    private final ContactDao contactDao;
    
    public ContactAdapter(Context context, int resource, List<Contact> contacts, ContactDao contactDao) {
        super(context, resource, contacts);
        this.context = context;
        this.resource = resource;
        this.contactDao = contactDao;
    }
    
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }
        
        TextView contact_id = convertView.findViewById(R.id.contact_id);
        TextView contact_name = convertView.findViewById(R.id.contact_name);
        TextView contact_phone = convertView.findViewById(R.id.contact_phone);
        Button button_delete_contact = convertView.findViewById(R.id.button_delete_contact);
        
        Contact contact = getItem(position);
        if (contact != null) {
            contact_id.setText("ID: " + contact.get_ID());
            contact_name.setText("Navn: " + contact.getName());
            contact_phone.setText("Tel: " + contact.getPhone());
    
            button_delete_contact.setOnClickListener(view -> {
                new DeleteContactAsyncTask().execute(contact.get_ID());
                remove(contact);
            });
        }
        
        return convertView;
    }
    
    @SuppressLint("StaticFieldLeak")
    private class DeleteContactAsyncTask extends AsyncTask<Long, Void, Void> {
        @Override
        protected Void doInBackground(Long... ids) {
            Contact contact = new Contact();
            contact.set_ID(ids[0]);
            contactDao.delete(contact);
            return null;
        }
    }
    
}
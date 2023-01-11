package com.example.mp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

//create adapter for the contact class that will serve as whats displayed in the recyclerview
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder>{

    Context context;
    List<Contact> contact_list;

    public ContactAdapter(Context context, List<Contact> contact_list) {
        this.context = context;
        this.contact_list = contact_list;
    }

    @NonNull
    @NotNull
    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        //put the name of the layout to get the view
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout_contacts, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ContactAdapter.ViewHolder holder, int position) {

        //set text in items in 'item_layout_contacts' layout file
        if (contact_list != null && contact_list.size() >0){
            Contact contact = contact_list.get(position);
            holder.contactemail_txt.setText(contact.getEmailaddress());
            holder.contactname_txt.setText(contact.getFirstname() + " " + contact.getLastname());
            //holder.contactstatus_txt.setText(contact.get);
            holder.contactlifetimevalue_txt.setText("$"+contact.getLifetimevalue().toString());
            holder.contactdatetime_txt.setText(contact.getDatejoined());


        }else{
            return;
        }
    }

    @Override
    public int getItemCount() {
        return contact_list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        //put the textview of the layout that wel be using for the adapter
        TextView contactemail_txt, contactname_txt, contactlifetimevalue_txt, contactdatetime_txt;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            contactemail_txt = itemView.findViewById(R.id.contactemail_txt);
            contactname_txt = itemView.findViewById(R.id.contactname_txt);
            contactlifetimevalue_txt = itemView.findViewById(R.id.contactlifetimevalue_txt);
            contactdatetime_txt = itemView.findViewById(R.id.contactdatetime_txt);
        }
    }
}

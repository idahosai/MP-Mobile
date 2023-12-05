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

public class EmailAdapter extends RecyclerView.Adapter<EmailAdapter.ViewHolder> {

    Context context;
    List<Email> email_list;

    public EmailAdapter(Context context, List<Email> email_list) {
        this.context = context;
        this.email_list = email_list;
    }

    @NonNull
    @NotNull
    @Override
    public EmailAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        //put the name of the layout to get the view
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull EmailAdapter.ViewHolder holder, int position) {
            //set text in items in 'item_layout' layout file
        if (email_list != null && email_list.size() >0){
            Email email = email_list.get(position);
            holder.id_txt.setText(email.getId().toString());
            holder.name_txt.setText(email.getName());
            holder.numberofcontactssento_txt.setText(email.getNumberofcontactssentto().toString());
            holder.dateofcreation_txt.setText(email.getDateofcreation());
            holder.subjecttitle_txt.setText(email.getSubjecttitle());
            holder.opens_txt.setText(email.getOpens().toString());
            //holder.datetime_txt.setText(email.getDateofcreation());

        }else{
            return;
        }
    }

    @Override
    public int getItemCount() {
        return email_list.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView id_txt, name_txt, numberofcontactssento_txt, dateofcreation_txt, subjecttitle_txt, opens_txt;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            //get id of items in 'item_layout' layout file
            id_txt = itemView.findViewById(R.id.id_txt);
            name_txt = itemView.findViewById(R.id.name_txt);
            numberofcontactssento_txt = itemView.findViewById(R.id.numberofcontactssento_txt);
            dateofcreation_txt = itemView.findViewById(R.id.dateofcreation_txt);
            subjecttitle_txt = itemView.findViewById(R.id.subjecttitle_txt);
            opens_txt = itemView.findViewById(R.id.opens_txt);
            //datetime_txt = itemView.findViewById(R.id.datetime_txt);
        }
    }
}

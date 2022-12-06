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
            holder.id_txt.setText(email.getId());
            holder.name_txt.setText(email.getName());
            holder.status_txt.setText(email.getStatus());
            holder.type_txt.setText(email.getType());
            holder.senttonumber_txt.setText(email.getNumberofcontactssentto());
            holder.stats_txt.setText(email.getAttachedstatsforautomation_id());
            holder.datetime_txt.setText(email.getDateofcreation());

        }else{
            return;
        }
    }

    @Override
    public int getItemCount() {
        return email_list.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView id_txt, name_txt, status_txt, type_txt, senttonumber_txt, stats_txt, datetime_txt;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            //get id of items in 'item_layout' layout file
            id_txt = itemView.findViewById(R.id.id_txt);
            name_txt = itemView.findViewById(R.id.name_txt);
            status_txt = itemView.findViewById(R.id.status_txt);
            type_txt = itemView.findViewById(R.id.type_txt);
            senttonumber_txt = itemView.findViewById(R.id.senttonumber_txt);
            stats_txt = itemView.findViewById(R.id.stats_txt);
            datetime_txt = itemView.findViewById(R.id.datetime_txt);
        }
    }
}

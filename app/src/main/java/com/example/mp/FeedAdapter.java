package com.example.mp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

class FeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

    public TextView txtTitle, txtPubDate, txtContent;
    public CheckBox checkBox;
    private ItemclickListener itemclickListener;

    public FeedViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);

        txtTitle = (TextView)itemView.findViewById(R.id.txtTitle);
        txtPubDate = (TextView)itemView.findViewById(R.id.txtPubDate);
        txtContent = (TextView)itemView.findViewById(R.id.txtContent);

        //set Event
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);

    }

    public void setItemclickListener(ItemclickListener itemclickListener) {
        this.itemclickListener = itemclickListener;
    }

    @Override
    public void onClick(View view) {

        itemclickListener.onClick(view, getAdapterPosition(), false);

    }

    @Override
    public boolean onLongClick(View view) {

        itemclickListener.onClick(view, getAdapterPosition(), true);

        return true;
    }
}

public class FeedAdapter extends RecyclerView.Adapter<FeedViewHolder>{


    private Rssobject rssobject;
    private Context mcontext;
    private LayoutInflater inflater;


    public FeedAdapter(Rssobject rssobject, Context mcontext) {
        this.rssobject = rssobject;
        this.mcontext = mcontext;
        inflater = LayoutInflater.from(mcontext);
    }

    @NonNull
    @NotNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.emailmakeritem_layout,parent,false);

        return new FeedViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FeedViewHolder holder, int position) {

        holder.txtTitle.setText(rssobject.getItems().get(position).getTitle());
        holder.txtPubDate.setText(rssobject.getItems().get(position).getPubDate());
        holder.txtContent.setText(rssobject.getItems().get(position).getContent());

        holder.setItemclickListener(new ItemclickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(!isLongClick){
                    if(holder.checkBox.isChecked()){
                        holder.checkBox.setChecked(false);
                        rssobject.getItems().get(position).setSelected(false);
                    }
                    else{
                        holder.checkBox.setChecked(true);
                        //change max number of post to display
                        rssobject.getItems().get(position).setSelected(true);
                    }

                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return rssobject.items.size();
    }
}

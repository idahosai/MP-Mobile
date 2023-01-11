package com.example.mp;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RecyclerCustomfeildAdapter extends RecyclerView.Adapter<RecyclerCustomfeildAdapter.ViewHolder>{

    Context context;
    ArrayList<Object> arrCustomfeilds;

    public RecyclerCustomfeildAdapter(Context context, ArrayList<Object> arrCustomfeilds) {
        this.context = context;
        this.arrCustomfeilds = arrCustomfeilds;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerCustomfeildAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        //put the name of the layout to get the view
        View view = LayoutInflater.from(context).inflate(R.layout.customfeild_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerCustomfeildAdapter.ViewHolder holder, int position) {

        //if (arrCustomfeilds != null && arrCustomfeilds.size() >0){

        Customfeild model = (Customfeild) arrCustomfeilds.get(position);
        holder.txtName.setText(model.name);
        holder.txtValue.setText(model.customfeildstringvalue);
        //this doesn't run for some reason
        holder.llrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.add_customfeild_item);

                EditText customfeildname = dialog.findViewById(R.id.customfeildname);
                EditText customfeildvalue = dialog.findViewById(R.id.customfeildvalue);
                Button btnAction_btn = dialog.findViewById(R.id.btnAction_btn);

                TextView txtTitle = dialog.findViewById(R.id.txtTitle);

                txtTitle.setText("Update Customfeild");

                btnAction_btn.setText("Update");

                //set the contents fo the popup layout
                customfeildname.setText(((Customfeild) arrCustomfeilds.get(position)).name);
                customfeildvalue.setText(((Customfeild) arrCustomfeilds.get(position)).customfeildstringvalue);

                btnAction_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name = "", value="";
                        if(!customfeildname.getText().toString().equals("")){
                            name = customfeildname.getText().toString();
                        }else{
                            Toast.makeText(context, "Please enter custom feild name",Toast.LENGTH_SHORT);
                        }
                        if(!customfeildvalue.getText().toString().equals("")){
                            value = customfeildvalue.getText().toString();
                        }else{
                            Toast.makeText(context, "Please enter custome feild value",Toast.LENGTH_SHORT);
                        }

                        Date datecalender = new Date();
                        System.out.println("**********************************************");
                        System.out.println(datecalender.getDate());
                        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String newestdate = formatter.format(datecalender.getDate());

                        //add as customfeild
                        arrCustomfeilds.set(position,new Customfeild(name,value, newestdate, newestdate));
                        notifyItemChanged(position);
                        dialog.dismiss();


                    }
                });
                //i need to have it show so i put this here
                dialog.show();

            }
        });

        //}
        //else{
        //    return;
        //}


        //for some reason the below code doesn't work.
        //il try to figure out how to fix this next

        holder.llrow.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setTitle("Delete Custom feild")
                        .setMessage("Are you sure you want to delete?")
                        .setIcon(R.drawable.ic_delete)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                arrCustomfeilds.remove(position);
                                notifyItemRemoved(position);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                //alert.setTitle("AlertDialogExample");
                alert.show();
                return true;
            }
        });



    }

    @Override
    public int getItemCount() {
        return arrCustomfeilds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtName;
        TextView txtValue;
        LinearLayout llrow;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            //get id of items in 'item_layout' layout file
            txtName = itemView.findViewById(R.id.txtCustomfeildName);
            txtValue = itemView.findViewById(R.id.txtCustomfeildValue);
            llrow = itemView.findViewById(R.id.llrow);
        }
    }

}

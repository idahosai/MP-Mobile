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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecyclerCustomfeildAdapter extends RecyclerView.Adapter<RecyclerCustomfeildAdapter.ViewHolder>{

    Context context;
    ArrayList<Customfeild> arrCustomfeilds;

    public RecyclerCustomfeildAdapter(Context context, ArrayList<Customfeild> arrCustomfeilds) {
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


                        //part to delete is below
                        //arrCustomfeilds.get(position).getId()
                        System.out.println("id of customfeild:" + model.id);
                        Retrofit retrofit = new Retrofit.Builder()
                                //has to have "http://" or it wont work
                                .baseUrl("http://mpmp-env49.eba-ecp2ssmp.us-east-2.elasticbeanstalk.com/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
                        //Double h = 9.60;
                        //pass the query
                        //Date datefromdatabase2 = new Date();
                        //Date datecalender2 = new Date();
                        //SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        //System.out.println(formatter2.format(datecalender2));
                        System.out.println("hereeeeeeeeeeee4");


                        Call<List<Customfeild>> call4 = jsonPlaceHolderApi.getCustomfeildApis(
                                model.id.toString(),name,value, newestdate, newestdate

                        );
                        call4.enqueue(new Callback<List<Customfeild>>() {
                            @Override
                            public void onResponse(Call<List<Customfeild>> call4, Response<List<Customfeild>> response) {
                                if(!response.isSuccessful()){
                                    try {
                                        System.out.println("Code: " + response.code() +""+ response.errorBody().string());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    return;
                                }
                                System.out.println("*");
                                //its a list of whatever is inside
                                List<Customfeild> customfeildApis = response.body();
                                System.out.println("************"+response.body().toString());
                                System.out.println("***********"+customfeildApis);
                                System.out.println("***********"+customfeildApis.size());
                                //if(customfeildApis.size() == 1) {
                                //    for (Customfeild customfeildApi : customfeildApis) {
                                //        String content = "";
                                //        content += "A name: " + customfeildApi.getName() + "\n";
                                //        content += "A dateofcreation: " + customfeildApi.getDateofcreation() + "\n";
                                //        content += "A lastcustomfeildupdate: " + customfeildApi.getLastcustomfeildupdate() + "\n\n";
                                //        arrCustomfeilds.add(new Customfeild(customfeildApi.getId(),customfeildApi.getName(),customfeildApi.getCustomfeildintvalue(),customfeildApi.getCustomfeildstringvalue(),customfeildApi.getDateofcreation(),customfeildApi.getLastcustomfeildupdate()));
                                //        System.out.println("***********" + content);
                                //    }
                                //}
                                for (Customfeild customfeildApi : customfeildApis) {
                                    String content = "";
                                    content += "A4 name: " + customfeildApi.getName() + "\n";
                                    content += "A4 dateofcreation: " + customfeildApi.getDateofcreation() + "\n";
                                    content += "A4 lastcustomfeildupdate: " + customfeildApi.getLastcustomfeildupdate() + "\n\n";
                                    //arrCustomfeilds.add(new Customfeild(customfeildApi.getId(),customfeildApi.getName(),customfeildApi.getCustomfeildintvalue(),customfeildApi.getCustomfeildstringvalue(),customfeildApi.getDateofcreation(),customfeildApi.getLastcustomfeildupdate()));
                                    System.out.println("***********" + content);
                                }

                            }
                            @Override
                            public void onFailure(Call<List<Customfeild>> call4, Throwable t) {

                                System.out.println("********"+t.getMessage());
                            }
                        });


                        //add as customfeild
                        arrCustomfeilds.set(position,new Customfeild(model.id,name,value, newestdate, newestdate));


                        //part to delete is above
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

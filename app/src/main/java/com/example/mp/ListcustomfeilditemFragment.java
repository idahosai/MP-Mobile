package com.example.mp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

public class ListcustomfeilditemFragment extends Fragment {

    EditText email_edttxt;
    EditText firstname_edttxt;
    EditText lastname_edttxt;
    EditText jobtitle_edttxt;
    EditText company_edttxt;
    EditText mobilephone_edttxt;
    EditText workphone_edttxt;
    EditText country_edttxt;
    EditText stateprovince_edttxt;
    EditText city_edttxt;
    EditText address_edttxt;
    EditText zippostalcode_edtxt;
    EditText website_edttxt;
    Button done_btn;


    //ArrayList<Object> arrCustomfeilds = new ArrayList<>();
    ArrayList<Customfeild> arrCustomfeilds = new ArrayList<>();
    RecyclerCustomfeildAdapter adapter;
    Button btnOpenDialog;
    RecyclerView recyclerView;

    String pk;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_listcustomfeilditem, container,false);

        email_edttxt = view.findViewById(R.id.email_edttxt);
        firstname_edttxt = view.findViewById(R.id.firstname_edttxt);
        lastname_edttxt = view.findViewById(R.id.lastname_edttxt);
        jobtitle_edttxt = view.findViewById(R.id.jobtitle_edttxt);
        company_edttxt = view.findViewById(R.id.company_edttxt);
        mobilephone_edttxt = view.findViewById(R.id.mobilephone_edttxt);
        workphone_edttxt = view.findViewById(R.id.workphone_edttxt);
        country_edttxt = view.findViewById(R.id.country_edttxt);
        stateprovince_edttxt = view.findViewById(R.id.stateprovince_edttxt);
        city_edttxt = view.findViewById(R.id.city_edttxt);
        address_edttxt = view.findViewById(R.id.address_edttxt);
        zippostalcode_edtxt = view.findViewById(R.id.zippostalcode_edtxt);
        website_edttxt = view.findViewById(R.id.website_edttxt);


        recyclerView = view.findViewById(R.id.recyclerCustomfeild);
        btnOpenDialog = view.findViewById(R.id.btnOpenDialog);

        done_btn = view.findViewById(R.id.done_btn);









        done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //save contact,save attachedcontact
                //save customfeild, save attachedcustomfeild(customfeild, contact), save attachedall(attachedcustomfeild,staff,)


                CheckSigninApi checkSigninApi= (CheckSigninApi) getArguments().getParcelable("thestaff");
                String content = "";
                content += "model: " + checkSigninApi.getModel() + "\n";
                content += "pk: " + checkSigninApi.getPk() + "\n";
                content += "fields: " + checkSigninApi.getFields().getUsername() + "\n\n";
                System.out.println("*****C******" + content);

                //i am going to pass this array to the api
                //arrCustomfeilds

                Retrofit retrofit = new Retrofit.Builder()
                        //has to have "http://" or it wont work
                        .baseUrl("http://mpmp-env22.eba-ecp2ssmp.us-east-2.elasticbeanstalk.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
                Double h = 9.60;
                //pass the query
                //Call<List<ContactApi>> call = jsonPlaceHolderApi.createContact(new Contact(h,"Jan 06,2022 6:01PM","update1@bell.net","update1","here"));

                Date datefromdatabase = new Date();
                Date datecalender = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                System.out.println(formatter.format(datecalender));
                System.out.println("hereeeeeeeeeeee");
                //System.out.println(datecalender.getMonth());
                //System.out.println(datecalender.getDate());
                /*
                Call<List<ContactApi>> call = jsonPlaceHolderApi.createContact(
                        arrCustomfeilds,formatter.format(datecalender),"",email_edttxt.getText().toString(),firstname_edttxt.getText().toString(),
                        lastname_edttxt.getText().toString(),jobtitle_edttxt.getText().toString(),
                        company_edttxt.getText().toString(),mobilephone_edttxt.getText().toString(),
                        workphone_edttxt.getText().toString(),country_edttxt.getText().toString(),
                        stateprovince_edttxt.getText().toString(),city_edttxt.getText().toString(),
                        address_edttxt.getText().toString(), zippostalcode_edtxt.getText().toString(),
                        website_edttxt.getText().toString(),"Manually"
                );
                */
                Call<List<ContactApi>> call = jsonPlaceHolderApi.createContact(
                        formatter.format(datecalender),"",email_edttxt.getText().toString(),firstname_edttxt.getText().toString(),
                        lastname_edttxt.getText().toString(),jobtitle_edttxt.getText().toString(),
                        company_edttxt.getText().toString(),mobilephone_edttxt.getText().toString(),
                        workphone_edttxt.getText().toString(),country_edttxt.getText().toString(),
                        stateprovince_edttxt.getText().toString(),city_edttxt.getText().toString(),
                        address_edttxt.getText().toString(), zippostalcode_edtxt.getText().toString(),
                        website_edttxt.getText().toString(),"Manually"
                );

                call.enqueue(new Callback<List<ContactApi>>() {
                    @Override
                    public void onResponse(Call<List<ContactApi>> call, Response<List<ContactApi>> response) {
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
                        List<ContactApi> contactApis = response.body();
                        System.out.println("************"+response.body().toString());
                        System.out.println("***********"+contactApis);
                        System.out.println("***********"+contactApis.size());
                        if(contactApis.size() == 1) {
                            for (ContactApi contactApi : contactApis) {
                                String content = "";
                                content += "model: " + contactApi.getModel() + "\n";
                                content += "pk: " + contactApi.getPk() + "\n";
                                pk = contactApi.getPk();
                                content += "fields: " + contactApi.getFields().getEmailaddress() + "\n\n";
                                System.out.println("***********" + content);
                                //ClassName details = new ClassName();
                                //Intent i = new Intent(LoginActivity.this , MainActivity.class);
                                //CheckSigninApi getValue8= (Rssobject) getArguments().getParcelable("staff");
                            }
                            //pass the object ver to the next age
                            //go to the next page
                            //Intent intentMain = new Intent(LoginActivity.this , MainActivity.class);
                            //startActivity(intentMain);
                        }

                        //here i do the new thing below
                        CheckSigninApi checkSigninApi= (CheckSigninApi) getArguments().getParcelable("thestaff");
                        for (Customfeild customfeildApi : arrCustomfeilds) {
                            //customfeildApi.getName();
                            //customfeildApi.getCustomfeildintvalue();
                            //customfeildApi.getCustomfeildstringvalue();
                            //customfeildApi.getDateofcreation();
                            //customfeildApi.getLastcustomfeildupdate();

                            checkSigninApi.getPk();
                            //String content = "";
                            //content += "model: " + checkSigninApi.getModel() + "\n";
                            //content += "pk: " + checkSigninApi.getPk() + "\n";
                            //content += "fields: " + checkSigninApi.getFields().getUsername() + "\n\n";
                            //System.out.println("*****C******" + content);
                            Retrofit retrofit = new Retrofit.Builder()
                                    //has to have "http://" or it wont work
                                    .baseUrl("http://mpmp-env22.eba-ecp2ssmp.us-east-2.elasticbeanstalk.com/")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
                            Double h = 9.60;
                            //pass the query
                            //Call<List<ContactApi>> call = jsonPlaceHolderApi.createContact(new Contact(h,"Jan 06,2022 6:01PM","update1@bell.net","update1","here"));
                            Date datefromdatabase = new Date();
                            Date datecalender = new Date();
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            System.out.println(formatter.format(datecalender));
                            System.out.println("hereeeeeeeeeeee");
                            //System.out.println(datecalender.getMonth());
                            //System.out.println(datecalender.getDate());

                            Call<List<ContactApi>> call2 = jsonPlaceHolderApi.createCustomfeild(
                                    pk,customfeildApi.getName(),customfeildApi.getCustomfeildintvalue(),customfeildApi.getCustomfeildstringvalue(),
                                    customfeildApi.getDateofcreation(),customfeildApi.getLastcustomfeildupdate(),checkSigninApi.getPk()
                            );
                            call2.enqueue(new Callback<List<ContactApi>>() {
                                @Override
                                public void onResponse(Call<List<ContactApi>> call2, Response<List<ContactApi>> response) {
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
                                    List<ContactApi> contactApis = response.body();
                                    System.out.println("************"+response.body().toString());
                                    System.out.println("***********"+contactApis);
                                    System.out.println("***********"+contactApis.size());
                                    if(contactApis.size() == 1) {
                                        for (ContactApi contactApi : contactApis) {
                                            String content = "";
                                            content += "A model: " + contactApi.getModel() + "\n";
                                            content += "A pk: " + contactApi.getPk() + "\n";
                                            content += "A fields: " + contactApi.getFields().getEmailaddress() + "\n\n";
                                            System.out.println("***********" + content);
                                        }
                                    }
                                }
                                @Override
                                public void onFailure(Call<List<ContactApi>> call2, Throwable t) {

                                    System.out.println("********"+t.getMessage());
                                }
                            });



                        }


                        //here i do the new thing above

                    }

                    @Override
                    public void onFailure(Call<List<ContactApi>> call, Throwable t) {

                        System.out.println("********"+t.getMessage());
                    }
                });










                Fragment fragment = new ImportmanuallyFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }});



        btnOpenDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create a dialog
                Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.add_customfeild_item);
                EditText customfeildname = dialog.findViewById(R.id.customfeildname);
                EditText customfeildvalue = dialog.findViewById(R.id.customfeildvalue);
                Button btnAction_btn = dialog.findViewById(R.id.btnAction_btn);

                btnAction_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //i will come back to this
                        String name = "", value="";

                        if(!customfeildname.getText().toString().equals("")){
                            name = customfeildname.getText().toString();


                        }else{
                            Toast.makeText(view.getContext(), "Please enter custome feild name",Toast.LENGTH_SHORT);


                        }
                        if(!customfeildvalue.getText().toString().equals("")){
                            value = customfeildvalue.getText().toString();

                        }else{
                            Toast.makeText(view.getContext(), "Please enter custome feild value",Toast.LENGTH_SHORT);


                        }
                        Date datecalender = new Date();
                        System.out.println("**********************************************");
                        System.out.println(datecalender.getDate());
                        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String newestdate = formatter.format(datecalender.getDate());

                        //add as customfeild
                        //arrCustomfeilds.add(new Customfeild(name,value, newestdate, newestdate));
                        arrCustomfeilds.add(new Customfeild(name,0, value,newestdate, newestdate));
                        adapter.notifyItemInserted(arrCustomfeilds.size()-1);
                        recyclerView.scrollToPosition(arrCustomfeilds.size()-1);

                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        //add the random feild contacts

        Date datecalender = new Date();
        System.out.println("**********************************************");
        System.out.println(datecalender.getDate());
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String newestdate2 = formatter.format(datecalender.getDate());

        //arrCustomfeilds.add(new Customfeild("shoe size","9",newestdate2,newestdate2));
        //arrCustomfeilds.add(new Customfeild("shirt size","32",newestdate2,newestdate2));
        //arrCustomfeilds.add(new Customfeild("pant size","31",newestdate2,newestdate2));

        Retrofit retrofit = new Retrofit.Builder()
                //has to have "http://" or it wont work
                .baseUrl("http://mpmp-env23.eba-ecp2ssmp.us-east-2.elasticbeanstalk.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        //Double h = 9.60;
        //pass the query
        //Date datefromdatabase2 = new Date();
        //Date datecalender2 = new Date();
        //SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //System.out.println(formatter2.format(datecalender2));
        System.out.println("hereeeeeeeeeeee3");


        Call<List<Customfeild>> call3 = jsonPlaceHolderApi.getCustomfeildApis();
        call3.enqueue(new Callback<List<Customfeild>>() {
            @Override
            public void onResponse(Call<List<Customfeild>> call3, Response<List<Customfeild>> response) {
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
                    content += "A id: " + customfeildApi.getId() + "\n";
                    content += "A dateofcreation: " + customfeildApi.getDateofcreation() + "\n";
                    content += "A lastcustomfeildupdate: " + customfeildApi.getLastcustomfeildupdate() + "\n\n";
                    arrCustomfeilds.add(new Customfeild(customfeildApi.getId(),customfeildApi.getName(),customfeildApi.getCustomfeildintvalue(),customfeildApi.getCustomfeildstringvalue(),customfeildApi.getDateofcreation(),customfeildApi.getLastcustomfeildupdate()));
                    System.out.println("***********" + content);
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
                adapter = new RecyclerCustomfeildAdapter(recyclerView.getContext(), arrCustomfeilds);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<List<Customfeild>> call3, Throwable t) {

                System.out.println("********"+t.getMessage());
            }
        });


        //arrCustomfeilds.add(new Customfeild("shoe size",0,"9",newestdate2,newestdate2));
        //arrCustomfeilds.add(new Customfeild("shirt size",0,"32",newestdate2,newestdate2));
        //arrCustomfeilds.add(new Customfeild("pant size",0,"31",newestdate2,newestdate2));

        //recyclerView.setHasFixedSize(true);

        //recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        //adapter = new RecyclerCustomfeildAdapter(recyclerView.getContext(), arrCustomfeilds);
        //recyclerView.setAdapter(adapter);


        return view;
    }
}

package com.example.mp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SubscribersFragment extends Fragment {

    Spinner signupdate_spn;
    Spinner between2dates_spn;
    EditText datewritten1_edttxt;
    EditText datewritten2_edttxt;
    EditText segmentname_edttxt;

    Button saveassegment_btn;
    Button search_btn;

    RecyclerView recycler_view_contacts;
    ContactAdapter adapter;

    private final String signupdatespinner[] = {"Sign up Date"};
    //private final String between2datesspinner[] = {"whenever", "between 2 dates"};
    private final String between2datesspinner[] = {"between 2 dates"};

    String signupdateselected;
    String between2datesselected;



    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        //don't forget to change the fragment id here to make it work
        View view = inflater.inflate(R.layout.fragment_subscribers, container,false);

        signupdate_spn = view.findViewById(R.id.signupdate_spn);
        between2dates_spn = view.findViewById(R.id.between2dates_spn);
        datewritten1_edttxt = view.findViewById(R.id.datewritten1_edttxt);
        datewritten2_edttxt = view.findViewById(R.id.datewritten2_edttxt);
        segmentname_edttxt = view.findViewById(R.id.segmentname_edttxt);

        saveassegment_btn = view.findViewById(R.id.saveassegment_btn);
        search_btn = view.findViewById(R.id.search_btn);


        saveassegment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save in segment table then save the segment in attachedsegment table
                //introtext_txt.setText(dayorweekselected + dayselected + segmentselected);

                Retrofit retrofit = new Retrofit.Builder()
                        //has to have "http://" or it wont work
                        .baseUrl("http://mpmp-env27.eba-ecp2ssmp.us-east-2.elasticbeanstalk.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

                System.out.println("hereeeeeeeeeeee5");

                Date datecalender = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                System.out.println(formatter.format(datecalender));

                Call<List<Segment>> call5 = jsonPlaceHolderApi.getSegmentApis(
                        segmentname_edttxt.getText().toString(), datewritten1_edttxt.getText().toString(),datewritten2_edttxt.getText().toString(),formatter.format(datecalender)

                );
                call5.enqueue(new Callback<List<Segment>>() {
                    @Override
                    public void onResponse(Call<List<Segment>> call5, Response<List<Segment>> response) {
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
                        List<Segment> segmentApis = response.body();
                        System.out.println("************"+response.body().toString());
                        System.out.println("***********"+segmentApis);
                        System.out.println("***********"+segmentApis.size());
                        //this should only have 1 lenth
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
                        for (Segment segmentApi : segmentApis) {
                            String content = "";
                            content += "A5 name: " + segmentApi.getName() + "\n";
                            content += "A5 dateofcreation: " + segmentApi.getDateofcreation() + "\n";
                            content += "A5 dateone: " + segmentApi.getDateone() + "\n\n";
                            //arrCustomfeilds.add(new Customfeild(customfeildApi.getId(),customfeildApi.getName(),customfeildApi.getCustomfeildintvalue(),customfeildApi.getCustomfeildstringvalue(),customfeildApi.getDateofcreation(),customfeildApi.getLastcustomfeildupdate()));
                            System.out.println("***********" + content);
                        }

                    }
                    @Override
                    public void onFailure(Call<List<Segment>> call5, Throwable t) {

                        System.out.println("********"+t.getMessage());
                    }
                });


            }
        });

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dateholder = datewritten1_edttxt.getText().toString();
                String dateholder2 = datewritten2_edttxt.getText().toString();
                //String sDate1="31/12/1998";
                Date date1= null;
                Date date2= null;
                try {
                    date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateholder);
                    date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateholder2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                System.out.println(dateholder+"\t"+date1 +"\n"+ dateholder2 + "\t"+date2);

                Retrofit retrofit = new Retrofit.Builder()
                        //has to have "http://" or it wont work
                        .baseUrl("http://mpmp-env27.eba-ecp2ssmp.us-east-2.elasticbeanstalk.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

                System.out.println("hereeeeeeeeeeee5");

                Date datecalender = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                System.out.println(formatter.format(datecalender));

                Call<List<Contact>> call7 = jsonPlaceHolderApi.getDateContactApis(
                        dateholder,dateholder2

                );
                call7.enqueue(new Callback<List<Contact>>() {
                    @Override
                    public void onResponse(Call<List<Contact>> call7, Response<List<Contact>> response) {
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
                        List<Contact> contactApis = response.body();
                        System.out.println("************"+response.body().toString());
                        System.out.println("***********"+contactApis);
                        System.out.println("***********"+contactApis.size());
                        //this should only have 1 lenth
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
                        List<Contact> contact_list2 = new ArrayList<>();
                        adapter.contact_list.clear();
                        for (Contact contactApi : contactApis) {
                            String content = "";
                            content += "A7 first name: " + contactApi.getFirstname() + "\n";
                            content += "A7 date joined: " + contactApi.getDatejoined() + "\n";
                            content += "A7 email: " + contactApi.getEmailaddress() + "\n\n";
                            //arrCustomfeilds.add(new Customfeild(customfeildApi.getId(),customfeildApi.getName(),customfeildApi.getCustomfeildintvalue(),customfeildApi.getCustomfeildstringvalue(),customfeildApi.getDateofcreation(),customfeildApi.getLastcustomfeildupdate()));
                            System.out.println("***********" + content);
                            contact_list2.add(new Contact(contactApi.getLifetimevalue(),contactApi.getDatejoined(),contactApi.getEmailaddress(),contactApi.getFirstname(),contactApi.getLastname()));
                            adapter.contact_list.add(new Contact(contactApi.getLifetimevalue(),contactApi.getDatejoined(),contactApi.getEmailaddress(),contactApi.getFirstname(),contactApi.getLastname()));

                        }

                        //adapter.contact_list.add(new Contact(h,"Jan 06,2022 6:01PM","update1@bell.net","update1","here"));
                        //adapter.contact_list.add(new Contact(t,"Jan 06, 2022 6:09PM","update2@bell.net","update2","here"));
                        adapter.notifyDataSetChanged();

                    }
                    @Override
                    public void onFailure(Call<List<Contact>> call7, Throwable t) {

                        System.out.println("********"+t.getMessage());
                    }
                });
                /*
                //if the current date is between the two provided dates
                Date datefromdatabase = new Date();

                System.out.println(datefromdatabase.after(date1));
                System.out.println(datefromdatabase.before(date2));
                if(datefromdatabase.after(date1) && datefromdatabase.before(date2)){
                    System.out.println("database date is after the date1 and before date2");
                    //get list to display in recyclerview below
                    List<Contact> contact_list2 = new ArrayList<>();
                    Double h = 9.60;
                    Double t = 34.80;
                    contact_list2.add(new Contact(h,"Jan 06,2022 6:01PM","update1@bell.net","update1","here"));
                    contact_list2.add(new Contact(t,"Jan 06, 2022 6:09PM","update2@bell.net","update2","here"));

                    //lets see if this works i just added it
                    adapter.contact_list.clear();
                    adapter.contact_list.add(new Contact(h,"Jan 06,2022 6:01PM","update1@bell.net","update1","here"));
                    adapter.contact_list.add(new Contact(t,"Jan 06, 2022 6:09PM","update2@bell.net","update2","here"));
                    adapter.notifyDataSetChanged();

                }
                */

                //introtext_txt.setText(dayorweekselected + dayselected + segmentselected);


            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, signupdatespinner);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, between2datesspinner);
        signupdate_spn.setAdapter(adapter);
        between2dates_spn.setAdapter(adapter2);

        signupdate_spn.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(view.getContext(), "you selected by item" + adapterView.getItemAtPosition(i), Toast.LENGTH_SHORT);

                        signupdateselected = (String) adapterView.getItemAtPosition(i);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                }

        );


        between2dates_spn.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(view.getContext(), "you selected by item" + adapterView.getItemAtPosition(i), Toast.LENGTH_SHORT);

                        between2datesselected = (String) adapterView.getItemAtPosition(i);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                }

        );

        recycler_view_contacts = view.findViewById(R.id.recycler_view_contacts);

        setRecyclerView2();


        return view;

    }

    private void setRecyclerView2(){
        recycler_view_contacts.setHasFixedSize(true);
        recycler_view_contacts.setLayoutManager(new LinearLayoutManager(recycler_view_contacts.getContext()));
        getList();
        //adapter = new ContactAdapter(recycler_view_contacts.getContext(), getList());
        //recycler_view_contacts.setAdapter(adapter);
    }

    /*
    private List<Contact> getList(){
        List<Contact> contact_list = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                //has to have "http://" or it wont work
                .baseUrl("http://mpmp-env25.eba-ecp2ssmp.us-east-2.elasticbeanstalk.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        System.out.println("hereeeeeeeeeeee4");


        Call<List<Contact>> call4 = jsonPlaceHolderApi.getContactApis();
        call4.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call4, Response<List<Contact>> response) {
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
                List<Contact> contactApis = response.body();
                System.out.println("************"+response.body().toString());
                System.out.println("***********"+contactApis);
                System.out.println("***********"+contactApis.size());

                for (Contact contactApi : contactApis) {
                    String content = "";
                    content += "A4 first name: " + contactApi.getFirstname() + "\n";
                    content += "A4 dateofcreation: " + contactApi.getDatejoined() + "\n";
                    content += "A4 last name: " + contactApi.getLastname() + "\n\n";
                    //arrCustomfeilds.add(new Customfeild(customfeildApi.getId(),customfeildApi.getName(),customfeildApi.getCustomfeildintvalue(),customfeildApi.getCustomfeildstringvalue(),customfeildApi.getDateofcreation(),customfeildApi.getLastcustomfeildupdate()));
                    contact_list.add(new Contact(contactApi.getStatus(),contactApi.getId(),contactApi.getLifetimevalue(),contactApi.getDatejoined(),contactApi.getNotes(),contactApi.getEmailaddress(), contactApi.getFirstname(), contactApi.getLastname(), contactApi.getJobtitle(), contactApi.getCompany(), contactApi.getMobilephone(), contactApi.getWorkphone(),contactApi.getCountry(),contactApi.getStateprovince(), contactApi.getCity(), contactApi.getAddress(), contactApi.getZip(), contactApi.getWebsite(), contactApi.getStopmethod(), contactApi.getConfirmquestionmark(), contactApi.getAddmethod(), contactApi.getSignupsource(), contactApi.getTotalreviewsleft(),contactApi.getLastemailratingdone()));
                    System.out.println("***********" + content);
                }


            }
            @Override
            public void onFailure(Call<List<Contact>> call4, Throwable t) {

                System.out.println("********"+t.getMessage());
            }
        });


        //Double h = 9.60;
        //Double t = 34.80;
        //contact_list.add(new Contact(h,"Aug 20,2022 2:01PM","iggy@bell.net","iggy","idahosa"));
        //contact_list.add(new Contact(t,"Sep 05, 2022 5:09PM","joe@bell.net","joe","samuel"));

        //loop through and add to the list

        return contact_list;
    }
    */

    private void getList(){
        List<Contact> contact_list = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                //has to have "http://" or it wont work
                .baseUrl("http://mpmp-env27.eba-ecp2ssmp.us-east-2.elasticbeanstalk.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        System.out.println("hereeeeeeeeeeee4");


        Call<List<Contact>> call4 = jsonPlaceHolderApi.getContactApis();
        call4.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call4, Response<List<Contact>> response) {
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
                List<Contact> contactApis = response.body();
                System.out.println("************"+response.body().toString());
                System.out.println("***********"+contactApis);
                System.out.println("***********"+contactApis.size());

                for (Contact contactApi : contactApis) {
                    String content = "";
                    content += "A4 first name: " + contactApi.getFirstname() + "\n";
                    content += "A4 dateofcreation: " + contactApi.getDatejoined() + "\n";
                    content += "A4 last name: " + contactApi.getLastname() + "\n\n";
                    //arrCustomfeilds.add(new Customfeild(customfeildApi.getId(),customfeildApi.getName(),customfeildApi.getCustomfeildintvalue(),customfeildApi.getCustomfeildstringvalue(),customfeildApi.getDateofcreation(),customfeildApi.getLastcustomfeildupdate()));
                    contact_list.add(new Contact(contactApi.getStatus(),contactApi.getId(),contactApi.getLifetimevalue(),contactApi.getDatejoined(),contactApi.getNotes(),contactApi.getEmailaddress(), contactApi.getFirstname(), contactApi.getLastname(), contactApi.getJobtitle(), contactApi.getCompany(), contactApi.getMobilephone(), contactApi.getWorkphone(),contactApi.getCountry(),contactApi.getStateprovince(), contactApi.getCity(), contactApi.getAddress(), contactApi.getZip(), contactApi.getWebsite(), contactApi.getStopmethod(), contactApi.getConfirmquestionmark(), contactApi.getAddmethod(), contactApi.getSignupsource(), contactApi.getTotalreviewsleft(),contactApi.getLastemailratingdone()));
                    System.out.println("***********" + content);
                }


                adapter = new ContactAdapter(recycler_view_contacts.getContext(), contact_list);
                recycler_view_contacts.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<List<Contact>> call4, Throwable t) {

                System.out.println("********"+t.getMessage());
            }
        });

}



}

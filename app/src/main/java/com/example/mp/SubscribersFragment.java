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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SubscribersFragment extends Fragment {

    Spinner signupdate_spn;
    Spinner between2dates_spn;
    EditText datewritten1_edttxt;
    EditText datewritten2_edttxt;

    Button saveassegment_btn;
    Button search_btn;

    RecyclerView recycler_view_contacts;
    ContactAdapter adapter;

    private final String signupdatespinner[] = {"Sign up Date"};
    private final String between2datesspinner[] = {"whenever", "between 2 dates"};

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

        saveassegment_btn = view.findViewById(R.id.saveassegment_btn);
        search_btn = view.findViewById(R.id.search_btn);


        saveassegment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save in segment table then save the segment in attachedsegment table
                //introtext_txt.setText(dayorweekselected + dayselected + segmentselected);
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
                    date1 = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(dateholder);
                    date2 = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(dateholder2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                System.out.println(dateholder+"\t"+date1 +"\n"+ dateholder2 + "\t"+date2);


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
        adapter = new ContactAdapter(recycler_view_contacts.getContext(), getList());
        recycler_view_contacts.setAdapter(adapter);
    }

    private List<Contact> getList(){
        List<Contact> contact_list = new ArrayList<>();
        Double h = 9.60;
        Double t = 34.80;
        contact_list.add(new Contact(h,"Aug 20,2022 2:01PM","iggy@bell.net","iggy","idahosa"));
        contact_list.add(new Contact(t,"Sep 05, 2022 5:09PM","joe@bell.net","joe","samuel"));


        return contact_list;
    }

}

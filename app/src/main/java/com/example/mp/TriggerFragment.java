package com.example.mp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TriggerFragment extends Fragment {


    private TextView introtext_txt;
    private EditText numberofarticles_edttxt;

    private Button checkselectedspinner_btn;

    private Spinner everydayorweek_spn;
    private Spinner daysofweek_spn;
    private EditText timeinput_edttxt;

    private CheckBox sendautomatically_chk;

    private Spinner segmentsdropdown_spn;

    private Button nextfromtrigger_btn;



    private final String dayorweek[] = {"Everyday", "Week"};
    private final String day[] = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    private String[] segments = {};
    //private String[] segments = {"New user", "Man", "Woman"};

    String dayorweekselected;
    String dayselected;
    String segmentselected;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_trigger, container,false);

        introtext_txt = view.findViewById(R.id.introtext_txt);

        checkselectedspinner_btn = view.findViewById(R.id.checkselectedspinner_btn);

        numberofarticles_edttxt = view.findViewById(R.id.numberofarticles_edttxt);

        everydayorweek_spn = view.findViewById(R.id.everydayorweek_spn);
        daysofweek_spn = view.findViewById(R.id.daysofweek_spn);
        timeinput_edttxt = view.findViewById(R.id.timeinput_edttxt);

        sendautomatically_chk = view.findViewById(R.id.sendautomatically_chk);

        segmentsdropdown_spn = view.findViewById(R.id.segmentsdropdown_spn);

        nextfromtrigger_btn = view.findViewById(R.id.nextfromtrigger_btn);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, dayorweek);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, day);

        everydayorweek_spn.setAdapter(adapter);
        daysofweek_spn.setAdapter(adapter2);

        CheckSigninApi checkSigninApi= (CheckSigninApi) getArguments().getParcelable("thestaff");

        //begin
        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(segments));
        Retrofit retrofit = new Retrofit.Builder()
                //has to have "http://" or it wont work
                .baseUrl("http://mpmp-env49.eba-ecp2ssmp.us-east-2.elasticbeanstalk.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        System.out.println("hereeeeeeeeeeee5");

        Date datecalender = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(formatter.format(datecalender));

        Call<List<Segment>> call6 = jsonPlaceHolderApi.getSegmentApis(checkSigninApi.getPk());
        call6.enqueue(new Callback<List<Segment>>() {
            @Override
            public void onResponse(Call<List<Segment>> call6, Response<List<Segment>> response) {
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


                    arrayList.add(segmentApi.getId().toString() +"."+segmentApi.getName());
                    segments = arrayList.toArray(segments);
                }
                ArrayAdapter<String> adapter3 = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, segments);
                segmentsdropdown_spn.setAdapter(adapter3);
            }
            @Override
            public void onFailure(Call<List<Segment>> call6, Throwable t) {

                System.out.println("********"+t.getMessage());
            }
        });
        //above



        everydayorweek_spn.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(view.getContext(), "you selected by item" + adapterView.getItemAtPosition(i), Toast.LENGTH_SHORT);

                        dayorweekselected = (String) adapterView.getItemAtPosition(i);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                }

        );

        daysofweek_spn.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(view.getContext(), "you selected by item" + adapterView.getItemAtPosition(i), Toast.LENGTH_SHORT);

                        dayselected = (String) adapterView.getItemAtPosition(i);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                }

        );


        segmentsdropdown_spn.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(view.getContext(), "you selected by item" + adapterView.getItemAtPosition(i), Toast.LENGTH_SHORT);

                        segmentselected = (String) adapterView.getItemAtPosition(i);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                }

        );


        checkselectedspinner_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                introtext_txt.setText(dayorweekselected + dayselected + segmentselected);


            }
        });


        nextfromtrigger_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                CheckSigninApi checkSigninApi= (CheckSigninApi) getArguments().getParcelable("thestaff");
                String content = "";
                content += "model: " + checkSigninApi.getModel() + "\n";
                content += "pk: " + checkSigninApi.getPk() + "\n";
                content += "fields: " + checkSigninApi.getFields().getUsername() + "\n\n";
                System.out.println("*****B******" + content);

                Fragment ldf = new EmailmakerFragment();
                Bundle args = new Bundle();
                args.putParcelable("thestaff",checkSigninApi);


                args.putString("number of articles", numberofarticles_edttxt.getText().toString());
                args.putString("send frequency", dayorweekselected);
                args.putString("days selected", dayselected);
                args.putString("subscriber segment",segmentselected);
                args.putString("time",timeinput_edttxt.getText().toString());
                args.putBoolean("send automatically",sendautomatically_chk.isChecked());
                ldf.setArguments(args);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, ldf);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                /*
                Fragment ldf = new EmailmakerFragment();
                Bundle args = new Bundle();
                args.putString("number of articles", numberofarticles_edttxt.getText().toString());
                args.putString("send frequency", dayorweekselected);
                args.putString("days selected", dayselected);
                args.putString("subscriber segment",segmentselected);
                args.putString("time",timeinput_edttxt.getText().toString());
                args.putBoolean("send automatically",sendautomatically_chk.isChecked());
                ldf.setArguments(args);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, ldf);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                */

            }
        });

        return view;
    }
}

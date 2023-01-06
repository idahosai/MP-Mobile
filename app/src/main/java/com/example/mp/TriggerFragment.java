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
    private final String segments[] = {"New user", "Man", "Woman"};

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
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, segments);
        everydayorweek_spn.setAdapter(adapter);
        daysofweek_spn.setAdapter(adapter2);
        segmentsdropdown_spn.setAdapter(adapter3);


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


            }
        });

        return view;
    }
}

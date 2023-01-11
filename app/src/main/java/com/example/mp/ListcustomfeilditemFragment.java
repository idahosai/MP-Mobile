package com.example.mp;

import android.app.Dialog;
import android.os.Bundle;
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

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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


    ArrayList<Object> arrCustomfeilds = new ArrayList<>();
    RecyclerCustomfeildAdapter adapter;
    Button btnOpenDialog;
    RecyclerView recyclerView;

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
                        arrCustomfeilds.add(new Customfeild(name,value, newestdate, newestdate));
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

        arrCustomfeilds.add(new Customfeild("shoe size","9","newestdate2","newestdate2"));
        arrCustomfeilds.add(new Customfeild("shirt size","32","newestdate2","newestdate2"));
        arrCustomfeilds.add(new Customfeild("pant size","31","newestdate2","newestdate2"));

        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        adapter = new RecyclerCustomfeildAdapter(recyclerView.getContext(), arrCustomfeilds);
        recyclerView.setAdapter(adapter);


        return view;
    }
}

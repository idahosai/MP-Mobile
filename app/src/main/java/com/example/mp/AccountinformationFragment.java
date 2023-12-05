package com.example.mp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AccountinformationFragment extends Fragment {

    EditText edt_firstname;
    EditText edt_lastname;
    EditText edt_username;

    Button btn_saveaccountinfo;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_accountinformation, container,false);


        edt_firstname = view.findViewById(R.id.edt_firstname);
        edt_lastname = view.findViewById(R.id.edt_lastname);
        edt_username = view.findViewById(R.id.edt_username);

        btn_saveaccountinfo = view.findViewById(R.id.btn_saveaccountinfo);


        CheckSigninApi checkSigninApi= (CheckSigninApi) getArguments().getParcelable("thestaff");
        String content = "";
        content += "model: " + checkSigninApi.getModel() + "\n";
        content += "pk: " + checkSigninApi.getPk() + "\n";
        content += "fields: " + checkSigninApi.getFields().getUsername() + "\n\n";
        System.out.println("*****B******" + content);

        edt_firstname.setText(checkSigninApi.getFields().getFirstname());
        edt_lastname.setText(checkSigninApi.getFields().getLastname());
        edt_username.setText(checkSigninApi.getFields().getUsername());

        btn_saveaccountinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckSigninApi checkSigninApi= (CheckSigninApi) getArguments().getParcelable("thestaff");
                String content = "";
                content += "model: " + checkSigninApi.getModel() + "\n";
                content += "pk: " + checkSigninApi.getPk() + "\n";
                content += "fields: " + checkSigninApi.getFields().getUsername() + "\n\n";
                System.out.println("*****B******" + content);





                Retrofit retrofit2 = new Retrofit.Builder()
                        //has to have "http://" or it wont work
                        .baseUrl("http://mpmp-env49.eba-ecp2ssmp.us-east-2.elasticbeanstalk.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                JsonPlaceHolderApi jsonPlaceHolderApi2 = retrofit2.create(JsonPlaceHolderApi.class);

                Call<Staff> call6 = jsonPlaceHolderApi2.getAccountInformationApis(
                        edt_firstname.getText().toString(),
                        edt_lastname.getText().toString(),
                        edt_username.getText().toString(),
                        checkSigninApi.getPk()

                );

                call6.enqueue(new Callback<Staff>() {
                    @Override
                    public void onResponse(Call<Staff> call6, Response<Staff> response) {
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
                        Staff contactApis = response.body();
                        System.out.println("************"+response.body().toString());
                        System.out.println("***********"+contactApis);
                        //System.out.println("***********"+contactApis.size());
                        //this should only have 1 lenth
                        //if(contactApis.size() == 1) {







                    }
                    @Override
                    public void onFailure(Call<Staff> call6, Throwable t) {

                        System.out.println("********"+t.getMessage());
                    }
                });





                /*
                Fragment ldf = new ListcustomfeilditemFragment();
                Bundle args = new Bundle();
                args.putParcelable("thestaff",checkSigninApi);
                ldf.setArguments(args);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, ldf);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                */

                /*
                Fragment fragment = new ListcustomfeilditemFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                */


            }});

        return view;
    }

}

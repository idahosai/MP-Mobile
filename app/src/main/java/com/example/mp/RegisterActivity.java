package com.example.mp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    private Button backsend_btn;
    private Button send_btn;
    private EditText username_txt;
    private EditText firstname_txt;
    private EditText lastname_txt;
    private EditText email_txt;
    private EditText registerationpassword_txt;
    private Spinner industry_spn;
    private TextView errormessage_txt;

    private final String industryspinner[] = {"Education","Fitness"};
    String industry_spnselected;
    //private Button register_btn;
    //private Button forgetpassword_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        backsend_btn = (Button) findViewById(R.id.backsend_btn);
        send_btn = findViewById(R.id.send_btn);
        username_txt = findViewById(R.id.username_txt);
        firstname_txt = findViewById(R.id.firstname_txt);
        lastname_txt = findViewById(R.id.lastname_txt);
        email_txt = findViewById(R.id.email_txt);
        registerationpassword_txt = findViewById(R.id.registerationpassword_txt);
        industry_spn = findViewById(R.id.industry_spn);
        errormessage_txt = findViewById(R.id.errormessage_txt);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, industryspinner);
        industry_spn.setAdapter(adapter);
        industry_spn.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(view.getContext(), "you selected by item" + adapterView.getItemAtPosition(i), Toast.LENGTH_SHORT);

                        industry_spnselected = (String) adapterView.getItemAtPosition(i);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                }

        );


        backsend_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                myIntent.putExtra("key", 5); //Optional parameters
                RegisterActivity.this.startActivity(myIntent);
                //createNewStudentAuth();
                //createNewStudentAccount(44);
            }
        });

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (industry_spn != null){

                    if(username_txt.length() < 20){

                        Retrofit retrofit = new Retrofit.Builder()
                                //has to have "http://" or it wont work
                                .baseUrl("http://mpmp-env49.eba-ecp2ssmp.us-east-2.elasticbeanstalk.com/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

                        Call<List<Contact>> call5 = jsonPlaceHolderApi.getIsRegisteredEmailApis(
                                email_txt.getText().toString()
                        );

                        call5.enqueue(new Callback<List<Contact>>() {
                            @Override
                            public void onResponse(Call<List<Contact>> call5, Response<List<Contact>> response) {
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
                                if(contactApis.size() == 1) {
                                    errormessage_txt.setText("Email Already Registered!!");
                                }
                                else{
                                    //check if email is not already registered then continue



                                    Retrofit retrofit2 = new Retrofit.Builder()
                                            //has to have "http://" or it wont work
                                            .baseUrl("http://mpmp-env49.eba-ecp2ssmp.us-east-2.elasticbeanstalk.com/")
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build();
                                    JsonPlaceHolderApi jsonPlaceHolderApi2 = retrofit2.create(JsonPlaceHolderApi.class);

                                    Call<Staff> call6 = jsonPlaceHolderApi2.getRegisterAccountApis(
                                            username_txt.getText().toString(),
                                    firstname_txt.getText().toString(),
                                    lastname_txt.getText().toString(),
                                    email_txt.getText().toString(),
                                    registerationpassword_txt.getText().toString(),
                                            industry_spnselected
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
                                            Intent myIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                                            myIntent.putExtra("key2", 6); //Optional parameters
                                            RegisterActivity.this.startActivity(myIntent);







                                        }
                                        @Override
                                        public void onFailure(Call<Staff> call6, Throwable t) {

                                            System.out.println("********"+t.getMessage());
                                        }
                                    });


                                }


                            }
                            @Override
                            public void onFailure(Call<List<Contact>> call5, Throwable t) {

                                System.out.println("********"+t.getMessage());
                            }
                        });





                    }
                    else{
                        errormessage_txt.setText("username must be under 20 characters");
                    }
                }
                else{
                    errormessage_txt.setText("must select Education or Fitness");
                }

            }
        });

    }
}

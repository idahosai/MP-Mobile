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

public class RegisteremailsubscriberActivity extends AppCompatActivity {


    private Button backsend_btn3;
    private Button send_btn3;
    private EditText username_txt3;
    private EditText firstname_txt3;
    private EditText lastname_txt3;
    private EditText email_txt3;
    private EditText registerationpassword_txt3;

    private TextView errormessage_txt3;


    //private Button register_btn;
    //private Button forgetpassword_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeremailsubscriber);


        backsend_btn3 = (Button) findViewById(R.id.backsend_btn3);
        send_btn3 = findViewById(R.id.send_btn3);
        username_txt3 = findViewById(R.id.username_txt3);
        firstname_txt3 = findViewById(R.id.firstname_txt3);
        lastname_txt3 = findViewById(R.id.lastname_txt3);
        email_txt3 = findViewById(R.id.email_txt3);
        registerationpassword_txt3 = findViewById(R.id.registerationpassword_txt3);

        errormessage_txt3 = findViewById(R.id.errormessage_txt3);





        backsend_btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(RegisteremailsubscriberActivity.this, LoginActivity.class);
                myIntent.putExtra("key", 5); //Optional parameters
                RegisteremailsubscriberActivity.this.startActivity(myIntent);
                //createNewStudentAuth();
                //createNewStudentAccount(44);
            }
        });

        send_btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if(username_txt3.length() < 20){

                    Retrofit retrofit = new Retrofit.Builder()
                            //has to have "http://" or it wont work
                            .baseUrl("http://mpmp-env50.eba-ecp2ssmp.us-east-2.elasticbeanstalk.com/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

                    Call<List<Contact>> call5 = jsonPlaceHolderApi.getIsRegisteredEmailForSubscriberApis(
                            email_txt3.getText().toString()
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

                                Retrofit retrofit2 = new Retrofit.Builder()
                                        //has to have "http://" or it wont work
                                        .baseUrl("http://mpmp-env50.eba-ecp2ssmp.us-east-2.elasticbeanstalk.com/")
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();
                                JsonPlaceHolderApi jsonPlaceHolderApi2 = retrofit2.create(JsonPlaceHolderApi.class);

                                Call<User> call6 = jsonPlaceHolderApi2.getRegisterEmailSubscriberAccountApis(
                                        username_txt3.getText().toString(),
                                        firstname_txt3.getText().toString(),
                                        lastname_txt3.getText().toString(),
                                        email_txt3.getText().toString(),
                                        registerationpassword_txt3.getText().toString()
                                );

                                call6.enqueue(new Callback<User>() {
                                    @Override
                                    public void onResponse(Call<User> call6, Response<User> response) {
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
                                        User userApis = response.body();
                                        System.out.println("************"+response.body().toString());
                                        System.out.println("***********"+userApis);
                                        //System.out.println("***********"+contactApis.size());
                                        //this should only have 1 lenth
                                        //if(contactApis.size() == 1) {
                                        Intent myIntent = new Intent(RegisteremailsubscriberActivity.this, LoginActivity.class);
                                        myIntent.putExtra("key2", 6); //Optional parameters
                                        RegisteremailsubscriberActivity.this.startActivity(myIntent);

                                    }
                                    @Override
                                    public void onFailure(Call<User> call6, Throwable t) {

                                        System.out.println("********"+t.getMessage());
                                    }
                                });

                            }
                            else{
                                //check if email is not already registered then continue

                                errormessage_txt3.setText("Email Is Not Of A Email Subscriber Currently!!");



                            }


                        }
                        @Override
                        public void onFailure(Call<List<Contact>> call5, Throwable t) {

                            System.out.println("********"+t.getMessage());
                        }
                    });





                }
                else{
                    errormessage_txt3.setText("username must be under 20 characters");
                }



            }
        });

    }
}

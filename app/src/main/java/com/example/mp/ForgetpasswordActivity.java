package com.example.mp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForgetpasswordActivity extends AppCompatActivity {

    private EditText emailrecover_txt;
    private Button sendrecoverpassword_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);

        emailrecover_txt = findViewById(R.id.emailrecover_txt);
        sendrecoverpassword_btn = findViewById(R.id.sendrecoverpassword_btn);


        sendrecoverpassword_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                Retrofit retrofit = new Retrofit.Builder()
                        //has to have "http://" or it wont work
                        .baseUrl("http://mpmp-env49.eba-ecp2ssmp.us-east-2.elasticbeanstalk.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);


                //pass the query
                Call<Staff> call = jsonPlaceHolderApi.getRecoverPasswordApis(emailrecover_txt.getText().toString());
                System.out.println("***********"+ emailrecover_txt.getText().toString());
                call.enqueue(new Callback<Staff>() {
                    @Override
                    public void onResponse(Call<Staff> call, Response<Staff> response) {
                        if(!response.isSuccessful()){
                            //message_txt.setText("Code: " + response.code());

                            return;
                        }
                        //its a list of whatever is inside
                        Staff checkSigninApis = response.body();

                        System.out.println("************"+response.body().toString());
                        System.out.println("***********"+checkSigninApis);
                        


                        Intent intentMain = new Intent(ForgetpasswordActivity.this , LoginActivity.class);
                        //pass the object ver to the next age
                        startActivity(intentMain);

                    }

                    @Override
                    public void onFailure(Call<Staff> call, Throwable t) {

                        System.out.println("***********//////////"+t.getMessage());
                        //message_txt.setText(t.getMessage());
                    }
                });


                //Intent myIntent = new Intent(MainActivity.this, RegisterActivity.class);
                //myIntent.putExtra("key", 5); //Optional parameters
                //MainActivity.this.startActivity(myIntent);

                //Intent intentMain = new Intent(LoginActivity.this , MainActivity.class);
                //startActivity(intentMain);


                //createNewStudentAuth();
                //createNewStudentAccount(44);

            }
        });







    }
}

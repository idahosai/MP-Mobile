package com.example.mp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText usernamelogin_txt;
    private EditText password_txt;
    private Button login_btn;
    private Button register_btn;
    private Button forgetpassword_btn;
    private TextView message_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernamelogin_txt = findViewById(R.id.usernamelogin_txt);

        password_txt = findViewById(R.id.password_txt);

        login_btn = (Button) findViewById(R.id.login_btn);

        register_btn = (Button) findViewById(R.id.register_btn);

        forgetpassword_btn = (Button) findViewById(R.id.forgetpassword_btn);

        message_txt = findViewById(R.id.message_txt);

        //Intent intent = new Intent(this, RegisterActivity.class);
        //startActivity(intent);

        forgetpassword_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent myIntent = new Intent(MainActivity.this, RegisterActivity.class);
                //myIntent.putExtra("key", 5); //Optional parameters
                //MainActivity.this.startActivity(myIntent);

                Intent intentMain = new Intent(LoginActivity.this , ForgetpasswordActivity.class);
                startActivity(intentMain);
                //createNewStudentAuth();
                //createNewStudentAccount(44);

            }
        });


        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent myIntent = new Intent(MainActivity.this, RegisterActivity.class);
                //myIntent.putExtra("key", 5); //Optional parameters
                //MainActivity.this.startActivity(myIntent);

                Intent intentMain = new Intent(LoginActivity.this , RegisterActivity.class);
                startActivity(intentMain);
                //createNewStudentAuth();
                //createNewStudentAccount(44);

            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                Retrofit retrofit = new Retrofit.Builder()
                        //has to have "http://" or it wont work
                        .baseUrl("http://mpmp-env44.eba-ecp2ssmp.us-east-2.elasticbeanstalk.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);


                //pass the query
                Call<List<CheckSigninApi>> call = jsonPlaceHolderApi.getCheckSigninApis(usernamelogin_txt.getText().toString(),password_txt.getText().toString());

                call.enqueue(new Callback<List<CheckSigninApi>>() {
                    @Override
                    public void onResponse(Call<List<CheckSigninApi>> call, Response<List<CheckSigninApi>> response) {
                        if(!response.isSuccessful()){
                            message_txt.setText("Code: " + response.code());

                            return;
                        }
                        //its a list of whatever is inside
                        List<CheckSigninApi> checkSigninApis = response.body();

                        System.out.println("************"+response.body().toString());
                        System.out.println("***********"+checkSigninApis);
                        System.out.println("***********"+checkSigninApis.size());

                        if(checkSigninApis.size() == 1) {

                            Intent intentMain = new Intent(LoginActivity.this , MainActivity.class);
                            for (CheckSigninApi checkSigninApi : checkSigninApis) {
                                String content = "";
                                content += "model: " + checkSigninApi.getModel() + "\n";
                                content += "pk: " + checkSigninApi.getPk() + "\n";
                                content += "fields: " + checkSigninApi.getFields().getUsername() + "\n\n";
                                System.out.println("***********" + content);



                                //ClassName details = new ClassName();
                                //Intent i = new Intent(LoginActivity.this , MainActivity.class);
                                intentMain.putExtra("staff", (Parcelable) checkSigninApi);


                                //CheckSigninApi getValue8= (Rssobject) getArguments().getParcelable("staff");



                            }


                            //pass the object ver to the next age


                            startActivity(intentMain);


                            //go to the next page
                            //Intent intentMain = new Intent(LoginActivity.this , MainActivity.class);
                            //startActivity(intentMain);
                        }

                    }

                    @Override
                    public void onFailure(Call<List<CheckSigninApi>> call, Throwable t) {

                        message_txt.setText(t.getMessage());
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

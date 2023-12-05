package com.example.mp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginemailsubscriberActivity extends AppCompatActivity {

    private EditText usernamelogin_txt2;
    private EditText password_txt2;
    private Button login_btn2;

    private Button btn_back2;

    private TextView message_txt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginemailsubscriber);

        usernamelogin_txt2 = findViewById(R.id.usernamelogin_txt2);

        password_txt2 = findViewById(R.id.password_txt2);

        login_btn2 = (Button) findViewById(R.id.login_btn2);

        message_txt2 = findViewById(R.id.message_txt2);

        btn_back2 = (Button) findViewById(R.id.btn_back2);


        btn_back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(LoginemailsubscriberActivity.this, LoginActivity.class);
                myIntent.putExtra("key", 5); //Optional parameters
                LoginemailsubscriberActivity.this.startActivity(myIntent);

            }
        });


        login_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                Retrofit retrofit = new Retrofit.Builder()
                        //has to have "http://" or it wont work
                        .baseUrl("http://mpmp-env50.eba-ecp2ssmp.us-east-2.elasticbeanstalk.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);


                //pass the query
                Call<List<User>> call = jsonPlaceHolderApi.getCheckSigninSubscriberApis(usernamelogin_txt2.getText().toString(),password_txt2.getText().toString());

                call.enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        if(!response.isSuccessful()){
                            message_txt2.setText("Code: " + response.code());

                            return;
                        }
                        //its a list of whatever is inside
                        List<User> userApis = response.body();

                        System.out.println("************"+response.body().toString());
                        System.out.println("***********"+userApis);
                        //System.out.println("***********"+checkSigninApis.size());

                        if(userApis.size() == 1) {

                            Intent intentMain = new Intent(LoginemailsubscriberActivity.this , SubscriberMain.class);
                            for (User uApi : userApis) {
                                String content = "";
                                //content += "model: " + uApi.getModel() + "\n";
                                //content += "pk: " + uApi.getPk() + "\n";
                                //content += "fields: " + uApi.getFields().getUsername() + "\n\n";
                                System.out.println("***********" + content);



                                //ClassName details = new ClassName();
                                //Intent i = new Intent(LoginActivity.this , MainActivity.class);
                                intentMain.putExtra("user", (Parcelable) uApi);


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
                    public void onFailure(Call<List<User>> call, Throwable t) {

                        message_txt2.setText(t.getMessage());
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

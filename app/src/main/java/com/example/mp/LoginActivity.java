package com.example.mp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private Button login_btn;
    private Button register_btn;
    private Button forgetpassword_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        login_btn = (Button) findViewById(R.id.login_btn);

        register_btn = (Button) findViewById(R.id.register_btn);

        forgetpassword_btn = (Button) findViewById(R.id.forgetpassword_btn);

        //Intent intent = new Intent(this, RegisterActivity.class);
        //startActivity(intent);


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
                //Intent myIntent = new Intent(MainActivity.this, RegisterActivity.class);
                //myIntent.putExtra("key", 5); //Optional parameters
                //MainActivity.this.startActivity(myIntent);

                Intent intentMain = new Intent(LoginActivity.this , MainActivity.class);
                startActivity(intentMain);
                //createNewStudentAuth();
                //createNewStudentAccount(44);

            }
        });

    }
}

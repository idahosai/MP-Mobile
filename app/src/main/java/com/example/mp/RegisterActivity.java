package com.example.mp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private Button resgistersend_btn;
    //private Button register_btn;
    //private Button forgetpassword_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        resgistersend_btn = (Button) findViewById(R.id.resgistersend_btn);



        resgistersend_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                myIntent.putExtra("key", 5); //Optional parameters
                RegisterActivity.this.startActivity(myIntent);
                //createNewStudentAuth();
                //createNewStudentAccount(44);
            }
        });
    }
}

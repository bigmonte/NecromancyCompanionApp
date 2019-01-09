package com.portalmafia.androidcardgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TestActivity extends AppCompatActivity {

    EditText username, password;

    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        username = findViewById(R.id.username_input);
        password =  findViewById(R.id.password_input);
        loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick (View v)
            {
                if(username.getText().toString().equals("joao") && password.getText().toString().equals("joao")){
                    //Toast.makeText(getApplicationContext(),"Login Success", Toast.LENGTH_LONG);
                    Intent openMainIntent = new Intent(TestActivity.this,
                            MainActivity.class);
                    startActivity(openMainIntent);
                }
            }
        });
    }
}

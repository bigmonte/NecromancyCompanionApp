package com.portalmafia.androidcardgame;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    Button button_logout;
    Typeface font;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        button_logout = findViewById(R.id.logout_button);


        font = Typeface.createFromAsset(getAssets(), "fonts/curse.ttf");

        button_logout.setTypeface(font);


        button_logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast toast = Toast.makeText(getApplicationContext(), "Logged out!", Toast.LENGTH_SHORT);
                toast.show();
                Intent openMainIntent = new Intent(SettingsActivity.this,
                        LoginActivity.class);
                startActivity(openMainIntent);


            }
        });
    }
}

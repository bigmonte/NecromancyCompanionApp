package com.portalmafia.androidcardgame;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ManageHatsActivity extends AppCompatActivity {

    Typeface font;

    Button unlockButton;
    TextView HelmetName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_hats);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        unlockButton = findViewById(R.id.button_unlockHelmet);
        HelmetName = findViewById(R.id.helmetNameTextView);

        font = Typeface.createFromAsset(getAssets(), "fonts/curse.ttf");

        unlockButton.setTypeface(font);
        HelmetName.setTypeface(font);

        unlockButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast toast = Toast.makeText(getApplicationContext(), "Helmet unlocked!", Toast.LENGTH_SHORT);
                toast.show();

            }
        });

    }
}

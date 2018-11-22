package com.portalmafia.androidcardgame;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;

public class PlayerInfoActivity extends AppCompatActivity {

    Class[] ActivitiesClass = new Class[2];
    Intent[] intents = new Intent[2];
    Typeface font;

    Button ManageHats, ManageSkills;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActivitiesClass[0] = ManageSkillsActivity.class;
        ActivitiesClass[1] = ManageHatsActivity.class;
        font = Typeface.createFromAsset(getAssets(), "fonts/curse.ttf");


        ManageSkills = findViewById(R.id.button_player_manageSkills);
        ManageHats = findViewById(R.id.button_player_manageHats);

        ManageSkills.setTypeface(font);
        ManageHats.setTypeface(font);

        ButtonActivityListener(ManageSkills, 0);
        ButtonActivityListener(ManageHats, 1);

    }

    private void ButtonActivityListener(Button buttonNews, final int activityIndex)
    {
        buttonNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeActivity(v,activityIndex );
            }
        });
    }

    public void ChangeActivity(View view, int index)
    {
        intents[index] = new Intent();
        intents[index].setClass(this, ActivitiesClass[index]);
        startActivity(intents[index]);
    }
}

package com.portalmafia.androidcardgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ManageSkillsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_skills);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}

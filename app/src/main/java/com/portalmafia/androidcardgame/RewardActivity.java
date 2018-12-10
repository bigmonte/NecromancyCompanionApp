package com.portalmafia.androidcardgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.Objects;

public class RewardActivity extends AppCompatActivity {

    Class[] ActivitiesClass = new Class[1];
    Intent[] intents = new Intent[1];

    ImageButton openRewardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        ActivitiesClass[0] = OpenedReward.class;

        openRewardButton = findViewById(R.id.OpenGiftImageButton);

        ButtonActivityListener(openRewardButton, 0);
    }

    private void ButtonActivityListener(ImageButton button, final int activityIndex)
    {
        button.setOnClickListener(new View.OnClickListener() {
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

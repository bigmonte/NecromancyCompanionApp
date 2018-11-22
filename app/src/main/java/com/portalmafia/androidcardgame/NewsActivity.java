package com.portalmafia.androidcardgame;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class NewsActivity extends AppCompatActivity {

    ImageButton articleButton;
    Class[] ActivitiesClass = new Class[1];
    Intent[] intents = new Intent[1];


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        articleButton = findViewById(R.id.button_openArticle);
        ActivitiesClass[0] = NewsArticleActivity.class;

        ButtonActivityListener(articleButton, 0);


    }

    private void ButtonActivityListener(ImageButton buttonNews, final int activityIndex)
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

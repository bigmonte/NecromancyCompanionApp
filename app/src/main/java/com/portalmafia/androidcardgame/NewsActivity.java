package com.portalmafia.androidcardgame;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;

public class NewsActivity extends AppCompatActivity {

    ImageButton articleButton;
    Class[] ActivitiesClass = new Class[1];
    Intent[] intents = new Intent[1];

    private WebView newsWebView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //articleButton = findViewById(R.id.button_openArticle);
        ActivitiesClass[0] = NewsArticleActivity.class;

        newsWebView = findViewById(R.id.newsWebView);
        newsWebView.setWebViewClient(new WebViewClient());
        newsWebView.loadUrl("http://necromancy.portalmafia.com/data/?cat=2");



    }

}

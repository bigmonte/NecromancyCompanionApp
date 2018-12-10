package com.portalmafia.androidcardgame;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.Random;

public class PlayerInfoActivity extends AppCompatActivity {

    Class[] ActivitiesClass = new Class[2];
    Intent[] intents = new Intent[2];

    NecromancyData necromancyData;

    Typeface font;

    Button ManageHats, ManageSkills;

    TextView BestRoundResultText, EnemiesKilledResultText, MoneyEarnedResultText;

    String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        necromancyData = ((NecromancyData) getApplicationContext());

        necromancyData.SetUsername("joao");
        necromancyData.SetPassword("joao");

        password = necromancyData.GetPassword();
        username = necromancyData.GetUsername();

        new GetDataTask().execute("http://10.0.2.2:8080/api/getData?username="+username+ "&password="+password);


        ActivitiesClass[0] = ManageSkillsActivity.class;
        ActivitiesClass[1] = ManageHatsActivity.class;
        font = Typeface.createFromAsset(getAssets(), "fonts/curse.ttf");

        // buttons
        ManageSkills = findViewById(R.id.button_player_manageSkills);
        ManageHats = findViewById(R.id.button_player_manageHats);

        // text Results

        BestRoundResultText = findViewById(R.id.BestRoundResult);
        EnemiesKilledResultText = findViewById(R.id.EnemiesKilledResult);
        MoneyEarnedResultText = findViewById(R.id.MoneyEarnedResult);


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

    class GetDataTask extends AsyncTask<String, Void, String> {
        //ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = new ProgressDialog(MainActivity.this);
//            progressDialog.setMessage(("loading data..."));
//            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {

            StringBuilder result = new StringBuilder();
            try {
                URL url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000); // milliseconds
                urlConnection.setConnectTimeout(10000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Content-Type", "application/json"); // set header
                urlConnection.connect();

                // Read data response from the server

                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result.append(line).append("\n");

                }

            } catch (IOException ex) {
                return ex.getMessage();

            }
            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {

            int BestRound = 0;
            int EnemiesKilled = 0;
            int MoneyEarned = 0;



            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONObject accountObj = jsonObject.getJSONObject("account");
                JSONObject dataObj = accountObj.getJSONObject("data");


                BestRound = dataObj.getInt("BestRound");
                EnemiesKilled = dataObj.getInt("EnemiesKilled");
                MoneyEarned = dataObj.getInt("MoneyEarned");

                necromancyData.SetBestRound(BestRound);
                necromancyData.SetEnemiesKilled(EnemiesKilled);
                necromancyData.SetMoneyEarned(MoneyEarned);

                BestRoundResultText.setText(String.format(Locale.US,"%d", BestRound));
                EnemiesKilledResultText.setText(String.format(Locale.US,"%d", EnemiesKilled));
                MoneyEarnedResultText.setText(String.format(Locale.US,"%d", MoneyEarned));


            } catch (Exception e) {

            }


            // set data response to textView

            // mResult.setText("status: " + necromancyData.getCoins()+" ok: " + coins);

//            if(progressDialog != null)
//            {
//                progressDialog.dismiss();
//            }

            super.onPostExecute(result);

        }
    }
}

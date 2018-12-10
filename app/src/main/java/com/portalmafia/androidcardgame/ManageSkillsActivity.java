package com.portalmafia.androidcardgame;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ManageSkillsActivity extends AppCompatActivity {

    NecromancyData necromancyData;
    Typeface font;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_skills);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        necromancyData = ((NecromancyData) getApplicationContext());
        font = Typeface.createFromAsset(getAssets(), "fonts/curse.ttf");


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

            int SkillDamage = 0;
            int SkillVelocity = 0;
            int SkillExtraHealth = 0;
            int SkillReloadTime = 0;



            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONObject accountObj = jsonObject.getJSONObject("account");
                JSONObject dataObj = accountObj.getJSONObject("data");


                SkillDamage = dataObj.getInt("SkillDamage");
                SkillVelocity = dataObj.getInt("SkillVelocity");
                SkillExtraHealth = dataObj.getInt("SkillExtraHealth");
                SkillReloadTime = dataObj.getInt("SkillReloadTime");

                necromancyData.SetSkillDamage(SkillDamage);
                necromancyData.SetSkillVelocity(SkillVelocity);
                necromancyData.SetSkillExtraHealth(SkillExtraHealth);
                necromancyData.SetSkillReloadTime(SkillReloadTime);




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

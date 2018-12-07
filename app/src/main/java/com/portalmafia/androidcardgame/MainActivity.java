package com.portalmafia.androidcardgame;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;



public class MainActivity extends Activity {

    private TextView mResult;
    NecromancyData necromancyData;
    private static ArrayList<Activity> activities = new ArrayList<>();
    Class[] ActivitiesClass = new Class[5];
    Intent[] intents = new Intent[5];

    ImageView cardLeftImage, cardRightImage;
    Button buttonNews, buttonPlayerInfo, buttonSettings, buttonEncyclopedia;
    ImageButton openGift;
    String gameCompetitionStatus = "competing";
    Typeface font;

    Random r;
    String username;
    String password;

    int maxScore = 7;
    int leftScore, rightScore = 0;
    AppManager singleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        activities.add(this);
        setContentView(R.layout.activity_main);

        necromancyData = ((NecromancyData) getApplicationContext());

        ActivitiesClass[0] = NewsActivity.class;
        ActivitiesClass[1] = PlayerInfoActivity.class;
        ActivitiesClass[2] = SettingsActivity.class;
        ActivitiesClass[3] = EncyclopediaActivity.class;
        ActivitiesClass[4] = RewardActivity.class;



        cardLeftImage = findViewById(R.id.cardLeftImage);
        cardRightImage = findViewById(R.id.cardRightImage);

        buttonNews = findViewById(R.id.buttoneNews);
        buttonPlayerInfo = findViewById(R.id.buttonPlayerInfo);
        buttonSettings = findViewById(R.id.buttonSettings);
        buttonEncyclopedia = findViewById(R.id.buttonEncyclopedia);
        openGift = findViewById(R.id.openGiftButton);

        font = Typeface.createFromAsset(getAssets(), "fonts/curse.ttf");


        r = new Random();

        buttonNews.setTypeface(font);
        buttonPlayerInfo.setTypeface(font);
        buttonSettings.setTypeface(font);
        buttonEncyclopedia.setTypeface(font);
        mResult = (TextView) findViewById(R.id.encyText);

        // get the variables from the singleton
        necromancyData.SetUsername("joao");
        necromancyData.SetPassword("joao");

        password = necromancyData.GetPassword();
        username = necromancyData.GetUsername();

        new GetDataTask().execute("http://10.0.2.2:8080/api/getData?username="+username+ "&password="+password);
          singleton = AppManager.getInstance();

        // Listen Restart button which will restart the gam

        // 0 - News
        // 1 - Player Info
        // 2 - Settings
        // 3 - Encyclopedia
        // 4 - open gift

        ButtonActivityListener(buttonNews, 0);
        ButtonActivityListener(buttonPlayerInfo, 1);
        ButtonActivityListener(buttonSettings, 2);
        ButtonActivityListener(buttonEncyclopedia, 3);
        ImageButtonActivityListener(openGift, 4);


    }

    private int getDrawableCard(int card) {
        return getResources().getIdentifier("card" + card, "drawable", getPackageName());
    }

    private Toast getToast(String s) {
        return Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT);
    }

    private void restartGame() {

        for (Activity activity : activities)
            activity.recreate();

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

    private void ImageButtonActivityListener(ImageButton buttonNews, final int activityIndex)
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

    class GetDataTask extends AsyncTask<String, Void, String>
    {
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
            try
            {
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
                while((line = bufferedReader.readLine()) != null)
                {
                    result.append(line).append("\n");

                }

            }
            catch (IOException ex)
            {
                return  ex.getMessage();

            }
            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            int aJsonInt = 0;
            int coins = 0;
            int BestRound = 0;
            int MoneyEarned = 0;
            int EnemiesKilled = 0;

            try
            {
                JSONObject jsonObject = new JSONObject(result);
                aJsonInt = jsonObject.getInt("status");

                JSONObject accountObj = jsonObject.getJSONObject("account");
                JSONObject dataObj = accountObj.getJSONObject("data");

                coins = dataObj.getInt("coins");
                BestRound = dataObj.getInt("BestRound");
                MoneyEarned = dataObj.getInt("MoneyEarned");
                EnemiesKilled = dataObj.getInt("EnemiesKilled");


                necromancyData.SetBestRound(BestRound);
                necromancyData.SetMoneyEarned(MoneyEarned);
                necromancyData.SetEnemiesKilled(EnemiesKilled);

            }
            catch(Exception e){

            }


            // set data response to textView

            mResult.setText("status: " + necromancyData.getCoins()+" ok: " + coins);

//            if(progressDialog != null)
//            {
//                progressDialog.dismiss();
//            }

            super.onPostExecute(result);

        }

        private String postData(String urlPath) throws IOException, JSONException {

        StringBuilder result = new StringBuilder();
        BufferedWriter bufferedWriter = null;
        BufferedReader bufferedReader = null;

        try {
            //Create data to send to server
            JSONObject dataToSend = new JSONObject();
            dataToSend.put("fbname", "Think twice code once");
            dataToSend.put("content", "feel good");
            dataToSend.put("likes", 1);
            dataToSend.put("comments", 1);

            //Initialize and config request, then connect to server.
            URL url = new URL(urlPath);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(10000 /* milliseconds */);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);  //enable output (body data)
            urlConnection.setRequestProperty("Content-Type", "application/json");// set header
            urlConnection.connect();

            //Write data into server
            OutputStream outputStream = urlConnection.getOutputStream();
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            bufferedWriter.write(dataToSend.toString());
            bufferedWriter.flush();

            //Read data response from server
            InputStream inputStream = urlConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line).append("\n");
            }
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        }

        return result.toString();
    }
    }

    class PutDataTask extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Updating data...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                return putData(params[0]);
            } catch (IOException ex) {
                return "Network error !";
            } catch (JSONException ex) {
                return "Data invalid !";
            }

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            mResult.setText(result);

            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }

        private String putData(String urlPath) throws IOException, JSONException {

            BufferedWriter bufferedWriter = null;
            String result = null;

            try {
                //Create data to update
                JSONObject dataToSend = new JSONObject();
                dataToSend.put("fbname", "Think twice code once ! HI !");
                dataToSend.put("content", "feel good - UPDATED !");
                dataToSend.put("likes", 999);
                dataToSend.put("comments", 999);

                //Initialize and config request, then connect to server
                URL url = new URL(urlPath);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(10000 /* milliseconds */);
                urlConnection.setRequestMethod("PUT");
                urlConnection.setDoOutput(true);  //enable output (body data)
                urlConnection.setRequestProperty("Content-Type", "application/json");// set header
                urlConnection.connect();

                //Write data into server
                OutputStream outputStream = urlConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                bufferedWriter.write(dataToSend.toString());
                bufferedWriter.flush();

                //Check update successful or not
                if (urlConnection.getResponseCode() == 200) {
                    return "Update successfully !";
                } else {
                    return "Update failed !";
                }
            } finally {
                if(bufferedWriter != null) {
                    bufferedWriter.close();
                }
            }
        }
    }

    class DeleteDataTask extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Deleting data...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                return deleteData(params[0]);
            } catch (IOException ex) {
                return "Network error !";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            mResult.setText(result);

            if(progressDialog != null) {
                progressDialog.dismiss();
            }
        }

        private String deleteData(String urlPath) throws IOException {

            String result =  null;

            //Initialize and config request, then connect to server.
            URL url = new URL(urlPath);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(10000 /* milliseconds */);
            urlConnection.setRequestMethod("DELETE");
            urlConnection.setRequestProperty("Content-Type", "application/json");// set header
            urlConnection.connect();

            //Check update successful or not
            if (urlConnection.getResponseCode() == 204) {
                result = "Delete successfully !";
            } else {
                result = "Delete failed !";
            }
            return result;
        }
    }

}



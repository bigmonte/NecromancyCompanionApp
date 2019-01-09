package com.portalmafia.androidcardgame;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends Activity {

    EditText username, password;

    String usr, pwd;

    Button loginButton;

    TextView login_label;

    NecromancyData necromancyData;

    Typeface font;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        necromancyData = ((NecromancyData) getApplicationContext());

        username = findViewById(R.id.username_input);
        password = findViewById(R.id.password_input);
        loginButton = findViewById(R.id.login_button);
        login_label = findViewById(R.id.login_banner);


        font = Typeface.createFromAsset(getAssets(), "fonts/curse.ttf");

        //username.setTypeface(font);
        //password.setTypeface(font);
        loginButton.setTypeface(font);

       // username.getBackground().setColorFilter(Color.argb(40,140 ,255,58), PorterDuff.Mode.SRC_ATOP);
        //password.getBackground().setColorFilter(Color.argb(40,140 ,255,58), PorterDuff.Mode.SRC_ATOP);





        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                usr = username.getText().toString();
                pwd = password.getText().toString();
                new GetDataTask().execute("http://10.0.2.2:8080/api/getData?username=" + usr + "&password=" + pwd);


            }
        });


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

            int statusCode;


            try {
                JSONObject jsonObject = new JSONObject(result);

                String status = jsonObject.getString("status");

                if (!(status.equals("200"))) // bad login
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Incorrect Login", Toast.LENGTH_LONG);
                    toast.show();

                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Welcome!", Toast.LENGTH_SHORT);
                    toast.show();
                    Intent openMainIntent = new Intent(LoginActivity.this,
                            MainActivity.class);
                    startActivity(openMainIntent);
                    necromancyData.SetUsername(usr);
                    necromancyData.SetPassword(pwd);
                }


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

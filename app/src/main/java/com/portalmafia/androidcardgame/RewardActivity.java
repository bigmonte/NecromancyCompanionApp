package com.portalmafia.androidcardgame;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.Objects;

public class RewardActivity extends AppCompatActivity implements SensorEventListener {

    Class[] ActivitiesClass = new Class[1];
    Intent[] intents = new Intent[1];

    ImageButton openRewardButton;


    private static final String TAG = "Accelerometer";
    private float lastX, lastY, lastZ;

    private SensorManager sensorManager;
    private Sensor accelerometer;

    private float deltaXMax = 0;
    private float deltaYMax = 0;
    private float deltaZMax = 0;

    private float deltaX = 0;
    private float deltaY = 0;
    private float deltaZ = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        ActivitiesClass[0] = OpenedReward.class;

        openRewardButton = findViewById(R.id.OpenGiftImageButton);

        //ButtonActivityListener(openRewardButton, 0);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {

            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener( this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
        }
    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener( this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    // parar listening the events
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // get as varia;oes
        deltaX = Math.abs( lastX - event.values[0] );
        deltaY = Math.abs( lastY - event.values[1] );
        deltaZ = Math.abs( lastZ - event.values[2] );

        // discriminar valores pequenos
        if (deltaX < 1)
            deltaX = 0;
        if (deltaY < 1)
            deltaY = 0;

        if (deltaX > 3) {
//            Intent intent = new Intent(this, OpenedReward.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
            ChangeActivity(0);

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }



    private void ButtonActivityListener(ImageButton button, final int activityIndex)
    {
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ChangeActivity(v,activityIndex );
//            }
//        });
    }

    public void ChangeActivity( int index)
    {
        intents[index] = new Intent();
        intents[index].setClass(this, ActivitiesClass[index]);
        startActivity(intents[index]);
    }
}

package com.upgrad.sampleapp;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SensorManager mSensorManager;
    private Sensor mSensor;
    ImageView mBall;
    TextView mLightReading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Sample Application");
        setSupportActionBar(toolbar);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (mSensor != null) {
            mSensorManager.registerListener(lightSensorListener, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            mLightReading.setText(getResources().getText(R.string.no_sensor));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // TODO: Improper Code Ethics - Code Missing
    }

    private final SensorEventListener lightSensorListener = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
                // TODO: Improper Code Ethics - Code Deprecated
                Drawable dr = getResources().getDrawable(R.drawable.ball);
                Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
                int lightValue = (int) event.values[0];
                int scale = 300 + (2 * lightValue);
                Drawable d = new BitmapDrawable(getResources(),
                        Bitmap.createScaledBitmap(bitmap, scale, scale, true));
                mBall.setImageDrawable(d);
                mLightReading.setText("Light Value: " + event.values[0]);
            }
        }
    };
}
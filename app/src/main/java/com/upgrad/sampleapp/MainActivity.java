package com.upgrad.sampleapp;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private ImageView mBall;
    private TextView mLightReading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBall = (ImageView) findViewById(R.id.ball);
        mLightReading = (TextView) findViewById(R.id.reading);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Sample Application");
        setSupportActionBar(toolbar);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (mSensor == null) {
            mLightReading.setText(getResources().getText(R.string.no_sensor));
        }
    }

    @Override
    protected void onDestroy() {
        // Destroy every sensor.
        mSensorManager = null;
        if (mSensor != null) {
            mSensor = null;
        }
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        // Register listener.
        super.onResume();
        if (mSensor != null) {
            mSensorManager.registerListener(mLightSensorListener, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        // Unregister listener.
        if (mSensor != null) {
            mSensorManager.unregisterListener(mLightSensorListener);
        }
        super.onPause();
    }

    private final SensorEventListener mLightSensorListener = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // do nothing.
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
                Drawable dr = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ball);
                Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
                // more accuracy with float.
                float lightValue =  event.values[0];
                int scale = (int) (300 + (2 * lightValue));
                Drawable drawable = new BitmapDrawable(getResources(),
                        Bitmap.createScaledBitmap(bitmap, scale, scale, true));
                mBall.setImageDrawable(drawable);
                mLightReading.setText(String.format(getString(R.string.light_value), event.values[0]));
            }
        }
    };
}

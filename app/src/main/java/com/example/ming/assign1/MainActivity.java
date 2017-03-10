package com.example.ming.assign1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;

import org.w3c.dom.Text;

import static java.lang.Math.sqrt;


public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private TextView accelValue, shakeResult, pressureText;
    private EditText thresholdTxt;
    private Sensor mySensor;
    private Sensor pressureSensor;
    private SensorManager SM;
    private Float threshold;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Create our own SensorManager
        SM = (SensorManager) getSystemService(SENSOR_SERVICE);

        //Acceleration Sensor
        mySensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        pressureSensor = SM.getDefaultSensor(Sensor.TYPE_PRESSURE);

        //Register Sensor listener
//        SM.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_NORMAL);
//        SM.registerListener(this, pressureSensor, SensorManager.SENSOR_DELAY_NORMAL);

        //AssignTextViews and EditText
        accelValue = (TextView)findViewById(R.id.accelValue);
        shakeResult = (TextView)findViewById(R.id.shakeResult);
        pressureText = (TextView) findViewById(R.id.pressureText);
        thresholdTxt = (EditText) findViewById(R.id.shakeThreshold);

        Button start = (Button)findViewById(R.id.startBtn);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                threshold = Float.parseFloat(thresholdTxt.getText().toString());
                SM.registerListener(MainActivity.this, mySensor, SensorManager.SENSOR_DELAY_NORMAL);
                //Log.d("start","clicked start");
            }
        });


        Button stop = (Button)findViewById(R.id.stopBtn);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //stop the sensor change???
               // Log.d("stop","clicked stop");
                SM.unregisterListener(MainActivity.this, mySensor);
                accelValue.setText("0.0, 0.0, 0.0");
                shakeResult.setText("");
            }
        });

        Button pressureBtn = (Button)findViewById(R.id.pressureBtn);
        pressureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //set textview for pressure display
                SM.registerListener(MainActivity.this, pressureSensor, SensorManager.SENSOR_DELAY_NORMAL);
               // Log.d("airpressure","clicked");
            }
        });


    }

    //protected void onResume(){ super.onResume();
    // registerListener}
    //protected void onPause(){super.onPause(); mSensorManager.unregisterlistener}
    //void onClick(View v){switch case}

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
      // Detect which sensor event is changed
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            double ax = event.values[0];
            double ay = event.values[1];
            double az = event.values[2];

            //display acceleration value
            accelValue.setText("{ "+ ax+", "+ ay+", "+ az +" }");

            if((sqrt(ax*ax+ay*ax+az*ax)) < threshold){
                shakeResult.setText("No Shake");
            }
            else{
                shakeResult.setText("Shake");
            }
        }
        else if (event.sensor.getType() == Sensor.TYPE_PRESSURE) {
            pressureText.setText(" " + event.values[0]);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //Not in use
    }
}

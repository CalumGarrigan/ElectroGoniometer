package com.garrigan.calum.ljmu.electrogoniometer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Handler;
import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;


public class GoniometerPage extends AppCompatActivity implements SensorEventListener {

    public static HashMap<String, Pair<Integer, Integer>> patientRecords = new HashMap<String, Pair<Integer, Integer>>();

    private TextView textView;
    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEvent currentPos;
    private Double startAngle;
    private Double endAngle;
    private Boolean isMeasuring = false;
    private ImageView playIcon;
    private Button btn;
    private TextView angle;
    private String string;
    private String joint;
    private Integer patientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_goniometer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        TextView btn = findViewById(R.id.save_data_button);
        angle = findViewById(R.id.angle);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getSharedPreferences("patient_data", MODE_PRIVATE).edit().putInt("extra_end_angle", endAngle.intValue());

                if(patientRecords.containsKey(patientId)) {
                    Pair<Integer, Integer> patientRecord = patientRecords.get(patientId);

                    if (joint.compareToIgnoreCase("left") == 0) {
                       // todo  patientRecord.first = endAngle.intValue();
                    }
                }
                Intent i = new Intent(GoniometerPage.this, PatientListPage.class);
               startActivity(i);
                finish();
            }
        });

        joint = getIntent().getStringExtra("extra_joint");
        patientId = getIntent().getIntExtra("extra_patient_id", 0);

        TextView title = findViewById(R.id.title);
        if(title != null) {
            title.setText("Patient #"+ patientId + ": " + joint + " Joint ");
        }

        playIcon = findViewById(R.id.playIcon);
        playIcon.setImageResource(R.drawable.ic_record_button);

        playIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isMeasuring) {
                    playIcon.setImageResource(R.drawable.ic_record_button);
                    endAngle = getAngle(currentPos);

                } else {
                    playIcon.setImageResource(R.drawable.ic_stop_button);
                    startAngle = getAngle(currentPos);
                }

                isMeasuring = !isMeasuring;
            }
        });


        setupSensor();
        blink();
    }

    private void setupSensor() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(GoniometerPage.this,sensor,sensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onSensorChanged(SensorEvent event) {
        currentPos = event;
        endAngle = getAngle(currentPos);
        if(isMeasuring) {
            TextView angleLabel = findViewById(R.id.angle);
            angleLabel.setText(Math.abs((endAngle.intValue() - startAngle.intValue())) + "°");
        }
    }

    /*
    * This method converts x/y acceleration values into x/y coordinates then an angle in degrees.
     */
    private double getAngle(SensorEvent event) {
        float xAcceleration = currentPos.values[0];
        float yAcceleration = currentPos.values[1];
        return Math.atan2(xAcceleration, yAcceleration) / (Math.PI / 180);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }

    private void blink() {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int timeToBlink = 500;    //in milissegunds
                try {
                    Thread.sleep(timeToBlink);
                } catch (Exception e) {
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        TextView txt = (TextView) findViewById(R.id.blinking_text);
                        if (txt.getVisibility() == View.VISIBLE) {
                            txt.setVisibility(View.INVISIBLE);
                        } else {
                            txt.setVisibility(View.VISIBLE);
                        }
                        blink();
                    }
                });
            }
        }).start();
    }
}


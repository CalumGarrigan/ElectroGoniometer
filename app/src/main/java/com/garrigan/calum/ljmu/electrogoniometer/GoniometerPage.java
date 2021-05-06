package com.garrigan.calum.ljmu.electrogoniometer;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;
import java.util.Set;

public class GoniometerPage extends AppCompatActivity implements SensorEventListener {

    private TextView textView;
    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEvent currentPos;
    private Double startAngle;
    private Double endAngle;
    private String measuredAngle;
    private Boolean isMeasuring = false;
    private ImageView playIcon;
    private TextView angle;
    private String joint;
    private String patientId;
    private String trialId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_goniometer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        angle = findViewById(R.id.angle);


        TextView btnSave = findViewById(R.id.save_data_button);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String measurement = patientId + " " + trialId + ": " + joint + " Joint  Angle: " + measuredAngle;
                MainPage.data.add(measurement);
                Set<String> set = new HashSet<String>();
                set.addAll(MainPage.data);
                getSharedPreferences("app", MODE_PRIVATE).edit().putStringSet("measurements", set).commit();

                startActivity(new Intent(GoniometerPage.this, ReadingsPage.class));
                finish();
            }
        });

        patientId = getIntent().getStringExtra("extra_patient_id");
        trialId = getIntent().getStringExtra("extra_trial_id");
        joint = getIntent().getStringExtra("extra_joint");

        TextView title = findViewById(R.id.title);
        if(title != null) {
            title.setText(patientId + " " + trialId + ": " + joint + " Joint ");
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
            measuredAngle = Math.abs((endAngle.intValue() - startAngle.intValue())) + "°";
            angleLabel.setText(measuredAngle);
        }

        TextView zAngleLabel = findViewById(R.id.z_angle);
        if(zAngleLabel != null){
            zAngleLabel.setText(("When z value is zero, start measurement" + " " + "="+ " " + getZAngle(event).intValue()  + "°"));
        }

    }

    /*
    * Angle between two points: atan2(y2-y1,x2-x1)(180/PI)
    * Radians to Degrees: 180/PI
    */
    private double getAngle(SensorEvent event) {
        float xvalue= currentPos.values[0];
        float yvalue = currentPos.values[1];
        return Math.atan2(xvalue, yvalue) / (Math.PI / 180);
    }

    /*
     * Angle between two points: atan2(y2-y1,x2-x1)(180/PI)
     * Radians to Degrees: 180/PI
     */
    private Double getZAngle(SensorEvent event) {
        float yAcceleration = currentPos.values[1];
        float zAcceleration = currentPos.values[2];
        return Math.atan2(yAcceleration, zAcceleration) / (Math.PI / 180) + (90);

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


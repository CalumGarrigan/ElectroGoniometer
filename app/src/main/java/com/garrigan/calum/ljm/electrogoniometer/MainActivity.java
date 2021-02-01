package com.garrigan.calum.ljm.electrogoniometer;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupButtonClicks();
    }

    private void setupButtonClicks() {
        TextView btnNewPatient = findViewById(R.id.button_new_patient);
        if(btnNewPatient != null) {
            btnNewPatient.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "New Patient pressed", Toast.LENGTH_LONG).show();
                }
            });
        }

        TextView btnPatientData = findViewById(R.id.button_patient_data);
        if(btnPatientData != null) {
            btnPatientData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Patient Data pressed", Toast.LENGTH_LONG).show();
                }
            });
        }

        TextView btnAbout = findViewById(R.id.button_about_app);
        if(btnAbout != null) {
            btnAbout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "About pressed", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
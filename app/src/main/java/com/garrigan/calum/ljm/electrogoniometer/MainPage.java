package com.garrigan.calum.ljm.electrogoniometer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_main);
        setupButtonClicks();
    }

    private void setupButtonClicks() {
        TextView btnNewPatient = findViewById(R.id.button_new_patient);
        if(btnNewPatient != null) {
            btnNewPatient.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), NewPatientPage.class));
                }
            });
        }

        TextView btnPatientData = findViewById(R.id.button_patient_data);
        if(btnPatientData != null) {
            btnPatientData.setOnClickListener(v ->
                    startActivity(new Intent(getApplicationContext(), PatientListPage.class))
            );
        }

        TextView btnAbout = findViewById(R.id.button_about_app);
        if(btnAbout != null) {
            btnAbout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), AboutPage.class));
                }
            });
        }
    }
}
package com.garrigan.calum.ljmu.electrogoniometer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Set;

public class MainPage extends AppCompatActivity {

    static ArrayList<String> data = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_main);
        if( data.isEmpty()) {
            Set<String> set = getSharedPreferences("app", MODE_PRIVATE).getStringSet("measurements", null);

            if(set != null) {
                data.addAll(set);
            }
        }
        setupButtonClicks();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    }

    private void setupButtonClicks() {
        TextView btnNewPatient = findViewById(R.id.button_new_patient);
        if(btnNewPatient != null) {
            btnNewPatient.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), NewReadingPage.class));
                }
            });
        }

        TextView btnPatientData = findViewById(R.id.button_patient_data);
        if(btnPatientData != null) {
            btnPatientData.setOnClickListener(v ->
                    startActivity(new Intent(getApplicationContext(), ReadingsPage.class))
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
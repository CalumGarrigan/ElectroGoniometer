package com.garrigan.calum.ljmu.electrogoniometer;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class AboutPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.page_about);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}

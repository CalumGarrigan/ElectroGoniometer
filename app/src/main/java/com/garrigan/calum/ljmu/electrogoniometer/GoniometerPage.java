package com.garrigan.calum.ljmu.electrogoniometer;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GoniometerPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_goniometer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String joint = getIntent().getStringExtra("extra_joint");
        TextView title = findViewById(R.id.title);

        if(title != null) {
            title.setText(joint + " Joint Measurement");
        }
    }
}

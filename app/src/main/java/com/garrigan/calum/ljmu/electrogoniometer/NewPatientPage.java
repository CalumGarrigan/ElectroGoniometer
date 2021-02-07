package com.garrigan.calum.ljmu.electrogoniometer;

import android.R.layout;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static android.R.layout.*;

public class NewPatientPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_new_patient);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupClicks();
    }

    private void setupClicks() {
        TextView btnMeasure = findViewById(R.id.measure_button);
        if(btnMeasure != null) {

            btnMeasure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent my = new Intent(getApplicationContext(), GoniometerPage.class);
                    RadioGroup radioGroup = findViewById(R.id.radio_joint);
                    RadioButton radioButton = radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());

                    my.putExtra("extra_joint", radioButton.getText());
                    startActivity(my);
                }
            });
        }
    }
}

package com.garrigan.calum.ljmu.electrogoniometer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static android.R.layout.*;

public class NewReadingPage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner patient_spinner;
    Spinner trial_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_new_reading);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        patient_spinner = findViewById(R.id.patient_id);
        ArrayAdapter<CharSequence> adapterPatient = ArrayAdapter.createFromResource(this,R.array.patient_numbers, R.layout.spinner_text);
        adapterPatient.setDropDownViewResource(simple_spinner_dropdown_item);
        patient_spinner.setAdapter(adapterPatient);
        patient_spinner.setOnItemSelectedListener(this);

        trial_spinner = findViewById(R.id.trial_id);
        ArrayAdapter<CharSequence> adapterTrial = ArrayAdapter.createFromResource(this,R.array.trial_numbers, R.layout.spinner_text);
        adapterTrial.setDropDownViewResource(simple_spinner_dropdown_item);
        trial_spinner.setAdapter(adapterTrial);
        trial_spinner.setOnItemSelectedListener(this);

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

                    my.putExtra("extra_patient_id", patient_spinner.getSelectedItem().toString());
                    my.putExtra("extra_trial_id", trial_spinner.getSelectedItem().toString());
                    my.putExtra("extra_joint", radioButton.getText());

                    startActivity(my);
                }
            });
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}



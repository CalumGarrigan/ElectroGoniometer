package com.garrigan.calum.ljmu.electrogoniometer;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class  PatientListPage extends AppCompatActivity {

    TextView textView;
    String string;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.page_patient_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textView=findViewById(R.id.textView);
        string=getIntent().getExtras().getString( "extra_end_angle");
        textView.setText(string);

        // todo GoniometerPage.patientRecords.forEach();
        //RecyclerView

    }
}

package com.garrigan.calum.ljmu.electrogoniometer;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AboutPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.page_about);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView text;
        text = (TextView) findViewById(R.id.text1);
        text.setMovementMethod(LinkMovementMethod.getInstance() );

        text = (TextView) findViewById(R.id.text2);
        text.setMovementMethod(LinkMovementMethod.getInstance() );

        text = (TextView) findViewById(R.id.text3);
        text.setMovementMethod(LinkMovementMethod.getInstance() );
    }
}

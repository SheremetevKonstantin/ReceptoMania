package com.sheremetev.receptomania;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_EMAIL_MESSAGE = "email";
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        email = (String)getIntent().getExtras().get(EXTRA_EMAIL_MESSAGE);
        TextView t = (TextView) findViewById(R.id.text);
        t.setText(email);
    }
}

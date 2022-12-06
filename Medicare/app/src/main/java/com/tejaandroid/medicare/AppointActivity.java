package com.tejaandroid.medicare;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AppointActivity extends AppCompatActivity {

    FloatingActionButton fab;
    TextView textView;
    ListView listView;
    AppointDatabase ad;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint);

        back = findViewById(R.id.backbtn4);
        back.setOnClickListener(v -> finish());

        textView = findViewById(R.id.textViewAppoint);
        listView = findViewById(R.id.list_appoint);

        ad = new AppointDatabase(this);
        final ArrayList<String> appointlist = ad.getAllData();
        @SuppressLint("ResourceType") ArrayAdapter<String> l = new ArrayAdapter<>(this, R.layout.appoint_name, appointlist);
        listView.setAdapter(l);

        int c = ad.getCount();
        if (c > 0)
            textView.setVisibility(View.INVISIBLE);
        else
            listView.setVisibility(View.INVISIBLE);

        fab = findViewById(R.id.fabAppoint);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AppointAddActivity.class);
                startActivity(intent);
            }
        });
    }
}
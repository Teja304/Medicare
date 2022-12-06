package com.tejaandroid.medicare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MedDetailsActivity extends AppCompatActivity {

    TextView name, times, t1, t2, t3, a, b;
    Button button;
    MedDatabase md;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_details);

        back = findViewById(R.id.backbtn3);
        back.setOnClickListener(v -> finish());

        name = findViewById(R.id.txtname);
        times = findViewById(R.id.txttimes);
        t1 = findViewById(R.id.txtt1);
        t2 = findViewById(R.id.txtt2);
        t3 = findViewById(R.id.txtt3);
        a = findViewById(R.id.text5);
        b = findViewById(R.id.text6);
        button = findViewById(R.id.delete);

        final String s = getIntent().getStringExtra("medicine");
        md = new MedDatabase(this);
        ArrayList<String[]> detail = md.getData();
        int i = detail.size();
        String s1 = "", s2 = "", s3 = "", s4 = "", s5 = "";
        int x = 0;
        while (x < i) {
            String[] d = detail.get(x);
            if (d[0].equals(s)) {
                s1 = d[0];
                s2 = d[1];
                s3 = d[2];
                s4 = d[3];
                s5 = d[4];
                break;
            }
            x++;
        }
        name.setText(s1);
        times.setText(s2);
        t1.setText(s3);
        t2.setText(s4);
        t3.setText(s5);

        if (s2.equals("1")) {
            a.setVisibility(View.INVISIBLE);
            b.setVisibility(View.INVISIBLE);
        }
        else if (s2.equals("2")) {
            b.setVisibility(View.INVISIBLE);
        }
        button.setOnClickListener(v -> {
            md.deleteMed(s);
            Toast.makeText(getApplicationContext(), s + " deleted", Toast.LENGTH_SHORT);
            Intent intent = new Intent(getApplicationContext(), MedActivity.class);
            finish();
            startActivity(intent);
        });
    }
}
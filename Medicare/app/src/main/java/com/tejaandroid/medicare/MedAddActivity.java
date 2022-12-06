package com.tejaandroid.medicare;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class MedAddActivity extends AppCompatActivity {

    EditText name;
    RadioGroup rg;
    RadioButton r1, r2, r3;
    ImageButton b1, b2, b3;
    ImageButton back;
    TextView t1, t2, t3;
    TimePickerDialog tp;
    FloatingActionButton fab;
    private MedDatabase md;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_add);

        back = findViewById(R.id.backbtn2);
        back.setOnClickListener(v -> finish());


        md = new MedDatabase(this);

        name = findViewById(R.id.editText1);
        rg = findViewById(R.id.radiogroup);
        r1 = findViewById(R.id.rb1);
        r2 = findViewById(R.id.rb2);
        r3 = findViewById(R.id.rb3);
        b1 = findViewById(R.id.button1);
        b2 = findViewById(R.id.button2);
        b3 = findViewById(R.id.button3);
        t1 = findViewById(R.id.textView5);
        t2 = findViewById(R.id.textView6);
        t3 = findViewById(R.id.textView7);
        fab = findViewById(R.id.fab1);

        rg.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton r = findViewById(checkedId);
            if (r == r1)
            {
                b1.setVisibility(View.VISIBLE);t1.setVisibility(View.VISIBLE);
            }
            else if (r == r2)
            {
                b1.setVisibility(View.VISIBLE);t1.setVisibility(View.VISIBLE);
                b2.setVisibility(View.VISIBLE);t2.setVisibility(View.VISIBLE);
            }
            else if (r == r3)
            {
                b1.setVisibility(View.VISIBLE);t1.setVisibility(View.VISIBLE);
                b2.setVisibility(View.VISIBLE);t2.setVisibility(View.VISIBLE);
                b3.setVisibility(View.VISIBLE);t3.setVisibility(View.VISIBLE);
            }
        });

        b1.setOnClickListener(v -> {
            final Calendar cldr = Calendar.getInstance();
            int hour = cldr.get(Calendar.HOUR_OF_DAY);
            int minutes = cldr.get(Calendar.MINUTE);
            tp = new TimePickerDialog(MedAddActivity.this, (tp, sHour, sMinute) -> t1.setText(sHour + ":" + sMinute), hour, minutes, true);
            tp.show();
        });

        b2.setOnClickListener(v -> {
            final Calendar cldr = Calendar.getInstance();
            int hour = cldr.get(Calendar.HOUR_OF_DAY);
            int minutes = cldr.get(Calendar.MINUTE);
            tp = new TimePickerDialog(MedAddActivity.this, (tp, sHour, sMinute) -> t2.setText(sHour + ":" + sMinute), hour, minutes, true);
            tp.show();
        });

        b3.setOnClickListener(v -> {
            final Calendar cldr = Calendar.getInstance();
            int hour = cldr.get(Calendar.HOUR_OF_DAY);
            int minutes = cldr.get(Calendar.MINUTE);
            tp = new TimePickerDialog(MedAddActivity.this, (tp, sHour, sMinute) -> t3.setText(sHour + ":" + sMinute), hour, minutes, true);
            tp.show();
        });

        fab.setOnClickListener(v -> {
            String n = name.getText().toString();
            RadioButton t = findViewById(rg.getCheckedRadioButtonId());
            String s = t.getText().toString();
            String s1, s2, s3;

            s1 = t1.getText().toString();
            s2 = t2.getText().toString();
            s3 = t3.getText().toString();

            if (md.insertData(n, s, s1, s2, s3))
            {
                Toast.makeText(getApplicationContext(), "Medicine added", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Can't be added", Toast.LENGTH_SHORT).show();
            }

            finish();
        });
    }
}
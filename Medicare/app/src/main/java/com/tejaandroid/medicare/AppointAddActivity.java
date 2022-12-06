package com.tejaandroid.medicare;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class AppointAddActivity extends AppCompatActivity {

    EditText name, hos;
    ImageButton back;
    TimePickerDialog tp;
    DatePickerDialog dp;
    TextView td, ta;
    ImageButton cal, time;
    FloatingActionButton fab;
    private AppointDatabase ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint_add);

        back = findViewById(R.id.backbtn5);
        back.setOnClickListener(v -> finish());

        ad = new AppointDatabase(this);

        name = findViewById(R.id.editText11);
        hos = findViewById(R.id.editText12);
        td = findViewById(R.id.textView14);
        ta = findViewById(R.id.textView16);

        cal = findViewById(R.id.calbutton);
        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int mon = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                dp = new DatePickerDialog(AppointAddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        td.setText(dayOfMonth + "/" + (month+1) + "/"+ year);
                    }
                }, year, mon, day);
                dp.show();
            }
        });

        time = findViewById(R.id.alarmbutton);
        time.setOnClickListener(v -> {
            final Calendar cldr = Calendar.getInstance();
            int hour = cldr.get(Calendar.HOUR_OF_DAY);
            int minutes = cldr.get(Calendar.MINUTE);
            tp = new TimePickerDialog(AppointAddActivity.this, (tp, sHour, sMinute) -> ta.setText(sHour + ":" + sMinute), hour, minutes, true);
            tp.show();
        });

        fab = findViewById(R.id.fabac);
        fab.setOnClickListener(v -> {
            String n = name.getText().toString();
            String h = hos.getText().toString();
            String s1 = td.getText().toString();
            String s2 = ta.getText().toString();

            if (ad.insertData(n, h, s1, s2))
            {
                Toast.makeText(getApplicationContext(), "Appointment added", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Can't be added", Toast.LENGTH_SHORT).show();
            }

            finish();
        });

    }
}
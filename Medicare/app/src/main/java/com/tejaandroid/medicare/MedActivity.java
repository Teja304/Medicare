package com.tejaandroid.medicare;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

public class MedActivity extends AppCompatActivity {

    FloatingActionButton fab;
    TextView textView;
    ListView listView;
    MedDatabase md;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med);

        back = findViewById(R.id.backbtn1);
        back.setOnClickListener(v -> finish());

        textView = findViewById(R.id.textViewMed);
        listView = findViewById(R.id.list_med);

        md = new MedDatabase(this);
        final ArrayList<String> medlist = md.getAllData();
        ArrayAdapter<String> l = new ArrayAdapter<>(this, R.layout.med_name, medlist);
        listView.setAdapter(l);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String m = medlist.get(position);
            Intent i = new Intent(getApplicationContext(), MedDetailsActivity.class);
            i.putExtra("medicine", m);
            startActivity(i);
        });

        int c = md.getCount();
        if (c > 0)
            textView.setVisibility(View.INVISIBLE);
        else
            listView.setVisibility(View.INVISIBLE);

        fab = findViewById(R.id.fabMed);
        fab.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), MedAddActivity.class);
            startActivity(i);
        });

        scheduleNotification();
    }

    private void scheduleNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,
                "default");
        builder.setContentTitle("Medicine time ");
        builder.setContentText("It's time for medicine!!!");
        builder.setAutoCancel(false);
        builder.setChannelId("10001");
        builder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        Notification notification1 = builder.build();

        Calendar calendar = Calendar.getInstance();
        Intent notiIntent = new Intent(this, MyNotificationPublisher.class);
        notiIntent.putExtra("notification-id", 1);
        notiIntent.putExtra("notification", notification1);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, notiIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager)
                getSystemService(Context.ALARM_SERVICE);

        md = new MedDatabase(this);
        ArrayList<String[]> arrayList = md.getData();
        int i = md.getCount();
        for (int x = 0; x < i; x++) {
            String[] content = arrayList.get(x);
            String[] t = content[2].split(":");
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(t[0]));
            calendar.set(Calendar.MINUTE, Integer.parseInt(t[1]));
            assert am != null;
            am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);

            if (content[1].equals("2")) {
                String[] t1 = content[3].split(":");
                calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(t1[0]));
                calendar.set(Calendar.MINUTE, Integer.parseInt(t1[1]));
                assert am != null;
                am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);
            } else if (content[1].equals("3")) {
                String[] t1 = content[3].split(":");
                calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(t1[0]));
                calendar.set(Calendar.MINUTE, Integer.parseInt(t1[1]));
                assert am != null;
                am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);

                String[] t2 = content[3].split(":");
                calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(t2[0]));
                calendar.set(Calendar.MINUTE, Integer.parseInt(t2[1]));
                assert am != null;
                am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        md = new MedDatabase(this);
        final ArrayList<String> medlist = md.getAllData();
        ArrayAdapter<String> l = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, medlist);
        listView.setAdapter(l);
    }
}
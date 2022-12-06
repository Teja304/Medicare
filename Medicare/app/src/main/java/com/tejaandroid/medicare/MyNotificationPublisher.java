package com.tejaandroid.medicare;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class MyNotificationPublisher extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = intent.getParcelableExtra("notification");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int imp = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel nc = new NotificationChannel("notification-id",
                    "NOTIFICATION_CHANNEL_NAME", imp);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(nc);
        }
        int id = intent.getIntExtra("notification-id", 0);
        assert notificationManager != null;
        notificationManager.notify(id, notification);
    }
}

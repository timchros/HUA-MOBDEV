package hua.dit.mobdev.ec.appl10.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;
import androidx.core.app.ServiceCompat;

public class MyForegroundService extends Service {

    public MyForegroundService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // NotificationChannel
        final String CHANNEL_ID = "CHANNEL_123";
        final NotificationChannel notificationChannel = new NotificationChannel(
                CHANNEL_ID, "Channel 2", NotificationManager.IMPORTANCE_DEFAULT);
        getSystemService(NotificationManager.class)
                .createNotificationChannel(notificationChannel);
        // Notification
        final NotificationCompat.Builder notificationBuilder =
            new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(hua.dit.mobdev.ec.appl10.R.mipmap.ic_launcher)
                .setContentTitle("Notification Title 2")
                .setContentText("Notification Text 2");
        final Notification notification = notificationBuilder.build();
        // Promote the service to a Foreground Service
        ServiceCompat.startForeground(
                this, 1234, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
        );
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
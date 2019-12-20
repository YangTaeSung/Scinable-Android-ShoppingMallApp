package org.techtown.push;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.sql.Time;
import java.util.Map;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.util.TimeUtils;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";


    // Get the google token value
    @Override
    public void onNewToken(String s) {

        super.onNewToken(s);
        Log.e("Firebase", "FirebaseInstanceIDService : " + s);

    }


    // When received message & Handling that
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String message = "";
        String title = "";

        // Notification
        if (remoteMessage.getNotification() != null) {
            message = remoteMessage.getNotification().getBody();
            title = remoteMessage.getNotification().getTitle();
        }

        // Data
        /* Will use data form */
        Map<String, String> data = remoteMessage.getData();
        String messageData = data.get("message");
        String titleData = data.get("title");
        String nameData = data.get("name");

        sendNotification(titleData, messageData, nameData);
    }


    private void sendNotification(String title, String message, String name) {

        Intent intent;
        PendingIntent pendingIntent;

        intent = new Intent(this, MainActivity.class);
        intent.putExtra("name", name);  //push 정보중 name 값을 mainActivity로 넘김

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pendingIntent = PendingIntent.getActivity(this, 0 , intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder;
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Higher than SDK26 version
        if (Build.VERSION.SDK_INT >= 26) {

            String channelId = "test push";
            String channelName = "test Push Message";
            String channelDescription = "New test Information";
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(channelDescription);
            // Channel settings
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300});
            notificationManager.createNotificationChannel(channel);
            // Builder registered channel
            notificationBuilder = new NotificationCompat.Builder(this, channelId);
        } else {
            notificationBuilder = new NotificationCompat.Builder(this);
        }

        notificationBuilder.setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setContentText(message);

        notificationManager.notify(9999 , notificationBuilder.build());
    }
}


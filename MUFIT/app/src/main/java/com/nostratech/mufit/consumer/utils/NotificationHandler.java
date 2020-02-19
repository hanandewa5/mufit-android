package com.nostratech.mufit.consumer.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.os.Build;
import androidx.core.app.NotificationCompat;

import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.history_detail.HistoryDetailActivity;
import com.nostratech.mufit.consumer.news_promo.NewsPromoActivity;
import com.nostratech.mufit.consumer.root.RootActivity;

public class NotificationHandler {

    private static final String CHANNEL_ID = "MUFIT_CHANNEL_ID_01";
    private Context context;

    public NotificationHandler(Context context){
        this.context = context;
    }

    //Show notification di device user
    public void showBookingVerifiedNotification(String title, String message, String bookingId){

        int requestID = (int) System.currentTimeMillis();

        Intent rootIntent = new Intent(context, RootActivity.class);

        Intent historyDetail= new Intent(context, HistoryDetailActivity.class);
        historyDetail.putExtra(HistoryDetailActivity.EXTRA_BOOKING_ID, bookingId);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(RootActivity.class);
        stackBuilder.addNextIntent(rootIntent);
        stackBuilder.addNextIntent(historyDetail);

        actionForNotification(title,message, stackBuilder, requestID);
    }

    public void showNewsVerifiedNotification(String title, String message){
        int requestID = (int) System.currentTimeMillis();

        Intent rootIntent = new Intent(context, RootActivity.class);

        Intent newsPromo = new Intent(context, NewsPromoActivity.class);
        //Create an intent with backstack of RootActivity as the parent and opens HistoryDetailActivit
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(RootActivity.class);
        stackBuilder.addNextIntent(rootIntent);
        stackBuilder.addNextIntent(newsPromo);

        actionForNotification(title,message, stackBuilder, requestID);
    }

    public void showEventVerifiedNotification(String title, String message){

        int requestID = (int) System.currentTimeMillis();

        Intent rootIntent = new Intent(context, RootActivity.class);

        //Create an intent with backstack of RootActivity as the parent and opens HistoryDetailActivit
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(RootActivity.class);
        stackBuilder.addNextIntent(rootIntent);

        actionForNotification(title, message, stackBuilder, requestID);
    }

    public void actionForNotification(String title, String message, TaskStackBuilder builder, int requestID){
        //Create intent
        PendingIntent pendingIntent = builder.getPendingIntent(requestID, PendingIntent.FLAG_ONE_SHOT);

        //Method ini untuk Android API 26 dan ke atas
        initChannels();

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context,
                CHANNEL_ID)
                .setSmallIcon(R.drawable.mufit_logo_transparent_3x)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.trainer_icon))
                .setColor(Color.RED)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if(notificationManager != null){
            notificationManager.notify(0, notificationBuilder.build());
        }
    }
    /*
     * Starting in Android 8.0 (API level 26), all notifications must be assigned to a channel or it will not appear.
     * The following method creates a channel for devices API 26 and above.
     */
    private void initChannels() {
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }

//        NotificationManager notificationManager =
//                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        //perubahan di parameter channel (IMPORTANCE_HIGH dari IMPORTANCE_DEFAULT), enableVibration(true) dan setSound
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                "MUFIT Notifications",
                NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("Inform users of new notifications.");
        channel.enableVibration(true);
        AudioAttributes att = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .build();
        channel.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION),att);


        notificationManager.createNotificationChannel(channel);
    }

}

package com.serasiautoraya.slimobiledrivertracking.util;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RemoteViews;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.serasiautoraya.slimobiledrivertracking.R;

import java.util.Map;

/**
 * Created by Randi Dwi Nandra on 16/01/2017.
 */
public class FCMMessageService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
//        Log.d("TAG_NOTIF", "Message Received here");
//        Map<String, String> data = remoteMessage.getData();
//        String value1  = data.get("title");
//        String value2 = data.get("content");
//        showNotification(data);
//        showPopUpAlert(data.get("title"));
    }

    public void showNotification(Map<String, String> message)
    {
//        Intent i = new Intent(this, MainView.class);
//        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP ); //FLAG_ACTIVITY_CLEAR_TOP

        Log.d("TAG_NOTIF", "Message Received here: "+message);
        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.notification_order);
//        contentView.setTextViewText(R.id.title, message.get("title"));
//        contentView.setTextViewText(R.id.text, message.get("content"));

//        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.ic_person_pin_black_24dp)
                .setAutoCancel(true)
                .setContentTitle("Title")
//                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setContentText(message.get("content"))
                .setCustomHeadsUpContentView(contentView)
                .setTicker(message.get("content"))
                .setVibrate(new long[] { 100, 250, 100, 250, 100, 250 })
                .setPriority(NotificationCompat.PRIORITY_MAX)
//                .setContentIntent(pendingIntent)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE);

        NotificationManager manager =   (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0,builder.build());
    }

    public void showPopUpAlert(String message){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(FCMMessageService.this.getApplicationContext());
                LayoutInflater inflater = LayoutInflater.from(FCMMessageService.this);

                View dialogView = inflater.inflate(R.layout.notification_order, null);
                builder.setView(dialogView);

                final AlertDialog alert = builder.create();
                alert.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                alert.show();

                Vibrator vibrator;
                vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(500);

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                Window window = alert.getWindow();
                lp.copyFrom(window.getAttributes());
                //This makes the dialog take up the full width
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                window.setAttributes(lp);
            }
        });

    }

}
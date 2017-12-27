package com.serasiautoraya.slimobiledrivertracking_training.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.serasiautoraya.slimobiledrivertracking_training.module.BaseModel.SharedPrefsModel;
import com.serasiautoraya.slimobiledrivertracking_training.module.Login.LoginActivity;
import com.serasiautoraya.slimobiledrivertracking_training.module.SQLIte.DBHelper;
import com.serasiautoraya.slimobiledrivertracking_training.R;
import com.serasiautoraya.slimobiledrivertracking_training.helper.HelperKey;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

/**
 * Created by Randi Dwi Nandra on 16/01/2017.
 */
public class FCMMessageService extends FirebaseMessagingService {

    public final void molo(){

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Map<String, String> data = remoteMessage.getData();

//        String value1  = data.get("title");
//        String value2 = data.get("content");

//        if(!SharedPrefsUtil.getString(this, HelperKey.KEY_USERNAME, "").equalsIgnoreCase(data.get("IdPersonal"))){
//            Log.d("NOTIF_TAG", "Usernya logout");

//        }else {
            showNotification(data);
//        }

//        showPopUpAlert(data.get("title"));
    }

    public void showNotification(Map<String, String> message)
    {
        SharedPrefsModel sharedPrefsModel = new SharedPrefsModel(getApplicationContext());

        Intent i = new Intent(this, LoginActivity.class);
//        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP ); //FLAG_ACTIVITY_CLEAR_TOP

        Log.d("TAG_NOTIF", "Message Received here: "+message);
        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.notification_order);
//        contentView.setTextViewText(R.id.title, message.get("title"));
//        contentView.setTextViewText(R.id.text, message.get("content"));

        int iconColor = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark);
        Bitmap bitmapIcon = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.logoselog);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setDefaults(Notification.DEFAULT_ALL)
                .setLargeIcon(bitmapIcon)
                .setSmallIcon(R.drawable.logoselog)
                .setColor(iconColor)
                .setAutoCancel(true)
                .setContentTitle(message.get("Title"))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message.get("Body")))
                .setContentText(message.get("Body"))
//                .setCustomHeadsUpContentView(contentView)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message.get("Body")))
                .setTicker(message.get("Body"))
                .setVibrate(new long[] { 100, 250, 100, 250, 100, 250 })
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentIntent(pendingIntent)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE);

        NotificationManager manager =   (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
//        Integer index = Integer.parseInt();
        manager.notify(sharedPrefsModel.get(HelperKey.KEY_NOTIF_ID, 0),builder.build());
        sharedPrefsModel.apply(HelperKey.KEY_NOTIF_ID, sharedPrefsModel.get(HelperKey.KEY_NOTIF_ID, 0) + 1);

        saveToDatabase(message);
    }

    private void saveToDatabase(Map<String, String> message){
        Calendar dateCal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy, hh:mm:ss");
        String dateString = sdf.format(dateCal.getTime());

        DBHelper dbHelper = DBHelper.getInstance(getApplicationContext());
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.NOTIFICATION_COLUMN_TITLE, message.get("Title"));
        contentValues.put(DBHelper.NOTIFICATION_COLUMN_MESSAGE, message.get("Body"));
        contentValues.put(DBHelper.NOTIFICATION_COLUMN_DATE, dateString);
        dbHelper.insert(DBHelper.NOTIFICATION_TABLE_NAME, contentValues);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        sendBroadcast(new Intent("FCMReceiver"));
    }

}
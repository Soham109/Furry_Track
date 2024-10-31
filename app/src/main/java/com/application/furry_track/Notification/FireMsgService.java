package com.application.furry_track.Notification;


import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.application.furry_track.Activity.Splash_Screen;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;


public class FireMsgService extends FirebaseMessagingService {

    private NotificationUtils notificationUtils;
    public static String isFlg = "no";
String TAG;


    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
    }



    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        try {

            Log.d("Msg", "Message received [" + remoteMessage + "]");

//            Toast.makeText(this, "Accept", Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, "ok "+ ApplicationPreferences.getValue("n_driver_name", "null", this), Toast.LENGTH_SHORT).show();
          try {
              if (remoteMessage == null)
                  return;
          }catch (Exception e){

          }


            try {
                Map<String, String> params = remoteMessage.getData();
                //      JSONObject object = new JSONObject(params);
                JSONObject json = new JSONObject(params);
                handleDataMessage(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }

            // Check if message contains a notification payload.
         /*  if (remoteMessage.getNotification() != null) {
                Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
               // handleNotification(remoteMessage.getNotification().getBody());
            }

            // Check if message contains a data payload.
            if (remoteMessage.getData().size() > 0) {
                Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

                try {
                    Map<String, String> params = remoteMessage.getData();
              //      JSONObject object = new JSONObject(params);
                    JSONObject json = new JSONObject(params);
                    handleDataMessage(json);
                } catch (Exception e) {
                    Log.e(TAG, "Exception: " + e.getMessage());
                }
            }*/
/*
            MainActivity_sankalan d=new MainActivity_sankalan();
            d.accept();*/


            /*Intent filter = new Intent();
            filter.setAction("com.myavin.myavin.notification");
            *//*filter.putExtra("pickup_child_msg_status", pickup_count);
            filter.putExtra("emcy_child_msg_status", emergency_count);
            filter.putExtra("child_id", child_id);*//*
            sendBroadcast(filter);*/


/*
            if (remoteMessage.getNotification() != null) {
                Log.d("Msg", "Message received [" + remoteMessage + "]");


            } else {
                Log.d("Msg------------------", "NULL NULL");
            }
            String title = remoteMessage.getData().get("title");
            String body = remoteMessage.getData().get("body");

            Intent resultIntent = new Intent(getApplicationContext(), Sub_Splash_Screen.class);
            resultIntent.putExtra("message", "hieooo");*/

            /*Intent intent = new Intent(this, Sub_Splash_Screen.class);
            intent.putExtra("noti", "yes");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
*/
          /*  PendingIntent pendingIntent = PendingIntent.getActivity(this, 1410, resultIntent, PendingIntent.FLAG_ONE_SHOT);
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {

                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.appsara_logo)
                        .setColor(getResources().getColor(R.color.app))

                        .setContentTitle(remoteMessage.getNotification().getTitle())
                        .setContentText(remoteMessage.getNotification().getBody())
                        .setSound(remoteMessage.getNotification().getLink())
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent);

                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                notificationManager.notify(1410, notificationBuilder.build());
            } else {
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.appsara_logo)
                        //.setContentTitle("Message")
                        .setContentTitle(remoteMessage.getNotification().getTitle())
                        .setContentText(remoteMessage.getNotification().getBody())
                        .setSound(remoteMessage.getNotification().getLink())
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent);
                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                notificationManager.notify(1410, notificationBuilder.build());
            }*/

           /* Intent filter = new Intent();
            filter.setAction("com.schoolapp_appsara.schoolapp_appsara");
            filter.putExtra("pickup_count", "Value");
            sendBroadcast(filter);*/




        } catch (Exception e) {

        }

    }


    private void handleNotification(String message) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
           // Intent pushNotification = new Intent(getApplicationContext(),Sub_Splash_Screen.class);
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            // play notification sound
           NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        }else{
            // If the app is in background, firebase itself handles the notification
        }
    }

    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());

        try {
            JSONObject data = json;
         //   JSONObject data = json.getJSONObject("data");

            String title = data.getString("title");
            String message = data.getString("message");
          // boolean isBackground = data.getBoolean("is_background");
            String imageUrl = "";
            String timestamp = "";

           // JSONObject payload = data.getJSONObject("payload");
/*
            Log.e(TAG, "title: " + title);
            Log.e(TAG, "message: " + message);
        //    Log.e(TAG, "isBackground: " + isBackground);
        //  Log.e(TAG, "payload: " + payload.toString());
            Log.e(TAG, "imageUrl: " + imageUrl);
            Log.e(TAG, "timestamp: " + timestamp);*/


         //   if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message
           /*     Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                // play notification sound
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();*/

                Intent resultIntent = new Intent(getApplicationContext(), Splash_Screen.class);
                resultIntent.putExtra("message", message);


                // check for image attachment
                if (TextUtils.isEmpty(imageUrl)) {
                    showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
                } else {
                    // image is present, show notification with image
                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
                }
/*
            } else {
                // app is in background, show the notification in notification tray
                Intent resultIntent = new Intent(getApplicationContext(), Sub_Splash_Screen.class);
                resultIntent.putExtra("message", message);

                // check for image attachment
                if (TextUtils.isEmpty(imageUrl)) {
                    showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
                } else {
                    // image is present, show notification with image
                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
                }
            }*/
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }


    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }

}

package com.application.furry_track.Notification;


import android.content.Context;
import android.content.Intent;

import androidx.legacy.content.WakefulBroadcastReceiver;

import com.application.furry_track.helper.ApplicationPreferences;


//WakefulBroadcastReceiver
public class FirebaseDataReceiver extends WakefulBroadcastReceiver {
 //   private DBManager dbManager;
    private final String TAG = "aaaaaaaaaaaaaaa";
    // long elapsedMinutes;

    @Override
    public void onReceive(Context context, Intent intent) {
//        Log.d(TAG, "I'm in!!!");

        //       Log.d(TAG, "no data");


              /*  try {

                    String title = intent.getStringExtra("title");
                    String msg = intent.getStringExtra("message");

                    dbManager = new DBManager(context);
                    dbManager.open();
                    dbManager.addNotif(title, msg);;
                    dbManager.close();


                } catch (Exception e) {
                    e.printStackTrace();
                }*/




/*
        Intent intentd = new Intent(context.getApplicationContext(), MainActivity_sankalan.class);
        intentd.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intentd.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intentd);*/

        /*Intent filter = new Intent();
        filter.setAction("com.kaya_driver.notification");
            *//*filter.putExtra("pickup_child_msg_status", pickup_count);
            filter.putExtra("emcy_child_msg_status", emergency_count);
            filter.putExtra("child_id", child_id);*//*
        //sendBroadcast(filter);
        LocalBroadcastManager.getInstance(context).sendBroadcast(filter);*/
        int noti = ApplicationPreferences.getIntValue("noti_count",context);
        noti = noti + 1;
        ApplicationPreferences.setIntValue("noti_count",noti,context);
        /*try {

            dbManager = new DBManager(context);
            dbManager.open();
            dbManager.insert(title, someDatad, img_url, timestamp);
            dbManager.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    /**
     * Method checks if the app is in background or not
     */





    /********************************************************************/



}
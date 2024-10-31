package com.application.furry_track.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.application.furry_track.R;
import com.application.furry_track.helper.ApplicationPreferences;

import java.util.Locale;


public class Splash_Screen extends AppCompatActivity implements Animation.AnimationListener {
    String[] PERMISSIONS = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.CAMERA, Manifest.permission.CALL_PHONE, Manifest.permission.RECORD_AUDIO};
    //Manifest.permission.WRITE_EXTERNAL_STORAGE,
    // Manifest.permission.READ_EXTERNAL_STORAGE,
    int PERMISSION_ALL = 1;
    int screen_second = 1700;
    public static int UNINSTALL_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Remove notificati+on bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.app_color));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.app_color));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);


        }

/*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            Drawable background = this.getResources().getDrawable(R.drawable.right_left_to_heder_a);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(this.getResources().getColor(android.R.color.transparent));
            window.setNavigationBarColor(this.getResources().getColor(android.R.color.transparent));
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setBackgroundDrawable(background);
        }*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && Splash_Screen.this != null) {
            PERMISSIONS = new String[]{Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_MEDIA_AUDIO};
        } else {
            PERMISSIONS = new String[]{Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE};
        }

        setContentView(R.layout.activity_splash_screen);
        if (ApplicationPreferences.getValue("lang_set", "", Splash_Screen.this).equalsIgnoreCase("gu")) {
            setApplicationLocale("gu");
        } else {
            setApplicationLocale("en");
        }
        /***************************************************/
        if (!hasPermissions(Splash_Screen.this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(Splash_Screen.this, PERMISSIONS, PERMISSION_ALL);
        } else {
/*
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("application/pdf");
            startActivityForResult(intent,1212);*/
            next_page();
        }
    }

/*

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1212:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    String uriString = uri.toString();
                    File myFile = new File(uriString);
                    String path = myFile.getAbsolutePath();
                    String displayName = null;

                    if (uriString.startsWith("content://")) {
                        Cursor cursor = null;
                        try {
                            cursor = getContentResolver().query(uri, null, null, null, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                            }
                        } finally {
                            cursor.close();
                        }
                    } else if (uriString.startsWith("file://")) {
                        displayName = myFile.getName();
                    }
                }
                break;
        }
        try {
            byte[] bytes = loadFile("fileName");
        }catch (Exception e){
            
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
*/


    /******************************/
    public boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // Log.e("DialogPermission","Ho! Ho! Ho!");  // Log printed
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (!hasPermissions(Splash_Screen.this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(Splash_Screen.this, PERMISSIONS, PERMISSION_ALL);
        } else {
            //   next_page();

            next_page();
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    private void next_page() {
        Handler handler = new Handler();
        Runnable r = new Runnable() {
            public void run() {


                if (ApplicationPreferences.getValue("regi", "", Splash_Screen.this).equalsIgnoreCase("y")) {
                 //   Intent intent = new Intent(getApplicationContext(), Ac_Court_List.class);
                   Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {

               //     Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                   Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        };
        handler.postDelayed(r, screen_second);
    }

    private void setApplicationLocale(String locale) {
        ApplicationPreferences.setValue("lang_set", locale, Splash_Screen.this);
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(new Locale(locale.toLowerCase()));
        } else {
            config.locale = new Locale(locale.toLowerCase());
        }
        resources.updateConfiguration(config, dm);
      /*  Intent intent = getIntent();
        finish();
        startActivity(intent);*/
    }


}

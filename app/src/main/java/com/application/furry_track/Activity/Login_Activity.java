package com.application.furry_track.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.application.furry_track.CustomeView.General_Dialog;
import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


/*import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;*/
import com.google.firebase.messaging.FirebaseMessaging;
import com.tapadoo.alerter.Alerter;
import com.application.furry_track.R;
import com.application.furry_track.databinding.ActivityLoginBinding;
import com.application.furry_track.helper.ApplicationPreferences;
import com.application.furry_track.helper.furry_track_Application;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import es.dmoral.toasty.Toasty;


public class Login_Activity extends Base_Activity implements View.OnClickListener {
    //,  txt_tool;//,txt_register;
    //
    private boolean doubleBackToExitPressedOnce = false;
    EditText input_email, input_password, input_c_password, input_name, input_contct;
    TextView txt_login, btn_register, txt_regi, txt_forgot, txt_sign_in;
    LinearLayout lin_c_password, lin_name, lin_contct;
    FrameLayout frm_image;
    ImageView img_doctor_image, img_blank;

    Activity mcontext;
    String url;
    String Device_id = "";
    AlertDialog dialog;
    String imagepath1 = "";

    String regId = "";
    //String version = "", myDeviceModel = "";
    //  private SmsVerifyCatcher smsVerifyCatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActivityLoginBinding binding = ActivityLoginBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());


        bindid();
    }


    void bindid() {

        mcontext = Login_Activity.this;
/*        Toolbar toolbar;
        mcontext = Login_Activity.this;
        toolbar = findViewById(R.id.toolbar_login);*/

      /*  PackageInfo pInfo = null;
        try {
            pInfo = mcontext.getPackageManager().getPackageInfo(mcontext.getPackageName(), 0);
            version = pInfo.versionName;

            //myDeviceModel = android.os.Build.MODEL+"/"+ android.os.Build.VERSION.SDK ;
            myDeviceModel = Build.MODEL + " / Android V : " + Build.VERSION.SDK_INT;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }*/


        /*toolbar.setTitleTextColor(Color.BLACK);
        toolbar.setSubtitleTextColor(Color.BLACK);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.btn_login));*/
        try {
            Device_id = getImeiNumber();
        } catch (Exception e) {

        }


        try {
            if (Device_id != null && !Device_id.isEmpty() && !Device_id.equals("null") && !Device_id.equalsIgnoreCase("null")) {
            } else {
                Device_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
            }
        } catch (Exception e) {

        }
        ApplicationPreferences.setValue("device_id", Device_id, Login_Activity.this);

        img_doctor_image = findViewById(R.id.img_doctor_image);

        input_email = findViewById(R.id.input_email);
        input_password = findViewById(R.id.input_password);
        input_c_password = findViewById(R.id.input_c_password);

        input_name = findViewById(R.id.input_name);
        input_contct = findViewById(R.id.input_contct);
        img_blank = findViewById(R.id.img_blank);


        frm_image = findViewById(R.id.frm_image);
        lin_c_password = findViewById(R.id.lin_c_password);
        lin_name = findViewById(R.id.lin_name);
        lin_contct = findViewById(R.id.lin_contct);

        txt_login = findViewById(R.id.txt_login);
        btn_register = findViewById(R.id.btn_register);
        txt_regi = findViewById(R.id.txt_regi);
        txt_forgot = findViewById(R.id.txt_forgot);
        txt_sign_in = findViewById(R.id.txt_sign_in);


        txt_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        txt_regi.setOnClickListener(this);
        txt_forgot.setOnClickListener(this);
        txt_sign_in.setOnClickListener(this);
        frm_image.setOnClickListener(this);


        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            //  Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        // Get new FCM registration token
                        regId = task.getResult();
                        //  toast(regId);


                    }
                });

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            //  Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        // Get new FCM registration token
                        regId = task.getResult();
                        //  toast(regId);


                    }
                });

        Login();
    }


    @Override
    public void onClick(View v) {

        if (v == btn_register) {

            if (regId != null && !regId.isEmpty() && !regId.equals("null") && !regId.equalsIgnoreCase("")) {
                if (!isempty_edittext(input_name) && !isempty_edittext(input_contct)&& !isempty_edittext(input_email) && !isempty_edittext(input_password) && !isempty_edittext(input_c_password)) {
                    //ApplicationPreferences.getValue("ContactNumber", mcontext)
                    if (input_password.getText().toString().equalsIgnoreCase(input_c_password.getText().toString())) {
                        if (isNetworkAvailable()) {
                            submit_api();
                            //postDataUsingVolley();
                        } else {

                            Alerter.create(mcontext)
                                    .setTitle(getString(R.string.nointernet))
                                    .setText(getString(R.string.check_internet))
                                    .setDuration(3500)
                                    .setBackgroundColor(R.color.colorPrimary)
                                    .setIcon(R.drawable.no_internet)
                                    .show();

                        }
                    } else {


                        input_c_password.requestFocus();
                        input_c_password.setError(getResources().getString(R.string.password_not_match));

                    }


                } else if (isempty_edittext(input_name)) {
                    input_name.requestFocus();
                    input_name.setError(getResources().getString(R.string.en_name));

                } else if (isempty_edittext(input_contct)) {
                    input_contct.requestFocus();
                    input_contct.setError(getResources().getString(R.string.en_contact));

                }else if (isempty_edittext(input_email)) {
                    input_email.requestFocus();
                    input_email.setError(getResources().getString(R.string.en_email));

                } else if (isempty_edittext(input_password)) {
                    input_password.requestFocus();
                    input_password.setError(getResources().getString(R.string.en_password));

                } else if (isempty_edittext(input_c_password)) {
                    input_c_password.requestFocus();
                    input_c_password.setError(getResources().getString(R.string.en_c_password));
                }

            } else {
                // regId = FirebaseInstanceId.getInstance().getToken();
                FirebaseMessaging.getInstance().getToken()
                        .addOnCompleteListener(new OnCompleteListener<String>() {
                            @Override
                            public void onComplete(@NonNull Task<String> task) {
                                if (!task.isSuccessful()) {
                                    //  Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                                    return;
                                }
                                // Get new FCM registration token
                                regId = task.getResult();
                                //toast(token);


                            }
                        });
                Toasty.warning(mcontext, "Please Try again.", Toast.LENGTH_SHORT, true).show();
            }
        } else if (v == txt_login) {

            if (regId != null && !regId.isEmpty() && !regId.equals("null") && !regId.equalsIgnoreCase("")) {
                if (!isempty_edittext(input_email) && !isempty_edittext(input_password)) {
                    //ApplicationPreferences.getValue("ContactNumber", mcontext)

                    if (isNetworkAvailable()) {
                        get_login();
                        //postDataUsingVolley();
                    } else {
                        Alerter.create(mcontext)
                                .setTitle(getString(R.string.nointernet))
                                .setText(getString(R.string.check_internet))
                                .setDuration(3500)
                                .setBackgroundColor(R.color.colorPrimary)
                                .setIcon(R.drawable.no_internet)
                                .show();
                        Toasty.error(mcontext, R.string.check_internet).show();
                        //getString(R.string.check_internet);
                    }

                } else if (isempty_edittext(input_email)) {
                    input_email.requestFocus();
                    input_email.setError(getResources().getString(R.string.en_email));
                    //  Toasty.warning(mcontext, getResources().getString(R.string.pl_mo_no), Toast.LENGTH_SHORT, true).show();

                } else if (isempty_edittext(input_password)) {
                    input_password.requestFocus();
                    input_password.setError(getResources().getString(R.string.en_password));
                    //  Toasty.warning(mcontext, getResources().getString(R.string.pl_mo_no), Toast.LENGTH_SHORT, true).show();

                }

            } else {
                // regId = FirebaseInstanceId.getInstance().getToken();
                FirebaseMessaging.getInstance().getToken()
                        .addOnCompleteListener(new OnCompleteListener<String>() {
                            @Override
                            public void onComplete(@NonNull Task<String> task) {
                                if (!task.isSuccessful()) {
                                    //  Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                                    return;
                                }
                                // Get new FCM registration token
                                regId = task.getResult();
                                //toast(token);


                            }
                        });
                Toasty.warning(mcontext, "Please Try again.", Toast.LENGTH_SHORT, true).show();
            }
        } else if (v == txt_regi) {

            sign_up();
        } else if (v == txt_sign_in) {
            Login();

        } else if (v == txt_forgot) {
            Toasty.info(mcontext, "Cooming Soon");

        } else if (v == frm_image) {
            setMultiShowButton();

        }


    }


    /*******************************************************************************/


    private void get_login() {
        dialog = new SpotsDialog(mcontext, getString(R.string.plzwait));
        dialog.setCancelable(false);
        dialog.show();
        try {

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, mcontext.getResources().getString(R.string.base_url) + "apiLoginMaster/GetLoginMaster?Email=" + input_email.getText().toString() + "&Password=" + input_password.getText().toString() + "&DeviceId=" + Device_id + "&FireBaseId=" + regId, null, new Response.Listener<JSONObject>() {
                //http://furrytrack.com/api/apiLoginMaster/GetLoginMaster?Email=Jaiminpatel@gmail.in&Password=abc@123_4&DeviceId=123&FireBaseId=
                @Override
                public void onResponse(JSONObject jobj) {
                    try {

                        if (dialog.isShowing()) {
                            dialog.cancel();
                        }

                        if (jobj != null && jobj.getString("Success").equalsIgnoreCase("1") || jobj.getString("Success").equalsIgnoreCase("2")) {


                            JSONArray asadqq = jobj.getJSONArray("content");
                            for (int p = 0; p < asadqq.length(); p++) {
                                JSONObject country_valuex = asadqq.getJSONObject(p);


                                ApplicationPreferences.setValue("UserId", country_valuex.getString("UserId"), mcontext);
                                ApplicationPreferences.setValue("Username", country_valuex.getString("Username"), mcontext);
                                ApplicationPreferences.setValue("UserImage", country_valuex.getString("UserImage"), mcontext);
                                ApplicationPreferences.setValue("Email", country_valuex.getString("Email"), mcontext);
                                ApplicationPreferences.setValue("Password", country_valuex.getString("Password"), mcontext);
                                ApplicationPreferences.setValue("ContactNo", country_valuex.getString("ContactNo"), mcontext);
                                ApplicationPreferences.setValue("DeviceId", country_valuex.getString("DeviceId"), mcontext);
                                ApplicationPreferences.setValue("FirebaseId", country_valuex.getString("FirebaseId"), mcontext);
                                ApplicationPreferences.setValue("DeviceInfo", country_valuex.getString("DeviceInfo"), mcontext);
                                ApplicationPreferences.setValue("IsActive", country_valuex.getString("IsActive"), mcontext);
                                ApplicationPreferences.setValue("IsDelete", country_valuex.getString("IsDelete"), mcontext);
                                ApplicationPreferences.setValue("ExtraField_A", country_valuex.getString("ExtraField_A"), mcontext);
                                ApplicationPreferences.setValue("ExtraField_B", country_valuex.getString("ExtraField_B"), mcontext);

                                ApplicationPreferences.setValue("regi", "y", mcontext);
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        } else {

                            Alerter.create(mcontext)
                                    .setTitle(jobj.getString("Headers"))
                                    .setText(jobj.getString("content"))
                                    .setDuration(3500)

                                    .setBackgroundColor(R.color.sub_color)
                                    .setIcon(R.drawable.sad)
                                    .show();
                            Toasty.error(mcontext, jobj.getString("Headers"));

                        }
                        if (dialog.isShowing()) {
                            dialog.cancel();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (dialog.isShowing()) {
                            dialog.cancel();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (dialog.isShowing()) {
                        dialog.cancel();
                    }
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "text/plain");
                    return params;
                }
            };
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            furry_track_Application.getInstance().addToRequestQueue(jsonObjectRequest);
        } catch (Exception e) {

        }
    }


    /*****************************************************************/


    /************************************************/

    void Login() {

        txt_regi.setVisibility(View.VISIBLE);
        txt_login.setVisibility(View.VISIBLE);
        lin_c_password.setVisibility(View.GONE);
        img_blank.setVisibility(View.VISIBLE);
        lin_name.setVisibility(View.GONE);
        lin_contct.setVisibility(View.GONE);
        frm_image.setVisibility(View.GONE);

        btn_register.setVisibility(View.GONE);
        txt_sign_in.setVisibility(View.GONE);

    }

    void sign_up() {

        txt_regi.setVisibility(View.GONE);
        txt_login.setVisibility(View.GONE);
        img_blank.setVisibility(View.GONE);
        lin_c_password.setVisibility(View.VISIBLE);
        frm_image.setVisibility(View.VISIBLE);
        lin_name.setVisibility(View.VISIBLE);
        lin_contct.setVisibility(View.VISIBLE);
        btn_register.setVisibility(View.VISIBLE);
        txt_sign_in.setVisibility(View.VISIBLE);

    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        FragmentManager fm = getSupportFragmentManager();
        Log.d("Count", fm.getBackStackEntryCount() + "");
        if (fm.getBackStackEntryCount() <= 0) {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toasty.warning(this, getString(R.string.backtoexit), Toast.LENGTH_SHORT, true).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            fm.popBackStack();
        }

    }


    @SuppressLint("MissingPermission")
    private String getImeiNumber() {
        final TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //getDeviceId() is Deprecated so for android O we can use getImei() method
            return telephonyManager.getImei();
        } else {
            return telephonyManager.getDeviceId();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            onCaptureImageResult(requestCode, resultCode, data);
        }

    }

    private void onCaptureImageResult(int requestCode, int resultCode, Intent data) {

        Uri resultUri = Uri.parse(data.getData().getPath());

        File imgFile = new File(resultUri.getPath());

        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(resultUri.getPath());

//            img_cep_image.setImageBitmap(myBitmap);


            Glide.with(Login_Activity.this)
                    .load(imgFile.getAbsolutePath())
                    .into(img_doctor_image);

            imagepath1 = "";
            imagepath1 = encodeToBase64(myBitmap, Bitmap.CompressFormat.JPEG, 30);

            Handler handler = new Handler();
            Runnable r = new Runnable() {
                public void run() {
                    try {
                        boolean success = imgFile.delete();
                    } catch (Exception e) {

                    }

                }
            };
            handler.postDelayed(r, 2200);
        }


    }


    private void setMultiShowButton() {

        ImagePicker.with(this)
               // .crop()
                .cropSquare()
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(2000, 1080)
                //Final image resolution will be less than 1080 x 1080(Optional)
                .start(11);


    }


    private void submit_api() {

        dialog = new SpotsDialog(mcontext, getString(R.string.plzwait));
        dialog.setCancelable(false);
        dialog.show();

        try {
            JSONObject object = new JSONObject();
            Map<String, String> params = new HashMap();


            params.put("PostTypeId", "1");
            params.put("Username", input_name.getText().toString());
            params.put("UserImage", imagepath1);
            params.put("Email", input_email.getText().toString());
            params.put("Password", input_password.getText().toString());
            params.put("DeviceId", ApplicationPreferences.getValue("device_id", "", Login_Activity.this));
            params.put("FirebaseId", regId);
            params.put("ContactNo", input_contct.getText().toString());


            object = new JSONObject(params);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, getResources().getString(R.string.post_base_url), object, new com.android.volley.Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jobj) {
                    try {

                        if (dialog.isShowing()) {
                            dialog.cancel();
                        }
                        JSONObject jsonObjetmain = jobj.getJSONObject("InsertImageDataResult");
                        if (jsonObjetmain != null && jsonObjetmain.getString("Success").equalsIgnoreCase("1")) {

                            Toasty.success(mcontext, jsonObjetmain.getString("Exception"));
                            Login();

                        } else if (jsonObjetmain != null && jsonObjetmain.getString("Success").equalsIgnoreCase("0")) {
                            General_Dialog.showDialog(mcontext, jsonObjetmain.getString("Exception"));

                        } else {
                            toast("json null");
                        }


                        if (dialog.isShowing()) {
                            dialog.cancel();
                        }
                    } catch (Exception e) {
                        toast(e + "");
                        e.printStackTrace();
                        if (dialog.isShowing()) {
                            dialog.cancel();
                        }
                    }
                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (dialog.isShowing()) {
                        dialog.cancel();
                    }

                }
            }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "text/plain");
                    return params;
                }
            };
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            furry_track_Application.getInstance().addToRequestQueue(jsonObjectRequest);
        } catch (Exception e) {

        }
    }


}

package com.application.furry_track.Activity;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.application.furry_track.R;

import java.io.ByteArrayOutputStream;


public class Base_Activity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private Dialog dialog;
    Dialog dialog_add;
    Typeface tf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {


            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.white));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.app_color));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        }

        initializeProgressDialog();


    }


    public String setZero(int val) {
        if (val < 10) {
            return "0" + val;
        }
        return "" + val;
    }

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    /*---------------- Initialize Progress Dialog--------------------*/
    private void initializeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }

        progressDialog = new ProgressDialog(Base_Activity.this);
        progressDialog.setIndeterminate(true);
        //progressDialog.setTitle(getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        /*progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                SampleActivity.this.finish();
            }
        });*/
    }


    /**
     * Present the progress dialog.
     *
     * @param messageId The identifier (R.string value) of the string to display in the dialog.
     */

    /*---------------- Display Progress Dialog--------------------*/
    protected void showLoadingDialog(final int messageId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.setMessage(getString(messageId));
                progressDialog.show();
            }
        });
    }

    public void violation_responce_new(String message, Context mContext) {

        androidx.appcompat.app.AlertDialog.Builder alertDialog = new androidx.appcompat.app.AlertDialog.Builder(mContext);

        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        // On pressing Settings button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {


            public void onClick(DialogInterface dialog, int which) {


            }
        });
        alertDialog.show();
    }

    /*---------------- Dismiss Progress Dialog--------------------*/
    protected void dismissLoadingDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        });
    }

    /*---------------- Setting edit text hint color--------------------*/

    public void sethint(EditText edt) {

        edt.setHintTextColor(getResources().getColor(R.color.gray));

    }
    /*



     *//*---------------- Setting edit text Typeface--------------------*//*
    public void settypeface_edittext(EditText edt)
    {    	
    	tf = Typeface.createFromAsset(getAssets(),
    			"fonts/AvenirNextLTPro_Regular.otf");
    	edt.setTypeface(tf);
    }
    
    *//*---------------- Setting textview Typeface--------------------*//*
    public void settypeface_textview(TextView txt)
    {
    	
    	tf = Typeface.createFromAsset(getAssets(),
    			"fonts/AvenirNextLTPro_Regular.otf");
    	txt.setTypeface(tf);
    }
    
    *//*************************//*
    
    public void settypeface_edittext_bold(EditText edt)
    {
    	
    	tf = Typeface.createFromAsset(getAssets(),
    			"fonts/AvenirNextLTPro_Demi.otf");
    	edt.setTypeface(tf);
    }
    
    *//*---------------- Setting textview Typeface--------------------*//*
    public void settypeface_textview_bold(TextView txt)
    {
    	
    	tf = Typeface.createFromAsset(getAssets(),
    			"fonts/AvenirNextLTPro_Demi.otf");
    	txt.setTypeface(tf);
    }  
   */


    /********************************/






    /*---------------- Toast message--------------------*/
    public void toast(String msg) {
        Toast.makeText(Base_Activity.this, msg, Toast.LENGTH_LONG).show();

    }

    /*---------------- Alert message--------------------*/
    /*public void alert(String message)
	{
		 dialog = new Dialog(Base_Activity.this);
		 dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		 dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		 dialog.setContentView(R.layout.dialog_sign_up);
		
		//dialog.setTitle("Hello");
		TextView txt_mess = (TextView) dialog.findViewById(R.id.txt_mess);
		TextView txt_ok = (TextView) dialog.findViewById(R.id.txt_ok);
		
		
		txt_mess.setText(message);
		
		txt_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
		});
		
		//textViewUser.setText("Hi");
		dialog.show();
	}*/
    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) Base_Activity.this
                .getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
        AppCompatActivity act = Base_Activity.this;
        if (act.getCurrentFocus() != null)
            inputMethodManager.hideSoftInputFromWindow(act.getCurrentFocus().getWindowToken(), 0);

    }

    /*---------------- check edit text dialog_empty--------------------*/
    protected boolean isempty_edittext(EditText edt) {
        return edt.getText().length() == 0 && edt.getText().toString().equalsIgnoreCase("");
    }

    /*---------------- check edit text e-mail adress is valid --------------------*/
    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    /*---------------- Connection Detector--------------------*/


    public boolean isNetworkAvailable() {
        NetworkInfo netInfo = null;
        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            netInfo = cm.getActiveNetworkInfo();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public final static boolean isValidPhoneNumber(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            if (target.length() < 10 || target.length() > 12) {
                return false;
            } else {
                return android.util.Patterns.PHONE.matcher(target).matches();
            }
        }
    }

    public final static boolean isValidpin(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            if (target.length() < 6 || target.length() > 7) {
                return false;
            } else {
                return android.util.Patterns.PHONE.matcher(target).matches();
            }
        }
    }


    public void violation_responce(String message, Activity mactivity) {

        androidx.appcompat.app.AlertDialog.Builder alertDialog = new androidx.appcompat.app.AlertDialog.Builder(mactivity);

        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        // On pressing Settings button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {


            public void onClick(DialogInterface dialog, int which) {

                mactivity.onBackPressed();

            }
        });

        alertDialog.show();
    }

    public void internet_connection(Activity mContext) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        // Setting Dialog Title
        alertDialog.setTitle("No Internet Connection !");
        // Setting Dialog Message
        alertDialog.setMessage("Please connect to working internet connection.");
        // On pressing Settings button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }
}

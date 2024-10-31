package com.application.furry_track.Fragment;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.ByteArrayOutputStream;

@SuppressWarnings("ALL")
@SuppressLint("NewApi")
public class Fragment_Base_Fragment extends Fragment {

    private ProgressDialog progressDialog;
    public Dialog dialog_add;
    AlertDialog dialog,dialog_a,dialog_b;
    Typeface tf;
    public ProgressDialog pDialog, pDialog2, pDialog3, pDialog4;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

    }

    public void toast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();

    }

    public boolean isNetworkAvailable() {
        NetworkInfo netInfo = null;
        try {
            ConnectivityManager cm = (ConnectivityManager) getActivity()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            netInfo = cm.getActiveNetworkInfo();
        } catch (SecurityException e) {
            // e.printStackTrace();
        }
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    public boolean isempty_edittext(EditText edt) {
        if (edt.getText().length() == 0 && edt.getText().toString().equalsIgnoreCase("")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isempty_auto(AutoCompleteTextView edt) {
        if (edt.getText().length() == 0 && edt.getText().toString().equalsIgnoreCase("")) {
            return true;
        } else {
            return false;
        }
    }

    public final static boolean isValidPhoneNumber(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            if (target.length() < 10 || target.length() > 14) {
                return false;
            } else {
                return android.util.Patterns.PHONE.matcher(target).matches();
            }
        }
    }



    /*---------------- check edit text e-mail adress is valid --------------------*/
    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    /**************************/


    public void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    String setZero(int val) {
        if (val < 10) {
            return "0" + val;
        }
        return "" + val;
    }



/************************************************************************************************/



/*************************************/


}
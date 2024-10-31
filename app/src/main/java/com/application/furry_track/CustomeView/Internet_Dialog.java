package com.application.furry_track.CustomeView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;


import com.application.furry_track.R;


public class Internet_Dialog {

    public static void showDialog(Context mContext){


         final AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setTitle("Internet Connection Error");
        alertDialog.setMessage("Please connect to working Internet connection");
        alertDialog.setIcon(R.drawable.icon);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();

            }
        });
        alertDialog.show();

    }
}

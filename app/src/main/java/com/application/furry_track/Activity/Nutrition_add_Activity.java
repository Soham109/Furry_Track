package com.application.furry_track.Activity;


import static com.application.furry_track.Activity.MainActivity.PET_ID;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.application.furry_track.CustomeView.General_Dialog;
import com.application.furry_track.R;
import com.application.furry_track.helper.ApplicationPreferences;
import com.application.furry_track.helper.furry_track_Application;
import com.tapadoo.alerter.Alerter;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;


public class Nutrition_add_Activity extends Base_Activity implements View.OnClickListener {

    private Activity mContext;
    TextView textView7, txt_dob_date;
    String Nutrition_Id = "";
    EditText edit_ListText, edit_Spend, edit_remarks;
    LinearLayout lin_dob_date;
    Dialog dialog, dialog_a;
    private static final int REQUEST_CAMERA = 0;
    int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_nutrition_activity);
        init();
    }

    private void init() {
        mContext = Nutrition_add_Activity.this;
        Toolbar toolbar = findViewById(R.id.toolbar_login);
        toolbar.setTitleTextColor(getColor(R.color.dashboard_text));
        toolbar.setSubtitleTextColor(getColor(R.color.dashboard_text));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        edit_ListText = findViewById(R.id.edit_ListText);
        edit_Spend = findViewById(R.id.edit_Spend);
        edit_remarks = findViewById(R.id.edit_remarks);

        lin_dob_date = findViewById(R.id.lin_dob_date);
        lin_dob_date.setOnClickListener(this);

        textView7 = findViewById(R.id.textView7);
        txt_dob_date = findViewById(R.id.txt_dob_date);
        textView7.setOnClickListener(this);
        getSupportActionBar().setTitle(getString(R.string.add_nutrition));
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            try {




                Nutrition_Id = bundle.getString("NutritionId", "");
                edit_Spend.setText(bundle.getString("Spend", ""));
                edit_ListText.setText(bundle.getString("ListText", ""));
                edit_remarks.setText(bundle.getString("Remarks", ""));
              //  txt_dob_date.setText(bundle.getString("Spenddate", ""));

                try {

                    DateFormat inputFormat = new SimpleDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss");
                    DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = inputFormat.parse(bundle.getString("Spenddate", ""));
                    txt_dob_date.setText(outputFormat.format(date));

                } catch (ParseException e) {
                    //   throw new RuntimeException(e);
                }

                getSupportActionBar().setTitle(getString(R.string.update_nutrition));




            } catch (Exception e) {
            }
        }




    }

    @Override
    public void onClick(View v) {
        if (v == textView7) {
            if (!isempty_edittext(edit_ListText)) {
                if (isNetworkAvailable()) {

                    submit_api();
                } else {
                    Alerter.create(mContext)
                            .setTitle(getString(R.string.nointernet))
                            .setText(getString(R.string.check_internet))
                            .setDuration(3500)
                            .setBackgroundColor(R.color.colorPrimary)
                            .setIcon(R.drawable.no_internet)
                            .show();
                }
            } else {
                edit_ListText.requestFocus();
                edit_ListText.setError(getResources().getString(R.string.en_item_list));
            }
        } else if (v == lin_dob_date) {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    txt_dob_date.setText(setZero(dayOfMonth) + "/" + setZero((monthOfYear + 1)) + "/" + year);
                }
            }, mYear, mMonth, mDay);
            //    datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {

            onBackPressed();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        //   intent.putExtra("checker", checker);
        startActivity(intent);
        finish();
    }


    /********************** final post       *************************************************/

    private void submit_api() {

        dialog = new SpotsDialog(mContext, getString(R.string.plzwait));
        dialog.setCancelable(false);
        dialog.show();

        try {
            JSONObject object = new JSONObject();
            Map<String, String> params = new HashMap();


            params.put("PostTypeId", "3");
            params.put("PetId", PET_ID);
            params.put("Spenddate", txt_dob_date.getText().toString());
            params.put("UserId", ApplicationPreferences.getValue("UserId", "", mContext));
            params.put("Remarks", edit_remarks.getText().toString());
            params.put("ListText", edit_ListText.getText().toString());
            params.put("Spend", edit_Spend.getText().toString());
            params.put("PetAge", "");

            if (Nutrition_Id != null && !Nutrition_Id.trim().isEmpty() && !Nutrition_Id.trim().equalsIgnoreCase("")) {
                params.put("NutritionId", Nutrition_Id);
                params.put("IsEdit", "2");
            } else {
                params.put("IsEdit", "1");
            }

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
                            violation_responce(jsonObjetmain.getString("Exception"));

                        } else if (jsonObjetmain != null && jsonObjetmain.getString("Success").equalsIgnoreCase("0")) {
                            General_Dialog.showDialog(mContext, jsonObjetmain.getString("Exception"));

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

    /****************************/


    public void violation_responce(String message) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        // AlertDialog.Builder adb = new AlertDialog.Builder(this);
        //  Dialog d = alertDialog.setView(new View(this)).create();

        // Setting Dialog Title
        //alertDialog.setTitle("No Internet Connection !");

        // Setting Dialog Message
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        // On pressing Settings button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {


            public void onClick(DialogInterface dialog, int which) {
                onBackPressed();


            }
        });


        alertDialog.show();
    }


    /****************************************************************************************/

    /*****************************************************************************************/


}

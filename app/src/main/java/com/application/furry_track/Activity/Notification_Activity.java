package com.application.furry_track.Activity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.application.furry_track.Adapter.Adapter_notificatio;
import com.application.furry_track.R;
import com.application.furry_track.get_set.notification_get_set;
import com.application.furry_track.helper.ApplicationPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import es.dmoral.toasty.Toasty;


public class Notification_Activity extends Base_Activity {

    RecyclerView recycler_view_noti;
    public String result;
    private Context mContext;
    SpotsDialog dialog;
    ImageView img_menu_active_cs;
    AsyncTask<Void, Void, Void> task;

    TextView empty;
    public static Adapter_notificatio mAdaptergrid;
    ArrayList<notification_get_set> active_requ_array = new ArrayList<>();
    public static String user = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        bindid();
    }


    private void bindid() {
        // TODO Auto-generated method stub

        mContext = Notification_Activity.this;
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getColor(R.color.dashboard_text));
        toolbar.setSubtitleTextColor(getColor(R.color.dashboard_text));
        setSupportActionBar(toolbar);
        //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.notification));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle extras = getIntent().getExtras();
      /*  if (extras != null) {
            user = extras.getString("key");
        }
      */
        ApplicationPreferences.setIntValue("noti_count", 0, Notification_Activity.this);
        //   cart_count.setText(ApplicationPreferences.getIntValue("noti_count", Notification_Activity.this) + "");

        recycler_view_noti = findViewById(R.id.rec_add_gift);
        @SuppressLint("WrongConstant") LinearLayoutManager layoutManagers
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycler_view_noti.setLayoutManager(layoutManagers);

        try {
            ApplicationPreferences.setIntValue("notifi_count", 0, getApplicationContext());
        } catch (Exception e) {

        }
        if (isNetworkAvailable()) {
            get_notification();
        } else {
            Toasty.error(mContext, R.string.check_internet).show();

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
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (task != null && task.getStatus() != AsyncTask.Status.FINISHED) {
            task.cancel(true);
        }


    }


    /***********************************          GET Notification                 ***********************************************************************************/
/*    public void get_notification() {
        try {
            active_requ_array.clear();
            dialog = new SpotsDialog(Notification_Activity.this, getString(R.string.plzwait));
            dialog.setCancelable(true);
            dialog.show();
            String url = "";

            url = getResources().getString(R.string.base_url) + "ApiNotification/GetNotification?UserId=" + ApplicationPreferences.getValue("EmployeeId", "", Notification_Activity.this);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                //  JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, getResources().getString(R.string.base_url) + "GetRequirements?referenceSchemeId=2", null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jobj) {
                    try {

                        if (jobj != null && jobj.getString("success").equalsIgnoreCase("true")) {
                            JSONArray jArray_cat = jobj.getJSONArray("content");

                            for (int i = 0; i < jArray_cat.length(); i++) {
                                JSONObject json_data = jArray_cat.getJSONObject(i);
                                notification_get_set gridtype = new notification_get_set();

                                gridtype.NotificationId = json_data.getString("NotificationId");
                                gridtype.Body = json_data.getString("Body");
                                if (json_data.getString("Title").equalsIgnoreCase("null")) {
                                    gridtype.Title = "";
                                } else {
                                    gridtype.Title = json_data.getString("Title");
                                }


                                active_requ_array.add(gridtype);

                            }


                            recycler_view_noti.setVisibility(View.VISIBLE);
                            mAdaptergrid = new Adapter_notificatio(Notification_Activity.this, active_requ_array);
                            recycler_view_noti.setAdapter(mAdaptergrid);


                        } else {

                            Toasty.error(mContext, jobj.getString("message")).show();


                        }
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                }
            }, new Response.ErrorListener() {


                @Override
                public void onErrorResponse(VolleyError error) {
// txtRegister.setClickable(true);
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    Log.v("PayNowRequestResponse", error.toString());
                }
            });
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            state_cyber_Application.getInstance().addToRequestQueue(jsonObjectRequest);

        } catch (Exception e) {
            e.printStackTrace();
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }

    }*/


    /********************************************************/

    private void get_notification() {
        dialog = new SpotsDialog(mContext, getString(R.string.plzwait));
        dialog.setCancelable(false);
        dialog.show();


        active_requ_array.clear();

        RequestQueue queue = Volley.newRequestQueue(mContext);

        String Url = "";
        if (ApplicationPreferences.getValue("Flag", "", mContext).toString().equalsIgnoreCase("1")) {
            Url = mContext.getResources().getString(R.string.base_url) + "ApiNotificationPolice/getdata?DistrictId=" + ApplicationPreferences.getValue("DistrictId", "", mContext);
        } else {
            Url = mContext.getResources().getString(R.string.base_url) + "ApiNotification/getdata?VolunteerId=" + ApplicationPreferences.getValue("VolunteerId", "", mContext);
        }


        StringRequest request = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (dialog.isShowing()) {
                    dialog.cancel();
                }
                try {
                    JSONObject jobj = new JSONObject(response);
                    if (jobj != null && jobj.getString("Success").equalsIgnoreCase("1")) {

                        JSONArray asad = jobj.getJSONArray("content");
                        for (int j = 0; j < asad.length(); j++) {
                            JSONObject asas = asad.getJSONObject(j);
                            notification_get_set banner_d = new notification_get_set();
                            banner_d.NotificationId = asas.getString("NotificationId");
                            banner_d.Title = asas.getString("Title");
                            banner_d.Description = asas.getString("Description");
                            banner_d.Todate = asas.getString("Todate");
                            banner_d.IsActive = asas.getString("IsActive");
                            banner_d.IsDelete = asas.getString("IsDelete");
                            banner_d.DistrictId = asas.getString("DistrictId");
                            banner_d.DistrictName = asas.getString("DistrictName");
                            banner_d.CreatedDate = asas.getString("CreatedDate");
                            banner_d.VolunteerId = asas.getString("VolunteerId");
                            banner_d.Name = asas.getString("Name");
                            banner_d.ContactNo = asas.getString("ContactNo");
                            banner_d.DateOfRegistration = asas.getString("DateOfRegistration");
                            banner_d.CurrentStatus = asas.getString("CurrentStatus");
                            banner_d.VolunteerUnlawful = asas.getString("VolunteerUnlawful");
                            banner_d.Expert = asas.getString("Expert");
                            banner_d.AwarnessPromoter = asas.getString("AwarnessPromoter");
                            banner_d.EmployeeId = asas.getString("EmployeeId");
                            banner_d.BuckleNo = asas.getString("BuckleNo");
                            banner_d.EmployeName = asas.getString("EmployeName");
                            banner_d.TaskId = asas.getString("TaskId");
                            banner_d.Expr1 = asas.getString("Expr1");
                            banner_d.Expr2 = asas.getString("Expr2");
                            banner_d.Expr3 = asas.getString("Expr3");

                            active_requ_array.add(banner_d);
                        }

                        recycler_view_noti.setVisibility(View.VISIBLE);
                        mAdaptergrid = new Adapter_notificatio(Notification_Activity.this, active_requ_array);
                        recycler_view_noti.setAdapter(mAdaptergrid);
                    } else {
                        Toasty.error(mContext, jobj.getString("message"), Toast.LENGTH_SHORT, true).show();
                        recycler_view_noti.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toasty.error(mContext, e + "", Toast.LENGTH_SHORT, true).show();
                    if (dialog.isShowing()) {
                        dialog.cancel();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                if (dialog.isShowing()) {
                    dialog.cancel();
                }
            }
        }) {
            /*  @Override
              protected Map<String, String> getParams() {
                  Map<String, String> params = new HashMap<String, String>();
                  params.put("otp_code", edit_otp.getText().toString());
                  params.put("auth_type", ApplicationPreferences.getValue("auth_type", "", mContext));
                  params.put("country_code", "91");
                  params.put("phone", edt_mobile.getText().toString());
                  return params;
              }*/
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + ApplicationPreferences.getValue("token", "", mContext));
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

}

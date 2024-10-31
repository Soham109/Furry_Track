package com.application.furry_track.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.application.furry_track.Activity.Grooming_add_Activity;
import com.application.furry_track.CustomeView.General_Dialog;
import com.application.furry_track.R;
import com.application.furry_track.get_set.f_grooming_get_set;
import com.application.furry_track.helper.furry_track_Application;
import com.tapadoo.alerter.Alerter;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;


public class Adapter_Grooming_List extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    ArrayList<f_grooming_get_set> data;

    private Activity context;
    private LayoutInflater inflater;

    AlertDialog dialog;

    public Adapter_Grooming_List(Activity activity, ArrayList<f_grooming_get_set> data) {

        this.context = activity;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    // Inflate the layou`t when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_video, parent, false);
        MyHolder holder = new MyHolder(view);


        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        MyHolder myHolder = (MyHolder) holder;
        //  myHolder.txt_time.setText("sdfgsdf");


        myHolder.txt_title.setText(data.get(position).getListText());
      //  myHolder.txt_date.setText(data.get(position).getSpenddate());
        myHolder.txt_amount.setText(data.get(position).getSpend());
        myHolder.txt_remarks.setText(data.get(position).getRemarks());



        try {

            DateFormat inputFormat = new SimpleDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss");
            DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = inputFormat.parse(data.get(position).getSpenddate());
            myHolder.txt_date.setText(outputFormat.format(date));

        } catch (ParseException e) {
            //   throw new RuntimeException(e);
        }
        myHolder.img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentds = new Intent(context.getApplicationContext(), Grooming_add_Activity.class);
                intentds.putExtra("pet_id", data.get(position).getPetId());
                intentds.putExtra("NutritionId", data.get(position).getGroomId());
                intentds.putExtra("ListText", data.get(position).getListText());
                intentds.putExtra("Spend", data.get(position).getSpend());
                intentds.putExtra("Spenddate", data.get(position).getSpenddate());
                intentds.putExtra("Remarks", data.get(position).getRemarks());

                context.startActivity(intentds);
                context.finish();


            }
        });

        myHolder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                    builder1.setMessage(context.getString(R.string.logoutss));
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(context.getString(R.string.yes),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    if (isNetworkAvailable()) {
                                        submit_api(data.get(position).getGroomId());

                                    } else {

                                        Alerter.create(context)
                                                .setTitle(context.getString(R.string.nointernet))
                                                .setText(context.getString(R.string.check_internet))
                                                .setDuration(3500)
                                                .setBackgroundColor(R.color.colorPrimary)
                                                .setIcon(R.drawable.no_internet)
                                                .show();

                                    }
                                }
                            });
                    builder1.setNegativeButton(context.getString(R.string.no), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int arg1) {
                            // TODO Auto-generated method stub
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });


     /*   myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(context.getApplicationContext(), Webview_Activity.class);
                    intent.putExtra("title", data.get(position).getTitle());
                    intent.putExtra("page_url", data.get(position).getURL());
                    intent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                    context.startActivity(intent);
                } catch (Exception e) {

                }
            }
        });*/
    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {


        TextView txt_title, txt_date, txt_amount, txt_remarks;
        ImageView img_edit, img_delete;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            txt_title = itemView.findViewById(R.id.txt_title);
            txt_date = itemView.findViewById(R.id.txt_date);
            txt_amount = itemView.findViewById(R.id.txt_amount);
            txt_remarks = itemView.findViewById(R.id.txt_remarks);

            img_edit = itemView.findViewById(R.id.img_edit);
            img_delete = itemView.findViewById(R.id.img_delete);


        }
    }

    public boolean isNetworkAvailable() {
        NetworkInfo netInfo = null;
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            netInfo = cm.getActiveNetworkInfo();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    private void submit_api(String pp_id) {

        dialog = new SpotsDialog(context, context.getString(R.string.plzwait));
        dialog.setCancelable(false);
        dialog.show();

        try {
            JSONObject object = new JSONObject();
            Map<String, String> params = new HashMap();


            params.put("IsEdit", "3");
            params.put("GroomId", pp_id);
            params.put("PostTypeId", "4");


            object = new JSONObject(params);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, context.getResources().getString(R.string.post_base_url), object, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jobj) {
                    try {

                        JSONObject jsonObjetmain = jobj.getJSONObject("InsertImageDataResult");
                        if (jsonObjetmain != null && jsonObjetmain.getString("Success").equalsIgnoreCase("1")) {
                            context.finish();
                            context.startActivity(context.getIntent());

                        } else if (jsonObjetmain != null && jsonObjetmain.getString("Success").equalsIgnoreCase("0")) {
                            General_Dialog.showDialog(context, jsonObjetmain.getString("Exception"));

                        } else {
                            Toast.makeText(context, "json null", Toast.LENGTH_SHORT).show();

                        }

                        if (dialog.isShowing()) {
                            dialog.cancel();
                        }
                    } catch (Exception e) {
                        Toast.makeText(context, e + "", Toast.LENGTH_SHORT).show();
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


}

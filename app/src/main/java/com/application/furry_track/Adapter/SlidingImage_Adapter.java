package com.application.furry_track.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.viewpager.widget.PagerAdapter;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.application.furry_track.Activity.Pet_add_Activity;
import com.application.furry_track.CustomeView.General_Dialog;
import com.application.furry_track.CustomeView.Image_show_Dialog;
import com.application.furry_track.R;
import com.application.furry_track.get_set.f_pet_get_set;
import com.application.furry_track.helper.furry_track_Application;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tapadoo.alerter.Alerter;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dmax.dialog.SpotsDialog;


public class SlidingImage_Adapter extends PagerAdapter {


    //private ArrayList<String> IMAGES;
    AlertDialog dialog;
    List<f_pet_get_set> IMAGES = Collections.emptyList();
    private LayoutInflater inflater;
    private Activity context;
    Dialog dialog_add;

    public SlidingImage_Adapter(Activity context, List<f_pet_get_set> Gridview_typedata) {

        this.context = context;
        this.IMAGES = Gridview_typedata;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return IMAGES.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

        assert imageLayout != null;
        ImageView image = imageLayout.findViewById(R.id.image);
        TextView txt_comment = imageLayout.findViewById(R.id.txt_comment);
        TextView txt_dob = imageLayout.findViewById(R.id.txt_dob);
        TextView txt_dob_aa = imageLayout.findViewById(R.id.txt_dob_aa);

        ImageView img_edit = imageLayout.findViewById(R.id.img_edit);

        ImageView img_delete = imageLayout.findViewById(R.id.img_delete);


        img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentds = new Intent(context.getApplicationContext(), Pet_add_Activity.class);
                intentds.putExtra("pet_id", IMAGES.get(position).getPetId());
                intentds.putExtra("pet_name", IMAGES.get(position).getPetName());
                intentds.putExtra("dob", IMAGES.get(position).getDOB());
                intentds.putExtra("BreedId", IMAGES.get(position).getBreedId());
                intentds.putExtra("BreedName", IMAGES.get(position).getBreedName());
                intentds.putExtra("TypeName", IMAGES.get(position).getTypeName());
                intentds.putExtra("TypeId", IMAGES.get(position).getTypeId());
                intentds.putExtra("remark", IMAGES.get(position).getRemarks());
                intentds.putExtra("pet_image", IMAGES.get(position).getPetImage());

                intentds.putExtra("BreedOther", IMAGES.get(position).getBreedOther());
                intentds.putExtra("TypeOther", IMAGES.get(position).getTypeOther());


                context.startActivity(intentds);
                context.finish();


            }
        });

        img_delete.setOnClickListener(new View.OnClickListener() {
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
                                        submit_api(IMAGES.get(position).getPetId());

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

        //   txt_comment.setTextColor(context.getColor(R.color.white));

        //imageView.setBackground(context.getResources().getDrawable(IMAGES.get(position).getBackground_image()));
        //  imageView.setBackgroundResource(IMAGES.get(position).getPetImage());


        // String s=resultp.get("images");
        Glide.with(context).load(context.getString(R.string.image_base_url) + IMAGES.get(position).getPetImage())
                //.placeholder(R.drawable.images)
                .error(R.drawable.pet)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .skipMemoryCache(true)
                .into(image);


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Image_show_Dialog.show_big_Image(context.getString(R.string.image_base_url) + IMAGES.get(position).getPetImage(), context);
                } catch (Exception e) {

                }

            }
        });

        txt_comment.setText(IMAGES.get(position).getPetName());
        txt_dob_aa.setText(IMAGES.get(position).getBreedName() + " / " + IMAGES.get(position).getTypeName());

        if (IMAGES.get(position).getTypeId().equalsIgnoreCase("6") && IMAGES.get(position).getBreedId().equalsIgnoreCase("11")) {
            txt_dob_aa.setText(IMAGES.get(position).getBreedOther() + " / " + IMAGES.get(position).getTypeOther());
        } else if (IMAGES.get(position).getTypeId().equalsIgnoreCase("6")) {
            txt_dob_aa.setText(IMAGES.get(position).getBreedName() + " / " + IMAGES.get(position).getTypeOther());
        } else if (IMAGES.get(position).getBreedId().equalsIgnoreCase("11")) {
            txt_dob_aa.setText(IMAGES.get(position).getBreedOther() + " / " + IMAGES.get(position).getTypeName());
        }


        //      txt_dob.setText(IMAGES.get(position).getDOB());

        try {

            DateFormat inputFormat = new SimpleDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss");
            DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = inputFormat.parse(IMAGES.get(position).getDOB());
            txt_dob.setText(outputFormat.format(date));

        } catch (ParseException e) {
            // throw new RuntimeException(e);
        }


        view.addView(imageLayout, 0);
       /* imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              *//*      if (IMAGES.get(position).banner_page_url != null && !IMAGES.get(position).banner_page_url.isEmpty() && !IMAGES.get(position).banner_page_url.equals("null")&& !IMAGES.get(position).banner_page_url.equalsIgnoreCase("")){
                        Intent intent = new Intent(context.getApplicationContext(), Webview_Activity.class);
                        intent.putExtra("title", "demo");
                        intent.putExtra("page_url", IMAGES.get(position).banner_page_url+ "");
                        //intent.addFlags( intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                        context.startActivity(intent);
                    }else {
                        show_big_Image(IMAGES.get(position).banner_image_url);
                    }
*//*


                //  show_big_Image(IMAGES.get(position).banner_image_url);
            }
        });*/

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


    private void submit_api(String pp_id) {

        dialog = new SpotsDialog(context, context.getString(R.string.plzwait));
        dialog.setCancelable(false);
        dialog.show();

        try {
            JSONObject object = new JSONObject();
            Map<String, String> params = new HashMap();


            params.put("IsEdit", "3");
            params.put("PetId", pp_id);
            params.put("PostTypeId", "2");


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


}
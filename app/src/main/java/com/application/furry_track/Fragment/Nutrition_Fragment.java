package com.application.furry_track.Fragment;

import static com.application.furry_track.Activity.MainActivity.PET_ID;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import com.application.furry_track.Activity.Nutrition_add_Activity;
import com.application.furry_track.Adapter.Adapter_Nutrition_List;
import com.application.furry_track.CustomeView.WrappableGridLayoutManager;
import com.application.furry_track.R;
import com.application.furry_track.get_set.f_nutrition_get_set;
import com.application.furry_track.helper.furry_track_Application;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tapadoo.alerter.Alerter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;


public class Nutrition_Fragment extends Fragment_Base_Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Activity mcontext;
    SpotsDialog dialog;
    FloatingActionButton flot_add;
    RecyclerView rec_add_gift;

    public static Nutrition_Fragment newInstance(String param1, String param2) {
        Nutrition_Fragment fragment = new Nutrition_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for getActivity() fragment
        View view = inflater.inflate(R.layout.fragment_vedio, container, false);
        init(view);
        return view;

    }

    void init(View view) {
        mcontext = getActivity();
        rec_add_gift = view.findViewById(R.id.rec_add_gift);

        RecyclerView.LayoutManager lm = new WrappableGridLayoutManager(getActivity(), 1);
        rec_add_gift.setLayoutManager(lm);
        rec_add_gift.setNestedScrollingEnabled(false);

        flot_add = view.findViewById(R.id.flot_add);

        flot_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentds = new Intent(getActivity().getApplicationContext(), Nutrition_add_Activity.class);
                /*intentds.putExtra("pet_id", IMAGES.get(position).getPetId());
                intentds.putExtra("pet_name", IMAGES.get(position).getPetName());
                intentds.putExtra("dob", IMAGES.get(position).getDOB());
                intentds.putExtra("BreedId", IMAGES.get(position).getBreedId());
                intentds.putExtra("BreedName", IMAGES.get(position).getBreedName());
                intentds.putExtra("TypeName", IMAGES.get(position).getTypeName());
                intentds.putExtra("TypeId", IMAGES.get(position).getTypeId());
                intentds.putExtra("remark", IMAGES.get(position).getRemarks());
                intentds.putExtra("pet_image", IMAGES.get(position).getPetImage());*/
                getActivity().startActivity(intentds);
                getActivity().finish();
            }
        });

        if (isNetworkAvailable()) {
            get_banner();
        } else {
            Alerter.create(mcontext)
                    .setTitle(getString(R.string.nointernet))
                    .setText(getString(R.string.check_internet))
                    .setDuration(3500)
                    .setBackgroundColor(R.color.sub_color)
                    .show();

        }

    }

    private void get_banner() {
        dialog = new SpotsDialog(mcontext, getString(R.string.plzwait));
        final ArrayList<f_nutrition_get_set> data_list_home = new ArrayList<>();
        dialog.setCancelable(false);
        dialog.show();
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, mcontext.getResources().getString(R.string.base_url) + "apiNutrition/GetNutrition?PetId=" + PET_ID, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jobj) {
                    try {
                        // data_list_home.clear();

                        if (jobj != null && jobj.getString("Success").equalsIgnoreCase("true")) {
                            JSONArray jArray_cat = jobj.getJSONArray("content");
                            for (int i = 0; i < jArray_cat.length(); i++) {
                                JSONObject json_data = jArray_cat.getJSONObject(i);

                                f_nutrition_get_set gift = new f_nutrition_get_set();
                                gift.NutritionId = json_data.getString("NutritionId");
                                gift.PetId = json_data.getString("PetId");
                                gift.PetName = json_data.getString("PetName");
                                gift.PetImage = json_data.getString("PetImage");
                                gift.PetAge = json_data.getString("PetAge");
                                gift.ListText = json_data.getString("ListText");
                                gift.Spend = json_data.getString("Spend");
                                gift.Spenddate = json_data.getString("Spenddate");
                                gift.Remarks = json_data.getString("Remarks");
                                gift.CreatedDate = json_data.getString("CreatedDate");


                                data_list_home.add(gift);


                            }
                            final Adapter_Nutrition_List mAdapterlist = new Adapter_Nutrition_List(mcontext, data_list_home);
                            rec_add_gift.setAdapter(mAdapterlist);

                        } else {
                            final Adapter_Nutrition_List mAdapterlist = new Adapter_Nutrition_List(mcontext, data_list_home);
                            rec_add_gift.setAdapter(mAdapterlist);
                            //  onBackPressed();
                              //  Toasty.error(mcontext, jobj.getString("Headers"), Toast.LENGTH_SHORT, true).show();
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
                private VolleyError error;


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
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            furry_track_Application.getInstance().addToRequestQueue(jsonObjectRequest);
        } catch (Exception e) {

        }


    }

}


package com.application.furry_track.Activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.application.furry_track.Fragment.Grooming_Fragment;
import com.application.furry_track.Fragment.Medicale_Fragment;
import com.application.furry_track.Fragment.Nutrition_Fragment;
import com.application.furry_track.get_set.f_pet_get_set;

import com.application.furry_track.Adapter.SlidingImage_Adapter;
import com.application.furry_track.BuildConfig;
import com.application.furry_track.R;
import com.application.furry_track.get_set.Services_home;
import com.application.furry_track.helper.ApplicationPreferences;
import com.google.android.material.tabs.TabLayout;
import com.tapadoo.alerter.Alerter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import es.dmoral.toasty.Toasty;
import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends Base_Activity implements View.OnClickListener {

    AlertDialog dialog, dialog_a, dialog_d;
    String[] count;
    int[] main_background_image;
    ArrayList<f_pet_get_set> mainBannerData_Array = new ArrayList<>();
    public static String PET_ID = "";
    private FrameLayout imageContainer;
    public static String main_color = "#07617D";
    public static String sub_color = "#2E383F";
    public static String black_color = "#000000";
    private boolean doubleBackToExitPressedOnce = false;
    Activity mcontext;
   // ImageView imag_arrow;

    ArrayList<Services_home> data_list_home = new ArrayList<>();


    ViewPager mPager;
    int currentPage = 0;
    int NUM_PAGES = 0;
    Dialog dialog_add;
    String imagepath1 = "";//, CompleteVisit = "";
    //   LinearLayout lin_date;
    Toolbar toolbar;
    String versionName = "";

    private TabLayout tabLayout;

    private ViewPager viewPager;
    private int[] tabIcons = {
            // R.drawable.info,

            R.drawable.nutretion,
            R.drawable.grooming,
            R.drawable.medical

    };


    //SwipeRefreshLayout swipeContainer;
    //NestedScrollView scrollview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind_id();
    }


    void bind_id() {
        mcontext = MainActivity.this;
     //   imag_arrow = findViewById(R.id.imag_arrow);
        toolbar = findViewById(R.id.toolbar_login);

        toolbar.setTitleTextColor(getColor(R.color.dashboard_text));
        toolbar.setSubtitleTextColor(getColor(R.color.dashboard_text));

        setSupportActionBar(toolbar);


        //  getSupportActionBar().setSubtitle(ApplicationPreferences.getValue("EmployeName", "", mcontext));
        // getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        //   swipeContainer = findViewById(R.id.swipeContainer);
        //   scrollview = findViewById(R.id.scrollview);
        // Setup refresh listener which triggers new data loading
       /* swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                call_main_api();
                swipeContainer.setRefreshing(false);

            }
        });
*/
        /**************    completed     *****************/


        //  getSupportActionBar().setIcon(R.drawable.toolbar_icon);


        try {
            versionName = BuildConfig.VERSION_NAME;
            //  txt_current_version.setText("App Version : " + versionName);
        } catch (Exception e) {

        }
        call_main_api();
    }


    void call_main_api() {
        if (isNetworkAvailable()) {
            get_dashboard();

        } else {

            Alerter.create(mcontext)
                    .setTitle(getString(R.string.nointernet))
                    .setText(getString(R.string.check_internet))
                    .setDuration(3500)
                    .setBackgroundColor(R.color.colorPrimary)
                    .setIcon(R.drawable.no_internet)
                    .show();

        }

      /*  imag_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                } catch (Exception e) {

                }

            }
        });*/
    }


    /*******************************************************/
    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            default:
                break;
        }
    }

    /**************************************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);

        final MenuItem menuItem = menu.findItem(R.id.notification_aaa);
        //  final MenuItem add = menu.findItem(R.id.add);

        View actionView = menuItem.getActionView();
        TextView titlesss = actionView.findViewById(R.id.titlesss);


        titlesss.setText(ApplicationPreferences.getIntValue("noti_count", MainActivity.this) + "");


        titlesss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentd = new Intent(getApplicationContext(), Notification_Activity.class);

                startActivity(intentd);
            }
        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {


            case R.id.notification_aaa:
                Intent intentd = new Intent(getApplicationContext(), Notification_Activity.class);
                startActivity(intentd);

                return true;

            case R.id.add:
                Intent intentds = new Intent(getApplicationContext(), Pet_add_Activity.class);
                startActivity(intentds);
                finish();

                return true;

            case R.id.logout:

                try {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(mcontext);
                    builder1.setMessage(mcontext.getString(R.string.logouts));
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(mcontext.getString(R.string.yes),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    ApplicationPreferences.clearAll(MainActivity.this);
                                    Intent login = new Intent(MainActivity.this, Login_Activity.class);
                                    startActivity(login);
                                    finish();

                                }
                            });
                    builder1.setNegativeButton(mcontext.getString(R.string.no), new DialogInterface.OnClickListener() {
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


                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /***************************             Load home page         ************************************/

    /**************************************************************************************************************************/
    /**************************************************************************************************************************/
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


    private void get_dashboard() {
        dialog = new SpotsDialog(mcontext, getString(R.string.plzwait));
        dialog.setCancelable(false);
        dialog.show();
        mainBannerData_Array.clear();
        data_list_home.clear();

        RequestQueue queue = Volley.newRequestQueue(mcontext);
        StringRequest request = new StringRequest(Request.Method.GET, mcontext.getResources().getString(R.string.base_url) + "apiPetRegistration/GetPetDetails?UserId=" + ApplicationPreferences.getValue("UserId", "", mcontext), new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jobj = new JSONObject(response);
                    if (jobj != null && jobj.getString("Success").equalsIgnoreCase("true")) {


                        JSONArray asadqq = jobj.getJSONArray("content");
                        for (int p = 0; p < asadqq.length(); p++) {
                            JSONObject asasdd = asadqq.getJSONObject(p);


                            f_pet_get_set banner_ds = new f_pet_get_set();

                            banner_ds.PetId = asasdd.getString("PetId");
                            banner_ds.PetName = asasdd.getString("PetName");
                            banner_ds.PetImage = asasdd.getString("PetImage");
                            banner_ds.PetAge = asasdd.getString("PetAge");
                            banner_ds.DOB = asasdd.getString("DOB");
                            banner_ds.Remarks = asasdd.getString("Remarks");
                            banner_ds.CreatedDate = asasdd.getString("CreatedDate");
                            banner_ds.UserId = asasdd.getString("UserId");
                            banner_ds.BreedId = asasdd.getString("BreedId");
                            banner_ds.BreedName = asasdd.getString("BreedName");
                            banner_ds.TypeId = asasdd.getString("TypeId");
                            banner_ds.TypeName = asasdd.getString("TypeName");

                            banner_ds.BreedOther = asasdd.getString("BreedOther");
                            banner_ds.TypeOther = asasdd.getString("TypeOther");


                            mainBannerData_Array.add(banner_ds);


                        }


                        banner();

                        PET_ID = "";
                        PET_ID = mainBannerData_Array.get(0).getPetId();

                        viewPager = findViewById(R.id.viewpager);
                        addTabs(viewPager);
                        tabLayout = findViewById(R.id.tabs);
                        tabLayout.setupWithViewPager(viewPager);
                        setupTabIcons();

                        //    home();

                    } else if (jobj.getString("Success").equalsIgnoreCase("false")) {

                        Intent login = new Intent(MainActivity.this, Pet_add_Activity.class);
                        startActivity(login);
                        finish();
                        Toasty.warning(mcontext, jobj.getString("Headers"));
                        //  violation_responce_new(jobj.getString("Headers"), mcontext);
                    } else {
                        /*ApplicationPreferences.clearAll(MainActivity.this);
                        Intent login = new Intent(MainActivity.this, Login_Activity.class);
                        startActivity(login);
                        finish();*/

                        violation_responce_new(jobj.getString("Headers"), mcontext);

                    }
                } catch (Exception e) {
                    e.printStackTrace();

                    Toasty.error(mcontext, e + "", Toast.LENGTH_SHORT, true).show();
                    if (dialog.isShowing()) {
                        dialog.cancel();
                    }
                }
                if (dialog.isShowing()) {
                    dialog.cancel();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                if (dialog.isShowing()) {
                    dialog.cancel();
                }
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + ApplicationPreferences.getValue("token", "", mcontext));
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }


    /*******************************************************************************/


    /********************************************************/
    private void banner() {
        currentPage = 0;
        NUM_PAGES = 0;
        mPager = findViewById(R.id.pager);
        mPager.setAdapter(new SlidingImage_Adapter(MainActivity.this, mainBannerData_Array));
        CircleIndicator indicator = findViewById(R.id.indicator);
        indicator.setViewPager(mPager);
        NUM_PAGES = mainBannerData_Array.size();
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        if (mainBannerData_Array.size() == 1) {
            indicator.setVisibility(View.GONE);
         //   imag_arrow.setVisibility(View.GONE);
        } else {
            indicator.setVisibility(View.VISIBLE);
          //  imag_arrow.setVisibility(View.VISIBLE);
        }



       /* Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);*/

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                currentPage = position;

            }

            @Override
            public void onPageSelected(int position) {

                String f = "";
                PET_ID = "";
                PET_ID = mainBannerData_Array.get(position).getPetId();
                addTabs(viewPager);
                setupTabIcons();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void setApplicationLocale(String locale) {

        ApplicationPreferences.setValue("lang_set", locale, getApplicationContext());
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(new Locale(locale.toLowerCase()));
        } else {
            config.locale = new Locale(locale.toLowerCase());
        }
        resources.updateConfiguration(config, dm);
        recreate();


    }


    public void violation_responce_aa(String message, Activity mactivity) {

        androidx.appcompat.app.AlertDialog.Builder alertDialog = new androidx.appcompat.app.AlertDialog.Builder(mactivity);

        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        // On pressing Settings button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {


            public void onClick(DialogInterface dialog, int which) {
                finish();
                startActivity(getIntent());


            }
        });

        alertDialog.show();
    }

    /**********************       completed       *************/
    private void setupTabIcons() {
        // tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);

    }

    private void addTabs(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        //adapter.addFrag(new Other_Info_Activity(), getString(R.string.community));
        adapter.addFrag(new Nutrition_Fragment(), getString(R.string.Nutrition));
        adapter.addFrag(new Grooming_Fragment(), getString(R.string.Grooming));
        adapter.addFrag(new Medicale_Fragment(), getString(R.string.Medical_history));


        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
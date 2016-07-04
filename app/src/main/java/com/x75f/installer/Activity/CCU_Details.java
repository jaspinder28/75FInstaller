package com.x75f.installer.Activity;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.api.client.json.GenericJson;
import com.kinvey.android.AsyncAppData;
import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyListCallback;
import com.kinvey.android.callback.KinveyUserCallback;
import com.kinvey.java.Logger;
import com.kinvey.java.Query;
import com.kinvey.java.User;
import com.x75f.installer.DB_Local.SQLliteAdapter;
import com.x75f.installer.Fragments.DamperTestFragment;
import com.x75f.installer.Fragments.DataLogFragment;
import com.x75f.installer.Fragments.NotesFragment;
import com.x75f.installer.Fragments.Summary_Fragment;
import com.x75f.installer.Fragments.SystemTestFragment;
import com.x75f.installer.R;
import com.x75f.installer.Utils.Generic_Methods;

import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class CCU_Details extends AppCompatActivity implements View.OnClickListener {

    public static ViewPager viewPager;
    String ccuname;
    String ccuid;
    public static Button bSummary;
    public static Toolbar tool_bar;
    public static Button bDatalog;
    public static Button bSystem;
    public static Button bDamper;
    public static Button bNotes;
    public static int currentPage;
    private static Context _singleton;
    public SQLliteAdapter sqLliteAdapter;

    public static Context getSingletonContext() {
        return _singleton;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_pages);
        viewPager = (ViewPager) findViewById(R.id.ViewPager);
        bSummary = (Button) findViewById(R.id.bSummary);
        bDatalog = (Button) findViewById(R.id.bDatalog);
        bSystem = (Button) findViewById(R.id.bSystem);
        bDamper = (Button) findViewById(R.id.bDamper);
        bNotes = (Button) findViewById(R.id.bNotes);
        tool_bar = (Toolbar) findViewById(R.id.tool_bar);

        setSupportActionBar(tool_bar);
        getSupportActionBar().setTitle("Select CCU");
        tool_bar.setNavigationIcon(getResources().getDrawable(R.mipmap.back));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        tool_bar.getNavigationIcon().setTint(getResources().getColor(R.color.white));
        tool_bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
//        setSupportActionBar(tool_bar);


        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(CCU_Details.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(CCU_Details.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(CCU_Details.this,
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
            }
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        _singleton = this;
        sqLliteAdapter = new SQLliteAdapter(CCU_Details.this);
        viewPager.setOffscreenPageLimit(5);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        bSummary.setOnClickListener(this);
        bDatalog.setOnClickListener(this);
        bSystem.setOnClickListener(this);
        bDamper.setOnClickListener(this);
        bNotes.setOnClickListener(this);
        ccuname = getIntent().getStringExtra("CcuData");
        ccuid = getIntent().getStringExtra("ccu_id");
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                currentPage = position;
            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case (R.id.bSummary):
                bSummary.setTextColor(getResources().getColor(R.color.primary));
                bSummary.setCompoundDrawablesRelativeWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.summaryc), null, null);
                bDatalog.setTextColor(getResources().getColor(R.color.gray));
                bDatalog.setCompoundDrawablesRelativeWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.datalog), null, null);
                bSystem.setTextColor(getResources().getColor(R.color.gray));
                bSystem.setCompoundDrawablesRelativeWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.systemtest), null, null);
                bDamper.setTextColor(getResources().getColor(R.color.gray));
                bDamper.setCompoundDrawablesRelativeWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.damper), null, null);
                bNotes.setTextColor(getResources().getColor(R.color.gray));
                bNotes.setCompoundDrawablesRelativeWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.notes), null, null);
                viewPager.setCurrentItem(0, false);
                break;
            case (R.id.bDatalog):
                bSummary.setTextColor(getResources().getColor(R.color.gray));
                bSummary.setCompoundDrawablesRelativeWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.summary), null, null);
                bDatalog.setTextColor(getResources().getColor(R.color.primary));
                bDatalog.setCompoundDrawablesRelativeWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.datalogc), null, null);
                bSystem.setTextColor(getResources().getColor(R.color.gray));
                bSystem.setCompoundDrawablesRelativeWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.systemtest), null, null);
                bDamper.setTextColor(getResources().getColor(R.color.gray));
                bDamper.setCompoundDrawablesRelativeWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.damper), null, null);
                bNotes.setTextColor(getResources().getColor(R.color.gray));
                bNotes.setCompoundDrawablesRelativeWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.notes), null, null);
                viewPager.setCurrentItem(1, false);
                break;
            case (R.id.bSystem):
                checkForOtpRow(2);
                break;
            case (R.id.bDamper):
                checkForOtpRow(3);
                break;
            case (R.id.bNotes):
                bSummary.setTextColor(getResources().getColor(R.color.gray));
                bSummary.setCompoundDrawablesRelativeWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.summary), null, null);
                bDatalog.setTextColor(getResources().getColor(R.color.gray));
                bDatalog.setCompoundDrawablesRelativeWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.datalog), null, null);
                bSystem.setTextColor(getResources().getColor(R.color.gray));
                bSystem.setCompoundDrawablesRelativeWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.systemtest), null, null);
                bDamper.setTextColor(getResources().getColor(R.color.gray));
                bDamper.setCompoundDrawablesRelativeWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.damper), null, null);
                bNotes.setTextColor(getResources().getColor(R.color.primary));
                bNotes.setCompoundDrawablesRelativeWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.notesc), null, null);
                viewPager.setCurrentItem(4, false);
                break;
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                Thread h = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sqLliteAdapter.updateEntire();
                    }
                });
                h.run();
                SharedPreferences prefs = CCU_Details.this.getSharedPreferences("login", Context.MODE_PRIVATE);
                prefs.edit().clear().apply();
                Intent i1 = new Intent(CCU_Details.this, MainActivity.class);
                startActivity(i1);
                finish();
                return true;
            case R.id.cPassword:

                return true;
            case R.id.Call_Support:

                return true;
            case R.id.Help:

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int position) {
            Bundle b = new Bundle();
            b.putString("ccuname", ccuname);
            b.putString("ccu_id", ccuid);
            Fragment fragment = null;

            switch (position) {
                case 0:
                    fragment = new Summary_Fragment();
                    break;
                case 1:
                    fragment = new DataLogFragment();
                    break;
                case 2:
                    fragment = new SystemTestFragment();
                    break;
                case 3:
                    fragment = new DamperTestFragment();
                    break;
                case 4:
                    fragment = new NotesFragment();
                    break;

            }


            assert fragment != null;
            fragment.setArguments(b);
            return fragment;
        }

        @Override
        public int getCount() {
            return 5;

        }

    }


    public void checkForOtpRow(final int x) {


        Query newquery = new Query();
        newquery.equals("_id", ccuid);
        if (Generic_Methods.isNetworkAvailable(CCU_Details.getSingletonContext())) {
            AsyncAppData<GenericJson> summary = Generic_Methods.getKinveyClient().appData("00CCUOneTimePassword", GenericJson.class);
            summary.get(newquery, new KinveyListCallback<GenericJson>() {
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
                @Override
                public void onSuccess(GenericJson[] genericJsons) {
                    try {
                        if (genericJsons.length == 0) {
                            Otp_Verification otp_verification = new Otp_Verification(CCU_Details.getSingletonContext(), ccuid, x);
                            otp_verification.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            otp_verification.show();
                            sqLliteAdapter.update(ccuid, 0);
                        } else if (genericJsons.length == 1) {

                            try {
                                JSONObject s = new JSONObject(genericJsons[0].toString());
                                if (s.getString("oneTimePassword") != null && sqLliteAdapter.getdata(ccuid) == 1) {
                                    viewPager.setCurrentItem(x, false);
                                    if (x == 2) {
                                        bSummary.setTextColor(getResources().getColor(R.color.gray));
                                        bSummary.setCompoundDrawablesRelativeWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.summary), null, null);
                                        bDatalog.setTextColor(getResources().getColor(R.color.gray));
                                        bDatalog.setCompoundDrawablesRelativeWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.datalog), null, null);
                                        bSystem.setTextColor(getResources().getColor(R.color.primary));
                                        bSystem.setCompoundDrawablesRelativeWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.systemtestc), null, null);
                                        bDamper.setTextColor(getResources().getColor(R.color.gray));
                                        bDamper.setCompoundDrawablesRelativeWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.damper), null, null);
                                        bNotes.setTextColor(getResources().getColor(R.color.gray));
                                        bNotes.setCompoundDrawablesRelativeWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.notes), null, null);
                                    } else {
                                        bSummary.setTextColor(getResources().getColor(R.color.gray));
                                        bSummary.setCompoundDrawablesRelativeWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.summary), null, null);
                                        bDatalog.setTextColor(getResources().getColor(R.color.gray));
                                        bDatalog.setCompoundDrawablesRelativeWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.datalog), null, null);
                                        bSystem.setTextColor(getResources().getColor(R.color.gray));
                                        bSystem.setCompoundDrawablesRelativeWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.systemtest), null, null);
                                        bDamper.setTextColor(getResources().getColor(R.color.primary));
                                        bDamper.setCompoundDrawablesRelativeWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.damperc), null, null);
                                        bNotes.setTextColor(getResources().getColor(R.color.gray));
                                        bNotes.setCompoundDrawablesRelativeWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.notes), null, null);
                                    }
                                } else {
                                    sqLliteAdapter.update(ccuid, 0);
                                    Otp_Verification otp_verification = new Otp_Verification(CCU_Details.getSingletonContext(), ccuid, x);
                                    otp_verification.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                    otp_verification.show();
                                }

                            } catch (Exception e) {
                                sqLliteAdapter.update(ccuid, 0);
                            }
                        }

                    } catch (Exception e) {
                        Otp_Verification otp_verification = new Otp_Verification(CCU_Details.getSingletonContext(), ccuid, x);
                        otp_verification.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        otp_verification.show();
                        sqLliteAdapter.update(ccuid, 0);
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Throwable throwable) {

                    Otp_Verification otp_verification = new Otp_Verification(CCU_Details.getSingletonContext(), ccuid, x);
                    otp_verification.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    otp_verification.show();
                    sqLliteAdapter.update(ccuid, 0);


                }
            });
        } else {
            Generic_Methods.getToast(CCU_Details.getSingletonContext(), getResources().getString(R.string.user_offline));
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        Generic_Methods.createEditSummarySharedPreference(CCU_Details.getSingletonContext(), "");
        Generic_Methods.createEditDataLogSharedPreference(CCU_Details.getSingletonContext(), "");
        Generic_Methods.PauseCalled();
        Generic_Methods.PauseCalled1();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Generic_Methods.h.removeCallbacks(Generic_Methods.mThraed);

        Thread h = new Thread(new Runnable() {
            @Override
            public void run() {
                Generic_Methods.unbindDrawables(findViewById(R.id.main_layout));
            }
        });
        h.start();

    }
}


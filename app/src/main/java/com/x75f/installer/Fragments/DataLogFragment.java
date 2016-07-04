package com.x75f.installer.Fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.json.GenericJson;
import com.google.gson.GsonBuilder;
import com.kinvey.android.AsyncAppData;
import com.kinvey.android.callback.KinveyListCallback;
import com.kinvey.java.Query;
import com.kinvey.java.query.AbstractQuery;
import com.x75f.installer.Activity.CCU_Details;
import com.x75f.installer.Adapters.Data_Log_Zone_Adapter;
import com.x75f.installer.R;
import com.x75f.installer.Utils.Data_Log;
import com.x75f.installer.Utils.Generic_Methods;
import com.x75f.installer.Utils.Helper;
import com.x75f.installer.Utils.Zone_log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class DataLogFragment extends Fragment {


    ArrayList<Zone_log> zone_list;
    private static ProgressDialog Pleasewait;
    @InjectView(R.id.nodata)
    TextView nodata;
    @InjectView(R.id.ZoneList)
    ListView ZoneList;
    Handler h = new Handler();
    Runnable update;
    String ccuName;
    View v;

    public static ArrayList<ArrayList<Zone_log>> mainlist;
    public static ArrayList<String> ccuMainData;
    private static int count = 0;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.data_log_fragment, container, false);
        ButterKnife.inject(this, v);
        ccuName = getArguments().getString("ccuname");
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onPause() {
        super.onPause();
        h.removeCallbacks(update);
        Generic_Methods.PauseCalled1();
    }

    @Override
    public void onResume() {
        super.onResume();
        ccuMainData = new ArrayList<>();
        mainlist = new ArrayList<>();
        zone_list = new ArrayList<>();
        count = 0;
        if(CCU_Details.getSingletonContext() != null && CCU_Details.viewPager.getCurrentItem() ==1){
            h.removeCallbacks(update);
            Generic_Methods.PauseCalled1();
            getData();
            Generic_Methods.PauseCalled();
            Generic_Methods.FetchDatalogData(getArguments().getString("ccu_id"));
            update = new Runnable() {
                @Override
                public void run() {
                    String s = Generic_Methods.getStringPreference(CCU_Details.getSingletonContext(), "data", "datalog");
                    QueryDataAgain(s);
                    h.postDelayed(update, 60000);
//                    scrollView.smoothScrollTo(0, 0);
                    if (Pleasewait != null && Pleasewait.isShowing()) {
                        Pleasewait.dismiss();
                        Pleasewait = null;
                    }
                }
            };
            h.postDelayed(update, 60000);
        }
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {

        super.setMenuVisibility(menuVisible);
        ccuMainData = new ArrayList<>();
        mainlist = new ArrayList<>();
        zone_list = new ArrayList<>();
        count = 0;

        if (DataLogFragment.this != null && isVisible() && CCU_Details.viewPager.getCurrentItem() == 1) {
            h.removeCallbacks(update);
            Generic_Methods.PauseCalled1();
            getData();
            Generic_Methods.PauseCalled();
            Generic_Methods.FetchDatalogData(getArguments().getString("ccu_id"));
            update = new Runnable() {
                @Override
                public void run() {
                    String s = Generic_Methods.getStringPreference(CCU_Details.getSingletonContext(), "data", "datalog");
                    QueryDataAgain(s);
                    h.postDelayed(update, 60000);
//                    scrollView.smoothScrollTo(0, 0);
                    if (Pleasewait != null && Pleasewait.isShowing()) {
                        Pleasewait.dismiss();
                        Pleasewait = null;
                    }
                }
            };
            h.postDelayed(update, 60000);
        } else {
            h.removeCallbacks(update);

        }
    }


    public void QueryDataAgain(String st) {
        try {
            if (isVisible() && CCU_Details.viewPager.getCurrentItem() == 1) {
                if (count < 10) {
                    count++;
                } else if (count == 10) {
                    mainlist.remove(0);
                    ccuMainData.remove(0);
                }

                if (Generic_Methods.isNetworkAvailable(CCU_Details.getSingletonContext())) {
                    if (Pleasewait == null) {
                        Pleasewait = ProgressDialog.show(CCU_Details.getSingletonContext(), "", "Please Wait...");
                    }
                    if (st == null || st.equalsIgnoreCase("")) {
                        nodata.setVisibility(View.VISIBLE);
//                        scrollView.setVisibility(View.GONE);
                        if (Pleasewait != null && Pleasewait.isShowing()) {
                            Pleasewait.dismiss();
                            Pleasewait = null;
                        }
                    } else {
                        if (nodata.getVisibility() == View.VISIBLE) {
                            nodata.setVisibility(View.GONE);
//                            scrollView.setVisibility(View.VISIBLE);
                        }
                        settingUpValues setting = new settingUpValues();
                        setting.execute(st);
                    }


                } else {
                    Generic_Methods.getToast(CCU_Details.getSingletonContext(), getResources().getString(R.string.user_offline));
                    if (Pleasewait != null && Pleasewait.isShowing()) {
                        Pleasewait.dismiss();
                        Pleasewait = null;
                    }
                }
            } else {
                h.removeCallbacks(update);
                Generic_Methods.PauseCalled1();
                if (Pleasewait != null && Pleasewait.isShowing()) {
                    Pleasewait.dismiss();
                    Pleasewait = null;
                }
            }
        } catch (Exception e) {
            Generic_Methods.getToast(CCU_Details.getSingletonContext(), e.getMessage());
        }
    }


    public class settingUpValues extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                final JSONObject s1 = new JSONObject(s);
                if (isVisible() && CCU_Details.viewPager.getCurrentItem() == 1) {
                    try {
                        Query newquery = new Query();
                        String collectionName = getArguments().getString("ccu_id") + "ZonesTS1";
                        newquery.setLimit(10);
                        newquery.equals("date_time", s1.optString("date_time"));
                        if (Generic_Methods.isNetworkAvailable(CCU_Details.getSingletonContext())) {
                            AsyncAppData<Zone_log> summary = Generic_Methods.getKinveyClient().appData(collectionName, Zone_log.class);
                            summary.get(newquery, new KinveyListCallback<Zone_log>() {
                                @Override
                                public void onSuccess(final Zone_log[] zone_logs) {

                                    if (Pleasewait != null && Pleasewait.isShowing()) {
                                        Pleasewait.dismiss();
                                        Pleasewait = null;
                                    }
                                    zone_list.addAll(Arrays.asList(zone_logs));

                                    if (count <= 10) {
                                        mainlist.add(new ArrayList<>(zone_list));
                                        ccuMainData.add(new String(s1.toString()));
                                        Data_Log_Zone_Adapter zones_adapter = new Data_Log_Zone_Adapter(CCU_Details.getSingletonContext(), mainlist, count, ccuMainData, ccuName);
                                        ZoneList.setAdapter(zones_adapter);
                                    }
                                    zone_list.clear();


                                }

                                @Override
                                public void onFailure(final Throwable throwable) {

                                    if (Pleasewait != null && Pleasewait.isShowing()) {
                                        Pleasewait.dismiss();
                                        Pleasewait = null;
                                    }
                                    Generic_Methods.getToast(CCU_Details.getSingletonContext(), throwable.getMessage());


                                }


                            });
                        } else {
                            if (Pleasewait != null && Pleasewait.isShowing()) {
                                Pleasewait.dismiss();
                                Pleasewait = null;
                            }
                        }
                    } catch (Exception e) {
                        Log.d("JSONException", e.getMessage());
                        if (Pleasewait != null && Pleasewait.isShowing()) {
                            Pleasewait.dismiss();
                            Pleasewait = null;
                        }

                        Generic_Methods.getToast(CCU_Details.getSingletonContext(), e.getMessage());
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                if (Pleasewait != null && Pleasewait.isShowing()) {
                    Pleasewait.dismiss();
                    Pleasewait = null;
                }
            }
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                JSONObject S = new JSONObject(params[0]);
            } catch (JSONException e) {
                e.printStackTrace();
                if (Pleasewait != null && Pleasewait.isShowing()) {
                    Pleasewait.dismiss();
                    Pleasewait = null;
                }
            }
            return params[0];
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Thread h = new Thread(new Runnable() {
            @Override
            public void run() {
                Generic_Methods.unbindDrawables(v.findViewById(R.id.main_layout));
            }
        });
        h.start();

    }

    public void getData() {
        if (Generic_Methods.isNetworkAvailable(CCU_Details.getSingletonContext())) {
            Query newquery = new Query();
            String collectionName = getArguments().getString("ccu_id") + "SystemTS1";
            newquery.addSort("date_time", AbstractQuery.SortOrder.DESC);
            newquery.setLimit(1);
            AsyncAppData<Data_Log> summary = Generic_Methods.getKinveyClient().appData(collectionName, Data_Log.class);
            if (summary.isOnline()) {

                summary.get(newquery, new KinveyListCallback<Data_Log>() {

                    @Override
                    public void onSuccess(Data_Log[] data_logs) {
                        if (data_logs.length != 0) {
                            QueryDataAgain(data_logs[0].toString());
                        } else {
                            QueryDataAgain("");
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.e("result", "failed");
                    }
                });
            }
        } else {
            Generic_Methods.getToast(CCU_Details.getSingletonContext(), getResources().getString(R.string.user_offline));
            if (Pleasewait != null && Pleasewait.isShowing()) {
                Pleasewait.dismiss();
                Pleasewait = null;
            }
        }
    }
}

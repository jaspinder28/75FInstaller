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
import android.widget.TextView;

import com.kinvey.android.AsyncAppData;
import com.kinvey.android.callback.KinveyListCallback;
import com.kinvey.java.Query;
import com.kinvey.java.query.AbstractQuery;
import com.x75f.installer.Activity.CCU_Details;
import com.x75f.installer.Adapters.Data_Log_Zone_Adapter;
import com.x75f.installer.R;
import com.x75f.installer.Utils.Data_Log;
import com.x75f.installer.Utils.Generic_Methods;
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
        dismissDialog();
        if (CCU_Details.getSingletonContext().datalogHandler != null && CCU_Details.getSingletonContext().datalogUpdate != null) {
            CCU_Details.getSingletonContext().datalogHandler.removeCallbacks(CCU_Details.getSingletonContext().datalogUpdate);
            CCU_Details.getSingletonContext().datalogHandler = null;
            CCU_Details.getSingletonContext().datalogUpdate = null;
            Generic_Methods.PauseCalledDatalog();
//            Generic_Methods.PauseCalledSummary();
            Log.e("onpause", "threadcancel");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ccuMainData = new ArrayList<>();
        mainlist = new ArrayList<>();
        zone_list = new ArrayList<>();
        count = 0;
        if (CCU_Details.getSingletonContext() != null && CCU_Details.getSingletonContext().viewPager.getCurrentItem() == 1) {
            Log.e("onresume", "onresume");
            CCU_Details.getSingletonContext().datalogHandler = new Handler();
            dismissDialog();
            if (Generic_Methods.isNetworkAvailable(CCU_Details.getSingletonContext())) {
                FetchData();
            } else {
                Generic_Methods.getToast(CCU_Details.getSingletonContext(), getResources().getString(R.string.user_offline));
            }


        }

    }


    public void QueryDataAgain(String st) {

            if (isVisible() && CCU_Details.getSingletonContext().viewPager.getCurrentItem() == 1) {
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
                        dismissDialog();
                    } else {
                        if (nodata.getVisibility() == View.VISIBLE) {
                            nodata.setVisibility(View.GONE);
                        }
                        settingUpValues(st);
                    }


                } else {
                    Generic_Methods.getToast(CCU_Details.getSingletonContext(), getResources().getString(R.string.user_offline));
                    dismissDialog();
                }
            }


    }


    public void settingUpValues(String data) {
        try {
            final JSONObject s1 = new JSONObject(data);
            Query newquery = new Query();
            String collectionName = getArguments().getString("ccu_id") + "ZonesTS1";
            newquery.setLimit(10);
            newquery.equals("date_time", s1.optString("date_time"));
            if (Generic_Methods.isNetworkAvailable(CCU_Details.getSingletonContext())) {
                AsyncAppData<Zone_log> summary = Generic_Methods.getKinveyClient().appData(collectionName, Zone_log.class);
                summary.get(newquery, new KinveyListCallback<Zone_log>() {
                    @Override
                    public void onSuccess(final Zone_log[] zone_logs) {

                        setList(zone_logs, s1.toString());
                        dismissDialog();
                    }


                    @Override
                    public void onFailure(final Throwable throwable) {
                        Log.e("fail", throwable.getMessage());
                        dismissDialog();
                    }

                });
            } else {
                Generic_Methods.getToast(CCU_Details.getSingletonContext(), getResources().getString(R.string.user_offline));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void setList(Zone_log[] zone_logs, String s1) {
        dismissDialog();
        zone_list.addAll(Arrays.asList(zone_logs));
        if (count <= 10) {
            mainlist.add(new ArrayList<>(zone_list));
            ccuMainData.add(new String(s1.toString()));
            Data_Log_Zone_Adapter zones_adapter = new Data_Log_Zone_Adapter(CCU_Details.getSingletonContext(), mainlist, count, ccuMainData, ccuName);
            ZoneList.setAdapter(zones_adapter);
        }
        zone_list.clear();
    }

    public void FetchData() {
        if (Generic_Methods.isNetworkAvailable(CCU_Details.getSingletonContext())) {
            if (Pleasewait == null) {
                Pleasewait = ProgressDialog.show(CCU_Details.getSingletonContext(), "", "Please Wait...");
            }
            Query newquery = new Query();
            String collectionName = getArguments().getString("ccu_id") + "SystemTS1";
            newquery.addSort("date_time", AbstractQuery.SortOrder.DESC);
            newquery.setLimit(1);
            AsyncAppData<Data_Log> summary = Generic_Methods.getKinveyClient().appData(collectionName, Data_Log.class);
            if (summary.isOnline()) {

                summary.get(newquery, new KinveyListCallback<Data_Log>() {

                    @Override
                    public void onSuccess(final Data_Log[] data_logs) {

                        dismissDialog();
                        if (data_logs.length == 0) {
                            getData("");
                        } else {
                            getData(data_logs[0].toString());
                        }


                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.e("result", "failed");
                        dismissDialog();
                    }
                });
            } else {
                Generic_Methods.getToast(CCU_Details.getSingletonContext(), getResources().getString(R.string.user_offline));
            }
        }
    }


    public void getData(String genericJson) {
        dismissDialog();
        if (genericJson != null && !genericJson.equalsIgnoreCase("")) {
            QueryDataAgain(genericJson);
        } else {
            QueryDataAgain("");
        }

        Generic_Methods.FetchDatalogData(getArguments().getString("ccu_id"));
        CCU_Details.getSingletonContext().datalogUpdate = new Runnable() {
            @Override
            public void run() {

                String s = Generic_Methods.getStringPreference(CCU_Details.getSingletonContext(), "data", "datalog");
                QueryDataAgain(s);
                if(CCU_Details.getSingletonContext().datalogHandler != null) {
                    CCU_Details.getSingletonContext().datalogHandler.postDelayed(CCU_Details.getSingletonContext().datalogUpdate, 60000);
                }else{
                    CCU_Details.getSingletonContext().datalogHandler = new Handler();
                    CCU_Details.getSingletonContext().datalogHandler.postDelayed(CCU_Details.getSingletonContext().datalogUpdate, 60000);
                }
                dismissDialog();


            }
        };
        if(CCU_Details.getSingletonContext().datalogHandler != null) {
            CCU_Details.getSingletonContext().datalogHandler.postDelayed(CCU_Details.getSingletonContext().datalogUpdate, 60000);
        }

    }


    public void dismissDialog() {
        if (Pleasewait != null) {
            if (Pleasewait.isShowing()) {
                Pleasewait.dismiss();
                Pleasewait = null;
            }
        }
    }


}

package com.x75f.installer.Fragments;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.json.GenericJson;
import com.kinvey.android.AsyncAppData;
import com.kinvey.android.callback.KinveyListCallback;
import com.kinvey.java.Query;
import com.x75f.installer.Activity.CCU_Details;
import com.x75f.installer.Activity.Otp_Verification;
import com.x75f.installer.Adapters.Damper_test_Adapter;
import com.x75f.installer.R;
import com.x75f.installer.Utils.Damper_test_row_data;
import com.x75f.installer.Utils.Generic_Methods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class DamperTestFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {
    @InjectView(R.id.alldampers)
    public CheckBox alldampers;
    @InjectView(R.id.damper_position_common)
    TextView damper_position_common;
    @InjectView(R.id.seekbar_common)
    SeekBar seekbar_common;
    @InjectView(R.id.zone_list)
    ListView zone_list;
    private static ProgressDialog Pleasewait;
    String ccuname;
    View v;
    ArrayList<Damper_test_row_data> zoneDatas;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ccuname = getArguments().getString("ccuname");
        v = inflater.inflate(R.layout.damper_test_fragment, container, false);
        ButterKnife.inject(this, v);
        seekbar_common.setOnSeekBarChangeListener(this);
        return v;
    }

//    @Override
//    public void setMenuVisibility(boolean menuVisible) {
//        super.setMenuVisibility(menuVisible);
//
//    }


    @Override
    public void onPause() {
        super.onPause();
        dismissDialog();
//        Generic_Methods.getToast(CCU_Details.getSingletonContext(),"ONPAUSEDAMPERTEST");
        if (CCU_Details.getSingletonContext().damperTestHandler != null && CCU_Details.getSingletonContext().damperTestUpdate != null) {
            CCU_Details.getSingletonContext().damperTestHandler.removeCallbacks(CCU_Details.getSingletonContext().damperTestUpdate);
            CCU_Details.getSingletonContext().damperTestHandler = null;
            CCU_Details.getSingletonContext().damperTestUpdate = null;

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isVisible() && CCU_Details.getSingletonContext().viewPager.getCurrentItem() == 3) {
//            Generic_Methods.getToast(CCU_Details.getSingletonContext(),"ONRESUMEDAMPERTEST");
            Log.d("dampertest", "yes");
            CCU_Details.getSingletonContext().damperTestHandler = new Handler();
            QuerySummaryData();
        }
    }

    public void QuerySummaryData() {
        if (isVisible() && CCU_Details.getSingletonContext().viewPager.getCurrentItem() == 3) {
            Query newquery = new Query();
            newquery.equals("ccu_name", ccuname);
            if (Generic_Methods.isNetworkAvailable(CCU_Details.getSingletonContext())) {
                if (Generic_Methods.getKinveyClient().user().isUserLoggedIn()) {
                    if (Pleasewait == null) {
                        Pleasewait = ProgressDialog.show(CCU_Details.getSingletonContext(), "", "Please Wait...");
                    }
//                Generic_Methods.getToast(CCU_Details.getSingletonContext(),"DAMPERDATA");
                    AsyncAppData<GenericJson> summary = Generic_Methods.getKinveyClient().appData("00CCUSummary", GenericJson.class);

                    summary.get(newquery, new KinveyListCallback<GenericJson>() {
                        @Override
                        public void onSuccess(GenericJson[] genericJsons) {
                            dismissDialog();
                            String Summarydata = genericJsons[0].toString();
                            UpdateData(Summarydata);

                        }

                        @Override
                        public void onFailure(Throwable throwable) {
                            dismissDialog();
                            Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Generic_Methods.ping();
                }
            } else {
                Generic_Methods.getToast(CCU_Details.getSingletonContext(), getResources().getString(R.string.user_offline));
            }
        }
    }


    public void UpdateData(String Summarydata) {
        try {
            checkForOtpEveryMinute();
            JSONObject s = new JSONObject(Summarydata);
            String zone_summary = s.getString("zone_summary");
            JSONArray zone_summarydata = new JSONArray(zone_summary);
            zoneDatas = new ArrayList<>();
            for (int i = 0; i < zone_summarydata.length(); i++) {
                JSONObject object = new JSONObject(zone_summarydata.get(i).toString());
                if (!object.getString("name").equalsIgnoreCase("CO") && !object.getString("name").equalsIgnoreCase("NO2") && !object.getString("name").equalsIgnoreCase("Pressure Sensor")) {
                    zoneDatas.add(new Damper_test_row_data(object.getString("name"), object.getInt("damper_pos"), object.getInt("fsv_address")));
                }
            }

            if (zoneDatas.size() != 0) {
                Damper_test_Adapter zones_adapter = new Damper_test_Adapter(CCU_Details.getSingletonContext(), zoneDatas, getArguments().getString("ccu_id"));
                zone_list.setAdapter(zones_adapter);
            }
        } catch (JSONException e) {
            Log.d("dampertestjsonerror", e.getMessage());
        }
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        damper_position_common.setText(progress + "");
        if (alldampers.isChecked()) {
            ArrayList<Damper_test_row_data> zonedata1 = new ArrayList<>();
            for (int i = 0; i < zoneDatas.size(); i++) {
                zonedata1.add(new Damper_test_row_data(zoneDatas.get(i).getName(), progress, zoneDatas.get(i).getFsv_address()));
            }
            Damper_test_Adapter zones_adapter = new Damper_test_Adapter(CCU_Details.getSingletonContext(), zonedata1, getArguments().getString("ccu_id"));
            zone_list.setAdapter(zones_adapter);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (alldampers.isChecked()) {
            if (Generic_Methods.isNetworkAvailable(CCU_Details.getSingletonContext())) {
                for (int i = 0; i < zoneDatas.size(); i++) {
                    String channel = getArguments().getString("ccu_id") + "_Installer_DMPTEST";
                    String msg = Generic_Methods.createPubnubSystemDamperMsg(seekBar.getProgress(), zoneDatas.get(i).getFsv_address());
                    Generic_Methods.PublishToChannel(channel, msg);

                }
            } else {
                Generic_Methods.getToast(CCU_Details.getSingletonContext(), getResources().getString(R.string.user_offline));
            }
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

    public void checkForOtpEveryMinute() {
        if (CCU_Details.getSingletonContext().damperTestHandler == null) {
            CCU_Details.getSingletonContext().damperTestHandler = new Handler();
        }

        CCU_Details.getSingletonContext().damperTestUpdate = new Runnable() {
            @Override
            public void run() {
                if (CCU_Details.getSingletonContext() != null && CCU_Details.getSingletonContext().viewPager.getCurrentItem() == 3) {
                    Log.e("checkingotpdamper", "yes");
                    if (Generic_Methods.isNetworkAvailable(CCU_Details.getSingletonContext())) {
                        if (Generic_Methods.getKinveyClient().user().isUserLoggedIn()) {
                            Query newquery = new Query();
                            newquery.equals("_id", getArguments().getString("ccu_id"));
                            AsyncAppData<GenericJson> summary = Generic_Methods.getKinveyClient().appData("00CCUOneTimePassword", GenericJson.class);

                            summary.get(newquery, new KinveyListCallback<GenericJson>() {

                                @Override
                                public void onSuccess(GenericJson[] genericJsons) {
                                    if (genericJsons.length == 0) {
                                        Otp_Verification otp_verification = new Otp_Verification(CCU_Details.getSingletonContext(), getArguments().getString("ccu_id"), 3);
                                        otp_verification.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        otp_verification.show();
                                    } else if (genericJsons.length == 1) {
                                        try {
                                            JSONObject s = new JSONObject(genericJsons[0].toString());
                                            if (!Generic_Methods.getDataFromOtpBasedOnCcuId(getArguments().getString("ccu_id")).optvalue.equalsIgnoreCase(s.getString("oneTimePassword"))) {
                                                Otp_Verification otp_verification = new Otp_Verification(CCU_Details.getSingletonContext(), getArguments().getString("ccu_id"), 2);
                                                otp_verification.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                                otp_verification.show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Throwable throwable) {
                                    Otp_Verification otp_verification = new Otp_Verification(CCU_Details.getSingletonContext(), getArguments().getString("ccu_id"), 3);
                                    otp_verification.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                    otp_verification.show();
                                }
                            });

                        } else {
                            Generic_Methods.ping();
                        }

                    } else {
                        Generic_Methods.getToast(CCU_Details.getSingletonContext(), getResources().getString(R.string.user_offline));
                    }
                    if (CCU_Details.getSingletonContext().damperTestHandler != null) {
                        CCU_Details.getSingletonContext().damperTestHandler.postDelayed(CCU_Details.getSingletonContext().damperTestUpdate, 60000);
                    }
                }

            }
        }

        ;

        if (CCU_Details.getSingletonContext().damperTestHandler != null)

        {
            CCU_Details.getSingletonContext().damperTestHandler.postDelayed(CCU_Details.getSingletonContext().damperTestUpdate, 60000);
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

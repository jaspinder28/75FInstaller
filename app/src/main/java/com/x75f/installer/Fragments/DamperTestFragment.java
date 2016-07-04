package com.x75f.installer.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
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

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (isVisible() && CCU_Details.viewPager.getCurrentItem() == 3) {
            Generic_Methods.PauseCalled();
            Generic_Methods.PauseCalled1();
            Log.d("dampertest", "yes");
            QuerySummaryData();
        }
    }

    public void QuerySummaryData() {
        if (isVisible() && CCU_Details.viewPager.getCurrentItem() == 3) {
            Query newquery = new Query();
            newquery.equals("ccu_name", ccuname);
            if (Generic_Methods.isNetworkAvailable(CCU_Details.getSingletonContext())) {
                if (Pleasewait == null) {
                    Pleasewait = ProgressDialog.show(CCU_Details.getSingletonContext(), "", "Please Wait...");
                }

                AsyncAppData<GenericJson> summary = Generic_Methods.getKinveyClient().appData("00CCUSummary", GenericJson.class);
                summary.get(newquery, new KinveyListCallback<GenericJson>() {
                    @Override
                    public void onSuccess(GenericJson[] genericJsons) {
                        try {
                            if (Pleasewait != null && Pleasewait.isShowing()) {
                                Pleasewait.dismiss();
                                Pleasewait = null;
                            }
                            String Summarydata = genericJsons[0].toString();
                            UpdateData(Summarydata);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        if (Pleasewait != null && Pleasewait.isShowing()) {
                            Pleasewait.dismiss();
                            Pleasewait = null;
                        }
                        Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Generic_Methods.getToast(CCU_Details.getSingletonContext(), getResources().getString(R.string.user_offline));
            }
        }
    }


    public void UpdateData(String Summarydata) {
        try {
            JSONObject s = new JSONObject(Summarydata);
            String zone_summary = s.getString("zone_summary");
            JSONArray zone_summarydata = new JSONArray(zone_summary);
            zoneDatas = new ArrayList<>();
            for (int i = 0; i < zone_summarydata.length(); i++) {
                JSONObject object = new JSONObject(zone_summarydata.get(i).toString());
                zoneDatas.add(new Damper_test_row_data(object.getString("name"), object.getInt("damper_pos"), object.getInt("fsv_address")));
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
}

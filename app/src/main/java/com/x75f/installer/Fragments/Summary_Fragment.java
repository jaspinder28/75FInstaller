package com.x75f.installer.Fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.api.client.json.GenericJson;
import com.kinvey.android.AsyncAppData;
import com.kinvey.android.callback.KinveyListCallback;
import com.kinvey.java.Logger;
import com.kinvey.java.Query;
import com.x75f.installer.Activity.CCU_Details;
import com.x75f.installer.Adapters.Zones_Adapter;
import com.x75f.installer.R;
import com.x75f.installer.Utils.Generic_Methods;
import com.x75f.installer.Utils.Helper;
import com.x75f.installer.Utils.Summary_Data;
import com.x75f.installer.Utils.zone_data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class Summary_Fragment extends Fragment {

    @InjectView(R.id.timezone)
    TextView timezone;
    @InjectView(R.id.ccuName)
    TextView ccuName;
    @InjectView(R.id.scrollView)
    ScrollView scrollView;
    @InjectView(R.id.Building_limit)
    TextView Building_limit;
    @InjectView(R.id.current_temp)
    TextView current_temp;
    @InjectView(R.id.current_humidity)
    TextView current_humidity;
    @InjectView(R.id.hvac_equipment)
    TextView hvac_equipment;
    @InjectView(R.id.hvac_equipment_detail)
    TextView hvac_equipment_detail;
    @InjectView(R.id.hvac_equipment_detail1)
    TextView hvac_equipment_detail1;
    @InjectView(R.id.hvac_equipment_detail2)
    TextView hvac_equipment_detail2;
    @InjectView(R.id.hvac_equipment_detail3)
    TextView hvac_equipment_detail3;
    @InjectView(R.id.hvac_equipment_detail4)
    TextView hvac_equipment_detail4;
    @InjectView(R.id.hvac_equipment_detail5)
    TextView hvac_equipment_detail5;
    @InjectView(R.id.economiser_detail)
    TextView economiser_detail;
    @InjectView(R.id.economiser_detail1)
    TextView economiser_detail1;
    @InjectView(R.id.economiser_detail2)
    TextView economiser_detail2;
    @InjectView(R.id.economiser_detail3)
    TextView economiser_detail3;
    @InjectView(R.id.economiser_detail4)
    TextView economiser_detail4;
    @InjectView(R.id.economiser_detail5)
    TextView economiser_detail5;
    @InjectView(R.id.timezone1)
    TextView timezone1;
    @InjectView(R.id.economiser_detail6)
    TextView economiser_detail6;
    @InjectView(R.id.economiser_detail7)
    TextView economiser_detail7;
    @InjectView(R.id.pressure_sensor)
    TextView pressure_sensor;
    @InjectView(R.id.ZoneList)
    ListView ZoneList;
    String cooling_stage1 = null;
    String cooling_stage2 = null;
    String heating_stage1 = null;
    String heating_stage2 = null;
    String fan_stage1 = null;
    String fan_stage2 = null;
    String humidifier = null;
    ArrayList<zone_data> zoneDatas;
    View v;
    @InjectView(R.id.user_limit)
    TextView user_limit;
    String ccuname;
    String ccuid;

    private static ProgressDialog Pleasewait = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ccuname = getArguments().getString("ccuname");
        ccuid = getArguments().getString("ccu_id");
        v = inflater.inflate(R.layout.summary_fragment, container, false);
        ButterKnife.inject(this, v);
        Logger.configBuilder().all();

        return v;


    }


    public void QueryDataAgain(String s) {
        if (Summary_Fragment.this != null && isVisible() && CCU_Details.getSingletonContext().viewPager.getCurrentItem() == 0) {

            if (Generic_Methods.isNetworkAvailable(CCU_Details.getSingletonContext())) {
                if (Pleasewait == null) {
                    Pleasewait = ProgressDialog.show(CCU_Details.getSingletonContext(), "", "Please Wait...");
                }
                UpdateTheSummary update = new UpdateTheSummary();
                update.execute(s);

            } else {
                Generic_Methods.getToast(CCU_Details.getSingletonContext(), getResources().getString(R.string.user_offline));
                dismissDialog();
            }
        } else {
            CCU_Details.getSingletonContext().summaryHandler.removeCallbacks(CCU_Details.getSingletonContext().summaryUpdate);
            Generic_Methods.PauseCalledSummary();
        }
    }


    public class UpdateTheSummary extends AsyncTask<String, Void, Summary_Data> {

        @Override
        protected Summary_Data doInBackground(String... params) {
            Summary_Data sd = null;
            try {
                JSONObject s = new JSONObject(params[0]);
                if (s.optBoolean("isPaired")) {
                    sd = new Summary_Data(s.optString("ccu_name"), s.optString("date_time"), s.optInt("building_no_cooler", 0), s.optInt("building_no_hotter", 0), s.optInt("user_no_cooler", 0), s.optInt("user_no_hotter", 0),
                            s.optInt("cm_cur_temp", 0), s.optInt("cm_cur_humidity", 0), s.optInt("cooling_stage_1", 0), s.optInt("cooling_stage_2", 0),
                            s.optInt("heating_stage_1"), s.optInt("heating_stage_2"), s.optInt("fan_stage_1"), s.optInt("fan_stage_2"), s.optInt("humidifier"), s.optBoolean("isEconomizerAvailable"),
                            s.optBoolean("isPaired"), s.optInt("analog1_damperPos", 0), s.optInt("analog2_damperPos", 0), s.optInt("analog3_damperPos", 0),
                            s.optInt("analog4_damperPos", 0), s.optDouble("mInsideAirEnthalpy"), s.optDouble("mOutsideAirEnthalpy"), s.optInt("mOutsideAirTemperature"), s.optInt("mOutsideAirMaxTemp"),
                            s.optInt("mOutsideAirMinTemp", 0), s.optInt("mOutsideAirHumidity", 0), s.optInt("mOutsideAirMaxHumidity", 0), s.optInt("mOutsideAirMinHumidity", 0),
                            s.optInt("mCO2Level", 0), s.optInt("mCO2LevelThreshold", 2000), s.optInt("mMixedAirTemperature"), s.optInt("mReturnAirTemperature"), s.optInt("mDamperPos", 0), s.optString("zone_summary"),
                            s.optInt("mNO2Level", 0), s.optInt("mNO2LevelThreshold", 10), s.optInt("mCOLevel", 0), s.optInt("mCOLevelThreshold", 250), s.optBoolean("isPressureSensorPaired", false), s.optDouble("mPressureLevel", 0), s.optDouble("mPressureLevelThreshold", 0),
                            s.optBoolean("isCOPaired", false), s.optBoolean("isNO2Paired", false),s.optInt("analog1_type",-1),s.optInt("analog2_type",-1),s.optInt("analog3_type",-1),s.optInt("analog4_type",-1));

                } else {
                    sd = new Summary_Data(s.optString("ccu_name", ""), s.optString("date_time", ""), s.optInt("building_no_cooler", 0),
                            s.optInt("building_no_hotter", 0), s.optInt("user_no_cooler", 0), s.optInt("user_no_hotter", 0),
                            s.optInt("cm_cur_temp", 0), s.optInt("cm_cur_humidity", 0), s.optInt("cooling_stage_1", 0), s.optInt("cooling_stage_2"),
                            s.optInt("heating_stage_1", 0), s.optInt("heating_stage_2", 0), s.optInt("fan_stage_1", 0), s.optInt("fan_stage_2", 0),
                            s.optInt("humidifier", 0), s.optBoolean("isEconomizerAvailable"),
                            s.optBoolean("isPaired"), s.optInt("analog1_damperPos", 0), s.optInt("analog2_damperPos", 0),
                            s.optInt("analog3_damperPos", 0),
                            s.optInt("analog4_damperPos", 0), s.optDouble("mInsideAirEnthalpy"), s.optDouble("mOutsideAirEnthalpy"),
                            s.optInt("mOutsideAirTemperature", 0), s.optInt("mOutsideAirMaxTemp", 0),
                            s.optInt("mOutsideAirMinTemp", 0), s.optInt("mOutsideAirHumidity", 0), s.optInt("mOutsideAirMaxHumidity", 0),
                            s.optInt("mOutsideAirMinHumidity", 0),
                            s.optInt("mMixedAirTemperature", 0), s.optInt("mReturnAirTemperature", 0),
                            s.optInt("mDamperPos", 0), s.optString("zone_summary"),
                            s.optBoolean("isPressureSensorPaired", false), s.optDouble("mPressureLevel", 0), s.optDouble("mPressureLevelThreshold", 0),s.optInt("analog1_type",-1),s.optInt("analog2_type",-1),s.optInt("analog3_type",-1),s.optInt("analog4_type",-1));
                }

                if (sd.getCooling_stage_1() == 0) {
                    cooling_stage1 = "Off";
                } else if (sd.getCooling_stage_1() == 1) {
                    cooling_stage1 = "On";
                } else if (sd.getCooling_stage_1() == -1) {
                    cooling_stage1 = "not availible";
                }

                if (sd.getCooling_stage_2() == 0) {
                    cooling_stage2 = "Off";
                } else if (sd.getCooling_stage_2() == 1) {
                    cooling_stage2 = "On";
                } else if (sd.getCooling_stage_2() == -1) {
                    cooling_stage2 = "not availible";
//                    Generic_Methods.getToast(CCU_Details.getSingletonContext(),"gehvga ");
                }

                if (sd.getHeating_stage_1() == 0) {
                    heating_stage1 = "Off";
                } else if (sd.getHeating_stage_1() == 1) {
                    heating_stage1 = "On";
                } else if (sd.getHeating_stage_1() == -1) {
                    heating_stage1 = "not availible";
                }

                if (sd.getHeating_stage_2() == 0) {
                    heating_stage2 = "Off";
                } else if (sd.getHeating_stage_2() == 1) {
                    heating_stage2 = "On";
                } else if (sd.getHeating_stage_2() == -1) {
                    heating_stage2 = "not availible";
                }
                if (sd.getFan_stage_1() == 0) {
                    fan_stage1 = "Off";
                } else if (sd.getFan_stage_1() == 1) {
                    fan_stage1 = "On";
                } else if (sd.getFan_stage_1() == -1) {
                    fan_stage1 = "not availible";
                }

                if (sd.getFan_stage_2() == 0) {
                    fan_stage2 = "Off";
                } else if (sd.getFan_stage_2() == 1) {
                    fan_stage2 = "On";
                } else if (sd.getFan_stage_2() == -1) {
                    fan_stage2 = "not availible";
                }

                if (sd.getHumidifier() == 0) {
                    humidifier = "Off";
                } else if (sd.getHumidifier() == 1) {
                    humidifier = "On";
                } else if (sd.getHumidifier() == -1) {
                    humidifier = "not availible";
                }

                JSONArray zone_summary = new JSONArray(sd.getZone_summary());

                zoneDatas = new ArrayList<>();
                zoneDatas.clear();
                for (int i = 0; i < zone_summary.length(); i++) {
                    JSONObject object = new JSONObject(zone_summary.get(i).toString());
                    if (object.optBoolean("fsv_paired")) {

                        try {
                            JSONObject object1 = object.getJSONObject("schedule");
                            zoneDatas.add(new zone_data(object.optString("name"), object.optBoolean("fsv_paired"), object.optInt("fsv_address"),
                                    object.optInt("damper_size"), object.optString("damper_type"), object.optString("occu_sensor"), object.optInt("set_temp"),
                                    object.optInt("cur_temp"), object.optInt("damper_pos"), object.optString("occupied"), object.optInt("airflow_temp"),
                                    object1.optString("monday_occutime"), object1.optString("monday_unoccutime"), object1.optInt("monday_occutemp"),
                                    object1.optString("tuesday_occutime"), object1.optString("tuesday_unoccutime"), object1.optInt("tuesday_occutemp"),
                                    object1.optString("wednesday_occutime"), object1.optString("wednesday_unoccutime"), object1.optInt("wednesday_occutemp"),
                                    object1.optString("thursday_occutime"), object1.optString("thursday_unoccutime"), object1.optInt("thursday_occutemp"),
                                    object1.optString("friday_occutime"), object1.optString("friday_unoccutime"), object1.optInt("friday_occutemp"),
                                    object1.optString("saturday_occutime"), object1.optString("saturday_unoccutime"), object1.optInt("saturday_occutemp"),
                                    object1.optString("sunday_occutime"), object1.optString("sunday_unoccutime"), object1.optInt("sunday_occutemp")));
                        } catch (JSONException e) {
                            Log.e("Zone_Error1", e.getMessage());
                            try {
                                JSONObject object1 = object.getJSONObject("schedule");
                                zoneDatas.add(new zone_data(object.optString("name"), object.optBoolean("fsv_paired"), object.optInt("fsv_address"),
                                        object.optInt("damper_size"), object.optString("damper_type"), object.optString("occu_sensor"), object.optInt("set_temp"),
                                        object.optInt("cur_temp"), object.optInt("damper_pos"), "", object.optInt("airflow_temp"),
                                        object1.optString("monday_occutime"), object1.optString("monday_unoccutime"), object1.optInt("monday_occutemp"),
                                        object1.optString("tuesday_occutime"), object1.optString("tuesday_unoccutime"), object1.optInt("tuesday_occutemp"),
                                        object1.optString("wednesday_occutime"), object1.optString("wednesday_unoccutime"), object1.optInt("wednesday_occutemp"),
                                        object1.optString("thursday_occutime"), object1.optString("thursday_unoccutime"), object1.optInt("thursday_occutemp"),
                                        object1.optString("friday_occutime"), object1.optString("friday_unoccutime"), object1.optInt("friday_occutemp"),
                                        object1.optString("saturday_occutime"), object1.optString("saturday_unoccutime"), object1.optInt("saturday_occutemp"),
                                        object1.optString("sunday_occutime"), object1.optString("sunday_unoccutime"), object1.optInt("sunday_occutemp")));
                            } catch (JSONException e1) {
                                Log.e("Zone_Error2", e1.getMessage());
                                try {
                                    zoneDatas.add(new zone_data(object.optString("name"), object.optBoolean("fsv_paired"), object.optInt("fsv_address"),
                                            object.optInt("damper_size"), object.optString("damper_type"), object.optString("occu_sensor"), object.optInt("set_temp"),
                                            object.optInt("cur_temp"), object.optInt("damper_pos"), object.optString("occupied"), object.optInt("airflow_temp"),
                                            "", "", 0,
                                            "", "", 0,
                                            "", "", 0,
                                            "", "", 0,
                                            "", "", 0,
                                            "", "", 0,
                                            "", "", 0));
                                } catch (Exception e2) {
                                    Log.e("Zone_Error3", e1.getMessage());
                                    zoneDatas.add(new zone_data(object.optString("name"), object.optBoolean("fsv_paired"), object.optInt("fsv_address"),
                                            object.optInt("damper_size"), object.optString("damper_type"), object.optString("occu_sensor"), object.optInt("set_temp"),
                                            object.optInt("cur_temp"), object.optInt("damper_pos"), "", object.optInt("airflow_temp"),
                                            "", "", 0,
                                            "", "", 0,
                                            "", "", 0,
                                            "", "", 0,
                                            "", "", 0,
                                            "", "", 0,
                                            "", "", 0));
                                }

                            }
                        }
                    } else {
                        zoneDatas.add(new zone_data(object.optString("name"), object.optBoolean("fsv_paired"), object.optInt("fsv_address"),
                                object.optInt("damper_size"), object.optString("damper_type"), object.optString("occu_sensor"), object.optInt("set_temp"),
                                object.optInt("cur_temp"), object.optInt("damper_pos"), "", 0,
                                "", "", 0,
                                "", "", 0,
                                "", "", 0,
                                "", "", 0,
                                "", "", 0,
                                "", "", 0,
                                "", "", 0));
                    }


                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


            return sd;
        }

        @Override
        protected void onPostExecute(Summary_Data sd) {
                Log.d("done", "dataloaded");

                timezone.setText(sd.getDate_time());
                timezone1.setText(sd.getDate_time());
                ccuName.setText(sd.getCcu_name());
                Building_limit.setText(sd.getBuilding_no_cooler() + "/" + sd.getBuilding_no_hotter());
                user_limit.setText(sd.getUser_no_cooler() + "/" + sd.getUser_no_hotter());
                current_temp.setText(sd.getCm_cur_temp() + "");
                current_humidity.setText(sd.getCm_cur_humidity() + "");
                if ((sd.getCooling_stage_1() == 0 || sd.getCooling_stage_1() == 1) && (sd.getCooling_stage_2() == 0 || sd.getCooling_stage_2() == 1)) {
                    hvac_equipment_detail.setText("Cooling Stage 1(" + cooling_stage1 + ") and 2(" + cooling_stage2 + ")");
                } else if ((sd.getCooling_stage_1() == -1) && (sd.getCooling_stage_2() == 0 || sd.getCooling_stage_2() == 1)) {
                    hvac_equipment_detail.setText("Cooling Stage 2(" + cooling_stage2 + ")");
                } else if ((sd.getCooling_stage_2() == -1) && (sd.getCooling_stage_1() == 0 || sd.getCooling_stage_1() == 1)) {
                    hvac_equipment_detail.setText("Cooling Stage 1(" + cooling_stage1 + ")");
                } else if (sd.getCooling_stage_1() == -1 && sd.getCooling_stage_2() == -1) {
                    hvac_equipment_detail.setVisibility(View.GONE);
                }

                if ((sd.getHeating_stage_1() == 0 || sd.getHeating_stage_1() == 1) && (sd.getHeating_stage_2() == 0 || sd.getHeating_stage_2() == 1)) {
                    hvac_equipment_detail1.setText("Heating Stage 1(" + heating_stage1 + ") and 2(" + heating_stage2 + ")");
                } else if ((sd.getHeating_stage_1() == -1) && (sd.getHeating_stage_2() == 0 || sd.getHeating_stage_2() == 1)) {
                    hvac_equipment_detail1.setText("Heating Stage 2(" + heating_stage2 + ")");
                } else if ((sd.getHeating_stage_2() == -1) && (sd.getHeating_stage_1() == 0 || sd.getHeating_stage_1() == 1)) {
                    hvac_equipment_detail1.setText("Heating Stage 1(" + heating_stage1 + ")");
                } else if (sd.getHeating_stage_1() == -1 && sd.getHeating_stage_2() == -1) {
                    hvac_equipment_detail1.setVisibility(View.GONE);
                }

                if ((sd.getFan_stage_1() == 0 || sd.getFan_stage_1() == 1) && (sd.getFan_stage_2() == 0 || sd.getFan_stage_2() == 1)) {
                    hvac_equipment_detail2.setText("Fan Stage 1(" + fan_stage1 + ") and 2(" + fan_stage2 + ")");
                } else if ((sd.getFan_stage_1() == -1) && (sd.getFan_stage_2() == 0 || sd.getFan_stage_2() == 1)) {
                    hvac_equipment_detail2.setText("Fan Stage 2(" + fan_stage2 + ")");
                } else if ((sd.getFan_stage_2() == -1) && (sd.getFan_stage_1() == 0 || sd.getFan_stage_1() == 1)) {
                    hvac_equipment_detail2.setText("Fan Stage 1(" + fan_stage1 + ")");
                } else if (sd.getFan_stage_1() == -1 && sd.getFan_stage_2() == -1) {
                    hvac_equipment_detail2.setVisibility(View.GONE);
                }

                if (sd.getHumidifier() == -1) {
                    hvac_equipment_detail3.setVisibility(View.GONE);
                } else {
                    hvac_equipment_detail3.setText("Humidifier(" + humidifier + ")");
                }

                if (sd.isEconomizerAvailable() == true && sd.isPaired() == true) {
                    hvac_equipment_detail4.setText("Economizer(Yes/Yes)");
                } else if (sd.isEconomizerAvailable() == true && sd.isPaired() == false) {
                    hvac_equipment_detail4.setText("Economizer(Yes/No)");
                } else {
                    hvac_equipment_detail4.setText("Economizer(No/No)");
                }
                hvac_equipment_detail5.setText("Analog(" + sd.getAnalog1_damperPos() + "/" + sd.getAnalog2_damperPos() + "/" + sd.getAnalog3_damperPos() + "/" + sd.getAnalog4_damperPos() + ")");

                economiser_detail.setText("Enthalpy(In:" + String.format("%.1f", sd.getmInsideAirEnthalpy()) + ")/(Out:" + String.format("%.1f", sd.getmOutsideAirEnthalpy()) + ")");
                economiser_detail1.setText("Outside Temp:" + sd.getmOutsideAirTemperature() + "(" + sd.getmOutsideAirMinTemp() + "-" + sd.getmOutsideAirMaxTemp() + ")");
                economiser_detail2.setText("Outside Humidity:" + sd.getmOutsideAirHumidity() + "(" + sd.getmOutsideAirMinHumidity() + "-" + sd.getmOutsideAirMaxHumidity() + ")");
                economiser_detail3.setText("Damper Pos:" + sd.getmDamperPos());
                economiser_detail4.setText("MAT:" + sd.getmMixedAirTemperature() + "/RAT:" + sd.getmReturnAirTemperature());
                if (sd.isPaired()) {

                    economiser_detail5.setText("CO2:" + sd.getmCO2Level() + "(" + sd.getmCO2LevelThreshold() + ")");
                    economiser_detail6.setText("CO:" + sd.getmCOLevel() + "(" + sd.getmCOLevelThreshold() + ")");
                    economiser_detail7.setText("NO2:" + sd.getmNO2Level() + "(" + sd.getmNO2LevelThreshold() + ")");

                } else {
                    economiser_detail.setText("Not Paired");
                    economiser_detail1.setVisibility(View.GONE);
                    economiser_detail2.setVisibility(View.GONE);
                    economiser_detail3.setVisibility(View.GONE);
                    economiser_detail4.setVisibility(View.GONE);
                    economiser_detail5.setVisibility(View.GONE);
                    economiser_detail6.setVisibility(View.GONE);
                    economiser_detail7.setVisibility(View.GONE);
                }
                scrollView.smoothScrollTo(0, 0);
                if (zoneDatas.size() != 0) {
                    Zones_Adapter zones_adapter = new Zones_Adapter(CCU_Details.getSingletonContext(), zoneDatas);
                    ZoneList.setAdapter(zones_adapter);
                    Helper.getListViewSize(ZoneList);
                }
                if (sd.isPressureSensorPaired()) {
                    pressure_sensor.setText(String.format("%.2f", sd.getmPressureLevel()) + "(" + String.format("%.2f", sd.getmPressureLevelThreshold()) + ")");
                } else {
                    pressure_sensor.setText("NA");
                }
                dismissDialog();

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

    public void GetData() {
        if (Pleasewait == null) {
            Pleasewait = ProgressDialog.show(CCU_Details.getSingletonContext(), "", "Please Wait...");
        }

        Query newquery = new Query();
        newquery.equals("_id", getArguments().getString("ccu_id"));
        AsyncAppData<GenericJson> summary = Generic_Methods.getKinveyClient().appData("00CCUSummary", GenericJson.class);
        Log.e("getdata", "jhbacs");
        if (summary.isOnline()) {
            Log.e("getdata1", "jhbacs1");
            summary.get(newquery, new KinveyListCallback<GenericJson>() {

                @Override
                public void onSuccess(final GenericJson[] genericJsons) {
                    Log.e("getdata2", "jhbacs2");
                    dismissDialog();
                    getData(genericJsons[0].toString());


                }

                @Override
                public void onFailure(Throwable throwable) {
                    Log.e("result", "failed");
                    dismissDialog();
                }
            });
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("onpause", "onpusesummary");
        dismissDialog();
        if (CCU_Details.getSingletonContext().summaryHandler != null && CCU_Details.getSingletonContext().summaryUpdate != null) {
            CCU_Details.getSingletonContext().summaryHandler.removeCallbacks(CCU_Details.getSingletonContext().summaryUpdate);
            CCU_Details.getSingletonContext().summaryHandler = null;
            CCU_Details.getSingletonContext().summaryUpdate = null;
            Generic_Methods.PauseCalledSummary();
            Log.e("onpause", "threadcancel");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("onresume", "onresume");
        if (CCU_Details.getSingletonContext() != null && CCU_Details.getSingletonContext().viewPager.getCurrentItem() == 0) {

            CCU_Details.getSingletonContext().summaryHandler = new Handler();

            dismissDialog();
            if (Generic_Methods.isNetworkAvailable(CCU_Details.getSingletonContext())) {
                GetData();
            } else {
                Generic_Methods.getToast(CCU_Details.getSingletonContext(), getResources().getString(R.string.user_offline));
            }
        }
    }


    public void getData(String genericJson) {
        if (CCU_Details.getSingletonContext().summaryHandler == null)
            CCU_Details.getSingletonContext().summaryHandler = new Handler();
        dismissDialog();
        if (genericJson != null && !genericJson.equalsIgnoreCase("")) {
            QueryDataAgain(genericJson);
        }

        Generic_Methods.FetchSummaryData(getArguments().getString("ccu_id"));
        Log.e("updating", "getdata");
        CCU_Details.getSingletonContext().summaryUpdate = new Runnable() {
            @Override
            public void run() {
                Log.e("updating1", "getdata1");
                if (getActivity() != null && CCU_Details.getSingletonContext().viewPager.getCurrentItem() == 0) {
                    if (Generic_Methods.isNetworkAvailable(CCU_Details.getSingletonContext())) {
                        Log.e("updating2", "getdata2");
                        String s = Generic_Methods.getStringPreference(CCU_Details.getSingletonContext(), "summary", "summarydata");
                        QueryDataAgain(s);
                        if (CCU_Details.getSingletonContext().summaryHandler != null) {
                            CCU_Details.getSingletonContext().summaryHandler.postDelayed(CCU_Details.getSingletonContext().summaryUpdate, 60000);
                        }
                        dismissDialog();
                    } else {
                        Generic_Methods.getToast(CCU_Details.getSingletonContext(), getResources().getString(R.string.user_offline));
                    }
                } else {
                    if (CCU_Details.getSingletonContext().summaryHandler != null && CCU_Details.getSingletonContext().summaryUpdate != null) {
                        CCU_Details.getSingletonContext().summaryHandler.removeCallbacks(CCU_Details.getSingletonContext().summaryUpdate);
                        CCU_Details.getSingletonContext().summaryHandler = null;
                        CCU_Details.getSingletonContext().summaryUpdate = null;
                    }
                }
            }

        };
        if (CCU_Details.getSingletonContext().summaryHandler != null) {
            CCU_Details.getSingletonContext().summaryHandler.postDelayed(CCU_Details.getSingletonContext().summaryUpdate, 60000);
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






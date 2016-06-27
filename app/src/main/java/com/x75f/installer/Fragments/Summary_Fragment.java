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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.json.GenericJson;
import com.kinvey.android.AsyncAppData;
import com.kinvey.android.callback.KinveyListCallback;
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
    @InjectView(R.id.ZoneList)
    ListView ZoneList;
    private  Handler handler = new Handler();

    @InjectView(R.id.user_limit)
    TextView user_limit;
    String ccuname;
    private static ProgressDialog Pleasewait;
//    Client mKinveyClient;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if(Summary_Fragment.this != null && isVisible() && CCU_Details.viewPager.getCurrentItem() == 0){
            Runnable runnable = new Runnable() {

                @Override
                public void run() {
                    try {
                        if (Summary_Fragment.this != null && isVisible() && CCU_Details.viewPager.getCurrentItem() == 0) {
                            QueryDataAgain();
                            handler.postDelayed(this, 60000 );
                        }
                    } catch (Exception e) {
                    }
                }
            };
            runnable.run();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ccuname = getArguments().getString("ccuname");
        View v = inflater.inflate(R.layout.summary_fragment, container, false);
        ButterKnife.inject(this, v);
        return v;


    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(isVisible()){
            Runnable runnable = new Runnable() {

                @Override
                public void run() {
                    try {
                        if (Summary_Fragment.this != null && isVisible() && CCU_Details.viewPager.getCurrentItem() == 0) {
                            QueryDataAgain();
                            handler.postDelayed(this, 60000 );
                        }
                    } catch (Exception e) {
                    }
                }
            };
            runnable.run();
        }
    }


    public void QueryDataAgain() {
        if (Summary_Fragment.this != null && isVisible() && CCU_Details.viewPager.getCurrentItem() == 0) {
            Query newquery = new Query();
            newquery.equals("ccu_name", ccuname);
            if(Generic_Methods.isNetworkAvailable(CCU_Details.getSingletonContext())) {
            if(Pleasewait == null) {
                Pleasewait = ProgressDialog.show(CCU_Details.getSingletonContext(), "", "Please Wait...");
            }

                AsyncAppData<GenericJson> summary = CCU_Details.mKinveyClient.appData("00CCUSummary", GenericJson.class);
                summary.get(newquery, new KinveyListCallback<GenericJson>() {
                    @Override
                    public void onSuccess(GenericJson[] genericJsons) {
                        try {
                            if (Pleasewait != null && Pleasewait.isShowing()) {
                                Pleasewait.dismiss();
                                Pleasewait = null;
                            }
                            String Summarydata = genericJsons[0].toString();
//                            UpdateTheSummary(Summarydata);
                            UpdateTheSummary updateTheSummary = new UpdateTheSummary();
                            updateTheSummary.execute(Summarydata);

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
            }else {
                Generic_Methods.getToast(CCU_Details.getSingletonContext(),getResources().getString(R.string.user_offline));
            }
        }
    }

     String cooling_stage1 = null;
     String cooling_stage2 = null;
     String heating_stage1 = null;
     String heating_stage2 = null;
     String fan_stage1 = null;
     String fan_stage2 = null;
     String humidifier = null;
    ArrayList<zone_data> zoneDatas;

    public class UpdateTheSummary extends AsyncTask<String,Void,Summary_Data>{

        @Override
        protected Summary_Data doInBackground(String... params) {
            Summary_Data sd = null;
            try {
                JSONObject s = new JSONObject(params[0]);
                sd = new Summary_Data(s.getString("ccu_name"), s.getString("date_time"), s.getInt("building_no_cooler"), s.getInt("building_no_hotter"), s.getInt("user_no_cooler"), s.getInt("user_no_hotter"),
                        s.getInt("cm_cur_temp"), s.getInt("cm_cur_humidity"), s.getInt("cooling_stage_1"), s.getInt("cooling_stage_2"),
                        s.getInt("heating_stage_1"), s.getInt("heating_stage_2"), s.getInt("fan_stage_1"), s.getInt("fan_stage_2"), s.getInt("humidifier"), s.getBoolean("isEconomizerAvailable"),
                        s.getBoolean("isPaired"), s.getInt("analog1_damperPos"), s.getInt("analog2_damperPos"), s.getInt("analog3_damperPos"),
                        s.getInt("analog4_damperPos"), s.getDouble("mInsideAirEnthalpy"), s.getDouble("mOutsideAirEnthalpy"), s.getInt("mOutsideAirTemperature"), s.getInt("mOutsideAirMaxTemp"),
                        s.getInt("mOutsideAirMinTemp"), s.getInt("mOutsideAirHumidity"), s.getInt("mOutsideAirMaxHumidity"), s.getInt("mOutsideAirMinHumidity"),
                        s.getInt("mCO2Level"), s.getInt("mCO2LevelThreshold"), s.getInt("mMixedAirTemperature"), s.getInt("mReturnAirTemperature"), s.getInt("mDamperPos"), s.getString("zone_summary"));

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
                 zoneDatas = new ArrayList<zone_data>();
//
                for (int i = 0; i < zone_summary.length(); i++) {
                    JSONObject object = new JSONObject(zone_summary.get(i).toString());
                    if (object.getBoolean("fsv_paired")) {

                        try {
                            JSONObject object1 = object.getJSONObject("schedule");
                            zoneDatas.add(new zone_data(object.getString("name"), object.getBoolean("fsv_paired"), object.getInt("fsv_address"),
                                    object.getInt("damper_size"), object.getString("damper_type"), object.getString("occu_sensor"), object.getInt("set_temp"),
                                    object.getInt("cur_temp"), object.getInt("damper_pos"), object.getString("occupied"), object.getInt("airflow_temp"),
                                    object1.getString("monday_occutime"), object1.getString("monday_unoccutime"), object1.getInt("monday_occutemp"),
                                    object1.getString("tuesday_occutime"), object1.getString("tuesday_unoccutime"), object1.getInt("tuesday_occutemp"),
                                    object1.getString("wednesday_occutime"), object1.getString("wednesday_unoccutime"), object1.getInt("wednesday_occutemp"),
                                    object1.getString("thursday_occutime"), object1.getString("thursday_unoccutime"), object1.getInt("thursday_occutemp"),
                                    object1.getString("friday_occutime"), object1.getString("friday_unoccutime"), object1.getInt("friday_occutemp"),
                                    object1.getString("saturday_occutime"), object1.getString("saturday_unoccutime"), object1.getInt("saturday_occutemp"),
                                    object1.getString("sunday_occutime"), object1.getString("sunday_unoccutime"), object1.getInt("sunday_occutemp")));
                        } catch (JSONException e) {
                            Log.e("Zone_Error1", e.getMessage());
                            try {
                                JSONObject object1 = object.getJSONObject("schedule");
                                zoneDatas.add(new zone_data(object.getString("name"), object.getBoolean("fsv_paired"), object.getInt("fsv_address"),
                                        object.getInt("damper_size"), object.getString("damper_type"), object.getString("occu_sensor"), object.getInt("set_temp"),
                                        object.getInt("cur_temp"), object.getInt("damper_pos"), "", object.getInt("airflow_temp"),
                                        object1.getString("monday_occutime"), object1.getString("monday_unoccutime"), object1.getInt("monday_occutemp"),
                                        object1.getString("tuesday_occutime"), object1.getString("tuesday_unoccutime"), object1.getInt("tuesday_occutemp"),
                                        object1.getString("wednesday_occutime"), object1.getString("wednesday_unoccutime"), object1.getInt("wednesday_occutemp"),
                                        object1.getString("thursday_occutime"), object1.getString("thursday_unoccutime"), object1.getInt("thursday_occutemp"),
                                        object1.getString("friday_occutime"), object1.getString("friday_unoccutime"), object1.getInt("friday_occutemp"),
                                        object1.getString("saturday_occutime"), object1.getString("saturday_unoccutime"), object1.getInt("saturday_occutemp"),
                                        object1.getString("sunday_occutime"), object1.getString("sunday_unoccutime"), object1.getInt("sunday_occutemp")));
                            } catch (JSONException e1) {
                                Log.e("Zone_Error2", e1.getMessage());
                                try {
                                    zoneDatas.add(new zone_data(object.getString("name"), object.getBoolean("fsv_paired"), object.getInt("fsv_address"),
                                            object.getInt("damper_size"), object.getString("damper_type"), object.getString("occu_sensor"), object.getInt("set_temp"),
                                            object.getInt("cur_temp"), object.getInt("damper_pos"), object.getString("occupied"), object.getInt("airflow_temp"),
                                            "", "", 0,
                                            "", "", 0,
                                            "", "", 0,
                                            "", "", 0,
                                            "", "", 0,
                                            "", "", 0,
                                            "", "", 0));
                                } catch (JSONException e2) {
                                    Log.e("Zone_Error3", e1.getMessage());
                                    zoneDatas.add(new zone_data(object.getString("name"), object.getBoolean("fsv_paired"), object.getInt("fsv_address"),
                                            object.getInt("damper_size"), object.getString("damper_type"), object.getString("occu_sensor"), object.getInt("set_temp"),
                                            object.getInt("cur_temp"), object.getInt("damper_pos"), "", object.getInt("airflow_temp"),
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
                        zoneDatas.add(new zone_data(object.getString("name"), object.getBoolean("fsv_paired"), object.getInt("fsv_address"),
                                object.getInt("damper_size"), object.getString("damper_type"), object.getString("occu_sensor"), object.getInt("set_temp"),
                                object.getInt("cur_temp"), object.getInt("damper_pos"), "", 0,
                                "", "", 0,
                                "", "", 0,
                                "", "", 0,
                                "", "", 0,
                                "", "", 0,
                                "", "", 0,
                                "", "", 0));
                    }


                }


            }catch (JSONException e){
                e.printStackTrace();
            }



            return sd;
        }

        @Override
        protected void onPostExecute(Summary_Data sd) {
            super.onPostExecute(sd);
            timezone.setText(sd.getDate_time());
            timezone1.setText(sd.getDate_time());
            ccuName.setText(sd.getCcu_name());
            Building_limit.setText(sd.getBuilding_no_cooler() + "/" + sd.getBuilding_no_hotter());
            user_limit.setText(sd.getUser_no_cooler() + "/" + sd.getUser_no_hotter());
            current_temp.setText(sd.getCm_cur_temp()+"");
            current_humidity.setText(sd.getCm_cur_humidity()+"");
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
            economiser_detail1.setText("Outside Temp:" + sd.getmOutsideAirTemperature() + "(" + sd.getmOutsideAirMinTemp() + "/" + sd.getmOutsideAirMaxTemp() + ")");
            economiser_detail2.setText("Outside Humidity:" + sd.getmOutsideAirHumidity() + "(" + sd.getmOutsideAirMinHumidity() + "/" + sd.getmOutsideAirMaxHumidity() + ")");
            economiser_detail3.setText("Damper Pos:" + sd.getmDamperPos());
            economiser_detail4.setText("MAT:" + sd.getmMixedAirTemperature() + "/RAT:" + sd.getmReturnAirTemperature());
            economiser_detail5.setText("CO2:" + sd.getmCO2Level() + "(" + sd.getmCO2LevelThreshold() + ")");

            if (zoneDatas.size() != 0) {
                Zones_Adapter zones_adapter = new Zones_Adapter(CCU_Details.getSingletonContext(), zoneDatas);
                ZoneList.setAdapter(zones_adapter);
                Helper.getListViewSize(ZoneList);
            }
        }
    }


}






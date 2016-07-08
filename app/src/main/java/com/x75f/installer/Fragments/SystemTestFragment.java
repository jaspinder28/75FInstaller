package com.x75f.installer.Fragments;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.api.client.json.GenericJson;
import com.kinvey.android.AsyncAppData;
import com.kinvey.android.callback.KinveyListCallback;
import com.kinvey.java.Query;
import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.x75f.installer.Activity.CCU_Details;
import com.x75f.installer.Activity.Otp_Verification;
import com.x75f.installer.R;
import com.x75f.installer.Utils.App_Constants;
import com.x75f.installer.Utils.Generic_Methods;
import com.x75f.installer.Utils.Summary_Data;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class SystemTestFragment extends Fragment implements View.OnClickListener {
    @InjectView(R.id.coolingStage1)
    ToggleButton coolingStage1;
    @InjectView(R.id.coolingStage2)
    ToggleButton coolingStage2;
    @InjectView(R.id.fanStage1)
    ToggleButton fanStage1;
    @InjectView(R.id.fanStage2)
    ToggleButton fanStage2;
    @InjectView(R.id.heatingStage1)
    ToggleButton heatingStage1;
    @InjectView(R.id.heatingStage2)
    ToggleButton heatingStage2;
    @InjectView(R.id.humidifier)
    ToggleButton humidifier;
    @InjectView(R.id.row11)
    RelativeLayout row11;
    @InjectView(R.id.row10)
    RelativeLayout row10;
    @InjectView(R.id.row1)
    RelativeLayout row1;
    @InjectView(R.id.row2)
    RelativeLayout row2;
    @InjectView(R.id.row3)
    RelativeLayout row3;
    @InjectView(R.id.row4)
    RelativeLayout row4;
    @InjectView(R.id.row5)
    RelativeLayout row5;
    @InjectView(R.id.row6)
    RelativeLayout row6;
    @InjectView(R.id.row7)
    RelativeLayout row7;
    @InjectView(R.id.row8)
    RelativeLayout row8;
    @InjectView(R.id.row9)
    RelativeLayout row9;
    @InjectView(R.id.analog1)
    Spinner analog1;
    @InjectView(R.id.analog2)
    Spinner analog2;
    @InjectView(R.id.analog3)
    Spinner analog3;
    @InjectView(R.id.analog4)
    Spinner analog4;
    String ccuname;
    private static ProgressDialog Pleasewait;
    public int cooling_stage1val;
    public int cooling_stage2val;
    public int fan_stage1val;
    public int fan_stage2val;
    public int heating_stage1val;
    public int heating_stage2val;
    public int humidifierval;
    int analog1position = 0;
    int analog2position = 0;
    int analog3position = 0;
    int analog4position = 0;
    View v;
    Integer[] damperValues = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
            21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 5, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70
            , 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100};


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.system_test_fragment, container, false);
        ButterKnife.inject(this, v);
        return v;
    }

//    @Override
//    public void setMenuVisibility(boolean menuVisible) {
//        super.setMenuVisibility(menuVisible);
//
//
//    }


    @Override
    public void onPause() {
        super.onPause();
        dismissDialog();
        if (CCU_Details.getSingletonContext().systemTestHandler != null && CCU_Details.getSingletonContext().systemTestUpdate != null) {
            CCU_Details.getSingletonContext().systemTestHandler.removeCallbacks(CCU_Details.getSingletonContext().systemTestUpdate);
            CCU_Details.getSingletonContext().systemTestHandler = null;
            CCU_Details.getSingletonContext().systemTestUpdate = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (SystemTestFragment.this != null && isVisible() && CCU_Details.getSingletonContext().viewPager.getCurrentItem() == 2) {

            CCU_Details.getSingletonContext().systemTestHandler = new Handler();
            Log.d("systemtest", "yes");
            Query newquery = new Query();
            ccuname = getArguments().getString("ccuname");
            newquery.equals("ccu_name", ccuname);
            if (Generic_Methods.isNetworkAvailable(CCU_Details.getSingletonContext())) {
                if (Generic_Methods.getKinveyClient().user().isUserLoggedIn()) {
                    if (Pleasewait == null) {
                        Pleasewait = ProgressDialog.show(CCU_Details.getSingletonContext(), "", "Please Wait...");
                    }

                    AsyncAppData<GenericJson> summary = Generic_Methods.getKinveyClient().appData("00CCUSummary", GenericJson.class);

                    summary.get(newquery, new KinveyListCallback<GenericJson>() {
                        @Override
                        public void onSuccess(GenericJson[] genericJsons) {

                            dismissDialog();
                            String Summarydata = genericJsons[0].toString();
                            setTheValues(Summarydata);


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

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case (R.id.coolingStage1):
//                if (coolingStage1.getText().toString().equalsIgnoreCase("OFF")) {
                if (!coolingStage1.isChecked()) {
//                    coolingStage1.setText("ON");
                    cooling_stage1val = 1;
//                    coolingStage1.setTextColor(getResources().getColor(R.color.primary));
                } else {
                    cooling_stage1val = 0;
//                    coolingStage1.setText("OFF");
//                    coolingStage1.setTextColor(getResources().getColor(R.color.black));
                }
                PubnubCall();
                break;
            case (R.id.coolingStage2):
                if (!coolingStage2.isChecked()) {
                    cooling_stage2val = 1;
//                    coolingStage2.setText("ON");
//                    coolingStage2.setTextColor(getResources().getColor(R.color.primary));
                } else {
                    cooling_stage2val = 0;
//                    coolingStage2.setText("OFF");
//                    coolingStage2.setTextColor(getResources().getColor(R.color.black));
                }
                PubnubCall();
                break;
            case (R.id.fanStage1):
                if (!fanStage1.isChecked()) {
                    fan_stage1val = 1;
//                    fanStage1.setText("ON");
//                    fanStage1.setTextColor(getResources().getColor(R.color.primary));
                } else {
                    fan_stage1val = 0;
//                    fanStage1.setText("OFF");
//                    fanStage1.setTextColor(getResources().getColor(R.color.black));
                }
                PubnubCall();
                break;
            case (R.id.fanStage2):
                if (!fanStage2.isChecked()) {
                    fan_stage2val = 1;
//                    fanStage2.setText("ON");
//                    fanStage2.setTextColor(getResources().getColor(R.color.primary));
                } else {
                    fan_stage2val = 0;
//                    fanStage2.setText("OFF");
//                    fanStage2.setTextColor(getResources().getColor(R.color.black));
                }
                PubnubCall();
                break;
            case (R.id.heatingStage1):
                if (!heatingStage1.isChecked()) {
                    heating_stage1val = 1;
//                    heatingStage1.setText("ON");
//                    heatingStage1.setTextColor(getResources().getColor(R.color.primary));
                } else {
                    heating_stage1val = 0;
//                    heatingStage1.setText("OFF");
//                    heatingStage1.setTextColor(getResources().getColor(R.color.black));
                }
                PubnubCall();
                break;
            case (R.id.heatingStage2):
                if (!heatingStage2.isChecked()) {
//                    heatingStage2.setText("ON");
                    heating_stage2val = 1;
//                    heatingStage2.setTextColor(getResources().getColor(R.color.primary));
                } else {
                    heating_stage2val = 0;
//                    heatingStage2.setText("OFF");
//                    heatingStage2.setTextColor(getResources().getColor(R.color.black));
                }
                PubnubCall();
                break;
            case (R.id.humidifier):
                if (!humidifier.isChecked()) {
//                    humidifier.setText("ON");
                    humidifierval = 1;
//                    humidifier.setTextColor(getResources().getColor(R.color.primary));
                } else {
//                    humidifier.setText("OFF");
                    humidifierval = 0;
//                    humidifier.setTextColor(getResources().getColor(R.color.black));
                }
                PubnubCall();
                break;
        }

    }

    public void setTheValues(String SummaryData) {
        try {
            JSONObject s = new JSONObject(SummaryData);
            Summary_Data sd = null;
            if (s.getBoolean("isPaired")) {
                sd = new Summary_Data(s.getString("ccu_name"), s.getString("date_time"), s.getInt("building_no_cooler"), s.getInt("building_no_hotter"), s.getInt("user_no_cooler"), s.getInt("user_no_hotter"),
                        s.getInt("cm_cur_temp"), s.getInt("cm_cur_humidity"), s.getInt("cooling_stage_1"), s.getInt("cooling_stage_2"),
                        s.getInt("heating_stage_1"), s.getInt("heating_stage_2"), s.getInt("fan_stage_1"), s.getInt("fan_stage_2"), s.getInt("humidifier"), s.getBoolean("isEconomizerAvailable"),
                        s.getBoolean("isPaired"), s.getInt("analog1_damperPos"), s.getInt("analog2_damperPos"), s.getInt("analog3_damperPos"),
                        s.getInt("analog4_damperPos"), s.getDouble("mInsideAirEnthalpy"), s.getDouble("mOutsideAirEnthalpy"), s.getInt("mOutsideAirTemperature"), s.getInt("mOutsideAirMaxTemp"),
                        s.getInt("mOutsideAirMinTemp"), s.getInt("mOutsideAirHumidity"), s.getInt("mOutsideAirMaxHumidity"), s.getInt("mOutsideAirMinHumidity"),
                        s.optInt("mCO2Level", 0), s.optInt("mCO2LevelThreshold", 2000), s.getInt("mMixedAirTemperature"), s.getInt("mReturnAirTemperature"), s.getInt("mDamperPos"), s.getString("zone_summary"),
                        s.optInt("mNO2Level", 0), s.optInt("mNO2LevelThreshold", 10), s.optInt("mCOLevel", 0), s.optInt("mCOLevelThreshold", 250), s.optBoolean("isPressureSensorPaired", false),
                        s.optDouble("mPressureLevel", 0), s.optDouble("mPressureLevelThreshold", 0), s.optBoolean("isCOPaired", false), s.optBoolean("isNO2Paired", false)
                        , s.optInt("analog1_type", -1), s.optInt("analog2_type", -1), s.optInt("analog3_type", -1), s.optInt("analog4_type", -1));

            } else {
                sd = new Summary_Data(s.getString("ccu_name"), s.getString("date_time"), s.getInt("building_no_cooler"), s.getInt("building_no_hotter"), s.getInt("user_no_cooler"), s.getInt("user_no_hotter"),
                        s.getInt("cm_cur_temp"), s.getInt("cm_cur_humidity"), s.getInt("cooling_stage_1"), s.getInt("cooling_stage_2"),
                        s.getInt("heating_stage_1"), s.getInt("heating_stage_2"), s.getInt("fan_stage_1"), s.getInt("fan_stage_2"), s.getInt("humidifier"), s.getBoolean("isEconomizerAvailable"),
                        s.getBoolean("isPaired"), s.getInt("analog1_damperPos"), s.getInt("analog2_damperPos"), s.getInt("analog3_damperPos"),
                        s.getInt("analog4_damperPos"), s.getDouble("mInsideAirEnthalpy"), s.getDouble("mOutsideAirEnthalpy"), s.getInt("mOutsideAirTemperature"), s.getInt("mOutsideAirMaxTemp"),
                        s.getInt("mOutsideAirMinTemp"), s.getInt("mOutsideAirHumidity"), s.getInt("mOutsideAirMaxHumidity"), s.getInt("mOutsideAirMinHumidity"),
                        s.getInt("mMixedAirTemperature"), s.getInt("mReturnAirTemperature"), s.getInt("mDamperPos"), s.getString("zone_summary"),
                        s.optBoolean("isPressureSensorPaired", false), s.optDouble("mPressureLevel", 0), s.optDouble("mPressureLevelThreshold", 0)
                        , s.optInt("analog1_type", -1), s.optInt("analog2_type", -1), s.optInt("analog3_type", -1), s.optInt("analog4_type", -1));
            }
            if (isVisible() && CCU_Details.getSingletonContext().viewPager.getCurrentItem() == 2) {
//                analog1.setMinValue(0);
//                analog1.setMaxValue(100);
                ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, damperValues);
                analog1.setAdapter(adapter);
                analog2.setAdapter(adapter);
                analog3.setAdapter(adapter);
                analog4.setAdapter(adapter);

                analog1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        analog1position = (int) parent.getItemAtPosition(position);
                        PubnubCall();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                analog2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        analog2position = (int) parent.getItemAtPosition(position);
                        PubnubCall();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                analog3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        analog3position = (int) parent.getItemAtPosition(position);
                        PubnubCall();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                analog4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        analog4position = (int) parent.getItemAtPosition(position);
                        PubnubCall();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                if (sd.getCooling_stage_1() == 0) {
                    cooling_stage1val = 0;
                    coolingStage1.setChecked(false);
//                    coolingStage1.setText("OFF");
//                    coolingStage1.setTextColor(getResources().getColor(R.color.black));
                } else if (sd.getCooling_stage_1() == 1) {
                    cooling_stage1val = 1;
                    coolingStage1.setChecked(true);
//                    coolingStage1.setText("ON");
//                    coolingStage1.setTextColor(getResources().getColor(R.color.primary));
                } else if (sd.getCooling_stage_1() == -1) {
                    cooling_stage1val = -1;
                    row1.setVisibility(View.GONE);
                }

                if (sd.getCooling_stage_2() == 0) {
//                    coolingStage2.setText("OFF");
                    cooling_stage2val = 0;
                    coolingStage2.setChecked(false);
//                    coolingStage2.setTextColor(getResources().getColor(R.color.black));
                } else if (sd.getCooling_stage_2() == 1) {
                    cooling_stage2val = 1;
                    coolingStage2.setChecked(true);
//                    coolingStage2.setText("ON");
//                    coolingStage2.setTextColor(getResources().getColor(R.color.primary));
                } else if (sd.getCooling_stage_2() == -1) {
                    cooling_stage2val = -1;
                    row2.setVisibility(View.GONE);
                }

                if (sd.getFan_stage_1() == 0) {
                    fan_stage1val = 0;
                    fanStage1.setChecked(false);
//                    fanStage1.setText("OFF");
//                    fanStage1.setTextColor(getResources().getColor(R.color.black));
                } else if (sd.getFan_stage_1() == 1) {
                    fan_stage1val = 1;
                    fanStage1.setChecked(true);
//                    fanStage1.setText("ON");
//                    fanStage1.setTextColor(getResources().getColor(R.color.primary));
                } else if (sd.getFan_stage_1() == -1) {
                    fan_stage1val = -1;
                    row3.setVisibility(View.GONE);
                }

                if (sd.getFan_stage_2() == 0) {
                    fan_stage2val = 0;
                    fanStage2.setChecked(false);
//                    fanStage2.setText("OFF");
//                    fanStage2.setTextColor(getResources().getColor(R.color.black));
                } else if (sd.getFan_stage_2() == 1) {
                    fan_stage2val = 1;
                    fanStage2.setChecked(true);
//                    fanStage2.setText("ON");
//                    fanStage2.setTextColor(getResources().getColor(R.color.primary));
                } else if (sd.getFan_stage_2() == -1) {
                    row4.setVisibility(View.GONE);
                    fan_stage2val = -1;
                }

                if (sd.getHeating_stage_1() == 0) {
                    heating_stage1val = 0;
                    heatingStage1.setChecked(false);
//                    heatingStage1.setText("OFF");
//                    heatingStage1.setTextColor(getResources().getColor(R.color.black));
                } else if (sd.getHeating_stage_1() == 1) {
                    heating_stage1val = 1;
                    heatingStage1.setChecked(true);
//                    heatingStage1.setText("ON");
//                    heatingStage1.setTextColor(getResources().getColor(R.color.primary));
                } else if (sd.getHeating_stage_1() == -1) {
                    heating_stage1val = -1;
                    row5.setVisibility(View.GONE);
                }
                if (sd.getHeating_stage_2() == 0) {
                    heating_stage2val = 0;
                    heatingStage2.setChecked(false);
//                    heatingStage2.setText("OFF");
//                    heatingStage2.setTextColor(getResources().getColor(R.color.black));
                } else if (sd.getHeating_stage_2() == 1) {
                    heating_stage2val = 1;
                    heatingStage2.setChecked(true);
//                    heatingStage2.setText("ON");
//                    heatingStage2.setTextColor(getResources().getColor(R.color.primary));
                } else if (sd.getHeating_stage_2() == -1) {
                    heating_stage2val = -1;
                    row6.setVisibility(View.GONE);
                }

                if (sd.getHumidifier() == 0) {
                    humidifierval = 0;
                    humidifier.setChecked(false);
//                    humidifier.setText("OFF");
//                    humidifier.setTextColor(getResources().getColor(R.color.black));
                } else if (sd.getHumidifier() == 1) {
                    humidifierval = 1;
                    humidifier.setChecked(true);
//                    humidifier.setText("ON");
//                    humidifier.setTextColor(getResources().getColor(R.color.primary));
                } else if (sd.getHumidifier() == -1) {
                    humidifierval = -1;
                    row7.setVisibility(View.GONE);
                }
                if (sd.getAnalog1_type() == -1) {
                    analog1position = -1;
                    row8.setVisibility(View.GONE);
                }
                if (sd.getAnalog2_type() == -1) {
                    analog2position = -1;
                    row9.setVisibility(View.GONE);
                }
                if (sd.getAnalog3_type() == -1) {
                    analog3position = -1;
                    row10.setVisibility(View.GONE);
                }
                if (sd.getAnalog4_type() == -1) {
                    analog4position = -1;
                    row11.setVisibility(View.GONE);
                }


                analog1.setSelection(sd.getAnalog1_damperPos());
                analog2.setSelection(sd.getAnalog2_damperPos());
                analog3.setSelection(sd.getAnalog3_damperPos());
                analog4.setSelection(sd.getAnalog4_damperPos());
                analog1position = sd.getAnalog1_damperPos();
                analog2position = sd.getAnalog2_damperPos();
                analog3position = sd.getAnalog3_damperPos();
                analog4position = sd.getAnalog4_damperPos();
//                analog2.setValue(sd.getAnalog2_damperPos());
//                analog3.setValue(sd.getAnalog3_damperPos());
//                analog4.setValue(sd.getAnalog4_damperPos());
                coolingStage1.setOnClickListener(SystemTestFragment.this);
                coolingStage2.setOnClickListener(SystemTestFragment.this);
                fanStage1.setOnClickListener(SystemTestFragment.this);
                fanStage2.setOnClickListener(SystemTestFragment.this);
                heatingStage1.setOnClickListener(SystemTestFragment.this);
                heatingStage2.setOnClickListener(SystemTestFragment.this);
                humidifier.setOnClickListener(SystemTestFragment.this);
                dismissDialog();
                checkForOtpEveryMinute();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("JSONException", e.getMessage());
        }
    }

    public void checkForOtpEveryMinute() {
        if (CCU_Details.getSingletonContext().systemTestHandler == null) {
            CCU_Details.getSingletonContext().systemTestHandler = new Handler();
        }

        CCU_Details.getSingletonContext().systemTestUpdate = new Runnable() {
            @Override
            public void run() {//
                if (CCU_Details.getSingletonContext() != null && CCU_Details.getSingletonContext().viewPager.getCurrentItem() == 2) {
                    Log.e("checkingotpsystemtest", "yes");
                    if (Generic_Methods.isNetworkAvailable(CCU_Details.getSingletonContext())) {
                        Query newquery = new Query();
                        newquery.equals("_id", getArguments().getString("ccu_id"));
                        AsyncAppData<GenericJson> summary = Generic_Methods.getKinveyClient().appData("00CCUOneTimePassword", GenericJson.class);
                        summary.get(newquery, new KinveyListCallback<GenericJson>() {

                            @Override
                            public void onSuccess(GenericJson[] genericJsons) {
                                if (genericJsons.length == 0) {
                                    Otp_Verification otp_verification = new Otp_Verification(CCU_Details.getSingletonContext(), getArguments().getString("ccu_id"), 2);
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
                                Otp_Verification otp_verification = new Otp_Verification(CCU_Details.getSingletonContext(), getArguments().getString("ccu_id"), 2);
                                otp_verification.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                otp_verification.show();
                            }
                        });

                    } else {
                        Generic_Methods.getToast(CCU_Details.getSingletonContext(), getResources().getString(R.string.user_offline));
                    }
                    if (CCU_Details.getSingletonContext().systemTestHandler != null) {
                        CCU_Details.getSingletonContext().systemTestHandler.postDelayed(CCU_Details.getSingletonContext().systemTestUpdate, 60000);
                    }
                }

            }
        };

        if (CCU_Details.getSingletonContext().systemTestHandler != null) {
            CCU_Details.getSingletonContext().systemTestHandler.postDelayed(CCU_Details.getSingletonContext().systemTestUpdate, 60000);
        }

    }

    public void PubnubCall() {
        if (Generic_Methods.isNetworkAvailable(CCU_Details.getSingletonContext())) {
            Thread h = new Thread(new Runnable() {
                @Override
                public void run() {
                    String channel = getArguments().getString("ccu_id") + "_Installer_SYSTEST";
                    String msg = Generic_Methods.createPubnubSystemTestMsg(analog1position, analog2position,
                            analog3position, analog4position, cooling_stage1val, cooling_stage2val, fan_stage1val, fan_stage2val,
                            heating_stage1val, heating_stage2val, humidifierval);
                    Generic_Methods.PublishToChannel(channel, msg);
                }
            });
            h.start();
        } else {
            Generic_Methods.getToast(CCU_Details.getSingletonContext(), getResources().getString(R.string.user_offline));
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

    public void dismissDialog() {
        if (Pleasewait != null) {
            if (Pleasewait.isShowing()) {
                Pleasewait.dismiss();
                Pleasewait = null;
            }
        }
    }
}

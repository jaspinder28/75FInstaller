package com.x75f.installer.Fragments;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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


public class SystemTestFragment extends Fragment implements View.OnClickListener, NumberPicker.OnScrollListener {
    static int mStackLevel = 0;
    @InjectView(R.id.coolingStage1)
    Button coolingStage1;
    @InjectView(R.id.coolingStage2)
    Button coolingStage2;
    @InjectView(R.id.fanStage1)
    Button fanStage1;
    @InjectView(R.id.fanStage2)
    Button fanStage2;
    @InjectView(R.id.heatingStage1)
    Button heatingStage1;
    @InjectView(R.id.heatingStage2)
    Button heatingStage2;
    @InjectView(R.id.humidifier)
    Button humidifier;
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
    NumberPicker analog1;
    @InjectView(R.id.analog2)
    NumberPicker analog2;
    @InjectView(R.id.analog3)
    NumberPicker analog3;
    @InjectView(R.id.analog4)
    NumberPicker analog4;
    String ccuname;
    private static ProgressDialog Pleasewait;
    public int cooling_stage1val;
    public int cooling_stage2val;
    public int fan_stage1val;
    public int fan_stage2val;
    public int heating_stage1val;
    public int heating_stage2val;
    public int humidifierval;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.system_test_fragment, container, false);
        ButterKnife.inject(this, v);
        return v;
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (SystemTestFragment.this != null && isVisible() && CCU_Details.viewPager.getCurrentItem() == 2) {
            Query newquery = new Query();
            ccuname = getArguments().getString("ccuname");
            newquery.equals("ccu_name", ccuname);
            if (Generic_Methods.isNetworkAvailable(CCU_Details.getSingletonContext())) {
                if (Pleasewait == null) {
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
                            setTheValues(Summarydata);

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

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case (R.id.coolingStage1):
                if (coolingStage1.getText().toString().equalsIgnoreCase("OFF")) {
                    coolingStage1.setText("ON");
                    cooling_stage1val = 1;
                    coolingStage1.setTextColor(getResources().getColor(R.color.primary));
                } else {
                    cooling_stage1val = 0;
                    coolingStage1.setText("OFF");
                    coolingStage1.setTextColor(getResources().getColor(R.color.black));
                }
                PubnubCall();
                break;
            case (R.id.coolingStage2):
                if (coolingStage2.getText().toString().equalsIgnoreCase("OFF")) {
                    cooling_stage2val = 1;
                    coolingStage2.setText("ON");
                    coolingStage2.setTextColor(getResources().getColor(R.color.primary));
                } else {
                    cooling_stage2val = 0;
                    coolingStage2.setText("OFF");
                    coolingStage2.setTextColor(getResources().getColor(R.color.black));
                }
                PubnubCall();
                break;
            case (R.id.fanStage1):
                if (fanStage1.getText().toString().equalsIgnoreCase("OFF")) {
                    fan_stage1val = 1;
                    fanStage1.setText("ON");
                    fanStage1.setTextColor(getResources().getColor(R.color.primary));
                } else {
                    fan_stage1val = 0;
                    fanStage1.setText("OFF");
                    fanStage1.setTextColor(getResources().getColor(R.color.black));
                }
                PubnubCall();
                break;
            case (R.id.fanStage2):
                if (fanStage2.getText().toString().equalsIgnoreCase("OFF")) {
                    fan_stage2val = 1;
                    fanStage2.setText("ON");
                    fanStage2.setTextColor(getResources().getColor(R.color.primary));
                } else {
                    fan_stage2val = 0;
                    fanStage2.setText("OFF");
                    fanStage2.setTextColor(getResources().getColor(R.color.black));
                }
                PubnubCall();
                break;
            case (R.id.heatingStage1):
                if (heatingStage1.getText().toString().equalsIgnoreCase("OFF")) {
                    heating_stage1val = 1;
                    heatingStage1.setText("ON");
                    heatingStage1.setTextColor(getResources().getColor(R.color.primary));
                } else {
                    heating_stage1val = 0;
                    heatingStage1.setText("OFF");
                    heatingStage1.setTextColor(getResources().getColor(R.color.black));
                }
                PubnubCall();
                break;
            case (R.id.heatingStage2):
                if (heatingStage2.getText().toString().equalsIgnoreCase("OFF")) {
                    heatingStage2.setText("ON");
                    heating_stage2val = 1;
                    heatingStage2.setTextColor(getResources().getColor(R.color.primary));
                } else {
                    heating_stage2val = 0;
                    heatingStage2.setText("OFF");
                    heatingStage2.setTextColor(getResources().getColor(R.color.black));
                }
                PubnubCall();
                break;
            case (R.id.humidifier):
                if (humidifier.getText().toString().equalsIgnoreCase("OFF")) {
                    humidifier.setText("ON");
                    humidifierval = 1;
                    humidifier.setTextColor(getResources().getColor(R.color.primary));
                } else {
                    humidifier.setText("OFF");
                    humidifierval = 0;
                    humidifier.setTextColor(getResources().getColor(R.color.black));
                }
                PubnubCall();
                break;
        }

    }

    public void setTheValues(String SummaryData) {
        try {
            JSONObject s = new JSONObject(SummaryData);
            Summary_Data sd = new Summary_Data(s.getString("ccu_name"), s.getString("date_time"), s.getInt("building_no_cooler"), s.getInt("building_no_hotter"), s.getInt("user_no_cooler"), s.getInt("user_no_hotter"),
                    s.getInt("cm_cur_temp"), s.getInt("cm_cur_humidity"), s.getInt("cooling_stage_1"), s.getInt("cooling_stage_2"),
                    s.getInt("heating_stage_1"), s.getInt("heating_stage_2"), s.getInt("fan_stage_1"), s.getInt("fan_stage_2"), s.getInt("humidifier"), s.getBoolean("isEconomizerAvailable"),
                    s.getBoolean("isPaired"), s.getInt("analog1_damperPos"), s.getInt("analog2_damperPos"), s.getInt("analog3_damperPos"),
                    s.getInt("analog4_damperPos"), s.getDouble("mInsideAirEnthalpy"), s.getDouble("mOutsideAirEnthalpy"), s.getInt("mOutsideAirTemperature"), s.getInt("mOutsideAirMaxTemp"),
                    s.getInt("mOutsideAirMinTemp"), s.getInt("mOutsideAirHumidity"), s.getInt("mOutsideAirMaxHumidity"), s.getInt("mOutsideAirMinHumidity"),
                    s.getInt("mCO2Level"), s.getInt("mCO2LevelThreshold"), s.getInt("mMixedAirTemperature"), s.getInt("mReturnAirTemperature"), s.getInt("mDamperPos"), s.getString("zone_summary"));
            if (isVisible() && CCU_Details.viewPager.getCurrentItem() == 2) {
                analog1.setMinValue(0);
                analog1.setMaxValue(100);
                analog2.setMinValue(0);
                analog2.setMaxValue(100);
                analog3.setMinValue(0);
                analog3.setMaxValue(100);
                analog4.setMinValue(0);
                analog4.setMaxValue(100);
                if (sd.getCooling_stage_1() == 0) {
                    cooling_stage1val = 0;
                    coolingStage1.setText("OFF");
                    coolingStage1.setTextColor(getResources().getColor(R.color.black));
                } else if (sd.getCooling_stage_1() == 1) {
                    cooling_stage1val = 1;
                    coolingStage1.setText("ON");
                    coolingStage1.setTextColor(getResources().getColor(R.color.primary));
                } else if (sd.getCooling_stage_1() == -1) {
                    cooling_stage1val = -1;
                    row1.setVisibility(View.GONE);
                }

                if (sd.getCooling_stage_2() == 0) {
                    coolingStage2.setText("OFF");
                    cooling_stage2val = 0;
                    coolingStage2.setTextColor(getResources().getColor(R.color.black));
                } else if (sd.getCooling_stage_2() == 1) {
                    cooling_stage2val = 1;
                    coolingStage2.setText("ON");
                    coolingStage2.setTextColor(getResources().getColor(R.color.primary));
                } else if (sd.getCooling_stage_2() == -1) {
                    cooling_stage2val = -1;
                    row2.setVisibility(View.GONE);
                }

                if (sd.getFan_stage_1() == 0) {
                    fan_stage1val = 0;
                    fanStage1.setText("OFF");
                    fanStage1.setTextColor(getResources().getColor(R.color.black));
                } else if (sd.getFan_stage_1() == 1) {
                    fan_stage1val = 1;
                    fanStage1.setText("ON");
                    fanStage1.setTextColor(getResources().getColor(R.color.primary));
                } else if (sd.getFan_stage_1() == -1) {
                    fan_stage1val = -1;
                    row3.setVisibility(View.GONE);
                }

                if (sd.getFan_stage_2() == 0) {
                    fan_stage2val = 0;
                    fanStage2.setText("OFF");
                    fanStage2.setTextColor(getResources().getColor(R.color.black));
                } else if (sd.getFan_stage_2() == 1) {
                    fan_stage2val = 1;
                    fanStage2.setText("ON");
                    fanStage2.setTextColor(getResources().getColor(R.color.primary));
                } else if (sd.getFan_stage_2() == -1) {
                    row4.setVisibility(View.GONE);
                    fan_stage2val = -1;
                }

                if (sd.getHeating_stage_1() == 0) {
                    heating_stage1val = 0;
                    heatingStage1.setText("OFF");
                    heatingStage1.setTextColor(getResources().getColor(R.color.black));
                } else if (sd.getHeating_stage_1() == 1) {
                    heating_stage1val = 1;
                    heatingStage1.setText("ON");
                    heatingStage1.setTextColor(getResources().getColor(R.color.primary));
                } else if (sd.getHeating_stage_1() == -1) {
                    heating_stage1val = -1;
                    row5.setVisibility(View.GONE);
                }
                if (sd.getHeating_stage_2() == 0) {
                    heating_stage2val = 0;
                    heatingStage2.setText("OFF");
                    heatingStage2.setTextColor(getResources().getColor(R.color.black));
                } else if (sd.getHeating_stage_2() == 1) {
                    heating_stage2val = 1;
                    heatingStage2.setText("ON");
                    heatingStage2.setTextColor(getResources().getColor(R.color.primary));
                } else if (sd.getHeating_stage_2() == -1) {
                    heating_stage2val = -1;
                    row6.setVisibility(View.GONE);
                }

                if (sd.getHumidifier() == 0) {
                    humidifierval = 0;
                    humidifier.setText("OFF");
                    humidifier.setTextColor(getResources().getColor(R.color.black));
                } else if (sd.getHumidifier() == 1) {
                    humidifierval = 1;
                    humidifier.setText("ON");
                    humidifier.setTextColor(getResources().getColor(R.color.primary));
                } else if (sd.getHumidifier() == -1) {
                    humidifierval = -1;
                    row7.setVisibility(View.GONE);
                }
                analog1.setValue(sd.getAnalog1_damperPos());
                analog2.setValue(sd.getAnalog2_damperPos());
                analog3.setValue(sd.getAnalog3_damperPos());
                analog4.setValue(sd.getAnalog4_damperPos());
                coolingStage1.setOnClickListener(SystemTestFragment.this);
                coolingStage2.setOnClickListener(SystemTestFragment.this);
                fanStage1.setOnClickListener(SystemTestFragment.this);
                fanStage2.setOnClickListener(SystemTestFragment.this);
                heatingStage1.setOnClickListener(SystemTestFragment.this);
                heatingStage2.setOnClickListener(SystemTestFragment.this);
                analog1.setOnScrollListener(this);
                humidifier.setOnClickListener(SystemTestFragment.this);
                if (Pleasewait != null && Pleasewait.isShowing()) {
                    Pleasewait.dismiss();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void PubnubCall() {
        if (Generic_Methods.isNetworkAvailable(CCU_Details.getSingletonContext())) {
            String channel = getArguments().getString("ccu_id") + "_Installer_SYSTEST";
            String msg = Generic_Methods.createPubnubSystemTestMsg(analog1.getValue(), analog2.getValue(),
                    analog3.getValue(), analog4.getValue(), cooling_stage1val, cooling_stage2val, fan_stage1val, fan_stage2val,
                    heating_stage1val, heating_stage2val, humidifierval);
            Generic_Methods.PublishToChannel(channel, msg);
        } else {
            Generic_Methods.getToast(CCU_Details.getSingletonContext(), getResources().getString(R.string.user_offline));
        }
    }


    @Override
    public void onScrollStateChange(NumberPicker view, int scrollState) {
        switch (view.getId()) {
            case (R.id.analog1):
                if (scrollState == SCROLL_STATE_IDLE) {
                    PubnubCall();
                }
                break;
            case (R.id.analog2):
                if (scrollState == SCROLL_STATE_IDLE) {
                    PubnubCall();
                }
                break;
            case (R.id.analog3):
                if (scrollState == SCROLL_STATE_IDLE) {
                    PubnubCall();
                }
                break;
            case (R.id.analog4):
                if (scrollState == SCROLL_STATE_IDLE) {
                    PubnubCall();
                }
                break;
        }
    }


}

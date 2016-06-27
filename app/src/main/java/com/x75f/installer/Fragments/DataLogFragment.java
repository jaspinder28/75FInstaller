package com.x75f.installer.Fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

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
import com.x75f.installer.Utils.Helper;
import com.x75f.installer.Utils.Zone_log;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class DataLogFragment extends Fragment {

    static String ccu_id1;
    @InjectView(R.id.tvCCUName)
    TextView tvCCUName;
    @InjectView(R.id.timezone)
    TextView timezone;
    @InjectView(R.id.tvMode)
    TextView tvMode;
    @InjectView(R.id.tvEnthaply)
    TextView tvEnthaply;
    @InjectView(R.id.tvOutsideTemp)
    TextView tvOutsideTemp;
    @InjectView(R.id.tvOutsideDamper)
    TextView tvOutsideDamper;
    @InjectView(R.id.tvCO2Level)
    TextView tvCO2Level;
    @InjectView(R.id.tvEcon)
    TextView tvEcon;
    @InjectView(R.id.tvMat)
    TextView tvMat;
    @InjectView(R.id.tvComfort)
    TextView tvComfort;
    @InjectView(R.id.tvHVAC)
    TextView tvHVAC;
    @InjectView(R.id.tvHVACDetail1)
    TextView tvHVACDetail1;
    @InjectView(R.id.tvHVACDetail2)
    TextView tvHVACDetail2;
    @InjectView(R.id.tvHVACDetail3)
    TextView tvHVACDetail3;
    @InjectView(R.id.tvHVACDetail4)
    TextView tvHVACDetail4;
    @InjectView(R.id.tvHVACDetail5)
    TextView tvHVACDetail5;
    @InjectView(R.id.tvHVACDetail6)
    TextView tvHVACDetail6;
    ArrayList<Zone_log> zone_list;
    private Handler handler = new Handler();
    private static ProgressDialog Pleasewait;
    @InjectView(R.id.ZoneList)
    ListView ZoneList;

    public static ArrayList<ArrayList<Zone_log>> mainlist;
    private static int count = 0;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.data_log_fragment, container, false);
        ButterKnife.inject(this, v);


        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        mainlist = new ArrayList<>();
        zone_list = new ArrayList<>();
        count = 0;

        if (isVisible()) {
            if (isVisible() && CCU_Details.viewPager.getCurrentItem() == 1) {
                try {
                    Runnable runnable = new Runnable() {

                        @Override
                        public void run() {
                            try {
                                if (isVisible() && CCU_Details.viewPager.getCurrentItem() == 1) {
                                    QueryDataAgain();
                                    handler.postDelayed(this, 60000);
                                }
                            } catch (Exception e) {
                                Generic_Methods.getToast(CCU_Details.getSingletonContext(), e.getMessage());
                            }
                        }
                    };
                    runnable.run();


                } catch (Exception e) {
                    Generic_Methods.getToast(CCU_Details.getSingletonContext(), e.getMessage());
                }
            }
        }
    }


    public void QueryDataAgain() {
        try {
            if (isVisible() && CCU_Details.viewPager.getCurrentItem() == 1) {
                if (count < 10) {
                    count++;
                } else if (count == 10) {
                    mainlist.remove(0);
                }
                Query newquery = new Query();
                String collectionName = getArguments().getString("ccu_id") + "SystemTS1";
                newquery.addSort("date_time", AbstractQuery.SortOrder.DESC);
                newquery.setLimit(1);
                if (Generic_Methods.isNetworkAvailable(CCU_Details.getSingletonContext())) {
                    if (Pleasewait == null) {
                        Pleasewait = ProgressDialog.show(CCU_Details.getSingletonContext(), "", "Please Wait...");
                    }

                    AsyncAppData<Data_Log> summary = CCU_Details.mKinveyClient.appData(collectionName, Data_Log.class);
                    summary.get(newquery, new KinveyListCallback<Data_Log>() {
                        @Override
                        public void onSuccess(Data_Log[] data_logs) {
                            settingUpValues setting = new settingUpValues();
                            setting.execute(data_logs[0]);
//                            settingUpValues(data_logs[0]);
                        }

                        @Override
                        public void onFailure(Throwable throwable) {
                            if (Pleasewait != null && Pleasewait.isShowing()) {
                                Pleasewait.dismiss();
                                Pleasewait = null;
                            }
                            Generic_Methods.getToast(CCU_Details.getSingletonContext(), throwable.getMessage());
                        }

                    });
                } else {
                    Generic_Methods.getToast(CCU_Details.getSingletonContext(), getResources().getString(R.string.user_offline));
                }
            }
        } catch (Exception e) {
            Generic_Methods.getToast(CCU_Details.getSingletonContext(), e.getMessage());
        }
    }

    String mode1 = "";
    String mode2 = "";
    String econ1;
    String econ2;
    String cooling_stage1 = null;
    String cooling_stage2 = null;
    String heating_stage1 = null;
    String heating_stage2 = null;
    String fan_stage1 = null;
    String fan_stage2 = null;
    String humidifier = null;

    public class settingUpValues extends AsyncTask<Data_Log, Void, Data_Log> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Data_Log data_log) {
            super.onPostExecute(data_log);
            tvCCUName.setText(getArguments().getString("ccuname"));
            timezone.setText(data_log.getDate_time());
            tvMode.setText(mode1 + "/" + mode2);
            tvEnthaply.setText(String.format("%.2f", data_log.getInside_enthalpy()) + "/" + String.format("%.2f", data_log.getEnv_enthalpy()));
            tvOutsideTemp.setText(String.format("%.2f", data_log.getEnv_temp()) + "");
            tvOutsideDamper.setText(data_log.getDcv_damper_pos() + "");
            tvCO2Level.setText(data_log.getDcv_co2_val() + "(" + data_log.getDcv_co2_limit() + ")");
            tvEcon.setText(econ1 + "/" + econ2);
            tvMat.setText(String.format("%.2f", data_log.getEcon_mixed_airflow_temp()) + "/" + String.format("%.2f", data_log.getEcon_return_airflow_temp()));
            tvComfort.setText(String.format("%.2f", data_log.getComfort_index()) + "");
            if ((data_log.getHvac_dx_stage1() == 0 || data_log.getHvac_dx_stage1() == 1) && (data_log.getHvac_dx_stage2() == 0 || data_log.getHvac_dx_stage2() == 1)) {
                tvHVACDetail1.setText("Cooling Stage 1(" + cooling_stage1 + ") and 2(" + cooling_stage2 + ")");
            } else if ((data_log.getHvac_dx_stage1() == -1) && (data_log.getHvac_dx_stage2() == 0 || data_log.getHvac_dx_stage2() == 1)) {
                tvHVACDetail1.setText("Cooling Stage 2(" + cooling_stage2 + ")");
            } else if ((data_log.getHvac_dx_stage2() == -1) && (data_log.getHvac_dx_stage1() == 0 || data_log.getHvac_dx_stage1() == 1)) {
                tvHVACDetail1.setText("Cooling Stage 1(" + cooling_stage1 + ")");
            } else if (data_log.getHvac_dx_stage1() == -1 && data_log.getHvac_dx_stage2() == -1) {
                tvHVACDetail1.setVisibility(View.GONE);
            }

            if ((data_log.getHvac_heat_stage1() == 0 || data_log.getHvac_heat_stage1() == 1) && (data_log.getHvac_heat_stage2() == 0 || data_log.getHvac_heat_stage2() == 1)) {
                tvHVACDetail2.setText("Heating Stage 1(" + heating_stage1 + ") and 2(" + heating_stage2 + ")");
            } else if ((data_log.getHvac_heat_stage1() == -1) && (data_log.getHvac_heat_stage2() == 0 || data_log.getHvac_heat_stage2() == 1)) {
                tvHVACDetail2.setText("Heating Stage 2(" + heating_stage2 + ")");
            } else if ((data_log.getHvac_heat_stage2() == -1) && (data_log.getHvac_heat_stage1() == 0 || data_log.getHvac_heat_stage1() == 1)) {
                tvHVACDetail2.setText("Heating Stage 1(" + heating_stage1 + ")");
            } else if (data_log.getHvac_heat_stage1() == -1 && data_log.getHvac_heat_stage2() == -1) {
                tvHVACDetail2.setVisibility(View.GONE);
            }

            if ((data_log.getHvac_fan_stage1() == 0 || data_log.getHvac_fan_stage1() == 1) && (data_log.getHvac_fan_stage2() == 0 || data_log.getHvac_fan_stage2() == 1)) {
                tvHVACDetail3.setText("Fan Stage 1(" + fan_stage1 + ") and 2(" + fan_stage2 + ")");
            } else if ((data_log.getHvac_fan_stage1() == -1) && (data_log.getHvac_fan_stage2() == 0 || data_log.getHvac_fan_stage2() == 1)) {
                tvHVACDetail3.setText("Fan Stage 2(" + fan_stage2 + ")");
            } else if ((data_log.getHvac_fan_stage2() == -1) && (data_log.getHvac_fan_stage1() == 0 || data_log.getHvac_fan_stage1() == 1)) {
                tvHVACDetail3.setText("Fan Stage 1(" + fan_stage1 + ")");
            } else if (data_log.getHvac_fan_stage1() == -1 && data_log.getHvac_fan_stage2() == -1) {
                tvHVACDetail3.setVisibility(View.GONE);
            }

            if (data_log.getHvac_humidifier() == -1) {
                tvHVACDetail4.setVisibility(View.GONE);
            } else {
                tvHVACDetail4.setText("Humidifier(" + humidifier + ")");
            }

            tvHVACDetail5.setText("Economizer(" + econ1 + "/" + econ2 + ")");
            tvHVACDetail6.setText("Analog(" + data_log.getHvac_analog1() + "/" + data_log.getHvac_analog2() + "/" + data_log.getHvac_analog3() + "/" + data_log.getHvac_analog4() + ")");
            if (isVisible() && CCU_Details.viewPager.getCurrentItem() == 1) {
                try {
                    Query newquery = new Query();
                    String collectionName = getArguments().getString("ccu_id") + "ZonesTS1";
                    newquery.setLimit(10);
                    newquery.equals("date_time", data_log.getDate_time());

                    AsyncAppData<Zone_log> summary = CCU_Details.mKinveyClient.appData(collectionName, Zone_log.class);
                    summary.get(newquery, new KinveyListCallback<Zone_log>() {
                        @Override
                        public void onSuccess(Zone_log[] zone_logs) {
                            if (Pleasewait != null && Pleasewait.isShowing()) {
                                Pleasewait.dismiss();
                                Pleasewait = null;
                            }
                            zone_list.addAll(Arrays.asList(zone_logs));

                            if (count <= 10) {
                                mainlist.add(new ArrayList<Zone_log>(zone_list));
                                Data_Log_Zone_Adapter zones_adapter = new Data_Log_Zone_Adapter(CCU_Details.getSingletonContext(), mainlist, count);
                                ZoneList.setAdapter(zones_adapter);
                                Helper.getListViewSize(ZoneList);
                            }
                            zone_list.clear();

                        }

                        @Override
                        public void onFailure(Throwable throwable) {
                            if (Pleasewait != null && Pleasewait.isShowing()) {
                                Pleasewait.dismiss();
                                Pleasewait = null;
                            }
                            Generic_Methods.getToast(CCU_Details.getSingletonContext(), throwable.getMessage());
                        }

                    });
                } catch (Exception e) {
                    Generic_Methods.getToast(CCU_Details.getSingletonContext(), e.getMessage());
                }
            }
        }

        @Override
        protected Data_Log doInBackground(Data_Log... params) {
            Data_Log data_log = params[0];
            if (data_log.getOperational_mode() == 0) {
                mode1 = "OFF";
            } else if (data_log.getOperational_mode() == 1) {
                mode1 = "AUTO";
            } else if (data_log.getOperational_mode() == 2) {
                mode1 = "COOLING";
            } else if (data_log.getOperational_mode() == 3) {
                mode1 = "HEATING";
            }

            if (data_log.getConditioning_mode() == 0) {
                mode2 = "COOLING";
            } else {
                mode2 = "HEATING";
            }
            if (data_log.getEcon_available() == 0) {
                econ1 = "NO";
            } else {
                econ1 = "YES";
            }
            if (data_log.getEcon_used() == 0) {
                econ2 = "NO";
            } else {
                econ2 = "YES";
            }

            if (data_log.getHvac_dx_stage1() == 0) {
                cooling_stage1 = "Off";
            } else if (data_log.getHvac_dx_stage1() == 1) {
                cooling_stage1 = "On";
            } else if (data_log.getHvac_dx_stage1() == -1) {
                cooling_stage1 = "not availible";
            }

            if (data_log.getHvac_dx_stage2() == 0) {
                cooling_stage2 = "Off";
            } else if (data_log.getHvac_dx_stage2() == 1) {
                cooling_stage2 = "On";
            } else if (data_log.getHvac_dx_stage2() == -1) {
                cooling_stage2 = "not availible";
            }
            if (data_log.getHvac_heat_stage1() == 0) {
                heating_stage1 = "Off";
            } else if (data_log.getHvac_heat_stage1() == 1) {
                heating_stage1 = "On";
            } else if (data_log.getHvac_heat_stage1() == -1) {
                heating_stage1 = "not availible";
            }
            if (data_log.getHvac_heat_stage2() == 0) {
                heating_stage2 = "Off";
            } else if (data_log.getHvac_heat_stage2() == 1) {
                heating_stage2 = "On";
            } else if (data_log.getHvac_heat_stage2() == -1) {
                heating_stage2 = "not availible";
            }
            if (data_log.getHvac_fan_stage1() == 0) {
                fan_stage1 = "Off";
            } else if (data_log.getHvac_fan_stage1() == 1) {
                fan_stage1 = "On";
            } else if (data_log.getHvac_fan_stage1() == -1) {
                fan_stage1 = "not availible";
            }
            if (data_log.getHvac_fan_stage2() == 0) {
                fan_stage2 = "Off";
            } else if (data_log.getHvac_fan_stage2() == 1) {
                fan_stage2 = "On";
            } else if (data_log.getHvac_fan_stage2() == -1) {
                fan_stage2 = "not availible";
            }

            if (data_log.getHvac_humidifier() == 0) {
                humidifier = "Off";
            } else if (data_log.getHvac_humidifier() == 1) {
                humidifier = "On";
            } else if (data_log.getHvac_humidifier() == -1) {
                humidifier = "not availible";
            }
            return data_log;
        }
    }

}

package com.x75f.installer.Adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;

import com.x75f.installer.R;
import com.x75f.installer.Utils.Zone_log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class Data_Log_Zone_Adapter extends BaseAdapter {
    ArrayList<Zone_log> zone_list;
    ArrayList<ArrayList<Zone_log>> mainZoneList;
    ArrayList<String> ccuMainData;
    Context c;
    ViewHolder view_holder;
    View row;
    LayoutInflater inflater;
    int count;
    String stringdata = "";
    String mode1 = "";
    String mode2 = "";
    String econ1;
    String econ2;
    String cooling_stage1 = "";
    String cooling_stage2 = "";
    String heating_stage1 = "";
    String heating_stage2 = "";
    String fan_stage1 = "";
    String fan_stage2 = "";
    String humidifier = "";
    String ccuname;

    public Data_Log_Zone_Adapter(Context c, ArrayList<ArrayList<Zone_log>> data, int count, ArrayList<String> ccuMainData1, String ccuname) {
        zone_list = new ArrayList<>();
        mainZoneList = new ArrayList<>();
        ccuMainData = new ArrayList<>();
        mainZoneList.clear();
        ccuMainData.clear();
        this.ccuMainData = ccuMainData1;
        mainZoneList = data;
        this.c = c;
        this.ccuname = ccuname;
        this.count = count;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mainZoneList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        row = convertView;
        view_holder = null;
        if (row == null) {
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.zone_log_row, parent, false);
            view_holder = new ViewHolder(row);
            row.setTag(view_holder);

        } else {
            view_holder = (ViewHolder) row.getTag();
        }
        if (mainZoneList.size() > 0) {

            try {
                final JSONObject s1 = new JSONObject(ccuMainData.get(ccuMainData.size() - position - 1));

                if (s1.optInt("operational_mode") == 0) {
                    mode1 = "OFF";
                } else if (s1.optInt("operational_mode") == 1) {
                    mode1 = "AUTO";
                } else if (s1.optInt("operational_mode") == 2) {
                    mode1 = "COOLING";
                } else if (s1.optInt("operational_mode") == 3) {
                    mode1 = "HEATING";
                }

                if (s1.optInt("conditioning_mode") == 0) {
                    mode2 = "COOLING";
                } else {
                    mode2 = "HEATING";
                }
                if (s1.optInt("econ_available") == 0) {
                    econ1 = "NO";
                } else {
                    econ1 = "YES";
                }
                if (s1.optInt("econ_used") == 0) {
                    econ2 = "NO";
                } else {
                    econ2 = "YES";
                }
                if (s1.optInt("hvac_dx_stage1") != 0) {
                    if (s1.optInt("dx_stage1_onoff") == 0) {
                        cooling_stage1 = "Off";
                    } else if (s1.optInt("dx_stage1_onoff") == 1) {
                        cooling_stage1 = "On";
                    } else if (s1.optInt("dx_stage1_onoff") == -1) {
                        cooling_stage1 = "not availible";
                    }
                }
                if (s1.optInt("hvac_dx_stage2") != 0) {
                    if (s1.optInt("dx_stage2_onoff") == 0) {
                        cooling_stage2 = "Off";
                    } else if (s1.optInt("dx_stage2_onoff") == 1) {
                        cooling_stage2 = "On";
                    } else if (s1.optInt("dx_stage2_onoff") == -1) {
                        cooling_stage2 = "not availible";
                    }
                }
                if (s1.optInt("hvac_heat_stage1") != 0) {
                    if (s1.optInt("heat_stage1_onoff") == 0) {
                        heating_stage1 = "Off";
                    } else if (s1.optInt("heat_stage1_onoff") == 1) {
                        heating_stage1 = "On";
                    } else if (s1.optInt("heat_stage1_onoff") == -1) {
                        heating_stage1 = "not availible";
                    }
                }
                if (s1.optInt("hvac_heat_stage2") != 0) {
                    if (s1.optInt("heat_stage2_onoff") == 0) {
                        heating_stage2 = "Off";
                    } else if (s1.optInt("heat_stage2_onoff") == 1) {
                        heating_stage2 = "On";
                    } else if (s1.optInt("heat_stage2_onoff") == -1) {
                        heating_stage2 = "not availible";
                    }
                }
                if (s1.optInt("hvac_fan_stage1") != 0) {
                    if (s1.optInt("fan_stage1_onoff") == 0) {
                        fan_stage1 = "Off";
                    } else if (s1.optInt("fan_stage1_onoff") == 1) {
                        fan_stage1 = "On";
                    } else if (s1.optInt("fan_stage1_onoff") == -1) {
                        fan_stage1 = "not availible";
                    }
                }
                if (s1.optInt("hvac_fan_stage2") != 0) {
                    if (s1.optInt("fan_stage2_onoff") == 0) {
                        fan_stage2 = "Off";
                    } else if (s1.optInt("fan_stage2_onoff") == 1) {
                        fan_stage2 = "On";
                    } else if (s1.optInt("fan_stage2_onoff") == -1) {
                        fan_stage2 = "not availible";
                    }
                }

                if (s1.optInt("hvac_humidifier") == 0) {
                    humidifier = "Off";
                } else if (s1.optInt("hvac_humidifier") == 1) {
                    humidifier = "On";
                } else if (s1.optInt("hvac_humidifier") == -1) {
                    humidifier = "not availible";
                }

                view_holder.tvCCUName.setText(ccuname);
                view_holder.timezone.setText(s1.optString("date_time"));
                view_holder.tvMode.setText(mode1 + "/" + mode2);
                view_holder.tvEnthaply.setText(String.format("%.2f", s1.optDouble("inside_enthalpy")) + "/" + String.format("%.2f", s1.optDouble("env_enthalpy")));
                view_holder.tvOutsideTemp.setText(String.format("%.2f", s1.optDouble("env_temp")) + "");
                view_holder.tvOutsideDamper.setText(s1.optInt("dcv_damper_pos") + "");
                if (s1.optInt("dcv_sensor_paired") == 1) {
                    if (s1.optInt("co_sensor_paired") == 1 && s1.optInt("no2_sensor_paired") == 1) {
                        view_holder.tvCO2Level.setText(s1.optInt("dcv_co2_val") + "(" + s1.optInt("dcv_co2_limit") + ")");
                        view_holder.tvCOLevel.setText(s1.optInt("co_value") + "(" + s1.optInt("co_limit") + ")");
                        view_holder.tvNO2Level.setText(s1.optInt("no2_value") + "(" + s1.optInt("no2_limit") + ")");
                    } else if (s1.optInt("co_sensor_paired") == 0 && s1.optInt("no2_sensor_paired") == 0) {
                        view_holder.tvCO2Level.setText(s1.optInt("dcv_co2_val") + "(" + s1.optInt("dcv_co2_limit") + ")");
                        view_holder.tvCOLevel.setText("NA");
                        view_holder.tvNO2Level.setText("NA");
                    } else if (s1.optInt("co_sensor_paired") == 0 && s1.optInt("no2_sensor_paired") == 1) {
                        view_holder.tvCO2Level.setText(s1.optInt("dcv_co2_val") + "(" + s1.optInt("dcv_co2_limit") + ")");
                        view_holder.tvCOLevel.setText("NA");
                        view_holder.tvNO2Level.setText(s1.optInt("no2_value") + "(" + s1.optInt("no2_limit") + ")");
                    } else if (s1.optInt("co_sensor_paired") == 1 && s1.optInt("no2_sensor_paired") == 0) {
                        view_holder.tvCO2Level.setText(s1.optInt("dcv_co2_val") + "(" + s1.optInt("dcv_co2_limit") + ")");
                        view_holder.tvCOLevel.setText(s1.optInt("co_value") + "(" + s1.optInt("co_limit") + ")");
                        view_holder.tvNO2Level.setText("NA");
                    }
                } else {
                    view_holder.tvCO2Level.setText("NA");
                    view_holder.tvCOLevel.setText("NA");
                    view_holder.tvNO2Level.setText("NA");
                }
                if (s1.optInt("dp_sensor_paired") == 0) {
                    view_holder.tvPressureSensor.setText("NA");
                } else {
                    view_holder.tvPressureSensor.setText(String.format("%.2f", s1.optDouble("dp_pressure")) + "(" + String.format("%.2f", s1.optDouble("dp_limit")) + ")");
                }
                view_holder.tvEcon.setText(econ1 + "/" + econ2);
                view_holder.tvMat.setText(String.format("%.2f", s1.optDouble("econ_mixed_airflow_temp")) + "/" + String.format("%.2f", s1.optDouble("econ_return_airflow_temp")));
                view_holder.tvComfortx.setText(String.format("%.2f", s1.optDouble("comfort_index")) + "");
                if ((s1.optInt("hvac_dx_stage1") == 1) && (s1.optInt("hvac_dx_stage2") == 1)) {
                    view_holder.tvHVACDetail1.setText("Cooling Stage 1(" + cooling_stage1 + ") and 2(" + cooling_stage2 + ")");
                } else if ((s1.optInt("hvac_dx_stage1") == 0) && (s1.optInt("hvac_dx_stage2") == 1)) {
                    view_holder.tvHVACDetail1.setText("Cooling Stage 2(" + cooling_stage2 + ")");
                } else if ((s1.optInt("hvac_dx_stage2") == 0) && (s1.optInt("hvac_dx_stage1") == 1)) {
                    view_holder.tvHVACDetail1.setText("Cooling Stage 1(" + cooling_stage1 + ")");
                } else if (s1.optInt("hvac_dx_stage1") == 0 && s1.optInt("hvac_dx_stage2") == 0) {
                    view_holder.tvHVACDetail1.setVisibility(View.GONE);
                }

                if ((s1.optInt("hvac_heat_stage1") == 1) && (s1.optInt("hvac_heat_stage2") == 1)) {
                    view_holder.tvHVACDetail2.setText("Heating Stage 1(" + heating_stage1 + ") and 2(" + heating_stage2 + ")");
                } else if ((s1.optInt("hvac_heat_stage1") == 0) && (s1.optInt("hvac_heat_stage2") == 1)) {
                    view_holder.tvHVACDetail2.setText("Heating Stage 2(" + heating_stage2 + ")");
                } else if ((s1.optInt("hvac_heat_stage2") == 0) && (s1.optInt("hvac_heat_stage1") == 1)) {
                    view_holder.tvHVACDetail2.setText("Heating Stage 1(" + heating_stage1 + ")");
                } else if (s1.optInt("hvac_heat_stage1") == 0 && s1.optInt("hvac_heat_stage2") == 0) {
                    view_holder.tvHVACDetail2.setVisibility(View.GONE);
                }

                if ((s1.optInt("hvac_fan_stage1") == 1) && (s1.optInt("hvac_fan_stage2") == 1)) {
                    view_holder.tvHVACDetail3.setText("Fan Stage 1(" + fan_stage1 + ") and 2(" + fan_stage2 + ")");
                } else if ((s1.optInt("hvac_fan_stage1") == 0) && (s1.optInt("hvac_fan_stage2") == 1)) {
                    view_holder.tvHVACDetail3.setText("Fan Stage 2(" + fan_stage2 + ")");
                } else if ((s1.optInt("hvac_fan_stage2") == 0) && (s1.optInt("hvac_fan_stage1") == 1)) {
                    view_holder.tvHVACDetail3.setText("Fan Stage 1(" + fan_stage1 + ")");
                } else if (s1.optInt("hvac_fan_stage1") == 0 && s1.optInt("hvac_fan_stage2") == 0) {
                    view_holder.tvHVACDetail3.setVisibility(View.GONE);
                }

                if (s1.optInt("hvac_humidifier") == -1) {
                    view_holder.tvHVACDetail4.setVisibility(View.GONE);
                } else {
                    view_holder.tvHVACDetail4.setText("Humidifier(" + humidifier + ")");
                }

                view_holder.tvHVACDetail5.setText("Economizer(" + econ1 + "/" + econ2 + ")");
                view_holder.tvHVACDetail6.setText("Analog(" + s1.optInt("analog1_damper_pos") + "/" + s1.optInt("analog2_damper_pos") + "/" + s1.optInt("analog3_damper_pos") + "/" + s1.optInt("analog4_damper_pos") + ")");
            } catch (JSONException E) {
                E.printStackTrace();
            }


            zone_list = mainZoneList.get(mainZoneList.size() - position - 1);
            if (zone_list.size() >= 10) {
                AddRow1(zone_list.get(0));
                AddRow2(zone_list.get(1));
                AddRow3(zone_list.get(2));
                AddRow4(zone_list.get(3));
                AddRow5(zone_list.get(4));
                AddRow6(zone_list.get(5));
                AddRow7(zone_list.get(6));
                AddRow8(zone_list.get(7));
                AddRow9(zone_list.get(8));
                AddRow10(zone_list.get(9));
            } else if (zone_list.size() == 9) {
                view_holder.row10.setVisibility(View.GONE);
                AddRow1(zone_list.get(0));
                AddRow2(zone_list.get(1));
                AddRow3(zone_list.get(2));
                AddRow4(zone_list.get(3));
                AddRow5(zone_list.get(4));
                AddRow6(zone_list.get(5));
                AddRow7(zone_list.get(6));
                AddRow8(zone_list.get(7));
                AddRow9(zone_list.get(8));
            } else if (zone_list.size() == 8) {
                view_holder.row10.setVisibility(View.GONE);
                view_holder.row9.setVisibility(View.GONE);
                AddRow1(zone_list.get(0));
                AddRow2(zone_list.get(1));
                AddRow3(zone_list.get(2));
                AddRow4(zone_list.get(3));
                AddRow5(zone_list.get(4));
                AddRow6(zone_list.get(5));
                AddRow7(zone_list.get(6));
                AddRow8(zone_list.get(7));
            } else if (zone_list.size() == 7) {
                view_holder.row10.setVisibility(View.GONE);
                view_holder.row9.setVisibility(View.GONE);
                view_holder.row8.setVisibility(View.GONE);
                AddRow1(zone_list.get(0));
                AddRow2(zone_list.get(1));
                AddRow3(zone_list.get(2));
                AddRow4(zone_list.get(3));
                AddRow5(zone_list.get(4));
                AddRow6(zone_list.get(5));
                AddRow7(zone_list.get(6));
            } else if (zone_list.size() == 6) {
                view_holder.row10.setVisibility(View.GONE);
                view_holder.row9.setVisibility(View.GONE);
                view_holder.row8.setVisibility(View.GONE);
                view_holder.row7.setVisibility(View.GONE);
                AddRow1(zone_list.get(0));
                AddRow2(zone_list.get(1));
                AddRow3(zone_list.get(2));
                AddRow4(zone_list.get(3));
                AddRow5(zone_list.get(4));
                AddRow6(zone_list.get(5));
            } else if (zone_list.size() == 5) {
                view_holder.row10.setVisibility(View.GONE);
                view_holder.row9.setVisibility(View.GONE);
                view_holder.row8.setVisibility(View.GONE);
                view_holder.row7.setVisibility(View.GONE);
                view_holder.row6.setVisibility(View.GONE);
                AddRow1(zone_list.get(0));
                AddRow2(zone_list.get(1));
                AddRow3(zone_list.get(2));
                AddRow4(zone_list.get(3));
                AddRow5(zone_list.get(4));
            } else if (zone_list.size() == 4) {
                view_holder.row10.setVisibility(View.GONE);
                view_holder.row9.setVisibility(View.GONE);
                view_holder.row8.setVisibility(View.GONE);
                view_holder.row7.setVisibility(View.GONE);
                view_holder.row6.setVisibility(View.GONE);
                view_holder.row5.setVisibility(View.GONE);
                AddRow1(zone_list.get(0));
                AddRow2(zone_list.get(1));
                AddRow3(zone_list.get(2));
                AddRow4(zone_list.get(3));
            } else if (zone_list.size() == 3) {
                view_holder.row10.setVisibility(View.GONE);
                view_holder.row9.setVisibility(View.GONE);
                view_holder.row8.setVisibility(View.GONE);
                view_holder.row7.setVisibility(View.GONE);
                view_holder.row6.setVisibility(View.GONE);
                view_holder.row5.setVisibility(View.GONE);
                view_holder.row4.setVisibility(View.GONE);
                AddRow1(zone_list.get(0));
                AddRow2(zone_list.get(1));
                AddRow3(zone_list.get(2));
            } else if (zone_list.size() == 2) {
                view_holder.row10.setVisibility(View.GONE);
                view_holder.row9.setVisibility(View.GONE);
                view_holder.row8.setVisibility(View.GONE);
                view_holder.row7.setVisibility(View.GONE);
                view_holder.row6.setVisibility(View.GONE);
                view_holder.row5.setVisibility(View.GONE);
                view_holder.row4.setVisibility(View.GONE);
                view_holder.row3.setVisibility(View.GONE);
                AddRow1(zone_list.get(0));
                AddRow2(zone_list.get(1));
            } else if (zone_list.size() == 1) {
                view_holder.row10.setVisibility(View.GONE);
                view_holder.row9.setVisibility(View.GONE);
                view_holder.row8.setVisibility(View.GONE);
                view_holder.row7.setVisibility(View.GONE);
                view_holder.row6.setVisibility(View.GONE);
                view_holder.row5.setVisibility(View.GONE);
                view_holder.row4.setVisibility(View.GONE);
                view_holder.row3.setVisibility(View.GONE);
                view_holder.row2.setVisibility(View.GONE);
                AddRow1(zone_list.get(0));
            } else if (zone_list.size() == 0) {
                view_holder.row10.setVisibility(View.GONE);
                view_holder.row9.setVisibility(View.GONE);
                view_holder.row8.setVisibility(View.GONE);
                view_holder.row7.setVisibility(View.GONE);
                view_holder.row6.setVisibility(View.GONE);
                view_holder.row5.setVisibility(View.GONE);
                view_holder.row4.setVisibility(View.GONE);
                view_holder.row3.setVisibility(View.GONE);
                view_holder.row2.setVisibility(View.GONE);
                view_holder.row1.setVisibility(View.GONE);
            }

        }
        return row;
    }

    public class ViewHolder {
        private TableRow row1;
        private TableRow row2;
        private TableRow row3;
        private TableRow row4;
        private TableRow row5;
        private TableRow row6;
        private TableRow row7;
        private TableRow row8;
        private TableRow row9;
        private TableRow row10;
        private TextView zone_name;
        private TextView zone_name1;
        private TextView zone_name2;
        private TextView zone_name3;
        private TextView zone_name4;
        private TextView zone_name5;
        private TextView zone_name6;
        private TextView zone_name7;
        private TextView zone_name8;
        private TextView zone_name9;
        private TextView tvComfort;
        private TextView tvComfort111;
        private TextView tvComfort2;
        private TextView tvComfort3;
        private TextView tvComfort4;
        private TextView tvComfort5;
        private TextView tvComfort6;
        private TextView tvComfort7;
        private TextView tvComfort8;
        private TextView tvComfort9;
        private TextView temp;
        private TextView temp1;
        private TextView temp2;
        private TextView temp3;
        private TextView temp4;
        private TextView temp5;
        private TextView temp6;
        private TextView temp7;
        private TextView temp8;
        private TextView temp9;
        private TextView damper;
        private TextView damper1;
        private TextView damper2;
        private TextView damper3;
        private TextView damper4;
        private TextView damper5;
        private TextView damper6;
        private TextView damper7;
        private TextView damper8;
        private TextView damper9;
        private TextView tvCCUName;
        private TextView timezone;
        private TextView tvMode;
        private TextView tvEnthaply;
        private TextView tvOutsideTemp;
        private TextView tvOutsideDamper;
        private TextView tvCO2Level;
        private TextView tvEcon;
        private TextView tvMat;
        private TextView tvComfortx;
        private TextView tvHVAC;
        private TextView tvHVACDetail1;
        private TextView tvHVACDetail2;
        private TextView tvHVACDetail3;
        private TextView tvHVACDetail4;
        private TextView tvHVACDetail5;
        private TextView tvCOLevel;
        private TextView tvNO2Level;
        private TextView tvPressureSensor;
        private TextView tvHVACDetail6;


        public ViewHolder(View v) {
            tvCCUName = (TextView) v.findViewById(R.id.tvCCUName);
            timezone = (TextView) v.findViewById(R.id.timezone);
            tvMode = (TextView) v.findViewById(R.id.tvMode);
            tvEnthaply = (TextView) v.findViewById(R.id.tvEnthaply);
            tvOutsideTemp = (TextView) v.findViewById(R.id.tvOutsideTemp);
            tvOutsideDamper = (TextView) v.findViewById(R.id.tvOutsideDamper);
            tvCO2Level = (TextView) v.findViewById(R.id.tvCO2Level);
            tvEcon = (TextView) v.findViewById(R.id.tvEcon);
            tvMat = (TextView) v.findViewById(R.id.tvMat);
            tvComfortx = (TextView) v.findViewById(R.id.tvComfortx);
            tvHVAC = (TextView) v.findViewById(R.id.tvHVAC);
            tvHVACDetail1 = (TextView) v.findViewById(R.id.tvHVACDetail1);
            tvHVACDetail2 = (TextView) v.findViewById(R.id.tvHVACDetail2);
            tvHVACDetail3 = (TextView) v.findViewById(R.id.tvHVACDetail3);
            tvHVACDetail4 = (TextView) v.findViewById(R.id.tvHVACDetail4);
            tvHVACDetail5 = (TextView) v.findViewById(R.id.tvHVACDetail5);
            tvCOLevel = (TextView) v.findViewById(R.id.tvCOLevel);
            tvNO2Level = (TextView) v.findViewById(R.id.tvNO2Level);
            tvPressureSensor = (TextView) v.findViewById(R.id.tvPressureSensor);
            tvHVACDetail6 = (TextView) v.findViewById(R.id.tvHVACDetail6);
            row1 = (TableRow) v.findViewById(R.id.row1);
            row2 = (TableRow) v.findViewById(R.id.row2);
            row3 = (TableRow) v.findViewById(R.id.row3);
            row4 = (TableRow) v.findViewById(R.id.row4);
            row5 = (TableRow) v.findViewById(R.id.row5);
            row6 = (TableRow) v.findViewById(R.id.row6);
            row7 = (TableRow) v.findViewById(R.id.row7);
            row8 = (TableRow) v.findViewById(R.id.row8);
            row9 = (TableRow) v.findViewById(R.id.row9);
            row10 = (TableRow) v.findViewById(R.id.row10);
            zone_name = (TextView) v.findViewById(R.id.zone_name);
            zone_name1 = (TextView) v.findViewById(R.id.zone_name1);
            zone_name2 = (TextView) v.findViewById(R.id.zone_name2);
            zone_name3 = (TextView) v.findViewById(R.id.zone_name3);
            zone_name4 = (TextView) v.findViewById(R.id.zone_name4);
            zone_name5 = (TextView) v.findViewById(R.id.zone_name5);
            zone_name6 = (TextView) v.findViewById(R.id.zone_name6);
            zone_name7 = (TextView) v.findViewById(R.id.zone_name7);
            zone_name8 = (TextView) v.findViewById(R.id.zone_name8);
            zone_name9 = (TextView) v.findViewById(R.id.zone_name9);
            tvComfort = (TextView) v.findViewById(R.id.tvComfort);
            tvComfort111 = (TextView) v.findViewById(R.id.tvComfort111);
            tvComfort2 = (TextView) v.findViewById(R.id.tvComfort2);
            tvComfort3 = (TextView) v.findViewById(R.id.tvComfort3);
            tvComfort4 = (TextView) v.findViewById(R.id.tvComfort4);
            tvComfort5 = (TextView) v.findViewById(R.id.tvComfort5);
            tvComfort6 = (TextView) v.findViewById(R.id.tvComfort6);
            tvComfort7 = (TextView) v.findViewById(R.id.tvComfort7);
            tvComfort8 = (TextView) v.findViewById(R.id.tvComfort8);
            tvComfort9 = (TextView) v.findViewById(R.id.tvComfort9);
            temp = (TextView) v.findViewById(R.id.temp);
            temp1 = (TextView) v.findViewById(R.id.temp1);
            temp2 = (TextView) v.findViewById(R.id.temp2);
            temp3 = (TextView) v.findViewById(R.id.temp3);
            temp4 = (TextView) v.findViewById(R.id.temp4);
            temp5 = (TextView) v.findViewById(R.id.temp5);
            temp6 = (TextView) v.findViewById(R.id.temp6);
            temp7 = (TextView) v.findViewById(R.id.temp7);
            temp8 = (TextView) v.findViewById(R.id.temp8);
            temp9 = (TextView) v.findViewById(R.id.temp9);
            damper = (TextView) v.findViewById(R.id.damper);
            damper1 = (TextView) v.findViewById(R.id.damper1);
            damper2 = (TextView) v.findViewById(R.id.damper2);
            damper3 = (TextView) v.findViewById(R.id.damper3);
            damper4 = (TextView) v.findViewById(R.id.damper4);
            damper5 = (TextView) v.findViewById(R.id.damper5);
            damper6 = (TextView) v.findViewById(R.id.damper6);
            damper7 = (TextView) v.findViewById(R.id.damper7);
            damper8 = (TextView) v.findViewById(R.id.damper8);
            damper9 = (TextView) v.findViewById(R.id.damper9);
        }


    }

    public void AddRow1(Zone_log zone_log) {
        view_holder.zone_name.setText(zone_log.getZone());
        String x = "";
        if (zone_log.getSchedule_type() == 0) {
            x = "SYS";
        } else if (zone_log.getSchedule_type() == 1) {
            x = "ZONE";
        } else if (zone_log.getSchedule_type() == 2) {
            x = "EXT";
        }
        view_holder.tvComfort.setText(x);
        String set_temp = String.format("%.1f", zone_log.getSet_temp());
        String text = "<font color=#D77B64>" + set_temp + "</font>" + "/" + String.format("%.1f", zone_log.getCur_temp());
        view_holder.temp.setText(Html.fromHtml(text));
//        view_holder.temp.setText(String.format("%.1f", zone_log.getSet_temp()) + "/" + String.format("%.1f", zone_log.getCur_temp()));
        view_holder.damper.setText(zone_log.getDamper_pos() + "[" + zone_log.getMin_damper_pos() + "-" + zone_log.getMax_damper_pos() + "]");
    }

    public void AddRow2(Zone_log zone_log) {
        view_holder.zone_name1.setText(zone_log.getZone());
        String x = "";
        if (zone_log.getSchedule_type() == 0) {
            x = "SYS";
        } else if (zone_log.getSchedule_type() == 1) {
            x = "ZONE";
        } else if (zone_log.getSchedule_type() == 2) {
            x = "EXT";
        }
        view_holder.tvComfort111.setText(x);
        String set_temp = String.format("%.1f", zone_log.getSet_temp());
        String text = "<font color=#D77B64>" + set_temp + "</font>" + "/" + String.format("%.1f", zone_log.getCur_temp());
        view_holder.temp1.setText(Html.fromHtml(text));
        view_holder.damper1.setText(zone_log.getDamper_pos() + "[" + zone_log.getMin_damper_pos() + "-" + zone_log.getMax_damper_pos() + "]");
    }

    public void AddRow3(Zone_log zone_log) {
        view_holder.zone_name2.setText(zone_log.getZone());
        String x = "";
        if (zone_log.getSchedule_type() == 0) {
            x = "SYS";
        } else if (zone_log.getSchedule_type() == 1) {
            x = "ZONE";
        } else if (zone_log.getSchedule_type() == 2) {
            x = "EXT";
        }
        view_holder.tvComfort2.setText(x);
        String set_temp = String.format("%.1f", zone_log.getSet_temp());
        String text = "<font color=#D77B64>" + set_temp + "</font>" + "/" + String.format("%.1f", zone_log.getCur_temp());
        view_holder.temp2.setText(Html.fromHtml(text));
        view_holder.damper2.setText(zone_log.getDamper_pos() + "[" + zone_log.getMin_damper_pos() + "-" + zone_log.getMax_damper_pos() + "]");
    }

    public void AddRow4(Zone_log zone_log) {
        view_holder.zone_name3.setText(zone_log.getZone());
        String x = "";
        if (zone_log.getSchedule_type() == 0) {
            x = "SYS";
        } else if (zone_log.getSchedule_type() == 1) {
            x = "ZONE";
        } else if (zone_log.getSchedule_type() == 2) {
            x = "EXT";
        }
        view_holder.tvComfort3.setText(x);
        String set_temp = String.format("%.1f", zone_log.getSet_temp());
        String text = "<font color=#D77B64>" + set_temp + "</font>" + "/" + String.format("%.1f", zone_log.getCur_temp());
        view_holder.temp3.setText(Html.fromHtml(text));
        view_holder.damper3.setText(zone_log.getDamper_pos() + "[" + zone_log.getMin_damper_pos() + "-" + zone_log.getMax_damper_pos() + "]");
    }

    public void AddRow5(Zone_log zone_log) {
        view_holder.zone_name4.setText(zone_log.getZone());
        String x = "";
        if (zone_log.getSchedule_type() == 0) {
            x = "SYS";
        } else if (zone_log.getSchedule_type() == 1) {
            x = "ZONE";
        } else if (zone_log.getSchedule_type() == 2) {
            x = "EXT";
        }
        view_holder.tvComfort4.setText(x);
        String set_temp = String.format("%.1f", zone_log.getSet_temp());
        String text = "<font color=#D77B64>" + set_temp + "</font>" + "/" + String.format("%.1f", zone_log.getCur_temp());
        view_holder.temp4.setText(Html.fromHtml(text));
        view_holder.damper4.setText(zone_log.getDamper_pos() + "[" + zone_log.getMin_damper_pos() + "-" + zone_log.getMax_damper_pos() + "]");
    }

    public void AddRow6(Zone_log zone_log) {
        view_holder.zone_name5.setText(zone_log.getZone());
        String x = "";
        if (zone_log.getSchedule_type() == 0) {
            x = "SYS";
        } else if (zone_log.getSchedule_type() == 1) {
            x = "ZONE";
        } else if (zone_log.getSchedule_type() == 2) {
            x = "EXT";
        }
        view_holder.tvComfort5.setText(x);
        String set_temp = String.format("%.1f", zone_log.getSet_temp());
        String text = "<font color=#D77B64>" + set_temp + "</font>" + "/" + String.format("%.1f", zone_log.getCur_temp());
        view_holder.temp5.setText(Html.fromHtml(text));
        view_holder.damper5.setText(zone_log.getDamper_pos() + "[" + zone_log.getMin_damper_pos() + "-" + zone_log.getMax_damper_pos() + "]");
    }

    public void AddRow7(Zone_log zone_log) {
        view_holder.zone_name6.setText(zone_log.getZone());
        String x = "";
        if (zone_log.getSchedule_type() == 0) {
            x = "SYS";
        } else if (zone_log.getSchedule_type() == 1) {
            x = "ZONE";
        } else if (zone_log.getSchedule_type() == 2) {
            x = "EXT";
        }
        view_holder.tvComfort6.setText(x);
        String set_temp = String.format("%.1f", zone_log.getSet_temp());
        String text = "<font color=#D77B64>" + set_temp + "</font>" + "/" + String.format("%.1f", zone_log.getCur_temp());
        view_holder.temp6.setText(Html.fromHtml(text));
        view_holder.damper6.setText(zone_log.getDamper_pos() + "[" + zone_log.getMin_damper_pos() + "-" + zone_log.getMax_damper_pos() + "]");
    }

    public void AddRow8(Zone_log zone_log) {
        view_holder.zone_name7.setText(zone_log.getZone());
        String x = "";
        if (zone_log.getSchedule_type() == 0) {
            x = "SYS";
        } else if (zone_log.getSchedule_type() == 1) {
            x = "ZONE";
        } else if (zone_log.getSchedule_type() == 2) {
            x = "EXT";
        }
        view_holder.tvComfort7.setText(x);
        String set_temp = String.format("%.1f", zone_log.getSet_temp());
        String text = "<font color=#D77B64>" + set_temp + "</font>" + "/" + String.format("%.1f", zone_log.getCur_temp());
        view_holder.temp7.setText(Html.fromHtml(text));
        view_holder.damper7.setText(zone_log.getDamper_pos() + "[" + zone_log.getMin_damper_pos() + "-" + zone_log.getMax_damper_pos() + "]");
    }

    public void AddRow9(Zone_log zone_log) {
        view_holder.zone_name8.setText(zone_log.getZone());
        String x = "";
        if (zone_log.getSchedule_type() == 0) {
            x = "SYS";
        } else if (zone_log.getSchedule_type() == 1) {
            x = "ZONE";
        } else if (zone_log.getSchedule_type() == 2) {
            x = "EXT";
        }
        view_holder.tvComfort8.setText(x);
        String set_temp = String.format("%.1f", zone_log.getSet_temp());
        String text = "<font color=#D77B64>" + set_temp + "</font>" + "/" + String.format("%.1f", zone_log.getCur_temp());
        view_holder.temp8.setText(Html.fromHtml(text));
        view_holder.damper8.setText(zone_log.getDamper_pos() + "[" + zone_log.getMin_damper_pos() + "-" + zone_log.getMax_damper_pos() + "]");
    }

    public void AddRow10(Zone_log zone_log) {
        view_holder.zone_name9.setText(zone_log.getZone());
        String x = "";
        if (zone_log.getSchedule_type() == 0) {
            x = "SYS";
        } else if (zone_log.getSchedule_type() == 1) {
            x = "ZONE";
        } else if (zone_log.getSchedule_type() == 2) {
            x = "EXT";
        }
        view_holder.tvComfort9.setText(x);
        String set_temp = String.format("%.1f", zone_log.getSet_temp());
        String text = "<font color=#D77B64>" + set_temp + "</font>" + "/" + String.format("%.1f", zone_log.getCur_temp());
        view_holder.temp9.setText(Html.fromHtml(text));
        view_holder.damper9.setText(zone_log.getDamper_pos() + "[" + zone_log.getMin_damper_pos() + "-" + zone_log.getMax_damper_pos() + "]");
    }
}

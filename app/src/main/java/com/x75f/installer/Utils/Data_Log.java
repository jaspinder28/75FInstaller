package com.x75f.installer.Utils;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public class Data_Log extends GenericJson {
    @Key("date_time")
    private String date_time;
    @Key("current_mode")
    private int current_mode;
    @Key("env_enthalpy")
    private double env_enthalpy;



    @Key("inside_enthalpy")

    private double inside_enthalpy;
    @Key("operational_mode")
    private int operational_mode;
    @Key("conditioning_mode")
    private int conditioning_mode;
    @Key("env_temp")
    private double env_temp;
    @Key("dcv_damper_pos")
    private int dcv_damper_pos;
    @Key("hvac_dx_stage1")
    private int hvac_dx_stage1;
    @Key("hvac_dx_stage2")
    private int hvac_dx_stage2;
    @Key("dx_stage1_onoff")
    private int dx_stage1_onoff;
    @Key("dx_stage2_onoff")
    private int dx_stage2_onoff;
    @Key("hvac_heat_stage1")
    private int hvac_heat_stage1;
    @Key("hvac_heat_stage2")
    private int hvac_heat_stage2;
    @Key("fan_stage1_onoff")
    private int fan_stage1_onoff;
    @Key("fan_stage2_onoff")
    private int fan_stage2_onoff;
    @Key("hvac_fan_stage1")
    private int hvac_fan_stage1;
    @Key("hvac_fan_stage2")
    private int hvac_fan_stage2;
    @Key("hvac_humidifier")
    private int hvac_humidifier;
    @Key("hvac_analog1")
    private int hvac_analog1;
    @Key("hvac_analog1_type")
    private int hvac_analog1_type;
    @Key("hvac_analog2")
    private int hvac_analog2;
    @Key("hvac_analog2_type")
    private int hvac_analog2_type;
    @Key("hvac_analog3")
    private int hvac_analog3;
    @Key("hvac_analog3_type")
    private int hvac_analog3_type;
    @Key("hvac_analog4")
    private int hvac_analog4;
    @Key("hvac_analog4_type")
    private int hvac_analog4_type;


    @Key("comfort_index")

    private double comfort_index;
    @Key("econ_mixed_airflow_temp")
    private double econ_mixed_airflow_temp;
    @Key("econ_return_airflow_temp")
    private double econ_return_airflow_temp;
    @Key("econ_available")
    private int econ_available;
    @Key("econ_used")
    private int econ_used;
    @Key("dcv_co2_val")
    private int dcv_co2_val;
    @Key("dcv_co2_limit")
    private int dcv_co2_limit;

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public void setDcv_co2_val(int dcv_co2_val) {
        this.dcv_co2_val = dcv_co2_val;
    }

    public void setDcv_co2_limit(int dcv_co2_limit) {
        this.dcv_co2_limit = dcv_co2_limit;
    }
    public int getDcv_co2_val() {
        return dcv_co2_val;
    }

    public int getDcv_co2_limit() {
        return dcv_co2_limit;
    }

    public int getCurrent_mode() {
        return current_mode;
    }

    public void setCurrent_mode(int current_mode) {
        this.current_mode = current_mode;
    }

    public double getEnv_enthalpy() {
        return env_enthalpy;
    }

    public void setEnv_enthalpy(double env_enthalpy) {
        this.env_enthalpy = env_enthalpy;
    }

    public double getInside_enthalpy() {
        return inside_enthalpy;
    }

    public void setInside_enthalpy(double inside_enthalpy) {
        this.inside_enthalpy = inside_enthalpy;
    }

    public int getOperational_mode() {
        return operational_mode;
    }

    public void setOperational_mode(int operational_mode) {
        this.operational_mode = operational_mode;
    }

    public int getConditioning_mode() {
        return conditioning_mode;
    }

    public void setConditioning_mode(int conditioning_mode) {
        this.conditioning_mode = conditioning_mode;
    }

    public double getEnv_temp() {
        return env_temp;
    }

    public void setEnv_temp(double env_temp) {
        this.env_temp = env_temp;
    }

    public int getDcv_damper_pos() {
        return dcv_damper_pos;
    }

    public void setDcv_damper_pos(int dcv_damper_pos) {
        this.dcv_damper_pos = dcv_damper_pos;
    }

    public Data_Log() {
    }

    public int getHvac_dx_stage1() {

        return hvac_dx_stage1;
    }

    public void setHvac_dx_stage1(int hvac_dx_stage1) {
        this.hvac_dx_stage1 = hvac_dx_stage1;
    }

    public int getHvac_dx_stage2() {
        return hvac_dx_stage2;
    }

    public void setHvac_dx_stage2(int hvac_dx_stage2) {
        this.hvac_dx_stage2 = hvac_dx_stage2;
    }

    public int getDx_stage1_onoff() {
        return dx_stage1_onoff;
    }

    public void setDx_stage1_onoff(int dx_stage1_onoff) {
        this.dx_stage1_onoff = dx_stage1_onoff;
    }

    public int getDx_stage2_onoff() {
        return dx_stage2_onoff;
    }

    public void setDx_stage2_onoff(int dx_stage2_onoff) {
        this.dx_stage2_onoff = dx_stage2_onoff;
    }

    public int getHvac_heat_stage1() {
        return hvac_heat_stage1;
    }

    public void setHvac_heat_stage1(int hvac_heat_stage1) {
        this.hvac_heat_stage1 = hvac_heat_stage1;
    }

    public int getHvac_heat_stage2() {
        return hvac_heat_stage2;
    }

    public void setHvac_heat_stage2(int hvac_heat_stage2) {
        this.hvac_heat_stage2 = hvac_heat_stage2;
    }

    public int getFan_stage1_onoff() {
        return fan_stage1_onoff;
    }

    public void setFan_stage1_onoff(int fan_stage1_onoff) {
        this.fan_stage1_onoff = fan_stage1_onoff;
    }

    public int getFan_stage2_onoff() {
        return fan_stage2_onoff;
    }

    public void setFan_stage2_onoff(int fan_stage2_onoff) {
        this.fan_stage2_onoff = fan_stage2_onoff;
    }

    public int getHvac_fan_stage1() {
        return hvac_fan_stage1;
    }

    public void setHvac_fan_stage1(int hvac_fan_stage1) {
        this.hvac_fan_stage1 = hvac_fan_stage1;
    }

    public int getHvac_fan_stage2() {
        return hvac_fan_stage2;
    }

    public void setHvac_fan_stage2(int hvac_fan_stage2) {
        this.hvac_fan_stage2 = hvac_fan_stage2;
    }

    public int getHvac_humidifier() {
        return hvac_humidifier;
    }

    public void setHvac_humidifier(int hvac_humidifier) {
        this.hvac_humidifier = hvac_humidifier;
    }

    public int getHvac_analog1() {
        return hvac_analog1;
    }

    public void setHvac_analog1(int hvac_analog1) {
        this.hvac_analog1 = hvac_analog1;
    }

    public int getHvac_analog1_type() {
        return hvac_analog1_type;
    }

    public void setHvac_analog1_type(int hvac_analog1_type) {
        this.hvac_analog1_type = hvac_analog1_type;
    }

    public int getHvac_analog2() {
        return hvac_analog2;
    }

    public void setHvac_analog2(int hvac_analog2) {
        this.hvac_analog2 = hvac_analog2;
    }

    public int getHvac_analog2_type() {
        return hvac_analog2_type;
    }

    public void setHvac_analog2_type(int hvac_analog2_type) {
        this.hvac_analog2_type = hvac_analog2_type;
    }

    public int getHvac_analog3() {
        return hvac_analog3;
    }

    public void setHvac_analog3(int hvac_analog3) {
        this.hvac_analog3 = hvac_analog3;
    }

    public int getHvac_analog3_type() {
        return hvac_analog3_type;
    }

    public void setHvac_analog3_type(int hvac_analog3_type) {
        this.hvac_analog3_type = hvac_analog3_type;
    }

    public int getHvac_analog4() {
        return hvac_analog4;
    }

    public void setHvac_analog4(int hvac_analog4) {
        this.hvac_analog4 = hvac_analog4;
    }

    public int getHvac_analog4_type() {
        return hvac_analog4_type;
    }

    public void setHvac_analog4_type(int hvac_analog4_type) {
        this.hvac_analog4_type = hvac_analog4_type;
    }

    public double getComfort_index() {
        return comfort_index;
    }

    public void setComfort_index(double comfort_index) {
        this.comfort_index = comfort_index;
    }

    public double getEcon_mixed_airflow_temp() {
        return econ_mixed_airflow_temp;
    }

    public void setEcon_mixed_airflow_temp(double econ_mixed_airflow_temp) {
        this.econ_mixed_airflow_temp = econ_mixed_airflow_temp;
    }

    public double getEcon_return_airflow_temp() {
        return econ_return_airflow_temp;
    }

    public void setEcon_return_airflow_temp(double econ_return_airflow_temp) {
        this.econ_return_airflow_temp = econ_return_airflow_temp;
    }

    public int getEcon_available() {
        return econ_available;
    }

    public void setEcon_available(int econ_available) {
        this.econ_available = econ_available;
    }

    public int getEcon_used() {
        return econ_used;
    }

    public void setEcon_used(int econ_used) {
        this.econ_used = econ_used;
    }
}

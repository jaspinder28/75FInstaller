package com.x75f.installer.Utils;

import java.io.Serializable;

/**
 * Created by WIN8.1 on 6/7/2016.
 */
public class Summary_Data implements Serializable {
    private String ccu_name;
    private String date_time;
    private int building_no_cooler;
    private int building_no_hotter;
    private int user_no_cooler;
    private int user_no_hotter;
    private int cm_cur_temp;
    private int cm_cur_humidity;
    private int cooling_stage_1;
    private int cooling_stage_2;
    private int heating_stage_1;
    private int heating_stage_2;
    private int fan_stage_1;
    private int fan_stage_2;
    private int humidifier;
    private int mNO2Level;
    private int mNO2LevelThreshold;
    private int mCOLevel;
    private int mCOLevelThreshold;
    private boolean isEconomizerAvailable;

    public String getDate_time() {
        return date_time;
    }

    private int analog1_damperPos;
    private int analog2_damperPos;
    private int analog3_damperPos;
    private int analog1_type;
    private int analog2_type;
    private int analog3_type;
    private int analog4_type;
    private int analog4_damperPos;
    private double mInsideAirEnthalpy;
    private double mOutsideAirEnthalpy;
    private int mOutsideAirTemperature;
    private int mOutsideAirMaxTemp;


    public int getmOutsideAirTemperature() {
        return mOutsideAirTemperature;
    }

    private int mOutsideAirMinTemp;
    private int mOutsideAirHumidity;
    private int mOutsideAirMaxHumidity;
    private int mOutsideAirMinHumidity;


    private int mCO2Level;
    private boolean isPaired;
    private int mCO2LevelThreshold;
    private int mMixedAirTemperature;
    private int mReturnAirTemperature;
    private boolean isPressureSensorPaired;
    private boolean isCOPaired;
    private boolean isNO2Paired;
    private double mPressureLevel;
    private double mPressureLevelThreshold;
    private int mDamperPos;

    public boolean isCOPaired() {
        return isCOPaired;
    }

    public boolean isNO2Paired() {
        return isNO2Paired;
    }

    public boolean isPressureSensorPaired() {
        return isPressureSensorPaired;
    }

    public double getmPressureLevel() {
        return mPressureLevel;
    }

    public double getmPressureLevelThreshold() {
        return mPressureLevelThreshold;
    }

    public int getmNO2Level() {
        return mNO2Level;
    }

    public int getmNO2LevelThreshold() {
        return mNO2LevelThreshold;
    }

    public int getmCOLevel() {
        return mCOLevel;
    }

    public int getmCOLevelThreshold() {
        return mCOLevelThreshold;
    }

    public int getmDamperPos() {
        return mDamperPos;
    }

    public Summary_Data(String ccu_name, String date_time, int building_no_cooler, int building_no_hotter, int user_no_cooler, int user_no_hotter, int cm_cur_temp, int cm_cur_humidity,
                        int cooling_stage_1, int cooling_stage_2, int heating_stage_1, int heating_stage_2, int fan_stage_1, int fan_stage_2, int humidifier,
                        boolean isEconomizerAvailable, boolean isPaired, int analog1_damperPos, int analog2_damperPos, int analog3_damperPos, int analog4_damperPos,
                        double mInsideAirEnthalpy, double mOutsideAirEnthalpy, int mOutsideAirTemperature, int mOutsideAirMaxTemp, int mOutsideAirMinTemp, int mOutsideAirHumidity,
                        int mOutsideAirMaxHumidity, int mOutsideAirMinHumidity, int mCO2Level, int mCO2LevelThreshold, int mMixedAirTemperature, int mReturnAirTemperature,
                        int mDamperPos, String zone_summary, int mNO2Level, int mNO2LevelThreshold, int mCOLevel, int mCOLevelThreshold, boolean isPressureSensorPaired, double mPressureLevel,
                        double mPressureLevelThreshold,boolean isCOPaired,boolean isNO2Paired,int analog1_type,int analog2_type,int analog3_type,int analog4_type) {
        this.ccu_name = ccu_name;
        this.date_time = date_time;
        this.building_no_cooler = building_no_cooler;
        this.building_no_hotter = building_no_hotter;
        this.user_no_cooler = user_no_cooler;
        this.user_no_hotter = user_no_hotter;
        this.cm_cur_temp = cm_cur_temp;
        this.cm_cur_humidity = cm_cur_humidity;
        this.cooling_stage_1 = cooling_stage_1;
        this.cooling_stage_2 = cooling_stage_2;
        this.heating_stage_1 = heating_stage_1;
        this.heating_stage_2 = heating_stage_2;
        this.fan_stage_1 = fan_stage_1;
        this.fan_stage_2 = fan_stage_2;
        this.humidifier = humidifier;
        this.mNO2Level = mNO2Level;
        this.mNO2LevelThreshold = mNO2LevelThreshold;
        this.mCOLevel = mCOLevel;
        this.mCOLevelThreshold = mCOLevelThreshold;
        this.isEconomizerAvailable = isEconomizerAvailable;
        this.isPaired = isPaired;
        this.analog1_damperPos = analog1_damperPos;
        this.analog2_damperPos = analog2_damperPos;
        this.analog3_damperPos = analog3_damperPos;
        this.analog4_damperPos = analog4_damperPos;
        this.mInsideAirEnthalpy = mInsideAirEnthalpy;
        this.mOutsideAirEnthalpy = mOutsideAirEnthalpy;
        this.mOutsideAirTemperature = mOutsideAirTemperature;
        this.mOutsideAirMaxTemp = mOutsideAirMaxTemp;
        this.mOutsideAirMinTemp = mOutsideAirMinTemp;
        this.mOutsideAirHumidity = mOutsideAirHumidity;
        this.mOutsideAirMaxHumidity = mOutsideAirMaxHumidity;
        this.mOutsideAirMinHumidity = mOutsideAirMinHumidity;
        this.mCO2Level = mCO2Level;
        this.mCO2LevelThreshold = mCO2LevelThreshold;
        this.mMixedAirTemperature = mMixedAirTemperature;
        this.mReturnAirTemperature = mReturnAirTemperature;
        this.mDamperPos = mDamperPos;
        this.zone_summary = zone_summary;
        this.isPressureSensorPaired = isPressureSensorPaired;
        this.mPressureLevel = mPressureLevel;
        this.mPressureLevelThreshold = mPressureLevelThreshold;
        this.isCOPaired = isCOPaired;
        this.isNO2Paired = isNO2Paired;
        this.analog1_type = analog1_type;
        this.analog2_type = analog2_type;
        this.analog3_type = analog3_type;
        this.analog4_type = analog4_type;
    }

    public Summary_Data(String ccu_name, String date_time, int building_no_cooler, int building_no_hotter, int user_no_cooler, int user_no_hotter, int cm_cur_temp, int cm_cur_humidity,
                        int cooling_stage_1, int cooling_stage_2, int heating_stage_1, int heating_stage_2, int fan_stage_1, int fan_stage_2, int humidifier,
                        boolean isEconomizerAvailable, boolean isPaired, int analog1_damperPos, int analog2_damperPos, int analog3_damperPos, int analog4_damperPos,
                        double mInsideAirEnthalpy, double mOutsideAirEnthalpy, int mOutsideAirTemperature, int mOutsideAirMaxTemp, int mOutsideAirMinTemp, int mOutsideAirHumidity,
                        int mOutsideAirMaxHumidity, int mOutsideAirMinHumidity, int mMixedAirTemperature, int mReturnAirTemperature,
                        int mDamperPos, String zone_summary, boolean isPressureSensorPaired, double mPressureLevel, double mPressureLevelThreshold,int analog1_type,int analog2_type,int analog3_type,int analog4_type) {
        this.ccu_name = ccu_name;
        this.date_time = date_time;
        this.building_no_cooler = building_no_cooler;
        this.building_no_hotter = building_no_hotter;
        this.user_no_cooler = user_no_cooler;
        this.user_no_hotter = user_no_hotter;
        this.cm_cur_temp = cm_cur_temp;
        this.cm_cur_humidity = cm_cur_humidity;
        this.cooling_stage_1 = cooling_stage_1;
        this.cooling_stage_2 = cooling_stage_2;
        this.heating_stage_1 = heating_stage_1;
        this.heating_stage_2 = heating_stage_2;
        this.fan_stage_1 = fan_stage_1;
        this.fan_stage_2 = fan_stage_2;
        this.humidifier = humidifier;
        this.isEconomizerAvailable = isEconomizerAvailable;
        this.isPaired = isPaired;
        this.analog1_damperPos = analog1_damperPos;
        this.analog2_damperPos = analog2_damperPos;
        this.analog3_damperPos = analog3_damperPos;
        this.analog4_damperPos = analog4_damperPos;
        this.mInsideAirEnthalpy = mInsideAirEnthalpy;
        this.mOutsideAirEnthalpy = mOutsideAirEnthalpy;
        this.mOutsideAirTemperature = mOutsideAirTemperature;
        this.mOutsideAirMaxTemp = mOutsideAirMaxTemp;
        this.mOutsideAirMinTemp = mOutsideAirMinTemp;
        this.mOutsideAirHumidity = mOutsideAirHumidity;
        this.mOutsideAirMaxHumidity = mOutsideAirMaxHumidity;
        this.mOutsideAirMinHumidity = mOutsideAirMinHumidity;
        this.mCO2Level = mCO2Level;
        this.mCO2LevelThreshold = mCO2LevelThreshold;
        this.mMixedAirTemperature = mMixedAirTemperature;
        this.mReturnAirTemperature = mReturnAirTemperature;
        this.mDamperPos = mDamperPos;
        this.zone_summary = zone_summary;
        this.isPressureSensorPaired = isPressureSensorPaired;
        this.mPressureLevel = mPressureLevel;
        this.mPressureLevelThreshold = mPressureLevelThreshold;
        this.analog1_type = analog1_type;
        this.analog2_type = analog2_type;
        this.analog3_type = analog3_type;
        this.analog4_type = analog4_type;
    }

    public int getAnalog1_type() {
        return analog1_type;
    }

    public int getAnalog2_type() {
        return analog2_type;
    }

    public int getAnalog3_type() {
        return analog3_type;
    }

    public int getAnalog4_type() {
        return analog4_type;
    }

    public int getmMixedAirTemperature() {
        return mMixedAirTemperature;
    }

    public int getmReturnAirTemperature() {
        return mReturnAirTemperature;
    }

    public int getBuilding_no_cooler() {
        return building_no_cooler;
    }

    public int getBuilding_no_hotter() {
        return building_no_hotter;
    }

    private String zone_summary;

    public int getFan_stage_1() {
        return fan_stage_1;
    }

    public int getFan_stage_2() {
        return fan_stage_2;
    }


    public String getCcu_name() {
        return ccu_name;
    }

    public void setCcu_name(String ccu_name) {
        this.ccu_name = ccu_name;
    }

    public int getUser_no_cooler() {
        return user_no_cooler;
    }

    public void setUser_no_cooler(int user_no_cooler) {
        this.user_no_cooler = user_no_cooler;
    }

    public int getUser_no_hotter() {
        return user_no_hotter;
    }

    public void setUser_no_hotter(int user_no_hotter) {
        this.user_no_hotter = user_no_hotter;
    }

    public int getCm_cur_temp() {
        return cm_cur_temp;
    }

    public void setCm_cur_temp(int cm_cur_temp) {
        this.cm_cur_temp = cm_cur_temp;
    }

    public int getCm_cur_humidity() {
        return cm_cur_humidity;
    }

    public void setCm_cur_humidity(int cm_cur_humidity) {
        this.cm_cur_humidity = cm_cur_humidity;
    }

    public int getCooling_stage_1() {
        return cooling_stage_1;
    }

    public void setCooling_stage_1(int cooling_stage_1) {
        this.cooling_stage_1 = cooling_stage_1;
    }

    public int getCooling_stage_2() {
        return cooling_stage_2;
    }

    public void setCooling_stage_2(int cooling_stage_2) {
        this.cooling_stage_2 = cooling_stage_2;
    }

    public int getHeating_stage_1() {
        return heating_stage_1;
    }

    public void setHeating_stage_1(int heating_stage_1) {
        this.heating_stage_1 = heating_stage_1;
    }

    public int getHeating_stage_2() {
        return heating_stage_2;
    }

    public void setHeating_stage_2(int heating_stage_2) {
        this.heating_stage_2 = heating_stage_2;
    }

    public int getHumidifier() {
        return humidifier;
    }

    public void setHumidifier(int humidifier) {
        this.humidifier = humidifier;
    }

    public boolean isEconomizerAvailable() {
        return isEconomizerAvailable;
    }

    public void setIsEconomizerAvailable(boolean isEconomizerAvailable) {
        this.isEconomizerAvailable = isEconomizerAvailable;
    }

    public boolean isPaired() {
        return isPaired;
    }

    public void setIsPaired(boolean isPaired) {
        this.isPaired = isPaired;
    }

    public int getAnalog1_damperPos() {
        return analog1_damperPos;
    }

    public void setAnalog1_damperPos(int analog1_damperPos) {
        this.analog1_damperPos = analog1_damperPos;
    }

    public int getAnalog2_damperPos() {
        return analog2_damperPos;
    }

    public void setAnalog2_damperPos(int analog2_damperPos) {
        this.analog2_damperPos = analog2_damperPos;
    }

    public int getAnalog3_damperPos() {
        return analog3_damperPos;
    }

    public void setAnalog3_damperPos(int analog3_damperPos) {
        this.analog3_damperPos = analog3_damperPos;
    }

    public int getAnalog4_damperPos() {
        return analog4_damperPos;
    }

    public void setAnalog4_damperPos(int analog4_damperPos) {
        this.analog4_damperPos = analog4_damperPos;
    }

    public double getmInsideAirEnthalpy() {
        return mInsideAirEnthalpy;
    }

    public void setmInsideAirEnthalpy(double mInsideAirEnthalpy) {
        this.mInsideAirEnthalpy = mInsideAirEnthalpy;
    }

    public double getmOutsideAirEnthalpy() {
        return mOutsideAirEnthalpy;
    }

    public void setmOutsideAirEnthalpy(double mOutsideAirEnthalpy) {
        this.mOutsideAirEnthalpy = mOutsideAirEnthalpy;
    }

    public int getmOutsideAirMaxTemp() {
        return mOutsideAirMaxTemp;
    }

    public void setmOutsideAirMaxTemp(int mOutsideAirMaxTemp) {
        this.mOutsideAirMaxTemp = mOutsideAirMaxTemp;
    }

    public int getmOutsideAirMinTemp() {
        return mOutsideAirMinTemp;
    }

    public void setmOutsideAirMinTemp(int mOutsideAirMinTemp) {
        this.mOutsideAirMinTemp = mOutsideAirMinTemp;
    }

    public int getmOutsideAirHumidity() {
        return mOutsideAirHumidity;
    }

    public void setmOutsideAirHumidity(int mOutsideAirHumidity) {
        this.mOutsideAirHumidity = mOutsideAirHumidity;
    }

    public int getmOutsideAirMaxHumidity() {
        return mOutsideAirMaxHumidity;
    }

    public void setmOutsideAirMaxHumidity(int mOutsideAirMaxHumidity) {
        this.mOutsideAirMaxHumidity = mOutsideAirMaxHumidity;
    }

    public int getmOutsideAirMinHumidity() {
        return mOutsideAirMinHumidity;
    }

    public void setmOutsideAirMinHumidity(int mOutsideAirMinHumidity) {
        this.mOutsideAirMinHumidity = mOutsideAirMinHumidity;
    }

    public int getmCO2Level() {
        return mCO2Level;
    }

    public void setmCO2Level(int mCO2Level) {
        this.mCO2Level = mCO2Level;
    }

    public int getmCO2LevelThreshold() {
        return mCO2LevelThreshold;
    }

    public void setmCO2LevelThreshold(int mCO2LevelThreshold) {
        this.mCO2LevelThreshold = mCO2LevelThreshold;
    }

    public String getZone_summary() {
        return zone_summary;
    }

    public void setZone_summary(String zone_summary) {
        this.zone_summary = zone_summary;
    }


}

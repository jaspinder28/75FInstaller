package com.x75f.installer.Utils;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

/**
 * Created by JASPINDER on 6/16/2016.
 */
public class Zone_log extends GenericJson {

    @Key("_zone")
    private String zone;
    @Key("schedule_type")
    private int schedule_type;
    @Key("cur_temp")
    private double cur_temp;
    @Key("set_temp")
    private double set_temp;
    @Key("damper_pos")
    private int damper_pos;
    @Key("max_damper_pos")
    private int max_damper_pos;
    @Key("min_damper_pos")
    private int min_damper_pos;

    public Zone_log() {
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public int getSchedule_type() {
        return schedule_type;
    }

    public void setSchedule_type(int schedule_type) {
        this.schedule_type = schedule_type;
    }

    public double getCur_temp() {
        return cur_temp;
    }

    public void setCur_temp(double cur_temp) {
        this.cur_temp = cur_temp;
    }

    public double getSet_temp() {
        return set_temp;
    }

    public void setSet_temp(double set_temp) {
        this.set_temp = set_temp;
    }

    public int getDamper_pos() {
        return damper_pos;
    }

    public void setDamper_pos(int damper_pos) {
        this.damper_pos = damper_pos;
    }

    public int getMax_damper_pos() {
        return max_damper_pos;
    }

    public void setMax_damper_pos(int max_damper_pos) {
        this.max_damper_pos = max_damper_pos;
    }

    public int getMin_damper_pos() {
        return min_damper_pos;
    }

    public void setMin_damper_pos(int min_damper_pos) {
        this.min_damper_pos = min_damper_pos;
    }
}

package com.x75f.installer.Utils;

import java.io.Serializable;

/**
 * Created by WIN8.1 on 6/8/2016.
 */
public class zone_data implements Serializable {

    private String name;
    private boolean fsv_paired;
    private int fsv_address;
    private int damper_size;
    private String damper_type;
    private String occu_sensor;
    private int set_temp;
    private int cur_temp;
    private int damper_pos;
    private String occupied;
    private int airflow_temp;
    private String monday_occutime;
    private String monday_unoccutime;
    private int monday_occutemp;
    private String tuesday_occutime;
    private String tuesday_unoccutime;
    private int tuesday_occutemp;
    private String wednesday_occutime;
    private String wednesday_unoccutime;
    private int wednesday_occutemp;
    private String thursday_occutime;
    private String thursday_unoccutime;
    private int thursday_occutemp;
    private String friday_occutime;
    private String friday_unoccutime;
    private int friday_occutemp;
    private String saturday_occutime;
    private String saturday_unoccutime;
    private int saturday_occutemp;
    private String sunday_occutime;
    private String sunday_unoccutime;
    private int sunday_occutemp;
    private String schedule_type;

    public String getSchedule_type() {
        return schedule_type;
    }

    public zone_data(String name, boolean fsv_paired, int fsv_address, int damper_size, String damper_type, String occu_sensor, int set_temp, int cur_temp, int damper_pos, String occupied, int airflow_temp, String monday_occutime, String monday_unoccutime, int monday_occutemp, String tuesday_occutime, String tuesday_unoccutime, int tuesday_occutemp, String wednesday_occutime, String wednesday_unoccutime, int wednesday_occutemp, String thursday_occutime, String thursday_unoccutime, int thursday_occutemp, String friday_occutime, String friday_unoccutime, int friday_occutemp, String saturday_occutime, String saturday_unoccutime, int saturday_occutemp, String sunday_occutime, String sunday_unoccutime, int sunday_occutemp, String schedule_type) {
        this.name = name;
        this.fsv_paired = fsv_paired;
        this.fsv_address = fsv_address;
        this.damper_size = damper_size;
        this.damper_type = damper_type;
        this.occu_sensor = occu_sensor;
        this.set_temp = set_temp;
        this.cur_temp = cur_temp;
        this.damper_pos = damper_pos;
        this.occupied = occupied;
        this.airflow_temp = airflow_temp;
        this.monday_occutime = monday_occutime;
        this.monday_unoccutime = monday_unoccutime;
        this.monday_occutemp = monday_occutemp;
        this.tuesday_occutime = tuesday_occutime;
        this.tuesday_unoccutime = tuesday_unoccutime;
        this.tuesday_occutemp = tuesday_occutemp;
        this.wednesday_occutime = wednesday_occutime;
        this.wednesday_unoccutime = wednesday_unoccutime;
        this.wednesday_occutemp = wednesday_occutemp;
        this.thursday_occutime = thursday_occutime;
        this.thursday_unoccutime = thursday_unoccutime;
        this.thursday_occutemp = thursday_occutemp;
        this.friday_occutime = friday_occutime;
        this.friday_unoccutime = friday_unoccutime;
        this.friday_occutemp = friday_occutemp;
        this.saturday_occutime = saturday_occutime;
        this.saturday_unoccutime = saturday_unoccutime;
        this.saturday_occutemp = saturday_occutemp;
        this.sunday_occutime = sunday_occutime;
        this.sunday_unoccutime = sunday_unoccutime;
        this.sunday_occutemp = sunday_occutemp;
        this.schedule_type = schedule_type;
    }

    public int getSet_temp() {
        return set_temp;
    }

    public int getCur_temp() {
        return cur_temp;
    }

    public int getDamper_pos() {
        return damper_pos;
    }

    public String getOccupied() {
        return occupied;
    }

    public int getAirflow_temp() {
        return airflow_temp;
    }

    public String getMonday_occutime() {
        return monday_occutime;
    }

    public String getMonday_unoccutime() {
        return monday_unoccutime;
    }

    public int getMonday_occutemp() {
        return monday_occutemp;
    }

    public String getTuesday_occutime() {
        return tuesday_occutime;
    }

    public String getTuesday_unoccutime() {
        return tuesday_unoccutime;
    }

    public int getTuesday_occutemp() {
        return tuesday_occutemp;
    }

    public String getWednesday_occutime() {
        return wednesday_occutime;
    }

    public String getWednesday_unoccutime() {
        return wednesday_unoccutime;
    }

    public int getWednesday_occutemp() {
        return wednesday_occutemp;
    }

    public String getThursday_occutime() {
        return thursday_occutime;
    }

    public String getThursday_unoccutime() {
        return thursday_unoccutime;
    }

    public int getThursday_occutemp() {
        return thursday_occutemp;
    }

    public String getFriday_occutime() {
        return friday_occutime;
    }

    public String getFriday_unoccutime() {
        return friday_unoccutime;
    }

    public int getFriday_occutemp() {
        return friday_occutemp;
    }

    public String getSaturday_occutime() {
        return saturday_occutime;
    }

    public String getSaturday_unoccutime() {
        return saturday_unoccutime;
    }

    public int getSaturday_occutemp() {
        return saturday_occutemp;
    }

    public String getSunday_occutime() {
        return sunday_occutime;
    }

    public String getSunday_unoccutime() {
        return sunday_unoccutime;
    }

    public int getSunday_occutemp() {
        return sunday_occutemp;
    }

    public String getName() {
        return name;
    }

    public boolean isFsv_paired() {
        return fsv_paired;
    }

    public int getFsv_address() {
        return fsv_address;
    }

    public int getDamper_size() {
        return damper_size;
    }

    public String getDamper_type() {
        return damper_type;
    }

    public String getOccu_sensor() {
        return occu_sensor;
    }
}

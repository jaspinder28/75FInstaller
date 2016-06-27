package com.x75f.installer.Utils;


public class Damper_test_row_data {

    private String name;
    private int damper_pos;
    private int fsv_address;

    public Damper_test_row_data(String name, int damper_pos, int fsv_address) {
        this.name = name;
        this.damper_pos = damper_pos;
        this.fsv_address = fsv_address;
    }

    public String getName() {
        return name;
    }

    public int getFsv_address() {
        return fsv_address;
    }

    public int getDamper_pos() {
        return damper_pos;
    }
}

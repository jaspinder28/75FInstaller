package com.x75f.installer.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TableRow;
import android.widget.TextView;

import com.x75f.installer.R;
import com.x75f.installer.Utils.zone_data;

import java.util.ArrayList;


public class Zones_Adapter extends BaseAdapter {
    ArrayList<zone_data> zone_datas;
    Context c;
    View_Holder view_holder;
    View row;
    LayoutInflater inflater;

    public Zones_Adapter(Context c, ArrayList<zone_data> data) {
        zone_datas = new ArrayList<>();
        zone_datas = data;
        this.c = c;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return zone_datas.size();
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
            row = inflater.inflate(R.layout.zone_detail_row, parent, false);
            view_holder = new View_Holder(row);
            row.setTag(view_holder);

        } else {
            view_holder = (View_Holder) row.getTag();
        }
        if (zone_datas.size() > 0) {
            view_holder.zoneName.setText("" + zone_datas.get(position).getName());
            if (zone_datas.get(position).isFsv_paired()) {
                view_holder.wrm_address.setText("" + zone_datas.get(position).getFsv_address());
                view_holder.damper_size.setText("" + zone_datas.get(position).getDamper_size());
                view_holder.installed_damper.setText("" + zone_datas.get(position).getDamper_type());
                view_holder.occupancy_sensor.setText("" + zone_datas.get(position).getOccu_sensor()+"            ");
                view_holder.desired_temp.setText("" + zone_datas.get(position).getSet_temp());
                view_holder.current_temp.setText("" + zone_datas.get(position).getCur_temp());
                view_holder.damper_pos.setText("" + zone_datas.get(position).getDamper_pos());
                if (zone_datas.get(position).getOccupied().equals("")) {
                    view_holder.tableRow10.setVisibility(View.GONE);
                } else {
                    view_holder.occupancy.setText("" + zone_datas.get(position).getOccupied());
                }
                view_holder.air_flow_temp.setText("" + zone_datas.get(position).getAirflow_temp());
                if (!zone_datas.get(position).getMonday_occutime().equals("")) {
                    view_holder.zone_schedule_Mon.setText("Mon      " + zone_datas.get(position).getMonday_occutime() + "/" + zone_datas.get(position).getMonday_occutemp() + "/" + zone_datas.get(position).getMonday_unoccutime());
                    view_holder.zone_schedule_Tue.setText("Tue       " + zone_datas.get(position).getTuesday_occutime() + "/" + zone_datas.get(position).getTuesday_occutemp() + "/" + zone_datas.get(position).getTuesday_unoccutime());
                    view_holder.zone_schedule_Wed.setText("Wed      " + zone_datas.get(position).getWednesday_occutime() + "/" + zone_datas.get(position).getWednesday_occutemp() + "/" + zone_datas.get(position).getWednesday_unoccutime());
                    view_holder.zone_schedule_Thu.setText("Thu       " + zone_datas.get(position).getThursday_occutime() + "/" + zone_datas.get(position).getThursday_occutemp() + "/" + zone_datas.get(position).getThursday_unoccutime());
                    view_holder.zone_schedule_Fri.setText("Fri         " + zone_datas.get(position).getFriday_occutime() + "/" + zone_datas.get(position).getFriday_occutemp() + "/" + zone_datas.get(position).getFriday_unoccutime());
                    view_holder.zone_schedule_Sat.setText("Sat        " + zone_datas.get(position).getSaturday_occutime() + "/" + zone_datas.get(position).getSaturday_occutemp() + "/" + zone_datas.get(position).getSaturday_unoccutime());
                    view_holder.zone_schedule_Sun.setText("Sun       " + zone_datas.get(position).getSunday_occutime() + "/" + zone_datas.get(position).getSunday_occutemp() + "/" + zone_datas.get(position).getSunday_unoccutime());
                } else {
                    view_holder.zone_schedule.setVisibility(View.GONE);
                    view_holder.zone_schedule_Mon.setVisibility(View.GONE);
                    view_holder.zone_schedule_Tue.setVisibility(View.GONE);
                    view_holder.zone_schedule_Wed.setVisibility(View.GONE);
                    view_holder.zone_schedule_Thu.setVisibility(View.GONE);
                    view_holder.zone_schedule_Fri.setVisibility(View.GONE);
                    view_holder.zone_schedule_Sat.setVisibility(View.GONE);
                    view_holder.zone_schedule_Sun.setVisibility(View.GONE);
                }
            } else {
                view_holder.tableRow2.setVisibility(View.VISIBLE);
                view_holder.tableRow3.setVisibility(View.GONE);
                view_holder.tableRow4.setVisibility(View.GONE);
                view_holder.tableRow5.setVisibility(View.GONE);
                view_holder.tableRow6.setVisibility(View.GONE);
                view_holder.tableRow7.setVisibility(View.GONE);
                view_holder.tableRow8.setVisibility(View.GONE);
                view_holder.tableRow9.setVisibility(View.GONE);
                view_holder.tableRow10.setVisibility(View.GONE);
                view_holder.tableRow11.setVisibility(View.GONE);
                view_holder.zone_schedule.setVisibility(View.GONE);
                view_holder.zone_schedule_Mon.setVisibility(View.GONE);
                view_holder.zone_schedule_Tue.setVisibility(View.GONE);
                view_holder.zone_schedule_Wed.setVisibility(View.GONE);
                view_holder.zone_schedule_Thu.setVisibility(View.GONE);
                view_holder.zone_schedule_Fri.setVisibility(View.GONE);
                view_holder.zone_schedule_Sat.setVisibility(View.GONE);
                view_holder.zone_schedule_Sun.setVisibility(View.GONE);
            }

        }
        return row;
    }

    public class View_Holder {
        private TextView zoneName;
        private TextView wrm_address;
        private TextView damper_size;
        private TextView installed_damper;
        private TextView occupancy_sensor;
        private TextView status;
        private TextView desired_temp;
        private TextView current_temp;
        private TextView damper_pos;
        private TextView occupancy;
        private TextView air_flow_temp;
        private TextView zone_schedule;
        private TextView zone_schedule_Mon;
        private TextView zone_schedule_Tue;
        private TextView zone_schedule_Wed;
        private TextView zone_schedule_Thu;
        private TextView zone_schedule_Fri;
        private TextView zone_schedule_Sat;
        private TextView zone_schedule_Sun;
        private TableRow tableRow2;
        private TableRow tableRow3;
        private TableRow tableRow4;
        private TableRow tableRow5;
        private TableRow tableRow6;
        private TableRow tableRow7;
        private TableRow tableRow8;
        private TableRow tableRow9;
        private TableRow tableRow10;
        private TableRow tableRow11;


        public View_Holder(View v) {
            zoneName = (TextView) v.findViewById(R.id.zoneName);
            wrm_address = (TextView) v.findViewById(R.id.wrm_address);
            damper_size = (TextView) v.findViewById(R.id.damper_size);
            installed_damper = (TextView) v.findViewById(R.id.installed_damper);
            occupancy_sensor = (TextView) v.findViewById(R.id.occupancy_sensor);
            status = (TextView) v.findViewById(R.id.status);
            desired_temp = (TextView) v.findViewById(R.id.desired_temp);
            current_temp = (TextView) v.findViewById(R.id.current_temp);
            damper_pos = (TextView) v.findViewById(R.id.damper_pos);
            occupancy = (TextView) v.findViewById(R.id.occupancy);
            air_flow_temp = (TextView) v.findViewById(R.id.air_flow_temp);
            zone_schedule = (TextView) v.findViewById(R.id.zone_schedule);
            zone_schedule_Mon = (TextView) v.findViewById(R.id.zone_schedule_Mon);
            zone_schedule_Tue = (TextView) v.findViewById(R.id.zone_schedule_Tue);
            zone_schedule_Wed = (TextView) v.findViewById(R.id.zone_schedule_Wed);
            zone_schedule_Thu = (TextView) v.findViewById(R.id.zone_schedule_Thu);
            zone_schedule_Fri = (TextView) v.findViewById(R.id.zone_schedule_Fri);
            zone_schedule_Sat = (TextView) v.findViewById(R.id.zone_schedule_Sat);
            zone_schedule_Sun = (TextView) v.findViewById(R.id.zone_schedule_Sun);
            tableRow2 = (TableRow) v.findViewById(R.id.tableRow2);
            tableRow3 = (TableRow) v.findViewById(R.id.tableRow3);
            tableRow4 = (TableRow) v.findViewById(R.id.tableRow4);
            tableRow5 = (TableRow) v.findViewById(R.id.tableRow5);
            tableRow6 = (TableRow) v.findViewById(R.id.tableRow6);
            tableRow7 = (TableRow) v.findViewById(R.id.tableRow7);
            tableRow8 = (TableRow) v.findViewById(R.id.tableRow8);
            tableRow9 = (TableRow) v.findViewById(R.id.tableRow9);
            tableRow10 = (TableRow) v.findViewById(R.id.tableRow10);
            tableRow11 = (TableRow) v.findViewById(R.id.tableRow11);
        }
    }
}

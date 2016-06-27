package com.x75f.installer.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SeekBar;
import android.widget.TextView;

import com.x75f.installer.Activity.CCU_Details;
import com.x75f.installer.R;
import com.x75f.installer.Utils.Damper_test_row_data;
import com.x75f.installer.Utils.Generic_Methods;

import java.util.ArrayList;

public class Damper_test_Adapter extends BaseAdapter {
    ArrayList<Damper_test_row_data> row_datas;
    Context c;
    ViewHolder viewHolder;
    View row;
    String cccuid;
    LayoutInflater inflater;

    public Damper_test_Adapter(Context c, ArrayList<Damper_test_row_data> row_data, String ccuid) {
        row_datas = new ArrayList<>();
        this.c = c;
        row_datas = row_data;
        notifyDataSetChanged();
        this.cccuid = ccuid;
    }

    @Override
    public int getCount() {
        return row_datas.size();
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
        viewHolder = null;
        if (row == null) {
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.damper_test_row, parent, false);
            viewHolder = new ViewHolder(row);
            row.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) row.getTag();
        }
        if (row_datas.size() != 0) {
            final Damper_test_row_data temp = row_datas.get(position);
            viewHolder.zone_name.setText(temp.getName());
            viewHolder.damper_position.setText(temp.getDamper_pos() + "");
            viewHolder.seekbar.setProgress(temp.getDamper_pos());
            SeekBar seekBar = (SeekBar) row.findViewById(R.id.seekbar);
            final TextView damper_position = (TextView) row.findViewById(R.id.damper_position);
            seekBar.setTag(position);
            damper_position.setTag(position);
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    damper_position.setText(progress + "");
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    if (Generic_Methods.isNetworkAvailable(CCU_Details.getSingletonContext())) {
                        String channel = cccuid + "_Installer_DMPTEST";
                        String msg = Generic_Methods.createPubnubSystemDamperMsg(seekBar.getProgress(), temp.getFsv_address());
                        Generic_Methods.PublishToChannel(channel, msg);
                    } else {
                        Generic_Methods.getToast(CCU_Details.getSingletonContext(), CCU_Details.getSingletonContext().getResources().getString(R.string.user_offline));
                    }
                }
            });
        }
        return row;
    }

    public class ViewHolder {
        public TextView zone_name;
        public TextView damper_position;
        public SeekBar seekbar;

        public ViewHolder(View v) {
            zone_name = (TextView) v.findViewById(R.id.zone_name);
            damper_position = (TextView) v.findViewById(R.id.damper_position);
            seekbar = (SeekBar) v.findViewById(R.id.seekbar);
        }


    }
}

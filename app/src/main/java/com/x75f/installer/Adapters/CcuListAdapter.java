package com.x75f.installer.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.x75f.installer.Activity.CCU_Details;
import com.x75f.installer.R;
import com.x75f.installer.Utils.UsersData;

import java.util.ArrayList;


public class CcuListAdapter extends BaseAdapter {
    ArrayList<UsersData> Ccudata;
    Context c;
    ViewHolder viewHolder;
    View row;
    LayoutInflater inflater;

    public CcuListAdapter(Context c, ArrayList<UsersData> data) {
        Ccudata = new ArrayList<>();
        this.c = c;
        Ccudata = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return Ccudata.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        row = convertView;
        viewHolder = null;
        if (row == null) {
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.ccu_address_list_row, parent, false);
            viewHolder = new ViewHolder(row);
            row.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) row.getTag();
        }
        if (Ccudata.size() > 0) {
            viewHolder.tvCcuName.setText(Ccudata.get(position).getCcuName());
            viewHolder.tvCcuAddress.setText(Ccudata.get(position).getAddress() + " " + Ccudata.get(position).getZipcode());

            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(c, CCU_Details.class);
                    i.putExtra("CcuData", Ccudata.get(position).getCcuName());
                    i.putExtra("ccu_id", Ccudata.get(position).getUsername());
                    c.startActivity(i);

                }
            });
        }
        return row;
    }

    public class ViewHolder {
        public TextView tvCcuName;
        public TextView tvCcuAddress;

        public ViewHolder(View v) {
            tvCcuName = (TextView) v.findViewById(R.id.tvCcuName);
            tvCcuAddress = (TextView) v.findViewById(R.id.tvCcuAddress);
        }


    }
}

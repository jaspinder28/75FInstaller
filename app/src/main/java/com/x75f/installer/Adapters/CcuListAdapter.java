package com.x75f.installer.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.x75f.installer.Activity.CCU_Details;
import com.x75f.installer.R;
import com.x75f.installer.Utils.UsersData;

import java.util.ArrayList;


public class CcuListAdapter extends BaseAdapter implements Filterable {
    ArrayList<UsersData> Ccudata;
    ArrayList<UsersData> mainData;
    ArrayList<UsersData> orig;
    Context c;
    ViewHolder viewHolder;
    View row;
    LayoutInflater inflater;

    public CcuListAdapter(Context c, ArrayList<UsersData> data) {
        Ccudata = new ArrayList<>();
        this.c = c;
        Ccudata = data;
        mainData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        int x;
        if (Ccudata == null) {
            x = mainData.size();
        } else {
            x = Ccudata.size();
        }
        return x;
    }

    @Override
    public Object getItem(int position) {
        if (Ccudata == null) {
            return mainData.get(position);
        } else {
            return Ccudata.get(position);
        }
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
        if (Ccudata == null) {
            if (mainData.size() > 0) {
                viewHolder.tvCcuName.setText(mainData.get(position).getCcuName());
                viewHolder.tvCcuAddress.setText(mainData.get(position).getAddress() + " " + mainData.get(position).getZipcode());

                row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(c, CCU_Details.class);
                        i.putExtra("CcuData", mainData.get(position).getCcuName());
                        i.putExtra("ccu_id", mainData.get(position).getUsername());
                        c.startActivity(i);

                    }
                });
            }
        } else {
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
        }
        return row;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<UsersData> results = new ArrayList<UsersData>();
                if (orig == null)
                    orig = Ccudata;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final UsersData g : orig) {
                            if (g.getCcuName().toLowerCase()
                                    .contains(constraint.toString()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                Ccudata = (ArrayList<UsersData>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
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

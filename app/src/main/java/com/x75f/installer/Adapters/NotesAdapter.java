package com.x75f.installer.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.x75f.installer.Activity.AddEditNote;
import com.x75f.installer.Activity.CCU_Details;
import com.x75f.installer.R;
import com.x75f.installer.Utils.NotesPreviewData;

import java.util.ArrayList;

/**
 * Created by JASPINDER on 7/11/2016.
 */
public class NotesAdapter extends BaseAdapter {

    ArrayList<NotesPreviewData> NotesData;
    Context c;
    ViewHolder viewHolder;
    View row;
    LayoutInflater inflater;


    public NotesAdapter(Context c, ArrayList<NotesPreviewData> data) {
        NotesData = data;
        this.c = c;
    }

    @Override
    public int getCount() {
        return NotesData.size();
    }

    @Override
    public Object getItem(int position) {
        return NotesData.get(position);
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
            row = inflater.inflate(R.layout.notes_list_row, parent, false);
            viewHolder = new ViewHolder(row);
            row.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) row.getTag();
        }
        if (NotesData.size() != 0) {
            NotesPreviewData temp = NotesData.get(position);
            viewHolder.tvNotePreviewText.setText(temp.getPreview_text());
            viewHolder.tvNoteUpdateTime.setText(temp.getModified_time());
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(CCU_Details.getSingletonContext(), AddEditNote.class);
                    c.startActivity(i);
                }
            });

        }
        return row;
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public class ViewHolder {
        public TextView tvNotePreviewText;
        public TextView tvNoteUpdateTime;

        public ViewHolder(View v) {
            tvNotePreviewText = (TextView) v.findViewById(R.id.tvNotePreviewText);
            tvNoteUpdateTime = (TextView) v.findViewById(R.id.tvNoteUpdateTime);
        }


    }
}

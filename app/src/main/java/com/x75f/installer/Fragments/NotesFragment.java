package com.x75f.installer.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.x75f.installer.Activity.CCU_Details;
import com.x75f.installer.R;
import com.x75f.installer.Utils.Generic_Methods;

/**
 * Created by WIN8.1 on 6/7/2016.
 */
public class NotesFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.notes_fragment,container,false);
        return v;
    }
}

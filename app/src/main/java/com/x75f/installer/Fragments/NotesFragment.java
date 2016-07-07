package com.x75f.installer.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.x75f.installer.R;
import com.x75f.installer.Utils.Generic_Methods;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by WIN8.1 on 6/7/2016.
 */
public class NotesFragment extends Fragment {
//    @InjectView(R.id.rtEditText)
//    RTEditText notesEditor;
//    Bundle state;
    private String message;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.notes_fragment, container, false);
//        state = savedInstanceState;
        ButterKnife.inject(this, v);
        return v;
    }



    @Override
    public void onResume() {
        super.onResume();
//        if (CCU_Details.getSingletonContext().viewPager.getCurrentItem() == 4) {
//            CCU_Details.getSingletonContext().NotesManager.registerEditor(notesEditor,true);
//        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}

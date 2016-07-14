package com.x75f.installer.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.kinvey.android.AsyncAppData;
import com.kinvey.android.callback.KinveyListCallback;
import com.kinvey.java.Query;
import com.x75f.installer.Activity.CCU_Details;
import com.x75f.installer.Adapters.NotesAdapter;
import com.x75f.installer.R;
import com.x75f.installer.Utils.Generic_Methods;
import com.x75f.installer.Utils.NotesPreviewData;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by WIN8.1 on 6/7/2016.
 */
public class NotesFragment extends Fragment {

    @InjectView(R.id.bAddNote)
    TextView bAddNote;
    @InjectView(R.id.NotesList)
    ListView NotesList;
    private ArrayList<NotesPreviewData> noteData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.notes_fragment, container, false);
        ButterKnife.inject(this, v);
        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (CCU_Details.getSingletonContext().viewPager.getCurrentItem() == 4) {
            noteData = new ArrayList<>();
            Query newquery = new Query();
            String collectionName = "00InstallerNotes";
            newquery.equals("ccu_id", getArguments().getString("ccu_id"));
            if (Generic_Methods.isNetworkAvailable(CCU_Details.getSingletonContext())) {
                AsyncAppData<NotesPreviewData> summary = Generic_Methods.getKinveyClient().appData(collectionName, NotesPreviewData.class);
                if (Generic_Methods.getKinveyClient().user().isUserLoggedIn()) {
                    summary.get(newquery, new KinveyListCallback<NotesPreviewData>() {

                        @Override
                        public void onSuccess(NotesPreviewData[] notesPreviewDatas) {
                            if (notesPreviewDatas.length != 0) {
                                noteData.clear();
                                noteData.addAll(Arrays.asList(notesPreviewDatas));
                                NotesAdapter adapter = new NotesAdapter(CCU_Details.getSingletonContext(), noteData);
                                NotesList.setAdapter(adapter);
                            }
                        }

                        @Override
                        public void onFailure(Throwable throwable) {

                        }
                    });
                }
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}

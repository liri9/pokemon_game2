package com.example.game_project.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.game_project.R;
import com.example.game_project.activities.ScoreActivity;
import com.example.game_project.data.ListOfScoreRecords;
import com.example.game_project.data.ScoreRecord;
import com.example.game_project.utilities.ScoreRecordAdapter;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class Fragment_List extends Fragment {
    private ListView fragmentList_LIST_Scores;
    private ScoreActivity.Callback_List callback_list;


    public void setCallback_list(ScoreActivity.Callback_List callback_list) {
        this.callback_list = callback_list;
    }

    private void initViews() {
        ListOfScoreRecords data = callback_list.getTopTenScoreRecords();
        if (data != null) {
            List<ScoreRecord> scoreRecords = data.getListOfScoreRecords();
            ScoreRecordAdapter adapter = new ScoreRecordAdapter(getActivity(), android.R.layout.simple_list_item_1, scoreRecords);
            fragmentList_LIST_Scores.setAdapter(adapter);

        }
        fragmentList_LIST_Scores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ScoreRecord scoreRecord = (ScoreRecord) adapterView.getItemAtPosition(i);
                LatLng location = new LatLng(scoreRecord.getLatitude(), scoreRecord.getLongitude());
                ((ScoreActivity) getActivity()).zoomToLocation(location);
            }
        });
    }


    private void findViews(View view) {
        fragmentList_LIST_Scores = view.findViewById(R.id.fragmentList_LIST_Scores);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(view);
        initViews();
        return view;
    }

}
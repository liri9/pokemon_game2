package com.example.game_project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.example.game_project.R;
import com.example.game_project.data.DataManager;
import com.example.game_project.data.ListOfScoreRecords;
import com.example.game_project.data.ScoreRecord;
import com.example.game_project.fragments.Fragment_List;
import com.example.game_project.fragments.Fragment_Maps;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.button.MaterialButton;

public class ScoreActivity extends AppCompatActivity {

    private MaterialButton score_BTN_close;
    private AppCompatImageView score_IMG_back;
    private Fragment_List fragment_list;
    private Fragment_Maps fragment_map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        findViews();
        initViews();
        initFragments();
        loadFragments();
    }

    private void initFragments() {
        fragment_map = new Fragment_Maps();
        fragment_map.setCallback_map(callback_map);


        fragment_list = new Fragment_List();
        fragment_list.setCallback_list(callback_list);
    }

    Callback_List callback_list = new Callback_List() {
        @Override
        public ListOfScoreRecords getTopTenScoreRecords() {
            return DataManager.getTopTenScoreRecords();
        }
    };

    public interface Callback_List {
        ListOfScoreRecords getTopTenScoreRecords();
    }

    private void loadFragments() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.score_LAY_list, fragment_list)
                .commit();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.score_LAY_map, fragment_map)
                .commit();
    }

    private void findViews() {
        score_BTN_close = findViewById(R.id.score_BTN_close);
        score_IMG_back = findViewById(R.id.score_IMG_back);
    }


    private void initViews() {
        Glide
                .with(ScoreActivity.this)
                .load("https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/2bc72d3c-39ce-45f3-a55c-e0903291ace0/d17dpy4-a8d0b94a-0351-4d9b-9636-33cc1842f0d2.jpg/v1/fill/w_600,h_486,q_75,strp/background_art_for_pokemon_by_orangedroplet_d17dpy4-fullview.jpg?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7ImhlaWdodCI6Ijw9NDg2IiwicGF0aCI6IlwvZlwvMmJjNzJkM2MtMzljZS00NWYzLWE1NWMtZTA5MDMyOTFhY2UwXC9kMTdkcHk0LWE4ZDBiOTRhLTAzNTEtNGQ5Yi05NjM2LTMzY2MxODQyZjBkMi5qcGciLCJ3aWR0aCI6Ijw9NjAwIn1dXSwiYXVkIjpbInVybjpzZXJ2aWNlOmltYWdlLm9wZXJhdGlvbnMiXX0.Xa7d7pbU0rU7MNWAomQrAPKjMwYxdB82ALLKMZO_pOs")
                .into(score_IMG_back);

        score_BTN_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScoreActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public interface Callback_Map {
        void setMarkers(GoogleMap map);
   //     void zoomToLocation(GoogleMap map, LatLng location);
    }
    public void zoomToLocation(LatLng location) {
        fragment_map.zoomToLocation(location);
    }
    Callback_Map callback_map = new Callback_Map() {
        @Override
        public void setMarkers(GoogleMap map) {
            map.clear();
            ListOfScoreRecords topTenScoreRecords = DataManager.getTopTenScoreRecords();
            if (topTenScoreRecords != null) {
                for (int i = 0; i < topTenScoreRecords.size(); i++) {
                    ScoreRecord result = topTenScoreRecords.get(i);
                    map.addMarker(new MarkerOptions()
                            .position(new LatLng(
                                    result.getLatitude(),
                                    result.getLongitude()))
                            .title("" + i));
                }
            }
        }


//        @Override
//        public void zoomToLocation(GoogleMap map,LatLng location) {
//            map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
//        }

    };


}
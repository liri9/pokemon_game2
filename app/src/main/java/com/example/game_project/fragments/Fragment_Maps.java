package com.example.game_project.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.game_project.R;
import com.example.game_project.activities.ScoreActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Fragment_Maps extends Fragment implements OnMapReadyCallback {
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 100;
    private ScoreActivity.Callback_Map callback_map;
    private GoogleMap googleMap;


    public Fragment_Maps() {

    }

    public void setCallback_map(ScoreActivity.Callback_Map callback_map) {
        this.callback_map = callback_map;
    }

    OnMapReadyCallback onMapReadyCallback = googleMap -> callback_map.setMarkers(googleMap);

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        SupportMapFragment supportMapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragment_map_MAP));
        supportMapFragment.getMapAsync(googleMap -> {
            this.googleMap = googleMap;
        });


        return view;
    }

    public void zoomToLocation(LatLng location) {
        if (googleMap!=null)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));

        addMarker(location);
//
//        // Construct a CameraPosition focusing on Mountain View and animate the camera to that position.
//        CameraPosition cameraPosition = new CameraPosition.Builder().target(location)      // Sets the center of the map to Mountain View
//                .zoom(17)                   // Sets the zoom
//                .build();                   // Creates a CameraPosition from the builder
//        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void addMarker(LatLng randomPlace) {
        googleMap.addMarker(new MarkerOptions().position(randomPlace));
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;

    }


}
package com.example.game_project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.example.game_project.R;
import com.example.game_project.init.MyGPS;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {
    MaterialButton main_BTN_start_game,main_BTN_high_scores;
    AppCompatImageView main_IMG_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initViews();
        initGPS();
    }
    private void initGPS(){
        MyGPS.getInstance().checkPermissions(this);
    }

    private void findViews() {
        main_IMG_back =findViewById(R.id.main_IMG_back);
        main_BTN_high_scores=findViewById(R.id.main_BTN_high_scores);
        main_BTN_start_game=findViewById(R.id.main_BTN_start_game);
    }

    private void initViews() {
        Glide
                .with(MainActivity.this)
                .load("https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/2bc72d3c-39ce-45f3-a55c-e0903291ace0/d17dpy4-a8d0b94a-0351-4d9b-9636-33cc1842f0d2.jpg/v1/fill/w_600,h_486,q_75,strp/background_art_for_pokemon_by_orangedroplet_d17dpy4-fullview.jpg?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7ImhlaWdodCI6Ijw9NDg2IiwicGF0aCI6IlwvZlwvMmJjNzJkM2MtMzljZS00NWYzLWE1NWMtZTA5MDMyOTFhY2UwXC9kMTdkcHk0LWE4ZDBiOTRhLTAzNTEtNGQ5Yi05NjM2LTMzY2MxODQyZjBkMi5qcGciLCJ3aWR0aCI6Ijw9NjAwIn1dXSwiYXVkIjpbInVybjpzZXJ2aWNlOmltYWdlLm9wZXJhdGlvbnMiXX0.Xa7d7pbU0rU7MNWAomQrAPKjMwYxdB82ALLKMZO_pOs")
                .into(main_IMG_back);

        main_BTN_high_scores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ScoreActivity.class);
                startActivity(intent);
            }
        });
        main_BTN_start_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this, ModeActivity.class);
                startActivity(intent2);
            }
        });


    }


}
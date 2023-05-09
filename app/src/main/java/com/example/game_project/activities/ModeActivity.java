package com.example.game_project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.example.game_project.R;
import com.example.game_project.data.GameManager;
import com.google.android.material.button.MaterialButton;

public class ModeActivity extends AppCompatActivity {
    private AppCompatImageView game_IMG_back;
    private MaterialButton mode_BTN_buttons;
    private MaterialButton mode_BTN_sensor;
    private Switch mode_SWT_fast;
    boolean checked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);
        findViews();
        setOnClickListeners();
        initBackground();
    }

    private void setOnClickListeners() {
        mode_SWT_fast.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
              checked = isChecked;
            }
        });
//        mode_BTN_buttons.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                GameManager.getInstance().setfastMode(checked);
//                GameManager.getInstance().setButtonMode(true);
//                GameManager.getInstance().setSensorMode(false);
//                 intent = new Intent(ModeActivity.this, GameActivity.class);
//            }
//        });
//        mode_BTN_sensor.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                GameManager.getInstance().setButtonMode(false);
//                GameManager.getInstance().setSensorMode(true);
//                 intent = new Intent(ModeActivity.this, GameActivity.class);
//            }
//
//        finish();
//        startActivity(intent);
//        }
//        );

        final int BTN_SENSOR_MODE = R.id.mode_BTN_sensor;
        final int BTN_BUTTON_MODE = R.id.mode_BTN_buttons;
        View.OnClickListener listener = v -> {
            Intent intent;
            switch (v.getId()) {
                case BTN_SENSOR_MODE:

                    GameManager.getInstance().setSensorMode(true);
                    GameManager.getInstance().setButtonMode(false);
                    intent = new Intent(ModeActivity.this, GameActivity.class);
                    break;
                case BTN_BUTTON_MODE:
                    GameManager.getInstance().setfastMode(checked);
                    GameManager.getInstance().setSensorMode(false);
                    GameManager.getInstance().setButtonMode(true);
                    intent = new Intent(ModeActivity.this, GameActivity.class);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid view id");
            }
            finish();
            startActivity(intent);
        };

        mode_BTN_sensor.setOnClickListener(listener);
        mode_BTN_buttons.setOnClickListener(listener);
    }

    private void findViews() {
        mode_BTN_buttons = findViewById(R.id.mode_BTN_buttons);
        mode_BTN_sensor = findViewById(R.id.mode_BTN_sensor);
        game_IMG_back = findViewById(R.id.mode_IMG_back);
        mode_SWT_fast = findViewById(R.id.mode_SWT_fast);
    }

    private void initBackground() {
        Glide
                .with(ModeActivity.this)
                .load("https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/2bc72d3c-39ce-45f3-a55c-e0903291ace0/d17dpy4-a8d0b94a-0351-4d9b-9636-33cc1842f0d2.jpg/v1/fill/w_600,h_486,q_75,strp/background_art_for_pokemon_by_orangedroplet_d17dpy4-fullview.jpg?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7ImhlaWdodCI6Ijw9NDg2IiwicGF0aCI6IlwvZlwvMmJjNzJkM2MtMzljZS00NWYzLWE1NWMtZTA5MDMyOTFhY2UwXC9kMTdkcHk0LWE4ZDBiOTRhLTAzNTEtNGQ5Yi05NjM2LTMzY2MxODQyZjBkMi5qcGciLCJ3aWR0aCI6Ijw9NjAwIn1dXSwiYXVkIjpbInVybjpzZXJ2aWNlOmltYWdlLm9wZXJhdGlvbnMiXX0.Xa7d7pbU0rU7MNWAomQrAPKjMwYxdB82ALLKMZO_pOs")
                .into(game_IMG_back);
    }
}
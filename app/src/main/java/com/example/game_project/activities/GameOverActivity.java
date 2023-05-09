package com.example.game_project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.example.game_project.R;
import com.example.game_project.data.GameManager;
import com.google.android.material.button.MaterialButton;

public class GameOverActivity extends AppCompatActivity {
    private AppCompatImageView gameOver_IMG_back;
    private MaterialButton gameOver_BTN_enter;
    private EditText gameover_EDT_name ;
    private GameManager gameManager;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        findViews();
        initViews();
    }
    private void findViews() {
        gameOver_IMG_back =findViewById(R.id.gameOver_IMG_back);
        gameover_EDT_name = findViewById(R.id.gameover_EDT_name);
        gameOver_BTN_enter = findViewById(R.id.gameover_BTN_enter);


    }

    private void initViews() {
        Glide
                .with(GameOverActivity.this)
                .load("https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/2bc72d3c-39ce-45f3-a55c-e0903291ace0/d17dpy4-a8d0b94a-0351-4d9b-9636-33cc1842f0d2.jpg/v1/fill/w_600,h_486,q_75,strp/background_art_for_pokemon_by_orangedroplet_d17dpy4-fullview.jpg?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7ImhlaWdodCI6Ijw9NDg2IiwicGF0aCI6IlwvZlwvMmJjNzJkM2MtMzljZS00NWYzLWE1NWMtZTA5MDMyOTFhY2UwXC9kMTdkcHk0LWE4ZDBiOTRhLTAzNTEtNGQ5Yi05NjM2LTMzY2MxODQyZjBkMi5qcGciLCJ3aWR0aCI6Ijw9NjAwIn1dXSwiYXVkIjpbInVybjpzZXJ2aWNlOmltYWdlLm9wZXJhdGlvbnMiXX0.Xa7d7pbU0rU7MNWAomQrAPKjMwYxdB82ALLKMZO_pOs")
                .into(gameOver_IMG_back);


        gameOver_BTN_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = gameover_EDT_name.getText().toString();
                if (!name.equals("")){
                    Intent intent = new Intent(GameOverActivity.this, ScoreActivity.class);
                    startActivity(intent);
                    saveScoreRecord();
                }
            }
        });

    }

    public void saveScoreRecord() {
        gameManager.getInstance().saveNewScoreRecord(name);
    }


}
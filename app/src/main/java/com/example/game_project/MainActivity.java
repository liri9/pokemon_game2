package com.example.game_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;
import android.media.MediaActionSound;

import com.google.android.material.button.MaterialButton;


import java.util.ArrayList;
import java.util.Random;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private final int DELAY1 = 400;
    //private final int DELAY2 = 800;
    private AppCompatImageView game_IMG_back;

    private AppCompatImageView[] game_IMG_hearts;
    private AppCompatImageView[][] game_IMG_pokeballs;
    private AppCompatImageView[] game_IMG_pikachus;
    private MaterialButton game_BTN_right;
    private MaterialButton game_BTN_left;
    private int lives = 3;
    private final int ROWS =5, COLS =3;
    private enum direction {right, left}
    private boolean pause = false;
    private Random rn = new Random();
    private boolean send=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initViews();
        updateUI();
    }

    private void updateUI() {
        updateLives();
        moveDownUI();
        sendPokeballs();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pause = false;
        startTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopTimer();
        pause = true;
    }

    private void initViews() {
        Glide
                .with(MainActivity.this)
                .load("https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/2bc72d3c-39ce-45f3-a55c-e0903291ace0/d17dpy4-a8d0b94a-0351-4d9b-9636-33cc1842f0d2.jpg/v1/fill/w_600,h_486,q_75,strp/background_art_for_pokemon_by_orangedroplet_d17dpy4-fullview.jpg?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7ImhlaWdodCI6Ijw9NDg2IiwicGF0aCI6IlwvZlwvMmJjNzJkM2MtMzljZS00NWYzLWE1NWMtZTA5MDMyOTFhY2UwXC9kMTdkcHk0LWE4ZDBiOTRhLTAzNTEtNGQ5Yi05NjM2LTMzY2MxODQyZjBkMi5qcGciLCJ3aWR0aCI6Ijw9NjAwIn1dXSwiYXVkIjpbInVybjpzZXJ2aWNlOmltYWdlLm9wZXJhdGlvbnMiXX0.Xa7d7pbU0rU7MNWAomQrAPKjMwYxdB82ALLKMZO_pOs")
                .into(game_IMG_back);
        game_BTN_left.setOnClickListener(v -> moveLeft());
        game_BTN_right.setOnClickListener(v -> moveRight());
    }

    private void findViews() {

        game_IMG_hearts = new AppCompatImageView[]
                {findViewById(R.id.game_IMG_heart1),
                        findViewById(R.id.game_IMG_heart2),
                        findViewById(R.id.game_IMG_heart3)};
        game_IMG_pokeballs = new AppCompatImageView[][]
                {{findViewById(R.id.game_IMG_ball1),
                        findViewById(R.id.game_IMG_ball2),
                        findViewById(R.id.game_IMG_ball3)},
                        {findViewById(R.id.game_IMG_ball4),
                                findViewById(R.id.game_IMG_ball5),
                                findViewById(R.id.game_IMG_ball6)},
                        {findViewById(R.id.game_IMG_ball7),
                                findViewById(R.id.game_IMG_ball8),
                                findViewById(R.id.game_IMG_ball9)},
                        {findViewById(R.id.game_IMG_ball10),
                                findViewById(R.id.game_IMG_ball11),
                                findViewById(R.id.game_IMG_ball12)},
                        {findViewById(R.id.game_IMG_ball13),
                                findViewById(R.id.game_IMG_ball14),
                                findViewById(R.id.game_IMG_ball15)},
                        {findViewById(R.id.game_IMG_ball16),
                                findViewById(R.id.game_IMG_ball17),
                                findViewById(R.id.game_IMG_ball18)}};
        game_IMG_pikachus = new AppCompatImageView[]
                {findViewById(R.id.game_IMG_pika1),
                        findViewById(R.id.game_IMG_pika2),
                        findViewById(R.id.game_IMG_pika3)};
        game_BTN_left = findViewById(R.id.game_BTN_left);
        game_BTN_right = findViewById(R.id.game_BTN_right);
        game_IMG_back = findViewById(R.id.game_IMG_back);
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                game_IMG_pokeballs[j][i].setVisibility(View.INVISIBLE);
            }
        }
        game_IMG_pikachus[0].setVisibility(View.INVISIBLE);
        game_IMG_pikachus[2].setVisibility(View.INVISIBLE);
        game_IMG_pikachus[1].setVisibility(View.VISIBLE);

    }

    private void moveRight() {
        if (game_IMG_pikachus[2].getVisibility() != View.VISIBLE) {
            if (game_IMG_pikachus[0].getVisibility()==View.VISIBLE) {
                updateLocationUI(1, direction.right);
            } else {
                updateLocationUI(2, direction.right);
            }
        }
    }


    private void moveLeft() {
        if (game_IMG_pikachus[0].getVisibility() != View.VISIBLE) {
            if (game_IMG_pikachus[1].getVisibility()==View.VISIBLE) {
                updateLocationUI(0, direction.left);
            } else {
                updateLocationUI(1, direction.left);
            }
        }
    }

    private void reduceLives() {
        lives--;
    }

    private void updateLives() {
        for (int i = 0; i < lives; i++) {
            game_IMG_hearts[i].setVisibility(View.VISIBLE);
        }

        for (int i = lives; i < game_IMG_hearts.length; i++) {
            game_IMG_hearts[i].setVisibility(View.INVISIBLE);
        }
        if (lives == 0) gameOver();
    }

    private void gameOver() {
        stopTimer();
        pause=true;
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                game_IMG_pokeballs[j][i].setVisibility(View.INVISIBLE);
            }
        }
        Intent intent = new Intent(MainActivity.this, GameOverActivity.class);
        startActivity(intent);
    }

    private Handler handler = new Handler();
    private Runnable runnable1 = new Runnable() {
        public void run() {
            handler.postDelayed(runnable1, DELAY1);
            updateUI();
        }
    };

    private void updateLocationUI(int location, direction direction) {
        if (direction == direction.right) {
            game_IMG_pikachus[location - 1].setVisibility(View.INVISIBLE);
            game_IMG_pikachus[location].setVisibility(View.VISIBLE);
        } else {
            game_IMG_pikachus[location + 1].setVisibility(View.INVISIBLE);
            game_IMG_pikachus[location].setVisibility(View.VISIBLE);
        }
        checkHit();
    }


    private void checkHit() {
        for (int i = 0; i < COLS; i++) {
            if (game_IMG_pokeballs[ROWS][i].getVisibility()==View.VISIBLE && game_IMG_pikachus[i].getVisibility()==View.VISIBLE) {
                reduceLives();
                vibrate();
                Toast.makeText(this, "CRASH", Toast.LENGTH_SHORT).show();
                updateLives();
            }
        }
    }


    private void vibrate() {
         Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(100);
        }
    }

    private void sendPokeballs() {
        if (send){
        int i = rn.nextInt(3);
        game_IMG_pokeballs[0][i].setVisibility(View.VISIBLE);}
        send = !send;

    }

    private void moveDownUI() {
        if (pause == false) {
            for (int j = 0; j < COLS; j++) {
                game_IMG_pokeballs[ROWS][j].setVisibility(View.INVISIBLE);
            }
            for (int j = 0; j < COLS; j++) {
                for (int i = ROWS; i > 0; i--) {
                    if (game_IMG_pokeballs[i - 1][j].getVisibility() == View.VISIBLE) {
                        game_IMG_pokeballs[i - 1][j].setVisibility(View.INVISIBLE);
                        game_IMG_pokeballs[i][j].setVisibility(View.VISIBLE);
                    }
                }
            }
            checkHit();
        }
    }


    private void startTimer() {
        handler.postDelayed(runnable1, DELAY1);
    }

    private void stopTimer() {
        handler.removeCallbacks(runnable1);
    }
}
package com.example.game_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;

import android.content.Context;
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
    private final int DELAY1 = 800;
    //private final int DELAY2 = 800;
    private AppCompatImageView game_IMG_back;

    private AppCompatImageView[] game_IMG_hearts;
    private AppCompatImageView[][] game_IMG_pokeballs;
    private AppCompatImageView[] game_IMG_pikachus;
    private MaterialButton game_BTN_right;
    private MaterialButton game_BTN_left;
    private int lives = 3;
    private MediaActionSound sound = new MediaActionSound();

    private boolean visiblePikachu[] = {false, true, false};
    private boolean visiblePokeball[][] = {
            {false, false, false},
            {false, false, false},
            {false, false, false},
            {false, false, false},
            {false, false, false},
            {false, false, false}};

    private enum direction {right, left}

    private boolean pause = false;
    private Random rn = new Random();


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
//        Glide
//                .with(MainActivity.this)
//                .load("https://freedesignfile.com/upload/2020/03/Farm-vector.jpg")
//                .into(game_IMG_back);
//        Glide
//                .with(MainActivity.this)
//                .load(R.drawable.img_back)
//                .into(game_IMG_back);
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
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 6; j++) {
                game_IMG_pokeballs[j][i].setVisibility(View.INVISIBLE);
            }
        }
        game_IMG_pikachus[0].setVisibility(View.INVISIBLE);
        game_IMG_pikachus[2].setVisibility(View.INVISIBLE);
        game_IMG_pikachus[1].setVisibility(View.VISIBLE);
    }

    private void moveRight() {
        if (!visiblePikachu[2]) {
            if (visiblePikachu[0] == true) {
                visiblePikachu[0] = false;
                visiblePikachu[1] = true;
                updateLocationUI(1, direction.right);
            } else {
                visiblePikachu[1] = false;
                visiblePikachu[2] = true;
                updateLocationUI(2, direction.right);
            }
        }
    }


    private void moveLeft() {
        if (visiblePikachu[0] == false) {
            if (visiblePikachu[1] == true) {
                visiblePikachu[1] = false;
                visiblePikachu[0] = true;
                updateLocationUI(0, direction.left);
            } else {
                visiblePikachu[2] = false;
                visiblePikachu[1] = true;
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
        Toast.makeText(this, "Game Over, starting again", Toast.LENGTH_SHORT).show();
        lives = 3;
    }

    private Handler handler = new Handler();
    private Runnable runnable1 = new Runnable() {
        public void run() {
            handler.postDelayed(runnable1, DELAY1);
            updateUI();
            //  handler.postDelayed(runnable, DELAY2);
        }
    };
//    private Runnable runnable2 = new Runnable() {
//        public void run() {
//            handler.postDelayed(runnable2, DELAY2);
//            sendPokeballs();
//            //  handler.postDelayed(runnable, DELAY2);
//        }
//    };

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

//    private void checkHit(int location) {
//        if (visiblePokeball[5][location]) {
//            //  vibrate();
//            reduceLives();
//            updateLives();
//            Toast.makeText(this, "CRASH", Toast.LENGTH_SHORT).show();
//
//        }
//    }

    private void checkHit() {
        for (int i = 0; i < 3; i++) {
            if (visiblePokeball[5][i] && visiblePikachu[i]) {
                reduceLives();
                vibrate();
                updateLives();
            //    sound.play(MediaActionSound.STOP_VIDEO_RECORDING);
                Toast.makeText(this, "CRASH", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void vibrate() {
         Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(10);
        }
    }

    private void sendPokeballs() {
        int i = rn.nextInt(3);
        visiblePokeball[0][i] = true;
        game_IMG_pokeballs[0][i].setVisibility(View.VISIBLE);

    }

    private void moveDownUI() {
        if (pause == false) {
            for (int j = 0; j < 3; j++) {
                visiblePokeball[5][j] = false;
                game_IMG_pokeballs[5][j].setVisibility(View.INVISIBLE);
            }
            for (int j = 0; j < 3; j++) {
                for (int i = 5; i > 0; i--) {
                    if (visiblePokeball[i - 1][j]) {
                        visiblePokeball[i - 1][j] = false;
                        visiblePokeball[i][j] = true;
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
        //    handler.postDelayed(runnable2, DELAY2);

    }

    private void stopTimer() {
        //    handler.removeCallbacks(runnable2);
        handler.removeCallbacks(runnable1);
    }
}
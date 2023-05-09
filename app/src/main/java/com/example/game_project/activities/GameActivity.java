package com.example.game_project.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.example.game_project.R;
import com.example.game_project.data.GameManager;
import com.example.game_project.data.Item;
import com.example.game_project.init.MyGPS;
import com.example.game_project.init.MySignal;
import com.example.game_project.utilities.SensorDetector;
import com.example.game_project.utilities.TypeItem;
import com.example.game_project.utilities.TypeVisibility;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {
    // todo change app logo
    private AppCompatImageView game_IMG_back; //
    private ArrayList<AppCompatImageView> game_IMG_hearts; //
    private ArrayList<ArrayList<AppCompatImageView>> game_IMG_pokeballs; //
    private ArrayList<AppCompatImageView> game_IMG_pikachus; //
    private MaterialButton game_BTN_right; //
    private MaterialButton game_BTN_left; //
    private final int NUM_OF_HEARTS = 3;
    private int lives = 3; //
    private Timer timerUpdateUI;
    private boolean isActiveController = true;

    private final int ROWS = 8, COLS = 5; //
    private GameManager gameManager; //
    private boolean pause = false;
    private SensorDetector sensorDetector;
    private TextView game_LBL_score;
    private TextView game_LBL_scoreNum;
    private int roundsToPokemon = 0; //


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        findViews();
        init();
        initView();
        updateUI();
    }

    private void doMove(String direction) {
        if (isActiveController) {
            gameManager.move(direction);
            renderpikas();
        }
    }

    public void updateUI() {
        if (gameManager.isBallCollision()) {
            renderCollision("boom", gameManager.getSTRING_LOST_1_LIFE(), true);
        } else if (gameManager.isPokemonCollision()) {
            renderCollision("success", gameManager.getSTRING_PLUS_10_COINS(), false);
        }
        renderScore();
        renderPokeballsTable();
        gameManager.setBallCollision(false);
        gameManager.setPokemonCollision(false);
    }

    private void makeSound(String type) {
        int musicId = 0;
        switch (type) {
            case "boom":
                musicId = R.raw.pika;
                break;
            case "success":
                musicId = R.raw.success;
                break;
        }
        if (musicId != 0) {
            MediaPlayer player = MediaPlayer.create(GameActivity.this, musicId);
            player.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopTimer();
        pause = true;
        if (gameManager.isSensorMode()) sensorDetector.stop();
    }


    protected void onStop() {
        super.onStop();
        //  stopTimer();
        MyGPS.getInstance().stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pause = false;
        startTimer();
        if (gameManager.isSensorMode()) {
            sensorDetector.start();
        }
        MyGPS.getInstance().start();
    }

    private void startTimer() {
        timerUpdateUI = new Timer();
        TimerTask callback_updateBalls = new TimerTask() {
            @Override
            public void run() {
                boolean insertPokemon;
                if (roundsToPokemon == 0) {
                    insertPokemon = true;
                    roundsToPokemon = 3;
                } else {
                    insertPokemon = false;
                    roundsToPokemon--;
                }
                runOnUiThread(() -> updateUIonTime(insertPokemon));
            }
        };
        timerUpdateUI.scheduleAtFixedRate(callback_updateBalls, gameManager.getDELAY(), gameManager.getDELAY());
    }


    private void initButtons() {
        game_BTN_left.setOnClickListener(v -> doMove("left"));
        game_BTN_right.setOnClickListener(v -> doMove("right"));
    }


    public void updateUIonTime(boolean insertPokemon) {
        gameManager.updateTable(insertPokemon);
        if (gameManager.isGameOver()) {
            updateUIGameOver();
        } else {
            updateUI();
        }
    }

    private void updateUIGameOver() {
        renderGameOver();
        isActiveController = false;
        stopTimer();
    }


    public void renderpikas() {
        ArrayList<Item> pikaItems = gameManager.getPikaItems();
        for (int i = 0; i < COLS; i++) {
            AppCompatImageView game_IMG_pika = game_IMG_pikachus.get(i);
            TypeVisibility typeVisibility = pikaItems.get(i).getTypeVisibility();
            game_IMG_pika.setVisibility(typeVisibility == TypeVisibility.VISIBLE ? View.VISIBLE : View.INVISIBLE);
        }
    }

    public void renderHearts() {
        ArrayList<Item> heartItems = gameManager.getHeartItems();
        for (int i = 0; i < NUM_OF_HEARTS; i++) {
            AppCompatImageView game_IMG_heart = game_IMG_hearts.get(i);
            TypeVisibility typeVisibility = heartItems.get(i).getTypeVisibility();
            game_IMG_heart.setVisibility(typeVisibility == TypeVisibility.VISIBLE ? View.VISIBLE : View.INVISIBLE);
        }
    }


    private void renderCollision(String musicType, String toastMassage, boolean doVibrate) {
        renderHearts();
        renderToast(toastMassage);
        if (doVibrate) vibrate();
        makeSound(musicType);
    }

    private void renderScore() {
        game_LBL_scoreNum.setText(Integer.toString((gameManager.getScore())));
    }

    private void initControls() {
        if (gameManager.isButtonMode()) initButtons();
        else initSensor();
    }

    private void initSensor() {
        sensorDetector = new SensorDetector(this, callback_movement);
    }

    private final SensorDetector.CallBack_Movement callback_movement = new SensorDetector.CallBack_Movement() {
        @Override
        public void moveRight() {
            doMove("right");
        }

        @Override
        public void moveLeft() {
            doMove("left");
        }
    };

    private void initGameManager() {
        gameManager = gameManager.getInstance()
                .setNumOfHearts(NUM_OF_HEARTS)
                .setNumOfRows(ROWS)
                .setNumOfColumns(COLS);
        gameManager.initItems(game_IMG_hearts, "hearts");
        gameManager.initItems(game_IMG_pikachus, "pikachu");
        int imagePokemon = R.drawable.img_jig;
        gameManager.initPokeballsMatrix(game_IMG_pokeballs, imagePokemon);
    }

    private void init() {
        initGameManager();
        initControls();
        initBackground();
    }


    private void renderGameOver() {
        renderHearts();
        renderScore();
        renderPokeballsTable();
        gameOver();
    }

    private void renderPokeballsTable() {
        ArrayList<ArrayList<Item>> matrixBallsItems = gameManager.getMatrixItems();
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Item pokeball = matrixBallsItems.get(row).get(col);
                TypeVisibility typeVisibility = pokeball.getTypeVisibility();
                AppCompatImageView imgPokeball = game_IMG_pokeballs.get(row).get(col);
                if (typeVisibility == TypeVisibility.VISIBLE) {
                    int imageResource;
                    if (pokeball.getTypeItem() == TypeItem.POKEBALL)
                        imageResource = R.drawable.ic_pokeball;
                    else imageResource = R.drawable.img_jig;
                    imgPokeball.setImageResource(imageResource);
                    imgPokeball.setVisibility(View.VISIBLE);
                } else {
                    imgPokeball.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    private void initView() {
        renderHearts();
        renderPokeballsTable();
        renderpikas();
    }

    private void initBackground() {
        Glide
                .with(GameActivity.this)
                .load("https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/2bc72d3c-39ce-45f3-a55c-e0903291ace0/d17dpy4-a8d0b94a-0351-4d9b-9636-33cc1842f0d2.jpg/v1/fill/w_600,h_486,q_75,strp/background_art_for_pokemon_by_orangedroplet_d17dpy4-fullview.jpg?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7ImhlaWdodCI6Ijw9NDg2IiwicGF0aCI6IlwvZlwvMmJjNzJkM2MtMzljZS00NWYzLWE1NWMtZTA5MDMyOTFhY2UwXC9kMTdkcHk0LWE4ZDBiOTRhLTAzNTEtNGQ5Yi05NjM2LTMzY2MxODQyZjBkMi5qcGciLCJ3aWR0aCI6Ijw9NjAwIn1dXSwiYXVkIjpbInVybjpzZXJ2aWNlOmltYWdlLm9wZXJhdGlvbnMiXX0.Xa7d7pbU0rU7MNWAomQrAPKjMwYxdB82ALLKMZO_pOs")
                .into(game_IMG_back);
    }

    private void stopTimer() {
        timerUpdateUI.cancel();
    }

    private void findButtons() {
        game_BTN_left = findViewById(R.id.game_BTN_left);
        game_BTN_right = findViewById(R.id.game_BTN_right);
        game_BTN_left.setVisibility(View.VISIBLE);
        game_BTN_right.setVisibility(View.VISIBLE);
    }

    private void findBackground() {
        game_IMG_back = findViewById(R.id.game_IMG_back);
    }

    private void findControl() {
        if (gameManager.getInstance().isButtonMode())
        {
            findButtons();}
    }

    private void findPikachus() {
        game_IMG_pikachus = new ArrayList<>(COLS);
        game_IMG_pikachus.add(findViewById(R.id.game_IMG_pika1));
        game_IMG_pikachus.add(findViewById(R.id.game_IMG_pika2));
        game_IMG_pikachus.add(findViewById(R.id.game_IMG_pika3));
        game_IMG_pikachus.add(findViewById(R.id.game_IMG_pika4));
        game_IMG_pikachus.add(findViewById(R.id.game_IMG_pika5));

    }

    private void findPokeballs() {
        game_IMG_pokeballs = new ArrayList<>(ROWS);
        for (int i = 0; i < ROWS; i++) {
            game_IMG_pokeballs.add(new ArrayList<>(COLS));
            for (int j = 0; j < COLS; j++) {
                int currentPlace = i * COLS + j + 1;
                int ballID = getResources().getIdentifier("game_IMG_ball" + currentPlace, "id", getPackageName());
                AppCompatImageView currentBall = findViewById(ballID);
                game_IMG_pokeballs.get(i).add(currentBall);
            }
        }
    }

    private void findScore() {
        game_LBL_score = findViewById(R.id.game_LBL_score);
        game_LBL_scoreNum = findViewById(R.id.game_LBL_score_num);
    }

    private void findHearts() {
        game_IMG_hearts = new ArrayList<>(NUM_OF_HEARTS);
        for (int i = 1; i <= NUM_OF_HEARTS; i++) {
            int heartID = getResources().getIdentifier("game_IMG_heart" + i, "id", getPackageName());
            AppCompatImageView currentHeart = findViewById(heartID);
            game_IMG_hearts.add(currentHeart);
        }
    }

    // todo on create
    private void findViews() {
        findHearts();
        findScore();
        findBackground();
        findControl();
        findPikachus();
        findPokeballs();
    }


    private void gameOver() {
        //stopTimer();
        pause = true;
        Intent intent = new Intent(GameActivity.this, GameOverActivity.class);
        startActivity(intent);
        gameManager.gameOver();
        finish();


    }

    private void vibrate() {
        MySignal.getInstance().vibrate();
    }


    public void renderToast(String msg) {
        MySignal.getInstance().toast(msg);
    }


}
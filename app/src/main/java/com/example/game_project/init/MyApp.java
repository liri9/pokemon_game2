package com.example.game_project.init;

import android.app.Application;

import com.example.game_project.data.GameManager;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        MySignal.init(this);
        MySP.init(this);
        MyGPS.init(this);
        GameManager.init();
    }
}

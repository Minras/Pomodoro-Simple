package com.minras.android.pomodorosimple;

import android.app.Application;
import android.content.Context;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Config.getInstance()
                .setStorage(getSharedPreferences(Config.STORAGE_NAME, Context.MODE_PRIVATE));
    }
}

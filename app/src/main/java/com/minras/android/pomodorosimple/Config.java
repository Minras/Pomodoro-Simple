package com.minras.android.pomodorosimple;

import android.content.SharedPreferences;

public class Config {

    public static final String STORAGE_NAME = "com.minras.android.pomodorosimple.STORAGE";
    // public final static String APP_LOG_TAG = "com.minras.android.pomodorosimple.LOG";

    private final static String STORAGE_KEY_DURATION_WORK = "duration.work";
    private final static int DEFAULT_DURATION_WORK = 25;
    private final static String STORAGE_KEY_DURATION_SHORTBREAK = "duration.shortbreak";
    private final static int DEFAULT_DURATION_SHORTBREAK = 5;
    private final static String STORAGE_KEY_DURATION_LONGBREAK = "duration.longbreak";
    private final static int DEFAULT_DURATION_LONGBREAK = 15;

    private static Config instance = null;
    private SharedPreferences storage;

    protected Config() {}

    public static Config getInstance() {
        if(instance == null) {
            instance = new Config();
        }
        return instance;
    }

    public SharedPreferences getStorage() {
        return storage;
    }

    public Config setStorage(SharedPreferences storage) {
        this.storage = storage;
        return this;
    }

    public void restoreDefaults() {
        setDurationWork(DEFAULT_DURATION_WORK);
        setDurationShortBreak(DEFAULT_DURATION_SHORTBREAK);
        setDurationLongBreak(DEFAULT_DURATION_LONGBREAK);
    }

    public int getDurationWork() {
        return storage.getInt(STORAGE_KEY_DURATION_WORK, DEFAULT_DURATION_WORK);
    }

    public void setDurationWork(int duration) {
        storage.edit().putInt(STORAGE_KEY_DURATION_WORK, duration).apply();
    }

    public int getDurationShortBreak() {
        return storage.getInt(STORAGE_KEY_DURATION_SHORTBREAK, DEFAULT_DURATION_SHORTBREAK);
    }

    public void setDurationShortBreak(int duration) {
        storage.edit().putInt(STORAGE_KEY_DURATION_SHORTBREAK, duration).apply();
    }

    public int getDurationLongBreak() {
        return storage.getInt(STORAGE_KEY_DURATION_LONGBREAK, DEFAULT_DURATION_LONGBREAK);
    }

    public void setDurationLongBreak(int duration) {
        storage.edit().putInt(STORAGE_KEY_DURATION_LONGBREAK, duration).apply();
    }
}

package com.minras.android.pomodorosimple.util;

import android.os.CountDownTimer;

import java.util.ArrayList;

public class Pomodoro {
    private static Pomodoro instance = new Pomodoro();

    ArrayList<PomodoroListener> listeners = new ArrayList<> ();

    private static int TICK_MS = 1000;
    CountDownTimer timer;
    private boolean isCounting = false;

    // Current timer duration in milliseconds
    private long currentDuration = 0;

    public static Pomodoro getInstance() {
        return instance;
    }

    private Pomodoro() {
    }

    public long getCurrentDuration() {
        return currentDuration;
    }
    private void setCurrentDuration(int seconds) {
        currentDuration = seconds * 60 * 1000;
    }

    public void stopTimer() {
        timer.cancel();
        setCurrentDuration(Config.getInstance().getDurationWork());
    }

    public void startTimer() {
        int durationWork = Config.getInstance().getDurationWork() * 60 * 1000;
        timer = new CountDownTimer(durationWork, TICK_MS) {
            public void onTick(long millisUntilFinished) {
                fireOnTimerUpdate(millisUntilFinished);
                isCounting = true;
            }

            public void onFinish() {
                isCounting = false;
                fireOnTimerStop();
            }
        }.start();
    }

    public interface PomodoroListener
    {
        void onTimerUpdate(long millisUntilFinished);
        void onTimerStop();
    }
    public void addPomodoroListener (PomodoroListener listener)
    {
        this.listeners.add(listener);
    }
    public void removePomodoroListener (PomodoroListener listener)
    {
        this.listeners.remove(listener);
    }
    private void fireOnTimerUpdate(long millisUntilFinished) {
        for (int i=0; i<listeners.size(); i++) {
            listeners.get(i).onTimerUpdate(millisUntilFinished);
        }
    }
    private void fireOnTimerStop() {
        for (int i=0; i<listeners.size(); i++) {
            listeners.get(i).onTimerStop();
        }
    }
}

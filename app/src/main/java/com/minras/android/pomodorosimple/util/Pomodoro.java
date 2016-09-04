package com.minras.android.pomodorosimple.util;

import android.os.CountDownTimer;

import java.util.ArrayList;

public class Pomodoro {
    private static final int STATUS_WORK = 0;
    private static final int STATUS_SHORTBREAK = 1;
    private static final int STATUS_LONGBREAK = 2;

    private int status = STATUS_WORK;

    private static Pomodoro instance = new Pomodoro();

    ArrayList<PomodoroListener> listeners = new ArrayList<> ();

    private static int TICK_MS = 1000;
    CountDownTimer timer;

    private boolean isCounting = false;
    // Current timer duration in milliseconds
    private long currentFullDuration = 0;

    public static Pomodoro getInstance() {
        return instance;
    }

    private Pomodoro() {
    }

    public long getCurrentFullDuration() {
        return currentFullDuration;
    }
    private void setCurrentFullDuration(int seconds) {
        currentFullDuration = seconds * 60 * 1000;
    }
    private void updateCurrentFullDuration() {
        switch (getStatus()) {
            case STATUS_SHORTBREAK:
                setCurrentFullDuration(Config.getInstance().getDurationShortBreak());
                break;
            case STATUS_WORK:
            default:
                setCurrentFullDuration(Config.getInstance().getDurationWork());
                break;
        }
    }

    public void stopTimer() {
        timer.cancel();
        isCounting = false;
        setStatus(STATUS_WORK);
        updateCurrentFullDuration();
    }

    public void startTimer() {
        timer = new CountDownTimer(getCurrentFullDuration(), TICK_MS) {
            public void onTick(long millisUntilFinished) {
                fireOnTimerUpdate(millisUntilFinished);
            }

            public void onFinish() {
                setNextStatus();
                fireOnTimerStop();
            }
        }.start();
        isCounting = true;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setNextStatus() {
        switch (getStatus()) {
            case STATUS_WORK:
                setStatus(STATUS_SHORTBREAK);
                break;
            case STATUS_SHORTBREAK:
            default:
                setStatus(STATUS_WORK);
                break;
        }
        updateCurrentFullDuration();
    }

    public boolean isCounting() {
        return isCounting;
    }

    public interface PomodoroListener
    {
        void onTimerUpdate(long millisUntilFinished);
        void onTimerStop();
    }
    public void addPomodoroListener (PomodoroListener listener)
    {
        this.listeners.add(listener);
        updateCurrentFullDuration();
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

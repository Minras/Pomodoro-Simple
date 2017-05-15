package com.minras.android.pomodorosimple.util;

import android.os.CountDownTimer;

import java.util.ArrayList;

public class Pomodoro {
    public static final int STATUS_WORK = 0;
    public static final int STATUS_SHORTBREAK = 1;
    public static final int STATUS_LONGBREAK = 2;

    private int status = STATUS_WORK;
    private boolean statusChanged = false;

    private static Pomodoro instance = new Pomodoro();

    private ArrayList<PomodoroListener> listeners = new ArrayList<> ();

    private CountDownTimer timer;

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
    private void setCurrentFullDuration(int minutes) {
        currentFullDuration = minutes * 60 * 1000;
        // DEBUG
        // currentFullDuration = (minutes != 1 && minutes!=5) ? minutes * 60 * 1000 : 5000;
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
        if (null != timer) {
            timer.cancel();
        }
        timer = new CountDownTimer(getCurrentFullDuration(), 500) {
            public void onTick(long millisUntilFinished) {
                fireOnTimerUpdate(millisUntilFinished);
            }

            public void onFinish() {
                statusChanged = false;
                fireOnTimerStop();
                if (!statusChanged) {
                    setNextStatus();
                }
            }
        }.start();
        isCounting = true;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.statusChanged = true;
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
        statusChanged = true;
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

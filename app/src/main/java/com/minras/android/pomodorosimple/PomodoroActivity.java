package com.minras.android.pomodorosimple;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PomodoroActivity extends AppCompatActivity implements View.OnClickListener {
    private static int TICK_MS = 500;

    private Button actionButton;
    private TextView timerText;
    private PomodoroView timerImage;
    CountDownTimer timer;

    private boolean isCounting = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pomodoro);

        actionButton = (Button) findViewById(R.id.btn_action);
        actionButton.setOnClickListener(this);

        timerText = (TextView) findViewById(R.id.timer_text);
        timerImage = (PomodoroView) findViewById(R.id.timer_image);

        updateTimer(Config.getInstance().getDurationWork() * 60 * 1000);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pomodoro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                openSettingsPage();
                return true;
            case R.id.action_credits:
                openCreditsPage();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openSettingsPage() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void openCreditsPage() {
        Intent intent = new Intent(this, CreditsActivity.class);
        startActivity(intent);
    }

    // TODO move
    private void startTimer() {
        int durationWork = Config.getInstance().getDurationWork() * 60 * 1000;
        timer = new CountDownTimer(durationWork, TICK_MS) {
            public void onTick(long millisUntilFinished) {
                updateTimer(millisUntilFinished);
                isCounting = true;
            }

            public void onFinish() {
                isCounting = false;
                updateTimer(0);
            }
        }.start();
        updateTimer(durationWork - 1);
    }

    // TODO move
    private void stopTimer() {
        timer.cancel();
        updateTimer(Config.getInstance().getDurationWork() * 60 * 1000);
    }

    // TODO move
    private void updateTimer(long msUntilFinished) {
        int minutes = (int)(msUntilFinished / 60000);
        int seconds = (int)((msUntilFinished - minutes * 60000) / 1000);
        timerText.setText(String.format("%02d:%02d", minutes, seconds));

        timerImage.updateTimer(Config.getInstance().getDurationWork() * 60 * 1000, msUntilFinished);

        actionButton.setText(0 == msUntilFinished ||
                Config.getInstance().getDurationWork() * 60 * 1000 == msUntilFinished ?
                R.string.btn_text_start : R.string.btn_text_stop);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_action:
                if (isCounting) {
                    stopTimer();
                } else {
                    startTimer();
                }
                break;
        }
    }
}

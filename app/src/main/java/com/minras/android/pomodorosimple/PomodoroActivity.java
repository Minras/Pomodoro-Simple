package com.minras.android.pomodorosimple;

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
    private static int DURATION_WORK = 25 * 60 * 1000;
    private static int DURATION_BREAK = 5 * 60 * 1000;
    private static int TICK_MS = 500;

    private Button actionButton;
    private TextView timerText;
    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pomodoro);

        actionButton = (Button) findViewById(R.id.btn_action);
        actionButton.setText(R.string.btn_text_start);
        actionButton.setOnClickListener(this);

        timerText = (TextView) findViewById(R.id.timer_text);

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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startTimer() {
        timer = new CountDownTimer(DURATION_WORK, TICK_MS) {

            public void onTick(long millisUntilFinished) {
                int minutes = (int)(millisUntilFinished / 60000);
                int seconds = (int)((millisUntilFinished - minutes * 60000) / 1000);
                timerText.setText(String.format("%02d:%02d", minutes, seconds));
            }

            public void onFinish() {
                timerText.setText("00:00");
            }
        }.start();
    }

    private void stopTimer() {
        timer.cancel();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_action:
                startTimer();
                break;
        }
    }
}

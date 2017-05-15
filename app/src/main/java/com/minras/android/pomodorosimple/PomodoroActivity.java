package com.minras.android.pomodorosimple;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.minras.android.pomodorosimple.util.Pomodoro;

public class PomodoroActivity extends AppCompatActivity
        implements View.OnClickListener, Pomodoro.PomodoroListener {

    private Button btnStart;
    private Button btnStop;
    private TextView timerText;
    private PomodoroView timerImage;
    private AlertDialog.Builder dialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pomodoro);

        btnStart = (Button) findViewById(R.id.btn_start);
        btnStart.setOnClickListener(this);

        btnStop = (Button) findViewById(R.id.btn_stop);
        btnStop.setOnClickListener(this);

        timerText = (TextView) findViewById(R.id.timer_text);
        timerImage = (PomodoroView) findViewById(R.id.timer_image);

        // Instantiate an AlertDialog.Builder with its constructor
        dialogBuilder = new AlertDialog.Builder(this);

        Pomodoro.getInstance().addPomodoroListener(this);

        updateTimer(Pomodoro.getInstance().getCurrentFullDuration());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void gotoNextAction() {
        if (Pomodoro.STATUS_SHORTBREAK == Pomodoro.getInstance().getStatus()) {
            stopTimer(Pomodoro.STATUS_WORK);
            return;
        }
        dialogBuilder.setMessage(R.string.dialog_message)
                .setTitle(R.string.dialog_title)
                .setNegativeButton(R.string.btn_text_continue_work, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        stopTimer(Pomodoro.STATUS_WORK);
                    }
                })
                .setPositiveButton(R.string.btn_text_start_break, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startTimer(Pomodoro.STATUS_SHORTBREAK);
                    }
                });

        // Get the AlertDialog from create()
        dialogBuilder.create().show();
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

    private void startTimer(int status) {
        Pomodoro.getInstance().setStatus(status);
        startTimer();
    }
    private void startTimer() {
        Pomodoro.getInstance().startTimer();
        updateTimer(Pomodoro.getInstance().getCurrentFullDuration() - 1);
    }

    private void stopTimer(int status) {
        Pomodoro.getInstance().setStatus(status);
        stopTimer();
    }
    private void stopTimer() {
        Pomodoro.getInstance().stopTimer();
        updateTimer(Pomodoro.getInstance().getCurrentFullDuration());
    }

    // TODO move
    private void updateTimer(long msUntilFinished) {
        int minutes = (int)(msUntilFinished / 60000);
        int seconds = (int)((msUntilFinished - minutes * 60000) / 1000);
        timerText.setText(String.format("%02d:%02d", minutes, seconds));

        Pomodoro pomodoro = Pomodoro.getInstance();
        timerImage.updateTimer(pomodoro.getCurrentFullDuration(), msUntilFinished);

        btnStop.setVisibility(pomodoro.isCounting() ? View.VISIBLE : View.GONE);
        btnStart.setVisibility(pomodoro.isCounting() ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                startTimer();
                break;
            case R.id.btn_stop:
                stopTimer();
                break;
        }
    }

    @Override
    public void onTimerUpdate(long millisUntilFinished) {
        updateTimer(millisUntilFinished);
    }

    @Override
    public void onTimerStop() {
        updateTimer(0);
        gotoNextAction();
    }
}

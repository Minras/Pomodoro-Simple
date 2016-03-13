package com.minras.android.pomodorosimple;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.SeekBar;

public class SettingsActivity extends AppCompatActivity {

    SeekBar durationWork;
    SeekBar durationShortBreak;
    SeekBar durationLongBreak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupUi();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupUi() {
        durationWork = (SeekBar) findViewById(R.id.duration_work);
        durationWork.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }

                    ;
                }
        );

        durationShortBreak = (SeekBar) findViewById(R.id.duration_shortbreak);
        durationLongBreak = (SeekBar) findViewById(R.id.duration_longbreak);
    }
}

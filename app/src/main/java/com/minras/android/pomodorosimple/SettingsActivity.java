package com.minras.android.pomodorosimple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    SeekBar durationWork;
    TextView durationWorkText;

    SeekBar durationShortBreak;
    TextView durationShortBreakText;

    SeekBar durationLongBreak;
    TextView durationLongBreakText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupUi();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.settings_btn_reset:
                Config.getInstance().restoreDefaults();
                setDefaultUiValues();
                break;
        }
    }

    private void setDefaultUiValues() {
        durationWork.setProgress(Config.getInstance().getDurationWork() - 1);
        durationShortBreak.setProgress(Config.getInstance().getDurationShortBreak() - 1);
        durationLongBreak.setProgress(Config.getInstance().getDurationLongBreak() - 1);
    }

    private void updateDurationText(TextView view, int resource, Object... args) {
        view.setText(
                String.format(getResources().getString(resource), args)
        );
    }

    private void setupUi() {
        durationWorkText = (TextView) findViewById(R.id.duration_work_text);
        updateDurationText(
                durationWorkText,
                R.string.header_settings_work_duration,
                Config.getInstance().getDurationWork());
        durationWork = (SeekBar) findViewById(R.id.duration_work);
        durationWork.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progress++;
                        Config.getInstance().setDurationWork(progress);
                        updateDurationText(durationWorkText, R.string.header_settings_work_duration, progress);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                }
        );

        durationShortBreakText = (TextView) findViewById(R.id.duration_shortbreak_text);
        updateDurationText(
                durationShortBreakText,
                R.string.header_settings_shortbreak_duration,
                Config.getInstance().getDurationShortBreak());
        durationShortBreak = (SeekBar) findViewById(R.id.duration_shortbreak);
        durationShortBreak.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progress++;
                        Config.getInstance().setDurationShortBreak(progress);
                        updateDurationText(
                                durationShortBreakText,
                                R.string.header_settings_shortbreak_duration,
                                Config.getInstance().getDurationShortBreak());
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                }
        );

        durationLongBreakText = (TextView) findViewById(R.id.duration_longbreak_text);
        updateDurationText(
                durationLongBreakText,
                R.string.header_settings_longbreak_duration,
                Config.getInstance().getDurationLongBreak());
        durationLongBreak = (SeekBar) findViewById(R.id.duration_longbreak);
        durationLongBreak.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progress++;
                        Config.getInstance().setDurationLongBreak(progress);
                        updateDurationText(
                                durationLongBreakText,
                                R.string.header_settings_longbreak_duration,
                                Config.getInstance().getDurationLongBreak());
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                }
        );

        Button btnReset = (Button) findViewById(R.id.settings_btn_reset);
        btnReset.setOnClickListener(this);

        setDefaultUiValues();
    }
}

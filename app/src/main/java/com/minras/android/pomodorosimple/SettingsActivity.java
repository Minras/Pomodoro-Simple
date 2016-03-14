package com.minras.android.pomodorosimple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.SeekBar;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

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

    private void updateText(TextView view, int resource, Object... args) {
        view.setText(
                String.format(getResources().getString(resource), args)
        );
    }

    private void setupUi() {
        durationWorkText = (TextView) findViewById(R.id.duration_work_text);
        updateText(durationWorkText, R.string.header_settings_work_duration, Config.getInstance().getDurationWork());
        durationWork = (SeekBar) findViewById(R.id.duration_work);
        durationWork.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progress++;
                        Config.getInstance().setDurationWork(progress);
                        updateText(durationWorkText, R.string.header_settings_work_duration, progress);
                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}
                }
        );

        durationShortBreak = (SeekBar) findViewById(R.id.duration_shortbreak);
        durationLongBreak = (SeekBar) findViewById(R.id.duration_longbreak);
    }
}

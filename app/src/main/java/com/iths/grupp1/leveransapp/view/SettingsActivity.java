package com.iths.grupp1.leveransapp.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.iths.grupp1.leveransapp.R;

public class SettingsActivity extends AppCompatActivity {
    private SeekBar seekBar;
    private TextView ordersPerPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        seekBar();
    }

    public void seekBar() {
        seekBar = (SeekBar) findViewById(R.id.numOfOrdersSeekBar);
        ordersPerPage = (TextView) findViewById(R.id.numOfOrders);
        ordersPerPage.setText(String.valueOf(seekBar.getProgress()));

        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int progressValue;
                    int minValue = 1;

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progressValue = progress;
                        ordersPerPage.setText(String.valueOf(progressValue));
                        if(progressValue == 0) {
                            ordersPerPage.setText(String.valueOf(minValue));
                        }
                    }
                    
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        if(progressValue != 0) {
                            ordersPerPage.setText(String.valueOf(progressValue));
                        }
                    }
                }
        );
    }
}

package com.iths.grupp1.leveransapp.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.iths.grupp1.leveransapp.R;

public class SettingsActivity extends AppCompatActivity {
    private SeekBar seekBar;
    private TextView ordersPerPage;
    private EditText changePhonenumber;
    private Button changePhoneNumberBtn;
    private String phoneNumber;
    //private Intent ordersPerPageIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        changePhonenumber = (EditText) findViewById(R.id.phoneNumber);
        changePhoneNumberBtn = (Button) findViewById(R.id.changeNumberBtn);

        changePhoneNumberBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                phoneNumber = changePhonenumber.getText().toString();
                showAlert(this);
            }
        });

        seekBar();
    }

    public void seekBar() {
        seekBar = (SeekBar) findViewById(R.id.numOfOrdersSeekBar);
        ordersPerPage = (TextView) findViewById(R.id.numOfOrders);
        ordersPerPage.setText(String.valueOf(seekBar.getProgress()));

        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int progressValue;

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (progress < 1) {
                            seekBar.setProgress(1); // magic solution, ha
                        }
                        progressValue = progress+1;
                        ordersPerPage.setText(String.valueOf(progressValue));

                    }
                    
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                }
        );
    }

    public void showAlert(View.OnClickListener v) {
        AlertDialog.Builder alertNr = new AlertDialog.Builder(this);
        alertNr.setMessage("Skriv in det nya numret")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        alertNr.show();
    }
}

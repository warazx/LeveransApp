package com.iths.grupp1.leveransapp.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import com.iths.grupp1.leveransapp.R;

public class SettingsActivity extends AppCompatActivity {
    private SeekBar seekBar;
    private TextView ordersPerPage;
    private EditText changePhonenumber;
    private Button changePhoneNumberBtn;
    private String phoneNumber = "";
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
                if(phoneNumber.equals(changePhonenumber.getText().toString())||changePhonenumber.getText().equals("")) {
                    showAlert(this, "Skriv in ditt nya nummer");
                }
                else {
                    phoneNumber = changePhonenumber.getText().toString();
                    saveInfo(view);
                    changePhoneNumberBtn.setEnabled(true);

                    //changePhonenumber.clearFocus();
                    changePhoneNumberBtn.requestFocus();
                }


                /*if(changePhonenumber.getText().length() != 10) {
                    showAlert(this, "Numret måste innehålla 10 siffror");
                }
                else if (changePhonenumber.getText().charAt(0) == '+') {
                    showAlert(this, "Plus");
                }
                else  {
                    phoneNumber = changePhonenumber.getText().toString();
                    saveInfo(view);
                }*/

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
                            seekBar.setProgress(1);
                        }
                        progressValue = progress+1;
                        ordersPerPage.setText(String.valueOf(progressValue));

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        saveInfo(seekBar);
                    }
                }
        );
    }

    public void validatePhoneNumber() {

    }

    public void showAlert(View.OnClickListener v, String errorMessage) {
        AlertDialog.Builder alertNr = new AlertDialog.Builder(this);
        alertNr.setMessage(errorMessage)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        alertNr.show();
    }

    public void saveInfo(View v) {
        SharedPreferences sharedPref = getSharedPreferences("userSettings", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("ordersPerPage", ordersPerPage.getText().toString());
        editor.putString("phoneNumber", changePhonenumber.getText().toString());

        editor.apply();
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
    }

    public void displayData(View view) {
        SharedPreferences sharedPref = getSharedPreferences("userSettings", Context.MODE_PRIVATE);

        String orders = sharedPref.getString("ordersPerPage", "");
        String number = sharedPref.getString("phoneNumber", "");
    }

}

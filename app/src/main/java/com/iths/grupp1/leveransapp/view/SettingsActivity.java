package com.iths.grupp1.leveransapp.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.iths.grupp1.leveransapp.R;
import com.iths.grupp1.leveransapp.model.Session;

public class SettingsActivity extends AppCompatActivity {
    public static final String STATUS_USER_SETTINGS = "STATUS_USER_SETTINGS";
    public static final String ORDERS_TO_ADD = "ORDERS_TO_ADD";
    public static final String PHONE_NUMBER_TO_ADD = "PHONE_NUMBER_TO_ADD";
    public static final String DEFAULT_ORDERS = "10";

    private TextView ordersPerPage;
    private EditText changePhoneNumber;
    private String phoneNumber = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        changePhoneNumber = (EditText) findViewById(R.id.activity_settings_phone_number_setting_value);
        Button changePhoneNumberBtn = (Button) findViewById(R.id.activity_settings_change_number_btn);

        changePhoneNumberBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(phoneNumber.equals(changePhoneNumber.getText().toString())) {
                    showAlert(getString(R.string.activity_settings_add_number_label));
                }

                else {
                    phoneNumber = changePhoneNumber.getText().toString();
                    saveInfo();
                }
            }
        });
        seekBar();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (! Session.isSessionValid(this)) {
            activityLogOut();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.actionbar_logout_item:
                activityLogOut();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /* Handles the seekbar and changes values in ordersPerPage */
    private void seekBar() {
        SeekBar seekBar = (SeekBar) findViewById(R.id.activity_settings_seekbar);
        ordersPerPage = (TextView) findViewById(R.id.activity_settings_orders_setting_text_value);
        //ordersPerPage.setText(String.valueOf(seekBar.getProgress()));
        ordersPerPage.setText("10");

        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int progressValue;

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progressValue = progress+10;
                        ordersPerPage.setText(String.valueOf(progressValue));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        saveInfo();
                    }
                }
        );
    }

    /* Shows an alert with a message got from param */
    private void showAlert(String errorMessage) {
        AlertDialog.Builder alertNr = new AlertDialog.Builder(this);
        alertNr.setMessage(errorMessage)
                .setPositiveButton(R.string.activity_settings_alert_btn_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        alertNr.show();
    }

    /* Saves userinfo as SharedPreference */
    private void saveInfo() {
        SharedPreferences sharedPref = getSharedPreferences(STATUS_USER_SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(ORDERS_TO_ADD, ordersPerPage.getText().toString());
        editor.putString(PHONE_NUMBER_TO_ADD, changePhoneNumber.getText().toString());
        editor.apply();

        Toast.makeText(this, "Sparat", Toast.LENGTH_SHORT).show();
    }

    private void activityLogOut() {
        Session.closeSession(this);
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
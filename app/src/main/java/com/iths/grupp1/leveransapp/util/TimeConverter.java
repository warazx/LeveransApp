package com.iths.grupp1.leveransapp.util;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by christiankarlsson on 14/11/16.
 * Edited by Kivanc 28/11/16
 */

class TimeConverter extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Calendar c = Calendar.getInstance();
        System.out.println("Delivered => "+c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        Toast.makeText(this, formattedDate, Toast.LENGTH_SHORT).show();


        TextView deliverDate = new TextView(this);
        deliverDate.setText("Delivered : "+formattedDate);
        deliverDate.setGravity(Gravity.CENTER);
        deliverDate.setTextSize(20);
        setContentView(deliverDate);
    }




}

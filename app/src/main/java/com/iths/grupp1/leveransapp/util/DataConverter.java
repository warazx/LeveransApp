package com.iths.grupp1.leveransapp.util;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public final class DataConverter {
    public static String longToDateString(long time) {
        return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(time));
    }
}

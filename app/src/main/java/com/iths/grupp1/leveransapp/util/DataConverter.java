package com.iths.grupp1.leveransapp.util;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

/**
 * Helper class to convert different kinds of data.
 */
public final class DataConverter {
    /**
     * Takes a long time value and converts it to show the formatted date.
     * @param time long value to be converted.
     * @return formatted String in "yyyy-MM-dd HH:mm" format.
     */
    public static String longToDateString(long time) {
        return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(time));
    }
}

package com.iths.grupp1.leveransapp.util;

import android.location.Location;
import android.util.Log;

/**
 * Helper class to remember the last known position.
 */
public final class GpsTracker {
    public static Location lastLocation;

    public static Location getLastLocation() {
        return lastLocation;
    }

    public static void setLastLocation(Location lastLocation) {
        Log.d("GpsTracker", String.format("LastLocation: %f, %f", lastLocation.getLatitude(), lastLocation.getLongitude()));
        GpsTracker.lastLocation = lastLocation;
    }
}


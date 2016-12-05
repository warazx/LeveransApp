package com.iths.grupp1.leveransapp.util;

import android.location.Location;

public final class GpsTracker {
    public static Location lastLocation;

    public static Location getLastLocation() {
        return lastLocation;
    }

    public static void setLastLocation(Location lastLocation) {
        GpsTracker.lastLocation = lastLocation;
    }
}


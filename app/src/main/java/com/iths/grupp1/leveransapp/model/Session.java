package com.iths.grupp1.leveransapp.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Handles User Login Session
 */
public abstract class Session {

    private static final String LOG = "session";

    private static final String STATUS_SESSION = "SESSION";
    private static final String SESSION_ACTIVE = "ACTIVE";
    private static final String SESSION_TIME = "TIME";

    private static final long SESSION_LENGTH = 30;

    /**
     * Starts a new valid login session for application.
     * @param context the current context/activity.
     */
    public static void newSession(Context context) {
        SharedPreferences session = context.getSharedPreferences(STATUS_SESSION, Context.MODE_PRIVATE);
        SharedPreferences.Editor sessionEditor = session.edit();

        sessionEditor.putBoolean(SESSION_ACTIVE,true);
        sessionEditor.putLong(SESSION_TIME,System.currentTimeMillis());
        sessionEditor.commit();

        Log.d(LOG,"New Session");
    }

    /**
     * Ends the login session for application
     * @param context the current context/activity.
     */
    public static void closeSession(Context context) {
        SharedPreferences session = context.getSharedPreferences(STATUS_SESSION, Context.MODE_PRIVATE);
        SharedPreferences.Editor sessionEditor = session.edit();

        sessionEditor.putBoolean(SESSION_ACTIVE,false);
        sessionEditor.putLong(SESSION_TIME,0);
        sessionEditor.commit();

        Log.d(LOG,"Closing Session");
    }

    /**
     * Checks if there is a valid login session active and restarts it if true.
     * @param context the current context/activity.
     * @return true if a valid login session is active, else false.
     */
    public static boolean isSessionValid(Context context) {
        SharedPreferences session = context.getSharedPreferences(STATUS_SESSION, Context.MODE_PRIVATE);
        boolean isSessionActive = session.getBoolean(SESSION_ACTIVE,false);

        if (! isSessionActive) {
            Log.d(LOG,"No session active.");
            return false;
        } else {
            long timeStored = session.getLong(SESSION_TIME, 0);
            long timeDifference = System.currentTimeMillis() - timeStored;
            long timeMinutes = (long) Math.floor((Math.max(1, timeDifference) / 1000) / 60);
            Log.d(LOG, "Minutes since last session: " + timeMinutes);

            if (timeMinutes < SESSION_LENGTH) {
                Log.d(LOG, "Session within allowed timeframe.");
                newSession(context);
                return true;
            } else {
                Log.d(LOG, "Too much time has passed.");
                return false;
            }
        }

    }

}

package com.example.sep4_and.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
    public static final String PREFS_NAME = "auth";
    private static final String FIRST_LAUNCH_KEY = "first_launch";
    private SharedPreferences sharedPreferences;

    public SharedPreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("access_token", token);
        editor.apply();
    }

    public String getToken() {
        return sharedPreferences.getString("access_token", null);
    }

    public void clearToken() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("access_token");
        editor.apply();
    }

    public boolean isUserLoggedIn() {
        return sharedPreferences.contains("access_token");
    }

    public boolean isFirstLaunch() {
        boolean firstLaunch = sharedPreferences.getBoolean(FIRST_LAUNCH_KEY, true);
        if (firstLaunch) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(FIRST_LAUNCH_KEY, false);
            editor.apply();
        }
        return firstLaunch;
    }
}
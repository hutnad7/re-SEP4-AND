package com.example.sep4_and.utils;

import android.content.Context;
import android.content.SharedPreferences;



public class TokenManager {
    private static final String PREFS_NAME = "prefs";
    private static final String KEY_TOKEN = "token";

    private static SharedPreferences sharedPreferences;

    public static void init(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        }
    }

    public static void saveToken(String token) {
        if (sharedPreferences == null) {
            throw new IllegalStateException("TokenManager is not initialized");
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_TOKEN, token);
        editor.apply();
    }

    public static String getToken() {
        if (sharedPreferences == null) {
            throw new IllegalStateException("TokenManager is not initialized");
        }
        return sharedPreferences.getString(KEY_TOKEN, null);
    }

    public static void clearToken() {
        if (sharedPreferences == null) {
            throw new IllegalStateException("TokenManager is not initialized");
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_TOKEN);
        editor.apply();
    }
}

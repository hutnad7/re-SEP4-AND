package com.example.sep4_and.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSessionManager {
    private static final String PREFS_NAME = "user_prefs";
    private static final String KEY_USER_ID = "user_id";

    public static void saveUserId(Context context, int userId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USER_ID, userId);
        editor.apply();
    }

    public static int getUserId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_USER_ID, -1);
    }

    public static void clearUserId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_USER_ID);
        editor.apply();
    }
}
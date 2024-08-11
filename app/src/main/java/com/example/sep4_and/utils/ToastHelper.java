package com.example.sep4_and.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sep4_and.R;

public class ToastHelper {

    private static final int DEFAULT_ICON = R.drawable.garden;

    public static void showCustomToast(Context context, String message) {
        showCustomToast(context, message, DEFAULT_ICON, Toast.LENGTH_SHORT);
    }

    public static void showCustomToast(Context context, String message, int iconResId) {
        showCustomToast(context, message, iconResId, Toast.LENGTH_SHORT);
    }

    public static void showCustomToast(Context context, String message, int iconResId, int duration) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.custom_toast, null);

        ImageView toastIcon = layout.findViewById(R.id.toast_icon);
        toastIcon.setImageResource(iconResId);

        TextView toastMessage = layout.findViewById(R.id.toast_message);
        toastMessage.setText(message);

        Toast toast = new Toast(context);
        toast.setDuration(duration);
        toast.setView(layout);
        toast.show();
    }
}

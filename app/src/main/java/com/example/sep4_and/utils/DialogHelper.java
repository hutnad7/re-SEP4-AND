package com.example.sep4_and.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sep4_and.R;

public class DialogHelper {

    private Context context;
    private String title;
    private String message;
    private String cancelButtonText;
    private String confirmButtonText;

    public DialogHelper(Context context, String title, String message, String cancelButtonText, String confirmButtonText) {
        this.context = context;
        this.title = title;
        this.message = message;
        this.cancelButtonText = cancelButtonText;
        this.confirmButtonText = confirmButtonText;
    }

    public void showDialog(Runnable onConfirm) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_confirm, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        TextView dialogTitle = dialogView.findViewById(R.id.dialogTitle);
        TextView dialogMessage = dialogView.findViewById(R.id.dialogMessage);
        Button buttonCancel = dialogView.findViewById(R.id.buttonCancel);
        Button buttonConfirm = dialogView.findViewById(R.id.buttonConfirm);

        dialogTitle.setText(title);
        dialogMessage.setText(message);
        buttonCancel.setText(cancelButtonText);
        buttonConfirm.setText(confirmButtonText);

        buttonCancel.setOnClickListener(v -> dialog.dismiss());

        buttonConfirm.setOnClickListener(v -> {
            if (onConfirm != null) {
                onConfirm.run();
            }
            dialog.dismiss();
        });

        dialog.show();
    }

    public void showDialogWithObject(Runnable onConfirm, Runnable onCancel) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_confirm, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        TextView dialogTitle = dialogView.findViewById(R.id.dialogTitle);
        TextView dialogMessage = dialogView.findViewById(R.id.dialogMessage);
        Button buttonCancel = dialogView.findViewById(R.id.buttonCancel);
        Button buttonConfirm = dialogView.findViewById(R.id.buttonConfirm);

        dialogTitle.setText(title);
        dialogMessage.setText(message);
        buttonCancel.setText(cancelButtonText);
        buttonConfirm.setText(confirmButtonText);

        buttonCancel.setOnClickListener(v -> {
            if (onCancel != null) {
                onCancel.run();
            }
            dialog.dismiss();
        });

        buttonConfirm.setOnClickListener(v -> {
            if (onConfirm != null) {
                onConfirm.run();
            }
            dialog.dismiss();
        });

        dialog.show();
    }
}
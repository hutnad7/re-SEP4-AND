package com.example.sep4_and.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sep4_and.R;
import com.example.sep4_and.model.Notification;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private List<Notification> notifications = new ArrayList<>();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification currentNotification = notifications.get(position);
        holder.textViewType.setText("Notification");
        holder.textViewMessage.setText(currentNotification.getMessage());
        holder.textViewRecurrent.setText(currentNotification.isRecurrent() ? "Recurrent" : "One-time");
        holder.textViewDate.setText(dateFormat.format(currentNotification.getTime()));
    }

    @Override
    public int getItemCount() {
        return notifications != null ? notifications.size() : 0;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
        notifyDataSetChanged();
    }

    static class NotificationViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewType;
        private TextView textViewMessage;
        private TextView textViewRecurrent;
        private TextView textViewDate;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewType = itemView.findViewById(R.id.textViewNotificationType);
            textViewMessage = itemView.findViewById(R.id.textViewNotificationMessage);
            textViewRecurrent = itemView.findViewById(R.id.textViewNotificationRecurrent);
            textViewDate = itemView.findViewById(R.id.textViewNotificationDate);
        }
    }
}
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
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM", Locale.getDefault());
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
        holder.textViewMessage.setText(currentNotification.getMessage());
        holder.textViewDate.setText(dateFormat.format(currentNotification.getTime()));
        holder.textViewTime.setText(timeFormat.format(currentNotification.getTime()));
    }

    @Override
    public int getItemCount() {
        if(notifications!=null)
            return notifications.size();
        else
            return 0;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
        notifyDataSetChanged();
    }

    static class NotificationViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewMessage;
        private TextView textViewDate;
        private TextView textViewTime;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMessage = itemView.findViewById(R.id.textViewNotificationMessage);
            textViewDate = itemView.findViewById(R.id.textViewNotificationDate);
            textViewTime = itemView.findViewById(R.id.textViewNotificationTime);
        }
    }
}
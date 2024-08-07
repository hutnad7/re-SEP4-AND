package com.example.sep4_and.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sep4_and.R;
import com.example.sep4_and.list.NotificationAdapter;
import com.example.sep4_and.model.Notification;
import com.example.sep4_and.model.User;
import com.example.sep4_and.viewmodel.NotificationViewModel;
import com.example.sep4_and.viewmodel.UserViewModel;

import java.util.List;


public class ViewNotificationsFragment extends Fragment {

    private NotificationViewModel notificationViewModel;
    private UserViewModel userViewModel;
    private RecyclerView recyclerView;
    private NotificationAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_notifications, container, false);

        TextView pageTitle = view.findViewById(R.id.pageTitle);
        TextView pageDescription = view.findViewById(R.id.pageDescription);
        recyclerView = view.findViewById(R.id.recyclerViewNotifications);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NotificationAdapter();
        recyclerView.setAdapter(adapter);

        notificationViewModel = new ViewModelProvider(this).get(NotificationViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        // Observe the current user and fetch notifications for that user
        userViewModel.getCurrentUser().observe(getViewLifecycleOwner(), currentUser -> {
            if (currentUser != null) {
                notificationViewModel.getNotificationsForUser(currentUser.getId()).observe(getViewLifecycleOwner(), notifications -> {
                    adapter.setNotifications(notifications);
                });
            }
        });

        return view;
    }
}
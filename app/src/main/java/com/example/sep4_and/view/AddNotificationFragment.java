package com.example.sep4_and.view;

import com.example.sep4_and.R;
import com.example.sep4_and.model.Notification;
import com.example.sep4_and.model.User;
import com.example.sep4_and.viewmodel.NotificationViewModel;
import com.example.sep4_and.viewmodel.UserViewModel;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;



import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddNotificationFragment extends Fragment {

    private EditText editTextMessage;
    private Button buttonPickDate;
    private Button buttonPickTime;
    private Switch switchRecurrent;
    private Button buttonAddNotification;
    private NotificationViewModel notificationViewModel;
    private UserViewModel userViewModel;
    private List<User> users;
    private Calendar calendar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_notification, container, false);

        editTextMessage = view.findViewById(R.id.editTextMessage);
        buttonPickDate = view.findViewById(R.id.buttonPickDate);
        buttonPickTime = view.findViewById(R.id.buttonPickTime);
        Switch switchRecurrent = view.findViewById(R.id.switchRecurrent);
        buttonAddNotification = view.findViewById(R.id.buttonAddNotification);

        notificationViewModel = new ViewModelProvider(this).get(NotificationViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        calendar = Calendar.getInstance();

        userViewModel.getAllUsers().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> userList) {
                users = userList;
            }
        });

        buttonPickDate.setOnClickListener(v -> showDatePickerDialog());
        buttonPickTime.setOnClickListener(v -> showTimePickerDialog());

        buttonAddNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = editTextMessage.getText().toString();
                boolean isRecurrent = switchRecurrent.isChecked();
                Date date = calendar.getTime();

                //TODO: Fix
                if (users != null && !users.isEmpty()) {

                    User latestUser = users.get(users.size() - 1); // Get the latest user
                    Notification notification = new Notification(message, date, isRecurrent, latestUser.getId());
                    notificationViewModel.insert(notification);
                    Toast.makeText(getContext(), "Notification Added", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getContext(), "No users available", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, year, month, dayOfMonth) -> {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void showTimePickerDialog() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                getContext(),
                (view, hourOfDay, minute) -> {
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendar.set(Calendar.MINUTE, minute);
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true // 24 hour format
        );
        timePickerDialog.show();
    }

}
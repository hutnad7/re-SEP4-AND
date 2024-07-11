package com.example.sep4_and.view;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.sep4_and.R;
import com.example.sep4_and.model.User;
import com.example.sep4_and.viewmodel.UserViewModel;

import java.util.List;

public class UserFragment extends Fragment{

    private UserViewModel userViewModel;
    private EditText userNameEditText;
    private EditText passwordEditText;
    private EditText emailEditText;
    private Button createUserButton;
    private Button fetchUsersButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        userNameEditText = view.findViewById(R.id.editTextUserName);
        passwordEditText = view.findViewById(R.id.editTextPassword);
        emailEditText = view.findViewById(R.id.editTextEmail);
        createUserButton = view.findViewById(R.id.buttonCreateUser);
        fetchUsersButton = view.findViewById(R.id.buttonFetchUsers);

        createUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });

        fetchUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchUsers();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        userViewModel.getAllUsers().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                // Update UI with users
            }
        });
    }

    private void createUser() {
        String userNameStr = userNameEditText.getText().toString();
        String passwordStr = passwordEditText.getText().toString();
        String emailStr = emailEditText.getText().toString();

        if (TextUtils.isEmpty(userNameStr) || TextUtils.isEmpty(passwordStr) || TextUtils.isEmpty(emailStr)) {
            Toast.makeText(getActivity(), "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User(userNameStr, passwordStr, emailStr);
        userViewModel.insert(user);
        Toast.makeText(getActivity(), "User created successfully", Toast.LENGTH_SHORT).show();
    }

    private void fetchUsers() {
        userViewModel.fetchUsersFromApi();
    }
}

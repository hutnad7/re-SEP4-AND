package com.example.sep4_and.view;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.sep4_and.R;
import com.example.sep4_and.dao.AppDatabase;
import com.example.sep4_and.model.User;
import com.example.sep4_and.viewmodel.UserViewModel;

public class EditProfileFragment extends Fragment {

    private static final String ARG_USER_ID = "user_id";

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button saveChangesButton;

    private UserViewModel userViewModel;
    private int userId;

    public static EditProfileFragment newInstance(int userId) {
        EditProfileFragment fragment = new EditProfileFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getInt(ARG_USER_ID);
        }
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        initViews(view);
        loadUser();

        saveChangesButton.setOnClickListener(v -> saveProfileChanges());

        return view;
    }

    private void initViews(View view) {
        firstNameEditText = view.findViewById(R.id.firstName);
        lastNameEditText = view.findViewById(R.id.lastName);
        emailEditText = view.findViewById(R.id.email);
        passwordEditText = view.findViewById(R.id.password);
        saveChangesButton = view.findViewById(R.id.save_changes_button);
    }

    private void loadUser() {
        userViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                firstNameEditText.setText(user.getFirstName());
                lastNameEditText.setText(user.getLastName());
                emailEditText.setText(user.getEmail());
                passwordEditText.setText(user.getPassword());
            } else {
                Toast.makeText(getActivity(), "Failed to load user. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveProfileChanges() {
        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (isValidInput(firstName, lastName, email, password)) {
            userViewModel.updateUserDetails( firstName, lastName, email, password);
            Toast.makeText(getActivity(), "Profile updated successfully", Toast.LENGTH_SHORT).show();
            getParentFragmentManager().popBackStack();
        } else {
            Toast.makeText(getActivity(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidInput(String firstName, String lastName, String email, String password) {
        return !firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty() && !password.isEmpty();
    }
}
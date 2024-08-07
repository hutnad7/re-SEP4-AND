package com.example.sep4_and.view;

import static android.app.PendingIntent.getActivity;

import android.os.AsyncTask;
import android.os.Bundle;
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

import com.example.sep4_and.R;
import com.example.sep4_and.dao.AppDatabase;
import com.example.sep4_and.model.User;

public class EditProfileFragment extends Fragment {

    private static final String ARG_USER_ID = "user_id";

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button saveChangesButton;

    private AppDatabase appDatabase;
    private User user;
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
        appDatabase = AppDatabase.getDatabase(requireActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        initViews(view);
        loadUser(userId);

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

    private void loadUser(int userId) {
        new LoadUserTask().execute(userId);
    }

    private void saveProfileChanges() {
        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (isValidInput(firstName, lastName, email, password)) {
            if (user == null) {
                user = new User(firstName, lastName, email, password);
                new SaveUserTask().execute(user);
            } else {
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email);
                user.setPassword(password);
                new UpdateUserTask().execute(user);
            }
        } else {
            Toast.makeText(getActivity(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidInput(String firstName, String lastName, String email, String password) {
        return !firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty() && !password.isEmpty();
    }

    private class LoadUserTask extends AsyncTask<Integer, Void, User> {
        @Override
        protected User doInBackground(Integer... userIds) {
            return appDatabase.userDao().getUserById(userIds[0]);
        }

        @Override
        protected void onPostExecute(User user) {
            if (user != null) {
                EditProfileFragment.this.user = user;
                firstNameEditText.setText(user.getFirstName());
                lastNameEditText.setText(user.getLastName());
                emailEditText.setText(user.getEmail());
                passwordEditText.setText(user.getPassword());
            }
        }
    }

    private class SaveUserTask extends AsyncTask<User, Void, Void> {
        @Override
        protected Void doInBackground(User... users) {
            appDatabase.userDao().insert(users[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            showToastAndGoBack("Profile created successfully");
        }
    }

    private class UpdateUserTask extends AsyncTask<User, Void, Void> {
        @Override
        protected Void doInBackground(User... users) {
            appDatabase.userDao().update(users[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            showToastAndGoBack("Profile updated successfully");
        }
    }

    private void showToastAndGoBack(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
    }
}
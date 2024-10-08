package com.example.sep4_and.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.sep4_and.R;
import com.example.sep4_and.model.User;
import com.example.sep4_and.network.requests.RegisterRequest;
import com.example.sep4_and.utils.ToastHelper;
import com.example.sep4_and.utils.TokenManager;
import com.example.sep4_and.viewmodel.UserViewModel;

public class SignupFragment extends Fragment {
    private UserViewModel userViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        // Initialize the UserViewModel
        userViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(UserViewModel.class);

        EditText firstNameEditText = view.findViewById(R.id.firstName);
        EditText lastNameEditText = view.findViewById(R.id.lastName);
        EditText emailEditText = view.findViewById(R.id.email);
        EditText passwordEditText = view.findViewById(R.id.password);
        Button signupButton = view.findViewById(R.id.signup_button);
        Button loginButton = view.findViewById(R.id.login_button);

        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            requireActivity().finish();
        });

        signupButton.setOnClickListener(v -> {
            String firstName = firstNameEditText.getText().toString();
            String lastName = lastNameEditText.getText().toString();
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (!firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                RegisterRequest newUser = new RegisterRequest(email, firstName, lastName, password);

                userViewModel.register(newUser).observe(getViewLifecycleOwner(), user -> {
                    if (user != null) {
                        TokenManager.saveToken(String.valueOf(user.getId()));
                        ToastHelper.showCustomToast(getContext(),"User registered Successfully");

                        // Redirect back to LoginActivity
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        requireActivity().finish();
                    } else {
                        ToastHelper.showCustomToast(getContext(),"Failed to register the user",R.drawable.system_problem);

                    }
                });
            } else {
                ToastHelper.showCustomToast(getContext(),"Please fill all fields",R.drawable.empty_box);

            }
        });

        return view;
    }
}
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
import androidx.lifecycle.ViewModelProvider;

import com.example.sep4_and.R;
import com.example.sep4_and.model.User;
import com.example.sep4_and.viewmodel.UserViewModel;

/*public class UserFragment extends Fragment {

    private UserViewModel userViewModel;
    private EditText editTextUserName;
    private EditText editTextPassword;
    private EditText editTextEmail;
    private Button buttonAddUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        editTextUserName = view.findViewById(R.id.editTextUserName);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        buttonAddUser = view.findViewById(R.id.buttonAddUser);

        buttonAddUser.setOnClickListener(v -> addUser());

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }

    private void addUser() {
        String userName = editTextUserName.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();

        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password) || TextUtils.isEmpty(email)) {
            Toast.makeText(getActivity(), "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User(userName, password, email);
        userViewModel.insert(user);
        Toast.makeText(getActivity(), "User added", Toast.LENGTH_SHORT).show();
        editTextUserName.setText("");
        editTextPassword.setText("");
        editTextEmail.setText("");
    }
}
*/
package com.example.sep4_and.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;


import com.example.sep4_and.model.AuthRequest;
import com.example.sep4_and.model.AuthResponse;
import com.example.sep4_and.model.DbCrossReference.UserWithGreenHouses;
import com.example.sep4_and.model.User;
import com.example.sep4_and.repository.UserRepository;
import com.example.sep4_and.utils.Auth0Config;
import com.example.sep4_and.utils.JWTUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserViewModel extends AndroidViewModel {
    private UserRepository repository;
    private LiveData<List<User>> allUsers;

    // Required by another section
    private MutableLiveData<User> user = new MutableLiveData<>();
    private MutableLiveData<String> authError = new MutableLiveData<>();
    private MutableLiveData<String> token = new MutableLiveData<>();

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
        allUsers = repository.getAllUsers(); // Initialize here
    }

    // Required by another section
    public LiveData<User> getUser() {
        return user;
    }

    // Required by another section
    public LiveData<String> getAuthError() {
        return authError;
    }

    // Required by another section
    public LiveData<String> getToken() {
        return token;
    }

    // Required by another section
    public void login(String email, String password) {
        LiveData<User> localUser = repository.login(email, password);
        localUser.observeForever(user -> {
            if (user != null) {
                this.user.postValue(user);
                token.postValue(JWTUtils.createToken(user.getEmail()));
            } else {
                authenticate(email, password);
            }
        });
    }

    // Required by another section
    private void authenticate(String email, String password) {
        AuthRequest authRequest = new AuthRequest(
                Auth0Config.CLIENT_ID,
                Auth0Config.CLIENT_SECRET,
                "https://dev-80rxgkd12lqyzoeg.us.auth0.com/api/v2/",
                "password",
                email,
                password
        );

        repository.authenticate(authRequest).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.isSuccessful()) {
                    token.postValue(response.body().getAccessToken());
                } else {
                    authError.postValue("Login Failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                authError.postValue("Network Error: " + t.getMessage());
            }
        });
    }

    // Method to fetch all users
    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    public void insert(User user) {
        repository.insert(user);
    }
}
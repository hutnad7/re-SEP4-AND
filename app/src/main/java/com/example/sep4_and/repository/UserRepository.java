package com.example.sep4_and.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.sep4_and.App;
import com.example.sep4_and.dao.AppDatabase;
import com.example.sep4_and.dao.UserDao;
import com.example.sep4_and.model.User;
import com.example.sep4_and.network.ApiService;
import com.example.sep4_and.network.RetrofitInstance;
import com.example.sep4_and.network.api.UserApi;
import com.example.sep4_and.network.requests.LoginRequest;
import com.example.sep4_and.network.requests.RegisterRequest;
import com.example.sep4_and.network.responses.LoginResponse;
import com.example.sep4_and.network.responses.RegisterResponse;
import com.example.sep4_and.utils.UserSessionManager;

public class UserRepository {
    private UserDao userDao;
    private ExecutorService executorService;
    private UserApi userApi;
    private boolean useApi = false; // Hardcoded flag
    private MutableLiveData<User> currentUser = new MutableLiveData<>(); // In-memory current user

    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        userDao = db.userDao();
        executorService = Executors.newSingleThreadExecutor();
        userApi = RetrofitInstance.getClient("").create(UserApi.class);
    }

    public LiveData<List<User>> getAllUsers() {
        if (useApi) {
            return new MutableLiveData<>(new ArrayList<>());
        } else {
            return userDao.getAllUsers();
        }
    }

    public void insert(User user) {
        if (useApi) {
            // TODO: Implement API insertion logic
        } else {
            executorService.execute(() -> {
                userDao.insert(user);
                Log.d("UserRepository", "User inserted: " + user.getId());
            });
        }
    }

    public LiveData<User> login(String email, String password) {
        Log.d("UserRepository", "Login called with email: " + email);
        MutableLiveData<User> userLiveData = new MutableLiveData<>();

        executorService.execute(() -> {
            User user = userDao.loginSync(email, password); // Use the synchronous method for debugging
            if (user != null) {
                currentUser.postValue(user); // Set current user
                UserSessionManager.saveUserId(App.getContext(), user.getId()); // Save userId to SharedPreferences
                Log.d("UserRepository", "Current user set: " + user.getId());
            }
            userLiveData.postValue(user);
            Log.d("UserRepository", user != null ? "Login successful for user: " + user.getId() : "Login failed");
        });

        return userLiveData;
    }

    public LiveData<User> getCurrentUser() {
        Log.d("UserRepository", "getCurrentUser called");
        int userId = UserSessionManager.getUserId(App.getContext());
        if (userId != -1) {
            executorService.execute(() -> {
                User user = userDao.getUserById(userId);
                currentUser.postValue(user);
            });
        }
        return currentUser; // Return the in-memory current user
    }

    public LiveData<User> register(RegisterRequest registerRequest) {
        if (useApi) {
            MutableLiveData<User> registerResult = new MutableLiveData<>();

            userApi.register(registerRequest).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        User createdUser = response.body();
                        insert(createdUser);
                        currentUser.postValue(createdUser); // Set current user
                        UserSessionManager.saveUserId(App.getContext(), createdUser.getId()); // Save userId to SharedPreferences
                        registerResult.setValue(createdUser);
                        Log.d("UserRepository", "Registration successful for user: " + createdUser.getId());
                    } else {
                        registerResult.setValue(null);
                        Log.d("UserRepository", "Registration failed");
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    registerResult.setValue(null);
                    Log.d("UserRepository", "Registration API call failed: " + t.getMessage());
                }
            });

            return registerResult;
        } else {
            MutableLiveData<User> registerResult = new MutableLiveData<>();
            User newUser = new User(registerRequest.getEmail(), registerRequest.getFirstName(), registerRequest.getLastName(), registerRequest.getPassword());
            executorService.execute(() -> {
                userDao.insert(newUser);
                currentUser.postValue(newUser); // Set current user
                UserSessionManager.saveUserId(App.getContext(), newUser.getId()); // Save userId to SharedPreferences
                registerResult.postValue(newUser);
                Log.d("UserRepository", "Registration successful for user: " + newUser.getId());
            });
            return registerResult;
        }
    }
}

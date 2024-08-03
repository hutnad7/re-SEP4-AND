package com.example.sep4_and.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.sep4_and.dao.AppDatabase;
import com.example.sep4_and.dao.UserDao;
import com.example.sep4_and.model.DbCrossReference.UserWithGreenHouses;
import com.example.sep4_and.model.User;
import com.example.sep4_and.network.ApiService;
import com.example.sep4_and.network.RetrofitInstance;
import com.example.sep4_and.network.api.UserApi;
import com.example.sep4_and.network.requests.LoginRequest;
import com.example.sep4_and.network.requests.RegisterRequest;
import com.example.sep4_and.network.responses.LoginResponse;
import com.example.sep4_and.network.responses.RegisterResponse;

public class UserRepository {
    private UserDao userDao;
    private ExecutorService executorService;
    private UserApi userApi;

    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        userDao = db.userDao();
        executorService = Executors.newSingleThreadExecutor();
        userApi = RetrofitInstance.getClient("").create(UserApi.class);
    }

    public LiveData<List<User>> getAllUsers() {
        return userDao.getAllUsers();
    }

    public LiveData<List<UserWithGreenHouses>> getUsersWithGreenHouses() {
        return userDao.getUsersWithGreenHouses();
    }

    public void insert(User user) {
        executorService.execute(() -> {
            userDao.insert(user);
        });
    }


    public LiveData<User> login(String email, String password) {
        MutableLiveData<User> userLiveData = new MutableLiveData<>();
        LoginRequest loginRequest = new LoginRequest(email, password);

        userApi.login(loginRequest).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();
                    userLiveData.setValue(user);
                } else {
                    userLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                userLiveData.setValue(null);
            }
        });

        return userLiveData;
    }


    public LiveData<User> register(RegisterRequest registerRequest) {
        MutableLiveData<User> registerResult = new MutableLiveData<>();

        userApi.register(registerRequest).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call,Response<User> response) {
                if (response.isSuccessful()) {
                    User createdUser = response.body();
                    insert(createdUser);  // Save the user locally after successful registration
                    registerResult.setValue(createdUser);
                } else {
                    registerResult.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                registerResult.setValue(null);
            }
        });

        return registerResult;
    }


}
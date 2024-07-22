package com.example.sep4_and.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
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
import com.example.sep4_and.model.AuthRequest;
import com.example.sep4_and.model.AuthResponse;
import com.example.sep4_and.model.DbCrossReference.UserWithGreenHouses;
import com.example.sep4_and.model.User;
import com.example.sep4_and.network.ApiService;
import com.example.sep4_and.network.RetrofitInstance;
import com.example.sep4_and.utils.Auth0Config;

public class UserRepository {
    private UserDao userDao;
    private ExecutorService executorService;
    private ApiService apiService;

    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        userDao = db.userDao();
        executorService = Executors.newSingleThreadExecutor();
        Retrofit retrofit = RetrofitInstance.getClient(Auth0Config.DOMAIN);
        apiService = retrofit.create(ApiService.class);
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
            Log.d("UserRepository", "User inserted: " + user.getEmail() + ", Password: " + user.getPassword());
        });
    }

    public Call<AuthResponse> authenticate(AuthRequest authRequest) {
        return apiService.login(authRequest);
    }

    public LiveData<User> login(String email, String password) {
        Log.d("UserRepository", "Attempting to login with email: " + email + " and password: " + password);
        LiveData<User> user = userDao.login(email, password);
        user.observeForever(new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null) {

                } else {
                    Log.d("UserRepository", "No user found with email: " + email + " and password: " + password);
                }
            }
        });
        return user;
    }

    public Call<User> register(User user) {
        return apiService.register(user);
    }
}
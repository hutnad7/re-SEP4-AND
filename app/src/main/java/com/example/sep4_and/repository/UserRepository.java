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
import com.example.sep4_and.model.AuthRequest;
import com.example.sep4_and.model.AuthResponse;
import com.example.sep4_and.model.DbCrossReference.UserWithGreenHouses;
import com.example.sep4_and.model.User;
import com.example.sep4_and.network.ApiService;
import com.example.sep4_and.network.RetrofitInstance;
import com.example.sep4_and.network.api.UserApi;
import com.example.sep4_and.network.requests.LoginRequest;
import com.example.sep4_and.network.responses.LoginResponse;
import com.example.sep4_and.utils.Auth0Config;

public class UserRepository {
    private UserDao userDao;
    private ExecutorService executorService;
    private UserApi userApi;

    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        userDao = db.userDao();
        executorService = Executors.newSingleThreadExecutor();
        Retrofit retrofit = RetrofitInstance.getClient(Auth0Config.DOMAIN);
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
            Log.d("UserRepository", "User inserted: " + user.getEmail() + ", Password: " + user.getPassword());
        });
    }

    /*public Call<AuthResponse> authenticate(AuthRequest authRequest) {
        return apiService.login(authRequest);
    }*/

   /* public LiveData<User> login(String email, String password) {
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
    }*/

    public LiveData<String> login(String email, String password) {
        MutableLiveData<String> tokenLiveData = new MutableLiveData<>();
        LoginRequest loginRequest = new LoginRequest(email, password);

        userApi.login(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String token = response.body().getToken();
                    RetrofitInstance.setAuthToken(token);
                    tokenLiveData.setValue(token);
                } else {
                    tokenLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                tokenLiveData.setValue(null);
            }
        });

        return tokenLiveData;
    }


    public LiveData<Boolean> register(User user) {
        MutableLiveData<Boolean> registerResult = new MutableLiveData<>();

        userApi.register(user).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    insert(user);  // Save the user locally after successful registration
                    registerResult.setValue(true);
                } else {
                    registerResult.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                registerResult.setValue(false);
            }
        });

        return registerResult;
    }


}
package com.example.sep4_and.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.sep4_and.DAO.AppDatabase;
import com.example.sep4_and.DAO.UserDAO;
import com.example.sep4_and.model.User;
import com.example.sep4_and.network.ApiService;

import java.util.List;

public class UserRepository {
    private String ApiUrl= "https://your.api.url/";
    private UserDAO userDao;
    private LiveData<List<User>> allUsers;
    private ApiService apiService;
    private ExecutorService executorService;

    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        userDao = db.userDao();
        allUsers = userDao.getAllUsers();
        executorService = Executors.newSingleThreadExecutor();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    public void insert(User user) {
        executorService.execute(() -> {
            userDao.insert(user);
        });
    }

    public void fetchUsersFromApi() {
        apiService.getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    executorService.execute(() -> {
                        for (User user : response.body()) {
                            userDao.insert(user);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                // Handle failure
            }
        });
    }
}

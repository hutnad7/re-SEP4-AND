package com.example.sep4_and.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;


import com.example.sep4_and.model.DbCrossReference.UserWithGreenHouses;
import com.example.sep4_and.model.User;
import com.example.sep4_and.repository.UserRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserViewModel extends AndroidViewModel {
    private UserRepository repository;
    private MutableLiveData<User> user = new MutableLiveData<>();
    private MutableLiveData<String> authError = new MutableLiveData<>();
    private MutableLiveData<String> token = new MutableLiveData<>();

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
    }

    public LiveData<User> getUser() {
        return user;
    }

    public LiveData<String> getAuthError() {
        return authError;
    }

    public LiveData<String> getToken() {
        return token;
    }

    public LiveData<String> login(String email, String password) {
        return repository.login(email, password);
    }

    public void insert(User user) {
        repository.insert(user);
    }

    public LiveData<List<User>> getAllUsers() {
        return null;
    }
}
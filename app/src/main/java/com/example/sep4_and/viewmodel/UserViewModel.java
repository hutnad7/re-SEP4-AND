package com.example.sep4_and.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;



import com.example.sep4_and.model.User;
import com.example.sep4_and.network.requests.RegisterRequest;
import com.example.sep4_and.repository.UserRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserViewModel extends AndroidViewModel {
    private UserRepository repository;
    private LiveData<List<User>> allUsers;
    private LiveData<User> currentUser;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
        allUsers = repository.getAllUsers();
        currentUser = repository.getCurrentUser(); // Retrieve the current user from the repository
    }

    public LiveData<User> getUser() {
        return currentUser;
    }

    //Should be used, isn't, app works, something went wrong
    public LiveData<String> getAuthError() {
        return new MutableLiveData<>(); // TODO: Implement
    }
    //Should be used, isn't, app works, something went wrong
    public LiveData<String> getToken() {
        return new MutableLiveData<>(); // TODO: Implement
    }

    public LiveData<User> login(String email, String password) {
        Log.d("UserViewModel", "Login called with email: " + email);  //Added when login was not working, kept in case it decides to not work again
        return repository.login(email, password);
    }

    //Ideally, we don't let a user get our entire user table
    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    public void insert(User user) {
        repository.insert(user);
    }

    public LiveData<User> getCurrentUser() {
        return repository.getCurrentUser();
    }

    public LiveData<User> register(RegisterRequest registerRequest) {
        return repository.register(registerRequest);
    }


    public void updateUserDetails(int userId, String firstName, String lastName, String email, String password) {
        // Convert userId to String and call the repository method //Maybe do it at start of call chain
        repository.updateUserDetails(userId, firstName, lastName, email, password);
    }
}
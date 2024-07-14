package com.example.sep4_and.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;


import com.example.sep4_and.model.DbCrossReference.UserWithGreenHouses;
import com.example.sep4_and.model.User;
import com.example.sep4_and.repository.UserRepository;


public class UserViewModel extends AndroidViewModel {
    private UserRepository repository;
    private LiveData<List<User>> allUsers;
    private LiveData<List<UserWithGreenHouses>> allUsersWithGreenHouses;

    public UserViewModel(Application application) {
        super(application);
        repository = new UserRepository(application);
        allUsers = repository.getAllUsers();
        allUsersWithGreenHouses = repository.getUsersWithGreenHouses();
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    public LiveData<List<UserWithGreenHouses>> getAllUsersWithGreenHouses() {
        return allUsersWithGreenHouses;
    }

    public void insert(User user) {
        repository.insert(user);
    }
}

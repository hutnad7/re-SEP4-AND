package com.example.sep4_and.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.List;



import com.example.sep4_and.DAO.AppDatabase;
import com.example.sep4_and.DAO.UserDAO;
import com.example.sep4_and.model.User;
import com.example.sep4_and.repository.UserRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private UserRepository repository;
    private LiveData<List<User>> allUsers;

    public UserViewModel(Application application) {
        super(application);
        repository = new UserRepository(application);
        allUsers = repository.getAllUsers();
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    public void insert(User user) {
        repository.insert(user);
    }

    public void fetchUsersFromApi() {
        repository.fetchUsersFromApi();
    }
}

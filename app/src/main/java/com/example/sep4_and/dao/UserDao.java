package com.example.sep4_and.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;


import com.example.sep4_and.model.User;

import java.util.List;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Query("SELECT * FROM users")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    LiveData<User> login(String email, String password);

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    User loginSync(String email, String password);

    @Query("SELECT * FROM users WHERE email = :email")
    User getUser(String email);

    @Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
    User getUserById(int userId);

    @Update
    void update(User user);
}
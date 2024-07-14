package com.example.sep4_and.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Junction;
import androidx.room.PrimaryKey;
import androidx.room.Relation;
import androidx.room.TypeConverters;

import com.example.sep4_and.model.DbCrossReference.GreenHouseUserCrossRef;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String userName;
    private String password;
    private String email;

    @Ignore
    private List<GreenHouse> greenHouses;

    public User(String userName, String password, String email) {
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<GreenHouse> getGreenHouses() {
        return greenHouses;
    }

    public void setGreenHouses(List<GreenHouse> greenHouses) {
        this.greenHouses = greenHouses;
    }
}

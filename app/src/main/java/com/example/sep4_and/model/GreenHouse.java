package com.example.sep4_and.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Junction;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import com.example.sep4_and.model.DbCrossReference.GreenHouseUserCrossRef;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "greenhouses")
public class GreenHouse {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String location;

    @Ignore
    private List<User> users;

    public GreenHouse(String name, String location) {
        this.name = name;
        this.location = location;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
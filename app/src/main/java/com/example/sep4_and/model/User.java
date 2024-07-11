package com.example.sep4_and.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String userName;
    private String password;
    private int email;
    private List<Notification> notifications = new ArrayList<>(); // Association with Notification
    private List<GreenHouse> greenHouses = new ArrayList<>(); // Association with GreenHouse

    // Constructor
    public User(int id, String userName, String password, int email) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    // Getters and Setters
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

    public int getEmail() {
        return email;
    }

    public void setEmail(int email) {
        this.email = email;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public List<GreenHouse> getGreenHouses() {
        return greenHouses;
    }

    public void setGreenHouses(List<GreenHouse> greenHouses) {
        this.greenHouses = greenHouses;
    }

}

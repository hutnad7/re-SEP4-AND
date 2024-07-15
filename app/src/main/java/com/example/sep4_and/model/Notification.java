package com.example.sep4_and.model;

public class Notification {
    private int id;
    private String message;
    private int time;
    private User user; // Association with User

    // Constructor
    public Notification(int id, String message, int time, User user) {
        this.id = id;
        this.message = message;
        this.time = time;
        this.user = user;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

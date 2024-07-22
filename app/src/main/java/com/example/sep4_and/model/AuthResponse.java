package com.example.sep4_and.model;

import com.google.gson.annotations.SerializedName;

public class AuthResponse {

    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("email")
    private String email;
    @SerializedName("userName")
    private String userName;

    public AuthResponse(String accessToken, String email, String userName) {
        this.accessToken = accessToken;
        this.email = email;
        this.userName = userName;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

package com.example.sep4_and.model;

public class AuthRequest {
    private String client_id;
    private String client_secret;
    private String audience;
    private String grant_type;
    private String username;
    private String password;

    public AuthRequest(String clientId, String clientSecret, String audience, String grantType, String username, String password) {
        this.client_id = clientId;
        this.client_secret = clientSecret;
        this.audience = audience;
        this.grant_type = grantType;
        this.username = username;
        this.password = password;
    }

    // Getters
    public String getClientId() {
        return client_id;
    }

    public String getClientSecret() {
        return client_secret;
    }

    public String getAudience() {
        return audience;
    }

    public String getGrantType() {
        return grant_type;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

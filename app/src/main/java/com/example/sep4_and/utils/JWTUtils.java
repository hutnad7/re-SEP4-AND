package com.example.sep4_and.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

public class JWTUtils {

    private static final String SECRET_KEY = "4N9Z7Fj6b6BmNydlfLnl3iVJzP4aPZ5mNZh1LJh6uV8=";
    private static final long EXPIRATION_TIME = 86400000;

    public static String createToken(String email) {
        return JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }
}

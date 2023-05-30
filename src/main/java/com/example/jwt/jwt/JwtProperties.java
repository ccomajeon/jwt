package com.example.jwt.jwt;

public interface JwtProperties {
    String SECRET = "cos";
    int EXPIRATION_TIME = 60000*10;
    String TOKEN_PREFIX = "Bearar ";
    String HEADER_STRING = "Authorization";
}

package com.example.product_service.service;

public interface AuthService {
    String getUserAccessToken(String username, String password);
}

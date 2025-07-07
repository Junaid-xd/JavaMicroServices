package com.example.User.Service.services;


import org.springframework.stereotype.Service;

@Service
public interface JwtService {

    String generateToken();
}

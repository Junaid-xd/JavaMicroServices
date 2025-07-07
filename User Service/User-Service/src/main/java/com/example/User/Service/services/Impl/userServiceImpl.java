package com.example.User.Service.services.Impl;

import com.example.User.Service.Models.User;
import com.example.User.Service.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.example.User.Service.services.userService;

import java.util.List;
import java.util.Optional;

@Service
public class userServiceImpl implements userService{

    @Autowired
    private UserRepo userRepository;

    @Autowired
    jwtServiceImpl jwtService;

    @Autowired
    AuthenticationManager authManager;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User addUser(User newuser) {
        return userRepository.save(newuser);
    }

    @Override
    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public String verify(User user) {
        Authentication authentication =
                authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        if(authentication.isAuthenticated())
            return jwtService.generateToken();
        return "Failed";
    }
}

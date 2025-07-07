package com.example.User.Service.services;

import com.example.User.Service.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface userService{
    List<User> getUsers();

    User addUser(User newuser);

    Optional<User> getUserById(Integer id);

    String verify(User user);

}

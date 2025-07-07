package com.example.User.Service.Repository;

import com.example.User.Service.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
    User findByEmail(String username);
}

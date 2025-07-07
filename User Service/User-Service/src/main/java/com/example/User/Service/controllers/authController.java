package com.example.User.Service.controllers;

import com.example.User.Service.Models.User;
import com.example.User.Service.services.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class authController {

    @Autowired
    private userService userService;

    @PostMapping("/login")
    public String userLogin(@RequestBody User user){

        return userService.verify(user);
    }
}

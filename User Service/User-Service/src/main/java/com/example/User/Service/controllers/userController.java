package com.example.User.Service.controllers;


import com.example.User.Service.DTO.OrderDTO;
import com.example.User.Service.Feign.UserInterface;
import com.example.User.Service.Models.User;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.example.User.Service.services.userService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class userController {

    @Autowired
    private userService userservice;

    @Autowired
    private UserInterface userInterface;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @GetMapping("/getUsers")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userservice.getUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("createUser")
    public ResponseEntity<?> createUser(@RequestBody User newUser){
        try {
            newUser.setPassword(encoder.encode(newUser.getPassword()));
            User savedUser = userservice.addUser(newUser);
            return ResponseEntity.status(HttpStatus.SC_CREATED).body(savedUser);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.SC_CONFLICT).body("Email already exists.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                    .body("Something went wrong while creating user.");
        }

    }

    @GetMapping("/getByID/{id}")
    public ResponseEntity<?> getUserByID(@PathVariable Integer id) {
        Optional<User> user = userservice.getUserById(id);
        if (user.isPresent())
            return ResponseEntity.ok(user);
        else
            return ResponseEntity.status(HttpStatus.SC_NOT_FOUND)
                    .body("User not found with ID: " + id);
    }

    @PostMapping("/createOrder")
    public ResponseEntity<?> createOrder(@RequestBody OrderDTO order){
        return userInterface.createOrder(order);
    }

    @GetMapping("/getOrdersByUserID/{id}")
    public ResponseEntity<?> getOrdersByUserID(@PathVariable Integer id){
        return userInterface.getOrdersByUserID(id);
    }

    @PutMapping("/updateOrder/{orderId}")
    public ResponseEntity<?> updateOrder(@PathVariable Integer orderId, @RequestBody OrderDTO orderDTO) {
       return userInterface.updateOrder(orderId, orderDTO);
    }


    @DeleteMapping("/deleteOrder/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable Integer orderId) {
        return userInterface.deleteOrder(orderId);
    }
}

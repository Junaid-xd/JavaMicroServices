package com.example.User.Service.controllers;

import com.example.User.Service.Utils.CookieUtil;
import com.example.User.Service.Utils.JwtUtil;
import com.example.User.Service.Models.User;
import com.example.User.Service.Repository.UserRepo;
import com.example.User.Service.Utils.RedisUtil;
import com.example.User.Service.services.userService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class authController {

    @Autowired
    private userService userService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    CookieUtil cookieUtil;

    @Autowired
    RedisUtil redisUtil;

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody User user, HttpServletResponse response){

        if(!userRepo.existsByEmail(user.getEmail())){
            return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body("User not found");
        }

        User theUser = userRepo.findByEmail(user.getEmail());

        if(!passwordEncoder.matches(user.getPassword(), theUser.getPassword())){

            return ResponseEntity.status(HttpStatus.SC_UNAUTHORIZED).body("Unauthorized");
        }


        String token = jwtUtil.generateToken(user.getEmail(), user.getPassword());

        //CookieUtil.addCookie(response, JwtUtil.TOKEN_NAME, token); //Storing JWT in cookie

        redisUtil.saveToken(token, token); //Storing JWT in Redis



        //return new ResponseEntity<>(user, org.springframework.http.HttpStatus.OK);

        return ResponseEntity.ok("Session ID = " + "Bearer "+ token);



    }



    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.SC_UNAUTHORIZED).body("Authorization header missing or invalid");
        }

        String sessionId = authHeader.substring(7); // Remove "Bearer "

        // Delete token from Redis
        redisUtil.deleteToken(sessionId);

        return ResponseEntity.ok("Logged out successfully");
    }

}

package com.example.User.Service.controllers;

import com.example.User.Service.Utils.CookieUtil;
import com.example.User.Service.Utils.JwtUtil;
import com.example.User.Service.Models.User;
import com.example.User.Service.Repository.UserRepo;
import com.example.User.Service.services.userService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody User user, HttpServletResponse response){

        if(!userRepo.existsByEmail(user.getEmail())){
            return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body("User not found");
        }

        User theUser = userRepo.findByEmail(user.getEmail());

        System.out.println("Email : " + user.getEmail());
        System.out.println("Password : " + user.getPassword());
        System.out.println("==================================");
        System.out.println("Email : " + theUser.getEmail());
        System.out.println("Password : " + theUser.getPassword());

        if(!passwordEncoder.matches(user.getPassword(), theUser.getPassword())){

            return ResponseEntity.status(HttpStatus.SC_UNAUTHORIZED).body("Unauthorized");
        }


        String token = jwtUtil.generateToken(user.getEmail(), user.getPassword());
        CookieUtil.addCookie(response, JwtUtil.TOKEN_NAME, token);

        return new ResponseEntity<>(user, org.springframework.http.HttpStatus.OK);



    }
}

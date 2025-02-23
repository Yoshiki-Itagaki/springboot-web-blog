package com.photo.myblog.controller;

import com.photo.myblog.entity.User;
import com.photo.myblog.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request){
        String token = authService.login(request.get("username"), request.get("password"));
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user){
        User newUser = authService.register(user);
        return ResponseEntity.ok(newUser);
    }


}

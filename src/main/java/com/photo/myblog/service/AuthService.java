package com.photo.myblog.service;

import com.nimbusds.jose.JOSEException;
import com.photo.myblog.entity.User;
import com.photo.myblog.repository.UserRepository;
import com.photo.myblog.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String login(String username, String password){

        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            // Get user details and generate JWT
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return jwtTokenProvider.generateToken(userDetails.getUsername());

        } catch(JOSEException e){
            throw new RuntimeException("Error generating JWT token", e);
        }
    }

    public User register(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}

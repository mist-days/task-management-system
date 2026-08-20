package com.mistdays.taskmanagementsystem.service;

import com.mistdays.taskmanagementsystem.dto.auth.LoginRequest;
import com.mistdays.taskmanagementsystem.dto.auth.LoginResponse;
import com.mistdays.taskmanagementsystem.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.mistdays.taskmanagementsystem.dto.user.UserCreateRequest;
import com.mistdays.taskmanagementsystem.dto.user.UserResponse;
import com.mistdays.taskmanagementsystem.entity.User;
import com.mistdays.taskmanagementsystem.mapper.UserMapper;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthService(AuthenticationManager authenticationManager,
                       UserDetailsService userDetailsService,
                       JwtService jwtService,
                       UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    public UserResponse register(UserCreateRequest request) {

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        User savedUser = userService.createUser(user);

        return UserMapper.toResponse(savedUser);
    }

    public LoginResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String jwtToken = jwtService.generateToken(userDetails);

        return new LoginResponse(jwtToken);
    }
}
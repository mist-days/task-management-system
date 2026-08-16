package com.mistdays.taskmanagementsystem.controller;

import com.mistdays.taskmanagementsystem.entity.User;
import com.mistdays.taskmanagementsystem.mapper.UserMapper;
import com.mistdays.taskmanagementsystem.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import com.mistdays.taskmanagementsystem.dto.user.UserCreateRequest;
import com.mistdays.taskmanagementsystem.dto.user.UserResponse;
import com.mistdays.taskmanagementsystem.dto.user.UserUpdateRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponse createUser(@Valid @RequestBody UserCreateRequest request) {

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        User savedUser = userService.createUser(user);

        return UserMapper.toResponse(savedUser);
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {
        List<User> users = userService.findAllUsers();
        List<UserResponse> responses = new ArrayList<>();
        for (User user : users){
            responses.add(UserMapper.toResponse(user));
        }
        return responses;
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {

        User user = userService.findUserById(id);

        return UserMapper.toResponse(user);
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateRequest request) {
        User updatedUser = userService.updateUser(id, request);

        return UserMapper.toResponse(updatedUser);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
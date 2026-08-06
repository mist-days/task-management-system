package com.mistdays.taskmanagementsystem.controller;

import com.mistdays.taskmanagementsystem.entity.User;
import com.mistdays.taskmanagementsystem.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;
import com.mistdays.taskmanagementsystem.dto.UserCreateRequest;
import com.mistdays.taskmanagementsystem.dto.UserResponse;
import com.mistdays.taskmanagementsystem.dto.UserUpdateRequest;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @PostMapping
//    public User createUser(@RequestBody User user) {
//        return userService.createUser(user);
//    }
    @PostMapping
    public UserResponse createUser(@RequestBody UserCreateRequest request) {

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        User savedUser = userService.createUser(user);

        UserResponse response = new UserResponse();
        response.setId(savedUser.getId());
        response.setUsername(savedUser.getUsername());
        response.setEmail(savedUser.getEmail());
        return response;

    }

    @GetMapping
    public List<UserResponse> getAllUsers() {
        List<User> users = userService.findAllUsers();
        List<UserResponse> responses = new ArrayList<>();
        for (User user : users){
            UserResponse response = new UserResponse();
            response.setId(user.getId());
            response.setUsername(user.getUsername());
            response.setEmail(user.getEmail());

            responses.add(response);
        }
        return responses;
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {

        User user = userService.findUserById(id);
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());

        return response;
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest request) {
        User existingUser = userService.findUserById(id);
        existingUser.setPassword(request.getPassword());
        existingUser.setUsername(request.getUsername());
        existingUser.setEmail(request.getEmail());
        User updatedUser = userService.updateUser(id, existingUser);

        UserResponse response = new UserResponse();
        response.setId(updatedUser.getId());
        response.setUsername(updatedUser.getUsername());
        response.setEmail(updatedUser.getEmail());

        return response;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }



}
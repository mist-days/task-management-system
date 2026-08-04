package com.mistdays.taskmanagementsystem.controller;

import com.mistdays.taskmanagementsystem.entity.User;
import com.mistdays.taskmanagementsystem.service.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
//import com.mistdays.taskmanagementsystem.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.findUserById(id);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }



}
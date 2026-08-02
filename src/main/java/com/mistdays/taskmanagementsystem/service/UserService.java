package com.mistdays.taskmanagementsystem.service;

import com.mistdays.taskmanagementsystem.entity.User;
import com.mistdays.taskmanagementsystem.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {

        user.setCreatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    public List<User> findAllUsers() {

        return userRepository.findAll();

    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }
}
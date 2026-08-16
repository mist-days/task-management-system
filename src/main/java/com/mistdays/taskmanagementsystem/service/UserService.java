package com.mistdays.taskmanagementsystem.service;

import com.mistdays.taskmanagementsystem.dto.user.UserUpdateRequest;
import com.mistdays.taskmanagementsystem.entity.User;
import com.mistdays.taskmanagementsystem.exception.ResourceNotFoundException;
import com.mistdays.taskmanagementsystem.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    public User updateUser(Long id, UserUpdateRequest request) {
        User existingUser = findUserById(id);
        existingUser.setUsername(request.getUsername());
        existingUser.setEmail(request.getEmail());
        existingUser.setPassword(request.getPassword());

        return userRepository.save(existingUser);
    }

    public void deleteUser(Long id) {
        User existingUser = findUserById(id);
        userRepository.delete(existingUser);
    }
}
package com.mistdays.taskmanagementsystem.mapper;

import com.mistdays.taskmanagementsystem.dto.UserResponse;
import com.mistdays.taskmanagementsystem.entity.User;

public class UserMapper {

    public static UserResponse toResponse(User user) {

        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        return response;

    }
}

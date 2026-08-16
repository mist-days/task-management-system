package com.mistdays.taskmanagementsystem.mapper;

import com.mistdays.taskmanagementsystem.dto.task.TaskResponse;
import com.mistdays.taskmanagementsystem.entity.Task;

public class TaskMapper {
    public static TaskResponse toResponse(Task task) {

        TaskResponse response = new TaskResponse();

        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setStatus(task.getStatus());
        response.setDeadline(task.getDeadline());
        response.setCreatedAt(task.getCreatedAt());
        if (task.getUser() != null) {
            response.setUserId(task.getUser().getId());
        }

        return response;
    }
}

package com.mistdays.taskmanagementsystem.service;

import com.mistdays.taskmanagementsystem.dto.TaskCreateRequest;
import com.mistdays.taskmanagementsystem.dto.TaskUpdateRequest;
import com.mistdays.taskmanagementsystem.entity.Task;
import com.mistdays.taskmanagementsystem.entity.User;
import com.mistdays.taskmanagementsystem.exception.TaskNotFoundException;
import com.mistdays.taskmanagementsystem.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    public TaskService(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    public Task createTask(TaskCreateRequest request) {
        User user = userService.findUserById(request.getUserId());

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setDeadline(request.getDeadline());
        task.setCreatedAt(LocalDateTime.now());
        task.setUser(user);

        return taskRepository.save(task);
    }

    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }

    public Task findTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task updateTask(Long id, TaskUpdateRequest request) {
        Task existingTask = findTaskById(id);

        existingTask.setTitle(request.getTitle());
        existingTask.setDescription(request.getDescription());
        existingTask.setStatus(request.getStatus());
        existingTask.setDeadline(request.getDeadline());

        return taskRepository.save(existingTask);
    }

    public void deleteTask(Long id) {
        Task existingTask = findTaskById(id);
        taskRepository.delete(existingTask);
    }
}
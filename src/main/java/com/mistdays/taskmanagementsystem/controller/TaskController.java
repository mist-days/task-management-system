package com.mistdays.taskmanagementsystem.controller;

import com.mistdays.taskmanagementsystem.dto.TaskUpdateRequest;
import com.mistdays.taskmanagementsystem.dto.TaskCreateRequest;
import com.mistdays.taskmanagementsystem.dto.TaskResponse;
import com.mistdays.taskmanagementsystem.entity.Task;
import com.mistdays.taskmanagementsystem.mapper.TaskMapper;
import com.mistdays.taskmanagementsystem.service.TaskService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

/**
 * REST controller for managing tasks.
 * Provides endpoints for creating, retrieving, updating, and deleting tasks.
 **/
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse createTask(@Valid @RequestBody TaskCreateRequest request) {
        Task task = taskService.createTask(request);
        return TaskMapper.toResponse(task);
    }

    @GetMapping
    public List<TaskResponse> getAllTasks() {
        List<Task> tasks = taskService.findAllTasks();
        List<TaskResponse> responses = new ArrayList<>();

        for (Task task : tasks) {
            responses.add(TaskMapper.toResponse(task));
        }

        return responses;
    }

    @GetMapping("/{id}")
    public TaskResponse getTaskById(@PathVariable Long id) {
        Task task = taskService.findTaskById(id);
        return TaskMapper.toResponse(task);
    }

    @PutMapping("/{id}")
    public TaskResponse updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskUpdateRequest request) {

        Task updatedTask = taskService.updateTask(id, request);
        return TaskMapper.toResponse(updatedTask);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}
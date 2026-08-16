package com.mistdays.taskmanagementsystem.mapper;

import com.mistdays.taskmanagementsystem.dto.task.TaskResponse;
import com.mistdays.taskmanagementsystem.entity.Task;
import com.mistdays.taskmanagementsystem.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

//import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

class TaskMapperTest {

    @Test
    void shouldMapTaskToTaskResponse() {
        // Arrange
        User user = new User();
        user.setId(1L);

        Task task = new Task();
        task.setId(10L);
        task.setTitle("Learn Spring Boot");
        task.setDescription("Study DTO and Mapper");
        task.setStatus("TODO");
        task.setDeadline(LocalDate.of(2026, 8, 15));
        task.setCreatedAt(LocalDateTime.of(2026, 8, 15, 10, 15, 30));
        task.setUser(user);

        // Act
        TaskResponse response = TaskMapper.toResponse(task);

        // Assert
        assertNotNull(response);
        assertEquals(10L, response.getId());
        assertEquals("Learn Spring Boot", response.getTitle());
        assertEquals("Study DTO and Mapper", response.getDescription());
        assertEquals("TODO", response.getStatus());
        assertEquals(LocalDate.of(2026, 8, 15), response.getDeadline());
        assertEquals(LocalDateTime.of(2026, 8, 15, 10, 15, 30), response.getCreatedAt());
        assertEquals(1L, response.getUserId());
    }

    @Test
    void shouldMapTaskToTaskResponseWhenUserIsNull() {
        // Arrange (Edge Case: verifying null-safety of TaskMapper)
        Task task = new Task();
        task.setId(10L);
        task.setTitle("Task without user");
        task.setUser(null);

        // Act
        TaskResponse response = TaskMapper.toResponse(task);

        // Assert
        assertNotNull(response);
        assertNull(response.getUserId());
    }
}
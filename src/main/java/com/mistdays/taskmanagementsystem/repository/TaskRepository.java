package com.mistdays.taskmanagementsystem.repository;

import com.mistdays.taskmanagementsystem.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

}

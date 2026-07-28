package com.mistdays.taskmanagementsystem.repository;

import com.mistdays.taskmanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
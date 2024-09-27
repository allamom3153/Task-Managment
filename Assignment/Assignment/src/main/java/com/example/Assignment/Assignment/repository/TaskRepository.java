package com.example.Assignment.Assignment.repository;

import com.example.Assignment.Assignment.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.*;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(Long userId);
    List<Task> findByStatus(String status);
    List<Task> findByPriority(String priority);
    List<Task> findByTitleContainingOrDescriptionContaining(String title, String description);
    Page<Task> findByUserId(Long userId, Pageable pageable);
}

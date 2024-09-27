package com.example.Assignment.Assignment.services;


import com.example.Assignment.Assignment.entity.Task;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.List;

public interface TaskService {
    Task createTask(Task task);
    Task getTask(Long id);
    List<Task> getTasks(String status, String priority, String search);
    Task updateTask(Long id, Task task);
    void deleteTask(Long id);
    Page<Task> getTasks(Long userId, String status, String priority, String search, Pageable pageable);
}

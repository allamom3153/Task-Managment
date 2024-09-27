package com.example.Assignment.Assignment.controllers;

import com.example.Assignment.Assignment.entity.Task;
import com.example.Assignment.Assignment.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public Task getTask(@PathVariable Long id) {
        return taskService.getTask(id);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public List<Task> getTasks(@RequestParam(required = false) String status,
                               @RequestParam(required = false) String priority,
                               @RequestParam(required = false) String search) {
        return taskService.getTasks(status, priority, search);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task task) {
        return taskService.updateTask(id, task);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public Page<Task> getTasks(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return taskService.getTasks(userId, status, priority, search, (Pageable) PageRequest.of(page, size));
    }

}


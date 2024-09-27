package com.example.Assignment.Assignment.services;


import com.example.Assignment.Assignment.entity.Task;
import com.example.Assignment.Assignment.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task getTask(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    @Override
    public List<Task> getTasks(String status, String priority, String search) {
        if (search != null) {
            return taskRepository.findByTitleContainingOrDescriptionContaining(search, search);
        }
        if (status != null) {
            return taskRepository.findByStatus(status);
        }
        if (priority != null) {
            return taskRepository.findByPriority(priority);
        }
        return taskRepository.findAll();
    }

    @Override
    public Task updateTask(Long id, Task task) {
        Task existingTask = getTask(id);
        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setStatus(task.getStatus());
        existingTask.setPriority(task.getPriority());
        existingTask.setDueDate(task.getDueDate());
        return taskRepository.save(existingTask);
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public Page<Task> getTasks(Long userId, String status, String priority, String search, Pageable pageable) {
        if (userId != null) {
            return taskRepository.findByUserId(userId, pageable);
        }
        // Implement other filtering options and default behavior if needed
        return taskRepository.findAll((org.springframework.data.domain.Pageable) pageable); // Fallback to find all tasks
    }
}

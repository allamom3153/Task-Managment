package com.example.Assignment.Assignment;


import com.example.Assignment.Assignment.controllers.TaskController;
import com.example.Assignment.Assignment.entity.Task;
import com.example.Assignment.Assignment.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TaskControllerTest {

    @InjectMocks
    private TaskController taskController;

    @Mock
    private TaskService taskService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    @WithMockUser(roles = "USER")
    @Test
    void createTask() throws Exception {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("New Task");

        when(taskService.createTask(any(Task.class))).thenReturn(task);

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"New Task\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("New Task"));
    }

    @WithMockUser(roles = "USER")
    @Test
    void getTask() throws Exception {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Existing Task");

        when(taskService.getTask(anyLong())).thenReturn(task);

        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Existing Task"));
    }

    @WithMockUser(roles = "USER")
    @Test
    void getTasks() throws Exception {
        Task task1 = new Task();
        task1.setId(1L);
        task1.setTitle("Task 1");

        Task task2 = new Task();
        task2.setId(2L);
        task2.setTitle("Task 2");

        List<Task> taskList = new ArrayList<>();
        taskList.add(task1);
        taskList.add(task2);

        when(taskService.getTasks(null, null, null)).thenReturn(taskList);

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    @WithMockUser(roles = "USER")
    @Test
    void updateTask() throws Exception {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Updated Task");

        when(taskService.updateTask(anyLong(), any(Task.class))).thenReturn(task);

        mockMvc.perform(put("/api/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Updated Task\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Updated Task"));
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void deleteTask() throws Exception {
        doNothing().when(taskService).deleteTask(anyLong());

        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isNoContent());

        verify(taskService, times(1)).deleteTask(1L);
    }

    @WithMockUser(roles = "USER")
    @Test
    void getTasksWithPagination() throws Exception {
        Task task1 = new Task();
        task1.setId(1L);
        task1.setTitle("Task 1");

        Task task2 = new Task();
        task2.setId(2L);
        task2.setTitle("Task 2");

        List<Task> taskList = new ArrayList<>();
        taskList.add(task1);
        taskList.add(task2);

        Page<Task> taskPage = new PageImpl<>(taskList, PageRequest.of(0, 10), taskList.size());
        when(taskService.getTasks(any(), any(), any(), any(), any())).thenReturn(taskPage);

        mockMvc.perform(get("/api/tasks?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[1].id").value(2));
    }
}

package com.example.Assignment.Assignment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password; // Hashed password

    @OneToMany(mappedBy = "user")
    private List<Task> tasks;

    // Getters and Setters
}

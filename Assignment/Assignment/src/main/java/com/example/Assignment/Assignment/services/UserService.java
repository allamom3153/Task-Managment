package com.example.Assignment.Assignment.services;


import com.example.Assignment.Assignment.entity.User;

public interface UserService {
    User register(User user);
    String login(String username, String password);
}

package com.example.Assignment.Assignment.services;


import com.example.Assignment.Assignment.entity.User;
import com.example.Assignment.Assignment.repository.UserRepository;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private JwtUtil jwtUtil;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public String login(String username, String password) {
        return "";
    }

//    @Override
//    public String login(String username, String password) {
//        User existingUser = userRepository.findByUsername(username);
//        if (existingUser != null && passwordEncoder.matches(password, existingUser.getPassword())) {
//            return jwtUtil.generateToken(username);
//        }
//        throw new RuntimeException("Invalid credentials");
//    }
}

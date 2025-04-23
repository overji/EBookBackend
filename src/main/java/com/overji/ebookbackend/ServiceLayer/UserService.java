package com.overji.ebookbackend.ServiceLayer;

import org.springframework.security.crypto.password.PasswordEncoder;
import com.overji.ebookbackend.EntityLayer.User;
import com.overji.ebookbackend.DataAccessLayer.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        Optional<User> ans = userRepository.findById(id);
        return ans.orElse(null);
    }

    public void addUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        } catch (Exception e) {
            // 捕获异常并抛出自定义异常
            throw new RuntimeException("Failed to save user: " + e.getMessage(), e);
        }
    }

    public User authenticateUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(passwordEncoder.encode(password))) {
            return user;
        }
        return null;
    }
}
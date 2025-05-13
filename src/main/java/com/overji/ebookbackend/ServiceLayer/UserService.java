package com.overji.ebookbackend.ServiceLayer;

import com.overji.ebookbackend.DataAccessLayer.UserAuthRepository;
import com.overji.ebookbackend.EntityLayer.UserAuth;
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
    private final UserAuthRepository userAuthRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserAuthRepository userAuthRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userAuthRepository = userAuthRepository;
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
            // 初始化 UserAuth 并设置关联
            UserAuth userAuth = new UserAuth();
            userAuth.setUser(user); // 设置 UserAuth 的 user 属性
            userAuth.setPassword(passwordEncoder.encode(user.getPassword())); // 加密密码

            // 设置 User 的 auth 属性
            user.setAuth(userAuth);

            // 保存 User 实体（级联保存 UserAuth）
            userRepository.save(user);
        } catch (Exception e) {
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

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void updatePasswordByUsername(String username, String password) {
        if (username == null) {
            throw new IllegalArgumentException("Unlogged in!");
        }
        if (password == null) {
            throw new IllegalArgumentException("New Password cannot be null");
        }
        try {
            userRepository.updatePasswordByUsername(username, passwordEncoder.encode(password));
        } catch (Exception e) {
            // 捕获异常并抛出自定义异常
            throw new RuntimeException("Failed to update password: " + e.getMessage(), e);
        }
    }

    public void updateIntroductionByUsername(String username, String introduction) {
        if (username == null) {
            throw new IllegalArgumentException("Unlogged in!");
        }
        if (introduction == null) {
            throw new IllegalArgumentException("New Introduction cannot be null");
        }
        try {
            userRepository.updateIntroductionByUsername(username, introduction);
        } catch (Exception e) {
            // 捕获异常并抛出自定义异常
            throw new RuntimeException("Failed to update introduction: " + e.getMessage(), e);
        }
    }

    public void updateAvatarByUsername(String username, String avatarPath) {
        if (username == null) {
            throw new IllegalArgumentException("Unlogged in!");
        }
        if (avatarPath == null) {
            throw new IllegalArgumentException("New Avatar cannot be null");
        }
        try {
            userRepository.updateAvatarByUsername(username, avatarPath);
        } catch (Exception e) {
            // 捕获异常并抛出自定义异常
            throw new RuntimeException("Failed to update avatar: " + e.getMessage(), e);
        }
    }
}
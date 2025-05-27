package com.overji.ebookbackend.serviceLayer.Implementation;

import com.overji.ebookbackend.daoLayer.UserAuthDAO;
import com.overji.ebookbackend.daoLayer.UserDAO;
import com.overji.ebookbackend.entityLayer.User;
import com.overji.ebookbackend.entityLayer.UserAuth;
import com.overji.ebookbackend.serviceLayer.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;
    private final UserAuthDAO userAuthDAO;

    public UserServiceImpl(UserDAO userDAO, PasswordEncoder passwordEncoder, UserAuthDAO userAuthDAO) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
        this.userAuthDAO = userAuthDAO;
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> ans = userDAO.findById(id);
        return ans.orElse(null);
    }

    @Override
    public void addUser(User user) {
        try {
            UserAuth userAuth = new UserAuth();
            userAuth.setUser(user);
            userAuth.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setAuth(userAuth);

            userDAO.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save user: " + e.getMessage(), e);
        }
    }

    @Override
    public User authenticateUser(String username, String password) {
        User user = userDAO.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    public void updatePasswordByUsername(String username, String password) {
        if (username == null) {
            throw new IllegalArgumentException("Unlogged in!");
        }
        if (password == null) {
            throw new IllegalArgumentException("New Password cannot be null");
        }
        try {
            userDAO.updatePasswordByUsername(username, passwordEncoder.encode(password));
        } catch (Exception e) {
            throw new RuntimeException("Failed to update password: " + e.getMessage(), e);
        }
    }

    @Override
    public void updateIntroductionByUsername(String username, String introduction) {
        if (username == null) {
            throw new IllegalArgumentException("Unlogged in!");
        }
        if (introduction == null) {
            throw new IllegalArgumentException("New Introduction cannot be null");
        }
        try {
            userDAO.updateIntroductionByUsername(username, introduction);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update introduction: " + e.getMessage(), e);
        }
    }

    @Override
    public void updateAvatarByUsername(String username, String avatarPath) {
        if (username == null) {
            throw new IllegalArgumentException("Unlogged in!");
        }
        if (avatarPath == null) {
            throw new IllegalArgumentException("New Avatar cannot be null");
        }
        try {
            userDAO.updateAvatarByUsername(username, avatarPath);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update avatar: " + e.getMessage(), e);
        }
    }

    @Override
    public void updateDisabledById(Long id, boolean isDisabled){
        try {
            userDAO.updateDisabledById(id, isDisabled);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update disabled status: " + e.getMessage(), e);
        }
    }
}
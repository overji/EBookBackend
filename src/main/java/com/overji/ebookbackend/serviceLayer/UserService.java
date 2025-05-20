package com.overji.ebookbackend.serviceLayer;

import com.overji.ebookbackend.entityLayer.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUserById(Long id);

    void addUser(User user);

    User authenticateUser(String username, String password);

    User getUserByUsername(String username);

    void updatePasswordByUsername(String username, String password);

    void updateIntroductionByUsername(String username, String introduction);

    void updateAvatarByUsername(String username, String avatarPath);
}
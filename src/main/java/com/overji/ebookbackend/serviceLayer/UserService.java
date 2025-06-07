package com.overji.ebookbackend.serviceLayer;

import com.overji.ebookbackend.entityLayer.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    Page<User> getAllUsers(Pageable pageable);

    User getUserById(Long id);

    void addUser(User user);

    User authenticateUser(String username, String password);

    User getUserByUsername(String username);

    void updatePasswordByUsername(String username, String password);

    void updateIntroductionByUsername(String username, String introduction);

    void updateAvatarByUsername(String username, String avatarPath);

    void updateDisabledById(Long id, boolean isDisabled);
}
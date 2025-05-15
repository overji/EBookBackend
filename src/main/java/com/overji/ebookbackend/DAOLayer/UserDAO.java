package com.overji.ebookbackend.DAOLayer;

import com.overji.ebookbackend.EntityLayer.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
    List<User> findAll();

    Optional<User> findById(Long id);

    User findByUsername(String username);

    void save(User user);

    void updatePasswordByUsername(String username, String password);

    void updateIntroductionByUsername(String username, String introduction);

    void updateAvatarByUsername(String username, String avatar);
}
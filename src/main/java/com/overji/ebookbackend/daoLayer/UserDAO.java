package com.overji.ebookbackend.daoLayer;

import com.overji.ebookbackend.entityLayer.User;

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

    void updateDisabledById(Long id, boolean isDisabled);
}
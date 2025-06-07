package com.overji.ebookbackend.daoLayer;

import com.overji.ebookbackend.entityLayer.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserDAO {
    Page<User> findAll(Pageable pageable);

    Optional<User> findById(Long id);

    User findByUsername(String username);

    void save(User user);

    void updatePasswordByUsername(String username, String password);

    void updateIntroductionByUsername(String username, String introduction);

    void updateAvatarByUsername(String username, String avatar);

    void updateDisabledById(Long id, boolean isDisabled);
}
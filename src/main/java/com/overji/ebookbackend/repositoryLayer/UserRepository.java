package com.overji.ebookbackend.repositoryLayer;

import com.overji.ebookbackend.entityLayer.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    Optional<User> findById(Long id);
    void deleteById(Long id);
    //根据username和password修改密码
    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.auth.password = ?2 WHERE u.username = ?1")
    void updatePasswordByUsername(String username, String password);
    //根据username和introduction修改简介
    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.introduction = ?2 WHERE u.username = ?1")
    void updateIntroductionByUsername(String username, String introduction);
    //根据username和avatar修改头像
    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.avatar = ?2 WHERE u.username = ?1")
    void updateAvatarByUsername(String username, String avatar);
}
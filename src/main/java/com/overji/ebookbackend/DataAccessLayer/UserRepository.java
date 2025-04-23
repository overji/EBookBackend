package com.overji.ebookbackend.DataAccessLayer;

import com.overji.ebookbackend.EntityLayer.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    Optional<User> findById(Long id);
    void deleteById(Long id);
}
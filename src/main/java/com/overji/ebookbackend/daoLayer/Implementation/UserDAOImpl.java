package com.overji.ebookbackend.daoLayer.Implementation;

import com.overji.ebookbackend.daoLayer.UserDAO;
import com.overji.ebookbackend.entityLayer.User;
import com.overji.ebookbackend.repositoryLayer.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDAOImpl implements UserDAO {
    private final UserRepository userRepository;

    public UserDAOImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void updatePasswordByUsername(String username, String password) {
        userRepository.updatePasswordByUsername(username, password);
    }

    @Override
    public void updateIntroductionByUsername(String username, String introduction) {
        userRepository.updateIntroductionByUsername(username, introduction);
    }

    @Override
    public void updateAvatarByUsername(String username, String avatar) {
        userRepository.updateAvatarByUsername(username, avatar);
    }

    @Override
    public void updateDisabledById(Long id, boolean isDisabled){
        userRepository.updateDisabledById(id, isDisabled);
    }
}
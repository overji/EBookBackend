package com.overji.ebookbackend.DAOLayer.imple;

import com.overji.ebookbackend.DAOLayer.UserAuthDAO;
import com.overji.ebookbackend.EntityLayer.UserAuth;
import com.overji.ebookbackend.RepositoryLayer.UserAuthRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserAuthDAOImpl implements UserAuthDAO {
    private final UserAuthRepository userAuthRepository;

    public UserAuthDAOImpl(UserAuthRepository userAuthRepository) {
        this.userAuthRepository = userAuthRepository;
    }

    @Override
    public void save(UserAuth userAuth) {
        userAuthRepository.save(userAuth);
    }
}
package com.overji.ebookbackend.daoLayer.Implementation;

import com.overji.ebookbackend.daoLayer.UserAuthDAO;
import com.overji.ebookbackend.entityLayer.UserAuth;
import com.overji.ebookbackend.repositoryLayer.UserAuthRepository;
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
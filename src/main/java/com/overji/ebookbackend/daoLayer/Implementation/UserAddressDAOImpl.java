package com.overji.ebookbackend.daoLayer.Implementation;

import com.overji.ebookbackend.daoLayer.UserAddressDAO;
import com.overji.ebookbackend.entityLayer.UserAddress;
import com.overji.ebookbackend.repositoryLayer.UserAddressRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserAddressDAOImpl implements UserAddressDAO {
    private final UserAddressRepository userAddressRepository;

    public UserAddressDAOImpl(UserAddressRepository userAddressRepository) {
        this.userAddressRepository = userAddressRepository;
    }

    @Override
    public void deleteById(Long id) {
        userAddressRepository.deleteById(id);
    }

    @Override
    public UserAddress findByUserAddrIdAndUserId(Long userAddrId, Long userId) {
        return userAddressRepository.findByUserAddrIdAndUserId(userAddrId, userId);
    }

    @Override
    public void deleteByUserAddrIdAndUserId(Long userAddrId, Long userId) {
        userAddressRepository.deleteByUserAddrIdAndUserId(userAddrId, userId);
    }

    @Override
    public List<UserAddress> findByUserId(Long userId) {
        return userAddressRepository.findByUserId(userId);
    }

    @Override
    public UserAddress save(UserAddress userAddress) {
        return userAddressRepository.save(userAddress);
    }
}
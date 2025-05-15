package com.overji.ebookbackend.DAOLayer.imple;

import com.overji.ebookbackend.DAOLayer.UserAddressDAO;
import com.overji.ebookbackend.EntityLayer.UserAddress;
import com.overji.ebookbackend.RepositoryLayer.UserAddressRepository;
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
    public Long findMaxUserAddrIdByUserId(Long userId) {
        return userAddressRepository.findMaxUserAddrIdByUserId(userId);
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
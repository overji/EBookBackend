package com.overji.ebookbackend.DAOLayer;

import com.overji.ebookbackend.EntityLayer.UserAddress;

import java.util.List;

public interface UserAddressDAO {
    void deleteById(Long id);

    Long findMaxUserAddrIdByUserId(Long userId);

    UserAddress findByUserAddrIdAndUserId(Long userAddrId, Long userId);

    void deleteByUserAddrIdAndUserId(Long userAddrId, Long userId);

    List<UserAddress> findByUserId(Long userId);

    UserAddress save(UserAddress userAddress);
}
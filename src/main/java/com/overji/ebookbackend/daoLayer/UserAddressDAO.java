package com.overji.ebookbackend.daoLayer;

import com.overji.ebookbackend.entityLayer.UserAddress;

import java.util.List;

public interface UserAddressDAO {
    void deleteById(Long id);

    UserAddress findByUserAddrIdAndUserId(Long userAddrId, Long userId);

    void deleteByUserAddrIdAndUserId(Long userAddrId, Long userId);

    List<UserAddress> findByUserId(Long userId);

    UserAddress save(UserAddress userAddress);
}
package com.overji.ebookbackend.serviceLayer;

import com.overji.ebookbackend.entityLayer.UserAddress;

import java.util.List;

public interface UserAddressService {
    UserAddress saveUserAddress(String username, String address, String phone, String receiver);

    void deleteUserAddress(String username, Long userAddrId);

    List<UserAddress> getUserAddresses(String username);
}
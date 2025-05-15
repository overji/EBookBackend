package com.overji.ebookbackend.ServiceLayer;

import com.overji.ebookbackend.EntityLayer.UserAddress;

import java.util.List;

public interface UserAddressService {
    UserAddress saveUserAddress(String username, String address, String phone, String receiver);

    void deleteUserAddress(String username, Long userAddrId);

    List<UserAddress> getUserAddresses(String username);
}
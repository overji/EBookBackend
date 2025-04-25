package com.overji.ebookbackend.ServiceLayer;

import com.overji.ebookbackend.DataAccessLayer.UserAddressRepository;
import com.overji.ebookbackend.EntityLayer.UserAddress;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAddressService {

    private final UserAddressRepository userAddressRepository;
    private final UserService userService;

    public UserAddressService(UserAddressRepository userAddressRepository, UserService userService) {
        this.userAddressRepository = userAddressRepository;
        this.userService = userService;
    }

    @Transactional
    public UserAddress saveUserAddress(String username, String address, String phone, String receiver) {
        Long userId = userService.getUserByUsername(username).getId();
        Long maxUserAddrId = userAddressRepository.findMaxUserAddrIdByUserId(userId);
        UserAddress userAddress = new UserAddress();
        userAddress.setUserAddrId(maxUserAddrId == null ? 0 : maxUserAddrId + 1);
        userAddress.setUser(userService.getUserByUsername(username));
        userAddress.setAddress(address);
        userAddress.setPhone(phone);
        userAddress.setReceiver(receiver);
        return userAddressRepository.save(userAddress);
    }

    @Transactional
    public void deleteUserAddress(String username, Long userAddrId) {
        if(username == null){
            throw new IllegalArgumentException("Unlogged in!");
        }
        if(userAddrId == null){
            throw new IllegalArgumentException("User address ID cannot be null!");
        }
        Long userId = userService.getUserByUsername(username).getId();
        userAddressRepository.deleteByUserAddrIdAndUserId(userAddrId, userId);
    }

    public List<UserAddress> getUserAddresses(String username) {
        Long userId = userService.getUserByUsername(username).getId();
        return userAddressRepository.findByUserId(userId);
    }
}
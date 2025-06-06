package com.overji.ebookbackend.serviceLayer.Implementation;

import com.overji.ebookbackend.daoLayer.UserAddressDAO;
import com.overji.ebookbackend.entityLayer.UserAddress;
import com.overji.ebookbackend.serviceLayer.UserAddressService;
import com.overji.ebookbackend.serviceLayer.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAddressServiceImpl implements UserAddressService {

    private final UserAddressDAO userAddressDAO;
    private final UserService userService;

    public UserAddressServiceImpl(UserAddressDAO userAddressDAO, UserService userService) {
        this.userAddressDAO = userAddressDAO;
        this.userService = userService;
    }

    @Override
    @Transactional
    public UserAddress saveUserAddress(String username, String address, String phone, String receiver) {
        Long userId = userService.getUserByUsername(username).getId();
        UserAddress userAddress = new UserAddress();
        userAddress.setUser(userService.getUserByUsername(username));
        userAddress.setAddress(address);
        userAddress.setPhone(phone);
        userAddress.setReceiver(receiver);
        return userAddressDAO.save(userAddress);
    }

    @Override
    @Transactional
    public void deleteUserAddress(String username, Long userAddrId) {
        if (username == null) {
            throw new IllegalArgumentException("Unlogged in!");
        }
        if (userAddrId == null) {
            throw new IllegalArgumentException("User address ID cannot be null!");
        }
        Long userId = userService.getUserByUsername(username).getId();
        userAddressDAO.deleteByUserAddrIdAndUserId(userAddrId, userId);
    }

    @Override
    public List<UserAddress> getUserAddresses(String username) {
        Long userId = userService.getUserByUsername(username).getId();
        return userAddressDAO.findByUserId(userId);
    }
}
package com.overji.ebookbackend.ServiceLayer.imple;

import com.overji.ebookbackend.DAOLayer.UserAddressDAO;
import com.overji.ebookbackend.EntityLayer.UserAddress;
import com.overji.ebookbackend.ServiceLayer.UserAddressService;
import com.overji.ebookbackend.ServiceLayer.UserService;
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
        Long maxUserAddrId = userAddressDAO.findMaxUserAddrIdByUserId(userId);
        UserAddress userAddress = new UserAddress();
        userAddress.setUserAddrId(maxUserAddrId == null ? 0 : maxUserAddrId + 1);
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
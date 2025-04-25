package com.overji.ebookbackend.DataAccessLayer;

import com.overji.ebookbackend.EntityLayer.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserAddressRepository extends JpaRepository<UserAddress,Long> {
    List<UserAddress> findByUserId(Long userId);
    void deleteById(Long id);

    @Query("SELECT MAX(ua.userAddrId) FROM UserAddress ua WHERE ua.user.id = ?1")
    Long findMaxUserAddrIdByUserId(Long userId);

    @Query("SELECT ua FROM UserAddress ua WHERE ua.userAddrId = ?1 AND ua.user.id = ?2")
    UserAddress findByUserAddrIdAndUserId(Long userAddrId, Long userId);

    @Modifying
    @Query("DELETE FROM UserAddress ua WHERE ua.userAddrId = ?1 AND ua.user.id = ?2")
    void deleteByUserAddrIdAndUserId(Long userAddrId, Long userId);
}

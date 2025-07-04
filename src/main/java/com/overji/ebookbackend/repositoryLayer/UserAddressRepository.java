package com.overji.ebookbackend.repositoryLayer;

import com.overji.ebookbackend.entityLayer.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserAddressRepository extends JpaRepository<UserAddress,Long> {
    List<UserAddress> findByUserId(Long userId);
    void deleteById(Long id);

    @Query("SELECT ua FROM UserAddress ua WHERE ua.id = ?1 AND ua.user.id = ?2")
    UserAddress findByUserAddrIdAndUserId(Long userAddrId, Long userId);

    @Modifying
    @Query("DELETE FROM UserAddress ua WHERE ua.id = ?1 AND ua.user.id = ?2")
    void deleteByUserAddrIdAndUserId(Long userAddrId, Long userId);
}

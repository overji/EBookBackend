package com.overji.ebookbackend.DataAccessLayer;

import com.overji.ebookbackend.EntityLayer.Cart;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.util.Pair;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart,Long> {
    @Query("SELECT c FROM Cart c WHERE c.user.id = ?1")
    List<Cart> findByUserId(Long userId);
}

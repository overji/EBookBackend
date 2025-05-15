package com.overji.ebookbackend.RepositoryLayer;

import com.overji.ebookbackend.EntityLayer.Cart;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart,Long> {
    @Query("SELECT c FROM Cart c WHERE c.user.id = ?1")
    List<Cart> findByUserId(Long userId);

    @Query("SELECT c FROM Cart c WHERE c.user.id = ?1 AND c.userCartId = ?2")
    Cart findByUserIdAndUserCartId(Long userId, Long userCartId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Cart c WHERE c.user_cart_id = ?1 AND c.user_id = ?2",nativeQuery = true)
    void deleteByUserCartIdAndUserId(Long userCartId, Long userId);
}

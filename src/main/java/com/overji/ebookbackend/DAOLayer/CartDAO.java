package com.overji.ebookbackend.DAOLayer;

import com.overji.ebookbackend.EntityLayer.Cart;

import java.util.List;

public interface CartDAO {
    List<Cart> findByUserId(Long userId);
    Cart findByUserIdAndUserCartId(Long userId, Long userCartId);
    void deleteByUserCartIdAndUserId(Long userCartId, Long userId);
    void delete(Cart cart);
    void save(Cart cart);
}
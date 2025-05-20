package com.overji.ebookbackend.serviceLayer;

import com.overji.ebookbackend.entityLayer.User;

import java.util.List;
import java.util.Map;

public interface CartService {
    List<Map<String, Object>> getCartItems(User user);

    Map<String, Object> addToCart(Long bookId, User user, Long number);

    Map<String, Object> updateCart(Long cartItemId, Long number, User user);

    Map<String, Object> deleteFromCart(Long cartItemId, User user);
}
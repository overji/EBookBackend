package com.overji.ebookbackend.DAOLayer.imple;

import com.overji.ebookbackend.DAOLayer.CartDAO;
import com.overji.ebookbackend.EntityLayer.Cart;
import com.overji.ebookbackend.RepositoryLayer.CartRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartDAOImpl implements CartDAO {
    private final CartRepository cartRepository;

    public CartDAOImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public List<Cart> findByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    @Override
    public Cart findByUserIdAndUserCartId(Long userId, Long userCartId) {
        return cartRepository.findByUserIdAndUserCartId(userId, userCartId);
    }

    @Override
    public void deleteByUserCartIdAndUserId(Long userCartId, Long userId) {
        cartRepository.deleteByUserCartIdAndUserId(userCartId, userId);
    }

    @Override
    public void delete(Cart cart) { // 新增方法实现
        cartRepository.delete(cart);
    }

    @Override
    public void save(Cart cart) {
        cartRepository.save(cart);
    }
}
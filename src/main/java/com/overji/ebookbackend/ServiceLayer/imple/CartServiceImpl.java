package com.overji.ebookbackend.ServiceLayer.imple;

import com.overji.ebookbackend.DAOLayer.BookDAO;
import com.overji.ebookbackend.DAOLayer.CartDAO;
import com.overji.ebookbackend.DAOLayer.UserDAO;
import com.overji.ebookbackend.EntityLayer.Cart;
import com.overji.ebookbackend.EntityLayer.User;
import com.overji.ebookbackend.ServiceLayer.CartService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService {
    private final CartDAO cartDAO;
    private final BookDAO bookDAO;
    private final UserDAO userDAO;

    public CartServiceImpl(CartDAO cartDAO, BookDAO bookDAO, UserDAO userDAO) {
        this.cartDAO = cartDAO;
        this.bookDAO = bookDAO;
        this.userDAO = userDAO;
    }

    @Override
    public List<Map<String, Object>> getCartItems(User user) {
        List<Cart> cartItems = cartDAO.findByUserId(user.getId());
        return cartItems.stream().map(cart -> Map.of(
                "id", cart.getUserCartId(),
                "book", cart.getBook().toMap(),
                "number", cart.getNumber()
        )).toList();
    }

    @Override
    public Map<String, Object> addToCart(Long bookId, User user, Long number) {
        if (cartDAO.findByUserId(user.getId()).stream()
                .anyMatch(c -> c.getBook().getId().equals(bookId))) {
            return Map.of(
                    "message", "书本已经在购物车中",
                    "ok", false,
                    "data", Map.of()
            );
        }
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setBook(bookDAO.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found")));
        cart.setNumber(number);
        cart.setUserCartId(user.getCartId());
        cartDAO.save(cart);
        return Map.of(
                "message", "Book added to cart successfully",
                "ok", true,
                "data", Map.of()
        );
    }

    @Override
    public Map<String, Object> updateCart(Long cartItemId, Long number, User user) {
        Cart cart = cartDAO.findByUserIdAndUserCartId(user.getId(), cartItemId);
        cart.setNumber(number);
        cartDAO.save(cart);
        return Map.of(
                "message", "Cart updated successfully",
                "ok", true,
                "data", Map.of()
        );
    }

    @Override
    public Map<String, Object> deleteFromCart(Long cartItemId, User user) {
        cartDAO.deleteByUserCartIdAndUserId(cartItemId, user.getId());
        return Map.of(
                "message", "Book removed from cart successfully",
                "ok", true,
                "data", Map.of()
        );
    }
}
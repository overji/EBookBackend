package com.overji.ebookbackend.ServiceLayer;

import com.overji.ebookbackend.DataAccessLayer.BookRepository;
import com.overji.ebookbackend.DataAccessLayer.CartRepository;
import com.overji.ebookbackend.DataAccessLayer.UserRepository;
import com.overji.ebookbackend.EntityLayer.Cart;
import com.overji.ebookbackend.EntityLayer.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public List<Map<String, Object>> getCartItems(User user) {
        List<Cart> cartItems = cartRepository.findByUserId(user.getId());
        return cartItems.stream().map(cart-> Map.of(
                    "id",cart.getUserCartId(),
                    "book",cart.getBook().toMap(),
                    "number",cart.getNumber()
        )).toList();
    }

    public Map<String,Object> addToCart(Long bookId, User user) {
        if(cartRepository.findByUserId(user.getId()).stream()
                .anyMatch(c -> c.getBook().getId().equals(bookId))){
            return Map.of(
                    "message", "Book already in cart",
                    "ok",false,
                    "data",Map.of()
            );
        }
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setBook(bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found")));
        cart.setNumber(1L);
        cart.setUserCartId(user.getCartId());
        cartRepository.save(cart);
        return Map.of(
                "message", "Book added to cart successfully",
                "ok",true,
                "data",Map.of()
        );
    }

    public Map<String,Object> updateCart(Long cartItemId, Long number, User user) {
        Cart cart = cartRepository.findByUserId(user.getId()).stream()
                .filter(c -> c.getUserCartId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Book not found in cart"));
        cart.setNumber(number);
        cartRepository.save(cart);
        return Map.of(
                "message", "Cart updated successfully",
                "ok",true,
                "data",Map.of()
        );
    }

    public Map<String,Object> deleteFromCart(Long cartItemId, User user) {
        Cart cart = cartRepository.findByUserId(user.getId()).stream()
                .filter(c -> c.getUserCartId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Book not found in cart"));
        cartRepository.delete(cart);
        return Map.of(
                "message", "Book removed from cart successfully",
                "ok",true,
                "data",Map.of()
        );
    }
}

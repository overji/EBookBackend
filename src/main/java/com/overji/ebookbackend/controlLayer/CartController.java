package com.overji.ebookbackend.controlLayer;


import com.overji.ebookbackend.entityLayer.User;
import com.overji.ebookbackend.serviceLayer.CartService;
import com.overji.ebookbackend.serviceLayer.UserService;
import com.overji.ebookbackend.utils.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/*
* this controller is used to handle the cart related requests
* it includes:
* 1. get all items in the cart
* 2. add item to the cart
* 3. update item in the cart
* 4. delete item from the cart
* 5. get all items in the cart by user id
* 6. add item to the cart by user id
* 7. update item in the cart by user id
* 8. delete item from the cart by user id
 */

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;
    private final UserService userService;

    // Constructor-based dependency injection
    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @GetMapping("")
    public Object getCartItems(
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        // If authenticated, proceed to get the cart items
        try {
            String username = UserContext.getCurrentUsername(request);
            User user = userService.getUserByUsername(username);
            return cartService.getCartItems(user);
        } catch (Exception e) {
            response.setStatus(400);
            return Map.of(
                    "ok", false,
                    "message", e.getMessage(),
                    "data", Map.of()
            );
        }
    }

    @PutMapping("")
    public Map<String, Object> addToCart(
            @PathParam("bookId") Long bookId,
            @PathParam("number") Long number,
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        try {
            // insert the book into the cart
            String username = UserContext.getCurrentUsername(request);
            User user = userService.getUserByUsername(username);
            return cartService.addToCart(bookId, user, number);
        } catch (Exception e) {
            response.setStatus(400);
            return Map.of(
                    "ok", false,
                    "message", e.getMessage(),
                    "data", Map.of()
            );
        }
    }

    @PutMapping("/{id}")
    public Map<String, Object> updateCart(
            @PathVariable Long id,
            @PathParam("number") Long number,
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        try {
            // update the book in the cart
            String username = UserContext.getCurrentUsername(request);
            User user = userService.getUserByUsername(username);
            return cartService.updateCart(id, number, user);
        } catch (Exception e) {
            response.setStatus(400);
            return Map.of(
                    "ok", false,
                    "message", e.getMessage(),
                    "data", Map.of()
            );
        }
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> deleteFromCart(
            @PathVariable Long id,
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        try {
            // delete the book from the cart
            String username = UserContext.getCurrentUsername(request);
            User user = userService.getUserByUsername(username);
            return cartService.deleteFromCart(id, user);
        } catch (Exception e) {
            response.setStatus(400);
            return Map.of(
                    "ok", false,
                    "message", e.getMessage(),
                    "data", Map.of()
            );
        }
    }
}

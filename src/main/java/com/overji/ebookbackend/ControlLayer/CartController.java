package com.overji.ebookbackend.ControlLayer;


import com.overji.ebookbackend.EntityLayer.User;
import com.overji.ebookbackend.ServiceLayer.CartService;
import com.overji.ebookbackend.ServiceLayer.UserService;
import com.overji.ebookbackend.Utils.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;
    private final UserService userService;

    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @GetMapping("")
    public Object getCartItems(
            HttpServletRequest request,
            HttpServletResponse response
    ){
        if(UserContext.getCurrentUsername(request).isEmpty()){
            return UserContext.unAuthorizedError(response);
        }
        try{
            String username = UserContext.getCurrentUsername(request);
            User user = userService.getUserByUsername(username);
            return cartService.getCartItems(user);
        } catch (Exception e){
            response.setStatus(400);
            return Map.of(
                    "ok", false,
                    "message", e.getMessage(),
                    "data", Map.of()
            );
        }
    }

    @PutMapping("")
    public Map<String,Object> addToCart(
            @RequestBody Map<String,Object> body,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        if(UserContext.getCurrentUsername(request).isEmpty()){
            return UserContext.unAuthorizedError(response);
        }
        try{
            String username = UserContext.getCurrentUsername(request);
            User user = userService.getUserByUsername(username);
            Long bookId = Long.valueOf(body.get("bookId").toString());
            return cartService.addToCart(bookId, user);
        } catch (Exception e){
            response.setStatus(400);
            return Map.of(
                    "ok", false,
                    "message", e.getMessage(),
                    "data", Map.of()
            );
        }
    }

    @PutMapping("/{id}")
    public Map<String,Object> updateCart(
            @PathVariable Long id,
            @RequestBody Map<String,Object> body,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        if(UserContext.getCurrentUsername(request).isEmpty()){
            return UserContext.unAuthorizedError(response);
        }
        try{
            String username = UserContext.getCurrentUsername(request);
            User user = userService.getUserByUsername(username);
            Long number = Long.valueOf(body.get("number").toString());
            return cartService.updateCart(id, number, user);
        } catch (Exception e){
            response.setStatus(400);
            return Map.of(
                    "ok", false,
                    "message", e.getMessage(),
                    "data", Map.of()
            );
        }
    }

    @DeleteMapping("/{id}")
    public Map<String,Object> deleteFromCart(
            @PathVariable Long id,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        if(UserContext.getCurrentUsername(request).isEmpty()){
            return UserContext.unAuthorizedError(response);
        }
        try{
            String username = UserContext.getCurrentUsername(request);
            User user = userService.getUserByUsername(username);
            return cartService.deleteFromCart(id, user);
        } catch (Exception e){
            response.setStatus(400);
            return Map.of(
                    "ok", false,
                    "message", e.getMessage(),
                    "data", Map.of()
            );
        }
    }
}

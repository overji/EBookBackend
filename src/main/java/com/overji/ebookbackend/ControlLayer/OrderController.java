package com.overji.ebookbackend.ControlLayer;

import com.overji.ebookbackend.EntityLayer.*;
import com.overji.ebookbackend.Utils.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;
import com.overji.ebookbackend.ServiceLayer.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;
    private final BookService bookService;
    public OrderController(OrderService orderService, UserService userService, BookService bookService) {
        this.orderService = orderService;
        this.userService = userService;
        this.bookService = bookService;
    }

    @GetMapping("")
    public Object getOrdersByUserId(
            HttpServletRequest request,
            HttpServletResponse response
    ){
        if(UserContext.getCurrentUsername(request).isEmpty()){
            return UserContext.unAuthorizedError(response);
        }
        try{
            String username = UserContext.getCurrentUsername(request);
            User user = userService.getUserByUsername(username);
            return orderService.getOrdersByUserId(user.getId());
        } catch (Exception e){
            response.setStatus(400);
            return Map.of(
                    "ok", false,
                    "message", e.getMessage(),
                    "data", Map.of()
            );
        }
    }

    @PostMapping("/{bookId}")
    public Map<String,Object> addOrderByBookId(
            @PathVariable Long bookId,
            @PathParam("number") Long number,
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
            String address = body.get("address").toString();
            String tel = body.get("tel").toString();
            String receiver = body.get("receiver").toString();
            return orderService.addOneOrder(address,tel,receiver,bookId,number,user);
        } catch (Exception e){
            response.setStatus(400);
            return Map.of(
                    "ok", false,
                    "message", e.getMessage(),
                    "data", Map.of()
            );
        }
    }

    @PostMapping("")
    public Map<String,Object> addOrder(
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
            String address = body.get("address").toString();
            String tel = body.get("tel").toString();
            String receiver = body.get("receiver").toString();
            List<Long> itemIds = ((List<Integer>) body.get("itemIds"))
                    .stream()
                    .map(Integer::longValue)
                    .toList();
            return orderService.addOrder(address,tel,receiver,itemIds,user);
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

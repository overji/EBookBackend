package com.overji.ebookbackend.controlLayer;

import com.overji.ebookbackend.entityLayer.*;
import com.overji.ebookbackend.utils.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;
import com.overji.ebookbackend.serviceLayer.*;

import java.util.List;
import java.util.Map;

/*
* this controller is used to handle order related requests
* it includes:
* 1. get all orders by user id
* 2. add order by book id
* 3. add order by order id
 */
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
        // Check if the user is logged in
        if(UserContext.getCurrentUsername(request).isEmpty()){
            return UserContext.unAuthorizedError(response);
        }
        try{
            // Get the username from the request
            String username = UserContext.getCurrentUsername(request);
            User user = userService.getUserByUsername(username);
            // get all orders by user id
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

    // this api is used for directly purchase a book with certain numbers
    // in the book page, without adding it to the cart
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
            // add one order by book id
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

    //this api is used for purchase in the cart
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

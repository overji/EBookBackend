package com.overji.ebookbackend.controlLayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.overji.ebookbackend.entityLayer.*;
import com.overji.ebookbackend.utils.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.server.PathParam;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import com.overji.ebookbackend.serviceLayer.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private final KafkaTemplate<String, String> kafkaTemplate;

    public OrderController(OrderService orderService, UserService userService, BookService bookService, KafkaTemplate<String, String> kafkaTemplate) {
        this.orderService = orderService;
        this.userService = userService;
        this.bookService = bookService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping("")
    public Object getOrdersByUserId(
            @PathParam("startTime") String startTime,
            @PathParam("endTime") String endTime,
            @PathParam("bookName") String bookName,
            @PathParam("pageIndex") Integer pageIndex,
            @PathParam("pageSize") Integer pageSize,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        if (startTime == null) startTime = "";
        if (endTime == null) endTime = "";
        if (bookName == null) bookName = "";
        try {
            // Get the username from the request
            if (startTime.isEmpty() && endTime.isEmpty() && bookName.isEmpty()) {
                String username = UserContext.getCurrentUsername(request);
                User user = userService.getUserByUsername(username);
                // get all orders by user id
                return orderService.getOrdersByUserId(user.getId(),
                        pageIndex == null ? 0 : pageIndex,
                        pageSize == null ? 8 : pageSize);
            } else {
                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                if (startTime.isEmpty()) {
                    startTime = "1970-01-01 00:00:00"; // default start time
                }

                if (endTime.isEmpty()) {
                    endTime = "9999-12-31 23:59:59"; // default end time
                }

                if (bookName.isEmpty()) {
                    bookName = ""; // default book name
                }
                // get all orders by user id and filter by time and book name
                String username = UserContext.getCurrentUsername(request);
                User user = userService.getUserByUsername(username);
                return orderService.findByBookNameAndStartTimeAndEndTimeAndUserId(
                        user.getId(),
                        bookName,
                        LocalDateTime.parse(startTime, outputFormatter),
                        LocalDateTime.parse(endTime, outputFormatter),
                        pageIndex == null ? 0 : pageIndex,
                        pageSize == null ? 8 : pageSize
                );
            }
        } catch (Exception e) {
            response.setStatus(400);
            return Map.of(
                    "ok", false,
                    "message", e.getMessage(),
                    "data", Map.of()
            );
        }
    }

    @GetMapping("/admin")
    public Object getOrdersByAdmin(
            @PathParam("startTime") String startTime,
            @PathParam("endTime") String endTime,
            @PathParam("bookName") String bookName,
            @PathParam("pageIndex") Integer pageIndex,
            @PathParam("pageSize") Integer pageSize,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        if (startTime == null) startTime = "";
        if (endTime == null) endTime = "";
        if (bookName == null) bookName = "";
        try {
            // Get the username from the request
            if (startTime.isEmpty() && endTime.isEmpty() && bookName.isEmpty()) {
                String username = UserContext.getCurrentUsername(request);
                // get all orders by user id
                return orderService.findAllOrders(pageIndex == null ? 0 : pageIndex,
                        pageSize == null ? 8 : pageSize);
            } else {
                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                if (startTime.isEmpty()) {
                    startTime = "1970-01-01 00:00:00"; // default start time
                }

                if (endTime.isEmpty()) {
                    endTime = "9999-12-31 23:59:59"; // default end time
                }

                if (bookName.isEmpty()) {
                    bookName = ""; // default book name
                }
                // get all orders by user id and filter by time and book name
                String username = UserContext.getCurrentUsername(request);
                return orderService.findByBookNameAndStartTimeAndEndTime(
                        bookName,
                        LocalDateTime.parse(startTime, outputFormatter),
                        LocalDateTime.parse(endTime, outputFormatter),
                        pageIndex == null ? 0 : pageIndex,
                        pageSize == null ? 8 : pageSize
                );
            }
        } catch (Exception e) {
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
    public Map<String, Object> addOrderByBookId(
            @PathVariable Long bookId,
            @PathParam("number") Long number,
            @RequestBody Map<String, Object> body,
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        try {
            // add one order by book id
            String username = UserContext.getCurrentUsername(request);
            String address = body.get("address").toString();
            String tel = body.get("tel").toString();
            String receiver = body.get("receiver").toString();
            User user = userService.getUserByUsername(username);
            Map<String, Object> orderRequest = Map.of(
                    "type", "direct-purchase",
                    "address", address,
                    "tel", tel,
                    "receiver", receiver,
                    "bookId", bookId,
                    "number", number,
                    "username", username
            );
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonMessage = objectMapper.writeValueAsString(orderRequest);
            kafkaTemplate.send("ebook-topic", jsonMessage);
            System.out.println("Kafka sent direct-purchase from username: " + username + ", bookId: " + bookId + ", number: " + number);
            System.out.println("Kafka sent message: " + jsonMessage);
            return Map.of(
                    "message", "Order placed successfully",
                    "ok", true,
                    "data", Map.of()
            );
        } catch (Exception e) {
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
    public Map<String, Object> addOrder(
            @RequestBody Map<String, Object> body,
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        try {
            String username = UserContext.getCurrentUsername(request);
            User user = userService.getUserByUsername(username);
            String address = body.get("address").toString();
            String tel = body.get("tel").toString();
            String receiver = body.get("receiver").toString();
            List<Long> itemIds = ((List<Integer>) body.get("itemIds"))
                    .stream()
                    .map(Integer::longValue)
                    .toList();
            Map<String, Object> orderRequest = Map.of(
                    "type", "cart-purchase",
                    "address", address,
                    "tel", tel,
                    "receiver", receiver,
                    "itemIds", itemIds,
                    "username", username
            );
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonMessage = objectMapper.writeValueAsString(orderRequest);
            kafkaTemplate.send("ebook-topic", jsonMessage);
            System.out.println("Kafka sent cart-purchase from username: " + username + ", itemIds: " + itemIds);
            System.out.println("Kafka sent message: " + jsonMessage);
            return Map.of(
                    "message", "Order placed successfully",
                    "ok", true,
                    "data", Map.of()
            );
        } catch (Exception e) {
            response.setStatus(400);
            return Map.of(
                    "ok", false,
                    "message", e.getMessage(),
                    "data", Map.of()
            );
        }
    }

    @GetMapping("/statistics")
    public Object getOrdersByAdmin(
            @PathParam("startTime") String startTime,
            @PathParam("endTime") String endTime,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        if (startTime == null) startTime = "";
        if (endTime == null) endTime = "";
        try {
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            if (startTime.isEmpty()) {
                startTime = "1970-01-01 00:00:00"; // default start time
            }

            if (endTime.isEmpty()) {
                endTime = "9999-12-31 23:59:59"; // default end time
            }
            return orderService.getUserStatistics(
                    LocalDateTime.parse(startTime, outputFormatter),
                    LocalDateTime.parse(endTime, outputFormatter)
            );
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

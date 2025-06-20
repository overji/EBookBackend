package com.overji.ebookbackend.serviceLayer.Implementation;

import com.overji.ebookbackend.daoLayer.*;
import com.overji.ebookbackend.entityLayer.*;
import com.overji.ebookbackend.serviceLayer.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderDAO orderDAO;
    private final UserDAO userDAO;
    private final OrderItemDAO orderItemDAO;
    private final BookDAO bookDAO;
    private final CartDAO cartDAO;

    public OrderServiceImpl(OrderDAO orderDAO, UserDAO userDAO, OrderItemDAO orderItemDAO, BookDAO bookDAO, CartDAO cartDAO) {
        this.orderDAO = orderDAO;
        this.userDAO = userDAO;
        this.orderItemDAO = orderItemDAO;
        this.bookDAO = bookDAO;
        this.cartDAO = cartDAO;
    }

    @Override
    public Map<String, Object> addOrder(String address, String tel, String receiver, List<Long> itemIds, User user) {
        Long totalMoney = 0L;
        for (Long itemId : itemIds) {
            Cart cart = cartDAO.findByUserIdAndUserCartId(user.getId(), itemId);
            Book book = bookDAO.findById(cart.getBook().getId()).orElseThrow(() -> new RuntimeException("Book not found"));
            totalMoney += book.getPrice() * cart.getNumber();
        }
        if (totalMoney > user.getBalance()) {
            return Map.of(
                    "message", "余额不足",
                    "ok", false,
                    "data", Map.of()
            );
        }
        Order order = new Order();
        order.setUser(user);
        order.setAddress(address);
        order.setTel(tel);
        order.setReceiver(receiver);
        orderDAO.save(order);
        for (Long itemId : itemIds) {
            Cart cart = cartDAO.findByUserIdAndUserCartId(user.getId(), itemId);
            Book book = bookDAO.findById(cart.getBook().getId()).orElseThrow(() -> new RuntimeException("Book not found"));
            if (book.getStock() < cart.getNumber()) {
                orderDAO.delete(order);
                return Map.of(
                        "message", "库存不足",
                        "ok", false,
                        "data", Map.of()
                );
            }
            if(book.isDeleted()) {
                orderDAO.delete(order);
                return Map.of(
                        "message", "书籍已被下架",
                        "ok", false,
                        "data", Map.of()
                );
            }
            OrderItem orderItem = new OrderItem();
            orderItem.setBook(book);
            orderItem.setNumber(cart.getNumber());
            orderItem.setOrder(order);
            orderItem.setCost(book.getPrice() * cart.getNumber());
            order.addItem(orderItem);
            orderItemDAO.save(orderItem);
            book.setSales(book.getSales() + cart.getNumber());
            bookDAO.save(book);
        }
        for (Long itemId : itemIds) {
            Cart cart = cartDAO.findByUserIdAndUserCartId(user.getId(), itemId);
            cartDAO.delete(cart);
        }
        user.setBalance(user.getBalance() - totalMoney);
        userDAO.save(user);
        return Map.of(
                "message", "Order placed successfully",
                "ok", true,
                "data", Map.of()
        );
    }

    @Override
    public Map<String, Object> getOrdersByUserId(Long userId, int pageIndex, int pageSize) {
        Pageable pageable = null;
        if (pageIndex == -1 || pageSize == -1) {
            pageable = Pageable.unpaged(); // Unpaged for all orders
        } else {
            pageable = Pageable.ofSize(pageSize).withPage(pageIndex);
        }
        Page<Order> orders = orderDAO.findByUserId(userId, pageable);
        return Map.of(
                "items", orders.stream().map(Order::toMap).toList(),
                "total", orders.getTotalPages()
        );
    }

    @Override
    public Map<String, Object> findByBookNameAndStartTimeAndEndTimeAndUserId(Long userId,
                                                                             String bookName,
                                                                             LocalDateTime startTime,
                                                                             LocalDateTime endTime,
                                                                             int pageIndex,
                                                                             int pageSize
    ) {
        Pageable pageable = null;
        if (pageIndex == -1 || pageSize == -1) {
            pageable = Pageable.unpaged(); // Unpaged for all orders
        } else {
            pageable = Pageable.ofSize(pageSize).withPage(pageIndex);
        }
        Page<Order> orders = orderDAO.findByBookNameAndStartTimeAndEndTimeAndUserId(
                bookName, startTime, endTime, userId, pageable);
        return Map.of(
                "items", orders.stream().map(Order::toMap).toList(),
                "total", orders.getTotalPages()
        );
    }

    @Override
    public Map<String, Object> findByBookNameAndStartTimeAndEndTime(String bookName,
                                                                    LocalDateTime startTime,
                                                                    LocalDateTime endTime,
                                                                    int pageIndex,
                                                                    int pageSize
    ) {
        Pageable pageable = null;
        if (pageIndex == -1 || pageSize == -1) {
            pageable = Pageable.unpaged(); // Unpaged for all orders
        } else {
            pageable = Pageable.ofSize(pageSize).withPage(pageIndex);
        }
        Page<Order> orders = orderDAO.findByBookNameAndStartTimeAndEndTime(bookName, startTime, endTime, pageable);
        return Map.of(
                "items", orders.stream().map(Order::toMap).toList(),
                "total", orders.getTotalPages()
        );
    }

    @Override
    public Map<String, Object> findAllOrders(int pageIndex, int pageSize) {
        Pageable pageable = null;
        if (pageIndex == -1 || pageSize == -1) {
            pageable = Pageable.unpaged(); // Unpaged for all orders
        } else {
            pageable = Pageable.ofSize(pageSize).withPage(pageIndex);
        }
        Page<Order> orders = orderDAO.findAllOrders(pageable);
        return Map.of(
                "items", orders.stream().map(Order::toMap).toList(),
                "total", orders.getTotalPages()
        );
    }

    @Override
    public Map<String, Object> addOneOrder(String address, String tel, String receiver, Long bookId, Long number, User user) {
        Book book = bookDAO.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        Long totalMoney = book.getPrice() * number;
        if (totalMoney > user.getBalance()) {
            return Map.of(
                    "message", "余额不足",
                    "ok", false,
                    "data", Map.of()
            );
        }
        if (book.getStock() < number) {
            return Map.of(
                    "message", "库存不足",
                    "ok", false,
                    "data", Map.of()
            );
        }
        if (book.isDeleted()) {
            return Map.of(
                    "message", "书籍已被下架",
                    "ok", false,
                    "data", Map.of()
            );
        }
        Order order = new Order();
        order.setUser(user);
        order.setAddress(address);
        order.setTel(tel);
        order.setReceiver(receiver);
        orderDAO.save(order);
        OrderItem orderItem = new OrderItem();
        orderItem.setBook(book);
        orderItem.setNumber(number);
        orderItem.setOrder(order);
        orderItem.setCost(book.getPrice() * number);
        order.addItem(orderItem);
        orderItemDAO.save(orderItem);
        book.setSales(book.getSales() + number);
        book.setStock(book.getStock() - number);
        bookDAO.save(book);
        user.setBalance(user.getBalance() - totalMoney);
        userDAO.save(user);
        return Map.of(
                "message", "Order placed successfully",
                "ok", true,
                "data", Map.of()
        );
    }

    @Override
    public Object getUserStatistics(LocalDateTime startTime,
                                    LocalDateTime endTime) {
        Pageable pageable = Pageable.unpaged(); // Unpaged for statistics
        List<Order> orders = orderDAO.findByBookNameAndStartTimeAndEndTime("", startTime, endTime, pageable)
                .stream()
                .toList();
        Map<User, Long> userStatistics = new HashMap<>();
        for (Order order : orders) {
            User user = order.getUser();
            Long totalMoney = userStatistics.getOrDefault(user, 0L);
            totalMoney += order.getItems().stream()
                    .mapToLong(OrderItem::getCost)
                    .sum();
            userStatistics.put(user, totalMoney);
        }
        //返回用户信息以及对应的总消费金额
        return userStatistics.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> userMap = new HashMap<>(entry.getKey().toMap());
                    userMap.put("totalCost", entry.getValue());
                    return userMap;
                })
                .collect(Collectors.toList());
    }
}
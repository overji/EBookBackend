package com.overji.ebookbackend.serviceLayer.Implementation;

import com.overji.ebookbackend.daoLayer.*;
import com.overji.ebookbackend.entityLayer.*;
import com.overji.ebookbackend.serviceLayer.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
        order.setUserOrderId(user.getOrderId());
        orderDAO.save(order);
        for (Long itemId : itemIds) {
            Cart cart = cartDAO.findByUserIdAndUserCartId(user.getId(), itemId);
            Book book = bookDAO.findById(cart.getBook().getId()).orElseThrow(() -> new RuntimeException("Book not found"));
            OrderItem orderItem = new OrderItem();
            orderItem.setBook(book);
            orderItem.setNumber(cart.getNumber());
            orderItem.setOrder(order);
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
    public List<Map<String, Object>> getOrdersByUserId(Long userId) {
        List<Order> orders = orderDAO.findByUserId(userId);
        return orders.stream().map(Order::toMap).toList();
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
        Order order = new Order();
        order.setUser(user);
        order.setAddress(address);
        order.setTel(tel);
        order.setReceiver(receiver);
        order.setUserOrderId(user.getOrderId());
        orderDAO.save(order);
        OrderItem orderItem = new OrderItem();
        orderItem.setBook(book);
        orderItem.setNumber(number);
        orderItem.setOrder(order);
        order.addItem(orderItem);
        orderItemDAO.save(orderItem);
        book.setSales(book.getSales() + number);
        bookDAO.save(book);
        user.setBalance(user.getBalance() - totalMoney);
        userDAO.save(user);
        return Map.of(
                "message", "Order placed successfully",
                "ok", true,
                "data", Map.of()
        );
    }
}
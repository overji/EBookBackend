package com.overji.ebookbackend.ServiceLayer;


import com.overji.ebookbackend.DataAccessLayer.*;
import com.overji.ebookbackend.EntityLayer.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderItemRepository orderItemRepository;
    private final BookRepository bookRepository;
    private final CartRepository cartRepository;
    public OrderService(OrderRepository orderRepository, UserRepository userRepository, OrderItemRepository orderItemRepository, BookRepository bookRepository, CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderItemRepository = orderItemRepository;
        this.bookRepository = bookRepository;
        this.cartRepository = cartRepository;
    }

    public Map<String,Object> addOrder(String address, String tel, String receiver, List<Long> itemIds, User user) {
        Long totalMoney = 0L;
        for (Long itemId : itemIds) {
            Cart cart = cartRepository.findByUserIdAndUserCartId(user.getId(),itemId);
            Book book = bookRepository.findById(cart.getBook().getId()).orElseThrow(() -> new RuntimeException("Book not found"));
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
        orderRepository.save(order);
        for (Long itemId : itemIds) {
            Cart cart = cartRepository.findByUserIdAndUserCartId(user.getId(),itemId);
            Book book = bookRepository.findById(cart.getBook().getId()).orElseThrow(() -> new RuntimeException("Book not found"));
            OrderItem orderItem = new OrderItem();
            orderItem.setBook(book);
            orderItem.setNumber(cart.getNumber());
            orderItem.setOrder(order);
            order.addItem(orderItem);
            orderItemRepository.save(orderItem);
            book.setSales(book.getSales() + cart.getNumber());
            bookRepository.save(book);
        }
        for (Long itemId : itemIds) {
            Cart cart = cartRepository.findByUserIdAndUserCartId(user.getId(),itemId);
            cartRepository.delete(cart);
        }
        this.orderRepository.save(order);
        user.setBalance(user.getBalance() - totalMoney);
        userRepository.save(user);
        return Map.of(
                "message", "Order placed successfully",
                "ok", true,
                "data",Map.of()
        );
    }

    public List<Map<String,Object>> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream().map(Order::toMap).toList();
    }

    public Map<String,Object> addOneOrder(String address, String tel, String receiver, Long bookId, Long number,User user){
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        Long totalMoney = book.getPrice() * number;
        System.out.println("totalMoney = " + totalMoney);
        System.out.println("user.getBalance() = " + user.getBalance());
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
        orderRepository.save(order);
        OrderItem orderItem = new OrderItem();
        orderItem.setBook(book);
        orderItem.setNumber(number);
        orderItem.setOrder(order);
        order.addItem(orderItem);
        orderItemRepository.save(orderItem);
        book.setSales(book.getSales() + number);
        bookRepository.save(book);
        this.orderRepository.save(order);
        user.setBalance(user.getBalance() - totalMoney);
        userRepository.save(user);
        return Map.of(
                "message", "Order placed successfully",
                "ok", true,
                "data",Map.of()
        );
    }
}

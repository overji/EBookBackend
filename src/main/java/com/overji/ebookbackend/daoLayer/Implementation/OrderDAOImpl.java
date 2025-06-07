package com.overji.ebookbackend.daoLayer.Implementation;

import com.overji.ebookbackend.daoLayer.OrderDAO;
import com.overji.ebookbackend.entityLayer.Order;
import com.overji.ebookbackend.repositoryLayer.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class OrderDAOImpl implements OrderDAO {
    private final OrderRepository orderRepository;

    public OrderDAOImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Page<Order> findByUserId(Long userId, Pageable pageable) {
        return orderRepository.findByUserId(userId, pageable);
    }

    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void delete(Order order) {
        orderRepository.delete(order);
    }

    @Override
    public Page<Order> findByBookNameAndStartTimeAndEndTime(String bookName, LocalDateTime startTime, LocalDateTime endTime, Pageable pageable) {
        return orderRepository.findByBookNameAndStartTimeAndEndTime(bookName, startTime, endTime, pageable);
    }

    @Override
    public Page<Order> findByBookNameAndStartTimeAndEndTimeAndUserId(String bookName, LocalDateTime startTime, LocalDateTime endTime, Long UserId, Pageable pageable) {
        return orderRepository.findByBookNameAndStartTimeAndEndTimeAndUserId(bookName, startTime, endTime, UserId, pageable);
    }

    @Override
    public Page<Order> findAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }
}
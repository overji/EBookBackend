package com.overji.ebookbackend.daoLayer.Implementation;

import com.overji.ebookbackend.daoLayer.OrderDAO;
import com.overji.ebookbackend.entityLayer.Order;
import com.overji.ebookbackend.repositoryLayer.OrderRepository;
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
    public List<Order> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
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
    public List<Order> findByBookNameAndStartTimeAndEndTime(String bookName, LocalDateTime startTime, LocalDateTime endTime){
        return orderRepository.findByBookNameAndStartTimeAndEndTime(bookName,startTime,endTime);
    }

    @Override
    public List<Order> findByBookNameAndStartTimeAndEndTimeAndUserId(String bookName, LocalDateTime startTime, LocalDateTime endTime, Long UserId){
        return orderRepository.findByBookNameAndStartTimeAndEndTimeAndUserId(bookName,startTime,endTime,UserId);
    }

    @Override
    public List<Order> findAllOrders(){
        return orderRepository.findAll();
    }
}
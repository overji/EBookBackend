package com.overji.ebookbackend.daoLayer.Implementation;

import com.overji.ebookbackend.daoLayer.OrderDAO;
import com.overji.ebookbackend.entityLayer.Order;
import com.overji.ebookbackend.repositoryLayer.OrderRepository;
import org.springframework.stereotype.Repository;

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
}
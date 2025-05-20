package com.overji.ebookbackend.daoLayer.Implementation;

import com.overji.ebookbackend.daoLayer.OrderItemDAO;
import com.overji.ebookbackend.entityLayer.OrderItem;
import com.overji.ebookbackend.repositoryLayer.OrderItemRepository;
import org.springframework.stereotype.Repository;

@Repository
public class OrderItemDAOImpl implements OrderItemDAO {
    private final OrderItemRepository orderItemRepository;

    public OrderItemDAOImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public void save(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }
}
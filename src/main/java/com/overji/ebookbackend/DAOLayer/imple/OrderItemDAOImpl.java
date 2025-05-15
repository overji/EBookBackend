package com.overji.ebookbackend.DAOLayer.imple;

import com.overji.ebookbackend.DAOLayer.OrderItemDAO;
import com.overji.ebookbackend.EntityLayer.OrderItem;
import com.overji.ebookbackend.RepositoryLayer.OrderItemRepository;
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
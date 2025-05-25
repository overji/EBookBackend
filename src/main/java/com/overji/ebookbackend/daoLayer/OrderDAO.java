package com.overji.ebookbackend.daoLayer;

import com.overji.ebookbackend.entityLayer.Order;

import java.util.List;

public interface OrderDAO {
    List<Order> findByUserId(Long userId);

    void save(Order order);

    void delete(Order order);
}
package com.overji.ebookbackend.DAOLayer;

import com.overji.ebookbackend.EntityLayer.Order;

import java.util.List;

public interface OrderDAO {
    List<Order> findByUserId(Long userId);

    void save(Order order);
}
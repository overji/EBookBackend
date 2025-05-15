package com.overji.ebookbackend.DAOLayer;

import com.overji.ebookbackend.EntityLayer.OrderItem;

public interface OrderItemDAO {
    void save(OrderItem orderItem);
}
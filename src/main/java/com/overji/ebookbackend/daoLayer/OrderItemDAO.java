package com.overji.ebookbackend.daoLayer;

import com.overji.ebookbackend.entityLayer.OrderItem;

public interface OrderItemDAO {
    void save(OrderItem orderItem);
}
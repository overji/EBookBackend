package com.overji.ebookbackend.daoLayer;

import com.overji.ebookbackend.entityLayer.Order;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderDAO {
    List<Order> findByUserId(Long userId);

    void save(Order order);

    void delete(Order order);

    List<Order> findByBookNameAndStartTimeAndEndTime(String bookName, LocalDateTime startTime, LocalDateTime endTime);

    List<Order> findByBookNameAndStartTimeAndEndTimeAndUserId(String bookName, LocalDateTime startTime, LocalDateTime endTime, Long UserId);

    List<Order> findAllOrders();
}
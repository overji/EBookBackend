package com.overji.ebookbackend.daoLayer;

import com.overji.ebookbackend.entityLayer.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderDAO {
    Page<Order> findByUserId(Long userId, Pageable pageable);

    void save(Order order);

    void delete(Order order);

    Page<Order> findByBookNameAndStartTimeAndEndTime(String bookName, LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);

    Page<Order> findByBookNameAndStartTimeAndEndTimeAndUserId(String bookName, LocalDateTime startTime, LocalDateTime endTime, Long UserId, Pageable pageable);

    Page<Order> findAllOrders(Pageable pageable);
}
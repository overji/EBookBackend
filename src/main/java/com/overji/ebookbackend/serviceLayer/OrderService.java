package com.overji.ebookbackend.serviceLayer;

import com.overji.ebookbackend.entityLayer.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface OrderService {
    Map<String, Object> addOrder(String address, String tel, String receiver, List<Long> itemIds, User user);

    List<Map<String, Object>> getOrdersByUserId(Long userId);

    List<Map<String, Object>> findByBookNameAndStartTimeAndEndTimeAndUserId(Long userId,
                                                                            String bookName,
                                                                            LocalDateTime startTime,
                                                                            LocalDateTime endTime
                                                                            );
    List<Map<String, Object>> findByBookNameAndStartTimeAndEndTime(String bookName,
                                                                   LocalDateTime startTime,
                                                                   LocalDateTime endTime
                                                                    );

    List<Map<String, Object>> findAllOrders();

    Map<String, Object> addOneOrder(String address, String tel, String receiver, Long bookId, Long number, User user);

    Object getUserStatistics(LocalDateTime startTime,
                             LocalDateTime endTime);
}
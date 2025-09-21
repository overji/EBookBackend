package com.overji.ebookbackend.controlLayer.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.overji.ebookbackend.serviceLayer.OrderService;
import com.overji.ebookbackend.serviceLayer.UserService;
import com.overji.ebookbackend.entityLayer.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Component
public class KafkaOrderListener {

    private final OrderService orderService;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    @Autowired
    public KafkaOrderListener(OrderService orderService, UserService userService, ObjectMapper objectMapper) {
        this.orderService = orderService;
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "ebook-topic", groupId = "ebook-group")
    @Transactional
    public void listen(String message) {
        try {
            // 解析消息
            Map<String, Object> orderRequest = objectMapper.readValue(message, Map.class);
            String type = orderRequest.get("type").toString();
            String username = orderRequest.get("username").toString();
            User user = userService.getUserByUsername(username);

            if ("direct-purchase".equals(type)) {
                // 直接购买
                String address = orderRequest.get("address").toString();
                String tel = orderRequest.get("tel").toString();
                String receiver = orderRequest.get("receiver").toString();
                Long bookId = Long.valueOf(orderRequest.get("bookId").toString());
                Long number = Long.valueOf(orderRequest.get("number").toString());
                System.out.println("Kafka received direct-purchase from username: " + username + ", bookId: " + bookId + ", number: " + number);
                orderService.addOneOrder(address, tel, receiver, bookId, number, user);
            } else if ("cart-purchase".equals(type)) {
                // 购物车购买
                String address = orderRequest.get("address").toString();
                String tel = orderRequest.get("tel").toString();
                String receiver = orderRequest.get("receiver").toString();
                List<Integer> intItemIds = (List<Integer>) orderRequest.get("itemIds");
                List<Long> itemIds = intItemIds.stream()
                                               .map(Integer::longValue)
                                               .toList();
                System.out.println("Kafka received cart-purchase from username: " + username + ", itemIds: " + itemIds);
                orderService.addOrder(address, tel, receiver, itemIds, user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error processing Kafka message: " + message);
        }
    }
}
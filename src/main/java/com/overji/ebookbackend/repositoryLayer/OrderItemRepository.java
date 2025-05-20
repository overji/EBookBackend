package com.overji.ebookbackend.repositoryLayer;


import com.overji.ebookbackend.entityLayer.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}

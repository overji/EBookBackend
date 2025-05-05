package com.overji.ebookbackend.DataAccessLayer;


import com.overji.ebookbackend.EntityLayer.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}

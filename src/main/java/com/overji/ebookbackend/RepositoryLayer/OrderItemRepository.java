package com.overji.ebookbackend.RepositoryLayer;


import com.overji.ebookbackend.EntityLayer.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}

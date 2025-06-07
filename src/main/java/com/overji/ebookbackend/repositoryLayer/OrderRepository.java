package com.overji.ebookbackend.repositoryLayer;

import com.overji.ebookbackend.entityLayer.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o WHERE o.user.id = ?1")
    Page<Order> findByUserId(Long userId, Pageable pageable);

    @Query("""
            SELECT o
            FROM Order o
            JOIN o.items order_item
            JOIN order_item.book books
            WHERE books.title LIKE CONCAT('%', :bookName, '%')
              AND o.createdAt > :startTime
              AND o.createdAt < :endTime
            """)
    Page<Order> findByBookNameAndStartTimeAndEndTime(
            @Param("bookName") String bookName,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            Pageable pageable
    );

    @Query("""
            SELECT o
            FROM Order o
            JOIN o.items order_item
            JOIN order_item.book books
            WHERE books.title LIKE CONCAT('%', :bookName, '%')
              AND o.createdAt > :startTime
              AND o.createdAt < :endTime
              AND o.user.id = :userId
            """)
    Page<Order> findByBookNameAndStartTimeAndEndTimeAndUserId(
            @Param("bookName") String bookName,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("userId") Long userId,
            Pageable pageable
    );
}

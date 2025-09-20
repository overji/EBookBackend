package com.overji.ebookbackend.entityLayer;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_in_id", nullable = false)
    private List<OrderItem> items = new ArrayList<>();

    @Column(nullable = true,columnDefinition = "TEXT")
    private String address;

    @Column(nullable = true,columnDefinition = "TEXT")
    private String tel;

    @Column(nullable = true,columnDefinition = "TEXT")
    private String receiver;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void addItem(OrderItem item) {
        this.items.add(item);
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTel() {
        return tel;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Map<String, Object> toMap() {
        return Map.of(
                "id", id,
                "items", items.stream().map(OrderItem::toMap).toList(),
                "address", address,
                "tel", tel,
                "receiver", receiver,
                "createdAt", createdAt
        );
    }

    public Map<String,Object> adminToMap(){
        return Map.of(
                "id", id,
                "items", items.stream().map(OrderItem::toMap).toList(),
                "address", address,
                "tel", tel,
                "receiver", receiver,
                "createdAt", createdAt
        );
    }
}

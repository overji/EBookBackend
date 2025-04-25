package com.overji.ebookbackend.EntityLayer;

import jakarta.persistence.*;

import java.util.Map;

@Entity
@Table(name = "userAddress")
public class UserAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "uid", nullable = false)
    private User user;

    @Column(nullable = false)
    private Long userAddrId;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String receiver;

    void setId(Long id) {
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
    public Long getUserAddrId() {
        return userAddrId;
    }
    public void setUserAddrId(Long userAddrId) {
        this.userAddrId = userAddrId;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getReceiver() {
        return receiver;
    }
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Map<String, Object> toMap() {
        return Map.of(
                "id", userAddrId,
                "address", address,
                "tel", phone,
                "receiver", receiver
        );
    }
}

package com.overji.ebookbackend.entityLayer;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private Long balance;

    @Column
    private String email;

    @Column
    @Lob
    private String introduction;

    @Column
    @Lob
    private String avatar = "";

    // User's privilege level，0为普通用户，1为普通管理员，2为超级管理员
    @Column(nullable = false,columnDefinition = "INT DEFAULT 0")
    private Long userPrivilege = 0L;

    // 是否被禁用
    @Column(nullable = false)
    private Boolean isDisabled = false;

    @CreationTimestamp
    @Column(nullable = false)
    private Timestamp createdAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<UserAddress> userAddresses;

    @ManyToMany(mappedBy = "likedUsers")
    private List<Comment> likedComments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<Cart> cartItems = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private UserAuth auth = new UserAuth();

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setAuth(UserAuth auth) {
        this.auth = auth;
    }

    public UserAuth getAuth() {
        return auth;
    }

    public void setPassword(String password) {
        this.auth.setPassword(password);
    }

    public String getPassword() {
        return this.auth.getPassword();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Long getBalance() {
        return balance;
    }

    public List<UserAddress> getUserAddresses() {
        return userAddresses;
    }

    public void setUserAddresses(List<UserAddress> userAddresses) {
        this.userAddresses = userAddresses;
    }

    public Long getUserPrivilege() {
        return this.userPrivilege;
    }

    public void setUserPrivilege(Long userPrivilege) {
        this.userPrivilege = userPrivilege;
    }

    public Boolean getIsDisabled() {
        return this.isDisabled;
    }

    public void setIsDisabled(Boolean isDisabled) {
        this.isDisabled = true;
    }

    public List<Cart> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<Cart> cartItems) {
        this.cartItems = cartItems;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Comment> getLikedComments() {
        return likedComments;
    }

    public void setLikedComments(List<Comment> likedComments) {
        this.likedComments = likedComments;
    }

    public Map<String, Object> toMap() {
        return Map.of(
                "id", id,
                "username", username,
                "nickname", nickname,
                "balance", balance,
                "email", email,
                "introduction", introduction,
                "avatar", avatar,
                "createdAt", createdAt,
                "privilege", userPrivilege,
                "isDisabled", isDisabled
        );
    }
}
package com.overji.ebookbackend.EntityLayer;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
    private String avatar;

    @CreationTimestamp
    @Column(nullable = false)
    private Timestamp createdAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserAddress> userAddresses;

    @ManyToMany(mappedBy = "likedUsers")
    private List<Comment> likedComments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cart> cartItems = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
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

    public Map<String, Object> toMap() {
        return Map.of(
                "id", id,
                "username", username,
                "nickname", nickname,
                "balance", balance,
                "email", email,
                "introduction", introduction,
                "avatar", avatar,
                "createdAt", createdAt
        );
    }

    public Long getCartId() {
        List<Boolean> cartIds = new ArrayList<>(Collections.nCopies(this.cartItems.size() + 1, false));
        for (Cart cart : this.cartItems) {
            if (cart.getUserCartId() >= cartIds.size()) {
                continue;
            }
            cartIds.set(cart.getUserCartId().intValue(), true);
        }
        for (int i = 0; i < cartIds.size(); i++) {
            if (!cartIds.get(i)) {
                return (long) i;
            }
        }
        return -1L;
    }

    public Long getOrderId() {
        List<Boolean> orderIds = new ArrayList<>(Collections.nCopies(this.orders.size() + 1, false));
        for (Order order : this.orders) {
            if (order.getUserOrderId() >= orderIds.size()) {
                continue;
            }
            orderIds.set(order.getUserOrderId().intValue(), true);
        }
        for (int i = 0; i < orderIds.size(); i++) {
            if (!orderIds.get(i)) {
                return (long) i;
            }
        }
        return -1L;
    }
}
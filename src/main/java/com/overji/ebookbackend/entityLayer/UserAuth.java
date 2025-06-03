package com.overji.ebookbackend.entityLayer;

import jakarta.persistence.*;

@Entity
@Table(name = "user_auth")
public class UserAuth {

    @Id
    private Long id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String password;

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
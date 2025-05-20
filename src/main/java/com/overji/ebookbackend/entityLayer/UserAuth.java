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

    // User's privilege level，0为普通用户，1为普通管理员，2为超级管理员
    @Column(nullable = false)
    private Long userPrivilege = 0L;

    // 是否被禁用
    @Column(nullable = false)
    private Boolean isDisabled = false;

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

    public Long getUserPrivilege(){
        return userPrivilege;
    }

    public void setUserPrivilege(Long userPrivilege) {
        this.userPrivilege = userPrivilege;
    }

    public Boolean getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(Boolean isDisabled) {
        this.isDisabled = isDisabled;
    }
}
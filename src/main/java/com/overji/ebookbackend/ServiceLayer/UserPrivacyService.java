package com.overji.ebookbackend.ServiceLayer;

import com.overji.ebookbackend.DAOLayer.UserDAO;
import com.overji.ebookbackend.EntityLayer.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrivacyService implements UserDetailsService {

    private final UserDAO userDAO;

    public UserPrivacyService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword()) // 假设密码已加密
                .roles("USER") // 设置用户角色
                .build();
    }
}
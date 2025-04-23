package com.overji.ebookbackend.ServiceLayer;

import com.overji.ebookbackend.EntityLayer.User;
import com.overji.ebookbackend.DataAccessLayer.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrivacyService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserPrivacyService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
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

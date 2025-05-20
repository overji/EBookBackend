package com.overji.ebookbackend.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SecurityConfig
 * Spring Security 配置类
 * 主要用于配置 Spring Security 的安全策略
 */
@Configuration
public class SecurityConfig {

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        // 获取 AuthenticationManager
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 禁用 CSRF 保护
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // 其他接口需要认证
            )
            .formLogin(form -> form
                .loginPage("/login") // 自定义登录页面
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .permitAll()
            );


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 使用 BCryptPasswordEncoder 进行密码加密
        return new BCryptPasswordEncoder();
    }
}
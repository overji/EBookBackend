package com.overji.ebookbackend.ControlLayer;

import com.overji.ebookbackend.DataAccessLayer.UserRepository;
import com.overji.ebookbackend.EntityLayer.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class AuthController {
    private final AuthenticationManager authenticationManager;

    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> requestData, HttpServletResponse response) {
        String username = requestData.get("username");
        String password = requestData.get("password");
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            // 创建一个 Cookie
            Cookie cookie = new Cookie("username", username);
            cookie.setHttpOnly(true); // 设置 HttpOnly 属性
            cookie.setPath("/"); // 设置 Cookie 的作用路径
            cookie.setMaxAge(7 * 24 * 60 * 60); // 设置 Cookie 的过期时间（7days）
            response.addCookie(cookie); // 将 Cookie 添加到响应中

            return Map.of(
                    "message", "ok",
                    "ok", true,
                    "data", Map.of()
            );
        } catch (AuthenticationException e) {
            return Map.of(
                    "message", "fail",
                    "ok", false,
                    "data", Map.of()
            );
        }
    }

    @PutMapping("/logout")
    public Map<String, Object> logout(HttpServletResponse response) {
        // 清除 Cookie
        Cookie cookie = new Cookie("username", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // 设置过期时间为 0，表示删除 Cookie
        response.addCookie(cookie);

        return Map.of(
                "message", "ok",
                "ok", true,
                "data", Map.of()
        );
    }
}
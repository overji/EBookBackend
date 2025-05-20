package com.overji.ebookbackend.controlLayer;

import com.overji.ebookbackend.entityLayer.User;
import com.overji.ebookbackend.serviceLayer.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/*
 *  AuthController.java
 *  Include APIs: login, logout
 */

@RestController
@RequestMapping("/api")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> requestData, HttpServletRequest request) {
        String username = requestData.get("username");
        String password = requestData.get("password");
        try {
            // 进行身份验证
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            // 创建一个 Cookie
            request.getSession().setAttribute("username", username);

            // 判断是否是管理员
            User user = userService.getUserByUsername(username);
            boolean isAdmin = user.getAuth().getUserPrivilege() == 1;
            if (isAdmin) {
                request.getSession().setAttribute("isAdmin", true);
            } else {
                request.getSession().setAttribute("isAdmin", false);
            }



            // 设置登录成功后的响应
            return Map.of(
                    "message", "ok",
                    "ok", true,
                    "data", Map.of(),
                    "isAdmin", isAdmin
            );
        } catch (AuthenticationException e) {
            // 身份验证失败
            e.printStackTrace();
            return Map.of(
                    "message", "fail",
                    "ok", false,
                    "data", Map.of(),
                    "isAdmin", false
            );
        }
    }

    @PutMapping("/logout")
    public Map<String, Object> logout(HttpServletRequest request) {
        // 清除 Session
        request.getSession().invalidate();

        // 设置登出成功后的响应
        return Map.of(
                "message", "ok",
                "ok", true,
                "data", Map.of()
        );
    }
}
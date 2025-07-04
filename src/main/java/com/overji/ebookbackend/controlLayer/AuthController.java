package com.overji.ebookbackend.controlLayer;

import com.overji.ebookbackend.entityLayer.User;
import com.overji.ebookbackend.serviceLayer.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

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
            // 使用 AuthenticationManager 进行认证
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            // 将认证信息存储到 SecurityContext
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);

            // 判断是否是
            User user = userService.getUserByUsername(username);
            if(user.getIsDisabled()) {
                return Map.of(
                        "message", "用户已被禁用",
                        "ok", false,
                        "data", Map.of(),
                        "isAdmin", false
                );
            }

            // 同步到 Session
            HttpSession session = request.getSession(true);
            session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, securityContext);
            session.setAttribute("username", username);
            boolean isAdmin = user.getUserPrivilege() == 1;
            session.setAttribute("isAdmin", isAdmin);

            return Map.of(
                    "message", "ok",
                    "ok", true,
                    "data", Map.of(),
                    "isAdmin", isAdmin
            );
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return Map.of(
                    "message", e.getMessage(),
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
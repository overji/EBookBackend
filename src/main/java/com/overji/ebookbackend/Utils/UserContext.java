package com.overji.ebookbackend.Utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * UserContext
 * 用于获取当前用户的信息
 */
@Component
public class UserContext {
    static public String getCurrentUsername(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("username".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return "";
    }

    static public Map<String, Object> unAuthorizedError(HttpServletResponse response) {
        response.setStatus(403);
        return Map.of("status", 401, "message", "Unauthorized", "ok", false);
    }
}
package com.overji.ebookbackend.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * UserContext
 * 用于获取当前用户的信息
 */
@Component
public class UserContext {
    static public String getCurrentUsername(HttpServletRequest request) {
        String username = (String)request.getSession().getAttribute("username");
        return (username == null)?"":username;
    }

    static public Map<String, Object> unAuthorizedError(HttpServletResponse response) {
        response.setStatus(403);
        return Map.of("status", 401, "message", "Unauthorized", "ok", false);
    }
}
package com.overji.ebookbackend.ControlLayer;

import com.overji.ebookbackend.EntityLayer.User;
import com.overji.ebookbackend.ServiceLayer.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(UserController.class);
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/test")
    public List<Map<String,String>> getAllUsers(){
        List<User> users = userService.getAllUsers();
        return users.stream().map(user -> Map.of(
                "id", String.valueOf(user.getId()),
                "username", user.getUsername(),
                "email", user.getEmail(),
                "introduction", user.getIntroduction(),
                "createdAt", String.valueOf(user.getCreatedAt())
        )).toList();
    }

    @PostMapping("/register")
    public Map<String, Object> registerUser(@RequestBody Map<String, String> requestData) {
        String username = requestData.get("username");
        String password = requestData.get("password");
        String email = requestData.get("email");
        String avatar = requestData.get("avatar");
        String introduction = requestData.get("introduction");

        // 这里可以添加逻辑，例如保存用户到数据库
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setAvatar(avatar);
        user.setIntroduction(introduction);
        try{
            userService.addUser(user);
            // 假设保存成功，返回一个响应
            return Map.of(
                    "status", 200,
                    "message", "User registered successfully",
                    "id", user.getId(),
                    "username", username,
                    "email", email
            );
        } catch (Exception e){
            // 捕获异常并返回错误信息
            logger.error("Failed to register user: {}", e.getMessage());
            return Map.of(
                    "status", 500,
                    "message", "Failed to register user: " + e.getMessage()
            );
        }
    }
}

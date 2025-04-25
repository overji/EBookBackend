package com.overji.ebookbackend.ControlLayer;

import com.overji.ebookbackend.EntityLayer.User;
import com.overji.ebookbackend.EntityLayer.UserAddress;
import com.overji.ebookbackend.ServiceLayer.UserAddressService;
import com.overji.ebookbackend.ServiceLayer.UserService;
import com.overji.ebookbackend.Utils.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final UserAddressService userAddressService;
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService, UserAddressService userAddressService) {
        this.userService = userService;
        this.userAddressService = userAddressService;
    }

    @GetMapping("/test")
    public List<Map<String, Object>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return users.stream().map(User::toMap).toList();
    }

    @PostMapping("/register")
    public Map<String, Object> registerUser(@RequestBody Map<String, Object> requestData) {
        String username = (String) requestData.get("username");
        String password = (String) requestData.get("password");
        String nickname = (String) requestData.get("nickname");
        String email = (String) requestData.get("email");
        String avatar = (String) requestData.get("avatar");
        String introduction = (String) requestData.get("introduction");
        Long balance = (Long) requestData.get("balance");
        if (balance == null) {
            balance = 0L;
        }


        // 这里可以添加逻辑，例如保存用户到数据库
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setAvatar(avatar);
        user.setIntroduction(introduction);
        user.setNickname(nickname);
        user.setBalance(balance);
        try {
            userService.addUser(user);
            // 假设保存成功，返回一个响应
            return Map.of(
                    "status", 200,
                    "message", "User registered successfully",
                    "id", user.getId(),
                    "username", username,
                    "email", email
            );
        } catch (Exception e) {
            // 捕获异常并返回错误信息
            logger.error("Failed to register user: {}", e.getMessage());
            return Map.of(
                    "status", 500,
                    "message", "Failed to register user: " + e.getMessage()
            );
        }
    }

    @PutMapping("/me/password")
    public Map<String, Object> changePassword(@RequestBody Map<String, String> requestData,
                                              HttpServletRequest request,
                                              HttpServletResponse response) {
        String username = UserContext.getCurrentUsername(request);
        try {
            this.userService.updatePasswordByUsername(username, requestData.get("password"));
            return Map.of(
                    "message", "Password updated successfully",
                    "ok", true,
                    "data", Map.of()
            );
        } catch (IllegalAccessError | RuntimeException e) {
            logger.error("Failed to update password: {}", e.getMessage());
            response.setStatus(500);
            return Map.of(
                    "status", 500,
                    "message", "Failed to update password: " + e.getMessage()
            );
        }
    }

    @PutMapping("/me/introduction")
    public Map<String, Object> changeIntroduction(@RequestBody Map<String, String> requestData,
                                                  HttpServletRequest request,
                                                  HttpServletResponse response) {
        String username = UserContext.getCurrentUsername(request);
        try {
            this.userService.updateIntroductionByUsername(username, requestData.get("introduction"));
            return Map.of(
                    "message", "Introduction updated successfully",
                    "ok", true,
                    "data", Map.of()
            );
        } catch (IllegalAccessError | RuntimeException e) {
            logger.error("Failed to update introduction: {}", e.getMessage());
            response.setStatus(500);
            return Map.of(
                    "status", 500,
                    "message", "Failed to update introduction: " + e.getMessage()
            );
        }
    }

    @PostMapping("/me/avatar")
    public Map<String, Object> uploadAvatar(@RequestBody Map<String, String> requestData,
                                            HttpServletRequest request,
                                            HttpServletResponse response
    ) {
        String fileContent = requestData.get("file");
        if (fileContent == null || fileContent.isEmpty()) {
            return Map.of(
                    "status", 400,
                    "message", "File content is empty"
            );
        }

        // 检查文件类型并移除 Base64 前缀
        String fileExtension;
        if (fileContent.startsWith("data:image/jpeg;base64,")) {
            fileContent = fileContent.substring("data:image/jpeg;base64,".length());
            fileExtension = ".jpg";
        } else if (fileContent.startsWith("data:image/png;base64,")) {
            fileContent = fileContent.substring("data:image/png;base64,".length());
            fileExtension = ".png";
        } else {
            response.setStatus(400);
            return Map.of(
                    "status", 400,
                    "message", "Unsupported file format"
            );
        }

        // 保存文件到本地
        String savePath = "avatars/";
        String filename = System.currentTimeMillis() + fileExtension; // 使用当前时间戳作为文件名
        File directory = new File(savePath);
        if (!directory.exists()) {
            directory.mkdirs(); // 创建目录
        }

        String filePath = savePath + filename; // 根据文件类型设置文件名
        try {
            byte[] decodedBytes = java.util.Base64.getDecoder().decode(fileContent); // 解码 Base64 内容
            try (java.io.FileOutputStream fos = new java.io.FileOutputStream(filePath)) {
                fos.write(decodedBytes); // 保存文件
            }
            //需要修改数据库中username对应的avatar项
            String username = UserContext.getCurrentUsername(request);
            this.userService.updateAvatarByUsername(username, filePath);
            return Map.of(
                    "status", 200,
                    "message", "File uploaded successfully",
                    "filePath", filePath,
                    "ok", true
            );
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
            response.setStatus(500);
            return Map.of(
                    "status", 500,
                    "message", "Failed to upload file: " + e.getMessage(),
                    "ok", false,
                    "filepath", ""
            );
        }
    }

    @GetMapping("/me")
    public Map<String, Object> getUserInfo(HttpServletRequest request,
                                           HttpServletResponse response
    ) {
        String username = UserContext.getCurrentUsername(request);
        User user = this.userService.getUserByUsername(username);
        if (user == null) {
            response.setStatus(403);
            return Map.of(
                    "message", "User not found",
                    "ok", false,
                    "data", Map.of()
            );
        }
        return user.toMap();
    }

    @PostMapping("/me/address")
    public Map<String, Object> addUserAddress(@RequestBody Map<String, String> requestData,
                                              HttpServletRequest request,
                                              HttpServletResponse response) {
        String username = UserContext.getCurrentUsername(request);
        String address = requestData.get("address");
        String phone = requestData.get("tel");
        String receiver = requestData.get("receiver");
        try {
            this.userAddressService.saveUserAddress(username, address, phone, receiver);
            return Map.of(
                    "message", "Address added successfully",
                    "ok", true,
                    "data", Map.of()
            );
        } catch (IllegalAccessError | RuntimeException e) {
            logger.error("Failed to add address: {}", e.getMessage());
            response.setStatus(500);
            return Map.of(
                    "status", 500,
                    "message", "Failed to add address: " + e.getMessage()
            );
        }
    }

    @GetMapping("/me/address")
    public List<Map<String, Object>> getUserAddresses(HttpServletRequest request,
                                                      HttpServletResponse response) {
        String username = UserContext.getCurrentUsername(request);
        return this.userAddressService.getUserAddresses(username)
                .stream()
                .map(UserAddress::toMap)
                .toList();
    }

    @DeleteMapping("/me/address/{userAddrId}")
    public Map<String, Object> deleteUserAddress(@PathVariable Long userAddrId,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response) {
        String username = UserContext.getCurrentUsername(request);
        try {
            this.userAddressService.deleteUserAddress(username, userAddrId);
            return Map.of(
                    "message", "Address deleted successfully",
                    "ok", true,
                    "data", Map.of()
            );
        } catch (IllegalAccessError | RuntimeException e) {
            logger.error("Failed to delete address: {}", e.getMessage());
            response.setStatus(500);
            return Map.of(
                    "status", 500,
                    "message", "Failed to delete address: " + e.getMessage()
            );
        }
    }

    @GetMapping("/{id}")
    public Map<String, Object> getOtherUserId(@PathVariable Long id,
                                              HttpServletResponse response,
                                              HttpServletRequest request
    ) {
        return userService.getUserById(id).toMap();
    }
}

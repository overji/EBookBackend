package com.overji.ebookbackend.controlLayer;

import com.overji.ebookbackend.entityLayer.User;
import com.overji.ebookbackend.entityLayer.UserAddress;
import com.overji.ebookbackend.serviceLayer.UserAddressService;
import com.overji.ebookbackend.serviceLayer.UserService;
import com.overji.ebookbackend.utils.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/*
* this class is used to handle user-related requests
* it includes user registration, login, password change, and user information retrieval
 */

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

    // can be ignored
    @GetMapping("/admin")
    public Map<String, Object> getUsers(
            @RequestParam int pageIndex,
            @RequestParam int pageSize,
            HttpServletResponse response,
            HttpServletRequest request) {
        Pageable pageable = Pageable.ofSize(pageSize).withPage(pageIndex);
        Page<User> users = userService.getAllUsers(pageable);
        long len = users.getTotalElements(); // 使用 getTotalElements() 获取总记录数
        List<User> paginatedUsers = users.getContent(); // 直接获取当前页的内容
        return Map.of("items", users.getContent()
                        .stream()
                        .map(User::toMap)
                        .toList(),
                      "total", (long) Math.ceil((double) len / pageSize)
                );
    }

    @PutMapping("/admin/{id}")
    public Map<String, Object> banUser(
            @PathVariable Long id,
            @RequestParam boolean isDisabled,
            HttpServletResponse response
    ) {
        try {
            userService.updateDisabledById(id, isDisabled);
            return Map.of(
                    "message", "User status updated successfully",
                    "ok", true,
                    "data", Map.of()
            );
        } catch (IllegalAccessError | RuntimeException e) {
            logger.error("Failed to update user status: {}", e.getMessage());
            response.setStatus(500);
            return Map.of(
                    "status", 500,
                    "message", "Failed to update user status: " + e.getMessage()
            );
        }
    }

    @PostMapping("/register")
    public Map<String, Object> registerUser(@RequestBody Map<String, Object> requestData) {
        // get parameters from request
        String username = (String) requestData.get("username");
        String password = (String) requestData.get("password");
        String nickname = (String) requestData.get("nickname");
        String email = (String) requestData.get("email");
        String avatar = (String) requestData.get("avatar");
        String introduction = (String) requestData.get("introduction");
        Long balance = (Long) requestData.get("balance");
        if (balance == null) {
            balance = 1000000L;
        }
        if(avatar == null){
            avatar = "";
        }
        if(introduction == null){
            introduction = "";
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
        user.setUserPrivilege(0L);
        try {
            userService.addUser(user);
            // 假设保存成功，返回一个响应
            return Map.of(
                    "status", 200,
                    "message", "User registered successfully",
                    "ok", true,
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
        // 获取当前登录用户的用户名
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
        //修改用户简介
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
    public Map<String, Object> uploadAvatar(@RequestParam("file") org.springframework.web.multipart.MultipartFile file,
                                            HttpServletRequest request,
                                            HttpServletResponse response) {
        if (file == null || file.isEmpty()) {
            return Map.of(
                    "status", 400,
                    "message", "File is empty"
            );
        }

        // 检查文件类型
        String originalFilename = file.getOriginalFilename();
        String fileExtension;
        if (originalFilename != null && originalFilename.endsWith(".jpg")) {
            fileExtension = ".jpg";
        } else if (originalFilename != null && originalFilename.endsWith(".png")) {
            fileExtension = ".png";
        } else {
            response.setStatus(400);
            return Map.of(
                    "status", 400,
                    "message", "Unsupported file format"
            );
        }

        // 保存文件到当前运行文件夹下的 ./avatars 文件夹
        String savePath = System.getProperty("user.dir") + File.separator + "avatars" + File.separator;
        String filename = System.currentTimeMillis() + fileExtension; // 使用当前时间戳作为文件名
        File directory = new File(savePath);
        if (!directory.exists()) {
            if (!directory.mkdirs()) { // 创建目录失败
                response.setStatus(500);
                return Map.of(
                        "status", 500,
                        "message", "Failed to create directory for file upload"
                );
            }
        }

        String filePath = savePath + filename;
        try {
            file.transferTo(new File(filePath)); // 保存文件
            // 更新数据库中 username 对应的 avatar 项
            String username = UserContext.getCurrentUsername(request);
            this.userService.updateAvatarByUsername(username, filename);
            return Map.of(
                    "status", 200,
                    "message", "File uploaded successfully",
                    "filePath", filename,
                    "ok", true
            );
        } catch (IOException e) {
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
        //获取个人信息
        String username = UserContext.getCurrentUsername(request);
        User user = this.userService.getUserByUsername(username);
        if (user == null) {
            response.setStatus(403);
            return Map.of(
                    "message", "User not found",
                    "ok", false,
                    "data", Map.of(),
                    "failed", true
            );
        }
        return user.toMap();
    }

    @PostMapping("/me/addresses")
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

    @GetMapping("/me/addresses")
    public List<Map<String, Object>> getUserAddresses(HttpServletRequest request,
                                                      HttpServletResponse response) {
        String username = UserContext.getCurrentUsername(request);
        return this.userAddressService.getUserAddresses(username)
                .stream()
                .map(UserAddress::toMap)
                .toList();
    }

    @DeleteMapping("/me/addresses/{userAddrId}")
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

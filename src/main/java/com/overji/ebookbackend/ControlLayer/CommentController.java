package com.overji.ebookbackend.ControlLayer;

import com.overji.ebookbackend.EntityLayer.User;
import com.overji.ebookbackend.ServiceLayer.CommentService;
import com.overji.ebookbackend.ServiceLayer.UserService;
import com.overji.ebookbackend.Utils.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/*
* this controller is used to handle comment related requests
* it includes:
* 1. like a comment
* 2. unlike a comment
* 3. reply to a comment
* 4. get all comments
 */

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;

    public CommentController(CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }

    @PutMapping("/{id}/like")
    public Map<String, Object> likeComment(
            @PathVariable Long id,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        if(UserContext.getCurrentUsername(request).isEmpty()){
            return UserContext.unAuthorizedError(response);
        }
        try{
            String username = UserContext.getCurrentUsername(request);
            User user = userService.getUserByUsername(username);
            commentService.likeComment(id, user.getId());
            return Map.of(
                    "ok", true,
                    "message", "Comment liked successfully",
                    "data",Map.of()
            );
        } catch(Exception e){
            response.setStatus(400);
            return Map.of(
                    "ok", false,
                    "message", e.getMessage(),
                    "data", Map.of()
            );
        }
    }

    @PutMapping("/{id}/unlike")
    public Map<String, Object> unlikeComment(
            @PathVariable Long id,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        if(UserContext.getCurrentUsername(request).isEmpty()){
            return UserContext.unAuthorizedError(response);
        }
        try{
            String username = UserContext.getCurrentUsername(request);
            User user = userService.getUserByUsername(username);
            commentService.unlikeComment(id, user.getId());
            return Map.of(
                    "ok", true,
                    "message", "Comment unliked successfully",
                    "data",Map.of()
            );
        } catch(Exception e){
            response.setStatus(400);
            return Map.of(
                    "ok", false,
                    "message", e.getMessage(),
                    "data", Map.of()
            );
        }
    }

    @PostMapping("/{id}")
    public Map<String, Object> replyComment(
            @PathVariable Long id,
            @RequestBody Map<String, Object> requestData,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        if(UserContext.getCurrentUsername(request).isEmpty()){
            return UserContext.unAuthorizedError(response);
        }
        try{
            String username = UserContext.getCurrentUsername(request);
            User user = userService.getUserByUsername(username);
            String content = (String) requestData.get("content");
            return commentService.replyToComment(id, user,content);
        } catch(Exception e){
            response.setStatus(400);
            return Map.of(
                    "ok", false,
                    "message", e.getMessage(),
                    "data", Map.of()
            );
        }
    }
}

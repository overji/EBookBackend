package com.overji.ebookbackend.ServiceLayer;

import com.overji.ebookbackend.EntityLayer.User;

import java.util.Map;

public interface CommentService {
    Map<String, Object> likeComment(Long commentId, Long userId);

    Map<String, Object> unlikeComment(Long commentId, Long userId);

    Map<String, Object> replyToComment(Long commentId, User user, String content);
}
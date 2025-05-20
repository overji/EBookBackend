package com.overji.ebookbackend.serviceLayer;

import com.overji.ebookbackend.entityLayer.User;

import java.util.Map;

public interface CommentService {
    Map<String, Object> likeComment(Long commentId, Long userId);

    Map<String, Object> unlikeComment(Long commentId, Long userId);

    Map<String, Object> replyToComment(Long commentId, User user, String content);
}
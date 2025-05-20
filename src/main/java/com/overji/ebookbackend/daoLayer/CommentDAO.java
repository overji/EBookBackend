package com.overji.ebookbackend.daoLayer;

import com.overji.ebookbackend.entityLayer.Comment;

public interface CommentDAO {
    void likeComment(Long commentId, Long userId);

    void unlikeComment(Long commentId, Long userId);

    Comment getReferenceById(Long commentId);
}
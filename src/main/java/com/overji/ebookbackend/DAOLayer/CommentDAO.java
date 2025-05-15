package com.overji.ebookbackend.DAOLayer;

import com.overji.ebookbackend.EntityLayer.Comment;

public interface CommentDAO {
    void likeComment(Long commentId, Long userId);

    void unlikeComment(Long commentId, Long userId);

    Comment getReferenceById(Long commentId);
}
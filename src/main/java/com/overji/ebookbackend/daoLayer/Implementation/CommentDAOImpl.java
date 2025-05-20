package com.overji.ebookbackend.daoLayer.Implementation;

import com.overji.ebookbackend.daoLayer.CommentDAO;
import com.overji.ebookbackend.entityLayer.Comment;
import com.overji.ebookbackend.repositoryLayer.CommentRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CommentDAOImpl implements CommentDAO {
    private final CommentRepository commentRepository;

    public CommentDAOImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public void likeComment(Long commentId, Long userId) {
        commentRepository.likeComment(commentId, userId);
    }

    @Override
    public void unlikeComment(Long commentId, Long userId) {
        commentRepository.unlikeComment(commentId, userId);
    }

    @Override
    public Comment getReferenceById(Long commentId) {
        return commentRepository.getReferenceById(commentId);
    }
}
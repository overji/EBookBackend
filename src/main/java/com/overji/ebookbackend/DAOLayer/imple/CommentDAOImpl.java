package com.overji.ebookbackend.DAOLayer.imple;

import com.overji.ebookbackend.DAOLayer.CommentDAO;
import com.overji.ebookbackend.EntityLayer.Comment;
import com.overji.ebookbackend.RepositoryLayer.CommentRepository;
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
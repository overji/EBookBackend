package com.overji.ebookbackend.serviceLayer.Implementation;

import com.overji.ebookbackend.daoLayer.BookDAO;
import com.overji.ebookbackend.daoLayer.CommentDAO;
import com.overji.ebookbackend.entityLayer.Book;
import com.overji.ebookbackend.entityLayer.Comment;
import com.overji.ebookbackend.entityLayer.User;
import com.overji.ebookbackend.serviceLayer.CommentService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentDAO commentDAO;
    private final BookDAO bookDAO;

    public CommentServiceImpl(CommentDAO commentDAO, BookDAO bookDAO) {
        this.commentDAO = commentDAO;
        this.bookDAO = bookDAO;
    }

    @Override
    public Map<String, Object> likeComment(Long commentId, Long userId) {
        commentDAO.likeComment(commentId, userId);
        return Map.of(
                "message", "Comment liked successfully",
                "ok", true,
                "data", Map.of()
        );
    }

    @Override
    public Map<String, Object> unlikeComment(Long commentId, Long userId) {
        commentDAO.unlikeComment(commentId, userId);
        return Map.of(
                "message", "Comment unliked successfully",
                "ok", true,
                "data", Map.of()
        );
    }

    @Override
    public Map<String, Object> replyToComment(Long commentId, User user, String content) {
        Comment toComment = commentDAO.getReferenceById(commentId);
        Comment newComment = new Comment();
        Book book = toComment.getBook();
        newComment.setBook(toComment.getBook());
        newComment.setReply(toComment.getUser().getUsername());
        newComment.setContent(content);
        newComment.setUser(user);
        book.addComment(newComment);
        bookDAO.save(book);
        return Map.of(
                "message", "Comment replied successfully",
                "ok", true,
                "data", Map.of()
        );
    }
}
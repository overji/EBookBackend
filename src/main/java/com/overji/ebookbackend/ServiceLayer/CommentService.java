package com.overji.ebookbackend.ServiceLayer;

import com.overji.ebookbackend.DataAccessLayer.BookRepository;
import com.overji.ebookbackend.DataAccessLayer.CommentRepository;
import com.overji.ebookbackend.DataAccessLayer.UserRepository;
import com.overji.ebookbackend.EntityLayer.Book;
import com.overji.ebookbackend.EntityLayer.Comment;
import com.overji.ebookbackend.EntityLayer.User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    public CommentService(CommentRepository commentRepository,BookRepository bookRepository) {
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
    }

    public Map<String, Object> likeComment(Long commentId, Long userId) {
        commentRepository.likeComment(commentId, userId);
        return Map.of(
                "message", "Comment liked successfully",
                "ok", true,
                "data", Map.of()
        );
    }

    public Map<String, Object> unlikeComment(Long commentId, Long userId) {
        commentRepository.unlikeComment(commentId, userId);
        return Map.of(
                "message", "Comment unliked successfully",
                "ok", true,
                "data", Map.of()
        );
    }

    public Map<String, Object> replyToComment(Long commentId, User user, String content) {
        Comment toComment = commentRepository.getReferenceById(commentId);
        Comment newComment = new Comment();
        Book book = toComment.getBook();
        newComment.setBook(toComment.getBook());
        newComment.setReply(toComment.getUser().getUsername());
        newComment.setContent(content);
        newComment.setUser(user);
        book.addComment(newComment);
        bookRepository.save(book);
        return Map.of(
                "message", "Comment replied successfully",
                "ok", true,
                "data", Map.of()
        );
    }
}

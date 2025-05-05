package com.overji.ebookbackend.DataAccessLayer;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.overji.ebookbackend.EntityLayer.Comment;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Modifying
    @Transactional
    @Query(value = """
            INSERT INTO comment_likes (comment_id, user_id) VALUES (?1, ?2)""",nativeQuery = true)
    void likeComment(Long commentId, Long userId);

    @Modifying
    @Transactional
    @Query(value = """
            DELETE FROM comment_likes WHERE comment_id = ?1 AND user_id = ?2""",nativeQuery = true)
    void unlikeComment(Long commentId, Long userId);
}

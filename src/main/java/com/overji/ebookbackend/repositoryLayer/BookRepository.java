package com.overji.ebookbackend.repositoryLayer;

import com.overji.ebookbackend.entityLayer.Book;
import com.overji.ebookbackend.entityLayer.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b JOIN b.tags t WHERE t.name = ?1 AND b.title LIKE %?2% ORDER BY b.id ASC")
    Page<Book> findAllByTagAndTitle(String tag, String keyword, Pageable pageable);

    @Query("SELECT b FROM Book b ORDER BY b.sales DESC LIMIT 10")
    List<Book> findTop10Books();

    @Query("SELECT b FROM Book b WHERE b.title LIKE %?1%")
    Page<Book> findByTitleContaining(String keyword, Pageable pageable);

    @Query("SELECT b FROM Book b JOIN b.tags t WHERE t.name = ?1")
    Page<Book> findByTagContaining(String tag, Pageable pageable);

    @Query("SELECT c FROM Comment c WHERE c.book.id = ?1 ORDER BY c.createdAt DESC")
    Page<Comment> getBookCommentsByTime(Long bookId, Pageable pageable);

    @Query("SELECT c FROM Comment c WHERE c.book.id = ?1 ORDER BY size(c.likedUsers) DESC")
    Page<Comment> getBookCommentsByLikes(Long bookId, Pageable pageable);
}

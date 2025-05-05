package com.overji.ebookbackend.DataAccessLayer;
import com.overji.ebookbackend.EntityLayer.Book;
import com.overji.ebookbackend.EntityLayer.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>{

    @Query("SELECT b FROM Book b WHERE ?1 MEMBER OF b.tags AND b.title LIKE %?2% ORDER BY b.id ASC")
    List<Book> findAllByTagAndTitle(String tag,String keyword);

    @Query("SELECT b FROM Book b ORDER BY b.sales DESC LIMIT 10")
    List<Book> findTop10Books();

    @Query("SELECT b FROM Book b WHERE b.title LIKE %?1%")
    List<Book> findByTitleContaining(String keyword);

    @Query("SELECT b FROM Book b WHERE ?1 MEMBER OF b.tags")
    List<Book> findByTagContaining(String tag);

    @Query("SELECT c FROM Comment c WHERE c.book.id = ?1 ORDER BY c.createdAt DESC")
    List<Comment> getBookCommentsByTime(Long BookId);

    @Query("SELECT c FROM Comment c WHERE c.book.id = ?1 ORDER BY size(c.likedUsers) DESC")
    List<Comment> getBookCommentsByLikes(Long BookId);
}

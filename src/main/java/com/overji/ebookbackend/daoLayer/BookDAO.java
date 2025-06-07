package com.overji.ebookbackend.daoLayer;

import com.overji.ebookbackend.entityLayer.Book;
import com.overji.ebookbackend.entityLayer.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookDAO {
    Page<Book> findAllByTagAndTitle(String tag, String keyword, Pageable pageable);

    Page<Book> findByTitleContaining(String keyword, Pageable pageable);

    Page<Book> findByTagContaining(String tag, Pageable pageable);

    Page<Book> findAll(Pageable pageable);

    Page<Book> adminFindAllByTagAndTitle(String tag, String keyword, Pageable pageable);

    Page<Book> adminFindByTitleContaining(String keyword, Pageable pageable);

    Page<Book> adminFindByTagContaining(String tag, Pageable pageable);

    Page<Book> adminFindAll(Pageable pageable);

    List<Book> findTop10Books();

    Page<Comment> getBookCommentsByTime(Long bookId, Pageable pageable);

    Page<Comment> getBookCommentsByLikes(Long bookId, Pageable pageable);

    Optional<Book> findById(Long id);

    void save(Book book);

    void deleteById(Long id);
}
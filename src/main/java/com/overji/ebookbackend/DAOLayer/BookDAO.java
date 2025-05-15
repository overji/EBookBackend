package com.overji.ebookbackend.DAOLayer;

import com.overji.ebookbackend.EntityLayer.Book;
import com.overji.ebookbackend.EntityLayer.Comment;

import java.util.List;
import java.util.Optional;

public interface BookDAO {
    List<Book> findAllByTagAndTitle(String tag, String keyword);

    List<Book> findTop10Books();

    List<Book> findByTitleContaining(String keyword);

    List<Book> findByTagContaining(String tag);

    List<Comment> getBookCommentsByTime(Long bookId);

    List<Comment> getBookCommentsByLikes(Long bookId);

    List<Book> findAll();

    Optional<Book> findById(Long id);

    void save(Book book);
}
package com.overji.ebookbackend.DAOLayer.imple;

import com.overji.ebookbackend.DAOLayer.BookDAO;
import com.overji.ebookbackend.EntityLayer.Book;
import com.overji.ebookbackend.EntityLayer.Comment;
import com.overji.ebookbackend.RepositoryLayer.BookRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BookDAOImpl implements BookDAO {
    private final BookRepository bookRepository;

    public BookDAOImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findAllByTagAndTitle(String tag, String keyword) {
        return bookRepository.findAllByTagAndTitle(tag, keyword);
    }

    @Override
    public List<Book> findTop10Books() {
        return bookRepository.findTop10Books();
    }

    @Override
    public List<Book> findByTitleContaining(String keyword) {
        return bookRepository.findByTitleContaining(keyword);
    }

    @Override
    public List<Book> findByTagContaining(String tag) {
        return bookRepository.findByTagContaining(tag);
    }

    @Override
    public List<Comment> getBookCommentsByTime(Long bookId) {
        return bookRepository.getBookCommentsByTime(bookId);
    }

    @Override
    public List<Comment> getBookCommentsByLikes(Long bookId) {
        return bookRepository.getBookCommentsByLikes(bookId);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public void save(Book book) { // 实现 save 方法
        bookRepository.save(book);
    }
}
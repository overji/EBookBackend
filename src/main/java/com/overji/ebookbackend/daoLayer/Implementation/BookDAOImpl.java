package com.overji.ebookbackend.daoLayer.Implementation;

import com.overji.ebookbackend.daoLayer.BookDAO;
import com.overji.ebookbackend.entityLayer.Book;
import com.overji.ebookbackend.entityLayer.Comment;
import com.overji.ebookbackend.repositoryLayer.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<Book> findAllByTagAndTitle(String tag, String keyword, Pageable pageable) {
        return bookRepository.findAllByTagAndTitle(tag, keyword, pageable);
    }

    @Override
    public Page<Book> findByTitleContaining(String keyword, Pageable pageable) {
        return bookRepository.findByTitleContaining(keyword, pageable);
    }

    @Override
    public Page<Book> findByTagContaining(String tag, Pageable pageable) {
        return bookRepository.findByTagContaining(tag, pageable);
    }

    @Override
    public Page<Book> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public Page<Comment> getBookCommentsByTime(Long bookId, Pageable pageable) {
        return bookRepository.getBookCommentsByTime(bookId, pageable);
    }

    @Override
    public Page<Comment> getBookCommentsByLikes(Long bookId, Pageable pageable) {
        return bookRepository.getBookCommentsByLikes(bookId, pageable);
    }

    @Override
    public List<Book> findTop10Books() {
        return bookRepository.findTop10Books();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public void save(Book book) { // 实现 save 方法
        bookRepository.save(book);
    }

    @Override
    public void deleteById(Long id) { // 实现 deleteById 方法
        bookRepository.deleteById(id);
    }
}
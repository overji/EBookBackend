package com.overji.ebookbackend.ServiceLayer;

import com.overji.ebookbackend.DataAccessLayer.BookRepository;
import com.overji.ebookbackend.DataAccessLayer.BookTagsRepository;
import com.overji.ebookbackend.EntityLayer.Book;
import com.overji.ebookbackend.EntityLayer.BookTag;
import com.overji.ebookbackend.EntityLayer.Comment;
import com.overji.ebookbackend.EntityLayer.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.overji.ebookbackend.Utils.UserContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookTagsRepository bookTagsRepository;

    public BookService(BookRepository bookRepository, BookTagsRepository bookTagsRepository) {
        this.bookRepository = bookRepository;
        this.bookTagsRepository = bookTagsRepository;
    }

    public Map<String, Object> getBooks(String tag, String keyword, int pageIndex, int pageSize) {
        List<Book> books = new ArrayList<>();
        if (Objects.equals(tag, "") && Objects.equals(keyword, "")) {
            books = bookRepository.findAll();
        } else if (!Objects.equals(tag, "") && Objects.equals(keyword, "")) {
            books = bookRepository.findByTagContaining(tag);
        } else if (Objects.equals(tag, "")) {
            books = bookRepository.findByTitleContaining(keyword);
        } else {
            books = bookRepository.findAllByTagAndTitle(tag, keyword);
        }
        int totalBooks = books.size();
        int start = pageIndex * pageSize;
        int end = Math.min(start + pageSize, totalBooks);
        List<Book> paginatedBooks = books.subList(start, end);
        int totalPages = (int) Math.ceil((double) totalBooks / pageSize);
        return Map.of(
                "total", totalPages,
                "items", paginatedBooks.stream().map(
                        Book::toMap
                )
        );
    }

    public Map<String, Object> getBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        return Map.of(
                "book", book.toMap()
        );
    }

    @Transactional
    public void insertBook(String title,
                           String author,
                           String description,
                           Long price,
                           String cover,
                           List<String> tags
    ) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setDescription(description);
        book.setPrice(price);
        book.setCover(cover);
        book.setSales(0L);
        bookRepository.save(book);
        long curIndex = 0L;
        for (String tag : tags) {
            BookTag bookTag = new BookTag();
            bookTag.setName(tag);
            bookTag.setBook(book);
            bookTag.setBookTagId(curIndex++);
            bookTagsRepository.save(bookTag);
            book.addTag(bookTag);
        }
        bookRepository.save(book);
    }

    @Transactional
    public void updateSales(Long id, Long sales) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        book.setSales(book.getSales() + sales);
        bookRepository.save(book);
    }

    public List<String> getAllTags() {
        List<BookTag> tags = bookTagsRepository.findAll();
        return tags.stream().map(
                        BookTag::getName
                )
                .distinct()
                .toList();
    }

    public List<Map<String, Object>> getTop10Books() {
        List<Book> books = bookRepository.findTop10Books();
        return books.stream().map(
                Book::toMap
        ).toList();
    }

    public Map<String, Object> getBookComments(Long bookId, String orderBy, int pageIndex, int pageSize, User user) {
        List<Map<String, Object>> comments;
        if (Objects.equals(orderBy, "likes")) {
            List<Comment> commentList = bookRepository.getBookCommentsByLikes(bookId);
            comments = commentList.stream().map(
                    comment -> comment.toMap(user)
            ).toList();
        } else {
            List<Comment> commentList = bookRepository.getBookCommentsByLikes(bookId);
            comments = commentList.stream().map(
                    comment -> comment.toMap(user)
            ).toList();
        }
        int totalComments = comments.size();
        int start = pageIndex * pageSize;
        int end = Math.min(start + pageSize, totalComments);
        List<Map<String, Object>> paginatedComments = comments.subList(start, end);
        int totalPages = (int) Math.ceil((double) totalComments / pageSize);
        return Map.of(
                "total", totalPages,
                "items", paginatedComments
        );
    }

    public Map<String,Object> postBookComments(Long bookId, String content,User user) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        Comment comment = new Comment();
        comment.setBook(book);
        comment.setUser(user);
        comment.setContent(content);
        book.addComment(comment);
        bookRepository.save(book);
        return Map.of(
                "message", "Comment posted successfully",
                "ok", true,
                "data",Map.of()
        );

    }
}

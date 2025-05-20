package com.overji.ebookbackend.serviceLayer.Implementation;

import com.overji.ebookbackend.daoLayer.BookDAO;
import com.overji.ebookbackend.daoLayer.BookTagsDAO;
import com.overji.ebookbackend.entityLayer.Book;
import com.overji.ebookbackend.entityLayer.BookTag;
import com.overji.ebookbackend.entityLayer.Comment;
import com.overji.ebookbackend.entityLayer.User;
import com.overji.ebookbackend.serviceLayer.BookService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class BookServiceImpl implements BookService {

    private final BookDAO bookDAO;
    private final BookTagsDAO bookTagsDAO;

    public BookServiceImpl(BookDAO bookDAO, BookTagsDAO bookTagsDAO) {
        this.bookDAO = bookDAO;
        this.bookTagsDAO = bookTagsDAO;
    }

    @Override
    public Map<String, Object> getBooks(String tag, String keyword, int pageIndex, int pageSize) {
        List<Book> books = new ArrayList<>();
        if (Objects.equals(tag, "") && Objects.equals(keyword, "")) {
            books = bookDAO.findAll();
        } else if (!Objects.equals(tag, "") && Objects.equals(keyword, "")) {
            books = bookDAO.findByTagContaining(tag);
        } else if (Objects.equals(tag, "")) {
            books = bookDAO.findByTitleContaining(keyword);
        } else {
            books = bookDAO.findAllByTagAndTitle(tag, keyword);
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

    @Override
    public Map<String, Object> getBookById(Long id) {
        Book book = bookDAO.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        return book.toMap();
    }

    @Override
    @Transactional
    public void insertBook(String title, String author, String description, Long price, String cover, List<String> tags) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setDescription(description);
        book.setPrice(price);
        book.setCover(cover);
        book.setSales(0L);
        bookDAO.save(book);
        long curIndex = 0L;
        for (String tag : tags) {
            BookTag bookTag = new BookTag();
            bookTag.setName(tag);
            bookTag.setBook(book);
            bookTag.setBookTagId(curIndex++);
            bookTagsDAO.save(bookTag);
            book.addTag(bookTag);
        }
        bookDAO.save(book);
    }

    @Override
    @Transactional
    public void updateSales(Long id, Long sales) {
        Book book = bookDAO.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        book.setSales(book.getSales() + sales);
        bookDAO.save(book);
    }

    @Override
    public List<String> getAllTags() {
        List<BookTag> tags = bookTagsDAO.findAll();
        return tags.stream().map(
                        BookTag::getName
                )
                .distinct()
                .toList();
    }

    @Override
    public List<Map<String, Object>> getTop10Books() {
        List<Book> books = bookDAO.findTop10Books();
        return books.stream().map(
                Book::toMap
        ).toList();
    }

    @Override
    public Map<String, Object> getBookComments(Long bookId, String orderBy, int pageIndex, int pageSize, User user) {
        List<Map<String, Object>> comments;
        if (Objects.equals(orderBy, "like")) {
            List<Comment> commentList = bookDAO.getBookCommentsByLikes(bookId);
            comments = commentList.stream().map(
                    comment -> comment.toMap(user)
            ).toList();
        } else {
            List<Comment> commentList = bookDAO.getBookCommentsByTime(bookId);
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

    @Override
    public Map<String, Object> postBookComments(Long bookId, String content, User user) {
        Book book = bookDAO.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        Comment comment = new Comment();
        comment.setBook(book);
        comment.setUser(user);
        comment.setContent(content);
        book.addComment(comment);
        bookDAO.save(book);
        return Map.of(
                "message", "Comment posted successfully",
                "ok", true,
                "data", Map.of()
        );
    }
}
package com.overji.ebookbackend.serviceLayer.Implementation;

import com.overji.ebookbackend.daoLayer.BookDAO;
import com.overji.ebookbackend.daoLayer.BookTagsDAO;
import com.overji.ebookbackend.entityLayer.Book;
import com.overji.ebookbackend.entityLayer.BookTag;
import com.overji.ebookbackend.entityLayer.Comment;
import com.overji.ebookbackend.entityLayer.User;
import com.overji.ebookbackend.serviceLayer.BookService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
        Page<Book> books = null;
        Pageable pageable = Pageable.ofSize(pageSize).withPage(pageIndex);
        if (Objects.equals(tag, "") && Objects.equals(keyword, "")) {
            books = bookDAO.findAll(pageable);
        } else if (!Objects.equals(tag, "") && Objects.equals(keyword, "")) {
            books = bookDAO.findByTagContaining(tag,pageable);
        } else if (Objects.equals(tag, "")) {
            books = bookDAO.findByTitleContaining(keyword,pageable);
        } else {
            books = bookDAO.findAllByTagAndTitle(tag, keyword,pageable);
        }
        List<Book> paginatedBooks = books.getContent();
        int totalPages = books.getTotalPages();
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
    public Map<String, Object> insertBook(String title, String author, String description, Long price, String cover, List<String> tags, Long stock, String ISBN) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setDescription(description);
        book.setPrice(price);
        book.setCover(cover);
        book.setSales(0L);
        book.setStock(stock);
        book.setIsbn(ISBN);

        bookDAO.save(book);
        long curIndex = 0L;
        for (String tag : tags) {
            BookTag bookTag = new BookTag();
            bookTag.setName(tag);
            bookTag.setBook(book);
            bookTagsDAO.save(bookTag);
            book.addTag(bookTag);
        }
        bookDAO.save(book);
        return Map.of(
                "message", "Book inserted successfully",
                "ok", true,
                "data", book.toMap(),
                "id", book.getId()
        );
    }

    @Override
    @Transactional
    public Map<String, Object> updateBook(Long id, String title, String author, String description, Long price, String cover, List<String> tags, Long stock, String ISBN) {
        Book book = bookDAO.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        book.setTitle(title);
        book.setAuthor(author);
        book.setDescription(description);
        book.setPrice(price);
        book.setCover(cover);
        book.setStock(stock);
        book.setIsbn(ISBN);

        // 清除旧的标签
        List<BookTag> existingTags = new ArrayList<>(book.getTags());
        for (BookTag tag : existingTags) {
            book.removeTag(tag);
            bookTagsDAO.delete(tag);
        }

        // 添加新的标签
        long curIndex = 0L;
        for (String tagName : tags) {
            BookTag bookTag = new BookTag();
            bookTag.setName(tagName);
            bookTag.setBook(book);
            book.addTag(bookTag);
            bookTagsDAO.save(bookTag);
        }

        bookDAO.save(book);
        return Map.of(
                "message", "Book updated successfully",
                "ok", true,
                "data", book.toMap()
        );
    }

    @Override
    @Transactional
    public Map<String, Object> updateSales(Long id, Long sales) {
        Book book = bookDAO.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        book.setSales(book.getSales() + sales);
        bookDAO.save(book);
        return Map.of(
                "message", "Sales updated successfully",
                "ok", true,
                "data", book.toMap()
        );
    }

    @Override
    @Transactional
    public Map<String, Object> deleteBook(Long id) {
        bookDAO.deleteById(id);
        return Map.of(
                "message", "Book deleted successfully",
                "ok", true,
                "data", Map.of()
        );
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
        Page<Comment> comments = null;
        Pageable pageable = Pageable.ofSize(pageSize).withPage(pageIndex);
        if (Objects.equals(orderBy, "like")) {
            comments = bookDAO.getBookCommentsByLikes(bookId,pageable);
        } else {
            comments = bookDAO.getBookCommentsByTime(bookId,pageable);
        }
        return Map.of(
                "total", comments.getTotalPages(),
                "items", comments.getContent().stream().map(
                        comment -> comment.toMap(user)
                ).collect(Collectors.toList())
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
package com.overji.ebookbackend.ControlLayer;

import com.overji.ebookbackend.ServiceLayer.BookService;
import com.overji.ebookbackend.Utils.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/book")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public Map<String, Object> getBooks(
            @RequestParam String tag,
            @RequestParam String keyword,
            @RequestParam int pageIndex,
            @RequestParam int pageSize,
            HttpServletResponse response,
            HttpServletRequest request) {
        if (UserContext.getCurrentUsername(request).isEmpty()) {
            return UserContext.unAuthorizedError(response);
        }


        tag = tag == null ? "" : tag.trim();
        keyword = keyword == null ? "" : keyword.trim();

        if (pageIndex < 0) {
            pageIndex = 0;
        }
        if (pageSize < 1) {
            pageSize = 8;
        }

        return bookService.getBooks(tag, keyword, pageIndex, pageSize);
    }

    @GetMapping("/rank")
    public Object getTopBooks(
            HttpServletResponse response,
            HttpServletRequest request
    ) {
        if (UserContext.getCurrentUsername(request).isEmpty()) {
            return UserContext.unAuthorizedError(response);
        }
        return bookService.getTop10Books();
    }

    @GetMapping("/{id}")
    public Map<String, Object> getBookById(
            @PathVariable Long id,
            HttpServletResponse response,
            HttpServletRequest request
    ) {
        if (UserContext.getCurrentUsername(request).isEmpty()) {
            return UserContext.unAuthorizedError(response);
        }
        try{
            return bookService.getBookById(id);
        } catch (Exception e) {
            response.setStatus(404);
            return Map.of(
                    "status", 404,
                    "message", "Book not found",
                    "ok", false
            );
        }

    }

    @GetMapping("/tags")
    public Object getTags(
            HttpServletResponse response,
            HttpServletRequest request
    ) {
        if (UserContext.getCurrentUsername(request).isEmpty()) {
            return UserContext.unAuthorizedError(response);
        }
        return bookService.getAllTags();
    }

    @PostMapping("/insert")
    public Map<String, Object> insertBook(
            @RequestBody Map<String, Object> requestData,
            HttpServletResponse response,
            HttpServletRequest request
    ) {
        if (UserContext.getCurrentUsername(request).isEmpty()) {
            return UserContext.unAuthorizedError(response);
        }
        String title = (String) requestData.get("title");
        String author = (String) requestData.get("author");
        String description = (String) requestData.get("description");
        Long price = ((Number) requestData.get("price")).longValue();
        String cover = (String) requestData.get("cover");
        List<String> tags = (List<String>) requestData.get("tags");

        bookService.insertBook(title, author, description, price, cover, tags);
        return Map.of(
                "status", 200,
                "message", "ok",
                "ok", true
        );
    }
}

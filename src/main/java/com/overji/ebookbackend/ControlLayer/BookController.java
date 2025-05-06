package com.overji.ebookbackend.ControlLayer;

import com.overji.ebookbackend.EntityLayer.Comment;
import com.overji.ebookbackend.EntityLayer.User;
import com.overji.ebookbackend.ServiceLayer.BookService;
import com.overji.ebookbackend.ServiceLayer.CommentService;
import com.overji.ebookbackend.ServiceLayer.UserService;
import com.overji.ebookbackend.Utils.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BookController {
    private final BookService bookService;
    private final CommentService commentService;
    private final UserService userService;

    public BookController(BookService bookService, CommentService commentService, UserService userService) {
        this.bookService = bookService;
        this.commentService = commentService;
        this.userService = userService;
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

    @GetMapping("/books/rank")
    public Object getTopBooks(
            HttpServletResponse response,
            HttpServletRequest request
    ) {
        if (UserContext.getCurrentUsername(request).isEmpty()) {
            return UserContext.unAuthorizedError(response);
        }
        return bookService.getTop10Books();
    }

    @GetMapping("/book/{id}")
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

    @GetMapping("/book/tags")
    public Object getTags(
            HttpServletResponse response,
            HttpServletRequest request
    ) {
        if (UserContext.getCurrentUsername(request).isEmpty()) {
            return UserContext.unAuthorizedError(response);
        }
        return bookService.getAllTags();
    }

    @PostMapping("/book/insert")
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

    @GetMapping("/book/{id}/comments")
    public Map<String, Object> getBookComments(
            @PathVariable Long id,
            @RequestParam String sort,
            @RequestParam int pageIndex,
            @RequestParam int pageSize,
            HttpServletResponse response,
            HttpServletRequest request
    ) {
        if (UserContext.getCurrentUsername(request).isEmpty()) {
            return UserContext.unAuthorizedError(response);
        }
        if(id == null){
            response.setStatus(404);
            return Map.of(
                    "status", 404,
                    "message", "Book not found",
                    "ok", false
            );
        }
        if(sort == null){
            sort = "createdTime";
        }
        if (pageIndex < 0) {
            pageIndex = 0;
        }
        if (pageSize < 1) {
            pageSize = 8;
        }
        try{
            String username = UserContext.getCurrentUsername(request);
            User user = userService.getUserByUsername(username);
            return bookService.getBookComments(id, sort, pageIndex, pageSize, user);
        } catch (Exception e) {
            response.setStatus(404);
            return Map.of(
                    "status", 404,
                    "message", e.getMessage(),
                    "ok", false
            );
        }
    }

    @PostMapping("/book/{id}/comments")
    public Map<String, Object> postBookComment(
            @PathVariable Long id,
            @RequestBody Map<String, Object> requestData,
            HttpServletResponse response,
            HttpServletRequest request
    ) {
        if (UserContext.getCurrentUsername(request).isEmpty()) {
            return UserContext.unAuthorizedError(response);
        }
        String content = (String) requestData.get("content");
        String username = UserContext.getCurrentUsername(request);
        User user = userService.getUserByUsername(username);
        bookService.postBookComments(id, content, user);
        return Map.of(
                "status", 200,
                "message", "ok",
                "ok", true
        );
    }
}

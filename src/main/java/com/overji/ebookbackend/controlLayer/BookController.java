package com.overji.ebookbackend.controlLayer;

import com.overji.ebookbackend.entityLayer.User;
import com.overji.ebookbackend.serviceLayer.BookService;
import com.overji.ebookbackend.serviceLayer.CommentService;
import com.overji.ebookbackend.serviceLayer.UserService;
import com.overji.ebookbackend.utils.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
/*
* this controller is used to handle all the requests related to books
* it includes:
* 1. get all books
* 2. get book by id
* 3. get all tags
* 4. insert book
* 5. get book comments
* 6. post book comment
* 7. get top 10 books
* 8. get books by tag
* 9. get books by keyword
*/

@RestController
@RequestMapping("/api")
public class BookController {
    private final BookService bookService;
    private final CommentService commentService;
    private final UserService userService;

    // Constructor injection for services
    // which can be autowired by Spring
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


        // Validate and sanitize input parameters
        tag = tag == null ? "" : tag.trim();
        keyword = keyword == null ? "" : keyword.trim();

        if (pageIndex < 0) {
            pageIndex = 0;
        }
        if (pageSize < 1) {
            pageSize = 8;
        }
        // get books by tag and(or) keyword
        return bookService.getBooks(tag, keyword, pageIndex, pageSize);
    }

    @GetMapping("/books/rank")
    public Object getTopBooks(
            HttpServletResponse response,
            HttpServletRequest request
    ) {

        // get top 10 books
        return bookService.getTop10Books();
    }

    @DeleteMapping("/book_covers/{filename}/del")
    public Map<String, Object> deleteBookCover(
            @PathVariable String filename,
            HttpServletResponse response,
            HttpServletRequest request
    ) {

        // delete book cover
        String filePath = System.getProperty("user.dir") + File.separator + "book-covers" + File.separator + filename;
        File file = new File(filePath);
        if (file.exists()) {
            if (file.delete()) {
                return Map.of(
                        "status", 200,
                        "message", "ok",
                        "ok", true
                );
            } else {
                response.setStatus(500);
                return Map.of(
                        "status", 500,
                        "message", "Failed to delete file"
                );
            }
        } else {
            response.setStatus(404);
            return Map.of(
                    "status", 404,
                    "message", "File not found"
            );
        }
    }

    @PostMapping("/books/cover")
    public Map<String, Object> uploadCover(@RequestParam("file") org.springframework.web.multipart.MultipartFile file,
                                            HttpServletRequest request,
                                            HttpServletResponse response) {
        if (file == null || file.isEmpty()) {
            return Map.of(
                    "status", 400,
                    "message", "File is empty"
            );
        }
        try{
            // 检查文件类型
            String originalFilename = file.getOriginalFilename();
            String fileExtension;
            if (originalFilename != null && originalFilename.endsWith(".jpg")) {
                fileExtension = ".jpg";
            } else if (originalFilename != null && originalFilename.endsWith(".png")) {
                fileExtension = ".png";
            } else {
                response.setStatus(400);
                return Map.of(
                        "status", 400,
                        "message", "Unsupported file format"
                );
            }

            // 保存文件到当前运行文件夹下的 ./avatars 文件夹
            String savePath = System.getProperty("user.dir") + File.separator + "book-covers" + File.separator;
            String filename = System.currentTimeMillis() + fileExtension; // 使用当前时间戳作为文件名
            File directory = new File(savePath);
            if (!directory.exists()) {
                if (!directory.mkdirs()) { // 创建目录失败
                    response.setStatus(500);
                    return Map.of(
                            "status", 500,
                            "message", "Failed to create directory for file upload"
                    );
                }
            }

            String filePath = savePath + filename;
            file.transferTo(new File(filePath)); // 保存文件
            // 返回文件路径
            return Map.of(
                    "status", 200,
                    "message", "ok",
                    "ok", true,
                    "filePath", "book_covers/" + filename // 返回文件路径
            );
        } catch (Exception e) {
            response.setStatus(500);
            return Map.of(
                    "status", 500,
                    "message", "File upload failed: " + e.getMessage()
            );
        }
    }

    @GetMapping("/book/{id}")
    public Map<String, Object> getBookById(
            @PathVariable Long id,
            HttpServletResponse response,
            HttpServletRequest request
    ) {

        try{
            // get book by id
            return bookService.getBookById(id);
        } catch (Exception e) {
            // if the book is not found
            response.setStatus(404);
            return Map.of(
                    "status", 404,
                    "message", "Book not found",
                    "ok", false
            );
        }
    }

    @DeleteMapping("/book/{id}")
    public Map<String, Object> deleteBook(
            @PathVariable Long id,
            HttpServletResponse response,
            HttpServletRequest request
    ) {

        // delete book by id
        bookService.deleteBook(id);
        return Map.of(
                "status", 200,
                "message", "ok",
                "ok", true
        );
    }

    @GetMapping("/book/tags")
    public Object getTags(
            HttpServletResponse response,
            HttpServletRequest request
    ) {

        // get all tags
        return bookService.getAllTags();
    }

    @PostMapping("/book/insert")
    public Map<String, Object> insertBook(
            @RequestBody Map<String, Object> requestData,
            HttpServletResponse response,
            HttpServletRequest request
    ) {

        // insert book
        String title = (String) requestData.get("title");
        String author = (String) requestData.get("author");
        String description = (String) requestData.get("description");
        Long price = ((Number) requestData.get("price")).longValue();
        Long stock = ((Number) requestData.get("stock")).longValue();
        String ISBN = (String) requestData.get("ISBN");
        String cover = (String) requestData.get("cover");
        List<String> tags = (List<String>) requestData.get("tags");

        // Validate input data
        if(title == null){
            title = "";
        }
        if(author == null){
            author = "";
        }
        if(description == null){
            description = "";
        }
        if (price == null || price < 0 || cover == null || cover.isEmpty() || tags == null || tags.isEmpty()) {
            response.setStatus(400);
            return Map.of(
                    "status", 400,
                    "message", "Invalid input data",
                    "ok", false
            );
        }

        try{
            return bookService.insertBook(title, author, description, price, cover, tags, stock, ISBN);
        } catch (Exception e) {
            response.setStatus(500);
            return Map.of(
                    "status", 500,
                    "message", "Failed to insert book: " + e.getMessage(),
                    "ok", false
            );
        }
    }

    @PostMapping("/book/modify/{id}")
    public Map<String, Object> updateBook(
            @PathVariable Long id,
            @RequestBody Map<String, Object> requestData,
            HttpServletResponse response,
            HttpServletRequest request
    ) {
        // update book
        String title = (String) requestData.get("title");
        String author = (String) requestData.get("author");
        String description = (String) requestData.get("description");
        Long price = ((Number) requestData.get("price")).longValue();
        Long stock = ((Number) requestData.get("stock")).longValue();
        String ISBN = (String) requestData.get("ISBN");
        String cover = (String) requestData.get("cover");
        List<String> tags = (List<String>) requestData.get("tags");
        System.out.println("Updating book with ID: " + id);
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Description: " + description);
        System.out.println("Price: " + price);
        System.out.println("Stock: " + stock);
        System.out.println("ISBN: " + ISBN);
        System.out.println("Cover: " + cover);
        System.out.println("Tags: " + tags);
        // Validate input data
        if(title == null){
            title = "";
        }
        if(author == null){
            author = "";
        }
        if(description == null){
            description = "";
        }
        if (price == null || price < 0 || cover == null || cover.isEmpty() || tags == null || tags.isEmpty()) {
            response.setStatus(400);
            return Map.of(
                    "status", 400,
                    "message", "Invalid input data",
                    "ok", false
            );
        }
        try {
            // update book by id
            return bookService.updateBook(id, title, author, description, price, cover, tags, stock, ISBN);
        } catch (Exception e) {
            response.setStatus(404);
            return Map.of(
                    "status", 404,
                    "message", e.getMessage(),
                    "ok", false
            );
        }
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

        // get book comments by parameters: id, sort, pageIndex, pageSize
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
            // get book comments by id
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

        // post book comment
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

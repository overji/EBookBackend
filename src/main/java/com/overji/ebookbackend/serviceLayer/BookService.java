package com.overji.ebookbackend.serviceLayer;

import com.overji.ebookbackend.entityLayer.User;

import java.util.List;
import java.util.Map;

public interface BookService {
    Map<String, Object> getBooks(String tag, String keyword, int pageIndex, int pageSize, boolean isAdmin);

    Map<String, Object> getBookById(Long id);

    Map<String, Object> updateBook(Long id, String title, String author, String description, Long price, String cover, List<String> tags, Long stock, String ISBN);

    Map<String, Object> insertBook(String title, String author, String description, Long price, String cover, List<String> tags, Long stock, String ISBN);

    Map<String, Object> updateSales(Long id, Long sales);

    Map<String, Object> deleteBook(Long id);

    List<String> getAllTags();

    List<Map<String, Object>> getTop10Books();

    Map<String, Object> getBookComments(Long bookId, String orderBy, int pageIndex, int pageSize, User user);

    Map<String, Object> postBookComments(Long bookId, String content, User user);

    Map<String,Object> setBookDeleted(Long bookId, boolean isDisabled);
}
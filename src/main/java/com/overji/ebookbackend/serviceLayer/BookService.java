package com.overji.ebookbackend.serviceLayer;

import com.overji.ebookbackend.entityLayer.User;

import java.util.List;
import java.util.Map;

public interface BookService {
    Map<String, Object> getBooks(String tag, String keyword, int pageIndex, int pageSize);

    Map<String, Object> getBookById(Long id);

    void insertBook(String title, String author, String description, Long price, String cover, List<String> tags);

    void updateSales(Long id, Long sales);

    List<String> getAllTags();

    List<Map<String, Object>> getTop10Books();

    Map<String, Object> getBookComments(Long bookId, String orderBy, int pageIndex, int pageSize, User user);

    Map<String, Object> postBookComments(Long bookId, String content, User user);
}
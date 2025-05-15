package com.overji.ebookbackend.DAOLayer;

import com.overji.ebookbackend.EntityLayer.BookTag;

import java.util.List;

public interface BookTagsDAO {
    void save(BookTag bookTag);
    List<BookTag> findAll();
}
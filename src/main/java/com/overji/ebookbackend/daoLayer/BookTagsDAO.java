package com.overji.ebookbackend.daoLayer;

import com.overji.ebookbackend.entityLayer.BookTag;

import java.util.List;

public interface BookTagsDAO {
    void save(BookTag bookTag);
    List<BookTag> findAll();
}
package com.overji.ebookbackend.DAOLayer.imple;

import com.overji.ebookbackend.DAOLayer.BookTagsDAO;
import com.overji.ebookbackend.EntityLayer.BookTag;
import com.overji.ebookbackend.RepositoryLayer.BookTagsRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookTagsDAOImpl implements BookTagsDAO {

    private final BookTagsRepository bookTagsRepository;

    public BookTagsDAOImpl(BookTagsRepository bookTagsRepository) {
        this.bookTagsRepository = bookTagsRepository;
    }

    @Override
    public void save(BookTag bookTag) {
        bookTagsRepository.save(bookTag);
    }

    @Override
    public List<BookTag> findAll() {
        return bookTagsRepository.findAll();
    }
}
package com.overji.ebookbackend.daoLayer.Implementation;

import com.overji.ebookbackend.daoLayer.BookTagsDAO;
import com.overji.ebookbackend.entityLayer.BookTag;
import com.overji.ebookbackend.repositoryLayer.BookTagsRepository;
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
        List<BookTag> tags = bookTagsRepository.findAll();
        return tags.stream()
                .filter(tag -> tag.getBook() != null && tag.getBook().getId() != null)
                .toList();
    }

    @Override
    public void delete(BookTag tag) {
        bookTagsRepository.delete(tag);
    }
}
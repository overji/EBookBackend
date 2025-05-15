package com.overji.ebookbackend.RepositoryLayer;

import com.overji.ebookbackend.EntityLayer.BookTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookTagsRepository extends JpaRepository<BookTag, Long> {

}

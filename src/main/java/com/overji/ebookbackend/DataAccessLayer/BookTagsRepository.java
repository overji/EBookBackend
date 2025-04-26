package com.overji.ebookbackend.DataAccessLayer;

import com.overji.ebookbackend.EntityLayer.BookTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookTagsRepository extends JpaRepository<BookTag, Long> {

}

package com.overji.ebookbackend.DataAccessLayer;

import com.overji.ebookbackend.EntityLayer.BookTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookTagsRepository extends JpaRepository<BookTag, Long> {

}

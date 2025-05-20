package com.overji.ebookbackend.repositoryLayer;

import com.overji.ebookbackend.entityLayer.BookTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookTagsRepository extends JpaRepository<BookTag, Long> {

}

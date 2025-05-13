package com.overji.ebookbackend.DataAccessLayer;
import com.overji.ebookbackend.EntityLayer.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {

}

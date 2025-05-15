package com.overji.ebookbackend.RepositoryLayer;
import com.overji.ebookbackend.EntityLayer.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {

}

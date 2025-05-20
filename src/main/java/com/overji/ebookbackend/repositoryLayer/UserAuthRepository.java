package com.overji.ebookbackend.repositoryLayer;
import com.overji.ebookbackend.entityLayer.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {

}

package server.mail.persistence.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.mail.persistence.admin.AdminEntity;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<AdminEntity, Long> {


    Optional<AdminEntity> findByUserId(String userId);
}

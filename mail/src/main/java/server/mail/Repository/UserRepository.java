package server.mail.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.mail.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
}

package server.mail.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.mail.domain.Review;

import java.util.List;
import java.util.Optional;


public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<List<Review>> findAllByProductIdOrderByUserNameAsc(Long id);
    Optional<List<Review>> findAllByUserIdOrderByProductNameAsc(Long id);

}

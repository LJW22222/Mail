package server.mail.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import server.mail.domain.Category;
import server.mail.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p order by p.name asc")
    Optional<List<Product>> findAllOrderByName();

    @Query("select p from Product p order by p.price asc")
    Optional<List<Product>> findAllOrderByPrice();

    @Query("select p from Product p order by p.id asc")
    Optional<List<Product>> findAllOrderById();

    Optional<Product> findAllById(Long id);


}

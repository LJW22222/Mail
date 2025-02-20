package server.mail.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import server.mail.domain.Category;
import server.mail.domain.Product;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {


    @Query("select c from Category c order by c.name asc")
    Optional<List<Category>> findAllCategoryByName();


    @Query("select c from Category c order by c.id asc")
    Optional<List<Category>> findCategoryListNoSort();

    @Query("select c.name from Category c where c.id = :id")
    Optional<String> findCategory(@Param("id") Long id);

}

package ir.darkdeveloper.testcontainers.repo;

import ir.darkdeveloper.testcontainers.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    @Query("select p from Product p " +
            "where upper(p.name) like upper(concat('%', :str, '%')) " +
            "or upper(p.description) like upper(concat('%', :str, '%')) ")
    List<Product> findByNameAndDescriptionContainsIgnoreCase(String str);
}

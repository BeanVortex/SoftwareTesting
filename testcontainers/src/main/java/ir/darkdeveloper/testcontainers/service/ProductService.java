package ir.darkdeveloper.testcontainers.service;


import ir.darkdeveloper.testcontainers.model.Product;
import ir.darkdeveloper.testcontainers.repo.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo repo;

    public Product save(Product product) {
        return repo.save(product);
    }

    public List<Product> saveAll(List<Product> products) {
        return repo.saveAll(products);
    }

    public List<Product> getAll() {
        return repo.findAll();
    }

    public Product getById(Long Id) {
        return repo.findById(Id).orElse(null);
    }

    public List<Product> getByNameAndDescriptionContainsIgnoreCase(String str) {
        return repo.findByNameAndDescriptionContainsIgnoreCase(str);
    }

    public boolean deleteById(Long Id) {
        try {
            repo.deleteById(Id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void deleteAll() {
        repo.deleteAll();
    }
}

package ir.darkdeveloper.mocking.service;

import ir.darkdeveloper.mocking.model.Product;
import ir.darkdeveloper.mocking.repo.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo repo;

    public List<Product> getAll() {
        // your logic goes here
        // and this logic is going to be tested
        return repo.findAll();
    }

    public Product getById(Long Id){
        // your logic goes here
        // and this logic is going to be tested
        return repo.findById(Id).orElse(null);
    }
}

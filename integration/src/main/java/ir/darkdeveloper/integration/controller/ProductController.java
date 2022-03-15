package ir.darkdeveloper.integration.controller;

import ir.darkdeveloper.integration.model.Product;
import ir.darkdeveloper.integration.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService service;

    @PostMapping("/save/")
    public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
        return ResponseEntity.ok(service.save(product));
    }
    @GetMapping("/{id}/")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }
    @GetMapping("/")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(service.getAll());
    }
    @GetMapping("/search/")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam("str") String str) {
        return ResponseEntity.ok(service.getByNameAndDescriptionContainsIgnoreCase(str));
    }
}



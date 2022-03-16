package ir.darkdeveloper.testcontainers.repo;

import ir.darkdeveloper.testcontainers.model.Product;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class ProductRepoTest {
    private final ProductRepo productRepo;

    @Autowired
    ProductRepoTest(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @BeforeEach
    void saveProducts() {
        var products = List.of(
                new Product("some_name_Abc", "DesCriPtiOn", new BigDecimal(1)),
                new Product("NaMeNumber2", "Some_description25", new BigDecimal(1)),
                new Product("A Name For Product", "Contains Description About Product",
                        new BigDecimal("136.54")),
                new Product("نام محصول", "توضیحات محصول", new BigDecimal("136.54"))
        );
        productRepo.saveAll(products);
    }

    @Test
    void findByNameAndDescriptionContains1() {
        var products = productRepo.findByNameAndDescriptionContainsIgnoreCase("name");
        System.out.println(products);
        assertEquals(3, products.size());
    }

    @Test
    void findByNameAndDescriptionContains2() {
        var products = productRepo.findByNameAndDescriptionContainsIgnoreCase("dEscRiptiOn");
        assertEquals(3, products.size());
    }

    @Test
    void findByNameAndDescriptionContains3() {
        var products = productRepo.findByNameAndDescriptionContainsIgnoreCase("نام");
        assertEquals(1, products.size());
    }

}
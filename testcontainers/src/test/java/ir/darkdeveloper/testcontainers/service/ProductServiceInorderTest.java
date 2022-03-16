package ir.darkdeveloper.testcontainers.service;

import ir.darkdeveloper.testcontainers.model.Product;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class ProductServiceInorderTest {

    private final ProductService service;
    private static final List<Long> productIds = new ArrayList<>();

    @Autowired
    public ProductServiceInorderTest(ProductService service) {
        this.service = service;
    }

    @Test
    @Order(1)
    void saveAll() {
        var products = List.of(
                new Product("some_name_Abc", "DesCriPtiOn", new BigDecimal(1)),
                new Product("NaMeNumber2", "Some_description25", new BigDecimal(1)),
                new Product("A Name For Product", "Contains Description About Product",
                        new BigDecimal("136.54")),
                new Product("نام محصول", "توضیحات محصول", new BigDecimal("136.54"))
        );
        var savedProducts = service.saveAll(products);
        assertNotNull(savedProducts);
        assertThat(savedProducts).isNotEmpty();
        products.forEach(product -> productIds.add(product.getId()));
    }

    @Test
    @Order(2)
    void getAll() {
        var fetchedProducts = service.getAll();
        System.out.println(fetchedProducts);
        assertThat(fetchedProducts).isNotEmpty();
        assertThat(fetchedProducts.size()).isEqualTo(4);
        IntStream.range(0, fetchedProducts.size()).forEach(i ->
                assertThat(fetchedProducts.get(i).getId()).isEqualTo(productIds.get(i))
        );
    }

    @Test
    @Order(3)
    void getById() {
        var fetchedProduct = service.getById(productIds.get(0));
        assertThat(fetchedProduct).isNotNull();
        assertThat(fetchedProduct.getId()).isEqualTo(productIds.get(0));
    }

    @Test
    @Order(4)
    void getByNameAndDescriptionContainsIgnoreCase() {
        var fetchProducts = service.getByNameAndDescriptionContainsIgnoreCase("abc");
        assertThat(fetchProducts).isNotEmpty();
        assertThat(fetchProducts.size()).isEqualTo(1);
    }

    @RepeatedTest(4)
    @Order(5)
    void deleteById() {
        var deletedProduct = service.deleteById(productIds.get(0));
        productIds.remove(0);
        assertThat(deletedProduct).isTrue();
    }
}
package ir.darkdeveloper.integration.service;

import ir.darkdeveloper.integration.model.Product;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProductServiceNoOrderTest {

    private final ProductService service;
    private List<Long> productIds;

    @Autowired
    public ProductServiceNoOrderTest(ProductService service) {
        this.service = service;
    }

    @BeforeEach
    public void setUp() {
        service.deleteAll();
        var products = List.of(
                new Product("some_name_Abc", "DesCriPtiOn", new BigDecimal(1)),
                new Product("NaMeNumber2", "Some_description25", new BigDecimal(1)),
                new Product("A Name For Product", "Contains Description About Product",
                        new BigDecimal("136.54")),
                new Product("نام محصول", "توضیحات محصول", new BigDecimal("136.54"))
        );
        service.saveAll(products);
        productIds = new ArrayList<>();
        products.forEach(product -> productIds.add(product.getId()));
    }

    @Test
    void getAll() {
        var fetchedProducts = service.getAll();
        assertThat(fetchedProducts).isNotEmpty();
        assertThat(fetchedProducts.size()).isEqualTo(4);
        IntStream.range(0, fetchedProducts.size()).forEach(i ->
                assertThat(fetchedProducts.get(i).getId()).isEqualTo(productIds.get(i))
        );
    }

    @Test
    void getById() {
        var fetchedProduct = service.getById(productIds.get(0));
        assertThat(fetchedProduct).isNotNull();
        assertThat(fetchedProduct.getId()).isEqualTo(productIds.get(0));
    }

    @Test
    void getByNameAndDescriptionContainsIgnoreCase() {
        var fetchProducts = service.getByNameAndDescriptionContainsIgnoreCase("abc");
        assertThat(fetchProducts).isNotEmpty();
        assertThat(fetchProducts.size()).isEqualTo(1);
    }

    @Test()
    void deleteById() {
        var deletedProduct = service.deleteById(productIds.get(0));
        assertThat(deletedProduct).isTrue();
    }
}

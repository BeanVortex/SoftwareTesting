package ir.darkdeveloper.mocking.service;

import ir.darkdeveloper.mocking.model.Product;
import ir.darkdeveloper.mocking.repo.ProductRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepo repo;
    private ProductService service;

    @BeforeEach
    void setUp() {
        service = new ProductService(repo);
    }

    @Test
    void getAll() {
        // given
        List<Product> given = List.of(
                new Product(1L, "name", "description", new BigDecimal(1)),
                new Product(2L, "name2", "description2", new BigDecimal(1)),
                new Product(2L, "name3", "description3", new BigDecimal("136.54"))
        );
        // when
        when(repo.findAll()).thenReturn(given);

        // then
        List<Product> products = service.getAll();
        assertThat(products.size()).isEqualTo(3);
    }

    @Test
    void getById() {
        // given
        Product given = new Product(1L, "name", "description", new BigDecimal(1));
        // when
        when(repo.findById(1L)).thenReturn(Optional.of(given));
        // then
        Product product = service.getById(1L);
        assertThat(product.getId()).isEqualTo(1L);
        assertThat(product.getName()).isEqualTo("name");
        assertThat(product.getDescription()).isEqualTo("description");
    }

}
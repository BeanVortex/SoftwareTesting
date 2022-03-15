package ir.darkdeveloper.integration.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.darkdeveloper.integration.model.Product;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.math.BigDecimal;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureRestDocs("docs/product")
class ProductControllerTest {

    private final WebApplicationContext webApplicationContext;
    private final RestDocumentationContextProvider restDocumentation;
    private MockMvc mockMvc;
    private static Long productId;

    @Autowired
    ProductControllerTest(WebApplicationContext webApplicationContext,
                          RestDocumentationContextProvider restDocumentation) {
        this.webApplicationContext = webApplicationContext;
        this.restDocumentation = restDocumentation;
    }

    @BeforeEach
    void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(document("{methodName}"))
                .build();
    }

    @Test
    @Order(1)
    void saveProduct() throws Exception {
        var product = Product.builder()
                .name("productName")
                .description("productDescription")
                .price(BigDecimal.valueOf(102.5))
                .build();

        mockMvc.perform(post("/api/v1/products/save/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapToJson(product))
                )
                .andDo(print())
                .andExpect(status().isOk())
                // means is it an object?
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.name").value("productName"))
                .andExpect(jsonPath("$.description").value("productDescription"))
                .andExpect(jsonPath("$.price").value(BigDecimal.valueOf(102.5)))
                .andDo(result -> {
                    var object = new JSONObject(result.getResponse().getContentAsString());
                    // your assertions here
                    productId = object.getLong("id");
                });

    }

    @Test
    @Order(2)
    void getProduct() throws Exception {
        mockMvc.perform(get("/api/v1/products/{id}/", productId)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.id").value(productId))
                .andExpect(jsonPath("$.name").value("productName"))
                .andExpect(jsonPath("$.description").value("productDescription"))
                .andExpect(jsonPath("$.price").value(BigDecimal.valueOf(102.5)));
    }

    @Test
    @Order(3)
    void getAllProducts() throws Exception {
        mockMvc.perform(get("/api/v1/products/")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0].id").value(productId))
                .andExpect(jsonPath("$.[0].name").value("productName"))
                .andExpect(jsonPath("$.[0].description").value("productDescription"))
                .andExpect(jsonPath("$.[0].price").value(BigDecimal.valueOf(102.5)));
    }

    @Test
    @Order(4)
    void searchProducts() throws Exception {
        mockMvc.perform(get("/api/v1/products/search/")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("str", "descript")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0].id").value(productId))
                .andExpect(jsonPath("$.[0].name").value("productName"))
                .andExpect(jsonPath("$.[0].description").value("productDescription"))
                .andExpect(jsonPath("$.[0].price").value(BigDecimal.valueOf(102.5)));
    }

    private String mapToJson(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }
}
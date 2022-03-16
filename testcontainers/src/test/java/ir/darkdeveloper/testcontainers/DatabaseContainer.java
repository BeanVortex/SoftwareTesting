package ir.darkdeveloper.testcontainers;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

public class DatabaseContainer {

    private static final PostgreSQLContainer container =
            (PostgreSQLContainer) new PostgreSQLContainer("postgres:13.1-alpine")
                    .withReuse(true);

    // this script should be run for the first time
    // echo testcontainers.reuse.enable=true  > ~/.testcontainers.properties

    static {
        container.start();
    }

    @DynamicPropertySource
    public static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.driver-class-name", container::getDriverClassName);
        registry.add("spring.jpa.properties.hibernate.dialect", () -> "org.hibernate.dialect.PostgreSQLDialect");
    }

}
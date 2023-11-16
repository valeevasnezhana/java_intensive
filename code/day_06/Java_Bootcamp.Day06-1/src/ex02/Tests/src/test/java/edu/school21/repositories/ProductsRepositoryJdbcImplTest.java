package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ProductsRepositoryJdbcImplTest {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(ProductsRepositoryJdbcImplTest.class);
    private DataSource dataSource;
    private ProductsRepository productsRepository;


    private final List<Product> EXPECTED_FIND_ALL_PRODUCTS = Arrays.asList(
            new Product(0L, "Product1", 100.0),
            new Product(1L, "Product2", 200.0),
            new Product(2L, "Product3", 250.0),
            new Product(3L, "Product4", 120.0),
            new Product(4L, "Product5", 150.0));

    private final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(
            1L, "Product2", 200.0);

    private final Product EXPECTED_UPDATED_PRODUCT = new Product(
            0L, "UpdatedProduct", 150.0);


    @BeforeEach
    public void setUp() {
        LOGGER.info("Setting up a new test case");
        dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
        productsRepository = new ProductsRepositoryJdbcImpl(dataSource);
    }

    @AfterEach
    public void tearDown() {
        LOGGER.info("Tearing down the current test " +
                "case\n__________________________________________________");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute("DROP TABLE product;");
    }

    @Test
    public void findAllTest() {
        LOGGER.info("Initiating findAllTest...");
        List<Product> products = productsRepository.findAll();
        assertEquals(EXPECTED_FIND_ALL_PRODUCTS, products);
        LOGGER.info("findAllTest successfully passed");
    }

    @Test
    public void findByIdTest() {
        LOGGER.info("Initiating findByIdTest...");
        Optional<Product> product = productsRepository.findById(1L);
        assertEquals(EXPECTED_FIND_BY_ID_PRODUCT, product.orElse(null));
        LOGGER.info("findByIdTest successfully passed");
    }

    @Test
    public void updateTest() {
        LOGGER.info("Initiating updateTest...");
        Product productToUpdate = new Product(0L, "UpdatedProduct", 150.0);
        productsRepository.update(productToUpdate);
        Optional<Product> updatedProduct = productsRepository.findById(0L);
        assertEquals(EXPECTED_UPDATED_PRODUCT, updatedProduct.orElse(null));
        LOGGER.info("updateTest successfully passed");
    }

    @Test
    public void saveTest() {
        LOGGER.info("Initiating saveTest...");
        Product productToSave = new Product(5L, "SavedProduct", 200.0);
        productsRepository.save(productToSave);
        Optional<Product> savedProduct = productsRepository.findById(5L);
        assertEquals(new Product(5L, "SavedProduct", 200.0),savedProduct.orElse(null));
        LOGGER.info("saveTest successfully passed");
    }

    @Test
    public void deleteTest() {
        LOGGER.info("Initiating deleteTest...");
        List<Product> products = productsRepository.findAll();
        assertEquals(EXPECTED_FIND_ALL_PRODUCTS, products);
        productsRepository.delete(0L);
        products = productsRepository.findAll();
        assertEquals(EXPECTED_FIND_ALL_PRODUCTS.subList(1, 5), products);
        LOGGER.info("deleteTest successfully passed");
    }
}



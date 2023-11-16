package edu.school21.repositories;

import edu.school21.models.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(ProductsRepositoryJdbcImpl.class);
    private final JdbcTemplate jdbcTemplate;

    public ProductsRepositoryJdbcImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    RowMapper<Product> rowMapper = (rs, rowNum) -> new Product(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getDouble("price")
    );

    @Override
    public List<Product> findAll() {
        LOGGER.debug("Fetching all products");
        List<Product> products =
                jdbcTemplate.query("SELECT * FROM product", rowMapper);
        LOGGER.debug("Retrieved {} products", products.size());
        return products;
    }

    @Override
    public Optional<Product> findById(Long id) {
        LOGGER.debug("Locating product by id {}", id);
        List<Product> products =
                jdbcTemplate.query("SELECT * FROM product WHERE id=?",
                        new Object[]{id}, rowMapper);
        if (products.isEmpty()) {
            LOGGER.debug("No product found with id {}", id);
            return Optional.empty();
        } else {
            LOGGER.debug("Product found: {}", products.get(0));
            return Optional.of(products.get(0));
        }
    }

    @Override
    public void update(Product product) {
        LOGGER.debug("Updating product with id {}", product.getId());
        jdbcTemplate.update(
                "UPDATE product SET name=?, price=? WHERE id=?",
                product.getName(), product.getPrice(), product.getId()
        );
        LOGGER.debug("Updated product with id {}", product.getId());
    }


    @Override
    public void save(Product product) {
        LOGGER.debug("Saving product: {}", product);
        jdbcTemplate.update(
                "INSERT INTO product (name, price) VALUES (?, ?)",
                product.getName(), product.getPrice()
        );
        LOGGER.debug("Saved product: {}", product);
    }

    @Override
    public void delete(Long id) {
        LOGGER.debug("Deleting product with id: {}", id);
        jdbcTemplate.update("DELETE FROM product WHERE id=?", id);
        LOGGER.debug("Deleted product with id: {}", id);
    }
}

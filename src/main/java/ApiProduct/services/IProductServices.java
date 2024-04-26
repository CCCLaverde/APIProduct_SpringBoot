package ApiProduct.services;

import ApiProduct.models.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IProductServices {

    List<Product> findAll();

    Optional<Product> findById(Long id);

    List<Product> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);

    void save(Product product);

    void deleteById(Long id);
}

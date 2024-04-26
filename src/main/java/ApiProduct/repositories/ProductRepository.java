package ApiProduct.repositories;

import ApiProduct.models.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    //Dos formas de hacer la funcion a implementar desde el repositorio
    //una es la función con la notación @Query de JPA
    //  y la otra con Query metods

    @Query("SELECT p FROM Product p WHERE p.price BETWEEN ?1 AND ?2")
    List<Product> findProductByPriceInRange(BigDecimal minPrice, BigDecimal maxPrice);

    List<Product> findProductByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
}

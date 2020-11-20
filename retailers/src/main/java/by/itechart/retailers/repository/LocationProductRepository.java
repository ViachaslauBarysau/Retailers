package by.itechart.retailers.repository;

import by.itechart.retailers.entity.LocationProduct;
import by.itechart.retailers.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationProductRepository extends JpaRepository<LocationProduct, Long> {
    boolean existsByProduct(Product product);
}

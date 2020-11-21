package by.itechart.retailers.repository;

import by.itechart.retailers.entity.LocationProduct;
import by.itechart.retailers.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationProductRepository extends JpaRepository<LocationProduct, Long> {

    List<LocationProduct> findAllByProduct(Product product);
}

package by.itechart.retailers.repository;

import by.itechart.retailers.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

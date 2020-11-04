package by.itechart.retailers.repository;

import by.itechart.retailers.entity.SupplierApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierApplicationRepository extends JpaRepository<SupplierApplication, Long> {
}

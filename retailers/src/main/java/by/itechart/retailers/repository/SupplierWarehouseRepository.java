package by.itechart.retailers.repository;

import by.itechart.retailers.entity.DeletedStatus;
import by.itechart.retailers.entity.SupplierWarehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierWarehouseRepository extends JpaRepository<SupplierWarehouse, Long> {

    List<SupplierWarehouse> findAllByCustomer_IdAndStatus(Long customerId, DeletedStatus status);
}

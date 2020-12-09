package by.itechart.retailers.repository;

import by.itechart.retailers.entity.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    Page<Supplier> findAllByCustomer_Id(Pageable pageable, Long id);

    List<Supplier> findAllByIdentifierIgnoreCaseAndCustomer_Id(String identifier, Long customerId);

}

package by.itechart.retailers.repository;

import by.itechart.retailers.entity.Bill;
import by.itechart.retailers.entity.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    List<Bill> findAllByBillNumberAndCustomer_Id(Integer billNumber, Long customerId);

    Page<Bill> findAllByLocationIn(Pageable pageable, List<Location> locations);

}
